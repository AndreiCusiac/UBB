bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    a db 10110010b
    b db 00101101b
    c dd 0

; our code starts here
segment code use32 class=code
    start:
        
        mov bl, [b] ; bl = 1010 1010b
        
        ror bl, 3 ; bl = 0101 0101b
        
        mov al, bl ; al = 0101 0101b
        and al, 00001111b ; al = 0000 0101b = 0000 b6 b5 b4 b3 
        
        mov bl, [a] ; bl = 1100 1100b
        
        rol bl, 3; bl = 0110 0110b
        
        mov ah, bl ; ah = 0110 0110b
        and ah, 11111000b ; ah = 0110 0000b = a4 a3 a2 a1 a0 000 
        or ah, 00000011b ; ah = 0110 0110b = a4 a3 a2 a1 a0 110
        
        mov dx, 1111111111111111b
        
        push dx
        push ax
        pop eax
        
        mov dword[c], eax ; c = 1111 1111 1111 1111 a4 a3 a2 a1 a0 110 0000 b6 b5 b4 b3 

        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
