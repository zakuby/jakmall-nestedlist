package jakmall.nestedlist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakmall.nestedlist.data.JokeRepository
import jakmall.nestedlist.data.JokeRepositoryImpl
import jakmall.nestedlist.data.remote.JokeRemoteDataSource
import jakmall.nestedlist.data.remote.JokeRemoteDataSourceImpl
import jakmall.nestedlist.data.remote.JokeService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideJokeRemoteDataSource(
        jokeService: JokeService
    ): JokeRemoteDataSource = JokeRemoteDataSourceImpl(jokeService)

    @Provides
    @Singleton
    fun provideJokeRepository(
        jokeRemoteDataSource: JokeRemoteDataSource
    ): JokeRepository = JokeRepositoryImpl(jokeRemoteDataSource)
}