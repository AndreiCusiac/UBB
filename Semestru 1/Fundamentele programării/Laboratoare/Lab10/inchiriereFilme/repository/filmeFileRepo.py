from domain.film import film
        
class filmeFileRepo:
    
    def __init__(self, fileName):
        '''
            Functie de initalizare a repository filme in fisier
            fileName = numele fisierului
        '''
        
        self.filme = []
        self.fileName = fileName
    
    def __loadFile(self):
        '''
            Functie de incarcare a tuturor datelor din fisier
        '''
        
        self.filme = []
        file = open(self.fileName, "r")
        line = file.readline().strip()
        while line != '':
            parts = line.split("/")
            f = film(parts[0], parts[1], parts[2], parts[3])
            self.filme.append(f)
            line = file.readline().strip()
        file.close()
        
    def __storeFile(self):
        '''
            Functie de scriere a tuturor datelor din memorie in fisier
        '''
        
        file = open(self.fileName, "w")
        for f in self.filme:
            file.write(str(f.getID()) + '/')
            file.write(f.getTitlu() + '/')
            file.write(f.getDescriere() + '/')
            file.write(f.getGen() + '\n')
        file.close()
    
    def __storeFileONE(self, f):
        '''
            Functie de stocare a unui film in fisier
        '''
        
        file = open(self.fileName, "a")
        file.write(str(f.getID()) + '/')
        file.write(f.getTitlu() + '/')
        file.write(f.getDescriere() + '/')
        file.write(f.getGen() + '\n')

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
        filmeFileRepo.__storeFileONE(self, f)
    
    def delFilm(self, ID):
        '''
            Functie de stergere film din lista
            ID - ID-ul filmului de sters
        '''
        
        listFilme = []
        for f in self.filme:
            if film.getID(f) != ID:
                listFilme.append(film(f.getID(), f.getTitlu(), f.getDescriere(), f.getGen()))
        
        file = open(self.fileName, "w")
        for f in listFilme:
            file.write(str(f.getID()) + '/')
            file.write(f.getTitlu() + '/')
            file.write(f.getDescriere() + '/')
            file.write(f.getGen() + '\n')
        file.close()
        
        filmeFileRepo.__loadFile(self)
        
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
        
        filmeFileRepo.__loadFile(self)
        
        for f in self.filme:
            if int(film.getID(f)) == int(ID):
                film.setID(f, newID)
                film.setTitlu(f, newTitlu)
                film.setDescriere(f, newDescriere)
                film.setGen(f, newGen)
                break
    
        filmeFileRepo.__storeFile(self)
    
    def findIDFilm(self, IDDeCautat):
        '''
            Functie de gasire film in functie de ID
            self - lista de filme unde se face cautarea ID-ului
            IDdeCautat - ID-ul de cautat in lista self
        '''
        
        gol = open("emptyFile.txt", "w")
        gol.close()
        IDOK = filmeFileRepo("emptyFile.txt")
        
        for f in self.filme:
            if int(f.getID()) == int(IDDeCautat):
                IDOK.addFilm(f.getID(), f.getTitlu(), f.getDescriere(), f.getGen())
        
        return IDOK

    def findTitluFilm(self, titluDeCautat):
        '''
            Functie de gasire film in functie de titlu
            self - lista de filme in care se face cautarea titlului
            titluDeCautat - titlul de cautat in lista self
        '''
        
        gol = open("emptyFile.txt", "w")
        gol.close()
        titluOK = filmeFileRepo("emptyFile.txt")
        filmeFileRepo.__loadFile(self)
        
        for f in self.filme:
            if f.getTitlu() == titluDeCautat:
                titluOK.addFilm(f.getID(), f.getTitlu(), f.getDescriere(), f.getGen())
        
        return titluOK
    
    def findGenFilm(self, genDeCautat):
        '''
            Functie de gasire film in functie de gen
            self - lista de filme in care se face cautarea genului
            genDeCautat - genul de cautat in lista self
        '''
        
        gol = open("emptyFile.txt", "w")
        gol.close()
        genOK = filmeFileRepo("emptyFile.txt")
        filmeFileRepo.__loadFile(self)
        
        for f in self.filme:
            if f.getGen() == genDeCautat:
                genOK.addFilm(f.getID(), f.getTitlu(), f.getDescriere(), f.getGen())
        
        return genOK

    def getAll(self):
        '''
            Functie ce returneaza toate filmele
            self - lista de filme
        '''
        
        filmeFileRepo.__loadFile(self)
        return self.filme

import unittest

