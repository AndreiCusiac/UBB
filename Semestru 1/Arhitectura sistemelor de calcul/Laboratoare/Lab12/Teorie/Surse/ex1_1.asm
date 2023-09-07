bits 32   

; declararea procedurii definite in limbajul C
extern _citireSirC

segment data use32 class=data
    ; ...

segment code use32 class=code

    ; definire procedura
    ; numele procedurii va fi prefixat cu caracterul _ (underscore)
    _asmConcat:
        ; Codul de intrare
        ; -------------------------------------------
        ; - crearea unui cadru de stivă (stack frame)
        push ebp
        mov ebp, esp
        
        ; - rezervarea pe stivă a spațiului necesar stocării variabilelor locale
        sub esp, 4*3    ; rezervam 12 octeti pentru a stoca sirul citit
        
        ; - salvarea pe stivă a unei copii a resurselor nevolatile modificate
        ; push ecx, pushad, ....
        
        ; Codul de apel
        ; ...
        
        ; Codul de iesire
        ; ...
        
   
