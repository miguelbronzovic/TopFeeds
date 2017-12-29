/*
 * Created by Miguel Bronzovic
 *
 * Webview screen component
 */
package com.topfeeds.sample.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import com.topfeeds.sample.R
import com.topfeeds.sample.R.id.action_save_image
import kotlinx.android.synthetic.main.activity_web_view.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber

private const val URL = "url"
private const val REQUEST_WRITE_PERMISSIONS = 9999

class WebViewActivity : AppCompatActivity() {

    private lateinit var urlToLoad: String

    companion object {

        fun start(callerCtx: Context, url: String) {
            val intent = Intent(callerCtx, WebViewActivity::class.java)
            intent.putExtra(URL, url)
            callerCtx.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        if (savedInstanceState != null) {
            urlToLoad = savedInstanceState.getString(URL)
        } else {
            urlToLoad = intent.getStringExtra(URL)
        }

        webview.settings.javaScriptEnabled = true
        webview.settings.loadWithOverviewMode = true
        webview.settings.useWideViewPort = true
        webview.loadUrl(urlToLoad)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(URL, urlToLoad)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_webview, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        return if (id == action_save_image) {
            requestApplicationPermissions()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            saveImageInGallery()
        } else {
            Toast.makeText(this, getString(R.string.write_storage_permission_error), Toast.LENGTH_LONG).show()
        }
    }

    private fun requestApplicationPermissions() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_PERMISSIONS)
    }

    /**
     * Saves Image in gallery
     */
    private fun saveImageInGallery() {
        Observable.fromCallable {
            val previewUrl = java.net.URL(urlToLoad)
            val bitmap = BitmapFactory.decodeStream(previewUrl.openConnection().getInputStream())
            MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Title", "Description")
        }
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe ({ _ ->
            Toast.makeText(this, getString(R.string.save_image_download_complete), Toast.LENGTH_SHORT).show()
        }, { error ->
            error.message?.let { Timber.e("Error saving image in gallery: $it") }
            Toast.makeText(this, getString(R.string.save_image_download_fail), Toast.LENGTH_SHORT).show()
        })
    }
}
