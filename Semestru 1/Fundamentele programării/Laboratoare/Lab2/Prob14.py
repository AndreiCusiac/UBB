def suma_div (a):
    s=0
    for i in range (1, a):
        if a%i==0:
            s+=i
    return s

n = int(input("Dati un numar: "))

ok=0
while ok==0:
    n+=1
    if (n==suma_div(n)):
        ok=1
        print("Numarul cautat este: ", n)

    
