'''
Created on 19 nov. 2020

@author: Raposatu
'''
class IdNegasit(Exception):
    def __init__(self, mesaj="Id-ul nu se gaseste in baza de date"):
        self.__mesaj=mesaj
        
class IdNegativ(Exception):
    def __init__(self, mesaj="Id-ul nu trebuie sa fie negativ"):
        self.__mesaj=mesaj
        
class NotaNecorespunzatoare(Exception):
    def __init__(self, mesaj="Nota trebuie sa fie cuprinsa intre 1 si 10"):
        self.__mesaj=mesaj
class ValidareEsuata(Exception):
    def __init__(self, mesaj="Validarea a esuat"):
        self.__mesaj=mesaj