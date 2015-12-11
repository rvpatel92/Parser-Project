__author__ = 'ravipatel'

class Compound(object):

    def __init__(self):
        self.statements = []

    def addStatement(self, statement):
        """
        :param statement:
        :return:
        """
        if (statement is None):
            raise ValueError("There are no statement arguements in the class Compound")
        self.statements.append(statement)

    def execute(self):
        for i in range (len(self.statements)):
            self.statements[i].execute()