package segmenter;

import javafx.util.Pair;
import vo.Program;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * 实现了接口Segmenter的所有基本算法
 * 各种语言的分词部分待实现
 *
 */


public abstract class SegmenterImpl implements Segmenter{

    //从所有项目中获取字典
    @Override
    public List<Word> getDictionary(List<Program> programs) {
        Dictionary.setSumOfPrograms(programs.size());
        Dictionary dictionary = new Dictionary();
        for (Program program : programs) {
            List<String> words = getWordsFromInput(program);
            //先遍历字典，看项目内是否存在这个词，如果存在就把该词的项目数+1
            for (Word vocabulary: dictionary.getVocabularies()) {
                for(String word:words){
                    if(word.equals(vocabulary.getName())){
                        vocabulary.AddCountOfPrograms();
                        break;
                    }
                }
            }
            //遍历单词，看字典内是否有这个词，如果不存在就给这个字典加入这个词
            for (String word : words) {
                //是否存在标记
                boolean IsExist = false;
                for (Word vocabulary : dictionary.getVocabularies()) {
                    if (word.equals(vocabulary.getName())) {
                        IsExist = true;
                        break;
                    }
                }
                if (!IsExist){
                    dictionary.addVocabulary(word);
                }
            }
        }
        for(Word Vocabulary:dictionary.getVocabularies()){
            Dictionary.getTotalDictionary().add(new Pair<String, Integer>(Vocabulary.getName(),Vocabulary.getCountOfPrograms()));
        }
        return dictionary.getVocabularies();
    }

    //从单个项目中获取字典
    @Override
    public Dictionary getDictionary(Program program){
        Dictionary dictionary=new Dictionary();
        List<String> words=getWordsFromInput(program);
        dictionary.setSumOfWords(words.size());
        //遍历单词，看字典内是否有这个词，如果存在就给这个字典加入这个词
        for (String word : words) {
            //是否存在标记
            boolean IsExist = false;
            for (Word vocabulary : dictionary.getVocabularies()) {
                if (word.equals(vocabulary.getName())) {
                    vocabulary.AddCountOfWords();
                    IsExist = true;
                    break;
                }
            }
            if (!IsExist) {
                Word w=new Word(word);
                dictionary.addVocabulary(w);
            }
        }
        for(String init:words){
            String word=String.valueOf(init.charAt(0));
            boolean IsExist = false;
            for (Word vocabulary : dictionary.getVocabularies()) {
                if (word.equals(vocabulary.getName())) {
                    vocabulary.AddCountOfWords();
                    IsExist = true;
                    break;
                }
            }
            if (!IsExist) {
                Word w=new Word(word);
                dictionary.addVocabulary(w);
            }
        }
        return dictionary;
    }

    //从一条项目中获取所有词
    public abstract List<String> getWordsFromInput(Program program);

    //找出一个项目的关键词
    @Override
    public List<String> getKeyWords(Program program) {
        Dictionary dictionary=getDictionary(program);
        List<String> words=Extract(dictionary);
        return words;
    }

