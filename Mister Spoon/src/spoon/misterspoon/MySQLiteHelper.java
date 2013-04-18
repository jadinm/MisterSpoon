package spoon.misterspoon;

import static android.provider.BaseColumns._ID;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "restaurant_database.db";
	public static final int DATABASE_VERSION = 1;
	
	//public static final String TABLE_ITEMS = "items";
	public static final String TABLE_Address = "Adresse";
	public static final String[] Address_column = new String[]{_ID, "gps", "numero", "rue", "ville"};
	
	public static final String TABLE_Contact= "Contact";
	public static final String[] Contact_column = new String[]{_ID, "telephone", "mailPerso", "fax", "email", "siteWeb"};
	
	public static final String TABLE_Advantage= "Avantage";
	public static final String[] Advantage_column = new String[]{_ID, "restNom", "avantagesSpec"};
	
	public static final String TABLE_Client= "Client";
	public static final String[] Client_column = new String[]{_ID, "email", "nom", "langue", "gsm"};
	
	public static final String TABLE_Cuisine= "Cuisine";
	public static final String[] Cuisine_column = new String[]{_ID, "restNom", "typeCuisine"};
	
	public static final String TABLE_Horaire= "Horaire";
	public static final String[] Horaire_column = new String[]{_ID, "restNom", "jourOuverture", "openHour", "closeHour"};
	
	public static final String TABLE_Menu= "Menu";
	public static final String[] Menu_column = new String[]{_ID, "menuNom", "restNom", "platNom"};
	
	public static final String TABLE_MenuPrice= "MenuPrix";
	public static final String[] MenuPrice_column = new String[]{_ID, "restNom", "menuNom", "prix"};
	
	public static final String TABLE_Payement= "Paiement";
	public static final String[] Payement_column = new String[]{_ID, "restNom", "typePaiement"};
	
	public static final String TABLE_Meal= "Plat";
	public static final String[] Meal_column = new String[]{_ID, "platNom", "restNom", "prix", "stock"};
	
	public static final String TABLE_FavouriteMeal= "PlatFavori";
	public static final String[] FavouriteMeal_column = new String[]{_ID, "email", "platNom"};
	
	public static final String TABLE_Booking= "Reservation";
	public static final String[] Booking_column = new String[]{_ID, "restNom", "email", "NbrReservation", "heure"};
	
	public static final String TABLE_Command= "Commande";
	public static final String[] Command_column = new String[]{_ID, "restNom", "email", "heure", "platNom", "quantite"};
	
	public static final String TABLE_Restaurant= "Restaurant";
	public static final String[] Restaurant_column = new String[]{_ID, "restNom", "email", "heure", "platNom", "quantite"};
	
	public static final String TABLE_FavouriteRestaurant= "RestoFavori";
	public static final String[] FavouriteRestaurant_column = new String[]{_ID, "email", "restNom"};
	
	public static final String TABLE_Specificity= "Specificite";
	public static final String[] Specificity_column = new String[]{_ID, "email", "spec"};
	
	public static final String TABLE_Allergy= "Allergie";
	public static final String[] Allergy_column = new String[]{_ID, "email", "allergie"};
	
	public static final String TABLE_MealSpecificity= "SpecificitePlat";
	public static final String[] MealSpecificity_column = new String[]{_ID, "platNom", "restNom", "specificite"};
	
	
	public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createDatabase(db);
		populateDatabase(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		deleteDatabase(db);
		onCreate(db);
	}
	
	public void createDatabase(SQLiteDatabase db) {//
		db.execSQL("CREATE TABLE " + TABLE_Address +  "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Address_column[1] + " VARCHAR NOT NULL UNIQUE ," + Address_column[2] + " VARCHAR, " + Address_column[3] + " VARCHAR NOT NULL, " + Address_column[4] + " VARCHAR NOT NULL);");
		db.execSQL("CREATE TABLE " + TABLE_Contact + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Contact_column[1] + " VARCHAR NOT NULL UNIQUE, " + Contact_column[2] + " VARCHAR NOT NULL UNIQUE, " + Contact_column[3] + " VARCHAR, " + Contact_column[4] + " VARCHAR, " + Contact_column[5] + " VARCHAR);");
		db.execSQL("CREATE TABLE " + TABLE_Restaurant + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Restaurant_column[1] + " VARCHAR NOT NULL UNIQUE, " + Restaurant_column[2] + " VARCHAR NOT NULL UNIQUE, " + Restaurant_column[3] + " VARCHAR NOT NULL UNIQUE, " + Restaurant_column[4] + " VARCHAR NOT NULL UNIQUE, " + Restaurant_column[5] + " INTEGER NOT NULL, " + Restaurant_column[6] + " TEXT, FOREIGN KEY ("+Restaurant_column[3]+") REFERENCES " + TABLE_Contact + "("+Contact_column[1]+"), FOREIGN KEY ("+Restaurant_column[4]+") REFERENCES " + TABLE_Address +"("+Address_column[1]+"));");
		db.execSQL("CREATE TABLE " + TABLE_Client + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Client_column[1] + " VARCHAR NOT NULL  UNIQUE, " + Client_column[2] + "  VARCHAR NOT NULL  UNIQUE , " + Client_column[3] + "  VARCHAR, " + Client_column[4] + "  VARCHAR NOT NULL  UNIQUE );");
		db.execSQL("CREATE TABLE " + TABLE_Advantage + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Advantage_column[1] + " VARCHAR NOT NULL," + Advantage_column[2] + " VARCHAR NOT NULL, FOREIGN KEY (" + Advantage_column[1] + ") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+"));");
		db.execSQL("CREATE TABLE " + TABLE_Cuisine + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Cuisine_column[1] +" VARCHAR NOT NULL," + Cuisine_column[2] +" VARCHAR NOT NULL, FOREIGN KEY ("+Cuisine_column[1]+") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+"));");
		db.execSQL("CREATE TABLE " + TABLE_Horaire + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Horaire_column[1] +" VARCHAR NOT NULL, " + Horaire_column[2] + " VARCHAR NOT NULL, " + Horaire_column[3] +" NOT NULL, " + Horaire_column[4] +" NOT NULL, FOREIGN KEY (" + Horaire_column[1] +") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+"));");
		db.execSQL("CREATE TABLE " + TABLE_Menu + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Menu_column[1] + " VARCHAR NOT NULL, " + Menu_column[2] + " VARCHAR NOT NULL, " + Menu_column[3] + " VARCHAR NOT NULL,  FOREIGN KEY ("+Menu_column[2]+") REFERENCES Restaurant (restNom));");
		db.execSQL("CREATE TABLE " + TABLE_MenuPrice + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + MenuPrice_column[1]  + " VARCHAR NOT NULL, " + MenuPrice_column[2] + " VARCHAR NOT NULL, " + MenuPrice_column[3] + "  DECIMAL(9,2) NOT NULL, UNIQUE ("+ MenuPrice_column[1]+", " + MenuPrice_column[2] +") ON CONFLICT REPLACE, FOREIGN KEY ("+MenuPrice_column[1]+") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+"));");
		db.execSQL("CREATE TABLE " + TABLE_Payement + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Payement_column[1] + " VARCHAR NOT NULL, " + Payement_column[2] + " VARCHAR NOT NULL,  FOREIGN KEY ("+Payement_column[1]+") REFERENCES Restaurant ("+Payement_column[2]+"));");
		db.execSQL("CREATE TABLE " + TABLE_Meal + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Meal_column[1] + " VARCHAR NOT NULL, " + Meal_column[2] + " VARCHAR NOT NULL, " + Meal_column[3] + " DECIMAL(9,2) NOT NULL, s" + Meal_column[4] + " INTEGER, UNIQUE ("+Meal_column[1]+", " + Meal_column[2]+") ON CONFLICT REPLACE, FOREIGN KEY ("+Meal_column[2]+") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+"));");
		db.execSQL("CREATE TABLE " + TABLE_FavouriteMeal + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + FavouriteMeal_column[1] + " VARCHAR NOT NULL , " + FavouriteMeal_column[2] + " VARCHAR NOT NULL , FOREIGN KEY ("+FavouriteMeal_column[1]+") REFERENCES Client("+Client_column[1]+"));");
		db.execSQL("CREATE TABLE " + TABLE_Booking + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Booking_column[1] + " VARCHAR NOT NULL, " + Booking_column[2] + " VARCHAR NOT NULL, " + Booking_column[3] + " INTEGER NOT NULL, " + Booking_column[4] + " VARCHAR NOT NULL, UNIQUE("+Booking_column[1]+", "+ Booking_column[4] + ", "+ Booking_column[2] +") ON CONFLICT REPLACE, FOREIGN KEY ("+Booking_column[1]+") REFERENCES "+ TABLE_Restaurant +"("+Restaurant_column[1]+"), FOREIGN KEY ("+Booking_column[2]+") REFERENCES "+TABLE_Client+ "("+Client_column[1]+"));");
		db.execSQL("CREATE TABLE " + TABLE_Command + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Command_column[1] +" VARCHAR NOT NULL, " + Command_column[2] +" VARCHAR NOT NULL, " + Command_column[3] + " VARCHAR, " + Command_column[4] + " VARCHAR NOT NULL, " + Command_column[5] + " INTEGER NOT NULL, FOREIGN KEY ("+Command_column[1]+") REFERENCES Restaurant (restNom), FOREIGN KEY ("+Command_column[2]+") REFERENCES "+ TABLE_Client +"("+Client_column[1]+"));");
		db.execSQL("CREATE TABLE " + TABLE_FavouriteRestaurant + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + FavouriteRestaurant_column[1] + " VARCHAR NOT NULL , " + FavouriteRestaurant_column[2] + " VARCHAR NOT NULL, FOREIGN KEY ("+FavouriteRestaurant_column[1]+") REFERENCES " + TABLE_Client + "("+Client_column[1]+"), FOREIGN KEY ("+FavouriteRestaurant_column[2]+") REFERENCES " + TABLE_Restaurant + "("+Restaurant_column[1]+"));");
		db.execSQL("CREATE TABLE " + TABLE_Specificity + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Specificity_column[1] + " VARCHAR NOT NULL , " + Specificity_column[2] + " VARCHAR NOT NULL, FOREIGN KEY ("+Specificity_column[1]+") REFERENCES "+TABLE_Client+"("+Client_column[1]+"));");
		db.execSQL("CREATE TABLE " + TABLE_Allergy + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Allergy_column[1] + " VARCHAR NOT NULL , " + Allergy_column[2] + " VARCHAR NOT NULL, FOREIGN KEY ("+Allergy_column[1]+") REFERENCES "+TABLE_Client+"("+Client_column[1]+"));");
		db.execSQL("CREATE TABLE " + TABLE_Allergy + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + MealSpecificity_column[1] + " VARCHAR NOT NULL, " + MealSpecificity_column[2] + " VARCHAR NOT NULL, " + MealSpecificity_column[3] + " VARCHAR NOT NULL, FOREIGN KEY ("+MealSpecificity_column[2]+") REFERENCES "+TABLE_Restaurant+"("+Restaurant_column[1]+"));");
		
	}
	public void populateDatabase(SQLiteDatabase db) {
		db.execSQL("INSERT INTO " + TABLE_Address + " VALUES('50.668572,4.616146','1A','Place des Brabançons','Louvain-la-Neuve');");
	}
	
	public void deleteDatabase(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Address);
	}

}

