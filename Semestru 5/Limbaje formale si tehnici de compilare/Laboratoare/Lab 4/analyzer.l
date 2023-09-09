%{
#include<stdlib.h>
#include<string.h>
#include<math.h>
#include "y.tab.h"

extern int yylex();
extern int yyparse();

typedef struct{
  int atom_code;
  int ts_code;
  char text[250];
}LexicalAtom;

typedef struct{
  char text[250];
}TSAtom;

LexicalAtom fip[300];
int fipLength = 0;
TSAtom ts[300];
int tsLength = 0;
int valid = 1;

void addLexicalAtom(int atom_code, int ts_code, char text[]){
  fip[fipLength].atom_code = atom_code;
  fip[fipLength].ts_code = ts_code;
  strcpy(fip[fipLength].text, text);
  fipLength++;
}

void addToTS(char atom[]){
  int found = 0;
  int i = 0;
  while(i < tsLength){
    int res = strcmp(ts[i].text, atom);
    if(res == 0){
      found = 1;
      addLexicalAtom(0, i, atom);
      break;
    }
    ++i;
  }

  if(found == 0){
    if(tsLength == 0){
      strcpy(ts[0].text, atom);
      tsLength++;
      addLexicalAtom(0, 111, atom);
    }
    else{
      if(tsLength == 1){
        if(strcmp(atom, ts[0].text) < 0){
          strcpy(ts[1].text, ts[0].text);
          strcpy(ts[0].text, atom);
          addLexicalAtom(0, 111, atom);
          tsLength++;
        }
        else{
          strcpy(ts[1].text, atom);
          addLexicalAtom(0, 111, atom);
          tsLength++;
        }
      }
      else{
        int pos = 0;
        while(pos < tsLength && strcmp(atom, ts[pos].text) > 0){
          pos++;
        }
        pos--;

        i = tsLength - 1;
        while(i > pos){
          strcpy(ts[i + 1].text, ts[i].text);
          i--;
        }
        strcpy(ts[pos + 1].text, atom);
        tsLength++;
        addLexicalAtom(0, 111, atom);
      } 
    }
  }
}

void updateTS(){
  for(int i = 0; i < fipLength; i++){
    if(fip[i].atom_code == 0){
      for(int j = 0; j < tsLength; j++){
        if(strcmp(ts[j].text, fip[i].text) == 0){
          fip[i].ts_code = j;
        }
      }
    }
  }
}

void fipShow(){
  printf("FIP:\n");
  int t;
  for(t = 0; t < fipLength;++t){
    printf("fip = %d, ts = %d, token = %s\n", fip[t].atom_code, fip[t].ts_code, fip[t].text);
  }
}

void tsShow(){
  printf("TS: \n");
  for(int i=0;i<tsLength;++i){
    printf("%d - %s\n", i, ts[i].text);
  }
  printf("\n");
}

void show(){
  tsShow();
  fipShow();
}
%}

%option noyywrap
DIGIT     [0-9]
ID        [a-zA-Z]{1,8}

%%
{DIGIT}+              {addToTS(yytext); return CONSTANT;}
{DIGIT}+"."{DIGIT}*   {addToTS(yytext); return CONSTANT;}

\#                    {addLexicalAtom(1,0,yytext); return '#';}
include               {addLexicalAtom(2,0,yytext); return INCLUDE;}
iostream              {addLexicalAtom(3,0,yytext); return IOSTREAM;}
using                 {addLexicalAtom(4,0,yytext); return USING;}
namespace             {addLexicalAtom(5,0,yytext); return NAMESPACE;}
std                   {addLexicalAtom(6,0,yytext); return STD;}
int                   {addLexicalAtom(7,0,yytext); return INT;}
main                  {addLexicalAtom(8,0,yytext); return MAIN;}
float                  {addLexicalAtom(9,0,yytext); return FLOAT;}
cin                   {addLexicalAtom(10,0,yytext); return CIN;}
cout                  {addLexicalAtom(11,0,yytext); return COUT;}
if                    {addLexicalAtom(12,0,yytext); return IF;}
else                  {addLexicalAtom(13,0,yytext); return ELSE;}
while                 {addLexicalAtom(14,0,yytext); return WHILE;}
\{                    {addLexicalAtom(15,0,yytext); return '{';}
\}                    {addLexicalAtom(16,0,yytext); return '}';}
\;                    {addLexicalAtom(17,0,yytext); return ';';}
\(                    {addLexicalAtom(18,0,yytext); return '(';}
\)                    {addLexicalAtom(19,0,yytext); return ')';}
\+                    {addLexicalAtom(20,0,yytext); return '+';}
\-                    {addLexicalAtom(21,0,yytext); return '-';}
\*                    {addLexicalAtom(22,0,yytext); return '*';}
\/                    {addLexicalAtom(23,0,yytext); return '/';}
\%                    {addLexicalAtom(24,0,yytext); return '%';}
\>                    {addLexicalAtom(25,0,yytext); return GT;}
\>=                   {addLexicalAtom(26,0,yytext); return GE;}
\<                    {addLexicalAtom(27,0,yytext); return LT;}
\<=                   {addLexicalAtom(28,0,yytext); return LE;}
\==                   {addLexicalAtom(29,0,yytext); return EQ;}
\!=                   {addLexicalAtom(30,0,yytext); return NEQ;}
\=                    {addLexicalAtom(31,0,yytext); return ASSIGN;}
return                {addLexicalAtom(32,0,yytext); return RETURN;}

{ID}                  {addToTS(yytext); return IDENTIFIER;}

[ \t\n]+          ;/* eat up whitespace */

. printf("ERROR: Illegal token %s\n", yytext); valid = 0;

%%