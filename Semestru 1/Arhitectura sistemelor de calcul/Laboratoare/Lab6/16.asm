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
    
    s1 db 'a', 'd', 'c', 'd', 'e', 'f'
    l1 equ $-s1
    
    s2 db '1', '2', '3', '4', '5'
    l2 equ $-s2
    
    d times (l1+l2) db 0 ; lungimea maxima pentru d, pentru ca nu cred ca pot folosi partea intreaga aici :-) 
    
    ; alternativ, as putea declara d in code segment, dar nu stiu daca e posibil
    
; our code starts here
segment code use32 class=code
    start:
        
        ;Se dau doua siruri de caractere S1 si S2. Sa se construiasca sirul D prin concatenarea elementelor de pe pozitiile impare din S2 cu elementele de pe pozitiile pare din S1
        ; S1: 'a', 'b', 'c', 'b', 'e', 'f'
        ; S2: '1', '2', '3', '4', '5'
        ; D: '1', '3', '5', 'b', 'b', 'f'
        
        mov esi, 0
        mov edi, 0
        mov ecx, l2 ; pregatim lucrul cu sirul s2
        mov bl, 1 ; un soi de contor, folosit pentru a determina paritatea pozitiei - il setam cu 1, deci il interpretam drept pozitie impara
        
        jecxz sf1 ; trecem direct la celalalt sir, daca sirul actual e vid
        
        Rep1:
            cmp bl, 1 ; verificam daca ne aflam pe o pozitie impara in s2 
            je poz_imp_da 
            jne poz_imp_nu
            
            poz_imp_da:
                mov al, [s2+esi] ; daca da, mutam termenul curent din s2 in al
                mov [d+edi], al ; apoi, din al il punem in pozitia curenta din d 
                
                inc edi ; incrementam pozitia curenta (din d) 
                mov bl, 0 ; am avut numar pe pozitie impara, deci urmeaza unul pe pozitie para
                
                jmp ex1 
            
            poz_imp_nu:
                mov bl, 1 ; am avut numar pe pozitie para, deci urmeaza unul pe pozitie impara
            
            ex1:
                inc esi ; incrementam oricum pozitia curenta (din s2)
            
        loop Rep1 ; pana cand epuizam elementele din s2
        
        sf1:
        
        mov esi, 0
        mov ecx, l1 ; pregatim lucrul cu sirul s1
        mov bl, 1 ; acelasi contor - il setam cu 1, deci il interpretam drept pozitie impara
        
        jecxz final ; trecem la final, daca sirul actual e vid
        
        Rep2:
            cmp bl, 0 ; verificam daca ne aflam pe o pozitie para in s1
            je poz_para_da 
            jne poz_para_nu
            
            poz_para_da:
                mov al, [s1+esi] ; daca da, mutam termenul curent din s1 in al
                mov [d+edi], al ; apoi, din al il punem in pozitia curenta din d 
                
                inc edi ; incrementam pozitia curenta (din d) 
                mov bl, 1 ; am avut numar pe pozitie para, deci urmeaza unul pe pozitie impara
                
                jmp ex2
            
            poz_para_nu:
                mov bl, 0 ; am avut numar pe pozitie impara, deci urmeaza unul pe pozitie para
            
            ex2:
                inc esi ; incrementam oricum pozitia curenta (din s1)
            
        loop Rep2 ; pana cand epuizam elementele din s1
        
        
        final: ; gata:)
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
