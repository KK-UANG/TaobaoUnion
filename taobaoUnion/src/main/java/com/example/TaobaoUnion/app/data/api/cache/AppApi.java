package com.example.TaobaoUnion.app.data.api.cache;

import com.example.TaobaoUnion.app.data.entity.Categories;
import com.example.TaobaoUnion.app.data.entity.FeaturedContent;
import com.example.TaobaoUnion.app.data.entity.HomeProducts;
import com.example.TaobaoUnion.app.data.entity.Preferential;
import com.example.TaobaoUnion.app.data.entity.FeaturedCategories;
import com.example.TaobaoUnion.app.data.entity.SearchResult;
import com.example.TaobaoUnion.app.data.entity.SearchRecommend;
import com.example.TaobaoUnion.app.data.entity.TicketParams;
import com.example.TaobaoUnion.app.data.entity.TicketResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface AppApi {

    @GET("discovery/categories")
    Observable<Categories> getCategories();

    @GET
    Observable<HomeProducts> getHomeProducts(@Url String url);

    @POST("tpwd")
    Observable<TicketResult> getTicket(@Body TicketParams ticketParams);

    @GET("recommend/categories")
    Observable<FeaturedCategories> getFeaturedCategories();

    @GET
    Observable<FeaturedContent> getFeaturedContent(@Url String url);

    @GET
    Observable<Preferential> getPreferential(@Url String url);

    @GET("search")
    Observable<SearchResult> getSearchResults(@Query("page") int page, @Query("keyword") String keyword);

    @GET("search/recommend")
    Observable<SearchRecommend> getSearchRecommend();

}
