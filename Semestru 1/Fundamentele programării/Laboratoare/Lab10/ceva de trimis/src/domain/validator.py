'''
Created on 10 nov. 2020

@author: Raposatu
'''
from domain.clase import Student,Disciplina
from repository.repository_baza_de_date import *
class ValidatorStudent():
    def __init__(self,repo):
        self.__repos=repo
    def validare_nume(self,nume):
        """
        Aceasta functie verifica daca numele introdus este conform
        nume-de tip string
        Returneaza o variabila verdict care va contine tipul de eroare 
        """
        verdict=0
        char_exis=False
        for char in nume:
            if char.isdigit():
                verdict=1
                #print("da",1)
                #return False
            elif not char.isalpha() and not char.isdigit() and char!=' ':
                verdict=2
                #print("da",2)
                #return False
            elif char.isalpha():
                char_exis=True
        if char_exis==False and verdict==0:
            verdict=3
            #print("da",3)
        #else:verdict=0
        #print(char_exis," ", nume)
        #print(verdict)
        return verdict
    
    def validare(self,stud):
        """
        Aceasta functie verifica daca studentul introdus este conform
        stud-de tip Student
        Nu returneaza nimic
        """
        erori=[]
        if stud.get_id()=="" or stud.get_id()==None  :erori.append("Id-ul nu treb sa fie un spatiu vid")#-
        try:
            int(stud.get_id())
        except ValueError:
            erori.append("Id-ul nu poate contine spatii libere sau litere")#-
        if stud.get_nume()=="":
            erori.append("Numele nu poate sa fie un spatiu vid")#-
        if self.validare_nume(stud.get_nume())==1:
            #print(self.validare_nume(stud.get_nume()))
            erori.append("Numele nu poate sa contina cifre")#-
        if self.validare_nume(stud.get_nume())==2:
            erori.append("Numele nu poate sa contina alte caractere in afara de spatii si litere")#-
        if self.validare_nume(stud.get_nume())==3:
            erori.append("Numele trebuie sa contina litere")#-
        if self.__repos.cauta_student(stud.get_id())!=None: 
            erori.append("Studentul se afla deja in baza de date")#-
        self.__erori=erori
        
    def get_erori(self):
        """
        Aceasta functie returneaza erorile obtinute
        NU are parametri
        Returneaza lista de erori
        """
        return self.__erori
            
class ValidatorDisciplina():
    def __init__(self,repo):
        self.__repod=repo
    def validare_nume(self,nume):
        """
        Aceasta functie verifica daca numele introdus este conform
        nume-de tip string
        Returneaza o variabila verdict care va contine tipul de eroare 
        """
        verdict=0
        char_exis=False
        for char in nume:
            if char.isdigit():
                verdict=1
                #return False
            elif not char.isalpha() and not char.isdigit() and char!=' ':
                verdict=2
                #return False
            elif char.isalpha():
                char_exis=True
        if char_exis==False and verdict==0:
            verdict=3
        #else:verdict=0
        return verdict
    
    def validare(self,disc):
        """
        Aceasta functie verifica daca disciplina introdusa este conforma
        disc-de tip Student
        Nu returneaza nimic
        """
        erori=[]
        if disc.get_id()=="":erori.append("Id-ul nu treb sa fie un spatiu vid")#-
        try:
            int(disc.get_id())
        except ValueError:
            erori.append("Id-ul nu poate contine spatii libere sau litere")#-
        if disc.get_nume()=="":
            erori.append("Numele nu poate sa fie un spatiu vid")#
        if self.validare_nume(disc.get_nume())==1:
            erori.append("Numele nu poate sa contina cifre")#-
        elif self.validare_nume(disc.get_nume())==2:
            erori.append("Numele nu poate sa contina alte caractere in afara de spatii si litere")
        elif self.validare_nume(disc.get_nume())==3:
            erori.append("Numele trebuie sa contina litere")#-
        if disc.get_profesor()=="":
            erori.append("Numele profesorului nu poate sa fie un spatiu vid")#-
        if self.validare_nume(disc.get_profesor())==1:
            erori.append("Numele profesorului nu poate sa contina cifre")#-
        elif self.validare_nume(disc.get_profesor())==2:
            erori.append("Numele profesorului nu poate sa contina alte caractere in afara de spatii si litere")#-
        elif self.validare_nume(disc.get_profesor())==3:
            erori.append("Numele profesorului trebuie sa contina litere")#-
        if self.__repod.cauta_disciplina(disc.get_id())!=None:        
            erori.append("Disciplina se afla deja in baza de date")
        self.__erori=erori
                
    def get_erori(self):
        """
        Aceasta functie returneaza erorile obtinute
        NU are parametri
        Returneaza lista de erori
        """
        return self.__erori
    
def test_validator_student():
    """
        Aceasta functie testeaza functia validator_student
        Nu preia niciun parametru
        Nu returneaza nimic
        AssertionError atunci cand exista o inegalitate
    """   
    p1=Student(1,"hjfeu")
    p2=Student("","ifeiu")
    p3=Student(4,"bfie1")
    repos=StudentRepository()
    val=ValidatorStudent(repos)
    val.validare(p1)
    assert val.get_erori()==[]
    val.validare(p2)
    assert val.get_erori()==['Id-ul nu treb sa fie un spatiu vid','Id-ul nu poate contine spatii libere sau litere']
    val.validare(p3)
    #print(val.get_erori())
    assert val.get_erori()==['Numele nu poate sa contina cifre']
    
    
