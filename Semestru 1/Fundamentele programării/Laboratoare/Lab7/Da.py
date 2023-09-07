'''
Created on 2 nov. 2020

@author: Andrei
'''

def get_test():
    c = [{"zi": 2, "tip": 3, "suma": 20}, {"zi": 4, "tip": 5, "suma": 30},{"zi": 1, "tip": 1, "suma": 45}, {"zi": 2, "tip": 3, "suma": 60}, {"zi": 16, "tip": 4, "suma": 50}, {"zi": 20, "tip": 3, "suma": 26}]
    return c

def get_index_zi():
    return "zi"

def get_index_tip():
    return "tip"

def get_index_sum():
    return "suma"

def afis(el):
    """
    functia afiseaza lizibil atributele unei cheltuieli
    el - lista, element al listei cheltuielilor
    """
    print("Ziua ", el[get_index_zi()])
    print("Tipul ", el[get_index_tip()])
    print("Suma ", el[get_index_sum()])
    print("\n")

def afis_big(c, l, o):
    """
    daca o = 1, functia parcurge acele elemente din lista c, cu proprietatea ca indecsii elementelor se afla in lista de indecsi l
    daca o !=1, functia parcurge toate elementele din lista c, cu proprietatea ca indecsii elementelor NU se afla in lista de indecsi l
    """
    if (o==1):
        for el in l:
            afis(c[el])
    else:
        for el in c:
            if c.index(el) not in l:
                afis(el)

def sterg_big(c, l):
    """
    functia sterge acele elemente din lista c care se regasesc in lista l
    """
    for el in l:
        c.remove(el)

def elem_funct_index(d, c, e):
    """
    functia returneaza in lista e elementele din c ai caror indecsi se regasesc in d
    d - lista, contine indecsi ai elementelor din c
    c - lista cheltuielilor
    e - va contine acele elemente din c ai caror indecsi se regasesc in d
    """
    e.clear()
    for el in d:
        if (el<len(c)):
            e.append(c[el])
            
def verif_float(a):
    """
    functia verifica daca variabila data poate fi interpretata drept un numar real
    a - variabila
    returneaza 1, daca a poate fi float, 0 altfel
    """
    if (type(a)==list):
        return 0
    try:
        float(a)
        return 1
    except ValueError or TypeError:
        return 0
    
def verif_int(a):
    """
    functia verifica daca variabila data poate fi interpretata drept un numar intreg
    a - variabila
    returneaza 1, daca a poate fi int, 0 altfel
    """
    if (type(a)==list):
        return 0
    elif (type(a)==float):
        if (a-int(a)!=0):
            return 0
    try:
        int(a)
        return 1
    except ValueError or TypeError:
        return 0

def valid_opt(a, o):
    """
    valideaza optiunea din meniu aleasa de utilizator
    a - nr. natural, descrie multimea {0, 1, 2, 3, ... , a} a numarului de optiuni valabile in cadrul unui (sub)meniu,
    o - variabila, optiunea introdusa de utilizator
    returneaza 1 daca o apartine multimii {0, 1, 2, 3, ..., a}, 2 daca apartine, dar nu este de tip int, 0 altfel
    """
    if (verif_int(o)==0):
        return 0
    
    if (type(o)==int and o>=0 and o<=a):
        return 1
    else:
        o = int(o)
        if (o>=0 and o<=a):
                return 2
    return 0

def cit_opt(nr_op):
    """
    functia permite introducera de la tastatura a valorii unei optiuni din meniu, precum si validarea ei in functie de meniul predefinit
    nr_op - nr natural, egal cu numarul de optiuni din meniul valabil la un momentdat
    returneaza o, o citit, cu conditia ca o sa fie numar natural, o <= nr_op
    """
    o = (input ("Optiunea este: "))
    while (valid_opt(nr_op, o)==0):
        print("Ati introdus o optiune nepermisa (din afara intervalului {0, 1, ... , ", nr_op, "} . Reintroduceti optiunea")
        o = (input ("Optiunea este: "))
    if (valid_opt(nr_op, o)==2):
        o = int(o)

    return o

