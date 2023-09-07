bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global _determina_minim       

; declare external functions needed by our program


; our data is declared here (the variables needed by our program)
segment data public data use32
    ; ...
    sir dd 0
    len_s dd 0
    min dd 7FFFFFFFh
;int _determina_minim(int sir[],int n)
; our code starts here
segment code public code use32
    _determina_minim:
        ; ...
        ;18. Se citeste de la tastatura un sir de numere in baza 10 fara semn. Sa se determine valoarea minima din sir si sa se afiseze in fisierul min.txt (fisierul va fi creat) valoarea minima, in baza 16 
        push ebp
        mov ebp, esp
        ;pushad
        
        mov eax, [ebp + 8]        ; eax <- offset sir
        mov [sir],eax
    
        mov ebx, [ebp + 12]        ; ebx <- valoarea lui n
        mov [len_s],ebx
        
        mov esi, [sir]
        mov ecx,[len_s]
        repeta:
            lodsd;eax=el din sir, esi+=4
            cmp eax,[min]
            jg mai_departe
            mov [min],eax
            mai_departe:
                loop repeta
        
        
        
        ;popad
        mov esp, ebp
        pop ebp
        mov eax,[min]
        ret
