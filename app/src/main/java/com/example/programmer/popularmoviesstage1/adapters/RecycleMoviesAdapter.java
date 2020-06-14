package com.example.programmer.popularmoviesstage1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programmer.popularmoviesstage1.R;
import com.example.programmer.popularmoviesstage1.models.Results;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleMoviesAdapter extends RecyclerView.Adapter<RecycleMoviesAdapter.VH> {

    public final static String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/";

    // var
    private Context context;
    private OnItemClick onItemClick;
    private List<Results> resultsList;


    public RecycleMoviesAdapter(Context context, OnItemClick onItemClick) {
        this.context = context;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_poster, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.bind(resultsList.get(i));

    }

    public void setList(List<Results> list) {
        this.resultsList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public interface OnItemClick {
        void onClick(int pos);
    }

    class VH extends RecyclerView.ViewHolder {
        private ImageView imgPoster;

        public VH(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_movie_poster);
            imgPoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onClick(getAdapterPosition());

                }
            });
        }

        void bind(Results results) {
            if (results != null) {

                Picasso.with(context)
                        .load(BASE_URL_IMAGE.concat(results.getPoster_path()))
                        .placeholder(R.drawable.image_not_available)
                        .into(imgPoster);
            }
        }
    }
}
