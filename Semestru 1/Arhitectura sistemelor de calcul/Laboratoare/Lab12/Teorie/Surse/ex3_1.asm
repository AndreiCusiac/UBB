bits 32

segment data use32 class=data
    lenRez dd 0
    ; ...

segment code use32 class=code

    ; definire procedura
    ; numele procedurii va fi prefixat cu caracterul _ (underscore)
    _asmConcat:
        ; Codul de intrare
        ; ...

        ; Codul de apel
        ; ...

        ; Codul de iesire
        ; --------------------------------------------------------------------------
        ; - restaurarea resurselor nevolatile care au fost alterate
        ; pop eax, pop ecx, popad, ...

        ; - eliberarea spațiului rezervat pe stivă pentru variabilele locale
        add esp, 4*3
        
        ; - refacerea cadrului de stivă al programului apelant
        mov esp, ebp
        pop ebp
        
        ; - revenirea din procedură și eliberarea parametrilor transmiși procedurii
        ; Convenția de apel este STDCALL, prin urmare:
        ; 1. Punem rezultatul obținut în registrul EAX
        mov eax, [lenRez]
        ; 2. Revenim din procedură fără să eliberăm parametrii de pe stivă
        ; (e responsabilitatea programului apelant să facă acest lucru)
        ret
