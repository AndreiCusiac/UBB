import re
from enum import Enum


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
                    pass


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


class Rule:
    def __init__(self, left_member:str = "", right_member:list = []):
        self.left = left_member
        self.right = []

    def __str__(self):
        print(id(self.left), id(self.right))
        return self.left + " -> " + str(self.right)


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

    def get_rule(self, index:int):
        return self.reg_prod[index]

    def processLine(self, line):
        before_arrow_symbol = True
        l = re.split('\n|\r|\t', line)
        prod = re.split(' ', l[0])
        spl = line.split()
        rule = Rule()
        print(spl)

        for el in spl:
            if el != self.arrow_symbol:
                if self.start_symbol is None:
                    self.start_symbol = el

                if before_arrow_symbol:

                    if el not in self.non_terminals:
                        self.non_terminals.append(el)
                    rule.left = el
                    # for atom in el:
                    #     if atom not in self.non_terminals:
                    #         self.non_terminals.append(atom)

                elif el != self.epsylon:

                    if el[0].isupper():
                        if el not in self.non_terminals:
                            self.non_terminals.append(el)
                    elif el not in self.terminals:
                        self.terminals.append(el)
                    rule.right.append(el)
                    # for atom in el:
                    #     if atom.isupper():
                    #         if atom not in self.non_terminals:
                    #             self.non_terminals.append(atom)
                    #     elif atom not in self.terminals:
                    #         self.terminals.append(atom)

            else:
                before_arrow_symbol = False
        print(rule)
        self.reg_prod.append(rule)

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
        for el in self.reg_prod:
            print(el)
        print()


class ActionType(Enum):
    SHIFT = "s"
    REDUCE = "r"
    ACCEPT = "acc"


class Trace:
    __STATE_ELEM = "state"
    __ALPH_ELEM = "alph"

    NON_POPPABLE = "$"

    def __init__(self, input:list, step:int =0, stack: list = [], action:str = "" ):
        self.__step = step
        self.__stack = stack
        self.__input = input
        self.__last_action = action
        self.__history = []

    def add_state_stack(self, state:int):
        stack_elem = {self.__STATE_ELEM: state}
        self.__stack.append(stack_elem)

    def add_alph_stack(self, alph:str):
        stack_elem = {self.__ALPH_ELEM: alph}
        self.__stack.append(stack_elem)

    def pop_input(self):
        elem = self.__input[0]
        if elem != self.NON_POPPABLE:
            self.__input = self.__input[1:]
        return elem

    def push_input(self, alph:str):
        #input_elem = {self.__ALPH_ELEM: alph}
        #self.__input.insert(0,input_elem)
        self.__input = alph+self.__input
    def set_action(self, action:ActionType):
        self.__last_action = action.value

    def add_history(self, rule_index:int):
        self.__history.insert(0, rule_index)

    def apply_rule(self, rule:Rule):
        #print("Start reducing")
        current_index_stack = len(self.__stack) - 1
        current_index_rule = len(rule.right) - 1
        #print(len(self.__stack), current_index_stack, current_index_rule)
        while current_index_rule >= 0:
            #print(len(self.__stack), current_index_stack, current_index_rule)
            elem = self.__stack[current_index_stack]
            if self.__STATE_ELEM in elem.keys():
                self.__stack.pop(current_index_stack)
                current_index_stack -= 1
            else:
                self.__stack.pop(current_index_stack)
                current_index_stack -= 1
                current_index_rule -= 1
            #print(self.__stack)
        current_state = self.__stack[current_index_stack][self.__STATE_ELEM]
        return current_state

    def get_current_state(self):
        return self.__stack[-1][self.__STATE_ELEM]

    def increase_step(self):
        self.__step += 1

    def __str__(self):
        return f'(%d,$%s,%s,%s,%s)' % (self.__step, self.__stack, self.__input,self.__last_action, self.__history)


class LRTable:

    def __init__(self):
        self.mtable = []

    def add_line(self, line: dict) -> None:
        self.mtable.append(line)

    def get_table(self) -> list:
        return self.mtable.copy()

    def get_action(self, current_state: int, current_path:str):
        #print(current_state ,current_path)
        return self.mtable[int(current_state)][current_path]

    def __str__(self):
        string = ""
        for el in self.mtable:
            string += str(el) +"\n"
        return string

