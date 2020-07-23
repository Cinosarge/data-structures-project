package binaryTree;

import exception.EmptyTreeException;
import nodeList.Position;

public class EvaluateExpressionTour extends EulerTour <ExpressionTerm,Integer> {

	public Integer execute(BinaryTree<ExpressionTerm> T) {
		init(T);
		try {
			return eulerTour(tree.root());
		} catch (EmptyTreeException e) {
			e.printStackTrace();
			return null; 
		}
	}

	public Integer processNode(Position<ExpressionTerm> v, TourResult<Integer> r) {
		ExpressionTerm term = v.element();
		if (tree.isInternal(v)) { 
			ExpressionOperator op = (ExpressionOperator) term;
			op.setOperands(r.left, r.right);
		} 
		return term.getValue();
	}

}
