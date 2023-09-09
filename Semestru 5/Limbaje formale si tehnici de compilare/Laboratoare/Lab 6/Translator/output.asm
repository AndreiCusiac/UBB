bits 32
global start
extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data

	pdesc db '%d', 0
	sdesc db '%d', 0
	nline db '%c', ' '
	a dd 0
	c dd 0

segment code use32 class=code
	start:
		push dword a
		push dword sdesc
		call [scanf]
		add esp, 4*2

		mov eax, [a]
		add eax, 3
		mov dword [a], eax

		mov eax, [a]
		sub eax, 8
		mov dword [a], eax

		mov eax, [a]
		push dword eax
		push dword pdesc
		call [printf]
		add esp, 4*2

		mov eax, [a]
		mov dword [c], eax

		mov eax, [c]
		mov ebx, 4
		mul ebx
		mov dword [c], eax

		mov ax, word[c + 0]
		mov dx, word[c + 2]
		mov bx, 2
		div bx
		mov word[c + 0], ax

		mov word[c + 2], 0

		mov eax, [c]
		push dword eax
		push dword pdesc
		call [printf]
		add esp, 4*2


		push dword 0
		call [exit]