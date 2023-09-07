from domain.client import client
from repository.filmeRepo import filmeRepo
from repository.clientiRepo import clientiRepo
from repository.inchirieriRepo import inchirieriRepo

class inchirieriServ:
    
    def __init__(self, inchirieri):
        '''
            Functie de initializare a service-ului pentru inchirieri
            inchirieri - lista de inchirieri
        '''
        
        self.inchirieri = inchirieri
    
    def sortByName(self):
        '''
            Functie de sortare a inchirierilor ce au inchiriat filme dupa nume
            inchirieri - lista de inchirieri
        '''
        
        inchirieriSorted = sorted(self.inchirieri, key = lambda x: x.getClient().getNume(), reverse = False)
        
        return inchirieriSorted
    
    
    
    def getNumarInchirieriByFilme(self, inchirieri, filme):
        '''
            Functie de get a numarului de inchirieri pentru fiecare film
            inchirieri - lista de inchirieri
            filme - lista de filme
        '''
        
        nrInchirieri = [0] * len(filme.getAll())
        
        for i in inchirieri.getAll():
            index = 0
            while (filme.getAll()[index].getTitlu() != i.getFilm().getTitlu()):
                index = index + 1
            nrInchirieri[index] = nrInchirieri[index] + 1
        
        return nrInchirieri
    
    def sortByFilmeNoInchirieri(self, inchirieri, filme):
        '''
            Functie de sortare a inchirierilor dupa numarul de inchirieri ale unui filme
            inchirieri - lista de inchirieri
            filme - lista de filme
        '''
        
        filmeSorted = filmeRepo()
        nrInchirieri = inchirieriServ.getNumarInchirieriByFilme(self, inchirieri, filme)
        
        
        for i in range(len(nrInchirieri)):
            indexCurent = nrInchirieri.index(max(nrInchirieri))
            filmeSorted.addFilm(filme.getAll()[indexCurent].getID(), filme.getAll()[indexCurent].getTitlu(), filme.getAll()[indexCurent].getDescriere(), filme.getAll()[indexCurent].getGen())
            nrInchirieri[indexCurent] = -1
            
        return filmeSorted
    
    
    
    def getNumarInchirieriByClients(self, inchirieri, clienti):
        '''
            Functie de get a numarului de inchirieri pentru fiecare client
            inchirieri - lista de inchirieri
            clienti - lista de clienti
        '''
        
        nrInchirieri = [0] * len(clienti.getAll())
        
        for i in inchirieri.getAll():
            index = 0
            while (clienti.getAll()[index].getNume() != i.getClient().getNume()):
                index = index + 1
            nrInchirieri[index] = nrInchirieri[index] + 1
        
        return nrInchirieri
    
    def sortByClientsNoInchirieri(self, inchirieri, clienti):
        '''
            Functie de sortare a inchirierilor dupa numarul de inchirieri ale unui client
            inchirieri - lista de inchirieri
            clienti - lista de clienti
        '''
        
        clientiSorted = []
        nrInchirieri = inchirieriServ.getNumarInchirieriByClients(self, inchirieri, clienti)
        
        for i in range(len(nrInchirieri)):
            indexCurent = nrInchirieri.index(max(nrInchirieri))
            clientiSorted.append(client(clienti.getAll()[indexCurent].getID(), clienti.getAll()[indexCurent].getNume(), clienti.getAll()[indexCurent].getCNP()))
            nrInchirieri[indexCurent] = -1
            
        return clientiSorted

import unittest

