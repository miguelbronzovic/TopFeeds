/*
 * Created by Miguel Bronzovic
 *
 * Data models
 */
package com.topfeeds.sample.model

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel
data class ChildrenData @ParcelConstructor constructor(val thumbnail: String, val title: String, val author: String, val
num_comments: Integer, val created_utc: Long, val preview: ImagePreview?)