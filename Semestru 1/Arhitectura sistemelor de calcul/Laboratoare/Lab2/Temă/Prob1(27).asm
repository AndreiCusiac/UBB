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
    
    a db 50
    b dw -45
    c dd 20
    d dq 40
    aux1 dw 0
    aux2 dw 0

; our code starts here
segment code use32 class=code
    start:
        ; (d+d-c)-(c+c-a)+(c+a)
        
        mov al, [a]
        cbw ; al -> ax
        cwd ; ax -> dx:ax
        
        ; c+a
        
        add ax, word [c+0]
        adc dx, word [c+2]
        
        mov bx, ax
        mov cx, dx ; cx:bx = c+a
        
        ; c+c-a
        
        mov al, [a]
        neg al
        
        cbw ; al -> ax
        cwd ; ax -> dx:ax
        
        add ax, word [c+0]
        adc dx, word [c+2]
        
        add ax, word [c+0]
        adc dx, word [c+2] ; dx:ax = c+c-a
        
        ; -(c+c-a)+(c+a)
        
        sub bx, ax
        sbb cx, dx ; cx:bx = -(c+c-a)+(c+a)
        
        mov [aux1], bx
        mov [aux2], cx
        
        ; (d+d-c)
        
        mov eax, [c]
        neg c
        
        cdq ; edx:eax = c
        
        add eax, dword [d+0]
        add edx, dword [d+4]
        
        add eax, dword [d+0]
        add edx, dword [d+4] ; edx:eax = d+d-c
        
        mov ebx, eax
        mov ecx, edx ; ecx:ebx = d+d-c
        
        mov ax, [aux1]
        cwde
        
        add ebx, eax
        
        mov ax, [aux2]
        cwde
        
        adc ecx, eax ; ecx:ebx = (d+d-c)-(c+c-a)+(c+a)

        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
