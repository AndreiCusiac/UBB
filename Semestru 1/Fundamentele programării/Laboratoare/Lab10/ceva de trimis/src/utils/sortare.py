'''
Created on 12 nov. 2020

@author: Raposatu
'''
from utils.getter_setter import *
class Utils:
    def sortare_bubble(self,lista,func=lambda x:x,reverse=False):
        """
        Aceasta functie se ocupa cu ordonarea unei liste dupa un anumit criteriu folosinf=d metoda bubble sort
        lista-o lista de dictionare
        func- o functie
        reverse- de tip boolean
        Nu returneaza nimic
        """
        for poz1 in range(len(lista)-1):
            for poz2 in range(poz1+1,len(lista)):
                if reverse==False:
                    if func(lista[poz1])>func(lista[poz2]):
                        lista[poz1],lista[poz2]=lista[poz2],lista[poz1]
                else: 
                    if func(lista[poz1])<func(lista[poz2]):
                        lista[poz1],lista[poz2]=lista[poz2],lista[poz1]
                        
    def sortare_bubble_criterii(self,lista,lista_alt,func=lambda x:x,func1=lambda x:x,reverse=False):
        """
        Aceasta functie se ocupa cu ordonarea unei liste dupa un anumit criteriu folosinf=d metoda bubble sort
        lista,lista_alt-o lista de dictionare
        func,func1- o functie
        reverse- de tip boolean
        Nu returneaza nimic
        """
        print(lista)
        print(lista_alt)
        if reverse==False:
            for poz1 in range(len(lista)-1):
                for poz2 in range(poz1+1,len(lista)):
                        if func(lista[poz1])>func(lista[poz2]):
                            lista[poz1],lista[poz2]=lista[poz2],lista[poz1]
            for poz1 in range(len(lista)-1):
                for poz2 in range(poz1+1,len(lista)):
                    if func(lista[poz1])==func(lista[poz2]) and get_id(lista[poz1]) !=get_id(lista[poz2]):
                            obiect1=""
                            obiect2=""
                            for element in lista_alt:
                                if get_id(element)==get_id(lista[poz1]):
                                    obiect1=func1(element)
                                if get_id(element)==get_id(lista[poz2]):
                                    obiect2=func1(element)
                            if obiect1<obiect2 and obiect1!="":
                                lista[poz1],lista[poz2]=lista[poz2],lista[poz1]
        else: 
            for poz1 in range(len(lista)-1):
                for poz2 in range(poz1+1,len(lista)):   
                    if func(lista[poz1])<func(lista[poz2]):
                        lista[poz1],lista[poz2]=lista[poz2],lista[poz1]
            for poz1 in range(len(lista)-1):
                for poz2 in range(poz1+1,len(lista)):
                        if func(lista[poz1])==func(lista[poz2]) and get_id(lista[poz1]) !=get_id(lista[poz2]):
                            obiect1=""
                            obiect2=""
                            for element in lista_alt:
                                if get_id(element)==get_id(lista[poz1]):
                                    obiect1=func1(element)
                                if get_id(element)==get_id(lista[poz2]):
                                    obiect2=func1(element)
                            if obiect1>obiect2 and obiect1!="":
                                lista[poz1],lista[poz2]=lista[poz2],lista[poz1]
                                
                        
                            
    def sortare_shell(self,lista,func=lambda x:x,reverse=False):
        """
        Aceasta functie se ocupa cu ordonarea unei liste dupa un anumit criteriu folosinf=d metoda shell sort
        lista-o lista de dictionare
        func- o functie
        reverse- de tip boolean
        Nu returneaza nimic
        """
        interv=len(lista)//2
        while interv>0:
            for index1 in range(interv,len(lista)):
                copie=lista[index1]
                index2=index1
                if reverse==False:
                    while index2>=interv and func(lista[index2-interv])>func(copie):
                            #lista[index2-interv],lista[index2]=lista[index2],lista[index2-interv]
                            lista[index2]=lista[index2-interv]
                            index2-=interv
                else:  
                    while index2>=interv and func(lista[index2-interv])<func(copie):
                            #lista[index2-interv],lista[index2]=lista[index2],lista[index2-interv]
                            lista[index2]=lista[index2-interv]
                            index2-=interv
                lista[index2]=copie
            interv//=2
            
        
                    
    def media(self,lista):
        """
        Aceasta functie se ocupa cu media aritmetica a elementelor unei liste
        lista-o lista 
        Returneaza media aritmetica a elementelor a listei
        """
        medie=0
        medie=self.calculeaza_suma(lista)
        #print(medie)
        #for element in lista:
            #medie+=float(element)
        #print(medie)
        medie/=len(lista)
        medie="{:.2f}".format(medie)
        medie=float(medie)
        #print(medie)
        return medie
    def calculeaza_suma(self,lista):
        if lista==[]: return 0
        else: return lista[0]+ self.calculeaza_suma(lista[1:])
    
    def test_medie(self):
        """
        Prin aceasta functie se testeaza functie de medie
        Nu primeste niciun parametru
        Nu returneaza nimic
        AssertionError atunci cand exista o inegalitate
        """
        assert self.media([2,2,2,2,2])==2.00
        assert self.media([1,2,3,4,5,6])==3.50
        assert self.media([135,125])==130.00
        #print(medie_pret([45,87,65,7498,123,1553,12]))
        assert self.media([45,87,65,7498,123,1553,12])==1340.43
        assert self.media([400,500,500,6000])==1850.00
        assert self.media([422,546,217,235])==355.00
        assert self.media([26,781249,6382,8724])==199095.25
    
    def test_sortare(self):
        """
        Prin aceasta functie se testeaza functie de sortare
        Nu primeste niciun parametru
        Nu returneaza nimic
        AssertionError atunci cand exista o inegalitate
        """
        lista=[{"id":15,"nota":10},{"id":12,"nota":7},{"id":11,"nota":5},{"id":17,"nota":8},{"id":18,"nota":9}]
        self.sortare_bubble(lista,get_nota)
        lista_cor=[{"id":11,"nota":5},{"id":12,"nota":7},{"id":17,"nota":8},{"id":18,"nota":9},{"id":15,"nota":10}]
        assert lista==lista_cor
        self.sortare_bubble(lista,get_nota)
        assert lista==lista_cor
    
