package core;

import com.google.gson.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ApiConnection {
    /**
     * urlBase para API fornecedora dos dados de filmes e sérias
     */
    public final String urlBase = "https://api.themoviedb.org/3/authentication";
    public final String urlTopMovie = "https://api.themoviedb.org/3/movie/top_rated?language=pt-BR&page=";

    public JsonArray getListTopMovies(Integer page) throws IOException, InterruptedException {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("webToken");
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        builder.uri(URI.create(urlTopMovie + page));
        builder.header("accept", "application/json");
        builder.header("Authorization", "Bearer " + token);
        builder.method("GET", HttpRequest.BodyPublishers.noBody());
        HttpRequest request = builder
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JsonElement jsonElement = JsonParser.parseString(response.body());
        return jsonElement.getAsJsonObject().get("results").getAsJsonArray();
    }

    public void getListMovies(Integer topNumber) throws IOException, InterruptedException {

        Integer count = 1;
        Integer page = 0;

        do{
            page++;

            JsonArray listMovies = getListTopMovies(page);
            for(JsonElement jsonElement: listMovies) {

                Gson gson = new Gson();
                if (count > topNumber) {
                    break;
                } else {
                    System.out.println("Filme: " + count);
                    Movie movie = gson.fromJson(jsonElement, Movie.class);
                    System.out.println(movie);
                    count++;
                }
            }


        }while (count <= topNumber);



    }

    public void getMovieTop(){

    }
}
