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
    
    A db 10
    B dw 20
    C dd 10b


; our code starts here
segment code use32 class=code
    start:
        ; ...
        
         ; (a+b)

        Mov al, [A]
        MOV AH, 0 ; AX -< [A]

        ADD AX,  [B] ; AX=AX+B

        ; (11b – c)

        ;SUB DEST, SOURCE ; DEST=DEST-SOURCE

        MOV EBX, 11B
        ;MOV ECX, [C]
        ;SUB EBX, ECX

        SUB EBX, [C] ; EBX= (11b – c)

        MOV ECX, 0
        MOV CX, AX ; ECX = (a+b)

        SUB ECX, EBX ; ECX = ECX-EBX = (a+b) – (11b – c)

    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
