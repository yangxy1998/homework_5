package frame.fragment;

import frame.impl.ProgramFrameImpl.FindFrame;
import frame.impl.ProgramFrameImpl.ModifyFrame;
import frame.impl.ProgramFrameImpl.SearchFrame;
import frame.impl.ShowFrameImpl.CloudFrame;
import frame.impl.ShowFrameImpl.LocalFrame;
import vo.Program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ProgramFrame extends JFrame{

    private DefaultListModel<String> defaultListModel;
    private JList<String> list;
    private JScrollPane scrollPane;
    private JButton button1;
    private static JTextField textField;
    private static JButton button2;
    public static Program program;
    public static int ScelectedIndex;

    /**
     * 构造方法
     * @param p
     * 传入一个项目
     * @param type
     * 0为只读模式
     * 1为修改模式
     * 2为查找模式
     * 3为检索模式
     */


    public ProgramFrame(Program p,int type){
        super(p.getProgramName());
        switch (type){
            case 1:
                Clear();
                ModifyFrame.Regist();
                break;
            case 2:
                Clear();
                FindFrame.Regist();
                break;
            case 3:
                Clear();
                SearchFrame.Regist();
                break;
            default:
                Clear();
        }
        ScelectedIndex=-1;
        setLayout(new FlowLayout());
        this.setSize(1280,300);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        program=p;
        defaultListModel=new DefaultListModel<>();
        defaultListModel.addElement("Id:"+program.getId());
        defaultListModel.addElement("国家："+program.getCountry());
        defaultListModel.addElement("大学："+program.getUniversity());
        defaultListModel.addElement("学院："+program.getSchool());
        defaultListModel.addElement("项目名称："+program.getProgramName());
        defaultListModel.addElement("项目主页："+program.getHomepage());
        defaultListModel.addElement("地址："+program.getLocation());
        defaultListModel.addElement("项目申请资讯邮箱："+program.getEmail());
        defaultListModel.addElement("联系方式："+program.getPhoneNumber());
        defaultListModel.addElement("学位："+program.getDegree());
        defaultListModel.addElement("申请截止时间（奖学金）："+program.getDeadlineWithAid());
        defaultListModel.addElement("申请截止时间（无奖学金）："+program.getDeadlineWithoutAid());
        list=new JList<>(defaultListModel);
        list.addMouseListener(new ListHandler());
        scrollPane=new JScrollPane(list);
        scrollPane.setVisible(true);
        scrollPane.setSize(1200,300);
        add(scrollPane);
        button1=new JButton("退出");
        button1.setVisible(true);
        button1.setSize(50,20);
        button1.addActionListener(new ButtonHandler1());
        add(button1);
        button2 = getButton2();
        textField=getTextField();
        if(textField!=null){
            add(textField);
        }
        if(button2!=null)add(button2);
    }


    /**
     * 给退出按钮的手柄
     */


    private class ButtonHandler1 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            LocalFrame.defaultListModel.clear();
            CloudFrame.defaultListModel.clear();
            if(LocalFrame.programs!=null) {
                for (Program program : LocalFrame.programs) {
                    LocalFrame.defaultListModel.addElement(program.getProgramName());
                }
            }
            if(SearchFrame.getPrograms()!=null) {
                CloudFrame.setShowingprograms(SearchFrame.getPrograms());
                for (Program program : SearchFrame.getPrograms()) {
                    CloudFrame.defaultListModel.addElement(program.getProgramName());
                }
            }
        }
    }

    private class ListHandler implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            JList thelist=(JList) e.getSource();
            int count=thelist.getSelectedIndex();
            ScelectedIndex=count;
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public static JTextField getTextField() {
        return textField;
    }

    public static void setTextField(JTextField textField) {
        ProgramFrame.textField = textField;
    }

    public static String getText(){
        return textField.getText();
    }

    public static JButton getButton2() {
        return button2;
    }

    public static void setButton2(JButton button2) {
        ProgramFrame.button2 = button2;
    }

    private static void Clear(){
        setTextField(null);
        setButton2(null);
    }
}
