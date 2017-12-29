/*
 * Created by Miguel Bronzovic on 12/26/17.
 *
 * Helper class for dates calculations.
 */
package com.topfeeds.sample.helper

import org.joda.time.DateTime
import org.joda.time.Hours
import org.joda.time.ReadableInstant

class DateHelper {

    companion object {
        /*
         * Calculates elapsed time since input date.
         */
        fun calculateElapsedTimeFromNow(inputDate: Long): Int {
            val readableNow: ReadableInstant = DateTime()
            val readableInput: ReadableInstant = DateTime(inputDate * 1000L)

            return Hours.hoursBetween(readableInput, readableNow).hours
        }
    }
}