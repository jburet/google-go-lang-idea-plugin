package ro.redeul.google.go.actions;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import ro.redeul.google.go.lang.psi.GoFile;

import java.util.Properties;

public class GoTemplatesFactory implements FileTemplateGroupDescriptorFactory {

    public enum Template {
        GoAppMain("Go Application"), GoFile("Go File");

        String file;
        Template(String file) {
            this.file = file;
        }

        public String getFile() {
            return file;
        }
    }

    public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {

//        final FileTemplateGroupDescriptor group =
//                new FileTemplateGroupDescriptor(GoBundle.message("file.template.group.title.go"), GoIcons.GO_ICON_16x16);
//
//        for (Template template : Template.values()) {
//            group.addTemplate(new FileTemplateDescriptor(template.getFile(), GoIcons.GO_ICON_16x16));
//        }
//
//        return group;
        return null;
    }

    public static GoFile createFromTemplate(PsiDirectory directory, String packageName, String fileName, Template template) {

        final FileTemplate fileTemplate = FileTemplateManager.getInstance().getInternalTemplate(template.getFile());

        Properties properties = new Properties(FileTemplateManager.getInstance().getDefaultProperties());

        properties.setProperty("PACKAGE_NAME", packageName);

        String text;
        try {
            text = fileTemplate.getText(properties);
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to load template for " + template.getFile(), e);
        }

        final PsiFileFactory factory = PsiFileFactory.getInstance(directory.getProject());
        final PsiFile file = factory.createFileFromText(fileName, text);

        return (GoFile) directory.add(file);
    }
}
