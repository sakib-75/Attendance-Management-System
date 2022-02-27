package com.beecoder.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.beecoder.attendanceapp.bean.FacultyBean;

public class MarkActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);
        setToolbar();
        showTableMark();

    }


    private void setToolbar() {

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.save);

        title.setText("All Students Mark");
        subtitle.setVisibility(View.GONE);
        back.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
    }

    private void showTableMark() {
        DbHelper dbHelper = new DbHelper(this);
        TableLayout tableLayoutMark = findViewById(R.id.tableLayoutMark);

        long [] idArrayMark = getIntent().getLongArrayExtra("idArrayMark");
        int[] rollArrayMark = getIntent().getIntArrayExtra("rollArrayMark");
        String[] nameArrayMark = getIntent().getStringArrayExtra("nameArrayMark");

        //row setup
        int rowSize = idArrayMark.length + 1;

        TableRow[] rows = new TableRow[rowSize];
        TextView[] roll_tvs = new TextView[rowSize];
        TextView[] name_tvs = new TextView[rowSize];
        TextView[] p_tvs = new TextView[rowSize];
        TextView[] a_tvs = new TextView[rowSize];
        TextView[] p_percent = new TextView[rowSize];
        TextView[] mark = new TextView[rowSize];


        for (int i = 0; i < rowSize; i++) {
            roll_tvs[i] = new TextView(this);
            name_tvs[i] = new TextView(this);
            p_tvs[i] = new TextView(this);
            a_tvs[i] = new TextView(this);
            p_percent[i] = new TextView(this);
            mark[i] = new TextView(this);

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
        mark[0].setText("Mark");
        mark[0].setTypeface(mark[0].getTypeface(), Typeface.BOLD);



        for (int i = 1; i < rowSize; i++) {
            roll_tvs[i].setText(String.valueOf(rollArrayMark[i - 1]));
            name_tvs[i].setText(nameArrayMark[i - 1]);

            //Count Total P
            int p_counts_Mark = dbHelper.getPCountMark(idArrayMark[i - 1]);
            p_tvs[i].setText(String.valueOf(p_counts_Mark));
            //Count Total A
            int a_counts_mark = dbHelper.getACountMark(idArrayMark[i - 1]);
            a_tvs[i].setText(String.valueOf(a_counts_mark));
            //Calculate percentage
            int total = p_counts_Mark + a_counts_mark;
            int percent = (p_counts_Mark*100)/total;
            p_percent[i].setText(String.valueOf(percent+"%"));

            //Calculate Mark
            int mark_counts;
            if(percent>=70 && percent<=74){
                mark_counts = 1;
            }
            else if(percent>=75 && percent<=79){
                mark_counts = 2;
            }
            else if(percent>=80 && percent<=89){
                mark_counts = 3;
            }
            else if(percent>=90 && percent<=94){
                mark_counts = 4;
            }
            else if(percent>=95 && percent<=100){
                mark_counts = 5;
            }
            else{
                mark_counts = 0;
            }

            mark[i].setText(String.valueOf(mark_counts));

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

            mark[i].setPadding(16, 16, 16, 16);
            mark[i].setTextColor(Color.parseColor("#8B008B"));


            rows[i].addView(roll_tvs[i]);
            rows[i].addView(name_tvs[i]);
            rows[i].addView(p_tvs[i]);
            rows[i].addView(a_tvs[i]);
            rows[i].addView(p_percent[i]);
            rows[i].addView(mark[i]);

            tableLayoutMark.addView(rows[i]);
        }

    }

}