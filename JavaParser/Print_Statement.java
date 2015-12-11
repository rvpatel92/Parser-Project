
public class Print_Statement implements Statement
{
	private Expression expr1;
	
	public Print_Statement()
	{	}
	
	public Print_Statement(Expression expr1)
	{
		if (expr1 == null)
			throw new IllegalArgumentException("expr1 error /PrintStatement");
		
		this.expr1 = expr1;
	}
	
	@Override
	public void execute()
	{
		//print a statement
		System.out.println(expr1.evaluate());
	}
}
