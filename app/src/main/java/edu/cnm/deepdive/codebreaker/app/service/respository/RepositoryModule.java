package edu.cnm.deepdive.codebreaker.app.service.respository;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public interface RepositoryModule {

  @Binds
  GameSummaryRepository bindGameSummaryRepository(GameSummaryRepositoryImpl implementation);

}
