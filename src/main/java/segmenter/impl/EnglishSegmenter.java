package segmenter.impl;

import main.SearchManager;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import segmenter.Segmenter;
import segmenter.SegmenterImpl;
import vo.Program;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 英文分词（Lucene分词）
 */


public class EnglishSegmenter {
    static {
        Segmenter segmenter=new SegmenterImpl() {

            @Override
            public List<String> getWordsFromInput(Program program) {
                //斯坦福英文分词器API：https://stanfordnlp.github.io/CoreNLP/api.html

                //下面是Lucene分词
                List<String> words=new ArrayList<>();
                StandardAnalyzer standardAnalyzer=new StandardAnalyzer();
                TokenStream tokenStream=standardAnalyzer.tokenStream("field",program.getAll());
                CharTermAttribute charTermAttribute=tokenStream.addAttribute(CharTermAttribute.class);
                try {
                    tokenStream.reset();
                    while(tokenStream.incrementToken()){
                        words.add(charTermAttribute.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return words;
            }
        };
        SearchManager.registSegmenter(segmenter);
    }
}
