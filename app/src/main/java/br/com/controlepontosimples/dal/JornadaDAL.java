package br.com.controlepontosimples.dal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.controlepontosimples.model.JornadaItem;
import br.com.controlepontosimples.model.PontoItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class JornadaDAL extends AbstractDAL {

	public JornadaDAL(Context context) {
		super(context);
	}

	public List<JornadaItem> getJornadas() {
		this.openReadableDatabase();
		List<JornadaItem> lst = new ArrayList<JornadaItem>();
		try {

			this.c = this.db.query(DatabaseContract.JornadaEntry.TABLE_NAME,
					DatabaseContract.JornadaEntry.getColumns(), null, null, null, null, null);
			if (this.c.getCount() > 0) {
				this.c.moveToFirst();
				do {
					lst.add(this.fillJornada());
				} while (this.c.moveToNext());
			}
		} catch (Exception e) {
			return null;
		} finally {
			this.closeDatabase();
		}
		return lst;
	}

	public JornadaItem getJornada(int id) {
		this.openReadableDatabase();
		JornadaItem item = new JornadaItem();
		try {
			String selection = DatabaseContract.JornadaEntry._ID + " = ?";
			String[] selectionArgs = new String[]{String.valueOf(id)};
			this.c = this.db.query(DatabaseContract.JornadaEntry.TABLE_NAME,
					DatabaseContract.JornadaEntry.getColumns(), selection, selectionArgs, null, null, null);
			if (this.c.getCount() > 0) {
				this.c.moveToFirst();
				item = this.fillJornada();
			}
			return item;
		} catch (Exception e) {
			e.printStackTrace();
			return item;
		} finally {
			this.closeDatabase();
		}
	}

	public JornadaItem getJornadaByTimestamp(long timestamp) {
		this.openReadableDatabase();
		JornadaItem item = null;
		try {
			String sql = "SELECT " + DatabaseContract.JornadaEntry._ID + ", "
					+ DatabaseContract.JornadaEntry.COLUMN_DT_JORNADA + " FROM "
					+ DatabaseContract.JornadaEntry.TABLE_NAME 
					+ " WHERE DATE(" + DatabaseContract.JornadaEntry.COLUMN_DT_JORNADA + "/1000, 'unixepoch') = DATE(" + String.valueOf(timestamp) + "/1000, 'unixepoch')";

			this.c = this.db.rawQuery(sql, null);
			if (this.c.getCount() > 0) {
				this.c.moveToFirst();
				item = this.fillJornada();
			}
			return item;
		} catch (Exception e) {
			e.printStackTrace();
			return item;
		} finally {
			this.closeDatabase();
		}
	}

	public long insertJornada(JornadaItem item) {
		this.openWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put(DatabaseContract.JornadaEntry.COLUMN_DT_JORNADA, item.getDataJornada());
			return this.db.insert(DatabaseContract.JornadaEntry.TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			this.closeDatabase();
		}
	}

	public int updateJornada(JornadaItem item) {
		this.openWritableDatabase();
		try {
			String whereClause = DatabaseContract.JornadaEntry._ID + " = ?";
			String[] whereArgs = new String[]{String.valueOf(item.getId())};
			ContentValues values = new ContentValues();
			values.put(DatabaseContract.JornadaEntry.COLUMN_DT_JORNADA, item.getDataJornada());
			return this.db.update(DatabaseContract.JornadaEntry.TABLE_NAME, values, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			this.closeDatabase();
		}
	}

	public int deleteJornada(int id) {
		this.openWritableDatabase();
		try {
			String whereClause = DatabaseContract.JornadaEntry._ID + " = ?";
			String[] whereArgs = new String[]{String.valueOf(id)};
			return this.db.delete(DatabaseContract.JornadaEntry.TABLE_NAME, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			this.closeDatabase();
		}
	}

	private JornadaItem fillJornada() {
		JornadaItem item = new JornadaItem();
		item.setId(this.c.getInt(this.c.getColumnIndex(DatabaseContract.JornadaEntry._ID)));
		item.setDataJornada(this.c.getLong(this.c.getColumnIndex(DatabaseContract.JornadaEntry.COLUMN_DT_JORNADA)));
		return item;
	}
}
