package spoon.misterspoon;

import static android.provider.BaseColumns._ID;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "restaurant_database.db";
	public static final int DATABASE_VERSION = 1;
	
	//Additional Orders (add by the program)
	public static ArrayList <String> Additional_Orders = new ArrayList <String> ();
	
	//Different tables
	public static final String TABLE_Address = "Adresse";
	public static final String[] Address_column = new String[]{_ID, "gps", "numero", "rue", "ville"};
	
	public static final String TABLE_Contact= "Contact";
	public static final String[] Contact_column = new String[]{_ID, "telephone", "fax", "email", "siteWeb"};
	
	public static final String TABLE_Advantage= "Avantage";
	public static final String[] Advantage_column = new String[]{_ID, "restNom", "avantagesSpec"};
	
	public static final String TABLE_Client= "Client";
	public static final String[] Client_column = new String[]{_ID, "email", "nom", "gsm"};
	
	public static final String TABLE_Cook= "Cuisine";
	public static final String[] Cook_column = new String[]{_ID, "restNom", "typeCuisine"};
	
	public static final String TABLE_Schedule= "Horaire";
	public static final String[] Schedule_column = new String[]{_ID, "restNom", "jourOuverture", "openHour", "closeHour"};
	
	public static final String TABLE_Closing ="Fermeture";
	public static final String[] Closing_column = new String[]{_ID, "restNom", "date"};
	
	public static final String TABLE_Menu= "Menu";
	public static final String[] Menu_column = new String[]{_ID, "menuNom", "categorie", "restNom", "platNom"};
	
	public static final String TABLE_MenuPrice= "MenuPrix";
	public static final String[] MenuPrice_column = new String[]{_ID, "restNom", "menuNom", "categorie", "prix"};
	
	public static final String TABLE_Payment= "Paiement";
	public static final String[] Payment_column = new String[]{_ID, "restNom", "typePaiement"};
	
	public static final String TABLE_Meal= "Plat";
	public static final String[] Meal_column = new String[]{_ID, "platNom", "restNom", "prix", "stock", "platDescription"};
	
	public static final String TABLE_FavouriteMeal= "PlatFavori";
	public static final String[] FavouriteMeal_column = new String[]{_ID, "email", "platNom"};
	
	public static final String TABLE_Booking= "Reservation";
	public static final String[] Booking_column = new String[]{_ID, "restNom", "email", "NbrReservation", "dateTime"};
	
	public static final String TABLE_Order= "Commande";
	public static final String[] Order_column = new String[]{_ID, "restNom", "email", "dateTime", "platNom", "quantite"};
	
	public static final String TABLE_Restaurant= "Restaurant";
	public static final String[] Restaurant_column = new String[]{_ID, "restNom", "emailPerso", "telephone", "gps", "capaTotale", "description", "note", "nbrVotants"};
	
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
		db.execSQL("PRAGMA foreign_keys = OFF;");
		createDatabase(db);
		populateDatabase(db);
		db.execSQL("PRAGMA foreign_keys = ON;");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("PRAGMA foreign_keys = OFF;");
		deleteDatabase(db);
		onCreate(db);
		populateDatabase(db);
		db.execSQL("PRAGMA foreign_keys = ON;");
		if (!Additional_Orders.isEmpty()) {//If the program enter new Orders
			for(int i=0; i<Additional_Orders.size(); i++) {//We execute them
				db.execSQL(Additional_Orders.get(i));
			}
		}
	}
	
	public void createDatabase(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_Address +  "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Address_column[1] + " VARCHAR NOT NULL UNIQUE ," + Address_column[2] + " VARCHAR, " + Address_column[3] + " VARCHAR NOT NULL, " + Address_column[4] + " VARCHAR NOT NULL);");
		db.execSQL("CREATE TABLE " + TABLE_Contact + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Contact_column[1] + " VARCHAR NOT NULL UNIQUE, " + Contact_column[2] + " VARCHAR, " + Contact_column[3] + " VARCHAR, " + Contact_column[4] + " VARCHAR);");
		db.execSQL("CREATE TABLE " + TABLE_Restaurant + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Restaurant_column[1] + " VARCHAR NOT NULL UNIQUE, " + Restaurant_column[2] + " VARCHAR NOT NULL UNIQUE, " + Restaurant_column[3] + " VARCHAR NOT NULL UNIQUE, " + Restaurant_column[4] + " VARCHAR NOT NULL UNIQUE, " + Restaurant_column[5] + " INTEGER NOT NULL, " + Restaurant_column[6] + " TEXT, " + Restaurant_column[7] + " INTEGER DEFAULT 0, " + Restaurant_column[8] + " INTEGER DEFAULT 0,  FOREIGN KEY ("+Restaurant_column[3]+") REFERENCES " + TABLE_Contact + "("+Contact_column[1]+"), FOREIGN KEY ("+Restaurant_column[4]+") REFERENCES " + TABLE_Address +"("+Address_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_Client + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Client_column[1] + " VARCHAR NOT NULL  UNIQUE, " + Client_column[2] + "  VARCHAR NOT NULL, " + Client_column[4] + "  VARCHAR);");
		db.execSQL("CREATE TABLE " + TABLE_Advantage + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Advantage_column[1] + " VARCHAR NOT NULL," + Advantage_column[2] + " VARCHAR NOT NULL, FOREIGN KEY (" + Advantage_column[1] + ") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_Cook + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Cook_column[1] +" VARCHAR NOT NULL," + Cook_column[2] +" VARCHAR NOT NULL, FOREIGN KEY ("+Cook_column[1]+") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_Schedule + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Schedule_column[1] +" VARCHAR NOT NULL, " + Schedule_column[2] + " VARCHAR NOT NULL, " + Schedule_column[3] +" VARCHAR NOT NULL, " + Schedule_column[4] +" VARCHAR NOT NULL, FOREIGN KEY (" + Schedule_column[1] +") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_Closing + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Closing_column[1] +" VARCHAR NOT NULL, " + Closing_column[2] + " VARCHAR NOT NULL, " + "FOREIGN KEY ("+Closing_column[1]+") REFERENCES "+TABLE_Restaurant+"("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_Menu + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Menu_column[1] + " VARCHAR NOT NULL, " + Menu_column[2] + " VARCHAR NOT NULL, " + Menu_column[3] + " VARCHAR NOT NULL, " + Menu_column[4] + " VARCHAR NOT NULL, FOREIGN KEY ("+Menu_column[3]+") REFERENCES "+TABLE_Restaurant+"("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_MenuPrice + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + MenuPrice_column[1]  + " VARCHAR NOT NULL, " + MenuPrice_column[2] + " VARCHAR NOT NULL, " + MenuPrice_column[3] + " VARCHAR NOT NULL, " + MenuPrice_column[4] + "  DECIMAL(9,2) NOT NULL, UNIQUE ("+ MenuPrice_column[1]+", " + MenuPrice_column[2] +", " + MenuPrice_column[3] +") ON CONFLICT REPLACE, FOREIGN KEY ("+MenuPrice_column[1]+") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_Payment + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Payment_column[1] + " VARCHAR NOT NULL, " + Payment_column[2] + " VARCHAR NOT NULL,  FOREIGN KEY ("+Payment_column[1]+") REFERENCES Restaurant ("+Payment_column[2]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_Meal + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Meal_column[1] + " VARCHAR NOT NULL, " + Meal_column[2] + " VARCHAR NOT NULL, " + Meal_column[3] + " DECIMAL(9,2) NOT NULL, s" + Meal_column[4] + " INTEGER, " + Meal_column[5] + " TEXT, UNIQUE ("+Meal_column[1]+", " + Meal_column[2]+") ON CONFLICT REPLACE, FOREIGN KEY ("+Meal_column[2]+") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_FavouriteMeal + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + FavouriteMeal_column[1] + " VARCHAR NOT NULL , " + FavouriteMeal_column[2] + " VARCHAR NOT NULL , FOREIGN KEY ("+FavouriteMeal_column[1]+") REFERENCES " + TABLE_Client + " ("+Client_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_Booking + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Booking_column[1] + " VARCHAR NOT NULL, " + Booking_column[2] + " VARCHAR NOT NULL, " + Booking_column[3] + " INTEGER NOT NULL, " + Booking_column[4] + " VARCHAR NOT NULL" + Booking_column[5] + " VARCHAR NOT NULL, UNIQUE("+Booking_column[1]+", "+ Booking_column[4] + ", "+ Booking_column[2]+ ", "+ Booking_column[5] +") ON CONFLICT REPLACE, FOREIGN KEY ("+Booking_column[1]+") REFERENCES "+ TABLE_Restaurant +"("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY ("+Booking_column[2]+") REFERENCES "+TABLE_Client+ "("+Client_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_Order + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Order_column[1] +" VARCHAR NOT NULL, " + Order_column[2] +" VARCHAR NOT NULL, " + Order_column[3] + " VARCHAR, " + Order_column[4] + " VARCHAR, " + Order_column[5] + " VARCHAR NOT NULL, " + Order_column[6] + " INTEGER NOT NULL, FOREIGN KEY ("+Order_column[1]+") REFERENCES "+ TABLE_Restaurant + "(" + Restaurant_column[1] +"), FOREIGN KEY ("+Order_column[2]+") REFERENCES "+ TABLE_Client +"("+Client_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_FavouriteRestaurant + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + FavouriteRestaurant_column[1] + " VARCHAR NOT NULL , " + FavouriteRestaurant_column[2] + " VARCHAR NOT NULL, FOREIGN KEY ("+FavouriteRestaurant_column[1]+") REFERENCES " + TABLE_Client + "("+Client_column[1]+"), FOREIGN KEY ("+FavouriteRestaurant_column[2]+") REFERENCES " + TABLE_Restaurant + "("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_Specificity + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Specificity_column[1] + " VARCHAR NOT NULL , " + Specificity_column[2] + " VARCHAR NOT NULL, FOREIGN KEY ("+Specificity_column[1]+") REFERENCES "+TABLE_Client+"("+Client_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_Allergy + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Allergy_column[1] + " VARCHAR NOT NULL , " + Allergy_column[2] + " VARCHAR NOT NULL, FOREIGN KEY ("+Allergy_column[1]+") REFERENCES "+TABLE_Client+"("+Client_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_MealSpecificity + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + MealSpecificity_column[1] + " VARCHAR NOT NULL, " + MealSpecificity_column[2] + " VARCHAR NOT NULL, " + MealSpecificity_column[3] + " VARCHAR NOT NULL, FOREIGN KEY ("+MealSpecificity_column[2]+") REFERENCES "+TABLE_Restaurant+"("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		
	}
	public void populateDatabase(SQLiteDatabase db) {//To correct (menuPrice) -> I don't find a good example
		
		db.execSQL("INSERT INTO " + TABLE_Address + "(" + Address_column[1] + ", " + Address_column[2] + ", " + Address_column[3] + ", " + Address_column[4] +") VALUES('50.668572,4.616146','1A','Place des Brabançons','Louvain-la-Neuve');");
		db.execSQL("INSERT INTO " + TABLE_Address + "(" + Address_column[1] + ", " + Address_column[2] + ", " + Address_column[3] + ", " + Address_column[4] +") VALUES('50.6609,4.617962',NULL,'Place Polyvalente','Louvain-la-Neuve');");
		db.execSQL("INSERT INTO " + TABLE_Address + "(" + Address_column[1] + ", " + Address_column[2] + ", " + Address_column[3] + ", " + Address_column[4] +") VALUES('50.671578,4.61282','26','Rue du Labrador','Louvain-la-Neuve');");
		
		db.execSQL("INSERT INTO " + TABLE_Contact + "(" + Contact_column[1] + ", " + Contact_column[2] + ", " + Contact_column[3] + ", " + Contact_column[4] +")  VALUES('010/45.15.85','010/45.25.46',NULL,'http://www.lacreperiebretonne.be/'); ");
		db.execSQL("INSERT INTO " + TABLE_Contact + "(" + Contact_column[1] + ", " + Contact_column[2] + ", " + Contact_column[3] + ", " + Contact_column[4] +")  VALUES('010/45.64.62','010/81.23.62','info@loungeatude.be','www.loungeatude.be'); ");
		db.execSQL("INSERT INTO " + TABLE_Contact + "(" + Contact_column[1] + ", " + Contact_column[2] + ", " + Contact_column[3] + ", " + Contact_column[4] +")  VALUES('010/48.84.26',NULL,'petitvingtieme@museeherge.com','http://www.lepetitvingtieme.be/'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Restaurant + "(" + Restaurant_column[1] + ", " + Restaurant_column[2] + ", " + Restaurant_column[3] + ", " + Restaurant_column[4] + ", " + Restaurant_column[5]+ ", " + Restaurant_column[6] + ", " + Restaurant_column[7] + ", " + Restaurant_column[8] +")  VALUES('Loungeatude', 'mathieu.jadin@student.uclouvain.be','010/45.64.62','50.6609,4.617962',55,'Un simple coup d’œil suffit à se rendre compte que vous n’êtes ni dans un restaurant. Ni dans un bar. Encore moins dans une galerie d’art. Et pourtant, vous prendrez l’apéritif au salon, vous dinerez dans la salle à manger et vous terminerez peut-être votre pousse-café au BAB’L devant une huile ou une aquarelle." +"\n" + " Vous êtes au Loungeatude." +"\n" + " Vous êtes chez vous.', 3, 2); ");
		db.execSQL("INSERT INTO " + TABLE_Restaurant + "(" + Restaurant_column[1] + ", " + Restaurant_column[2] + ", " + Restaurant_column[3] + ", " + Restaurant_column[4] + ", " + Restaurant_column[5]+ ", " + Restaurant_column[6] + ", " + Restaurant_column[7] + ", " + Restaurant_column[8] +")   VALUES('Crêperie Bretonne','ludovic.fastre@student.uclouvain.be','010/45.15.85','50.668572,4.616146',45,'Dans notre salle au décor rustique ou en terrasse aux beaux jours, dégustez l''une de nos 350 crêpes salées d''inspiration française ou exotique, ou appréciez l''une de nos salades variées. Pour accompagner votre plat, nous avons à votre disposition une carte de plus de 200 bières artisanales belges dont toutes les trapistes et 5 bières au fût. Nous pouvons aussi vous servir un cidre bien frais ou vous laisser choisir un vin de pays." +"\n" + "En dessert, vous choisirez une crêpe sucrée ou flambée, une coupe de glace maison ou encore un milkshake que vous accompagnerez d''un café lointain, un thé vert parfumé ou une infusion. Nous vous accueillons et vous servons tout au long du jour de 9h du matin jusqu''à 1h de la nuit, la cuisine restant ouverte non-stop.', 3, 1); ");
		db.execSQL("INSERT INTO " + TABLE_Restaurant + "(" + Restaurant_column[1] + ", " + Restaurant_column[2] + ", " + Restaurant_column[3] + ", " + Restaurant_column[4] + ", " + Restaurant_column[5]+ ", " + Restaurant_column[6] + ", " + Restaurant_column[7] + ", " + Restaurant_column[8] +")  VALUES('Le Petit Vingtième','antoine.walsdorf@student.uclouvain.be','010/48.84.26','50.671578,4.61282',45,'Au sein du Musée Hergé, le restaurant" +"\n" + "le Petit Vingtième vous invite, dans un cadre raffiné" +"\n" + "et moderne, pour un moment de plaisir et de détente." +"\n" + "Proposant une gastronomie fine variant au fil" +"\n" + "des saisons, vous pourrez y déguster au gré de" +"\n" + "vos envies des préparations mêlant fraîcheur" +"\n" + "des produits et pureté du goût.', 4, 1); ");
		
		db.execSQL("INSERT INTO " + TABLE_Client + "(" + Client_column[1] + ", " + Client_column[2] + ", " + Client_column[3] +") VALUES('toni@hotmail.com','Durant','0472/56.97.48');  ");
		db.execSQL("INSERT INTO " + TABLE_Client + "(" + Client_column[1] + ", " + Client_column[2] + ", " + Client_column[3] +") VALUES('j.p@yahoo.fr','Dupont','0473/56.34.76');  ");
		
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Loungeatude','Végétarien'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Loungeatude','Wi-Fi'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Loungeatude','Accès handicapés'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +") VALUES('Loungeatude','Service traiteur');  ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Crêperie Bretonne','Végétarien'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Crêperie Bretonne','Terrasse'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +") VALUES('Crêperie Bretonne','Service traiteur');  ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +") VALUES('Le Petit Vingtième','Service traiteur');  ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +") VALUES('Le Petit Vingtième','Wi-Fi');  ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Le Petit Vingtième','Accès handicapés'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Le Petit Vingtième','Climatisation'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Le Petit Vingtième','Plats enfants'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Cook + "(" + Cook_column[1] + ", " + Cook_column[2] +")  VALUES('Le Petit Vingtième','Française'); ");
		db.execSQL("INSERT INTO " + TABLE_Cook + "(" + Cook_column[1] + ", " + Cook_column[2] +")  VALUES('Loungeatude','Française'); ");
		db.execSQL("INSERT INTO " + TABLE_Cook + "(" + Cook_column[1] + ", " + Cook_column[2] +")  VALUES('Crêperie Bretonne','Crêpes'); ");
		db.execSQL("INSERT INTO " + TABLE_Cook + "(" + Cook_column[1] + ", " + Cook_column[2] +")  VALUES('Loungeatude','Fusion'); ");
		db.execSQL("INSERT INTO " + TABLE_Cook + "(" + Cook_column[1] + ", " + Cook_column[2] +")  VALUES('Loungeatude','Méditerranéenne'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Le Petit Vingtième','Mardi','10:30:00','17:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Le Petit Vingtième','Mercredi','10:30:00','17:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Le Petit Vingtième','Jeudi','10:30:00','17:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Le Petit Vingtième','Vendredi','10:30:00','17:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Le Petit Vingtième','Samedi','10:00:00','18:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Le Petit Vingtième','Dimanche','10:00:00','18:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Creperie Bretonne','Lundi','00:00:00','01:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Creperie Bretonne','Lundi','09:00:00','24:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")   VALUES('Creperie Bretonne','Mardi','00:00:00','01:00:00');");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Creperie Bretonne','Mardi','09:00:00','24:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Creperie Bretonne','Mercredi','00:00:00','01:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Creperie Bretonne','Mercredi','09:00:00','24:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Creperie Bretonne','Jeudi','00:00:00','01:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Creperie Bretonne','Jeudi','09:00:00','24:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Creperie Bretonne','Vendredi','00:00:00','01:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Creperie Bretonne','Vendredi','09:00:00','24:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Creperie Bretonne','Samedi','00:00:00','01:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Creperie Bretonne','Samedi','09:00:00','24:00:00');  ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Creperie Bretonne','Dimanche','00:00:00','01:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Creperie Bretonne','Dimanche','09:00:00','24:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Loungeatude','Lundi','11:00:00','14:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Loungeatude','Lundi','19:00:00','22:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Loungeatude','Mardi','11:00:00','14:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Loungeatude','Mardi','19:00:00','22:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Loungeatude','Mercredi','11:00:00','14:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Loungeatude','Mercredi','19:00:00','22:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Loungeatude','Jeudi','11:00:00','14:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Loungeatude','Jeudi','19:00:00','22:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Loungeatude','Vendredi','11:00:00','14:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Loungeatude','Vendredi','19:00:00','22:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Loungeatude','Samedi','18:00:00','22:00:00'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Closing + "(" + Closing_column[1] + ", " + Closing_column[2] +")  VALUES('Crêperie Bretonne','2013-05-01'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Nos Viandes','plat','Le Petit Vingtième','Entrecote de Boeuf Belge Grillee +- 300gr'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Nos Entrées','entree','Le Petit Vingtième','Carpaccio de Bresaola et Jambon de Parme'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Nos Poissons','plat','Le Petit Vingtième','Gambas rôties au four'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Potage-Pâte-Salade-Préparation','entree','Loungeatude','Potage de scampis et canard laqué à la thaïe'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Volailles','plat','Loungeatude','Jambonnette de volaille aux scampis'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Viandes','plat','Loungeatude','Filet pur de boeuf irlandais en Tagliata, parmigiano et rucola'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Salades','entree','Crêperie Bretonne','Salade au roquefort'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Salades','entree','Crêperie Bretonne','Salade Tomates Mozzarella'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Crêpe des Gourmets','plat','Crêperie Bretonne','Crêpe La tartiflette'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Crêpe des Gourmets','plat','Crêperie Bretonne','Crêpe au saumon fumé, sauce crème et citron à l''aneth'); ");
		//To correct -> find menu with price but with some meals recorded
		db.execSQL("INSERT INTO " + TABLE_MenuPrice + "(" + MenuPrice_column[1] + ", " + MenuPrice_column[2] + ", " + MenuPrice_column[3] +") VALUES('Crêperie Bretonne','Dejeuner','plat',7);  ");
		
		db.execSQL("INSERT INTO " + TABLE_Payment + "(" + Payment_column[1] + ", " + Payment_column[2] +")  VALUES('Crêperie Bretonne','Cash'); ");
		db.execSQL("INSERT INTO " + TABLE_Payment + "(" + Payment_column[1] + ", " + Payment_column[2] +")  VALUES('Le Petit Vingtième','MasterCard'); ");
		db.execSQL("INSERT INTO " + TABLE_Payment + "(" + Payment_column[1] + ", " + Payment_column[2] +")  VALUES('Le Petit Vingtième','Cash'); ");
		db.execSQL("INSERT INTO " + TABLE_Payment + "(" + Payment_column[1] + ", " + Payment_column[2] +")  VALUES('Loungeatude','Cash'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Salade au roquefort','Crêperie Bretonne',13.95,3, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Salade Tomates Mozzarella','Crêperie Bretonne',11.2,20, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Crêpe La tartiflette','Crêperie Bretonne',12.15,16, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Crêpe au saumon fumé, sauce crème et citron à l''aneth','Crêperie Bretonne',13.95,15, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +") VALUES('Potage de scampis et canard laqué à la thaïe','Loungeatude',17.95,10, NULL);  ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Jambonnette de volaille aux scampis','Loungeatude',19.95,5, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +") VALUES('Filet pur de boeuf irlandais en Tagliata','Loungeatude',25.95,10, NULL);  ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Carpaccio de Bresaola et Jambon de Parme','Le Petit Vingtième',12,10, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Entrecôte de Boeuf Belge Grillée +- 300gr','Le Petit Vingtième',19,5, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Gambas rôties au four','Le Petit Vingtième',17,20, NULL); ");
		
		db.execSQL("INSERT INTO " + TABLE_FavouriteMeal + "(" + FavouriteMeal_column[1] + ", " + FavouriteMeal_column[2] + ")  VALUES('toni@hotmail.com','Brochette de poulet'); ");
		db.execSQL("INSERT INTO " + TABLE_FavouriteMeal + "(" + FavouriteMeal_column[1] + ", " + FavouriteMeal_column[2] + ") VALUES('toni@hotmail.com','Steak frites');  ");
		db.execSQL("INSERT INTO " + TABLE_FavouriteMeal + "(" + FavouriteMeal_column[1] + ", " + FavouriteMeal_column[2] + ")  VALUES('j.p@yahoo.fr','Steak fites'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Booking + "(" + Booking_column[1] + ", " + Booking_column[2] + ", " + Booking_column[3] + ", " + Booking_column[4] +") VALUES('Le Petit Vingtième','toni@hotmail.com',3,'2013-04-30 19:00:00');  ");
		db.execSQL("INSERT INTO " + TABLE_Booking + "(" + Booking_column[1] + ", " + Booking_column[2] + ", " + Booking_column[3] + ", " + Booking_column[4] +")  VALUES('Crêperie Bretonne','j.p@yahoo.fr',45,'2013-05-02 20:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Booking + "(" + Booking_column[1] + ", " + Booking_column[2] + ", " + Booking_column[3] + ", " + Booking_column[4] +")  VALUES('Crêperie Bretonne','toni@hotmail.com',6,'2013-04-30 22:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Booking + "(" + Booking_column[1] + ", " + Booking_column[2] + ", " + Booking_column[3] + ", " + Booking_column[4] +")  VALUES('Loungeatude','j.p@yahoo.fr',10,'2013-04-30 20:00:00'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Order + "(" + Order_column[1] + ", " + Order_column[2] + ", " + Order_column[3] + ", " + Order_column[4] + ", " + Order_column[5] +")  VALUES('Le Petit Vingtième','toni@hotmail.com','2013-04-30 19:00:00','Carpaccio de Bresaola et Jambon de Parme',3); ");
		db.execSQL("INSERT INTO " + TABLE_Order + "(" + Order_column[1] + ", " + Order_column[2] + ", " + Order_column[3] + ", " + Order_column[4] + ", " + Order_column[5] +")  VALUES('Crêperie Bretonne','j.p@yahoo.fr','2013-05-02 20:00:00','Crêpe La tartiflette',40); ");
		db.execSQL("INSERT INTO " + TABLE_Order + "(" + Order_column[1] + ", " + Order_column[2] + ", " + Order_column[3] + ", " + Order_column[4] + ", " + Order_column[5] +")  VALUES('Crêperie Bretonne','j.p@yahoo.fr','2013-04-30 20:00:00','Crêpe au saumon fumé, sauce crème et citron à l''aneth',5); ");
		db.execSQL("INSERT INTO " + TABLE_Order + "(" + Order_column[1] + ", " + Order_column[2] + ", " + Order_column[3] + ", " + Order_column[4] + ", " + Order_column[5] +")  VALUES('Loungeatude','j.p@yahoo.fr',NULL,'Jambonnette de volaille aux scampis',10); ");
				
		db.execSQL("INSERT INTO " + TABLE_FavouriteRestaurant + "(" + FavouriteRestaurant_column[1] + ", " + FavouriteRestaurant_column[2] + ")  VALUES('toni@hotmail.com','Crêperie Bretonne'); ");
		db.execSQL("INSERT INTO " + TABLE_FavouriteRestaurant + "(" + FavouriteRestaurant_column[1] + ", " + FavouriteRestaurant_column[2] + ")  VALUES('j.p@yahoo.fr','Crêperie Bretonne'); ");
		db.execSQL("INSERT INTO " + TABLE_FavouriteRestaurant + "(" + FavouriteRestaurant_column[1] + ", " + FavouriteRestaurant_column[2] + ")   VALUES('j.p@yahoo.fr','Loungeatude'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Specificity + "(" + Specificity_column[1] + ", " + Specificity_column[2] + ") VALUES('j.p@yahoo.fr','Végétarien');  ");
		db.execSQL("INSERT INTO " + TABLE_Specificity + "(" + Specificity_column[1] + ", " + Specificity_column[2] + ")  VALUES('toni@hotmail.com','Végétarien'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Allergy + "(" + Allergy_column[1] + ", " + Allergy_column[2] + ")  VALUES('toni@hotmail.com','gluten'); ");
		
		db.execSQL("INSERT INTO " + TABLE_MealSpecificity + "(" + MealSpecificity_column[1] + ", " + MealSpecificity_column[2] + ", " + MealSpecificity_column[3] + ") VALUES('Salade Tomates Mozzarella','Crêperie Bretonne','Végétarien');  ");
		
		
	}
	
	public void deleteDatabase(SQLiteDatabase db) {
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Advantage);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Cook);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Schedule);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Menu);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MenuPrice);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Payment);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Meal);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FavouriteMeal);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Booking);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Order);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FavouriteRestaurant);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Specificity);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Allergy);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MealSpecificity);
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Restaurant);//Must be destroy at the end because of the foreign keys
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Client);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Address);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Contact);
	}

}

