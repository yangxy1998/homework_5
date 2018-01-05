package search.impl;


import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlHandler {
    private volatile List<String> list=new ArrayList<>();
    private volatile List<MyThread> threads=new ArrayList<>();
    public void setListFromUrl(String url) throws Exception{

        Connection connection= Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (Windows NT 5.1; zh-CN; rv:1.9.1.3) Gecko/20100101 Firefox/8.0");
        String html=connection.get().outerHtml();
        String[] lines=html.split("ID");
        for(String pageLine:lines){
            setListFromLine(pageLine);
        }

    }

    public void setListFromLine(String pageLine) throws Exception{
        MyThread myThread=new MyThread(pageLine);
        myThread.start();
        threads.add(myThread);
    }

    /**等待所有子线程结束**/
    public void await(){
        for(MyThread thread:threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getList(){
        return list;
    }

    /**设计自己的线程**/
    public class MyThread extends Thread {
        private Matcher match;
        private String pageLine;
        private int mode;
        MyThread(String pageLine){
            this.pageLine=pageLine;
            //设置线程运行模式
            mode=1;
        }
        MyThread(Matcher match){
            this.match=match;
            //设置线程运行模式
            mode=2;
        }
        @Override
        public void run() {
            if(mode==1) {//线程运行模式：获取项目列表，并开始子线程爬取网页信息
                Pattern pattern = Pattern.compile("\"link\":\"(.*?)\"");//要抓取的内容
                String[] line = pageLine.split("ID");
                List<MyThread> threads = new ArrayList<>();
                Matcher matcher = pattern.matcher(pageLine);
                if (matcher.find()) {
                    MyThread myThread = new MyThread(matcher);
                    myThread.start();
                    threads.add(myThread);
                }
                for (MyThread thread : threads) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(mode==2) {//线程运行模式：爬取网页信息
                try {
                    list.add(getHtml(match));
                    System.out.println(match.group(1) + "finished");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**爬取网页信息，返回整个网页所有的文本信息**/
        private String getHtml(Matcher matcher) throws Exception{
            /**改进代码，使用Jsoup**/
            String result=matcher.group(1);
            result=result.replace("\\","");
            Connection connection= Jsoup.connect(result).userAgent("Mozilla/5.0 (Windows NT 5.1; zh-CN) AppleWebKit/535.12 (KHTML, like Gecko) Chrome/22.0.1229.79 Safari/535.12");
            String html=connection.get().outerHtml();
            html=html.replaceAll("( )+"," ");
            html=html.replace("\n","");
            html=html.replaceAll("( )+"," ");
            /**原版代码**/
//            String result=matcher.group(1);
//            result=result.replace("\\","");
//            BufferedReader inhtml=null;
//            URL readHTML=new URL(result);
//            URLConnection connecthtml=readHTML.openConnection();
//            connecthtml.connect();
//            inhtml=new BufferedReader(new InputStreamReader(connecthtml.getInputStream()));
//            String lineText;
//            String html="";
//            while ((lineText = inhtml.readLine()) != null){
//                html+=lineText;
//            }
            return html;
        }
    }
}
