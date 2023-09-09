%{
#include <stdio.h> /* for (f)printf() */
#include <stdlib.h> /* for exit() */
#include <string.h>
#include "bAttrib.h"
#include "bCodASM.h"

FILE *fpOut;

/*
struct attrib{
  char varn[10];
  char cod[250];
};
typedef struct attrib attributes;*/
    
typedef struct node
{
    char value[15];
    struct node* next;
}
node;

node* idList;

char tempbuffer[250];
char datasegmentbuffer[100][256];
int dataNo = 0;

char codesegmentbuffer[256][256];
int codeNo = 0;

char idToAssign[101];

int  lineno = 1; /* number of current source line */
extern int yylex(); /* lexical analyzer generated from lex.l */
extern char *yytext; /* last token, defined in lex.l  */

void
yyerror(char *s) {
  printf("\n \n \nMy error: \n");
  printf( "Syntax error on line #%d: %s\n", lineno, s);
  printf( "Last token was \"%s\"\n", yytext);
  exit(1);
}

int tempnr = 1;
void newTempName(char* s){
  sprintf(s,"temp%d",tempnr);
  tempnr++;
}

void addTemps2String(char* str){
 int i;
 for(i=1;i<tempnr;i++){
    sprintf(tempbuffer,"temp%d dw 0\n",i);
    strcat(str,tempbuffer);
 }
}

void addIDs2DataSegmentBuffer(){
	char buff[256];
	node* current = idList;
	
	strcpy(buff, "");
	
	while(current != NULL) {
		strcpy(buff, "");
		sprintf(buff,"\t%s dd 0\n", current->value);
		strcpy(datasegmentbuffer[dataNo],buff);
		printf("%s", datasegmentbuffer[dataNo]);
		current = current->next;
		dataNo = dataNo + 1;
	}
}

void addID2DataSegmentBuffer(char* id) {
	char buff[256];
	
	strcpy(buff, "");
	
	sprintf(buff,"\t%s dd 0\n", id);
	strcpy(datasegmentbuffer[dataNo],buff);
	//printf("%s", datasegmentbuffer[dataNo]);
	dataNo = dataNo + 1;
}

void addCode2CodeSegmentBuffer(char* id) {
	char buff[256];
	
	strcpy(buff, "");
	
	sprintf(buff,"\t\t%s\n", id);
	strcpy(codesegmentbuffer[codeNo],buff);
	//printf("%s", codesegmentbuffer[codeNo]);
	codeNo = codeNo + 1;
}


int idCount = 0;

int addId(char str[]) {
	node* newElement = malloc(sizeof(node));
	strcpy(newElement->value, str);
	newElement->next = NULL;
	//printf(newElement->value);
	
	if (idList == NULL) {
		idList = newElement;
		idCount += 1;
		return 1;
	}
	else {
		node* current = idList;
		
		while(1) {
		
			if (strcmp(current->value, str) == 0) {
				return 0;
			}
		
			if (current->next == NULL) {
				current->next = newElement;
				idCount += 1;
				return 1;
			}
			current = current->next;
		}
	}
	
	return -1;
}

void addDataSegment() {
	//addTemps2String(datasegmentbuffer);
	//addIDs2DataSegmentBuffer();
}

%}

%union {
 char varname[10];
 attributes pairAttrib;
}

%token INT
%token RETURN

%token COUT
%token CIN
%token OUTP
%token INP
%token SEMICOLON
%token EQUAL
%token PLUS
%token MINUS
%token MULTIPLY
%token DIVIDE

%token <varname> ID 
%token <varname> CONST 
%type <pairAttrib> term
%type <pairAttrib> expression
%type <pairAttrib> op
%type <pairAttrib> opAd
%type <pairAttrib> opMin
%type <pairAttrib> opMul
%type <pairAttrib> opDeimp
%type <pairAttrib> opImp

%%
program : statement_list
	
		RETURN
	
		;


statement_list  : statement_list statement
                | statement 
                ;
                                                                                   
statement       : decl SEMICOLON
				| assignment SEMICOLON
                | read_statement SEMICOLON
                | write_statement SEMICOLON
                ;
              
decl : INT ID { /*strcpy($$.cod,"");*/
                /*strcpy($$.varn,$2); */
				
				//printf("Hei\n");
				//printf($2);
				
				int added = addId($2);
				
				if (added == 1) {
					addID2DataSegmentBuffer($2);
				}
              } ;			  

