package liquibase.integration.ant;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import org.apache.tools.ant.BuildException;

import java.io.Writer;

public class MarkNextChangeSetRanTask extends BaseLiquibaseTask {

    @Override
    public void executeWithLiquibaseClassloader() throws BuildException {

        if (!shouldRun()) {
            return;
        }

        Liquibase liquibase = null;
        try {
            liquibase = createLiquibase();

            Writer writer = createOutputWriter();
            if (writer == null) {
                liquibase.markNextChangeSetRan(new Contexts(getContexts()), new LabelExpression(getLabels()));
            } else {
                liquibase.markNextChangeSetRan(new Contexts(getContexts()), new LabelExpression(getLabels()), writer);
                writer.flush();
                writer.close();
            }

        } catch (Exception e) {
            throw new BuildException(e);
        } finally {
            closeDatabase(liquibase);
        }
    }
}