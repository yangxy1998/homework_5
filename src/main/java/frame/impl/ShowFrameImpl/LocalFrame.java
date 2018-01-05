package frame.impl.ShowFrameImpl;

import database.DatabaseHandler;
import frame.FrameHandler;
import frame.fragment.MessageFrame;
import frame.fragment.ProgramFrame;
import main.SearchManager;
import vo.Program;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class LocalFrame {

    public static List<Program> programs;
    public static DefaultListModel<String> defaultListModel=new DefaultListModel<>();
    private static JList<String> list=new JList<>(defaultListModel);
    private static DatabaseHandler databaseHandler;
    private static int index;

    public static void Regist(){

        databaseHandler=SearchManager.getDatabaseHandler();
        String URL="jdbc:mysql://localhost:3306/test";
        String Username="root";
        String Password="admin";
        databaseHandler.connectTo(URL,Username,Password);
        index=-1;
        List<JButton> buttons=new ArrayList<>();
        JButton button1=new JButton("从数据库导入项目");
        JButton button2=new JButton("修改指定项目");
        JButton button3=new JButton("查找指定项目");
        JButton button4=new JButton("删除指定项目");
        button1.addActionListener(new ButtonHandler1());
        button2.addActionListener(new ButtonHandler2());
        button3.addActionListener(new ButtonHandler3());
        button4.addActionListener(new ButtonHandler4());
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        list.addMouseListener(new ListHandler());
        FrameHandler.setList(list);
        FrameHandler.setButtons(buttons);
        FrameHandler.setHeader("查看本地数据库");
        FrameHandler.setTextField(null);

    }

    private static class ButtonHandler1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            programs=databaseHandler.getPrograms();
            defaultListModel.clear();
            for(Program program:programs){
                defaultListModel.addElement(program.getProgramName());
            }
        }
    }


    private static class ButtonHandler2 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if(index<0){
                MessageFrame messageFrame=new MessageFrame("您还没有选择数据！");
            }

            else{
                ProgramFrame detailFrame=new ProgramFrame(programs.get(index),1);
                defaultListModel.clear();
                programs=databaseHandler.getPrograms();
                for(Program program:programs){
                    defaultListModel.addElement(program.getProgramName());
                }
            }
        }
    }

    private static class ButtonHandler3 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(programs==null){
                MessageFrame messageFrame=new MessageFrame("您还没有导入数据！");
                return;
            }
            Program program=new Program();
            program.setId("按Id查找");
            program.setCountry("按国家查找");
            program.setUniversity("按大学查找");
            program.setSchool("按学院查找");
            program.setProgramName("按项目名查找");
            program.setHomepage("按主页查找");
            program.setLocation("按地址查找");
            program.setEmail("按邮箱查找");
            program.setPhoneNumber("按联系方式查找");
            program.setDegree("按学位查找");
            program.setDeadlineWithAid("按申请截止时间（奖学金）查找");
            program.setDeadlineWithoutAid("按申请截止时间（无奖学金）查找");
            ProgramFrame detailFrame=new ProgramFrame(program,2);
            programs=databaseHandler.getPrograms();
            defaultListModel.clear();
            for(Program p:programs){
                defaultListModel.addElement(p.getProgramName());
            }
        }
    }

    private static class ButtonHandler4 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(programs==null){
                MessageFrame messageFrame=new MessageFrame("您还没有导入数据！");
                return;
            }
            if(index<0){
                MessageFrame messageFrame=new MessageFrame("您还没有选择数据！");
            }
            else{
                databaseHandler.deleteProgram(programs.get(index).getId());
                MessageFrame messageFrame=new MessageFrame("已删除！");
                programs=databaseHandler.getPrograms();
                defaultListModel.clear();
                for(Program program:programs){
                    defaultListModel.addElement(program.getProgramName());
                }
            }
        }
    }

    private static class ListHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JList thelist=(JList) e.getSource();
            int count=thelist.getSelectedIndex();
            if(e.getClickCount()==2){
                ProgramFrame detailFrame=new ProgramFrame(programs.get(count),0);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JList thelist=(JList) e.getSource();
            int count=thelist.getSelectedIndex();
            index=count;
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public static void setPrograms(List<Program> programs) {
        LocalFrame.programs = programs;
    }

    public static DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

}
