def test_all():
    """
    functia aduna la un loc apeluri catre toate functiile de tip test
    returneaza un mesaj de eroare atunci cand se sesizeaza nefunctionalitatea vreunei functii de tip test
    """
    test_verif_float()
    test_verif_int()
    test_valid_zi()
    test_valid_tip()
    test_valid_sum()
    test_valid_opt()
    test_elim_tip()
    test_elim_sum()
    test_reun_liste()
    test_verif_cond_lista()
    test_sum_tot()
    test_index_sum_max()
    test_elem_funct_index()
    #print(valid_zi("31"))
    #test_do_back()
    #print(valid_zi(31))
    #print(valid_zi(32))
    print("\n")

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

def test_elem_funct_index():
    d = []
    e = []

    d.clear
    e.clear
    elem_funct_index([0, 1, 3], get_test(), e)
    assert e == [{"zi": 2, "tip": 3, "suma": 20}, {"zi": 4, "tip": 5, "suma": 30}, {"zi": 2, "tip": 3, "suma": 60}]

    d.clear
    e.clear
    elem_funct_index([], get_test(), e)
    assert e == []

    d.clear
    e.clear
    elem_funct_index([6], get_test(), e)
    assert e == []

    d.clear
    e.clear
    elem_funct_index([3, 4], get_test(), e)
    assert e == [{"zi": 2, "tip": 3, "suma": 60}, {"zi": 16, "tip": 4, "suma": 50}]
    

def do_back (a, b):
    """
    functia asigura back-up-ul in lista b a listei date in a, prin copierea listei a in b
    a, b - liste
    """
    d = a.copy()
    b.append(d)

def test_do_back():
    cb = [[{"zi": 2, "tip": 3, "suma": 20}], [{"zi": 2, "tip": 3, "suma": 20}, {"zi": 4, "tip": 5, "suma": 30}], [{"zi": 2, "tip": 3, "suma": 20}, {"zi": 4, "tip": 5, "suma": 30},{"zi": 1, "tip": 1, "suma": 45}]]
    c = [{"zi": 2, "tip": 3, "suma": 20}, {"zi": 4, "tip": 5, "suma": 30},{"zi": 1, "tip": 1, "suma": 45}, {"zi": 2, "tip": 3, "suma": 60}, {"zi": 16, "tip": 4, "suma": 50}, {"zi": 20, "tip": 3, "suma": 26}]
    
    backup(c, cb)
    assert c == [{"zi": 2, "tip": 3, "suma": 20}, {"zi": 4, "tip": 5, "suma": 30},{"zi": 1, "tip": 1, "suma": 45}]
    
    cb = []
    c = [{"zi": 2, "tip": 3, "suma": 20}, {"zi": 4, "tip": 5, "suma": 30},{"zi": 1, "tip": 1, "suma": 45}, {"zi": 2, "tip": 3, "suma": 60}, {"zi": 16, "tip": 4, "suma": 50}, {"zi": 20, "tip": 3, "suma": 26}]
    
    backup(c, cb)
    assert c == [{"zi": 2, "tip": 3, "suma": 20}, {"zi": 4, "tip": 5, "suma": 30},{"zi": 1, "tip": 1, "suma": 45}, {"zi": 2, "tip": 3, "suma": 60}, {"zi": 16, "tip": 4, "suma": 50}, {"zi": 20, "tip": 3, "suma": 26}]
    
    cb = [[{"zi": 2, "tip": 3, "suma": 20}]]
    c = [{"zi": 2, "tip": 3, "suma": 20}, {"zi": 4, "tip": 5, "suma": 30},{"zi": 1, "tip": 1, "suma": 45}, {"zi": 2, "tip": 3, "suma": 60}, {"zi": 16, "tip": 4, "suma": 50}, {"zi": 20, "tip": 3, "suma": 26}]
    
    backup(c, cb)
    assert c == [{"zi": 2, "tip": 3, "suma": 20}]

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

def test_verif_float():
    assert (verif_float(2)) == 1
    assert (verif_float(-9)) == 1
    assert (verif_float(2.5)) == 1
    assert (verif_float("2")) == 1
    assert (verif_float("mere")) == 0
    assert (verif_float(-3.2)) == 1
    assert (verif_float([2])) == 0
    assert (verif_float("-3.2")) == 1

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

