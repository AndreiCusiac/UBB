Cumparaturi

Pentru a gestiona cumparaturile de care au nevoie inainte de anumite evenimente (excursie, petrecere),
un grup de prieteni din camin folosesc o aplicatie mobila si un server care ofera servicii REST despre 
aceste cumparaturi, un element avand urmatoarele proprietati:
  - id - un intreg pozitiv reprezentand id-ul intern al resursei, ex. 1
  - name - sir de caractere unic, reprezentand numele produsului, ex 'bere'
  - quantity - o lista de perechi { value, user } reprezentand cantitatile dorite de utilizatori,
    ex. [{ value: 3, user: 'a' }, { value: 2, user: 'b' }]
  - totalPrice - numar reprezentand cat a costat achizitionarea produselor, insumand toate cantitatile,
    ex. 3+2 bere au costat 30 lei
  - boughtBy - utilizatorul care a cumparat produsele, ex. 'a'
  - status - sir de caractere reprezentand starea cumparaturii, cu valorile posibile 'active' si 'done'

Aplicatia mobila ofera urmatoarele ecrane separate:

1. (1p) Un ecran ce permite utilizatorului sa-si introduca numele - sir de ceractere. Acest nume este salvat
local, persistent. Pornind de la acest ecran utilizatorul poate deschide ecranele specificate la (2) si (3).

   2. Ecranul celui care posteaza cumparaturile necesare, cu operatii disponibile atat in modul online cat si in modul offline
     a. (1p) Permite inregistrarea unei cumparaturi prin introducerea proprietatilor name si value si declansarea 
     unui buton create care face un POST /item, punand in corpul cererii { name, value, user }.
     b. (1.5p) Afiseaza toate cumparaturile postate de utilizator, pentru fiecare item afisand
     name, value (cat a inregistrat utilizatorul). Daca produsele au fost cumparate (status = 'done'),
     se va afisa si price (cat are de platit utilizatorul) si boughtBy (cui trebuie sa plateasca).
     Datele sunt aduse de pe server via GET /item?postBy=user.

3. Ecranul celor care achizitioneaza produsele, cu operatii disponibile in modul online
  a. (1p) Permite utilizatorului sa afiseze toate elementele active, via GET /item?status=active.
  Pentru fiecare element se afiseaza name si quantity (suma totala a valorilor cerute de utilizatori).
  c. (1.5p) Permite utilizatorului sa inregistreze cumpararea unui element din lista, prin deschiderea unui
  dialog in care se va introduce totalPrice.
  Pe server se va apela PATCH /item/:id cu corpul cererii { totalPrice, status: 'done', boughtBy }

(1p) Serverul emite evenimente prin web socket atunci cand starea unui element se modifica.
Aplicatia client va afisa o notificare si va actualiza datele prezentate pe ecran.  

(1p) In timpul executiei operatiilor de intrare/iesire, locale sau de pe server, aplicatia va afisa
un progress indicator.

(1p) Daca operatiile de intrare/iesire esueaza cu o eroare, utilizatorul va fi notificat si 
i se va permite sa reia (retry) operatia care a esuat. 
