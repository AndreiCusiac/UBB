bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf, fscanf, fprintf, fopen, fclose   ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll

import scanf msvcrt.dll
import printf msvcrt.dll
import fprintf msvcrt.dll
import fscanf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll

    ; exit is a function that ends the calling process. It is defined in msvcrt.dll 
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    car db '%c', 0
    form db '%d', 0
    rea db 'r', 0
    wri db 'w', 0
    
    n dd 0
    k dd 0
    
    nume1 db 'Input.txt', 0
    nume2 db 'output-i.txt', 0
    
    sir db 'abcdegh', 0
    
    desc1 dd -1
    desc2 dd -1
    
; our code starts here
segment code use32 class=code
    start:
        
        push dword rea
        push dword nume1
        call [fopen]
        add esp, 4*2
        
        mov [desc1], eax
        
        cmp eax, 0
        je final
        
        push dword n
        push dword form
        push dword [desc1]
        call [fscanf]
        add esp, 4*3
        
        push dword [desc1]
        call [fclose]
        add esp, 4
        
        inc dword [n]
        
        Repeta:

            cmp dword [n], 0
            je final
            
            mov ecx, [n]
            dec ecx
            add ecx, 48
            mov byte[nume2+7], cl
            
            push dword wri
            push dword nume2
            call [fopen]
            add esp, 4*2
            
            mov [desc2], eax
            
            cmp eax, 0
            je final
            
            mov ecx, [n]
            
            mov esi, sir
            
            cld 
            
            Scriem:
            
                mov [k], ecx
            
                mov eax, 0
                lodsb
                
                push dword eax
                push dword car
                push dword [desc2]
                call [fprintf]
                add esp, 4*3
                
                mov ecx, [k]
                
            loop Scriem
            
            push dword [desc2]
            call [fclose]
            add esp, 4
            
            dec dword [n]
            
            
        jmp Repeta
        
        final:
        
        ; exit(0)
        push dword 0      ; push the parameter for exit onto the stack
        call [exit]       ; call exit to terminate the program
