package ro.redeul.google.go.lang.psi.impl.toplevel;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import ro.redeul.google.go.lang.lexer.GoTokenTypes;
import ro.redeul.google.go.lang.parser.GoElementTypes;
import ro.redeul.google.go.lang.psi.statements.GoBlockStatement;
import ro.redeul.google.go.lang.psi.toplevel.GoFunctionParameterList;
import ro.redeul.google.go.lang.psi.visitors.GoElementVisitor;
import ro.redeul.google.go.lang.psi.impl.GoPsiElementBase;
import ro.redeul.google.go.lang.psi.toplevel.GoFunctionDeclaration;

/**
 * Author: Toader Mihai Claudiu <mtoader@gmail.com>
 * <p/>
 * Date: Aug 26, 2010
 * Time: 2:33:51 PM
 */
public class GoFunctionDeclarationImpl extends GoPsiElementBase implements GoFunctionDeclaration {

    public GoFunctionDeclarationImpl(@NotNull ASTNode node) {
        super(node);
    }

    public String getFunctionName() {
        PsiElement identifier = findChildByType(GoTokenTypes.mIDENT);

        return identifier != null ? identifier.getText() : "";
    }

    @Override
    public String getName() {
        return getFunctionName();
    }

    @Override
    public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
        return null;
    }

    public boolean isMain() {
        return getFunctionName().equals("main");
    }

    public GoBlockStatement getBlock() {
        return findChildByClass(GoBlockStatement.class);
    }

    @Override
    public GoFunctionParameterList getParameters() {
        return findChildByClass(GoFunctionParameterList.class);
    }

    public String toString() {
        return "FunctionDeclaration(" + getFunctionName() + ")";
    }

    public void accept(GoElementVisitor visitor) {
        visitor.visitFunctionDeclaration(this);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {

        // try the parameter list
        // TODO: implement the actual PsiElement for FunctionParameterList
        PsiElement functionParameterList = findChildByType(GoElementTypes.FUNCTION_PARAMETER_LIST);
        if ( functionParameterList != null && functionParameterList != lastParent ) {
            PsiElement functionParameters[] = functionParameterList.getChildren();
            for (PsiElement functionParameter : functionParameters) {
                if ( ! processor.execute(functionParameter, state) )  {
                    return false;
                }
            }
        }

        // TODO: try the return parameters

        return processor.execute(this, state);
    }
}
