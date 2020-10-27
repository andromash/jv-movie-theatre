package com.hibernate.cinema.service.mapper;

import com.hibernate.cinema.model.CinemaHall;
import com.hibernate.cinema.model.dto.CinemaHallRequestDto;
import com.hibernate.cinema.model.dto.CinemaHallResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CinemaHallMapper {

    public CinemaHall mapDtoToCinemaHall(CinemaHallRequestDto cinemaHallRequestDto) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setDescription(cinemaHallRequestDto.getDescription());
        cinemaHall.setCapacity(cinemaHallRequestDto.getCapacity());
        return cinemaHall;
    }

    public CinemaHallResponseDto mapCinemaHallToDto(CinemaHall cinemaHall) {
        CinemaHallResponseDto cinemaHallDto = new CinemaHallResponseDto();
        cinemaHallDto.setCapacity(cinemaHall.getCapacity());
        cinemaHallDto.setDescription(cinemaHall.getDescription());
        cinemaHallDto.setId(cinemaHall.getId());
        return cinemaHallDto;
    }
}
