import collections
import numbers
import re

from MyHash import MyHashTable

M_FOR_HASH = 13
hash = MyHashTable(M_FOR_HASH)
MAXIMUM_ACCEPTED_LENGTH_OF_ID = 8

dictionary_of_reserved = {}
fip = []
reserved_words_and_symbols = ["#", "include", "using", "namespace", "std", "main", "int", "float", "unsigned", "cout",
                              "cin", "if", "else", "while", "(", ")", "{", "}", "+", "-", "*", "/", ";", '"', "!=", "=",
                              "<=",
                              ">=", "<<", ">>", "<", ">", "return", "iostream"]

reserved_words = ["include", "using", "namespace", "std", "main", "int", "float", "unsigned", "cout", "cin", "if", "else", "while", "return", "iostream"]

erors = []

for i in range(0, len(reserved_words_and_symbols)):
    dictionary_of_reserved[reserved_words_and_symbols[i]] = i

keys_of_reserved = dictionary_of_reserved.keys()
NO_OF_CONST_AND_IDS = len(dictionary_of_reserved)


def separate_registereds(str):
    str3 = str
    str4 = str

    for el in reserved_words_and_symbols:
        occurances_of_current_el = 0
        while el in str4:
            # print(el)
            occurances_of_current_el += 1

            length_of_reserved_found = len(el)

            position_of_reserved_found = -1
            for i in range(0, occurances_of_current_el):
                position_of_reserved_found = str3.find(el, position_of_reserved_found + 1)

            position_of_next = position_of_reserved_found + length_of_reserved_found

            str2 = str4.replace(el, "", 1)
            str4 = str2

            # print("Str3 now:", str3, "\n El now: ", el, "\n length: ", length_of_reserved_found, "\n position of next: ", position_of_next)
            # print("Str4:", str4)

            str3 = str3[:position_of_reserved_found] + ' ' + str3[
                                                             position_of_reserved_found:position_of_next] + ' ' + str3[
                                                                                                                  position_of_next:]

    print("\nStr3:", str3)
    # str3 = re.split(' |\n|\r|\t', str3)
    str3 = str3.split()
    str3 = [i for i in str3 if i != ""]
    # print("Str3 after split: ", str3)

    # if (str1 != ""):
    #     dict[str.find(str1)] = str1
    #
    # od = collections.OrderedDict(sorted(dict.items()))
    #
    # for k, v in od.items():
    #     rez.append(v)
    #
    # print("Rez: ", rez)

    return str3


def parser(file_name):
    f = open(file_name, mode='r', encoding='utf8', newline='\r\n')
    p = []

    for i, line in enumerate(f):
        print(line, end="")

        # spl = re.split(' |\n|\r|\t', line)
        spl = line.split()
        print("Split: ", spl)
        for el in spl:
            if el in reserved_words_and_symbols:
                p.append(el)
            elif el != "":
                rez = separate_registereds(el)
                if rez is not None:
                    for e in rez:
                        p.append(e)

    # print(p)
    return p


def reader(file_name):
    f = open(file_name, mode='r', encoding='utf8', newline='\r\n')

    with open(file_name, "r") as filestream:
        for line in filestream:
            currentline = line.split("\n")
            print(currentline)


def writer(param, file_name):
    f = open(file_name, mode='w', encoding='utf8', newline='\r\n')

    for el in param:
        f.write(el)
        f.write('\n')

    f.close()


def write_fip(param, file_name):
    f = open(file_name, mode='w', encoding='utf8', newline='\r\n')

    for el in param:
        f.write(str(el[0]))
        f.write(" ")
        f.write(str(el[1]))
        f.write('\n')

    f.close()


def isNumber(str):
    try:
        float(str)
        return True
    except ValueError:
        return False


def is_valid_identifier(atom):
    reg = "^[a-zA-Z_][a-zA-Z0-9_]*$"

    if re.match(reg, atom) is None:
        return False

    return True


def form_intern(list_of_atoms):
    for atom in list_of_atoms:

        if atom in keys_of_reserved:
            new_tuple = (dictionary_of_reserved[atom], '-')
        else:
            # print(type(atom))

            new_tuple = None

            if not isNumber(atom):
                if not (len(atom) < MAXIMUM_ACCEPTED_LENGTH_OF_ID and is_valid_identifier(atom) == True):
                    new_tuple = (NO_OF_CONST_AND_IDS, "EROARE")

                    if atom not in erors:
                        erors.append(atom)

            if new_tuple is None and hash.findElement(atom) == -1:
                hash.addElement(atom)

            if new_tuple == None:
                new_tuple = (NO_OF_CONST_AND_IDS, hash.findElement(atom))

        fip.append(new_tuple)


def cauta_erori(file_name, string_to_search):
    line_number = 0
    list_of_results = []

    with open(file_name, 'r') as read_obj:
        for line in read_obj:
            line_number += 1
            if string_to_search in line:
                list_of_results.append((line_number, line.rstrip()))

    return list_of_results


def display_errors(file_name):
    if len(erors) == 0:
        print("Nicio eroare intalnita.")
        return
    for el in erors:
        print("Eroare:", el, "\nIntalnita pe liniile:")
        print(cauta_erori(file_name, el))


if __name__ == '__main__':
    print("Dictionary:", dictionary_of_reserved)
    print("NO_OF_CONST_AND_IDS:", NO_OF_CONST_AND_IDS)
    # print(dictionary_of_reserved.items())

    name = "3"

    # reader('1.txt')
    list_of_atoms = parser(name + '.txt')
    writer(list_of_atoms, name + "out.txt")
    form_intern(list_of_atoms)
    write_fip(fip, name + 'fip.txt')

    # print(fip)
    # print(hash.getInternalForm())
    # print(hash.getAll())
    # print("TS:", hash.getTS())

    display_errors(name + '.txt')

    # reader('3.txt')

    # tuple = (1, '-')
    # tuple2 = (3, "-")
    # tuple3 = (10, 3)
    # fip.append(tuple)
    # fip.append(tuple2)
    # fip.append(tuple3)
    #

    # list = [[] for _ in range(5)]
    # new_tuple = (1, 2)
    # list[3].append(new_tuple)
    # print(list)
