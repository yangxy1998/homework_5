package segmenter;

import vo.Program;

import java.util.List;


/**
 * 包含所有分词方法的接口
 */


public interface Segmenter {

    //从所有项目中获取字典
    List<Word> getDictionary(List<Program> programs);

    //从单个项目中获取字典
    Dictionary getDictionary(Program program);

    //从一条项目中获取所有词
    List<String> getWordsFromInput(Program program);

    //找出一个项目的关键词
    List<String> getKeyWords(Program program);

    //计算两个项目之间的相似度
    double getSimilarity(Program program1,Program program2);

    //通过输入字符串返回最匹配的项目
    List<Program> getMatchedPrograms(String input,List<Program> programs,int showcount);

    //通过输入字符串返回按照元素最匹配的项目
    List<Program> getMatchedPrograms(String input,List<Program> programs,int element,int showcount);

    //只留单一元素的项目，便于后期进行分类检索
    List<Program> setProgramsWithSingleElement(List<Program> programs,int element);
}
