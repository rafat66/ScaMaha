package visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.LineComment;


//comment visitor

public class CommentVisitor extends ASTVisitor {
	CompilationUnit cu;
	String source;

	public CommentVisitor(CompilationUnit cu, String source) {
		super();
		this.cu = cu;
		this.source = source;
	}
	
	public boolean visit(LineComment node) {
		int start = node.getStartPosition();
		int end = start + node.getLength();
		if (start >= 0 && end <= source.length() && start <= end) {
		String comment = source.substring(start, end);
//		System.err.println(comment.replaceAll("[^a-zA-Z0-9]", " ").trim());
		}else { System.out.println("Invalid indices for substring extraction.");}
		return true;
	}

	public boolean visit(BlockComment node) {
		int start = node.getStartPosition();
		int end = start + node.getLength();
		if (start >= 0 && end <= source.length() && start <= end) {
		String comment = source.substring(start, end);
//		System.err.println(comment.replaceAll("[^a-zA-Z0-9]", " ").trim());
		}else { System.out.println("Invalid indices for substring extraction.");}
		return true;
	}

}