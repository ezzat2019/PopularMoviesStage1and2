package com.example.programmer.popularmoviesstage1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programmer.popularmoviesstage1.R;
import com.example.programmer.popularmoviesstage1.models.reviews.ReviwesResult;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.HolderReviews> {
    private ReivewsClick reivewsClick;
    private List<ReviwesResult> list;

    public ReviewsAdapter(ReivewsClick reivewsClick) {
        this.reivewsClick = reivewsClick;
    }

    @NonNull
    @Override
    public HolderReviews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderReviews(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderReviews holder, int position) {
        holder.fill(list.get(position));

    }

    public void setList(List<ReviwesResult> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ReivewsClick {
        void onClickonReives(int pos);
    }

    class HolderReviews extends RecyclerView.ViewHolder {
        private TextView txt_auther, txt_content;
        private Button btn;

        public HolderReviews(@NonNull View itemView) {
            super(itemView);
            txt_auther = itemView.findViewById(R.id.txt_autur);
            txt_content = itemView.findViewById(R.id.txt_content);
            btn = itemView.findViewById(R.id.btn_review);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reivewsClick.onClickonReives(getAdapterPosition());
                }
            });


        }

        public void fill(ReviwesResult reviwesResult) {
            txt_content.setText(reviwesResult.getContent() + "");
            txt_auther.setText(reviwesResult.getAuthor());

        }
    }
}



