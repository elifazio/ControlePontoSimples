package br.com.controlepontosimples.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class AbstractDAL extends DatabaseHelper {

	protected SQLiteDatabase db;
	protected Cursor c;

	protected AbstractDAL(Context context) {
		super(context);
	}

	protected void openReadableDatabase() {
		if (this.db != null && this.db.isOpen() && this.db.isReadOnly()) {
			return;
		} else {
			this.closeDatabase();
		}
		this.db = this.getReadableDatabase();
	}
	
	protected void openWritableDatabase() {
		if (this.db != null && this.db.isOpen() && !this.db.isReadOnly()) {
			return;
		} else {
			this.closeDatabase();
		}
		this.db = this.getWritableDatabase();
	}

	protected void closeDatabase() {
		if (this.db != null && this.db.isOpen()) {
			this.closeCursor();
			this.db.close();
		}
	} 
	
	protected void closeCursor() {
		if(this.c != null && !this.c.isClosed()) {
			this.c.close();
		}
	}

}
