'''
Created on 17 nov. 2020

@author: Raposatu
'''
from domain.clase import *
from domain.validator import *
from utils.exceptii import IdNegasit,NotaNecorespunzatoare,IdNegativ,ValidareEsuata
from controller.controller_catalog import Adaugare_Catalog,Cautare_Catalog,Afisare,Stergere_Catalog,Modificare_Catalog,Filtrare_Catalog,Statistica_Catalog,Random_Ex

class Gestionare:
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        self.__adauga=Adaugare()
        self.__modif=Modificare()
        self.__ster=Stergere()
    def defineste_meniu(self):
        """
        Aceasta functie se ocupa cu formarea unui dictionar meniu care va stoca atat functionalitatile cat si mesajele aferente
        Nu are niciun parametru
        Returneaza meniul cerut mai sus
        """ 
        meniu={}
        meniu["1"]=(self.__adauga.meniu_adaugare,"->pentru a adauga ceva in catalog")
        meniu["2"]=(self.__ster.meniu_stergere,"->pentru a sterge ceva din catalog")
        meniu["3"]=(self.__modif.meniu_modificare,"->pentru a modifica ceva din catalog ")
        meniu["4"]=(None,"->pentru a iesi ")
        return meniu
    
    def mesaj_intrare(self,meniu):
        """
        Aceasta functie se ocupa cu conceperea unui mesaj de intrare pt utilizator
        meniu-de tip dictionar
        Nu returneaza nimic
        """
        mesaj_text="Introducetiuna dintre optiunile de mai jos:\n"
        for poz,el in enumerate(meniu):
            #print(poz)
            #print(meniu[str(poz+1)])
            mesaj_text+=str(poz+1)+meniu[str(poz+1)][1]+"\n"
        mesaj_text+="Optiunea dumneavoastra : "
        return mesaj_text
    
    def meniu_gestionare(self,repos,repod,repon):
        """
        Aceasta functie face parte din meniul principal si se ocupa cu stergerea unui element din catalog
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        gata=True
        #print("Afiseaza in Cristosul Matii")
        meniu=self.defineste_meniu()
        while gata:
            try:
                optiune=input(self.mesaj_intrare(meniu))
                if meniu[optiune][0]==None:
                    gata=False
                else:
                    functie=meniu[optiune][0]
                    #print(functie)
                    functie(repos,repod,repon)
            except KeyError:
                print("Optiune invalida")

class Meniu:
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        self.__gest=Gestionare()
        self.__caut=Cautare()
        self.__filt=Filtrare()
        self.__gen=Generare()
        self.__stat=Statistici()
    def defineste_meniu(self):
        """
        Aceasta functie se ocupa cu formarea unui dictionar meniu care va stoca atat functionalitatile cat si mesajele aferente
        Nu are niciun parametru
        Returneaza meniuul cerut mai sus
        """ 
        meniu={}
        meniu["1"]=(self.__gest.meniu_gestionare,"->pentru a gestiona catalogul")
        meniu["2"]=(self.__caut.meniu_cautare,"->pentru a cauta in catalog")
        meniu["3"]=(self.__filt.meniu_filtrare,"->pentru a filtra catalogul")
        meniu["4"]=(self.__gen.meniu_generare,"->pentru a genera ceva in catalog")
        meniu["5"]=(self.__stat.meniu_statistica,"->pentru a afisa statistici")
        meniu["6"]=(None,"->pentru a iesi")
        return meniu
    
    def mesaj_intrare(self,meniu):
        """
        Aceasta functie se ocupa cu conceperea unui mesaj de intrare pt utilizator
        meniu-de tip dictionar
        Nu returneaza nimic
        """
        mesaj_text="Introducetiuna dintre optiunile de mai jos:\n"
        for poz,el in enumerate(meniu):
            #print(poz)
            #print(meniu[str(poz+1)])
            mesaj_text+=str(poz+1)+meniu[str(poz+1)][1]+"\n"
        mesaj_text+="Optiunea dumneavoastra : "
        return mesaj_text
    
    def meniu(self,repos,repod,repon):
        """
        Aceasta functie reprezinta meniul principal al aplicatiei
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        gata=True
        #print("Afiseaza in Cristosul Matii")
        meniu=self.defineste_meniu()
        while gata:
            try:
                optiune=input(self.mesaj_intrare(meniu))
                if meniu[optiune][0]==None:
                    gata=False
                else:
                    functie=meniu[optiune][0]
                    #print(functie)
                    functie(repos,repod,repon)
            except KeyError as m:
                print(m)
                print("Optiune invalida 1")
    

