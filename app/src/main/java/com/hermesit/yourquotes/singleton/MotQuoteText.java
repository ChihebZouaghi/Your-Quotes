package com.hermesit.yourquotes.singleton;

/**
 * Created by Chiheb on 20/07/2016.
 */
public class MotQuoteText {

    private int idMotQuoteText;
    private String motQuote;
    private String authorName;

    public MotQuoteText() {
    }

    public int getIdMotQuoteText() {
        return idMotQuoteText;
    }

    public void setIdMotQuoteText(int idMotQuoteText) {
        this.idMotQuoteText = idMotQuoteText;
    }

    public String getMotQuote() {
        return motQuote;
    }

    public void setMotQuote(String motQuote) {
        this.motQuote = motQuote;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
