package binaryTree;

import exception.EmptyTreeException;
import nodeList.Position;

public class PrintExpressionTour extends EulerTour<ExpressionTerm, String> {

	private String outString = "";
	public String execute(BinaryTree<ExpressionTerm> T) { 
		init(T); 
		System.out.print("Espressione: "); 
		try{
			eulerTour(T.root());
		}catch (EmptyTreeException e){
			e.printStackTrace();
		} 
		System.out.println(); 
		return null;
	} 

	protected void visitLeft(Position<ExpressionTerm> v, TourResult<String> r) { 
		if (tree.isInternal(v)){
			outString += "(";
			System.out.print("("); 
		}
	} 

	protected void visitBelow(Position<ExpressionTerm> v, TourResult<String> r) { 
		if (tree.isInternal(v)){
			outString += v.element();
			System.out.print(v.element()); 
		}
	} 

	protected void visitRight(Position<ExpressionTerm> v, TourResult<String> r) { 
		if (tree.isInternal(v)){
			outString += ")";
			System.out.print(")"); 
		}
	}

	public String processNode(Position<ExpressionTerm> v, TourResult<String> r) {
		return  outString;
	} 

}
