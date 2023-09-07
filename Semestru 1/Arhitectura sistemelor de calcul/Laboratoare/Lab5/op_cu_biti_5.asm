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
    
    A db 10110010b
    B db 00101101b
    C dd 0

; our code starts here
segment code use32 class=code
    start:
        
        mov eax,0
        mov al,[B] ;al=10100101b
        mov cl,1
        shl al,cl ;al=01001010b
        mov cl,4
        shr al,cl ;al=00000100b
        mov ah, 00000011b
        mov bl,[A] ;bl=11100111b
        mov cl,3
        shl bl,cl ;bl=00111000b
        or bl,ah ;bl=00111001b
        mov ah,bl ;ah=00111001b
        mov bx,ax ;bx=0000000000111001b
        mov eax,0FFFFFFFFh
        mov ax,bx ;ax=bx=0000000000111001b
        mov [C],eax ;C=11111111111111110000000000111001b=FFFFFFFF3B04h

        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
