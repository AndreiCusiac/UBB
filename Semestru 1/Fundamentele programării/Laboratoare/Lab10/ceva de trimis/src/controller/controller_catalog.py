'''
Created on 17 nov. 2020

@author: Raposatu
'''
from random import randint,randrange
from repository.repository_baza_de_date import *
from domain.clase import *
from utils.exceptii import IdNegasit, NotaNecorespunzatoare,IdNegativ
from domain.validator import *
from utils.sortare import Utils
from repository.repository_salvare_citire import Fisier
#fis=Fisier()
class Adaugare_Catalog:
    #def __init__(self):
        #self.__repos=StudentRepository()
        #self.__repod=DisciplinaRepository()
        #self.__repn=NotaRepository()
    def adauga_student_catalog(self,repos,id,nume):
        """
        Aceasta functie se ocupa cu adaugarea unui student
        repos-de tip StudentRepository
        id-de tip int
        nume-de tip string
        Nu returneaza nimic
        """
        repos.adauga_student(Student(id,nume))
        #fis.salveaza_studenti(repos, "studenti.txt")
        #cat.adauga_studenti(Student(id,nume))
    def adauga_disciplina_catalog(self,repod,id,nume,profesor):
        """
        Aceasta functie se ocupa cu adaugarea unei discipline
        repod-de tip DisciplinaRepository
        id-de tip int
        nume-de tip string
        profesor-de tip string
        Nu returneaza nimic
        """
        repod.adauga_disciplina(Disciplina(id,nume,profesor))
        #fis.salveaza_discipline(repod, "discipline.txt")
        #cat.adauga_disciplina(Disciplina(id,nume,profesor))
        
    def adauga_nota_catalog(self,repos,repod,repon,id_stud,id_disc,nota):
        """
        Aceasta functie se ocupa cu adaugarea unei note
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        id_stud,id_disc,nota-de tip int
        Nu returneaza nimic
        NotaNecorespunzatoare atunci cand nota nu e cuprinsa in intervalul [1,10]
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id_stud=int(id_stud)
        id_disc=int(id_disc)
        nota=int(nota)
        if nota<1 or nota>10: raise NotaNecorespunzatoare
        stud=repos.cauta_student(id_stud)
        disc=repod.cauta_disciplina(id_disc)
        if stud==None or disc==None: raise IdNegasit
        repon.adauga_nota(Nota(stud,disc,nota))
        #fis.salveaza_note(repon, "note.txt")
        
class Cautare_Catalog:
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        self.__fis=Afisare()
    def afisare_student(self,repos,id):
        """
        Aceasta functie se ocupa cu cautarea unui student
        repos-de tip StudentRepository
        id-de tip int
        Returneaza obiectul student
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id=int(id)
        stud=repos.cauta_student(id)
        #try:
        if stud==None:raise IdNegasit
        #except TypeError:
        return stud
        
    def afisare_disciplina(self,repod,id):
        """
        Aceasta functie se ocupa cu cautarea unui student
        repod-de tip DisciplinaRepository
        id-de tip int
        Returneaza obiectul disciplina
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id=int(id)
        disc=repod.cauta_disciplina(id)
        #try:
        if disc==None: raise IdNegasit
        #except TypeError:
        return disc 
        
class Afisare:
    
    def afisare_catalog(self,repos,repod,repon):
        """
        Aceasta functie se ocupa de afisarea eleganta a catalogului
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Returneaza un sir ce contine catalogul
        """
        
        stud_anal=[]
        sir="\n"
        for element in repon.get_lista_note():
            if element.get_id_stud() not in stud_anal:
                stud_anal.append(element.get_id_stud())
                sir_index=self.afisare_date_student(repos,repod,repon,element.get_student())
                sir+=sir_index
        return sir
        
        
    def afisare_lista_studenti(self,repos):
        """
        Aceasta functie se ocupa de afisarea eleganta a unei liste de studenti
        repos-de tip StudentRepository
        Returneaza un sir ce contine lista de studenti
        """
        sir="\n"
        for element in repos.get_lista_studenti():
            sir=sir+ "Studentul cu ID-ul "+ str(element.get_id())+" are numele " + element.get_nume()+ "\n"
        return sir 
    
    def afisare_lista_discipline(self,repod):     
        """
        Aceasta functie se ocupa de afisarea eleganta a unei liste de discipline
        repod-de tip DisciplinaRepository
        Returneaza un sir ce contine lista de discipline
        """
        sir="\n"
        for element in repod.get_lista_discipline():
            sir=sir+ "Disciplina cu ID-ul "+ str(element.get_id())+" are numele " + element.get_nume()+ " si este predata de "+ element.get_profesor()+"\n"
        return sir 
        
    
    def afisare_date_student(self,repos,repod,repon,student):
        """
        Aceasta functie se ocupa de afisarea eleganta a unui student
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        student- de tip Student
        Returneaza un sir ce contine date despre student
        """
        sir="\n"
        discipline_analizate=[]
        sir=sir+"Studentul cu ID-ul "+str(student.get_id()) +" are numele "+ student.get_nume()+" si are urmatoarea situatie :\n"
        for element in repon.get_lista_note():
            if element.get_student()==student and element.get_id_disc() not in discipline_analizate:
                discipline_analizate.append(element.get_id_disc())
                sir_note=""
                for element2 in repon.get_lista_note():
                    if element2.get_disciplina()==element.get_disciplina() and element2.get_student()==student:
                        sir_note+=str(element2.get_valoare())+" "
                sir=sir+ "La disciplina "+ element.get_disciplina().get_nume() +" predata de " + element.get_disciplina().get_profesor()+" are urmatoarele note : "+sir_note+ "\n"
        return sir
         
        
    def afisare_date_disciplina(self,repos,repod,repon,disciplina):
        """
        Aceasta functie se ocupa de afisarea eleganta a unei discipline
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        disciplina- de tip Disciplina
        Returneaza un sir ce contine date despre disciplina
        """
        sir="\n"
        studenti_analizati=[]
        sir=sir+"Disciplina cu ID-ul"+str(disciplina.get_id()) +" are numele "+ disciplina.get_nume()+" si este predata de "+ disciplina.get_profesor()+ " are urmatoarea situatie :\n"
        for element in repon.get_lista_note():
            if element.get_disciplina()==disciplina and element.get_id_stud() not in studenti_analizati:
                studenti_analizati.append(element.get_id_stud())
                sir_note=""
                for element2 in repon.get_lista_note():
                    if element2.get_student()==element.get_student() and element2.get_disciplina()==disciplina:
                        sir_note+=str(element2.get_valoare())+" "
                sir=sir+ "Studentul  "+ element.get_student().get_nume() +" are urmatoarele note : "+sir_note+ "\n"
        return sir
         
    def afisare_sortare_nume(self,lista):
        """
        Aceasta functie se ocupa de afisarea in ordine crescatoare a uei liste
        lista- o lista de disctionare
        Returneaza un sir ce contine afisarea eleganta a acelei liste
        """
        sir="\n"
        for element in lista:
            sir+="Studentul cu ID-ul "+ str(get_id(element))+" are numele "+ get_nume(element)+"\n"
        return sir
        
    def afisare_sortare_medie(self,repos,lista):
        """
        Aceasta functie se ocupa de afisarea in ordine descrescatoare a uei liste
        lista- o lista de disctionare
        Returneaza un sir ce contine afisarea eleganta a acelei liste
        """
        sir="\n"
        #lista.reverse()
        for element in lista:
            for stud in repos.get_lista_studenti():
                if get_id(element)==stud.get_id():
                    sir+="Studentul cu numele "+ stud.get_nume()+" are media "+ str(get_nota(element))+"\n"
        return sir
    
    def afisare_sortare_medie_profesori(self,lista):
        """
        Aceasta functie se ocupa de afisarea in ordine descrescatoare a uei liste
        lista- o lista de disctionare
        Returneaza un sir ce contine afisarea eleganta a acelei liste
        """
        sir="\n"
        #lista.reverse()
        for element in lista:
                    sir+="Profesorul cu numele "+ get_id(element)+" are media "+ str(get_nota(element))+"\n"
        return sir       
    
    def afisare_generare_student(self,id,nume):
        """
        Aceasta functie se ocupa de afisarea unui student generat
        id-de tip int
        nume-de tip string
        Returneaza un sir ce contine afisarea eleganta a acelei student
        """
        sir=""
        sir="Student generat cu id "+str(id)+" si cu numele "+nume
        return sir
        
    def afisare_generare_disciplina(self,id,nume,nume_prof):
        """
        Aceasta functie se ocupa de afisarea unei discipline generate
        id-de tip int
        nume-de tip string
        profesor-de tip string
        Returneaza un sir ce contine afisarea eleganta a acelei discipline
        """
        sir=""
        sir="Disciplina generata cu id "+str(id)+", cu numele "+nume+" si predata de "+nume_prof
        return sir
        
class Stergere_Catalog():
    def sterge_student_catalog(self,repos,repon,id):
        """
        Aceasta functie se ocupa cu stergerea unui student dupa id
        repos-de tip StudentRepository
        repon-de tip NotaRepository
        id-de tip int
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id=int(id)
        stud=repos.cauta_student(id)
        if stud==None: raise IdNegasit 
        repos.sterge_student(id)
        for nota in repon.get_lista_note():
            if nota.get_id_stud()==id:
                repon.sterge_nota(id,nota.get_id_disc(),nota.get_valoare())
        
        #fis.salveaza_studenti(repos, "studenti.txt")
        #fis.salveaza_note(repon, "note.txt")
        
        
    def sterge_disciplina_catalog(self,repod,repon,id):
        """
        Aceasta functie se ocupa cu stergerea unei discipline dupa id
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        id-de tip int
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id=int(id)
        disc=repod.cauta_disciplina(id)
        if disc==None: raise IdNegasit 
        repod.sterge_disciplina(id)
        for nota in repon.get_lista_note():
            if nota.get_id_disc()==id:
                repon.sterge_nota(nota.get_id_stud(),id,nota.get_valoare())
        #fis.salveaza_discipline(repod, "discipline.txt")
        #fis.salveaza_note(repon, "note.txt")
class Modificare_Catalog:
    def modificare_student_id_catalog(self,repos,repon,id,noul_id):
        """
        Aceasta functie se ocupa cu modificarea id-ului unui student
        repos-de tip StudentRepository
        repon-de tip NotaRepository
        id, noul_id-de tip int
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id=int(id)
        noul_id=int(noul_id)
        #if id<0 or noul_id<0: raise IdNegativ
        stud=repos.cauta_student(id)
        if stud==None: raise IdNegasit 
        #print(stud.get_tot_student())
        repos.sterge_student(id)
        stud.set_id(noul_id)
        repos.adauga_student(stud)
        for nota in repon.get_lista_note():
            if nota.get_id_stud()==id:
                nota.set_student(stud)
        #fis.salveaza_studenti(repos, "studenti.txt")
        #fis.salveaza_note(repon, "note.txt")
        
    def modificare_student_nume_catalog(self,repos,repon,id,noul_nume):
        """
        Aceasta functie se ocupa cu modificarea numelui unui student
        repos-de tip StudentRepository
        repon-de tip NotaRepository
        id-de tip int
        noul_nume- de tip string
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id=int(id)
        stud=repos.cauta_student(id)
        if stud==None: raise IdNegasit 
        repos.sterge_student(id)
        stud.set_nume(noul_nume)
        repos.adauga_student(stud)
        for nota in repon.get_lista_note():
            if nota.get_id_stud()==id:
                nota.set_student(stud)
        #fis.salveaza_studenti(repos, "studenti.txt")
        
    def modificare_disciplina_id_catalog(self,repod,repon,id,noul_id):
        """
        Aceasta functie se ocupa cu modificarea id-ului unei discipline
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        id, noul_id-de tip int
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id=int(id)
        noul_id=int(noul_id)
        #if id<0 or noul_id<0: raise IdNegativ
        disc=repod.cauta_disciplina(id)
        if disc==None: raise IdNegasit 
        repod.sterge_disciplina(id)
        disc.set_id(noul_id)
        repod.adauga_disciplina(disc)
        for nota in repon.get_lista_note():
            if nota.get_id_disc()==id:
                nota.set_disciplina(disc)
        #fis.salveaza_discipline(repod, "discipline.txt")
        #fis.salveaza_note(repon, "note.txt")
    def modificare_disciplina_nume_catalog(self,repod,repon,id,noul_nume):
        """
        Aceasta functie se ocupa cu modificarea numelui unei discipline
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        id-de tip int
        noul_nume- de tip string
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id=int(id)
        disc=repod.cauta_disciplina(id)
        if disc==None: raise IdNegasit 
        repod.sterge_disciplina(id)
        disc.set_nume(noul_nume)
        repod.adauga_disciplina(disc)
        for nota in repon.get_lista_note():
            if nota.get_id_disc()==id:
                nota.set_disciplina(disc)
        #fis.salveaza_discipline(repod, "discipline.txt")        
    def modificare_disciplina_profesor_catalog(self,repod,repon,id,noul_nume):
        """
        Aceasta functie se ocupa cu modificarea profesorului unei discipline
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        id-de tip int
        noul_nume- de tip string
        Nu returneaza nimic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id=int(id)
        disc=repod.cauta_disciplina(id)
        if disc==None: raise IdNegasit 
        repod.sterge_disciplina(id)
        disc.set_profesor(noul_nume)
        repod.adauga_disciplina(disc)
        for nota in repon.get_lista_note():
            if nota.get_id_disc()==id:
                nota.set_disciplina(disc)
        #fis.salveaza_discipline(repod, "discipline.txt")

