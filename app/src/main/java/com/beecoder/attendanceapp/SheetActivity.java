package com.beecoder.attendanceapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class SheetActivity extends AppCompatActivity {
    Toolbar toolbar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet);

        showTable();
        setToolbar();
    }
    private void setToolbar() {

        toolbar2 = findViewById(R.id.toolbar);
        TextView title = toolbar2.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar2.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar2.findViewById(R.id.back);
        ImageButton save = toolbar2.findViewById(R.id.save);


        title.setText("Monthly Attendance Sheet");
        subtitle.setVisibility(View.GONE);
        back.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
    }

    private void showTable() {
        DbHelper dbHelper = new DbHelper(this);
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        long[] idArray = getIntent().getLongArrayExtra("idArray");
        int[] rollArray = getIntent().getIntArrayExtra("rollArray");
        String[] nameArray = getIntent().getStringArrayExtra("nameArray");

        String month = getIntent().getStringExtra("month");
        assert month != null;
        int DAY_IN_MONTH = getDayInMonth(month);


        //row setup
        assert idArray != null;
        int rowSize = idArray.length + 1;

        TableRow[] rows = new TableRow[rowSize];
        TextView[] roll_tvs = new TextView[rowSize];
        TextView[] name_tvs = new TextView[rowSize];
        TextView[] p_tvs = new TextView[rowSize];
        TextView[] a_tvs = new TextView[rowSize];
        TextView[] p_percent = new TextView[rowSize];


        TextView[][] status_tvs = new TextView[rowSize][DAY_IN_MONTH + 1];

        for (int i = 0; i < rowSize; i++) {
            roll_tvs[i] = new TextView(this);
            name_tvs[i] = new TextView(this);
            p_tvs[i] = new TextView(this);
            a_tvs[i] = new TextView(this);
            p_percent[i] = new TextView(this);

            for (int j = 1; j <= DAY_IN_MONTH; j++) {
                status_tvs[i][j] = new TextView(this);
            }
        }

        //header
        roll_tvs[0].setText("Roll");
        roll_tvs[0].setTypeface(roll_tvs[0].getTypeface(), Typeface.BOLD);
        name_tvs[0].setText("Name");
        name_tvs[0].setTypeface(name_tvs[0].getTypeface(), Typeface.BOLD);
        p_tvs[0].setText("T. P");
        p_tvs[0].setTypeface(p_tvs[0].getTypeface(), Typeface.BOLD);
        a_tvs[0].setText("T. A");
        a_tvs[0].setTypeface(a_tvs[0].getTypeface(), Typeface.BOLD);
        p_percent[0].setText("P %");
        p_percent[0].setTypeface(p_percent[0].getTypeface(), Typeface.BOLD);


        for (int i = 1; i <= DAY_IN_MONTH; i++) {
            status_tvs[0][i].setText(String.valueOf(i));
            status_tvs[0][i].setTypeface(status_tvs[0][i].getTypeface(), Typeface.BOLD);
        }

        for (int i = 1; i < rowSize; i++) {
            assert rollArray != null;
            roll_tvs[i].setText(String.valueOf(rollArray[i - 1]));
            assert nameArray != null;
            name_tvs[i].setText(nameArray[i - 1]);

            //Count Total P
            int p_counts = dbHelper.getPCount(idArray[i - 1],month);
            p_tvs[i].setText(String.valueOf(p_counts));
            //Count Total A
            int a_counts = dbHelper.getACount(idArray[i - 1],month);
            a_tvs[i].setText(String.valueOf(a_counts));

            int total = p_counts+a_counts;
            int percent = (p_counts*100)/total;
            p_percent[i].setText(String.valueOf(percent+"%"));



            for (int j = 1; j <= DAY_IN_MONTH; j++) {
                String day = String.valueOf(j);
                if (day.length() == 1) day = "0" + day;
                String date = day + "." + month;
                String status = dbHelper.getStatus(idArray[i - 1], date);
                status_tvs[i][j].setText(status);

            }

        }

        for (int i = 0; i < rowSize; i++) {
            rows[i] = new TableRow(this);

            if (i % 2 == 0)
                rows[i].setBackgroundColor(Color.parseColor("#EEEEEE"));
            else rows[i].setBackgroundColor(Color.parseColor("#E4E4E4"));

            roll_tvs[i].setPadding(16, 16, 16, 16);
            name_tvs[i].setPadding(16, 16, 16, 16);
            p_tvs[i].setPadding(20, 16, 16, 16);
            p_tvs[i].setTextColor(Color.parseColor("#008000"));
            a_tvs[i].setPadding(20, 16, 16, 16);
            a_tvs[i].setTextColor(Color.parseColor("#FF0000"));
            p_percent[i].setPadding(16, 16, 16, 16);
            p_percent[i].setTextColor(Color.parseColor("#D2691E"));


            rows[i].addView(roll_tvs[i]);
            rows[i].addView(name_tvs[i]);
            rows[i].addView(p_tvs[i]);
            rows[i].addView(a_tvs[i]);
            rows[i].addView(p_percent[i]);

            for (int j = 1; j <= DAY_IN_MONTH; j++) {
                status_tvs[i][j].setPadding(16, 16, 16, 16);
                rows[i].addView(status_tvs[i][j]);

            }
            tableLayout.addView(rows[i]);
        }

    }

    private int getDayInMonth(String month) {
        int monthIndex = Integer.parseInt(month.substring(0, 2)) - 1;
        int year = Integer.parseInt(month.substring(3));

        Log.i("1234567890", "getDayInMonth: "+monthIndex+year);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, monthIndex);
        calendar.set(Calendar.YEAR, year);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}