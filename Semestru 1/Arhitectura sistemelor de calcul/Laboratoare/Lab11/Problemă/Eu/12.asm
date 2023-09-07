bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf   ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll

import scanf msvcrt.dll
import printf msvcrt.dll

    ; exit is a function that ends the calling process. It is defined in msvcrt.dll 
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    s1 db 'ab',0
    ls1 db $-s1
    
    s2 db 'abc', 0
    ls2 db $-s2
    
    s3 db 'abbcc', 0
    ls3 db $-s3
    
; our code starts here
segment code use32 class=code
    start:
        
        ; Se da un nume de fisier (definit in segmentul de date). Sa se creeze un fisier cu numele dat, 
        ; apoi sa se citeasca de la tastatura numere si sa se scrie valorile citite in fisier pana cand se citeste de la tastatura valoarea 0.
        
        ; Cream un fisier pentru scriere
        
        mov esi, s3
        mov ecx, ls3
        
        cld
        
        Repeta:
            stosb
            push dword [al]
            call [printf]
            add esp, 4*1
        
        loop Repeta
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
