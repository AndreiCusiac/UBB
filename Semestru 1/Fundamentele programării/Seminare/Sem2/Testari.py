#Verifica daca un numar e prim
def testePrim():
    assert ePrim(0)==False
    assert ePrim(1)==False
    assert ePrim(7)
    assert ePrim(2)==True
    assert ePrim(8)==False
    assert ePrim(-7)==False
    assert ePrim(23)==True
    assert ePrim(21)==False

def ePrim(nr):
    """
        Verifica daca numarul e prim
        nr - nr intreg
        returneaza True daca e prim, nr are are???
    """
    if nr<2:
        return False
    for div in range(2, nr//2+1):
        if nr%div==0:
            return False
    return True

testePrim()


def testUrmPrim():
    assert urmPrim(7)==11
    assert urmPrim(8)==11
    assert urmPrim(-7)==2
    assert urmPrim(1)==2
    assert urmPrim(21)==23

def urmPrim(nr):
    """
    Cauta urmatorul numar prim
    nr - nr intreg
    returneaza nr intre, prim, >0, >n, cel mai mic de acest fel
    """
    rez = nr+1
    while not ePrim(rez):
        rez+=1
    return rez  

testUrmPrim()

def ui():
    while True:
        nr = int (input("Dati nr (0 pt iesire): "))
        if nr=='0':
            break
        print("Urmatorul prim: ",urmPrim(nr))
    print("byby")

ui()
