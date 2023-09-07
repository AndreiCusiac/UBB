'''
Created on 7 nov. 2020

@author: Raposatu
'''
from domain.clase import *
from repository.baza_de_date import Catalog
class Testare:
    def test_adaugare(self):
        """
        Prin aceasta functie se testeaza functie de adaugare in baza de date
        Nu primeste niciun parametru
        Nu returneaza nimic
        AssertionError atunci cand exista o inegalitate
        """
        p1=Student(1,"hjfeu")
        p2=Student(2,"ifeiu")
        p3=Student(4,"bfie")
        
        dis1=Disciplina(10,"romana","mama")
        dis2=Disciplina(20,"mate","tata")
        dis3=Disciplina(30,"matem","tatat")
        dis4=Disciplina(40,"matema","tatata")
        
        cat=Catalog()
        
        cat.adauga_disciplina(dis1)
        cat.adauga_studenti(Student(5,"hbbb"))
        #print(cat.afisare_baza_date())
        baza_corecta=[{5: {10: []}}]
        assert cat.afisare_baza_date()==baza_corecta
        
        cat.adauga_disciplina(dis2)
        cat.adauga_studenti(p1)
        baza_corecta=[{5: {10: [], 20:[]}},{1:{10: [], 20:[]}}]
        assert cat.afisare_baza_date()==baza_corecta
        #print(cat.afisare_studenti())
        #print(cat.afisare_discipline())
        baza_stud=[{'id': 5, 'nume': 'hbbb'}, {'id': 1, 'nume': 'hjfeu'}]
        baza_disc=[{'id': 10, 'nume': 'romana', 'profesor': 'mama', 'note': []}, {'id': 20, 'nume': 'mate', 'profesor': 'tata', 'note': []}]
        assert cat.afisare_studenti()==baza_stud
        assert cat.afisare_discipline()==baza_disc
        
        cat.adauga_studenti(p2)
        cat.adauga_disciplina(dis3)
        cat.adauga_studenti(p3)
        cat.adauga_disciplina(dis4)
        #print(cat.afisare_baza_date())
        #print(cat.afisare_studenti())
        #print(cat.afisare_discipline())
        baza_corecta=[{5: {10: [], 20: [], 30: [], 40: []}}, {1: {10: [], 20: [], 30: [], 40: []}}, {2: {10: [], 20: [], 30: [], 40: []}}, {4: {10: [], 20: [], 30: [], 40: []}}]
        baza_stud=[{'id': 5, 'nume': 'hbbb'}, {'id': 1, 'nume': 'hjfeu'}, {'id': 2, 'nume': 'ifeiu'}, {'id': 4, 'nume': 'bfie'}]
        baza_disc=[{'id': 10, 'nume': 'romana', 'profesor': 'mama', 'note': []}, {'id': 20, 'nume': 'mate', 'profesor': 'tata', 'note': []}, {'id': 30, 'nume': 'matem', 'profesor': 'tatat', 'note': []}, {'id': 40, 'nume': 'matema', 'profesor': 'tatata', 'note': []}]
        assert cat.afisare_baza_date()==baza_corecta
        assert cat.afisare_studenti()==baza_stud
        assert cat.afisare_discipline()==baza_disc
    
    def test_modificare(self):
        """
        Prin aceasta functie se testeaza functie de modificare in baza de date
        Nu primeste niciun parametru
        Nu returneaza nimic
        AssertionError atunci cand exista o inegalitate
        """
        p1=Student(1,"hjfeu")
        p2=Student(2,"ifeiu")
        
        dis1=Disciplina(10,"romana","mama")
        dis2=Disciplina(20,"mate","tata")
        
        
        cat=Catalog()
        
        cat.adauga_studenti(p1)
        cat.adauga_disciplina(dis1)
        baza_corecta=[{1: {10: []}}]
        assert cat.afisare_baza_date()==baza_corecta
        
        pi=Student(100,"orice")
        cat.set_student_id(1, pi)
        baza_corecta=[{100: {10: []}}]
        baza_stud=[ {'id': 100, 'nume': 'hjfeu'}]
       
        #print(cat.afisare_studenti())
        assert cat.afisare_baza_date()==baza_corecta
        assert cat.afisare_studenti()==baza_stud
        
        disc=Disciplina(110,"orice","orice")
        cat.set_disciplina_id(10,disc)
        baza_corecta=[{100: {110: []}}]
        baza_disc=[{'id': 110, 'nume': 'romana', 'profesor': 'mama', 'note': []}]
        #print(cat.afisare_baza_date())
        #print(cat.afisare_discipline())  
        assert cat.afisare_baza_date()==baza_corecta
        assert cat.afisare_discipline()==baza_disc
        
        cat.adauga_studenti(p2)
        cat.adauga_disciplina(dis2)
        pi=Student(200,"orice")
        disc=Disciplina(220,"orice","orice")
        cat.set_student_id(p2.get_id(),pi)
        cat.set_disciplina_id(dis2.get_id(), disc)
        #print(cat.afisare_baza_date())
        #print(cat.afisare_studenti())
        #print(cat.afisare_discipline())   
        baza_corecta=[{100: {110: [], 220: []}}, {200: {110: [], 220: []}}]
        baza_stud=[{'id': 100, 'nume': 'hjfeu'}, {'id': 200, 'nume': 'ifeiu'}]
        baza_disc=[{'id': 110, 'nume': 'romana', 'profesor': 'mama', 'note': []}, {'id': 220, 'nume': 'mate', 'profesor': 'tata', 'note': []}]
        assert cat.afisare_baza_date()==baza_corecta
        assert cat.afisare_studenti()==baza_stud
        assert cat.afisare_discipline()==baza_disc
        
        cat.set_student_nume(100, Student(15,"Mihai"))
        baza_stud=[{'id': 100, 'nume': 'Mihai'}, {'id': 200, 'nume': 'ifeiu'}]          
        assert cat.afisare_studenti()==baza_stud
        
        cat.set_disciplina_nume(110,Disciplina(15,"Romana","orice"))
        baza_disc=[{'id': 110, 'nume': 'Romana', 'profesor': 'mama', 'note': []}, {'id': 220, 'nume': 'mate', 'profesor': 'tata', 'note': []}]
        assert cat.afisare_discipline()==baza_disc
        
        cat.set_disciplina_profesor(110, Disciplina(15,"orice","Livada"))
        baza_disc=[{'id': 110, 'nume': 'Romana', 'profesor': 'Livada', 'note': []}, {'id': 220, 'nume': 'mate', 'profesor': 'tata', 'note': []}]
        assert cat.afisare_discipline()==baza_disc
    
    
    def test_stergere(self):
        """
        Prin aceasta functie se testeaza functie de stergere in baza de date
        Nu primeste niciun parametru
        Nu returneaza nimic
        AssertionError atunci cand exista o inegalitate
        """
        p1=Student(1,"hjfeu")
        p2=Student(2,"ifeiu")
        p3=Student(4,"bfie")
        
        dis1=Disciplina(10,"romana","mama")
        dis2=Disciplina(20,"mate","tata")
        dis3=Disciplina(30,"matem","tatat")
        
        cat=Catalog()
        cat.adauga_studenti(p1)
        cat.adauga_studenti(p2)
        cat.adauga_studenti(p3)
        cat.adauga_disciplina(dis1)
        cat.adauga_disciplina(dis2)
        cat.adauga_disciplina(dis3)
        baza_corecta=[{1: {10: [], 20: [], 30: []}}, {2: {10: [], 20: [], 30: []}}, {4: {10: [], 20: [], 30: []}}]
        baza_stud=[ {'id': 1, 'nume': 'hjfeu'}, {'id': 2, 'nume': 'ifeiu'}, {'id': 4, 'nume': 'bfie'}]
        baza_disc=[{'id': 10, 'nume': 'romana', 'profesor': 'mama', 'note': []}, {'id': 20, 'nume': 'mate', 'profesor': 'tata', 'note': []}, {'id': 30, 'nume': 'matem', 'profesor': 'tatat', 'note': []}]
        
        #print(cat.afisare_baza_date())
        #print(cat.afisare_studenti())
        #print(cat.afisare_discipline())   
        
        assert cat.afisare_baza_date()==baza_corecta
        assert cat.afisare_studenti()==baza_stud
        assert cat.afisare_discipline()==baza_disc
        
        cat.sterge_student(4)
        baza_corecta=[{1: {10: [], 20: [], 30: []}}, {2: {10: [], 20: [], 30: []}}]
        baza_stud=[ {'id': 1, 'nume': 'hjfeu'}, {'id': 2, 'nume': 'ifeiu'}]
        assert cat.afisare_baza_date()==baza_corecta
        assert cat.afisare_studenti()==baza_stud
        
        cat.sterge_disciplina(20)
        baza_corecta=[{1: {10: [], 30: []}}, {2: {10: [], 30: []}}]
        baza_disc=[{'id': 10, 'nume': 'romana', 'profesor': 'mama', 'note': []}, {'id': 30, 'nume': 'matem', 'profesor': 'tatat', 'note': []}]
        #print(cat.afisare_baza_date())
        #print(cat.afisare_discipline())
        assert cat.afisare_baza_date()==baza_corecta
        assert cat.afisare_discipline()==baza_disc
        
        cat.sterge_disciplina(30)
        baza_corecta=[{1: {10: []}}, {2: {10: []}}]
        baza_disc=[{'id': 10, 'nume': 'romana', 'profesor': 'mama', 'note': []}]
        assert cat.afisare_baza_date()==baza_corecta
        assert cat.afisare_discipline()==baza_disc
        
        
        cat.sterge_student(1)
        baza_corecta=[{2: {10: []}}]
        baza_stud=[{'id': 2, 'nume': 'ifeiu'}]
        assert cat.afisare_baza_date()==baza_corecta
        assert cat.afisare_studenti()==baza_stud
    
    def test_afisare(self):
        """
        Prin aceasta functie se testeaza functie de afisare din baza de date
        Nu primeste niciun parametru
        Nu returneaza nimic
        AssertionError atunci cand exista o inegalitate
        """
        p1=Student(1,"hjfeu")
        p2=Student(2,"ifeiu")
        p3=Student(4,"bfie")
        
        dis1=Disciplina(10,"romana","mama")
        dis2=Disciplina(20,"mate","tata")
        dis3=Disciplina(30,"matem","tatat")
        
        cat=Catalog()
        cat.adauga_studenti(p1)
        cat.adauga_studenti(p2)
        cat.adauga_studenti(p3)
        cat.adauga_disciplina(dis1)
        cat.adauga_disciplina(dis2)
        cat.adauga_disciplina(dis3)
        baza_corecta=[{1: {10: [], 20: [], 30: []}}, {2: {10: [], 20: [], 30: []}}, {4: {10: [], 20: [], 30: []}}]
        baza_stud=[ {'id': 1, 'nume': 'hjfeu'}, {'id': 2, 'nume': 'ifeiu'}, {'id': 4, 'nume': 'bfie'}]
        baza_disc=[{'id': 10, 'nume': 'romana', 'profesor': 'mama', 'note': []}, {'id': 20, 'nume': 'mate', 'profesor': 'tata', 'note': []}, {'id': 30, 'nume': 'matem', 'profesor': 'tatat', 'note': []}]
    
        assert cat.afisare_baza_date()==baza_corecta
        assert cat.afisare_studenti()==baza_stud
        assert cat.afisare_discipline()==baza_disc
    def test_cautare(self):
        """
        Prin aceasta functie se testeaza functie de cautare in baza de date
        Nu primeste niciun parametru
        Nu returneaza nimic
        AssertionError atunci cand exista o inegalitate
        """
        p1=Student(1,"hjfeu")
        p2=Student(2,"ifeiu")
        p3=Student(4,"bfie")
        
        dis1=Disciplina(10,"romana","mama")
        dis2=Disciplina(20,"mate","tata")
        dis3=Disciplina(30,"matem","tatat")
        
        cat=Catalog()
        cat.adauga_studenti(p1)
        cat.adauga_studenti(p2)
        cat.adauga_studenti(p3)
        cat.adauga_disciplina(dis1)
        cat.adauga_disciplina(dis2)
        cat.adauga_disciplina(dis3)
        
        stud={10: [], 20: [], 30: []}
        dis={1: [], 2: [], 4: []}
        stud_data={'id': 1, 'nume': 'hjfeu'}
        dis_data={'id': 10, 'nume': 'romana', 'profesor': 'mama', 'note': []}
        assert cat.get_date_student(1)==stud
        assert cat.get_date_disciplina(10)==dis
        assert cat.get_student(1)==stud_data
        assert cat.get_disciplina(10)==dis_data
    
    def test_asignare_note(self):
        """
        Prin aceasta functie se testeaza functie de adaugare a notelor in baza de date
        Nu primeste niciun parametru
        Nu returneaza nimic
        AssertionError atunci cand exista o inegalitate
        """
        p1=Student(1,"hjfeu")
        p2=Student(2,"ifeiu")
        p3=Student(4,"bfie")
        
        dis1=Disciplina(10,"romana","mama")
        dis2=Disciplina(20,"mate","tata")
        dis3=Disciplina(30,"matem","tatat")
        
        cat=Catalog()
        cat.adauga_studenti(p1)
        cat.adauga_studenti(p2)
        cat.adauga_disciplina(dis1)
        cat.adauga_disciplina(dis2)
    
        baza_corecta=[{1: {10: [],20:[]}}, {2: {10: [],20:[]}}]
        assert cat.afisare_baza_date()==baza_corecta
        cat.adaugare_nota(1, 10, 5)
        cat.adaugare_nota(1, 10, 5)
        cat.adaugare_nota(1, 20, 7)
        cat.adaugare_nota(2, 10, 7)
        cat.adaugare_nota(2, 20, 10)
        
        baza_corecta=[{1: {10: [5,5],20:[7]}}, {2: {10: [7],20:[10]}}]
        assert cat.afisare_baza_date()==baza_corecta
        
        cat.adauga_disciplina(dis3)
        baza_corecta=[{1: {10: [5,5],20:[7],30:[]}}, {2: {10: [7],20:[10],30:[]}}]
        assert cat.afisare_baza_date()==baza_corecta
        
        cat.adauga_studenti(p3)
        baza_corecta=[{1: {10: [5,5],20:[7],30:[]}}, {2: {10: [7],20:[10],30:[]}},{4: {10: [],20:[],30:[]}}]
        assert cat.afisare_baza_date()==baza_corecta
        
        
    def testare(self):
        """
        Prin aceasta functie se retuleaza toate testele de mai jos
        Nu primeste niciun parametru
        Nu returneaza nimic
        AssertionError poate aparea la una dintre testele de mai jos
        """
        self.test_adaugare()
        self.test_stergere()
        self.test_modificare()
        self.test_cautare()
        self.test_afisare()
        self.test_asignare_note()
        
testing=Testare()
testing.testare()
    
    