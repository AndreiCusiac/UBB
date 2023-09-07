'''
Created on 3 dec. 2020

@author: Raposatu
'''
from domain.clase import *

class StudentRepositoryNou:
    def __init__(self,fis):
        """
        Aceasta functie prestabilieste niste valori implicite
        Nu are parametri
        Nu returneaza nimic
        """
        self.__fis=fis
        self.__lista_studenti=[]
    def adauga_student(self,other):
        """
        Aceasta functie adauga un student in baza de date
        other- de tip Student
        Nu returneaza nimic
        """
        #self.__lista_studenti.append(other)
        with open(self.__fis,'a') as fisier:
            fisier.write(str(other.get_id())+":"+other.get_nume()+"\n")
        
    def sterge_student(self,id):
        """
        Aceasta functie sterge un student din baza de date
        id-de tip int
        Nu returneaza nimic
        """
        fisier_aux=open("aux_stud.txt","w")
        stud_sterge=self.cauta_student(id)
        if stud_sterge!=None:
            with open(self.__fis,'r') as fisier:
                #numar=int(fisier.readline())
                for linie in fisier:
                    if linie!="":
                        date=linie
                        date=date.split(":")
                        stud=Student(int(date[0]),date[1][:-1])
                        if stud!=stud_sterge:
                            fisier_aux.write(str(stud.get_id())+":"+stud.get_nume()+"\n")
        fisier_aux.close()
        self.copiaza_fisier()
    def cauta_student(self,id):
        """
        Aceasta functie cauta un student in baza de date
        id-de tip int
        Returneaza obiectul student sau none in cazul in care nu se afla in baza de date
        """
        nume=""
        with open(self.__fis,'r') as fisier:
            #numar=int(fisier.readline())
            for linie in fisier:
                if linie!="":
                    date=linie
                    date=date.split(":")
                    if int(date[0])==id:
                        nume=date[1]
                        break
            if nume=="": return None
            else: return Student(id,nume[:-1])                        
    
    def get_lista_studenti(self):
        """
        Aceasta functie returneaza lista studentilor care sunt in baza de date
        Nu are parametri
        Returneaza o lista de obiecte
        """
        #self.__lista_studenti=[]
        lista_studenti=[]
        with open(self.__fis,'r') as fisier:
            #numar=int(fisier.readline())
            for linie in fisier:
                if linie!="":
                    date=linie
                    date=date.split(":")
                    stud=Student(int(date[0]),date[1][:-1])
                    lista_studenti.append(stud)
                    
        return lista_studenti
    
    def copiaza_fisier(self):
        """
        Aceasta functie se ocupa cu transferul de date intre 2 fisiere
        Nu are parametri
        Nu returneaza nimic
        """
        fisier_aux=open("aux_stud.txt","r")
        fisier=open(self.__fis,"w")
        for linie in fisier_aux:
            fisier.write(linie)
        fisier_aux.close()
        fisier.close()
        
        
