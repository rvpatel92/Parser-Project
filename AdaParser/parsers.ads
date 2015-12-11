with Lexical_Analyzers, Features, Expressions, Statements, Boolean_Expressions, Tokens;
use Lexical_Analyzers, Features, Expressions, Statements, Boolean_Expressions, Tokens;

package Parsers is
   --given
   parser_exception: exception;

   --given
   type Parser is private;

   --Creating parser
   function create_parser (file_name: in String) return Parser;

   --Creating instance
   procedure parse (p: in out Parser; f: out Feature);

   --Get Compound
   procedure get_compound(p: in out Parser; com : out Compound);

   --Get Statemeents
   procedure get_statement (p: in out Parser; stmt: out Statement_Access);
   procedure if_statement (p: in out Parser; stmt: out Statement_Access);
   procedure assignment_statement(p: in out Parser; stmt: out Statement_Access);
   procedure print_statement(p: in out Parser; stmt: out Statement_Access);
   procedure loop_statement(p: in out Parser; stmt: out Statement_Access);

   -- Get Expressions/BooleanExpression
   procedure get_expression(p: in out Parser; expr: out Expression_Access);
   procedure get_bool_expression(p: in out Parser; bool_expr: out Boolean_Expression);

   -- Get Arithmetic/Relative OPS
   procedure get_arith_opt(p: in out Parser; arith_opt: out Arithmetic_Operator);
   procedure get_relative_opt(p: in out Parser; rel_opt: out Relational_Operator);

   --Match token to TokenType
   procedure match(tok: in Token; expected: in Token_Type);

   -- Lexeme converts into a string
   function LexemeToString( lex: in Lexeme) return String;

   --given
private
   type Parser is record
      lex: Lexical_Analyzer;
   end record;

end Parsers;
