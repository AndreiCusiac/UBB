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
  int welcomeSocket, newSocket;
  uint16_t  nr, rez;
  char buffer[1024];
  struct sockaddr_in serverAddr;
  struct sockaddr_storage serverStorage;
  socklen_t addr_size;
  
  welcomeSocket = socket(AF_INET, SOCK_STREAM, 0);
  serverAddr.sin_family = AF_INET;		
  serverAddr.sin_port = htons(4321);
  serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");	

  bind(welcomeSocket, (struct sockaddr *) &serverAddr, sizeof(serverAddr));	
  listen(welcomeSocket,5);							
  addr_size = sizeof serverStorage;
  while(1)
  {
	printf("Waiting for clients...\n");
	newSocket = accept(welcomeSocket, (struct sockaddr *) &serverStorage, &addr_size);
 
	recv(newSocket, &nr, sizeof(uint16_t), 0);
	nr=ntohs(nr);
	printf("Data received\n");   			
	rez=nr;
	printf("Sending to the client\n");
	rez=htons(rez);
	send(newSocket,&rez,sizeof(uint16_t),0);
	close(newSocket);
  }
  close(welcomeSocket);

  return 0;
}