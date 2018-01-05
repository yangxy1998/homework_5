package segmenter;

/**
 * 词汇类
 */


public class Word{

    //词汇内容
    private String Name;

    //该单词的数量
    private int CountOfWords;

    //出现该单词的项目数
    private int CountOfPrograms;

    //单词总量
    private int SumOfWords;

    //项目总量
    private static int SumOfPrograms;

    //TF—IDF值
    private double TF_IDF;

    public Word(String Name){
        this.Name=Name;
        CountOfWords=1;
        CountOfPrograms=1;
    }

    //该词数量+1
    public void AddCountOfWords(){
        CountOfWords++;
    }

    //包含该词的项目数+1
    public void AddCountOfPrograms(){
        CountOfPrograms++;
    }

    public String getName() {
        return Name;
    }

    public void setCountOfPrograms(int countOfPrograms) {
        CountOfPrograms = countOfPrograms;
    }

    public void setCountOfWords(int countOfWords) {
        CountOfWords = countOfWords;
    }

    public int getCountOfWords() {
        return CountOfWords;
    }

    public int getCountOfPrograms() {
        return CountOfPrograms;
    }

    public static void setSumOfPrograms(int sumOfPrograms) {
        SumOfPrograms = sumOfPrograms;
    }

    public void setSumOfWords(int sumOfWords) {
        SumOfWords = sumOfWords;
    }

    public double getTF_IDF(){
        setTF_IDF();
        return TF_IDF;
    }

    //计算TF—IDF值
    public void setTF_IDF() {
        double l=(CountOfWords/SumOfWords);
        double r=Math.log(SumOfPrograms)/Math.log(CountOfPrograms+1);
        this.TF_IDF = l*r;
    }

}
