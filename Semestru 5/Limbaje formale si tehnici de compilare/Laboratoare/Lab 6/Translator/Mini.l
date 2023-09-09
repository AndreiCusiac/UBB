%{

#include <math.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
extern int yylex();
extern int yyparse();


#define M_FOR_HASH 13
#define LEN_MAX 8

FILE *fipOut;	
FILE *tsOut;

typedef struct node
{
    char value[15];
    struct node* next;
}
node;

node* hash[M_FOR_HASH] = {NULL};

int count = 0;
int line = 1;
char reserved[40][10] = {"int", "cout", "cin", "+\0", "-\0", "*\0", "/\0", ";\0", "=\0", "<<", ">>", "<\0", ">\0"};

int searchForReserved(char* str) {
	int i;
	for (i = 0; i < 40; i++) {
		if (strcmp(str, reserved[i]) == 0) {
			return i + 1;
		}
	}

	return -1;
}

int hashFunction(char str[]) {
	int ascii_sum;
	ascii_sum = 0;

	int i;
	for (i = 0; i < strlen(str); i++) {
		ascii_sum = ascii_sum + str[i];
	}

	return ascii_sum % M_FOR_HASH;
}

void addElement(char str[]) {
	int key = hashFunction(str);

	node* newElement = malloc(sizeof(node));
	strcpy(newElement->value, str);
	newElement->next = NULL;

	if (hash[key] == NULL) {
		hash[key] = newElement;
	}
	else {
		node* current = hash[key];

		while(1) {
			if (current->next == NULL) {
				current->next = newElement;
				break;
			}
			current = current->next;
		}
	}
}

int findElement(char str[]) {
	int key = hashFunction(str);
	int index;
	index = 0;

	node* current = hash[key];

	while (current != NULL) {
		if (strcmp(str, current->value) == 0) {
			return key * 10 + index;
		}
		current = current->next;
		index = index + 1;
	}

	return -1;
}

void writeToFip(char str[]) {
	fprintf(fipOut, str);
	fprintf(fipOut, "\n");
}

void writeToTS(char str[], char type) {
	
	int val;
	val = searchForReserved(str);

	if (val != -1) {
		fprintf(tsOut, "%d", val);
		fprintf(tsOut, " -");
	}
	else {
		fprintf(tsOut, "0 ");

		if (strlen(str) > LEN_MAX && type == 'i') {
			fprintf(tsOut, "EROARE");
		}
		else {
			if (findElement(str) == -1) {
				addElement(str);
			}
			fprintf(tsOut, "%d", findElement(str));
		}
	}

	fprintf(tsOut, "\n");
}

%}

%%

int {writeToFip(yytext); writeToTS(yytext, 'r'); count++;}
cout {writeToFip(yytext); writeToTS(yytext, 'r'); count++;}
cin {writeToFip(yytext); writeToTS(yytext, 'r'); count++;}
"+" {writeToFip(yytext); writeToTS(yytext, 'r'); count++;}
"-" {writeToFip(yytext); writeToTS(yytext, 'r'); count++;}
"*" {writeToFip(yytext); writeToTS(yytext, 'r'); count++;}
"/" {writeToFip(yytext); writeToTS(yytext, 'r'); count++;}
";" {writeToFip(yytext); writeToTS(yytext, 'r'); count++;}
"=" {writeToFip(yytext); writeToTS(yytext, 'r'); count++;}
"<<" {writeToFip(yytext); writeToTS(yytext, 'r'); count++;}
">>" {writeToFip(yytext); writeToTS(yytext, 'r'); count++;}
"<" {writeToFip(yytext); writeToTS(yytext, 'r'); count++;}
">" {writeToFip(yytext); writeToTS(yytext, 'r'); count++;}
[a-zA-Z_][a-zA-Z_0-9]{0,7} {/*printf("Identifier: %s\n", yytext);*/ writeToFip(yytext); writeToTS(yytext, 'i'); count++;}
[+|-]?([1-9][0-9]*|0) {/*printf("Integer: %s\n", yytext);*/ writeToFip(yytext); writeToTS(yytext, 'd'); count++;;}
[\n] {line = line + 1;}
[ \t]+
. {printf("Invalid token %s at line %d \n", yytext, line);}

%%

yywrap() {}

int main(int argc, char* argv[])
{
	FILE *fp;
	char fileDefault[200] = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Limbaje formale si tehnici de compilare\\Laboratoare\\Lab 6\\";
	char file1[200] = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Limbaje formale si tehnici de compilare\\Laboratoare\\Lab 6\\mini1.txt";
	char file2[200] = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Limbaje formale si tehnici de compilare\\Laboratoare\\Lab 6\\mini2.txt";
	char file3[200] = "C:\\Users\\Andrei\\Desktop\\UBB\\Semestru 5\\Limbaje formale si tehnici de compilare\\Laboratoare\\Lab 6\\mini3.txt";


	if (argc > 1) {
		printf("Fisierul dat: %s\n", argv[1]);

		strcat(fileDefault, argv[1]);
		strcat(fileDefault, ".txt");

		char* filename;
		filename = fileDefault;

		printf("Current filename: %s\n", filename);
		fp = fopen(filename,"r");

		char fip[10] = "";
		strcat(fip, argv[1]);
		strcat(fip, "fip.txt");

		char ts[10] = "";
		strcat(ts, argv[1]);
		strcat(ts, "ts.txt");

		char* fipFile = fip;
		char* tsFile = ts;

		fipOut = fopen(fipFile,"w");
		tsOut = fopen(tsFile,"w");
		
		yyin = fp;
		
		//yyparse();
		yylex();
		//yyparse();

		fclose(fp);
		fclose(fipOut);
		fclose(tsOut);

		printf("Written fip in file: %s\n", fipFile);
		printf("Written ts in file: %s\n", tsFile);
	}
	else {
		char* filename;
		filename = file1;

		printf("Current filename: %s\n", filename);
		fp = fopen(filename,"r");

		char fip[20] = "mini1fip.txt";
		char ts[20] = "mini1ts.txt";
		char* fipFile = fip;
		char* tsFile = ts;

		fipOut = fopen(fipFile,"w");
		tsOut = fopen(tsFile,"w");
		
		yyin = fp;
		yylex();
		
		fclose(fp);
		fclose(fipOut);
		fclose(tsOut);

		printf("Written fip in file: %s\n", fipFile);
		printf("Written ts in file: %s\n", tsFile);
	}

	return 0;
}
