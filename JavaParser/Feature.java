//from gaylers classes

public class Feature 
{
	private Id var1;
	
	private Compound cp1;
	
	public Feature(Id var1, Compound cp1)
	{
		if (var1 == null)
			throw new IllegalArgumentException("Id null /Feature");
		
		if (cp1 == null)
			throw new IllegalArgumentException("cp1 null /Feature");
		
		this.var1 = var1;
		this.cp1 = cp1;
	}
	
	public void execute()
	{
		var1.evaluate();
		
		/********************************************
		 * Why are you evaluating the variable here?
		 */
		cp1.execute();
		
	}
	
	
}
