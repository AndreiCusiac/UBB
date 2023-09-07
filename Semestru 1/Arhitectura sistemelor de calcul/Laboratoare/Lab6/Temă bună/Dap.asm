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
    
    r dd 11000101000110110000010001110001b
    t dd 00101101011101011010111111010011b
    q dd 0

; our code starts here
segment code use32 class=code
    start:
        
        mov ebx, [t] ; ebx = t31 t30 .... t10 t9 t8  t7 t6 t5 t4  t3 t2 t1 t0
        ror ebx, 10 ; ebx = t9 t8 t7 t6  t5 t4 t3 t2  t1 t0 t31 t30 ... t16 t15 t14  t13 t12 t11 t10 
        
        mov eax, ebx ; eax = t9 t8 t7 t6  t5 t4 t3 t2  t1 t0 t31 t30 ... t16 t15 t14  t13 t12 t11 t10 
        and eax, 00000000000000000000000001111111b ; eax = 0000 0000 0000 0000 0000 0000 0 t16 t15 t14  t13 t12 t11 t10
        
        mov  ebx, [r]
        mov ecx, [t]
        xor ebx, ecx ; ebx = xor intre r si t 
        
        and ebx, 00000001111111111111111110000000b; ebx = 0000 000 tr24  tr23 tr22 tr21 tr20 ...  tr11 tr10 tr9 tr8  tr7 000 0000b
        
        or eax, ebx ; eax = 0000 000 tr24  tr23 tr22 tr21 tr20 ...  tr11 tr10 tr9 tr8  tr7 t16 t15 t14  t13 t12 t11 t10
        
        mov ebx, [r] ; ebx = r31 r30 ... r11 r10 r9 r8  r7 r6 r5 r4  r3 r2 r1 r0
        rol ebx, 20 ; ebx = r11 r10 r9 r8  r7 r6 r5 r4  .... r15 r14 r13 r12
        
        and ebx, 11111110000000000000000000000000b; ebx = r11 r10 r9 r8  r7 r6 r5 0000 0000 0000 0000 0000 0000
        
        or eax, ebx ; eax = r11 r10 r9 r8  r7 r6 r5 tr24  tr23 tr22 tr21 tr20 ...  tr11 tr10 tr9 tr8  tr7 t16 t15 t14  t13 t12 t11 t10
        
        mov dword[q], eax 
        
        

        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
