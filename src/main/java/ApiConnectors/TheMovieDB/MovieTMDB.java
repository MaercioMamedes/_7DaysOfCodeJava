package ApiConnectors.TheMovieDB;

public record MovieTMDB(
              Integer id,
              String popularity,
              String poster_path,
              String release_date,
              String title,
              Double vote_average,
              Integer vote_count

) {
    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", popularity='" + popularity + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", release_date='" + release_date + '\'' +
                ", title='" + title + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                '}';
    }
}
