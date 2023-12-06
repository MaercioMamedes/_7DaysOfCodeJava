package core;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        ApiConnection api = new ApiConnection();
        api.connectionTestApi();
        api.getListMovies(20);
        api.getListImagesMoviesTop(21);
        api.getTitlesMoviesTop(21);
    }
}
