package com.BookMyShow.BookMy.services;

import com.BookMyShow.BookMy.Models.Show;
import com.BookMyShow.BookMy.Models.ShowSeat;
import com.BookMyShow.BookMy.Models.ShowSeatType;
import com.BookMyShow.BookMy.repositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PriceCalculatorService {

    private ShowSeatTypeRepository showSeatTypeRepository;

    @Autowired
    public PriceCalculatorService(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }



    public int calculateBookingPrice(List<ShowSeat>showSeats, Show show){
        int amount = 0;

        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show.getId());
        for(ShowSeat showSeat : showSeats){
            for(ShowSeatType showSeatType : showSeatTypes){
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType()));
                amount += showSeatType.getPrice();
            }
        }
        return amount;
    }
}
