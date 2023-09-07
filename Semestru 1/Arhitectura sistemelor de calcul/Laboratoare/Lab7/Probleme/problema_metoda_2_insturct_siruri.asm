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
    a dq 1122334455667788h, 1111222233334444h, 3333333333333333h; 66, 33,33
    l_a equ ($-a)/8
    r times l_a dw 0 ; 132,132
    k equ 6
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov esi,a
        mov edi,r
        mov ecx,l_a
        jecxz final
        cld
        repeta:
            lodsd; eax=a[esi], esi+=4
            and eax,00FF0000h; 00 00 0f f0
            shr eax,16
            mov dl,al
            shl eax,4
            mov bh,ah
            and eax,000000F0h
            shr eax,4; 00 00 00 0f
            add al,bh; 00 00 00 ceva+ceva
            cmp al,k
            je pune_in_sir
            jmp continua
            pune_in_sir:
                mov bl,k
                mov al,dl
                mul bl; ax-rez
                stosw;r[edi]=ax, edi+=2
            continua:
                add esi,4; pt a trece la urm element
                loop repeta
            
        final:
        ;mda, inseamna ca nu mere
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
