def memoize(function):
    cache = {}
    def decorated_function(*args):
        if args in cache:
            return cache[args]
        else:
            val = function(*args)
            cache[args] = val
            return val
    return decorated_function


def fib(position):
    if position < 2:
        return position
    else:
        return fib(position-1) + fib(position-2)

@memoize
def fibMemo(position):
    if position < 2:
        return position
    else:
        return fibMemo(position-1) + fibMemo(position-2)	

import sys
if sys.platform == "win32":
    from time import clock
else:
    from time import time as clock

position = 35
timeBegin = clock()
retFib = fib(position)
timeEnd = clock()
print "Position: %d Result: %d Time: %.10fs" % (position, retFib, timeEnd - timeBegin)
timeBegin = clock()
retFibMemo = fibMemo(position)
timeEnd = clock()
print "Position: %d Result: %d Time: %.10fs" % (position, retFibMemo, timeEnd - timeBegin)