class DisciplinaRepositoryNou:
    def __init__(self,fis):
        """
        Aceasta functie prestabilieste niste valori implicite
        Nu are parametri
        Nu returneaza nimic
        """
        self.__fis=fis
        self.__lista_discipline=[]
    def adauga_disciplina(self,other):
        """
        Aceasta functie adauga o disciplina in baza de date
        other- de tip Disciplina
        Nu returneaza nimic
        """
        #self.__lista_studenti.append(other)
        with open(self.__fis,'a') as fisier:
            fisier.write(str(other.get_id())+":"+other.get_nume()+":"+other.get_profesor()+"\n")
    def sterge_disciplina(self,id):
        """
        Aceasta functie sterge o disciplina din baza de date
        id-de tip int
        Nu returneaza nimic
        """
        fisier_aux=open("aux_stud.txt","w")
        disc_sterge=self.cauta_disciplina(id)
        if disc_sterge!=None:
            with open(self.__fis,'r') as fisier:
                #numar=int(fisier.readline())
                for linie in fisier:
                    if linie!="":
                        date=linie
                        date=date.split(":")
                        disc=Disciplina(int(date[0]),date[1],date[2][:-1])
                        if disc!=disc_sterge:
                            fisier_aux.write(str(disc.get_id())+":"+disc.get_nume()+":"+disc.get_profesor()+"\n")
        fisier_aux.close()
        self.copiaza_fisier()
    def cauta_disciplina(self,id):
        """
        Aceasta functie cauta o disciplina in baza de date
        id-de tip int
        Returneaza obiectul disciplina sau none in cazul in care nu se afla in baza de date
        
        """
        
        
        '''
        nume=""
        prof=""
        with open(self.__fis,'r') as fisier:
            #numar=int(fisier.readline())
            for linie in fisier:
                if linie!="":
                    date=linie
                    date=date.split(":")
                    if int(date[0])==id:
                        nume=date[1]
                        prof=date[2]
                        break
            if nume=="": return None
            else: return Disciplina(id,nume,prof[:-1])       
        '''
        obiect=self.__cauta_in_lista(self.get_lista_discipline(), id)
        return obiect
            
    def __cauta_in_lista(self,lista,id):
        """
        Aceasta functie se ocupa de cautarea recursiva a unui element intr-o lista
        lista-lista de obiecte
        id-int
        Returneaza  None daca elementul nu se afla in lista, sau elementul in sine
        
        
        Analiza Complexitatii:
        
        DIN PUNCT DE VEDERE AL TIMPULUI DE EXECUTIE:
        
        CAZ FAVORABIL: elementul cautat se afla pe prima pozitie in lista theta(1)
        CAZ NEFAVORABIL: elementul nu se afla in lista, caz in care analizam toata lista theta(n)
        CAZ MEDIU: Variatia algoritmului este data de pozitia elementului in lista, astfel un element se poate afla pe 
        pozitia 1,2,...,n-1 unde n este lungimea listei, sau n in cel mai rau caz, deci complexitatea este T(n),
        unde T(n) este numarul maxim de apelari.
        T(n)=1+T(n-1)
        T(n-1)=1+T(n-2)
        ...
        T(1)=1+T(0)
        T(0)=0
        O(n)=(T(0)+T(1)+...+T(n))/n   
        O(n)=(1+2+...+n-1)/n = (n*(n-1)/2)/n = (n-1)/2=> theta(n)
        Complexitatea generala este O(n)
        
        
        DIN PUNCT DE VEDERE AL SPATIULUI CONSUMAT IN MEMORIE:
        
        Algoritmul este out_place, adica necesita memorie aditionala
        CAZ FAVORABIL: daca elementul se afla pe prima pozitie, fac numai o singura copie =>theta(n)
        CAZ NEFAVORABIL: daca elementul se afla pe ultima pozitie, fac n copii,=>O(n^2)

        CAZ MEDIU: Variatia este data de pozitia elementului in lista.  
        T(n)=1+2+3+...+(n-1)=n(n-1)/2=>O(n^2)
        Complexitatea generala este O(n^2)
        
        """
        if lista==[]: return None
        elif lista[0].get_id()==id: return lista[0]
        else: return self.__cauta_in_lista(lista[1:], id)                  
    
    def get_lista_discipline(self):
        """
        Aceasta functie returneaza lista disciplinelor care sunt in baza de date
        Nu are parametri
        Returneaza o lista de obiecte
        """
        #self.__lista_studenti=[]
        lista_discipline=[]
        with open(self.__fis,'r') as fisier:
            #numar=int(fisier.readline())
            for linie in fisier:
                if linie!="":
                    date=linie
                    date=date.split(":")
                    disc=Disciplina(int(date[0]),date[1],date[2][:-1])
                    lista_discipline.append(disc)
                    
        return lista_discipline
    
    def copiaza_fisier(self):
        """
        Aceasta functie se ocupa cu transferul de date intre 2 fisiere
        Nu are parametri
        Nu returneaza nimic
        """
        fisier_aux=open("aux_stud.txt","r")
        fisier=open(self.__fis,"w")
        for linie in fisier_aux:
            fisier.write(linie)
        fisier_aux.close()
        fisier.close()
        
class NotaRepositoryNou:
    def __init__(self,repos,repod,fis):
        """
        Aceasta functie prestabilieste niste valori implicite
        Nu are parametri
        Nu returneaza nimic
        """
        self.__repos=repos
        self.__repod=repod
        self.__fis=fis
        self.__lista_note=[]
    def adauga_nota(self,other):
        """
        Aceasta functie adauga o nota in baza de date
        other- de tip nota
        Nu returneaza nimic
        """
        #self.__lista_studenti.append(other)
        with open(self.__fis,'a') as fisier:
            fisier.write(str(other.get_student().get_id())+":"+str(other.get_disciplina().get_id())+":"+str(other.get_valoare())+"\n")
    def sterge_nota(self,id_stud,id_disc,val):
        """
        Aceasta functie sterge o nota din baza de date
        id_stud,id_disc,val-de tip int
        Nu returneaza nimic
        """
        fisier_aux=open("aux_stud.txt","w")
        nota_sterge=self.cauta_nota(id_stud,id_disc,val)
        if nota_sterge!=None:
            with open(self.__fis,'r') as fisier:
                #numar=int(fisier.readline())
                for linie in fisier:
                    if linie!="":
                        date=linie
                        date=date.split(":")
                        stud=self.__repos.cauta_student(int(date[0]))
                        disc=self.__repod.cauta_disciplina(int(date[1]))
                        nota=Nota(stud,disc,int(date[2][:-1]))
                        if nota!=nota_sterge:
                            fisier.write(str(nota.get_student().get_id())+":"+str(nota.get_disciplina().get_id())+":"+str(nota.get_valoare())+"\n")
        fisier_aux.close()
        self.copiaza_fisier()
    def cauta_nota(self,id_stud,id_disc,val):
        """
        Aceasta functie cauta o nota in baza de date
        id_stud,id_disc,val-de tip int
        Returneaza obiectul nota sau none in cazul in care nu se afla in baza de date
        """
        stud=None
        disc=None
        with open(self.__fis,'r') as fisier:
            #numar=int(fisier.readline())
            for linie in fisier:
                if linie!="":
                    date=linie
                    date=date.split(":")
                    if int(date[0])==id_stud and int(date[1])==id_disc and int(date[2])==val:
                        stud=self.__repos.cauta_student(int(date[0]))
                        disc=self.__repod.cauta_disciplina(int(date[1]))
                        break
            if stud==None: return None
            else: return Nota(stud,disc,val)                        
    
    def get_lista_note(self):
        """
        Aceasta functie returneaza lista notelor care sunt in baza de date
        Nu are parametri
        Returneaza o lista de obiecte
        """
        #self.__lista_studenti=[]
        lista_note=[]
        with open(self.__fis,'r') as fisier:
            #numar=int(fisier.readline())
            for linie in fisier:
                if linie!="":
                    date=linie
                    date=date.split(":")
                    stud=self.__repos.cauta_student(int(date[0]))
                    disc=self.__repod.cauta_disciplina(int(date[1]))
                    nota=Nota(stud,disc,int(date[2][:-1]))
                    lista_note.append(nota)
                    
        return lista_note
    
    def copiaza_fisier(self):
        """
        Aceasta functie se ocupa cu transferul de date intre 2 fisiere
        Nu are parametri
        Nu returneaza nimic
        """
        fisier_aux=open("aux_stud.txt","r")
        fisier=open(self.__fis,"w")
        for linie in fisier_aux:
            fisier.write(linie)
        fisier_aux.close()
        fisier.close()
        
