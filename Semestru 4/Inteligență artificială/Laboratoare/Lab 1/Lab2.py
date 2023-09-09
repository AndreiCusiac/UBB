# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
import re
import math

def teste3():
    firstVector = ['1', '0', '2', '0', '3']
    secondVector = ['1', '2', '0', '3', '1']
    assert returnProduct(firstVector, secondVector) == 5.0

    firstVector = ['1', '0']
    secondVector = ['0', '0']
    assert returnProduct(firstVector, secondVector) == 1.0

    firstVector = ['2', '2']
    secondVector = ['2', '5']
    assert returnProduct(firstVector, secondVector) == 3.0

def citire3():
    coordonate = input("Dati dimensiunile vectorului (separate prin spatiu): ")

    listCoordonate = list(coordonate.split(" "))
    print("S-a citit: ", listCoordonate, "\n")

    return listCoordonate

def returnLastWord(sentence):
    lastWord = re.sub("[^a-zA-Z0-9]", " ", sentence).split()
    #lastWord.sort()
    #return (lastWord.pop(len(lastWord) - 1))

    compare = lastWord[0]
    for i in lastWord:
        if (i > compare):
            compare = i

    return compare


def teste2():
    firstPoint = ['1', '5']
    secondPoint = ['4', '1']
    assert returnDistance(firstPoint, secondPoint) == 5.0

    firstPoint = ['1', '0']
    secondPoint = ['0', '0']
    assert returnDistance(firstPoint, secondPoint) == 1.0

    firstPoint = ['2', '2']
    secondPoint = ['2', '5']
    assert returnDistance(firstPoint, secondPoint) == 3.0

def citire2():
    coordonate = input("Dati coordonatele punctului (separate prin spatiu): ")
    listCoordonate = list(coordonate.split(" "))
    print("S-a citit: ", listCoordonate, "\n")
    return listCoordonate

def returnDistance(firstPoint, secondPoint):

    sum = 0

    for i in range(len(firstPoint)):
        #print(secondPoint[i])
        #print(firstPoint[i])
        sum += pow(float(secondPoint[i]) - float(firstPoint[i]), 2)

    return math.sqrt(sum)

if __name__ == '__main__':

    print("Problema 3.")

    teste3()
    firstVector = citire3()
    secondVector = citire3()
    print("Ultimul cuvant alfabetic este: ", returnLastWord(val))

    print("\nProblema 2.")

    teste2()
    firstVector = citire2()
    secondVector = citire2()
    print("Distanta dintre cele 2 puncte este: ", returnDistance(firstPoint, secondPoint))

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
 