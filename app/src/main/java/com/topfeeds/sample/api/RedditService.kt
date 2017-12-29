/*
 * Created by Miguel Bronzovic
 *
 * Retrofit's api service interface
 */
package com.topfeeds.sample.api

import com.topfeeds.sample.model.TopNews
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

const val TOP = "/top/.json"

interface RedditService {

    @GET(TOP)
    fun getTopNews(@Query("count") count: Int, @Query("limit") limit: Int, @Query("after") after: String?): Observable<TopNews>
}