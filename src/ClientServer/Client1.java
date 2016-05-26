package ClientServer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by fashkl
 */

public class Client1 extends JFrame {

    JTextArea txtfld = new JTextArea();
    JScrollPane txtAreaPane;
    JTextArea txtarea = new JTextArea();
    JButton btn;
    DataOutputStream toServer;
    DataInputStream fromServer;

    public static void main(String[] args) {
        new Client1();
    }

    public Client1() {

        setLayout(null);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
//        add(txtfldPane,BorderLayout.CENTER);
        JScrollPane pane = new JScrollPane(txtarea);

//        txtarea.setBounds(5, 5, 474, 245);
        txtarea.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtarea.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        txtarea.setEditable(false);
        txtAreaPane = new JScrollPane(txtarea);
        txtAreaPane.setBounds(5, 5, 474, 245);
        add(txtAreaPane);


        JScrollPane txtFldPane = new JScrollPane(txtfld);
//        txtfld.setBounds(5, 256, 400, 50);
        txtfld.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        txtfld.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtFldPane.setBounds(5, 256, 400, 50);
        add(txtFldPane);

//        add(pane,BorderLayout.CENTER);
        btn = new JButton("Send");
        btn.setBounds(408, 256, 70, 50);
        add(btn);


        setTitle("Client1 ");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true); // It is necessary to show the frame her

        try {
            Socket socket = new Socket("127.0.0.1", 6100);

            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
            //to show that client to is connected

            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        toServer.writeUTF("Client1 : " + txtfld.getText() + "\n");
                        txtarea.append(   "me       : "+txtfld.getText()+"\n");
                        toServer.flush();
                        txtfld.setText("");

                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                }

            });

            while (true) {
                txtarea.append(fromServer.readUTF()+"\n");
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