class Filtrare_Catalog:
    
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        self.__utils=Utils()
    def ordonare_dupa_nume(self,repos,repod,repon,key="catalog"):
        """
        Aceasta functie se ocupa cu ordonarea alfabetica a studentilor
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        key- este optional si poate fi de tip string sau int
        Returneaza lista studentilor ordonata alfabetic
        """
        lista=[]
        stud_anal=[]
        if key=="catalog":
            for element in repon.get_lista_note():
                if element.get_id_stud() not in stud_anal:
                    stud_anal.append(element.get_id_stud())
                    lista_intermediara={}
                    set_id(lista_intermediara,element.get_id_stud())
                    set_nume(lista_intermediara,element.get_student().get_nume())
                    lista.append(lista_intermediara)
            lista_nota=[]
            situatie_medii=self.creeaza_medii_generale(repos,repod,repon)
            for element in situatie_medii:
                lista_intermediara={}
                set_id(lista_intermediara,element)
                set_nota(lista_intermediara,situatie_medii[element])
                lista_nota.append(lista_intermediara)
        else:
            for element in repon.get_lista_note():
                if element.get_id_disc()==key and element.get_id_stud() not in stud_anal:
                    stud_anal.append(element.get_id_stud())
                    lista_intermediara={}
                    set_id(lista_intermediara,element.get_id_stud())
                    set_nume(lista_intermediara,element.get_student().get_nume())
                    lista.append(lista_intermediara)
            lista_nota=[]     
            situatie_medii=self.creeaza_medie(repos,repod,repon)
            for student in situatie_medii:
                for disciplina in situatie_medii[student]:
                    if disciplina==key:
                        lista_intermediara={}
                        set_id(lista_intermediara,student)
                        set_nota(lista_intermediara,situatie_medii[student][disciplina])
                        lista_nota.append(lista_intermediara)
        #print(lista)
        self.__utils.sortare_bubble_criterii(lista,lista_nota,get_nume,get_nota)
        #self.__utils.sortare_bubble(lista,get_nume)
        return lista.copy()
        
    
    def ordonare_dupa_medie(self,repos,repod,repon,key="catalog"):
        """
        Aceasta functie se ocupa cu ordonarea crescatoare a studentilor dupa medie
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        key- este optional si poate fi de tip string sau int
        Returneaza lista studentilor ordonata crescator dupa medie
        """
        lista=[]
        if key=="catalog":
            situatie_medii=self.creeaza_medii_generale(repos,repod,repon)
            for element in situatie_medii:
                lista_intermediara={}
                set_id(lista_intermediara,element)
                set_nota(lista_intermediara,situatie_medii[element])
                lista.append(lista_intermediara)
            lista_nume=[]
            for element in situatie_medii:
                lista_intermediara={}
                set_id(lista_intermediara,element)
                set_nume(lista_intermediara,repos.cauta_student(element).get_nume())
                lista_nume.append(lista_intermediara)
        else:
            situatie_medii=self.creeaza_medie(repos,repod,repon)
            for student in situatie_medii:
                for disciplina in situatie_medii[student]:
                    if disciplina==key:
                        lista_intermediara={}
                        set_id(lista_intermediara,student)
                        set_nota(lista_intermediara,situatie_medii[student][disciplina])
                        lista.append(lista_intermediara)
            lista_nume=[]
            for student in situatie_medii:
                for disciplina in situatie_medii[student]:
                    if disciplina==key:
                        lista_intermediara1={}
                        set_id(lista_intermediara1,student)
                        set_nume(lista_intermediara1,repos.cauta_student(student).get_nume())
                        lista_nume.append(lista_intermediara1)
        self.__utils.sortare_bubble_criterii(lista,lista_nume,get_nota,get_nume,reverse=True)
        #self.__utils.sortare_bubble(lista,get_nota,reverse=True)
        return lista.copy()
    
    def creeaza_medie(self,repos,repod,repon):
        """
        Aceasta functie se ocupa cu generarea mediilor la toate disciplinele
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Returneaza un disctionar de dictionare de liste 
        """
        medii_elevi={}
        situatie_elev={}
        #stud_anal=[]
        #disc_anal=[]
        for student in repos.get_lista_studenti():
            situatie_elev={}
            for disciplina in repod.get_lista_discipline():
                situatie=[]
                for nota in repon.get_lista_note():
                    if nota.get_student()==student and nota.get_disciplina()==disciplina:
                        situatie.append(nota.get_valoare())
                try:
                    medie=self.__utils.media(situatie)
                except ZeroDivisionError:
                    medie=0
                situatie_elev[disciplina.get_id()]=medie
            medii_elevi[student.get_id()]=situatie_elev
        return medii_elevi
            
    def creeaza_medii_generale(self,repos,repod,repon):
        """
        Aceasta functie se ocupa cu generarea mediilor generale
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        Returneaza un dictionar
        """
        situatie_medii={}
        situatie_medii=self.creeaza_medie(repos,repod,repon)
        medii_generale={}
        lista=[]
        for student in situatie_medii:
            lista=[]
            for disciplina in situatie_medii[student]:
                lista.append(situatie_medii[student][disciplina])
            try:
                medie=self.__utils.media(lista)
            except ZeroDivisionError:
                medie=0
            medii_generale[student]=medie
        return medii_generale
    
    def creeaza_medie_profesori(self,repos,repod,repon):
        medii_discipline={}
        situatie_disciplina={}
        #stud_anal=[]
        #disc_anal=[]
        for disciplina in repod.get_lista_discipline():
            situatie_disciplina={}
            for student in repos.get_lista_studenti():
                situatie=[]
                for nota in repon.get_lista_note():
                    if nota.get_student()==student and nota.get_disciplina()==disciplina:
                        situatie.append(nota.get_valoare())
                try:
                    medie=self.__utils.media(situatie)
                except ZeroDivisionError:
                    medie=0
                situatie_disciplina[student.get_id()]=medie
            medii_discipline[disciplina.get_id()]=situatie_disciplina
        return medii_discipline
        
    def creeaza_medii_generale_profesori(self,repos,repod,repon):
        situatie_medii={}
        situatie_medii=self.creeaza_medie_profesori(repos,repod,repon)
        medii_generale={}
        medie_profi={}
        lista=[]
        #print(situatie_medii)
        for disciplina in situatie_medii:
            lista=[]
            for student in situatie_medii[disciplina]:
                lista.append(situatie_medii[disciplina][student])
            #print(lista)
            try:
                medie=self.__utils.media(lista)
            except ZeroDivisionError:
                medie=0
            medii_generale[disciplina]=medie
        return medii_generale
    
    def creeaza_medii_gerenale_profesori_complet(self,repos,repod,repon):
        medii_generale={}
        medii_generale=self.creeaza_medii_generale_profesori(repos, repod, repon)
        medie_profi={}
        lista_profi=[]
        disc_profi={}
        prof_anal=[]
        situatie_profi=[]
        #print(medii_generale)
        for disc in medii_generale:
            disciplina=repod.cauta_disciplina(disc)
            prof=disciplina.get_profesor()
            if prof not in prof_anal:
                prof_anal.append(prof)
                medie_profi[prof]=[]
                medie_profi[prof].append(medii_generale[disc])
            else:
                medie_profi[prof].append(medii_generale[disc])
                #situatie_profi.append()
        #print(medie_profi)
        for prof in medie_profi:
            disc_profi={}
            try:
                medie=self.__utils.media(medie_profi[prof])
            except ZeroDivisionError:
                medie=0
            #print(medie)
            set_id(disc_profi,prof)
            set_nota(disc_profi,medie)
            lista_profi.append(disc_profi)
            
        return lista_profi
    
