package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class BalanceEnquiry extends JFrame implements ActionListener {

    JButton b1;
    JLabel l1;
    String pin;

    BalanceEnquiry(String pin) {
        this.pin = pin;

        setLayout(null);

        // ✅ Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(960, 1080, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 960, 1080);
        add(background);

        // ✅ Balance Label
        l1 = new JLabel("Loading...");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(190, 350, 500, 35);
        background.add(l1);

        // ✅ Back Button
        b1 = new JButton("BACK");
        b1.setBounds(390, 633, 150, 35);
        background.add(b1);

        b1.addActionListener(this);

        // ✅ Balance Logic
        int balance = 0;

        try {
            if (pin == null || pin.equals("")) {
                throw new Exception("Invalid PIN");
            }

            Conn c = new Conn();

            String query = "SELECT * FROM bank WHERE pin=?";
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setString(1, pin);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String mode = rs.getString("mode");
                String amt = rs.getString("amount");

                if (amt != null && amt.matches("\\d+")) {
                    int amount = Integer.parseInt(amt);

                    if ("Deposit".equalsIgnoreCase(mode)) {
                        balance += amount;
                    } else {
                        balance -= amount;
                    }
                }
            }

            l1.setText("Your Current Balance is ₹ " + balance);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            l1.setText("Unable to fetch balance");
        }

        setSize(960, 1080);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transactions(pin).setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEnquiry("1234"); // ✅ test PIN
    }
}