from domain.client import client
from domain.film import film
from repository.clientiRepo import clientiRepo
from repository.filmeRepo import filmeRepo
from repository.inchirieriRepo import inchirieriRepo

class ValidatorException(Exception):
    
    def __init__(self, errors):
        '''
            Functie de initializare a validatorului
            self - validatorul
            errors - erorile aruncate
        '''
        
        self.errors = errors

    def getErrors(self):
        '''
            Functie de preluare a erorilor
            self - erorile aruncate
        '''
        return self.errors

class validatorFilme:
    
    def __init(self, validator):
        '''
            Functie de initializare a validatorului
        '''
        self.validator = validator
    
    def validateFilme(self, film, filme):
        '''
            Functie pentru validarea unicitatii ID-ului
            film - filmul de testat
            filme - lista de filme
        '''
        
        ID = film.getID()
        
        for f in filme.getAll():
            if f.getID() == ID:
                raise ValidatorException(['ID-ul deja exista!'])
    
    def validateFilm(self, film):
        '''
            Throw ValidatorException if fields are empty
            film - filmul de testat
        '''
        
        errors = []
        
        if film.getID() == '':
            errors.append("Campul ID nu poate fi gol!")
        if film.getTitlu() == '':
            errors.append("Campul Titlu nu poate fi gol!")
        if film.getDescriere() == '':
            errors.append("Campul Descriere nu poate fi gol!")
        if film.getGen() == '':
            errors.append("Campul Gen nu poate fi gol!")
        
        if len(errors) > 0:
            raise ValidatorException(errors)

class validatorClienti:
    
    def __init(self, validator):
        '''
            Functie de initializare a validatorului
        '''
        
        self.validator = validator

    def validateClienti(self, client, clienti):
        '''
            Functie pentru validarea unicitatii ID-ului
            client - clientul de testat
            clienti - lista de clienti
        '''
        
        errors = []
        ID = client.getID()
        CNP = client.getCNP()
        
        for c in clienti.getAll():
            if c.getID() == ID: errors.append('ID-ul deja exista!')
            if c.getCNP() == CNP: errors.append('CNP-ul deja exista!')
        
        if len(errors) > 0:
            raise ValidatorException(errors)

    def validateClient(self, client):
        '''
            Throw ValidatorException if fields are empty
            client - clientul de testat
        '''
        
        errors = []
        
        if client.getID() == '':
            errors.append("Campul ID nu poate fi gol!")
        if client.getNume() == '':
            errors.append("Campul Nume nu poate fi gol!")
        if client.getCNP() == '':
            errors.append("Campul CNP nu poate fi gol!")
        
        if str(client.getCNP()).isnumeric() == False:
            errors.append("CNP-ul nu poate contine alte caractere decat cifre!")
        else:
            if len(str(client.getCNP())) != 13:
                errors.append("CNP-ul nu are formatul corect!")
        
        if len(errors) > 0:
            raise ValidatorException(errors)

class validatorInchirieri:
    
    def __init(self, validator):
        '''
            Functie de initializare a validatorului
        '''
        
        self.validator = validator
    
    def validateInchirieri(self, titluFilm, inchirieri):
        '''
            Functie pentru validarea disponibilitatii filmului
            numeFilm - numele filmului de testat
            inchirieri - lista de inchirieri
        '''
        
        errors = []
        
        for i in inchirieri.getAll():
            if i.getFilm().getTitlu() == titluFilm and i.getState() == False:
                errors.append("Filmul este deja inchiriat!")
        
        if len(errors) > 0:
            raise ValidatorException(errors)
    
    def validateInchiriere(self, numeClient, titluFilm, clienti, filme):
        '''
            Throw ValidatorException if film or client does not exist
            inchiriere - inchirierea de testat
            filme - lista de filme
            clienti - lista de clienti
        '''
        
        errors = []
        
        listClient = clienti.findNumeClient(numeClient)
        listFilme = filme.findTitluFilm(titluFilm)
        
        if len(listClient.getAll()) == 0:
            errors.append("Clientul nu exista!")
        
        if len(listFilme.getAll()) == 0:
            errors.append("Filmul nu exista!")
        
        if len(errors) > 0:
            raise ValidatorException(errors)
    
    def validateStergereClient(self, inchirieri, client):
        '''
            Functie de validare a stergerii
            inchirieri - lista inchirieri
            client - client de verificat
        '''
        
        errors = []
        
        for i in inchirieri.getAll():
            if i.getClient().getID() == client.getID():
                errors.append("Nu se poate sterge un client ce are o inchiriere!")
                break
        
        if len(errors) > 0:
            raise ValidatorException(errors)

import unittest

class TestCaseValidatoare(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)
    
    def testValidateFilm(self):
        '''
            Functie de test a functiei validateFilm
        '''
        
        val = validatorFilme()
        
        try:
            validatorFilme.validateFilm(val, film(1, "Ana", "", "Horror"))
            assert False
        except ValidatorException:
            assert True
    
    def testValidateClient(self):
        '''
            Functie de test a functiei validateClient
        '''
        
        val = validatorClienti()
        
        try:
            validatorClienti.validateClient(val, client(1, "", 1981008226715))
            assert False
        except ValidatorException:
            assert True
    
    def testValidateInchiriere(self):
        '''
            Functie de test a functiei validateInchiriere
        '''
        
        val = validatorInchirieri()
        
        testInchirieri = inchirieriRepo()
        C = clientiRepo()
        F = filmeRepo()
        C.addClient(1, "Ion Ionescu", 1981008226715)
        F.addFilm(1, "Ana", "Ana are mere", "Horror")
        testInchirieri.addInchiriere(C.getAll()[0], F.getAll()[0])

        try:
            validatorInchirieri.validateInchiriere(val, "Tudor", F.getAll()[0].getTitlu(), C, F)
            assert False
        except ValidatorException:
            assert True
            
        try:
            validatorInchirieri.validateInchiriere(val, C.getAll()[0], "Bana", C, F)
            assert False
        except ValidatorException:
            assert True

        try:
            validatorInchirieri.validateInchirieri(val, F.getAll()[0].getTitlu(), testInchirieri)
            assert False
        except ValidatorException:
            assert True

'''
unittest.main()
'''