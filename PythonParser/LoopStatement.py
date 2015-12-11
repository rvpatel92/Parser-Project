__author__ = 'ravipatel'

from Statement import Statement

class LoopStatement(Statement):

    def __init__(self, assignStatement, boolexpr1, cp1):
        if (assignStatement is None):
            raise ValueError("AssignmentStatement in the LoopStatement class has an error")
        self.assignStatement = assignStatement
        if (boolexpr1 is None):
            raise ValueError("Boolean Expression 1 is wrong in the loopstatement and has an error")
        self.boolexpr1 = boolexpr1
        if (cp1 is None):
            raise ValueError("cp1 is wrong in the loopstatement and has an error")
        self.cp1 = cp1

    def execute(self):
        self.assignStatement.execute()
        while (self.boolexpr1.evaluate() is False):
            self.cp1.execute()