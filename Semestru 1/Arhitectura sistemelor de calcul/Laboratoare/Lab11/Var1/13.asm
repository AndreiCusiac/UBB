bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf   ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll

import scanf msvcrt.dll
import printf msvcrt.dll

extern compara

    ; exit is a function that ends the calling process. It is defined in msvcrt.dll 
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32
    ; ...
    
    s1 db 'eabc', 0
    parc1 equ $-s1-2
    ls1 equ $-s1-1
    
    s2 db 'eabc1', 0
    parc2 equ $-s2-2
    ls2 equ $-s2-1
        
    s3 db 'gabecdd', 0
    parc3 equ $-s2-2
    ls3 equ $-s3-1
    
    ls dd 0
    
    fdec db '%d', 0
    fcar db '%c', 0
    
    a dd 0
    b dd 0
    k dd 0
    
    var1 dd 0
    var2 dd 0
    
    mesaj db 'cr %c ', 0
    
    msj db 'try', 0
    
    beste db 'contor este %d', 0
    
    mesajNePareRau db 'Nu s-a gasit un sufix comun', 0
    
    mesajTot db 'Tot cuvantul', 0
    
    linie_noua db 10, 0
    
; our code starts here
segment code use32 public code
    start:
        
        ; Se dau (direct in segmentul de date) trei siruri de caractere. Sa se afiseze cel mai lung sufix comun 
        ; pentru fiecare din cele trei perechi de cate doua siruri ce se pot forma.
        
        mov [b], dword 0
        mov esi, s1
        mov edi, s2
        
        mov dword [var1], parc1
        mov dword [var2], parc2
        
        push dword ls1
        push dword ls2
        
        call compara
        
        mov dword [ls], ebx
        
        mov ecx, [ls]
        
        Repeta:
            mov [k], ecx
            
            mov eax, 0
            mov ebx, 0
            
            mov edx, [var1]

            mov al, [esi+edx]
            
            mov edx, [var2]
            
            mov bl, [edi+edx]
            
            cmp al, bl
            
            je Crestem  
            jne Iesim
            
            Crestem:
                inc dword [b]
                dec dword [var1]
                dec dword [var2]
                jmp Continuam
                
            Iesim:
                mov ebx, [b]
                cmp ebx, 0
                je NePareRau
                jne Afisam
            
            Continuam:
            
            mov ecx, [k]
        
        loop Repeta
        
        Afisam:
            
            mov eax, dword [ls]
            mov ebx, [b]
            
            cmp eax, ebx
            je Tot  
            jne NuTot
            
            Tot:
            
            push dword mesajTot
            call [printf]
            add esp, 4
            
            NuTot:
            
            mov eax, 0
            mov eax, parc1
            sub eax, [b]
            mov esi, eax
            inc esi
            
            mov ecx, [b]
            
            Tiparim:
                mov [k], ecx
            
                mov eax, 0
                mov al, [s1+esi]
                inc esi
                mov [a], eax
                
                push dword [a]
                push dword fcar
                call [printf]
                add esp, 4*2
                    
                mov ecx, [k]
            
            loop Tiparim
            jmp Final
            
        NePareRau:
            push dword mesajNePareRau
            call [printf]
            add esp, 4
            
            jmp Final
        
        Final:
        
        push dword linie_noua
        call [printf]
        add esp, 4
        
        ; exit(0)
        push dword 0      ; push the parameter for exit onto the stack
        call [exit]       ; call exit to terminate the program
