package com.example.astronout.cataloguemovie30.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {

    @SerializedName("vote_count")
    private Long mVoteCount;
    @SerializedName("id")
    private Long mId;
    @SerializedName("video")
    private Boolean mVideo;
    @SerializedName("vote_average")
    private Double mVoteAverage;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("popularity")
    private Double mPopularity;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("original_language")
    private String mOriginalLanguage;
    @SerializedName("original_title")
    private String mOriginalTitle;
    @SerializedName("genre_ids")
    private List<Long> mGenreIds;
    @SerializedName("adult")
    private Boolean mAdult;
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("release_date")
    private String mReleaseDate;

    public Movie(Long mVoteCount, Long mId, Boolean mVideo, Double mVoteAverage, String mTitle, Double mPopularity, String mPosterPath, String mOriginalLanguage, String mOriginalTitle, List<Long> mGenreIds, Boolean mAdult, String mBackdropPath, String mOverview, String mReleaseDate) {
        this.mVoteCount = mVoteCount;
        this.mId = mId;
        this.mVideo = mVideo;
        this.mVoteAverage = mVoteAverage;
        this.mTitle = mTitle;
        this.mPopularity = mPopularity;
        this.mPosterPath = mPosterPath;
        this.mOriginalLanguage = mOriginalLanguage;
        this.mOriginalTitle = mOriginalTitle;
        this.mGenreIds = mGenreIds;
        this.mAdult = mAdult;
        this.mBackdropPath = mBackdropPath;
        this.mOverview = mOverview;
        this.mReleaseDate = mReleaseDate;
    }

    String baseImageUrl = "https://image.tmdb.org/t/p/w500";

    public Movie() {

    }

    public String getmPosterPath(){
//        return "https://image.tmdb.org/t/p/w500" + mPosterPath;
        return mPosterPath;
    }

    public Long getmVoteCount() {
        return mVoteCount;
    }

    public void setmVoteCount(Long mVoteCount) {
        this.mVoteCount = mVoteCount;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public Boolean getmVideo() {
        return mVideo;
    }

    public void setmVideo(Boolean mVideo) {
        this.mVideo = mVideo;
    }

    public Double getmVoteAverage() {
        return mVoteAverage;
    }

    public void setmVoteAverage(Double mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Double getmPopularity() {
        return mPopularity;
    }

    public void setmPopularity(Double mPopularity) {
        this.mPopularity = mPopularity;
    }

    public void setmPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getmOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setmOriginalLanguage(String mOriginalLanguage) {
        this.mOriginalLanguage = mOriginalLanguage;
    }

    public String getmOriginalTitle() {
        return mOriginalTitle;
    }

    public void setmOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    public List<Long> getmGenreIds() {
        return mGenreIds;
    }

    public void setmGenreIds(List<Long> mGenreIds) {
        this.mGenreIds = mGenreIds;
    }

    public Boolean getmAdult() {
        return mAdult;
    }

    public void setmAdult(Boolean mAdult) {
        this.mAdult = mAdult;
    }

    public String getmBackdropPath() {
        return mBackdropPath;
    }

    public void setmBackdropPath(String mBackdropPath) {
        this.mBackdropPath = mBackdropPath;
    }

    public String getmOverview() {
        return mOverview;
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mVoteCount);
        dest.writeValue(this.mId);
        dest.writeValue(this.mVideo);
        dest.writeValue(this.mVoteAverage);
        dest.writeString(this.mTitle);
        dest.writeValue(this.mPopularity);
        dest.writeString(this.mPosterPath);
        dest.writeString(this.mOriginalLanguage);
        dest.writeString(this.mOriginalTitle);
        dest.writeList(this.mGenreIds);
        dest.writeValue(this.mAdult);
        dest.writeString(this.mBackdropPath);
        dest.writeString(this.mOverview);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.baseImageUrl);
    }

    protected Movie(Parcel in) {
        this.mVoteCount = (Long) in.readValue(Long.class.getClassLoader());
        this.mId = (Long) in.readValue(Long.class.getClassLoader());
        this.mVideo = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.mVoteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.mTitle = in.readString();
        this.mPopularity = (Double) in.readValue(Double.class.getClassLoader());
        this.mPosterPath = in.readString();
        this.mOriginalLanguage = in.readString();
        this.mOriginalTitle = in.readString();
        this.mGenreIds = new ArrayList<Long>();
        in.readList(this.mGenreIds, Long.class.getClassLoader());
        this.mAdult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.mBackdropPath = in.readString();
        this.mOverview = in.readString();
        this.mReleaseDate = in.readString();
        this.baseImageUrl = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
