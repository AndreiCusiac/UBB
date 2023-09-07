from Calcule import *
from Verificare import *


def ui_filtrare_cu_scor_mai_mic(lista_participanti, backup_lista):
    # citeste scorul si afiseaza scorurile mai mici decat scorul citit
    # date de intrare: lista_participanti, backup_lista- list
    # date de iesire: afiseaza participantii corespunzatori
    scor = int(input("Introduceti scor: "))
    filtrare_cu_scor_mai_mic(scor, lista_participanti, backup_lista)
    if len(lista_participanti) == 1:
        print("\nNu exista participanti!")
        return
    for i in range(1, len(lista_participanti)):
        if lista_participanti[i] != -1:
            print("Participantul " + str(i) + " cu scorul " + str(lista_participanti[i]) + " ")


def ui_adauga_scor_participant_lista(lista_argumente, lista_participanti, backup_lista):
    # citeste scoruri pentru cele 10 probe
    # date de intare: lista_participanti- list
    # date de iesire: -
    if len(lista_argumente)!=10:
        print("Numar de argumete pentru adaugare invalid!")
        return
    a = []
    try:
        for elem in lista_argumente:
            x = int(elem)
            verificare_scor(x)
            a.append(x)
        scor_total = creeaza_participant(a)
        adauga_scor_participant_lista(scor_total, lista_participanti, backup_lista)
        print("Comanda adauga s-a realizat cu succes!")
    except Exception as ve:
        print("comanda de adaugare NU s-a realizat cu succes\n")


def ui_inserare_scor_participant(lista_participanti, backup_lista):
    # citeste o valoare intreaga, reprezentand id-ul participantului
    # date de intare: lista_participanti-list
    # date de iesire: -
    a = []
    try:
        id_ul = int(input("Introduceti id participant: "))
        verificare_id(id_ul, lista_participanti)
        for i in range(1, 11):
            x = int(input("Introduceti scor pentru proba " + str(i) + ": "))
            verificare_scor(x)
            a.append(x)
        inserare_scor_participant(id_ul, lista_participanti, a, backup_lista)
    except Exception as ve:
        print(str(ve))


def ui_sterge_scor_participant(lista_argumente, lista_participanti, backup_lista):
    # citeste o valoare intreaga, reprezentand id-ul participantului
    # date de intrare: lista_participanti-list
    # date de iesire: -
    if len(lista_argumente) != 1:
        print("Numar de argumente pentru stergere invalid!")
        return
    try:
        for elem in lista_argumente:
            id_ul = int(elem)
        verificare_id(id_ul, lista_participanti)
        sterge_scor_participant(id_ul, lista_participanti, backup_lista)
        print("Stergerea s-a realizat cu succes!")
    except Exception as ve:
        print("Stergerea NU s-a realizat!")


def ui_sterge_scor_interval_participanti(lista_participanti, backup_lista):
    # citeste 2 valori intregi, reprezentand capetele intervalelor
    # date de intrare: lista_participanti-list
    # date de iesire: -
    try:
        id1 = int(input("Introduceti primul participant: "))
        verificare_id(id1, lista_participanti)
        id2 = int(input("Introduceti ultimul participant: "))
        verificare_id(id2, lista_participanti)
        sterge_scor_interval_participanti(id1, id2, lista_participanti, backup_lista)
    except Exception as ve:
        print(str(ve))


def ui_inlocuieste_scor_participant(lista_participanti, backup_lista):
    # citeste id si scoruri probe
    # date de intrare: lista_participanti-list
    # date de iesire: -
    a = []
    try:
        id_ul = int(input("Introduceti id-ul: "))
        verificare_id(id_ul, lista_participanti)
        for i in range(1, 11):
            x = int(input("Introduceti scor pentru proba " + str(i) + ": "))
            verificare_scor(x)
            a.append(x)
        inlocuieste_scor_participant(id_ul, lista_participanti, a, backup_lista)
    except Exception as ve:
        print(str(ve))


