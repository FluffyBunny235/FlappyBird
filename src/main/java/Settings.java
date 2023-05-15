import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings implements ActionListener, ChangeListener {
    JFrame frame;
    JPanel panel1, panel2, panel3;
    JPanel panelHolder;
    JPanel panelLast;
    JButton apply;
    JButton easyMode;
    JButton cancel;
    JButton reset;
    JLabel acc, accSetTo;
    JSlider acceleration;
    JLabel vel, velSetTo;
    JSlider terminalVelocity;
    JLabel bonus, bonusSetTo;
    JSlider doubleJumpBonus;
    public Settings() {
        frame = new JFrame("Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelHolder = new JPanel();
        panelHolder.setLayout(new BoxLayout(panelHolder, BoxLayout.Y_AXIS));
        frame.add(panelHolder);

        panel1 = new JPanel();
        acc = new JLabel("Acceleration x 100");
        panel1.add(acc, FlowLayout.LEFT);
        acceleration = new JSlider(10, 100, 75);
        acceleration.addChangeListener(this);
        panel1.add(acceleration, FlowLayout.CENTER);
        accSetTo = new JLabel("Set To: 75");
        panel1.add(accSetTo, FlowLayout.RIGHT);
        panelHolder.add(panel1);

        panel2 = new JPanel();
        vel = new JLabel("Terminal Velocity");
        panel2.add(vel, FlowLayout.LEFT);
        terminalVelocity = new JSlider(1, 50, 30);
        terminalVelocity.addChangeListener(this);
        panel2.add(terminalVelocity, FlowLayout.CENTER);
        velSetTo = new JLabel("Set To: 30");
        panel2.add(velSetTo, FlowLayout.RIGHT);
        panelHolder.add(panel2);

        panel3 = new JPanel();
        bonus = new JLabel("Double Jump Bonus");
        panel3.add(bonus, FlowLayout.LEFT);
        doubleJumpBonus = new JSlider(1, 10, 5);
        doubleJumpBonus.addChangeListener(this);
        panel3.add(doubleJumpBonus, FlowLayout.CENTER);
        bonusSetTo = new JLabel("Set To: 5");
        panel3.add(bonusSetTo, FlowLayout.RIGHT);
        panelHolder.add(panel3);


        panelLast = new JPanel();
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        panelLast.add(cancel, FlowLayout.LEFT);
        reset = new JButton("Reset");
        reset.addActionListener(this);
        panelLast.add(reset, FlowLayout.CENTER);
        easyMode = new JButton("Easy Mode");
        easyMode.addActionListener(this);
        panelLast.add(easyMode);
        apply = new JButton("Apply");
        apply.addActionListener(this);
        panelLast.add(apply, FlowLayout.RIGHT);
        panelHolder.add(panelLast);

        frame.setSize(400, 200);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Apply")) {
            Main.tv = terminalVelocity.getValue();
            Main.b.setTerminalVelocity(terminalVelocity.getValue());
            Main.bonus = doubleJumpBonus.getValue();
            Main.b.setDoubleJumpBonus(doubleJumpBonus.getValue());
            Main.acceleration = (double)(acceleration.getValue())/100;
            Main.b.setAcceleration((double)(acceleration.getValue())/100);
            frame.setVisible(false);
            Main.d.frame.setVisible(true);
        }
        if (e.getActionCommand().equals("Reset")) {
            terminalVelocity.setValue(30);
            doubleJumpBonus.setValue(5);
            acceleration.setValue(75);
        }
        if (e.getActionCommand().equals("Cancel")) {
            terminalVelocity.setValue(Main.tv);
            doubleJumpBonus.setValue(Main.bonus);
            acceleration.setValue((int)(Main.acceleration*100));
            frame.setVisible(false);
            Main.d.frame.setVisible(true);
        }
        if (e.getActionCommand().equals("Easy Mode")) {
            terminalVelocity.setValue(25);
            doubleJumpBonus.setValue(10);
            acceleration.setValue(100);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == acceleration) {
            accSetTo.setText("Set To: " + acceleration.getValue());
        }
        if (e.getSource() == terminalVelocity) {
            velSetTo.setText("Set To: " + terminalVelocity.getValue());
        }
        if (e.getSource() == doubleJumpBonus) {
            bonusSetTo.setText("Set To: " + doubleJumpBonus.getValue());
        }
    }
}
