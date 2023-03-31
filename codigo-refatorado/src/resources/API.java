package resources;

public enum API {

    IMDB_TOP_TV("https://imdb-api.com/en/API/MostPopularTVs/");

    private final String url;

    API(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
