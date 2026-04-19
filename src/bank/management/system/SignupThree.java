package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class SignupThree extends JFrame implements ActionListener {

    JRadioButton r1,r2,r3,r4;
    JButton b1,b2;
    JCheckBox c1,c2,c3,c4,c5,c6,c7;
    String formno;

    SignupThree(String formno){

        this.formno = formno;

        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 3");
        setLayout(null);

        // ✅ Image Fix
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        JLabel logo = new JLabel(new ImageIcon(i2));
        logo.setBounds(150, 0, 100, 100);
        add(logo);

        JLabel l1 = new JLabel("Page 3: Account Details");
        l1.setBounds(280,40,400,40);
        add(l1);

        JLabel l2 = new JLabel("Account Type:");
        l2.setBounds(100,140,200,30);
        add(l2);

        // Account type
        r1 = new JRadioButton("Saving Account");
        r2 = new JRadioButton("Fixed Deposit Account");
        r3 = new JRadioButton("Current Account");
        r4 = new JRadioButton("Recurring Deposit Account");

        r1.setBounds(100,180,200,30);
        r2.setBounds(350,180,250,30);
        r3.setBounds(100,220,250,30);
        r4.setBounds(350,220,250,30);

        ButtonGroup bg = new ButtonGroup();
        bg.add(r1); bg.add(r2); bg.add(r3); bg.add(r4);

        add(r1); add(r2); add(r3); add(r4);

        // Services
        JLabel l10 = new JLabel("Services Required:");
        l10.setBounds(100,300,200,30);
        add(l10);

        c1 = new JCheckBox("ATM CARD");
        c2 = new JCheckBox("Internet Banking");
        c3 = new JCheckBox("Mobile Banking");
        c4 = new JCheckBox("EMAIL Alerts");
        c5 = new JCheckBox("Cheque Book");
        c6 = new JCheckBox("E-Statement");

        JCheckBox[] services = {c1,c2,c3,c4,c5,c6};

        int y = 350;
        for(JCheckBox c : services){
            c.setBounds(100,y,250,30);
            c.setBackground(Color.WHITE);
            add(c);
            y += 40;
        }

        // Declaration
        c7 = new JCheckBox("I declare that the details are correct", true);
        c7.setBounds(100,600,400,30);
        add(c7);

        // Buttons
        b1 = new JButton("Submit");
        b2 = new JButton("Cancel");

        b1.setBounds(250,650,100,30);
        b2.setBounds(400,650,100,30);

        add(b1); add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        setSize(850,800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){

        if(ae.getSource() == b2){
            System.exit(0);
        }

        String atype = null;

        if(r1.isSelected()) atype = "Saving Account";
        else if(r2.isSelected()) atype = "Fixed Deposit Account";
        else if(r3.isSelected()) atype = "Current Account";
        else if(r4.isSelected()) atype = "Recurring Deposit Account";

        // ✅ Validation
        if(atype == null || !c7.isSelected()){
            JOptionPane.showMessageDialog(null, "Please select account type and accept declaration");
            return;
        }

        Random ran = new Random();

        // ✅ Proper Card + PIN
        String cardno = "504093" + (Math.abs(ran.nextLong()) % 1000000000000L);
        String pin = String.format("%04d", ran.nextInt(10000));

        String facility = "";
        if(c1.isSelected()) facility += " ATM Card";
        if(c2.isSelected()) facility += " Internet Banking";
        if(c3.isSelected()) facility += " Mobile Banking";
        if(c4.isSelected()) facility += " Email Alerts";
        if(c5.isSelected()) facility += " Cheque Book";
        if(c6.isSelected()) facility += " E-Statement";

        try{
            Conn c = new Conn();

            // ✅ PreparedStatement
            String q1 = "INSERT INTO signupThree VALUES(?,?,?,?,?)";
            PreparedStatement ps1 = c.c.prepareStatement(q1);

            ps1.setString(1, formno);
            ps1.setString(2, atype);
            ps1.setString(3, cardno);
            ps1.setString(4, pin);
            ps1.setString(5, facility);

            ps1.executeUpdate();

            String q2 = "INSERT INTO login VALUES(?,?,?)";
            PreparedStatement ps2 = c.c.prepareStatement(q2);

            ps2.setString(1, formno);
            ps2.setString(2, cardno);
            ps2.setString(3, pin);

            ps2.executeUpdate();

            JOptionPane.showMessageDialog(null, "Card Number: " + cardno + "\nPIN: " + pin);

            new Deposit(pin).setVisible(true);
            setVisible(false);

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args){
        new SignupThree("");
    }
}


