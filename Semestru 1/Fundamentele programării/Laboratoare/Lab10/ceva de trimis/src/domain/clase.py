'''
Created on 4 nov. 2020

@author: Raposatu
'''
from utils.getter_setter import *
import unittest
#from domain.validator import *
class Student:
    def __init__(self,id,nume):
        """
        Aceasta functie este o initializare a unui obiect de tip Student
        id-de tip int
        nume-de tip string
        Nu returneaza nimic
        """
        self.__id=id
        self.__nume=nume
        #self.__validator=ValidatorStudent()
        
    def get_id(self):
        """
        Prin aceasta functie se poate accesa o variabila privata
        Nu are parametri
        Returneaza valoarea variabilei private 
        """
        return self.__id
    
    def set_id(self,valoare):
        """
        Prin aceasta functie se poate modifica o variabila privata
        valoare-de tip int
        Nu returneaza nimic 
        """
        self.__id=int(valoare)
        
    def get_nume(self):
        """
        Prin aceasta functie se poate accesa o variabila privata
        Nu are parametri
        Returneaza valoarea variabilei private 
        """
        return self.__nume
    
    def set_nume(self, valoare):
        """
        Prin aceasta functie se poate modifica o variabila privata
        valoare-de tip string
        Nu returneaza nimic 
        """
        self.__nume=valoare
    
    def get_tot_student(self):
        """
        Prin aceasta functie se reda obiectul student intr o forma citibila
        Nu are parametri
        Returneaza o lista cu informatiile despre student  
        """
        lista=[]
        lista.append(self.__id)
        lista.append(self.__nume)
        return lista
    #@staticmethod
    def __eq__(self,other):
        """
        Prin aceasta functie se pot compara doua obiecte printr un anumit criteriu
        other-de tip Student
        Returneaza valoarea de adevar a egalitatii celor 2 corpuri dupa acel criteriu
        """
        if other==None: return False
        return self.__id==other.__id
    

class Disciplina:
    def __init__(self, id, nume, profesor):
        """
        Aceasta functie este o initializare a unui obiect de tip Disciplina
        id-de tip int
        nume-de tip string
        profesor-de tip string
        Nu returneaza nimic
        """
        self.__id=id
        self.__nume=nume
        self.__profesor=profesor
           
    def get_id(self):
        """
        Prin aceasta functie se poate accesa o variabila privata
        Nu are parametri
        Returneaza valoarea variabilei private 
        """
        return self.__id
    
    def set_id(self,valoare):
        """
        Prin aceasta functie se poate modifica o variabila privata
        valoare-de tip int
        Nu returneaza nimic 
        """
        self.__id=valoare
        
    def get_nume(self):
        """
        Prin aceasta functie se poate accesa o variabila privata
        Nu are parametri
        Returneaza valoarea variabilei private 
        """
        return self.__nume
    
    def set_nume(self,valoare):
        """
        Prin aceasta functie se poate modifica o variabila privata
        valoare-de tip string
        Nu returneaza nimic 
        """
        self.__nume=valoare
    
    def get_profesor(self):
        """
        Prin aceasta functie se poate accesa o variabila privata
        Nu are parametri
        Returneaza valoarea variabilei private 
        """
        return self.__profesor
    
    def set_profesor(self,valoare):
        """
        Prin aceasta functie se poate modifica o variabila privata
        valoare-de tip string
        Nu returneaza nimic 
        """
        self.__profesor=valoare
        
    def get_toata_disciplina(self):
        """
        Prin aceasta functie se pot accesa toate variabilele privvate
        Nu are parametri
        Returneaza o lista cu valorile variabilelor private  
        """
        lista=[]
        lista.append(self.__id)
        lista.append(self.__nume)
        lista.append(self.__profesor)
        return lista
        #return "{} {} {}".format(self.__id,self.__nume,self.__profesor)
    #@staticmethod
    def __eq__(self,other):
        """
        Prin aceasta functie se pot compara doua obiecte printr un anumit criteriu
        other-de tip Disciplina
        Returneaza valoarea de adevar a egalitatii celor 2 corpuri dupa acel criteriu
        """
        if other==None: return False
        return self.__id==other.__id
    

