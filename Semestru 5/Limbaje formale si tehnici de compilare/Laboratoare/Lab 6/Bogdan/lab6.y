%{
	#include <stdio.h>
	#include <stdlib.h>
	#include <string.h>

	int yylex(void);
	int yyerror(const char *s);
	int success = 1;

	char varNameAssigment[101];

	char dataSegment[100][256];
	char codeSegment[100][256];

	int dataSegmentLines = 0;
	int codeSegmentLines = 0;

	void addToDataSegment(char *variable) {
		char declare[256];
		strcpy(declare, "\t");
		strcat(declare, variable);
		strcat(declare, " dd 0");

		strcpy(dataSegment[dataSegmentLines], declare);
		dataSegmentLines++;
	}

	void addToCodeSegment(char *line) {
		char indentedLine[256] = "\t";
		strcat(indentedLine, line);

		strcpy(codeSegment[codeSegmentLines], indentedLine);
		codeSegmentLines++;
	}
%}

%union{
	char varName[101];
	int value;
}

%token ID
%token INT_CONST
%token INT
%token CIN
%token COUT
%token SEMI_COLON
%token COMMA
%token PLUS
%token MINUS
%token MULTIPLY
%token DIVISION
%token LEFT_ROUND_PARENTHESIS
%token RIGHT_ROUND_PARENTHESIS
%token LEFT_SQUARE_PARENTHESIS
%token RIGHT_SQUARE_PARENTHESIS
%token ASSIGNMENT
%token LEFT_BRACKET
%token RIGHT_BRACKET
%token DOUBLE_LESS
%token DOUBLE_GREATER
%token RETURN



%start program

%%

program : INT ID LEFT_ROUND_PARENTHESIS RIGHT_ROUND_PARENTHESIS LEFT_BRACKET listaInstr RETURN INT_CONST SEMI_COLON RIGHT_BRACKET;
listaInstr: instr | instr listaInstr ;
instr: instrIO SEMI_COLON | instrAttr SEMI_COLON | declarare SEMI_COLON ;
instrIO: CIN DOUBLE_GREATER ID {char line[256] = "push dword "; strcat(line, yylval.varName); addToCodeSegment(line); addToCodeSegment("push dword format_scanf"); addToCodeSegment("call [scanf]"); addToCodeSegment("add esp, 4*2\n");}
	| COUT DOUBLE_LESS ID {char line[256] = "mov eax, ["; strcat(line, yylval.varName); strcat(line, "]"); addToCodeSegment(line); addToCodeSegment("push dword eax"); addToCodeSegment("push dword format_printf"); addToCodeSegment("call [printf]"); addToCodeSegment("add esp, 4*2\n");};

instrAttr: ID {strcpy(varNameAssigment, yylval.varName);} ASSIGNMENT expresie ;

expresie: expresieAttr | expresieAdd | expresieSubb | expresieMul | expresieDiv ;

expresieAttr: ID { char line[256]; sprintf(line, "mov eax, [%s]", yylval.varName); 
					addToCodeSegment(line); 
					sprintf(line, "mov dword [%s], eax \n", varNameAssigment); 
					addToCodeSegment(line);} 
			| INT_CONST {char line[256]; sprintf(line, "mov dword [%s], %d \n", varNameAssigment, yylval.value); 
					addToCodeSegment(line);};

expresieAdd: operand PLUS operand_adunare;

operand_adunare: ID {char line[256]; sprintf(line, "add eax, [%s]", yylval.varName); 
					addToCodeSegment(line); 
					sprintf(line, "mov dword [%s], eax \n", varNameAssigment); 
					addToCodeSegment(line);} 
				| INT_CONST {char line[256]; sprintf(line, "add eax, %d", yylval.value); 
					addToCodeSegment(line); 
					sprintf(line, "mov dword [%s], eax \n", varNameAssigment); 
					addToCodeSegment(line);};

expresieSubb: operand MINUS operand_scadere;

