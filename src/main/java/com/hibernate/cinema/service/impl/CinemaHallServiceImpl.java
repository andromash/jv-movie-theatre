package com.hibernate.cinema.service.impl;

import com.hibernate.cinema.dao.CinemaHallDao;
import com.hibernate.cinema.lib.Inject;
import com.hibernate.cinema.lib.Service;
import com.hibernate.cinema.model.CinemaHall;
import com.hibernate.cinema.service.CinemaHallService;
import java.util.List;

@Service
public class CinemaHallServiceImpl implements CinemaHallService {
    @Inject
    private CinemaHallDao cinemaHallDao;

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        return cinemaHallDao.add(cinemaHall);
    }

    @Override
    public List<CinemaHall> getAll() {
        return cinemaHallDao.getAll();
    }
}
