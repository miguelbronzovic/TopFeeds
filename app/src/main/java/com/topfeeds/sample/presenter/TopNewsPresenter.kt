/*
 * Created by Miguel Bronzovic
 *
 * Presenter implementation to handle interaction between view and Reddit API client
 */
package com.topfeeds.sample.presenter

import android.os.Bundle
import com.topfeeds.sample.Constants
import com.topfeeds.sample.api.ApiModule
import com.topfeeds.sample.model.Children
import org.parceler.Parcels
import rx.Subscription

class TopNewsPresenter(view: TopNewsView) : Presenter, OnCompleteListener {

    private val activityView = view
    private val dataInteractor = TopNewsDataInteractor(ApiModule.okHttpClient)
    private lateinit var subscription: Subscription

    override fun loadDataItems() {
        activityView.showProgressBar()
        subscription = dataInteractor.getTopNews(this, 0, null)
    }

    override fun onStop() = subscription.unsubscribe()

    override fun showError(error: String) = activityView.showErrorMessage(error)

    override fun onLoadMoreItems(totalItems: Int, after: String) {
        activityView.showProgressBar()
        subscription = dataInteractor.getTopNews(this, totalItems, after)
    }

    override fun onRestoreState(bundle: Bundle) {
        val after = bundle.getString(Constants.AFTER_VALUE)
        val children = Parcels.unwrap<List<Children>>(bundle.getParcelable(Constants.CHILDREN_ITEMS))
        activityView.loadDataItems(children, after)
    }

    override fun onComplete(children: List<Children>, after: String) {
        activityView.hideProgressBar()
        activityView.loadDataItems(children, after)
    }

    override fun onError(errorMessage: String) = showError(errorMessage)
}