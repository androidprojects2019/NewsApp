package apis;
import apis.model.NewsCategoryResponse;
import apis.model.NewsResponse;
import apis.model.NewsSourcesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebServices {
    @GET("sources")
    Call<NewsSourcesResponse> getNewsSources(@Query("apiKey") String key,
                                             @Query("language") String lang);

    @GET("everything")
    Call<NewsResponse> getNews(@Query("apiKey") String key,
                               @Query("language") String lang,
                               @Query("sources") String sources);
    @GET("sources")
    Call<NewsCategoryResponse> getNewsSourcesByCategory (@Query("apiKey") String key,
                                                         @Query("language") String lang,
                                                         @Query("category") String sourcesCategory);


 }