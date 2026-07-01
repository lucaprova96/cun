package com.luce.providers

import com.luce.adapters.AppAdapter
import com.luce.models.Category
import com.luce.models.Episode
import com.luce.models.Genre
import com.luce.models.Movie
import com.luce.models.People
import com.luce.models.TvShow
import com.luce.models.Video
import kotlinx.coroutines.sync.Mutex

interface ProviderPortalUrl {
    val portalUrl: String
    val defaultPortalUrl: String
}

interface ProviderConfigUrl {
    val defaultBaseUrl: String

    suspend fun onChangeUrl(forceRefresh: Boolean = false): String
    val changeUrlMutex: Mutex
}

interface IptvProvider : Provider

interface Provider {

    val baseUrl: String
    val name: String
    val logo: String
    val language: String

    suspend fun getHome(): List<Category>

    suspend fun search(query: String, page: Int = 1): List<AppAdapter.Item>

    suspend fun getMovies(page: Int = 1): List<Movie>

    suspend fun getTvShows(page: Int = 1): List<TvShow>

    suspend fun getMovie(id: String): Movie

    suspend fun getTvShow(id: String): TvShow

    suspend fun getEpisodesBySeason(seasonId: String): List<Episode>

    suspend fun getGenre(id: String, page: Int = 1): Genre

    suspend fun getPeople(id: String, page: Int = 1): People

    suspend fun getServers(id: String, videoType: Video.Type): List<Video.Server>

    suspend fun getVideo(server: Video.Server): Video

    companion object {
        data class ProviderSupport(
            val movies: Boolean,
            val tvShows: Boolean
        )

        val providers = mapOf(
            StreamingCommunityProvider("it") to ProviderSupport(movies = true, tvShows = true),
            AnimeWorldProvider to ProviderSupport(movies = true, tvShows = true),
            Altadefinizione01Provider to ProviderSupport(movies = true, tvShows = true),
            GuardaFlixProvider to ProviderSupport(movies = true, tvShows = false),
            CB01Provider to ProviderSupport(movies = true, tvShows = true),
            AnimeUnityProvider to ProviderSupport(movies = true, tvShows = true),
            AnimeSaturnProvider to ProviderSupport(movies = false, tvShows = true),
            GuardaSerieProvider to ProviderSupport(movies = true, tvShows = true),
            PlutoTvItProvider to ProviderSupport(movies = false, tvShows = true)
        )

        // Helper functions to check support
        fun supportsMovies(provider: Provider): Boolean {
            val support = providers[provider] ?: ProviderSupport(movies = true, tvShows = true)
            return support.movies
        }

        fun supportsTvShows(provider: Provider): Boolean {
            val support = providers[provider] ?: ProviderSupport(movies = true, tvShows = true)
            return support.tvShows
        }

        fun findByName(name: String): Provider? {
            return providers.keys.find { it.name == name }
        }
    }
}