def valid_zi(a):
    """
    valideaza tipul de data si valorea introdusa de utilizator
    a - variabila
    returneaza 1, daca a este de tip intreg si apartine unei luni (1 <= a <= 31), 0 altfel
    """
    if (verif_int(a)==0):
        return 0
    
    if (type(a)==int and a>=1 and a<=31):
        return 1
    else:
        a = int(a)
        if (a>=1 and a<=31):
            return 2
    return 0

def valid_tip(a):
    """
    valideaza tipul de data si valorea introdusa de utilizator
    a - variabila
    returneaza 1, daca a este de tip intreg si descrie un tip de cheltuiala (1, 2, 3, 4 sau 5), 0 altfel
    """
    if (verif_int(a)==0):
        return 0
    
    if (type(a)==int and a>=1 and a<=5):
        return 1
    else:
        a = int(a)
        if (a>=1 and a<=5):
            return 2
    return 0

def valid_sum(a):
    """
    valideaza tipul de data si valorea introdusa de utilizator
    a - variabila
    returneaza 1, daca a este de tip float si mai mare decat 0, 0 altfel
    """
    if (verif_float(a)==0):
        return 0
    
    if ((type(a)==float or type(a)==int) and a>0):
        return 1
    else:
        a = float(a)
        if (a>0):
            return 2
    return 0

def reun_liste(a, b):
    """
    functia returneaza intersectia listelor a si b, cu elemente strict crescatoare
    a, b - liste
    returneaza o lista cu elementele comune a si b, lista vida altfel
    """
    e = []
    for i in a:
        for j in b:
            if i==j:
                e.append(i)
    return e

def verif_cond_lista(l, d, x, cond, comp):
    """
    functia verifica daca un element al listei de cheltuieli indeplineste conditiile specificate prin variabilele cond si comp
    l - lista, contine cheltuielile
    x - numar natural, indexul listelor-element, specifica ziua, tipul sau suma unei cheltuieli
    cond - 0, 1 sau 2, in functie de tipul de comparatie logica dorita
    comp - numar intreg, in functie de care se face comparatia logica
    returneaza prin d lista indicilor apartinand l care satisfac conditiile
    """
    d.clear()
    if (cond == 0):
        for i in range(0, len(l)):
            if (l[i][x] < comp):
                d.append(i)

    if (cond == 1):
        for i in range(0, len(l)):
            if (l[i][x] == comp):
                d.append(i)

    if (cond == 2):
        for i in range(0, len(l)):
            if (l[i][x] > comp):
                d.append(i)
                
def cit_zi():
    """
    functia permite introducera de la tastatura a valorii unei zile, corespunzatoare unei anumite cheltuieli
    a - variabila (se verifica sa corespunda cerintelor)
    returneaza a, a citit
    """

    print("\n")
    a = (input("Dati ziua (1-31): "))
    print("\n")

    while (valid_zi(a)==0):
        print("\n")
        print("Valoarea introdusa nu este acceptabila. Introduceti o noua valoare")
        #print(a, valid_zi(a))
        a = (input("Dati ziua (1-31): "))

    
    if (valid_zi(a)==2):
        a = int(a)
    return a
        
def cit_tip():
    """
    functia permite introducera de la tastatura a valorii unui tip, corespunzator unei anumite cheltuieli
    a - variabila (se verifica sa corespunda cerintelor)
    returneaza a, a citit
    """

    print("\n")
    print("Dati tipul: \n 1. Mancare - tasta 1 \n 2. Intretinere - tasta 2 \n 3. Imbracaminte- tasta 3 \n 4. Telefon - tasta 4 \n 5. Altele - tasta 5")
    a = input ("Tipul: ")
    print("\n")

    while (valid_tip(a)==0):
        print("\n")
        print("Valoarea introdusa nu este acceptabila. Introduceti o noua valoare")
        print("Dati tipul: \n 1. Mancare - tasta 1 \n 2. Intretinere - tasta 2 \n 3. Imbracaminte- tasta 3 \n 4. Telefon - tasta 4 \n 5. Altele - tasta 5")
        a = input ("Tipul: ")
        print("\n")
              
    if (valid_tip(a)==2):
        a = int(a)  
    return a

