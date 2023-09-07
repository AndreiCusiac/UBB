n = int (input("Dati o valoare pentru n: "))

d=1

for i in range (2, n):
    if (n%i==0):
        d=0;

if (d==1):
    print("n este prim")
else:
    print("n nu e prim")
