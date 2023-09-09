import copy
import re
from enum import Enum


class Rule:
    def __init__(self, left_member: str = "", right_member: list = []):
        self.left = left_member
        self.right = []

    def __str__(self):
        return self.left + " -> " + str(self.right)

    def __eq__(self, other):
        return self.left == other.left and self.right == other.right


class ActionType(Enum):
    SHIFT = "s"
    REDUCE = "r"
    ACCEPT = "acc"


class GrammarParser:
    def __init__(self, filename):
        self.start_symbol = None
        self.non_terminals = []
        self.terminals = []
        self.reg_prod = []
        self.arrow_symbol = '->'
        self.epsylon = 'ε'
        self.fileName = filename
        self.end_of_line = "$"

    def getNonTerminals(self) -> list:
        return self.non_terminals

    def getTerminals(self) -> list:
        return self.terminals

    def getRegProd(self) -> list:
        return self.reg_prod

    def getStartSymbol(self):
        return self.start_symbol

    def getArrowSymbol(self):
        return self.arrow_symbol

    def getEpsylon(self):
        return self.epsylon

    def get_rule(self, index: int):
        return self.reg_prod[index]

    def get_first_table(self):
        first = {}
        for non_term in self.non_terminals:
            first[non_term] = []
            epsy_at_the_end = False
            for rule in self.reg_prod:
                if rule.left == non_term:
                    potential_first = str(rule.right[0])
                    if potential_first in self.terminals and potential_first not in first[non_term]:
                        first[non_term].append(potential_first)
                    elif potential_first == self.epsylon:
                        epsy_at_the_end = True
            if epsy_at_the_end:
                first[non_term].append(self.end_of_line)
        return first


    def processLine(self, line):
        before_arrow_symbol = True
        l = re.split('\n|\r|\t', line)
        prod = re.split(' ', l[0])
        spl = line.split()
        rule = Rule()
        #print(spl)

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
        #print(rule)
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


class ItemLR1:
    def __init__(self, production, lookahead=None):
        if lookahead is None:
            lookahead = []
        self.production = production
        self.lookahead = lookahead

    def setProduction(self, production):
        self.production = production

    def setLookahead(self, lookahead):
        self.lookahead = lookahead

    def getProduction(self) -> Rule:
        return self.production

    def getLookahead(self) -> list:
        return self.lookahead

    def __str__(self) -> str:
        return f"ItemLR1 (%s, %s)" % (self.production, self.lookahead)


class Set:


    def __init__(self, nonTerminal=None):
        self.nonTerminalProducer = nonTerminal
        self.items = []

    def addItem(self, item: ItemLR1):
        self.items.append(item)

    def getItems(self) -> list:
        return self.items

    def getNonTerminalProducer(self):
        return self.nonTerminalProducer

    def isItemInSet(self, item: ItemLR1) -> bool:
        return item in self.items

    def printItems(self):
        for item in self.items:
            print(item.production, ",", item.lookahead)
    def __str__(self) -> str:
        return f"Set [non-term : %s, items = %s ]" % (self.nonTerminalProducer, [str(it) for it in self.items])

