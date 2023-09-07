'''
Created on 9 dec. 2020

@author: Catalin
'''
from domain.entities import Player, MVPCandidate
from domain.validators import ValidatePlayer, ValidatorException
from repository.inmemory import InMemoryPlayerRepository, RepositoryException
import unittest

class PlayerService():
    def __init__(self, val, rep):
        self.val = val
        self.rep = rep
    
    def createPlayer(self, idd, name, position, points):
        """
            create player entity
            validate player entity
            store player
            idd, name, position, points - parameters
        """
        p = Player(idd, name, position, points)
        self.val.validatePlayer(p)
        self.rep.storePlayer(p)
    
    def getAllPlayers(self):
        """
            get a list of all players
        """
        return self.rep.getAllPlayers()
    
    def findPosition(self, stringToFind):
        """
            show all players whose positon match given substring
        """
        newList = self.getAllPlayers()
        rez = []
        for el in newList:
            if el.getPosition().find(stringToFind) != -1:
                rez.append(el)
        return rez
    
    def mvpCandidate(self, idToFind, nm):
        """
            show mvps descending (players with highest goal/match ratio
            nm - number of matches played
        """
        ok = 0
        newList = self.getAllPlayers()
        for el in newList:
            if el.getId() == idToFind:
                mvp = MVPCandidate(el, nm)
                ok = 1
        if ok == 0:
            return -1
        return mvp.getPointsAverage()
        
class TestCasePlayerService(unittest.TestCase):
    def setUp(self):
        self.val = ValidatePlayer()
        self.rep = InMemoryPlayerRepository()
        self.srv = PlayerService(self.val, self.rep)
    
    def testCreatePlayer(self):
        self.assertEqual(self.srv.getAllPlayers(), [])
        self.srv.createPlayer("1","Stephen Curry","point_guard","178")
        self.srv.createPlayer("2","Stephen Curry","point_guard","178")
        with self.assertRaises(RepositoryException):
            self.srv.createPlayer("1","Stephen Curry","point_guard","178")
        with self.assertRaises(ValidatorException):
            self.srv.createPlayer("-1","Stephen Curry","point_guard","178")
        with self.assertRaises(ValidatorException):
            self.srv.createPlayer("","Stephen Curry","point_guard","178")
        with self.assertRaises(ValidatorException):
            self.srv.createPlayer("1","","point_guard","178")
        with self.assertRaises(ValidatorException):
            self.srv.createPlayer("1","Stephen Curry","","178")
        with self.assertRaises(ValidatorException):
            self.srv.createPlayer("1","Stephen Curry","point_guard","")
        self.assertEqual(self.srv.getAllPlayers(), [Player("1","Stephen Curry","point_guard","178"), Player("2","Stephen Curry","point_guard","178")])
    
    def testFindPosition(self):
        self.srv.createPlayer("1","Stephen Curry","point_guard","178")
        self.srv.createPlayer("2","Stephen Curry","point_guard","178")
        self.srv.createPlayer("3","Stephen Curry","point_center","178")
        self.assertEqual(self.srv.findPosition("point"), [Player("1","Stephen Curry","point_guard","178"), Player("2","Stephen Curry","point_guard","178"), Player("3","Stephen Curry","point_center","178")])
        self.assertEqual(self.srv.findPosition("guard"), [Player("1","Stephen Curry","point_guard","178"), Player("2","Stephen Curry","point_guard","178")])
        self.assertEqual(self.srv.findPosition("hehe"), [])
    
    def testMVPCandidate(self):
        self.srv.createPlayer("1","Stephen Curry","point_guard","180")
        self.assertEqual(self.srv.mvpCandidate("1", 20), 9)
        self.assertEqual(self.srv.mvpCandidate("2", 20), -1)
if __name__=='__main__':
    unittest.main()