package com.hermesit.yourquotes.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hermesit.yourquotes.R;

/**
 * Created by Chiheb on 04/08/2016.
 */
public class QuoteDialog extends DialogFragment {

    private EditText addEditQuote;
    private Button shareButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout, container, false);
       addEditQuote = (EditText) view.findViewById(R.id.idAddEditQuoteInDialog);

        addEditQuote.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

       shareButton = (Button) view.findViewById(R.id.idShareQuoteInDialog);

        Bundle mArgs = getArguments();
        String quoteTxt = mArgs.getString(MainActivity.KEYQUOTE);
        addEditQuote.setText(quoteTxt);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addEditQuote.getText().toString().equals("") || addEditQuote.getText().toString().equals(" ") ){
                    Toast.makeText(getActivity(),"You must write your quote first !",Toast.LENGTH_LONG).show();
                }else {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.putExtra(Intent.EXTRA_TEXT, addEditQuote.getText().toString());
                    startActivity(Intent.createChooser(share, getResources().getString(R.string.other_share_option_title)));
                }
            }
        });

        return  view;
    }
}