class Adaugare:
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        self.__cont=Adaugare_Catalog()
        #self.__caut=Cautare_Cont()
        #self.__aleg=Alegeri()
    def defineste_meniu(self):
        """
        Aceasta functie se ocupa cu formarea unui dictionar meniu care va stoca atat functionalitatile cat si mesajele aferente
        Nu are niciun parametru
        Returneaza meniuul cerut mai sus
        """ 
        meniu={}
        meniu["1"]=(self.adaugare_student,"->pentru a adauga un student")
        meniu["2"]=(self.adaugare_disciplina,"->pentru a adauga o disciplina")
        meniu["3"]=(self.meniu_adaugare_nota,"->pentru a adauga o nota")
        meniu["4"]=(None,"->pentru a iesi")
        return meniu
    
    def mesaj_intrare(self,meniu):
        """
        Aceasta functie se ocupa cu conceperea unui mesaj de intrare pt utilizator
        meniu-de tip dictionar
        Nu returneaza nimic
        """
        mesaj_text="Introducetiuna dintre optiunile de mai jos:\n"
        for poz,el in enumerate(meniu):
            #print(poz)
            #print(meniu[str(poz+1)])
            mesaj_text+=str(poz+1)+meniu[str(poz+1)][1]+"\n"
        mesaj_text+="Optiunea dumneavoastra : "
        return mesaj_text
    
    def meniu_adaugare(self,repos,repod,repon):
        """
        Aceasta functie face parte din meniul de gestionare si se ocupa cu adaugarea unui element din catalog
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        gata=True
        print("Afiseaza in Cristosul Matii")
        meniu=self.defineste_meniu()
        while gata:
            try:
                optiune=input(self.mesaj_intrare(meniu))
                if meniu[optiune][0]==None:
                    gata=False
                else:
                    functie=meniu[optiune][0]
                    #print(functie)
                    if optiune=='1':functie(repos)
                    if optiune=='2':functie(repod)
                    if optiune=='3':functie(repos,repod,repon)
            except KeyError:
                print("Optiune invalida")
    
    
    def adaugare_student(self,repos):
        """
        Aceasta functie se ocupa cu adaugarea unui student
        repos-de tip StudentRepository
        Nu returneaza nimic
        ValueError apare atunci cand sunt erori de validare
        """
        nume=""
        id=""
        gata=True
        while gata:
            nume=input("Introduceti numele : ")
            id=input("Introduceti id-ul : ")
            val=ValidatorStudent(repos)
            try:
                val.validare(Student(id,nume))
                if val.get_erori()!=[]: raise ValueError
                gata=False
            except ValueError:
                for el in val.get_erori():
                    print(el)
        self.__cont.adauga_student_catalog(repos, int(id), nume)
        #cat.adauga_studenti(Student(id,nume))
        
        
    def adaugare_disciplina(self,repod):
        """
        Aceasta functie se ocupa cu adaugarea unei discipline
        repod-de tip DisciplinaRepository
        Nu returneaza nimic
        ValueError apare atunci cand sunt erori de validare
        """
        nume=""
        id=""
        profesor=""
        gata=True
        while gata:
            nume=input("Introduceti numele : ")
            id=input("Introduceti id-ul : ")
            profesor=input("Introduceti profesorul : ")
            val=ValidatorDisciplina(repod)
            try:
                val.validare(Disciplina(id,nume,profesor))
                if val.get_erori()!=[]: raise ValueError
                gata=False
            except ValueError:
                for el in val.get_erori():
                    print(el)
        self.__cont.adauga_disciplina_catalog(repod, int(id), nume, profesor)
        #cat.adauga_disciplina(Disciplina(id,nume,profesor))
        
    def meniu_adaugare_nota(self,repos,repod,repon):
        """
        Aceasta functie se ocupa cu adaugarea unei note
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        NotaNecorespunzatoare atunci cand nota nu e cuprinsa in intervalul [1,10]
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        try:
            id_stud=input("Introduceti id-ul studentului: ")
            id_disc=input("Introduceti id-ul disciplinei : ")
            nota=input("Introduceti nota : ")
            self.__cont.adauga_nota_catalog(repos,repod,repon,id_stud,id_disc,nota)
        except ValueError:
            print("Id-ul sau nota sunt scrise incorect")
        except IdNegasit:
            print("Id ul nu se afla in baza de date")
        except NotaNecorespunzatoare:
            print ("Nota trebuie sa fie cuprinsa intre 1 si 10")
 
 
