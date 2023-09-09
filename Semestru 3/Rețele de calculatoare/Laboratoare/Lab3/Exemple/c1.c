/* Enunt: Un server primeste numere de la clienti si le afiseaza pe ecran. Serverul trimite inapoi fiecarui client numarul.

Compilare: 
	gcc server.c -o server
	gcc client.c -o client
	
Rulare in doua terminale diferite:
	./server
	./client
*/
#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>

int main(){
  int clientSocket;
  uint16_t nr,rez;
  char buffer[1024];
  struct sockaddr_in serverAddr;

  clientSocket = socket(AF_INET, SOCK_STREAM, 0);
  
  serverAddr.sin_family = AF_INET;	
  serverAddr.sin_port = htons(4321);	
  serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");	
   
  if (connect(clientSocket, (struct sockaddr *) &serverAddr, sizeof serverAddr) < 0) {
    printf("Eroare la conectarea la server\n");
    return 1;
  }
  printf("Introduceti numarul:\n");
  scanf("%hu",&nr);
  
  printf("Trimit la server %d\n",nr); 
  nr=htons(nr);
  send(clientSocket,&nr,sizeof(uint16_t),0);

  recv(clientSocket, &rez, sizeof(uint16_t), 0);
  rez=ntohs(rez);
  printf("Data received: %d\n",rez);   				
  close(clientSocket);

  return 0;
}