from domain.client import client
        
class clientiFileRepo:
    
    def __init__(self, fileName):
        '''
            Functie de initalizare a repository clienti in fisier
            fileName = numele fisierului
        '''
        
        self.clienti = []
        self.fileName = fileName
    
    def __loadFile(self):
        '''
            Functie de incarcare a tuturor datelor din fisier
        '''
        
        self.clienti = []
        file = open(self.fileName, "r")
        line = file.readline()
        while line != '':
            parts = line.split("/")
            c = client(parts[0], parts[1], parts[2])
            self.clienti.append(c)
            line = file.readline().strip()
        file.close()
        
    def __storeFile(self):
        '''
            Functie de scriere a tuturor datelor din memorie in fisier
        '''
        
        file = open(self.fileName, "w")
        for c in self.clienti:
            file.write(str(c.getID()) + '/')
            file.write(str(c.getNume()) + '/')
            file.write(str(c.getCNP()) + '\n')
        file.close()
    
    def __storeFileONE(self, c):
        '''
            Functie de scriere in fisier a unui client
        '''
        
        file = open(self.fileName, "a")
        file.write(str(c.getID()) + '/')
        file.write(str(c.getNume()) + '/')
        file.write(str(c.getCNP()) + '\n')
    
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
        clientiFileRepo.__storeFileONE(self, c)
    
    def delClient(self, ID):
        '''
            Functie de stergere client din lista
            ID - ID-ul clientului de sters
        '''
        
        listClienti = []
        for c in self.clienti:
            if client.getID(c) != ID:
                listClienti.append(client(c.getID(), c.getNume(), c.getCNP()))
        
        file = open(self.fileName, "w")
        for c in listClienti:
            file.write(str(c.getID()) + '/')
            file.write(str(c.getNume()) + '/')
            file.write(str(c.getCNP()) + '\n')
        file.close()
        
        clientiFileRepo.__loadFile(self)
        
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
        
        clientiFileRepo.__loadFile(self)
        
        for c in self.clienti:
            if int(c.getID()) == int(ID):
                client.setID(c, newID)
                client.setNume(c, newNume)
                client.setCNP(c, newCNP)
                break
    
        clientiFileRepo.__storeFile(self)
    
    def findIDClient(self, IDDeCautat):
        '''
            Functie de gasire client in functie de ID
            self - lista de clienti unde se face cautarea ID-ului
            IDdeCautat - ID-ul de cautat in lista self
        '''
        
        gol = open("emptyFile.txt", "w")
        gol.close()
        IDOK = clientiFileRepo("emptyFile.txt")
        clientiFileRepo.__loadFile(self)
        
        for c in self.clienti:
            if int(c.getID()) == int(IDDeCautat):
                IDOK.addClient(c.getID(), c.getNume(), c.getCNP())
        
        return IDOK

    def findNumeClient(self, numeDeCautat):
        '''
            Functie de gasire a unui client in functie de nume
            self - lista de clienti in care se face cautarea numelui
            numeDeCautat - numele de cautat in lista self
        '''
        
        gol = open("emptyFile.txt", "w")
        gol.close()
        numeOK = clientiFileRepo("emptyFile.txt")
        
        for c in self.clienti:
            if c.getNume() == numeDeCautat:
                numeOK.addClient(c.getID(), c.getNume(), c.getCNP())
        
        return numeOK
    
    def findCNPClient(self, CNPDeCautat):
        '''
            Functie de gasire client in functie de CNP
            self - lista de clienti in care se face cautarea CNP-ului
            numeDeCautat - numele de cautat in lista self
        '''
        
        gol = open("emptyFile.txt", "w")
        gol.close()
        CNPOK = clientiFileRepo("emptyFile.txt")
        clientiFileRepo.__loadFile(self)
        
        for c in self.clienti:
            if str(c.getCNP()) == str(CNPDeCautat).replace('\n', ''):
                CNPOK.addClient(c.getID(), c.getNume(), c.getCNP())
        
        return CNPOK

    def getAll(self):
        '''
            Functie ce returneaza toti clientii
            self - lista de clienti
        '''
        
        clientiFileRepo.__loadFile(self)
        return self.clienti

import unittest

