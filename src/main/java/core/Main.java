package core;

import ApiConnectors.TheMovieDB.ApiConnection;


public class Main {

    public static void main(String[] args) {
        ApiConnection api = new ApiConnection();
        api.connectionTestApi();
        api.getListMovies(3);
        api.getListImagesMoviesTop(3);
        api.getTitlesMoviesTop(3);
    }
}
