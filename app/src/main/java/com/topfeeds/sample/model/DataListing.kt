/*
 * Created by Miguel Bronzovic
 *
 * Data models
 */
package com.topfeeds.sample.model

import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Parcel
data class DataListing @ParcelConstructor constructor(val children: List<Children>, val after: String, val before: String)