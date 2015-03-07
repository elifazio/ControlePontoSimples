package br.com.controlepontosimples.dal;

import android.provider.BaseColumns;

public class DatabaseContract {

	public static final int DATABASE_VERSION = 2;
	public static final String DATABASE_NAME = "controlepontosimples.db";

	public static interface IAbstractEntry extends BaseColumns {

	}

	public static class JornadaEntry implements IAbstractEntry {
		public static final String TABLE_NAME = "tb_jornada";
		public static final String COLUMN_DT_JORNADA = "dt_jornada";

		public static String[] getColumns() {
			return new String[]{_ID, COLUMN_DT_JORNADA};
		}

		public static String getSqlCreateTable() {
			return "create table " + TABLE_NAME + " (" + _ID + " integer primary key autoincrement, "
					+ COLUMN_DT_JORNADA + " integer not null);";
		}
	}

	public static class PontoEntry implements IAbstractEntry {
		public static final String TABLE_NAME = "tb_ponto";
		public static final String COLUMN_ID_JORNADA = "id_jornada";
		public static final String COLUMN_DT_PONTO_INICIO = "dt_ponto_inicio";
		public static final String COLUMN_DT_PONTO_FIM = "dt_ponto_fim";

		public static String[] getColumns() {
			return new String[]{_ID, COLUMN_ID_JORNADA, COLUMN_DT_PONTO_INICIO, COLUMN_DT_PONTO_FIM};
		}

		public static String getSqlCreateTable() {
			return "create table " + TABLE_NAME + " (" + _ID + " integer primary key autoincrement, "
					+ COLUMN_ID_JORNADA + " integer not null," + COLUMN_DT_PONTO_INICIO + " integer not null,"
					+ COLUMN_DT_PONTO_FIM + " integer null);";
		}
	}

	public static class LembreteEntry implements IAbstractEntry {
		public static final String TABLE_NAME = "tb_lembrete";
		public static final String COLUMN_ID_PONTO = "id_ponto";
		public static final String COLUMN_DT_LEMBRETE = "dt_lembrete";

		public static String[] getColumns() {
			return new String[]{_ID, COLUMN_ID_PONTO, COLUMN_DT_LEMBRETE};
		}

		public static String getSqlCreateTable() {
			return "create table " + TABLE_NAME + " (" + _ID + " integer primary key autoincrement, "
					+ COLUMN_ID_PONTO + " integer not null,"
					+ COLUMN_DT_LEMBRETE + " integer not null);";
		}
	}

}
