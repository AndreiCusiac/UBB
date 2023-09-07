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
    auxx dd 0
    rez dd 0
    
; our code starts here
segment code use32 class=code
    start:
        ; (100+a+b*c)/(a-100)+e+x/a
        
        ; b*c
        
        mov al, [b]
        mov ah, 0
        mul word[c] ; dx:ax = b*c
        
        mov bx, ax
        mov cx, dx ; cx:bx = b*c
        
        ; 100+a
        
        mov al, [a]
        add al, 100
        
        mov ah, 0
        mov dx, 0 ; dx:ax = 100+a
        
        ; 100+a+b*c
        
        clc
        
        add ax, bx
        adc dx, cx ; dx:ax = 100+a+b*c
        
        ; a-100
        
        mov bl, [a]
        sub bl, 100
        
        mov bh, 0 ; bx = a-100
        
        ; (100+a+b*c)/(a-100)
        
        div bx ; ax = catul lui (100+a+b*c)/(a-100)
        
        ; (100+a+b*c)/(a-100)+e
        
        mov dx, 0 ; dx:ax = catul lui (100+a+b*c)/(a-100)
        
        clc 
        
        add ax, word[e+0]
        adc dx, word[e+2] ; dx:ax = (100+a+b*c)/(a-100)+e
        
        push dx
        push ax
        pop ebx
        mov [auxx], ebx ; auxx = (100+a+b*c)/(a-100)+e
        
        ; x/a
        
        mov eax, dword[x+0]
        mov edx, dword[x+4] ; edx:eax = x 
        
        mov ebx, 0  
        mov bl, [a]
        
        div ebx ; eax = catul x/a 
        
        ;???
        ;div dword[a]
        ;???
        
        ; (100+a+b*c)/(a-100)+e+x/a
        
        add eax, [auxx]
        
        ;mov [rez], eax
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
