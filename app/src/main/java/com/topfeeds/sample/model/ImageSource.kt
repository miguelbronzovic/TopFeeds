/*
 * Created by Miguel Bronzovic
 *
 * Data models
 */
package com.topfeeds.sample.model

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class ImageSource @ParcelConstructor constructor(val url: String)