def cit_sum():
    """
    functia permite introducera de la tastatura a valorii unei valori numerice, corespunzatoare unei anumite cheltuieli
    a - variabila (se verifica sa corespunda cerintelor)
    returneaza a, a citit
    """
    print("\n")
    a = input("Dati suma (mai mare decat 0): ")
    print("\n")
    
    while (valid_sum(a)==0):
        print("\n")
        print("Valoarea introdusa nu este acceptabila. Introduceti o noua valoare")
        a = (input("Dati suma (mai mare decat 0): "))

    a = float(a)
    return a

def help_modif_zi(c, d):
    """
    functia citeste ziua dorita si afiseaza cheltuielile din acea zi
    z - numar natural, zi
    """
    z = cit_zi()
    
    print("\n")
    
    print("Toate cheltuielile din aceasta zi sunt: ")
    
    verif_cond_lista(c, d, get_index_zi(), 1, z)

def help_modif_tip(c, d):
    """
    functia citeste tipul dorita si afiseaza cheltuielile de acel tip
    t - numar natural, tip
    """
    t = cit_tip()
    
    print("\n")
    
    print("Toate cheltuielile de acest tip sunt: ")
    
    verif_cond_lista(c, d, get_index_tip(), 1, t)

def help_modif_sum(c, d):
    """
    functia citeste suma dorita si afiseaza cheltuielile cu acea suma
    s - numar real, suma
    """

    s = cit_sum()
    
    print("\n")
    
    print("Toate cheltuielile cu aceasta suma sunt: ")
    
    verif_cond_lista(c, d, get_index_sum(), 1, s)
    
def identif_cheltuiala(c):
    """
    functia permite identificarea unei cheltuieli existente in vederea modificarii acesteia
    d, e, f, test - liste, contin indecsi 
    """

    d = []
    e = []
    f = []
    
    print("Dati atributele (ziua, tipul si suma) cheltuielii pe care doriti sa o modificati: ")

    help_modif_zi(c, d)

    while  (len(d) == 0):
        print("Nu au fost gasite cheltuieli in aceasta zi")
        d.clear()
        help_modif_zi(c, d)
        
    afis_big(c, d, 1)

        
    help_modif_tip(c, e)
    test = reun_liste(d, e)

    while  (len(test) == 0):
        print("Nu au fost gasite cheltuieli din ziua specificata de acest tip")
        e.clear()
        help_modif_tip(c, e)
        test = reun_liste(d, e)

    d = reun_liste(d, e)
    
    afis_big(c, d, 1)


    help_modif_sum(c, f)
    test = reun_liste(d, f)
    
    while  (len(test) == 0):
        print("Nu au fost gasite cheltuieli din ziua si de tipul specificat de aceasta suma")
        f.clear()
        help_modif_sum(c, f)
        test = reun_liste(d, f)

    d = reun_liste(d, f)

    afis_big(c, d, 1)
    
    return d[0]

'''
Modul cu functii orientate spre functionalitatile propuse
'''

def ad_nou(z, t, s):
    """
    functia permite introducerea in liste predefinite a unei noi cheltuieli, cu toate atributele sale (zi, tip, suma)
    """
    return {get_index_zi(): z, get_index_tip(): t, get_index_sum(): s}

def modif(c):
    """
    functia  permite modificarea unei cheltuieli deja existente
    i - numar natural, indexul cheltuielii ce se doreste a fi modificata
    """
    
    i = identif_cheltuiala(c)

    print("Cheltuiala initiala a fost identificata cu succes! \n")
    
    print("Dati atributele (ziua, tipul si suma) noi cheltuieli: ")
    c[i] = ad_nou()

    print("\n")
    
