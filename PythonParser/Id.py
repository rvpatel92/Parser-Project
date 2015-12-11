__author__ = 'ravipatel'

from Memory import fetch

class Id(object):
    """
    """
    def __init__ (self, var1):
        """
        :param var1:
        :return:
        """
        if (var1 is None):
            raise ValueError("There is no variable in the Id Class")
        if (var1 != ' '):
            
            '''
            If you are going to do an error check here, you need to 
            check whether var is valid - not just whether it does
            meet 1 of many criteria.
            '''
            self.var1 = var1
        else:
            raise ValueError("There is a space where var1 should be and that is an error in the Id Class")

    def evaluate(self):
        return fetch(self.var1)

    def getVar(self):
        return self.var1



