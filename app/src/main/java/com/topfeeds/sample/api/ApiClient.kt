/*
 * Created by Miguel Bronzovic
 *
 * API client contract methods
 */
package com.topfeeds.sample.api

import com.topfeeds.sample.model.TopNews
import rx.Observable

interface ApiClient {
    fun getTopNewsListing(count: Int, limit: Int, after: String?) : Observable<TopNews>
}