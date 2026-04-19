package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class PinChange extends JFrame implements ActionListener {

    JPasswordField t1, t2;
    JButton b1, b2;
    String pin;

    PinChange(String pin) {
        this.pin = pin;

        setLayout(null);

        // ✅ Background Fix
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(960, 1080, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 960, 1080);
        add(background);

        JLabel l1 = new JLabel("CHANGE YOUR PIN");
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setForeground(Color.WHITE);
        l1.setBounds(280, 330, 300, 35);
        background.add(l1);

        JLabel l2 = new JLabel("New PIN:");
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setForeground(Color.WHITE);
        l2.setBounds(180, 390, 150, 35);
        background.add(l2);

        JLabel l3 = new JLabel("Re-Enter PIN:");
        l3.setFont(new Font("System", Font.BOLD, 16));
        l3.setForeground(Color.WHITE);
        l3.setBounds(180, 440, 200, 35);
        background.add(l3);

        t1 = new JPasswordField();
        t1.setBounds(350, 390, 180, 25);
        background.add(t1);

        t2 = new JPasswordField();
        t2.setBounds(350, 440, 180, 25);
        background.add(t2);

        b1 = new JButton("CHANGE");
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
            String npin = new String(t1.getPassword());
            String rpin = new String(t2.getPassword());

            // ✅ Validation
            if (npin.equals("") || rpin.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all fields");
                return;
            }

            if (!npin.equals(rpin)) {
                JOptionPane.showMessageDialog(null, "PIN does not match");
                return;
            }

            if (npin.length() != 4 || !npin.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "PIN must be 4 digits");
                return;
            }

            if (ae.getSource() == b1) {

                Conn c = new Conn();

                // ✅ Secure Queries
                String q1 = "UPDATE bank SET pin=? WHERE pin=?";
                String q2 = "UPDATE login SET pin=? WHERE pin=?";
                String q3 = "UPDATE signupthree SET pin=? WHERE pin=?";

                PreparedStatement ps1 = c.c.prepareStatement(q1);
                PreparedStatement ps2 = c.c.prepareStatement(q2);
                PreparedStatement ps3 = c.c.prepareStatement(q3);

                ps1.setString(1, rpin);
                ps1.setString(2, pin);

                ps2.setString(1, rpin);
                ps2.setString(2, pin);

                ps3.setString(1, rpin);
                ps3.setString(2, pin);

                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();

                JOptionPane.showMessageDialog(null, "PIN Changed Successfully");

                setVisible(false);
                new Transactions(rpin).setVisible(true);

            } else if (ae.getSource() == b2) {
                setVisible(false);
                new Transactions(pin).setVisible(true);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new PinChange("");
    }
}
