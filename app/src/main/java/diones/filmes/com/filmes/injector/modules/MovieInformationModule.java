package diones.filmes.com.filmes.injector.modules;

import dagger.Module;
import dagger.Provides;
import diones.filmes.com.filmes.domain.MovieDetailUsecase;
import diones.filmes.com.filmes.injector.Activity;
import diones.filmes.com.filmes.model.repository.MovieRepository;

@Module
public class MovieInformationModule {

    private final int mMovieId;

    public MovieInformationModule(int movieId) {
        mMovieId = movieId;
    }

    @Provides @Activity
    MovieDetailUsecase provideGetCharacterInformationUsecase (
            MovieRepository movieRepository) {

        return new MovieDetailUsecase(mMovieId, movieRepository);
    }

}
