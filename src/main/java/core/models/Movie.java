package core.models;

import ApiConnectors.TheMovieDB.ApiConnection;
import ApiConnectors.TheMovieDB.MovieTMDB;

import java.util.Arrays;
import java.util.List;

public class Movie {
    private String title;
    private String urlImage;
    private final double rating;
    private final int year;

    public Movie(MovieTMDB movieTMDB){
        this.title = movieTMDB.title();
        this.urlImage = ApiConnection.getFullUrlImage(movieTMDB.poster_path());
        this.rating = movieTMDB.vote_average();

        List<String> date_formatted = List.of(movieTMDB.release_date().split("-"));
        System.out.println(date_formatted);
        this.year =  Integer.parseInt(date_formatted.get(0));



    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public double getRating() {
        return rating;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", rating=" + rating +
                ", year=" + year +
                '}';
    }
}