with Lexical_Analyzers, Features, Expressions, Statements, Boolean_Expressions, Tokens, Parsers;
use Lexical_Analyzers, Features, Expressions, Statements, Boolean_Expressions, Tokens, Parsers;

package body Parsers is

   tok: Token;

   -- should not need this global variable
   -- should be using the lexical analyzer belonging to the parser

   -------------------
   -- create_parser --
   -------------------
   function create_parser (file_name: in String) return Parser is
      p: Parser;
   begin
      p.lex := create_lexical_analyzer(file_name);
      return p;
   end create_parser;

   -----------
   -- parse --
   -----------
   procedure parse (p: in out Parser; f: out Feature) is
      com: Compound;
   begin
      --Getting Token and matching it to Feature
      get_next_token(p.lex,tok);
      match (tok,FEATURE_TOK);
      --Getting ID and matching it to ID
      get_next_token(p.lex,tok);
      match (tok,ID_TOK);
      --Getting Token and matching it to Is
      get_next_token(p.lex,tok);
      match (tok,IS_TOK);
      --Getting Token and matching it to Do
      get_next_token(p.lex,tok);
      match (tok,DO_TOK);
      --Getting Compound
      get_compound(p,com);
      --Getting Token and matching it to End
      get_next_token(p.lex,tok);
      match (tok,END_TOK);
      f := create_feature(com);
   end parse;


   ------------------
   -- get_compound --
   ------------------
   procedure get_compound(p: in out Parser; com: out Compound) is

      stmt: Statement_Access;
   begin
      --Getting first statement
      get_statement(p,stmt);
      --Adding statement into compound
      add(com,stmt);
      --Looking to see if there are more statements
      tok := get_lookahead_token(p.lex);
      --Check to see if there are more statements to be added
      while (get_token_type(tok) = ID_TOK or get_token_type(tok) = IF_TOK or get_token_type(tok) = PRINT_TOK or get_token_type(tok) = FROM_TOK) loop
        --get the statement from the while loop
        get_statement(p,stmt);
        --add the new statement into compound
        add(com,stmt);
        tok := get_lookahead_token(p.lex);
   end loop;
   end get_compound;


   ------------------------------
   -- assignment_statement --
   ------------------------------
   procedure assignment_statement(p: in out Parser; stmt: out Statement_Access) is
      --making sure necessary variables are needed to fulfill the object parameters
      var: Id;
      expr: Expression_Access;

   begin
      --getting the token ID and matching it
      get_next_token(p.lex,tok);
      match(tok,ID_TOK);
      --creating an variable id to hold the value
      var := create_id(get_lexeme(tok)(1));
      --getting assignment_operator and matching it
      get_next_token(p.lex,tok);
      match(tok, ASSIGN_TOK);
      --getting expression
      get_expression(p,expr);
      stmt := create_assignment_statement(var,expr);
   end assignment_statement;


   ---------------------
   -- print_statement --
   ---------------------
   procedure print_statement(p: in out Parser; stmt: out Statement_Access) is
      --making sure necessary variables are needed to fulfill the object parameters
      expr: Expression_Access;
   begin
      --getting the token print and matching it
      get_next_token(p.lex,tok);
      match(tok,PRINT_TOK);
      --getting the token left paren tok and matching it
      get_next_token(p.lex,tok);
      match(tok,LEFT_PAREN_TOK);
      --getting expression
      get_expression(p,expr);
      --getting the token right parent tok and matching it
      get_next_token(p.lex,tok);
      match(tok,RIGHT_PAREN_TOK);
      stmt := create_print_statement(expr);
   end print_statement;


   ---------------------
   -- if_statement --
   ---------------------
   procedure if_statement(p: in out Parser; stmt: out Statement_Access) is
      --making sure necessary variables are needed to fulfill the object parameters
      expr: Boolean_Expression;
      com1: Compound;
      com2: Compound;
   begin
      --getting the token if and matching it
      get_next_token(p.lex,tok);
      match(tok, IF_TOK);
      -- getting boolean expression
      get_bool_expression(p,expr);
      --getting the token then and matching it
      get_next_token(p.lex,tok);
      match(tok,THEN_TOK);
      --getting compound
      get_compound(p,com1);
      --getting the token else and matching it
      get_next_token(p.lex,tok);
      match(tok,ELSE_TOK);
      --getting compound 2
      get_compound(p,com2);
      --getting the token end and matching it
      get_next_token(p.lex,tok);
      match(tok,END_TOK);
      stmt := create_if_statement(expr,com1,com2);
   end if_statement;

   ----------------------
   -- loop_statement ----
   ----------------------
   procedure loop_statement(p: in out Parser; stmt: out Statement_Access) is
      --making sure necessary variables are needed to fulfill the object parameters
      stmt1: Statement_Access;
      expr: Boolean_Expression;
      com1: Compound;
   begin
      --getting the token from and matching it
      get_next_token(p.lex,tok);
      match(tok, FROM_TOK);
      --getting assignment_statement
      assignment_statement(p,stmt1);
      --getting the token until and matching it
      get_next_token(p.lex,tok);
      match(tok,UNTIL_TOK);
      --getting boolean_expression
      get_bool_expression(p,expr);
      --getting the token loop and matching it
      get_next_token(p.lex,tok);
      match(tok,LOOP_TOK);
      --getting compound
      get_compound(p,com1);
      --getting the token end and matching it
      get_next_token(p.lex,tok);
      match(tok,END_TOK);
      stmt := create_loop_statement(stmt1,expr,com1);
   end loop_statement;


   ----------------------
   -- get_statement ----
   ----------------------
   procedure get_statement(p: in out Parser; stmt: out Statement_Access) is

   begin
      tok := get_lookahead_token(p.lex);
      --if the statement begins with an id
      if get_token_type(tok) = ID_TOK then
         assignment_statement(p,stmt);
      --if the statement begins with print
      elsif get_token_type(tok) = PRINT_TOK then
         print_statement(p,stmt);
      --if the statement begins with if
      elsif get_token_type(tok) = IF_TOK then
         if_statement(p,stmt);
      --if the statement begins with a from
      elsif get_token_type(tok) = FROM_TOK then
         loop_statement(p,stmt);
      else
         --raise error, statement is not working
         raise parser_exception with "Statement in get_statement method is not working properly";
      end if;
   end get_statement;


   --------------------------
   -- boolean_expression ----
   --------------------------
   procedure get_bool_expression(p: in out Parser; bool_expr: out Boolean_Expression) is
      --making sure necessary variables are needed to fulfill the object parameters
      relat_opt: Relational_Operator;
      exp1: Expression_Access;
      exp2: Expression_Access;

   begin
      --getting relational operator
      get_relative_opt(p,relat_opt);
      --getting first expression
      get_expression(p,exp1);
      --getting second expression
      get_expression(p,exp2);
      bool_expr := create_boolean_expression(relat_opt,exp1,exp2);
   end get_bool_expression;


   ----------------------
   -- get_expression ----
   ----------------------
   procedure get_expression(p: in out Parser; expr: out Expression_Access) is
      --making sure necessary variables are needed to fulfill the object parameters
      exp1: Expression_Access;
      exp2: Expression_Access;
      arith_opt: Arithmetic_Operator;
   begin
      --figuring out which expression starts with either id, number, or arithmetic operator
      tok := get_lookahead_token(p.lex);
      --if the token found is equal to an ID
      if get_token_type(tok) = ID_TOK then
         get_next_token(p.lex,tok);
         --method from the expression class to make an id a variable expression
         expr := create_variable_expression(get_lexeme(tok)(1));
      -- if the token found is equal to a number
      elsif get_token_type(tok) = Tokens.LIT_INT_TOK then
         get_next_token(p.lex,tok);
         --method from the expression class to make an number from an constant expression
         expr := create_constant_expression(Integer'Value(LexemeToString(get_lexeme(tok))));
      else
         --used from the binary expression
         get_arith_opt(p,arith_opt);
         get_expression(p,exp1);
         get_expression(p,exp2);
         expr := create_binary_expression(arith_opt,exp1,exp2);
      end if;
   end get_expression;


   ----------------------
   -- get_arith_opt ----
   ----------------------
   procedure get_arith_opt(p: in out Parser; arith_opt: out Arithmetic_Operator) is

   begin
      get_next_token(p.lex,tok);
      --get token tok and see if it addition token
      if get_token_type(tok) = ADD_TOK then
         arith_opt := ADD_OP;
      --get token tok and see if it is subtraction token
      elsif get_token_type(tok) = SUB_TOK then
         arith_opt := SUB_OP;
      --get token tok and see if it is multiplication token
      elsif get_token_type(tok) = MUL_TOK then
         arith_opt := MUL_OP;
      --get token tok and see if it is division token
      elsif get_token_type(tok) = DIV_TOK then
         arith_opt := DIV_OP;
      else
         --raise error saying the arithmetic operator in the parser class is not working corectly
         raise parser_exception with "Arithmetic Operator in Parser Class is Wrong";
      end if;
   end get_arith_opt;


   ------------------------
   -- get_relative_opt ----
   ------------------------
   procedure get_relative_opt(p: in out Parser; rel_opt: out Relational_Operator) is


   begin
      get_next_token(p.lex,tok);
      --if the token tok is equal to the equality token
      if get_token_type(tok) = EQ_TOK then
         rel_opt := EQ_OP;
      --if the token tok is equal to the not equal token
      elsif get_token_type(tok) = NE_TOK then
         rel_opt := NE_OP;
      --if the token tok is equal to the greater than or equal to token
      elsif get_token_type(tok) = GE_TOK then
         rel_opt := GE_OP;
      -- if the token tok is equal to the greater than token
      elsif get_token_type(tok) = GT_TOK then
         rel_opt := GT_OP;
      --if the token tok is euqal to the less than or equal to token
      elsif get_token_type(tok) = LE_TOK then
         rel_opt := LE_OP;
      --if the token tok is equal to the less than token
      elsif get_token_type(tok) = LT_TOK then
         rel_opt := LT_OP;
      else
         raise parser_exception with "Relational Operator in the Parser Class is wrong, or missing";
      end if;
   end get_relative_opt;


   -------------
   --- match ---
   -------------
   procedure match(tok: in Token; expected: in Token_Type) is
   begin
      --matches the token type to the expected token that is in the parameters
      if (get_token_type(tok) /= expected) then
         raise parser_exception with "match method is not matching the token and tokentype";
      end if;
   end match;


   ----------------------
   -- LexemetoString ----
   ----------------------
   function LexemeToString (lex: in Lexeme) return String is
      --making sure necessary variables are needed to fulfill the object parameters
      size: Positive := lexeme_length(lex);
      Str: String(1 .. size);

   begin
      --used to convert a lexeme to a string
      for i in Integer range 1 .. size loop
         Str(i) := lex(i);
      end loop;
      return str;
   end LexemeToString;

   end Parsers;


-- 100/100 Please see my comments in your code.
