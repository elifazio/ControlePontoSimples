package br.com.controlepontosimples.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context) {
		super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			db.execSQL(DatabaseContract.JornadaEntry.getSqlCreateTable());
			db.execSQL(DatabaseContract.PontoEntry.getSqlCreateTable());
			db.execSQL(DatabaseContract.LembreteEntry.getSqlCreateTable());
		} catch (Exception e) {
			db.endTransaction();
			e.printStackTrace();
		} finally {
			if (db.inTransaction()) {
				db.setTransactionSuccessful();
				db.endTransaction();
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			if (oldVersion == 1 && newVersion == 2) {
				db.beginTransaction();
				db.execSQL(DatabaseContract.LembreteEntry.getSqlCreateTable());
			}
		} catch (Exception e) {
			db.endTransaction();
			e.printStackTrace();
		} finally {
			if (db.inTransaction()) {
				db.setTransactionSuccessful();
				db.endTransaction();
			}
		}
	}

}
