a = int (input("Dati o valoare pentru a: "))
b = int (input("Dati o valoare pentru b: "))
while (a!=b):
    if (a>b):
        a=a-b
    else:
        b=b-a
print("Cel mai mare divizor comun este:", a)