    @Override
    public List<Program> setProgramsWithSingleElement(List<Program> programs,int element){
        List<Program> ps=new ArrayList<>();
        for(Program program:programs){
            Program p=new Program();
            switch (element){
                case 0:p.setId(program.getId());break;
                case 1:p.setCountry(program.getCountry());break;
                case 2:p.setUniversity(program.getUniversity());break;
                case 3:p.setSchool(program.getSchool());break;
                case 4:p.setProgramName(program.getProgramName());break;
                case 5:p.setHomepage(program.getHomepage());break;
                case 6:p.setLocation(program.getLocation());break;
                case 7:p.setEmail(program.getEmail());break;
                case 8:p.setPhoneNumber(program.getPhoneNumber());break;
                case 9:p.setDegree(program.getDegree());break;
                case 10:p.setDeadlineWithAid(program.getDeadlineWithAid());break;
                case 11:p.setDeadlineWithoutAid(program.getDeadlineWithoutAid());break;
                default:break;
            }
            ps.add(p);
        }
        return ps;
    }
    @Override
    public List<Program> getMatchedPrograms(String input,List<Program> programs,int element,int showcount){
        double[] similarities=new double[programs.size()];
        List<Program> target=new ArrayList<>();
        Program inputprogram=new Program();
        inputprogram.setProgramName(input);
        inputprogram.setDegree("");
        inputprogram.setDeadlineWithoutAid("");
        inputprogram.setDeadlineWithAid("");
        inputprogram.setCountry("");
        inputprogram.setEmail("");
        inputprogram.setHomepage("");
        inputprogram.setUniversity("");
        inputprogram.setPhoneNumber("");
        inputprogram.setSchool("");
        inputprogram.setLocation("");
        inputprogram.setId("");
        int i=0;
        List<Program> ps=setProgramsWithSingleElement(programs,element);
        for(Program program:ps) {
            similarities[i] = getSimilarity(program, inputprogram);
            i++;
        }
        List<Integer> indexs=ExtractIndex(similarities,showcount);//查找最匹配的项目
        for(Integer index :indexs){
            target.add(programs.get(index));
        }
        return target;
    }
    //计算两个项目之间的相似度
    public double getSimilarity(Program program1, Program program2) {
        Dictionary dictionary1=getDictionary(program1);
        Dictionary dictionary2=getDictionary(program2);
        List<String> strings1=getKeyWords(program1);
        List<String> strings2=getKeyWords(program2);
        List<String> list=new ArrayList<>();
        list.addAll(strings1);
        list.addAll(strings2);
        double molecular=0.0D;//分子
        double denominator=1.0D;//分母
        double left=0.0D,right=0.0D;
        if(strings1.size()<=strings2.size()) {
            for (int i = 0; i < strings1.size()+strings2.size(); i++) {
                Word word1=dictionary1.Find(list.get(i));
                Word word2=dictionary2.Find(list.get(i));
                if (word1 != null && word2 != null) {
                    molecular += word1.getCountOfWords() * word2.getCountOfWords();
                    left+=Math.pow(word1.getCountOfWords(),2);
                    right+=Math.pow(word2.getCountOfWords(),2);
                }
            }
            denominator=Math.sqrt(left*right);
        }
        else{
            for (int i = strings1.size()+strings2.size()-1; i >strings1.size()-strings2.size()-1; i--) {
                Word word1=dictionary1.Find(list.get(i));
                Word word2=dictionary2.Find(list.get(i));
                if (word1 != null && word2 != null) {
                    molecular += word1.getCountOfWords() * word2.getCountOfWords();
                    left+=Math.pow(word1.getCountOfWords(),2);
                    right+=Math.pow(word2.getCountOfWords(),2);
                }
            }
            denominator=Math.sqrt(left*right);
        }
        return molecular/denominator;
    }

    //通过输入字符串并选择显示的项目数量，返回最匹配的项目
    public List<Program> getMatchedPrograms(String input, List<Program> programs,int showcount) {
        double[] similarities=new double[programs.size()];
        List<Program> target=new ArrayList<>();
        Program inputprogram=new Program();
        inputprogram.setProgramName(input);
        inputprogram.setDegree("");
        inputprogram.setDeadlineWithoutAid("");
        inputprogram.setDeadlineWithAid("");
        inputprogram.setCountry("");
        inputprogram.setEmail("");
        inputprogram.setHomepage("");
        inputprogram.setUniversity("");
        inputprogram.setPhoneNumber("");
        inputprogram.setSchool("");
        inputprogram.setLocation("");
        inputprogram.setId("");
        int i=0;
        for(Program program:programs) {
            similarities[i] = getSimilarity(program, inputprogram);
            i++;
        }
        List<Integer> indexs=ExtractIndex(similarities,showcount);//查找最匹配的项目
        for(Integer index :indexs){
            target.add(programs.get(index));
        }
        return target;
    }

    //从字典中提取项目关键词
    private List<String> Extract(Dictionary dictionary){
        List<String> words=new ArrayList<>();
        double[] tf_idfs=new double[dictionary.getVocabularies().size()];
        for(int i=0;i<dictionary.getVocabularies().size();i++){
            tf_idfs[i]=dictionary.getVocabularies().get(i).getTF_IDF();
        }
        //选取20个关键词的索引
        List<Integer> indexs=ExtractIndex(tf_idfs,20);
        for(Integer i:indexs){
            words.add(dictionary.getVocabularies().get(i).getName());
        }
        return words;
    }

    //从相似度数组similarities当中提取指定数量size个相似度最高的program索引
    private List<Integer> ExtractIndex(double[] similarities,int size){
        List<Integer> indexs=new ArrayList<>();
        for(int i=0;i<size&&i<similarities.length;i++){
            double max=-1.0D;
            int maxindex=-1;
            for(int j=0;j<similarities.length;j++){
                if(indexs.contains(j))continue;
                if(similarities[j]>max){
                    max=similarities[j];
                    maxindex=j;
                }
            }
            indexs.add(maxindex);
        }
        return indexs;
    }

    List<String> getInitals(List<String> words){
        List<String> initals=new ArrayList<>();
        for(String s:words){
            initals.add(String.valueOf(s.charAt(0)));
        }
        return initals;
    }
}
