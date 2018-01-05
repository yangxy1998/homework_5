package frame.impl.ProgramFrameImpl;

import database.ChangeType;
import database.DatabaseHandler;
import frame.fragment.MessageFrame;
import frame.fragment.ProgramFrame;
import frame.impl.ShowFrameImpl.LocalFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyFrame {

    public static void Regist(){
        JButton button=new JButton("确定修改");
        button.setSize(50,20);
        button.setVisible(true);
        button.addActionListener(new ButtonListener());
        ProgramFrame.setButton2(button);

        JTextField textField=new JTextField("",20);
        textField.setVisible(true);
        ProgramFrame.setTextField(textField);
    }
    private static class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int count=ProgramFrame.ScelectedIndex;
            if(count==0){
                MessageFrame messageFrame=new MessageFrame("唯一Id不可被修改！");
            }
            else if(count<0){
                MessageFrame messageFrame=new MessageFrame("您没有选择要修改的数据！");
            }
            else {
                if (ProgramFrame.getText() != null) {
                    DatabaseHandler databaseHandler = LocalFrame.getDatabaseHandler();
                    databaseHandler.modifyProgram(ProgramFrame.program, ChangeType.setType(count + 1), ProgramFrame.getText());
                    MessageFrame messageFrame=new MessageFrame("您的修改已提交！");
                    LocalFrame.programs=databaseHandler.getPrograms();
                } else {
                    MessageFrame messageFrame=new MessageFrame("您输入了错误的数据！");
                }
            }
        }
    }
}
