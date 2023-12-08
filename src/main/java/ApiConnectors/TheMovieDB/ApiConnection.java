package ApiConnectors.TheMovieDB;

import com.google.gson.*;
import core.models.Movie;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ApiConnection {
    /**
     * Classe de conexão com a API do the MovieDB
     */

    private static final String urlImageBase = "https://image.tmdb.org/t/p/original";
    private static final String urlBase = "https://api.themoviedb.org/3/authentication";
    private static final String urlTopMovie = "https://api.themoviedb.org/3/movie/top_rated?language=pt-BR&page=";
    Dotenv dotenv = Dotenv.load();
    private final String token = dotenv.get("webToken");

    public static String getUrlImageBase(String s) {
        return urlImageBase;
    }

    public void connectionTestApi(){
        /*
          Método de requisição teste com a API
         */

        try{
            HttpRequest.Builder builder = HttpRequest.newBuilder();
            builder.uri(URI.create(urlBase));
            builder.header("accept", "application/json");
            builder.header("Authorization", "Bearer " + token);
            builder.method("GET", HttpRequest.BodyPublishers.noBody());
            HttpRequest request = builder
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

        } catch (InterruptedException | IOException ignored){


        } finally {
            System.out.println("teste de comunicação com API finalizado");
        }
    }

    private JsonArray getListTopMovies(Integer page){
        /*
            Método para buscar os filmes mais bem avaliado da plataforma
         */

        JsonArray listMovies = null;
        try{
            HttpRequest.Builder builder = HttpRequest.newBuilder();
            builder.uri(URI.create(urlTopMovie + page));
            builder.header("accept", "application/json");
            builder.header("Authorization", "Bearer " + token);
            builder.method("GET", HttpRequest.BodyPublishers.noBody());
            HttpRequest request = builder
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JsonElement jsonElement = JsonParser.parseString(response.body());
            listMovies = jsonElement.getAsJsonObject().get("results").getAsJsonArray();
            
        } catch (InterruptedException | IOException e){
            System.out.println("Falha durante retorno dos dados dos filmes da API");
            System.out.println(e);
            
        }
        return listMovies;
    }


    public List<Movie> getListMovies(Integer topNumber){
        /*
            Método para retornar lista de Filmes
         */

        int count = 1;
        Integer page = 0;
        List<Movie> listMoviesModel = new ArrayList<>();

        try{
            do {
                page++;
                JsonArray listMovies = getListTopMovies(page);
                for (JsonElement jsonElement : listMovies) {
                    Gson gson = new Gson();
                    if (count > topNumber) {
                        break;
                    } else {
                        System.out.println("Filme: " + count);
                        MovieTMDB movieTMDB = gson.fromJson(jsonElement, MovieTMDB.class);
                        System.out.println(movieTMDB);
                        Movie movie = new Movie(movieTMDB);
                        listMoviesModel.add(movie);
                        //System.out.println(movie);
                        count++;
                    }
                }
            } while (count <= topNumber);

        } catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()) + "\nFalha ao acessar API");
        }
        return listMoviesModel;
    }

    public void getListImagesMoviesTop(int topNumber) {
        /*
        Método para retornar listas das imagens dos filmes mais bem avaliados
         */

        int count = 1;
        int page = 0;
        List<String> listMoviesImages = new ArrayList<>();

        try {
            do {
                page++;
                JsonArray listMovies = getListTopMovies(page);
                for (JsonElement jsonElement : listMovies) {

                    if (count > topNumber) {
                        break;
                    } else {
                        JsonElement image_uri = jsonElement.getAsJsonObject().get("poster_path");
                        String urlImage = urlImageBase + image_uri.toString().trim().replaceAll("^\"|\"$", "");
                        listMoviesImages.add(urlImage);
                        count++;
                    }
                }
            } while (count <= topNumber);

            for (String movie : listMoviesImages) {
                System.out.println(movie);
            }
        } catch (Exception e) {
            System.out.println(e + "\nFalha ao acessar API");
        }
    }
    
    public static String getFullUrlImage(String urlImagePath){
        return urlImageBase + urlImagePath.trim().replaceAll("^\"|\"$", "");
    }
    public void getTitlesMoviesTop(int topNumber) {
        /*
            Método para retornar listas dos títulos dos filmes mais bem avaliados
         */

        int count = 1;
        int page = 0;
        List<String> listMoviesTitles = new ArrayList<>();

        try {
            do {
                page++;

                JsonArray listMovies = getListTopMovies(page);
                for (JsonElement jsonElement : listMovies) {

                    if (count > topNumber) {
                        break;
                    } else {
                        JsonElement title = jsonElement.getAsJsonObject().get("title");
                        listMoviesTitles.add(title.toString());
                        count++;
                    }
                }
            } while (count <= topNumber);

            for (String movie : listMoviesTitles) {
                System.out.println(movie);
            }
        } catch (Exception e) {
            System.out.println(e + "\nFalha ao acessar API");
        }
    }
}
