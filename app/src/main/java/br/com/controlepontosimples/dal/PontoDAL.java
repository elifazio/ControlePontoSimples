package br.com.controlepontosimples.dal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import br.com.controlepontosimples.model.JornadaItem;
import br.com.controlepontosimples.model.PontoItem;

public class PontoDAL extends AbstractDAL {

	public PontoDAL(Context context) {
		super(context);
	}

	public List<PontoItem> getPontos() {
		this.openReadableDatabase();
		List<PontoItem> lst = new ArrayList<PontoItem>();
		try {

			this.c = this.db.query(DatabaseContract.PontoEntry.TABLE_NAME, DatabaseContract.PontoEntry.getColumns(),
					null, null, null, null, null);
			if (this.c.getCount() > 0) {
				this.c.moveToFirst();
				do {
					lst.add(this.fillPonto());
				} while (this.c.moveToNext());
			}
		} catch (Exception e) {
			return null;
		} finally {
			this.closeDatabase();
		}
		return lst;
	}

	public List<PontoItem> getPontosByCodigoJornada(int codigoJornada) {
		this.openReadableDatabase();
		List<PontoItem> lst = new ArrayList<PontoItem>();
		try {
			String selection = DatabaseContract.PontoEntry.COLUMN_ID_JORNADA + " = ?";
			String[] selectionArgs = new String[]{String.valueOf(codigoJornada)};
			String sortOrder = DatabaseContract.PontoEntry.COLUMN_DT_PONTO_INICIO + " ASC";
			this.c = this.db.query(DatabaseContract.PontoEntry.TABLE_NAME, DatabaseContract.PontoEntry.getColumns(),
					selection, selectionArgs, null, null, sortOrder);
			if (this.c.getCount() > 0) {
				this.c.moveToFirst();
				do {
					lst.add(this.fillPonto());
				} while (this.c.moveToNext());
			}
		} catch (Exception e) {
			return null;
		} finally {
			this.closeDatabase();
		}
		return lst;
	}

	public PontoItem getPonto(int id) {
		this.openReadableDatabase();
		PontoItem item = new PontoItem();
		try {
			String selection = DatabaseContract.PontoEntry._ID + " = ?";
			String[] selectionArgs = new String[]{String.valueOf(id)};
			this.c = this.db.query(DatabaseContract.PontoEntry.TABLE_NAME, DatabaseContract.PontoEntry.getColumns(),
					selection, selectionArgs, null, null, null);
			if (this.c.getCount() > 0) {
				this.c.moveToFirst();
				item = this.fillPonto();
			}
			return item;
		} catch (Exception e) {
			e.printStackTrace();
			return item;
		} finally {
			this.closeDatabase();
		}
	}

	public long insertPonto(PontoItem item) {
		this.openWritableDatabase();
		try {
			ContentValues values = new ContentValues();
			values.put(DatabaseContract.PontoEntry.COLUMN_ID_JORNADA, item.getCodigoJornada());
			values.put(DatabaseContract.PontoEntry.COLUMN_DT_PONTO_INICIO, item.getDataPontoInicio());
			if (item.getDataPontoFim() != null) {
				values.put(DatabaseContract.PontoEntry.COLUMN_DT_PONTO_FIM, item.getDataPontoFim());
			}
			return this.db.insert(DatabaseContract.PontoEntry.TABLE_NAME, null, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			this.closeDatabase();
		}
	}

	public int updatePonto(PontoItem item) {
		this.openWritableDatabase();
		try {
			String whereClause = DatabaseContract.PontoEntry._ID + " = ?";
			String[] whereArgs = new String[]{String.valueOf(item.getId())};
			ContentValues values = new ContentValues();
			values.put(DatabaseContract.PontoEntry.COLUMN_ID_JORNADA, item.getCodigoJornada());
			values.put(DatabaseContract.PontoEntry.COLUMN_DT_PONTO_INICIO, item.getDataPontoInicio());
			values.put(DatabaseContract.PontoEntry.COLUMN_DT_PONTO_FIM, item.getDataPontoFim());

			return this.db.update(DatabaseContract.PontoEntry.TABLE_NAME, values, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			this.closeDatabase();
		}
	}

	public int deletePonto(int id) {
		this.openWritableDatabase();
		try {
			String whereClause = DatabaseContract.PontoEntry._ID + " = ?";
			String[] whereArgs = new String[]{String.valueOf(id)};
			return this.db.delete(DatabaseContract.PontoEntry.TABLE_NAME, whereClause, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			this.closeDatabase();
		}
	}

	private PontoItem fillPonto() {
		PontoItem item = new PontoItem();
		item.setId(this.c.getInt(this.c.getColumnIndex(DatabaseContract.PontoEntry._ID)));
		item.setCodigoJornada(this.c.getInt(this.c.getColumnIndex(DatabaseContract.PontoEntry.COLUMN_ID_JORNADA)));
		item.setDataPontoInicio(this.c.getLong(this.c
				.getColumnIndex(DatabaseContract.PontoEntry.COLUMN_DT_PONTO_INICIO)));

		if (!this.c.isNull((this.c.getColumnIndex(DatabaseContract.PontoEntry.COLUMN_DT_PONTO_FIM)))) {

			item.setDataPontoFim(this.c.getLong(this.c.getColumnIndex(DatabaseContract.PontoEntry.COLUMN_DT_PONTO_FIM)));
		}
		//Se existe data fim, calcula a quantidade de horas
//		if(item.getDataPontoFim() != null){
//			item.setDataPontoFim(null);
//		}
		return item;
	}

}
