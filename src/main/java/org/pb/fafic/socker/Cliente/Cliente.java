package org.pb.fafic.socker.Cliente;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cliente");
        JButton button = new JButton("Enviar Imagem");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        Socket socket = new Socket("localhost", 8080);

                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] data = new byte[(int) file.length()];
                        fileInputStream.read(data);

                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        dataOutputStream.writeInt(data.length);
                        dataOutputStream.write(data, 0, data.length);

                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        String message = dataInputStream.readUTF();

                        JOptionPane.showMessageDialog(null, "Mensagem do servidor: " + message);

                        fileInputStream.close();
                        dataOutputStream.close();
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.getContentPane().add(button);
        frame.setVisible(true);
    }
}
