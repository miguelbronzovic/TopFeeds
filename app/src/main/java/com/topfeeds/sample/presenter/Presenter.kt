/*
 * Created by Miguel Bronzovic
 *
 * Presenter's contract methods
 */
package com.topfeeds.sample.presenter

import android.os.Bundle

interface Presenter {

    fun loadDataItems()

    fun onStop()

    fun showError(error: String)

    fun onLoadMoreItems(totalItems: Int, after: String)

    fun onRestoreState(bundle: Bundle)
}