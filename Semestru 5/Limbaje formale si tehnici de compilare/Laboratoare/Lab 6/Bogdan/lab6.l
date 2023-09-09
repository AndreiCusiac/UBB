%{
	#include<stdio.h>
	#include<stdlib.h>
	#include<string.h>
	#include"lab6.tab.h"
	#include<math.h>
%}

%option yylineno

INT_CONST	([+-]?(([1-9][0-9]*)|(0[1-7][0-7]*)|(0[xX][1-9a-fA-F][0-9a-fA-F]*)|(0[bB]1[01]*))[uU]?[lL]?[lL]?[uU]?)|0
ID	[a-zA-Z][a-zA-Z_]*

%%


int {
		printf( "Cuvant cheie: %s\n", yytext );
		return INT;
	}
cin {
		printf( "Cuvant cheie: %s\n", yytext );
		return CIN;
	}
cout {
		printf( "Cuvant cheie: %s\n", yytext );
		return COUT;
	}

return {
		printf( "Cuvant cheie: %s\n", yytext );
		return RETURN;
	}
"=" {
		printf( "Operator: %s\n", yytext );
		return ASSIGNMENT;
	}
"{" {		
		printf( "Separator: %s\n", yytext );
		return LEFT_BRACKET;
	}
"}" {
		printf( "Separator: %s\n", yytext );
		return RIGHT_BRACKET;
	}
";" {
		printf( "Separator: %s\n", yytext );
		return SEMI_COLON;
	}
"," {
		printf( "Separator: %s\n", yytext );
		return COMMA;
	}
"(" {
		printf( "Separator: %s\n", yytext );
		return LEFT_ROUND_PARENTHESIS;
	}
")" {
		printf( "Separator: %s\n", yytext );
		return RIGHT_ROUND_PARENTHESIS;
	}
"[" {
		printf( "Separator: %s\n", yytext );
		return LEFT_SQUARE_PARENTHESIS;
	}
"]" {
		printf( "Separator: %s\n", yytext );
		return RIGHT_SQUARE_PARENTHESIS;
	}

"+" {
		printf( "Operator: %s\n", yytext );
		return PLUS;
	}
"-" {
		printf( "Operator: %s\n", yytext );
		return MINUS;
	}
"*" {
		printf( "Operator: %s\n", yytext );
		return MULTIPLY;
	}
"/" {
		printf( "Operator: %s\n", yytext );
		return DIVISION;
	}

"<<" {		
		printf( "Operator: %s\n", yytext );
		return DOUBLE_LESS;
	}

">>" {
		printf( "Operator: %s\n", yytext );
		return DOUBLE_GREATER;
	}


{INT_CONST} {
		printf( "Constanta int: %s\n", yytext );
		int x = atoi(yytext);
		yylval.value = x;
		return INT_CONST;
	}
{ID} {
		if(yyleng > 8) {
			printf("Error at line %d: identifer length must be max. 8 characters", yylineno);
			return -1;
		}
		strcpy(yylval.varName, yytext);
		printf("Identificator: %s\n", yytext );
		return ID;
	}

[ \t\n]+

. {
		printf("Error at line %d: lexical error", yylineno);
		return -1;
	}

%%

int yywrap()
{
    return 1;
}