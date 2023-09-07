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
        
        mov al, [b]
        cbw 
        imul word[c] ; dx:ax = b*c
        
        mov bx, ax
        mov cx, dx ; cx:bx = b*c
        
        mov al, [a]
        add al, 100
        cbw
        cwd ; dx:ax = a+100
        
        add bx, ax
        adc cx, dx ; cx:bx = 100+a+b*c
        
        mov al, [a]
        sub al, 100
        
        cbw ; al -> ax
        
        mov [auxx], ax ; auxx = a-100
        
        mov ax, bx
        mov dx, cx ; dx:ax = 100+a+b*c
        
        idiv word[auxx] ; ax = catul (100+a+b*c)/(a-100)
        
        cwd ; dx:ax = (100+a+b*c)/(a-100)
        
        add ax, word[e+0]
        adc dx, word[e+2] ; dx:ax = (100+a+b*c)/(a-100)+e
        
        mov bx, ax
        mov cx, dx ; cx:bx = (100+a+b*c)/(a-100)+e
        
        mov al, [a]
        cbw
        cwde
        mov [auxx1], eax ; auxx1 = a
        
        mov eax, dword[x+0]
        mov edx, dword[x+4]
        
        idiv dword[auxx1] ; eax = catul (x/a)
        
        push cx
        push bx
        pop ebx ; ebx = (100+a+b*c)/(a-100)+e
        
        add eax, ebx

        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