operand_scadere: ID {char line[256]; sprintf(line, "sub eax, [%s]", yylval.varName);
					addToCodeSegment(line);
					sprintf(line, "mov dword [%s], eax \n", varNameAssigment);
					addToCodeSegment(line);} 
				| INT_CONST {char line[256]; sprintf(line, "sub eax, %d", yylval.value); addToCodeSegment(line); sprintf(line, "mov dword [%s], eax \n", varNameAssigment); addToCodeSegment(line);};

expresieMul: operand MULTIPLY operand_inmultire;

operand_inmultire: ID {char line[256]; sprintf(line, "mul eax, [%s]", yylval.varName);
					addToCodeSegment(line);
					sprintf(line, "mov dword [%s], eax \n", varNameAssigment);
					addToCodeSegment(line);} 
				| INT_CONST {char line[256];	sprintf(line, "mov ebx, %d", yylval.value); addToCodeSegment(line); strcpy(line, "mul ebx"); addToCodeSegment(line); sprintf(line, "mov dword [%s], eax \n", varNameAssigment); addToCodeSegment(line);};

expresieDiv: operand_impartire DIVISION operand_impartire2;

operand_impartire: ID {char line[256]; sprintf(line, "mov dx, [%s + 2]", yylval.varName);
						addToCodeSegment(line);
						sprintf(line, "mov ax, [%s]", yylval.varName);
						addToCodeSegment(line);} 
				| INT_CONST {char line[256]; strcpy(line, "mov dx, 0");
						addToCodeSegment(line);
						sprintf(line, "mov ax, %d", yylval.value);
						addToCodeSegment(line);};

operand_impartire2: ID {char line[256]; sprintf(line, "div word [%s]", yylval.varName);
						addToCodeSegment(line);
						sprintf(line, "mov word [%s], ax", varNameAssigment);
						addToCodeSegment(line);
						sprintf(line, "mov word [%s + 2], 0 \n", varNameAssigment);
						addToCodeSegment(line);} 
			| INT_CONST {char line[256]; sprintf(line, "mov bx, %d", yylval.value);
						addToCodeSegment(line);
						strcpy(line, "div bx");
						addToCodeSegment(line);
						sprintf(line, "mov word [%s], ax", varNameAssigment);
						addToCodeSegment(line);
						sprintf(line, "mov word [%s + 2], 0 \n", varNameAssigment);
						addToCodeSegment(line);};

operand: ID {char line[256]; sprintf(line, "mov eax, [%s]", yylval.varName); 
				addToCodeSegment(line);}
		| INT_CONST {char line[256]; sprintf(line, "mov eax, %d", yylval.value); 
				addToCodeSegment(line);} ;

declarare: INT listaDeclarare;
listaDeclarare: ID {addToDataSegment(yylval.varName);} | ID {addToDataSegment(yylval.varName);} COMMA listaDeclarare;


%%

int main()
{
    yyparse();
    if(success){
    	printf("Parsing Successful\n");
    }
    int i;
    FILE *outFile = fopen("fisier.asm", "w");
    fprintf(outFile, "bits 32 \nglobal start \nextern exit, printf, scanf \nimport exit msvcrt.dll \nimport printf msvcrt.dll \nimport scanf msvcrt.dll \n\nsegment data use32 class=data \n");
    fprintf(outFile, "\tformat_scanf db \"%s\", 0 \n", "%d");
    fprintf(outFile, "\tformat_printf db \"%s\", 0 \n", "%d");

    for(i = 0; i < dataSegmentLines; i++) {
	fprintf(outFile, "%s \n", dataSegment[i]);
    }
    fprintf(outFile, "\nsegment code use32 class=code \n");
    fprintf(outFile, "start: \n");
    for(i = 0; i < codeSegmentLines; i++) {
	fprintf(outFile, "%s \n", codeSegment[i]);
    }

    fprintf(outFile, "\tpush dword 0 \n");
    fprintf(outFile, "\tcall [exit]");
    return 0;
}

int yyerror(const char *msg)
{
	extern int yylineno;
	printf("Parsing Failed\nLine Number: %d %s\n",yylineno,msg);
	success = 0;
	return 0;
}