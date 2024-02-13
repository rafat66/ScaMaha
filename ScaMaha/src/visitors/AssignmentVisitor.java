package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Assignment.Operator;

public class AssignmentVisitor extends ASTVisitor {
        private List<Assignment> assigments = new ArrayList<Assignment>(); 
        private Assignment currentNode;
        /**
         * @return the leftHandSide
         */
        public Expression getLeftHandSide() {
                return currentNode.getLeftHandSide();
        }
        /**
         * 
         * @return the operator used for assignment operation
         */
        public Operator getOperator(){
                return currentNode.getOperator();
        }
        /**
         * @return the rightHandSide
         */
        public Expression getRightHandSide() {
                return currentNode.getRightHandSide();
        }
        
        public boolean visit(Assignment node) {
                assigments.add(node);
                currentNode = node;
                return super.visit(node);
        }
        
        public List<Assignment> getAssignments() {
                return assigments;
        }
}