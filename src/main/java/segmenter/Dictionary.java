package segmenter;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 每个项目自己的字典
 * 一个项目对应一个字典
 * totalDictionary是词库，包含所有词的内容和含该词的项目数量
 * SumOfPrograms存储项目总量
 *
 */

public class Dictionary {

    //词库，装载了出现该单词的项目数
    private static List<Pair<String,Integer>> totalDictionary=new ArrayList<>();

    //项目当中包含词的总量
    private int SumOfWords;

    //总项目数
    private static int SumOfPrograms;

    //字典
    private List<Word> vocabularies;

    public Dictionary(){
        vocabularies=new ArrayList<>();
        SumOfPrograms=1;
        SumOfWords=1;
    }

    public List<Word> getVocabularies() {
        return vocabularies;
    }

    public void setVocabularies(List<Word> vocabularies) {
        this.vocabularies = vocabularies;
    }

    //通过字符给字典添加词
    public void addVocabulary(String vocabulary){
        Word word=new Word(vocabulary);
        word.setSumOfWords(SumOfWords);
        word.setSumOfPrograms(SumOfPrograms);
        vocabularies.add(word);
    }

    //通过其他字典已有的词给字典添加词
    public void addVocabulary(Word vocabulary){
        Word word=new Word(vocabulary.getName());
        word.setSumOfWords(SumOfWords);
        word.setSumOfPrograms(SumOfPrograms);
        word.setCountOfWords(vocabulary.getCountOfWords());
        for(Pair<String,Integer> p:totalDictionary){
            if(p.getKey().equals(word.getName())){
                word.setCountOfPrograms(p.getValue().intValue());
            }
        }
        vocabularies.add(word);
    }

    public void setSumOfWords(int sumOfWords) {
        SumOfWords = sumOfWords;
    }

    public int getSumOfWords() {
        return SumOfWords;
    }

    public static int getSumOfPrograms() {
        return SumOfPrograms;
    }

    //获取词库
    public static List<Pair<String, Integer>> getTotalDictionary() {
        return totalDictionary;
    }

    public static void setSumOfPrograms(int sumOfPrograms) {
        SumOfPrograms = sumOfPrograms;
    }

    //在字典当中通过字符串查词
    public Word Find(String string){
        for(Word word : vocabularies){
            if(string.equals(word.getName()))
                return word;
        }
        return null;
    }

    //通过其他字典的词查词
    public Word Find(Word word){
        return Find(word.getName());
    }

}
