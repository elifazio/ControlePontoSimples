package br.com.controlepontosimples.dal;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import br.com.controlepontosimples.model.LembreteItem;

public class LembreteDAL extends AbstractDAL {

	public LembreteDAL(Context context) {
		super(context);
	}

	public List<LembreteItem> getLembretes() {
		this.openReadableDatabase();
		List<LembreteItem> lst = new ArrayList<LembreteItem>();
		try {

			this.c = this.db.query(DatabaseContract.LembreteEntry.TABLE_NAME,
					DatabaseContract.LembreteEntry.getColumns(), null, null, null, null, null);
			if (this.c.getCount() > 0) {
				this.c.moveToFirst();
				do {
					lst.add(this.fillLembrete());
				} while (this.c.moveToNext());
			}
		} catch (Exception e) {
			return null;
		} finally {
			this.closeDatabase();
		}
		return lst;
	}

	public LembreteItem getLembrete(int id) {
		this.openReadableDatabase();
		LembreteItem item = new LembreteItem();
		try {
			String selection = DatabaseContract.LembreteEntry._ID + " = ?";
			String[] selectionArgs = new String[]{String.valueOf(id)};
			this.c = this.db.query(DatabaseContract.LembreteEntry.TABLE_NAME,
					DatabaseContract.LembreteEntry.getColumns(), selection, selectionArgs, null, null, null);
			if (this.c.getCount() > 0) {
				this.c.moveToFirst();
				item = this.fillLembrete();
			}
			return item;
		} catch (Exception e) {
			e.printStackTrace();
			return item;
		} finally {
			this.closeDatabase();
		}
	}

	public LembreteItem getLembreteByTimestamp(long timestamp) {
		this.openReadableDatabase();
		LembreteItem item = null;
		try {
			String sql = "SELECT " + DatabaseContract.LembreteEntry._ID + ", "
					+ DatabaseContract.LembreteEntry.COLUMN_ID_PONTO + ", "
					+ DatabaseContract.LembreteEntry.COLUMN_DT_LEMBRETE 
					+ " FROM "
					+ DatabaseContract.LembreteEntry.TABLE_NAME 
					+ " WHERE DATE(" + DatabaseContract.LembreteEntry.COLUMN_DT_LEMBRETE + "/1000, 'unixepoch') = DATE(" + String.valueOf(timestamp) + "/1000, 'unixepoch')";

			this.c = this.db.rawQuery(sql, null);
			if (this.c.getCount() > 0) {
				this.c.moveToFirst();
				item = this.fillLembrete();
			}
			return item;
		} catch (Exception e) {
			e.printStackTrace();
			return item;
		} finally {
			this.closeDatabase();
		}
	}

	public long insertLembrete(LembreteItem item) {
		this.openWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put(DatabaseContract.LembreteEntry.COLUMN_ID_PONTO, item.getCodigoPonto());
			values.put(DatabaseContract.LembreteEntry.COLUMN_DT_LEMBRETE, item.getDataLembrete());
			return this.db.insert(DatabaseContract.LembreteEntry.TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			this.closeDatabase();
		}
	}

	public int updateLembrete(LembreteItem item) {
		this.openWritableDatabase();
		try {
			String whereClause = DatabaseContract.LembreteEntry._ID + " = ?";
			String[] whereArgs = new String[]{String.valueOf(item.getId())};
			ContentValues values = new ContentValues();
			values.put(DatabaseContract.LembreteEntry.COLUMN_ID_PONTO, item.getCodigoPonto());
			values.put(DatabaseContract.LembreteEntry.COLUMN_DT_LEMBRETE, item.getDataLembrete());
			return this.db.update(DatabaseContract.LembreteEntry.TABLE_NAME, values, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			this.closeDatabase();
		}
	}

	public int deleteLembrete(int id) {
		this.openWritableDatabase();
		try {
			String whereClause = DatabaseContract.LembreteEntry._ID + " = ?";
			String[] whereArgs = new String[]{String.valueOf(id)};
			return this.db.delete(DatabaseContract.LembreteEntry.TABLE_NAME, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			this.closeDatabase();
		}
	}

	private LembreteItem fillLembrete() {
		LembreteItem item = new LembreteItem();
		item.setId(this.c.getInt(this.c.getColumnIndex(DatabaseContract.LembreteEntry._ID)));
		item.setCodigoPonto(this.c.getInt(this.c.getColumnIndex(DatabaseContract.LembreteEntry.COLUMN_ID_PONTO)));
		item.setDataLembrete(this.c.getLong(this.c.getColumnIndex(DatabaseContract.LembreteEntry.COLUMN_DT_LEMBRETE)));
		return item;
	}
	
}
