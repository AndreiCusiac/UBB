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
    
    s db 5, 25, 55, 127
    
    l equ $-s
    k db 0 ; contor
    
    d times l db 0
    
; our code starts here
segment code use32 class=code
    start:
        
        ; Se da un sir de octeti s. Sa se construiasca sirul de octeti d, care contine pe fiecare pozitie numarul de biti 1 ai octetului de pe pozitia corespunzatoare din s.
        ; s: 5, 25, 55, 127
        ; 101, 11001,10111,1111111
        ; d: 2, 3, 5, 7
        
        mov esi, 0
        mov edi, 0
        mov ecx, l
        
        jecxz final
        
        ParcurgemS:
            mov byte[k], 0 ; contor, numara cifrele de 1 din reprezentarea binara pentru octetul din s curent 
            mov al, byte[s+esi]
            
            cmp al, 0
            jne Impartim ; daca catul nu este 0, continuam impartirea la 2 
            je Culegem ; daca catul este 0, culegem bl 
            
            Impartim:
                cbw ; ax <-- al
                   
                mov bl, 2
                idiv bl ; al - catul, ah - restul
                
                cmp ah, 1 
                je Adunam
                jne NuAdunam
                
                Adunam:
                    inc byte[k] ; daca restul este 1, incrementam contorul
                
                NuAdunam:
                    cmp al, 0 ; verificam daca reluam sau nu procesul de impartire
                    jne Impartim
                    je Culegem
                
            Culegem:
                mov bl, byte[k]
                mov [d+edi], bl ; ii asignam pozitiei actuale din d numarul cifrelor de 1 din reprezentarea binara a octetului corespunzator din s
                inc edi 
                inc esi ; incrementam pozitia curenta din ambele siruri
        
        loop ParcurgemS
        
        final:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
