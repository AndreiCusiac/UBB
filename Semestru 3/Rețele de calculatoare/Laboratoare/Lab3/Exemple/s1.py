#Enunt: Clientul trimite serverului un numar. Serverul il primeste si il afiseaza pe ecran.
#
#Rulare in doua terminale diferite:
#	python server.py
#	python client.py
import socket

TCP_IP = "127.0.0.1"
TCP_PORT = 4321

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((TCP_IP, TCP_PORT))
s.listen(1)

while 1:
	conn, addr = s.accept()
	print 'Connection address:', addr
	data = conn.recv(10)
	print "Data received"
        conn.send(data)
        print "Sending to the client"
	if not data: break
conn.close()