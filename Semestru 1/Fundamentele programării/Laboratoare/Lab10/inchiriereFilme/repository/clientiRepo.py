from domain.client import client

class clientiRepo:

    def __init__(self):
        '''
            Functie de initializare a clasei clienti
            self - obiectul clienti
        '''
        
        self.clienti = []
    
    def addClient(self, ID, nume, CNP):
        '''
            Functie de adaugare client in lista de clienti
            ID - ID-ul clientului de adaugat
            nume - numele clientului de adaugat
            CNP - CNP-ul clientului de adaugat
            Cerinte:
                - ID-ul sa nu coincida cu al altuia
                - CNP-ul sa aiba 13 cifre SI sa nu coincida cu al altuia
        '''
        
        c = client(ID, nume, CNP)
        self.clienti.append(c)
    
    def delClient(self, ID):
        '''
            Functie de stergere client din lista
            ID - ID-ul clientului de sters
            Functia returneaza o lista de clienti fara cel sters
        '''
        
        listClienti = clientiRepo()
        
        for c in clientiRepo.getAll(self):
            if client.getID(c) != ID:
                listClienti.clienti.append(c)
        
        return listClienti

    def updClient(self, ID, newID, newNume, newCNP):
        '''
            Functie de actualizare date client in lista de clienti
            ID - ID-ul clientului de modificat
            newID - noul ID al clientului
            newNume - noul nume al clientului
            newCNP - noul CNP al clientului
            Cerinte:
                - newID-ul sa nu coincida cu al altuia
                - newCNP-ul sa aiba 13 cifre SI sa nu coincida cu al altuia
        '''
        
        for c in clientiRepo.getAll(self):
            if client.getID(c) == ID:
                client.setID(c, newID)
                client.setNume(c, newNume)
                client.setCNP(c, newCNP)
                break
    
    def findIDClient(self, IDDeCautat):
        '''
            Functie de gasire client in functie de ID
            self - lista de clienti unde se face cautarea ID-ului
            IDdeCautat - ID-ul de cautat in lista self
        '''
        
        IDOK = clientiRepo()
        
        for c in clientiRepo.getAll(self):
            if c.getID() == IDDeCautat:
                IDOK.addClient(c.getID(), c.getNume(), c.getCNP())
        
        return IDOK
    
    def findNumeClient(self, numeDeCautat):
        '''
            Functie de gasire a unui client in functie de nume
            self - lista de clienti in care se face cautarea numelui
            numeDeCautat - numele de cautat in lista self
        '''
        
        numeOK = clientiRepo()
        
        for c in clientiRepo.getAll(self):
            if c.getNume() == numeDeCautat:
                numeOK.addClient(c.getID(), c.getNume(), c.getCNP())
        
        return numeOK
    
    def findCNPClient(self, CNPDeCautat):
        '''
            Functie de gasire client in functie de CNP
            self - lista de clienti in care se face cautarea CNP-ului
            numeDeCautat - numele de cautat in lista self
        '''
        
        CNPOK = clientiRepo()
        
        for c in clientiRepo.getAll(self):
            if c.getCNP() == CNPDeCautat:
                CNPOK.addClient(c.getID(), c.getNume(), c.getCNP())
        
        return CNPOK
    
    def getAll(self):
        '''
            Functie ce returneaza toti clientii
            self - lista de clienti
        '''
        
        return self.clienti

import unittest

class TestCaseClientiRepo(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testAddClient(self):
        '''
            Functie de test a functiei addClient
        '''
        
        testClienti = clientiRepo()
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        
        self.assertEqual(client.getID(testClienti.getAll()[0]), 1, "ADAUGARE CLIENT. ID")
        self.assertEqual(client.getNume(testClienti.getAll()[0]), "Ion Ionescu", "ADAUGARE CLIENT. NUME")
        self.assertEqual(client.getCNP(testClienti.getAll()[0]), 1981008226715, "ADAUGARE CLIENT. CNP")
    
    def testDelClient(self):
        '''
            Functie de test a functiei delClient
        '''
        
        testClienti = clientiRepo()
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(2, "Ana Blandiana", 2641234567890)
        testClienti = testClienti.delClient(1)
        
        self.assertEqual(client.getID(testClienti.getAll()[0]), 2, "STERGERE CLIENT. ID")
        self.assertEqual(client.getNume(testClienti.getAll()[0]), "Ana Blandiana", "STERGERE CLIENT. NUME")
        self.assertEqual(client.getCNP(testClienti.getAll()[0]), 2641234567890, "STERGERE CLIENT. CNP")
    
    def testUpdClient(self):
        '''
            Functie de test a functiei updClient
        '''
        
        testClienti = clientiRepo()
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.updClient(1, 2, "Ana Blandiana", 2641234567890)
        
        self.assertEqual(client.getID(testClienti.getAll()[0]), 2, "UPDATE CLIENT. ID")
        self.assertEqual(client.getNume(testClienti.getAll()[0]), "Ana Blandiana", "UPDATE CLIENT. NUME")
        self.assertEqual(client.getCNP(testClienti.getAll()[0]), 2641234567890, "UPDATE CLIENT. CNP")
    
    def testFindIDClient(self):
        '''
            Functie de test a functiei findIDClient
        '''
        
        testClienti = clientiRepo()
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(2, "Ana Blandiana", 2641234567890)
        
        testClientiFound = clientiRepo.findIDClient(testClienti, 2)
        
        for c in testClientiFound.getAll():
            self.assertEqual(c.getID(), 2, "FIND ID CLIENT. ID")
            self.assertEqual(c.getNume(), "Ana Blandiana", "FIND ID CLIENT. NUME")
            self.assertEqual(c.getCNP(), 2641234567890, "FIND ID CLIENT. CNP")
    
    def testFindNumeClient(self):
        '''
            Functie de test a functiei findNumeClient
        '''
        
        testClienti = clientiRepo()
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(2, "Ana Blandiana", 2641234567890)
        
        testClientiFound = clientiRepo.findNumeClient(testClienti, "Ana Blandiana")
        
        for c in testClientiFound.getAll():
            self.assertEqual(c.getID(), 2, "FIND ID CLIENT. ID")
            self.assertEqual(c.getNume(), "Ana Blandiana", "FIND ID CLIENT. NUME")
            self.assertEqual(c.getCNP(), 2641234567890, "FIND ID CLIENT. CNP")
    
    def testFindCNPClient(self):
        '''
            Functie de test a functiei findCNPClient
        '''
        
        testClienti = clientiRepo()
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(2, "Ana Blandiana", 2641234567890)
        
        testClientiFound = clientiRepo.findCNPClient(testClienti, 2641234567890)
        
        for c in testClientiFound.getAll():
            self.assertEqual(c.getID(), 2, "FIND ID CLIENT. ID")
            self.assertEqual(c.getNume(), "Ana Blandiana", "FIND ID CLIENT. NUME")
            self.assertEqual(c.getCNP(), 2641234567890, "FIND ID CLIENT. CNP")

'''
unittest.main()
'''