def ui_afiseaza_cu_scor_mai_mic(lista_participanti, backup_lista):
    # citeste scor
    # date de intrare: lista_participanti-list
    # date de iesire:-
    scor = int(input("Introduceti scor: "))
    afiseaza_cu_scor_mai_mic(scor, lista_participanti)


def ui_afiseaza_cu_scor_mai_mare(lista_participanti, backup_lista):
    # citeste scor
    # date de intrare: lista_participanti-list
    # date de iesire:-
    scor = int(input("Introduceti scor: "))
    afiseaza_cu_scor_mai_mare(scor, lista_participanti)


def ui_afiseaza_ordonat_dupa_scor(lista_participanti, backup_lista):
    # afiseaza particiantii ordonati dupa scor
    # date de intrare: lista_participanti-list
    # date de iesire:-
    lista = []
    if len(lista_participanti) == 1:
        print("\nNu exista participanti!")
        return
    for i in range(1, len(lista_participanti)):
        if lista_participanti[i] >= 1:
            lista.append([i, lista_participanti[i]])
    lista.sort(key=lambda x: x[1], reverse=True)
    for elem in lista:
        print("Participantul " + str(elem[0]) + " cu scorul: " + str(elem[1]))


def ui_afiseaza_lista(lista_argumente, lista_participanti, backup_lista):
    # afiseaza particiantii
    # date de intrare: lista_participanti-list
    # date de iesire:-
    if len(lista_participanti) == 1:
        print("\nNu exista participanti!")
        return
    for i in range(1, len(lista_participanti)):
        if lista_participanti[i] == -1:
            print("Participantul " + str(i) + " are scorul sters!")
        else:
            print("Participantul " + str(i) + " cu scorul " + str(lista_participanti[i]) + " ")


def citire_comanda():
    # verifica daca comanda citita este un nr intreg
    # date de intrare: -
    # date de iesire: x- intreg
    try:
        lista_comenzi = []
        cmd = input("Introduceti comenzi: ")
        lista_comenzi = cmd.split(';')
        return lista_comenzi
    except ValueError:
        print("\nValoare intreaga nevalida")


def ui_media_scorurilor_interval(lista_argumente, lista_participanti, backup_lista):
    # citeste doua id-uri reprezentand un interval si afiseaza media scorurilor din interval
    # date de intrare: lista_participanti-list
    # date de iesire: media scorurilor- str
    if len(lista_argumente) != 2:
        print("Numar de argumente pentru medie invalid!")
        return
    try:
        id1 = int(lista_argumente[0])
        verificare_id(id1, lista_participanti)
        id2 = int(lista_argumente[1])
        verificare_id(id2, lista_participanti)
        media = media_scorurilor_interval(id1, id2, lista_participanti)
        print("Media scorurilor din interval este: " + str(media))
    except Exception as ve:
        print("Media scorurilor nu s-a realizat!")


def ui_scor_mic_interval(lista_participanti, backup_lista):
    # citeste doua id-uri reprezentand capetele unui interval si afiseaza cel mai mic scor dintr-un interval
    # date de intrare:lista_participanti-list
    # date de iesire : cel mai mic scor- str
    try:
        id1 = int(input("Introduceti primul participant: "))
        verificare_id(id1, lista_participanti)
        id2 = int(input("Introduceti ultimul participant: "))
        verificare_id(id2, lista_participanti)
        scor_mic = scor_mic_interval(id1, id2, lista_participanti)
        for i in range(id1, id2 + 1):
            if lista_participanti[i] == scor_mic:
                print("Cel mai mic scor din interval: " + str(i) + "-" + str(scor_mic))
    except Exception as ve:
        print(str(ve))


def ui_scor_multiplu_de_10(lista_participanti, backup_lista):
    # citeste doua id-uri reprezentand capetele unui interval si afiseaza scorurile multiple de 10
    # date de intrare:lista_participanti-list
    # date de iesire : scorurile multiple de 10 - str
    try:
        id1 = int(input("Introduceti primul participant: "))
        verificare_id(id1, lista_participanti)
        id2 = int(input("Introduceti ultimul participant: "))
        verificare_id(id2, lista_participanti)
        scoruri = scor_multiplu_de_10(id1, id2, lista_participanti)
        if not scoruri:
            print("Nu exista scoruri multiple de 10 in interval.")
        else:
            print("Scoruri multiple de 10: ")
            print(scoruri)
    except Exception as ve:
        print(str(ve))


