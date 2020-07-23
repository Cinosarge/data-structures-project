package binaryTree;

public class AdditionOperator extends ExpressionOperator {

	public Integer getValue() { 
		return (firstOperand + secondOperand);
	}
	
	public String toString() { 
		return new String("+"); 
	}

}
