package frame;

import frame.fragment.ShowFrame;
import frame.impl.ShowFrameImpl.CloudFrame;
import frame.impl.ShowFrameImpl.LocalFrame;
import frame.impl.ShowFrameImpl.ParseFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame{
    List<JButton> buttons;
    public MainFrame(){
        super("数据检索工具");
        setLayout(new FlowLayout());
        setSize(500,100);
        setVisible(true);
        JButton button1=new JButton("爬取网页信息");
        JButton button2=new JButton("查看本地数据库");
        JButton button3=new JButton("检索云数据库");
        button1.addActionListener(new ButtonListener(0));
        button2.addActionListener(new ButtonListener(1));
        button3.addActionListener(new ButtonListener(2));
        button1.setVisible(true);
        button2.setVisible(true);
        button3.setVisible(true);
        add(button1);
        add(button2);
        add(button3);
    }

    private class ButtonListener implements ActionListener{

        int type;
        ButtonListener(int i){
            type=i;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (type){
                case 0:
                    ShowFrame.Clear();//归零
                    ParseFrame.Regist();
                    break;
                case 1:
                    ShowFrame.Clear();//归零
                    LocalFrame.Regist();
                    break;
                case 2:
                    ShowFrame.Clear();//归零
                    CloudFrame.Regist();
                    break;
                default:
                    ShowFrame.Clear();//归零
            }
            ShowFrame showFrame=new ShowFrame();
            showFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }
}

