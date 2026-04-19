package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class Deposit extends JFrame implements ActionListener {

    JTextField t1;
    JButton b1, b2;
    JLabel l1;
    String pin;

    Deposit(String pin) {
        this.pin = pin;

        setLayout(null);

        // ✅ Background Image Fix
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(960, 1080, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 960, 1080);
        add(background);

        // Text
        l1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(190, 350, 400, 35);
        background.add(l1);

        // TextField
        t1 = new JTextField();
        t1.setFont(new Font("Arial", Font.BOLD, 22));
        t1.setBounds(190, 420, 320, 30);
        background.add(t1);

        // Buttons
        b1 = new JButton("DEPOSIT");
        b2 = new JButton("BACK");

        b1.setBounds(390, 588, 150, 35);
        b2.setBounds(390, 633, 150, 35);

        background.add(b1);
        background.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        setSize(960, 1080);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == b1) {

                String amount = t1.getText();

                // ✅ Validation
                if (amount.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter amount");
                    return;
                }

                if (!amount.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Enter valid numeric amount");
                    return;
                }

                Date date = new Date();

                Conn c = new Conn();

                // ✅ Secure Query
                String q = "INSERT INTO bank VALUES(?,?,?,?)";
                PreparedStatement ps = c.c.prepareStatement(q);

                ps.setString(1, pin);
                ps.setString(2, date.toString());
                ps.setString(3, "Deposit");
                ps.setString(4, amount);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "₹ " + amount + " Deposited Successfully");

                setVisible(false);
                new Transactions(pin).setVisible(true);

            } else if (ae.getSource() == b2) {
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Deposit("");
    }
}
