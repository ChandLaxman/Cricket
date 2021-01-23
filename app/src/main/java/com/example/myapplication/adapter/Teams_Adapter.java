package com.example.myapplication.adapter;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.DetailActivity;
import com.example.myapplication.PlayersList;
import com.example.myapplication.R;
import com.example.myapplication.Teams_fragment;
import com.example.myapplication.models.Teams_model;
import com.example.myapplication.services.DatabaseHelper;
import com.example.myapplication.services.GlobalValue;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class Teams_Adapter extends RecyclerView.Adapter<Teams_Adapter.ViewHolder>  implements Filterable {
GlobalValue globalValue;
    private List<Teams_model> listdata;
    private List<Teams_model> list_data_filtered;
    Context mcontext;
    double total;
    DatabaseHelper mydb;

    ImageView close1;
    String action_team_id;
    String action_team_name;

    TextInputEditText item_name,rate,gst,amount,qty;

    private static final int ID_UP = 1;
    private static final int ID_DOWN = 2;
    private static final int ID_SEARCH = 3;
    private static final int ID_INFO = 4;
    private static final int ID_ERASE = 5;
    private static final int ID_OK = 6;
String edit_item_id;

   // RecyclerView recyclerView;
    public Teams_Adapter(Context context, List<Teams_model> acceted_list) {
        this.listdata = acceted_list;
        this.list_data_filtered = acceted_list;
        this.mcontext = context;
    }  
    @Override  
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {  
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());  
        View listItem= layoutInflater.inflate(R.layout.list_view_teams, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        globalValue=new GlobalValue(mcontext);
        mydb=new DatabaseHelper(mcontext);
        mydb=new DatabaseHelper(mcontext);
        Cursor res = mydb.getAll_Teams();
        if(res.getCount() == 0) {
            Toast.makeText(mcontext,"Hi",Toast.LENGTH_SHORT).show();

        }
        return viewHolder;


    }  
  
    @Override  
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Teams_model myListData = listdata.get(position);

        holder.team_name.setText(listdata.get(position).getTeam_name());
        holder.matches.setText(listdata.get(position).getTeam_matches());
        holder.won.setText(listdata.get(position).getTeam_won());
        holder.lost.setText(listdata.get(position).getTeam_lost());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                action_team_id=list_data_filtered.get(position).getTeam_id();


                edit_team(mcontext,holder.edit.getContext(),action_team_id);




            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                action_team_id=list_data_filtered.get(position).getTeam_id();

                boolean isdeleted=mydb.delete_team(action_team_id);
                if(isdeleted){
                    Toast.makeText(mcontext,"Deleted",Toast.LENGTH_SHORT).show();
                    Teams_fragment.Get_Team_details();

                }else {
                    Toast.makeText(mcontext,"Failed to Deleted",Toast.LENGTH_SHORT).show();

                }



            }
        });

 holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                action_team_id=list_data_filtered.get(position).getTeam_id();
                action_team_name=list_data_filtered.get(position).getTeam_name();

               Intent i=new Intent(mcontext, PlayersList.class);
               globalValue.putString("team_id_player_list",action_team_id);
               globalValue.putString("team_name_player_list",action_team_name);
               mcontext.startActivity(i);

            }
        });



    }  
  

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView team_name,matches,won,lost;
        public  ImageView edit,delete;
        LinearLayout main_layout;
      //  private QuickAction quickAction;

        public ViewHolder(final View itemView) {
            super(itemView);



            this.team_name = (TextView) itemView.findViewById(R.id.team_name);
            this.matches = (TextView) itemView.findViewById(R.id.matches);
            this.won = (TextView) itemView.findViewById(R.id.won);
            this.lost = (TextView) itemView.findViewById(R.id.lost);

            this.edit = (ImageView) itemView.findViewById(R.id.edit);
            this.delete = (ImageView) itemView.findViewById(R.id.delete);
            this.main_layout = (LinearLayout) itemView.findViewById(R.id.main_layout);




        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    list_data_filtered = listdata;


                } else {
                    List<Teams_model> filteredList = new ArrayList<>();
                    for (Teams_model row : listdata) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTeam_name().toLowerCase().contains(charString.toLowerCase()) || row.getTeam_matches().toLowerCase().contains(charSequence)
                                 || row.getTeam_lost().toLowerCase().contains(charSequence)|| row.getTeam_won().toLowerCase().contains(charSequence)
                        ) {
                            filteredList.add(row);
                        }
                    }

                    list_data_filtered = filteredList;


                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list_data_filtered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list_data_filtered = (ArrayList<Teams_model>) filterResults.values;

                if(list_data_filtered.size()==0){

                    Teams_fragment.recycler_view.setVisibility(View.GONE);
                    Teams_fragment.nodata.setVisibility(View.VISIBLE);



                }else {

                    Teams_fragment.recycler_view.setVisibility(View.VISIBLE);
                    Teams_fragment.nodata.setVisibility(View.GONE);


                }
                notifyDataSetChanged();

            }
        };
    }

    public void edit_team(final Context ccontext,Context context1,String action_team_id123) {

        TextView submit;
        TextView from,to;
        EditText item_name;

        final Dialog dialog = new Dialog(context1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_team);

        close1 = (ImageView) dialog.findViewById(R.id.close1);
        submit = (TextView) dialog.findViewById(R.id.submit_item);
        item_name = (EditText) dialog.findViewById(R.id.item_name);
        Cursor res = mydb.get_team_id(action_team_id123);
        if (res != null) {

            if (res.getCount() == 0) {

                Toast.makeText(context1, "No Data Found", Toast.LENGTH_SHORT).show();
                return;
            } else {

                while (res.moveToNext()) {

                    item_name.setText(res.getString(1));



                }
            }
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(item_name.getText().toString().length()==0){
                    item_name.setError("Required*");
                    item_name.requestFocus();

                }else {
                    item_name.setError(null);

                        boolean isinserted = mydb.update_team_name(action_team_id123,item_name.getText().toString());

                        if(isinserted){
                            Toast.makeText(context1,"Team Updated",Toast.LENGTH_SHORT).show();
                            Teams_fragment.Get_Team_details();


                        }else {
                            Toast.makeText(context1,"Failed to Update Team",Toast.LENGTH_SHORT).show();
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