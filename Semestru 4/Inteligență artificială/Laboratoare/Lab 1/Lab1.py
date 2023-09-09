# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.
import re
import math

def teste1():
    sentence = 'Ana are mere rosii si galbene'
    assert returnLastWord(sentence) == 'si'

    sentence = 'aaa -=1 aaa -9813-0.. a aa aaa'
    assert returnLastWord(sentence) == 'aaa'

    sentence = 'cuvant'
    assert returnLastWord(sentence) == 'cuvant'

    sentence = 'cuvan cuvant cuvan'
    assert returnLastWord(sentence) == 'cuvant'

def citire1():
    propozitiaMea = input("Dati o propozitie (fara ghilimele): ")
    # propozitiaMea = ''
    print("S-a citit: ", propozitiaMea, "\n")
    return propozitiaMea

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

    print("Problema 1.")

    teste1()
    val = citire1()
    print("Ultimul cuvant alfabetic este: ", returnLastWord(val))

    print("\nProblema 2.")

    teste2()
    firstPoint = citire2()
    secondPoint = citire2()
    print("Distanta dintre cele 2 puncte este: ", returnDistance(firstPoint, secondPoint))

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
