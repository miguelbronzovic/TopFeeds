/*
 * Created by Miguel Bronzovic
 *
 * Data models
 */
package com.topfeeds.sample.model

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel
data class ImagePreview @ParcelConstructor constructor(val images: List<RedditImage>)