class Collection:
    def __init__(self, fileName):
        self.startingGrammar = GrammarParser(fileName)
        self.startingGrammar.parser()
        # self.startingGrammar.printResults()
        self.sets = []
        self.separator = '.'

        self.table = []
        self.addRowAndColumns()
        self.first = self.startingGrammar.get_first_table()
        self.lookahead_inherit_tree = {}
        self.lookaheads = {}

    def getSets(self) -> [Set]:
        return self.sets

    def addSet(self, set: Set):
        self.sets.append(set)

    def addRowAndColumns(self):
        dict = {}

        for state in self.startingGrammar.getNonTerminals():
            dict[state] = ''

        for state in self.startingGrammar.getTerminals():
            dict[state] = ''

        dict['$'] = ''

        self.table.append(dict)

    def update_lookahead_list(self, item:ItemLR1):
        elem_afected, lookahead = self.get_lookahead(item)
        # print("get_lookahead",elem_afected, lookahead )
        if elem_afected is not None and lookahead is not None:
            if elem_afected not in self.lookaheads.keys():
                self.lookaheads[elem_afected] = []
                if lookahead != [self.startingGrammar.end_of_line]:
                    self.lookaheads[elem_afected] += [el for el in lookahead if el not in self.lookaheads[elem_afected]]

    def update_lookahead_item(self,item:ItemLR1):
        if item.production.left in self.lookaheads.keys() and self.lookaheads[item.production.left] != []:
            item.lookahead += [el for el in self.lookaheads[item.production.left] if el not in item.lookahead]
        else:
            if item.lookahead == []:
                item.lookahead = [self.startingGrammar.end_of_line]


    def reset_lookaheads(self):
        self.lookaheads ={}

    def constructSet0(self):
        redProds = self.startingGrammar.getRegProd()
        arrow = self.startingGrammar.getArrowSymbol()
        set0 = Set()
        self.reset_lookaheads()
        newProd = copy.deepcopy(redProds[0])
        newProd.right.insert(0, self.separator)
        newItem = ItemLR1(newProd)
        newItem.lookahead = [self.startingGrammar.end_of_line]
        self.update_lookahead_list(newItem)
        set0.addItem(newItem)
        #print("lookaheads", lookaheads)
        done = False
        while not done:
            done = True
            sep_index = 0
            elem_after_sep = str(newProd.right[sep_index + 1])
            if elem_after_sep in self.startingGrammar.getNonTerminals():
                for rule in redProds:
                    if rule.left == elem_after_sep:
                        newProd = copy.deepcopy(rule)
                        newProd.right.insert(0, self.separator)
                        newItem = ItemLR1(newProd)
                        self.update_lookahead_item(newItem)
                        self.update_lookahead_list(newItem)
                        set0.addItem(newItem)
                        #set0.printItems()
                        done = False
                        #print("newItem", newItem)
        self.sets.append(set0)
        # self.noSets += 1
        set0.printItems()

    def populateTableRow(self, currentSet: int, dictionaryOfColumns: dict, actionType: str):

        strng = ''

        if actionType == "SHIFT":
            strng = "s_"
        elif actionType == "REDUCE":
            strng = "r_"
        elif actionType == "ACC":
            strng = "acc"

        for el in dictionaryOfColumns:
            if self.table[currentSet][el] != '' and self.table[currentSet][el] != strng + str(dictionaryOfColumns[el]):
                print("Hei! :", self.table[currentSet][el])
                raise Exception("Atentie! Gramatica nu este de tipul LR1!")

            self.table[currentSet][el] = strng + str(dictionaryOfColumns[el])

    def constructTable(self):
        for set in self.sets:
            for item in set.getItems():
                dictionary = {}
                actionType = ''
                if self.getNextStateAfterSeparator(item) is None:
                    ruleNumber = self.getRuleNumberOfProduction(item)

                    for lookahead in item.lookahead:
                        if ruleNumber != 0:
                            actionType = "REDUCE"
                            dictionary[lookahead] = ruleNumber
                        else:
                            actionType = "ACC"
                            dictionary[lookahead] = ''

                    self.populateTableRow(self.sets.index(set), dictionary, actionType)


    def getRuleNumberOfProduction(self, production: ItemLR1):
        for regProd in self.startingGrammar.getRegProd():
            copyProd = copy.deepcopy(production)
            copyProd.production.right.remove(self.separator)
            if regProd == copyProd.production:
                return self.startingGrammar.getRegProd().index(regProd)

    def getNextStateAfterSeparator(self, item: ItemLR1):
        indexOfSeparator = item.production.right.index(self.separator)

        if indexOfSeparator + 1 >= len(item.production.right):
            return None

        return item.production.right[indexOfSeparator + 1]

    def getLeftSideOfProduction(self, item: ItemLR1):
        return item.production.left

    def areSetsDuplicate(self, set1: Set, set2: Set):
        if str(set1.getNonTerminalProducer()) != str(set2.getNonTerminalProducer()):
            return False

        items1 = set1.getItems()
        items2 = set2.getItems()

        if len(items1) != len(items2):
            return False

        for index in range(0, len(items1)):
            if not (items1[index].production == items2[index].production and
                    items1[index].lookahead == items2[index].lookahead):
                return False

        return True

    def setAlreadyExistsAtIndex(self, set: Set):
        for existingSets in self.sets:
            if self.areSetsDuplicate(set, existingSets):
                return self.sets.index(existingSets)

        return -1

    def get_lookahead(self, item:ItemLR1):
        # A -> a . B b, d and first(b d) = y => B -> (any),y
        separator_index = item.production.right.index(self.separator)
        if separator_index == len(item.production.right) - 1:
            # item already finished
            return (None,None)
        next_elem_after_sep = item.production.right[separator_index + 1] # B
        if next_elem_after_sep not in self.startingGrammar.getNonTerminals():
            # we cant make predictions based on a terminal
            return (None,None)
        lookahead = None
        next_prediction = item.production.right[separator_index + 2] \
                            if separator_index + 2 <= len(item.production.right) - 1 \
                            else None # b
        if next_prediction is not None:
            if next_prediction in self.startingGrammar.getNonTerminals():
                lookahead = copy.copy(self.first[next_prediction])
            else:
                lookahead = copy.copy(next_prediction)
        else:
            lookahead = copy.copy(item.lookahead)
        return next_elem_after_sep, lookahead # (B, y)


    def get_lookahead_from_old_set(self, set:Set, item:ItemLR1):
        lookahead = []
        #print("inherit for set ", item, set)
        for old_item in set.getItems():
            old_copy_item = copy.deepcopy(old_item)
            old_copy_item.production.right.remove(self.separator)
            copy_item = copy.deepcopy(item)
            copy_item.production.right.remove(self.separator)
            #print("compare", old_copy_item.production, copy_item.production)
            if old_copy_item.production == copy_item.production:
                lookahead = old_item.lookahead
                break
        return lookahead

    def create_all_reg_items(self):
        redProds = self.startingGrammar.getRegProd()
        arrow = self.startingGrammar.getArrowSymbol()

        set_all = Set()

        for prod in redProds:
            # prod.insert(prod.index(arrow) + 1, self.separator)

            newProd = copy.deepcopy(prod)

            newProd.right.insert(0, self.separator)

            newItem = ItemLR1(newProd)
            # add lookahead here

            set_all.addItem(newItem)

        # self.sets.append(set_all)
        # self.noSets += 1
        set_all.printItems()
        return set_all

    def constructAllSets(self):
        self.constructSet0()

        all_items = self.create_all_reg_items()

        addedSet = True
        currentSet = 0

        while addedSet:
            self.reset_lookaheads()
            addedSet = False
            print("currentSet", currentSet)
            statesToGoTo = []
            # determine which states do wee need to go
            while len(statesToGoTo) == 0:
                for items in self.sets[currentSet].getItems():

                    stateAfterSeparator = self.getNextStateAfterSeparator(items)

                    if stateAfterSeparator not in statesToGoTo and stateAfterSeparator is not None:
                        statesToGoTo.append(stateAfterSeparator)

                if len(statesToGoTo) == 0:
                    currentSet += 1
                    print("currentSet", currentSet)
                    #break

                if currentSet >= len(self.sets):
                    break

            #print("statesToGoTo",statesToGoTo)
            dictionaryOfWhichSetStateLeadsTo = {}

            for stateToGo in statesToGoTo:
                #print("state To Go",stateToGo)
                newSet = Set(nonTerminal=stateToGo)

                for item in self.sets[currentSet].getItems():
                    stateAfterSeparator = self.getNextStateAfterSeparator(item)
                    #print(stateAfterSeparator)
                    if stateAfterSeparator is not None and stateAfterSeparator == stateToGo and item not in newSet.getItems():
                        newIt = self.createNewItemWithMovedSeparator(item)
                        # add lookahead here
                        lookahead = self.get_lookahead_from_old_set(self.sets[currentSet], newIt)
                        #print("inherit lookahead", lookahead)
                        if lookahead == []:
                            newIt.lookahead = copy.copy(lookahead)

                        # if currentSet in self.lookahead_inherit_tree.keys():
                            # parent_set_index = self.lookahead_inherit_tree[currentSet]
                            # print("parent", parent_set_index)
                            # lookahead = self.get_lookahead_from_old_set(self.sets[parent_set_index], newIt)
                            # print("inherit lookahead", lookahead)
                            # newIt.lookahead = lookahead

                        self.update_lookahead_item(newIt)

                        # set other lookaheads
                        self.update_lookahead_list(newIt)

                        newSet.addItem(newIt)
                #newSet.printItems()
                #print("current state to go", newSet, currentSet)
                for newItems in newSet.getItems():
                    newState = self.getNextStateAfterSeparator(newItems)
                    #print("newState", newState)
                    if newState is not None and newState in self.startingGrammar.getNonTerminals():
                        #print("inhere")
                        for item in all_items.getItems():
                            if self.getLeftSideOfProduction(item) == newState and item not in newSet.getItems():
                                #print("haleluia")
                                # add lookahead here
                                newIt = copy.deepcopy(item)
                                newIt.lookahead = newSet.getItems()[0].lookahead
                                # lookahead = self.get_lookahead_from_old_set(self.sets[currentSet], newIt)
                                # print("inherit lookahead", lookahead)
                                # if lookahead == []:
                                #     newIt.lookahead = copy.copy(lookahead)
                                self.update_lookahead_item(newIt)
                                self.update_lookahead_list(newIt)
                                newSet.addItem(newIt)
                #print("new current state to go", newSet)
                #print("lookaheads", self.lookaheads)
                #print("newSet", newSet)
                if self.setAlreadyExistsAtIndex(newSet) == -1:
                    addedSet = True
                    self.sets.append(newSet)
                    self.addRowAndColumns()

                    dictionaryOfWhichSetStateLeadsTo[stateToGo] = len(self.sets) - 1
                else:
                    dictionaryOfWhichSetStateLeadsTo[stateToGo] = self.setAlreadyExistsAtIndex(newSet)
                if dictionaryOfWhichSetStateLeadsTo[stateToGo] not in self.lookahead_inherit_tree.keys():
                    self.lookahead_inherit_tree[dictionaryOfWhichSetStateLeadsTo[stateToGo]] = currentSet
                self.populateTableRow(currentSet, dictionaryOfWhichSetStateLeadsTo, "SHIFT")
            #print("dictionaryOfWhichSetStateLeadsTo", dictionaryOfWhichSetStateLeadsTo)
            currentSet += 1

        self.constructTable()

    def printAllSets(self):
        for index in range(0, len(self.sets)):
            print("\nSet", index)
            print("Produced by", self.sets[index].getNonTerminalProducer())
            for items in self.sets[index].getItems():
                print(items.production.left, self.startingGrammar.arrow_symbol, items.production.right, ',',
                      items.lookahead)

    def printTable(self):
        print("\nLR Table:")
        for row in range(0, len(self.table)):
            print("Set", row)
            print(self.table[row])
            # for key in self.table[row]:
            #     print(key, self.table[row][key])

    def getLRTable(self):
        return self.table

    def createNewItemWithMovedSeparator(self, item: ItemLR1):
        newIt = copy.deepcopy(item)
        newIt.lookahead = copy.copy(item.lookahead)
        indexOfSeparator = newIt.production.right.index(self.separator)
        newIt.production.right[indexOfSeparator], newIt.production.right[indexOfSeparator + 1] = \
            newIt.production.right[indexOfSeparator + 1], newIt.production.right[indexOfSeparator]

        return newIt



