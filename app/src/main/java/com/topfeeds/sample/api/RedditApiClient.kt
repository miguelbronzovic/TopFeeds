/*
 * Created by Miguel Bronzovic
 *
 * Reddit Api Client
 */
package com.topfeeds.sample.api

import com.google.gson.GsonBuilder
import com.topfeeds.sample.model.TopNews
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

private const val BASE_URL = "https://www.reddit.com/"

class RedditApiClient(okHttpClient: OkHttpClient) : ApiClient {
    private var redditService: RedditService

    init {
        val gson = GsonBuilder()
                .create()

        val rxAdapter = RxJavaCallAdapterFactory.create()

        val retrofitAdapter = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build()

        redditService = retrofitAdapter.create(RedditService::class.java)
    }

    override fun getTopNewsListing(count: Int, limit: Int, after: String?): Observable<TopNews> {
        return redditService.getTopNews(count, limit, after)
    }
}