class Nota:
    def __init__(self, stud, disc, valoare ):
        """
        Aceasta functie este o initializare a unui obiect de tip Disciplina
        id-de tip int
        nume-de tip string
        profesor-de tip string
        Nu returneaza nimic
        """
        self.__stud=stud
        self.__disc=disc
        self.__val=valoare

    def get_id_stud(self):
        """
        Prin aceasta functie se poate accesa o variabila privata
        Nu are parametri
        Returneaza valoarea variabilei private 
        """
        return self.__stud.get_id()
    
    def get_id_disc(self):
        """
        Prin aceasta functie se poate accesa o variabila privata
        Nu are parametri
        Returneaza valoarea variabilei private 
        """
        return self.__disc.get_id()
    def get_student(self):
        """
        Prin aceasta functie se poate accesa o variabila privata
        Nu are parametri
        Returneaza valoarea variabilei private 
        """
        return self.__stud
    def set_student(self,other):
        """
        Prin aceasta functie se poate modifica o variabila privata
        other-de tip Student
        Nu returneaza nimic 
        """
        self.__stud=other
    def get_disciplina(self):
        """
        Prin aceasta functie se poate accesa o variabila privata
        Nu are parametri
        Returneaza valoarea variabilei private 
        """
        return self.__disc
    def set_disciplina(self,other):
        """
        Prin aceasta functie se poate modifica o variabila privata
        other-de tip Disciplina
        Nu returneaza nimic 
        """
        self.__disc=other
    def get_valoare(self):
        """
        Prin aceasta functie se poate accesa o variabila privata
        Nu are parametri
        Returneaza valoarea variabilei private 
        """
        return self.__val
    
    #@staticmethod
    
class Testare:
    def testare(self):
        """
        Aceasta functie testeaza tot domeninul de clase
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        s1=Student(15,"andrei")
        assert s1.get_id()==15
        s1.set_id(14)
        assert s1.get_id()==14
        assert s1.get_nume()=="andrei"
        s1.set_nume("darius")
        assert s1.get_nume()=="darius"
        assert s1.get_tot_student()==[14,'darius']
        s2=Student(14,"andrei")
        assert s1==s2
        
        d1=Disciplina(10,"mate","andrei")
        assert d1.get_id()==10
        d1.set_id(11)
        assert d1.get_id()==11
        assert d1.get_profesor()=="andrei"
        assert d1.get_nume()=="mate"
        d1.set_nume("fiz")
        assert d1.get_nume()=="fiz"
        assert d1.get_profesor()=="andrei"
        d1.set_profesor("darius")
        assert d1.get_profesor()=="darius"
        assert d1.get_toata_disciplina()==[11,'fiz','darius']
        d2=Disciplina(11,"mate","andrei")
        assert d1==d2
        
        n1=Nota(s1,d1,10)
        assert n1.get_student()==s1
        n1.set_student(s2)
        assert n1.get_student()==s2
        assert n1.get_disciplina()==d1
        n1.set_disciplina(d2)
        assert n1.get_disciplina()==d2
        assert n1.get_valoare()==10
        
test=Testare()
test.testare()
    
    
class TestCaseDomain(unittest.TestCase):
    def setUp(self):
        #code executed before every testMethod
        self.s1=Student(15,"andrei")
        self.d1=Disciplina(10,"mate","andrei")
        self.n1=Nota(self.s1,self.d1,10)
    def tearDown(self):
        #cleanup code executed after every testMethod
        pass
    def testStudent(self):
        """
        Aceasta functie testeaza clasa Student
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.assertEqual(self.s1.get_id(),15)
        self.s1.set_id(14)
        self.assertEqual(self.s1.get_id(),14)
        self.assertEqual( self.s1.get_nume(),"andrei")
        self.s1.set_nume("darius")
        self.assertEqual( self.s1.get_nume(),"darius")
        self.assertEqual( self.s1.get_tot_student(),[14,'darius'])
        s2=Student(14,"andrei")
        self.assertTrue( self.s1==s2)
    def testDisciplina(self):
        """
        Aceasta functie testeaza clasa Disciplina
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.assertEqual(self.d1.get_id(),10) 
        self.d1.set_id(11)
        self.assertTrue (self.d1.get_id()==11)
        self.assertTrue (self.d1.get_profesor()=="andrei")
        self.assertTrue (self.d1.get_nume()=="mate")
        self.d1.set_nume("fiz")
        self.assertTrue (self.d1.get_nume()=="fiz")
        self.assertTrue (self.d1.get_profesor()=="andrei")
        self.d1.set_profesor("darius")
        self.assertTrue (self.d1.get_profesor()=="darius")
        self.assertTrue (self.d1.get_toata_disciplina()==[11,'fiz','darius'])
        d2=Disciplina(11,"mate","andrei")
        self.assertTrue( self.d1==d2)
    def testNota(self):
        """
        Aceasta functie testeaza clasa Nota
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.assertTrue( self.n1.get_student()==self.s1)
        s2=Student(14,"andrei")
        self.n1.set_student(s2)
        self.assertTrue( self.n1.get_student()==s2)
        self.assertTrue (self.n1.get_disciplina()==self.d1)
        d2=Disciplina(11,"mate","andrei")
        self.n1.set_disciplina(d2)
        self.assertTrue( self.n1.get_disciplina()==d2)
        self.assertTrue( self.n1.get_valoare()==10)
    if __name__ == '__main__':
        unittest.main()
            
                
            
            
    
    
               