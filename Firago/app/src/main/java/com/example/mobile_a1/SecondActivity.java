package com.example.mobile_a1;
/*
Project:    Firago(Hotel Booking App)
Programmers: Divyangbhai
Description: This is the second page. Primary function is to
             allow user to pick the # of rooms and adults needed to be fitted
             for a calendar date.

 */
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Days;
import org.w3c.dom.Text;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SecondActivity extends AppCompatActivity {

    private Button GoToReceiptPage;

    // hotel information
    private String hotel;
    private Integer price;
    private Integer bookingPrice;

    // labels
    private TextView roomsCount;
    private TextView adultsCount;
    private TextView orderTotal;
    private TextView checkInDate;
    private TextView checkOutDate;
    private TextView errorMessage;

    // date picker listeners
    private DatePickerDialog.OnDateSetListener checkInDateListener;
    private DatePickerDialog.OnDateSetListener checkOutDateListener;

    // seek bars
    private SeekBar roomsSeekBar;
    private SeekBar adultsSeekBar;
    private SeekBar childrenSeekBar;

    //Progbar
    private ProgressBar mProgBarHorz;
    private int mProgBarStatus=0;
    private TextView timeRemaining;
    private int timeLeft=30;

    private Handler handle = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getWidgets();
        readIntentInfo();
        setListeners();
        setSeekBarListener(roomsSeekBar, roomsCount, false);
        setSeekBarListener(adultsSeekBar, adultsCount, false);
        updateOrderTotal(false);

        // set the title
        setTitle(hotel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgBarHorz = (ProgressBar) findViewById(R.id.ProgBar);
        mProgBarHorz.setMax(50);

    }
    @Override
    protected void onResume(){
        super.onResume();
        mProgBarStatus=0;
        mProgBarHorz.setProgress(mProgBarStatus);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgBarStatus<50){
                    mProgBarStatus++;
                    SystemClock.sleep(200);
                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgBarHorz.setProgress(mProgBarStatus);
                            //timeRemaining.setText("Time Remaining in Session " +timeLeft+ " Seconds" );
                            //timeLeft--;
                        }
                    });
                }
                if(mProgBarStatus==50)
                {

                    mProgBarStatus=0;
                    Intent userActive = new Intent(SecondActivity.this,AreYouStillThere.class);
                    Thread.currentThread().interrupt();
                    startActivity(userActive);

                }


            }
        }).start();


    }
    /*
     * FUNCTION : readIntentInfo
     *
     * DESCRIPTION : Reads information from previous activity
     *
     * PARAMETERS : None
     *
     * RETURNS : None
     */
    private void readIntentInfo() {

        // get intent info
        Intent intent = getIntent();
        hotel = intent.getStringExtra("hotel");
        price = intent.getIntExtra("price", 0);

    }
    /*
     * FUNCTION : getWidgets
     *
     * DESCRIPTION : Ties palette from xml to java
     *
     * PARAMETERS : None
     *
     * RETURNS : None
     */
    private void getWidgets() {

        // set the labels
        roomsCount = (TextView) findViewById(R.id.roomsCount);
        adultsCount = (TextView) findViewById(R.id.adultsCount);
        orderTotal = (TextView) findViewById(R.id.orderTotal);
        checkInDate = (TextView) findViewById(R.id.checkinDate);
        checkOutDate = (TextView) findViewById(R.id.checkoutDate);
        errorMessage = (TextView) findViewById(R.id.errorMessage);

        // set the seek bars
        roomsSeekBar = (SeekBar) findViewById(R.id.roomsSeekBar);
        adultsSeekBar = (SeekBar) findViewById(R.id.adultsSeekBar);

        // set up button to place order
        GoToReceiptPage = (Button) findViewById(R.id.SendReceiptPage);

    }
    //listens for sliders change
    private void setSeekBarListener(SeekBar seekBar, final TextView count, final boolean isChildrenCount) {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int value = (!isChildrenCount) ? progress+1 : progress;
                count.setText(String.valueOf(value));
                updateOrderTotal(false);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }
    /*
    Function:updateOrderTotal
    Description:This function updates the order total for all rooms & and people
    Param:calculateDays
    Returns:nothing
     */
    private void updateOrderTotal(boolean calculateDays) {

        bookingPrice = price;
        int ROOMPRICE = 50;
        int ADULTPRICE = 25;


        if (!roomsCount.equals("") && !adultsCount.equals("")) {

            bookingPrice += ((Integer.parseInt(roomsCount.getText().toString()) - 1) * ROOMPRICE) +
                    ((Integer.parseInt(adultsCount.getText().toString()) - 1) * ADULTPRICE);

        }

        if (calculateDays && !checkInDate.equals("") && !checkOutDate.equals("")) {

            long days = getDaysBetween();
            if(days > 0) {

                errorMessage.setText("");
                bookingPrice = bookingPrice * (int)days;

            }
            else {

                errorMessage.setText("Invalid dates selected.");

            }

        }

        orderTotal.setText("Order Total: $" + String.valueOf(bookingPrice));
        mProgBarStatus=0;

    }
    /*
       Function:getDaysBetween
       Description:This function gets the range for how long user is staying
       Param:NULL
       Returns:number of days being stayed
        */
    private long getDaysBetween() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        String sCheckin = checkInDate.getText().toString();
        String sCheckout = checkOutDate.getText().toString();

        try {

            Date checkin = dateFormat.parse(sCheckin);
            Date checkout = dateFormat.parse(sCheckout);

            long days = checkout.getTime() - checkin.getTime();

            return TimeUnit.DAYS.convert(days, TimeUnit.MILLISECONDS);

        }
        catch (ParseException e) {

            e.printStackTrace();
            return -1;

        }

    }

    /*
     * FUNCTION : setListeners
     *
     * DESCRIPTION : This function sets the listeners for the confirm button that will navigate to the next page. This function will call
     *               the proper ID references to grab the number of rooms, the number of guests. It will display an updated price as the user
     *               chooses their options. The confirm button will send all data using intent.putExtra.
     *
     * PARAMETERS : None
     *
     * RETURNS : None
     */
    private void setListeners() {

        // set label default values
        roomsCount.setText("1");
        adultsCount.setText("1");


        // set listener for order button
        GoToReceiptPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long days = getDaysBetween();
                if (days > 0) {
                    String checkIn = checkInDate.getText().toString();
                    String checkOut = checkOutDate.getText().toString();
                    int numOfGuests = Integer.parseInt(adultsCount.getText().toString());
                    int numOfRooms = Integer.parseInt(roomsCount.getText().toString());
                    long numOfdays = getDaysBetween();

                    errorMessage.setText("");
                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                    intent.putExtra("fullPrice", bookingPrice);/* STRING-DOUBLE*/
                    intent.putExtra("checkIn", checkIn);/*STRING-STRING*/
                    intent.putExtra("checkOut", checkOut);/*STRING-STRING*/
                    intent.putExtra("numOfGuests", numOfGuests);/*STRING-INT*/
                    intent.putExtra("numOfRooms", numOfRooms);/*STRING-INT*/
                    intent.putExtra("numOfDays", numOfdays);/*STRING-LONG*/
                    intent.putExtra("theHotel", hotel);/*STRING-STRING*/
                    intent.putExtra("pricePerNight", price);/*STRING-INT*/
                    mProgBarStatus=-5000;
                    startActivity(intent);

                }
                else {

                    errorMessage.setText("Invalid dates selected.");

                }


            }
        });

        //listens for new dates for checkin
        checkInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SecondActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, checkInDateListener,
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        checkInDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;

                String date = month + "/" + dayOfMonth + "/" + year;
                checkInDate.setText(date);
                updateOrderTotal(true);

            }
        };
        //listens for check out date to be pressed
        checkOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SecondActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, checkOutDateListener,
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        checkOutDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;

                String date = month + "/" + dayOfMonth + "/" + year;
                checkOutDate.setText(date);
                updateOrderTotal(true);

            }
        };

    }
}
