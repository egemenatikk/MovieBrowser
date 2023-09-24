/*
 * Copyright 2022 Commencis. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Commencis.
 * Any reproduction of this material must contain this notice.
 */

package com.atike.moviebrowser.movieoverview

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class MovieType : Parcelable {
    UPCOMING,
    NOW_PLAYING,
    POPULAR,
    TOP_RATED
}
