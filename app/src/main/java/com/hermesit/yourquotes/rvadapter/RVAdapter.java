package com.hermesit.yourquotes.rvadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hermesit.yourquotes.R;
import com.hermesit.yourquotes.singleton.MotQuoteText;

import java.util.Collections;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVHolder> {

    private LayoutInflater layoutInflater;
    List<MotQuoteText> data = Collections.emptyList();

    public RVAdapter(Context context, List<MotQuoteText> data) {
        layoutInflater = LayoutInflater.from(context);
        this.data = data;

    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_appearence_row, parent, false);
        RVHolder holder = new RVHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RVHolder holder, int position) {
        MotQuoteText currentMQT = data.get(position);
        holder.tx1.setText(currentMQT.getMotQuote());
        holder.tx2.setText(currentMQT.getAuthorName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RVHolder extends RecyclerView.ViewHolder {

        TextView tx1;
        TextView tx2;



        public RVHolder(View itemView) {
            super(itemView);
            tx1 = (TextView) itemView.findViewById(R.id.idSingleQuoteTV);
            tx2 = (TextView) itemView.findViewById(R.id.idTV2);
        }
    }
}
