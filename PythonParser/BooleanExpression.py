__author__ = 'ravipatel'

from RelationalOperator import RelationalOperator

class BooleanExpression(object):

    def __init__(self, Relat_Opt, expr1, expr2):
        if (Relat_Opt is None):
            raise ValueError("Error in relational operator in the relational operator class")
        self.Relat_Opt = Relat_Opt
        if (expr1 is None):
            raise ValueError("Error in expr1 in the relational operator class")
        self.expr1 = expr1
        if (expr2 is None):
            raise ValueError("Error in expr2 in the relational operator class")
        self.expr2 = expr2

    def evaluate(self):
        if (self.Relat_Opt == RelationalOperator.le_opt):
            answer = (self.expr1.evaluate() <= self.expr2.evaluate())
        elif (self.Relat_Opt == RelationalOperator.ge_opt):
            answer = (self.expr1.evaluate() >= self.expr2.evaluate())
        elif (self.Relat_Opt == RelationalOperator.lt_opt):
            answer = (self.expr1.evaluate() < self.expr2.evaluate())
        elif (self.Relat_Opt == RelationalOperator.gt_opt):
            answer = (self.expr1.evaluate() > self.expr2.evaluate())
        elif (self.Relat_Opt == RelationalOperator.eq_opt):
            answer = (self.expr1.evaluate() == self.expr2.evaluate())
        elif (self.Relat_Opt == RelationalOperator.ne_opt):
            answer = (self.expr1.evaluate() != self.expr2.evaluate())
        else:
            raise ValueError("Error")
        return answer
