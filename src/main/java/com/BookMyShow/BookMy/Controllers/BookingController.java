package com.BookMyShow.BookMy.Controllers;

import com.BookMyShow.BookMy.Models.Booking;
import com.BookMyShow.BookMy.Models.ResponseStatus;
import com.BookMyShow.BookMy.dto.BookMovieRequestDto;
import com.BookMyShow.BookMy.dto.BookMovieResponseDto;
import com.BookMyShow.BookMy.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private BookingService bookingService;
    @Autowired
    private BookingController (BookingService bookingService){
        this.bookingService=bookingService;
    }

    public BookMovieResponseDto bookMovie(BookMovieRequestDto bookMovieRequestDto){
        BookMovieResponseDto response = new BookMovieResponseDto();
        try {
          Booking booking =  bookingService.bookMovie(bookMovieRequestDto.getUserId(),
                    bookMovieRequestDto.getShowId(), bookMovieRequestDto.getShowSeatIds());

          response.setBookingId(booking.getId());
          response.setResponseStatus(ResponseStatus.SUCCESS);
          response.setAmount(booking.getAmount());

        }catch (RuntimeException runtimeException){
            response.setResponseStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
    public Booking cancelMovie(){
        return null;
    }
}
