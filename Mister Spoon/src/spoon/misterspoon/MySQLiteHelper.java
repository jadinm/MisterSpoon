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
	public static final String[] Client_column = new String[]{_ID, "email", "nom", "gsm" , "mdp"};
	
	public static final String TABLE_Cook= "Cuisine";
	public static final String[] Cook_column = new String[]{_ID, "restNom", "typeCuisine"};
	
	public static final String TABLE_Schedule= "Horaire";
	public static final String[] Schedule_column = new String[]{_ID, "restNom", "jourOuverture", "openHour", "closeHour"};
	
	public static final String TABLE_Closing ="Fermeture";
	public static final String[] Closing_column = new String[]{_ID, "restNom", "date"};
	
	public static final String TABLE_Image="Image";
	public static final String[] Image_column = new String[]{_ID, "restNom", "imageName"};
	
	public static final String TABLE_ImageMeal="ImageMeal";
	public static final String[] ImageMeal_column = new String[]{_ID, "platNom" , "restNom", "imageName"};
	
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
	public static final String[] Restaurant_column = new String[]{_ID, "restNom", "emailPerso", "telephone", "gps", "capaTotale", "description", "note", "nbrVotants" , "mdp"};
	
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
		db.execSQL("CREATE TABLE " + TABLE_Restaurant + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Restaurant_column[1] + " VARCHAR NOT NULL UNIQUE, " + Restaurant_column[2] + " VARCHAR NOT NULL UNIQUE, " + Restaurant_column[3] + " VARCHAR NOT NULL UNIQUE, " + Restaurant_column[4] + " VARCHAR NOT NULL UNIQUE, " + Restaurant_column[5] + " INTEGER NOT NULL, " + Restaurant_column[6] + " TEXT, " + Restaurant_column[7] + " FLOAT DEFAULT 0, " + Restaurant_column[8] + " INTEGER DEFAULT 0, " + Restaurant_column[9] + " VARCHAR NOT NULL, FOREIGN KEY ("+Restaurant_column[3]+") REFERENCES " + TABLE_Contact + "("+Contact_column[1]+"), FOREIGN KEY ("+Restaurant_column[4]+") REFERENCES " + TABLE_Address +"("+Address_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_Client + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Client_column[1] + " VARCHAR NOT NULL  UNIQUE, " + Client_column[2] + "  VARCHAR NOT NULL, " + Client_column[3] + "  VARCHAR, " + Client_column[4] + " VARCHAR NOT NULL);");
		db.execSQL("CREATE TABLE " + TABLE_Advantage + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Advantage_column[1] + " VARCHAR NOT NULL," + Advantage_column[2] + " VARCHAR NOT NULL, FOREIGN KEY (" + Advantage_column[1] + ") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_Cook + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Cook_column[1] +" VARCHAR NOT NULL," + Cook_column[2] +" VARCHAR NOT NULL, FOREIGN KEY ("+Cook_column[1]+") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_Schedule + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Schedule_column[1] +" VARCHAR NOT NULL, " + Schedule_column[2] + " VARCHAR NOT NULL, " + Schedule_column[3] +" VARCHAR NOT NULL, " + Schedule_column[4] +" VARCHAR NOT NULL, FOREIGN KEY (" + Schedule_column[1] +") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_Closing + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Closing_column[1] +" VARCHAR NOT NULL, " + Closing_column[2] + " VARCHAR NOT NULL, " + "FOREIGN KEY ("+Closing_column[1]+") REFERENCES "+TABLE_Restaurant+"("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_Image + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Image_column[1] +" VARCHAR NOT NULL, " + Image_column[2] + " VARCHAR NOT NULL, " + "FOREIGN KEY ("+Image_column[1]+") REFERENCES "+TABLE_Restaurant+"("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_ImageMeal + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + ImageMeal_column[1] +" VARCHAR NOT NULL, " + ImageMeal_column[2] + " VARCHAR NOT NULL , " + ImageMeal_column[3] +" VARCHAR NOT NULL, " + "FOREIGN KEY ("+ImageMeal_column[2]+") REFERENCES "+TABLE_Restaurant+"("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_Menu + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Menu_column[1] + " VARCHAR NOT NULL, " + Menu_column[2] + " VARCHAR NOT NULL, " + Menu_column[3] + " VARCHAR NOT NULL, " + Menu_column[4] + " VARCHAR NOT NULL, FOREIGN KEY ("+Menu_column[3]+") REFERENCES "+TABLE_Restaurant+"("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_MenuPrice + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + MenuPrice_column[1]  + " VARCHAR NOT NULL, " + MenuPrice_column[2] + " VARCHAR NOT NULL, " + MenuPrice_column[3] + " VARCHAR NOT NULL, " + MenuPrice_column[4] + "  DECIMAL(9,2) NOT NULL, UNIQUE ("+ MenuPrice_column[1]+", " + MenuPrice_column[2] +", " + MenuPrice_column[3] +") ON CONFLICT REPLACE, FOREIGN KEY ("+MenuPrice_column[1]+") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_Payment + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Payment_column[1] + " VARCHAR NOT NULL, " + Payment_column[2] + " VARCHAR NOT NULL,  FOREIGN KEY ("+Payment_column[1]+") REFERENCES Restaurant ("+Payment_column[2]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_Meal + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Meal_column[1] + " VARCHAR NOT NULL, " + Meal_column[2] + " VARCHAR NOT NULL, " + Meal_column[3] + " DECIMAL(9,2) NOT NULL, " + Meal_column[4] + " INTEGER, " + Meal_column[5] + " TEXT, UNIQUE ("+Meal_column[1]+", " + Meal_column[2]+") ON CONFLICT REPLACE, FOREIGN KEY ("+Meal_column[2]+") REFERENCES "+ TABLE_Restaurant+" ("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_FavouriteMeal + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + FavouriteMeal_column[1] + " VARCHAR NOT NULL , " + FavouriteMeal_column[2] + " VARCHAR NOT NULL , FOREIGN KEY ("+FavouriteMeal_column[1]+") REFERENCES " + TABLE_Client + " ("+Client_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE );");
		db.execSQL("CREATE TABLE " + TABLE_Booking + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Booking_column[1] + " VARCHAR NOT NULL, " + Booking_column[2] + " VARCHAR NOT NULL, " + Booking_column[3] + " INTEGER NOT NULL, " + Booking_column[4] + " VARCHAR NOT NULL , UNIQUE("+Booking_column[1]+", "+ Booking_column[4] + ", "+ Booking_column[2]+ ", "+ Booking_column[3] +") ON CONFLICT REPLACE, FOREIGN KEY ("+Booking_column[1]+") REFERENCES "+ TABLE_Restaurant +"("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY ("+Booking_column[2]+") REFERENCES "+TABLE_Client+ "("+Client_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_Order + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Order_column[1] +" VARCHAR NOT NULL, " + Order_column[2] +" VARCHAR NOT NULL, " + Order_column[3] + " VARCHAR, " + Order_column[4] + " VARCHAR NOT NULL, " + Order_column[5] + " INTEGER NOT NULL, FOREIGN KEY ("+Order_column[1]+") REFERENCES "+ TABLE_Restaurant + "(" + Restaurant_column[1] +") ON UPDATE CASCADE ON DELETE CASCADE , FOREIGN KEY ("+Order_column[2]+") REFERENCES "+ TABLE_Client +"("+Client_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_FavouriteRestaurant + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + FavouriteRestaurant_column[1] + " VARCHAR NOT NULL , " + FavouriteRestaurant_column[2] + " VARCHAR NOT NULL, FOREIGN KEY ("+FavouriteRestaurant_column[1]+") REFERENCES " + TABLE_Client + "("+Client_column[1]+"), FOREIGN KEY ("+FavouriteRestaurant_column[2]+") REFERENCES " + TABLE_Restaurant + "("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_Specificity + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Specificity_column[1] + " VARCHAR NOT NULL , " + Specificity_column[2] + " VARCHAR NOT NULL, FOREIGN KEY ("+Specificity_column[1]+") REFERENCES "+TABLE_Client+"("+Client_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_Allergy + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Allergy_column[1] + " VARCHAR NOT NULL , " + Allergy_column[2] + " VARCHAR NOT NULL, FOREIGN KEY ("+Allergy_column[1]+") REFERENCES "+TABLE_Client+"("+Client_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		db.execSQL("CREATE TABLE " + TABLE_MealSpecificity + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + MealSpecificity_column[1] + " VARCHAR NOT NULL, " + MealSpecificity_column[2] + " VARCHAR NOT NULL, " + MealSpecificity_column[3] + " VARCHAR NOT NULL, FOREIGN KEY ("+MealSpecificity_column[2]+") REFERENCES "+TABLE_Restaurant+"("+Restaurant_column[1]+") ON UPDATE CASCADE ON DELETE CASCADE);");
		
	}
	public void populateDatabase(SQLiteDatabase db) {//To correct (menuPrice) -> I don't find a good example
		
		db.execSQL("INSERT INTO " + TABLE_Address + "(" + Address_column[1] + ", " + Address_column[2] + ", " + Address_column[3] + ", " + Address_column[4] +") VALUES('50.668572,4.616146','1A','Place des Brabancons','Louvain-la-Neuve');");
		db.execSQL("INSERT INTO " + TABLE_Address + "(" + Address_column[1] + ", " + Address_column[2] + ", " + Address_column[3] + ", " + Address_column[4] +") VALUES('50.6609,4.617962',NULL,'Place Polyvalente','Louvain-la-Neuve');");
		db.execSQL("INSERT INTO " + TABLE_Address + "(" + Address_column[1] + ", " + Address_column[2] + ", " + Address_column[3] + ", " + Address_column[4] +") VALUES('50.671578,4.61282','26','Rue du Labrador','Louvain-la-Neuve');");
		db.execSQL("INSERT INTO " + TABLE_Address + "(" + Address_column[1] + ", " + Address_column[2] + ", " + Address_column[3] + ", " + Address_column[4] +") VALUES('50.842736,4.345844','23','Place Rouppe','Bruxelles');");
		
		db.execSQL("INSERT INTO " + TABLE_Contact + "(" + Contact_column[1] + ", " + Contact_column[2] + ", " + Contact_column[3] + ", " + Contact_column[4] +")  VALUES('010/45.15.85','010/45.25.46',NULL,'http://www.lacreperiebretonne.be/'); ");
		db.execSQL("INSERT INTO " + TABLE_Contact + "(" + Contact_column[1] + ", " + Contact_column[2] + ", " + Contact_column[3] + ", " + Contact_column[4] +")  VALUES('010/45.64.62','010/81.23.62','info@loungeatude.be','www.loungeatude.be'); ");
		db.execSQL("INSERT INTO " + TABLE_Contact + "(" + Contact_column[1] + ", " + Contact_column[2] + ", " + Contact_column[3] + ", " + Contact_column[4] +")  VALUES('010/48.84.26',NULL,'petitvingtieme@museeherge.com','http://www.lepetitvingtieme.be/'); ");
		db.execSQL("INSERT INTO " + TABLE_Contact + "(" + Contact_column[1] + ", " + Contact_column[2] + ", " + Contact_column[3] + ", " + Contact_column[4] +")  VALUES('02/512.29.21',NULL,NULL,'http://www.commechezsoi.be'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Restaurant + "(" + Restaurant_column[1] + ", " + Restaurant_column[2] + ", " + Restaurant_column[3] + ", " + Restaurant_column[4] + ", " + Restaurant_column[5]+ ", " + Restaurant_column[6] + ", " + Restaurant_column[7] + ", " + Restaurant_column[8] +", " + Restaurant_column[9] +")  VALUES('Loungeatude', 'mathieu.jadin@student.uclouvain.be','010/45.64.62','50.6609,4.617962',55,'Un simple coup d''oeil suffit e se rendre compte que vous n''�tes ni dans un restaurant. Ni dans un bar. Encore moins dans une galerie deart. Et pourtant, vous prendrez l''aperitif au salon, vous dinerez dans la salle a manger et vous terminerez peut-etre votre pousse-cafe au bar devant une huile ou une aquarelle." +"\n" + " Vous etes au Loungeatude." +"\n" + " Vous etes chez vous.', 3, 2, 'lounge'); ");
		db.execSQL("INSERT INTO " + TABLE_Restaurant + "(" + Restaurant_column[1] + ", " + Restaurant_column[2] + ", " + Restaurant_column[3] + ", " + Restaurant_column[4] + ", " + Restaurant_column[5]+ ", " + Restaurant_column[6] + ", " + Restaurant_column[7] + ", " + Restaurant_column[8] +", " + Restaurant_column[9] +")   VALUES('Creperie Bretonne','ludovic.fastre@student.uclouvain.be','010/45.15.85','50.668572,4.616146',45,'Dans notre salle au decor rustique ou en terrasse aux beaux jours, degustez l''une de nos 350 crepes salees d''inspiration franeaise ou exotique, ou appreciez l''une de nos salades variees. Pour accompagner votre plat, nous avons e votre disposition une carte de plus de 200 bieres artisanales belges dont toutes les trapistes et 5 bieres au fet. Nous pouvons aussi vous servir un cidre bien frais ou vous laisser choisir un vin de pays." +"\n" + "En dessert, vous choisirez une crepe sucree ou flambee, une coupe de glace maison ou encore un milkshake que vous accompagnerez d''un cafe lointain, un the vert parfume ou une infusion. Nous vous accueillons et vous servons tout au long du jour de 9h du matin jusqu''e 1h de la nuit, la cuisine restant ouverte non-stop.', 3, 1, 'breton'); ");
		db.execSQL("INSERT INTO " + TABLE_Restaurant + "(" + Restaurant_column[1] + ", " + Restaurant_column[2] + ", " + Restaurant_column[3] + ", " + Restaurant_column[4] + ", " + Restaurant_column[5]+ ", " + Restaurant_column[6] + ", " + Restaurant_column[7] + ", " + Restaurant_column[8] +", " + Restaurant_column[9] +")  VALUES('Le Petit Vingtieme','antoine.walsdorff@student.uclouvain.be','010/48.84.26','50.671578,4.61282',45,'Au sein du Musee Herge, le restaurant" +"\n" + "le Petit Vingtieme vous invite, dans un cadre raffine" +"\n" + "et moderne, pour un moment de plaisir et de detente." +"\n" + "Proposant une gastronomie fine variant au fil" +"\n" + "des saisons, vous pourrez y deguster au gre de" +"\n" + "vos envies des preparations melant fraecheur" +"\n" + "des produits et purete du goet.', 4, 1, 'petit'); ");
		db.execSQL("INSERT INTO " + TABLE_Restaurant + "(" + Restaurant_column[1] + ", " + Restaurant_column[2] + ", " + Restaurant_column[3] + ", " + Restaurant_column[4] + ", " + Restaurant_column[5]+ ", " + Restaurant_column[6] + ", " + Restaurant_column[7] + ", " + Restaurant_column[8] +", " + Restaurant_column[9] +")  VALUES('Comme chez soi','comme@chez.soi','02/512.29.21','50.842736,4.345844',123,'Venez chez nous c''est trop bon', 4, 3, 'soi'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Client + "(" + Client_column[1] + ", " + Client_column[2] + ", " + Client_column[3] +", " + Client_column[4] +") VALUES('toni@hotmail.com','Durant','0472/56.97.48', 'dudu');  ");
		db.execSQL("INSERT INTO " + TABLE_Client + "(" + Client_column[1] + ", " + Client_column[2] + ", " + Client_column[3] +", " + Client_column[4] +") VALUES('j.p@yahoo.fr','Dupont','0473/56.34.76', 'yahoo');  ");
		
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Loungeatude','Vegetarien'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Loungeatude','Wi-Fi'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Loungeatude','Acces handicapes'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +") VALUES('Loungeatude','Service traiteur');  ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Creperie Bretonne','Vegetarien'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Creperie Bretonne','Terrasse'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +") VALUES('Creperie Bretonne','Service traiteur');  ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +") VALUES('Le Petit Vingtieme','Service traiteur');  ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +") VALUES('Le Petit Vingtieme','Wi-Fi');  ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Le Petit Vingtieme','Acces handicapes'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Le Petit Vingtieme','Climatisation'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Le Petit Vingtieme','Plats enfants'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Comme chez soi','Wi-Fi'); ");
		db.execSQL("INSERT INTO " + TABLE_Advantage + "(" + Advantage_column[1] + ", " + Advantage_column[2] +")  VALUES('Comme chez soi','Terrasse'); ");

		
		db.execSQL("INSERT INTO " + TABLE_Cook + "(" + Cook_column[1] + ", " + Cook_column[2] +")  VALUES('Le Petit Vingtieme','Francaise'); ");
		db.execSQL("INSERT INTO " + TABLE_Cook + "(" + Cook_column[1] + ", " + Cook_column[2] +")  VALUES('Loungeatude','Francaise'); ");
		db.execSQL("INSERT INTO " + TABLE_Cook + "(" + Cook_column[1] + ", " + Cook_column[2] +")  VALUES('Creperie Bretonne','Crepes'); ");
		db.execSQL("INSERT INTO " + TABLE_Cook + "(" + Cook_column[1] + ", " + Cook_column[2] +")  VALUES('Loungeatude','Fusion'); ");
		db.execSQL("INSERT INTO " + TABLE_Cook + "(" + Cook_column[1] + ", " + Cook_column[2] +")  VALUES('Loungeatude','Mediterraneenne'); ");
		db.execSQL("INSERT INTO " + TABLE_Cook + "(" + Cook_column[1] + ", " + Cook_column[2] +")  VALUES('Comme chez soi','Mediterraneenne'); ");
		db.execSQL("INSERT INTO " + TABLE_Cook + "(" + Cook_column[1] + ", " + Cook_column[2] +")  VALUES('Comme chez soi','Francaise'); ");

		
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Le Petit Vingtieme','Mardi','10:30:00','17:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Le Petit Vingtieme','Mercredi','10:30:00','17:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Le Petit Vingtieme','Jeudi','10:30:00','17:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Le Petit Vingtieme','Vendredi','10:30:00','17:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Le Petit Vingtieme','Samedi','10:00:00','18:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Le Petit Vingtieme','Dimanche','10:00:00','18:00:00'); ");
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
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Comme chez soi','Lundi','11:00:00','14:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Comme chez soi','Lundi','19:00:00','22:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Comme chez soi','Mardi','11:00:00','14:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Comme chez soi','Mardi','19:00:00','22:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Comme chez soi','Mercredi','11:00:00','14:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Comme chez soi','Mercredi','19:00:00','22:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Comme chez soi','Jeudi','11:00:00','14:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Comme chez soi','Jeudi','19:00:00','22:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Comme chez soi','Vendredi','11:00:00','14:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Comme chez soi','Vendredi','19:00:00','22:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Schedule + "(" + Schedule_column[1] + ", " + Schedule_column[2] +", " + Schedule_column[3] + ", " + Schedule_column[4] +")  VALUES('Comme chez soi','Samedi','18:00:00','22:00:00'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Closing + "(" + Closing_column[1] + ", " + Closing_column[2] +")  VALUES('Creperie Bretonne','05-01'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +") VALUES('Le Petit Vingtieme','pti1');");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Le Petit Vingtieme','pti2'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Le Petit Vingtieme','pti3'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Le Petit Vingtieme','pti4'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Le Petit Vingtieme','pti5'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Le Petit Vingtieme','pti6'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Loungeatude','loungeatude_1'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Loungeatude','loungeatude_2'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Loungeatude','loungeatude_3'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Loungeatude','loungeatude_4'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Loungeatude','loungeatude_4'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Loungeatude','loungeatude_5'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Loungeatude','loungeatude_6'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Loungeatude','loungeatude_7'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Loungeatude','loungeatude_8'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Loungeatude','loungeatude_9'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Creperie Bretonne','bret2'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Creperie Bretonne','bret3'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Creperie Bretonne','bret4'); ");
		db.execSQL("INSERT INTO " + TABLE_Image + "(" + Image_column[1] + ", " + Image_column[2] +")  VALUES('Creperie Bretonne','bret5'); ");
		
		db.execSQL("INSERT INTO " + TABLE_ImageMeal + "(" + ImageMeal_column[1] + ", " + ImageMeal_column[2] + ", " + ImageMeal_column[3] +") VALUES ('Crepe La tartiflette','Creperie Bretonne','bret1');");
		
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Nos Viandes','plat','Le Petit Vingtieme','Entrecote de Boeuf Belge Grillee +- 300gr'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Nos Entrees','entree','Le Petit Vingtieme','Carpaccio de Bresaola et Jambon de Parme'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Nos Poissons','plat','Le Petit Vingtieme','Gambas reties au four'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Potage-Pete-Salade-Preparation','entree','Loungeatude','Potage de scampis et canard laque e la thaee'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Volailles','plat','Loungeatude','Jambonnette de volaille aux scampis'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Viandes','plat','Loungeatude','Filet pur de boeuf irlandais en Tagliata, parmigiano et rucola'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Salades','entree','Creperie Bretonne','Salade au roquefort'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Salades','entree','Creperie Bretonne','Salade Tomates Mozzarella'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Crepe des Gourmets','plat','Creperie Bretonne','Crepe La tartiflette'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Crepe des Gourmets','plat','Creperie Bretonne','Crepe au saumon fume, sauce creme et citron e l" + "'" + "'aneth'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Lunch','entree','Comme chez soi','Cari de loup de mer aux champignons, artichaut et ciboulette'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Lunch','plat','Comme chez soi','Coquelet croustillant a l''instar de Vise, garniture exotique a la verveine-citron'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Nos Plats','plat','Comme chez soi','Contrefilet de Rubia Gallega a la Leffe brune, gelee de moelle, gateau de patates douces au romarin'); ");
		db.execSQL("INSERT INTO " + TABLE_Menu + "(" + Menu_column[1] + ", " + Menu_column[2] +", " + Menu_column[3] +", " + Menu_column[4] +")  VALUES('Crepe des Gourmets','plat','Creperie Bretonne','Crepe au filet de biche a l orange et au gingembre'); ");

		
		//To correct -> find menu with price but with some meals recorded
		db.execSQL("INSERT INTO " + TABLE_MenuPrice + "(" + MenuPrice_column[1] + ", " + MenuPrice_column[2] + ", " + MenuPrice_column[3] +  ", " + MenuPrice_column[4] +") VALUES('Creperie Bretonne','Dejeuner','plat',7);  ");
		
		db.execSQL("INSERT INTO " + TABLE_Payment + "(" + Payment_column[1] + ", " + Payment_column[2] +")  VALUES('Creperie Bretonne','Cash'); ");
		db.execSQL("INSERT INTO " + TABLE_Payment + "(" + Payment_column[1] + ", " + Payment_column[2] +")  VALUES('Le Petit Vingtieme','MasterCard'); ");
		db.execSQL("INSERT INTO " + TABLE_Payment + "(" + Payment_column[1] + ", " + Payment_column[2] +")  VALUES('Le Petit Vingtieme','Cash'); ");
		db.execSQL("INSERT INTO " + TABLE_Payment + "(" + Payment_column[1] + ", " + Payment_column[2] +")  VALUES('Loungeatude','Cash'); ");
		db.execSQL("INSERT INTO " + TABLE_Payment + "(" + Payment_column[1] + ", " + Payment_column[2] +")  VALUES('Comme chez soi','Cash'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Salade au roquefort','Creperie Bretonne',13.95,3, NULL); ");//12.8125
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Salade Tomates Mozzarella','Creperie Bretonne',11.2,20, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Crepe La tartiflette','Creperie Bretonne',12.15,16, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Crepe au saumon fume, sauce creme et citron e l" + "'" + "'aneth','Creperie Bretonne',13.95,15, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Potage de scampis et canard laque e la thaee','Loungeatude',17.95,10, NULL);  ");//21.28
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Jambonnette de volaille aux scampis','Loungeatude',19.95,5, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Filet pur de boeuf irlandais en Tagliata','Loungeatude',25.95,10, NULL);  ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Carpaccio de Bresaola et Jambon de Parme','Le Petit Vingtieme',12,10, NULL); ");//16
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Entrecete de Boeuf Belge Grillee +- 300gr','Le Petit Vingtieme',19,5, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Gambas reties au four','Le Petit Vingtieme',17,20, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Cari de loup de mer aux champignons, artichaut et ciboulette','Comme chez soi',15.5,5, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Coquelet croustillant a l''instar de Vise, garniture exotique a la verveine-citron','Comme chez soi',25.5,5, NULL); ");
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Contrefilet de Rubia Gallega a la Leffe brune, gelee de moelle, gateau de patates douces au romarin','Comme chez soi',69,5, NULL); "); 
		db.execSQL("INSERT INTO " + TABLE_Meal + "(" + Meal_column[1] + ", " + Meal_column[2] + ", " + Meal_column[3] + ", " + Meal_column[4] + ", " + Meal_column[5] +")  VALUES('Crepe au filet de biche a l orange et au gingembre','Creperie Bretonne',12.5,5, NULL); ");

		db.execSQL("INSERT INTO " + TABLE_FavouriteMeal + "(" + FavouriteMeal_column[1] + ", " + FavouriteMeal_column[2] + ")  VALUES('toni@hotmail.com','Brochette de poulet'); ");
		db.execSQL("INSERT INTO " + TABLE_FavouriteMeal + "(" + FavouriteMeal_column[1] + ", " + FavouriteMeal_column[2] + ") VALUES('toni@hotmail.com','Steak frites');  ");
		db.execSQL("INSERT INTO " + TABLE_FavouriteMeal + "(" + FavouriteMeal_column[1] + ", " + FavouriteMeal_column[2] + ")  VALUES('j.p@yahoo.fr','Steak fites'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Booking + "(" + Booking_column[1] + ", " + Booking_column[2] + ", " + Booking_column[3] + ", " + Booking_column[4] +")  VALUES('Le Petit Vingtieme','toni@hotmail.com',3,'2013-04-30 19:00:00');  ");
		db.execSQL("INSERT INTO " + TABLE_Booking + "(" + Booking_column[1] + ", " + Booking_column[2] + ", " + Booking_column[3] + ", " + Booking_column[4] +")  VALUES('Creperie Bretonne','j.p@yahoo.fr',45,'2013-05-02 20:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Booking + "(" + Booking_column[1] + ", " + Booking_column[2] + ", " + Booking_column[3] + ", " + Booking_column[4] +")  VALUES('Creperie Bretonne','toni@hotmail.com',6,'2013-04-30 22:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Booking + "(" + Booking_column[1] + ", " + Booking_column[2] + ", " + Booking_column[3] + ", " + Booking_column[4] +")  VALUES('Loungeatude','j.p@yahoo.fr',10,'2013-04-30 20:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Booking + "(" + Booking_column[1] + ", " + Booking_column[2] + ", " + Booking_column[3] + ", " + Booking_column[4] +")  VALUES('Creperie Bretonne','j.p@yahoo.fr',10,'2013-04-30 20:00:00'); ");
		db.execSQL("INSERT INTO " + TABLE_Booking + "(" + Booking_column[1] + ", " + Booking_column[2] + ", " + Booking_column[3] + ", " + Booking_column[4] +")  VALUES('Creperie Bretonne','toni@hotmail.com',3,'2013-05-30 19:00:00');  ");
		
		db.execSQL("INSERT INTO " + TABLE_Order + "(" + Order_column[1] + ", " + Order_column[2] + ", " + Order_column[3] + ", " + Order_column[4] + ", " + Order_column[5] +")  VALUES('Creperie Bretonne','j.p@yahoo.fr','NULL','Crepe au filet de biche a l orange et au gingembre',3); ");
		db.execSQL("INSERT INTO " + TABLE_Order + "(" + Order_column[1] + ", " + Order_column[2] + ", " + Order_column[3] + ", " + Order_column[4] + ", " + Order_column[5] +")  VALUES('Le Petit Vingtieme','toni@hotmail.com','2013-04-30 19:00:00','Carpaccio de Bresaola et Jambon de Parme',3); ");
		db.execSQL("INSERT INTO " + TABLE_Order + "(" + Order_column[1] + ", " + Order_column[2] + ", " + Order_column[3] + ", " + Order_column[4] + ", " + Order_column[5] +")  VALUES('Creperie Bretonne','j.p@yahoo.fr','2013-05-02 20:00:00','Crepe La tartiflette',40); ");
		db.execSQL("INSERT INTO " + TABLE_Order + "(" + Order_column[1] + ", " + Order_column[2] + ", " + Order_column[3] + ", " + Order_column[4] + ", " + Order_column[5] +")  VALUES('Creperie Bretonne','j.p@yahoo.fr','2013-05-02 20:00:00','Crepe au saumon fume, sauce creme et citron et aneth',5); ");
		db.execSQL("INSERT INTO " + TABLE_Order + "(" + Order_column[1] + ", " + Order_column[2] + ", " + Order_column[3] + ", " + Order_column[4] + ", " + Order_column[5] +")  VALUES('Loungeatude','j.p@yahoo.fr',NULL,'Jambonnette de volaille aux scampis',10); ");
		db.execSQL("INSERT INTO " + TABLE_Order + "(" + Order_column[1] + ", " + Order_column[2] + ", " + Order_column[3] + ", " + Order_column[4] + ", " + Order_column[5] +")  VALUES('Creperie Bretonne','toni@hotmail.com','2013-04-30 22:00:00','Crepe au filet de biche a l orange et au gingembre',6); ");
		db.execSQL("INSERT INTO " + TABLE_Order + "(" + Order_column[1] + ", " + Order_column[2] + ", " + Order_column[3] + ", " + Order_column[4] + ", " + Order_column[5] +")  VALUES('Creperie Bretonne','j.p@yahoo.fr','2013-04-30 20:00:00','Crepe La tartiflette',40); ");
		db.execSQL("INSERT INTO " + TABLE_Order + "(" + Order_column[1] + ", " + Order_column[2] + ", " + Order_column[3] + ", " + Order_column[4] + ", " + Order_column[5] +")  VALUES('Creperie Bretonne','toni@hotmail.com','2013-05-30 19:00:00','Carpaccio de Bresaola et Jambon de Parme',3); ");
		
		db.execSQL("INSERT INTO " + TABLE_FavouriteRestaurant + "(" + FavouriteRestaurant_column[1] + ", " + FavouriteRestaurant_column[2] + ")  VALUES('toni@hotmail.com','Creperie Bretonne'); ");
		db.execSQL("INSERT INTO " + TABLE_FavouriteRestaurant + "(" + FavouriteRestaurant_column[1] + ", " + FavouriteRestaurant_column[2] + ")  VALUES('j.p@yahoo.fr','Creperie Bretonne'); ");
		db.execSQL("INSERT INTO " + TABLE_FavouriteRestaurant + "(" + FavouriteRestaurant_column[1] + ", " + FavouriteRestaurant_column[2] + ")   VALUES('j.p@yahoo.fr','Loungeatude'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Specificity + "(" + Specificity_column[1] + ", " + Specificity_column[2] + ") VALUES('j.p@yahoo.fr','Vegetarien');  ");
		db.execSQL("INSERT INTO " + TABLE_Specificity + "(" + Specificity_column[1] + ", " + Specificity_column[2] + ")  VALUES('toni@hotmail.com','Vegetarien'); ");
		
		db.execSQL("INSERT INTO " + TABLE_Allergy + "(" + Allergy_column[1] + ", " + Allergy_column[2] + ")  VALUES('toni@hotmail.com','gluten'); ");
		
		db.execSQL("INSERT INTO " + TABLE_MealSpecificity + "(" + MealSpecificity_column[1] + ", " + MealSpecificity_column[2] + ", " + MealSpecificity_column[3] + ") VALUES('Salade Tomates Mozzarella','Creperie Bretonne','Vegetarien');  ");
		
		
	}
	
	public void deleteDatabase(SQLiteDatabase db) {
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Advantage);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Cook);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Schedule);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Closing);
		db.execSQL("DROP TABLE IT EXISTS " + TABLE_Image);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ImageMeal);
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