def test_verif_int():
    assert (verif_int(2)) == 1
    assert (verif_int(2.5)) == 0
    assert (verif_int("2")) == 1
    assert (verif_int(3.0)) == 1
    assert (verif_int("mere")) == 0
    assert (verif_int(-3.2)) == 0
    assert (verif_int([2])) == 0
    assert (verif_int("-3.2")) == 0
    assert (verif_int("31")) == 1
    assert (verif_int("-2")) == 1
    assert (verif_int("7")) == 1
    assert (verif_int(-4)) == 1

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

def test_valid_opt():
    assert (valid_opt(5, "mere")) == 0
    assert (valid_opt(5, "2")) == 2
    assert (valid_opt(5, "7")) == 0
    assert (valid_opt(5, 2)) == 1
    assert (valid_opt(5, 2.5)) == 0
    assert (valid_opt(5, -9)) == 0
    assert (valid_opt(5, 5)) == 1
    assert (valid_opt(5, 6)) == 0
    assert (valid_opt(5, 0)) == 1

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

def test_valid_zi():
    assert (valid_zi("mere")) == 0
    assert (valid_zi(20.9)) == 0 
    assert (valid_zi(-2)) == 0 
    assert (valid_zi([7])) == 0
    assert (valid_zi(32)) == 0
    assert (valid_zi(31)) == 1
    assert (valid_zi("7")) == 2
    assert (valid_zi("31")) == 2
    assert (valid_zi("32")) == 0
    assert (valid_zi(0)) == 0
    

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

def test_valid_tip():
    assert (valid_tip("mere")) == 0
    assert (valid_tip(20.9)) == 0 
    assert (valid_tip(-2)) == 0 
    assert (valid_tip(6)) == 0
    assert (valid_tip(0)) == 0
    assert (valid_tip([3])) == 0
    assert (valid_tip(32)) == 0
    assert (valid_tip(3)) == 1
    assert (valid_tip("4")) == 2 

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
    
def test_valid_sum():
    assert (valid_sum("mere")) == 0
    assert (valid_sum(20.9)) == 1 
    assert (valid_sum(-2)) == 0 
    assert (valid_sum([7])) == 0
    assert (valid_sum(6)) == 1
    assert (valid_sum(31)) == 1
    assert (valid_sum(0)) == 0
    assert (valid_sum("31.29")) == 2

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

def test_reun_liste():
    e = []

    e.clear()
    e = reun_liste([0, 1, 2, 4], [1, 2, 3, 5])
    assert e == [1, 2]

    e.clear()
    e = reun_liste([0, 1, 2, 4], [3])
    assert e == []

    e.clear()
    e = reun_liste([], [3, 4])
    assert e == []

    e.clear()
    e = reun_liste([3, 4, 5], [3, 4])
    assert e == [3, 4]

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

def test_verif_cond_lista():
    d = []
    
    d.clear()
    verif_cond_lista(get_test(), d, get_index_tip(), 1, 4)
    assert d == [4]

    d.clear()
    verif_cond_lista(get_test(), d, get_index_tip(), 1, 2)
    assert d == []

    d.clear()
    verif_cond_lista(get_test(), d, get_index_sum(), 1, 30)
    assert d == [1]

    d.clear()
    verif_cond_lista(get_test(), d, get_index_sum(), 0, 40)
    assert d == [0, 1, 5]

    d.clear()
    verif_cond_lista(get_test(), d, get_index_zi(), 0, 50)
    assert d == [0, 1, 2, 3, 4, 5]

    d.clear()
    verif_cond_lista(get_test(), d, get_index_zi(), 2, 50)
    assert d == []

    d.clear()
    verif_cond_lista(get_test(), d, get_index_tip(), 0, 6)
    assert d == [0, 1, 2, 3, 4, 5]

def test_sum_tot():
    assert (sum_tot(get_test(), [1, 2, 3]) == 135)
    assert (sum_tot(get_test(), []) == 0)
    assert (sum_tot(get_test(), [6]) == 0)
    assert (sum_tot(get_test(), [1]) == 30)

def test_index_sum_max():
    assert (index_sum_max([]) == -1)
    assert (index_sum_max(get_test()) == 3)

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

def ad_nou():
    """
    functia permite introducerea in liste predefinite a unei noi cheltuieli, cu toate atributele sale (zi, tip, suma)
    """
    print("\n")
    print("Pentru a adauga o noua cheltuiala trebuie sa specificati: ziua, suma si tipul")
    z=(cit_zi())
    t=(cit_tip())
    s=(cit_sum())
    return {"zi": z, "tip": t, "suma": s}

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
    
    print("Toate cheltuielile de aceasta suma sunt: ")
    
    verif_cond_lista(c, d, get_index_sum(), 1, s)


