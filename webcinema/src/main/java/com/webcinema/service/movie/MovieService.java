package com.webcinema.service.movie;

import com.webcinema.extension.ResourceNotFoundException;
import com.webcinema.model.*;
import com.webcinema.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService implements IMovieService{
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
    private final MovieActorsRepository movieActorsRepository;
    private final MovieGenresRepository movieGenresRepository;

    @Override
    @Transactional
    public Movie addMovie(String nameMovie, String detail, MultipartFile imgMovie, Long derectorId, List<Long> listActor, List<Long> listGenre, int duration, String trailerURL) throws IOException, SQLException {
        Movie movie = new Movie();

        movie.setName(nameMovie);
        movie.setDetail(detail);
        if(!imgMovie.isEmpty()){
            byte[] bytePhoto = imgMovie.getBytes();
            Blob img = new SerialBlob(bytePhoto);
            movie.setImageURL(img);
        }

        Director director = new Director();
        director = directorRepository.findByIdDirector(derectorId);
        movie.setDirector(director);
        movieRepository.save(movie);

        List<MovieActors> movieActors = new ArrayList<>();
        for(Long actorId : listActor){
            Actor actor = new Actor();
            actor = actorRepository.findByIdActor(actorId);
            MovieActors movieActor = new MovieActors();
            movieActor.setMovie(movie);
            movieActor.setActor(actor);
            movieActors.add(movieActor);
        }
        movie.setMovieActors(movieActors);

        List<MovieGenres> movieGenres = new ArrayList<>();
        for(Long genreId : listGenre){
            Genre genre = genreRepository.findByIdGenre(genreId);
            MovieGenres movieGenre = new MovieGenres();
            movieGenre.setGenre(genre);
            movieGenre.setMovie(movie);
            movieGenres.add(movieGenre);
        }
        movie.setMovieGenres(movieGenres);

        movie.setDuration(duration);
        movie.setTrailerURL(trailerURL);
        movie.setIsShowing(0);

        return movieRepository.save(movie);
    }

    @Override
    @Transactional
    public Movie updateMovie(Long movieId, String nameMovie, String detail, byte[] bytePhoto, Long directorId, List<Long> listActor, List<Long> listGenre, int duration, String trailerURL, int isShowing) {

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim!"));

        if(nameMovie != null) movie.setName(nameMovie);
        if(detail != null) movie.setDetail(detail);
        if(bytePhoto != null && bytePhoto.length > 0){
            try {
                movie.setImageURL(new SerialBlob(bytePhoto));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        Director director = directorRepository.findByIdDirector(directorId);
        movie.setDirector(director);


        if(listActor != null){
            movieActorsRepository.deleteByMovieId(movie.getId());
            List<MovieActors> movieActors = new ArrayList<>();

            for(Long actorId : listActor){
                Actor actor = actorRepository.findByIdActor(actorId);
                if (actor == null) {
                    throw new ResourceNotFoundException("Không tìm thấy diễn viên với ID: " + actorId);
                }

                MovieActors movieActorNew = new MovieActors();

                movieActorNew.setActor(actor);
                movieActorNew.setMovie(movie);
                movieActors.add(movieActorNew);
            }

            movie.setMovieActors(movieActors);
        }

        if(listGenre != null){
            movieGenresRepository.deleteByMovieId(movie.getId());

            List<MovieGenres> movieGenres = new ArrayList<>();
            for (Long genreId : listGenre) {
                Genre genre = genreRepository.findByIdGenre(genreId);
                if (genre == null) {
                    throw new ResourceNotFoundException("Không tìm thấy thể loại phim với ID: " + genreId);
                }

                MovieGenres movieGenreNew = new MovieGenres();
                movieGenreNew.setGenre(genre);
                movieGenreNew.setMovie(movie);

                movieGenres.add(movieGenreNew);
            }

            movie.setMovieGenres(movieGenres);
        }

        if(duration != 0) movie.setDuration(duration);
        if(trailerURL != null) movie.setTrailerURL(trailerURL);
        if(isShowing == 0 || isShowing == 1) movie.setIsShowing(isShowing);

        return movieRepository.save(movie);
    }

    @Override
    public byte[] getMoviePhotoById(Long movieId) throws SQLException {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if(movie.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Movie not found!");
        }

        Blob movieBlob = movie.get().getImageURL();

        if(movieBlob != null){
            byte[] photobytes = movieBlob.getBytes(1, (int)movieBlob.length());
            return photobytes;
        }
        return new byte[0];
    }

    @Override
    public void deleteMovie(Long movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);

        if(movie.isPresent()){
            movieRepository.deleteById(movieId);
        }
    }

    @Override
    public List<Movie> allMovie() {

        List<Movie> movies = new ArrayList<>();
        movies = movieRepository.findAll();
        return movies;
    }

    @Override
    public List<Movie> allMovieOpen() {
        List<Movie> movies = movieRepository.findAllByMovieOpen();

        return movies;
    }

    @Override
    public List<Movie> allMovieClose() {
        List<Movie> movies = movieRepository.findAllByMovieClose();
        return movies;
    }

    @Override
    @Transactional
    public List<MovieActors> allMovieActors(Long movieId) {
        List<MovieActors> movieActors = movieActorsRepository.findMovieActorsByMovieId(movieId);

        return movieActors;
    }

    @Override
    @Transactional
    public List<MovieGenres> allMovieGenres(Long movieId) {
        List<MovieGenres> movieGenres = movieGenresRepository.findMovieGenresByMovieId(movieId);
        return movieGenres;
    }


}


