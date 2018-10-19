package org.redout.agendawidget.weather.darksky;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DarkSkyRetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.darksky.net/";

    public static Retrofit getDarkSkyRetofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
