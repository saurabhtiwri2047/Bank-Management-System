package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JLabel l1, l2, l3;
    JTextField tf1;
    JPasswordField pf2;
    JButton b1, b2, b3;

    Login() {
        setTitle("AUTOMATED TELLER MACHINE");
        setLayout(null);

        // ✅ Correct Image Path (make sure icons folder is in src)
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l11 = new JLabel(i3);
        l11.setBounds(70, 10, 100, 100);
        add(l11);

        // ✅ Heading
        l1 = new JLabel("WELCOME TO ATM");
        l1.setFont(new Font("Arial", Font.BOLD, 36)); // safer font
        l1.setBounds(200, 40, 450, 40);
        add(l1);

        // ✅ Card Number
        l2 = new JLabel("Card No:");
        l2.setFont(new Font("Arial", Font.BOLD, 22));
        l2.setBounds(125, 150, 150, 30);
        add(l2);

        tf1 = new JTextField();
        tf1.setBounds(300, 150, 230, 30);
        tf1.setFont(new Font("Arial", Font.BOLD, 14));
        add(tf1);

        // ✅ PIN
        l3 = new JLabel("PIN:");
        l3.setFont(new Font("Arial", Font.BOLD, 22));
        l3.setBounds(125, 220, 150, 30);
        add(l3);

        pf2 = new JPasswordField();
        pf2.setBounds(300, 220, 230, 30);
        pf2.setFont(new Font("Arial", Font.BOLD, 14));
        add(pf2);

        // ✅ Buttons
        b1 = new JButton("SIGN IN");
        b2 = new JButton("CLEAR");
        b3 = new JButton("SIGN UP");

        JButton[] buttons = {b1, b2, b3};
        for (JButton b : buttons) {
            b.setBackground(Color.BLACK);
            b.setForeground(Color.WHITE);
            b.setFont(new Font("Arial", Font.BOLD, 14));
            add(b);
            b.addActionListener(this);
        }

        b1.setBounds(300, 300, 100, 30);
        b2.setBounds(430, 300, 100, 30);
        b3.setBounds(300, 350, 230, 30);

        getContentPane().setBackground(Color.WHITE);

        setSize(800, 480);
        setLocationRelativeTo(null); // ✅ center screen
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == b1) {
                String cardno = tf1.getText();
                String pin = new String(pf2.getPassword()); // ✅ correct way

                if (cardno.equals("") || pin.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter Card Number and PIN");
                    return;
                }

                Conn c1 = new Conn();

                // ✅ Secure query
                String q = "SELECT * FROM login WHERE cardno=? AND pin=?";
                PreparedStatement ps = c1.c.prepareStatement(q);
                ps.setString(1, cardno);
                ps.setString(2, pin);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN");
                }

            } else if (ae.getSource() == b2) {
                tf1.setText("");
                pf2.setText("");

            } else if (ae.getSource() == b3) {
                setVisible(false);
                new SignupOne().setVisible(true);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}