package ClientServer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by fashkl
 */
public class Server extends JFrame {

    JTextArea text;
    DataInputStream inputFromClient;
    DataOutputStream outputToClient;
    ServerSocket server;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        setLayout(null);
        text = new JTextArea();
        text.setBounds(5, 5, 474, 300);
        text.setFont(new Font("TimesRoman", Font.BOLD, 11));
        text.setEditable(false);

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        text.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Server");
        setLocationRelativeTo(null);
        JScrollPane pane = new JScrollPane(text);
        pane.setBounds(5, 5, 474, 300);
        add(pane);

        try {

            server = new ServerSocket(6100);
            text.append("Server Started. \n");
            Socket socket1 = server.accept();
            text.append("Client1 Started. \n");
            Socket socket2 = server.accept();
            text.append("Client2 Started. \n");

            new Thread(new tsk(socket1, socket2, text)).start();
            new Thread(new tsk(socket2, socket1, text)).start();
//            text.append("Server Started. \n");

        } catch (IOException e) {
            System.err.println(e);
        }

    }
}

class tsk implements Runnable {

    Socket s1, s2;
    JTextArea t;

    public tsk(Socket s, Socket ss, JTextArea tt) {
        s1 = s;
        s2 = ss;
        t = tt;
    }


    @Override
    public void run() {
        try {
            DataInputStream inputFromClient1 = new DataInputStream(s1.getInputStream());
            DataOutputStream outputToClient1 = new DataOutputStream(s1.getOutputStream());
            DataInputStream inputFromClient2 = new DataInputStream(s2.getInputStream());
            DataOutputStream outputToClient2 = new DataOutputStream(s2.getOutputStream());
            while (true) {
                String dd = inputFromClient2.readUTF();
                t.append(dd+"\n");

                outputToClient1.writeUTF(dd);
            }

        } catch (IOException ex) {
        }
    }

}
