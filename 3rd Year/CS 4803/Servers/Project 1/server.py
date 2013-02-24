# Echo server program
import socket

HOST = ''                 # Symbolic name meaning the local host
PORT = 8080              # Arbitrary non-privileged port
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST, PORT))
while 1:
    s.listen(1)
    conn, addr = s.accept()

    while 1:
        data = conn.recv(1024)
        if not data: break
        conn.send("Good Job " + data)
    conn.close()
