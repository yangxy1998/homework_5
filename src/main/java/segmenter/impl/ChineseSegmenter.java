package segmenter.impl;

import main.SearchManager;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import segmenter.Segmenter;
import segmenter.SegmenterImpl;
import vo.Program;

import java.util.ArrayList;
import java.util.List;


/**
 * 中文分词（ansj分词）
 */


public class ChineseSegmenter {
    static {
        Segmenter segmenter=new SegmenterImpl() {

            @Override
            public List<String> getWordsFromInput(Program program) {
                List<String> words = new ArrayList<>();
                Result result=ToAnalysis.parse(program.getAll());
                List<Term> terms=result.getTerms();
                for(Term term:terms){
                    words.add(term.getName());
                }
                return words;
            }

        };

        SearchManager.registSegmenter(segmenter);
    }
}
