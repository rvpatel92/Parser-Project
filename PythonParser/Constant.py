__author__ = 'ravipatel'

from Expression import Expression

class Constant(Expression):

    def __init__(self, value):
        if (value is None):
            raise ValueError("There is a problem with the value in the Constant class")
        self.value = value

    def evaluate(self):
        return self.value