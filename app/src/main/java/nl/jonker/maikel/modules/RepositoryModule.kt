package nl.jonker.maikel.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.jonker.maikel.repositories.FavouritesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideFavoritesRepository(@ApplicationContext context: Context): FavouritesRepository {
        return FavouritesRepository(context)
    }
}