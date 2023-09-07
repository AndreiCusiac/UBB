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
        ; ...

        ; Codul de apel
        ; ...
        call _citireSirC
        
        ; Codul de iesire
        ; ...  
        ret
