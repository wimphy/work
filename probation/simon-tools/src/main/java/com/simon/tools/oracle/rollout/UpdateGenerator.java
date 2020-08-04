package com.simon.tools.oracle.rollout;

import com.simon.tools.GenWriter;
import com.simon.tools.services.GenTask;
import com.simon.tools.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.simon.tools.utils.Strings.formatBatchName;

public class UpdateGenerator implements IGenerator {
    protected static final Logger logger = LoggerFactory.getLogger(IGenerator.class);
    private static final String sqlFormat = "update %1$s set description=%2$s where id=%3$s";
    protected ArrayBlockingQueue<GenTask> queue = new ArrayBlockingQueue<>(2000);
    protected ConcurrentHashMap<String, GenWriter> writersMap = new ConcurrentHashMap<>();

    protected AtomicBoolean stop = new AtomicBoolean(false);
    protected @Autowired ExecutorService executorService;
    protected @Value("${db.rows.batch}") int batchSize;

    @Override
    public void take(GenTask genTask) throws InterruptedException {
        if (genTask != null) {
            queue.put(genTask);
        }
    }

    @PostConstruct
    private void generate() {
        executorService.execute(() -> {
            while (!stop.get() || queue.size() > 0) {
                GenTask genTask = queue.poll();
                if (genTask == null) {
                    oneSecond();
                    continue;
                }
                doTask(genTask);
            }
            cleanup();
            logger.info(getClassName() + " stopped");
        });
    }

    public void stop() {
        stop.set(true);
        logger.info(getClassName() + " is stopping");
    }

    protected void cleanup() {
        for (GenWriter writer : writersMap.values()) {
            try {
                writer.newLine();
                writer.append("commit;");
                writer.newLine();
                Common.close(writer);
            } catch (IOException e) {
                logger.error("failed to close writer:" + writer, e);
            }
        }
    }

    protected String getClassName() {
        String res;
        Class<?> enclosingClass = getClass().getEnclosingClass();
        if (enclosingClass != null) {
            res = enclosingClass.getName();
        } else {
            res = getClass().getName();
        }
        return res;
    }

    protected void doTask(GenTask genTask) {
        if (genTask == null) {
            return;
        }
        try {
            GenWriter writer = createNewWriter(genTask, "0");
            String sql = String.format(sqlFormat, genTask.getTableName(), genTask.getDescription(), genTask.getId());
            writer.append(sql).append(";");
            writer.newLine();

            long rowNumber = genTask.getRowNumber();
            if (rowNumber > 0 && rowNumber % batchSize == 0) {
                writer.newLine();
                writer.append("commit;");
                writer.newLine();
            }
            logger.info(genTask + ":" + sql);
            writer.flush();
        } catch (IOException e) {
            logger.error(genTask.getFileName() + ": failed to write to file, =" + genTask, e);
        }
    }

    protected void oneSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //do nothing
        }
    }

    protected GenWriter createNewWriter(GenTask genTask, String index) throws IOException {
        String sqlFileName = formatBatchName(genTask.getFileName(), index);
        GenWriter writer = writersMap.get(sqlFileName);
        if (writer == null) {
            writer = new GenWriter(sqlFileName);
            writersMap.put(sqlFileName, writer);
            writer.append("set define off;");
            writer.newLine();
        }
        return writer;
    }
}
