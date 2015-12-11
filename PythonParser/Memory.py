memArray = [0]*53

# Why 53?  There are only 26 possible variables.

def fetch(var):
    index = (ord(var)) - (ord('a')) + 26
    return memArray[index]

def store(var, value):
    index = (ord(var)) - (ord('a')) + 26
    memArray[index] = value