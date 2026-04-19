package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class MiniStatement extends JFrame implements ActionListener {

    JButton b1;
    JLabel l1;

    MiniStatement(String pin) {
        super("Mini Statement");

        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Bank Name
        JLabel l2 = new JLabel("Indian Bank");
        l2.setBounds(150, 20, 200, 20);
        add(l2);

        // Card Info
        JLabel l3 = new JLabel();
        l3.setBounds(20, 80, 350, 20);
        add(l3);

        // Transactions Label
        l1 = new JLabel();
        l1.setVerticalAlignment(JLabel.TOP);

        JScrollPane scroll = new JScrollPane(l1);
        scroll.setBounds(20, 120, 350, 250);
        add(scroll);

        // Balance Label
        JLabel l4 = new JLabel();
        l4.setBounds(20, 400, 350, 20);
        add(l4);

        // Exit Button
        b1 = new JButton("Exit");
        b1.setBounds(150, 450, 100, 30);
        add(b1);

        b1.addActionListener(this);

        try {
            Conn c = new Conn();

            // ✅ Get Card Number
            String q1 = "SELECT * FROM login WHERE pin=?";
            PreparedStatement ps1 = c.c.prepareStatement(q1);
            ps1.setString(1, pin);

            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                String card = rs1.getString("cardno");
                l3.setText("Card Number: " + card.substring(0, 4) + "XXXXXXXX" + card.substring(12));
            }

            // ✅ Get Transactions
            int balance = 0;
            StringBuilder html = new StringBuilder("<html>");

            String q2 = "SELECT * FROM bank WHERE pin=?";
            PreparedStatement ps2 = c.c.prepareStatement(q2);
            ps2.setString(1, pin);

            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                String date = rs2.getString("date");
                String mode = rs2.getString("mode");
                String amount = rs2.getString("amount");

                html.append(date)
                    .append(" &nbsp;&nbsp; ")
                    .append(mode)
                    .append(" &nbsp;&nbsp; ₹")
                    .append(amount)
                    .append("<br><br>");

                if (mode.equalsIgnoreCase("Deposit")) {
                    balance += Integer.parseInt(amount);
                } else {
                    balance -= Integer.parseInt(amount);
                }
            }

            html.append("</html>");
            l1.setText(html.toString());

            l4.setText("Total Balance: ₹ " + balance);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        setSize(400, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new MiniStatement("");
    }
}