read_statement  : CIN INP ID { sprintf(tempbuffer,"push dword %s", $3);
								addCode2CodeSegmentBuffer(tempbuffer);
								
								sprintf(tempbuffer,"push dword sdesc");
								addCode2CodeSegmentBuffer(tempbuffer);
								
								sprintf(tempbuffer,"call [scanf]");
								addCode2CodeSegmentBuffer(tempbuffer);
								
								sprintf(tempbuffer,"add esp, 4*2\n");
								addCode2CodeSegmentBuffer(tempbuffer);
								
                       //fprintf(fpOut, "call read_int %s\n",$3);
                       }
                ;

write_statement : COUT OUTP ID { sprintf(tempbuffer,"mov eax, [%s]", $3);
								addCode2CodeSegmentBuffer(tempbuffer);
								
								sprintf(tempbuffer,"push dword eax");
								addCode2CodeSegmentBuffer(tempbuffer);
                                
                                //sprintf(tempbuffer,"push dword nline");
								//addCode2CodeSegmentBuffer(tempbuffer);
								
								sprintf(tempbuffer,"push dword pdesc");
								addCode2CodeSegmentBuffer(tempbuffer);
								
								sprintf(tempbuffer,"call [printf]");
								addCode2CodeSegmentBuffer(tempbuffer);
								
								sprintf(tempbuffer,"add esp, 4*2\n");
								addCode2CodeSegmentBuffer(tempbuffer);
                       //fprintf(fpOut, "call print_int %s\n",$3.varn);
                       }
                ;
				
assignment      : ID EQUAL {
                    //fprintf(fpOut, "%s\n",$3.cod);
                    //fprintf(fpOut, "mov ax, %s\n",$3.varn);
                    //fprintf(fpOut, "mov %s,ax\n",$1);
					sprintf(idToAssign, "%s", $1);
                   }
				  
				expression
                ;
				
expression      : term 

                | op PLUS opAd
				
				| op MINUS opMin
				
				| op MULTIPLY opMul
				
				| opDeimp DIVIDE opImp
                ;
				
op : CONST { sprintf(tempbuffer,"mov eax, %s", $1);
			addCode2CodeSegmentBuffer(tempbuffer);	  
			}
	| ID { sprintf(tempbuffer,"mov eax, [%s]", $1);
			addCode2CodeSegmentBuffer(tempbuffer);	  
			}
	;
	
opAd : CONST { sprintf(tempbuffer,"add eax, %s", $1);
			addCode2CodeSegmentBuffer(tempbuffer);
			
			sprintf(tempbuffer,"mov dword [%s], eax\n", idToAssign);
			addCode2CodeSegmentBuffer(tempbuffer);	
			}
	| ID { sprintf(tempbuffer,"add eax, [%s]", $1);
			addCode2CodeSegmentBuffer(tempbuffer);
			
			sprintf(tempbuffer,"mov dword [%s], eax\n", idToAssign);
			addCode2CodeSegmentBuffer(tempbuffer);	
			}
	;
	
opMin : CONST { sprintf(tempbuffer,"sub eax, %s", $1);
			addCode2CodeSegmentBuffer(tempbuffer);
			
			sprintf(tempbuffer,"mov dword [%s], eax\n", idToAssign);
			addCode2CodeSegmentBuffer(tempbuffer);	
			}
	| ID { sprintf(tempbuffer,"sub eax, [%s]", $1);
			addCode2CodeSegmentBuffer(tempbuffer);
			
			sprintf(tempbuffer,"mov dword [%s], eax\n", idToAssign);
			addCode2CodeSegmentBuffer(tempbuffer);	
			}
	;
	
opMul : CONST { sprintf(tempbuffer,"mov ebx, %s", $1);
			addCode2CodeSegmentBuffer(tempbuffer);

			sprintf(tempbuffer,"mul ebx");
			addCode2CodeSegmentBuffer(tempbuffer);
			
			sprintf(tempbuffer,"mov dword [%s], eax\n", idToAssign);
			addCode2CodeSegmentBuffer(tempbuffer);	
			}
	| ID { sprintf(tempbuffer,"mov ebx, [%s]", $1);
			addCode2CodeSegmentBuffer(tempbuffer);
	
			sprintf(tempbuffer,"mul ebx");
			addCode2CodeSegmentBuffer(tempbuffer);
			
			sprintf(tempbuffer,"mov dword [%s], eax\n", idToAssign);
			addCode2CodeSegmentBuffer(tempbuffer);	
			}
	;
	
