/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author gwen
 */
public class Quiz {
    private String wordHint;
    private String answer;

    public Quiz(String wordHint, String answer) {
        this.wordHint = wordHint;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String score) {
        this.answer = score;
    }

    public String getWordHint() {
        return wordHint;
    }

    public void setWordHint(String wordHint) {
        this.wordHint = wordHint;
    }
    

}
