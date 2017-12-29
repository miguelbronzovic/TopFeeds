/*
 * Created by Miguel Bronzovic
 *
 * Data models
 */
package com.topfeeds.sample.model

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel(Parcel.Serialization.BEAN)
data class TopNews @ParcelConstructor constructor(val kind: String, val data: DataListing)