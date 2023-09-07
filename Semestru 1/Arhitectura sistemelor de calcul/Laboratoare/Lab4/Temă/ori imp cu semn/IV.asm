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
    aux1 dd 0
    rez dd 0
    
; our code starts here
segment code use32 class=code
    start:
        ; (100+a+b*c)/(a-100)+e+x/a
        
        ; b*c
        
        mov al, [b]
        cbw 
        imul word[c] ; dx:ax = b*c
        
        mov bx, ax
        mov cx, dx ; cx:bx = b*c
        
        ; 100+a
        
        mov al, [a]
        cbw
        add ax, 100 ; ax = 100+a
        cwd ; dx:ax = 100+a
        
        ; 100+a+b*c
        
        clc
        
        ;???
        ;add ax, bx
        ;adc dx, cx
        ;mov bx, [a] nu e ok ; mov de asta, trebuie sa fie egal cu asta
        ;sub bx, 100
        ;idiv bx
        ;???
        
        add bx, ax
        adc cx, dx ; cx:bx = 100+a+b*c
        
        ; a-100
        
        mov al, [a]
        cbw 
        sub ax, 100 ; ax = a-100
        
        mov [auxx], ax ; auxx = a-100
        
        ; (100+a+b*c)/(a-100)
        
        mov ax, bx
        mov dx, cx ; dx:ax = 100+a+b*c
        
        ;???
        ;idiv word[auxx] ; ax = catul (100+a+b*c)/(a-100)
        ;???
        
        mov bx, [auxx]
        idiv bx ; ax = catul lui (100+a+b*c)/(a-100)
        
        cwd ; dx:ax = (100+a+b*c)/(a-100)
        
        ; (100+a+b*c)/(a-100)+e
        
        clc
        
        add ax, word[e+0]
        adc dx, word[e+2] ; dx:ax = (100+a+b*c)/(a-100)+e
        
        push dx
        push ax
        pop ebx 
        mov [aux1], ebx ; aux1 = (100+a+b*c)/(a-100)+e
        
        ; x/a
        
        mov al, [a]
        cbw
        cwde ; eax = a
        
        mov ebx, eax ; ebx = a
        
        mov eax, dword[x+0]
        mov edx, dword[x+4] ; edx:eax = x
        
        ;???
        ;idiv dword[a] tot nu e ok, trebuie să fie de același tip partea asta [cu partea asta]
        ;mov ebx, dword[a]
        ;???
        
        idiv ebx ; eax = catul lui x/a
        
        ; (100+a+b*c)/(a-100)+e+x/a
        
        mov ebx, [aux1]
        
        add eax, ebx ; eax = (100+a+b*c)/(a-100)+e+x/a
        
        ;mov [rez], eax 

        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
