package com.example.myapplication.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DetailActivity;
import com.example.myapplication.PlayersList;
import com.example.myapplication.R;
import com.example.myapplication.Teams_fragment;
import com.example.myapplication.models.Players_model;
import com.example.myapplication.services.DatabaseHelper;
import com.example.myapplication.services.GlobalValue;
import com.example.myapplication.services.KittenClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class Players_Adapter extends RecyclerView.Adapter<Players_Adapter.ViewHolder>  implements Filterable {

    GlobalValue globalValue;
    private List<Players_model> listdata;
    private List<Players_model> list_data_filtered;
    Context mcontext;
    double total;
    DatabaseHelper mydb;
    ImageView close1;
    public  static ImageView add_image;
    String action_team_id;
    TextInputEditText item_name,rate,gst,amount,qty;

    private static final int ID_UP = 1;
    private static final int ID_DOWN = 2;
    private static final int ID_SEARCH = 3;
    private static final int ID_INFO = 4;
    private static final int ID_ERASE = 5;
    private static final int ID_OK = 6;
    Uri imageUri;

    String edit_item_id;

   // RecyclerView recyclerView;
    public Players_Adapter(Context context, List<Players_model> acceted_list) {
        this.listdata = acceted_list;
        this.list_data_filtered = acceted_list;
        this.mcontext = context;

    }
    @Override  
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {  
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());  
        View listItem= layoutInflater.inflate(R.layout.list_view_palyers, parent, false);
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

        final Players_model myListData = listdata.get(position);


        Bitmap bmp = BitmapFactory.decodeByteArray(list_data_filtered.get(position).getImageBytes(), 0, list_data_filtered.get(position).getImageBytes().length);

// Set the Bitmap data to the ImageView
     
        holder.player_image.setImageBitmap(bmp);

        holder.team_name.setText(listdata.get(position).getPlayer_name());

        holder.team_name.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mcontext, DetailActivity.class);
//                mcontext.startActivity(intent);

            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action_team_id=list_data_filtered.get(position).getPlayer_id();

                Intent intent=new Intent(mcontext, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("payer_id",action_team_id);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });

//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                action_team_id=list_data_filtered.get(position).getPlayer_id();
//
//                Intent intent=new Intent(mcontext, DetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("payer_id",action_team_id);
//                intent.putExtras(bundle);
//                mcontext.startActivity(intent);
//
//                //edit_team(mcontext,holder.edit.getContext(),action_team_id);
//
//
//
//
//
//            }
//        });
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                action_team_id=list_data_filtered.get(position).getPlayer_id();
//
//                boolean isdeleted=mydb.delete_player(action_team_id);
//                if(isdeleted){
//                    Toast.makeText(mcontext,"Deleted",Toast.LENGTH_SHORT).show();
//                    PlayersList.Get_Team_details();
//
//
//                }else {
//                    Toast.makeText(mcontext,"Failed to Deleted",Toast.LENGTH_SHORT).show();
//
//                }
//
//
//
//            }
//        });

//        ViewCompat.setTransitionName(holder.player_image, String.valueOf(position) + "_image");
//
//        holder.player_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onKittenClicked(holder, position);
//            }
//        });

    }  
  

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView team_name,matches,won,lost;
        public  ImageView edit,delete;
        ImageView player_image;
        LinearLayout layout;
      //  private QuickAction quickAction;

        public ViewHolder(final View itemView) {
            super(itemView);



            this.team_name = (TextView) itemView.findViewById(R.id.team_name);
            this.layout = (LinearLayout) itemView.findViewById(R.id.layout);


            this.edit = (ImageView) itemView.findViewById(R.id.edit);
            this.delete = (ImageView) itemView.findViewById(R.id.delete);
            this.player_image = (ImageView) itemView.findViewById(R.id.player_image);


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
                    List<Players_model> filteredList = new ArrayList<>();
                    for (Players_model row : listdata) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getPlayer_name().toLowerCase().contains(charString.toLowerCase())
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
                list_data_filtered = (ArrayList<Players_model>) filterResults.values;

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
        dialog.setContentView(R.layout.add_player);

        close1 = (ImageView) dialog.findViewById(R.id.close1);
        submit = (TextView) dialog.findViewById(R.id.submit_item);
        item_name = (EditText) dialog.findViewById(R.id.item_name);
        add_image = (ImageView) dialog.findViewById(R.id.add_image);
//        add_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                PlayersList.openGallery();
//
//            }
//        });

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(item_name.getText().toString().length()==0){
//                    item_name.setError("Required*");
//                    item_name.requestFocus();
//
//                }else {
//                    item_name.setError(null);
//                    String fhud=action_team_id123;
//                    String gvrfr=item_name.getText().toString();
//
//                        boolean isinserted = mydb.update_player_name(action_team_id123,item_name.getText().toString());
//
//                        if(isinserted){
//                            Toast.makeText(context1,"Player Updated",Toast.LENGTH_SHORT).show();
//                            PlayersList.Get_Team_details();
//
//
//                        }else {
//                            Toast.makeText(context1,"Failed to Update Player",Toast.LENGTH_SHORT).show();
//                        }
//
//                    dialog.dismiss();
//                }
//
//            }
//        });


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
//
//    public static void openGallery(Activity activity333){
//        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity333);
//        builder.setTitle("Choose your profile picture");
//
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//
//                if (options[item].equals("Take Photo")) {
//                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    activity333.startActivityForResult(takePicture, 0);
//
//                } else if (options[item].equals("Choose from Gallery")) {
//
//                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                    activity333.startActivityForResult(gallery, 1);
////                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                    startActivityForResult(pickPhoto , 1);
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (resultCode != RESULT_CANCELED) {
//            switch (requestCode) {
//                case 0:
//                    if (resultCode == RESULT_OK && data != null) {
//                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
//                        add_image.setImageBitmap(selectedImage);
//                    }
//
//                    break;
//                case 1:
//                    if (resultCode == RESULT_OK && requestCode == 1){
//                        imageUri = data.getData();
//                        add_image.setImageURI(imageUri);
//                        break;
//                    }
//
//            }
//        }
//
//    }
}  