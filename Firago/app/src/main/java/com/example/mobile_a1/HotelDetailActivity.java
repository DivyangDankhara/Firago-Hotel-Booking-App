package com.example.mobile_a1;
/*
Project:    Firago(Hotel Booking App)
Programmers: Divyangbhai
Description: This is the Hotel Description activity. This page will allow see the description of the hotel app.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class HotelDetailActivity extends AppCompatActivity {

    Button selectHotel;
    TextView hotelName;
    TextView hotelPrice;
    ImageView hotelImage;
    RatingBar ratingBar;
    Intent Next;
    Intent Prev;
    String hotel;
    float rating;
    int price;
    int hotelImageInt;
    String hotelDescription;
    TextView hotelDescriptionArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);



        // getting the View from the layout
        selectHotel = (Button)findViewById(R.id.btnHotelselect);
        hotelName = (TextView) findViewById(R.id.hotel_name);
        hotelPrice = (TextView)findViewById(R.id.hotel_price);
        hotelImage = (ImageView) findViewById(R.id.hotel_image);
        ratingBar = (RatingBar) findViewById(R.id.ratingBarHotel);
        hotelDescriptionArea = (TextView) findViewById(R.id.hotelDec);

        // get the previous details and assigning to the this classes variables.
        Prev = getIntent();
        hotel = Prev.getStringExtra("hotel");
        price = Prev.getIntExtra("price", 0);
        rating = Float.parseFloat(Prev.getStringExtra("rating"));
        hotelImageInt = Prev.getIntExtra("hotelImage",this.getResources().getIdentifier("ic_launcher", "drawable", this.getPackageName()));

        // making the Hotel Description.
        hotelDescription = "The "+ hotel +" is the right choice for visitors who are searching for a combination of charm, peace and quiet, and a convenient position from which to explore CANADA.";

        setTitle(hotel+" - Details");

        hotelDescriptionArea.setText(hotelDescription);
        hotelImage.setImageDrawable(this.getResources().getDrawable(hotelImageInt));
        ratingBar.setRating(rating);
        hotelName.setText(hotel);
        hotelPrice.setText(Integer.toString(price));


        selectHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next = new Intent(HotelDetailActivity.this,SecondActivity.class);

                Next.putExtra("hotel", hotel);
                Next.putExtra("price", price);
                startActivity(Next);
            }
        });


    }
}
