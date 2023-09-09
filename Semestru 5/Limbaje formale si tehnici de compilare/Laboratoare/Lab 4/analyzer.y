%{
#include <stdio.h>
#include <stdlib.h>

#define YYDEBUG 1

int yylex(void);
void yyerror(char *s)
{
  printf("%s\n", s);
}

%}

%token IDENTIFIER
%token CONSTANT
%token MAIN
%token IF
%token ELSE
%token WHILE
%token INT
%token FLOAT
%token CIN
%token COUT
%token STD
%token IOSTREAM
%token INCLUDE
%token USING
%token NAMESPACE
%token GT
%token LT
%token GE
%token LE
%token ASSIGN
%token NEQ
%token EQ
%token OR
%token AND
%token NO_PARAMS
%token VOID
%token RETURN 

%start program

%%     
program: libraries namespace function | function ;
libraries: '#' INCLUDE LT IOSTREAM GT ;
namespace: USING NAMESPACE STD ';' ;
function: INT MAIN '(' ')' '{' listaInstructiuni return '}' | INT MAIN ;
listaInstructiuni: instructiune | instructiune listaInstructiuni ;
instructiune: declarare | atribuire | if | while | cin | cout ;
declarare: type IDENTIFIER ';' | type IDENTIFIER ASSIGN CONSTANT ';' ;
type: INT | FLOAT ;
atribuire: IDENTIFIER ASSIGN expresie ';' ;
expresie: term | expresie '+' term | expresie '-' term | expresie '*' term | expresie '/' term | expresie '%' term ;
term: CONSTANT | IDENTIFIER ;
cin: CIN GT GT IDENTIFIER ';' ;
cout: COUT LT LT IDENTIFIER ';' ;
if: IF '(' condition ')' '{' listaInstructiuni '}' | IF '(' condition ')' '{' listaInstructiuni '}' ELSE '{' listaInstructiuni '}' ;
condition: expresie relatie expresie ;
relatie: LE | LT | GE | GT | EQ | NEQ ;
while: WHILE '(' condition ')' '{' listaInstructiuni '}' ;
return: RETURN CONSTANT ';' ;

%%
extern FILE *yyin;
int main(int argc, char **argv)
{
  if (argc > 1) 
    yyin = fopen(argv[1], "r");
    
  if ( !yyparse() ) 
    fprintf(stderr,"\t Program is correct\n");
}
