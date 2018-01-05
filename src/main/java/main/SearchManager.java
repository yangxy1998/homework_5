package main;

import database.DatabaseHandler;
import search.FileHandler;
import search.WebSpider;
import segmenter.Segmenter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class SearchManager {

    //网页爬虫
    private static List<WebSpider> webSpiders;

    //文件处理
    private static FileHandler fileHandler;

    //多种语言分词方法
    private static List<Segmenter> segmenters;

    //数据库连接
    private static Connection connection;

    //数据库处理
    private static DatabaseHandler databaseHandler;

    private SearchManager(){}

    public static void registSpider(WebSpider webSpider){
        getWebSpider().add(webSpider);
    }

    public static void registFileHandler(FileHandler fh){
        fileHandler = fh;
    }

    public static void registSegmenter(Segmenter segmenter){
        getSegmenters().add(segmenter);
    }

    public static void registDatabaseHandler(DatabaseHandler dh){databaseHandler=dh;}

    //启动连接
    public static void startConnection(Connection c){
        connection=c;
    }

    //终止连接
    public static void disConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static List<WebSpider> getWebSpider(){
        if(webSpiders == null){
            synchronized (SearchManager.class){
                if(webSpiders == null)
                    webSpiders = new ArrayList<>();
            }
        }
        return webSpiders;
    }

    public static FileHandler getFileHandler() {
        return fileHandler;
    }

    /**
     * 选取分词语言
     * 0=中文分词
     * 1=英文分词
     */
    public static Segmenter getSegmenter(int type){
        return getSegmenters().get(type);
    }

    public static List<Segmenter> getSegmenters(){
        if(segmenters==null){
            synchronized (SearchManager.class){
                if(segmenters==null)
                    segmenters=new ArrayList<>();
            }
        }
        return segmenters;
    }

    public static DatabaseHandler getDatabaseHandler(){return databaseHandler;}

}
