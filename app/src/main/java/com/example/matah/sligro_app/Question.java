package com.example.matah.sligro_app;

import java.util.ArrayList;

/**
 * Created by Negwood on 19-Jan-18.
 */

public class Question {
    public String getIndex() {
        return index;
    }

    public String getqContent() {
        return qContent;
    }

    public String getAnswerText() {
        return answerText;
    }

    public String getAnswerIndex() {
        return answerIndex;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String index, qContent, answerText, answerIndex;
    private ArrayList<String> answers;


    public Question(String index, String qContent, String answerText, String answerIndex, ArrayList<String> answers) {
        this.index = index;
        this.qContent = qContent;
        this.answerText = answerText;
        this.answerIndex = answerIndex;
        this.answers = answers;
    }
}
