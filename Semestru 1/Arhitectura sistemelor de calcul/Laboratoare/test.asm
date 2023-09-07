; Codul de mai jos va deschide un fisier numit "ana.txt" din directorul curent si va citi un text de maxim 100 de caractere din acel fisier.
; Programul va folosi functia fopen pentru deschiderea fisierului, functia fread pentru citirea din fisier si functia fclose pentru inchiderea fisierului deschis.
; Deoarece in apelul functiei fopen programul foloseste modul de acces "r", daca un fisier cu numele dat nu exista in directorul curent, apelul functiei fopen nu va reusi (eroare). Detalii despre modurile de acces sunt prezentate in sectiunea "Suport teoretic".

bits 32 

global start        

; declare external functions needed by our program
extern exit, fopen, fread, fclose, printf
import exit msvcrt.dll  
import fopen msvcrt.dll  
import fread msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    
    ; j db 3
    
    ; k equ $-j
    
    ; a db 42
    
    ; var1 dw 7
    
    ; b dw 0A2B3h
    
    ; d resw 2
    
    ; ef times 2*2 dw 1234h
    
    ; e dd 'abcde', '123', 0

    ; f db 5
    
    ; g equ $-a
    
    ; var dd 17, 18, 19, 021A3h, -3
    
    TabHexa db '0123456789ABCDEF'
    
    sir db '123'
    l equ $-sir
    d times l db 0
    
; our code starts here
segment code use32 class=code
    start:
        
      mov ebx, TabHexa
      
      mov al, 6
      
      xlat
      
      
      
      cld 
      mov esi, sir
      mov edi, d
      mov ecx, l
      
      Repeta:
        
        mov eax, 0
        
        lodsb
        
        sub eax, 30h
        
        stosb 
      
      loop Repeta
      
      
      ; shl ax, 8
      
      ; mov eax, 10
      
      ; lea eax, [eax*2]
      
      ; lea eax, [eax*4 + eax]
      
      ; stc 
      ; pushf
      ; pop eax
      ; mov ebx, 0
      ; mov ecx, 9
      
      ; stc 
      ; mov al, 10110011b
      ; rcr al, 2
      
      ; xlat
      
      ; std 
      
      ; pushf
      ; popf 
      
      ; pusha
      
      ; popa
      
      ; mov eax, 10010011b
      ; mov ebx, g
      ; mov ecx, k
      
      ; mov eax, 2
      
      ; mov [var], eax
      
      ; mov eax, dword[j]
      
      ; push dword eax
      ; push word [a]
      
      ; mov dword[b], 12345h
      
      ; push dword 12345h
      
      ; mov eax, 1234h
      
      ; mov dword[j], a
      
      
      ; mov dword[j], 1234567h
      
      
        
      final:
        
        ; exit(0)
        push    dword 0      
        call    [exit]       