class TestCaseInchirieriServ(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testSortByName(self):
        '''
            Functie de testare a funcitiei sortByName
        '''
        
        testClienti = clientiRepo()
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(2, "Ana Blandiana", 2641234567890)
        
        testFilme = filmeRepo()
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.addFilm(2, "Ana Blandiana", "Ana Blandiana are mere", "Comedy")
        
        testInchirieri = inchirieriRepo()
        testInchirieri.addInchiriere(testClienti.getAll()[0], testFilme.getAll()[0])
        testInchirieri.addInchiriere(testClienti.getAll()[1], testFilme.getAll()[1])
        
        inchirieriSorted = inchirieriServ.sortByName(testInchirieri)
        
        self.assertEqual(inchirieriSorted[0].getClient().getNume(), "Ana Blandiana", "SORT BY NAME")
    
    def testGetNumarInchirieriByFilme(self):
        '''
            Functie de test a functiei getNumarInchirieriByFilme
        '''
        
        testClienti = clientiRepo()
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(2, "Ana Blandiana", 2641234567890)
        testClienti.addClient(3, "Ion Stefanescu", 1234567890123)
        
        testFilme = filmeRepo()
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.addFilm(2, "Ana Blandiana", "Ana Blandiana are mere", "Comedy")
        testFilme.addFilm(3, "Ion", "Ion are mere", "Comedy")
        
        testInchirieri = inchirieriRepo()
        testInchirieri.addInchiriere(testClienti.getAll()[0], testFilme.getAll()[0])
        testInchirieri.addInchiriere(testClienti.getAll()[1], testFilme.getAll()[1])
        testInchirieri.returnareFilm("Ana Blandiana")
        testInchirieri.addInchiriere(testClienti.getAll()[1], testFilme.getAll()[1])
        
        nrInchirieri = inchirieriServ.getNumarInchirieriByFilme(testInchirieri, testInchirieri, testFilme)
        
        self.assertEqual(nrInchirieri[1], 2, "NUMAR INCHIRIERI BY FILME")
    
    def testSortByFilmeNoInchirieri(self):
        '''
            Functie de test a functiei sortByFilmeNoInchirieri
        '''
        
        testClienti = clientiRepo()
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(2, "Ana Blandiana", 2641234567890)
        testClienti.addClient(3, "Ion Stefanescu", 1234567890123)
        
        testFilme = filmeRepo()
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.addFilm(2, "Ana Blandiana", "Ana Blandiana are mere", "Comedy")
        testFilme.addFilm(3, "Ion", "Ion are mere", "Comedy")
        
        testInchirieri = inchirieriRepo()
        testInchirieri.addInchiriere(testClienti.getAll()[0], testFilme.getAll()[0])
        testInchirieri.addInchiriere(testClienti.getAll()[1], testFilme.getAll()[1])
        testInchirieri.returnareFilm("Ana Blandiana")
        testInchirieri.addInchiriere(testClienti.getAll()[1], testFilme.getAll()[1])
        
        filmeSorted = inchirieriServ.sortByFilmeNoInchirieri(testClienti, testInchirieri, testFilme)
        
        self.assertEqual(filmeSorted.getAll()[0].getTitlu(), "Ana Blandiana", "NUMAR INCHIRIERI FILME")
    
    def testGetNumarInchirieriByClients(self):
        '''
            Functie de test a functiei getNumarInchirieriByClients
        '''
        
        testClienti = clientiRepo()
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(2, "Ana Blandiana", 2641234567890)
        testClienti.addClient(3, "Ion Stefanescu", 1234567890123)
        
        testFilme = filmeRepo()
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.addFilm(2, "Ana Blandiana", "Ana Blandiana are mere", "Comedy")
        testFilme.addFilm(3, "Ion", "Ion are mere", "Comedy")
        
        testInchirieri = inchirieriRepo()
        testInchirieri.addInchiriere(testClienti.getAll()[0], testFilme.getAll()[0])
        testInchirieri.addInchiriere(testClienti.getAll()[1], testFilme.getAll()[1])
        testInchirieri.returnareFilm("Ana Blandiana")
        testInchirieri.addInchiriere(testClienti.getAll()[1], testFilme.getAll()[1])
        
        nrInchirieri = inchirieriServ.getNumarInchirieriByClients(testInchirieri, testInchirieri, testClienti)
        
        self.assertEqual(nrInchirieri[1], 2, "GET NUMAR INCHIRIERI BY CLIENTS")
    
    def testSortByClientsNoInchirieri(self):
        '''
            Functie de test a functiei sortByClientsNoInchirieri
        '''
        
        testClienti = clientiRepo()
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(2, "Ana Blandiana", 2641234567890)
        testClienti.addClient(3, "Ion Stefanescu", 1234567890123)
        
        testFilme = filmeRepo()
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.addFilm(2, "Ana Blandiana", "Ana Blandiana are mere", "Comedy")
        testFilme.addFilm(3, "Ion", "Ion are mere", "Comedy")
        
        testInchirieri = inchirieriRepo()
        testInchirieri.addInchiriere(testClienti.getAll()[0], testFilme.getAll()[0])
        testInchirieri.addInchiriere(testClienti.getAll()[1], testFilme.getAll()[1])
        testInchirieri.returnareFilm("Ana Blandiana")
        testInchirieri.addInchiriere(testClienti.getAll()[1], testFilme.getAll()[1])
        
        clientiSorted = inchirieriServ.sortByClientsNoInchirieri(testClienti, testInchirieri, testClienti)
        
        self.assertEqual(clientiSorted[0].getNume(), "Ana Blandiana", "SORT BY CLIENTS INCHIRIERI")

'''
unittest.main()
'''