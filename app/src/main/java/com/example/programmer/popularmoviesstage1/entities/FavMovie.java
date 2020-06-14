package com.example.programmer.popularmoviesstage1.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "fav_items")
public class FavMovie implements Parcelable {
    public static final Parcelable.Creator<FavMovie> CREATOR = new Parcelable.Creator<FavMovie>() {
        @Override
        public FavMovie createFromParcel(Parcel source) {
            return new FavMovie(source);
        }

        @Override
        public FavMovie[] newArray(int size) {
            return new FavMovie[size];
        }
    };
    @ColumnInfo(name = "poster_path")
    private String poster_path;
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "is_fav")
    private Boolean isFav;
    @ColumnInfo(name = "release_date")
    private String release_date;
    @ColumnInfo(name = "vote_average")
    private Double vote_average;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "title")
    private String title;

    public FavMovie(String poster_path, int id, Boolean isFav, String release_date, Double vote_average, String overview, String title) {
        this.poster_path = poster_path;
        this.id = id;
        this.isFav = isFav;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.overview = overview;
        this.title = title;
    }

    @Ignore
    public FavMovie(String poster_path, int id, String release_date, Double vote_average, String overview, String title) {
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.overview = overview;
        this.title = title;
        this.id = id;
    }

    protected FavMovie(Parcel in) {
        this.poster_path = in.readString();
        this.id = in.readInt();
        this.isFav = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.release_date = in.readString();
        this.vote_average = (Double) in.readValue(Double.class.getClassLoader());
        this.overview = in.readString();
        this.title = in.readString();
    }

    public Boolean getFav() {
        return isFav;
    }

    public void setFav(Boolean fav) {
        isFav = fav;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.poster_path);
        dest.writeInt(this.id);
        dest.writeValue(this.isFav);
        dest.writeString(this.release_date);
        dest.writeValue(this.vote_average);
        dest.writeString(this.overview);
        dest.writeString(this.title);
    }
}
