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
    
    b db 3
    c db 7
    e dw 29
    f dw 13
    g dw 6
    auxx dw 0

; our code starts here
segment code use32 class=code
    start:
           ; [(e+f-g)+(b+c)*3]/5
           
           ; (e+f-g)
           
           mov ax, [e]
           mov bx, [f]
           add ax, bx
           mov bx, [g]
           sub ax, bx
           
           ; pregatesc inmultirea
           
           mov [auxx], ax ; auxx = e+f-g
           
           ; (b+c)*3
           
           mov al, [b]
           mov bl, [c]
           add al, bl
           mov bl, 3
           mul bl ; ax = (b+c)*3
           
           ; [(e+f-g)+(b+c)*3]
           
           add ax, [auxx]
           
           ; [(e+f-g)+(b+c)*3]/5
           
           mov dx, 0
           mov bl, 5
           mov bh, 0
           div bx
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
