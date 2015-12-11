
public class Boolean_Expression implements Expression

/**********************************************
 * expression is modeling an arithmetic expression
 */
{
	private Relational_Operator ropt;
	
	private Expression expr1;
	
	private Expression expr2;
	
	public Boolean_Expression()
	{	}
	
	public Boolean_Expression(Relational_Operator ropt, Expression expr1, Expression expr2)
	{
		if (ropt == null)
			throw new IllegalArgumentException("ropt error /BooleanExpression");
		if (expr1 == null)
			throw new IllegalArgumentException("expr1 error /BooleanExpression");
		if (expr2 == null)
			throw new IllegalArgumentException("expr2 error /BooleanExpression");
		
		this.ropt = ropt;
		this.expr1 = expr1;
		this.expr2 = expr2;
	}
	
	@Override
	public int evaluate()
	
	/*************************************
	 * should be returning a boolean
	 */
	{
		
		// if expression (whatever relational operator) expression 2, then true = 1 else false = 0
		int value = 0;
		if (ropt == Relational_Operator.gt_opt)
			value = expr1.evaluate() > expr2.evaluate() ? 1 : 0;
		else if (ropt == Relational_Operator.ge_opt)
			value = expr1.evaluate() >= expr2.evaluate() ? 1 : 0;
		else if (ropt == Relational_Operator.le_opt)
			value = expr1.evaluate() <= expr2.evaluate() ? 1 : 0;		
		else if (ropt == Relational_Operator.lt_opt)
			value = expr1.evaluate() < expr2.evaluate() ? 1 : 0;
		else if (ropt == Relational_Operator.eq_opt)
			value = expr1.evaluate() == expr2.evaluate() ? 1 : 0;
		else if (ropt == Relational_Operator.ne_opt)
			value = expr1.evaluate() != expr2.evaluate() ? 1 : 0;
		
		return value;
	}
}
