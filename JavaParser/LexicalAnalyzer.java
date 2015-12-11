//from gaylers classes with added tokens, character free
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LexicalAnalyzer
{
	
	private List<Token> tokens;
	
	public LexicalAnalyzer(String fileName) throws FileNotFoundException, LexicalException
	{
		tokens = new ArrayList<Token>();
		Scanner sourceCode = new Scanner (new File (fileName));
		int lineNumber = 0;
		while (sourceCode.hasNext())
		{
			String line = sourceCode.nextLine();
			processLine (line, lineNumber);
			lineNumber++;
		}
		tokens.add(new Token (lineNumber, 1, "EOS", TokenType.EOSLine));
		sourceCode.close();
	}

	private void processLine(String line, int lineNumber) throws LexicalException
	{
		int index = 0;
		index = skipWhiteSpace (line, index);
		while (index < line.length())
		{
			String lexeme = getLexeme (line, lineNumber, index);
			TokenType tokType = getTokenType (lexeme, lineNumber, index);
			tokens.add(new Token (lineNumber + 1, index + 1, lexeme, tokType));
			index += lexeme.length();
			index = skipWhiteSpace (line, index);
		}
	}

	private TokenType getTokenType(String lexeme, int lineNumber, int columnNumber) throws LexicalException
	{
		TokenType tokType = null;
		int index = 0;
		if (Character.isLetter(lexeme.charAt(0)))
		{
			if (lexeme.length() == 1)
				if (isValidIdentifier (lexeme.charAt(0)))
					tokType = TokenType.IdTok;
				else
					throw new LexicalException ("invalid lexeme at row number " + 
						(lineNumber + 1) + " and column " + (columnNumber + 1));
			else if(lexeme.length()>1)
			{
				if(getLexeme(lexeme, lineNumber, index) != null)
					if(lexeme.equalsIgnoreCase("feature"))
						tokType = TokenType.FEATURE_RESERVED;
					else if(lexeme.equalsIgnoreCase("is"))
						tokType = TokenType.IS_RESERVED;
					else if(lexeme.equalsIgnoreCase("do"))
						tokType = TokenType.DO_RESERVED;
					else if(lexeme.equalsIgnoreCase("end"))
						tokType = TokenType.END_RESERVED;
					else if(lexeme.equalsIgnoreCase("if"))
						tokType = TokenType.IF_RESERVED;
					else if(lexeme.equalsIgnoreCase("then"))
						tokType = TokenType.THEN_RESERVED;
					else if(lexeme.equalsIgnoreCase("else"))
						tokType = TokenType.ELSE_RESERVED;
					else if(lexeme.equalsIgnoreCase("print"))
						tokType = TokenType.PRINT_RESERVED;
					else if(lexeme.equalsIgnoreCase("from"))
						tokType = TokenType.FROM_RESERVED;
					else if(lexeme.equalsIgnoreCase("until"))
						tokType = TokenType.UNTIL_RESERVED;
					else if(lexeme.equalsIgnoreCase("loop"))
						tokType = TokenType.LOOP_RESERVED;
			}
			else
				throw new LexicalException ("invalid lexeme at row number " + 
					(lineNumber + 1) + " and column " + (columnNumber + 1));
		}
		else if (Character.isDigit (lexeme.charAt(0)))
		{
			if (allDigits (lexeme))
				tokType = TokenType.constantTok;
			else
				throw new LexicalException ("invalid lexeme at row number " + 
						(lineNumber + 1) + " and column " + (columnNumber + 1));					
		}
		
		else if (lexeme.equals("+"))
			tokType = TokenType.add_opt;
		else if (lexeme.equals("-"))
			tokType = TokenType.sub_opt;
		else if (lexeme.equals("/"))
			tokType = TokenType.div_opt;
		else if (lexeme.equals("*"))
			tokType = TokenType.mul_opt;
		else if (lexeme.equals(":="))
			tokType = TokenType.assign_opt;
		else if (lexeme.equals("<="))
			tokType = TokenType.le_opt;
		else if (lexeme.equals("<"))
			tokType = TokenType.lt_opt;
		else if (lexeme.equals(">="))
			tokType = TokenType.ge_opt;
		else if (lexeme.equals(">"))
			tokType = TokenType.gt_opt;
		else if (lexeme.equals ("="))
			tokType = TokenType.eq_opt;
		else if (lexeme.equals("/="))
			tokType = TokenType.ne_opt;
		else if (lexeme.equals("("))
			tokType = TokenType.LEFT_OPERAND;
		else if (lexeme.equals(")"))
			tokType = TokenType.RIGHT_OPERAND;
		else
			throw new LexicalException ("invalid lexeme at row number " + 
					(lineNumber + 1) + " and column " + (columnNumber + 1));			
		return tokType;
	}

	private boolean allDigits(String s)
	{
		int i = 0;
		while (i < s.length() && Character.isDigit(s.charAt(i)))
			i++;
		return i == s.length();
	}

	private String getLexeme(String line, int lineNumber, int index)
	{
		int i = index;
		while (i < line.length() && !Character.isWhitespace(line.charAt(i)))
			i++;
		return line.substring(index, i);
	}

	private int skipWhiteSpace(String line, int index)
	{
		while (index < line.length() && Character.isWhitespace(line.charAt(index)))
			index++;
		return index;
	}

	public Token getNextToken() throws LexicalException
	{
		if (tokens.isEmpty())
			throw new LexicalException ("no more tokens");
		return tokens.remove(0);
	}

	public Token getLookaheadToken() throws LexicalException
	{
		if (tokens.isEmpty())
			throw new LexicalException ("no more tokens");
		return tokens.get(0);
	}
	
	public static boolean isValidIdentifier (char ch)
	{
		return Character.isLetter(ch);
	}

}