uti=Utils()
uti.test_medie()
uti.test_sortare()

import unittest
class TestCaseUtils(unittest.TestCase):
    def setUp(self):
        #code executed before every testMethod
        self.__uti=Utils()
    def tearDown(self):
        #cleanup code executed after every testMethod
        pass
    def test_medie(self):
        """
        Prin aceasta functie se testeaza functie de medie
        Nu primeste niciun parametru
        Nu returneaza nimic
        AssertionError atunci cand exista o inegalitate
        """
        self.assertEqual( self.__uti.media([2,2,2,2,2]),2.00)
        self.assertEqual( self.__uti.media([1,2,3,4,5,6]),3.50)
        self.assertEqual( self.__uti.media([135,125]),130.00)
        #print(medie_pret([45,87,65,7498,123,1553,12]))
        self.assertEqual( self.__uti.media([45,87,65,7498,123,1553,12]),1340.43)
        self.assertEqual( self.__uti.media([400,500,500,6000]),1850.00)
        self.assertEqual( self.__uti.media([422,546,217,235]),355.00)
        self.assertEqual( self.__uti.media([26,781249,6382,8724]),199095.25)
        
    def test_sortare_bubble(self):
        """
        Prin aceasta functie se testeaza functie de sortare
        Nu primeste niciun parametru
        Nu returneaza nimic
        AssertionError atunci cand exista o inegalitate
        """
        lista=[{"id":15,"nota":10},{"id":12,"nota":7},{"id":11,"nota":5},{"id":17,"nota":8},{"id":18,"nota":9}]
        self.__uti.sortare_bubble(lista,get_nota)
        lista_cor=[{"id":11,"nota":5},{"id":12,"nota":7},{"id":17,"nota":8},{"id":18,"nota":9},{"id":15,"nota":10}]
        self.assertEqual( lista,lista_cor)
        self.__uti.sortare_bubble(lista,get_nota)
        self.assertEqual( lista,lista_cor)
        lista=[5,3,8,10,2,7]
        lista_cor=[2,3,5,7,8,10]
        self.__uti.sortare_bubble(lista)
        self.assertEqual(lista,lista_cor)
        lista=[5,3,8,10,2,7]
        lista_cor=[10,8,7,5,3,2]
        self.__uti.sortare_bubble(lista,reverse=True)
        self.assertEqual( lista,lista_cor)
        #print("da")
        
    def test_sortare_shell(self):
        """
        Prin aceasta functie se testeaza functie de sortare
        Nu primeste niciun parametru
        Nu returneaza nimic
        AssertionError atunci cand exista o inegalitate
        """
        lista=[{"id":15,"nota":10},{"id":12,"nota":7},{"id":11,"nota":5},{"id":17,"nota":8},{"id":18,"nota":9}]
        self.__uti.sortare_shell(lista,get_nota)
        lista_cor=[{"id":11,"nota":5},{"id":12,"nota":7},{"id":17,"nota":8},{"id":18,"nota":9},{"id":15,"nota":10}]
        self.assertEqual( lista,lista_cor)
        self.__uti.sortare_shell(lista,get_nota)
        self.assertEqual( lista,lista_cor)
        lista=[5,3,8,10,2,7]
        lista_cor=[2,3,5,7,8,10]
        self.__uti.sortare_shell(lista)
        self.assertEqual( lista,lista_cor)
        lista=[5,3,8,10,2,7]
        lista_cor=[10,8,7,5,3,2]
        self.__uti.sortare_shell(lista,reverse=True)
        self.assertEqual( lista,lista_cor)
    if __name__ == '__main__':
        unittest.main()
            

