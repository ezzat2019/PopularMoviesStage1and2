package com.example.programmer.popularmoviesstage1.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programmer.popularmoviesstage1.R;
import com.example.programmer.popularmoviesstage1.models.video.Results;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.VH> {

    public TrailerClick trailerClick;
    private List<Results> list;

    public TrailersAdapter(TrailerClick trailerClick) {
        this.trailerClick = trailerClick;
    }

    @NonNull
    @Override
    public TrailersAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrailersAdapter.VH(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.trailer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersAdapter.VH holder, int position) {
        holder.fillData(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Results> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface TrailerClick {
        void onClick(int pos);

        void onClickShare(int pos);
    }

    class VH extends RecyclerView.ViewHolder {
        private TextView txt_name;
        private ImageView imageView;
        private ImageView imageView2;

        public VH(@NonNull final View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_trailer_name);
            imageView = itemView.findViewById(R.id.img_share);
            imageView2 = itemView.findViewById(R.id.img_main);
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    trailerClick.onClick(getAdapterPosition());
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    trailerClick.onClickShare(getAdapterPosition());
                }
            });


        }

        public void fillData(Results traier) {
            txt_name.setText(traier.getName() + "");
        }
    }
}
