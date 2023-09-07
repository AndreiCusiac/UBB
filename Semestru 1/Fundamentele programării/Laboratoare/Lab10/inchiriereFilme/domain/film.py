class film:
    
    def __init__(self, ID, titlu, descriere, gen):
        '''
            Functie de creare a unui film
            ID - ID-ul noului film
            titlu - titlul noului film
            descriere - descrierea noului film
            gen - genul noului film
            Cerinte:
                - ID-ul sa nu coincida cu al altuia
        '''
        
        self.ID = ID
        self.titlu = titlu
        self.descriere = descriere
        self.gen = gen
    
    def getID(self):
        '''
            Functie ce returneaza ID-ul unui film
            self - filmul de referinta
        '''
        
        return self.ID
    
    def getTitlu(self):
        '''
            Functie ce returneaza titlul unui film
            self - filmul de referinta
        '''
        
        return self.titlu
    
    def getDescriere(self):
        '''
            Functie ce returneaza descrierea unui film
            self - filmul de referinta
        '''
        
        return self.descriere
    
    def getGen(self):
        '''
            Functie ce returneaza genul unui film
            self - filmul de referinta
        '''
        
        return self.gen
    
    def setID(self, newID):
        '''
            Functie ce seteaza ID-ul unui film
            self - filmul de referinta
            newID - noul ID al filmului
        '''
        
        self.ID = newID
    
    def setTitlu(self, newTitlu):
        '''
            Functie ce seteaza titlul unui film
            self - filmul de referinta
            newTitlu - noul titlu al filmului
        '''
        
        self.titlu = newTitlu
    
    def setDescriere(self, newDescriere):
        '''
            Functie ce seteaza descrierea unui film
            self - filmul de referinta
            newDescriere - noua descriere a unui film
        '''
        
        self.descriere = newDescriere
    
    def setGen(self, newGen):
        '''
            Functie ce seteaza genul unui film
            self - filmul de referinta
            newGen - noul gen al unui film
        '''
        
        self.gen = newGen

import unittest

class TestCaseFilm(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testInitFilm(self):
        '''
            Functie de test a functiei initFilm
        '''
        
        testFilm = film(1, "Ana", "Ana are mere", "Horror")
        
        self.assertEqual(film.getID(testFilm), 1, "INITIALIZARE FILM. ID")
        self.assertEqual(film.getTitlu(testFilm), "Ana", "INITIALIZARE FILM. TITLU")
        self.assertEqual(film.getDescriere(testFilm), "Ana are mere", "INITIALIZARE FILM. DESCRIERE")
        self.assertEqual(film.getGen(testFilm), "Horror", "INITIALIZARE FILM. GEN")
    
    def testSetID(self):
        '''
            Functie de test a functiei setID
        '''
        
        testFilm = film(1, "Ana", "Ana are mere", "Horror")
        film.setID(testFilm, 2)
        newTestFilm = film(2, "Ana", "Ana are mere", "Horror")
        
        self.assertEqual(film.getID(newTestFilm), 2, "SETID. ID")
        self.assertEqual(film.getTitlu(newTestFilm), "Ana", "SETID. TITLU")
        self.assertEqual(film.getDescriere(newTestFilm), "Ana are mere", "SETID. DESCRIERE")
        self.assertEqual(film.getGen(newTestFilm), "Horror", "SETID. GEN")
    
    def testSetTitlu(self):
        '''
            Functie de test a functiei setTitlu
        '''
        
        testFilm = film(1, "Ana", "Ana are mere", "Horror")
        film.setTitlu(testFilm, "Maria")
        newTestFilm = film(1, "Maria", "Ana are mere", "Horror")
        
        self.assertEqual(film.getID(newTestFilm), 1, "SETTITLU. ID")
        self.assertEqual(film.getTitlu(newTestFilm), "Maria", "SETTITLU. TITLU")
        self.assertEqual(film.getDescriere(newTestFilm), "Ana are mere", "SETTITLU. DESCRIERE")
        self.assertEqual(film.getGen(newTestFilm), "Horror", "SETTITLU. GEN")
    
    def testSetDescriere(self):
        '''
            Functie de test a functiei setDescriere
        '''
        
        testFilm = film(1, "Ana", "Ana are mere", "Horror")
        film.setDescriere(testFilm, "Maria are mere")
        newTestFilm = film(1, "Ana", "Maria are mere", "Horror")
        
        self.assertEqual(film.getID(newTestFilm), 1, "SETDESCRIERE. ID")
        self.assertEqual(film.getTitlu(newTestFilm), "Ana", "SETDESCRIERE. TITLU")
        self.assertEqual(film.getDescriere(newTestFilm), "Maria are mere", "SETDESCRIERE. DESCRIERE")
        self.assertEqual(film.getGen(newTestFilm), "Horror", "SETDESCRIERE. GEN")
    
    def testSetGen(self):
        '''
            Functie de test a functiei setGen
        '''
        
        testFilm = film(1, "Ana", "Ana are mere", "Horror")
        film.setGen(testFilm, "Comedy")
        newTestFilm = film(1, "Ana", "Ana are mere", "Comedy")
        
        self.assertEqual(film.getID(newTestFilm), 1, "SETGEN. ID")
        self.assertEqual(film.getTitlu(newTestFilm), "Ana", "SETGEN. TITLU")
        self.assertEqual(film.getDescriere(newTestFilm), "Ana are mere", "SETGEN. DESCRIERE")
        self.assertEqual(film.getGen(newTestFilm), "Comedy", "SETGEN. GEN")

if  __name__ == "__main__":
    unittest.main()