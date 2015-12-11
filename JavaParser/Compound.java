import java.util.ArrayList;
import java.util.List;

public class Compound 
{
	// creates a list of statements
	private List<Statement> statelist1 = new ArrayList<Statement>();
	
	public Compound(List<Statement> statelist1)
	{
		if (statelist1 == null)
			throw new IllegalArgumentException("Null error in stateList1/Compound");
		
		this.statelist1 = statelist1;
	}
	
	public void execute()
	{
		// adds statements into list
		for (int i = 0; i < statelist1.size(); i++)
			statelist1.get(i).execute();
	}
}
