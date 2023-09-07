
;cod pentru sumafunctie.asm

bits 32                         

segment code use32 public code

global compara  ; eticheta
    

compara:
	
	mov eax, [esp + 4] ;accesam primul param de pe stiva
	
    mov ebx, [esp + 8] ;accesam al doilea param de pe stiva
    
    cmp eax, ebx
    
    ja Mutam
    jna Returnam
    
    Mutam:
        mov ebx, eax
    
    Returnam:    
  
    ret 4*2 ; in acest caz 8 reprezinta 
	;numarul de octeti ce trebuie eliberati de pe stiva 
	;(parametrii pasati procedurii - adica cei 2 pusi pe stiva*4)
	
	