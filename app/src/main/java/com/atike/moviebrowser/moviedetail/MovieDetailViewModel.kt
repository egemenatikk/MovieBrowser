/*
 * Copyright 2022 Commencis. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Commencis.
 * Any reproduction of this material must contain this notice.
 */

package com.atike.moviebrowser.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atike.moviebrowser.network.Company
import com.atike.moviebrowser.network.Genre
import com.atike.moviebrowser.network.Language
import com.atike.moviebrowser.network.Movie
import com.atike.moviebrowser.network.MovieApi
import com.atike.moviebrowser.network.MovieCastMember
import com.atike.moviebrowser.network.MovieCredits
import com.atike.moviebrowser.network.MovieCrewMember
import com.atike.moviebrowser.network.MovieDetailModel
import com.atike.moviebrowser.network.MovieReviewModel
import com.atike.moviebrowser.network.MovieReviewResponseModel
import kotlinx.coroutines.launch

private const val YEAR_END_INDEX = 4
private const val MONTH_START_INDEX = 5
private const val MONTH_END_INDEX = 7
private const val DAY_START_INDEX = 8
private const val MINUTES_IN_AN_HOUR = 60
private const val THREE_DIGIT = 3
private const val REVIEW_DATE_WITHOUT_TIME_INDEX = 10

class MovieDetailViewModel(movieId: Int?) : ViewModel() {

    private val _movieDetail = MutableLiveData<MovieDetailModel?>()
    val movieDetail: LiveData<MovieDetailModel?>
        get() = _movieDetail

    val movieTitle: LiveData<String> = Transformations.map(_movieDetail) {
        it?.title.orEmpty()
    }

    val urlBackdrop: LiveData<String> = Transformations.map(_movieDetail) {
        completeImageUrl(it?.backdropPath.orEmpty())
    }

    val genres: LiveData<String> = Transformations.map(_movieDetail) {
        makeTagsSentence(it?.genres.orEmpty())
    }

    val urlPoster: LiveData<String> = Transformations.map(_movieDetail) {
        completeImageUrl(it?.posterPath.orEmpty())
    }

    val releaseDate: LiveData<String> = Transformations.map(_movieDetail) {
        getReleaseDateString(it?.releaseDate.orEmpty())
    }

    val runtime: LiveData<String> = Transformations.map(_movieDetail) {
        getRuntimeString(it?.runtime)
    }

    val budget: LiveData<String> = Transformations.map(_movieDetail) {
        formatMoneyAmount(it?.budget)
    }

    val revenue: LiveData<String> = Transformations.map(_movieDetail) {
        formatMoneyAmount(it?.revenue)
    }

    val language: LiveData<String> = Transformations.map(_movieDetail) {
        getLanguage(it?.spokenLanguages.orEmpty(), it?.originalLanguage.orEmpty())
    }

    val status: LiveData<String> = Transformations.map(_movieDetail) {
        it?.status.orEmpty()
    }

    val popularity: LiveData<String> = Transformations.map(_movieDetail) {
        getPopularityString(it?.popularity)
    }

    val imdbScore: LiveData<String> = Transformations.map(_movieDetail) {
        formatImdbScore(it?.voteAverage)
    }

    val productionCompanies: LiveData<String> = Transformations.map(_movieDetail) {
        getProductionCompaniesString(it?.productionCompanies.orEmpty())
    }

    val originalTitle: LiveData<String> = Transformations.map(_movieDetail) {
        it?.originalTitle.orEmpty()
    }

    val overview: LiveData<String> = Transformations.map(_movieDetail) {
        it?.overview.orEmpty()
    }

    private val _movieCredits = MutableLiveData<MovieCredits>()

    val castMembers: LiveData<List<MovieCastMember>> = Transformations.map(_movieCredits) {
        it?.cast.orEmpty()
    }

    val crewMembers: LiveData<List<MovieCrewMember>> = Transformations.map(_movieCredits) {
        it?.crew.orEmpty()
    }

    private val _recommendedMovies = MutableLiveData<List<Movie>>()
    val recommendedMovies: LiveData<List<Movie>>
        get() = _recommendedMovies

    private val _similarMovies = MutableLiveData<List<Movie>>()
    val similarMovies: LiveData<List<Movie>>
        get() = _similarMovies

    private val _reviews = MutableLiveData<MovieReviewResponseModel?>()
    val reviews: LiveData<String> = Transformations.map(_reviews) {
        getReviewString(it?.results.orEmpty())
    }

    init {
        getDetailedMovie(movieId)
    }

    private fun getDetailedMovie(movieId: Int?) {
        movieId ?: return
        viewModelScope.launch {
            _movieDetail.value = runCatching {
                MovieApi.retrofitService.getMovieDetail(movieId)
            }.getOrNull()
            _movieCredits.value = runCatching {
                MovieApi.retrofitService.getMovieCredits(movieId)
            }.getOrNull()
            _recommendedMovies.value = runCatching {
                MovieApi.retrofitService.getRecommendedMovies(movieId).results
            }.getOrNull()
            _similarMovies.value = runCatching {
                MovieApi.retrofitService.getSimilarMovies(movieId).results
            }.getOrNull()
            _reviews.value = runCatching {
                MovieApi.retrofitService.getReviews(movieId)
            }.getOrNull()
        }
    }