def st_zi(c, z):
    """
    functia sterge toate cheltuielile dintr-o zi data 

    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea 
    z - numar natural, citit
    d - lista, contine indecsi
    """

    d = []
    el_del = []
    
    verif_cond_lista(c, d, get_index_zi(), 1, z)
    elem_funct_index(d, c, el_del)
    
    if len(d) != 0:
        sterg_big(c, el_del)
        print("Stergerea a fost efectuata cu succes!")
        
    else:
        print("Nu au fost gasite astfel de cheltuieli")

    print("\n")
    
def st_interval_zi(c):
    """
    functia sterge toate cheltuielile dintr-un interval de timp dat 

    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea 
    z1 - numar natural, citit
    z2 - numar natural, citit
    d - lista, contine indecsi
    """

    print("\n")
    print("Dati data de inceput: ")
    z1 = cit_zi()

    print("Dati data de sfarsit: ")
    z2 = cit_zi()
    
    d = []
    e = []
    el_del = []
    
    verif_cond_lista(c, d, get_index_zi(), 2, z1-1)
    verif_cond_lista(c, e, get_index_zi(), 0, z2+1)

    d = reun_liste(d, e)
    elem_funct_index(d, c, el_del)
    
    if len(d) != 0:
        sterg_big(c, el_del)
        print("Stergerea a fost efectuata cu succes!")
        
    else:
        print("Nu au fost gasite astfel de cheltuieli")

def st_tip(c):
    """
    functia sterge toate cheltuielile de un anumit tip dat 

    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea 
    t - numar natural, citit
    d - lista, contine indecsi
    """

    print("\n")
    print("Alegeti tipul pentru care doriti sa stergeti chelutilelile")
    t = cit_tip()

    d = []
    el_del = []
    
    verif_cond_lista(c, d, get_index_tip(), 1, t)
    elem_funct_index(d, c, el_del)
    
    if len(d) != 0:
        sterg_big(c, el_del)
        print("Stergerea a fost efectuata cu succes!")
        
    else:
        print("Nu au fost gasite astfel de cheltuieli")

    print("\n")
    
def tip_sum(c):
    """
    functia tipareste toate cheltuielile mai mari decat o suma data
    returneaza cheltuielile cerute, altfel returneaza un mesaj de eroare in cazul in care nu exista cheltuieli care indeplinesc conditia data
    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea 
    s - numar intreg, citit
    d - lista, contine indici
    """

    print("\n")
    print("Alegeti o valoare minima fata de cheltuielile ce doriti sa fie afisate")
    s = cit_sum()

    d = []
    
    print("Cheltuielile mai mari decat ", s, " sunt: ")
    verif_cond_lista(c, d, get_index_sum(), 2, s)

    if len(d) != 0:
        afis_big(c, d, 1)
        
    else:
        print("Nu au fost gasite astfel de cheltuieli")

    print("\n")

def tip_zi_sum(c):
    """
    functia tipareste toate cheltuielile mai mici decat o suma data, efectuate inaintea unei zile date
    returneaza cheltuielile cerute, altfel returneaza un mesaj de eroare in cazul in care nu exista cheltuieli care indeplinesc conditia data
    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea 
    s - numar intreg, citit
    z - numar intreg, citit
    d - lista, contine indici
    e - lista, contine indici
    """

    print("\n")
    print("Alegeti o valoare maxima fata de cheltuielile ce doriti sa fie afisate")
    s = cit_sum()

    print("\n")
    print("Alegeti ziua inaintea careia trebuie sa aiba loc cheltuielile")
    z = cit_zi()

    d = []
    e = []

    print("Cheltuielile dinaintea zilei ", z, ", mai mici decat ", s, " sunt: ")
    verif_cond_lista(c, d, get_index_sum(), 0, s)
    verif_cond_lista(c, e, get_index_zi(), 0, z)

    d = reun_liste(d, e)

    if len(d) != 0:
        afis_big(c, d, 1)
        
    else:
        print("Nu au fost gasite astfel de cheltuieli")

    print("\n")

