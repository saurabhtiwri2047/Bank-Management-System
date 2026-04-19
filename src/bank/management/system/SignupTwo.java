package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class SignupTwo extends JFrame implements ActionListener {

    JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13;
    JButton b;
    JRadioButton r1,r2,r3,r4;
    JTextField t1,t2;
    JComboBox c1,c2,c3,c4,c5;
    String formno;

    // ✅ Correct Constructor
    SignupTwo(String formno){

        this.formno = formno;

        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 2");
        setLayout(null);

        // ✅ Image Fix
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        JLabel logo = new JLabel(new ImageIcon(i2));
        logo.setBounds(150, 0, 100, 100);
        add(logo);

        // Labels
        l1 = new JLabel("Page 2: Additional Details");
        l1.setBounds(280,30,600,40);
        add(l1);

        l2 = new JLabel("Religion:");
        l2.setBounds(100,120,150,30);
        add(l2);

        String religion[] = {"Hindu","Muslim","Sikh","Christian","Other"};
        c1 = new JComboBox(religion);
        c1.setBounds(350,120,320,30);
        add(c1);

        l3 = new JLabel("Category:");
        l3.setBounds(100,170,150,30);
        add(l3);

        String category[] = {"General","OBC","SC","ST","Other"};
        c2 = new JComboBox(category);
        c2.setBounds(350,170,320,30);
        add(c2);

        l4 = new JLabel("Income:");
        l4.setBounds(100,220,150,30);
        add(l4);

        String income[] = {"<1,50,000","<2,50,000","<5,00,000","Upto 10,00,000","Above 10,00,000"};
        c3 = new JComboBox(income);
        c3.setBounds(350,220,320,30);
        add(c3);

        l5 = new JLabel("Education:");
        l5.setBounds(100,270,150,30);
        add(l5);

        String education[] = {"Non-Graduate","Graduate","Post-Graduate","Doctorate","Others"};
        c4 = new JComboBox(education);
        c4.setBounds(350,270,320,30);
        add(c4);

        l6 = new JLabel("Occupation:");
        l6.setBounds(100,320,150,30);
        add(l6);

        String occupation[] = {"Salaried","Self-Employed","Business","Student","Retired","Others"};
        c5 = new JComboBox(occupation);
        c5.setBounds(350,320,320,30);
        add(c5);

        l7 = new JLabel("PAN:");
        l7.setBounds(100,370,150,30);
        add(l7);

        t1 = new JTextField();
        t1.setBounds(350,370,320,30);
        add(t1);

        l8 = new JLabel("Aadhar:");
        l8.setBounds(100,420,150,30);
        add(l8);

        t2 = new JTextField();
        t2.setBounds(350,420,320,30);
        add(t2);

        // Radio Buttons
        l9 = new JLabel("Senior Citizen:");
        l9.setBounds(100,470,150,30);
        add(l9);

        r1 = new JRadioButton("Yes");
        r2 = new JRadioButton("No");

        r1.setBounds(350,470,100,30);
        r2.setBounds(460,470,100,30);

        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(r1); bg1.add(r2);

        add(r1); add(r2);

        l10 = new JLabel("Existing Account:");
        l10.setBounds(100,520,180,30);
        add(l10);

        r3 = new JRadioButton("Yes");
        r4 = new JRadioButton("No");

        r3.setBounds(350,520,100,30);
        r4.setBounds(460,520,100,30);

        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(r3); bg2.add(r4);

        add(r3); add(r4);

        // Button
        b = new JButton("Next");
        b.setBounds(570,620,100,30);
        add(b);

        b.addActionListener(this);

        setSize(850,750);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){

        try {
            Conn c = new Conn();

            String q = "INSERT INTO signuptwo VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = c.c.prepareStatement(q);

            ps.setString(1, formno);
            ps.setString(2, (String)c1.getSelectedItem());
            ps.setString(3, (String)c2.getSelectedItem());
            ps.setString(4, (String)c3.getSelectedItem());
            ps.setString(5, (String)c4.getSelectedItem());
            ps.setString(6, (String)c5.getSelectedItem());
            ps.setString(7, t1.getText());
            ps.setString(8, t2.getText());
            ps.setString(9, r1.isSelected() ? "Yes" : "No");
            ps.setString(10, r3.isSelected() ? "Yes" : "No");

            ps.executeUpdate();

            new SignupThree(formno).setVisible(true);
            setVisible(false);

        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args){
        new SignupTwo("");
    }
}