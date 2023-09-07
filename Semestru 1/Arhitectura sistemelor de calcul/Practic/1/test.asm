bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf, fprintf, fopen, fclose   ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll

import scanf msvcrt.dll
import printf msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll
import fopen msvcrt.dll

    ; exit is a function that ends the calling process. It is defined in msvcrt.dll 
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    nume_fis db '1-impar_neg.txt', 0
    
    typ db 'w', 0
    desc dd -1
    
    a dd 0
    b dd 0

    spatiu_nou db ' ', 0
    linie_noua db 10, 0
    form db '%d', 0
    
; our code starts here
segment code use32 class=code
    start:
        
        push dword typ
        push dword nume_fis
        call [fopen]
        add esp, 4*2
        
        mov [desc], eax
        
        cmp eax, 0
        je final
        
        Repeta:
        
            push dword a
            push dword form
            call [scanf]
            add esp, 4*2
            
            cmp dword [a], 0
            je iesim
            jl Verificam
            jg continuam
            
            Verificam:
                
                mov eax, [a]
                mov ebx, 0
                
                Impartim:
                    
                    shr eax, 1
                    jc Incrementam
                    jnc MaiVedem
                    
                    Incrementam:
                        inc ebx
                    
                    MaiVedem:
                        
                        cmp eax, 0
                        je ScriemSauNuScriem
                        jne Impartim
            
            ScriemSauNuScriem:
                
                shr ebx, 1
                jc Scriem
                jnc continuam
                
                Scriem:
                    
                    push dword [a]
                    push dword form
                    push dword [desc]
                    call [fprintf]
                    add esp, 4*3
                    
                    push dword spatiu_nou
                    push dword [desc]
                    call [fprintf]
                    add esp, 4*2
            
            continuam:
        
        jmp Repeta
        
        iesim:
        push dword [desc]
        call [fclose]
        add esp, 4
        
        final:
        
        ; exit(0)
        push dword 0      ; push the parameter for exit onto the stack
        call [exit]       ; call exit to terminate the program