def test_validator_disciplina():
    """
        Aceasta functie testeaza functia validator_disciplina
        Nu preia niciun parametru
        Nu returneaza nimic
        AssertionError atunci cand exista o inegalitate
    """   
    dis1=Disciplina(10,"romana","mama")
    dis2=Disciplina("","mate","tata")
    dis3=Disciplina(30,"matem1","tatat")
    dis4=Disciplina(40,"matem1","tatat1")
    repod=DisciplinaRepository()
    val=ValidatorDisciplina(repod)
    val.validare(dis1)
    assert val.get_erori()==[]
    val.validare(dis2)
    assert val.get_erori()==['Id-ul nu treb sa fie un spatiu vid','Id-ul nu poate contine spatii libere sau litere']
    val.validare(dis3)
    assert val.get_erori()==['Numele nu poate sa contina cifre']
    val.validare(dis4)
    assert val.get_erori()==['Numele nu poate sa contina cifre','Numele profesorului nu poate sa contina cifre']
    
    
#test_validator_student()
#test_validator_disciplina()

import unittest
class TestCaseValidatorDomain(unittest.TestCase):
    def setUp(self):
        #code executed before every testMethod
        self.repos=StudentRepository()
        self.vals=ValidatorStudent(self.repos)
        self.repod=DisciplinaRepository()
        self.vald=ValidatorDisciplina(self.repod)
    def tearDown(self):
        #cleanup code executed after every testMethod
        pass
    def test_validator_student(self):
        """
            Aceasta functie testeaza functia validator_student
            Nu preia niciun parametru
            Nu returneaza nimic
            AssertionError atunci cand exista o inegalitate
        """   
        p1=Student(1,"hjfeu")
        p2=Student("","ifeiu")
        p3=Student(4,"bfie1")
        self.vals.validare(p1)
        self.assertEqual(self.vals.get_erori(),[])
        self.vals.validare(p2)
        self.assertEqual(self.vals.get_erori(),['Id-ul nu treb sa fie un spatiu vid','Id-ul nu poate contine spatii libere sau litere'])
        self.vals.validare(p3)
        #print(val.get_erori())
        self.assertEqual( self.vals.get_erori(),['Numele nu poate sa contina cifre'])
        self.vals.validare(Student(1,''))
        self.assertEqual(self.vals.get_erori(),['Numele nu poate sa fie un spatiu vid', 'Numele trebuie sa contina litere'])
        self.vals.validare(Student(1,'!'))
        self.assertEqual(self.vals.get_erori(),["Numele nu poate sa contina alte caractere in afara de spatii si litere"])
        self.vals.validare(Student(1,' '))
        self.assertEqual(self.vals.get_erori(),['Numele trebuie sa contina litere'])
        self.repos.adauga_student(p1)
        self.vals.validare(Student(1,'anderi'))
        #print(self.vals.get_erori())
        self.assertEqual(self.vals.get_erori(),['Studentul se afla deja in baza de date'])

    def test_validator_disciplina(self):
        """
            Aceasta functie testeaza functia validator_disciplina
            Nu preia niciun parametru
            Nu returneaza nimic
            AssertionError atunci cand exista o inegalitate
        """
        dis1=Disciplina(10,"romana","mama")
        dis2=Disciplina("","mate","tata")
        dis3=Disciplina(30,"matem1","tatat")
        dis4=Disciplina(40,"matem1","tatat1")
        self.vald.validare(dis1)
        self.assertTrue( self.vald.get_erori()==[])
        self.vald.validare(dis2)
        self.assertTrue( self.vald.get_erori()==['Id-ul nu treb sa fie un spatiu vid','Id-ul nu poate contine spatii libere sau litere'])
        self.vald.validare(dis3)
        self.assertTrue( self.vald.get_erori()==['Numele nu poate sa contina cifre'])
        self.vald.validare(dis4)
        self.assertTrue( self.vald.get_erori()==['Numele nu poate sa contina cifre','Numele profesorului nu poate sa contina cifre']) 
        self.vald.validare(Disciplina(1,'',"da"))
        self.assertTrue( self.vald.get_erori()==['Numele nu poate sa fie un spatiu vid', 'Numele trebuie sa contina litere']) 
        self.vald.validare(Disciplina(1,'da',""))
        self.assertTrue( self.vald.get_erori()==['Numele profesorului nu poate sa fie un spatiu vid', 'Numele profesorului trebuie sa contina litere']) 
        self.vald.validare(Disciplina(1,'da',"!"))
        self.assertTrue( self.vald.get_erori()==['Numele profesorului nu poate sa contina alte caractere in afara de spatii si litere']) 
        self.vald.validare(Disciplina(1,'!',"da"))
        self.assertTrue( self.vald.get_erori()==['Numele nu poate sa contina alte caractere in afara de spatii si litere']) 
        self.repod.adauga_disciplina(dis1)
        self.vald.validare(Disciplina(10,"da","da"))
        #print(self.vald.get_erori())
        self.assertTrue( self.vald.get_erori()==['Disciplina se afla deja in baza de date']) 
        
        
    if __name__ == '__main__':
        unittest.main()


        