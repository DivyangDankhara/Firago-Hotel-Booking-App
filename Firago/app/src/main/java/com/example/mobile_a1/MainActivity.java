/*
 * FILE : MainActivity.java
 * PROJECT : PROG3150- Assignment #1
 * PROGRAMMER : Attila Katona, Div Danksheny, Tudor Lupu, Firas Aribibibibi
 * FIRST VERSION : 2019-02-03
 * DESCRIPTION :    This is the first page of a three page app. This program will act like the intermediate moderation between a user and a hotel booking
 *                  app. We will start with a city already choson. Toronto will be the city, it will display 5 different hotels with pricing info. The user
 *                  will choose a picture and it will send the user to the second page, that will ask the user for details about their stay
 */
package com.example.mobile_a1;

import android.content.Intent;
import android.graphics.Color;
import android.service.autofill.FillEventHistory;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;
/*
Project:    Firago(Hotel Booking App)
Programmers: Divyangbhai
Description: This is the main activity. This page will allow user to chose from a selective number
             of hotels.
 */
public class MainActivity extends AppCompatActivity {


    List<Hotel> hotelList;

    /*
     * FUNCTION : onCreate
     *
     * DESCRIPTION : This is the onCreate function for the first page of the hotel app. This function will add hotels to the hotel list.
     *               it will display the pictures. It also starts a listening event activity for the pictures to check for clicks. If a picture is clicked, it will use
     *               the intent to pass the hotel and the price per night to the second page.
     *
     * PARAMETERS : Bundle savedInstanceState -The savedInstanceState is a reference to a Bundle object that is passed into the onCreate method of every Android Activity.
     *
     * RETURNS : None
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Firago");
        hotelList = new ArrayList<>();
        //adding hotel to list
        hotelList.add(new Hotel(R.drawable.bondplacehotel98, "Bond Place Hotel", 98,1));
        hotelList.add(new Hotel(R.drawable.chelseahotel128, "Chelsea Hotel", 128,2));
        hotelList.add(new Hotel(R.drawable.deltahotel128, "Delta Hotel", 128,2.5));
        hotelList.add(new Hotel(R.drawable.doubletree195, "Double Tree Hotel", 195,3));
        hotelList.add(new Hotel(R.drawable.hiltonhotel277, "Hilton Hotel", 277,4));
        hotelList.add(new Hotel(R.drawable.hotel8986, "Hotel 89", 89,4.5));

        ListView listView  = (ListView)findViewById(R.id.listView);

        MyHotelAdapter adapter = new MyHotelAdapter(this, R.layout.hotel_list_item, hotelList);
        listView.setAdapter(adapter);
        //saving data for next page
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(MainActivity.this, HotelDetailActivity.class);
                intent.putExtra("hotel", hotelList.get(position).getHotelName());
                intent.putExtra("price", hotelList.get(position).getHotelPrice());
                intent.putExtra("rating", Double.toString(hotelList.get(position).getHotelRating()));
                intent.putExtra("hotelImage", hotelList.get(position).getHotelImage());
                startActivity(intent);
            }
        });

    }
}
