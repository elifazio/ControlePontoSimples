package br.com.controlepontosimples;

import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TimePickerDialogFragment extends DialogFragment {
	public static final String TAG = "TimePickerDialogFragment";
	public static final String ARG_FIELD_HORA_MINUTO = "timestamp";
	public static final String ARG_FIELD_LINHA = "linhadalista";
	public static final String ARG_FIELD_TIPO_CAMPO_EDICAO_HORA = "tipocampoedicaohora";
	public static final int TIPO_CAMPO_HORA_INICIO = 1;
	public static final int TIPO_CAMPO_HORA_FIM = 2;

	private static OnTimeSetListener listener;

	static TimePickerDialogFragment newInstance(OnTimeSetListener fragment, int linha, long timestamp,
			int tipoCampoEdicao) {
		listener = fragment;
		TimePickerDialogFragment newFragment = new TimePickerDialogFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_FIELD_LINHA, linha);
		args.putLong(ARG_FIELD_HORA_MINUTO, timestamp);
		args.putInt(ARG_FIELD_TIPO_CAMPO_EDICAO_HORA, tipoCampoEdicao);
		newFragment.setArguments(args);
		return newFragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle args = getArguments();
		Long ts = args.getLong(ARG_FIELD_HORA_MINUTO);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ts);
		return new TimePickerDialog(getActivity(), listener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true);
	}
}