class TestCaseClientiFileRepo(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        file = open("clientiTest.txt", "w")
        file.close()

    def tearDown(self):
        unittest.TestCase.tearDown(self)
        file = open("clientiTest.txt", "w")
        file.close()
    
    def testAddClient(self):
        '''
            Functie de test a functiei addClient
        '''
        
        testClienti = clientiFileRepo("clientiTest.txt")
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        
        self.assertEqual(client.getID(testClienti.getAll()[0]), "1", "ADAUGARE CLIENT. ID")
        self.assertEqual(client.getNume(testClienti.getAll()[0]), "Ion Ionescu", "ADAUGARE CLIENT. NUME")
        self.assertEqual(client.getCNP(testClienti.getAll()[0]), "1981008226715\n", "ADAUGARE CLIENT. CNP")
    
    def testDelClient(self):
        '''
            Functie de test a functiei delClient
        '''
        
        testClienti = clientiFileRepo("clientiTest.txt")
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(2, "Ana Blandiana", 2641234567890)
        testClienti.delClient(1)
        
        self.assertEqual(client.getID(testClienti.getAll()[0]), "2", "STERGERE CLIENT. ID")
        self.assertEqual(client.getNume(testClienti.getAll()[0]), "Ana Blandiana", "STERGERE CLIENT. NUME")
        self.assertEqual(client.getCNP(testClienti.getAll()[0]), "2641234567890\n", "STERGERE CLIENT. CNP")
    
    def testUpdClient(self):
        '''
            Functie de test a functiei updClient
        '''
        
        testClienti = clientiFileRepo("clientiTest.txt")
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.updClient(1, 2, "Ana Blandiana", 2641234567890)
        
        self.assertEqual(client.getID(testClienti.getAll()[0]), "2", "UPDATE CLIENT. ID")
        self.assertEqual(client.getNume(testClienti.getAll()[0]), "Ana Blandiana", "UPDATE CLIENT. NUME")
        self.assertEqual(client.getCNP(testClienti.getAll()[0]), "2641234567890\n", "UPDATE CLIENT. CNP")
    
    def testFindIDClient(self):
        '''
            Functie de test a functiei findIDClient
        '''
        
        return
        testClienti = clientiFileRepo("clientiTest.txt")
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(2, "Ana Blandiana", 2641234567890)
        
        testClientiFound = clientiFileRepo.findIDClient(testClienti, 2)
        
        for c in testClientiFound.getAll():
            self.assertEqual(c.getID(), "2", "FIND ID CLIENT. ID")
            self.assertEqual(c.getNume(), "Ana Blandiana", "FIND ID CLIENT. NUME")
            self.assertEqual(c.getCNP(), "2641234567890", "FIND ID CLIENT. CNP")
    
    def testFindNumeClient(self):
            '''
                Functie de test a functiei findNumeClient
            '''
            
            testClienti = clientiFileRepo("clientiTest.txt")
            testClienti.addClient(1, "Ion Ionescu", 1981008226715)
            testClienti.addClient(2, "Ana Blandiana", 2641234567890)
            
            testClientiFound = clientiFileRepo.findNumeClient(testClienti, "Ana Blandiana")
            
            for c in testClientiFound.getAll():
                self.assertEqual(c.getID(), "2", "FIND ID CLIENT. ID")
                self.assertEqual(c.getNume(), "Ana Blandiana", "FIND ID CLIENT. NUME")
                self.assertEqual(c.getCNP(), "2641234567890\n", "FIND ID CLIENT. CNP")
    
    def testFindCNPClient(self):
        '''
            Functie de test a functiei findCNPClient
        '''
        
        return
        testClienti = clientiFileRepo("clientiTest.txt")
        
        testClienti.addClient(1, "Ion Ionescu", 1981008226715)
        testClienti.addClient(2, "Ana Blandiana", 2641234567890)
        
        testClientiFound = clientiFileRepo.findCNPClient(testClienti, 2641234567890)
        
        for c in testClientiFound.getAll():
            self.assertEqual(c.getID(), "2", "FIND ID CLIENT. ID")
            self.assertEqual(c.getNume(), "Ana Blandiana", "FIND ID CLIENT. NUME")
            self.assertEqual(c.getCNP(), "2641234567890", "FIND ID CLIENT. CNP")

'''
unittest.main()
'''