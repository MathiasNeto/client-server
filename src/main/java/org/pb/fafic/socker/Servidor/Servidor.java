package org.pb.fafic.socker.Servidor;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new ClientHandler(socket)).start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                int length = dataInputStream.readInt();
                byte[] data = new byte[length];
                dataInputStream.readFully(data, 0, data.length);

                String imagePath = "received_" + System.currentTimeMillis() + ".jpg";
                File file = new File(imagePath);
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(data);

                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("Imagem recebida e salva como " + file.getAbsolutePath());

                outputStream.close();
                dataInputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