class Cautare:
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        self.__afis=Afisare()
        self.__caut=Cautare_Catalog()
        #self.__aleg=Alegeri()
    def defineste_meniu(self):
        """
        Aceasta functie se ocupa cu formarea unui dictionar meniu care va stoca atat functionalitatile cat si mesajele aferente
        Nu are niciun parametru
        Returneaza meniuul cerut mai sus
        """ 
        meniu={}
        meniu["1"]=(self.cautare_student,"->pentru a cauta un student dupa id")
        meniu["2"]=(self.cautare_lista_studenti,"->pentru a afisa lista de studenti disponibila")
        meniu["3"]=(self.cautare_disciplina,"->pentru a cauta o disciplina dupa id")
        meniu["4"]=(self.cautare_lista_discipline,"->pentru a afisa lista de discipline disponibila")
        meniu["5"]=(self.afiseaza_catalog,"->pentru a afisa catalogul")
        meniu["6"]=(None,"->pentru a iesi")
        return meniu
    
    def mesaj_intrare(self,meniu):
        """
        Aceasta functie se ocupa cu conceperea unui mesaj de intrare pt utilizator
        meniu-de tip dictionar
        Nu returneaza nimic
        """
        mesaj_text="Introducetiuna dintre optiunile de mai jos:\n"
        for poz,el in enumerate(meniu):
            #print(poz)
            #print(meniu[str(poz+1)])
            mesaj_text+=str(poz+1)+meniu[str(poz+1)][1]+"\n"
        mesaj_text+="Optiunea dumneavoastra : "
        return mesaj_text
    
    def meniu_cautare(self,repos,repod,repon):
        """
        Aceasta functie face parte din meniul principal si se ocupa cu afisarea unui catalog sau a datelor acestuia
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        
        """
        gata=True
        meniu=self.defineste_meniu()
        while gata:
            try:
                optiune=input(self.mesaj_intrare(meniu))
                if meniu[optiune][0]==None:
                    gata=False
                else:
                    functie=meniu[optiune][0]
                    #print(functie)
                    functie(repos,repod,repon)
            except KeyError:
                print("Optiune invalida")
    
    def cautare_student(self,repos,repod,repon):
        """
        Aceasta functie se ocupa cu cautarea unui student
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id=input("Introduceti id-ul studentului: ")
        #self.__aleg.alege_id_student(cat,False)
        try:
            student=self.__caut.afisare_student(repos,id)
            sir=self.__afis.afisare_date_student(repos,repod,repon,student)
            print(sir)
        except ValueError:
            print("Id-ul nu este scris corect")
        except IdNegasit:
            print("Id-ul nu se afla in baza de date")
            
    def cautare_disciplina(self,repos,repod,repon):
        """
        Aceasta functie se ocupa cu cautarea unei discipline
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id=input("Introduceti id-ul disciplinei: ")
        try:
            disciplina=self.__caut.afisare_disciplina(repod,id)
            sir=self.__afis.afisare_date_disciplina(repos,repod,repon,disciplina)
            print(sir)
        except ValueError:
            print("Id-ul nu este scris corect")
        except IdNegasit:
            print("Id-ul nu se afla in baza de date")
            
    def cautare_lista_studenti(self,repos,repod,repon):
        """
        Aceasta functie se ocupa cu afisarea listei de studenti
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        #afisare_lista_studenti(cat)
        #self.__afis.afisare_lista_studenti(cat)
        sir=self.__afis.afisare_lista_studenti(repos)
        print(sir)
    def cautare_lista_discipline(self,repos,repod,repon):
        """
        Aceasta functie se ocupa cu afisarea listei de discipline
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        #afisare_lista_discipline(cat)
        #self.__afis.afisare_lista_discipline(cat)
        sir=self.__afis.afisare_lista_discipline(repod)
        print(sir)
    def afiseaza_catalog(self,repos,repod,repon):
        """
        Aceasta functie se ocupa cu afisarea catalogului 
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        #afisare_catalog(cat)
        #self.__afis.afisare_catalog(cat)     
        sir=self.__afis.afisare_catalog(repos,repod,repon)
        print(sir)   
        

class Stergere:
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        #self.__aleg=Alegeri()
        #self.__caut=Cautare_Cont()
        self.__ster=Stergere_Catalog()
    def defineste_meniu(self):
        """
        Aceasta functie se ocupa cu formarea unui dictionar meniu care va stoca atat functionalitatile cat si mesajele aferente
        Nu are niciun parametru
        Returneaza meniuul cerut mai sus
        """ 
        meniu={}
        meniu["1"]=(self.stergere_student_id,"->pentru a sterge un student dupa id")
        #meniu["2"]=(stergere_student_nume,"->pentru a sterge un student dupa id")
        meniu["2"]=(self.stergere_disciplina_id,"->pentru a sterge o disciplina dupa id")
        #meniu["4"]=(stergere_disciplina_nume,"->pentru a sterge o disciplina dupa id")
        meniu["3"]=(None,"->pentru a iesi")
        return meniu
    
    def mesaj_intrare(self,meniu):
        """
        Aceasta functie se ocupa cu conceperea unui mesaj de intrare pt utilizator
        meniu-de tip dictionar
        Nu returneaza nimic
        """
        mesaj_text="Introducetiuna dintre optiunile de mai jos:\n"
        for poz,el in enumerate(meniu):
            #print(poz)
            #print(meniu[str(poz+1)])
            mesaj_text+=str(poz+1)+meniu[str(poz+1)][1]+"\n"
        mesaj_text+="Optiunea dumneavoastra : "
        return mesaj_text
    
    def meniu_stergere(self,repos,repod,repon):
        """
        Aceasta functie face parte din meniul de gestionare si se ocupa cu stergerea unui element din catalog
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        gata=True
        meniu=self.defineste_meniu()
        while gata:
            try:
                optiune=input(self.mesaj_intrare(meniu))
                if meniu[optiune][0]==None:
                    gata=False
                else:
                    functie=meniu[optiune][0]
                    #print(functie)
                    if optiune=='1':functie(repos,repon)
                    if optiune=='2':functie(repod,repon)
            except KeyError:
                print("Optiune invalida")
    
    def stergere_student_id(self,repos,repon):
        """
        Aceasta functie se ocupa cu stergerea unui student dupa id
        repos-de tip StudentRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        IdNegativ apare atunci cand id ul este negativ
        """
        try:
            id=input("Introduceti ID-ul studentului : ")
            self.__ster.sterge_student_catalog(repos,repon, id)
            print("Studentul a fost sters cu succes")
        except ValueError:
            print("Id ul nu este scris cum trebuie")
        except IdNegasit:
            print("Id-ul nu se afla in baza de date")
        except IdNegativ:
            print("Id-ul nu trebuie sa fie negativ")
            
    def stergere_disciplina_id(self,repod,repon):
        """
        Aceasta functie se ocupa cu stergerea unei discipline dupa id
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        IdNegativ apare atunci cand id ul este negativ
        """
        #print(cat.get_lista_id_discipline())
        try:
            id=input("Introduceti ID-ul disciplinei: ")
            self.__ster.sterge_disciplina_catalog(repod,repon, id)
            print("Disciplina a fost stearsa cu succes")
        except ValueError:
            print("Id ul nu este scris cum trebuie")
        except IdNegasit:
            print("Id-ul nu se afla in baza de date")
        except IdNegativ:
            print("Id-ul nu trebuie sa fie negativ")

