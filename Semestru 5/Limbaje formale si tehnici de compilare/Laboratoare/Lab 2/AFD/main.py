import re

class AutomatFinit:
    #
    # def __init__(self, states, final_states, alphabet, transitions, trans_dict):
    #     self.states = states
    #     self.final_states = final_states
    #     self.alphabet = alphabet
    #     self.transitions = transitions
    #     self.trans_dict = trans_dict

    def __init__(self):
        self.states = []
        self.initial_states = []
        self.final_states = []
        self.alphabet = []
        self.transitions = []
        self.trans_dict = {}
        self.nedeterminist_flag = False

    def setStates(self, val):
        self.states = val

    def setFinalStates(self, val):
        self.final_states = val

    def setAlphabet(self, val):
        self.alphabet = val

    def setTransitions(self, val):
        self.transitions = val

    def setTransDict(self, val):
        self.trans_dict = val

    def readAFConsole(self):
        self.nedeterminist_flag = False
        sts = input("Dati multimea starilor, separate prin spatiu: ")

        spl = re.split(' |\n|\r|\t', sts)
        self.states.clear()
        for el in spl:
            self.states.append(el)

        stf = input("Dati multimea starilor initiale, separate prin spatiu: ")

        spl = re.split(' |\n|\r|\t', stf)
        self.initial_states.clear()
        for el in spl:
            self.initial_states.append(el)

        stf = input("Dati multimea starilor finale, separate prin spatiu: ")

        spl = re.split(' |\n|\r|\t', stf)
        self.final_states.clear()
        for el in spl:
            self.final_states.append(el)

        self.transitions.clear()
        self.alphabet.clear()
        print("Dati multimea tranzitiilor, sau stop pentru oprire. ")
        inp = ""
        while inp != "stop":
            inp = input("Tranzitie: ")
            if inp == "stop":
                break
            spl = re.split(' |\n|\r|\t', inp)
            t1 = spl[0]
            t2 = spl[1]
            t3 = spl[2]
            if (t1, t2) in self.trans_dict.keys():
                #raise Exception("Atentie! Automatul finit nu este determinist!")
                self.nedeterminist_flag = True
            self.trans_dict[(t1, t2)] = t3
            transition_tuple = (t1, t2, t3)
            self.transitions.append(transition_tuple)
            if t2 not in self.alphabet:
                self.alphabet.append(t2)

    def readAF(self, file):
        self.nedeterminist_flag = False
        self.states.clear()
        self.initial_states.clear()
        self.final_states.clear()
        self.transitions.clear()
        self.alphabet.clear()
        self.trans_dict.clear()

        f = open(file, mode='r', encoding='utf8', newline='\r\n')

        for i, line in enumerate(f):
            # print(line, end="")

            # print(i)

            spl = re.split(' |\n|\r|\t', line)
            # print(spl)

            t1 = None
            t2 = None
            t3 = None
            transition_tuple = None

            for el in spl:
                # print(el)

                if el == '' or el is None or el == '\n' or el == '\r' or el == '\t':
                    continue

                if i == 0:
                    self.states.append(el)
                elif i == 1:
                    self.initial_states.append(el)
                elif i == 2:
                    self.final_states.append(el)
                else:
                    if t1 is None:
                        t1 = el
                    elif t2 is None:
                        t2 = el
                        if el not in self.alphabet:
                            self.alphabet.append(el)
                    else:
                        t3 = el
                        if (t1, t2) in self.trans_dict.keys():
                            #raise Exception("Atentie! Automatul finit nu este determinist!")
                            self.nedeterminist_flag = True
                        self.trans_dict[(t1, t2)] = t3
                        transition_tuple = (t1, t2, t3)
                        # print(trans_dict)
                        self.transitions.append(transition_tuple)

    def displayAF(self):
        print("Multimea starilor:", self.states)
        print("Alfabetul:", self.alphabet)
        print("Multimea tranzitiilor:", self.transitions)
        print("Multimea starilor initiale:", self.initial_states)
        print("Multimea starilor finale:", self.final_states)

    def displayAFMeniu(self):
        print()
        print("1. Afisati multimea starilor")
        print("2. Afisati alfabetul")
        print("3. Afisati multimea tranzitiilor")
        print("4. Afisati multimea starilor initiale")
        print("5. Afisati multimea starilor finale")
        print("6. Afisati intreg automatul")
        print("0. Iesiti")

        return input("Optiune:")

    def displayMenu(self):
        print()
        print("1. Afisati elementele automatului finit")
        print("2. Verificati o secventa")
        print("3. Determinati cel mai lung prefix acceptat")
        print("4. Folositi un nou automat")
        print("0. Iesiti")

        return input("Optiune:")

    def checkSecventa(self, secv):

        for current_state in self.initial_states:
            check = True

            for letter in secv:
                if (current_state, letter) not in self.trans_dict.keys():
                    check = False
                    break
                else:
                    current_state = self.trans_dict[(current_state, letter)]

            # print(chars)

            if current_state not in self.final_states:
                check = False

            if check:
                return True

        return False

    # def checkSecventa(self, secv):
    #     current_state = self.states[0]
    #
    #     for letter in secv:
    #         if (current_state, letter) not in self.trans_dict.keys():
    #             return False
    #         else:
    #             current_state = self.trans_dict[(current_state, letter)]
    #
    #     # print(chars)
    #
    #     if current_state not in self.final_states:
    #         return False
    #
    #     return True

    def checkPrefix(self, secv):
        word = ''
        last_accepted = ''

        for letter in secv:
            word += letter

            if self.checkSecventa(word):
                last_accepted = word



        if last_accepted == '' and self.checkSecventa(last_accepted):
            return "secventa vida acceptata"
        else:
            return last_accepted
        #return word

    # def checkPrefix(self, secv):
    #     current_state = self.states[0]
    #
    #     word = ''
    #     last_accepted = ''
    #
    #     for letter in secv:
    #         if (current_state, letter) not in self.trans_dict.keys():
    #             # return word
    #             break
    #         else:
    #             current_state = self.trans_dict[(current_state, letter)]
    #             word += letter
    #             if current_state in self.final_states:
    #                 last_accepted = word
    #
    #     if last_accepted == '' and self.checkSecventa(last_accepted):
    #         return "secventa vida acceptata"
    #
    #     if current_state not in self.trans_dict.keys():
    #         return last_accepted
    #
    #     return word

    def displayAccepted(self):
        secv = ""

        while secv != "stop":
            print()
            secv = input("Dati secventa (stop pentru iesire):")
            if self.checkSecventa(secv):
                print("Secventa acceptata")
            else:
                print("Secventa neacceptata")

    def displayPrefix(self):
        secv = ""

        while secv != "stop":
            print()
            secv = input("Dati secventa (stop pentru iesire):")
            pre = self.checkPrefix(secv)
            if pre == 'secventa vida acceptata':
                print("Cel mai lung prefix acceptat:", "")
            elif pre != '':
                print("Cel mai lung prefix acceptat:", pre)
            else:
                print("Secventa neacceptata")

    def displayChange(self):
        print()
        print("1. Modificati printr-un fisier de intrare")
        print("2. Modificati de la consola")
        print("0. Iesiti")

        return input("Optiune:")

    def meniu(self, file_name):
        print("Bine ati venit la programul de automate finite deterministe!")
        if file_name != "":
            print("Automat finit citit din fisierul", file_name)

        running = True
        print("Doriti sa:")
        while running:
            opt = self.displayMenu()

            if opt == "0":
                print("Final program.")
                running = False
                break
            elif opt == "1":
                opt2 = self.displayAFMeniu()
                while opt2 != "0":
                    if opt2 == "1":
                        print("Multimea starilor:", self.states)
                    elif opt2 == "2":
                        print("Alfabetul:", self.alphabet)
                    elif opt2 == "3":
                        print("Multimea tranzitiilor:", self.transitions)
                    elif opt2 == "4":
                        print("Multimea starilor initiale:", self.initial_states)
                    elif opt2 == "5":
                        print("Multimea starilor initiale:", self.final_states)
                    elif opt2 == "6":
                        print("Multimea starilor:", self.states)
                        print("Alfabetul:", self.alphabet)
                        print("Multimea tranzitiilor:", self.transitions)
                        print("Multimea starilor initiale:", self.initial_states)
                        print("Multimea starilor finale:", self.final_states)
                    opt2 = self.displayAFMeniu()
            elif opt == "2":
                if self.nedeterminist_flag:
                    print("Atentie! Automatul actual este nedeterminist!")
                else:
                    self.displayAccepted()
            elif opt == "3":
                if self.nedeterminist_flag:
                    print("Atentie! Automatul actual este nedeterminist!")
                else:
                    self.displayPrefix()
            elif opt == "4":
                opt2 = self.displayChange()
                if opt2 == "1":
                    file = input("Dati numele fisierului: ")
                    self.readAF(file + '.txt')
                elif opt2 == "2":
                    self.readAFConsole()
                else:
                    pass
