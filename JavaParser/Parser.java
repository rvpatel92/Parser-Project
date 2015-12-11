//from gaylers classes
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Parser 
{
	private LexicalAnalyzer lex;
	
	public Parser(String fileName) throws FileNotFoundException, LexicalException
	{
		// assigns the test file to lex
		lex = new LexicalAnalyzer (fileName);
	}
	
	public Feature parse() throws ParserException, LexicalException
	{
		// matches to get feature
		Token tok1 = lex.getNextToken();
		match (tok1, TokenType.FEATURE_RESERVED);
		
		// gets id = A
		Id var1 = getId();
		
		// matches to get is
		tok1 = lex.getNextToken();
		match (tok1, TokenType.IS_RESERVED);
		
		//matches to get do
		tok1 = lex.getNextToken();
		match (tok1, TokenType.DO_RESERVED);
		
		// gets statement or list of statement
		Compound cp1 = getCompound();
		
		//matches to get end
		tok1 = lex.getNextToken();
		match(tok1, TokenType.END_RESERVED);
		
		// returns the program with id and the compound
		return new Feature(var1, cp1);

	}
	
	private Compound getCompound() throws LexicalException, ParserException
	{
		Statement state1;
		
		List<Statement> stateList1 = new ArrayList<Statement>();
		//looks at the token list
		Token tok1 = lex.getLookaheadToken();
		// if the token list = one of the token types
		if (tok1.getTokType() == TokenType.IdTok || tok1.getTokType() == TokenType.IF_RESERVED 
				|| tok1.getTokType() == TokenType.PRINT_RESERVED || tok1.getTokType() == TokenType.FROM_RESERVED)
		{
			//calls this method that gets the statement from which the token came from and is = to statement state1 which is added to the list
			stateList1.add(state1 = getStatement());
		}

		tok1 = lex.getLookaheadToken();
		//looks for more just incase.
		while (tok1.getTokType() == TokenType.IdTok || tok1.getTokType() == TokenType.IF_RESERVED 
				|| tok1.getTokType() == TokenType.PRINT_RESERVED || tok1.getTokType() == TokenType.FROM_RESERVED)
		{
			stateList1.add(state1 = getStatement());
			tok1 = lex.getLookaheadToken();
		}
		
		return new Compound(stateList1);
	}
	
	private Statement getStatement() throws LexicalException, ParserException
	{
		Statement state1 = null;
		
		Token tok1 = lex.getLookaheadToken();
		if (tok1.getTokType() == TokenType.IdTok)
		{
			state1 = getAssignmentStatement();
		}
		else if (tok1.getTokType() == TokenType.IF_RESERVED)
		{
			state1 = getIfStatement();
		}
		else if (tok1.getTokType() == TokenType.PRINT_RESERVED)
		{
			state1 = getPrintStatement();
		}
		else if (tok1.getTokType() == TokenType.FROM_RESERVED)
		{
			state1 = getLoopStatement();
		}
		
		return state1;
	}
	
	private Statement getAssignmentStatement() throws LexicalException, ParserException
	{
		Id var1 = getId();
		
		Token tok = lex.getNextToken();
		match(tok, TokenType.assign_opt);
		
		Expression expr1 = getExpression();
		
		return new Assignment_Statement(var1, expr1);
	}
	
	private Statement getIfStatement() throws LexicalException, ParserException
	{
		Token tok1 = lex.getNextToken();
		match (tok1, TokenType.IF_RESERVED);
		
		Boolean_Expression boolexpr1 = getBooleanExpression();
		
		tok1 = lex.getNextToken();
		match (tok1, TokenType.THEN_RESERVED);
		
		Compound cp1 = getCompound();
		
		tok1 = lex.getNextToken();
		match (tok1, TokenType.ELSE_RESERVED);
		
		Compound cp2 = getCompound();
		
		tok1 = lex.getNextToken();
		match (tok1, TokenType.END_RESERVED);
		
		return new If_Statement(boolexpr1, cp1, cp2);
	}
	
	private Statement getPrintStatement() throws LexicalException, ParserException
	{
		Token tok1 = lex.getNextToken();
		match (tok1, TokenType.PRINT_RESERVED);
		
		tok1 = lex.getNextToken();
		match (tok1, TokenType.LEFT_OPERAND);
		
		Expression expr1 = getExpression();
		
		tok1 = lex.getNextToken();
		match (tok1, TokenType.RIGHT_OPERAND);
		
		return new Print_Statement(expr1);
	}
	
	private Statement getLoopStatement() throws LexicalException, ParserException
	{
		Token tok1 = lex.getNextToken();
		match (tok1, TokenType.FROM_RESERVED);
		
		Statement assignment1 = getAssignmentStatement();
		
		tok1 = lex.getNextToken();
		match (tok1, TokenType.UNTIL_RESERVED);
		
		Boolean_Expression boolexp1 = getBooleanExpression();
		
		tok1 = lex.getNextToken();
		match (tok1, TokenType.LOOP_RESERVED);
		
		Compound cp1 = getCompound();
		
		tok1 = lex.getNextToken();
		match (tok1, TokenType.END_RESERVED);
		
		return new Loop_Statement2(assignment1, boolexp1, cp1);
	}
	
	private Constant getConstant() throws ParserException, LexicalException
	{
		Token token = lex.getNextToken();
		match (token, TokenType.constantTok);
		
		int value = Integer.parseInt(token.getLexeme());
		
		return new Constant(value);
	}
	
	private Expression getExpression() throws LexicalException, ParserException

	{
		Expression expr = null;
		
		Token token = lex.getLookaheadToken();
		
			if(token.getTokType() == TokenType.add_opt || token.getTokType() == TokenType.sub_opt 
				|| token.getTokType() == TokenType.mul_opt || token.getTokType() == TokenType.div_opt)
			{
				 expr = getBinaryExpression();
			}
			else if(token.getTokType() == TokenType.IdTok)
			{
				expr = getId();
			}
			else if(token.getTokType() == TokenType.constantTok)
			{
				expr = getConstant();
			}
			return expr;
	}
	
	private Expression getBinaryExpression() throws LexicalException, ParserException
	{
		Arithmetic_Operator op = getArithmeticOperator();
		
		Expression expr1 = getExpression();
		
		Expression expr2 = getExpression();
		
		return new Binary_Expression(op, expr1, expr2);
	}
	
	private Arithmetic_Operator getArithmeticOperator() throws ParserException, LexicalException 
	{
		Arithmetic_Operator op;
		
		Token token = lex.getNextToken();
		
		if(token.getTokType() == TokenType.add_opt)
			op = Arithmetic_Operator.add_opt;
		else if(token.getTokType() == TokenType.sub_opt)
			op = Arithmetic_Operator.sub_opt;
		else if(token.getTokType() == TokenType.mul_opt)
			op = Arithmetic_Operator.mul_opt;
		else if(token.getTokType() == TokenType.div_opt)
			op = Arithmetic_Operator.div_opt;
		else
			throw new ParserException ("arithmetic operator expected at line number: " + token.getRowNumber() + 
					" column number: " + token.getColumnNumber());
		return op;
	}
	
	private Relational_Operator getRelationalOperator() throws LexicalException, ParserException
	{
		Relational_Operator ropt = null;
		
		Token token = lex.getNextToken();
		
		if(token.getTokType() == TokenType.eq_opt)
			ropt = Relational_Operator.eq_opt;
		else if(token.getTokType() == TokenType.ne_opt)
			ropt = Relational_Operator.ne_opt;
		else if(token.getTokType() == TokenType.le_opt)
			ropt = Relational_Operator.le_opt;
		else if(token.getTokType() == TokenType.lt_opt)
			ropt = Relational_Operator.lt_opt;
		else if(token.getTokType() == TokenType.ge_opt)
			ropt = Relational_Operator.ge_opt;
		else if(token.getTokType() == TokenType.gt_opt)
			ropt = Relational_Operator.gt_opt;

		return ropt;
	}
	
	private Boolean_Expression getBooleanExpression() throws ParserException, LexicalException
	{
		Relational_Operator ropt = getRelationalOperator();
		
		Expression expr1 = getExpression();
		
		Expression expr2 = getExpression();
		
		return new Boolean_Expression(ropt, expr1, expr2);
	}
	
	
	private Id getId() throws LexicalException, ParserException
	{
		Token tok = lex.getNextToken();
		
		match (tok, TokenType.IdTok);
		
		return new Id (tok.getLexeme().charAt(0));
	}
	
	private void match(Token tok, TokenType tokType) throws ParserException
	{
		if (tok.getTokType() != tokType)
			throw new ParserException (tokType.name() + " expected at row " +
					tok.getRowNumber() +" and column "  + tok.getColumnNumber());
	}
}
