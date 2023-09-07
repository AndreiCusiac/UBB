bits 32

; declararea procedurii definite in limbajul C
extern _citireSirC

segment data use32 class=data
    ; ...
    adresaSirCitit dd 0

segment code use32 class=code

    
    _asmConcat:
        ; Codul de intrare
        ; ...

        ; Codul de apel
        ; -----------------------------------------------------------------
        ; - salvarea resurselor volatile aflate în uz
        ; push ecx, push eax, pushad, ...
        
        ; - respectarea constrângerilor stabilite
        ; aici nu e cazul
        
        ; - pregătirea argumentelor transmise procedurii (punerea pe stivă)
        push dword adresaSirCitit
        
        ; - apelul procedurii
        call _citireSirC
        
        ; Codul de iesire
        ; ...
