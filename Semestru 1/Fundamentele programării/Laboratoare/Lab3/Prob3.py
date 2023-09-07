def prim (a):
    v=1
    for i in range(2, a):
        if (a%i==0):
            v=0
    if (a<2):
        v=0
    return v

def rel_prim (a, b):
    if a*b==0:
        return 0
    while (a!=b):
        if (a>b):
            a-=b
        else:
            b-=a
    return a

def sub_1 (l):
    print("Optiunea 1")
    #global n
    n = int (input("Dati numarul de numere din lista: "))
    print("Dati elementele listei: ")
    l.clear()   
    for i in range(0, n):
        b = int (input())
        l.append(b)
    print("\n")

def sub_2 (n, l):
    print("Optiunea 2")
    if (len(l)==0):
        print("Lista este vida")
    else:
        print("Lista citita este: ", l)
    print("\n")

def sub_3 (n, l):
    print("Optiunea 3")
    lmax=0
    pmax=0
    p=0
    la=0
    for i in range(0, len(l)):
        if (prim(l[i])==1):
            if (la==0):
                p=i
            la+=1
            if (la>lmax):
                lmax=la
                pmax=p
        else:
            la=0
    print("Secventa cautata este: ")
    c = []
    if len(l)==0:
        print("Lista este vida")
    elif lmax==0:
        print("Nu exista secventa ceruta")
    else:
        for i in range(pmax, pmax+lmax):
            c.append(l[i])
    print(c)
    print("\n")

def sub_4 (n, l):
    print("Optiunea 4")
    lmax=0
    pmax=0
    p=0
    la=1
    for i in range(0, len(l)-1):
        if (rel_prim(l[i], l[i+1])==1):
            if (la==1):
                p=i
            la+=1
            if (la>lmax):
                lmax=la
                pmax=p
        else:
            la=1
    print("Secventa cautata este: ")
    c = []
    if len(l)==0:
        print("Lista este vida")
    elif lmax==0:
        print("Nu exista secventa ceruta")
    else:
        for i in range(pmax, pmax+lmax):
            c.append(l[i])
    print(c)
    print("\n")

def ui ():
    print("Inceput program")
    print("Dati o optiune: \n 0 - Incheierea programului \n 1 - Citirea unei liste de numere intregi \n 2 - Afisarea listei \n 3 - Secventa cea mai lunga de numere prime \n 4 - Secventa cea mai lunga de numere relativ prime \n")
    o = int (input ("Optiunea este: "))
    n = 0
    l = []
    while (o!=0):
        if (o==1):
            sub_1(l)
            #print(n, '\n', l)
        
        elif (o==2):
            sub_2(n, l)
        
        elif (o==3):
            sub_3(n, l)

        elif (o==4):
            sub_4(n, l)
        
        o = int (input("Dati o noua optiune (0, 1, 2, 3, 4): "))
    print("Sfarsit program")

ui()

