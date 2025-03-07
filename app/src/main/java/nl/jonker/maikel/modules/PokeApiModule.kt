package nl.jonker.maikel.pokedex.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.jonker.maikel.services.PokemonService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date


@Module
@InstallIn(SingletonComponent::class) // Install in singleton component, means only one instance per provided binding
object ApiModule {
    private const val BASE_URL = "https://pokeapi.co/api/"
    // Define Moshi here so you only have to define it once, and inject and reuse elsewhere
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder() // Notice the icon in the IDE on the left side, click it to see where it is injected
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .add(KotlinJsonAdapterFactory())
        .build()
    // The moshi argument here is injected
    @Provides
    fun providePokemonService(moshi: Moshi): PokemonService = Retrofit.Builder() // Click icon to check where the injection originated from
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(PokemonService::class.java)
}