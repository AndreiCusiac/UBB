from domain.client import client
from domain.film import film
from domain.inchiriere import inchiriere

class inchirieriRepo:
    
    def __init__(self):
        '''
            Functie de initializare a clasei inchirieri
            self - obiectul inchirieri
        '''
        
        self.inchirieri = []
    
    def addInchiriere(self, client, film):
        '''
            Functie de adaugare inchiriere in lista de inchirieri
            client - clientul care inchiriaza
            film - filmul inchiriat
        '''
        
        i = inchiriere(client, film)
        self.inchirieri.append(i)
        
    def returnareFilm(self, titluFilm):
        '''
            Functie de marcare a unui film ca returnat
            self - lista de inchirieri
            film - filmul adus inapoi
        '''
        
        for i in reversed(self.inchirieri):
            if i.getFilm().getTitlu() == titluFilm:
                i.setReturnat()
                break
    
    def findByClient(self, numeClient):
        '''
            Functie de returnare a tuturor inchirierilor facute de un client
            self - lista de inchirieri
            client - clientul dorit
        '''
        
        listFilme = inchirieriRepo()
        
        for i in self.inchirieri:
            if i.getClient().getNume() == numeClient:
                listFilme.addInchiriere(i.getClient(), i.getFilm())
        
        return listFilme
    
    def findByFilm(self, titluFilm):
        '''
            Functie de returnare a tuturor inchirierilor ale unui film
            self - lista de inchirieri
            film - filmul dorit
        '''
        
        listClienti = inchirieriRepo()
        
        for i in self.inchirieri:
            if i.getFilm().getTitlu() == titluFilm:
                listClienti.addInchiriere(i.getClient(), i.getFilm())
        
        return listClienti
    
    def getAll(self):
        '''
            Functie ce returneaza toate inchirierile
            self - lista de inchirieri
        '''
        
        return self.inchirieri

import unittest

class TestCaseInchirieriRepo(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testAddInchiriere(self):
        '''
            Functie de test a functiei addInchiriere
        '''
        
        testInchirieri = inchirieriRepo()
        c = client(1, "Ion Ionescu", 1981008226715)
        f = film(1, "Ana", "Ana are mere", "Horror")
        testInchirieri.addInchiriere(c, f)
        
        assert testInchirieri.getAll()[0].getClient() == c
        assert testInchirieri.getAll()[0].getFilm() == f
    
    def testReturnareFilm(self):
        '''
            Functie de test a functiei returnareFilm
        '''
        
        testInchirieri = inchirieriRepo()
        c = client(1, "Ion Ionescu", 1981008226715)
        f = film(1, "Ana", "Ana are mere", "Horror")
        testInchirieri.addInchiriere(c, f)
        
        assert testInchirieri.getAll()[0].returnat == False
        
        testInchirieri.returnareFilm("Ana")
        
        assert testInchirieri.getAll()[0].returnat == True
    
    def testFindByClient(self):
        '''
            Functie de test a functiei findByClient
        '''
        
        testInchirieri = inchirieriRepo()
        c = client(1, "Ion Ionescu", 1981008226715)
        f = film(1, "Ana", "Ana are mere", "Horror")
        testInchirieri.addInchiriere(c, f)
        
        listFilmeFound = testInchirieri.findByClient(c.getNume())
        
        assert listFilmeFound.getAll()[0].getFilm().getTitlu() == "Ana"
    
    def testFindByFilm(self):
        '''
            Functie de test a functiei findByFilm
        '''
        
        testInchirieri = inchirieriRepo()
        c = client(1, "Ion Ionescu", 1981008226715)
        f = film(1, "Ana", "Ana are mere", "Horror")
        testInchirieri.addInchiriere(c, f)
        
        listFilmeFound = testInchirieri.findByFilm(f.getTitlu())
        
        assert listFilmeFound.getAll()[0].getClient().getNume() == "Ion Ionescu"

'''
unittest.main()
'''