def identif_cheltuiala(c):
    """
    functia permite identificarea unei cheltuieli existente in vederea modificarii acesteia
    d, e, f, test - liste, contin indecsi 
    """

    d = []
    e = []
    f = []
    test = []
    
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

def modif(c):
    """
    functia  permite modificarea unei cheltuieli deja existente
    i - numar natural, indexul cheltuielii ce se doreste a fi modificata
    """

    i = 0
    
    i = identif_cheltuiala(c)

    print("Cheltuiala initiala a fost identificata cu succes! \n")
    
    print("Dati atributele (ziua, tipul si suma) noi cheltuieli: ")
    c[i] = ad_nou()

    print("\n")

def adaugare (c, cb, nr_op):
    """
    functia permite operatii de adaugare/ modificare a cheltuielilor existente, in functie de optiunea citita de la tastatura
    c, cb - liste, cheltuieli + back-up
    nr_op - numar natural, indica numarul de optiuni accesibile utilizatorului
    """
    
    print("\n")
    print("Ati ales optiunea Adaugare. Doriti sa: \n 1. Adaugati o cheltuiala noua - tasta 1 \n 2. Actualizati o cheltuiala existenta - tasta 2 \n 0. Reveniti la meniul principal - tasta 0")
    o = cit_opt(nr_op)

    while (o!=0):
        if (o==1):
            do_back(c, cb)
            c.append(ad_nou())
            print("Cheltuiala ", len(c), " a fost adaugata cu succes! \n")
            
        elif (o==2):
            if (len(c) != 0): 
                do_back(c, cb)
                modif(c)
                print("Cheltuiala a fost modificata cu succes!")
            else:
                print("\n Atentie! Lista cheltuielilor este momentan vida. Nu se pot efectua operatii asupra ei. \n")
                
        else:
            print("Optiunea selectata nu este valida. Introduceti o noua optiune.")

        print("\n Doriti sa: \n 0. Reveniti la meniul principal - tasta 0 \n 1. Adaugati o cheltuiala noua - tasta 1 \n 2. Actualizati o cheltuiala existenta - tasta 2")
        o = cit_opt(nr_op)
        
    print("\n")

def st_zi(c):
    """
    functia sterge toate cheltuielile dintr-o zi data 

    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea 
    z - numar natural, citit
    d - lista, contine indecsi
    """

    print("\n")
    print("Alegeti ziua pentru care doriti sa stergeti chelutilelile")
    z = cit_zi()

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

def stergere (c, cb, nr_op):
    """
    functia permite operatii de adaugare/ modificare a cheltuielilor existente, in functie de optiunea citita de la tastatura
    c, cb - liste, cheltuieli + back-up
    nr_op - numar natural, indica numarul de optiuni accesibile utilizatorului
    """
    
    print("\n")
    print("Ati ales optiunea Stergere. Doriti sa: \n 1. Stergeti cheluielile pentru o zi data - tasta 1 \n 2. Stergeti cheltuielile pentru un interval de timp dat - tasta 2 \n 3. Stergeti cheltuielile de un anumit tip - tasta 3 \n 0. Reveniti la meniul principal - tasta 0")
    o = cit_opt(nr_op)

    while (o!=0):
        if (o==1):
            do_back(c, cb)
            st_zi(c)
            
        elif (o==2):
            do_back(c, cb)
            st_interval_zi(c)

        elif (o==3):
            do_back(c, cb)
            st_tip(c)
            
        else:
            print("Optiunea selectata nu este valida. Introduceti o noua optiune.")

        print("Doriti sa: \n 1. Stergeti cheluielile pentru o zi data - tasta 1 \n 2. Stergeti cheltuielile pentru un interval de timp dat - tasta 2 \n 3. Stergeti cheltuielile de un anumit tip - tasta 3 \n 0. Reveniti la meniul principal - tasta 0")
        o = cit_opt(nr_op)
        
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

