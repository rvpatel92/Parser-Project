
public class Assignment_Statement implements Statement
{
	private Id var1;
	
	private Expression expr1;
	
	public Assignment_Statement()
	{	}
	
	public Assignment_Statement(Id var1, Expression expr1)
	{
		if (var1 == null)
			throw new IllegalArgumentException("var1 error/AssignmentStatement");
		if (expr1 == null)
			throw new IllegalArgumentException("expr1 error /AssignmentStatement");
		
		this.var1 = var1;
		this.expr1 = expr1;
	}
	
	@Override
	public void execute()
	{
		//method to store var and evaluate for a method in memory
		Memory.store(var1.getChar(), expr1.evaluate());
	}
}
