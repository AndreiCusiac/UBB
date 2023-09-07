'''
Created on 21 nov. 2020

@author: Raposatu
'''
from domain.clase import *
from repository.repository_baza_de_date import *
from controller.controller_catalog import *
from utils.exceptii import *
class Testare():
    def __init__(self):
        """
        Aceasta functie prestabileste niste clase pentru testare
        Nu are parametri
        Nu returneaza nimic
        """
        self.__repos=StudentRepository()
        self.__repod=DisciplinaRepository()
        self.__repon=NotaRepository()
        self.__adau=Adaugare_Catalog()
        self.__ster=Stergere_Catalog()
        self.__modif=Modificare_Catalog()
        self.__afis=Afisare()
        self.__caut=Cautare_Catalog()
        self.__filt=Filtrare_Catalog()
        self.__stat=Statistica_Catalog()
        
    def test_adaugare(self):
        """
        Aceasta functie testeaza clasa Adaugare_catalog
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        #adaugare student
        self.__adau.adauga_student_catalog(self.__repos, 15, 'carina')
        stud=self.__repos.cauta_student(15)
        assert stud.get_id()==15
        assert stud.get_nume()=='carina'
        
        #adaugare disciplina
        self.__adau.adauga_disciplina_catalog(self.__repod, 20, "mate","marius")
        disc=self.__repod.cauta_disciplina(20)
        assert disc.get_id()==20
        assert disc.get_nume()=="mate"
        assert disc.get_profesor()=="marius"
        
        #adaugare nota
        self.__adau.adauga_nota_catalog(self.__repos, self.__repod, self.__repon, 15, 20, 10)
        nota=self.__repon.cauta_nota(15,20,10)
        assert nota.get_student().get_id()==15
        assert nota.get_student().get_nume()=="carina"
        assert nota.get_id_stud()==15
        assert nota.get_disciplina().get_id()==20
        assert nota.get_disciplina().get_nume()=="mate"
        assert nota.get_disciplina().get_profesor()=="marius"
        assert nota.get_id_disc()==20
        assert nota.get_valoare()==10
        self.__repos.sterge_student(15)
        self.__repod.sterge_disciplina(20)
        
        
    def test_stergere(self):
        """
        Aceasta functie testeaza clasa Stergere_catalog
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.__adau.adauga_student_catalog(self.__repos, 314, "cosmin")
        self.__ster.sterge_student_catalog(self.__repos,self.__repon,314)
        stud=self.__repos.cauta_student(314)
        assert stud==None
        
        self.__adau.adauga_student_catalog(self.__repos, 314, "cosmin")
        self.__adau.adauga_disciplina_catalog(self.__repod, 315, "mate", "florin")
        try:
            self.__ster.sterge_student_catalog(self.__repos, self.__repon, 315)
            assert False
        except IdNegasit:
            assert True
        self.__adau.adauga_nota_catalog(self.__repos, self.__repod, self.__repon, 314, 315, 10)
        self.__ster.sterge_student_catalog(self.__repos, self.__repon, 314)
        nota=self.__repon.cauta_nota(314,315,10)
        assert nota==None
        self.__ster.sterge_disciplina_catalog(self.__repod, self.__repon, 315)
        disc=self.__repod.cauta_disciplina(315)
        assert disc==None
    
        self.__repos.sterge_student(15)
        self.__repod.sterge_disciplina(20)
        
    def test_modificare(self):
        """
        Aceasta functie testeaza clasa Modificare_catalog
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.__adau.adauga_student_catalog(self.__repos, 314, "cosmin")
        try:
            self.__modif.modificare_student_id_catalog(self.__repos, self.__repon, 314, "3a")
            assert False
        except ValueError:
            assert True
        #print("da")
        self.__modif.modificare_student_id_catalog(self.__repos, self.__repon, 314, 25)
        stud=self.__repos.cauta_student(314)
        assert stud==None
        stud=self.__repos.cauta_student(25)
        assert stud.get_nume()=="cosmin"
        self.__modif.modificare_student_nume_catalog(self.__repos, self.__repon, 25, "cosmina")
        stud=self.__repos.cauta_student(25)
        assert stud.get_nume()=="cosmina"
        
        self.__adau.adauga_disciplina_catalog(self.__repod, 315, "mate", "marius")
        self.__modif.modificare_disciplina_id_catalog(self.__repod,self.__repon,315,26)
        disc=self.__repod.cauta_disciplina(26)
        assert disc.get_nume()=="mate"
        
        self.__modif.modificare_disciplina_nume_catalog(self.__repod, self.__repon, 26, "matematica")
        disc=self.__repod.cauta_disciplina(26)
        assert disc.get_nume()=="matematica"
        
        self.__modif.modificare_disciplina_profesor_catalog(self.__repod, self.__repon, 26, "mircea")
        disc=self.__repod.cauta_disciplina(26)
        assert disc.get_profesor()=="mircea"
        
        
    def test_cautare(self):
        """
        Aceasta functie testeaza clasa Cautare_catalog
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.__adau.adauga_student_catalog(self.__repos, 100, "andrei")
        try:
            self.__caut.afisare_student(self.__repos, "20e")
            assert False
        except ValueError:
            assert True
        stud=self.__caut.afisare_student(self.__repos, 100)
        assert stud.get_id()==100
        assert stud.get_nume()=="andrei"
        
        self.__adau.adauga_disciplina_catalog(self.__repod, 101, "fizica", "elisei")
        try:
            disc=self.__caut.afisare_disciplina(self.__repod, 100)
            assert False
        except IdNegasit:
            assert True
        disc=self.__caut.afisare_disciplina(self.__repod, 101)
        assert disc.get_id()==101
        assert disc.get_nume()=="fizica"
        assert disc.get_profesor()=="elisei"
        
        
    def test_filtare(self):
        """
        Aceasta functie testeaza clasa Filtrare_catalog
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        #self.__repon.sterge_nota(15, 10, 10)
        repos=StudentRepository()
        repod=DisciplinaRepository()
        repon=NotaRepository()
        self.__adau.adauga_student_catalog(repos, 1, "andrei")
        self.__adau.adauga_student_catalog(repos, 2, "cosbuc")
        self.__adau.adauga_student_catalog(repos, 3, "geanina")
        self.__adau.adauga_disciplina_catalog(repod, 10, "mate", "ghita")
        self.__adau.adauga_nota_catalog(repos,repod, repon, 1, 10, 10)
        #self.__adau.adauga_nota_catalog(self.__repos, self.__repod, self.__repon, 2, 10, 10)
        #self.__adau.adauga_nota_catalog(self.__repos, self.__repod, self.__repon, 3, 10, 10)
        lista=self.__filt.ordonare_dupa_nume(repos, repod, repon)
        #print(lista)
        assert lista==[{'id': 1, 'nume': 'andrei'}]
        self.__adau.adauga_nota_catalog(repos, repod, repon, 2, 10, 9)
        lista=self.__filt.ordonare_dupa_nume(repos,repod,repon)
        assert lista==[{'id': 1, 'nume': 'andrei'}, {'id': 2, 'nume': 'cosbuc'}]
        self.__adau.adauga_nota_catalog(repos, repod, repon, 3, 10,8)
        lista=self.__filt.ordonare_dupa_nume(repos,repod, repon)
        assert lista==[{'id': 1, 'nume': 'andrei'}, {'id': 2, 'nume': 'cosbuc'}, {'id': 3, 'nume': 'geanina'}]
        
        lista=self.__filt.ordonare_dupa_medie(repos, repod, repon)
        #print(lista)         
        assert lista==[{'id': 3, 'nota': 8.0}, {'id': 2, 'nota': 9.0}, {'id': 1, 'nota': 10.0}]
        
    def test_statistica(self):
        """
        Aceasta functie testeaza clasa Statistica_catalog
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        repos=StudentRepository()
        repod=DisciplinaRepository()
        repon=NotaRepository()
        self.__adau.adauga_student_catalog(repos, 1, "andrei")
        self.__adau.adauga_student_catalog(repos, 2, "cosbuc")
        self.__adau.adauga_student_catalog(repos, 3, "geanina")
        self.__adau.adauga_disciplina_catalog(repod, 10, "mate", "ghita")
        self.__adau.adauga_nota_catalog(repos,repod, repon, 1, 10, 10)
        self.__adau.adauga_nota_catalog(repos, repod, repon, 2, 10, 9)
        self.__adau.adauga_nota_catalog(repos, repod, repon, 3, 10,8)
        lista=self.__filt.ordonare_dupa_nume(repos,repod,repon,key=10)
        assert lista==[{'id': 1, 'nume': 'andrei'}, {'id': 2, 'nume': 'cosbuc'}, {'id': 3, 'nume': 'geanina'}]
        self.__adau.adauga_disciplina_catalog(repod, 20, "mate", "ghita")
        self.__adau.adauga_nota_catalog(repos, repod, repon, 2, 20, 9)
        lista=self.__filt.ordonare_dupa_nume(repos,repod,repon,key=20)
        assert lista==[{'id': 2, 'nume': "cosbuc"}]
        lista=self.__filt.ordonare_dupa_medie(repos,repod,repon,key=20)
        #print(lista)
        assert lista==[{'id': 1, 'nota': 0}, {'id': 3, 'nota': 0}, {'id': 2, 'nota': 9.0}]
        
    def testare(self):
        """
        Aceasta functie testeaza tot controller-ul
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.test_adaugare()
        self.test_stergere()
        self.test_modificare()
        self.test_cautare()
        self.test_filtare()
        self.test_statistica()
        
#test=Testare()
#test.testare()


import unittest
class TestCaseController(unittest.TestCase):
    def setUp(self):
        #code executed before every testMethod
        self.__repos=StudentRepository()
        self.__repod=DisciplinaRepository()
        self.__repon=NotaRepository()
        self.__adau=Adaugare_Catalog()
        self.__modif=Modificare_Catalog()
        self.__caut=Cautare_Catalog()
        self.__filt=Filtrare_Catalog()
        self.__stat=Statistica_Catalog()
        
    def tearDown(self):
        #cleanup code executed after every testMethod
        self.__repos=[]
        self.__repod=[]
        self.__repon=[]
        
    def test_adaugare(self):
        """
        Aceasta functie testeaza clasa Adaugare_catalog
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        
        #adaugare student
        self.__adau.adauga_student_catalog(self.__repos, 15, 'carina')
        stud=self.__repos.cauta_student(15)
        self.assertEqual( stud.get_id(),15)
        self.assertEqual( stud.get_nume(),'carina')
        
        #adaugare disciplina
        self.__adau.adauga_disciplina_catalog(self.__repod, 20, "mate","marius")
        disc=self.__repod.cauta_disciplina(20)
        self.assertEqual( disc.get_id(),20)
        self.assertEqual( disc.get_nume(),"mate")
        self.assertEqual( disc.get_profesor(),"marius")
        
        #adaugare nota
        self.__adau.adauga_nota_catalog(self.__repos, self.__repod, self.__repon, 15, 20, 10)
        nota=self.__repon.cauta_nota(15,20,10)
        self.assertEqual( nota.get_student().get_id(),15)
        self.assertEqual( nota.get_student().get_nume(),"carina")
        self.assertEqual( nota.get_id_stud(),15)
        self.assertEqual( nota.get_disciplina().get_id(),20)
        self.assertEqual( nota.get_disciplina().get_nume(),"mate")
        self.assertEqual( nota.get_disciplina().get_profesor(),"marius")
        self.assertEqual( nota.get_id_disc(),20)
        self.assertEqual( nota.get_valoare(),10)
        self.__repos.sterge_student(15)
        self.__repod.sterge_disciplina(20)
    
    
    def test_modificare(self):
        """
        Aceasta functie testeaza clasa Modificare_catalog
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.__adau.adauga_student_catalog(self.__repos, 314, "cosmin")
        self.assertRaises(ValueError,self.__modif.modificare_student_id_catalog,self.__repos, self.__repon, 314, "3a")
        self.assertRaises(IdNegasit,self.__modif.modificare_student_id_catalog,self.__repos, self.__repon, 315, 1)
        #print("da")
        self.__modif.modificare_student_id_catalog(self.__repos, self.__repon, 314, 25)
        stud=self.__repos.cauta_student(314)
        self.assertEqual( stud,None)
        stud=self.__repos.cauta_student(25)
        self.assertEqual( stud.get_nume(),"cosmin")
        self.__modif.modificare_student_nume_catalog(self.__repos, self.__repon, 25, "cosmina")
        stud=self.__repos.cauta_student(25)
        self.assertEqual( stud.get_nume(),"cosmina")
        
        
        self.__adau.adauga_disciplina_catalog(self.__repod, 315, "mate", "marius")
        self.__repon.adauga_nota(Nota(Student(25,"cosmina"),Disciplina(315,"mate","marius"),10))
        self.assertRaises(ValueError,self.__modif.modificare_disciplina_id_catalog,self.__repod, self.__repon, 315, "3a",)
        self.assertRaises(IdNegasit,self.__modif.modificare_disciplina_id_catalog,self.__repod, self.__repon, 314, 2)
        self.__modif.modificare_disciplina_id_catalog(self.__repod,self.__repon,315,26)
        disc=self.__repod.cauta_disciplina(26)
        self.assertEqual( disc.get_nume(),"mate")
        
        self.__modif.modificare_disciplina_nume_catalog(self.__repod, self.__repon, 26, "matematica")
        disc=self.__repod.cauta_disciplina(26)
        self.assertEqual( disc.get_nume(),"matematica")
        
        self.__modif.modificare_disciplina_profesor_catalog(self.__repod, self.__repon, 26, "matei")
        disc=self.__repod.cauta_disciplina(26)
        self.assertEqual( disc.get_profesor(),"matei")
        
        nota=self.__repon.cauta_nota(25,315,10)
        self.assertEqual(nota,None)
        nota=self.__repon.cauta_nota(25,26,10)
        self.assertEqual(nota.get_student(),Student(25,"cosmina"))
        self.assertEqual(nota.get_disciplina(),Disciplina(26,"matematica","matei"))
    
    
    def test_cautare(self):
        """
        Aceasta functie testeaza clasa Cautare_catalog
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.__adau.adauga_student_catalog(self.__repos, 100, "andrei")
        self.assertRaises(ValueError,self.__caut.afisare_student,self.__repos, "20e")
        self.assertRaises(IdNegasit,self.__caut.afisare_student,self.__repos, 191)
        
        stud=self.__caut.afisare_student(self.__repos, 100)
        self.assertEqual( stud.get_id(),100)
        self.assertEqual( stud.get_nume(),"andrei")
        
        self.__adau.adauga_disciplina_catalog(self.__repod, 101, "fizica", "elisei")
        self.assertRaises(ValueError,self.__caut.afisare_disciplina,self.__repod, "20e")
        self.assertRaises(IdNegasit,self.__caut.afisare_disciplina,self.__repod, 100)
        disc=self.__caut.afisare_disciplina(self.__repod, 101)
        self.assertEqual( disc.get_id(),101)
        self.assertEqual( disc.get_nume(),"fizica")
        self.assertEqual( disc.get_profesor(),"elisei")
 
    def test_filtare(self):
        """
        Aceasta functie testeaza clasa Filtrare_catalog
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        #self.__repon.sterge_nota(15, 10, 10)
        self.__adau.adauga_student_catalog(self.__repos, 1, "andrei")
        self.__adau.adauga_student_catalog(self.__repos, 2, "cosbuc")
        self.__adau.adauga_student_catalog(self.__repos, 3, "geanina")
        self.__adau.adauga_disciplina_catalog(self.__repod, 10, "mate", "ghita")
        self.__adau.adauga_nota_catalog(self.__repos,self.__repod,self.__repon, 1, 10, 10)
        #self.__adau.adauga_nota_catalog(self.__repos, self.__repod, self.__repon, 2, 10, 10)
        #self.__adau.adauga_nota_catalog(self.__repos, self.__repod, self.__repon, 3, 10, 10)
        lista=self.__filt.ordonare_dupa_nume(self.__repos, self.__repod, self.__repon)
        #print(lista)
        self.assertEqual( lista,[{'id': 1, 'nume': 'andrei'}])
        self.__adau.adauga_nota_catalog(self.__repos, self.__repod, self.__repon, 2, 10, 9)
        lista=self.__filt.ordonare_dupa_nume(self.__repon,self.__repod,self.__repon)
        self.assertEqual( lista,[{'id': 1, 'nume': 'andrei'}, {'id': 2, 'nume': 'cosbuc'}])
        self.__adau.adauga_nota_catalog(self.__repos, self.__repod, self.__repon, 3, 10,8)
        lista=self.__filt.ordonare_dupa_nume(self.__repos,self.__repod, self.__repon)
        self.assertEqual( lista,[{'id': 1, 'nume': 'andrei'}, {'id': 2, 'nume': 'cosbuc'}, {'id': 3, 'nume': 'geanina'}])
        
        lista=self.__filt.ordonare_dupa_medie(self.__repos, self.__repod, self.__repon)
        #print(lista)         
        self.assertEqual( lista,[{'id': 3, 'nota': 8.0}, {'id': 2, 'nota': 9.0}, {'id': 1, 'nota': 10.0}])
    
    def test_statistica(self):
        """
        Aceasta functie testeaza clasa Statistica_catalog
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.__adau.adauga_student_catalog(self.__repos, 1, "andrei")
        self.__adau.adauga_student_catalog(self.__repos, 2, "cosbuc")
        self.__adau.adauga_student_catalog(self.__repos, 3, "geanina")
        self.__adau.adauga_disciplina_catalog(self.__repod, 10, "mate", "ghita")
        self.__adau.adauga_nota_catalog(self.__repos,self.__repod, self.__repon, 1, 10, 10)
        self.__adau.adauga_nota_catalog(self.__repos, self.__repod, self.__repon, 2, 10, 9)
        self.__adau.adauga_nota_catalog(self.__repos, self.__repod, self.__repon, 3, 10,8)
        lista=self.__filt.ordonare_dupa_nume(self.__repos,self.__repod,self.__repon,key=10)
        self.assertEqual( lista,[{'id': 1, 'nume': 'andrei'}, {'id': 2, 'nume': 'cosbuc'}, {'id': 3, 'nume': 'geanina'}])
        self.__adau.adauga_disciplina_catalog(self.__repod, 20, "mate", "ghita")
        self.__adau.adauga_nota_catalog(self.__repos, self.__repod, self.__repon, 2, 20, 9)
        lista=self.__filt.ordonare_dupa_nume(self.__repos,self.__repod,self.__repon,key=20)
        self.assertEqual( lista,[{'id': 2, 'nume': "cosbuc"}])
        lista=self.__filt.ordonare_dupa_medie(self.__repos,self.__repod,self.__repon,key=20)
        #print(lista)
        self.assertEqual( lista,[{'id': 1, 'nota': 0}, {'id': 3, 'nota': 0}, {'id': 2, 'nota': 9.0}])
    
    if __name__ == '__main__':
        unittest.main()
        
    
        
        
        
