package search.impl;

import main.SearchManager;
import search.FileHandler;
import search.Parser;
import search.WebSpider;
import vo.Program;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 请在此类中完成自己对象初始化工作，并注册
 */
public class Manager {

    static{
        // TODO:在此初始化所需组件，并将其注册到SearchManager中供主函数使用
        FileHandler fileHandler=new FileHandler() {
            @Override
            public int program2File(List<Program> programList) {
                int i=0;
                try {
                    PrintWriter printWriter=new PrintWriter(new FileWriter("program.txt"));
                    for(;i<programList.size();i++){
                        //将爬取到的program列表写入文件
                        printWriter.print("Id:"+programList.get(i).getId()+"\t");
                        printWriter.print("国家:"+programList.get(i).getCountry()+"\t");
                        printWriter.print("学校:"+programList.get(i).getUniversity()+"\t");
                        printWriter.print("学院:"+programList.get(i).getSchool()+"\t");
                        printWriter.print("项目名称:"+programList.get(i).getProgramName()+"\t");
                        printWriter.print("项目主页:"+programList.get(i).getHomepage()+"\t");
                        printWriter.print("地址:"+programList.get(i).getLocation()+"\t");
                        printWriter.print("邮箱:"+programList.get(i).getEmail()+"\t");
                        printWriter.print("联系方式:"+programList.get(i).getPhoneNumber()+"\t");
                        printWriter.print("学位:"+programList.get(i).getDegree()+"\t");
                        printWriter.print("申请截止时间（奖学金）:"+programList.get(i).getDeadlineWithAid()+"\t");
                        printWriter.print("申请截止时间（无奖学金）:"+programList.get(i).getDeadlineWithoutAid()+"\n");
                    }
                    printWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return i;
            }
        };
        WebSpider webSpider=new WebSpider() {
            @Override
            public Parser getParser() {

                Parser parser=new Parser() {
                    @Override
                    public Program parseHtml(String html) {
                        /**所有匹配**/
                        //匹配学院
                        Pattern forSchool = Pattern.compile("<h3>(.*?)</h3> </a>");
                        //匹配项目名
                        Pattern forProgamName = Pattern.compile("<h1 class=\"section-hero-title section-title\">(.*?)</h1>");
                        //匹配项目主页
                        Pattern forHomepage = Pattern.compile("<div class=\"menu-item black-button\"> <a href=\"(.*?)\">Apply <i class");
                        //匹配地址
                        Pattern forLocation = Pattern.compile("<div class=\"valign\"> <div class=\"bullet-item-value\"> (.*?) </div> </div> </div> <div class=\"bullet-item-bottom sub-title\" data-same-height-group=\"bullet-item-bottom\"> <div class=\"valign\"> <div class=\"small bullet-item-label\"> Location </div>");
                        //匹配学位信息
                        Pattern forDegreeList = Pattern.compile("<div class=\"dropdown-item-wrapper\"> (.*?) </i> <span class=\"title\">");
                        Pattern forDegree = Pattern.compile("(<i class=\"icon icon-nav-(.+?)\">  </i> )*(<i class=\"icon icon-nav-(.+?)\">)");
                        //匹配截止日期
                        Pattern forDeadLine = Pattern.compile("<h3>Admissions Dates</h3>(.*?)<div>");

                        Program program = new Program();
                        //设置标识id
                        String forId= UUID.randomUUID().toString();
                        program.setId(forId.replace("-",""));
                        //设置国家
                        program.setCountry("United States");
                        //设置学校名
                        program.setUniversity("Northeastern University");
                        //设置邮箱
                        program.setEmail("NULL");
                        //设置联系方式
                        program.setPhoneNumber("NULL");
                        //初始化
                        program.setSchool("NULL");
                        program.setProgramName("NULL");
                        program.setHomepage("NULL");
                        program.setLocation("NULL");
                        program.setDegree("NULL");
                        program.setDeadlineWithAid("NULL");
                        program.setDeadlineWithoutAid("NULL");
                        //设置学院名
                        Matcher matcher = forSchool.matcher(html);
                        if (matcher.find()) {
                            program.setSchool(matcher.group(1));
                        }
                        //设置项目名称
                        matcher = forProgamName.matcher(html);
                        if (matcher.find()) {
                            program.setProgramName(matcher.group(1));
                        }
                        //设置项目主页
                        matcher = forHomepage.matcher(html);
                        if (matcher.find()) {
                            program.setHomepage(matcher.group(1));
                        }
                        //设置地址
                        matcher = forLocation.matcher(html);
                        if (matcher.find()) {
                            program.setLocation(matcher.group(1));
                        }
                        //设置学位
                        matcher = forDegreeList.matcher(html);
                        if (matcher.find()) {
                            matcher = forDegree.matcher(matcher.group(1));
                            if (matcher.find()) {
                                if (matcher.group(1) != null) {
                                    program.setDegree(matcher.group(2));
                                    for (int k = 2; 2 * k <= matcher.groupCount(); k++) {
                                        program.setDegree(program.getDegree() + " or " + matcher.group(2 * k));
                                    }
                                } else {
                                    program.setDegree(matcher.group(4));
                                }
                            }
                        }
                        //设置截止日期
                        matcher = forDeadLine.matcher(html);
                        if (matcher.find()) {
                            String ddl=matcher.group(1);
                            ddl = ddl.replace("&nbsp;", " ");
                            ddl=ddl.replaceAll("<.*?>"," ");
                            program.setDeadlineWithAid(ddl);
                            program.setDeadlineWithoutAid(ddl);
                        }
                        return program;
                    }
                };
                return parser;
            }

            @Override
            public List<String> getHtmlFromWeb() {
                List<String> list=null;
                HtmlHandler htmlHandler=new HtmlHandler();
                for(int i=0;i<=15;i++) {
                    //从json当中获取项目列表
                    String listurl = "https://www.northeastern.edu/graduate/wp-json/nu/api/program-list/" + i;
                    try {
                        htmlHandler.setListFromUrl(listurl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                htmlHandler.await();//等待线程全部结束
                list=htmlHandler.getList();
                return list;
            }
        };
         SearchManager.registFileHandler(fileHandler);
         SearchManager.registSpider(webSpider);
    }
}
