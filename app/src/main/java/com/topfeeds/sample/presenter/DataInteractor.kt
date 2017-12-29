/*
 * Created by Miguel Bronzovic
 *
 * DataInteractor's contract methods and listeners
 */
package com.topfeeds.sample.presenter

import com.topfeeds.sample.model.Children
import rx.Subscription

interface OnCompleteListener {
    fun onComplete(children: List<Children>, after: String)

    fun onError(errorMessage: String)
}

interface DataInteractor {
    fun getTopNews(completeListener: OnCompleteListener, totalItems: Int, after: String?): Subscription
}