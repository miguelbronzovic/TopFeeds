/*
 * Created by Miguel Bronzovic
 *
 * DataInteractor implementation to consume data from Reddit Api Client
 */
package com.topfeeds.sample.presenter

import com.topfeeds.sample.api.RedditApiClient
import okhttp3.OkHttpClient
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

private const val LIMIT = 10

class TopNewsDataInteractor(okHttpClient: OkHttpClient) : DataInteractor {
    private val redditApiClient = RedditApiClient(okHttpClient)

    override fun getTopNews(completeListener: OnCompleteListener, totalItems: Int, after: String?): Subscription =
            redditApiClient.getTopNewsListing(totalItems, LIMIT, after)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ result ->
                        completeListener.onComplete(result.data.children, result.data.after)
                    }, { error ->
                        error.message?.let { completeListener.onError(it) }
                    })
}