class Modificare:
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        #self.__caut=Cautare_Cont()
        #self.__aleg=Alegeri()
        self.__modif=Modificare_Catalog()
    def defineste_meniu(self):
        """
        Aceasta functie se ocupa cu formarea unui dictionar meniu care va stoca atat functionalitatile cat si mesajele aferente
        Nu are niciun parametru
        Returneaza meniul cerut mai sus
        """ 
        meniu={}
        meniu["1"]=(self.modificare_student_id,"->pentru a modifica id-ul unui student ")
        meniu["2"]=(self.modificare_student_nume,"->pentru a modifica numele unui student ")
        meniu["3"]=(self.modificare_disciplina_id,"->pentru a modifica id-ul unei discipline ")
        meniu["4"]=(self.modificare_disciplina_nume,"->pentru a modifica numele unei discipline ")
        meniu["5"]=(self.modificare_disciplina_profesor,"->pentru a modifica profesorul unei discipline ")
        meniu["6"]=(None,"->pentru a iesi")
        return meniu
    
    def mesaj_intrare(self,meniu):
        """
        Aceasta functie se ocupa cu conceperea unui mesaj de intrare pt utilizator
        meniu-de tip dictionar
        Nu returneaza nimic
        """
        mesaj_text="Introducetiuna dintre optiunile de mai jos:\n"
        for poz,el in enumerate(meniu):
            #print(poz)
            #print(meniu[str(poz+1)])
            mesaj_text+=str(poz+1)+meniu[str(poz+1)][1]+"\n"
        mesaj_text+="Optiunea dumneavoastra : "
        return mesaj_text
    def meniu_modificare(self,repos,repod,repon):
        """
        Aceasta functie face parte din meniul de gestionare si se ocupa cu modificarea unui element din catalog
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        gata=True
        meniu=self.defineste_meniu()
        while gata:
            try:
                optiune=input(self.mesaj_intrare(meniu))
                if meniu[optiune][0]==None:
                    gata=False
                else:
                    functie=meniu[optiune][0]
                    #print(functie)
                    if optiune=='1' or optiune=='2':functie(repos,repon)
                    else : functie(repod,repon)
            except KeyError:
                print("Optiune invalida")
    
    def modificare_student_id(self,repos,repon):
        """
        Aceasta functie se ocupa cu modificarea id-ului unui student
        repos-de tip StudentRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        IdNegativ apare atunci cand id ul este negativ
        ValidareEsuata apare atunci cand obiectul nu este valid
        """
        id=input("Introduceti ID-ul studentului : ")
        noul_id=input("Introduceti noul ID al studentului : ") 
        val=ValidatorStudent(repos)
        try:
            #print("da1")
            val.validare(Student(noul_id,"orice"))
            if val.get_erori()!=[]: raise ValidareEsuata
            #print("da2")
            self.__modif.modificare_student_id_catalog(repos, repon, id, noul_id)
            #print("da3")
        except ValueError:
            print("Id-ul este scris gresit")
        except IdNegasit:
            print("Id-ul nu se afla in baza de date")
        except IdNegativ:
            print("Id-ul nu trebuie sa fie negativ")
        except ValidareEsuata:
            print(val.get_erori())
            for el in val.get_erori():
                print(el)
    
    def modificare_student_nume(self,repos,repon):
        """
        Aceasta functie se ocupa cu modificarea numelui unui student
        repos-de tip StudentRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        ValidareEsuata apare atunci cand obiectul nu este valid
        """
        id=input("Introduceti ID-ul studentului : ")
        noul_nume=input("Introduceti noul nume al studentului : ") 
        val=ValidatorStudent(repos)
        try:
            val.validare(Student(10000010000010001000,noul_nume))
            if val.get_erori()!=[]: raise ValidareEsuata
            self.__modif.modificare_student_nume_catalog(repos, repon, id, noul_nume)
        except ValueError:
            print("Id-ul este scris gresit")
        except IdNegasit:
            print("Id-ul nu se afla in baza de date")
        except ValidareEsuata:
            for el in val.get_erori():
                print(el)
            
    def modificare_disciplina_id(self,repod,repon):
        """
        Aceasta functie se ocupa cu modificarea id-ului unei discipline
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        IdNegativ apare atunci cand id ul este negativ
        ValidareEsuata apare atunci cand obiectul nu este valid
        """
        id=input("Introduceti ID-ul disciplinei : ")
        noul_id=input("Introduceti noul ID al disciplinei : ") 
        val=ValidatorDisciplina(repod)
        try:
            val.validare(Disciplina(noul_id,"orice","orice"))
            if val.get_erori()!=[]: raise ValidareEsuata
            self.__modif.modificare_disciplina_id_catalog(repod, repon, id, noul_id)
            #cat.set_disciplina_id(id, Disciplina(noul_id,"orice","orice"))
        except ValueError:
            print("Id-ul este scris gresit")
        except IdNegasit:
            print("Id-ul nu se afla in baza de date")
        except ValidareEsuata:
            for el in val.get_erori():
                print(el)      
    
    def modificare_disciplina_nume(self,repod,repon):
        """
        Aceasta functie se ocupa cu modificarea numelui unei discipline
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        ValidareEsuata apare atunci cand obiectul nu este valid
        """
        id=input("Introduceti ID-ul disciplinei : ")
        noul_nume=input("Introduceti noul nume al disciplinei : ") 
        val=ValidatorDisciplina(repod)
        try:
            val.validare(Disciplina(10000010000010001000,noul_nume,"orice"))
            if val.get_erori()!=[]: raise ValidareEsuata
            self.__modif.modificare_disciplina_nume_catalog(repod, repon, id, noul_nume)
        except ValueError:
            print("Id-ul este scris gresit")
        except IdNegasit:
            print("Id-ul nu se afla in baza de date")
        except ValidareEsuata:
            for el in val.get_erori():
                print(el)
            
    def modificare_disciplina_profesor(self,repod,repon):
        """
        Aceasta functie se ocupa cu modificarea profesorului unei discipline
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        ValidareEsuata apare atunci cand obiectul nu este valid
        """
        id=input("Introduceti ID-ul disciplinei : ")
        noul_nume=input("Introduceti noul profesor al disciplinei : ") 
        val=ValidatorDisciplina(repod)
        try:
            val.validare(Disciplina(10000010000010001000,"orice",noul_nume))
            if val.get_erori()!=[]: raise ValidareEsuata
            self.__modif.modificare_disciplina_profesor_catalog(repod, repon, id, noul_nume)
        except ValueError:
            print("Id-ul este scris gresit")
        except IdNegasit:
            print("Id-ul nu se afla in baza de date")
        except ValidareEsuata:
            for el in val.get_erori():
                print(el)
            
        
class Filtrare:
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        self.__fis=Afisare()
        self.__filt=Filtrare_Catalog()
    def defineste_meniu(self):
        """
        Aceasta functie se ocupa cu formarea unui dictionar meniu care va stoca atat functionalitatile cat si mesajele aferente
        Nu are niciun parametru
        Returneaza meniuul cerut mai sus
        """ 
        meniu={}
        meniu["1"]=(self.meniu_sortare_alfabetica,"->pentru a sorta studentii in ordine alfabetica")
        meniu["2"]=(self.meniu_sortare_medie,"->pentru a sorta studentii dupa medie")
        #meniu["3"]=(meniu_filtrare,"->pentru a filtra ctalogul")
        #meniu["4"]=(meniu_statistici,"->pentru a afisa statistici")
        meniu["3"]=(None,"->pentru a iesi")
        return meniu
    
    def mesaj_intrare(self,meniu):
        """
        Aceasta functie se ocupa cu conceperea unui mesaj de intrare pt utilizator
        meniu-de tip dictionar
        Nu returneaza nimic
        """
        mesaj_text="Introducetiuna dintre optiunile de mai jos:\n"
        for poz,el in enumerate(meniu):
            #print(poz)
            #print(meniu[str(poz+1)])
            mesaj_text+=str(poz+1)+meniu[str(poz+1)][1]+"\n"
        mesaj_text+="Optiunea dumneavoastra : "
        return mesaj_text
    
    def meniu_filtrare(self,repos,repod,repon):
        """
        Aceasta functie reprezinta meniul principal al aplicatiei
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        gata=True
        #print("Afiseaza in Cristosul Matii")
        meniu=self.defineste_meniu()
        while gata:
            try:
                optiune=input(self.mesaj_intrare(meniu))
                if meniu[optiune][0]==None:
                    gata=False
                else:
                    functie=meniu[optiune][0]
                    #print(functie)
                    functie(repos,repod,repon)
            except KeyError as m:
                print("Optiune invalida")
                
    def meniu_sortare_alfabetica(self,repos,repod,repon):
        """
        Aceasta functie se ocupa cu ordonarea alfabetica a studentilor
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        lista=[]
        #print("da")
        lista=self.__filt.ordonare_dupa_nume(repos,repod,repon)
        #print(lista)
        sir=self.__fis.afisare_sortare_nume(lista)
        print(sir)
        
    def meniu_sortare_medie(self,repos,repod,repon):
        """
        Aceasta functie se ocupa cu ordonarea crescatoare a studentilor dupa medie
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        lista=[]
        lista=self.__filt.ordonare_dupa_medie(repos,repod,repon)
        sir=self.__fis.afisare_sortare_medie(repos,lista)
        print(sir)

    
