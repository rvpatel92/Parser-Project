__author__ = 'ravipatel'

from Statement import Statement

class IfStatement(Statement):

    def __init__(self, boolexpr1, cp1, cp2):
        if (boolexpr1 is None):
            raise ValueError("boolexpr1 in ifstatement is wrong")
        self.boolexpr1 = boolexpr1
        if (cp1 is None):
            raise ValueError("cp1 in ifstatement is wrong")
        self.cp1 = cp1
        if (cp2 is None):
            raise ValueError("cp2 in ifstatement is wrong")
        self.cp2 = cp2

    def execute(self):
        if (self.boolexpr1.evaluate() is True):
            self.cp1.execute()
        elif (self.boolexpr1.evaluate() is False):
            self.cp2.execute()
            '''
            what if neither of the 2 (returns something besides a boolean) is true?
            '''