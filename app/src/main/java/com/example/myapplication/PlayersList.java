package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.Players_Adapter;
import com.example.myapplication.adapter.Teams_Adapter;
import com.example.myapplication.models.Players_model;
import com.example.myapplication.services.DatabaseHelper;
import com.example.myapplication.services.GlobalValue;
import com.example.myapplication.services.KittenClickListener;
import com.example.myapplication.services.KittenViewHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission_group.CAMERA;
import static android.Manifest.permission_group.STORAGE;
import static java.security.AccessController.getContext;

public  class PlayersList extends AppCompatActivity  {

    public  static DatabaseHelper mydb;
    public  static ImageView nodata;
    public  static RecyclerView recycler_view;
    FloatingActionButton floating_action_button;
    public  static Context activity ;
    public  static Activity activity12 ;
    public  static  GlobalValue globalValue;
    public static List<Players_model> players_list;
    public  static Players_Adapter madapter;
    private static final int PICK_IMAGE = 100;
    public  static ImageView add_image;
    private static final int PERMISSION_REQUEST_CODE = 200;

    Uri imageUri;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players);
        activity=getBaseContext();
       // activity12= (Activity) getApplicationContext();

        nodata=(ImageView)findViewById(R.id.nodata);
        recycler_view=(RecyclerView) findViewById(R.id.recycler_view);
        globalValue=new GlobalValue(getApplicationContext());
        mydb=new DatabaseHelper(getApplicationContext());
        Cursor res = mydb.getAll_Teams();
        if(res.getCount() == 0) {
            Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_SHORT).show();

        }

        Get_Team_details();
        floating_action_button=(FloatingActionButton) findViewById(R.id.floating_action_button);
        floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTeamPOPUP(getIntent());
            }
        });
        recycler_view.setLayoutManager(new GridLayoutManager(this, 2));
        recycler_view.setHasFixedSize(true);
      //  floating_action_button=(FloatingActionButton) findViewById(R.id.floating_action_button);

    }


    public static void Get_Team_details() {
        String vkdf = globalValue.getString("team_id_player_list");

        Cursor res = mydb.getAll_Team_players(globalValue.getString("team_id_player_list"));
        if (res != null) {

        if (res.getCount() == 0) {
            nodata.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.GONE);
            Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            players_list = new ArrayList<>();
            while (res.moveToNext()) {

                Players_model cartdetails = new Players_model();
                cartdetails.setPlayer_id(res.getString(0));
                cartdetails.setPlayer_name(res.getString(1));
                cartdetails.setPlayer_team_id(res.getString(2));
                cartdetails.setPlayer_team_name(res.getString(3));
                cartdetails.setPlayer_total_matches(res.getString(4));
                cartdetails.setPlayer_total_matches(res.getString(5));
                cartdetails.setImageBytes(res.getBlob(6));


                players_list.add(cartdetails);


            }



            nodata.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            madapter = new Players_Adapter(activity, players_list);
            recycler_view.setHasFixedSize(true);
            recycler_view.setLayoutManager(new GridLayoutManager(activity, 2));
          //  recycler_view.setLayoutManager(new LinearLayoutManager(activity));
            recycler_view.setAdapter(madapter);






//            recycler_view.setVisibility(View.VISIBLE);
//            recycler_view.setAdapter(new Players_Adapter(activity,players_list,this));
//

        }
    }
    }

    public   void AddTeamPOPUP(final Intent context) {
        TextView submit;
        TextView from,to;
        EditText item_name;
        ImageView  close1;
        final Dialog dialog = new Dialog(PlayersList.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_player);

        close1 = (ImageView) dialog.findViewById(R.id.close1);

        submit = (TextView) dialog.findViewById(R.id.submit_item);
        item_name = (EditText) dialog.findViewById(R.id.item_name);
        add_image = (ImageView) dialog.findViewById(R.id.add_image);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (ActivityCompat.checkSelfPermission(PlayersList.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(PlayersList.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(PlayersList.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    openGallery();
                } else {


                    if (ActivityCompat.checkSelfPermission(PlayersList.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            init();
                    } else {
                        ActivityCompat.requestPermissions(PlayersList.this, new String[]{Manifest.permission.CAMERA}, 101);
                    }
                    if (ActivityCompat.checkSelfPermission(PlayersList.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        ActivityCompat.requestPermissions(PlayersList.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);
                    }

                    if (ActivityCompat.checkSelfPermission(PlayersList.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


                    } else {
                        ActivityCompat.requestPermissions(PlayersList.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 103);
                    }


                }


            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(item_name.getText().toString().length()==0){
                    item_name.setError("Required*");
                    item_name.requestFocus();

                }else {
                    item_name.setError(null);

                    boolean isexists=mydb.check_player_name(item_name.getText().toString(),globalValue.getString("team_id_player_list"));
                    if(isexists){


                        item_name.setText("Already Exists ");
                        item_name.requestFocus();

                    }else {

                        Bitmap bitmap = ((BitmapDrawable) add_image.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                        byte[] imageInByte = baos.toByteArray();


//                            if (imageInByte.length >4000) {
//                                bitmap = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
//                                Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.8), (int) (bitmap.getHeight() * 0.8), true);
//                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                                resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                                imageInByte = stream.toByteArray();
//                            }
//

                        Players_model worldpop = new Players_model("",item_name.getText().toString(),globalValue.getString("team_id_player_list"),globalValue.getString("team_name_player_list"),"0","0",imageInByte);

                        boolean isinserted = mydb.insert_players(worldpop);

                        if(isinserted){
                            Toast.makeText(activity,"Team Added",Toast.LENGTH_SHORT).show();
                            Get_Team_details();

                        }else {
                            Toast.makeText(activity,"Failed to Add Team",Toast.LENGTH_SHORT).show();
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
    public void openGallery() {


        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PlayersList.this);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {

                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, 1);
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto , 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        add_image.setImageBitmap(selectedImage);


//                        if (globalValue.getString("edit_player").equalsIgnoreCase("no")) {
//                            add_image.setImageBitmap(selectedImage);
//
//                        } else {
//                            Players_Adapter.add_image.setImageBitmap(selectedImage);
//                        }

                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && requestCode == 1) {
                        imageUri = data.getData();
                        add_image.setImageURI(imageUri);

//                        if (globalValue.getString("edit_player").equalsIgnoreCase("no")) {
//
//                            add_image.setImageURI(imageUri);
//                        } else {
//                            Players_Adapter.add_image.setImageURI(imageUri);
//
//                        }
                        break;
                    }

            }

        }

    }

    @Override
    public void onBackPressed() {
        //Checks if the navigation drawer is open -- If so, close it

        Intent intent =new Intent(PlayersList.this,MainActivity.class);
        startActivity(intent);


    }


}