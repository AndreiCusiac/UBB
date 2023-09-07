bits 32
global start        

; declararea procedurii definite într-o librărie cu legare dinamică
extern printf
import printf msvcrt.dll

segment data use32 class=data
    sir db "abcdefghijkl", 0
    format db "Sir: %s", 0

segment code use32 class=code
        ; ...
        
        ; Codul de apel
        ; Convenția de apel: CDECL
        ; -----------------------------------------------------------------
        ; - salvarea resurselor volatile aflate în uz
        push ecx
        ; ...
        ; - pregătirea argumentelor transmise procedurii (punerea pe stivă)
        push dword sir
        push dword format
        ; - apelul procedurii
        call [printf]
        ; - eliberarea parametrilor puși pe stivă
        add esp, 4*2
        
        ; ...