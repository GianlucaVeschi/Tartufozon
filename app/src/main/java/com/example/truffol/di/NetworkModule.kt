package com.example.truffol.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.truffol.network.ShopService
import com.example.truffol.network.TruffleService
import com.example.truffol.network.model.ShopDtoMapper
import com.example.truffol.network.model.TruffleDtoMapper
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

//Not Used Anymore, Here just for ref
const val POSTMAN_DB = "https://761b9ae7-1a9c-4756-ace0-1bae12bfbead.mock.pstmn.io/"
const val HEROKU_DB = "https://my-tartufo-api.herokuapp.com/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @ApplicationContext appContext: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(
                ChuckerInterceptor.Builder(appContext)
                    .collector(ChuckerCollector(appContext))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .connectTimeout(30, TimeUnit.SECONDS) //Backend is really slow
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitShopService(okHttpClient: OkHttpClient): ShopService {
        return Retrofit.Builder()
            .baseUrl(HEROKU_DB)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ShopService::class.java)
    }

    @Singleton
    @Provides
    fun provideShopDtoMapper() : ShopDtoMapper {
        return ShopDtoMapper()
    }

    @Singleton
    @Provides
    fun provideTruffleDtoMapper() : TruffleDtoMapper {
        return TruffleDtoMapper()
    }

    @Singleton
    @Provides
    fun provideRetrofitTruffleService(okHttpClient: OkHttpClient): TruffleService {
        return Retrofit.Builder()
            .baseUrl(HEROKU_DB)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(TruffleService::class.java)
    }

/** Not used anymore, here just for reference */
//    @Singleton
//    @Provides
//    fun provideNetworkDataSource(
//        truffleService: TruffleService,
//        shopService: ShopService,
//        shopDtoMapper: ShopDtoMapper,
//        truffleDtoMapper: TruffleDtoMapper
//    ): RemoteDataSource {
//        return RemoteDataSource(truffleService, shopService,shopDtoMapper,truffleDtoMapper)
//    }

}