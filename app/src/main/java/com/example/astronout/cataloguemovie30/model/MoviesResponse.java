package com.example.astronout.cataloguemovie30.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse implements Parcelable {

    @SerializedName("page")
    private Long mPage;
    @SerializedName("results")
    private List<Movie> mResults;
    @SerializedName("total_pages")
    private Long mTotalPages;
    @SerializedName("total_results")
    private Long mTotalResults;

    public Long getmPage() {
        return mPage;
    }

    public void setmPage(Long mPage) {
        this.mPage = mPage;
    }

    public List<Movie> getmResults() {
        return mResults;
    }

    public void setmResults(List<Movie> mResults) {
        this.mResults = mResults;
    }

    public Long getmTotalPages() {
        return mTotalPages;
    }

    public void setmTotalPages(Long mTotalPages) {
        this.mTotalPages = mTotalPages;
    }

    public Long getmTotalResults() {
        return mTotalResults;
    }

    public void setmTotalResults(Long mTotalResults) {
        this.mTotalResults = mTotalResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mPage);
        dest.writeTypedList(this.mResults);
        dest.writeValue(this.mTotalPages);
        dest.writeValue(this.mTotalResults);
    }

    public MoviesResponse() {
    }

    protected MoviesResponse(Parcel in) {
        this.mPage = (Long) in.readValue(Long.class.getClassLoader());
        this.mResults = in.createTypedArrayList(Movie.CREATOR);
        this.mTotalPages = (Long) in.readValue(Long.class.getClassLoader());
        this.mTotalResults = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<MoviesResponse> CREATOR = new Creator<MoviesResponse>() {
        @Override
        public MoviesResponse createFromParcel(Parcel source) {
            return new MoviesResponse(source);
        }

        @Override
        public MoviesResponse[] newArray(int size) {
            return new MoviesResponse[size];
        }
    };
}