def ui_filtrare_scor_multiplu(lista_participanti, backup_lista):
    # citeste un scor si afiseaza participantii cu scor multiplu
    # date de intrare: lista_participanti,backup_lista-list
    # date de iesire: afiseaza participanti cu scor multiplu
    nr = int(input("Introduceti scor: "))
    a = filtrare_scor_multiplu(nr, lista_participanti)
    if len(lista_participanti) == 1:
        print("\nNu exista participanti!")
        return
    for i in range(0, len(a)):
        print("Participantul " + str(i + 1) + " cu scorul " + str(a[i]) + " ")


def ui_undo(lista_argumente, lista_participanti, backup_lista):
    # reface ultima operatiune care a modificat lista
    # date de intrare: lista_participanti, backup_lista -list
    # date de iesire: -
    try:
        lista = undo(backup_lista)
        if len(lista) == len(lista_participanti):
            for i in range(0, len(lista_participanti)):
                lista_participanti[i] = lista[i]
        else:
            for i in range(len(lista), len(lista_participanti)):
                lista_participanti.pop()
            for i in range(0, len(lista_participanti)):
                lista_participanti[i] = lista[i]
    except Exception as ve:
        print(str(ve))


def meniu():
    # se defineste meniul: se apeleaza comanda corespunzatoare
    # date de intrare:-
    # date de iesire:-
    lista_participanti = [-1, 10, 35, 47, 56, 60, 79, 45, 10]
    backup_lista = [lista_participanti[:]]
    comenzi = {
        0: [ui_afiseaza_lista, "0.Afiseaza lista de scoruri."],
        1: [ui_adauga_scor_participant_lista, "1.Adauga participant lista"],
        2: [ui_inserare_scor_participant, "2.Inserare scor pentru un participant"],
        3: [ui_sterge_scor_participant, "3.Sterge scor participant"],
        4: [ui_sterge_scor_interval_participanti, "4.Sterge scor pentru un interval de participanti"],
        5: [ui_inlocuieste_scor_participant, "5.Inlocuieste scorul unui participant"],
        6: [ui_afiseaza_cu_scor_mai_mic, "6.Afiseaza participantii care au un scor mai mic decat cel dat"],
        7: [ui_afiseaza_ordonat_dupa_scor, "7.Afiseaza participantii ordonati dupa scor"],
        8: [ui_afiseaza_cu_scor_mai_mare,
            "8.Afiseaza participantii cu un scor mai mare decat cel dat, ordonati dupa scor."],
        9: [ui_media_scorurilor_interval, "9.Calculeaza media scorurilor dintr-un interval."],
        10: [ui_scor_mic_interval, "10.Calculeaza cel mai mic scor dintr-un interval."],
        11: [ui_scor_multiplu_de_10, "11.Tipareste participantii dintr-un interval care au scorul multiplu de 10."],
        12: [ui_filtrare_scor_multiplu, "12.Filtreaza participantii care au scorul multiplu de un nr dat."],
        13: [ui_filtrare_cu_scor_mai_mic, "13.Filtrare participanți care au scorul mai mic decât un scor dat."],
        14: [ui_undo, "14.Reface ultima operație."]
    }
    while True:
        try:

            afisare_meniu(comenzi)
            lista_comenzi = citire_comanda()
            for elem in lista_comenzi:
                cmd = elem.split(' ')
                if verifica_comanda(int(cmd[0]), comenzi):
                    comenzi[int(cmd[0])][0](cmd[1:], lista_participanti, backup_lista)
                else:
                    print("\nComanda invalida")
        except Exception as ve:
            print(str(ve))


def afisare_meniu(comenzi):
    # se afiseaza meniul
    # date de intrare: comenzi- dictionar
    # date de iesire: tipareste pe ecran
    print("\nWelcome\n")
    print("Comenzi:\n")
    for i in comenzi:
        print(comenzi[i][1])
