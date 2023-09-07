'''
Created on 9 dec. 2020

@author: Catalin
'''
from domain.entities import Player
from repository.inmemory import InMemoryPlayerRepository, RepositoryException
from utils.fileutils import clearFileContent
import unittest
class FilePlayerRepository(InMemoryPlayerRepository):
    def __init__(self, fileName):
        """
            initiate filerepo
            fileName - name of file to work with
        """
        self.fileName = fileName
        InMemoryPlayerRepository.__init__(self)
    
    def createPlayerFromLine(self, line):
        """
            create a player entity from a string
            line - string
        """
        fields = line.split(',')
        return Player(fields[0],fields[1],fields[2],fields[3])
        
    def loadFromFile(self):
        """
            clear data from memory repository
            read file content and store in memory repository
        """
        InMemoryPlayerRepository.clearAll(self)
        f = open(self.fileName)
        for line in f:
            if line == "":
                continue
            p = self.createPlayerFromLine(line)
            InMemoryPlayerRepository.storePlayer(self, p)
        f.close()
    
    def appendToFile(self, p):
        """
            append player in the file
        """
        f = open(self.fileName,"a")
        f.write(p.__str__())
        f.write('\n')
        f.close()
            
    def storePlayer(self, p):
        """
            load data from file
            store in memory repo
            append new player to file
        """
        self.loadFromFile()
        InMemoryPlayerRepository.storePlayer(self, p)
        self.appendToFile(p)
    
    def getAllPlayers(self):
        self.loadFromFile()
        return InMemoryPlayerRepository.getAllPlayers(self)
    
    def getSizePlayers(self):
        self.loadFromFile()
        return InMemoryPlayerRepository.getSizePlayers(self)

class TestCaseFilePlayerRepository(unittest.TestCase):
    def setUp(self):
        self.p1 = Player("1","Stephen Curry","point_guard","178")
        self.p2 = Player("2","Stephen Curry","point_guard","178")
        self.p3 = Player("1","Stephen Curry","point_guard","178")
        self.fileName = "test.txt"
        self.rep = FilePlayerRepository(self.fileName)
    
    def testStorePlayer(self):
        clearFileContent(self.fileName)
        self.assertEqual(self.rep.getSizePlayers(), 0)
        self.rep.storePlayer(self.p1)
        self.rep.storePlayer(self.p2)
        with self.assertRaises(RepositoryException):
            self.rep.storePlayer(self.p3)
        self.assertEqual(self.rep.getAllPlayers(), [self.p1,self.p2])
        self.assertEqual(self.rep.getSizePlayers(), 2)
if __name__=='__main__':
    unittest.main()