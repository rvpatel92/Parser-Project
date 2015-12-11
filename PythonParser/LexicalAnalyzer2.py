__author__ = 'ravipatel'

from LexicalException import LexicalException
from TokenType import TokenType
from Token import Token

class LexicalAnalyzer2(object):

    tokenList = []

    def __init__(self, filename):
        if filename is None:
            raise LexicalException("file is invalid")

        file = open(filename)
        line_Number = 0
        for line in file:
            line_Number = line_Number + 1
            self.process_Line(line.strip(), line_Number)
        self.tokenList.append(Token(TokenType.EOS, "EOS", line_Number + 1, 1))

    def process_Line(self, line, line_Number):
        index = 0
        index = self.skip_White_Space(line, index)
        while (index < len(line)):
            lexeme = self.get_Next_Lexeme(line, line_Number, index)
            lexeme = lexeme.lower()
            toktype = self.get_Token_Type(lexeme, line_Number, index)
            index += len(lexeme)
            self.tokenList.append(Token(toktype, lexeme, line_Number, index + 1))
            index = self.skip_White_Space(line, index)

    def get_Token_Type(self, lexeme, line_Number, index):
        if lexeme[0].isalpha():
            if (len(lexeme) == 1):
                tok_type = TokenType.ID
            elif (lexeme == "feature"):
                tok_type = TokenType.FEATURE
            elif (lexeme == "end"):
                tok_type = TokenType.END
            elif (lexeme == "if"):
                tok_type = TokenType.IF
            elif (lexeme == "is"):
                tok_type = TokenType.IS
            elif (lexeme == "print"):
                tok_type = TokenType.PRINT
            elif (lexeme == "loop"):
                tok_type = TokenType.LOOP
            elif (lexeme == "until"):
                tok_type = TokenType.UNTIL
            elif (lexeme == "from"):
                tok_type = TokenType.FROM
            elif (lexeme == "do"):
                tok_type = TokenType.DO
            elif (lexeme == "else"):
                tok_type = TokenType.ELSE
            elif (lexeme == "then"):
                tok_type = TokenType.THEN
            else:
                raise LexicalException('invalid identifier at row ' + index + ' column ' + line_Number)
        elif (lexeme.isdigit()):
            if (self.alldigit(lexeme)):
                tok_type = TokenType.CONSTANT
            else:
                raise LexicalException('invalid identifier at row ' + index + ' column ' + line_Number)
        elif (lexeme == ":="):
            tok_type = TokenType.ASSIGN_TOK
        elif (lexeme == "+"):
            tok_type = TokenType.ADD_TOK
        elif (lexeme == "-"):
            tok_type = TokenType.SUB_TOK
        elif (lexeme == "*"):
            tok_type = TokenType.MUL_TOK
        elif (lexeme == "/"):
            tok_type = TokenType.DIV_TOK
        elif (lexeme == "="):
            tok_type = TokenType.EQ_TOK
        elif (lexeme == "/="):
            tok_type= TokenType.NE_TOK
        elif (lexeme == "<"):
            tok_type = TokenType.LT_TOK
        elif (lexeme == "<="):
            tok_type = TokenType.LE_TOK
        elif (lexeme == ">"):
            tok_type = TokenType.GT_TOK
        elif (lexeme == ">="):
            tok_type = TokenType.GE_TOK
        elif (lexeme == "("):
            tok_type = TokenType.LEFT_PAREN__TOK
        elif (lexeme == ")"):
            tok_type = TokenType.RIGHT_PAREN_TOK
        else:
            raise LexicalException("invalid lexeme at row " + index + ' column ' + line_Number)
        return tok_type

    def skip_White_Space(self, line, index):
        i = index
        while (i < len(line) and line[i] == ' '):
            i = i + 1
        return i

    def get_Next_Lexeme(self, line, line_Number, index):
        i = index
        while (i < len(line) and line[i] is not ' '):
            i += 1
        return line[index:i]

    def alldigit(self, s):
        index = 0
        while index < len(s) and s[index].isdigit():
            index += 1
        return index >= len(s)

    def getNextToken(self):
        if not self.tokenList:
            raise LexicalException("Error in getNextToken in the LexicalAnalyzer class")
        temp = self.tokenList.pop(0)
        return temp

    def getLookAheadToken(self):
        if not self.tokenList:
            raise LexicalException("Error in getNextToken in the LexicalAnalyzer")
        return self.tokenList[0]