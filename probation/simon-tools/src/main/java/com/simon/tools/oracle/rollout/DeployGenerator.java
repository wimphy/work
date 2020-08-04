package com.simon.tools.oracle.rollout;

import com.simon.tools.GenWriter;
import com.simon.tools.services.GenTask;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class DeployGenerator extends UpdateGenerator {
    private static final String sqlFormat = "begin " +
            "for s in ( " +
            "select distinct f.curve_id from chart_curve_v f, meta.object_meta om where chart_id = %s " +
            "              and f.curve_id = om.object_id " +
            "               and om.object_type_id = 1 " +
            "               and om.status_id = 5 " +
            "              ) loop " +
            "meta.pkg_status.set_status_and_deploy(1,s.curve_id,2,5,null,null,null); " +
            "end loop; " +
            "end;";

    private @Value("${db.deploy.batch}") int commitSize;

    @Override
    protected void doTask(GenTask genTask) {
        if (genTask == null) {
            return;
        }
        try {
            GenWriter writer = createNewWriter(genTask);
            String sql = String.format(sqlFormat, genTask.getId());
            writer.append(sql);
            writer.newLine();
            writer.append("/");
            writer.newLine();

            long rowNumber = genTask.getRowNumber();
            if (rowNumber > 0 && rowNumber % commitSize == 0) {
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

    protected GenWriter createNewWriter(GenTask genTask) throws IOException {
        long i = genTask.getRowNumber() / batchSize + 1;
        return createNewWriter(genTask, String.valueOf(i));
    }
}