    private fun completeImageUrl(imagePath: String?): String {
        imagePath?.let {
            return "https://www.themoviedb.org/t/p/original$it"
        }

        return "https://developer.android.com/static/codelabs" +
                "/basic-android-kotlin-training-internet-images/img/467c213c859e1904.png"
    }

    private fun formatImdbScore(imdbScore: Double?): String {
        return imdbScore?.let {
            "${it.toBigDecimal().toPlainString()} ★"
        }.orEmpty()
    }

    private fun makeTagsSentence(genres: List<Genre>?): String {
        return genres.orEmpty().joinToString(separator = " | ") { it.name.toString() }
    }

    private fun getReleaseYear(releaseDate: String?): String {
        return if (releaseDate.orEmpty().length > YEAR_END_INDEX) {
            releaseDate!!.substring(0, YEAR_END_INDEX)
        } else {
            ""
        }
    }

    private fun getReleaseDateString(releaseDate: String?): String {
        releaseDate?.let {
            val releaseYear = getReleaseYear(it)
            var releaseMonth = ""
            if (it.length > MONTH_START_INDEX && it.length > MONTH_END_INDEX) {
                releaseMonth = it.substring(MONTH_START_INDEX, MONTH_END_INDEX)
            }
            var releaseDay = ""
            if (it.length > DAY_START_INDEX) {
                releaseDay = it.substring(DAY_START_INDEX)
                if (releaseDay.first() == '0' && releaseDay.length > 1) {
                    releaseDay = releaseDay.substring(1)
                }
            }
            val releaseMonthString = getMonthString(releaseMonth)
            if (releaseYear.isNotEmpty() && releaseMonth.isNotEmpty() && releaseDay.isNotEmpty()) {
                return "$releaseDay $releaseMonthString $releaseYear"
            }
        }
        return ""
    }

    private fun getMonthString(month: String): String {
        return when (month) {
            "01" -> "Jan"
            "02" -> "Feb"
            "03" -> "Mar"
            "04" -> "Apr"
            "05" -> "May"
            "06" -> "Jun"
            "07" -> "Jul"
            "08" -> "Aug"
            "09" -> "Sep"
            "10" -> "Oct"
            "11" -> "Nov"
            "12" -> "Dec"
            else -> ""
        }
    }

    private fun getRuntimeString(runtime: Int?): String {
        runtime ?: return ""
        val hours = runtime.div(MINUTES_IN_AN_HOUR)
        val minutes = runtime.rem(MINUTES_IN_AN_HOUR)

        return if (hours == 0) {
            "${minutes}min"
        } else {
            "${hours}h ${minutes}min"
        }
    }

    private fun formatMoneyAmount(moneyAmount: Int?): String {
        moneyAmount ?: return ""
        val moneyAmountString = moneyAmount.toString()
        var moneyAmountStringFormatted = ""
        var index = moneyAmountString.length

        while (index > THREE_DIGIT) {
            moneyAmountStringFormatted = ",${moneyAmountString.substring(index - THREE_DIGIT, index)}" +
                    moneyAmountStringFormatted
            index -= THREE_DIGIT
        }

        return "$${moneyAmountString.substring(0, index)}$moneyAmountStringFormatted"

    }

    private fun getLanguage(spokenLanguages: List<Language>?, originalLanguage: String?): String {
        spokenLanguages ?: return ""
        originalLanguage ?: return ""
        var spokenLanguageReturn = ""
        for (spokenLanguage in spokenLanguages) {
            if (spokenLanguage.languageCode.equals(originalLanguage)) {
                spokenLanguageReturn = spokenLanguage.englishName!!
                break
            }
        }
        return spokenLanguageReturn
    }

    private fun getPopularityString(popularity: Double?): String {
        popularity?.let {
            return it.toBigDecimal().toPlainString() + " \uD83D\uDCC8"
        }
        return ""
    }

    private fun getProductionCompaniesString(productionCompanies: List<Company>?): String {
        productionCompanies ?: return ""
        var productionCompaniesString = ""

        for (productionCompany in productionCompanies) {
            productionCompaniesString += "${productionCompany.name}\n"
        }

        return productionCompaniesString

    }

    private fun getReviewString(reviewList: List<MovieReviewModel>?): String {
        reviewList ?: return ""
        var returnString = ""
        reviewList.forEach { review ->
            returnString +=
            """
                
                Author name: ${review.author}

                Review date: ${getReleaseDateString(review.createdAt?.substring(0, REVIEW_DATE_WITHOUT_TIME_INDEX))}

                ${review.content}
                -----
                
            """.trimIndent()
        }
        return returnString
    }

}
