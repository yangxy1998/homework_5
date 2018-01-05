package frame.fragment;

import frame.FrameHandler;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ShowFrame extends JFrame{


    protected JList<String> list;
    protected JScrollPane scrollPane;
    protected List<JButton> buttons;
    protected List<JRadioButton> radioButtons;
    protected ButtonGroup chooser;
    protected static JTextField textField;

    public ShowFrame(){
        super(FrameHandler.getHeader());

        setVisible(true);
        setSize(1200,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        list= FrameHandler.getList();

        scrollPane=new JScrollPane(list);
        scrollPane.setVisible(true);
        scrollPane.setSize(1100,300);
        scrollPane.setAlignmentX(10);
        scrollPane.setAlignmentY(10);
        add(scrollPane);

        buttons=FrameHandler.getButtons();

        for(int i=0;i<buttons.size();i++){
            buttons.get(i).setVisible(true);
            buttons.get(i).setSize(100,50);
            add(buttons.get(i));
        }

        if((radioButtons=FrameHandler.getRadioButtons())!=null){
            chooser=new ButtonGroup();
            for(int i=0;i<radioButtons.size();i++){
                radioButtons.get(i).setVisible(true);
                radioButtons.get(i).setSize(50,20);
                add(radioButtons.get(i));
                chooser.add(radioButtons.get(i));
            }
        }

        if((textField=FrameHandler.getTextField())!=null){
            textField.setVisible(true);
            add(textField);
        }
    }

    public static String getText(){
        return textField.getText();
    }

    public static void Clear(){
        FrameHandler.setRadioButtons(null);
        FrameHandler.setHeader("");
        FrameHandler.setTextField(null);
        FrameHandler.setButtons(null);
        FrameHandler.setList(null);
    }
}