class SyntacticParser:

    def __init__(self, table: LRTable, grammar: GrammarParser) -> None:
        self.__lr_table = table
        self.__grammar = grammar

    def __reformat_input(self, input:str):
        """
        This function should reformat the input according to a grammar
        """
        return input+"$"  #temporary

    def parse(self,input:str) -> None:
        print("Processing input:", input)
        input = self.__reformat_input(input)
        start_state = 0
        trace = Trace(input)
        trace.add_state_stack(start_state)
        is_accepted = False
        while True:
            current_state = int(trace.get_current_state())
            current_path = trace.pop_input()
            action = self.__lr_table.get_action(current_state, current_path)
            #print(action)
            if action is None:
                break
            action_type, next = self.__decode_action(action)
            next = int(next) if next is not None else None
            #print(action_type, next)
            match action_type:
                case ActionType.SHIFT.value:
                    self.__shift(trace,current_path, next)
                case ActionType.REDUCE.value:
                    self.__reduce(trace, current_path, next, self.__grammar.get_rule(next))
                case ActionType.ACCEPT.value:
                    is_accepted = True
                    trace.set_action(ActionType.ACCEPT)
                    break
            print(trace)
        if is_accepted:
            print("Input good!")
        else:
            print("Input Bad!")

    def __shift(self, trace:Trace, alph: str, next: int):
        trace.add_alph_stack(alph)
        trace.add_state_stack(next)
        trace.set_action(ActionType.SHIFT)
        trace.increase_step()

    def __reduce(self, trace:Trace, alph: str, rule_index:int, rule: Rule):
        #print("rule", rule.right)
        current_state = trace.apply_rule(rule)
        trace.add_alph_stack(rule.left)
        action = self.__lr_table.get_action(current_state, rule.left)
        _, next = self.__decode_action(action)
        trace.add_state_stack(next)
        if alph != Trace.NON_POPPABLE:
            trace.push_input(alph)
        trace.add_history(rule_index)
        trace.set_action(ActionType.REDUCE)
        #return current_state

    def __decode_action(self, action:str) -> tuple:
        sep = "_"
        data = action.split(sep)
        return tuple(data) if len(data) > 1 else (data[0],None)



if __name__ == '__main__':
    print('Hello, Andrei')

    file = 'gram3'
    fileName = file + '.txt'

    # col = Collection(fileName)
    # col.constructAllSets()

    gP = GrammarParser(fileName)
    print(gP.__dict__)
    gP.parser()
    gP.printResults()

    tablee = LRTable()
    print(tablee.__dict__)
    #table.add_line({"S":ActionType.SHIFT.value+"_1"+,"A":ActionType.SHIFT.value+"_2","a":ActionType.SHIFT.value+"_3","b":ActionType.SHIFT.value+"_4","$":None})
    tablee.add_line({"S":"s_1","A":"s_2","a":"s_3","b":"s_4","$":None})
    tablee.add_line({"S":None,"A":None,"a":None,"b":None,"$":"acc"})
    tablee.add_line({"S": None, "A": "s_5", "a": "s_6", "b": "s_7", "$": None})
    tablee.add_line({"S": None, "A": "s_8", "a": "s_3", "b": "s_4", "$": None})
    tablee.add_line({"S": None, "A": None, "a": "r_3", "b": "r_3", "$": None})
    tablee.add_line({"S": None, "A": None, "a": None, "b": None, "$": "r_1"})
    tablee.add_line({"S": None, "A": "s_9", "a": "s_6", "b": "s_7", "$": None})
    tablee.add_line({"S": None, "A": None, "a": None, "b": None, "$": "r_3"})
    tablee.add_line({"S": None, "A": None, "a": "r_2", "b": "r_2", "$": None})
    tablee.add_line({"S": None, "A": None, "a": None, "b": None, "$": "r_2"})

    print(tablee)

    syn_parser = SyntacticParser(tablee,gP)
    syn_parser.parse("abab")