def cautare (c, nr_op):
    """
    functia permite operatii de cautare a cheltuielilor existente, in functie de optiunea citita de la tastatura
    c - lista, cheltuieli 
    nr_op - numar natural, indica numarul de optiuni accesibile utilizatorului
    """
    
    print("\n")
    print("Ati ales optiunea Cautare. Doriti sa: \n 1. Tipariti cheltuielile mai mari decat o suma data - tasta 1 \n 2. Tipariti cheltuielile efectuate inainte de o zi si mai mici decat o suma anume - tasta 2 \n 3. Tipariti cheluielile de un anumit tip - tasta 3 \n 0. Reveniti la meniul principal - tasta 0")
    o = cit_opt(nr_op)

    while (o!=0):
        if (o==1):
            tip_sum(c)
            
        elif (o==2):
            tip_zi_sum(c)
            
        elif (o==3):    
            tip_tip(c)

        else:    
            print("Optiunea selectata nu este valida. Introduceti o noua optiune.")

        print("Ati ales optiunea Cautare. Doriti sa: \n 1. Tipariti cheltuielile mai mari decat o suma data - tasta 1 \n 2. Tipariti cheltuielile efectuate inainte de o zi si mai mici decat o suma anume - tasta 2 \n 3. Tipariti cheluielile de un anumit tip - tasta 3 \n 0. Reveniti la meniul principal - tasta 0")
        o = cit_opt(nr_op)
        
    print("\n")

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

def rap_sum_tip(c):
    """
    functia calculeaza suma totala pentru au anumit tip de cheltuiala
    returneaza suma ceruta, altfel returneaza un mesaj de eroare in cazul in care nu exista cheltuieli care indeplinesc conditia data
    c - lista, cuprinde cheltuielile cu care se face cautarea si afisarea
    d - lista, contine indecsi
    t - numar intreg, citit
    s - numar real, suma ceruta
    """

    print("\n")
    print("Alegeti tipul de cheltuiala: \n")
    t = cit_tip()
    d = []
    s = 0
    
    verif_cond_lista(c, d, get_index_tip(), 1, t)
    s = sum_tot(c, d)
    
    print("Suma totala este: ")
    
    if s != 0:
        print(s)
        
    else:
        print("Nu au fost gasite astfel de cheltuieli")

    print("\n")

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

def raportare (c, nr_op):
    """
    functia permite operatii de raportare a cheltuielilor existente, in functie de optiunea citita de la tastatura
    c - lista, cheltuieli
    nr_op - numar natural, indica numarul de optiuni accesibile utilizatorului
    """
    
    print("\n")
    print("Ati ales optiunea Raportare. Doriti sa: \n 1. Tipariti suma totala pentru un anumit tip de cheltuiala - tasta 1 \n 2. Gasiti ziua in care s-a efectuat cheltuiala de suma maxima - tasta 2 \n 3. Tipariti cheltuielile de o anumita suma - tasta 3 \n 4. Tipariti cheltuielile sortate dupa tip - tasta 4 \n 0. Reveniti la meniul principal - tasta 0")
    o = cit_opt(nr_op)

    while (o!=0):
        if (o==1):
            rap_sum_tip(c)
            
        elif (o==2):
            rap_zi_sum_max(c)
            
        elif (o==3):    
            rap_sum(c)

        elif (o==4):
            rap_tip(c)

        else:    
            print("Optiunea selectata nu este valida. Introduceti o noua optiune.")

        print("Ati ales optiunea Raportare. Doriti sa: \n 1. Tipariti suma totala pentru un anumit tip de cheltuiala - tasta 1 \n 2. Gasiti ziua in cu cheltuieli maxime - tasta 2 \n 3. Tipariti cheltuielile de o anumita suma - tasta 3 \n 4. Tipariti cheltuielile sortate dupa tip - tasta 4 \n 0. Reveniti la meniul principal - tasta 0")
        o = cit_opt(nr_op)
        
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

def test_elim_tip():
    d = []
    
    d.clear()
    verif_cond_lista(get_test(), d, get_index_tip(), 1, 5)
    assert d == [1]

    d.clear()
    verif_cond_lista(get_test(), d, get_index_tip(), 1, 4)
    assert d == [4]

    d.clear()
    verif_cond_lista(get_test(), d, get_index_tip(), 1, 2)
    assert d == []

    d.clear()
    verif_cond_lista(get_test(), d, get_index_tip(), 1, 3)
    assert d == [0, 3, 5]
    

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

