print("Dati o valoare pentru n: ")
n=int (input ())

p=3
nr=3

if n<4:
    print (n)
else:
    while (p<n):
        nr+=1
        auxx=nr
        d=2
        while auxx!=1 and (p<n):
            if auxx%d==0:
                k=d
                if (d==nr):
                    p+=1
                else:
                    p+=d
                while (auxx%d==0):
                    auxx/=d
            d+=1
    print ("Valoarea cautata este: ", k)

