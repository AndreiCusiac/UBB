 <?php
 /* Enunt: Clientul trimite serverului un numar. Serverul il primeste si il afiseaza pe ecran.
	
 Rulare in doua terminale diferite:
	php server.php
	php client.php
 */ 
 $s = socket_create(AF_INET, SOCK_STREAM, 0);
 socket_connect($s , "127.0.0.1",4321);
 $nr=readline("Dati un numar: \n");
 socket_send($s, $nr, 100, 0);
 socket_recv($s,$nr,100,0);
 echo "Am primit de la server $nr\n";
 socket_close($s);
?>