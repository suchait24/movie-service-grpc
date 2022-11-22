package com.movie.service.grpc;

import com.grpc.demo.models.Movie;
import com.grpc.demo.models.MovieRequest;
import com.grpc.demo.models.MovieResponse;
import com.grpc.demo.service.MovieServiceGrpc.MovieServiceImplBase;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;


@Slf4j
@GrpcService
public class MovieService extends MovieServiceImplBase {

    private static List<Movie> moviesList = new ArrayList<>();

    static {

        moviesList.add(Movie.newBuilder().setMovieId(12).setName("movie-1").setDirector("director-1")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(13).setName("movie-2").setDirector("director-2")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(14).setName("movie-3").setDirector("director-3")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(15).setName("movie-4").setDirector("director-4")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(16).setName("movie-5").setDirector("director-5")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(17).setName("movie-6").setDirector("director-6")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(18).setName("movie-7").setDirector("director-7")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(19).setName("movie-8").setDirector("director-8")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(20).setName("movie-11").setDirector("director-1")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(21).setName("movie-22").setDirector("director-2")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(22).setName("movie-33").setDirector("director-3")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(23).setName("movie-44").setDirector("director-4")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(24).setName("movie-55").setDirector("director-5")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(25).setName("movie-66").setDirector("director-6")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(26).setName("movie-77").setDirector("director-7")
                .addAllActors(List.of("actor-1","actor-2")).build());

        moviesList.add(Movie.newBuilder().setMovieId(27).setName("movie-8").setDirector("director-8")
                .addAllActors(List.of("actor-1","actor-2")).build());

    }

    @Override
    public void getMovies(MovieRequest request, StreamObserver<MovieResponse> responseObserver) {

        log.info("Inside movie-service getMovies() method.");

        if(isNull(request)) {
            //throw ex
        }

        int offset = request.getOffset();
        int count  = request.getCount();

        List<Movie> movies = getPage(moviesList, offset, count);

        MovieResponse movieResponse = MovieResponse.newBuilder()
                .setOffset(offset)
                .setCount(count)
                .addAllMovies(movies)
                .build();

        responseObserver.onNext(movieResponse);
        responseObserver.onCompleted();
    }

    public List<Movie> getPage(List<Movie> sourceList, int page, int pageSize) {

        int fromIndex = (page - 1) * pageSize;
        if(sourceList == null || sourceList.size() < fromIndex){
            return Collections.emptyList();
        }
        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }
}
