package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;

public class SignupOne extends JFrame implements ActionListener {

    JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12;
    JTextField t1,t2,t3,t4,t5,t6,t7;
    JRadioButton r1,r2,r3,r4,r5;
    JButton b;
    JDateChooser dateChooser;

    Random ran = new Random();
    long first4 = Math.abs((ran.nextLong() % 9000L) + 1000L);
    String first = String.valueOf(first4);

    SignupOne() {

        setTitle("NEW ACCOUNT APPLICATION FORM");
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        JLabel logo = new JLabel(new ImageIcon(i2));
        logo.setBounds(20,0,100,100);
        add(logo);

        l1 = new JLabel("APPLICATION FORM NO. " + first);
        l1.setFont(new Font("Arial", Font.BOLD, 30));
        l1.setBounds(140,20,600,40);
        add(l1);

        l2 = new JLabel("Page 1: Personal Details");
        l2.setBounds(290,80,400,30);
        add(l2);

        l3 = new JLabel("Name:");
        l3.setBounds(100,140,200,30);
        add(l3);

        t1 = new JTextField();
        t1.setBounds(300,140,400,30);
        add(t1);

        l4 = new JLabel("Father's Name:");
        l4.setBounds(100,190,200,30);
        add(l4);

        t2 = new JTextField();
        t2.setBounds(300,190,400,30);
        add(t2);

        l5 = new JLabel("Date of Birth:");
        l5.setBounds(100,240,200,30);
        add(l5);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(300,240,400,30);
        add(dateChooser);

        l6 = new JLabel("Gender:");
        l6.setBounds(100,290,200,30);
        add(l6);

        r1 = new JRadioButton("Male");
        r2 = new JRadioButton("Female");

        r1.setBounds(300,290,100,30);
        r2.setBounds(450,290,100,30);

        ButtonGroup bg = new ButtonGroup();
        bg.add(r1); bg.add(r2);

        add(r1); add(r2);

        l7 = new JLabel("Email:");
        l7.setBounds(100,340,200,30);
        add(l7);

        t3 = new JTextField();
        t3.setBounds(300,340,400,30);
        add(t3);

        l8 = new JLabel("Marital Status:");
        l8.setBounds(100,390,200,30);
        add(l8);

        r3 = new JRadioButton("Married");
        r4 = new JRadioButton("Unmarried");
        r5 = new JRadioButton("Other");

        r3.setBounds(300,390,100,30);
        r4.setBounds(450,390,120,30);
        r5.setBounds(600,390,100,30);

        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(r3); bg2.add(r4); bg2.add(r5);

        add(r3); add(r4); add(r5);

        l9 = new JLabel("Address:");
        l9.setBounds(100,440,200,30);
        add(l9);

        t4 = new JTextField();
        t4.setBounds(300,440,400,30);
        add(t4);

        l10 = new JLabel("City:");
        l10.setBounds(100,490,200,30);
        add(l10);

        t5 = new JTextField();
        t5.setBounds(300,490,400,30);
        add(t5);

        l11 = new JLabel("Pin Code:");
        l11.setBounds(100,540,200,30);
        add(l11);

        t6 = new JTextField();
        t6.setBounds(300,540,400,30);
        add(t6);

        l12 = new JLabel("State:");
        l12.setBounds(100,590,200,30);
        add(l12);

        t7 = new JTextField();
        t7.setBounds(300,590,400,30);
        add(t7);

        b = new JButton("Next");
        b.setBounds(620,660,80,30);
        add(b);

        b.addActionListener(this);

        setSize(850,800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {

        String name = t1.getText();
        String fname = t2.getText();

        String dob = "";
        if (dateChooser.getDate() != null) {
            dob = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
        }

        String gender = r1.isSelected() ? "Male" : r2.isSelected() ? "Female" : null;
        String marital = r3.isSelected() ? "Married" : r4.isSelected() ? "Unmarried" : r5.isSelected() ? "Other" : null;

        try {
            if(name.equals("") || fname.equals("") || dob.equals("") || gender == null || marital == null){
                JOptionPane.showMessageDialog(null, "Please fill all required fields");
                return;
            }

            Conn c = new Conn();

            if (c.c == null) {
                JOptionPane.showMessageDialog(null, "Database not connected!");
                return;
            }

            // ✅ FIXED QUERY (father_name column used)
            String q = "INSERT INTO signup (formno, name, father_name, dob, gender, email, marital, address, city, pincode, state) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = c.c.prepareStatement(q);

            ps.setString(1, first);
            ps.setString(2, name);
            ps.setString(3, fname);   // variable name can be fname
            ps.setString(4, dob);
            ps.setString(5, gender);
            ps.setString(6, t3.getText());
            ps.setString(7, marital);
            ps.setString(8, t4.getText());
            ps.setString(9, t5.getText());
            ps.setString(10, t6.getText());
            ps.setString(11, t7.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Inserted Successfully ✅");

            new SignupTwo(first).setVisible(true);
            setVisible(false);

        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args){
        new SignupOne();
    }
}