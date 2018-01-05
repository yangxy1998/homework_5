package frame.fragment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageFrame extends JFrame{
    JLabel label;
    JButton button;

    public MessageFrame(String text){
        setLayout(new FlowLayout());
        this.setSize(200,100);
        this.setVisible(true);
        button=new JButton("确定");
        button.addActionListener(new Handler());
        label=new JLabel(text);
        add(label);
        add(button);
    }


    private class Handler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
