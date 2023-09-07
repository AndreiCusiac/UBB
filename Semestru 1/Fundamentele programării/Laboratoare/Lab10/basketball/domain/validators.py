'''
Created on 9 dec. 2020

@author: Catalin
'''
from domain.entities import Player
import unittest

class ValidatorException(Exception):
    def __init__(self, errors):
        self.errors = errors
    def getErrors(self):
        return self.errors
    
class ValidatePlayer():
    def validatePlayer(self, p):
        """
            validate player p
            fields cannot be empty
            id and points field can only be positive integers
        """
        errors = []
        if p.getId() == "":
            errors.append("Id field cannot be empty.")
        else:
            try:
                x = int(p.getId())
                if x < 0:
                    errors.append("Id field can only be a positive integer.")
            except:
                errors.append("Id field can only be a positive integer.")
        if p.getPoints() == "":
            errors.append("Points field cannot be empty.")
        else:
            try:
                x = int(p.getPoints())
                if x < 0:
                    errors.append("Points field can only be a positive integer.")
            except:
                errors.append("Points field can only be a positive integer.")
        if p.getPosition() == "":
            errors.append("Position field cannot be empty.")
        if p.getName() == "":
            errors.append("Name field cannot be empty.")
        if len(errors)>0:
            raise ValidatorException(errors)

class TestCaseValidateStudent(unittest.TestCase):
    def setUp(self):
        self.p1 = Player("1","Stephen Curry","point_guard","178")
        self.p2 = Player("-1","Stephen Curry","point_guard","178")
        self.p3 = Player("","Stephen Curry","point_guard","178")
        self.p4 = Player("1","","point_guard","178")
        self.p5 = Player("1","Stephen Curry","","178")
        self.p6 = Player("1","Stephen Curry","point_guard","")
        self.val = ValidatePlayer()
    
    def testPlayerEntity(self):
        self.val.validatePlayer(self.p1)
        with self.assertRaises(ValidatorException):
            self.val.validatePlayer(self.p2)
        with self.assertRaises(ValidatorException):
            self.val.validatePlayer(self.p3)
        with self.assertRaises(ValidatorException):
            self.val.validatePlayer(self.p4)
        with self.assertRaises(ValidatorException):
            self.val.validatePlayer(self.p5)
        with self.assertRaises(ValidatorException):
            self.val.validatePlayer(self.p6)
if __name__=='__main__':
    unittest.main()