class Trace:
    __STATE_ELEM = "state"
    __ALPH_ELEM = "alph"

    NON_POPPABLE = "$"

    def __init__(self, input: list, step: int = 0, stack: list = [], action: str = ""):
        self.__step = step
        self.__stack = stack
        self.__input = input
        self.__last_action = action
        self.__history = []

    def get_history(self):
        return self.__history

    def add_state_stack(self, state: int):
        stack_elem = {self.__STATE_ELEM: state}
        self.__stack.append(stack_elem)

    def add_alph_stack(self, alph: str):
        stack_elem = {self.__ALPH_ELEM: alph}
        self.__stack.append(stack_elem)

    def pop_input(self):
        elem = self.__input[0]
        if elem != self.NON_POPPABLE:
            self.__input = self.__input[1:]
        return elem

    def push_input(self, alph: str):
        # input_elem = {self.__ALPH_ELEM: alph}
        # self.__input.insert(0,input_elem)
        self.__input = [alph] + self.__input

    def set_action(self, action: ActionType):
        self.__last_action = action.value

    def add_history(self, rule_index: int):
        self.__history.insert(0, rule_index)

    def apply_rule(self, rule: Rule):
        # print("Start reducing")
        current_index_stack = len(self.__stack) - 1
        current_index_rule = len(rule.right) - 1
        # print(len(self.__stack), current_index_stack, current_index_rule)
        while current_index_rule >= 0:
            # print(len(self.__stack), current_index_stack, current_index_rule)
            elem = self.__stack[current_index_stack]
            if self.__STATE_ELEM in elem.keys():
                self.__stack.pop(current_index_stack)
                current_index_stack -= 1
            else:
                self.__stack.pop(current_index_stack)
                current_index_stack -= 1
                current_index_rule -= 1
            # print(self.__stack)
        current_state = self.__stack[current_index_stack][self.__STATE_ELEM]
        return current_state

    def get_current_state(self):
        return self.__stack[-1][self.__STATE_ELEM]

    def increase_step(self):
        self.__step += 1

    def __str__(self):
        return f'(%d,$%s,%s,%s,%s)' % (self.__step, self.__stack, self.__input, self.__last_action, self.__history)


