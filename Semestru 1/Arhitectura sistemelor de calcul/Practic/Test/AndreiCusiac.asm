bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf, fopen, fclose, fprintf, fscanf, fread   ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll

import scanf msvcrt.dll
import printf msvcrt.dll
import fclose msvcrt.dll
import fopen msvcrt.dll
import fscanf msvcrt.dll
import fprintf msvcrt.dll
import fread msvcrt.dll

    ; exit is a function that ends the calling process. It is defined in msvcrt.dll 
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    len equ 100
    s db 'mere pere sajasjk asasa dassdas', 0
    
    nume1 times len db 0
    nume2 db 'output.txt', 0
    
    format_string db '%s', 0
    format_numar db '%d', 0
    format_caracter db '%c', 0
    
    spatiu_nou db ' ', 0
    
    n dd 0
    b dd 0
    k dd 0
    
    rea dd 'r', 0
    wri dd 'w', 0
    
    desc1 dd -1
    desc2 dd -1
    
    
    
; our code starts here
segment code use32 class=code
    start:
        
        push dword nume1
        push dword format_string
        call [scanf]
        add esp, 4*2
        
        push dword n
        push dword format_numar
        call [scanf]
        add esp, 4*2
        
        push dword rea
        push dword nume1
        call [fopen]
        add esp, 4*2
        
        mov [desc1], eax
        
        cmp eax, 0
        je final
        
        push dword wri
        push dword nume2
        call [fopen]
        add esp, 4*2
        
        mov [desc2], eax
        
        cmp eax, 0
        je final
        
        push dword [desc1]
        push dword len
        push dword 1
        push dword s        
        call [fread]
        add esp, 4*4
        
        mov ecx, eax; salvam in ecx numarul de caractere din fisier
        cmp ecx, 0
        je TreciLaFinal
        jne MaiDeparte
        
        TreciLaFinal:
            jmp final
        
        MaiDeparte:
        
        mov esi, s
        mov edi, 0
        cld 
        
        mov dword[b], 0
        
        Repeta:
            
            mov dword[k], ecx
            
            mov eax, 0
            lodsb
            
            cmp eax, 10
            je Verificam
            
            cmp eax, ' '
            je Verificam
            
            Incrementam:
                inc dword[b]
                stosb 
            
            jmp Gata
            
            Verificam:
                mov ecx, dword[n]
                mov ebx, dword[b]
                
                cmp ecx, ebx
                jne TrecemPeste
                je PunemInFisier
                
                PunemInFisier:
                    
                    push dword edi
                    push dword format_string
                    push dword [desc2]
                    call [fprintf]
                    add esp, 4*3
                    
                    push dword spatiu_nou
                    push dword format_caracter
                    push dword [desc2]
                    call [fprintf]
                    add esp, 4*3
                    
                TrecemPeste:
                    
                    mov edi, 0
                    mov dword[b], 0
                
            Gata:
            
            mov ecx, dword[k]
            
        loop Repeta
        
        push dword [desc1]
        call [fclose]
        add esp, 4
        
        push dword [desc2]
        call [fclose]
        add esp, 4
        
        final:
        
        ; exit(0)
        push dword 0      ; push the parameter for exit onto the stack
        call [exit]       ; call exit to terminate the program