class Statistica_Catalog:
    
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        self.__afis=Afisare()
        self.__filt=Filtrare_Catalog()
        self.__utils=Utils()
    def ordonare_dupa_nume(self,repos,repod,repon,id):
        """
        Aceasta functie se ocupa cu ordonarea alfabetica a studentilor la o anumita materie
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        id- nota-de tip int
        Returneaza lista studentilor ordonata alfabetic
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id=int(id)
        disc=repod.cauta_disciplina(id)
        if disc==None:raise IdNegasit
        lista=[]
        #print("da")
        lista=self.__filt.ordonare_dupa_nume(repos,repod,repon,key=id)
        #print(lista)
        sir=self.__afis.afisare_sortare_nume(lista)
        return sir
        
    
    def ordonare_dupa_medie(self,repos,repod,repon,id):
        """
        Aceasta functie se ocupa cu ordonarea crescatoare a studentilor dupa medie la o anumita materie
        repos-de tip StudentRepository
        repod-de tip DisciplinaRepository
        repon-de tip NotaRepository
        id- nota-de tip int
        Returneaza lista studentilor ordonata crescator dupa medie
        ValueError daca id-ul sau nota contine litere
        IdNegasit daca un id nu se afla in baza de date
        """
        id=int(id)
        disc=repod.cauta_disciplina(id)
        #print(disc)
        if disc==None:raise IdNegasit
        lista=[]
        lista=self.__filt.ordonare_dupa_medie(repos,repod,repon,key=id)
        #print(lista)
        index=0
        gata=True
        while gata:
            if get_nota(lista[index])==0:
                lista.pop(index)
            else:index+=1
            if index==len(lista):gata=False
            
        #print(lista)
        sir=self.__afis.afisare_sortare_medie(repos,lista)
        return sir
        #print(sir)
        
    def ordonare_profesori_medie(self,repos,repod,repon):
        lista=self.__filt.creeaza_medii_gerenale_profesori_complet(repos,repod,repon)
        #print(lista)
        self.__utils.sortare_shell(lista, get_nota,reverse=True)
        #print(lista)
        sir=self.__afis.afisare_sortare_medie_profesori(lista)
        #print(sir)
        return sir
    
class Random_Ex:
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        self.__afis=Afisare()
    def generare_studenti(self,repos,numar):
        """
        Aceasta functie genereaza un anumit numar de studenti
        repos-de tip StudentRepository
        numar- nota-de tip int
        Returneaza studentii generati intr o forma eleganta
        """
        cate=0
        sir=""
        while cate<numar:
            nr_lit=randint(1,50)
            nume=""
            for contor in range(1,nr_lit+1):
                nume+=chr(randrange(26)+ord('a'))
            nr_cif=randint(1,30)
            id=""
            for contor in range(1,nr_cif+1):
                id+=chr(randrange(10)+ord('0'))
            id=int(id)
            vals=ValidatorStudent(repos)
            vals.validare(Student(id,nume))
            if vals.get_erori()==[]:
                repos.adauga_student(Student(id,nume))
                sir_nou=self.__afis.afisare_generare_student(id, nume)
                sir+=sir_nou+'\n'
                #cat.afisare_date_student(id)
                cate+=1
        return sir
    
    def generare_discipline(self,repod,numar):
        """
        Aceasta functie genereaza un anumit numar de discipline
        repod-de tip DisciplinaRepository
        numar- nota-de tip int
        Returneaza disciplinele generate intr o forma eleganta
        """
        cate=0
        sir=""
        while cate<numar:
            nr_lit=randint(1,50)
            nume=""
            for contor in range(1,nr_lit+1):
                nume+=chr(randrange(26)+ord('a'))
            nr_cif=randint(1,30)
            id=""
            for contor in range(1,nr_cif+1):
                id+=chr(randrange(10)+ord('0'))
            id=int(id)
            nr_lit=randint(1,50)
            nume_prof=""
            for contor in range(1,nr_lit+1):
                nume_prof+=chr(randrange(26)+ord('a'))
            vald=ValidatorDisciplina(repod)
            vald.validare(Disciplina(id,nume,nume_prof))
            if vald.get_erori()==[]:
                repod.adauga_disciplina(Disciplina(id,nume,nume_prof))
                sir_nou=self.__afis.afisare_generare_disciplina(id, nume, nume_prof)
                sir+=sir_nou+'\n'
                #cat.afisare_date_student(id)
                cate+=1
        return sir
        
        
        
        