from domain.film import film

class filmeRepo:
    
    def __init__(self):
        '''
            Functie de initializare a clasei filme
            self - obiectul filme
        '''
        
        self.filme = []
    
    def addFilm(self, ID, titlu, descriere, gen):
        '''
            Functie de adaugare film in lista de filme
            ID - ID-ul filmului de adaugat
            titlu - titlul filmului de adaugat
            descriere - descrierea filmului de adaugat
            gen - genul filmului de adaugat
            Cerinte:
                - ID-ul sa nu coincida cu al altuia
        '''
        
        f = film(ID, titlu, descriere, gen)
        self.filme.append(f)
    
    def delFilm(self, ID):
        '''
            Functie de stergere film din lista
            self - lista de filme
            ID - ID-ul filmului de sters
        '''
        
        listFilme = filmeRepo()
        
        for f in filmeRepo.getAll(self):
            if film.getID(f) != ID:
                listFilme.filme.append(f)
        
        return listFilme
    
    def updFilm(self, ID, newID, newTitlu, newDescriere, newGen):
        '''
            Functie de actualizare date film in lista de filme
            ID - ID-ul filmului de modificat
            newID - noul ID al filmului
            newTitlu - noul titlu al filmului
            newDescriere - noua descriere a filmului
            newGen - noul gen al filmului
            Cerinte:
                - newID-ul sa nu coincida cu al altuia
        '''
        
        for f in filmeRepo.getAll(self):
            if film.getID(f) == ID:
                film.setID(f, newID)
                film.setTitlu(f, newTitlu)
                film.setDescriere(f, newDescriere)
                film.setGen(f, newGen)
                break

    def findIDFilm(self, IDDeCautat):
        '''
            Functie de gasire film in functie de ID
            self - lista de filme unde se face cautarea ID-ului
            IDdeCautat - ID-ul de cautat in lista self
        '''
        
        IDOK = filmeRepo()
        
        for f in filmeRepo.getAll(self):
            if f.getID() == IDDeCautat:
                IDOK.addFilm(f.getID(), f.getTitlu(), f.getDescriere(), f.getGen())
        
        return IDOK
    
    def findTitluFilm(self, titluDeCautat):
        '''
            Functie de gasire film in functie de titlu
            self - lista de filme in care se face cautarea titlului
            titluDeCautat - titlul de cautat in lista self
        '''
        
        titluOK = filmeRepo()
        
        for f in filmeRepo.getAll(self):
            if f.getTitlu() == titluDeCautat:
                titluOK.addFilm(f.getID(), f.getTitlu(), f.getDescriere(), f.getGen())
        
        return titluOK
    
    def findGenFilm(self, genDeCautat):
        '''
            Functie de gasire film in functie de gen
            self - lista de filme in care se face cautarea genului
            genDeCautat - genul de cautat in lista self
        '''
        
        genOK = filmeRepo()
        
        for f in filmeRepo.getAll(self):
            if f.getGen() == genDeCautat:
                genOK.addFilm(f.getID(), f.getTitlu(), f.getDescriere(), f.getGen())
        
        return genOK
    
    def getAll(self):
        '''
            Functie ce returneaza toate filmele
            self - lista de filme
        '''
        
        return self.filme

import unittest

class TestCaseFilmeRepo(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testAddFilm(self):
        '''
            Functie de test a functiei addFilm
        '''
        
        testFilme = filmeRepo()
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        assert film.getID(testFilme.getAll()[0]) == 1
        assert film.getTitlu(testFilme.getAll()[0]) == "Ana"
        assert film.getDescriere(testFilme.getAll()[0]) == "Ana are mere"
        assert film.getGen(testFilme.getAll()[0]) == "Horror"

    def testDelFilm(self):
        '''
            Functie de test a functiei delFilm
        '''
        
        testFilme = filmeRepo()
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.addFilm(2, "Bana", "Bana are mere", "Extreme Horror")
        testFilme = testFilme.delFilm(1)
        assert film.getID(testFilme.getAll()[0]) == 2
        assert film.getTitlu(testFilme.getAll()[0]) == "Bana"
        assert film.getDescriere(testFilme.getAll()[0]) == "Bana are mere"
        assert film.getGen(testFilme.getAll()[0]) == "Extreme Horror"

    def testUpdFilm(self):
        '''
            Functie de test a functiei updFilm
        '''
        
        testFilme = filmeRepo()
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.updFilm(1, 2, "Ana Blandiana", "Ana Blandiana are mere", "Comedy")
        assert film.getID(testFilme.getAll()[0]) == 2
        assert film.getTitlu(testFilme.getAll()[0]) == "Ana Blandiana"
        assert film.getDescriere(testFilme.getAll()[0]) == "Ana Blandiana are mere"
        assert film.getGen(testFilme.getAll()[0]) == "Comedy"
    
    def testFindIDFilm(self):
        '''
            Functie de test a functiei findIDFilm
        '''
        
        testFilme = filmeRepo()
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.addFilm(2, "Ana Blandiana", "Ana Blandiana are mere", "Comedy")
        
        testFilmeFound = filmeRepo.findIDFilm(testFilme, 2)
        
        for f in testFilmeFound.getAll():
            assert f.getID() == 2
            assert f.getTitlu() == "Ana Blandiana"
            assert f.getDescriere() == "Ana Blandiana are mere"
            assert f.getGen() == "Comedy"
    
    def testFindTitluFilm(self):
        '''
            Functie de test a functiei findTitluFilm
        '''
        
        testFilme = filmeRepo()
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.addFilm(2, "Ana Blandiana", "Ana Blandiana are mere", "Comedy")
        
        testFilmeFound = filmeRepo.findTitluFilm(testFilme, "Ana Blandiana")
        
        for f in testFilmeFound.getAll():
            assert f.getID() == 2
            assert f.getTitlu() == "Ana Blandiana"
            assert f.getDescriere() == "Ana Blandiana are mere"
            assert f.getGen() == "Comedy"
    
    def testFindGenFilm(self):
        '''
            Functie de test a functiei findGenFilm
        '''
        
        testFilme = filmeRepo()
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.addFilm(2, "Ana Blandiana", "Ana Blandiana are mere", "Comedy")
        
        testFilmeFound = filmeRepo.findGenFilm(testFilme, "Comedy")
        
        for f in testFilmeFound.getAll():
            assert f.getID() == 2
            assert f.getTitlu() == "Ana Blandiana"
            assert f.getDescriere() == "Ana Blandiana are mere"
            assert f.getGen() == "Comedy"

'''
unittest.main()
'''