bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fscanf, fprintf, fopen, fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import fscanf msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll

    ; exit is a function that ends the calling process. It is defined in msvcrt.dll 
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    
    s db '1', '2', 'a', 'b', 0
    
; our code starts here
segment code use32 class=code
    start:
        
        ; Se da un numar natural negativ a (a: dword). Sa se afiseze valoarea lui in baza 10 si in baza 16, 
        ; in urmatorul format: "a = <base_10> (baza 10), a = <base_16> (baza 16)"
        
        mov eax, [s]
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
