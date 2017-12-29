/*
 * Created by Miguel Bronzovic
 *
 * Main screen, loads reddit top feeds
 */
package com.topfeeds.sample.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.topfeeds.sample.Constants
import com.topfeeds.sample.R
import com.topfeeds.sample.model.Children
import com.topfeeds.sample.presenter.TopNewsPresenter
import com.topfeeds.sample.presenter.TopNewsView
import com.topfeeds.sample.ui.adapter.ChildrenAdapter
import com.topfeeds.sample.ui.adapter.EndlessRecyclerViewScrollListener
import com.topfeeds.sample.ui.adapter.ThumbnailItemClickListener
import kotlinx.android.synthetic.main.activity_main.*
import org.parceler.Parcels

class MainActivity : AppCompatActivity(), TopNewsView, ThumbnailItemClickListener {

    private val topNewsPresenter = TopNewsPresenter(this)
    private val linearLayoutManager = LinearLayoutManager(this)
    private val scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
            Handler().postDelayed({
                topNewsPresenter.onLoadMoreItems(totalItemsCount, afterValue)
            }, 500)
        }
    }

    private lateinit var progressDialog: ProgressDialog
    private lateinit var adapter: ChildrenAdapter
    private lateinit var afterValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setup recyclerview
        adapter = ChildrenAdapter(this)

        news.adapter = adapter
        news.layoutManager = linearLayoutManager
        news.setHasFixedSize(false)
        news.addOnScrollListener(scrollListener)

        if (savedInstanceState != null) {
            topNewsPresenter.onRestoreState(savedInstanceState)
        } else {
            topNewsPresenter.loadDataItems()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constants.AFTER_VALUE, afterValue)
        outState.putParcelable(Constants.CHILDREN_ITEMS, Parcels.wrap(adapter.childrenItems))
    }

    override fun showProgressBar() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage(resources.getString(R.string.progress_dialog_message))
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    override fun hideProgressBar() = progressDialog.dismiss()

    override fun showErrorMessage(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG)
    }

    override fun loadDataItems(items: List<Children>, after: String) {
        afterValue = after

        if (adapter.childrenItems != null) {
            adapter.insertItems(items)
        } else {
            adapter.addNewItems(items)
        }
    }

    override fun onThumbnailClick(url: String) = WebViewActivity.start(this, url)
}