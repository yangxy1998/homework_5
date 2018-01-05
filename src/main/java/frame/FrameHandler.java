package frame;

import javax.swing.*;
import java.util.List;

public final class FrameHandler {

    //题目
    private static String header;

    //项目显示列表
    private static JList<String> list;

    //所有按钮
    private static List<JButton> buttons;

    //单选框
    private static List<JRadioButton> radioButtons;

    //文本框
    private static JTextField textField;

    public static List<JButton> getButtons() {
        return buttons;
    }

    public static void setButtons(List<JButton> buttons) {
        FrameHandler.buttons = buttons;
    }

    public static List<JRadioButton> getRadioButtons() {
        return radioButtons;
    }

    public static void setRadioButtons(List<JRadioButton> radioButtons) {
        FrameHandler.radioButtons = radioButtons;
    }

    public static JList<String> getList() {
        return list;
    }

    public static void setList(JList<String> l) {
        list = l;
    }

    public static String getHeader() {
        return header;
    }

    public static void setHeader(String header) {
        FrameHandler.header = header;
    }

    public static JTextField getTextField() {
        return textField;
    }

    public static void setTextField(JTextField textField) {
        FrameHandler.textField = textField;
    }
}
