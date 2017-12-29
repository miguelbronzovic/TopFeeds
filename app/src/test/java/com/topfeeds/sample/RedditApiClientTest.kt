package com.topfeeds.sample

import com.topfeeds.sample.api.ApiModule
import com.topfeeds.sample.api.RedditApiClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Miguel Bronzovic
 *
 * Api Client test suite
 */
private const val DEFAULT_WAIT_TIME = 30L

class RedditApiClientTest {
    private lateinit var apiClient: RedditApiClient
    private val latch: CountDownLatch = CountDownLatch(1)
    private val called: AtomicBoolean = AtomicBoolean()

    @Before
    fun setUp() {
        apiClient = RedditApiClient(ApiModule.okHttpClient)
    }

    @Test
    fun shouldGetTopNewsListingFromBackend() {
        apiClient.getTopNewsListing(0, 10, null).subscribe { result ->
            Assert.assertEquals(result.kind, "Listing")
            Assert.assertNotNull(result.data)
            Assert.assertNotNull(result.data.children)
            Assert.assertEquals(result.data.children.size, 10)

            latch.countDown()
            called.set(true)
        }
        latch.await(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);
        assertApiWasCalled(called);
    }

    @Test
    fun shouldGetAfterCodeForPaginationFromBackend() {
        var afterValue: String? = null
        var countItems = 0

        apiClient.getTopNewsListing(0, 10, null).subscribe { result ->
            afterValue = result.data.after
            countItems = result.data.children.size

            Assert.assertEquals(result.kind, "Listing")
            Assert.assertNotNull(result.data)
            Assert.assertNotNull(result.data.children)
            Assert.assertEquals(result.data.children.size, 10)
            Assert.assertNotNull(result.data.after)
            Assert.assertNull(result.data.before)

            latch.countDown()
            called.set(true)
        }
        latch.await(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);
        assertApiWasCalled(called);

        apiClient.getTopNewsListing(countItems, 10, afterValue).subscribe { result ->
            Assert.assertEquals(result.kind, "Listing")
            Assert.assertNotNull(result.data)
            Assert.assertNotNull(result.data.children)
            Assert.assertEquals(result.data.children.size, 10)
            Assert.assertNotNull(result.data.after)
            Assert.assertNotNull(result.data.before)

            latch.countDown()
            called.set(true)
        }
    }

    private fun assertApiWasCalled(called: AtomicBoolean) =
            Assert.assertTrue("Network did not respond within " + DEFAULT_WAIT_TIME +
                    " seconds or there is a previous assertion failure. Check previous logs.", called.get())
}