bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf   ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll

import scanf msvcrt.dll
import printf msvcrt.dll

extern sufix

    ; exit is a function that ends the calling process. It is defined in msvcrt.dll 
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    s1 db 'aa', 0
    ls1 equ $-s1-2
    parc1 equ $-s1-1
    
    s2 db 'aac3dd3', 0
    ls2 equ $-s2-2
    parc2 equ $-s2-1
        
    s3 db 'c123d', 0
    ls3 equ $-s3-2
    parc3 equ $-s3-1
    
    ls dd 0
    
    fdec db '%d', 0
    fcar db '%c', 0
    
    b dd 0
    k dd 0
    
    mesajNePareRau db 'Nu s-a gasit un sufix comun', 0
    
    mesajPrimulSir db 'Primul sir: ', 0
    mesajAlDoileaSir db 'Al doilea sir: ', 0
    mesajAlTreileaSir db 'Al treilea sir: ', 0
    
    mesajAfis12 db 'Sufix 1-2: ', 0
    mesajAfis13 db 'Sufix 1-3: ', 0
    mesajAfis23 db 'Sufix 2-3: ', 0
    
    linie_noua db 10, 0
    
; our code starts here
segment code use32 class=code
    start:
        
        ; Se dau (direct in segmentul de date) trei siruri de caractere. Sa se afiseze cel mai lung sufix comun 
        ; pentru fiecare din cele trei perechi de cate doua siruri ce se pot forma.
        
        push dword mesajPrimulSir
        call [printf]
        add esp, 4
        
        push dword s1
        call [printf]
        add esp, 4
        
        push dword linie_noua
        call [printf]
        add esp, 4
        
        push dword mesajAlDoileaSir
        call [printf]
        add esp, 4
        
        push dword s2
        call [printf]
        add esp, 4
        
        push dword linie_noua
        call [printf]
        add esp, 4
        
        push dword mesajAlTreileaSir
        call [printf]
        add esp, 4
        
        push dword s3
        call [printf]
        add esp, 4
        
        push dword linie_noua
        call [printf]
        add esp, 4
        
        push dword linie_noua
        call [printf]
        add esp, 4
        
        ; primul si al doilea sir
        
        push dword mesajAfis12
        call [printf]
        add esp, 4
        
        push dword parc2
        push dword parc1
        push dword ls2
        push dword ls1
        push dword s2
        push dword s1
        
        call sufix ; trimitem parametrii in functie
        
        cmp ebx, 0 ; verificam daca exista un sufix de afisat
        je NePareRau1 ; daca nu, afisam un mesaj de eroare
        jne Afisam1 ; daca da, afisam sufixul
        
        Afisam1:
            
            cld
            
            mov esi, s1
            add esi, parc1
            sub esi, ebx ; ne pozitionam intr-unul din cele 2 siruri, pe pozitia la care a fost gasit ultimul caracter comum
            
            mov ecx, ebx
            
            Tiparim1:
                mov [k], ecx
            
                mov eax, 0
                lodsb ; incarcam in eax caracterul curent 
                
                push dword eax ; si il afisam
                push dword fcar
                call [printf]
                add esp, 4*2
                    
                mov ecx, [k]
            
            loop Tiparim1
            jmp Final1
            
        NePareRau1:
            push dword mesajNePareRau
            call [printf]
            add esp, 4
            
            jmp Final1
        
        Final1:
        
        push dword linie_noua
        call [printf]
        add esp, 4
        
        ; primul si al treilea sir
        
        push dword mesajAfis13
        call [printf]
        add esp, 4
        
        push dword parc3
        push dword parc1
        push dword ls3
        push dword ls1
        push dword s3
        push dword s1
        
        call sufix ; trimitem parametrii in functie
        
        cmp ebx, 0 ; verificam daca exista un sufix de afisat
        je NePareRau2 ; daca nu, afisam un mesaj de eroare
        jne Afisam2 ; daca da, afisam sufixul
        
        Afisam2:
            
            cld
            
            mov esi, s1
            add esi, parc1
            sub esi, ebx ; ne pozitionam intr-unul din cele 2 siruri, pe pozitia la care a fost gasit ultimul caracter comum
            
            mov ecx, ebx
            
            Tiparim2:
                mov [k], ecx
            
                mov eax, 0
                lodsb ; incarcam in eax caracterul curent 
                
                push dword eax ; si il afisam
                push dword fcar
                call [printf]
                add esp, 4*2
                    
                mov ecx, [k]
            
            loop Tiparim2
            jmp Final2
            
        NePareRau2:
            push dword mesajNePareRau
            call [printf]
            add esp, 4
            
            jmp Final2
        
        Final2:
        
        push dword linie_noua
        call [printf]
        add esp, 4
        
        ; al doilea si al treilea sir
        
        push dword mesajAfis23
        call [printf]
        add esp, 4
        
        push dword parc2
        push dword parc3
        push dword ls2
        push dword ls3
        push dword s2
        push dword s3
        
        call sufix ; trimitem parametrii in functie
        
        cmp ebx, 0 ; verificam daca exista un sufix de afisat
        je NePareRau3 ; daca nu, afisam un mesaj de eroare
        jne Afisam3 ; daca da, afisam sufixul
        
        Afisam3:
            
            cld
            
            mov esi, s3
            add esi, parc3
            sub esi, ebx ; ne pozitionam intr-unul din cele 2 siruri, pe pozitia la care a fost gasit ultimul caracter comum
            
            mov ecx, ebx
            
            Tiparim3:
                mov [k], ecx
            
                mov eax, 0
                lodsb ; incarcam in eax caracterul curent 
                
                push dword eax ; si il afisam
                push dword fcar
                call [printf]
                add esp, 4*2
                    
                mov ecx, [k]
            
            loop Tiparim3
            jmp Final3
            
        NePareRau3:
            push dword mesajNePareRau
            call [printf]
            add esp, 4
            
            jmp Final3
        
        Final3:
        
        ; exit(0)
        push dword 0      ; push the parameter for exit onto the stack
        call [exit]       ; call exit to terminate the program