class Statistici:
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        self.__fis=Afisare()
        self.__filt=Filtrare_Catalog()
        self.__stat=Statistica_Catalog()
    def defineste_meniu(self):
        """
        Aceasta functie se ocupa cu formarea unui dictionar meniu care va stoca atat functionalitatile cat si mesajele aferente
        Nu are niciun parametru
        Returneaza meniuul cerut mai sus
        """ 
        meniu={}
        meniu["1"]=(self.meniu_sortare_alfabetica_disciplina,"->pentru a sorta studentii in ordine alfabetica la o anumita disciplina")
        meniu["2"]=(self.meniu_sortare_medie_disciplina,"->pentru a sorta studentii dupa medie la o anumita disciplina")
        meniu["3"]=(self.meniu_sortare_medie_stud,"->pentru a afisa pe primii 20% dintre studenti ")
        meniu["4"]=(self.mediu_sortare_profesori,"->pentru a afisa lista descrescatoare a profesorilor dupa medie")
        meniu["5"]=(None,"->pentru a iesi")
        return meniu
    
    def mesaj_intrare(self,meniu):
        """
        Aceasta functie se ocupa cu conceperea unui mesaj de intrare pt utilizator
        meniu-de tip dictionar
        Nu returneaza nimic
        """
        mesaj_text="Introducetiuna dintre optiunile de mai jos:\n"
        for poz,el in enumerate(meniu):
            #print(poz)
            #print(meniu[str(poz+1)])
            mesaj_text+=str(poz+1)+meniu[str(poz+1)][1]+"\n"
        mesaj_text+="Optiunea dumneavoastra : "
        return mesaj_text
    
    def meniu_statistica(self,repos,repod,repon):
        """
        Aceasta functie reprezinta meniul principal al aplicatiei
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        gata=True
        #print("Afiseaza in Cristosul Matii")
        meniu=self.defineste_meniu()
        while gata:
            try:
                optiune=input(self.mesaj_intrare(meniu))
                if meniu[optiune][0]==None:
                    gata=False
                else:
                    functie=meniu[optiune][0]
                    #print(functie)
                    functie(repos,repod,repon)
            except KeyError as m:
                print(m)
                print("Optiune invalida")
                
    def meniu_sortare_alfabetica_disciplina(self,repos,repod,repon):
        """
        Aceasta functie se ocupa cu ordonarea alfabetica a studentilor la o anumita disciplina
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        sir=""
        verdict=[]
        id=input("Introduceti ID-ul disciplinei :")
        try:
            sir=self.__stat.ordonare_dupa_nume(repos, repod, repon, id)
            verdict=[any for char in sir if char.isalpha()]
        except ValueError:
            print("Id-ul nu este scris corect")
        except IdNegasit:
            print("Id-ul nu se afla in baza de date")
        if verdict==[]:
            print("Nu exista suficiente informatii pentru aceasta disciplina")
        else:
            print(sir)
        
    def meniu_sortare_medie_disciplina(self,repos,repod,repon):
        """
        Aceasta functie se ocupa cu ordonarea descrescatoare a studentilor dupa medie la o anumita disciplina
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        sir=""
        verdict=[]
        id=input("Introduceti ID-ul disciplinei :")
        try:
            sir=self.__stat.ordonare_dupa_medie(repos, repod, repon, id)
            verdict=[any for char in sir if char.isalpha()]
            #print(verdict)
            #print(sir)
        except ValueError:
            print("Id-ul nu este scris corect")
        except IdNegasit:
            print("Id-ul nu se afla in baza de date")
        if verdict==[]:
            print("Nu exista suficiente informatii pentru aceasta disciplina")
        else:
            print(sir)
    
    def mediu_sortare_profesori(self,repos,repod,repon):
        lista=[]
        sir=self.__stat.ordonare_profesori_medie(repos, repod, repon)
        print(sir)
        
        
    def meniu_sortare_medie_stud(self,repos,repod,repon):
        """
        Aceasta functie se ocupa cu ordonarea descrescatoare a studentilor dupa medie si afisarea a primilor 20% dintre acestia
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        lista=[]
        lista=self.__filt.ordonare_dupa_medie(repos,repod,repon)
        #lista.reverse()
        #print(lista)
        lungime=len(lista)//5
        #print(lungime)
        while len(lista)>lungime:
            lista.pop()
        #lista.reverse()
        sir=self.__fis.afisare_sortare_medie(repos,lista)
        print(sir)


