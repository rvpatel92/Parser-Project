//from gaylers classes
import java.io.FileNotFoundException;


public class Interpreter
{

	public static void main(String[] args)
	{
		try
		{
			Parser p = new Parser("test4.e");
			Feature assn = p.parse();
			assn.execute();
		}
		catch (ParserException e)
		{
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (LexicalException e)
		{
			e.printStackTrace();
		}
		
		/*******************************************
		 * should have a "catch-all" handler to catch
		 * any other thrown exceptions
		 */
	}

}

/**********************************************
92/100 Please see my comments in your code. 
*/