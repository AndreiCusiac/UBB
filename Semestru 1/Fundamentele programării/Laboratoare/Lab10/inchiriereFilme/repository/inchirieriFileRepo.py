from domain.client import client
from domain.film import film
from domain.inchiriere import inchiriere

class inchirieriFileRepo():
    
    def __init__(self, fileName):
        '''
            Functie de initializare a clasei inchirieri
            self - obiectul inchirieri
        '''
        
        self.inchirieri = []
        self.fileName = fileName
    
    def loadFile(self):
        '''
            Functie de incarcare a tuturor datelor din fisier
        '''
        
        self.inchirieri = []
        file = open(self.fileName, "r")
        line = file.readline()
        while line != '':
            parts = line.split("/")
            c = client(parts[0], parts[1], parts[2])
            f = film(parts[3], parts[4], parts[5], parts[6])
            i = inchiriere(c, f)
            if parts[7] == "True\n": i.setReturnat()
            self.inchirieri.append(i)
            line = file.readline().strip()
        file.close()
    
    def __storeFile(self):
        '''
            Functie de scriere a tuturor datelor din memorie in fisier
        '''
        
        file = open(self.fileName, "w")
        for i in self.inchirieri:
            file.write(str(i.getClient().getID()) + '/')
            file.write(i.getClient().getNume() + '/')
            CNP = i.getClient().getCNP().replace('\n', '')
            file.write(CNP + '/')
            file.write(str(i.getFilm().getID()) + '/')
            file.write(i.getFilm().getTitlu() + '/')
            file.write(i.getFilm().getDescriere() + '/')
            file.write(i.getFilm().getGen() + '/')
            file.write(str(i.getState()) + '\n')
        file.close()
    
    def __storeFileONE(self, i):
        '''
            Functie de salvare a unei noi inchirieri in fisier
        '''
            
        file = open(self.fileName, "a")
        file.write(str(i.getClient().getID()) + '/')
        file.write(i.getClient().getNume() + '/')
        CNP = i.getClient().getCNP().replace('\n', '')
        file.write(CNP + '/')
        file.write(str(i.getFilm().getID()) + '/')
        file.write(i.getFilm().getTitlu() + '/')
        file.write(i.getFilm().getDescriere() + '/')
        file.write(i.getFilm().getGen() + '/')
        file.write(str(i.getState()) + '\n')
        
    def addInchiriere(self, client, film):
        '''
            Functie de adaugare inchiriere in lista de inchirieri
            client - clientul care inchiriaza
            film - filmul inchiriat
        '''
        
        i = inchiriere(client, film)
        self.inchirieri.append(i)
        inchirieriFileRepo.__storeFileONE(self, i)
    
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
            
        inchirieriFileRepo.__storeFile(self)
    
    def updateInchiriere(self, client, film):
        '''
            Functia actualizeaza fisierul de inchirieri in cazul unei modificari de parametri
            client - client modificat
            film - film modificat
        '''
        
        gol = open("emptyFile.txt", "w")
        gol.close()
        listInchirieriNEW = inchirieriFileRepo("emptyFile.txt")
        
        for i in self.inchirieri.getAll():
            if i.getFilm().getID() == film.getID():
                listInchirieriNEW.addInchiriere(client, film)
            else: listInchirieriNEW.addInchiriere(i.getClient(), i.getFilm())
        
        file = open("inchirieri.txt", "w")
        for i in listInchirieriNEW.getAll():
            file.write(str(i.getClient().getID()) + '/')
            file.write(i.getClient().getNume() + '/')
            CNP = i.getClient().getCNP().replace('\n', '')
            file.write(CNP + '/')
            file.write(str(i.getFilm().getID()) + '/')
            file.write(i.getFilm().getTitlu() + '/')
            file.write(i.getFilm().getDescriere() + '/')
            file.write(i.getFilm().getGen() + '/')
            file.write(str(i.getState()) + '\n')
        file.close()
    
    def findByClient(self, numeClient):
        '''
            Functie de returnare a tuturor inchirierilor facute de un client
            self - lista de inchirieri
            client - clientul dorit
        '''
        
        inchirieriFileRepo.loadFile(self)
        gol = open("emptyFile.txt", "w")
        gol.close()
        listFilme = inchirieriFileRepo("emptyFile.txt")
        
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
        
        inchirieriFileRepo.loadFile(self)
        gol = open("emptyFile.txt", "w")
        gol.close()
        listClienti = inchirieriFileRepo("emptyFile.txt")
        
        for i in self.inchirieri:
            if i.getFilm().getTitlu() == titluFilm:
                listClienti.addInchiriere(i.getClient(), i.getFilm())
        
        return listClienti
    
    def getAll(self):
        '''
            Functie ce returneaza toate inchirierile
            self - lista de inchirieri
        '''
        
        inchirieriFileRepo.loadFile(self)
        return self.inchirieri

import unittest

class TestCaseInchirieriFileRepo(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        file = open("inchirieriTest.txt", "w")
        file.close()

    def tearDown(self):
        unittest.TestCase.tearDown(self)
    
    def testAddInchiriere(self):
        '''
            Functie de test a functiei addInchiriere
        '''
        
        testInchirieri = inchirieriFileRepo("inchirieriTest.txt")
        c = client(1, "Ion Ionescu", 1981008226715)
        f = film(1, "Ana", "Ana are mere", "Horror")
        testInchirieri.addInchiriere(c, f)
        
        self.assertEqual(testInchirieri.getAll()[0].getClient().getNume(), "Ion Ionescu", "ADAUGARE INCHIRIERE. CLIENT")
        self.assertEqual(testInchirieri.getAll()[0].getFilm().getTitlu(), "Ana", "ADAUGARE INCHIRIERE. FILM")
    
    def testReturnareFilm(self):
        '''
            Functie de test a functiei returnareFilm
        '''
        
        testInchirieri = inchirieriFileRepo("inchirieriTest.txt")
        c = client(1, "Ion Ionescu", 1981008226715)
        f = film(1, "Ana", "Ana are mere", "Horror")
        testInchirieri.addInchiriere(c, f)
        
        self.assertFalse(testInchirieri.getAll()[0].returnat, "RETURNARE FILM")
        
        testInchirieri.returnareFilm("Ana")
        
        self.assertFalse(testInchirieri.getAll()[0].returnat, "RETURNARE FILM")
    
    def testFindByClient(self):
        '''
            Functie de test a functiei findByClient
        '''
        
        testInchirieri = inchirieriFileRepo("inchirieriTest.txt")
        c = client(1, "Ion Ionescu", 1981008226715)
        f = film(1, "Ana", "Ana are mere", "Horror")
        testInchirieri.addInchiriere(c, f)
        
        listFilmeFound = testInchirieri.findByClient(c.getNume())
        
        self.assertEqual(listFilmeFound.getAll()[0].getFilm().getTitlu(), "Ana", "FIND BY CLIENT")
    
    def testFindByFilm(self):
        '''
            Functie de test a functiei findByFilm
        '''
        
        testInchirieri = inchirieriFileRepo("inchirieriTest.txt")
        c = client(1, "Ion Ionescu", 1981008226715)
        f = film(1, "Ana", "Ana are mere", "Horror")
        testInchirieri.addInchiriere(c, f)
        
        listFilmeFound = testInchirieri.findByFilm(f.getTitlu())
        
        self.assertEqual(listFilmeFound.getAll()[0].getClient().getNume(), "Ion Ionescu", "FIND BY FILM")

'''
unittest.main()
'''