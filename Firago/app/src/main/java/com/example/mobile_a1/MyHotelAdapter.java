package com.example.mobile_a1;


import com.example.mobile_a1.R;
import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;
/*
Project:    Firago(Hotel Booking App)
Programmers: Divyangbhai
Description: This is the hotel adapter class. Primary function to allow hotel to
              have many values being tied to it.
 */
public class MyHotelAdapter extends ArrayAdapter<Hotel>
{
    Context mCtx;
    int resource;
    List<Hotel> hotelList;

    public MyHotelAdapter(Context mCtx, int resource, List<Hotel> hotelList)
    {
        super(mCtx, resource, hotelList);

        this.mCtx = mCtx;
        this.resource = resource;
        this.hotelList = hotelList;
    }


    /*
     * FUNCTION : getView
     *
     * DESCRIPTION : Establishes all widgets with their xml id.
     *
     * PARAMETERS : Position, View, ViewGroup
     *
     * RETURNS : View
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(resource,null);
        Hotel hotel = hotelList.get(position);

        final TextView hotelName = view.findViewById(R.id.hotel_name);
        final TextView hotelPrice = view.findViewById(R.id.hotel_price);
        ImageView hotelImage = view.findViewById(R.id.hotel_image);
        RatingBar ratingBar =view.findViewById(R.id.ratingBarHotel);
        //setting values for front page
        hotelName.setText(hotel.getHotelName());
        hotelPrice.setText(""+hotel.getHotelPrice());
        hotelImage.setImageDrawable(mCtx.getResources().getDrawable( hotel.getHotelImage()));
        ratingBar.setRating((float) hotel.getHotelRating());

        return view;
    }
}
