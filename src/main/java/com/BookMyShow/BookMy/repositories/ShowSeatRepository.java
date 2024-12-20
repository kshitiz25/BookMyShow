package com.BookMyShow.BookMy.repositories;

import com.BookMyShow.BookMy.Models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    @Override
   Optional<ShowSeat> findById(Long aLong);

    @Override
    ShowSeat save(ShowSeat showSeat);

}
