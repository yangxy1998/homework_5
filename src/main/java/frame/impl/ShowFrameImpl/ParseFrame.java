package frame.impl.ShowFrameImpl;

import database.DatabaseHandler;
import frame.FrameHandler;
import frame.fragment.MessageFrame;
import frame.fragment.ProgramFrame;
import main.SearchManager;
import search.Parser;
import search.WebSpider;
import vo.Program;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class ParseFrame {

    private static List<Program> programs;
    private static DefaultListModel<String> defaultListModel=new DefaultListModel<>();
    private static JList<String> list=new JList<>(defaultListModel);
    public static void Regist(){

        List<JButton> buttons=new ArrayList<>();
        JButton button1=new JButton("爬取信息");
        JButton button2=new JButton("保存到本地数据库");
        button1.addActionListener(new ButtonHandler1());
        button2.addActionListener(new ButtonHandler2());
        buttons.add(button1);
        buttons.add(button2);
        list.addMouseListener(new ListHandler());
        FrameHandler.setList(list);
        FrameHandler.setButtons(buttons);
        FrameHandler.setHeader("爬取网页信息");
        FrameHandler.setTextField(null);

    }

    private static class ButtonHandler1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<WebSpider> webSpiders = SearchManager.getWebSpider();
            List<Program> programs=new ArrayList<>();
            for (WebSpider webSpider : webSpiders) {
                Parser parser = webSpider.getParser();
                List<String> pages = webSpider.getHtmlFromWeb();
                for (String page : pages) {
                    programs.add(parser.parseHtml(page));
                }
            }
            ParseFrame.programs=programs;
            for(Program program:programs){
                defaultListModel.addElement(program.getProgramName());
            }
            FrameHandler.setList(list);
        }
    }


    private static class ButtonHandler2 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(programs==null){
                MessageFrame messageFrame=new MessageFrame("您还没有爬取信息！");
                return;
            }
            DatabaseHandler databaseHandler=SearchManager.getDatabaseHandler();
            String URL="jdbc:mysql://localhost:3306/test";
            String Username="root";
            String Password="admin";
            databaseHandler.connectTo(URL,Username,Password);
            databaseHandler.addPrograms(programs);
        }
    }

    private static class ListHandler implements MouseListener{

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

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public static List<Program> getPrograms() {
        return programs;
    }
}
