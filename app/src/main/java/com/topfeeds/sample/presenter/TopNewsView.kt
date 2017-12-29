/*
 * Created by Miguel Bronzovic
 *
 * Expose methods to handle Activity widgets
 */
package com.topfeeds.sample.presenter

import com.topfeeds.sample.model.Children

interface TopNewsView {
    fun showProgressBar()

    fun hideProgressBar()

    fun showErrorMessage(error: String)

    fun loadDataItems(items: List<Children>, after: String)
}