class TestCaseStergereBlackBoxing(unittest.TestCase):
    def setUp(self):
        #code executed before every testMethod
        self.__repos=StudentRepository()
        self.__repod=DisciplinaRepository()
        self.__repon=NotaRepository()
        self.__ster=Stergere_Catalog()
        self.s1=Student(3,"andreea")
        self.s2=Student(4,"marius")
        self.d1=Disciplina(10,"mate","popa")
        self.d2=Disciplina(20,"fizica","dani")
        self.n1=Nota(self.s1,self.d1,10)
        self.n2=Nota(self.s2,self.d2,8)
        self.__repos.adauga_student(self.s1)
        self.__repos.adauga_student(self.s2)
        self.__repod.adauga_disciplina(self.d1)
        self.__repod.adauga_disciplina(self.d2)
        self.__repon.adauga_nota(self.n1)
        self.__repon.adauga_nota(self.n2)
        
    def tearDown(self):
        #cleanup code executed after every testMethod
        self.__repos=[]
        self.__repod=[]
        self.__repon=[]
        
    def test_sterge_studenti(self):
        """
        Aceasta functie testeaza clasa Stergere_catalog pentru studenti
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.assertRaises(ValueError,self.__ster.sterge_student_catalog,self.__repos, self.__repon,"3f")
        self.assertRaises(IdNegasit,self.__ster.sterge_student_catalog,self.__repos, self.__repon,33)
        self.assertRaises(ValueError,self.__ster.sterge_student_catalog,self.__repos, self.__repon,"aa")
        
        self.__ster.sterge_student_catalog(self.__repos, self.__repon,3)
        stud=self.__repos.cauta_student(3)
        self.assertEqual(stud,None)
        
        nota=self.__repon.cauta_nota(self.n1.get_id_stud, self.n1.get_id_disc, 10)
        self.assertEqual(nota,None)
        
        self.assertRaises(IdNegasit,self.__ster.sterge_student_catalog,self.__repos, self.__repon,3)
        
            
    def test_sterge_discipline(self):
        """
        Aceasta functie testeaza clasa Stergere_catalog pentru discipline
        Nu are parametri
        Nu returneaza nimic
        AssertionError poate aparea in urma verificarilor de mai jos
        """
        self.assertRaises(ValueError,self.__ster.sterge_disciplina_catalog,self.__repod, self.__repon,"3f")
        self.assertRaises(IdNegasit,self.__ster.sterge_disciplina_catalog,self.__repod, self.__repon,33)
        self.assertRaises(ValueError,self.__ster.sterge_disciplina_catalog,self.__repod, self.__repon,"aa")
        
        self.__ster.sterge_disciplina_catalog(self.__repod, self.__repon,10)
        disc=self.__repod.cauta_disciplina(10)
        self.assertEqual(disc,None)
        
        nota=self.__repon.cauta_nota(self.n1.get_id_stud, self.n1.get_id_disc, 10)
        self.assertEqual(nota,None)
        
        self.assertRaises(IdNegasit,self.__ster.sterge_disciplina_catalog,self.__repod, self.__repon,10)
        
    if __name__ == '__main__':
        unittest.main()    
