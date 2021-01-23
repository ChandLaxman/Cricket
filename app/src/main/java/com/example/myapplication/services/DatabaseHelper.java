package com.example.myapplication.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.models.Players_model;
import com.example.myapplication.models.Teams_model;


public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Cricket Scorer";

    public static final String ADD_Teams = "teams";
    public static final String team_id = "team_id";
    public static final String team_name = "team_name";
    public static final String team_matches = "team_matches";
    public static final String team_won = "team_won";
    public static final String team_lost = "team_lost";


    public static final String ADD_player = "players";
    public static final String player_id = "player_id";
    public static final String player_name = "player_name";
    public static final String player_team_id = "player_team_id";
    public static final String player_team_name = "player_team_name";
    public static final String player_total_matches = "player_total_matches";
    public static final String player_total_innings = "player_total_innings";
    public static final String KEY_IMG_URL = "ImgFavourite";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables

    @Override
    public void onCreate(final SQLiteDatabase db) {

        String ADD_TEAM__ =
                "CREATE TABLE " + ADD_Teams + "("

                        + team_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + team_name + " TEXT,"
                        + team_matches + " TEXT,"
                        + team_won + " TEXT,"
                        + team_lost + " TEXT"

                        + ")";

        db.execSQL(ADD_TEAM__);
        String ADD_PLAYERS__ =
                "CREATE TABLE " + ADD_player + "("

                        + player_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + player_name + " TEXT,"
                        + player_team_id + " TEXT,"
                        + player_team_name + " TEXT,"
                        + player_total_matches + " TEXT,"
                        + player_total_innings + " TEXT,"
                        + KEY_IMG_URL + " BLOB "
                        + ")";

        db.execSQL(ADD_PLAYERS__);
        
    }
    public static void deleteDatabase(Context mContext) {
        mContext.deleteDatabase(DATABASE_NAME);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed

        db.execSQL("DROP TABLE IF EXISTS " + ADD_Teams);
        db.execSQL("DROP TABLE IF EXISTS " + ADD_player);

        // Create tables again
        onCreate(db);
    }

    public boolean insert_teams(Teams_model store) {

        //  private String team_id,team_name,team_matches,team_won,team_lost;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
       // values.put(team_id,store.get());
        values.put(team_name,store.getTeam_name());
        values.put(team_matches,store.getTeam_matches());
        values.put(team_won,store.getTeam_won());
        values.put(team_lost,store.getTeam_lost());

        long result =  db.insert(ADD_Teams, null, values);
        db.close();
        if(result == -1)
            return false;
        else
            return true;

    }
    public boolean insert_players(Players_model store) {

        //player_id,player_name,player_team_id,player_team_name,player_total_matches,player_total_innings;


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
       // values.put(team_id,store.get());
        values.put(player_name,store.getPlayer_name());
        values.put(player_team_id,store.getPlayer_team_id());
        values.put(player_team_name,store.getPlayer_team_name());
        values.put(player_total_matches,"");
        values.put(player_total_innings,"");
        values.put(KEY_IMG_URL,store.getImageBytes());

        long result =  db.insert(ADD_player, null, values);
        db.close();
        if(result == -1)
            return false;
        else
            return true;

    }

//
//    public  boolean check_team_name(String dbfield) {
//        SQLiteDatabase sqldb = this.getWritableDatabase();
//        String Query = "Select * from " + ADD_Teams + " where " + team_name + " = " + dbfield;
//        Cursor cursor = sqldb.rawQuery(Query, null);
//        if(cursor.getCount() <= 0){
//            cursor.close();
//            return false;
//        }
//        else
//        {
//            cursor.close();
//            return true;
//        }
//    }


    public  boolean check_team_name(String dbfield) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String Query = "Select * from " + ADD_Teams + " where " + team_name + " = ?";
        Cursor cursor = sqldb.rawQuery(Query, new String[] { dbfield });
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }




        public  boolean check_player_name(String playername,String team_id) {
        SQLiteDatabase sqldb = this.getWritableDatabase();

        String Query = "Select * from " + ADD_player + " where " + player_name + " =?"  +" AND "+ player_team_id + " =?";
        Cursor cursor = sqldb.rawQuery(Query, new String[] { playername,team_id });
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        else
        {
            cursor.close();
            return true;
        }
    }
     public  boolean check_team() {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String Query = "Select * from " + ADD_Teams ;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        else
        {
            cursor.close();
            return true;
        }
    }

