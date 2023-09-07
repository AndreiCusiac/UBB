nr = 10
while (nr!=0):

    nota = input("\n Dati nota: ")
    
    if not (type(nota) == int or type(nota) == str or type(nota) == float):
        print("Nota data poate fi doar de tip string, int sau float!")
        
    try:
        int(nota)
    except ValueError:
        try:
            float(nota)
        except ValueError:
            print("Nota data nu poate fi interpretata drept calificativ!")
        
    if (float(nota) < 1):
        print("Nota data este prea mica (nota minima acceptata este 1)!")
        
    if (float(nota) > 10):
        print("Nota data este prea mare (nota maxima acceptata este 10)!")
        
    nr -= 1
