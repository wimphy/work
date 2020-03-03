package com.simon.archetype;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.exception.*;
import org.apache.maven.archetype.generator.DefaultFilesetArchetypeGenerator;
import org.apache.maven.archetype.generator.FilesetArchetypeGenerator;
import org.apache.velocity.context.Context;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component(
        role = FilesetArchetypeGenerator.class
)
public class MyFilesetArchetypeGenerator extends DefaultFilesetArchetypeGenerator {
    private static final Pattern TOKEN_PATTERN = Pattern.compile("__((?:[^_]+_)*[^_]+)__");

    private File getOutputFile(String template, String directory, File outputDirectoryFile, boolean packaged, String packageName, String moduleOffset, Context context) {
        String templateName = StringUtils.replaceOnce(template, directory, "");
        String outputFileName = directory + "/" + (packaged ? this.getPackageAsDirectory(packageName) : "") + "/123" + templateName.substring(moduleOffset.length());
        outputFileName = this.replaceFilenameTokens(outputFileName, context);
        return new File(outputDirectoryFile, outputFileName);
    }

    private String replaceFilenameTokens(String filePath, Context context) {
        StringBuffer interpolatedResult = new StringBuffer();
        Matcher matcher = TOKEN_PATTERN.matcher(filePath);

        while(true) {
            while(matcher.find()) {
                String propertyToken = matcher.group(1);
                String contextPropertyValue = (String)context.get(propertyToken);
                if (contextPropertyValue != null && contextPropertyValue.trim().length() > 0) {
                    if (this.getLogger().isDebugEnabled()) {
                        this.getLogger().debug("Replacing property '" + propertyToken + "' in file path '" + filePath + "' with value '" + contextPropertyValue + "'.");
                    }

                    matcher.appendReplacement(interpolatedResult, contextPropertyValue);
                } else {
                    this.getLogger().warn("Property '" + propertyToken + "' was not specified, so the token in '" + filePath + "' is not being replaced.");
                }
            }

            matcher.appendTail(interpolatedResult);
            if (this.getLogger().isDebugEnabled()) {
                this.getLogger().debug("Final interpolated file path: '" + interpolatedResult + "'");
            }

            return interpolatedResult.toString();
        }
    }
}
