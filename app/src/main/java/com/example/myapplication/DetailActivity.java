package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.IntRange;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.adapter.Players_Adapter;
import com.example.myapplication.models.Players_model;
import com.example.myapplication.players.Player_batting_fragment;
import com.example.myapplication.players.Player_bowling_fragment;
import com.example.myapplication.players.Player_fielding_fragment;
import com.example.myapplication.services.DatabaseHelper;
import com.example.myapplication.services.GlobalValue;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission_group.CAMERA;
import static android.Manifest.permission_group.STORAGE;

/**
 * Created by naman on 29/05/15.
 */
public class DetailActivity extends AppCompatActivity {
    BubbleNavigationLinearView top_navigation_constraint;
    boolean doubleBackToExitPressedOnce = false;
    private static MainActivity sMainActivity;
    DatabaseHelper mydb;
    GlobalValue globalValue;
    ImageView add_image;
    TextView player_name;
    ImageView edit, delete,image;
    public Context activity;
    Uri imageUri;
    String action_team_id123;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int REQUEST_WRITE_PERMISSION = 786;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players_details);
        mydb = new DatabaseHelper(getApplicationContext());
        delete = (ImageView) findViewById(R.id.delete);
        edit = (ImageView) findViewById(R.id.edit);
        image = (ImageView) findViewById(R.id.image);
        player_name = (TextView) findViewById(R.id.player_name);
        activity = getBaseContext();
        globalValue = new GlobalValue(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        action_team_id123 = bundle.getString("payer_id");



        Cursor res11 = mydb.get_player_id(action_team_id123);
        if (res11 != null) {

            if (res11.getCount() == 0) {

                Toast.makeText(DetailActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                return;
            } else {

                while (res11.moveToNext()) {

                    player_name.setText(res11.getString(1));
                    byte[] image11 = res11.getBlob(6);
                    Bitmap bmp = BitmapFactory.decodeByteArray(image11, 0, image11.length);


                    image.setImageBitmap(bmp);


                }
            }
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTeamPOPUP(getIntent());
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddTeamPOPUP(getIntent());

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailActivity.this);
                alertDialogBuilder.setMessage("Are you sure, You wanted Delete this Player");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {


                                boolean isdeleted = mydb.delete_player(action_team_id123);
                                if (isdeleted) {
                                    Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    PlayersList.Get_Team_details();


                                } else {
                                    Toast.makeText(DetailActivity.this, "Failed to Deleted", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

        Cursor res = mydb.getAll_Teams();
        if (res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();

        }
        top_navigation_constraint = (BubbleNavigationLinearView) findViewById(R.id.top_navigation_constraint);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Player_batting_fragment())
                .commit();
        top_navigation_constraint.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                //navigation changed, do something
                if (position == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Player_batting_fragment())
                            .commit();
                }
                if (position == 1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Player_bowling_fragment())
                            .commit();
                }
                if (position == 2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Player_fielding_fragment())
                            .commit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Checks if the navigation drawer is open -- If so, close it

Intent intent =new Intent(DetailActivity.this,PlayersList.class);
startActivity(intent);


    }


    public void AddTeamPOPUP(final Intent context) {
        TextView submit;
        TextView from, to;
        EditText item_name;
        ImageView close1;
        final Dialog dialog = new Dialog(DetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_player);

        close1 = (ImageView) dialog.findViewById(R.id.close1);

        submit = (TextView) dialog.findViewById(R.id.submit_item);
        item_name = (EditText) dialog.findViewById(R.id.item_name);
        add_image = (ImageView) dialog.findViewById(R.id.add_image);


        Cursor res = mydb.get_player_id(action_team_id123);
        if (res != null) {

            if (res.getCount() == 0) {

                Toast.makeText(DetailActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                return;
            } else {

                while (res.moveToNext()) {

                    item_name.setText(res.getString(1));
                    byte[] image = res.getBlob(6);
                    Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);


                    add_image.setImageBitmap(bmp);


                }
            }
        }

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    openGallery();
                } else {


                    if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            init();
                    } else {
                        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.CAMERA}, 101);
                    }
                    if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);
                    }

                    if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


                    } else {
                        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 103);
                    }


                }


            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (item_name.getText().toString().length() == 0) {
                    item_name.setError("Required*");
                    item_name.requestFocus();

                } else {
                    item_name.setError(null);
                    String fhud = action_team_id123;
                    String gvrfr = item_name.getText().toString();
                    Bitmap bitmap = ((BitmapDrawable) add_image.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageInByte = baos.toByteArray();
                    Players_model worldpop = new Players_model(action_team_id123, item_name.getText().toString(), "", "", "0", "0", imageInByte);

                    boolean isinserted = mydb.update_player_name(worldpop);

                    if (isinserted) {
                        Toast.makeText(DetailActivity.this, "Player Updated", Toast.LENGTH_SHORT).show();
                        Cursor res11 = mydb.get_player_id(action_team_id123);
                        if (res11 != null) {

                            if (res11.getCount() == 0) {

                                Toast.makeText(DetailActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                                return;
                            } else {

                                while (res11.moveToNext()) {

                                    player_name.setText(res11.getString(1));
                                    byte[] image11 = res11.getBlob(6);
                                    Bitmap bmp = BitmapFactory.decodeByteArray(image11, 0, image11.length);


                                    image.setImageBitmap(bmp);


                                }
                            }
                        }


                    } else {
                        Toast.makeText(DetailActivity.this, "Failed to Update Player", Toast.LENGTH_SHORT).show();
                    }

                    dialog.dismiss();
                }

            }
        });


        try {
            if (!(dialog.isShowing())) {
                dialog.show();
            } else {
                dialog.dismiss();
                //   filtered_date.setText(globalValue.getString("from_receipt")+" to "+globalValue.getString("to_receipt"));
            }
        } catch (Exception e) {

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

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
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
}