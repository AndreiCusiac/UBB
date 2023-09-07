'''
Created on 9 dec. 2020

@author: Catalin
'''
from domain.entities import Player
import unittest

class RepositoryException(Exception):
    pass

class InMemoryPlayerRepository():
    def __init__(self):
        self.players = {}
    
    def storePlayer(self, p):
        """
            store a player in the database
            p is a player
            raise RepositoryException if there is already another player with the same id
        """
        if p.getId() in self.players:
            raise RepositoryException
        self.players[p.getId()] = p
    
    def getAllPlayers(self):
        """
            get a list of all current registered players
        """
        return list(self.players.values())
    
    def getSizePlayers(self):
        """
            return how many players are registerd
        """
        return len(self.getAllPlayers())
    
    def clearAll(self):
        """
            clear all data in the dictionary
        """
        self.players.clear()
    
class TestCaseInMemoryPlayerRepository(unittest.TestCase):
    def setUp(self):
        self.p1 = Player("1","Stephen Curry","point_guard","178")
        self.p2 = Player("2","Stephen Curry","point_guard","178")
        self.p3 = Player("1","Stephen Curry","point_guard","178")
        self.rep = InMemoryPlayerRepository()
    
    def testStorePlayer(self):
        self.assertEqual(self.rep.getSizePlayers(), 0)
        self.rep.storePlayer(self.p1)
        self.rep.storePlayer(self.p2)
        with self.assertRaises(RepositoryException):
            self.rep.storePlayer(self.p3)
        self.assertEqual(self.rep.getAllPlayers(), [self.p1,self.p2])
        self.assertEqual(self.rep.getSizePlayers(), 2)
if __name__=='__main__':
    unittest.main()