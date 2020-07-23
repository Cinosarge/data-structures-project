package binaryTree;

public class ExpressionVariable extends ExpressionTerm {

	protected Integer var;
	public ExpressionVariable(Integer x) { 
		var = x;
	}
	public void setVariable(Integer x) { 
		var = x;
	}
	public Integer getValue() { 
		return var; 
	}
	public String toString() { 
		return var.toString();
	}

}
