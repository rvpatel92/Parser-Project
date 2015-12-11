
public class Loop_Statement2 implements Statement
{
	private Statement assignStatement1;
	
	private Boolean_Expression boolexpr1;
	
	private Compound cp1;
	
	public Loop_Statement2()
	{	}
	
	public Loop_Statement2(Statement assignStatement1,Boolean_Expression boolexpr1, Compound cp1)
	{
		if (assignStatement1 == null)
			throw new IllegalArgumentException("AssignStatement error /Loop_Statement");
		if (boolexpr1 == null)
			throw new IllegalArgumentException("Boolexpr1 error /LoopStatement");
		if (cp1 == null)
			throw new IllegalArgumentException("cp1 error /LoopStatement");
		
		this.assignStatement1 = assignStatement1;
		this.boolexpr1 = boolexpr1;
		this.cp1 = cp1;
	}
	
	@Override
	public void execute()
	{
		// execute a statement then do whatever is compound while boolean expression is false
		assignStatement1.execute();
		do
		{
			cp1.execute();
		} while (boolexpr1.evaluate() == 0);
	}
	
}
