package main;

import frame.MainFrame;

import javax.swing.*;


public final class Searcher {

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            //加载网页爬虫
            Class.forName("search.impl.Manager");
            //加载多语言关键词检索
            Class.forName("segmenter.impl.ChineseSegmenter");//中文
            Class.forName("segmenter.impl.EnglishSegmenter");//英文
            //加载数据库处理
            Class.forName("database.impl.Manager");
            //加载图形界面
            Class.forName("frame.MainFrame");
        } catch (ClassNotFoundException e) {
            System.out.println("资源缺失！");
        }

        MainFrame mainFrame=new MainFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//        DatabaseHandler databaseHandler=SearchManager.getDatabaseHandler();
//        String URL="jdbc:mysql://localhost:3306/test";
//        String Username="root";
//        String Password="admin";
//        databaseHandler.connectTo(URL,Username,Password);

//        List<WebSpider> webSpiders = SearchManager.getWebSpider();
//
//        List<Program> programs = new ArrayList<>();
//        for (WebSpider webSpider : webSpiders) {
//            Parser parser = webSpider.getParser();
//            List<String> pages = webSpider.getHtmlFromWeb();
//            for (String page : pages) {
//                programs.add(parser.parseHtml(page));
//            }
//        }
//        databaseHandler.addPrograms(programs);
//        List<Program> programList=databaseHandler.getPrograms();
//        databaseHandler.modifyProgram(programList.get(6), ChangeType.country,"UK");
//        Program program=databaseHandler.findProgram(programList.get(6).getId());

//        List<Program> programs20=SearchManager.getSegmenter(1).getMatchedPrograms("This is a text.",programs,20);
//        for(Program program1:programs20){
//            System.out.println(program1.getId());
//        }
//        FileHandler fileHandler = SearchManager.getFileHandler();
//        fileHandler.program2File(programs);

    }
}