class LRTable:

    def __init__(self, table = []):
        self.mtable = table

    def add_line(self, line: dict) -> None:
        self.mtable.append(line)

    def get_table(self) -> list:
        return self.mtable.copy()

    def get_action(self, current_state: int, current_path: str):
        # print(current_state ,current_path)
        return self.mtable[int(current_state)][current_path]

    def __str__(self):
        string = ""
        for el in self.mtable:
            string += str(el) + "\n"
        return string


class SyntacticParser:

    def __init__(self, table: LRTable, grammar: GrammarParser) -> None:
        self.__lr_table = table
        self.__grammar = grammar

    def __reformat_input(self, input: str) -> list:
        """
        This function should reformat the input according to a grammar
        """
        #return [str(el) for el in (input + "$")]  # temporary
        return input.split(" ") + ["$"]

    def parse(self, input: str) -> list:
        print("Processing input:", input)
        input = self.__reformat_input(input)
        start_state = 0
        trace = Trace(input)
        trace.add_state_stack(start_state)
        is_accepted = False
        print(trace)
        while True:
            current_state = int(trace.get_current_state())
            current_path = trace.pop_input()
            action = self.__lr_table.get_action(current_state, current_path)
            # print(action)
            if action is None:
                break
            action_type, next = self.__decode_action(action)
            next = int(next) if next is not None else None
            # print(action_type, next)

            if action_type == ActionType.SHIFT.value:
                self.__shift(trace, current_path, next)
            elif action_type == ActionType.REDUCE.value:
                self.__reduce(trace, current_path, next, self.__grammar.get_rule(next))
            elif action_type == ActionType.ACCEPT.value:
                is_accepted = True
                trace.set_action(ActionType.ACCEPT)
                break
            # match action_type:
            #     case ActionType.SHIFT.value:
            #         self.__shift(trace,current_path, next)
            #     case ActionType.REDUCE.value:
            #         self.__reduce(trace, current_path, next, self.__grammar.get_rule(next))
            #     case ActionType.ACCEPT.value:
            #         is_accepted = True
            #         trace.set_action(ActionType.ACCEPT)
            #         break
            print(trace)
        if is_accepted:
            print("Input good!")
            return trace.get_history()
        else:
            print("Input Bad!")
            return []

    def __shift(self, trace: Trace, alph: str, next: int):
        trace.add_alph_stack(alph)
        trace.add_state_stack(next)
        trace.set_action(ActionType.SHIFT)
        trace.increase_step()

    def __reduce(self, trace: Trace, alph: str, rule_index: int, rule: Rule):
        # print("rule", rule.right)
        current_state = trace.apply_rule(rule)
        trace.add_alph_stack(rule.left)
        action = self.__lr_table.get_action(current_state, rule.left)
        _, next = self.__decode_action(action)
        trace.add_state_stack(next)
        if alph != Trace.NON_POPPABLE:
            trace.push_input(alph)
        trace.add_history(rule_index)
        trace.set_action(ActionType.REDUCE)
        # return current_state

    def __decode_action(self, action: str) -> tuple:
        sep = "_"
        data = action.split(sep)
        return tuple(data) if len(data) > 1 else (data[0], None)


