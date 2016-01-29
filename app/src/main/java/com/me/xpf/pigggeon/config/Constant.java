package com.me.xpf.pigggeon.config;

/**
 * Created by pengfeixie on 16/1/29.
 */
public interface Constant {

    String BASE_URL = "https://api.dribbble.com/v1/";

    String BASE_AUTH_RUL = "https://dribbble.com/oauth/";

    String AUTH_URL = BASE_AUTH_RUL + "authorize";

    String TOKEN_RUL = "https://dribbble.com/oauth/token";

    String PAGE = "page";
    String PER_PAGE = "per_page";

    int PERPAGE = 20;

    String LIST = "list";
    String SHOTS = "shots";

    String ANIMATED = "animated";
    String REBOUNDS = "rebounds";
    String DEBUTS = "debuts";
    String PLAY_OFFS = "playoffs";
    String TEAMS = "teams";
    String LIKES = "likes";
    String MY_LIKES = "my_likes";
    String FOLOWING = "following";
    String MY_SHOTS = "my_shots";
    String BUCKET_SHOTS = "bucket_shots";

    String SORT = "sort";
    String POPULARITY = "";
    String COMMENTS = "comments";
    String RECENT = "recent";
    String VIEWS = "views";

}
