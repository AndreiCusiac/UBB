import re


class Collection:
    def __init__(self, fileName):
        self.startingGrammar = GrammarParser(fileName)
        self.startingGrammar.parser()
        # self.startingGrammar.printResults()
        self.sets = []
        self.separator = '.'

        self.table = []
        self.addRowAndColumns()

    def getSets(self):
        return self.sets

    def addSet(self, set):
        self.sets.append(set)

    def addRowAndColumns(self):
        dict = {}

        for state in self.startingGrammar.getNonTerminals():
            dict[state] = ''

        for state in self.startingGrammar.getTerminals():
            dict[state] = ''

        dict['$'] = ''

        self.table.append(dict)

    def constructSet0(self):
        redProds = self.startingGrammar.getRegProd()
        arrow = self.startingGrammar.getArrowSymbol()

        set0 = Set(None)

        for prod in redProds:
            prod.insert(prod.index(arrow) + 1, self.separator)

            newItem = ItemLR1(prod)
            set0.addItem(newItem)

        self.sets.append(set0)
        # self.noSets += 1
        set0.printItems()

    def getStateToGoTo(self, item):
        indexOfSeparator = item.production.index(self.separator)
        return item.production[indexOfSeparator + 1]

    def getLeftSideOfProduction(self, item):
        return item.production[0]

    def areSetsDuplicate(self, set1, set2):
        items1 = set1.getItems()
        items2 = set2.getItems()

        if len(items1) != len(items2):
            return False

        for index in range(0, len(items1)):
            if not (items1[index].production == items2[index].production and
                    items1[index].lookahead == items2[index].lookahead):
                return False

        return True

    def doesSetExist(self, set):
        for existingSets in self.sets:
            if self.areSetsDuplicate(set, existingSets):
                return True

        return False

    def constructAllSets(self):
        self.constructSet0()

        addedSet = True
        currentSet = 0

        while addedSet and currentSet < len(self.sets):
            addedSet = False

            statesToGoTo = []

            for items in self.sets[currentSet].getItems():

                stateAfterSeparator = self.getStateToGoTo(items)

                if stateAfterSeparator not in statesToGoTo:
                    statesToGoTo.append(stateAfterSeparator)

            print(statesToGoTo)

            for stateToGo in statesToGoTo:
                newSet = Set(stateToGo)

                for item in self.sets[currentSet].getItems():
                    stateAfterSeparator = self.getStateToGoTo(item)

                    if stateAfterSeparator == newSet and item not in newSet.getItems():
                        newSet.addItem(item)

                for newItems in newSet.getItems():
                    newState = self.getStateToGoTo(newItems)

                    if newState in self.startingGrammar.getNonTerminals():
                        for item in self.sets[currentSet].getItems():
                            if self.getLeftSideOfProduction(item) == newState and item not in newSet.getItems():
                                newSet.addItem(item)

                if not self.doesSetExist(newSet):
                    

class ItemLR1:
    def __init__(self, production):
        self.production = production
        self.lookahead = ['$']

    def setProduction(self, production):
        self.production = production

    def setLookahead(self, lookahead):
        self.lookahead = lookahead

    def getProduction(self):
        return self.production

    def getLookahead(self):
        return self.lookahead


class Set:
    def __init__(self, nonTerminal):
        self.nonTerminalProducer = nonTerminal
        self.items = []

    def addItem(self, item):
        self.items.append(item)

    def getItems(self):
        return self.items

    def getNonTerminalProducer(self):
        return self.nonTerminalProducer

    def isItemInSet(self, item):
        return item in self.items

    def printItems(self):
        for item in self.items:
            print(item.production, ",", item.lookahead)


class GrammarParser:
    def __init__(self, filename):
        self.start_symbol = None
        self.non_terminals = []
        self.terminals = []
        self.reg_prod = []
        self.arrow_symbol = '->'
        self.epsylon = 'ε'
        self.fileName = filename

    def getNonTerminals(self):
        return self.non_terminals

    def getTerminals(self):
        return self.terminals

    def getRegProd(self):
        return self.reg_prod

    def getStartSymbol(self):
        return self.start_symbol

    def getArrowSymbol(self):
        return self.arrow_symbol

    def getEpsylon(self):
        return self.epsylon

    def processLine(self, line):
        before_arrow_symbol = True

        l = re.split('\n|\r|\t', line)
        prod = re.split(' ', l[0])
        self.reg_prod.append(prod)
        spl = line.split()

        # print(spl)

        for el in spl:
            if el != self.arrow_symbol:
                if self.start_symbol is None:
                    self.start_symbol = el

                if before_arrow_symbol:

                    if el not in self.non_terminals:
                        self.non_terminals.append(el)

                    # for atom in el:
                    #     if atom not in self.non_terminals:
                    #         self.non_terminals.append(atom)

                elif el != self.epsylon:

                    if el[0].isupper():
                        if el not in self.non_terminals:
                            self.non_terminals.append(el)
                    elif el not in self.terminals:
                        self.terminals.append(el)

                    # for atom in el:
                    #     if atom.isupper():
                    #         if atom not in self.non_terminals:
                    #             self.non_terminals.append(atom)
                    #     elif atom not in self.terminals:
                    #         self.terminals.append(atom)

            else:
                before_arrow_symbol = False

    def parser(self):
        f = open(self.fileName, mode='r', encoding='utf8', newline='\r\n')
        p = []

        for i, line in enumerate(f):
            # print(line, end="")

            # spl = re.split(' |\n|\r|\t', line)
            # spl = line.split()
            # print("Split: ", spl)
            self.processLine(line)

        # print(p)
        return p

    def printResults(self):
        print("Simbol de start:")
        print(self.start_symbol)
        print()

        print("Ne-terminale:")
        print(self.non_terminals)
        print()

        print("Terminale:")
        print(self.terminals)
        print()

        print("Reguli de productie:")
        print(self.reg_prod)
        print()


if __name__ == '__main__':
    print('Hello, Andrei')

    file = 'gram2'
    fileName = file + '.txt'

    col = Collection(fileName)
    col.constructAllSets()

    # gP = GrammarParser(fileName)
    # gP.parser()
    # gP.printResults()

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
