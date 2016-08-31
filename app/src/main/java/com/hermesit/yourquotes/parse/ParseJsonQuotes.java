package com.hermesit.yourquotes.parse;

import android.text.Html;

import com.hermesit.yourquotes.singleton.MotQuoteText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chiheb on 29/08/2016.
 */
public class ParseJsonQuotes {

    public static List<MotQuoteText> parseQuotes(String content){

        JSONArray quotesArray = null;

        try {
            quotesArray = new JSONArray(content);

            List<MotQuoteText> quoteTextList = new ArrayList<>();

            for (int i = 0; i < quotesArray.length(); i++) {
                JSONObject quoteObj = null;
                quoteObj = quotesArray.getJSONObject(i);
                MotQuoteText quote = new MotQuoteText();
                quote.setIdMotQuoteText(quoteObj.getInt("ID"));
                quote.setAuthorName(quoteObj.getString("title"));
                quote.setMotQuote(editFirst(quoteObj.getString("content")));
                quoteTextList.add(quote);
            }

            return quoteTextList;

        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }


    }

    private static String editFirst(String content) {
        content = Html.fromHtml(content).toString();

        return content;
    }
}
