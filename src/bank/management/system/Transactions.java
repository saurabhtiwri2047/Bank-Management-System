package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Transactions extends JFrame implements ActionListener {

    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    Transactions(String pin) {
        this.pin = pin;

        setLayout(null);

        // ✅ Background Fix
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(960, 1080, Image.SCALE_DEFAULT);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 960, 1080);
        add(background);

        JLabel l1 = new JLabel("Please Select Your Transaction");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(235, 400, 400, 35);
        background.add(l1);

        // Buttons
        b1 = new JButton("DEPOSIT");
        b2 = new JButton("WITHDRAW");
        b3 = new JButton("FAST CASH");
        b4 = new JButton("MINI STATEMENT");
        b5 = new JButton("PIN CHANGE");
        b6 = new JButton("BALANCE ENQUIRY");
        b7 = new JButton("EXIT");

        JButton[] btns = {b1,b2,b3,b4,b5,b6,b7};

        int x1 = 170, x2 = 390, y = 500;

        for (int i = 0; i < 6; i++) {
            JButton btn = btns[i];
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

        if (ae.getSource() == b1) {
            setVisible(false);
            new Deposit(pin).setVisible(true);

        } else if (ae.getSource() == b2) {
            setVisible(false);
            new Withdraw(pin).setVisible(true);  // ✅ FIXED

        } else if (ae.getSource() == b3) {
            setVisible(false);
            new FastCash(pin).setVisible(true);

        } else if (ae.getSource() == b4) {
            new MiniStatement(pin).setVisible(true);

        } else if (ae.getSource() == b5) {
            setVisible(false);
            new PinChange(pin).setVisible(true); // ✅ FIXED

        } else if (ae.getSource() == b6) {
            setVisible(false);
            new BalanceEnquiry(pin).setVisible(true);

        } else if (ae.getSource() == b7) {

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to exit?",
                    "Exit",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        new Transactions("");
    }
}