//    public Cursor getAllTeams(String item_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from "+ADD_Teams+" WHERE "+ item_id + "=" + item_id,null);
//        return res;
//    }

    public Cursor getAll_Teams() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ADD_Teams,null);
        return res;
    }
  public Cursor getAll_Team_players(String team_id) {
        SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor res = db.rawQuery("select * from "+ADD_Teams,null);
      Cursor res = db.rawQuery("select * from "+ADD_player+" WHERE "+ player_team_id + "=" + team_id,null);

      return res;
    }
  public Cursor get_player_id(String team_id) {
        SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor res = db.rawQuery("select * from "+ADD_Teams,null);
      Cursor res = db.rawQuery("select * from "+ADD_player+" WHERE "+ player_id + "=" + team_id,null);

      return res;
    } public Cursor get_team_id(String team_id) {
        SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor res = db.rawQuery("select * from "+ADD_Teams,null);
      Cursor res = db.rawQuery("select * from "+ADD_Teams+" WHERE "+ team_id + "=" + team_id,null);

      return res;
    }

    public Cursor getsoitems_id(String item_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ADD_Teams+" WHERE "+ team_id + "=" + item_id,null);
        return res;
    }

    public boolean update_team_scrore(String team_id,String matches,String won,String lost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(team_matches,matches);
        values.put(team_won,won);
        values.put(team_lost,lost);

        db.update(ADD_Teams, values, "team_id = ?",new String[] {team_id});
        return true;

    }

    public boolean update_team_name(String team_id,String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(team_name,name);


        db.update(ADD_Teams, values, "team_id = ?",new String[] {team_id});
        return true;

    }
    public boolean update_player_name(Players_model store) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(player_name,store.getPlayer_name());
        values.put(KEY_IMG_URL,store.getImageBytes());

        db.update(ADD_player, values, "player_id = ?",new String[] {store.getPlayer_id()});
        return true;

    }


//    public boolean update_team_score(String qty,String amount,String total,String item_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(SO_qty,qty);
//        values.put(SO_amount,amount);
//        values.put(SO_total,total);
//        values.put(SO_item_id,item_id);
//
//
//         db.update(ADD_Teams, values, SO_item_id+ " = " +"'"+pack_id+"'" + " AND " +SO_id + " = " + "'" +main_id+ "'" ,null);
//        return true;
//    }
    
    public void delete_all_teams() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ ADD_Teams);
        db.close();
    }
    
     public boolean delete_team(String Cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ADD_Teams, team_id + " = ?",
                new String[]{Cart});

        long result =  db.delete(ADD_Teams, team_id + " = ?",
                new String[]{Cart});
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean delete_player(String Cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ADD_player, player_id + " = ?",
                new String[]{Cart});

        long result =  db.delete(ADD_player, player_id + " = ?",
                new String[]{Cart});
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }

}//android.database.sqlite.SQLiteException: AUTOINCREMENT is only allowed on an INTEGER PRIMARY KEY (code 1): , while compiling: CREATE TABLE stock_master ( id int(255) PRIMARY KEY AUTOINCREMENT , item_id int(255) , item_name varchar(150) , godown_id int(10) , outlet_id int(10) , from_source int(10) , company_id int(10) , total_qty decimal(12,3) , remaining_qty decimal(12,3) , total_conversion_qty decimal(12,3) , remaining_conversion_qty decimal(12,3) , reference_id varchar(100) , description varchar(30) , uom_id int(10) , status varchar(20) , created_by varchar(50) , created_date datetime , updated_by varchar(50) , updated_date datetime )