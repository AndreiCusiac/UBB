from repository.clientiRepo import clientiRepo
import random

class sort:
    
    def __init__(self, ceva):
        '''
            Functie de initializare a clasei sort
            ceva - ceva de sortat dupa altceva
            
        '''
        
        self.ceva = ceva
    
    def sortCevaQuickSort(self, *, key = lambda x:x.getID(), reverse = True):
        '''
            Functie de sortare ceva
            self - ceva de sortat
            Functia returneaza o lista ce reprezinta lista initiala sortata
        '''
        
        def partitie(self, lista, left, right):
            i = left
            j = right
            p = (left + right) // 2
            
            while i <= j:
                while key(lista[i]) < key(lista[p]): i = i + 1
                while key(lista[p]) < key(lista[j]): j = j - 1
                if i <= j:
                    lista[i], lista[j] = lista[j], lista[i]
                    i = i + 1
                    j = j - 1
                
            return i
            
        def quickSort(self, lista, left, right):
            index = partitie(self, lista, left, right)
            if left < index - 1: quickSort(self, lista, left, index - 1)
            if index < right: quickSort(self, lista, index, right)
            
        lista = self
        quickSort(self, lista, 0, len(lista) - 1)
        if reverse == False: return lista
        return reversed(lista)
    
    def sortCevaGnomeSort(self, key = lambda x:x.getID(), reverse = True):
        '''
            Functie de sortare ceva
            self - ceva de sortat
            Functia returneaza o lista ce reprezinta lista initiala sortata
        '''
        
        lista = self
        
        i = 0
        while i < len(lista):
            if i == 0 or key(lista[i]) >= key(lista[i-1]):
                i = i + 1
            else:
                lista[i], lista[i-1] = lista[i-1], lista[i]
                i = i - 1
        
        if reverse == False: return lista
        return reversed(lista)

import unittest

class TestCaseSort(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testSortQUICK(self):
        '''
            Functie de test a functiei sortClientiDescID
        '''
        
        ceva = random.sample(range(10), 10)
        cevaSortedQuickSort = sort.sortCevaQuickSort(ceva, key = lambda x:x, reverse = False)
        cevaSortedDefault = sorted(ceva, key = lambda x:x, reverse = False)
        for i in range(10):
            self.assertTrue(cevaSortedDefault[i] == cevaSortedQuickSort[i])
                    
        
    def testSortGNOME(self):
        '''
            Functie de test a functiei sortClientiDescID
        '''
        
        ceva = random.sample(range(10), 10)
        cevaSortedQuickSort = sort.sortCevaQuickSort(ceva, key = lambda x:x, reverse = False)
        cevaSortedDefault = sorted(ceva, key = lambda x:x, reverse = False)
        for i in range(10):
            self.assertTrue(cevaSortedDefault[i] == cevaSortedQuickSort[i])

if __name__ == '__main__':
    unittest.main()