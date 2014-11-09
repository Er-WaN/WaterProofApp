package fr.maaf.waterproof.dao;

import java.util.ArrayList;
import java.util.List;

import fr.maaf.waterproof.db.DAOBase;
import fr.maaf.waterproof.model.DataSeance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class SeanceDAO extends DAOBase {
	
	public static final String SEANCE_TABLE = "Seance";
	public static final String SEANCE_ID = "_id";
	public static final String SEANCE_DATE_DEBUT = "Date_debut";
	public static final String SEANCE_DATE_FIN = "Date_fin";
	public static final String SEANCE_DUREE = "Duree";
	public static final String SEANCE_DISTANCE_GPS = "Distance_gps";
	public static final String SEANCE_DISTANCE_VOITURE = "Distance_voiture";
	
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + SEANCE_TABLE + ";";
	
	public SeanceDAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Ajoute une nouvelle Seance dans la base de donnees
	 * @param dateDebut
	 * @param dateFin
	 * @param duree
	 * @param distanceGPS
	 * @param distanceVoiture
	 */
	public void create(String dateDebut, String dateFin, String duree, String distanceGPS, String distanceVoiture) {
		ContentValues value = new ContentValues();
		value.put(SeanceDAO.SEANCE_DATE_DEBUT, dateDebut);
		value.put(SeanceDAO.SEANCE_DATE_FIN, dateFin);
		value.put(SeanceDAO.SEANCE_DUREE, duree);
		value.put(SeanceDAO.SEANCE_DISTANCE_GPS, distanceGPS);
		value.put(SeanceDAO.SEANCE_DISTANCE_VOITURE, distanceVoiture);
		mDb.insert(SEANCE_TABLE, null, value);
	}
	
	/**
	 * @param seance
	 */
	public void delete(DataSeance seance) {
		mDb.delete(SEANCE_TABLE, SEANCE_ID + " = ?", new String[] {String.valueOf(seance.getId())});
	}
	
	/**
	 * Retourne la liste de toutes les seances
	 * @return List<DataSeance> la liste des seances
	 */
	public List<DataSeance> getAllSeances() {
		List<DataSeance> seanceList = new ArrayList<DataSeance>();

		Cursor cursor = mDb.rawQuery("SELECT * FROM Seance", null);

		if (cursor.moveToFirst()) {
			do {
				DataSeance seance = new DataSeance();
				seance.setId(cursor.getInt(0));
				seance.setDateDebut(cursor.getString(1));
				seance.setDateFin(cursor.getString(2));
				seance.setDuree(cursor.getString(3));
				seance.setDistanceGPS(cursor.getString(4));
				seance.setDistanceVoiture(cursor.getString(5));
				seanceList.add(seance);
			} while (cursor.moveToNext());

		}
		cursor.close();
		return seanceList;
	}
	
	/**
	 * Retourne le nombre total de seances
	 * @return int le nombre de seance
	 */
	public int getNombreSeances() {
		
		int nombreSeances = 0;
		
		Cursor cursor = mDb.rawQuery("SELECT count(*) FROM Seance", null);
		
		if (cursor.moveToFirst()) {
			do {
				nombreSeances = cursor.getInt(0);
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return nombreSeances;
	}
	
	public List<String> getListeSeanceString() {
		
		List<String> seanceListString = new ArrayList<String>();
		List<DataSeance> seanceList = new ArrayList<DataSeance>();

		Cursor cursor = mDb.rawQuery("SELECT * FROM Seance", null);

		if (cursor.moveToFirst()) {
			do {
				DataSeance seance = new DataSeance();
				seance.setId(cursor.getInt(0));
				seance.setDateDebut(cursor.getString(1));
				seance.setDateFin(cursor.getString(2));
				seance.setDuree(cursor.getString(3));
				seance.setDistanceGPS(cursor.getString(4));
				seance.setDistanceVoiture(cursor.getString(5));
				seanceList.add(seance);
				
				seanceListString.add("Date : " + seance.getDateDebut() + ", Km : " + seance.getDistanceVoiture());
			} while (cursor.moveToNext());

		}
		cursor.close();
		return seanceListString;
	}

}