def tip_tip(c):
    """
    functia tipareste toate cheltuielile de un anumit tip dat
    returneaza cheltuielile cerute, altfel returneaza un mesaj de eroare in cazul in care nu exista cheltuieli care indeplinesc conditia data
    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea 
    t - numar intreg, citit
    ok - numar intreg, folosit drept verificare
    """

    print("\n")
    print("Alegeti tipul cheltuielilor ce doriti sa fie afisate")
    t = cit_tip()
    d = []
    
    print("Cheltuielile de tipul ", t, " sunt: ")
    verif_cond_lista(c, d, get_index_tip(), 1, t)

    if len(d) != 0:
        afis_big(c, d, 1)
        
    else:
        print("Nu au fost gasite astfel de cheltuieli")

    print("\n")
    
def rap_sum_tip(c, t):
    """
    functia calculeaza suma totala pentru au anumit tip de cheltuiala
    returneaza suma ceruta, altfel returneaza un mesaj de eroare in cazul in care nu exista cheltuieli care indeplinesc conditia data
    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea
    d - lista, contine indecsi
    t - numar intreg, citit
    s - numar real, suma ceruta
    """
    
    d = []
    
    verif_cond_lista(c, d, get_index_tip(), 1, t)
    s = sum_tot(c, d)
    
    print("Suma totala este: ")
    
    if s != 0:
        print(s)
        
    else:
        print("Nu au fost gasite astfel de cheltuieli")

    print("\n")

def rap_zi_sum_max(c):
    """
    functia afiseaza ziua in care s-a efectuat cheltuiala maxima
    returneaza ziua ceruta, altfel returneaza un mesaj de eroare in cazul in care nu exista cheltuieli care indeplinesc conditia data
    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea 
    i - numar intreg, index
    """
    
    print("\n")

    i = index_sum_max(c)

    print("Ziua in care s-a efectuat cheltuiala de suma maxima este: ")
    
    if i >= 0:
        afis(c[i])
        
    else:
        print("Nu au fost gasita suma maxima")

    print("\n")

def rap_sum(c):
    """
    functia tipareste toate cheltuielile de o anumita suma data
    returneaza cheltuielile cerute, altfel returneaza un mesaj de eroare in cazul in care nu exista cheltuieli care indeplinesc conditia data
    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea 
    s - numar real, citit
    d - lista, contine indecsi
    """
    
    print("\n")
    print("Alegeti suma cheltuielilor: ")
    s = cit_sum()
    d = []
    
    print("Cheltuielile de suma ", s, " sunt: ")
    verif_cond_lista(c, d, get_index_sum(), 1, s)

    if len(d) != 0:
        afis_big(c, d, 1)
        
    else:
        print("Nu au fost gasite astfel de cheltuieli")

    print("\n")

def rap_tip(c):
    """
    functia tipareste toate cheltuielile in functie de tip, in ordine
    returneaza cheltuielile cerute, altfel returneaza un mesaj de eroare in cazul in care nu exista cheltuieli care indeplinesc conditia data
    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea 
    """
    
    print("\n")
    d = []
    ok = 0
    
    for t in range(1, 6):
        d.clear()
        verif_cond_lista(c, d, get_index_tip(), 1, t)
        if (len(d) != 0):
            ok=1
            print("Tipul ", t, ": \n")
            afis_big(c, d, 1)

    if (ok == 0):
        print("Nu au fost gasite cheltuieli")
    
    print("\n")
    
def elim_tip(c):
    """
    functia tipareste toate cheltuielile, mai putin cele de un anumit tip dat
    returneaza cheltuielile cerute, altfel returneaza un mesaj de eroare in cazul in care nu exista cheltuieli care indeplinesc conditia data
    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea 
    t - numar intreg, citit
    d - lista, contine indexi
    """

    print("\n")
    print("Alegeti tipul cheltuielilor ce NU doriti sa fie afisate")
    t = cit_tip()
    d = []

    print("Toate cheltuielile, in afara celor de tipul ", t, ", sunt: ")
    verif_cond_lista(c, d, get_index_tip(), 1, t)

    if len(d) != len(c):
        afis_big(c, d, 0)
        
    else:
        print("Nu au fost gasite astfel de cheltuieli")

    print("\n")

