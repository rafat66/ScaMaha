package visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class LineCounterVisitor extends ASTVisitor {
    private int linesOfCode = 0;

    public int getLinesOfCode() {
        return linesOfCode;
    }
    
    @Override
    public boolean visit(MethodDeclaration node) {
        // Count the lines of code within method declarations
        int startLine = ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition());
        int endLine = ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition() + node.getLength());
        int lineCount = endLine - startLine + 1;
        linesOfCode += lineCount;
        return super.visit(node);
    }
}

