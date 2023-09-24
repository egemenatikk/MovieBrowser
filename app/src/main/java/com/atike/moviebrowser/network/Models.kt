/*
 * Copyright 2022 Commencis. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Commencis.
 * Any reproduction of this material must contain this notice.
 */

package com.atike.moviebrowser.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

private const val IMAGE_PREFIX = "https://www.themoviedb.org/t/p/original"

@Parcelize
data class Movie(
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "id") val id: Int?,
    @Json(name = "title") val title: String?,
    @Json(name = "vote_average") val voteAverage: Double?,
) : Parcelable {

    @IgnoredOnParcel
    val urlPoster = "$IMAGE_PREFIX$posterPath"

    @IgnoredOnParcel
    val imdbScore = "${voteAverage?.toBigDecimal()?.toPlainString()} ★"

    @IgnoredOnParcel
    val releaseYear = "${releaseDate?.substring(0,4)}"
}

@Parcelize
data class MovieResponseBody(
    val results: List<Movie>?
) : Parcelable

@Parcelize
data class MovieDetailModel(
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "budget") val budget: Int?,
    @Json(name = "genres") val genres: List<Genre>?,
    @Json(name = "id") val id: Int?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "original_title") val originalTitle: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "popularity") val popularity: Double?,
    @Json(name = "production_companies") val productionCompanies: List<Company>?,
    @Json(name = "revenue") val revenue: Int?,
    @Json(name = "runtime") val runtime: Int?,
    @Json(name = "spoken_languages") val spokenLanguages: List<Language>?,
    @Json(name = "status") val status: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "vote_average") val voteAverage: Double?
) : Parcelable

@Parcelize
data class Genre(
    @Json(name = "name") val name: String?,
    @Json(name = "id") val id: Int?
) : Parcelable

@Parcelize
data class Language(
    @Json(name = "english_name") val englishName: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "iso_639_1") val languageCode: String?
) : Parcelable

@Parcelize
data class Company(
    @Json(name = "name") val name: String?
) : Parcelable

@Parcelize
data class MovieCredits(
    @Json(name = "id") val id: Int?,
    @Json(name = "cast") val cast: List<MovieCastMember>?,
    @Json(name = "crew") val crew: List<MovieCrewMember>?
) : Parcelable

@Parcelize
data class MovieCastMember(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "original_name") val originalName: String?,
    @Json(name = "profile_path") val profilePath: String?,
    @Json(name = "character") val character: String?
) : Parcelable {

    @IgnoredOnParcel
    var profileImageUrl = "$IMAGE_PREFIX$profilePath"
}

@Parcelize
data class MovieCrewMember(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "original_name") val originalName: String?,
    @Json(name = "profile_path") val profilePath: String?,
    @Json(name = "department") val department: String?,
    @Json(name = "job") val job: String?
) : Parcelable {

    @IgnoredOnParcel
    var profileImageUrl = "$IMAGE_PREFIX$profilePath"
}

@Parcelize
data class MovieReviewResponseModel(
    @Json(name = "results") val results: List<MovieReviewModel>?
) : Parcelable

@Parcelize
data class MovieReviewModel(
    @Json(name = "author") val author: String?,
    @Json(name = "content") val content: String?,
    @Json(name = "created_at") val createdAt: String?
) : Parcelable
