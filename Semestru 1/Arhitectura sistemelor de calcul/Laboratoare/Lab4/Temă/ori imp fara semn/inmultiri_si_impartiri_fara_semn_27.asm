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
    
    a db 102
    b db 3
    c dw 4
    e dd 5
    x dq 204
    auxx dw 0
    auxx1 dd 0
    
; our code starts here
segment code use32 class=code
    start:
        ; (100+a+b*c)/(a-100)+e+x/a
        
        mov ax, [c]
        mov bl, [b]
        mov bh, 0
        
        mul bx ; dx:ax = b*c
        
        mov bx, ax
        mov cx, dx ; cx:bx = b*c
        
        mov ax, 0
        mov al, [a]
        
        add ax, 100
        
        cwd ; dx:ax = 100+a
        
        add ax, bx
        adc dx, cx ; dx:ax = 100+a+b*c
        
        mov bx, 0
        mov bl, [a]
        sub bx, 100
        
        div bx ; ax = catul (100+a+b*c)/(a-100)
        
        mov ax, bx
        
        cwd ; dx:ax = (100+a+b*c)/(a-100)
        
        add ax, word[e+0]
        adc dx, word[e+2] ; dx:ax = (100+a+b*c)/(a-100)+e
        
        mov bx, ax
        mov cx, dx ; cx:bx = (100+a+b*c)/(a-100)+e
        
        mov eax, 0
        mov al, [a]
        mov [auxx1], eax ; auxx1 = a
        
        mov eax, dword[x+0]
        mov edx, dword[x+4]
        
        div dword[auxx1] ; eax = catul (x/a)
        
        push cx
        push bx
        pop ebx
        
        add eax, ebx
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
