
public class If_Statement implements Statement
{
	private Boolean_Expression boolexpr1;
	
	private Compound cp1;
	
	private Compound cp2;
	
	public If_Statement()
	{	}
	
	public If_Statement(Boolean_Expression boolexpr1, Compound cp1, Compound cp2)
	{
		if (boolexpr1 == null)
			throw new IllegalArgumentException("boolexpr1 error /IfStatement");
		if (cp1 == null)
			throw new IllegalArgumentException("cp1 error /IfStatemetn");
		if (cp2 == null)
			throw new IllegalArgumentException("cp2 error /IfStatemetn");
		
		this.boolexpr1 = boolexpr1;
		this.cp1 = cp1;
		this.cp2 = cp2;
	}
	
	@Override
	public void execute()
	{
		// if boolexpression is true, then execute first code block else execute second code block
		if (boolexpr1.evaluate() == 1)
		{
			cp1.execute();
		}
		else if (boolexpr1.evaluate() == 0)
			
			/***********************
			 * just else
			 */
		{
			cp2.execute();
		}
	}
}
