bits 32 

global start        

extern exit
import exit msvcrt.dll
segment data use32 class=data

S dq 1122334455667788h, 8877665544332211h
lungimeS equ ($-S)/8
k equ 3
sumaActuala db 0
R resb lungimeS*2

segment code use32 class=code
start:
mov ecx, lungimeS
jecxz final

mov edi, R
mov esi, S

repeta: ;repeta pentru fiecare element (quadword) din sirul S
mov byte [sumaActuala], 0
add esi, 2 ;mergem la byte-ul dorit (esi + 2) (tinand cont de Little Endian)
mov al, [esi] ;copiem byte-ul dorit in al

mov bl, 10
catTimp: ;cat timp al diferit de 0 (aici calculam suma cifrelor care se salveaza in sumaActuala)
mov ah, 0; ax = al
div bl
;al = cat, ah = rest
add byte [sumaActuala], ah
cmp al, 0
jnz catTimp

cmp byte [sumaActuala], k ;comparam suma actuala la k
jne notEqual
mov al, [esi] ;daca este egal il inmultim cu k si copiem cei 2 octeti rezultat ai inmultirii in sirul rezultat:
mov bl, k
mul bl
mov [edi], ax
add edi, 2
notEqual: ;daca nu este egal:
add esi, 6 ;trecem la urmatorul quadword din sir (esi + 6) (tinand cont de Little Endian)
loop repeta

final:
    push    dword 0
    call    [exit]