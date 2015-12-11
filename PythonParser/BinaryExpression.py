__author__ = 'ravipatel'

from Expression import Expression
from ArithmeticOperator import ArithmeticOperator

class BinaryExpression(Expression):

    def __init__(self, arith_opt, expr1, expr2):
        if (arith_opt is None):
            raise ValueError("There is an error with arith_opt in the BinaryExpression Class")
        self.arith_opt = arith_opt
        if (expr1 is None):
            raise ValueError("There is an error with expr1 in the BinaryExpressino Class")
        self.expr1 = expr1
        if (expr2 is None):
            raise ValueError("There is an error with expr2 in the BinaryExpression class")
        self.expr2 = expr2

    def evaluate(self):
        if (self.arith_opt == ArithmeticOperator.add_opt):
            answer = (self.expr1.evaluate() + self.expr2.evaluate())
        elif self.arith_opt == ArithmeticOperator.sub_opt:
            answer = (self.expr1.evaluate() - self.expr2.evaluate())
        elif self.arith_opt == ArithmeticOperator.mul_opt:
            answer = (self.expr1.evaluate() * self.expr2.evaluate())
        elif self.arith_opt == ArithmeticOperator.div_opt:
            answer = (self.expr1.evaluate() / self.expr2.evaluate())
        else:
            raise ValueError("Something in the binaryExpression class iwthin the evaluate method is broken")
        return int(answer)