def elim_sum(c):
    """
    functia tipareste toate cheltuielile, mai putin cele mai mici decat o suma data
    returneaza cheltuielile cerute, altfel returneaza un mesaj de eroare in cazul in care nu exista cheltuieli care indeplinesc conditia data
    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea 
    s - numar intreg, citit
    d - lista, contine indexi
    """

    print("\n")
    print("Alegeti suma minima a cheltuielilor ce NU doriti sa fie afisate")
    s = cit_sum()
    d = []

    print("Toate cheltuielile, in afara celor mai mici de suma ", s, ", sunt: ")
    verif_cond_lista(c, d, get_index_sum(), 0, s)

    if len(d) != len(c):
        afis_big(c, d, 0)
        
    else:
        print("Nu au fost gasite astfel de cheltuieli")

    print("\n")
    
def do_back (a, b):
    """
    functia asigura back-up-ul in lista b a listei date in a, prin copierea listei a in b
    a, b - liste
    """
    d = a.copy()
    b.append(d)

def sum_tot(c, l):
    """
    functia returneaza suma sumelor acelor cheltuieli din c ai caror indecsi se regasesc in l, altfel 0 daca l e vida
    c - lista cheltuielilor
    l - lista indescilor
    """
    if (len(l) == 0):
        return 0
    else:
        s = 0
        for el in l:
            if el<len(c):
                s += c[el][get_index_sum()]
    return s

def index_sum_max(c):
    """
    functia returneaza indexul elementului din lista c cu proprietatea ca retine suma maxima a vreunei cheltuieli
    c - lista cheltuielilor
    """
    maxx = 0
    imax = -1
    
    for i in c:
        if i[get_index_sum()] > maxx:
            maxx = i[get_index_sum()]
            imax = c.index(i)

    return imax


def valid_comanda(com):
    if (com[0]=="add"):
        try: 
            if (valid_zi(com[1]) * valid_tip(com[2]) * valid_sum(com[3]) == 1): 
                return 1

            elif (valid_zi(com[1]) * valid_tip(com[2]) * valid_sum(com[3]) > 1):
                com[1] = int (com[1])
                com[2] = int (com[2])
                com[3] = int (com[3])
                return 1
                
        except (IndexError or KeyError or ValueError):
            return 0
        
    if (com[0]=="dell"):
        try: 
            if (valid_zi(com[1]) == 1):
                return 1

            elif (valid_zi(com[1]) == 2):
                com[1] = int (com[1])
                return 1
            
        except (IndexError or KeyError or ValueError):
            return 0
        
    if (com[0]=="rap"):
        try: 
            if (valid_tip(com[1]) == 1):
                return 1

            elif (valid_tip(com[1]) == 2):
                com[1] = int (com[1])
                return 1
            
        except (IndexError or KeyError or ValueError):
            return 0
    
    if (com[0]=="afis"):
        try: 
            return 1
        except (IndexError or KeyError or ValueError):
            return 0
    
    if (com[0]=="undo"):
        try: 
            return 1
        except (IndexError or KeyError or ValueError):
            return 0
    
    return 0

def ui1 ():
    """
    """
    #test_all()
    c = []
    cb = []
    
    print("Inceput program")
    print("Comenzi posibile: add zi/tip/suma, dell zi, rap tip, afis, undo \n")
    comanda = (input("Dati lista de comenzi: "))
    
    comenzi = comanda.split("; ")
    
    for el in comenzi: 
        el = el.split(" ")
        if valid_comanda(el) == 1:
            
            if (el[0]=="add"):
                
                c.append(ad_nou(el[1], el[2], el[3]))
                
            if (el[0]=="dell"):
                st_zi(c, el[1])
                
            if (el[0]=="rap"):
                rap_sum_tip(c, el[1])
                
            if (el[0]=="afis"):
                afis_big(c, list(range(0, len(c))), 1)
                
            if (el[0]=="undo"):    
                backup(c, cb)
#add 1 0 5; add 3 3 50; add 1 4 23; add 7 2 60; add 14 1 60; add 20 5 68; add 1 3 0; add 9 2 10; add 4 4 12; afis;

ui1()
