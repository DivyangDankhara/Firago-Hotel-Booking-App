package com.example.mobile_a1;
/*
Project:    Firago(Hotel Booking App)
Programmers: Divyangbhai
Description: This is the hotel class. Primary function to allow values to be stored
              and/or validated(in future).
 */

public class Hotel
{
    int hotelImage;
    int HotelPrice;
    String hotelName;
    double hotelRating;


    //Constructor
    public Hotel(int hotelImage, String hotelName, int hotelPrice,double rating)
    {
        this.hotelImage = hotelImage;
        HotelPrice = hotelPrice;
        this.hotelName = hotelName;
        this.hotelRating = rating;

    }
    //getter for Hotel image
    public int getHotelImage() {
        return hotelImage;
    }
    //getter for hotel price
    public int getHotelPrice() {
        return HotelPrice;
    }
    //getter for which hotel chosen
    public String getHotelName() {
        return hotelName;
    }
    //getter for rating of said hotel
    public double getHotelRating(){ return hotelRating;}


}
