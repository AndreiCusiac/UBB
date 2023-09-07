'''
Created on 6 nov. 2020

@author: Raposatu
'''
from repository.repository_baza_de_date import StudentRepository,DisciplinaRepository,NotaRepository
from repository.repository_nou import *
from repository.repository_salvare_citire import Fisier
from ui.meniuri import Meniu
from domain.clase import *
from controller.test_controller import *

repos=StudentRepositoryNou("studenti1.txt")
repod=DisciplinaRepositoryNou("discipline1.txt")
repon=NotaRepositoryNou(repos,repod,"note1.txt")
#fis=Fisier()
#fis.citire_fisier(repos, repod, repon, "studenti.txt","discipline.txt","note.txt")
#fis.citeste_discipline(repod, "discipline.txt")
#fis.citeste_note(repos, repod, repon, "note.txt")
#print("sa")
men=Meniu()
men.meniu(repos,repod,repon)
#print("sa1")
#fis.salveaza_fisier(repos, repod, repon,"studenti.txt","discipline.txt","note.txt")
