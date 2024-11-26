package com.webcinema.controller;

import com.webcinema.dto.MovieDto;
import com.webcinema.model.Movie;
import com.webcinema.model.MovieActors;
import com.webcinema.model.MovieGenres;
import com.webcinema.service.movie.IMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final IMovieService movieService;

    @PostMapping("/add_movie")
    public ResponseEntity<MovieDto> addMovie(@RequestParam("nameMovie") String nameMovie,
                                             @RequestParam("detail") String detail,
                                             @RequestParam("imgMovie")MultipartFile imgMovie,
                                             @RequestParam("directorId") Long directorId,
                                             @RequestParam("listActor")List<Long> listActor,
                                             @RequestParam("listGenre") List<Long> listGenre,
                                             @RequestParam("duration") int duration,
                                             @RequestParam("trailerURL") String trailerURL
                                             ) throws SQLException, IOException {
        Movie movie = movieService.addMovie(nameMovie, detail, imgMovie, directorId, listActor, listGenre, duration, trailerURL);
        MovieDto movieDto = new MovieDto();
        if(movie != null){
            movieDto.setStatus("Thêm thành công!");
            movieDto.setName(movie.getName());
            List<String> nameActors = new ArrayList<>();
            for(MovieActors movieActor : movie.getMovieActors()){
                String nameActor = movieActor.getActor().getFullName();
                nameActors.add(nameActor);
            }
            movieDto.setActorNames(nameActors);

            List<String> nameGenres = new ArrayList<>();
            for(MovieGenres movieGenre : movie.getMovieGenres()){
                String nameGenre = movieGenre.getGenre().getNameGenre();
                nameGenres.add(nameGenre);
            }
            movieDto.setGenreNames(nameGenres);
        } else{
            movieDto.setStatus("Thêm thất bại!");
        }

        return ResponseEntity.ok(movieDto);
    }

    @PutMapping("/update_movie/{movieId}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long movieId,
                                                @RequestParam("nameMovie") String nameMovie,
                                                @RequestParam("detail") String detail,
                                                @RequestParam("imgMovie")MultipartFile imgMovie,
                                                @RequestParam("directorId") Long directorId,
                                                @RequestParam("listActor")List<Long> listActor,
                                                @RequestParam("listGenre") List<Long> listGenre,
                                                @RequestParam("duration") int duration,
                                                @RequestParam("trailerURL") String trailerURL,
                                                @RequestParam("isShowing") int isShowing
                                                ) throws IOException, SQLException {
        byte[] photobytes = imgMovie != null && !imgMovie.isEmpty() ? imgMovie.getBytes() : movieService.getMoviePhotoById(movieId);

        Movie movie = movieService.updateMovie(movieId, nameMovie, detail, photobytes, directorId, listActor, listGenre, duration, trailerURL, isShowing);
        MovieDto movieDto = new MovieDto();
        if(movie != null){
            movieDto.setStatus("Cập nhật thành công!");
            movieDto.setName(movie.getName());
            if(movie.getMovieActors() != null){
                List<String> nameActors = new ArrayList<>();
                for(MovieActors movieActor : movie.getMovieActors()){
                    String nameActor = movieActor.getActor().getFullName();
                    nameActors.add(nameActor);
                }
                movieDto.setActorNames(nameActors);
            }

            if(movie.getMovieGenres() != null){
                List<String> nameGenres = new ArrayList<>();
                for(MovieGenres movieGenre : movie.getMovieGenres()){
                    String nameGenre = movieGenre.getGenre().getNameGenre();
                    nameGenres.add(nameGenre);
                }
                movieDto.setGenreNames(nameGenres);
            }
            movieDto.setIsShowing(movie.getIsShowing());
        } else {
            movieDto.setStatus("Cập nhật thất bại!");
        }

        return ResponseEntity.ok(movieDto);
    }

    @DeleteMapping("/delete_movie/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId){
        movieService.deleteMovie(movieId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/allMovie")
    public ResponseEntity<List<MovieDto>> getAllMovie() throws SQLException {
        List<Movie> movies = movieService.allMovie();
        List<MovieDto> movieDtos = new ArrayList<>();

        for(Movie movie : movies){
            MovieDto movieDto = new MovieDto();

            movieDto.setId(movie.getId());
            movieDto.setDetail(movie.getDetail());
            movieDto.setDuration(movie.getDuration());
            movieDto.setIsShowing(movie.getIsShowing());

            Blob imgMovie = movie.getImageURL();
            byte[] photobytes = imgMovie.getBytes(1, (int)imgMovie.length());
            if(photobytes.length > 0 && photobytes != null){
                String imgBase64 = Base64.getEncoder().encodeToString(photobytes);

                movieDto.setImageURL(imgBase64);
            }
            movieDto.setTrailerURL(movie.getTrailerURL());
            movieDto.setDirectorName(movie.getDirector().getFullName());

            List<String> allNameActor = new ArrayList<>();
            List<Long> allIdActor = new ArrayList<>();
            for(MovieActors movieActor : movie.getMovieActors()){
                String nameActor = movieActor.getActor().getFullName();
                Long idActor = movieActor.getActor().getId();
                allIdActor.add(idActor);
                allNameActor.add(nameActor);

            }
            movieDto.setActorNames(allNameActor);
            movieDto.setActorId(allIdActor);


            List<String> allNameGenre = new ArrayList<>();
            List<Long> allIdGenre = new ArrayList<>();
            for(MovieGenres movieGenre : movie.getMovieGenres()){
                String nameGenre = movieGenre.getGenre().getNameGenre();
                Long idGenre = movieGenre.getGenre().getId();
                allIdGenre.add(idGenre);
                allNameGenre.add(nameGenre);

            }
            movieDto.setActorNames(allNameGenre);
            movieDto.setActorId(allIdGenre);

            movieDtos.add(movieDto);
        }

        return ResponseEntity.ok(movieDtos);
    }

    @GetMapping("/allMovieOpen")
    public ResponseEntity<List<MovieDto>> getAllMovieOpen() throws SQLException {
        List<Movie> movies = movieService.allMovieOpen();
        List<MovieDto> movieDtos = new ArrayList<>();

        for(Movie movie : movies){
            MovieDto movieDto = new MovieDto();

            movieDto.setId(movie.getId());
            movieDto.setDetail(movie.getDetail());
            movieDto.setDuration(movie.getDuration());
            movieDto.setIsShowing(movie.getIsShowing());

            Blob imgMovie = movie.getImageURL();
            byte[] photobytes = imgMovie.getBytes(1, (int)imgMovie.length());
            if(photobytes.length > 0 && photobytes != null){
                String imgBase64 = Base64.getEncoder().encodeToString(photobytes);

                movieDto.setImageURL(imgBase64);
            }
            movieDto.setTrailerURL(movie.getTrailerURL());
            movieDto.setDirectorName(movie.getDirector().getFullName());

            List<String> allNameActor = new ArrayList<>();
            List<Long> allIdActor = new ArrayList<>();
            for(MovieActors movieActor : movie.getMovieActors()){
                String nameActor = movieActor.getActor().getFullName();
                Long idActor = movieActor.getActor().getId();
                allIdActor.add(idActor);
                allNameActor.add(nameActor);

            }
            movieDto.setActorNames(allNameActor);
            movieDto.setActorId(allIdActor);


            List<String> allNameGenre = new ArrayList<>();
            List<Long> allIdGenre = new ArrayList<>();
            for(MovieGenres movieGenre : movie.getMovieGenres()){
                String nameGenre = movieGenre.getGenre().getNameGenre();
                Long idGenre = movieGenre.getGenre().getId();
                allIdGenre.add(idGenre);
                allNameGenre.add(nameGenre);

            }
            movieDto.setActorNames(allNameGenre);
            movieDto.setActorId(allIdGenre);

            movieDtos.add(movieDto);
        }

        return ResponseEntity.ok(movieDtos);
    }

    @GetMapping("/allMovieClose")
    public ResponseEntity<List<MovieDto>> getAllMovieClose() throws SQLException {
        List<Movie> movies = movieService.allMovieClose();
        List<MovieDto> movieDtos = new ArrayList<>();

        for(Movie movie : movies){
            MovieDto movieDto = new MovieDto();

            movieDto.setId(movie.getId());
            movieDto.setDetail(movie.getDetail());
            movieDto.setDuration(movie.getDuration());
            movieDto.setIsShowing(movie.getIsShowing());

            Blob imgMovie = movie.getImageURL();
            byte[] photobytes = imgMovie.getBytes(1, (int)imgMovie.length());
            if(photobytes.length > 0 && photobytes != null){
                String imgBase64 = Base64.getEncoder().encodeToString(photobytes);

                movieDto.setImageURL(imgBase64);
            }
            movieDto.setTrailerURL(movie.getTrailerURL());
            movieDto.setDirectorName(movie.getDirector().getFullName());

            List<String> allNameActor = new ArrayList<>();
            List<Long> allIdActor = new ArrayList<>();
            for(MovieActors movieActor : movie.getMovieActors()){
                String nameActor = movieActor.getActor().getFullName();
                Long idActor = movieActor.getActor().getId();
                allIdActor.add(idActor);
                allNameActor.add(nameActor);

            }
            movieDto.setActorNames(allNameActor);
            movieDto.setActorId(allIdActor);


            List<String> allNameGenre = new ArrayList<>();
            List<Long> allIdGenre = new ArrayList<>();
            for(MovieGenres movieGenre : movie.getMovieGenres()){
                String nameGenre = movieGenre.getGenre().getNameGenre();
                Long idGenre = movieGenre.getGenre().getId();
                allIdGenre.add(idGenre);
                allNameGenre.add(nameGenre);

            }
            movieDto.setActorNames(allNameGenre);
            movieDto.setActorId(allIdGenre);

            movieDtos.add(movieDto);
        }

        return ResponseEntity.ok(movieDtos);
    }
}
