package frame.impl.ShowFrameImpl;

import database.DatabaseHandler;
import frame.FrameHandler;
import frame.fragment.MessageFrame;
import frame.fragment.ProgramFrame;
import frame.fragment.ShowFrame;
import frame.impl.ProgramFrameImpl.SearchFrame;
import main.SearchManager;
import segmenter.Segmenter;
import vo.Program;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CloudFrame {
    //云端上的项目
    public static List<Program> cloudprograms;
    //正在展示的项目
    public static List<Program> showingprograms;

    public static DefaultListModel<String> defaultListModel=new DefaultListModel<>();
    private static JList<String> list=new JList<>(defaultListModel);
    private static DatabaseHandler databaseHandler;
    private static int index;
    private static Segmenter segmenter;

    public static void Regist(){

        databaseHandler= SearchManager.getDatabaseHandler();
//        String URL="jdbc:mysql://119.27.166.115:2017/java_exp";
//        String Username="whu_iss_2017";
//        String Password="iss_java_2017";
        String URL="jdbc:mysql://localhost:3306/test";
        String Username="root";
        String Password="admin";
        databaseHandler.connectTo(URL,Username,Password);
        index=-1;
        List<JButton> buttons=new ArrayList<>();
        JButton button1=new JButton("从数据库导入项目");
        JButton button2=new JButton("模糊搜索");
        JButton button3=new JButton("按类型精确搜索");
        JButton button4=new JButton("上传项目");
        button1.addActionListener(new ButtonHandler1());
        button2.addActionListener(new ButtonHandler2());
        button3.addActionListener(new ButtonHandler3());
        button4.addActionListener(new ButtonHandler4());
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        List<JRadioButton> radioButtons=new ArrayList<>();
        radioButtons.add(new JRadioButton("中文检索"));
        radioButtons.add(new JRadioButton("英文检索"));
        for(int i=0;i<2;i++){
            radioButtons.get(i).addItemListener(new RadioButtonListener(i));
        }
        list.addMouseListener(new ListHandler());
        JTextField textField=new JTextField("",10);
        FrameHandler.setList(list);
        FrameHandler.setButtons(buttons);
        FrameHandler.setHeader("检索云数据库");
        FrameHandler.setTextField(textField);
        FrameHandler.setRadioButtons(radioButtons);

    }

    private static class ButtonHandler1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cloudprograms=databaseHandler.getPrograms();
            showingprograms=cloudprograms;
            defaultListModel.clear();
            for(Program program:showingprograms){
                defaultListModel.addElement(program.getProgramName());
            }
        }
    }


    private static class ButtonHandler2 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(cloudprograms==null){
                MessageFrame messageFrame=new MessageFrame("您还没有导入数据！");
                return;
            }
            showingprograms=segmenter.getMatchedPrograms(ShowFrame.getText(),cloudprograms,20);
            defaultListModel.clear();
            for(Program program:showingprograms){
                defaultListModel.addElement(program.getProgramName());
            }
        }
    }

    private static class ButtonHandler3 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(cloudprograms==null){
                MessageFrame messageFrame=new MessageFrame("您还没有导入数据！");
                return;
            }
            Program program=new Program();
            program.setId("按Id检索");
            program.setCountry("按国家检索");
            program.setUniversity("按大学检索");
            program.setSchool("按学院检索");
            program.setProgramName("按项目名检索");
            program.setHomepage("按主页检索");
            program.setLocation("按地址检索");
            program.setEmail("按邮箱检索");
            program.setPhoneNumber("按联系方式检索");
            program.setDegree("按学位检索");
            program.setDeadlineWithAid("按申请截止时间（奖学金）检索");
            program.setDeadlineWithoutAid("按申请截止时间（无奖学金）检索");
            ProgramFrame detailFrame=new ProgramFrame(program,3);
            if(SearchFrame.getPrograms()!=null){
                showingprograms= SearchFrame.getPrograms();
            }
            defaultListModel.clear();
            for(Program p:showingprograms){
                defaultListModel.addElement(p.getProgramName());
            }
        }
    }

    private static class ButtonHandler4 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            databaseHandler.addPrograms(ParseFrame.getPrograms());
        }
    }

    private static class ListHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JList thelist=(JList) e.getSource();
            int count=thelist.getSelectedIndex();
            if(e.getClickCount()==2){
                ProgramFrame detailFrame=new ProgramFrame(CloudFrame.getShowingprograms().get(count),0);
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
        CloudFrame.showingprograms = programs;
    }

    public static void setShowingprograms(List<Program> showingprograms) {
        CloudFrame.showingprograms = showingprograms;
    }

    public static List<Program> getShowingprograms() {
        return showingprograms;
    }

    public static DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    private static class RadioButtonListener implements ItemListener{

        int type;

        RadioButtonListener(int i){
            type=i;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            segmenter=SearchManager.getSegmenter(type);
        }
    }

    public static Segmenter getSegmenter() {
        return segmenter;
    }
}