class Generare:
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        self.__rand=Random_Ex()
        #self.__afis=Afisare()
    def defineste_meniu(self):
        """
        Aceasta functie se ocupa cu formarea unui dictionar meniu care va stoca atat functionalitatile cat si mesajele aferente
        Nu are niciun parametru
        Returneaza meniuul cerut mai sus
        """ 
        meniu={}
        
        meniu["1"]=(self.generare_studenti,"->pentru a genera un anumit numar de studenti")
        meniu["2"]=(self.generare_discipline,"->pentru a genera un anumit numar de discipline")
        #meniu["3"]=(self.__filt.meniu_filtrare,"->pentru a filtra catalogul")
        #meniu["4"]=(self.meniu_generare,"->pentru a afisa statistici")
        #meniu["4"]=(meniu_statistici,"->pentru a afisa statistici")
        meniu["3"]=(None,"->pentru a iesi")
        return meniu
        
    def mesaj_intrare(self,meniu):
            """
            Aceasta functie se ocupa cu conceperea unui mesaj de intrare pt utilizator
            meniu-de tip dictionar
            Nu returneaza nimic
            """
            mesaj_text="Introducetiuna dintre optiunile de mai jos:\n"
            for poz,el in enumerate(meniu):
                #print(poz)
                #print(meniu[str(poz+1)])
                mesaj_text+=str(poz+1)+meniu[str(poz+1)][1]+"\n"
            mesaj_text+="Optiunea dumneavoastra : "
            return mesaj_text
        
    def meniu_generare(self,repos,repod,repon):
            """
            Aceasta functie reprezinta meniul principal al aplicatiei
            repos-de tip StudentRepository
            repod-de tip DisciplinaRepository
            repon-de tip NotaRepository
            Nu returneaza nimic
            """
            gata=True
            #print("Afiseaza in Cristosul Matii")
            meniu=self.defineste_meniu()
            while gata:
                try:
                    optiune=input(self.mesaj_intrare(meniu))
                    if meniu[optiune][0]==None:
                        gata=False
                    else:
                        functie=meniu[optiune][0]
                        #print(functie)
                        functie(repos,repod,repon)
                except KeyError:
                    print("Optiune invalida")
    
    def generare_studenti(self,repos,repod,repon):
        """
        Aceasta functie se ocupa de generearea unui anumit numar de studenti
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        numar=int(input("Introduceti numarul de studenti generati : "))
        sir=self.__rand.generare_studenti(repos, numar)
        print(sir)
        #self.__afis.afisare_lista_studenti(cat)
        
    def generare_discipline(self,repos,repod,repon):
        """
        Aceasta functie se ocupa de generearea unui anumit numar de discipline
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Nu returneaza nimic
        """
        numar=int(input("Introduceti numarul de discipline generate : "))
        sir=self.__rand.generare_discipline(repod,numar)
        print(sir)
        #self.__afis.afisare_lista_discipline(cat)

    
    