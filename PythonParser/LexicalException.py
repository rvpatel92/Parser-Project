__author__ = 'ravipatel'


class LexicalException(Exception):
    """


    """
    def __init__(self, errorDefinition):
        """
        Constructor
        errorDefinition returns error message to users
        """
        print(errorDefinition)