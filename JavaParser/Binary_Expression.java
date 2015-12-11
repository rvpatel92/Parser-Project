
public class Binary_Expression implements Expression
{
	private Arithmetic_Operator arithopt1;
	
	private Expression expr1;
	
	private Expression expr2;
	
	public Binary_Expression()
	{	}
	
	public Binary_Expression(Arithmetic_Operator arithopt1, Expression expr1, Expression expr2)
	{
		if (arithopt1 == null)
			throw new IllegalArgumentException("arithopt1 error /Binary Expression");
		if (expr1 == null)
			throw new IllegalArgumentException("expr1 error /Binary_Expression");
		if (expr2 == null)
			throw new IllegalArgumentException("expr2 error /Binary_Expression");
		
		this.arithopt1 = arithopt1;
		this.expr1 = expr1;
		this.expr2 = expr2;
	}
	
	@Override
	public int evaluate()
	{
		// gives the value of expression 1 and expression 2 by adding, subtracting, multiplying or dividing
		int value = 0;
		
		if (arithopt1 == Arithmetic_Operator.add_opt)
			value = expr1.evaluate() + expr2.evaluate();
		else if (arithopt1 == Arithmetic_Operator.div_opt)
			value = expr1.evaluate() / expr2.evaluate();
		else if (arithopt1 == Arithmetic_Operator.mul_opt)
			value = expr1.evaluate() * expr2.evaluate();
		else if (arithopt1 == Arithmetic_Operator.sub_opt)
			value = expr1.evaluate() * expr2.evaluate();
		
		return value;
	}
}
