package com.example.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.Teams_Adapter;
import com.example.myapplication.models.Teams_model;
import com.example.myapplication.services.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class Teams_fragment extends Fragment {
    ImageView close1;
   public  static DatabaseHelper mydb;
    public  static ImageView nodata;
    public  static RecyclerView recycler_view;
    FloatingActionButton floating_action_button;
    public  static Activity activity ;
    public static List<Teams_model> teams_list;
    public  static Teams_Adapter madapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.teams, container, false);
        mydb=new DatabaseHelper(getContext());
        nodata=(ImageView)rootview.findViewById(R.id.nodata);
        recycler_view=(RecyclerView) rootview.findViewById(R.id.recycler_view);
        floating_action_button=(FloatingActionButton) rootview.findViewById(R.id.floating_action_button);
        activity = (Activity)rootview.getContext();
        Cursor res = mydb.getAll_Teams();
        if(res.getCount() == 0) {
            Toast.makeText(getActivity(),"Hi",Toast.LENGTH_SHORT).show();

        }

        floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTeamPOPUP(getActivity().getIntent());
            }
        });
Get_Team_details();

        return rootview;
    }

    public  static void Get_Team_details() {

        Cursor res = mydb.getAll_Teams();
        if (res != null) {
        if (res.getCount() == 0) {
            nodata.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.GONE);
            Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show();
            return;
        } else {
            teams_list = new ArrayList<>();
            while (res.moveToNext()) {

                Teams_model cartdetails = new Teams_model();

                cartdetails.setTeam_id(res.getString(0));
                cartdetails.setTeam_name(res.getString(1));
                cartdetails.setTeam_matches(res.getString(2));
                cartdetails.setTeam_won(res.getString(3));
                cartdetails.setTeam_lost(res.getString(4));


                teams_list.add(cartdetails);


            }


            nodata.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            madapter = new Teams_Adapter(activity, teams_list);
            recycler_view.setHasFixedSize(true);
            recycler_view.setLayoutManager(new LinearLayoutManager(activity));
            recycler_view.setAdapter(madapter);

        }
    }
    }



    public void AddTeamPOPUP(final Intent context) {
        TextView submit;
        TextView from,to;
        EditText item_name;

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_team);

        close1 = (ImageView) dialog.findViewById(R.id.close1);
        submit = (TextView) dialog.findViewById(R.id.submit_item);
        item_name = (EditText) dialog.findViewById(R.id.item_name);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(item_name.getText().toString().length()==0){
                    item_name.setError("Required*");
                    item_name.requestFocus();

                }else {
                    item_name.setError(null);

                    boolean isexists=mydb.check_team_name(item_name.getText().toString());
                    if(isexists){


                        item_name.setText("Already Exists ");
                        item_name.requestFocus();

                    }else {
                        Teams_model worldpop = new Teams_model("",item_name.getText().toString(),"0","0","0");

                        boolean isinserted = mydb.insert_teams(worldpop);

                        if(isinserted){
                            Toast.makeText(getActivity(),"Team Added",Toast.LENGTH_SHORT).show();
                            Get_Team_details();

                        }else {
                            Toast.makeText(getActivity(),"Failed to Add Team",Toast.LENGTH_SHORT).show();
                        }

                    }

                    dialog.dismiss();
                }


            }
        });


        try
        {
            if(!(dialog.isShowing()))
            {
                dialog.show();
            }
            else
            {
                dialog.dismiss();
                //   filtered_date.setText(globalValue.getString("from_receipt")+" to "+globalValue.getString("to_receipt"));
            }
        }
        catch (Exception e)
        {

        }
        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //  filtered_date.setText(globalValue.getString("from_receipt")+" to "+globalValue.getString("to_receipt"));

            }
        });

    }

}
