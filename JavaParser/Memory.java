//from gaylers classes
public class Memory
{
	private static int[] mem = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
		,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
		,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	/*************************************************
	 * since our language is not case sensitive, there
	 * are only 26 possible identifiers
	 */
	
	/**
	 * @param ch - must be a valid identifier
	 * @return value of ch
	 * @throws IllegalArgumentException if ch is not a valid identifier
	 */
	public static int fetch (char ch)
	{
		return mem[getIndex(ch)];
	}
	
	/**
	 * @param ch - must be a valid identifier
	 * @param value
	 * @throws IllegalArgumentException if ch is not a valid identifier
	 */
	public static void store (char ch, int value)
	{
		mem[getIndex(ch)] = value;
	}
	
	private static int getIndex (char ch)
	{
		if (!LexicalAnalyzer.isValidIdentifier(ch))
			throw new IllegalArgumentException (ch + " is not a valid identifier");
		return ch - 'A';
	}
	
	public static void displayMemory()
	{
		for (int i = 0; i < 3; i++)
			
			/**********
			 * 3 ??
			 */
			System.out.println ((char)('A' + i) + ": " + mem[i]);
	}
}