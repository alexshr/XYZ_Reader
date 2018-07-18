package com.alexshr.xyz;

public interface AppConfig {

    String API_BASE = "https://go.udacity.com";
    String API_PATH = "/xyz-reader-json";

    int RETRY_COUNT = 3;
    long RETRY_INITIAL_DELAY = 100;

    int MAX_CONNECTION_TIMEOUT = 5000;
    int MAX_READ_TIMEOUT = 5000;
    int MAX_WRITE_TIMEOUT = 5000;

    String ETAG_HEADER = "ETag";
    String IF_NONE_MATCH_HEADER = "If-None-Match";

    String DATABASE_NAME = "articles";

    String DATE_FORMAT_IN = "yyyy-MM-dd'T'HH:mm:ss.sss";
    String DATE_FORMAT_OUT = "MMM dd, yyyy";
    int OFFSET_PAGE_LIMIT = 2;

    //https://stackoverflow.com/a/11019879/2886841
    String TITLE_BACKGROUND_TRANSPARENCY = "CC";
    int MAX_BODY_LENGTH = 10000;
    float DARK_FACTOR = 0.6f;
}
