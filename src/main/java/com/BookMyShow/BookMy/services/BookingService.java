package com.BookMyShow.BookMy.services;

import com.BookMyShow.BookMy.Models.*;
import com.BookMyShow.BookMy.exception.ShowNotFoundException;
import com.BookMyShow.BookMy.exception.ShowSeatNotAvailableException;
import com.BookMyShow.BookMy.exception.UserNotFoundException;
import com.BookMyShow.BookMy.repositories.BookingRepository;
import com.BookMyShow.BookMy.repositories.ShowRepository;
import com.BookMyShow.BookMy.repositories.ShowSeatRepository;
import com.BookMyShow.BookMy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private BookingRepository bookingRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculatorService priceCalculatorService;

    @Autowired
    public BookingService(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository,BookingRepository bookingRepository, PriceCalculatorService priceCalculatorService) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
        this.priceCalculatorService=priceCalculatorService;
    }



    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId,Long showId, List<Long> showSeatIds){
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("Invalid User Id");
        }

        User bookedBy = optionalUser.get();

        Optional<Show> optionalShow = showRepository.findById(showId);
        if (optionalShow.isEmpty()){
            throw new ShowNotFoundException("Invalid SHowId");
        }
        Show show = optionalShow.get();

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
        for(ShowSeat showSeat : showSeats){
            if(!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)){
                throw new ShowSeatNotAvailableException("ShowSeat with id : " + showSeat.getId()+ "isn't Available");
            }
        }
        List<ShowSeat> bookedShowSeats = new ArrayList<>();
        for(ShowSeat showSeat : showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.LOCKED);
            bookedShowSeats.add(showSeatRepository.save(showSeat));
        }
        Booking booking = new Booking();
        booking.setUser(bookedBy);
        booking.setBookingStatus(BookingStatus.IN_PROGRESS);
        booking.setPayments(new ArrayList<>());
        booking.setShowSeats(bookedShowSeats);
        booking.setCreatedAt(new Date());
        booking.setLastModifiedAt(new Date());
        booking.setAmount(priceCalculatorService.calculateBookingPrice(bookedShowSeats, show));

        return bookingRepository.save(booking);

    }
}
