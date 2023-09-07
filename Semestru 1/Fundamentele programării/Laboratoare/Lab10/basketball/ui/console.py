'''
Created on 9 dec. 2020

@author: Catalin
'''
from domain.validators import ValidatorException
from repository.inmemory import RepositoryException
class Console():
    def __init__(self, srv):
        self.srv = srv
    
    def createPlayer(self):
        """
            create a player by given parameters
            print errors if not valid
        """
        idd = input("Give id:")
        name = input("Give name:")
        position = input("Give postion:")
        points = input("Give points:")
        try:
            self.srv.createPlayer(idd,name,position,points)
            print("Operation successful")
        except ValidatorException as err:
            print(err.getErrors())
        except RepositoryException:
            print("Duplicate id found.")
        
    def showAllPlayers(self):
        """
            show all registered players
        """
        newList = self.srv.getAllPlayers()
        for el in newList:
            print(el.__str__())
    
    def findPosition(self):
        """
            show all players whose positon match given substring
        """
        stringToFind = input("Give substring:")
        newList = self.srv.findPosition(stringToFind)
        if newList == []:
            print("No players were found.")
        else:
            for el in newList:
                print(el.__str__())
    
    def mvpCandidate(self):
        """
            give number of matches and show goal/match ratio
        """
        idToFind = input("Give player id:")
        nm = input("Give number of matches:")
        rez = self.srv.mvpCandidate(idToFind, nm)
        if rez == -1:
            print("Player not found.")
        else:
            print("Goal/match ratio is:", round(rez,2))
        
    def createMenu(self):
        menu = {}
        menu["1"] = (self.createPlayer,"Add player.")
        menu["2"] = (self.showAllPlayers, "Show all players.")
        menu["3"] = (self.findPosition, "Find players that play on certain position.")
        menu["4"] = (self.mvpCandidate, "MVP Candidate.")
        menu["x"] = ("x","Close app.")
        return menu
    
    def generateMenu(self, menu):
        rez = "\n"
        for k, v in menu.items():
            rez += k + "." + v[1] + "\n"
        rez += "Give command:"
        return rez
    
    def showUi(self):
        print("Basketball players app.")
        menu = self.createMenu()
        while True:
            cmd = input(self.generateMenu(menu))
            if cmd == 'x':
                print("Stay frosty.")
                break
            try:
                x = int(cmd)
                if x >= 1 and x <= 4:
                    fct = menu[cmd][0]
                    fct()
                else:
                    raise Exception
            except:
                print("Unknown command.")