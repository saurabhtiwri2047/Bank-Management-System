package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Date;
import java.sql.*;

public class Withdraw extends JFrame implements ActionListener {

    JTextField t1;
    JButton b1, b2;
    String pin;

    Withdraw(String pin) {
        this.pin = pin;

        setLayout(null);

        // ✅ Background Fix
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(960, 1080, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 960, 1080);
        add(background);

        JLabel l1 = new JLabel("MAXIMUM WITHDRAWAL IS ₹10,000");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(190, 350, 400, 20);
        background.add(l1);

        JLabel l2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setBounds(190, 400, 400, 20);
        background.add(l2);

        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));
        t1.setBounds(190, 450, 330, 30);
        background.add(t1);

        b1 = new JButton("WITHDRAW");
        b1.setBounds(390, 588, 150, 35);
        background.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(390, 633, 150, 35);
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
            if (ae.getSource() == b2) {
                setVisible(false);
                new Transactions(pin).setVisible(true);
                return;
            }

            String amountText = t1.getText();

            // ✅ Validation
            if (amountText.equals("")) {
                JOptionPane.showMessageDialog(null, "Enter amount");
                return;
            }

            if (!amountText.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Enter valid number");
                return;
            }

            int amount = Integer.parseInt(amountText);

            if (amount > 10000) {
                JOptionPane.showMessageDialog(null, "Max limit is ₹10,000");
                return;
            }

            Conn c = new Conn();

            // ✅ Get Balance
            int balance = 0;
            String q1 = "SELECT * FROM bank WHERE pin=?";
            PreparedStatement ps1 = c.c.prepareStatement(q1);
            ps1.setString(1, pin);

            ResultSet rs = ps1.executeQuery();

            while (rs.next()) {
                if (rs.getString("mode").equalsIgnoreCase("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }

            if (balance < amount) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance");
                return;
            }

            // ✅ Insert Withdrawal
            Date date = new Date();

            String q2 = "INSERT INTO bank VALUES(?,?,?,?)";
            PreparedStatement ps2 = c.c.prepareStatement(q2);

            ps2.setString(1, pin);
            ps2.setString(2, date.toString());
            ps2.setString(3, "Withdrawal");
            ps2.setString(4, String.valueOf(amount));

            ps2.executeUpdate();

            JOptionPane.showMessageDialog(null, "₹ " + amount + " Debited Successfully");

            setVisible(false);
            new Transactions(pin).setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Withdraw("");
    }
}