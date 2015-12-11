__author__ = 'ravipatel'

from Statement import Statement

class PrintStatement(Statement):

    def __init__(self, expr1):
        if (expr1 is None):
            raise Exception("Expr1 is wrong in print statement")
        self.expr1 = expr1

    def execute(self):
        print(self.expr1.evaluate())