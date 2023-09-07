'''
Created on 9 dec. 2020

@author: Catalin
'''
import unittest
class Player():
    def __init__(self, idd, name, position, points):
        self.idd = idd
        self.name = name
        self.position = position
        self.points = points
    
    def getId(self):
        return self.idd
    
    def getName(self):
        return self.name
    
    def getPosition(self):
        return self.position
    
    def getPoints(self):
        return self.points
    
    def __str__(self):
        return str(self.getId()+","+self.getName()+","+self.getPosition()+","+self.getPoints())
    
    def __eq__(self, other):
        return self.getId() == other.getId()
    
class TestCasePlayerEntity(unittest.TestCase):
    def setUp(self):
        self.p1 = Player("1","Stephen Curry","point_guard","178")
    
    def testPlayerEntity(self):
        self.assertEqual(self.p1.getId(),"1")
        self.assertEqual(self.p1.getName(),"Stephen Curry")
        self.assertEqual(self.p1.getPosition(),"point_guard")
        self.assertEqual(self.p1.getPoints(),"178")
        
    def testDa(self):
        self.assertEqual(self.p1.getPoints(),"178")
        pass

class MVPCandidate():
    def __init__(self, p, nm):
        self.player = p
        self.numberOfMatches = nm
    
    def getPointsAverage(self):
        return float(self.player.getPoints()) / float(self.numberOfMatches)

class TestCaseMVPCandidate(unittest.TestCase):
    def setUp(self):
        self.p1 = Player("1","Stephen Curry","point_guard","20")
        self.mvp1 = MVPCandidate(self.p1, 5)
    
    def testPlayerEntity(self):
        self.assertEqual(self.mvp1.getPointsAverage(), 4)
if __name__=='__main__':
    unittest.main()