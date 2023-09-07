bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fscanf, fprintf, fopen, fclose, scanf, printf   ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll

import scanf msvcrt.dll
import printf msvcrt.dll

import fscanf msvcrt.dll
import fprintf msvcrt.dll

import fopen msvcrt.dll
import fclose msvcrt.dll

    ; exit is a function that ends the calling process. It is defined in msvcrt.dll 
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    nr dd 0
    k db 0
    
    fdec db '%d', 0
    
    nume_fis db 'NumeDeFisier.txt', 0
    
    mod_acces_scriere db 'w', 0
    
    desc_nume_fis dd 0
    
    mesaj_intampinare db 'Dati numere pana la valoarea 0:', 0
    
    mesaj_citire db 'nr = ', 0
    
    mesaj db 'Numerele citite de la tastatura sunt: ', 0
    mesaj_vid db 'Nu s-au introdus valori nenule!', 0
    
    linie_noua db 10, 0
    
    spatiu_nou db ' ', 0
    
; our code starts here
segment code use32 class=code
    start:
        
        ; Se da un nume de fisier (definit in segmentul de date). Sa se creeze un fisier cu numele dat, 
        ; apoi sa se citeasca de la tastatura numere si sa se scrie valorile citite in fisier pana cand se citeste de la tastatura valoarea 0.
        
        ; Cream un fisier pentru scriere
        
        push dword mod_acces_scriere
        push dword nume_fis
        call [fopen]
        add esp, 4*2
        
        mov [desc_nume_fis], eax ; salvam descriptorul fisierului
        
        cmp eax, 0 ; verificam daca a aparut o eroare la crearea fisierului
        je final ; daca da, incheiem rularea programului
        
        ; Scriem un mesaj de confirmare in fisier
        
        push dword mesaj
        ;push dword linie_noua
        push dword [desc_nume_fis]
        call [fprintf]
        add esp, 4*2
        
        push dword linie_noua
        push dword [desc_nume_fis]
        call [fprintf]
        add esp, 4*1
        
        ; Afisam in consola un mesaj pentru utilizator
        
        push dword mesaj_intampinare
        ;push dword linie_noua
        call [printf]
        add esp, 4*1
        
        push dword linie_noua
        call [printf]
        add esp, 4*1
        
        mov ecx, 2 ; asiguram desfasurarea loop-ului de citire de cate ori este nevoie 
        
        RepetamCititulPanaLa0:
            
            ; "nr = "
            
            push dword mesaj_citire
            call [printf]
            add esp, 4*1
            
            push dword nr
            push dword fdec
            call [scanf]
            add esp, 4*2
            
            mov bl, [nr]
            cmp bl, 0 ; verificam daca s-a citit valoarea 0
            
            je InAfaraLupului ; daca da, iesim din loop
            jne InLup ; daca nu, continuam loop-ul
            
            InLup:
                
                ; Adaugam numarul citit in fisier
            
                push dword [nr]
                push dword fdec
                push dword [desc_nume_fis]
                call [fprintf]
                add esp, 4*3
                
                inc byte[k] ; verificam in contor daca s-au citit numere nenule
                
                ; Adaugam un spatiu pentru lizibilitate
                
                push dword spatiu_nou
                push dword [desc_nume_fis]
                call [fprintf]
                add esp, 4*2
                
                mov ecx, 2
            
        loop RepetamCititulPanaLa0
        
        InAfaraLupului:
            
            mov bl, [k]
            cmp bl, 0 ; verificam daca s-au citit numere nenule
            
            je MesajDeAtentionare ; daca da, scriem in fisier un mesaj de atentionare
            jne Incheiere ; daca nu, inchidem fisierul
            
            MesajDeAtentionare:
                
                push dword mesaj_vid
                push dword [desc_nume_fis]
                call [fprintf]
                add esp, 4*2
                
                
        Incheiere:
            
            push dword [desc_nume_fis]
            call [fclose]
            add esp, 4
        
        final:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
