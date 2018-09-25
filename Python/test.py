#!/usr/bin/env python
args = [1,20,3]
#print range(*args)
def printa(a = "hello world", *vars):
    print(a)
    for i in vars:
        print(i)
    print('''
    1
    2
    3
    4
    5
    ''')
printa("no default here, sadly",*args)

def dictTest(val=1, hi=2, hello=3):
    print(val, hi, hello)
d = {"val":3, "hi":2, "hello":1}
dictTest(**d)
err = dictTest
err()

def lamb(n):
    return lambda x: x + la(n)
def la(n):
    return n*n
f = lamb(2)
print(f(1))
