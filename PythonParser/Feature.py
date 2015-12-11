__author__ = 'ravipatel'


class Feature(object):
    """
    """
    def __init__(self, cp1):

        if (cp1 is None):
            raise ValueError("no cp1 is in Feature class")
        self.cp1 = cp1

    def execute(self):
        self.cp1.execute()
