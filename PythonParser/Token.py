__author__ = 'ravipatel'

from TokenType import TokenType
from LexicalException import LexicalException

class Token(object):
    def __init__(self, tokType, lexeme, rowNumber, columnNumber):
        """
        :param tokType:
        :param lexeme:
        :param rowNumber:
        :param columnNumber:
        :return:
        constructor
        """
        if (lexeme is None or tokType is None):
            raise ValueError('None lexeme')
        if (rowNumber <= 0 or columnNumber <= 0):
            raise ValueError("Negative number is error in Token Class")

        self.tokType = tokType
        self.lexeme = lexeme
        self.rowNumber = rowNumber
        self.columnNumber = columnNumber

    def getTokType(self):
        return self.tokType

    def getLexeme(self):
        return self.lexeme

    def getRowNumber(self):
        return self.rowNumber

    def getColumnNumber(self):
        return self.columnNumber