import unittest
class TestCaseRepository(unittest.TestCase):
    def setUp(self):
        #code executed before every testMethod
        self.__repos=StudentRepositoryNou("tests.txt")
        self.__repod=DisciplinaRepositoryNou("testd.txt")
        self.__repon=NotaRepositoryNou(self.__repos,self.__repod,"testn.txt")
    def tearDown(self):
        #cleanup code executed after every testMethod
        fisier=open("tests.txt",'w')
        fisier.write("")
        fisier.close()
        fisier=open("testd.txt",'w')
        fisier.write("")
        fisier.close()
        fisier=open("testn.txt",'w')
        fisier.write("")
        fisier.close()
        #self.__repos=[]
        #self.__repod=[]
        #self.__repon=[]
        
    def test_adaugare(self):
        """
        Aceasta functie testeaza functia de adaugare pentru clasa Repository-ul de clase
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.__repos.adauga_student(Student(15,"andrei"))
        self.assertEqual( len(self.__repos.get_lista_studenti()),1)
        
        self.__repod.adauga_disciplina(Disciplina(10,"mate","andrei"))
        self.assertEqual( len(self.__repod.get_lista_discipline()),1)
        
        self.__repon.adauga_nota(Nota(Student(15,"andrei"),Disciplina(10,"mate","andrei"),10))
        self.assertEqual( len(self.__repon.get_lista_note()),1)
        
    def test_sterge(self):
        """
        Aceasta functie testeaza functia de stergere pentru clasa Repository-ul de clase
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.__repos.adauga_student(Student(15,"andrei"))
        self.__repos.sterge_student(15)
        stud=self.__repos.cauta_student(15)
        self.assertTrue (stud==None)
        
        self.__repod.adauga_disciplina(Disciplina(10,"mate","andrei"))
        self.__repod.sterge_disciplina(10)
        disc=self.__repod.cauta_disciplina(10)
        self.assertTrue( disc==None)
        
        self.__repon.adauga_nota(Nota(Student(15,"andrei"),Disciplina(10,"mate","andrei"),10))
        self.__repon.sterge_nota(15,10,10)
        nota=self.__repon.cauta_nota(15,10,10)
        self.assertTrue( nota==None)
        
    def test_cautare(self):
        """
        Aceasta functie testeaza functia de cautare pentru clasa Repository-ul de clase
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.__repos.adauga_student(Student(15,"andrei"))
        stud=self.__repos.cauta_student(15)
        self.assertEqual( stud.get_id(),15)
        self.assertEqual( stud.get_nume(),"andrei")
        
        self.__repod.adauga_disciplina(Disciplina(10,"mate","andrei"))
        disc=self.__repod.cauta_disciplina(10)
        self.assertEqual (disc.get_id(),10)
        self.assertEqual( disc.get_nume(),"mate")
        self.assertEqual( disc.get_profesor(),"andrei")
        
        self.__repon.adauga_nota(Nota(stud,disc,10))
        nota=self.__repon.cauta_nota(stud.get_id(), disc.get_id(), 10)
        self.assertTrue (nota.get_student()==stud)
        self.assertTrue (nota.get_disciplina()==disc)
        self.assertTrue (nota.get_valoare()==10)
        
    if __name__ == '__main__':
        unittest.main()
        

    
        