#
# af_state = "file"
# states = []
# final_states = []
# alphabet = []
# transitions = []
# trans_dict = {}
#
# def readAFConsole():
#     sts = input("Dati multimea starilor, separate prin spatiu: ")
#
#     spl = re.split(' |\n|\r|\t', sts)
#     states.clear()
#     for el in spl:
#         states.append(el)
#
#     stf = input("Dati multimea starilor finale, separate prin spatiu: ")
#
#     spl = re.split(' |\n|\r|\t', stf)
#     final_states.clear()
#     for el in spl:
#         final_states.append(el)
#
#     transitions.clear()
#     alphabet.clear()
#     print("Dati multimea tranzitiilor, sau stop pentru oprire. ")
#     inp = ""
#     while inp != "stop":
#         inp = input("Tranzitie: ")
#         if inp == "stop":
#             break
#         spl = re.split(' |\n|\r|\t', inp)
#         t1 = spl[0]
#         t2 = spl[1]
#         t3 = spl[2]
#         if (t1, t2) in trans_dict.keys():
#             raise Exception("Atentie! Automatul finit nu este determinist!")
#         trans_dict[(t1, t2)] = t3
#         transition_tuple = (t1, t2, t3)
#         transitions.append(transition_tuple)
#         if el not in alphabet:
#             alphabet.append(el)
#
#
# def readAF(file_name):
#
#     f = open(file_name, mode='r', encoding='utf8', newline='\r\n')
#
#     for i, line in enumerate(f):
#         # print(line, end="")
#
#         # print(i)
#
#         spl = re.split(' |\n|\r|\t', line)
#         # print(spl)
#
#         t1 = None
#         t2 = None
#         t3 = None
#         transition_tuple = None
#
#         for el in spl:
#             #print(el)
#
#             if el == '' or el is None or el == '\n' or el == '\r' or el == '\t':
#                 continue
#
#             if i == 0:
#                 states.append(el)
#             elif i == 1:
#                 final_states.append(el)
#             else:
#                 if t1 is None:
#                     t1 = el
#                 elif t2 is None:
#                     t2 = el
#                     if el not in alphabet:
#                         alphabet.append(el)
#                 else:
#                     t3 = el
#                     if (t1, t2) in trans_dict.keys():
#                         raise Exception("Atentie! Automatul finit nu este determinist!")
#                     trans_dict[(t1, t2)] = t3
#                     transition_tuple = (t1, t2, t3)
#                     #print(trans_dict)
#                     transitions.append(transition_tuple)
#
#
#
# def displayAF():
#     print("Multimea starilor:", states)
#     print("Alfabetul:", alphabet)
#     print("Multimea tranzitiilor:", transitions)
#     print("Multimea starilor finale:", final_states)
#
# def displayAFMeniu():
#     print()
#     print("1. Afisati multimea starilor")
#     print("2. Afisati alfabetul")
#     print("3. Afisati multimea tranzitiilor")
#     print("4. Afisati multimea starilor finale")
#     print("5. Afisati intreg automatul")
#     print("0. Iesiti")
#
#     return input("Optiune:")
#
# def displayMenu():
#     print()
#     print("1. Afisati elementele automatului finit")
#     print("2. Verificati o secventa")
#     print("3. Determinati cel mai lung prefix acceptat")
#     print("4. Folositi un nou automat")
#     print("0. Iesiti")
#
#     return input("Optiune:")
#
#
# def checkSecventa(secv):
#     current_state = states[0]
#
#     for letter in secv:
#         if (current_state, letter) not in trans_dict.keys():
#             return False
#         else:
#             current_state = trans_dict[(current_state, letter)]
#
#     #print(chars)
#
#     if current_state not in final_states:
#         return False
#
#     return True
#
# def checkPrefix(secv):
#     current_state = states[0]
#
#     word = ''
#     last_accepted = ''
#
#     for letter in secv:
#         if (current_state, letter) not in trans_dict.keys():
#             #return word
#             break
#         else:
#             current_state = trans_dict[(current_state, letter)]
#             word += letter
#             if current_state in final_states:
#                 last_accepted = word
#
#     if last_accepted == '' and checkSecventa(last_accepted):
#         return "secventa vida acceptata"
#
#     if current_state not in trans_dict.keys():
#         return last_accepted
#
#     return word
#
# def displayAccepted():
#     secv = ""
#
#     while secv != "stop":
#         print()
#         secv = input("Dati secventa (stop pentru iesire):")
#         if checkSecventa(secv):
#             print("Secventa acceptata")
#         else:
#             print("Secventa neacceptata")
#
#
# def displayPrefix():
#     secv = ""
#
#     while secv != "stop":
#         print()
#         secv = input("Dati secventa (stop pentru iesire):")
#         pre = checkPrefix(secv)
#         if pre == 'secventa vida acceptata':
#             print("Cel mai lung prefix acceptat:", "")
#         elif pre != '':
#             print("Cel mai lung prefix acceptat:", pre)
#         else:
#             print("Secventa neacceptata")
#
#
# def displayChange():
#     print()
#     print("1. Modificati printr-un fisier de intrare")
#     print("2. Modificati de la consola")
#     print("0. Iesiti")
#
#     return input("Optiune:")
#
#
# def meniu(file_name):
#     print("Bine ati venit la programul de automate finite deterministe!")
#     if file_name != "":
#         print("Automat finit citit din fisierul", file_name)
#
#     running = True
#     print("Doriti sa:")
#     while running:
#         opt = displayMenu()
#
#         if opt == "0":
#             print("Final program.")
#             running = False
#             break
#         elif opt == "1":
#             opt2 = displayAFMeniu()
#             while opt2 != "0":
#                 if opt2 == "1":
#                     print("Multimea starilor:", states)
#                 elif opt2 == "2":
#                     print("Alfabetul:", alphabet)
#                 elif opt2 == "3":
#                     print("Multimea tranzitiilor:", transitions)
#                 elif opt2 == "4":
#                     print("Multimea starilor finale:", final_states)
#                 elif opt2 == "5":
#                     print("Multimea starilor:", states)
#                     print("Alfabetul:", alphabet)
#                     print("Multimea tranzitiilor:", transitions)
#                     print("Multimea starilor finale:", final_states)
#                 opt2 = displayAFMeniu()
#         elif opt == "2":
#             displayAccepted()
#         elif opt == "3":
#             displayPrefix()
#         elif opt == "4":
#             opt2 = displayChange()
#             if opt2 == "1":
#                 file = input("Dati numele fisierului: ")
#                 readAF(file + '.txt')
#             elif opt2 == "2":
#                 readAFConsole()
#             else:
#                 pass


if __name__ == '__main__':
    name = 'symbol_literals'
    file_name = name + '.txt'
    af = AutomatFinit()
    af.readAF(file_name)
    #readAF(name + '.txt')
    #displayAF()
    af.meniu(file_name)

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
