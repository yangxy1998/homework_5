package frame.impl.ProgramFrameImpl;

import database.ChangeType;
import database.DatabaseHandler;
import frame.fragment.MessageFrame;
import frame.fragment.ProgramFrame;
import frame.impl.ShowFrameImpl.LocalFrame;
import main.SearchManager;
import vo.Program;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class FindFrame {
    public static void Regist(){

        JButton button=new JButton("查找信息");
        button.setSize(50,20);
        button.setVisible(true);
        button.addActionListener(new ButtonListener());
        ProgramFrame.setButton2(button);

        JTextField textField=new JTextField("",20);
        textField.setVisible(true);
        ProgramFrame.setTextField(textField);
    }

    private static class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int count=ProgramFrame.ScelectedIndex;
            if(count<0){
                MessageFrame messageFrame=new MessageFrame("您没有选择要查找的数据类型！");
            }
            else if(ProgramFrame.getText()!=null){
                DatabaseHandler databaseHandler = SearchManager.getDatabaseHandler();
                if(count==0){
                    Program program=databaseHandler.findProgram(ProgramFrame.getText());
                    if(program==null){
                        MessageFrame messageFrame = new MessageFrame("搜索失败！未找到指定id的项目。");
                    }
                    else{
                        List<Program> programs=new ArrayList<>();
                        programs.add(program);
                        LocalFrame.setPrograms(programs);
                        MessageFrame messageFrame = new MessageFrame("搜索成功！找到指定id为"+ProgramFrame.getText()+"的项目。");
                    }
                }
                else {
                    List<Program> programs = databaseHandler.findPrograms(ChangeType.setType(count + 1), ProgramFrame.getText());
                    if (programs == null) {
                        MessageFrame messageFrame = new MessageFrame("搜索失败！");
                    } else {
                        LocalFrame.setPrograms(programs);
                        MessageFrame messageFrame = new MessageFrame("搜索完成！共搜索到" + programs.size() + "条数据！");
                    }
                }
            }
            else{
                MessageFrame messageFrame=new MessageFrame("您输入了错误的数据！");
            }
        }
    }
}
