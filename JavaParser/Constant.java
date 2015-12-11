public class Constant implements Expression
{
	// from gaylers classes
	private int value;
	
	public Constant(int value)
	{
		this.value = value;
	}

	@Override
	public int evaluate()
	{
		return value;
	}
}