__author__ = 'ravipatel'

from Memory import store
from Statement import Statement


class Assignment_Statement(Statement):
    """

    """
    def __init__(self,var1, expr1):
        if (var1 is None):
            raise ValueError("Error for var1 in assignment_Statement class")
        self.var1 = var1
        if (expr1 is None):
            raise ValueError("Error for expr1 in assignment_statement class")
        self.expr1 = expr1

    def execute(self):
        store(self.var1.getVar(), self.expr1.evaluate())