class TestCaseFilmeFileRepo(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        file = open("filmeTest.txt", "w")
        file.close()

    def tearDown(self):
        unittest.TestCase.tearDown(self)
        file = open("filmeTest.txt", "w")
        file.close()
    
    def testAddFilm(self):
        '''
            Functie de test a functiei addFilm
        '''
        
        testFilme = filmeFileRepo("filmeTest.txt")
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        
        self.assertEqual(film.getID(testFilme.getAll()[0]), "1", "ADAUGARE FILM. ID")
        self.assertEqual(film.getTitlu(testFilme.getAll()[0]), "Ana", "ADAUGARE FILM. TITLU")
        self.assertEqual(film.getDescriere(testFilme.getAll()[0]), "Ana are mere", "ADAUGARE FILM. DESCRIERE")
        self.assertEqual(film.getGen(testFilme.getAll()[0]), "Horror", "ADAUGARE FILM. GEN")
    
    def testDelFilm(self):
        '''
            Functie de test a functiei delFilm
        '''
        
        testFilme = filmeFileRepo("filmeTest.txt")
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.addFilm(2, "Bana", "Bana are mere", "Extreme Horror")
        testFilme.delFilm(1)
        
        self.assertEqual(film.getID(testFilme.getAll()[0]), "2", "DELETE FILM. ID")
        self.assertEqual(film.getTitlu(testFilme.getAll()[0]), "Bana", "DELETE FILM. TITLU")
        self.assertEqual(film.getDescriere(testFilme.getAll()[0]), "Bana are mere", "DELETE FILM. DESCRIERE")
        self.assertEqual(film.getGen(testFilme.getAll()[0]), "Extreme Horror", "DELETE FILM. GEN")
    
    def testUpdFilm(self):
        '''
            Functie de test a functiei updFilm
        '''
        
        testFilme = filmeFileRepo("filmeTest.txt")
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.updFilm(1, 2, "Ana Blandiana", "Ana Blandiana are mere", "Comedy")
        
        self.assertEqual(film.getID(testFilme.getAll()[0]), "2", "UPDATE FILM. ID")
        self.assertEqual(film.getTitlu(testFilme.getAll()[0]), "Ana Blandiana", "UPDATE FILM. TITLU")
        self.assertEqual(film.getDescriere(testFilme.getAll()[0]), "Ana Blandiana are mere", "UPDATE FILM. DESCRIERE")
        self.assertEqual(film.getGen(testFilme.getAll()[0]), "Comedy", "UPDATE FILM. GEN")
    
    
    def testFindIDFilm(self):
        '''
            Functie de test a functiei findIDFilm
        '''
        
        return
        testFilme = filmeFileRepo("filmeTest.txt")
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.addFilm(2, "Ana Blandiana", "Ana Blandiana are mere", "Comedy")
        
        testFilmeFound = filmeFileRepo.findIDFilm(testFilme, 2)
        
        for f in testFilmeFound.getAll():
            self.assertEqual(f.getID(), "2", "FIND ID FILM. ID")
            self.assertEqual(f.getTitlu(), "Ana Blandiana", "FIND ID FILM. TITLU")
            self.assertEqual(f.getDescriere(), "Ana Blandiana are mere", "FIND ID FILM. DESCRIERE")
            self.assertEqual(f.getGen(), "Comedy", "FIND ID FILM. GEN")
    
        
    def testFindTitluFilm(self):
        '''
            Functie de test a functiei findTitluFilm
        '''
        
        testFilme = filmeFileRepo("filmeTest.txt")
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.addFilm(2, "Ana Blandiana", "Ana Blandiana are mere", "Comedy")
        
        testFilmeFound = filmeFileRepo.findTitluFilm(testFilme, "Ana Blandiana")
        
        for f in testFilmeFound.getAll():
            self.assertEqual(f.getID(), "2", "FIND TITLU FILM. ID")
            self.assertEqual(f.getTitlu(), "Ana Blandiana", "FIND TITLU FILM. TITLU")
            self.assertEqual(f.getDescriere(), "Ana Blandiana are mere", "FIND TITLU FILM. DESCRIERE")
            self.assertEqual(f.getGen(), "Comedy", "FIND TITLU FILM. GEN")
    
    
    def testFindGenFilm(self):
        '''
            Functie de test a functiei findGenFilm
        '''
        
        return
        testFilme = filmeFileRepo("filmeTest.txt")
        testFilme.addFilm(1, "Ana", "Ana are mere", "Horror")
        testFilme.addFilm(2, "Ana Blandiana", "Ana Blandiana are mere", "Comedy")
        
        testFilmeFound = filmeFileRepo.findGenFilm(testFilme, "Comedy")
        
        for f in testFilmeFound.getAll():
            self.assertEqual(f.getID(), "2", "FIND GEN FILM. ID")
            self.assertEqual(f.getTitlu(), "Ana Blandiana", "FIND GEN FILM. TITLU")
            self.assertEqual(f.getDescriere(), "Ana Blandiana are mere", "FIND GEN FILM. DESCRIERE")
            self.assertEqual(f.getGen(), "Comedy", "FIND GEN FILM. GEN")
    

'''
unittest.main()
'''