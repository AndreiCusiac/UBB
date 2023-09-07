bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
; Se da un sir de quadworduri. Sa se identifice octetii inferiori din cuvintele superioare din dublu cuvintele inferioare. Dupa ce s-au identificat octetii sa se verifice daca au suma cifrelor egale cu o constanta k definita in data segment iar in caz afirmativ sa se salveze acesti octeti intr-un sir rezultat dupa ce au fost inmultiti cu valoarea constantei k.
segment data use32 class=data
    ; ...
    sirQ DQ 0FFFFFFFFFF_A0_FFFFh, 0FFFFFFFFFF_28_FFFFh, 0FFFFFFFFFF_37_FFFFh, 0FFFFFFFFFF_FF_FFFFh, 0FFFFFFFFFF_19_FFFFh    ;Sir de numere quadword
    len EQU ($-sirQ)/8                                                                                                      ;Nr. de elemente al sirului SirQ
    k db 0Ah                                                                                                                ;Constanta
    sirR times len DW 0                                                                                                     ;Sir de rezultate
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ecx, len                ;Stabilesc numarul de repetari
        mov esi, 0                  ;Contor pentru deplasare in SirQ
        mov edi, 0                  ;Contor pentru deplasare in SirR
        jecxz Sfarsit               ;Oprire in caz ca ecx = 0
        start_loop:                 
        mov ebx, ecx                ;Salvez valoarea lui ecx pentru a putea folosi acest registru si a nu obtine un loop infinit
        mov edx, [sirQ + esi]       ;Pun elementul din SirQ in edx
        mov cl, 16                  ;Stabilesc cu cati biti voi face shit la dreapta
        SHR edx, cl                 ;Mut cei 2 octeti cautati la finalul lui edx
        AND edx, 000000FFh          ;Elimin ceilalti octeti care nu sunt necesari
        mov eax, [sirQ + esi]       ;Pun elementul din SirQ in eax
        mov cl, 12                  ;Stabilesc cu cati biti voi face shit la dreapta
        SHR eax, cl                 ;Mut cei 2 octeti cautati astfel incat primul sa fie in ultimul nibble a lui ah, iar al 2 lea octet in primul nibble a lui al
        mov cl, 4                   ;;Stabilesc cu cati biti voi face shit la dreapta
        SHR al, cl                  ;Mut al 2 lea octet la finalul lui al
        AND eax, 00000F0Fh          ;Elimin ceilalti octeti care nu sunt necesari
        add al, ah                  ;Calculez suma celor 2 octeti
        cmp al, [k]                 ;Compar suma cu k
        jne skip                    ;Daca suma este egala cu k...
            mov ax, dx              ;Adaug cei 2 octeti (salvati in dx la inceput) in ax
            mul byte [k]            ;Inmultesc octetii cu k
            mov [sirR + edi], ax    ;Adaug rezultatul in sirul de rezultate
            add edi, 2              ;Cresc contorul cu care ma deplasez in SirR
        skip:                       
        add esi, 8                  ;Cresc contorul cu care ma deplasez in SirQ
        mov ecx, ebx                ;Restabilesc valoarea lui ecx pentru a nu avea un loop infinit
        loop start_loop             
        Sfarsit:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
