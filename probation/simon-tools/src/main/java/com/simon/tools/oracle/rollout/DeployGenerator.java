package com.simon.tools.oracle.rollout;

import com.simon.tools.GenWriter;
import com.simon.tools.services.ConfigurationService;
import com.simon.tools.services.GenTask;
import com.simon.tools.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.simon.tools.utils.Strings.formatPS1Name;

public class DeployGenerator extends UpdateGenerator {
    private static final Logger logger = LoggerFactory.getLogger(DeployGenerator.class);
    private @Autowired ConfigurationService configurationService;

    @Override
    protected void doTask(GenTask genTask) {
        if (genTask == null) {
            return;
        }
        try {
            GenWriter writer = createNewWriter(genTask);
            String sql = configurationService.get("invalidate");
            sql = String.format(sql, genTask.getId());
            sql = String.format("curl \"%s\"", sql);
            writer.append(sql);
            writer.newLine();
            logger.info(genTask + ":" + sql);
            writer.flush();
        } catch (IOException e) {
            logger.error(genTask.getFileName() + ": failed to write to file, =" + genTask, e);
        }
    }

    protected GenWriter createNewWriter(GenTask genTask) throws IOException {
        String sqlFileName = formatPS1Name(genTask.getFileName());
        GenWriter writer = writersMap.get(sqlFileName);
        if (writer == null) {
            writer = new GenWriter(sqlFileName);
            writersMap.put(sqlFileName, writer);
            writer.append("$runDate=$args[0]");
            writer.newLine();
        }
        return writer;
    }

    @Override
    protected void cleanup() {
        for (GenWriter writer : writersMap.values()) {
            Common.close(writer);
        }
    }
}
