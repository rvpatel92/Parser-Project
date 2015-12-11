__author__ = 'ravipatel'

from Parser import Parser
from LexicalException import LexicalException
from ParserException import ParserException

class Interpreter(object):

    if __name__ == '__main__':
        try:
            p = Parser("test4.e")
            feature = p.Feature()
            feature.execute()
        except IOError:
            print("The source file being used in the interpreter is not located")
        except LexicalException as e:
            print("Lexical Exception is " + str(e))
        except ValueError as e:
            print("Value Error is " + str(e))
        except ParserException as e:
            print("Parser Exception is " + str(e))
        except:
            print("This is a catch all error message that is used for an error not being found or being noted by any exception handlers")
            
            '''
            96/100 Please see my comments in your code.
            '''