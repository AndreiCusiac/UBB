'''
Created on 9 dec. 2020

@author: Catalin
'''
from repository.filerepo import FilePlayerRepository
from domain.validators import ValidatePlayer
from service.controller import PlayerService
from ui.console import Console

val = ValidatePlayer()
rep = FilePlayerRepository("players.txt")
srv = PlayerService(val,rep)
ui = Console(srv)

ui.showUi()
