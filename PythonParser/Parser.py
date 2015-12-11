__author__ = 'ravipatel'

from ArithmeticOperator import ArithmeticOperator
from IfStatement import IfStatement
from PrintStatement import PrintStatement
from LoopStatement import LoopStatement
from BooleanExpression import BooleanExpression
from Constant import Constant
from RelationalOperator import RelationalOperator
from BinaryExpression import BinaryExpression
from TokenType import TokenType
from Feature import Feature
from Id import Id
from Compound import Compound
from LexicalAnalyzer2 import LexicalAnalyzer2
from ParserException import ParserException
from AssignmentStatement import Assignment_Statement
from Token import Token

class Parser():

    def __init__(self, filename):
        if (filename is None):
            raise ParserException("The filename does not exist or it is not being used in the parser")
        self.lex = LexicalAnalyzer2(filename)

    def Feature(self):
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.FEATURE)
        var1 = self.getId()
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.IS)
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.DO)
        cp1 = self.getCompound()
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.END)
        return Feature(cp1)

    def getId(self):
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.ID)
        tok2 = Token.getLexeme(tok1)
        return Id(tok2)


    def getCompound(self):
        cp1 = Compound()
        tok1 = self.lex.getLookAheadToken()
        while (tok1.getTokType() == TokenType.ID or tok1.getTokType() == TokenType.IF or tok1.getTokType() is TokenType.PRINT
               # should have == instead of is
        or tok1.getTokType() == TokenType.FROM):
            state1 = self.getStatement()
            cp1.addStatement(state1)
            tok1 = self.lex.getLookAheadToken()
        return cp1

    def getStatement(self):

        tok1 = self.lex.getLookAheadToken()
        if (tok1.getTokType() == TokenType.ID):
            state1 = self.getAssignmentStatement()
        elif (tok1.getTokType() == TokenType.IF):
            state1 = self.getIfStatement()
        elif (tok1.getTokType() == TokenType.PRINT):
            state1 = self.getPrintStatement()
        elif (tok1.getTokType() == TokenType.FROM):
            state1 = self.getLoopStatement()
        else:
            raise ParserException("There is an error with obtaining statements in your getStatement in the parser class")
        return state1

    def getLoopStatement(self):
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.FROM)
        assignment1 = self.getAssignmentStatement()
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.UNTIL)
        boolexp1 = self.getBooleanExpression()
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.LOOP)
        cp1 = self.getCompound()
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.END)
        return LoopStatement(assignment1, boolexp1, cp1)


    def getPrintStatement(self):
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.PRINT)
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.LEFT_PAREN__TOK)
        expr1 = self.getExpression()
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.RIGHT_PAREN_TOK)
        return PrintStatement(expr1)


    def getIfStatement(self):
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.IF)
        boolexpr1 = self.getBooleanExpression()
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.THEN)
        cp1 = self.getCompound()
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.ELSE)
        cp2 = self.getCompound()
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.END)
        return IfStatement(boolexpr1, cp1, cp2)

    def getBooleanExpression(self):
        ropt = self.getRelationalOperator()
        expr1 = self.getExpression()
        expr2 = self.getExpression()
        return BooleanExpression(ropt, expr1, expr2)


    def getAssignmentStatement(self):
        var1 = self.getId()
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.ASSIGN_TOK)
        expr1 = self.getExpression()
        return Assignment_Statement(var1, expr1)

    def getExpression(self):
        tok1 = self.lex.getLookAheadToken()
        if (tok1.getTokType() == TokenType.ADD_TOK or tok1.getTokType() == TokenType.SUB_TOK
        or tok1.getTokType() == TokenType.MUL_TOK or tok1.getTokType() == TokenType.DIV_TOK):
            expr  = self.getBinaryExpression()
        elif (tok1.getTokType() == TokenType.ID):
            expr = self.getId()
        elif (tok1.getTokType() == TokenType.CONSTANT):
            expr = self.getConstant()
        else:
            raise ParserException("There is an error in obtaining an expression in your get expression class")
        
        '''
        You should be able to give a better error message involving exactly where in the source code
        that the error was discovered.
        '''
        
        return expr

    def getConstant(self):
        tok1 = self.lex.getNextToken()
        self.match(tok1, TokenType.CONSTANT)
        try:
            constant1 = int(tok1.getLexeme())
        except:
            raise ParserException("The value in the getConstant method is not converting into a number in the Parser Class")
        return Constant(constant1)


    def getBinaryExpression(self):
        ao = self.getArithmeticOperator()
        expr1 = self.getExpression()
        expr2 = self.getExpression()
        return BinaryExpression(ao, expr1, expr2)

    def getArithmeticOperator(self):
        tok1 = self.lex.getNextToken()
        if (tok1.getTokType() == TokenType.ADD_TOK):
            ao = ArithmeticOperator.add_opt
        elif (tok1.getTokType() == TokenType.SUB_TOK):
            ao = ArithmeticOperator.sub_opt
        elif (tok1.getTokType() == TokenType.MUL_TOK):
            ao = ArithmeticOperator.mul_opt
        elif (tok1.getTokType() == TokenType.DIV_TOK):
            ao = ArithmeticOperator.div_opt
        else:
            raise ParserException("There is an error with the arithmetic oeprator in the parser class")
        return ao

    def getRelationalOperator(self):
        tok1 = self.lex.getNextToken()
        if (tok1.getTokType() == TokenType.EQ_TOK):
            ropt = RelationalOperator.eq_opt
        elif (tok1.getTokType() == TokenType.NE_TOK):
            ropt = RelationalOperator.ne_opt
        elif (tok1.getTokType() == TokenType.GT_TOK):
            ropt = RelationalOperator.gt_opt
        elif (tok1.getTokType() == TokenType.GE_TOK):
            ropt = RelationalOperator.ge_opt
        elif (tok1.getTokType() == TokenType.LT_TOK):
            ropt = RelationalOperator.lt_opt
        elif (tok1.getTokType() == TokenType.LE_TOK):
            ropt = RelationalOperator.le_opt
        else:
            raise ParserException("There is something wrong with getting the relational operator in the parser class" + str(tok1))
        return ropt

    def match(self, tok, tokenType):
        assert tok is not None
        assert tokenType is not None
        if (tok.getTokType() != tokenType):
            raise ParserException("The match between the tok and the tokentype is not correct.  Make sure they are matching")