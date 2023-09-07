bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fprintf, printf, scanf, fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
import fclose msvcrt.dll

    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    sir resb 120
    nr_cifre dd 0
    format_s db "%s",10,0
    mode db "w",0
    nume db "prega4.txt",0
    descriptor dd -1
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;deschid fisierul
        push dword mode
        push dword nume
        call [fopen]
        add esp, 8
        mov [descriptor], eax; salvez descriptorul
        citire:
            ; citestc cate un sir
            push dword sir
            push dword format_s
            call [scanf]
            add esp, 8

            ;verific daca nu este egal cu $            
            cmp byte[sir], '$'
            je final 
            mov esi, 0; esi este un contor de positie din sir
            mov dword[nr_cifre],0
            parcurge:
                mov al, [sir+esi]; incarc in al, elementul de 0e posizita sir[esi]
                cmp al, 0; 
                je gata_sir
                cmp al, '0'
                jl continua
                cmp al, '9' ;verific daca al este cifra
                jg continua
                inc dword[nr_cifre]; daca da cresc un contor de aaparitii
                continua:
                    inc esi; trec la urmatorul element
                    jmp parcurge
            gata_sir:
                cmp dword[nr_cifre],1; compar nr_cifre cu 1, daca e mai mare, voi afisa sirul in fisier
                jl mai_departe
                pushad
                push dword sir
                push dword format_s
                push dword [descriptor]
                call [fprintf]
                add esp, 12
                popad
            mai_departe:
                jmp citire
                
            
        final :
        ;inchid fisierul
        push dword [descriptor]
        call [fclose]
        add esp, 4
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
