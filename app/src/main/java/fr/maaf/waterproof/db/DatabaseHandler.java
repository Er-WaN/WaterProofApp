package fr.maaf.waterproof.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	public final String DBNAME = "AndroidConduite";
	
	public static final String SEANCE_TABLE = "Seance";
	public static final String SEANCE_ID = "_id";
	public static final String SEANCE_DATE_DEBUT = "Date_debut";
	public static final String SEANCE_DATE_FIN = "Date_fin";
	public static final String SEANCE_DUREE = "Duree";
	public static final String SEANCE_DISTANCE_GPS = "Distance_gps";
	public static final String SEANCE_DISTANCE_VOITURE = "Distance_voiture";
	

	
	public static final String SEANCE_TABLE_CREATE = "CREATE TABLE " + SEANCE_TABLE + 
			" (" + SEANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			SEANCE_DATE_DEBUT + " STRING , " + SEANCE_DATE_FIN + " STRING , " + 
			SEANCE_DUREE + " STRING , " + SEANCE_DISTANCE_GPS + " STRING, " +
			SEANCE_DISTANCE_VOITURE + " STRING	" + ")";
	

	
			

	public DatabaseHandler(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create the table Contact_Table
		db.execSQL(SEANCE_TABLE_CREATE);
		
		db.execSQL("insert into Seance (Date_debut, Date_fin, Duree, "
				+ "Distance_gps, Distance_voiture) values (100, 100, null, 100, 100)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + SEANCE_TABLE);
		onCreate(db);
	}

}