package spoon.misterspoon;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RestaurantOwner {
	
	public MySQLiteHelper sqliteHelper;
	
	private String emailPerso;
	private Restaurant restaurant;
	private ArrayList<PreBooking> preBooking = new ArrayList<PreBooking>();
	private ArrayList<Booking> booking = new ArrayList<Booking>();
	
	public RestaurantOwner(MySQLiteHelper sqliteHelper, String emailPerso) {
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
		if(!email.equals(restaurant.getRestaurantEmail())){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if (email != null) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[2] + " = " + email + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
				db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[2] + " = " + email + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + email + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.setRestaurantEmail(email);//TODO
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantName(String name) {
		if(!name.equals(restaurant.getRestaurantName())){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if (name != null) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[1] + " = " + name + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
				db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[2] + " = " + name + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.setRestaurantName(name);//TODO
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantCapacity(int capacity) {
		if(capacity != restaurant.getRestaurantCapacity()){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if (capacity != 0) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[5] + " = " + capacity + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
				db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[5] + " = " + capacity + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.setRestaurantCapacity(capacity);//TODO
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantDescription(String description) {
		if(!description.equals(restaurant.getRestaurantDescription())){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if (description != null) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[6] + " = " + description + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
				db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[6] + " = " + description + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.setRestaurantDescription(description);//TODO
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantPhone(String phone) {
		if(!phone.equals(restaurant.getRestaurantPhone())){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if (phone != null) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[3] + " = " + phone + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
				db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Restaurant + " SET " + MySQLiteHelper.Restaurant_column[3] + " = " + phone + " WHERE " + MySQLiteHelper.Restaurant_column[2] + " = " + emailPerso + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.setRestaurantPhone(phone);//TODO
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantFax(String fax) {
		if(!fax.equals(restaurant.getRestaurantFax())){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if (fax != null) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Contact + " SET " + MySQLiteHelper.Contact_column[3] + " = " + fax + " WHERE " + MySQLiteHelper.Contact_column[4] + " = " + emailPerso + " ;");
				db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Contact + " SET " + MySQLiteHelper.Contact_column[3] + " = " + fax + " WHERE " + MySQLiteHelper.Contact_column[4] + " = " + emailPerso + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.setRestaurantFax(fax);//TODO
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantWebSite(String webSite) {
		if(!webSite.equals(restaurant.getRestaurantWebSite())){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if (webSite != null) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Contact + " SET " + MySQLiteHelper.Contact_column[5] + " = " + webSite + " WHERE " + MySQLiteHelper.Contact_column[4] + " = " + emailPerso + " ;");
				db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Contact + " SET " + MySQLiteHelper.Contact_column[5] + " = " + webSite + " WHERE " + MySQLiteHelper.Contact_column[4] + " = " + emailPerso + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.setRestaurantWebSite(webSite);//TODO
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantPosition(GPS position) {
		if(position.getLongitude()!=restaurant.getRestaurantPosition().getLongitude() && position.getLatitude()!=restaurant.getRestaurantPosition().getLatitude()){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if (position != null) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[1] + " = " + position + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getRestaurantPosition() + " ;");
				db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[1] + " = " + position + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getRestaurantPosition() + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.setRestaurantPosition(position);//TODO
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantNumero(int numero) {
		if(numero!=restaurant.getRestaurantNumero){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if (numero != 0) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[2] + " = " + numero + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getPosition() + " ;");
				db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[2] + " = " + numero + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getPosition() + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.setRestaurantNumero(numero);//TODO
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantRue(String rue) {
		if(!rue.equals(restaurant.getRestaurantRue())){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if (rue != null) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[3] + " = " + rue + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getPosition() + " ;");
				db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[3] + " = " + rue + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getPosition() + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.setRestaurantRue(rue);//TODO
			return true;
		}
		return true;
	}
	
	public boolean setRestaurantVille(String ville) {
		if(!ville.equals(restaurant.getRestaurantVille())){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if (ville != null) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[4] + " = " + ville + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getPosition() + " ;");
				db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Address + " SET " + MySQLiteHelper.Address_column[4] + " = " + ville + " WHERE " + MySQLiteHelper.Address_column[1] + " = " + restaurant.getPosition() + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.setRestaurantVille(ville);//TODO
			return true;
		}
		return true;
	}
	
	public boolean addRestaurantHoraire(OpenHour horaire) {
		if(!(horaire.getOpenDay()).equals((restaurant.getRestaurantHoraire()).getOpenDay())){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			if ((restaurant.getRestaurantHoraire()).getOpenDay != null && (restaurant.getRestaurantHoraire()).getOpenTime != null && (restaurant.getRestaurantHoraire()).getCloseTime != null) {
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Shedule + " SET " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + " WHERE " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
				db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Shedule + " SET " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + " WHERE " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Shedule + " SET " + MySQLiteHelper.Shedule_column[2] + " = " + horaire.getOpenDay() + " WHERE " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
				db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Shedule + " SET " + MySQLiteHelper.Shedule_column[2] + " = " + horaire.getOpenDay() + " WHERE " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Shedule + " SET " + MySQLiteHelper.Shedule_column[3] + " = " + horaire.getOpenTime() + " WHERE " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
				db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Shedule + " SET " + MySQLiteHelper.Shedule_column[3] + " = " + horaire.getOpenTime() + " WHERE " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
				MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Shedule + " SET " + MySQLiteHelper.Shedule_column[4] + " = " + horaire.getCloseTime() + " WHERE " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
				db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Shedule + " SET " + MySQLiteHelper.Shedule_column[4] + " = " + horaire.getCloseTime() + " WHERE " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			}
			else {
				return false;
			}
			db.close();
			restaurant.addRestaurantHoraire(horaire);//TODO
			return true;
		}
		return true;
	}
	
	public boolean removeRestaurantHoraire(OpenHour horaire){
		if(!(horaire.getOpenDay()).equals((restaurant.getRestaurantHoraire()).getOpenDay())){
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Shedule + " WHERE " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
			MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Shedule + " WHERE " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
			db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Shedule + " WHERE " + MySQLiteHelper.Shedule_column[2] + " = " + horaire.getOpenDay() + " AND " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
			MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Shedule + " WHERE " + MySQLiteHelper.Shedule_column[2] + " = " + horaire.getOpenDay() + " AND " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
			db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Shedule + " WHERE " + MySQLiteHelper.Shedule_column[3] + " = " + horaire.getOpenTime() + " AND " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
			MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Shedule + " WHERE " + MySQLiteHelper.Shedule_column[3] + " = " + horaire.getOpenTime() + " AND " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
			db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Shedule + " WHERE " + MySQLiteHelper.Shedule_column[4] + " = " + horaire.getCloseTime() + " AND " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
			MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Shedule + " WHERE " + MySQLiteHelper.Shedule_column[4] + " = " + horaire.getCloseTime() + " AND " + MySQLiteHelper.Shedule_column[1] + " = " + restaurant.getRestaurantName() + ";");
			db.close();
			restaurant.removeRestaurantHoraire(horaire);//TODO
			return true;
		}
		return false;
	}
	
	public boolean addRestaurantTypePaiements(String typePaiement) {
		if (typePaiement != null) {
			for(String currentType : restaurant.getRestaurantTypePaiements){
				if(typePaiement.equals(currentType)) return true;
			}
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			
			MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Payment + " SET " + MySQLiteHelper.Payment_column[2] + " = " + typePaiement + " WHERE " + MySQLiteHelper.Payment_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Payment + " SET " + MySQLiteHelper.Payment_column[2] + " = " + typePaiement + " WHERE " + MySQLiteHelper.Payment_column[1] + " = " + restaurant.getRestaurantName() + " ;");
			db.close();
		}
		else {
			return false;
		}
		restaurant.addRestaurantTypePaiements(typePaiement);//TODO
		return true;
	}
	
	public boolean removeRestaurantTypePaiements(String typePaiement){
		if (typePaiement != null) {
			boolean found = false;
			for(String currentType : restaurant.getRestaurantTypePaiements){
				if(typePaiement.equals(currentType)) found = true;
			}
			if (!found) {
				return false;
			}
			else{
				SQLiteDatabase db = sqliteHelper.getWritableDatabase();
				db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Payment + " WHERE " + MySQLiteHelper.Payment_column[2] + " = " + typePaiement + " AND " + MySQLiteHelper.Payment_column[1] + " = " + restaurant.getRestaurantName() + ";");
				MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Payment + " WHERE " + MySQLiteHelper.Payment_column[2] + " = " + typePaiement + " AND " + MySQLiteHelper.Payment_column[1] + " = " + restaurant.getRestaurantName() + ";");
				db.close();
			}
		}
		else
			return false;
	}
	
	public ArrayList<Booking> getRestaurantPreReservation(boolean getFromDatabase){
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			preBooking.clear();//We remove all the elements
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Order_column[2] + ", " + MySQLiteHelper.Order_column[4] + ", " + MySQLiteHelper.Order_column[5] + " FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[1] + "=" + restaurant.getRestaurantName() + " GROUP BY " + MySQLiteHelper.Order_column[2], null);
			if (cursor.moveToFirst()) {//If the information exists
				ArrayList <Meal> commande;
				while (!cursor.isAfterLast()) {//As long as there is one element to read
					String currentClient = cursor.getString(0);
					commande = new ArrayList <Meal> ();
					while(!cursor.isAfterLast() && currentClient == cursor.getString(0)) {
						commande.add(new Meal (cursor.getString(1), cursor.getInt(2)));//We stock the quantity of the meal with the instance variable "stock"
						cursor.moveToNext();
					}
					preBooking.add(new preBooking(currentClient, commande));
				}
			}
			
			db.close();
		}
		
		return preBooking;
	}

	public void addPreBooking(PreBooking preBooking) {
		//TODO WUT WUT
		//TODO
		//TODO
		//TODO
	}
	
	public void removeRestaurantPreReservation(PreBooking preBooking){
		if (preBooking == null) {//If the parameter is null
			return ;
		}
		
		boolean found = false;
		int index;
		
		String ClientEmail = preBooking.getClient().getEmail();//Email of the client where we are ordering
		
		for (int i=0; i<preBooking.size() && !found; i++) {
			
			String IemeClientEmail = preBooking.get(i).getClient().getEmail();//Email of the client where we have ordered
			
			if (IemeClientEmail.equals(ClientEmail)) {//We compare the order
				
				int counter = 0;//Count the number of same meals in their ordered
				
				for (int j=0; j<preBooking.get(i).getCommande().size(); j++) {
					
					Meal JemeMealoOrdered = (preBooking.get(i)).getCommande().get(j);// Jth meal of one order already registered
					
					boolean MealFound = false;
					
					for (int k=0; k<preBooking.getCommande().size() && !MealFound; k++) {
						
						Meal KemeMealOrdered = preBooking.getCommande().get(k);// Kth meal of the order not registered
						
						if (JemeMealOrdered.getMealName().equals(KemeMealOrdered.getMealName()) && JemeMealOrdered.getMealStock() == KemeMealOrdered.getMealStock()) {//if it's the same meal in the same quantities
							counter++;
							MealFound = true;
						}
					}
				}
				
				if (counter == preBooking.get(i).getCommande().size()) {//All the order match -> the order is in the list
					found = true;
					index = i;
				}
			}
		}
		
		if (!found) {//The parameter is not in the list
			return;
		}
		
		preBooking.remove(index);
		
		
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
				
				ArrayList <Meal> Commande;
				Restaurant currentClient = new Client (cursor.getString(0));
				int nbrReservation = cursor.getInt(1);
				Time time = new Time (cursor.getString(2));
				
				Cursor cursor2 = db.rawQuery("SELECT " + MySQLiteHelper.Order_column[4] + ", " + MySQLiteHelper.Order_column[5] + " FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[2] + "=" + email + " AND " + MySQLiteHelper.Order_column[1] + " = " + Cursor.getString(0) + " AND " + MySQLiteHelper.Order_column[3] + " = " + Cursor.getString(4) , null);
				if (cursor2.moveToFirst()) {//If there is an order for this restaurant
					
					while (!cursor2.isAfterLast()) {//As long as there is one element to read
						
						Commande = new ArrayList <Meal> ();
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

	public void addBooking(Booking booking) {
		//TODO WUT WUT ?
		//TODO
		//TODO
		//TODO
	}
	
	public void removeRestaurantReservation(Booking booking){
		if (booking == null) {//If the parameter is null
			return ;
		}
		
		boolean found = false;
		int index;
		
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
						
						Meal JemeMealoOrdered = (this.booking.get(i)).getCommande().get(j);// Jth meal of one order already registered
						
						boolean MealFound = false;
						
						for (int k=0; k<booking.getCommande().size() && !MealFound; k++) {
							
							Meal KemeMealOrdered = booking.getCommande().get(k);// Kth meal of the order not registered
							
							if (JemeMealOrdered.getMealName().equals(KemeMealOrdered.getMealName()) && JemeMealOrdered.getMealStock() == KemeMealOrdered.getMealStock()) {//if it's the same meal in the same quantities
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
		
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Booking + " WHERE " + MySQLiteHelper.Booking_column[2] + " = " + clientEmail + " AND " + MySQLiteHelper.Booking_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.booking_column[4] + " = " + nbrPlaces + ";");
		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Booking + " WHERE " + MySQLiteHelper.Booking_column[2] + " = " + clientEmail + " AND " + MySQLiteHelper.Booking_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.booking_column[4] + " = " + nbrPlaces + ";");
		
		if(booking.getCommande()!=null) {//We had the order if it exists
			db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[2] + " = " + clientEmail + " AND " + MySQLiteHelper.Order_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.Order_column[3] + " = " + nbrPlaces + ";");
			MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[2] + " = " + clientEmail + " AND " + MySQLiteHelper.Order_column[1] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.Order_column[3] + " = " + nbrPlaces + ";");
		}
		
		db.close();
	}
	
	public boolean addRestaurantAvantage(String avantage) {
		if (avantage != null) {
			for(String currentAvantage : restaurant.getRestaurantAvantages){
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
		restaurant.addRestaurantAvantages(avantage);//TODO
		return true;
	}
	
	public boolean removeRestaurantAvantage(String avantage){
		if (avantage != null) {
			boolean found = false;
			for(String currentAvantage : restaurant.getRestaurantAvantages){
				if(avantage.equals(currentAvantage)) found = true;
			}
			if (!found) {
				return false;
			}
			else{
				SQLiteDatabase db = sqliteHelper.getWritableDatabase();
				db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Advantage + " WHERE " + MySQLiteHelper.Advantage_column[2] + " = " + avantage + " AND " + MySQLiteHelper.Advantage_column[1] + " = " + restaurant.getRestaurantName() + ";");
				MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Advantage + " WHERE " + MySQLiteHelper.Advantage_column[2] + " = " + avantage + " AND " + MySQLiteHelper.Advantage_column[1] + " = " + restaurant.getRestaurantName() + ";");
				db.close();
			}
		}
		else
			return false;
	}
	
	public boolean addRestaurantCuisine(String cuisine) {
		if (cuisine != null) {
			for(String currentCuisine : restaurant.getRestaurantCuisine){
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
		restaurant.addRestaurantCuisine(cuisine);//TODO
		return true;
	}
	
	public boolean removeRestaurantCuisine(String cuisine){
		if (cuisine != null) {
			boolean found = false;
			for(String currentCuisine : restaurant.getRestaurantCuisine){
				if(cuisine.equals(currentCuisine)) found = true;
			}
			if (!found) {
				return false;
			}
			else{
				SQLiteDatabase db = sqliteHelper.getWritableDatabase();
				db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Cook + " WHERE " + MySQLiteHelper.Cook_column[2] + " = " + cuisine + " AND " + MySQLiteHelper.Cook_column[1] + " = " + restaurant.getRestaurantName() + ";");
				MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Cook + " WHERE " + MySQLiteHelper.Cook_column[2] + " = " + cuisine + " AND " + MySQLiteHelper.Cook_column[1] + " = " + restaurant.getRestaurantName() + ";");
				db.close();
			}
		}
		else
			return false;
	}
}
