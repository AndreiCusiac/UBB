%{
	#include <stdlib.h>
	#include <stdio.h>
	#include <string.h>
	
	#include "bAttrib.h"
	#include "bCodASM.h"
	void yyerror (char *);
	extern int yylex(void);
	extern char *yytext; /* last token, defined in lex.l  */
	
	int  lineno = 1; /* number of current source line */
	
	int tempnr = 1;
	void newTempName(char* s){
		sprintf(s,"temp%d",tempnr);
		tempnr++;
	}

	char tempbuffer[250];
	char datasegmentbuffer[500];

	void addTemps2String(char* str){
	int i;
	for(i=1;i<tempnr;i++){
		sprintf(tempbuffer,"temp%d dw ?\n",i);
		strcat(str,tempbuffer);
	}
}
%}

%union {
 char varname[10];
 attributes pairAttrib;
}


%start program

%token INT
%token <varname> ID
%token <varname> CONST
%token COUT
%token CIN
%token OUTP
%token INP
%token RETURN
%% 

program : 

	{ strcpy(datasegmentbuffer,"");
		printf(INCEPUT); }
	
	{ printf(TEXTSEGINCEPUT);
		printf(TEXTSEGMIJLOC); }
	
	lista_instr
	
	RETURN 
	
	{ printf(TEXTSEGSFARSIT);        
		printf(SFARSIT); }
	
	;

lista_instr : instr 
	| instr lista_instr
	;

instr : decl
	| output
	| input
	| num
	;

decl : tip lista_id 
	| lista_id
	;

lista_id : num
	;

num : ID ';'
	| ID '=' op ';'
	;

op : ID 
	| CONST
	| ID operand op
	| CONST operand op
	;

operand : '+'
	| '-'
	| '*'
	| '/'
	;

tip : INT 
	;

output : COUT OUTP afis ';'
	;

afis : ID
	| CONST
	;

input : CIN INP ID ';'
	;

%%

void yyerror(char *s) {
  printf("\n \n \nMy error: \n");
  printf( "Syntax error on line #%d: %s\n", lineno, s);
  printf( "Last token was \"%s\"\n", yytext);
  exit(1);
}

extern FILE *yyin;

int main(int argc, char* argv[])
{
	FILE *fp;
	char fileDefault[200] = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Limbaje formale si tehnici de compilare\\Laboratoare\\Lab 6\\";
	char file1[200] = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Limbaje formale si tehnici de compilare\\Laboratoare\\Lab 6\\mini1.txt";
	char file2[200] = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Limbaje formale si tehnici de compilare\\Laboratoare\\Lab 6\\mini2.txt";
	char file3[200] = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Limbaje formale si tehnici de compilare\\Laboratoare\\Lab 6\\mini3.txt";
	char* filename;

	if (argc > 1) {
		printf("Fisierul dat: %s\n", argv[1]);

		strcat(fileDefault, argv[1]);
		strcat(fileDefault, ".txt");

		filename = fileDefault;
	}
	else {
		filename = file1;
	}

	printf("Current filename: %s\n", filename);
	fp = fopen(filename,"r");

	yyin = fp;

	yyparse();

	fclose(fp);

	printf("OK!");

	return 0;
}

