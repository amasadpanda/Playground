import math

def problem9():
    def isPTriple(*tuple):
        lit = list(map(lambda x: x*x, tuple))
        return (lit[0] + lit[1]  == lit[2])
    max = 1000;
    for i in range(1, max):
        for j in range(1, max-i):
            if isPTriple(i,j, max-i-j):
                return (i*j*(max-i-j))

def problem10():
    primes = []
    sieve = [True]*2000000
    for i in range(2,2000000):
        if sieve[i] == True:
            primes.append(i)
        for j in range(i,2000000,i):
            sieve[j] = False
    return primes

def problem11():
    content = []
    with open("problem10data", "r") as ins:
        for lines in ins:
            content.append(list(map(int, lines.split())))
    ma = 0
    n = len(content)
    for row in content:
        ma = max(ma, rolling(*row))
    for i in range(n):
        ma = max(ma, rolling(*[row[i] for row in content]))
    for i in range(n):
        ma = max(ma, rolling(*[content[i+j][j] for j in range(n-i)]))
        ma = max(ma, rolling(*[content[j][i+j] for j in range(n-i)]))
    for i in range(n):
        ma = max(ma, rolling(*[content[i+j][n-i-1] for j in range(n-i)]))
        ma = max(ma,rolling(*[content[j][n-i-j-1] for j in range(n-i)]))
    return ma

def rolling(*row):
    def prob(*arg):
        prod = 1
        for i in arg:
            prod *= i
        return prod
    pro = prob(*row[:4])
    ma = pro
    for i in range(4,len(row)):
        if row[i-4] != 0:
            pro /= (row[i-4])
        if row[i] != 0:
            pro *= row[i]
        ma = max(pro, ma)
    return ma

def problem12():
    triangle = 1
    count = 2
    while combination_with_duplicates(primeFactors(triangle)) < 500:
        #print(triangle,':', (primeFactors(triangle)),'~', len(divisors(triangle)))
        triangle += count
        count+=1
    return triangle

#Super slow for primes
def primeFactors(n):
    factors = []
    divideBy = 2
    while n >= divideBy:
        if n % divideBy == 0:
            factors.append(divideBy)
            n /= divideBy
        else:
            divideBy+=1
    return factors
#super super slow
def divisors(n):
    divisors = [1]
    for i in range(2,n+1):
        if n % i == 0:
            divisors.append(i)
    return divisors

def combination_with_duplicates(li):
    """
    Uses polynomial expansion to generate combinations
    Duplicates of prime factors into the polynomial from (1+x+x^2...x^duplicate)
    """
    from itertools import groupby
    from functools import reduce
    res = [1]
    for i in [len(list(group)) for key, group in groupby(li)]:
        res = polynomial_expansion(res, [1]*(i+1))
    return sum(res)

def polynomial_expansion(list1, list2):
    res = [0]*(len(list1)+len(list2)-1)
    for o1,i1 in enumerate(list1):
        for o2,i2 in enumerate(list2):
            res[o1+o2] += i1*i2
    return res

def problem13():
    sum = 0
    with open("problem13data", "r") as ins:
        for lines in ins:
            sum += (int(lines))
    return sum

def problem14():
    ma = 0
    dict = {1:1, 0:0}
    def collatz(n):
        x = 1
        if n in dict:
            return dict[n]
        elif n % 2 == 0:
            x = 1+collatz(int(n / 2))
        else:
            x = 2+collatz(int((3*n+1)/2))
        dict[n] = x
        return dict[n]
    for i in range(1000000, 500000, -1):
        collatz(i)
        if dict[i] > dict[ma]:
            ma = i
    return ma

def problem15():
    def nPrWRP(n,r,r1):
        f = math.factorial
        return f(n) // f(r) // f(r1)
    return nPrWRP(40, 20, 20)

def problem16():
    num = str(2**1000)
    sum = 0
    for i in num:
        sum += int(i)
    return sum

def problem17():
    def toVernacular(n):
        dict = {
        0:0, 1:3, 2:3, 3:5, 4: 4, 5:4,
        6:3, 7:5, 8:5, 9:4, 10:3,
        11:6, 12:6, 13:8, 14:8, 15:7,
        16:7, 17: 9, 18:8, 19:8,
        20:6, 30:6, 40:5, 50:5, 60:5,
        70:7, 80:6, 90:6
        } #SMH 40 without the u! FORTY not FOURTY
        str = 0
        if n > 99:
            str += 7 #hundred = 7 letters
            str += dict[n//100]
            if n % 100 == 0:
                return str
            n  = n % 100
            str += 3 #and = 3 letters
        if n > 9:
            if n < 20:
                str += dict[n] # 10 - 19 are custom
                return str
            else:
                str += dict[(n // 10) * 10]
                n = n%10
        if n < 10:
            str += dict[n]
        return str
    sum = 0
    for i in range(1, 1000):
        sum += toVernacular(i)
        print(i,toVernacular(i) )
    return sum + 11 #one thousand = 11 letters

def problem18():
    pyramid = []
    with open("problem18data", "r") as ins:
        for lines in ins:
            pyramid.append(list(map(int, lines.split())))

    for i in range(len(pyramid)-2, -1 , -1):
        for j in range(len(pyramid[i])):
            pyramid[i][j] += max(pyramid[i+1][j], pyramid[i+1][j+1])
            #print(pyramid[i][j], end=' ')
        #print()
    return pyramid[0][0]

def problem19():
    dict = {
        9:30, 4:30, 6:30, 11:30
    }
    day = 2 #sunday marks the first day
    sum = 0
    for year in range(1900, 2001):
        #print(year, sum)
        for month in range(1, 13):
            #print("\t", month, day)
            if day == 1 and year != 1900:
                sum += 1
            if month in dict:
                day += dict[month]
            elif month == 2:
                if year % 4 == 0 and (year % 100 != 0 or year % 400 == 0):
                    day += 29
                else:
                    day += 28
            else:
                day += 31
            day %= 7
    return sum
def problem20():
    hun = str(math.factorial(100))
    sum = 0
    for i in hun:
        sum += int(i)
    return sum

def problem67():
    pyramid = []
    with open("problem67data", "r") as ins:
        for lines in ins:
            pyramid.append(list(map(int, lines.split())))

    for i in range(len(pyramid)-2, -1 , -1):
        for j in range(len(pyramid[i])):
            pyramid[i][j] += max(pyramid[i+1][j], pyramid[i+1][j+1])
            #print(pyramid[i][j], end=' ')
        #print()
    return pyramid[0][0]

print(problem20())
