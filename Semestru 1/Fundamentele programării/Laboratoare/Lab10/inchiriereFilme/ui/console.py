from domain.client import client
from repository.clientiRepo import clientiRepo
from service.clientiServ import clientiServ
from service.sort import sort
from domain.film import film
from repository.filmeRepo import filmeRepo
from repository.inchirieriRepo import inchirieriRepo
from domain.validatoare import validatorClienti, validatorFilme, validatorInchirieri,\
    ValidatorException
from service.inchirieriServ import inchirieriServ
from repository.clientiFileRepo import clientiFileRepo
from repository.inchirieriFileRepo import inchirieriFileRepo

class console():
    
    def __init__(self, clienti, filme, inchirieri):
        self.clienti = clienti
        self.filme = filme
        self.inchirieri = inchirieri
    
    def addFilm(self):
        '''
            Functie de adaugare film
            self - lista de filme
            Raise ValidatorException if ID coincide cu al altuia
        '''
        
        ID = input("Dati ID-ul filmului: ")
        titlu = input("Dati titlul filmului: ")
        descriere = input("Dati descrierea filmului: ")
        gen = input("Dati genul filmului: ")
        print('\n')
        
        try:
            validatorFilme.validateFilm(self, film(ID, titlu, descriere, gen))
            validatorFilme.validateFilme(self, film(ID, titlu, descriere, gen), self.filme)
            self.filme.addFilm(ID, titlu, descriere, gen)
        except ValidatorException as msg:
            print("Nu s-a reusit introducerea! Va rugam incercati din nou!\n", msg, '\n')
    
    def delFilm(self):
        '''
            Functie de stergere film
            self - lista de filme
        '''
        
        ID = input("Dati ID-ul filmului de sters: ")
        print('\n')
        
        self.filme.delFilm(ID)
    
    def updFilm(self):
        '''
            Functie de actualizare atribute film
            self - lista de filme
            Raise ValidatorException if ID coincide cu al altuia
        '''
        
        ID = input("Dati ID-ul filmului de modificat: ")
        print('\n')
        newID = input("Dati noul ID al filmului: ")
        newTitlu = input("Dati noul titlu al filmului: ")
        newDescriere = input("Dati noua descriere a filmului: ")
        newGen = input("Dati noul gen al filmului: ")
        print('\n')
        
        try:
            validatorFilme.validateFilm(self, film(newID, newTitlu, newDescriere, newGen))
            if ID != newID: validatorFilme.validateFilme(self, film(newID, newTitlu, newDescriere, newGen), self.filme)
            else: validatorFilme.validateFilme(self, film(-1, newTitlu, newDescriere, newGen), self.filme)
            self.filme.updFilm(ID, newID, newTitlu, newDescriere, newGen)
        except ValidatorException as msg:
            print("Nu s-a reusit actualizarea! Va rugam incercati din nou!\n", msg, '\n')

    def findIDFilm(self):
        '''
            Functie de gasire film cu un anumit ID
            self - lista de filme
        '''
        
        IDDeCautat = input("Dati ID-ul filmului de cautat: ")
        print('\n')
        
        listaCuID = self.filme.findIDFilm(IDDeCautat)
        console.showFindFilme(listaCuID)
    
    def findTitluFilm(self):
        '''
            Functie de gasire a filmului cu un anumit titlu
            self - lista de filme
        '''
        
        titluDeCautat = input("Dati titlul filmului de cautat: ")
        print('\n')
        
        listaCuTitluri = self.filme.findTitluFilm(titluDeCautat)
        console.showFindFilme(listaCuTitluri)
    
    def findGenFilm(self):
        '''
            Functie de gasire a filmului cu un anumit gen
            self - lista de clienti
        '''
        
        genDeCautat = input("Dati genul filmului de cautat: ")
        print('\n')
        
        listaCuGenuri = self.filme.findGenFilm(genDeCautat)
        console.showFindFilme(listaCuGenuri)

    def showFilme(self):
        '''
            Functie de afisare lista filme
            self - lista de filme
        '''
        
        for f in self.filme.getAll():
            print("ID:", f.getID())
            print("Titlu:", f.getTitlu())
            print("Descriere:", f.getDescriere())
            print("Gen:", f.getGen())
            print('\n')
    
    def showFindFilme(self):
        '''
            Functie de afisare lista de cautari
            self - lista de filme
        '''
        
        for f in self.filme:
            print("ID:", f.getID())
            print("Titlu:", f.getTitlu())
            print("Descriere:", f.getDescriere())
            print("Gen:", f.getGen())
            print('\n')
    
    def addClient(self):
        '''
            Functie de adaugare client
            self - lista de clienti
            Raise ValidatorException if ID or CNP coincide cu al altuia
        '''
        
        ID = input("Dati ID-ul clientului: ")
        nume = input("Dati numele clientului: ")
        CNP = input("Dati CNP-ul clientului: ")
        print('\n')
        
        try:
            validatorClienti.validateClient(self, client(ID, nume, CNP))
            validatorClienti.validateClienti(self, client(ID, nume, CNP), self.clienti)
            self.clienti.addClient(ID, nume, CNP)
        except ValidatorException as msg:
            print("Nu s-a reusit introducerea! Va rugam incercati din nou!\n", msg, '\n')
    
    def delClient(self):
        '''
            Functie de stergere client
            self - lista de clienti
        '''
        
        ID = input("Dati ID-ul clientului de sters: ")
        print('\n')
        try:
            client = self.clienti.findIDClient(ID).getAll()[0]
            validatorInchirieri.validateStergereClient(self, self.inchirieri, client)
            self.clienti.delClient(ID)
        except ValidatorException as msg:
            print("Nu s-a reusit stergerea! Va rugam incercati din nou!\n", msg, '\n')
    
    def updClient(self):
        '''
            Functie de actualizare atribute client
            self - lista de clienti
            Raise ValidatorException if ID or CNP coincide cu al altuia
        '''
        
        ID = input("Dati ID-ul clientului de modificat: ")
        print('\n')
        newID = input("Dati noul ID al clientului: ")
        newNume = input("Dati noul nume al clientului: ")
        newCNP = input("Dati noul CNP al clientului: ")
        print('\n')
        
        try:
            validatorClienti.validateClient(self, client(newID, newNume, newCNP))
            if ID != newID: validatorClienti.validateClienti(self, client(newID, newNume, newCNP), self.clienti)
            else: validatorClienti.validateClienti(self, client(-1, newNume, newCNP), self.clienti)
            self.clienti.updClient(ID, newID, newNume, newCNP)
            c = self.clienti.findIDClient(ID).getAll()[0]
            for i in self.inchirieri.getAll():
                if i.getClient().getID() == c.getID():
                    inchirieriFileRepo.updateInchiriere(self, c, i.getFilm())
        except ValidatorException as msg:
            print("Nu s-a reusit actualizarea! Va rugam incercati din nou!\n", msg, '\n')
    
    def findIDClient(self):
        '''
            Functie de gasire client cu un anumit ID
            self - lista de clienti
        '''
        
        IDDeCautat = input("Dati ID-ul clientului de cautat: ")
        print('\n')
        
        listaCuID = self.clienti.findIDClient(IDDeCautat)
        console.showFindClienti(listaCuID)
    
    def findNumeClient(self):
        '''
            Functie de gasire a clientului cu un anumit nume
            self - lista de clienti
        '''
        
        numeDeCautat = input("Dati numele clientului de cautat: ")
        print('\n')
        
        listaCuNume = clientiRepo.findNumeClient(self.clienti, numeDeCautat)
        console.showFindClienti(listaCuNume)
    
    def findCNPClient(self):
        '''
            Functie de gasire a clientului cu un anumit CNP
            self - lista de clienti
        '''
        
        CNPDeCautat = input("Dati CNP-ul clientului de cautat: ")
        print('\n')
        
        listaCuCNP = clientiRepo.findCNPClient(self.clienti, CNPDeCautat)
        console.showFindClienti(listaCuCNP)
    
    def sortClientiDescID(self):
        '''
            Functie de sortare a clientilor 
            self - lista de clienti
        '''
        
        def printRecursiv(l, i):
            '''
                Functie de afis recursiv
            '''
            
            if i == len(l): return
            
            print("ID:", l[i].getID())
            print("Nume:", l[i].getNume())
            print("CNP:", l[i].getCNP())
            print('\n')
            
            printRecursiv(l, i + 1)
        
        listSorted = sort.sortCevaQuickSort(self.clienti)
        
        l = []
        for c in listSorted:
            l.append(c)
        
        printRecursiv(l, 0)
    
    def genRandomClienti(self):
        '''
            Functie de generare random clienti
            self - lista de clienti
        '''
        
        n = int(input("Cati clienti random sa se adauge? "))
        print('\n')
        self.clienti = clientiServ.genRandomClienti(self, n, 0)
    
    def sumaIDsameName(self):
        '''
            Functie de aflare a sumei ID-urilor clientilor cu acelasi nume
            self = lista clienti
        '''
        
        rezultat = clientiServ.sumaIDsameName(self.clienti)
        
        for i in range(len(rezultat)):
            print(rezultat[i])
        
        print('\n')
    
    def showClienti(self):
        '''
            Functie de afisare lista clienti
            self - lista de clienti
        '''
        
        for c in self.clienti.getAll():
            print("ID:", c.getID())
            print("Nume:", c.getNume())
            CNP = c.getCNP().replace('\n', '')
            print("CNP:", CNP)
            print('\n')

    def showFindClienti(self):
        '''
            Functie de afisare lista clienti
            self - lista de clienti
        '''

        for c in self.clienti:
            print("ID:", c.getID())
            print("Nume:", c.getNume())
            CNP = c.getCNP().replace('\n', '')
            print("CNP:", CNP)
            print('\n')
    
    def addInchiriere(self):
        '''
            Functie de adaugare inchiriere in lista de inchirieri
            self - lista de inchirieri
        '''

        numeClient = input("Dati numele clientului: ")
        titluFilm = input("Dati titlul filmului: ")
        print('\n')
        
        try:            
            validatorInchirieri.validateInchiriere(self, numeClient, titluFilm, self.clienti, self.filme)
            validatorInchirieri.validateInchirieri(self, titluFilm, self.inchirieri)
            
            client = self.clienti.findNumeClient(numeClient).getAll()[0]
            film = self.filme.findTitluFilm(titluFilm).getAll()[0]
            
            self.inchirieri.addInchiriere(client, film)
        
        except ValidatorException as msg:
            print("Nu s-a reusit introducerea! Va rugam incercati din nou!\n", msg, '\n')
    
    def returnareFilm(self):
        '''
            Functie de marcare film din lista de inchirieri ca returnat
            self - lista de inchirieri
        '''
        
        numeFilm = input("Dati numele filmului returnat: ")
        print('\n')
        
        self.inchirieri.returnareFilm(numeFilm)
    
    def findByClient(self):
        '''
            Functie de gasire a tuturor inchirierilor facute de un client
            self - lista de clienti
        '''
        
        numeClient = input("Dati numele clientului: ")
        print('\n')
        
        listFilme = self.inchirieri.findByClient(numeClient)
        
        console.showFindInchirieri(listFilme)
    
    def findByFilm(self):
        '''
            Functie de gasire a tuturor inchirierilor unui film
            self - lista de filme
        '''
        
        titluFilm = input("Dati titlul filmului: ")
        print('\n')
        
        listClienti = self.inchirieri.findByFilm(titluFilm)
        
        console.showFindInchirieri(listClienti)
    
    def sortByName(self):
        '''
            Functie de sortare dupa nume a inchirierilor
            self - lista de inchirieri
        '''
        
        inchirieriSorted = inchirieriServ.sortByName(self.inchirieri)
        
        for i in inchirieriSorted:
            print("Nume client:", i.getClient().getNume())
            print("Nume film:", i.getFilm().getTitlu())
            print("Returnat:", i.getState())
            print('\n')
    
    def sortByFilmeNoInchirieri(self):
        '''
            Functie de determinare a celor mai inchiriate filme
        '''
        
        self.inchirieri.loadFile()
        filmeSorted = inchirieriServ.sortByFilmeNoInchirieri(self, self.inchirieri, self.filme)
        nrInchirieri = inchirieriServ.getNumarInchirieriByFilme(self, self.inchirieri, filmeSorted)
        
        index = 0
        for f in filmeSorted.getAll():
            print("ID:", f.getID())
            print("Titlu:", f.getTitlu())
            print("Descriere:", f.getDescriere())
            print("Gen:", f.getGen())
            print("Numar de inchirieri:", nrInchirieri[index])
            print('\n')
            index = index + 1
            if index == 2: break
    
    def sortByClientsNoInchirieri(self):
        '''
            Functie de sortare a inchirierilor dupa numarul de inchirieri ale unui client
        '''
        
        clientiSorted = inchirieriServ.sortByClientsNoInchirieri(self, self.inchirieri, self.clienti)
        
        for c in clientiSorted:
            nrInchirieri = 0
            for i in self.inchirieri.getAll():
                if i.getClient().getID() == c.getID():
                    nrInchirieri = nrInchirieri + 1
            print("ID:", c.getID())
            print("Nume:", c.getNume())
            print("CNP:", str(c.getCNP()).replace('\n', ''))
            print("Numar de filme inchiriate:", nrInchirieri)
            print('\n')
    
    def sortByClientsNoInchirieriOneThird(self):
        '''
            Functie de sortare a inchirierilor dupa numarul de inchirieri ale unui client
        '''
        
        clientiSorted = inchirieriServ.sortByClientsNoInchirieri(self, self.inchirieri, self.clienti)
        
        index = 0
        for c in clientiSorted:
            nrInchirieri = 0
            for i in self.inchirieri.getAll():
                if i.getClient().getID() == c.getID():
                    nrInchirieri = nrInchirieri + 1
            print("ID:", c.getID())
            print("Nume:", c.getNume())
            print("CNP:", str(c.getCNP()).replace('\n', ''))
            print("Numar de filme inchiriate:", nrInchirieri)
            print('\n')
            if index > len(clientiSorted): break
        
    def showInchirieri(self):
        '''
            Functie de afisare a tuturor inchirierilor
            self - lista de inchirieri
        '''
        
        for i in self.inchirieri.getAll():
            print("Nume client:", i.getClient().getNume())
            print("Nume film:", i.getFilm().getTitlu())
            print("Returnat:", i.getState())
            print('\n')
    
    def showFindInchirieri(self):
        '''
            Functie de afisare a tuturor inchirierilor gasite
            self - lista de inchirieri
        '''
        
        for i in self.inchirieri:
            print("Nume client:", i.getClient().getNume())
            print("Nume film:", i.getFilm().getTitlu())
            print("Returnat:", i.getState())
            print('\n')
        
    def showUI(self):
        '''
            Functie de afisare UI
        '''
        
        print("Aplicatie inchiriere filme\n")
        print("1. Adaugare film")
        print("2. Stergere film")
        print("3. Modificare atribute film")
        print("4. Afisare lista filme")
        print("5. Gasire filme dupa ID")
        print("6. Gasire filme dupa titlu")
        print("7. Gasire filme dupa gen")
        print('\n')
        print("8. Adaugare client")
        print("9. Stergere client")
        print("10. Modificare atribute client")
        print("11. Afisare lista clienti")
        print("12. Gasire clienti dupa ID")
        print("13. Gasire clienti dupa nume")
        print("14. Gasire clienti dupa CNP")
        print("15. Sortare clienti invers dupa ID")
        print("16. Generare aleatoare n clienti")
        print("17. Suma ID clienti cu acelasi nume")
        print('\n')
        print("18. Adaugare inchiriere")
        print("19. Setare film ca returnat")
        print("20. Afisare lista inchirieri")
        print("21. Gasire inchirieri dupa client")
        print("22. Gasire inchirieri dupa film")
        print('\n')
        print("23. Cele mai inchiriate filme")
        print("24. Clienti cu filme ordonati dupa nume")
        print("25. Clienti cu filme ordonati dupa inchirieri")
        print("26. Cei mai fideli 30% clienti")
        print('\n')
        
        console.showFilme(self)
        console.showClienti(self)
        console.showInchirieri(self)
        
        while True:
            cmd = int(input("Dati comanda: "))
            print('\n')
            if cmd == 1:
                console.addFilm(self)
            if cmd == 2:
                console.delFilm(self)
            if cmd == 3:
                console.updFilm(self)
            if cmd == 4:
                console.showFilme(self)
            if cmd == 5:
                console.findIDFilm(self)
            if cmd == 6:
                console.findTitluFilm(self)
            if cmd == 7:
                console.findGenFilm(self)
            if cmd == 8:
                console.addClient(self)
            if cmd == 9:
                console.delClient(self)
            if cmd == 10:
                console.updClient(self)
            if cmd == 11:
                console.showClienti(self)
            if cmd == 12:
                console.findIDClient(self)
            if cmd == 13:
                console.findNumeClient(self)
            if cmd == 14:
                console.findCNPClient(self)
            if cmd == 15:
                console.sortClientiDescID(self)
            if cmd == 16:
                console.genRandomClienti(self)
            if cmd == 17:
                console.sumaIDsameName(self)
            if cmd == 18:
                console.addInchiriere(self)
            if cmd == 19:
                console.returnareFilm(self)
            if cmd == 20:
                console.showInchirieri(self)
            if cmd == 21:
                console.findByClient(self)
            if cmd == 22:
                console.findByFilm(self)
            if cmd == 23:
                console.sortByFilmeNoInchirieri(self)
            if cmd == 24:
                console.sortByName(self)
            if cmd == 25:
                console.sortByClientsNoInchirieri(self)
            if cmd == 26:
                console.sortByClientsNoInchirieriOneThird(self)
            