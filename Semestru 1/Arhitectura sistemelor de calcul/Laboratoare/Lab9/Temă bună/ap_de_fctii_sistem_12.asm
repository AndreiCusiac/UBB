bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    a dd 0
    
    fdec db '%d', 0
    
    mesaj db 'a=', 0
    
    fwrite db 'a = %d (baza 10), a = %x (baza 16)', 0
    
; our code starts here
segment code use32 class=code
    start:
        
        ; Se da un numar natural negativ a (a: dword). Sa se afiseze valoarea lui in baza 10 si in baza 16, 
        ; in urmatorul format: "a = <base_10> (baza 10), a = <base_16> (baza 16)"
        
        push dword mesaj
        call [printf]
        add esp, 4*1
        
        ; Se afiseaza: "a="
        
        push dword a
        push dword fdec
        call [scanf]
        add esp, 4*2
        
        ; Citeste a (decimal)
        
        push dword [a]
        push dword [a]
        push dword fwrite
        call [printf]
        add esp, 4*3
        
        ; Afiseaza: "a = baza 10, a = baza 16"
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
