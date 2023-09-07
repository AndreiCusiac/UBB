'''
Created on 6 nov. 2020

@author: Raposatu
'''
def get_id(pac):
    """
    Prin aceasta functie se face accesul la un anumit element dintr-o structura de data
    pac-de tip dictionar
    Returneaza elementul cerut de parametru
    """
    return pac["id"]
def set_id(pac,valoare):
    """
    Prin aceasta functie se poate modifica un anumit element dintr-o structura de data
    pac-de tip dictionar
    valoare-de tip int
    Returneaza structura de data
    """
    pac["id"]=valoare
    return pac
def get_nume(pac):
    """
    Prin aceasta functie se face accesul la un anumit element dintr-o structura de data
    pac-de tip dictionar
    Returneaza elementul cerut de parametru
    """
    return pac["nume"]
def set_nume(pac,valoare):
    """
    Prin aceasta functie se poate modifica un anumit element dintr-o structura de data
    pac-de tip dictionar
    valoare-de tip string
    Returneaza structura de data
    """
    pac["nume"]=valoare
    return pac
def get_profesor(pac):
    """
    Prin aceasta functie se face accesul la un anumit element dintr-o structura de data
    pac-de tip dictionar
    Returneaza elementul cerut de parametru
    """
    return pac["profesor"]
def set_profesor(pac,valoare):
    """
    Prin aceasta functie se poate modifica un anumit element dintr-o structura de data
    pac-de tip dictionar
    valoare-de tip string
    Returneaza structura de data
    """
    pac["profesor"]=valoare
    return pac
def get_nota(pac):
    """
    Prin aceasta functie se face accesul la un anumit element dintr-o structura de data
    pac-de tip dictionar
    Returneaza elementul cerut de parametru
    """
    return pac["nota"]
def get_note(pac):
    """
    Prin aceasta functie se face accesul la un anumit element dintr-o structura de data
    pac-de tip dictionar
    Returneaza elementul cerut de parametru
    """
    return pac["note"].copy()
def set_nota(pac,valoare):
    """
    Prin aceasta functie se poate modifica un anumit element dintr-o structura de data
    pac-de tip dictionar
    valoare-de tip string
    Returneaza structura de data
    """
    pac["nota"]=valoare
    return pac
def set_note_lista_vida(pac):
    """
    Prin aceasta functie se face accesul la un anumit element dintr-o structura de data,cand aceasta este initializata
    pac-de tip dictionar
    Returneaza elementul cerut de parametru
    """
    pac["note"]=[]
    return pac
    