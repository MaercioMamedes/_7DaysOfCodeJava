package core;

import ApiConnectors.TheMovieDB.ApiConnection;
import core.models.Movie;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        ApiConnection api = new ApiConnection();
        api.connectionTestApi();
        List<Movie> listMovie = api.getListMovies(3);

        listMovie.forEach(System.out::println);

        api.getListImagesMoviesTop(3);
        api.getTitlesMoviesTop(3);
    }
}
