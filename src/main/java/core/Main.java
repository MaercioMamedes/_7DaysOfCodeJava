package core;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        ApiConnection api = new ApiConnection();
        api.getListMovies(250);
    }
}
