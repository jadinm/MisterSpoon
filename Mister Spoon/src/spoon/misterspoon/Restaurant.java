package spoon.misterspoon;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Restaurant {
	
	public MySQLiteHelper sqliteHelper;
	
	String restaurantName;
	int nbrVotants;
	int note;
	int capacity;
	String Description;
	
	String phone;
	String email;
	String fax;
	String webSite;
	
	GPS position;
	int numero;
	String rue;
	String ville;
	
	ArrayList <OpenHour> horaire;
	
	ArrayList <String> typePaiements;
	
	ArrayList <String> avantages;
	
	ArrayList <String> cuisine;
	
	Carte carte;
	
	/*
	 * @param: the parameter is not null
	 * @post : create an object Restaurant with only its 'restaurantName' filled
	 */
	public Restaurant (String restaurantName) {
		
		this.restaurantName = restaurantName;
	}
	
	/*
	 * @param: the parameters are not null
	 * @post: create an object Restaurant and fill it with the information in the database
	 */
	public Restaurant (MySQLiteHelper sqliteHelper, String restaurantName) {
		
		this.restaurantName = restaurantName;
		this.sqliteHelper = sqliteHelper;
		this.horaire = new ArrayList<OpenHour>();
		this.avantages = new ArrayList<String>();
		this.cuisine = new ArrayList<String>();
		this.typePaiements = new ArrayList<String>();
		
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();
		
		// "telephone", "gps", "capaTotale", "description", "position", "phone"
		Cursor cursor = db.rawQuery ("SELECT " + MySQLiteHelper.Restaurant_column[3] + ", " + MySQLiteHelper.Restaurant_column[4] + ", " + MySQLiteHelper.Restaurant_column[5] + ", " + MySQLiteHelper.Restaurant_column[6] + ", " + MySQLiteHelper.Restaurant_column[7] + ", " + MySQLiteHelper.Restaurant_column[8] + " FROM " + MySQLiteHelper.TABLE_Restaurant + " WHERE " + MySQLiteHelper.Restaurant_column[1] + " = " + restaurantName, null);
		if (cursor.moveToFirst()) {
			
			phone = cursor.getString(0);
			position = new GPS (cursor.getString(1));
			capacity = cursor.getInt(2);
			Description = cursor.getString(3);
			note = cursor.getInt(4);
			nbrVotants = cursor.getInt(5);
		}
		
		//"email", "fax", "webSite"
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Contact_column[2] + ", " + MySQLiteHelper.Contact_column[3] + ", " + MySQLiteHelper.Contact_column[4] + " FROM " + MySQLiteHelper.TABLE_Contact + " WHERE " + MySQLiteHelper.Contact_column[1] + " = " + phone, null);
		if (cursor.moveToFirst()) {

			fax = cursor.getString(0);
			email = cursor.getString(1);
			webSite = cursor.getString(2);
		}
		
		//"numero", "rue", "ville"
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Address_column[2] + ", " + MySQLiteHelper.Address_column[3] + ", " + MySQLiteHelper.Address_column[4] + " FROM " + MySQLiteHelper.TABLE_Address + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + position.toString(), null);
		if (cursor.moveToFirst()) {
			
			if (cursor.getString(0)!=null) {
				numero = cursor.getInt(0);
			}
			rue = cursor.getString(1);
			ville = cursor.getString(2);
		}
		
		//"horaire"
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Schedule_column[2] + ", " + MySQLiteHelper.Schedule_column[3] + ", " + MySQLiteHelper.Schedule_column[4] + " FROM " + MySQLiteHelper.TABLE_Schedule + " WHERE " + MySQLiteHelper.Schedule_column[1] + " = " + restaurantName, null);
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast()) {//If there is one element more to read
				horaire.add(new OpenHour(cursor.getString(0), new Time(cursor.getString(1)), new Time (cursor.getString(2))));
			}
		}
		
		//"typePaiements"
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Payment_column[2] + " FROM " + MySQLiteHelper.TABLE_Payment + " WHERE " + MySQLiteHelper.Payment_column[1] + " = " + restaurantName, null);
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast()) {//If there is one element more to read
				typePaiements.add(cursor.getString(0));
			}
		}
		
		//"avantages"
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Advantage_column[2] + " FROM " + MySQLiteHelper.TABLE_Advantage + " WHERE " + MySQLiteHelper.Advantage_column[1] + " = " + restaurantName, null);
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast()) {//If there is one element more to read
				avantages.add(cursor.getString(0));
			}
		}
		
		//"cuisine"
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Cook_column[2] + " FROM " + MySQLiteHelper.TABLE_Cook + " WHERE " + MySQLiteHelper.Cook_column[1] + " = " + restaurantName, null);
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast()) {//If there is one element more to read
				cuisine.add(cursor.getString(0));
			}
		}
		
		//Cartes
		this.carte = new Carte (this.sqliteHelper, this.restaurantName);
		
		
		db.close();
	}
	
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public void setRestaurantNbrVotants(int nbrVotants) {
		this.nbrVotants = nbrVotants;
	}

	public void setRestaurantNote(int note) {
		this.note = note;
	}

	public void setRestaurantCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setRestaurantDescription(String description) {
		Description = description;
	}

	public void setRestaurantPhone(String phone) {
		this.phone = phone;
	}

	public void setRestaurantEmail(String email) {
		this.email = email;
	}

	public void setRestaurantFax(String fax) {
		this.fax = fax;
	}

	public void setRestaurantWebSite(String webSite) {
		this.webSite = webSite;
	}

	public void setRestaurantPosition(GPS position) {
		this.position = position;
	}

	public void setRestaurantNumero(int numero) {
		this.numero = numero;
	}

	public void setRestaurantRue(String rue) {
		this.rue = rue;
	}

	public void setRestaurantVille(String ville) {
		this.ville = ville;
	}

	public void setRestaurantHoraire(ArrayList<OpenHour> horaire) {
		this.horaire = horaire;
	}

	public void setRestaurantCarte(Carte carte) {
		this.carte = carte;
	}

	/*
	 * @post : return the value of 'restaurantName'
	 */
	public String getRestaurantName () {
		
		return restaurantName;
	}
	
	/*
	 * @post : return the value of 'nbrVotants'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public int getRestaurantNbrVotants (boolean getFromDatabase) {
		
		if(getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery ("SELECT " + MySQLiteHelper.Restaurant_column[8] + " FROM " + MySQLiteHelper.TABLE_Restaurant + " WHERE " + MySQLiteHelper.Restaurant_column[1] + " = " + restaurantName, null);
			if (cursor.moveToFirst()) {
				
				nbrVotants = cursor.getInt(0);
			}
			db.close();
		}
		
		return nbrVotants;
	}
	
	/*
	 * @post : return the value of 'note'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public int getRestaurantNote (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery ("SELECT " + MySQLiteHelper.Restaurant_column[7] + " FROM " + MySQLiteHelper.TABLE_Restaurant + " WHERE " + MySQLiteHelper.Restaurant_column[1] + " = " + restaurantName, null);
			if (cursor.moveToFirst()) {
				
				note = cursor.getInt(0);
			}
			db.close();
		}
		
		return note;
	}
	
	/*
	 * @post : return the value of 'capacity'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public int getRestaurantCapacity(boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery ("SELECT " + MySQLiteHelper.Restaurant_column[5] + " FROM " + MySQLiteHelper.TABLE_Restaurant + " WHERE " + MySQLiteHelper.Restaurant_column[1] + " = " + restaurantName, null);
			if (cursor.moveToFirst()) {
				
				capacity = cursor.getInt(0);
			}
			db.close();
		}
		
		return capacity;
	}
	
	/*
	 * @post : return the value of 'description'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public String getRestaurantDescription (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery ("SELECT " + MySQLiteHelper.Restaurant_column[6] + " FROM " + MySQLiteHelper.TABLE_Restaurant + " WHERE " + MySQLiteHelper.Restaurant_column[1] + " = " + restaurantName, null);
			if (cursor.moveToFirst()) {
				
				Description = cursor.getString(0);
			}
			db.close();
		}
		
		return Description;
		
	}
	
	/*
	 * @post : return the value of 'phone'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public String getRestaurantPhone (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery ("SELECT " + MySQLiteHelper.Restaurant_column[3] + " FROM " + MySQLiteHelper.TABLE_Restaurant + " WHERE " + MySQLiteHelper.Restaurant_column[1] + " = " + restaurantName, null);
			if (cursor.moveToFirst()) {
				
				phone = cursor.getString(0);
			}
			
			db.close();
		}
		
		return phone;
	}
	
	/*
	 * @post : return the value of 'email'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public String getRestaurantEmail (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Contact_column[3] + " FROM " + MySQLiteHelper.TABLE_Contact + " WHERE " + MySQLiteHelper.Contact_column[1] + " = " + phone, null);
			if (cursor.moveToFirst()) {

				email = cursor.getString(0);
			}
			
			
			db.close();
		}
		
		return email;
	}
	
	/*
	 * @post : return the value of 'fax'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public String getRestaurantFax (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Contact_column[2] + " FROM " + MySQLiteHelper.TABLE_Contact + " WHERE " + MySQLiteHelper.Contact_column[1] + " = " + phone, null);
			if (cursor.moveToFirst()) {

				fax = cursor.getString(0);
			}
			
			
			db.close();
		}
		
		return fax;
		
	}
	
	/*
	 * @post : return the value of 'webSite'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public String getRestaurantWebSite (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Contact_column[4] + " FROM " + MySQLiteHelper.TABLE_Contact + " WHERE " + MySQLiteHelper.Contact_column[1] + " = " + phone, null);
			if (cursor.moveToFirst()) {

				webSite = cursor.getString(0);
			}
			
			
			db.close();
		}
		
		return webSite;
	}
	
	/*
	 * @post : return the value of 'position'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public GPS getRestaurantPosition (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery ("SELECT " + MySQLiteHelper.Restaurant_column[4] + " FROM " + MySQLiteHelper.TABLE_Restaurant + " WHERE " + MySQLiteHelper.Restaurant_column[1] + " = " + restaurantName, null);
			if (cursor.moveToFirst()) {
				
				position = new GPS (cursor.getString(0));
			}			
			
			db.close();
		}
		
		return position;
	}
	
	/*
	 * @post : return the value of 'numero'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public int getRestaurantNumero (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Address_column[2] + " FROM " + MySQLiteHelper.TABLE_Address + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + position.toString(), null);
			if (cursor.moveToFirst()) {
				
				if (cursor.getString(0)!=null) {
					numero = cursor.getInt(0);
				}
			}
			
			db.close();
		}
		
		return numero;
	}
	
	/*
	 * @post : return the value of 'rue'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public String getRestaurantRue (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Address_column[3] + " FROM " + MySQLiteHelper.TABLE_Address + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + position.toString(), null);
			if (cursor.moveToFirst()) {
				
				rue = cursor.getString(0);
			}
			
			db.close();
		}
		
		return rue;
	}
	
	/*
	 * @post : return the value of 'ville'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public String getRestaurantVille (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Address_column[4] + " FROM " + MySQLiteHelper.TABLE_Address + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + position.toString(), null);
			if (cursor.moveToFirst()) {
				
				ville = cursor.getString(0);
			}
			
			db.close();
		}
		
		return ville;
	}
	
	/*
	 * @post : return the value of 'horaire'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public ArrayList <OpenHour> getRestaurantHoraire (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Schedule_column[2] + ", " + MySQLiteHelper.Schedule_column[3] + ", " + MySQLiteHelper.Schedule_column[4] + " FROM " + MySQLiteHelper.TABLE_Schedule + " WHERE " + MySQLiteHelper.Schedule_column[1] + " = " + restaurantName, null);
			if (cursor.moveToFirst()) {
				while (cursor.isAfterLast()) {//If there is one element more to read
					horaire.add(new OpenHour(cursor.getString(0), new Time(cursor.getString(1)), new Time (cursor.getString(2))));
				}
			}
			
			db.close();
		}
		
		return horaire;
		
	}
	
	/*
	 * @post : return the value of 'typePaiments'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public ArrayList <String> getRestaurantTypePaiements (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Payment_column[2] + " FROM " + MySQLiteHelper.TABLE_Payment + " WHERE " + MySQLiteHelper.Payment_column[1] + " = " + restaurantName, null);
			if (cursor.moveToFirst()) {
				while (cursor.isAfterLast()) {//If there is one element more to read
					typePaiements.add(cursor.getString(0));
				}
			}
			
			db.close();
		}
		
		return typePaiements;
		
	}
	
	/*
	 * @post : return the value of 'avantages'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public ArrayList <String> getRestaurantAvantages (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Advantage_column[2] + " FROM " + MySQLiteHelper.TABLE_Advantage + " WHERE " + MySQLiteHelper.Advantage_column[1] + " = " + restaurantName, null);
			if (cursor.moveToFirst()) {
				while (cursor.isAfterLast()) {//If there is one element more to read
					avantages.add(cursor.getString(0));
				}
			}
			
			db.close();
		}
		
		return avantages;
	}
	
	/*
	 * @post : return the value of 'cuisine'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public ArrayList <String> getRestaurantCuisine (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Cook_column[2] + " FROM " + MySQLiteHelper.TABLE_Cook + " WHERE " + MySQLiteHelper.Cook_column[1] + " = " + restaurantName, null);
			if (cursor.moveToFirst()) {
				while (cursor.isAfterLast()) {//If there is one element more to read
					cuisine.add(cursor.getString(0));
				}
			}
			
			db.close();
		}
		
		return cuisine;
	}
	
	public void addRestaurantHoraire(OpenHour horaire){
		this.horaire.add(horaire);
	}
	
	public void removeRestaurantHoraire(int index){
		this.horaire.remove(index);
	}
	
	public void addRestaurantTypePaiements(String type){
		this.typePaiements.add(type);
	}
	
	public void removeRestaurantTypePaiements(int index){
		this.typePaiements.remove(index);
	}
	
	public void addRestaurantAvantages(String avantage){
		this.avantages.add(avantage);
	}
	
	public void removeRestaurantAvantages(int index){
		this.avantages.remove(index);
	}
	
	public void addRestaurantCuisine(String cuisine){
		this.cuisine.add(cuisine);
	}
	
	public void removeRestaurantCuisine(int index){
		this.cuisine.remove(index);
	}

	
	//TODO
	/* 
	 * @post : return the value of 'carte'
	 * If getFromDatabase is true, this value is get from the database
	 */
	public Carte getRestaurantCarte (boolean getFromDatabase) {
		if (getFromDatabase) {
			this.carte = new Carte (this.sqliteHelper, this.restaurantName);
		}
		return this.carte;
	}
	
	//TODO
	public boolean setRestaurantCarte (Carte carte) {
		if (carte==null) {
			return false;
		}
		else {
			this.carte = carte;
		}
		return true;
	}
	
}
