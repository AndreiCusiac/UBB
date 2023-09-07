class client:
    
    def __init__(self, ID, nume, CNP):
        '''
            Functie de creare a unui client
            ID - ID-ul noului client
            nume - numele noului client
            CNP - CNP-ul noului client
            Cerinte:
                - ID-ul sa nu coincida cu al altuia
                - CNP-ul sa aiba 13 cifre SI sa nu coincida cu al altuia
        '''
        
        self.ID = ID
        self.nume = nume
        self.CNP = CNP
        self.nrInchirieri = 0

    def getID(self):
        '''
            Functie ce returneaza ID-ul unui client
            self - clientul de referinta
        '''
        
        return self.ID
    
    def getNume(self):
        '''
            Functie ce returneaza numele unui client
            self - clientul de referinta
        '''
        
        return self.nume
    
    def getCNP(self):
        '''
            Functie ce returneaza CNP-ul unui client
            self - clientul de referinta
        '''
        return self.CNP
    
    def setID(self, newID):
        '''
            Functie ce seteaza ID-ul unui client
            self - clientul de referinta
            newID - noul ID al clientului
        '''
        
        self.ID = newID
    
    def setNume(self, newNume):
        '''
            Functie ce seteaza numele unui client
            self - clientul de referinta
            newNume - noul nume al clientului
        '''
        
        self.nume = newNume
    
    def setCNP(self, newCNP):
        '''
            Functie ce seteaza CNP-ul unui client
            self - clientul de referinta
            newCNP - noul CNP al clientului
        '''
        
        self.CNP = newCNP

import unittest

class TestCaseClient(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)
    
    def testInitClient(self):
        '''
            Functie de test a functiei initClient
        '''
        
        testClient = client(1, "Nenea Vasile", 1981008226715)
        
        self.assertEqual(client.getID(testClient), 1, "INITIALIZARE CLIENT. ID")
        self.assertEqual(client.getNume(testClient), "Nenea Vasile", "INITIALIZARE CLIENT. NUME")
        self.assertEqual(client.getCNP(testClient), 1981008226715, "INITIALIZARE CLIENT. CNP")
        
        
    def testSetID(self):
        '''
            Functie de test a functiei setID
        '''
        
        testClient = client(1, "Nenea Vasile", 1981008226715)
        client.setID(testClient, 2)
        newTestClient = client(2, "Nenea Vasile", 1981008226715)
        
        self.assertEqual(client.getID(testClient), client.getID(newTestClient), "SETID. ID")
        self.assertEqual(client.getNume(testClient), client.getNume(newTestClient), "SETID. NUME")
        self.assertEqual(client.getCNP(testClient), client.getCNP(newTestClient), "SETID. CNP")
    
    def testSetNume(self):
        '''
            Functie de test a functiei setNume
        '''
        
        testClient = client(1, "Nenea Vasile", 1981008226715)
        client.setNume(testClient, "Nenea Nelu")
        newTestClient = client(1, "Nenea Nelu", 1981008226715)
        
        self.assertEqual(client.getID(testClient), client.getID(newTestClient), "SETNUME. ID")
        self.assertEqual(client.getNume(testClient), client.getNume(newTestClient), "SETNUME. NUME")
        self.assertEqual(client.getCNP(testClient), client.getCNP(newTestClient), "SETNUME. CNP")
    
    def testSetCNP(self):
        '''
            Functie de test a functiei setCNP
        '''
        
        testClient = client(1, "Nenea Vasile", 1981008226715)
        client.setCNP(testClient, 2690211314723)
        newTestClient = client(1, "Nenea Vasile", 2690211314723)
        
        self.assertEqual(client.getID(testClient), client.getID(newTestClient), "SETCNP. ID")
        self.assertEqual(client.getNume(testClient), client.getNume(newTestClient), "SETCNP. NUME")
        self.assertEqual(client.getCNP(testClient), client.getCNP(newTestClient), "SETCNP. CNP")

if  __name__ == "__main__":
    unittest.main()