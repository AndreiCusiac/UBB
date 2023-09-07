bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    a db 0
    
    b dw 0
    
    fdec1 db '%d', 0
    fdec2 db '%d', 0
    
    mesaj1 db 'a=', 0
    mesaj2 db 'b=', 0
    
    conf1 db 'DA.', 0
    conf2 db 'NU.', 0
    
; our code starts here
segment code use32 class=code
    start:
        
        ; Sa se citeasca de la tastatura un octet si un cuvant. Sa se afiseze pe ecran daca bitii octetului citit se regasesc consecutiv printre bitii cuvantului. Exemplu:
        ; a = 10 = 0000 1010b
        ; b = 256 = 0000 0001 0000 0000b
        ; Pe ecran se va afisa NU.
        ; a = 0Ah = 0000 1010b = 10
        ; b = 6151h = 0110 0001 0101 0001b = 24913
        ; Pe ecran se va afisa DA (bitii se regasesc pe pozitiile 5-12).
        
        ; a
        
        ; Se afiseaza: "a="
        
        push dword mesaj1
        call [printf]
        add esp, 4*1
        
        ; Citeste a (decimal)
        
        push dword a
        push dword fdec1
        call [scanf]
        add esp, 4*2
        
        ; b
        
        ; Se afiseaza: "b="
        
        push dword mesaj2
        call [printf]
        add esp, 4*1
        
        ; Citeste b (decimal)
        
        push dword b
        push dword fdec2
        call [scanf]
        add esp, 4*2
        
        mov al, [a]
        cbw ; ax = a
        
        cmp ax, [b] ; verificam daca a si b coincid 
        je AfisamDa ; daca da, afisam Da
        jne Cautam ; daca nu, incepem sa cautam bitii lui a in cadrul bitilor lui b
        
        Cautam:
            
            mov ecx, 8
            mov al, [a] ; al = a
            mov bx, [b] ; bx = b
            
            ParcurgemB:
                
                cmp bl, al ; verificam daca octetul inferior din b coincide cu a 
                je AfisamDa
                
                shr bx, 1 ; mutam bitii lui b cu o pozitie spre stanga 
            
            loop ParcurgemB
        
        AfisamNu:
            
            push dword conf2
            call [printf]
            add esp, 4*1
            
            jmp final
        
        AfisamDa:
            
            push dword conf1
            call [printf]
            add esp, 4*1
        
        final:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
