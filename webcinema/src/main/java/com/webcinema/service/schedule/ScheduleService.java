package com.webcinema.service.schedule;

import com.webcinema.extension.ResourceNotFoundException;
import com.webcinema.model.Branch;
import com.webcinema.model.Movie;
import com.webcinema.model.Room;
import com.webcinema.model.Schedule;
import com.webcinema.repository.BranchRepository;
import com.webcinema.repository.MovieRepository;
import com.webcinema.repository.RoomRepository;
import com.webcinema.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final MovieRepository movieRepository;
    private final BranchRepository branchRepository;
    private final RoomRepository roomRepository;

    @Override
    public Schedule createSchedule(LocalDate startDate, LocalTime startTime, double price, Long movieId, Long branchId, Long roomId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim!"));

        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi nhánh!"));

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng!"));

        Schedule schedule = new Schedule();
        schedule.setStartDate(startDate);
        schedule.setStartTime(startTime);

        int durationInMinutes = movie.getDuration();
        LocalTime endTime = startTime.plusMinutes(durationInMinutes);
        schedule.setEndTime(endTime);
        schedule.setPrice(price);
        schedule.setMovie(movie);
        schedule.setBranch(branch);
        schedule.setRoom(room);

        return scheduleRepository.save(schedule);
    }
}
