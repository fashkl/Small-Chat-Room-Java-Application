package ClientServer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.*;
import java.net.Socket;

/**
 * Created by fashkl
 */
public class Client2 extends JFrame {

    JTextArea txtfld = new JTextArea();
    JScrollPane txtfldPane = new JScrollPane(txtfld);
    JTextArea txtarea;
    JButton btn;
    DataOutputStream toServer;
    DataInputStream fromServer;


    public static void main(String[] args) {
        new Client2();
    }

    public Client2() {

        setLayout(null);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
//        add(txtfldPane,BorderLayout.CENTER);

        JScrollPane pane1 = new JScrollPane(txtfld);
        txtarea = new JTextArea();
        JScrollPane pane = new JScrollPane(txtarea);
        pane.setBounds(5, 5, 474, 245);
        txtarea.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtarea.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        txtarea.setEditable(false);
        add(pane);

        pane1.setBounds(5, 256, 400, 50);
        txtfld.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        txtfld.setFont(new Font("TimesRoman", Font.BOLD, 12));
        add(pane1);

//        add(pane,BorderLayout.CENTER);
        btn = new JButton("Send");
        btn.setBounds(408, 256, 70, 50);
        add(btn);
        final JLabel me=new JLabel("me : ",JLabel.CENTER);
        me.setForeground(Color.red);

        setTitle("Client 2");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true); // It is necessary to show the frame her

        try {
            Socket socket = new Socket("127.0.0.1", 6100);

            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
//            toServer.writeUTF("Client2 Connected \n");
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        toServer.writeUTF("Client2 :  " + txtfld.getText());
                        txtarea.append("me       :  "+txtfld.getText()+"\n");
//                        txtarea.append(me+txtfld.getText()+"\n");
                        toServer.flush();
                        txtfld.setText("");
                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                }

            });
//
            while (true) {
                txtarea.append(fromServer.readUTF());
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
