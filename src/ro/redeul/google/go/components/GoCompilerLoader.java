package ro.redeul.google.go.components;

import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import ro.redeul.google.go.GoFileType;
import ro.redeul.google.go.compilation.GoCompiler;
import ro.redeul.google.go.compilation.GoMakefileCompiler;
import ro.redeul.google.go.ide.GoProjectSettings;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Author: Toader Mihai Claudiu <mtoader@gmail.com>
 * <p/>
 * Date: Aug 24, 2010
 * Time: 1:27:29 AM
 */
public class GoCompilerLoader extends AbstractProjectComponent {

    public GoCompilerLoader(Project project) {
        super(project);
    }

//    public void initComponent() {
//        TODO: insert component initialization logic here
//    }

//    public void disposeComponent() {
    // TODO: insert component disposal logic here
//    }

    @NotNull
    public String getComponentName() {
        return "GoCompilerLoader";
    }

    public void projectOpened() {
        CompilerManager compilerManager = CompilerManager.getInstance(myProject);
        compilerManager.addCompilableFileType(GoFileType.GO_FILE_TYPE);

        switch (GoProjectSettings.getInstance(myProject).getState().BUILD_SYSTEM_TYPE) {
            case Internal:
                compilerManager.addTranslatingCompiler(
                        new GoCompiler(myProject),
                        new HashSet<FileType>(Arrays.asList(GoFileType.GO_FILE_TYPE)),
                        new HashSet<FileType>(Arrays.asList(FileType.EMPTY_ARRAY)));

                break;
            case Makefile:
                compilerManager.addTranslatingCompiler(
                        new GoMakefileCompiler(myProject),
                        new HashSet<FileType>(Arrays.asList(GoFileType.GO_FILE_TYPE)),
                        new HashSet<FileType>(Arrays.asList(FileType.EMPTY_ARRAY)));
                break;
        }
    }

    public void projectClosed() {
        // called when project is being closed
    }
}
