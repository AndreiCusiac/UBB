from domain.client import client
from domain.film import film

class inchiriere:
    
    def __init__(self, client, film):
        '''
            Functie de creare a unei inchirieri
            client - clientul care inchiriaza
            film - filmul inchiriat
        '''
        
        self.client = client
        self.film = film
        self.returnat = False
    
    def getClient(self):
        '''
            Functie de returnare a clientului unei inchirieri
            self - inchirierea
        '''
        
        return self.client
    
    def getFilm(self):
        '''
            Functie de returnare a filmului unei inchirieri
            self - inchirierea
        '''
        
        return self.film
    
    def getState(self):
        '''
            Functie de returnare a statusului unui film - returnat/nereturnat
            self - inchirierea
        '''
        
        return self.returnat
        
    def setReturnat(self):
        '''
            Functie de setare a filmului ca returnat
            self - inchirierea
        '''
        
        self.returnat = True

import unittest

class TestCaseInchiriere(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testInitInchiriere(self):
        '''
            Functie de testare a functiei initInchiriere
        '''
        
        testClient = client(1, "Nenea Vasile", 1981008226715)
        testFilm = film(1, "Ana", "Ana are mere", "Horror")
        testInchiriere = inchiriere(testClient, testFilm)
        self.assertEqual(testInchiriere.getClient(), testClient, "INITIALIZARE INCHIRIERE. CLIENT")
        self.assertEqual(testInchiriere.getFilm(), testFilm, "INITIALIZARE INCHIRIERE. FILM")
    
    def testSetReturnat(self):
        '''
            Functie de testare a functiei setReturnat
        '''
        
        testClient = client(1, "Nenea Vasile", 1981008226715)
        testFilm = film(1, "Ana", "Ana are mere", "Horror")
        testInchiriere = inchiriere(testClient, testFilm)
        self.assertFalse(testInchiriere.getState(), "INITIALIZARE INCHIRIERE. RETURNAT")
        testInchiriere.setReturnat()
        self.assertTrue(testInchiriere.getState(), "SETRETURNAT")

'''
unittest.main()
'''