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

    a dd 40
b db 3
c dw 5
d db 9
auxx dw 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
    ; (2-b)
;Mov bl, 2
;Sub bl, [b] ; bl=b-2
Neg byte[b]
Add byte[b], 2 ; -b+2

Mov bl, [b]
Mov bh, 0 ; bx =[b]

; a in dx:ax pt impartire
Mov ax, word [a+0]
Mov dx, word[a+2] ; dx:ax = [a]
Div bx ;       dx:ax la bx, in ax cat (si in dx rest)  ax= cat de la a / (2- b)
; salvare pe ax in dx

Mov dx, ax
;(c+1)
Inc word [c] ; c=c+1
Mov bx, [c] ; bx= (c+1)

; (d-1)
Dec byte[d] ; d=d-1
Mov cl, [d] ; cl =d-1
Mov ch, 0 ; cx = d-1
Mov ax, bx

Mul cx ; cx* ax = dx:ax = a / (2- b) + (c+1)*(d-1)

Mov bx, [auxx]
Mov cx, 0 ; cx:bx =[auxx] extins
; adunare cx:bx+
                 ; Dx:ax
Add ax, bx
Adc  dx, cx ; dx+cx+transportul de la op anterioara CF =se salveaza val 1 cand exista transport
; rez final in dx:ax


        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