def test_elim_sum():
    d = []
    
    d.clear()
    verif_cond_lista(get_test(), d, get_index_sum(), 0, 20)
    assert d == []

    d.clear()
    verif_cond_lista(get_test(), d, get_index_sum(), 0, 30)
    assert d == [0, 5]

    d.clear()
    verif_cond_lista(get_test(), d, get_index_sum(), 0, 40)
    assert d == [0, 1, 5]

    d.clear()
    verif_cond_lista(get_test(), d, get_index_sum(), 0, 50)
    assert d == [0, 1, 2, 5]

def filtrare (c, nr_op):
    """
    functia permite operatii de raportare a cheltuielilor existente, in functie de optiunea citita de la tastatura
    c - lista, cheltuieli
    nr_op - numar natural, indica numarul de optiuni accesibile utilizatorului
    """
    
    print("\n")
    print("Ati ales optiunea Filtrare. Doriti sa: \n 1. Eliminati cheltuielile de un anumit tip - tasta 1 \n 2. Eliminati cheltuielile mai mici decat o anume suma \n 0. Reveniti la meniul principal - tasta 0")
    o = cit_opt(nr_op)

    while (o!=0):
        if (o==1):
            elim_tip(c)
            
        elif (o==2):
            elim_sum(c)
            
        else:    
            print("Optiunea selectata nu este valida. Introduceti o noua optiune.")

        print("Ati ales optiunea Filtrare. Doriti sa: \n 1. Eliminati cheltuielile de un anumit tip - tasta 1 \n 2. Eliminati cheltuielile mai mici decat o anume suma \n 0. Reveniti la meniul principal - tasta 0")
        o = cit_opt(nr_op)
    
    print("\n")

def backup (c, cb):
    if (len(cb) != 0):
        d = cb.pop()
        #print("Lungimea actuala a lui cb este: ", len(cb))
        #print ("d este: \n", d)
        print("Ati ales optiunea Back-up. Lista anterioara era: ", c, "\n")
        c.clear()
        for el in d:
            c.append(el)
        print("Lista de cheltuieli a revenit la starea dinaintea ultimei operatii efectuate. Lista actuala este: ")
        print(c, "\n")

    else:
        print("Nu se mai pot efectua operatii de back-up")
    print("\n")

def ui ():
    test_all()
    
    c = []
    cb = []
    
    print("Inceput program")
    print("Doriti sa faceti o operatie de: \n 1. Adaugare - tasta 1 \n 2. Stergere - tasta 2 \n 3. Cautare - tasta 3 \n 4. Raportare - tasta 4 \n 5. Filtrare - tasta 5 \n 6. Back-up - tasta 6 \n 7. Oprire program - tasta 0")

    o = cit_opt(6)
    
    while (o!=0): 
        if (o==1):
            adaugare(c, cb, 2)
            print("Lista actuala este: ")
            print(c, "\n")

        elif (o==2):
            if (len(c) != 0): 
                stergere(c, cb, 3)
                print("Lista actuala este: ")
                print(c, "\n")
            else:
                print("\n Atentie! Lista cheltuielilor este momentan vida. Nu se pot efectua operatii asupra ei. \n")
        
        elif (o==3):
            if (len(c) != 0): 
                cautare(c, 3)
            else:
                print("\n Atentie! Lista cheltuielilor este momentan vida. Nu se pot efectua operatii asupra ei. \n")
    
        elif (o==4):
            if (len(c) != 0): 
                raportare(c, 4)
            else:
                print("\n Atentie! Lista cheltuielilor este momentan vida. Nu se pot efectua operatii asupra ei. \n")

        elif (o==5):
            if (len(c) != 0): 
                filtrare(c, 2)
            else:
                print("\n Atentie! Lista cheltuielilor este momentan vida. Nu se pot efectua operatii asupra ei. \n")

        elif (o==6):
            if (len(c) != 0): 
                backup(c, cb)
            else:
                print("\n Atentie! Lista cheltuielilor este momentan vida. Nu se pot efectua operatii asupra ei. \n")

        else:
            print("Optiunea selectata nu este valida. Introduceti o noua optiune.")   
        
        print("Doriti sa faceti o operatie de: \n 1. Adaugare - tasta 1 \n 2. Stergere - tasta 2 \n 3. Cautare - tasta 3 \n 4. Raportare - tasta 4 \n 5. Filtrare - tasta 5 \n 6. Back-up - tasta 6 \n 7. Oprire program - tasta 0")
        print("\n")

        o = cit_opt(6)

    print("Sfarsit program")

ui()

