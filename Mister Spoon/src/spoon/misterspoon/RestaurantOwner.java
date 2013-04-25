package spoon.misterspoon;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RestaurantOwner {
	
	public MySQLiteHelper sqliteHelper;
	
	private String emailPerso;
	private Restaurant restaurant;
	private ArrayList<PreBooking> preBooking;
	private ArrayList<Booking> booking;
	
	public RestaurantOwner(MySQLiteHelper sqliteHelper, String emailPerso) {
		this.preBooking = new ArrayList<PreBooking>();
		this.booking = new ArrayList<Booking>();
		this.sqliteHelper = sqliteHelper;
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		this.emailPerso = emailPerso;
		
		db.close();
	}
	
	/*
	 * @post : return the email of the client
	 */
	public String getEmail () {
		return this.emailPerso;
	}

	public boolean setRestaurantEmail(String email) {
		if (email == null) {
			return false;
		}
		if(!email.equals(restaurant.getRestaurantEmail(false))){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[2] + " = " + email + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[2] + " = " + email + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + email + " ;");
			db.close();
			restaurant.setRestaurantEmail(email);
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantName(String name) {
		if (name == null) {
			return false;
		}
		if(!name.equals(restaurant.getRestaurantName())){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[1] + " = " + name + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[2] + " = " + name + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
			db.close();
			restaurant.setRestaurantName(name);
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantCapacity(int capacity) {
		if(capacity != restaurant.getRestaurantCapacity(false)){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if (capacity != 0) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[5] + " = " + capacity + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
				db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[5] + " = " + capacity + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.setRestaurantCapacity(capacity);
		}
		return true;
	}
	
	public boolean setRestaurantDescription(String description) {
		if (description == null) {
			return false;
		}
		if(!description.equals(restaurant.getRestaurantDescription(false))){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[6] + " = " + description + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[6] + " = " + description + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
			db.close();
			restaurant.setRestaurantDescription(description);
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantPhone(String phone) {
		if (phone == null) {
			return false;
		}
		if(!phone.equals(restaurant.getRestaurantPhone(false))){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[3] + " = " + phone + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[3] + " = " + phone + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
			db.close();
			restaurant.setRestaurantPhone(phone);
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantFax(String fax) {
		if (fax == null) {
			return false;
		}
		if(!fax.equals(restaurant.getRestaurantFax(false))){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Contact + " SET " + MySQLiteHelper.Contact_column[3] + " = " + fax + " WHERE " + MySQLiteHelper.Contact_column[4] + " = " + emailPerso + " ;");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Contact + " SET " + MySQLiteHelper.Contact_column[3] + " = " + fax + " WHERE " + MySQLiteHelper.Contact_column[4] + " = " + emailPerso + " ;");
			db.close();
			restaurant.setRestaurantFax(fax);
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantWebSite(String webSite) {
		if (webSite == null) {
			return false;
		}
		if(!webSite.equals(restaurant.getRestaurantWebSite(false))){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Contact + " SET " + MySQLiteHelper.Contact_column[5] + " = " + webSite + " WHERE " + MySQLiteHelper.Contact_column[4] + " = " + emailPerso + " ;");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Contact + " SET " + MySQLiteHelper.Contact_column[5] + " = " + webSite + " WHERE " + MySQLiteHelper.Contact_column[4] + " = " + emailPerso + " ;");
			db.close();
			restaurant.setRestaurantWebSite(webSite);
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantPosition(GPS position) {
		if (position == null) {
			return false;
		}
		if(position.getLongitude()!=restaurant.getRestaurantPosition(false).getLongitude() && position.getLatitude()!=restaurant.getRestaurantPosition(false).getLatitude()){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[1] + " = " + position + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getRestaurantPosition(false) + " ;");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[1] + " = " + position + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getRestaurantPosition(false) + " ;");
			db.close();
			restaurant.setRestaurantPosition(position);
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantNumero(int numero) {
		if(numero!=restaurant.getRestaurantNumero(false)){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if (numero != 0) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[2] + " = " + numero + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getRestaurantPosition(false) + " ;");
				db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[2] + " = " + numero + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getRestaurantPosition(false) + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.setRestaurantNumero(numero);
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantRue(String rue) {
		if (rue == null) {
			return false;
		}
		if(!rue.equals(restaurant.getRestaurantRue(false))){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[3] + " = " + rue + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getRestaurantPosition(false) + " ;");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[3] + " = " + rue + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getRestaurantPosition(false) + " ;");
			db.close();
			restaurant.setRestaurantRue(rue);
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantVille(String ville) {
		if (ville == null) {
			return false;
		}
		if(!ville.equals(restaurant.getRestaurantVille(false))){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[4] + " = " + ville + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getRestaurantPosition(false) + " ;");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[4] + " = " + ville + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getRestaurantPosition(false) + " ;");
			db.close();
			restaurant.setRestaurantVille(ville);
			return true;
		}
		return true;
	}
	
	public boolean addRestaurantHoraire(OpenHour horaire) {
		if (horaire != null) {
			if(restaurant.getRestaurantHoraire(false).size() != 0){
				for(OpenHour currentHoraire : restaurant.getRestaurantHoraire(false)){
					if(horaire.getOpenDay().equals(currentHoraire.getOpenDay()) && horaire.getCloseTime().equals(currentHoraire.getCloseTime()) && horaire.getOpenTime().equals(currentHoraire.getOpenTime())) return true;
				}
			}
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Schedule + " SET " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + " WHERE " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Schedule + " SET " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + " WHERE " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Schedule + " SET " + MySQLiteHelper.Schedule_column[2] + " = " + horaire.getOpenDay() + " WHERE " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Schedule + " SET " + MySQLiteHelper.Schedule_column[2] + " = " + horaire.getOpenDay() + " WHERE " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Schedule + " SET " + MySQLiteHelper.Schedule_column[3] + " = " + horaire.getOpenTime() + " WHERE " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Schedule + " SET " + MySQLiteHelper.Schedule_column[3] + " = " + horaire.getOpenTime() + " WHERE " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Schedule + " SET " + MySQLiteHelper.Schedule_column[4] + " = " + horaire.getCloseTime() + " WHERE " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Schedule + " SET " + MySQLiteHelper.Schedule_column[4] + " = " + horaire.getCloseTime() + " WHERE " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			db.close();
			restaurant.addRestaurantHoraire(horaire);
			return true;
		}
		else return false;
	}
	
	public boolean removeRestaurantHoraire(OpenHour horaire){
		if (horaire != null) {
			boolean found = false;
			int index = 0;
			for(OpenHour currentHoraire : restaurant.getRestaurantHoraire(false)){
				if(horaire.getOpenDay().equals(currentHoraire.getOpenDay()) && horaire.getCloseTime().equals(currentHoraire.getCloseTime()) && horaire.getOpenTime().equals(currentHoraire.getOpenTime())) return true;
				index++;
			}
			if (!found) {
				return false;
			}
			else{
				SQLiteDatabase db = sqliteHelper.getWritableDatabase();
				db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Schedule + " WHERE " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
				MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Schedule + " WHERE " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
				db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Schedule + " WHERE " + MySQLiteHelper.Schedule_column[2] + " = " + horaire.getOpenDay() + " AND " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
				MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Schedule + " WHERE " + MySQLiteHelper.Schedule_column[2] + " = " + horaire.getOpenDay() + " AND " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
				db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Schedule + " WHERE " + MySQLiteHelper.Schedule_column[3] + " = " + horaire.getOpenTime() + " AND " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
				MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Schedule + " WHERE " + MySQLiteHelper.Schedule_column[3] + " = " + horaire.getOpenTime() + " AND " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
				db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Schedule + " WHERE " + MySQLiteHelper.Schedule_column[4] + " = " + horaire.getCloseTime() + " AND " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
				MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Schedule + " WHERE " + MySQLiteHelper.Schedule_column[4] + " = " + horaire.getCloseTime() + " AND " + MySQLiteHelper.Schedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
				db.close();
				restaurant.removeRestaurantHoraire(index);
				return true;
			}
		}
		else
			return false;
	}
	
	public boolean addRestaurantTypePaiements(String typePaiement) {
		if (typePaiement != null) {
			for(String currentType : restaurant.getRestaurantTypePaiements(false)){
				if(typePaiement.equals(currentType)) return true;
			}
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Payment + " SET " + MySQLiteHelper.Payment_column[2] + " = " + typePaiement + " WHERE " + MySQLiteHelper.Payment_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Payment + " SET " + MySQLiteHelper.Payment_column[2] + " = " + typePaiement + " WHERE " + MySQLiteHelper.Payment_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			db.close();
			restaurant.addRestaurantTypePaiements(typePaiement);
		}
		else {
			return false;
		}
		return true;
	}
	
	public boolean removeRestaurantTypePaiements(String typePaiement){
		if (typePaiement != null) {
			boolean found = false;
			int index = 0;
			for(String currentType : restaurant.getRestaurantTypePaiements(false)){
				if(typePaiement.equals(currentType)) found = true;
				index++;
			}
			if (!found) {
				return false;
			}
			else{
				SQLiteDatabase db = sqliteHelper.getWritableDatabase();
				db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Payment + " WHERE " + MySQLiteHelper.Payment_column[2] + " = " + typePaiement + " AND " + MySQLiteHelper.Payment_column[1] + " = " + restaurant.getRestaurantName() + ";");
				MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Payment + " WHERE " + MySQLiteHelper.Payment_column[2] + " = " + typePaiement + " AND " + MySQLiteHelper.Payment_column[1] + " = " + restaurant.getRestaurantName() + ";");
				db.close();
				restaurant.removeRestaurantTypePaiements(index);
			}
			return true;
		}
		else
			return false;
	}
	
	public ArrayList<PreBooking> getRestaurantPreReservation(boolean getFromDatabase){
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			preBooking.clear();//We remove all the elements
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Order_column[2] + ", " + MySQLiteHelper.Order_column[4] + ", " + MySQLiteHelper.Order_column[5] + " FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[1] + "=" + restaurant.getRestaurantName() + " GROUP BY " + MySQLiteHelper.Order_column[2], null);
			if (cursor.moveToFirst()) {//If the information exists
				ArrayList <Meal> commande;
				while (!cursor.isAfterLast()) {//As long as there is one element to read
					Client currentClient = new Client(cursor.getString(0));
					commande = new ArrayList <Meal> ();
					while(!cursor.isAfterLast() && currentClient.getEmail() == cursor.getString(0)) {
						commande.add(new Meal (cursor.getString(1), cursor.getInt(2)));//We stock the quantity of the meal with the instance variable "stock"
						cursor.moveToNext();
					}
					preBooking.add(new PreBooking(currentClient, commande));
				}
			}
			
			db.close();
		}
		
		return preBooking;
	}
	
	public void removeRestaurantPreReservation(PreBooking preBooking){
		if (preBooking == null) {//If the parameter is null
			return ;
		}
		
		boolean found = false;
		int index = 0;
		
		String ClientEmail = preBooking.getClient().getEmail();//Email of the client where we are ordering
		
		for (int i=0; i<this.preBooking.size() && !found; i++) {
			
			String IemeClientEmail = this.preBooking.get(i).getClient().getEmail();//Email of the client where we have ordered
			
			if (IemeClientEmail.equals(ClientEmail)) {//We compare the order
				
				int counter = 0;//Count the number of same meals in their ordered
				
				for (int j=0; j<this.preBooking.get(i).getCommande().size(); j++) {
					
					Meal JemeMealOrdered = (this.preBooking.get(i)).getCommande().get(j);// Jth meal of one order already registered
					
					boolean MealFound = false;
					
					for (int k=0; k<preBooking.getCommande().size() && !MealFound; k++) {
						
						Meal KemeMealOrdered = preBooking.getCommande().get(k);// Kth meal of the order not registered
						
						if (JemeMealOrdered.getMealName().equals(KemeMealOrdered.getMealName()) && JemeMealOrdered.getMealStock(false) == KemeMealOrdered.getMealStock(false)) {//if it's the same meal in the same quantities
							counter++;
							MealFound = true;
						}
					}
				}
				
				if (counter == this.preBooking.get(i).getCommande().size()) {//All the order match -> the order is in the list
					found = true;
					index = i;
				}
			}
		}
		
		if (!found) {//The parameter is not in the list
			return;
		}
		
		this.preBooking.remove(index);
		
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		String email = preBooking.getClient().getEmail();
			
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[2] + " = " + email + " AND " + MySQLiteHelper.Order_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.Order_column[3] + " is NULL ;");
		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[2] + " = " + email + " AND " + MySQLiteHelper.Order_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.Order_column[3] + " is NULL ;");
		
		db.close();
	}
	
	public ArrayList<Booking> getRestaurantReservation(boolean getFromDatabase){
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			booking.clear();//We remove all the elements
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Booking_column[2] + ", " + MySQLiteHelper.Booking_column[3] + ", " + MySQLiteHelper.Booking_column[4] + " FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[1] + "=" + restaurant.getRestaurantName() + " GROUP BY " + MySQLiteHelper.Booking_column[2], null);
			if (cursor.moveToFirst()) {//If the information exists
				
				ArrayList <Meal> Commande = new ArrayList<Meal>();
				Client currentClient = new Client (cursor.getString(0));
				int nbrReservation = cursor.getInt(1);
				Time time = new Time (cursor.getString(2));
				
				Cursor cursor2 = db.rawQuery("SELECT " + MySQLiteHelper.Order_column[4] + ", " + MySQLiteHelper.Order_column[5] + " FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[1] + "=" + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.Order_column[2] + " = " + currentClient.getEmail() + " AND " + MySQLiteHelper.Order_column[3] + " = " + time.toString() , null);
				if (cursor2.moveToFirst()) {//If there is an order for this restaurant
					
					while (!cursor2.isAfterLast()) {//As long as there is one element to read
						
						while(!cursor2.isAfterLast()) {
							Commande.add(new Meal (cursor2.getString(0), cursor2.getInt(1)));//We stock the quantity of the meal with the instance variable "stock"
							cursor2.moveToNext();
						}
					}
				}
				
				booking.add(new Booking(currentClient, nbrReservation, time, Commande));
				
				cursor.moveToNext();
			}
			
			db.close();
		}
		
		return booking;
	}
	
	public void removeRestaurantReservation(Booking booking){
		if (booking == null) {//If the parameter is null
			return ;
		}
		
		boolean found = false;
		int index = 0;
		
		String ClientEmail = booking.getClient().getEmail();//Email of the client where we are reserving
		int nbrPlace = booking.getNombrePlaces();//time when we are reserving
		Time time = booking.getHeureReservation();//number of places we are reserving
		
		for (int i=0; i<this.booking.size() && !found; i++) {//We test if the booking isn't already registered
			
			String IemeClientEmail = this.booking.get(i).getClient().getEmail();//Email of the client where we have reserved
			Time IemeTime = this.booking.get(i).getHeureReservation();//time when we have reserved
			int IemeNbrPlace = this.booking.get(i).getNombrePlaces();//number of places we have reserved
			
			if (IemeClientEmail.equals(ClientEmail) && time.equals(IemeTime) && nbrPlace==IemeNbrPlace) {//We compare reservations
				
				int counter = 0;//Count the number of same meals in their ordered
				
				if (this.booking.get(i).getCommande() != null && booking.getCommande() != null) {//If both have an order
					for (int j=0; j<this.booking.get(i).getCommande().size(); j++) {
						
						Meal JemeMealOrdered = (this.booking.get(i)).getCommande().get(j);// Jth meal of one order already registered
						
						boolean MealFound = false;
						
						for (int k=0; k<booking.getCommande().size() && !MealFound; k++) {
							
							Meal KemeMealOrdered = booking.getCommande().get(k);// Kth meal of the order not registered
							
							if (JemeMealOrdered.getMealName().equals(KemeMealOrdered.getMealName()) && JemeMealOrdered.getMealStock(false) == KemeMealOrdered.getMealStock(false)) {//if it's the same meal in the same quantities
								counter++;
								MealFound = true;
							}
						}
					}
				
					if (counter == this.booking.get(i).getCommande().size()) {//All the order match -> the reservation is already in the list
						found=true;
						index = i;
					}
				}
				else if (this.booking.get(i).getCommande() == null && booking.getCommande() == null) {//both have no order (and other informations are the same) -> the reservation is already in the list
					found=true;
					index = i;
				}
			}
		}
		
		if (!found) {//The parameter is not in the list
			return;
		}
		
		this.booking.remove(index);
		
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		String clientEmail = booking.getClient().getEmail();
		
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Booking + " WHERE " + MySQLiteHelper.Booking_column[2] + " = " + clientEmail + " AND " + MySQLiteHelper.Booking_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.Booking_column[4] + " = " + nbrPlace + ";");
		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Booking + " WHERE " + MySQLiteHelper.Booking_column[2] + " = " + clientEmail + " AND " + MySQLiteHelper.Booking_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.Booking_column[4] + " = " + nbrPlace + ";");
		
		if(booking.getCommande()!=null) {//We had the order if it exists
			db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[2] + " = " + clientEmail + " AND " + MySQLiteHelper.Order_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.Order_column[3] + " = " + nbrPlace + ";");
			MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[2] + " = " + clientEmail + " AND " + MySQLiteHelper.Order_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.Order_column[3] + " = " + nbrPlace + ";");
		}
		
		db.close();
	}
	
	public boolean addRestaurantAvantage(String avantage) {
		if (avantage != null) {
			for(String currentAvantage : restaurant.getRestaurantAvantages(false)){
				if(avantage.equals(currentAvantage)) return true;
			}
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Advantage + " SET " + MySQLiteHelper.Advantage_column[2] + " = " + avantage + " WHERE " + MySQLiteHelper.Advantage_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Advantage + " SET " + MySQLiteHelper.Advantage_column[2] + " = " + avantage + " WHERE " + MySQLiteHelper.Advantage_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			db.close();
		}
		else {
			return false;
		}
		restaurant.addRestaurantAvantages(avantage);
		return true;
	}
	
	public boolean removeRestaurantAvantage(String avantage){
		if (avantage != null) {
			boolean found = false;
			int index = 0;
			for(String currentAvantage : restaurant.getRestaurantAvantages(false)){
				if(avantage.equals(currentAvantage)) found = true;
				index++;
			}
			if (!found) {
				return false;
			}
			else{
				SQLiteDatabase db = sqliteHelper.getWritableDatabase();
				db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Advantage + " WHERE " + MySQLiteHelper.Advantage_column[2] + " = " + avantage + " AND " + MySQLiteHelper.Advantage_column[1] + " = " + restaurant.getRestaurantName() + ";");
				MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Advantage + " WHERE " + MySQLiteHelper.Advantage_column[2] + " = " + avantage + " AND " + MySQLiteHelper.Advantage_column[1] + " = " + restaurant.getRestaurantName() + ";");
				db.close();
				restaurant.removeRestaurantAvantages(index);
			}
			return true;
		}
		else
			return false;
	}
	
	public boolean addRestaurantCuisine(String cuisine) {
		if (cuisine != null) {
			for(String currentCuisine : restaurant.getRestaurantCuisine(false)){
				if(cuisine.equals(currentCuisine)) return true;
			}
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Cook + " SET " + MySQLiteHelper.Cook_column[2] + " = " + cuisine + " WHERE " + MySQLiteHelper.Cook_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Cook + " SET " + MySQLiteHelper.Cook_column[2] + " = " + cuisine + " WHERE " + MySQLiteHelper.Cook_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			db.close();
		}
		else {
			return false;
		}
		restaurant.addRestaurantCuisine(cuisine);
		return true;
	}
	
	public boolean removeRestaurantCuisine(String cuisine){
		if (cuisine != null) {
			boolean found = false;
			int index = 0;
			for(String currentCuisine : restaurant.getRestaurantCuisine(false)){
				if(cuisine.equals(currentCuisine)) found = true;
				index++;
			}
			if (!found) {
				return false;
			}
			else{
				SQLiteDatabase db = sqliteHelper.getWritableDatabase();
				db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Cook + " WHERE " + MySQLiteHelper.Cook_column[2] + " = " + cuisine + " AND " + MySQLiteHelper.Cook_column[1] + " = " + restaurant.getRestaurantName() + ";");
				MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Cook + " WHERE " + MySQLiteHelper.Cook_column[2] + " = " + cuisine + " AND " + MySQLiteHelper.Cook_column[1] + " = " + restaurant.getRestaurantName() + ";");
				db.close();
				restaurant.removeRestaurantCuisine(index);
			}
			return true;
		}
		else
			return false;
	}
}
