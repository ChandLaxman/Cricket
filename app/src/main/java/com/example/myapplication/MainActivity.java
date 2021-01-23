package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.services.DatabaseHelper;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

public class MainActivity extends AppCompatActivity {
    BubbleNavigationLinearView top_navigation_constraint;
    boolean doubleBackToExitPressedOnce = false;
    private static MainActivity sMainActivity;
    DatabaseHelper mydb;

    public static MainActivity getInstance() {
        return sMainActivity;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mydb=new DatabaseHelper(getApplicationContext());
       Cursor res = mydb.getAll_Teams();
        if(res.getCount() == 0) {
            Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_SHORT).show();

        }
        top_navigation_constraint=(BubbleNavigationLinearView)findViewById(R.id.top_navigation_constraint);

        top_navigation_constraint.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                //navigation changed, do something
                if(position==0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Match_fragment())
                            .commit();
                }
                if(position==1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Teams_fragment())
                            .commit();
                }
                if(position==2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new History_fragment())
                            .commit();
                }
                if(position==3) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Settings_fragment())
                            .commit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Checks if the navigation drawer is open -- If so, close it


            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                Intent intent=new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);

                MainActivity.this.finish();

                finish();
                moveTaskToBack(true);
                finishAffinity();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;



                }
            }, 2000);
        }



}