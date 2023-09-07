from domain.client import client
from repository.clientiRepo import clientiRepo
import random
from random import randint

class clientiServ:
    
    def __init__(self, clienti):
        '''
            Functie de initializare a service-ului pentru clienti
            inchirieri - lista de clienti
        '''
        
        self.clienti = clienti
    
    def sortClientiDescID(self):
        '''
            Functie de sortare descrescatoare a clientilor dupa ID
            self - lista de clienti de sortat
            Functia returneaza o lista ce reprezinta lista sortata initial
        '''
    
        listSorted = sorted(self.clienti, key = lambda x: x.getID(), reverse = True)
        return listSorted
    
    def genRandomClienti(self, n, i):
        '''
            Functie de generare aleatorie a clientilor
            self - lista de clienti
            n - numarul de clienti de adaugat
        '''
            
        if i == n: return
                
        maxID = 0
        for c in self.clienti.getAll():
            if str(c.getID()) > str(maxID):
                maxID = int(c.getID())
                
        lg = randint(1, 3)
        nume = [0] * lg
        lg = range(lg)
        for j in lg:
            nume[j] = random.choice('ab')
                
        CNP = 0
        for j in range(13):
            CNP = CNP * 10 + randint(1, 9)
            
        self.clienti.addClient(maxID + 1, ''.join(nume), CNP)
        
        clientiServ.genRandomClienti(self, n, i + 1)
    
    def sumaIDsameName(self):
        '''
            Functie de aflare a sumei ID-urilor celor care au acelasi nume
            self - lista de clienti
        '''
        
        def isAlready(array, name):
            for n in array:
                if n == name:
                    return 1
            return 0
        
        array = []
        
        for c in self.clienti:
            if isAlready(array, c.getNume()) == False:
                array.append(c.getNume())
        
        suma = [0] * len(array)
        
        for i in range(len(array)):
            for c in self.clienti:
                if array[i] == c.getNume():
                    suma[i] = suma[i] + int(c.getID())
        
        rezultat = []
        for i in range(len(array)):
            rezultat.append([array[i], suma[i]])
        
        return rezultat

import unittest

class TestCaseClientiServ(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testSortClientiDescID(self):
        '''
            Functie de test a functiei sortClientiDescID
        '''
        
        testClienti = clientiRepo()
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(2, "Ana Blandiana", 2641234567890)
        
        testClientiSorted = clientiServ.sortClientiDescID(testClienti)
        
        self.assertEqual(testClientiSorted[0].getID(), 2, "SORTARE CLIENTI DESCRESCATOR DUPA ID")
    
    def testSumaIDsameName(self):
        '''
            Functie de test a functiei sumaIDsameName
        '''
        
        testClienti = clientiRepo()
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(4, "Ana Blandiana", 2981008226715)
        testClienti.addClient(2, "Ion Ionescu", 2641234567890)
        
        rezultat = clientiServ.sumaIDsameName(testClienti)
        
        self.assertEqual(rezultat[0], ["Ion Ionescu", 3], "SUMA ID-URILOR CLIENTILOR CU ACELASI NUME")

'''
unittest.main()
'''