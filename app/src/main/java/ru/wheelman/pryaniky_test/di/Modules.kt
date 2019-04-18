package ru.wheelman.pryaniky_test.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.wheelman.pryaniky_test.model.network.IRemoteDataSource
import ru.wheelman.pryaniky_test.model.network.RemoteApi
import ru.wheelman.pryaniky_test.model.network.RemoteDataSource

val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://prnk.blob.core.windows.net")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
    }

    single { get<Retrofit>().create(RemoteApi::class.java) }

    single { RemoteDataSource(get()) as IRemoteDataSource }

}