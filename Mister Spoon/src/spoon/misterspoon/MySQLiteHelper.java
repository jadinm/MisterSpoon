package spoon.misterspoon;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static android.provider.BaseColumns._ID;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "my_first_database.db";
	public static final int DATABASE_VERSION = 1;
	
	//public static final String TABLE_ITEMS = "items";
	public static final String TABLE_Adress = "Adresse";
	public static final String[] Adress_column = new String[]{_ID, "gps", "numero", "rue", "ville" };
	
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
	
	public void createDatabase(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_Adress +  "(" + Adress_column[1] + " VARCHAR PRIMARY KEY NOT NULL UNIQUE ," + Adress_column[2] + " VARCHAR, " + Adress_column[3] + " VARCHAR NOT NULL, " + Adress_column[4] + " VARCHAR NOT NULL);");
	}
	public void populateDatabase(SQLiteDatabase db) {
			db.execSQL("INSERT INTO " + TABLE_Adress + " VALUES('50.668572,4.616146','1A','Place des Brabançons','Louvain-la-Neuve');");
	}
	
	public void deleteDatabase(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Adress);
	}

}