opDeimp : CONST { sprintf(tempbuffer,"mov ax, %s", $1);
			addCode2CodeSegmentBuffer(tempbuffer);
			
			sprintf(tempbuffer,"mov dx, 0", $1);
			addCode2CodeSegmentBuffer(tempbuffer);
			}
	| ID { sprintf(tempbuffer,"mov ax, word[%s + 0]", $1);
			addCode2CodeSegmentBuffer(tempbuffer);
			
			sprintf(tempbuffer,"mov dx, word[%s + 2]", $1);
			addCode2CodeSegmentBuffer(tempbuffer);
			}
	;	
	
opImp : CONST { sprintf(tempbuffer,"mov bx, %s", $1);
			addCode2CodeSegmentBuffer(tempbuffer);
			
			sprintf(tempbuffer,"div bx");
			addCode2CodeSegmentBuffer(tempbuffer);
			
			sprintf(tempbuffer,"mov word[%s + 0], ax\n", idToAssign);
			addCode2CodeSegmentBuffer(tempbuffer);
			
			sprintf(tempbuffer,"mov word[%s + 2], 0\n", idToAssign);
			addCode2CodeSegmentBuffer(tempbuffer);
			}
	| ID { sprintf(tempbuffer,"div word[%s]", $1);
			addCode2CodeSegmentBuffer(tempbuffer);
			
			sprintf(tempbuffer,"mov word[%s + 0], ax\n", idToAssign);
			addCode2CodeSegmentBuffer(tempbuffer);
			
			sprintf(tempbuffer,"mov word[%s + 2], 0\n", idToAssign);
			addCode2CodeSegmentBuffer(tempbuffer);
			}
	;
	
term            : CONST { sprintf(tempbuffer,"mov eax, %s", $1);
						addCode2CodeSegmentBuffer(tempbuffer);
						
						sprintf(tempbuffer,"mov dword [%s], eax\n", idToAssign);
						addCode2CodeSegmentBuffer(tempbuffer);	  
                       }
					   
                | ID { sprintf(tempbuffer,"mov eax, [%s]", $1);
						addCode2CodeSegmentBuffer(tempbuffer);
						
						sprintf(tempbuffer,"mov dword [%s], eax\n", idToAssign);
						addCode2CodeSegmentBuffer(tempbuffer);
                       }
                ;

%%

extern FILE *yyin;

int main(int argc, char* argv[])
{
	FILE *fp;
	char fileDefault[200] = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Limbaje formale si tehnici de compilare\\Laboratoare\\Lab 6\\";
	char file1[200] = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Limbaje formale si tehnici de compilare\\Laboratoare\\Lab 6\\mini1.txt";
	char file2[200] = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Limbaje formale si tehnici de compilare\\Laboratoare\\Lab 6\\mini2.txt";
	char file3[200] = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Limbaje formale si tehnici de compilare\\Laboratoare\\Lab 6\\mini3.txt";
	char* filename;
	
	char startFile[256] = "bits 32\nglobal start\nextern exit, scanf, printf\nimport exit msvcrt.dll\nimport scanf msvcrt.dll\nimport printf msvcrt.dll\n\n";
	
	char segmentData[256] = "segment data use32 class=data\n\n";
	char printDesc[256] = "\tpdesc db \'%s\', 0\n";
	char scanDesc[256] = "\tsdesc db \'%s\', 0\n";
    
	char newLineDesc[256] = "\tnline db \'%s\', ' '\n";
	
	char segmentCode[256] = "\nsegment code use32 class=code\n\tstart:\n";
	
	char exitFile[256] = "\n\t\tpush dword 0\n\t\tcall [exit]";

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
	
	char fpASM[15] = "output.asm";
	char* fpFile = fpASM;
	fpOut = fopen(fpFile, "w+");
	
	fprintf(fpOut, startFile);
	fprintf(fpOut, segmentData);
	fprintf(fpOut, printDesc, "%d");
	fprintf(fpOut, scanDesc, "%d");
    
	fprintf(fpOut, newLineDesc, "%c");
	
	int i;
	
	for (i = 0; i < dataNo; i++) {
		fprintf(fpOut, "%s", datasegmentbuffer[i]);
	}
	
	fprintf(fpOut, segmentCode);
	
	for (i = 0; i < codeNo; i++) {
		fprintf(fpOut, "%s", codesegmentbuffer[i]);
	}
	
	fprintf(fpOut, exitFile);


	fclose(fp);
	fclose(fpOut);

	printf("OK!");

	return 0;
}
