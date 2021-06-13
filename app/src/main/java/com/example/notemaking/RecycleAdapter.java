package com.example.notemaking;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Viewholder> {

    private List<String> titles;
    private List<String> dates ;
    private Listener listener ;


    interface Listener{
        void oneClick(int position) ;
        void Longclick(int position) ;
    }

    //To make the activities register to Listener .
    public void setListener(Listener listener){
        this.listener = listener ;
    }

    public RecycleAdapter(List<String> titles , List<String> dates ){
        this.dates = dates ;
        this.titles = titles ;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Random random = new Random() ;
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlayout , parent , false);
        cv.setCardBackgroundColor(Color.argb(255 ,random.nextInt(256) , random.nextInt(256) , random.nextInt(256)));
        return new Viewholder(cv) ;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        final CardView view = holder.cardView ;
        TextView textView = view.findViewById(R.id.card_text) ;
        TextView textView1 = view.findViewById(R.id.card_date) ;
        textView.setText(titles.get(position));
        textView1.setText(dates.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.oneClick(position);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.Longclick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return  titles.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        private CardView cardView ;
        public Viewholder(@NonNull CardView v) {
            super(v);
            cardView = v ;
        }
    }
}
