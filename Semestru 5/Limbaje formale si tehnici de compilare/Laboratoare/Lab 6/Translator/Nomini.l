%{
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "bAttrib.h"
#include "bMini.tab.h"

extern int yylex();
extern int yyparse();

extern int lineno; 

%}

%%

"int" {return INT;}
"cout" {return COUT;}
"cin" {return CIN;}
"return 0;" {return RETURN;}
"+" {return PLUS;}
"-" {return MINUS;}
"*" {return MULTIPLY;}
"/" {return DIVIDE;}
";" {return SEMICOLON;}
"=" {return EQUAL;}
"<<" {return OUTP;}
">>" {return INP;}
[a-zA-Z_][a-zA-Z_0-9]{0,7} {strcpy(yylval.varname,yytext); return ID;}
[+|-]?([1-9][0-9]*|0) {strcpy(yylval.varname,yytext); return CONST;}
[\n] lineno++;
[ \t\r]+
. {printf("Invalid token: %s \n on line #%d\n",
                                yytext,lineno);}

%%

yywrap() {}

