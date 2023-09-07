from repository.clientiRepo import clientiRepo
from repository.filmeRepo import filmeRepo
from repository.inchirieriRepo import inchirieriRepo

from repository.clientiFileRepo import clientiFileRepo
from repository.filmeFileRepo import filmeFileRepo
from repository.inchirieriFileRepo import inchirieriFileRepo

import unittest
from domain.client import TestCaseClient
from domain.film import TestCaseFilm
from domain.inchiriere import TestCaseInchiriere
from domain.validatoare import TestCaseValidatoare
from repository.clientiFileRepo import TestCaseClientiFileRepo
from repository.clientiRepo import TestCaseClientiRepo
from repository.filmeFileRepo import TestCaseFilmeFileRepo
from repository.filmeRepo import TestCaseFilmeRepo
from repository.inchirieriRepo import TestCaseInchirieriRepo
from service.clientiServ import TestCaseClientiServ
from service.inchirieriServ import TestCaseInchirieriServ

def runTests():
    loader = unittest.TestLoader()
    suite = unittest.TestSuite((
        loader.loadTestsFromTestCase(TestCaseClient),
        loader.loadTestsFromTestCase(TestCaseFilm),
        loader.loadTestsFromTestCase(TestCaseInchiriere),
        loader.loadTestsFromTestCase(TestCaseValidatoare),
        loader.loadTestsFromTestCase(TestCaseClientiFileRepo),
        loader.loadTestsFromTestCase(TestCaseClientiRepo),
        loader.loadTestsFromTestCase(TestCaseFilmeFileRepo),
        loader.loadTestsFromTestCase(TestCaseFilmeRepo),
        loader.loadTestsFromTestCase(TestCaseInchirieriRepo),
        loader.loadTestsFromTestCase(TestCaseClientiServ),
        loader.loadTestsFromTestCase(TestCaseInchirieriServ),
        ))
    
    runner = unittest.TextTestRunner()
    runner.run(suite)

runTests()

print('\n')

from ui.console import console

# clienti = clientiRepo()
# filme = filmeRepo()
# inchirieri = inchirieriRepo()

clienti = clientiFileRepo("clienti.txt")
filme = filmeFileRepo("filme.txt")
inchirieri = inchirieriFileRepo("inchirieri.txt")

ui = console(clienti, filme, inchirieri)
ui.showUI()