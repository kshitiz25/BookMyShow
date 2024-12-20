package com.BookMyShow.BookMy.repositories;

import com.BookMyShow.BookMy.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking ,Long> {
    @Override
    Booking save (Booking booking);
}
