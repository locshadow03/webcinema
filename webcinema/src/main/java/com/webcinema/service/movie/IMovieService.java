package com.webcinema.service.movie;

import com.webcinema.model.Movie;
import com.webcinema.model.MovieActors;
import com.webcinema.model.MovieGenres;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IMovieService {

    Movie addMovie(String nameMovie, String detail, MultipartFile imgMovie, Long directorId, List<Long> listActor, List<Long> listGenre, int duration, String trailerURL) throws IOException, SQLException;

    Movie updateMovie(Long movieID, String nameMovie, String detail, byte[] bytePhoto, Long directorId, List<Long> listActor, List<Long> listGenre, int duration, String trailerURL, int isShowing);

    byte[] getMoviePhotoById(Long movieId) throws SQLException;

    void deleteMovie(Long movieId);

    List<Movie> allMovie();

    List<Movie> allMovieOpen();

    List<Movie> allMovieClose();

    List<MovieActors> allMovieActors(Long movieId);
    List<MovieGenres> allMovieGenres(Long movieId);
}