if __name__ == '__main__':
    print('Hello, Andrei')

    file = 'gramC'
    fileName = file + '.txt'

    col = Collection(fileName)
    col.constructAllSets()
    col.printAllSets()
    col.printTable()

    gP = GrammarParser(fileName)
    gP.parser()
    print("first",gP.get_first_table())
    gP.printResults()

    tablee = LRTable(col.getLRTable())

    # print(tablee.__dict__)
    # # table.add_line({"S":ActionType.SHIFT.value+"_1"+,"A":ActionType.SHIFT.value+"_2","a":ActionType.SHIFT.value+"_3","b":ActionType.SHIFT.value+"_4","$":None})
    # tablee.add_line({"S": "s_1", "A": "s_2", "a": "s_3", "b": "s_4", "$": None})
    # tablee.add_line({"S": None, "A": None, "a": None, "b": None, "$": "acc"})
    # tablee.add_line({"S": None, "A": "s_5", "a": "s_6", "b": "s_7", "$": None})
    # tablee.add_line({"S": None, "A": "s_8", "a": "s_3", "b": "s_4", "$": None})
    # tablee.add_line({"S": None, "A": None, "a": "r_3", "b": "r_3", "$": None})
    # tablee.add_line({"S": None, "A": None, "a": None, "b": None, "$": "r_1"})
    # tablee.add_line({"S": None, "A": "s_9", "a": "s_6", "b": "s_7", "$": None})
    # tablee.add_line({"S": None, "A": None, "a": None, "b": None, "$": "r_3"})
    # tablee.add_line({"S": None, "A": None, "a": "r_2", "b": "r_2", "$": None})
    # tablee.add_line({"S": None, "A": None, "a": None, "b": None, "$": "r_2"})
    #
    # print(tablee)

    # syn_parser = SyntacticParser(tablee,gP)
    # print(syn_parser.parse("a b a b"))
