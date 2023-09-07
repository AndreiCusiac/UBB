def do_back (a, b):
    """
    functia asigura back-up-ul in lista b a listei date in a, prin copierea listei a in b
    a, b - liste
    """
    d = a.copy()
    b.append(d)

def backup (c, cb):
    if (len(cb) != 0):
        d = cb.pop()
        print ("d este: \n", d)
        c.clear()
        for el in d:
            c.append(el)
        print("Ati ales optiunea Back-up. Lista de cheltuieli a revenit la starea dinaintea ultimei operatii efectuate")

    else:
        print("Nu se mai pot efectua operatii de back-up")
    print("\n")


cb = []
c = [{"zi": 2, "tip": 3, "suma": 20}]

do_back(c, cb)


c = [{"zi": 2, "tip": 3, "suma": 20}, {"zi": 4, "tip": 5, "suma": 30}]

do_back(c, cb)

c = [{"zi": 2, "tip": 3, "suma": 20}, {"zi": 4, "tip": 5, "suma": 30}, {"zi": 1, "tip": 1, "suma": 45}]

do_back(c, cb)

print ("c este: \n", c)

print("cb este: \n", cb)

c.pop(1)
print("c este: \n", c)

print("cb este: \n", cb)

print("Se face backup")

backup(c, cb)

print("cb este: \n", cb)

print ("c este: \n", c)

