package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    FastCash(String pin) {
        this.pin = pin;

        setLayout(null);

        // ✅ Background Fix
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(960, 1080, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 960, 1080);
        add(background);

        JLabel l1 = new JLabel("SELECT WITHDRAWAL AMOUNT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(235, 400, 400, 35);
        background.add(l1);

        // Buttons
        b1 = new JButton("Rs 100");
        b2 = new JButton("Rs 500");
        b3 = new JButton("Rs 1000");
        b4 = new JButton("Rs 2000");
        b5 = new JButton("Rs 5000");
        b6 = new JButton("Rs 10000");
        b7 = new JButton("BACK");

        JButton[] buttons = {b1,b2,b3,b4,b5,b6,b7};

        int x1 = 170, x2 = 390, y = 500;

        for (int i = 0; i < 6; i++) {
            JButton btn = buttons[i];
            if (i % 2 == 0) {
                btn.setBounds(x1, y, 150, 35);
            } else {
                btn.setBounds(x2, y, 150, 35);
                y += 45;
            }
            background.add(btn);
            btn.addActionListener(this);
        }

        b7.setBounds(390, 650, 150, 35);
        background.add(b7);
        b7.addActionListener(this);

        setSize(960, 1080);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {

        try {
            if (ae.getSource() == b7) {
                setVisible(false);
                new Transactions(pin).setVisible(true);
                return;
            }

            // ✅ Get Amount Safely
            String text = ((JButton) ae.getSource()).getText();
            String amount = text.replace("Rs ", "");

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

            // ✅ Check Balance
            if (balance < Integer.parseInt(amount)) {
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
            ps2.setString(4, amount);

            ps2.executeUpdate();

            JOptionPane.showMessageDialog(null, "₹ " + amount + " Debited Successfully");

            setVisible(false);
            new Transactions(pin).setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new FastCash("");
    }
}