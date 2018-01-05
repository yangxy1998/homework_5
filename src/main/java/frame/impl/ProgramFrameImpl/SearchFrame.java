package frame.impl.ProgramFrameImpl;

import frame.fragment.MessageFrame;
import frame.fragment.ProgramFrame;
import frame.impl.ShowFrameImpl.CloudFrame;
import segmenter.Segmenter;
import vo.Program;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchFrame {

    private static List<Program> programs;

    public static void Regist(){

        JButton button=new JButton("检索信息");
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
            int element=ProgramFrame.ScelectedIndex;
            if(element<0){
                MessageFrame messageFrame=new MessageFrame("您没有选择要查找的数据类型！");
            }
            else if(ProgramFrame.getText()!=null){
                Segmenter segmenter= CloudFrame.getSegmenter();

                programs=segmenter.getMatchedPrograms(ProgramFrame.getText(),CloudFrame.cloudprograms,element,20);
                if(programs==null){
                    MessageFrame messageFrame=new MessageFrame("抱歉，没有找到相关数据。");
                }
            }
            else{
                MessageFrame messageFrame=new MessageFrame("您输入了错误的数据！");
            }
        }
    }

    public static List<Program> getPrograms() {
        return programs;
    }
}
