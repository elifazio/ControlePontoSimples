package br.com.controlepontosimples;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import android.app.AlertDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import br.com.controlepontosimples.bll.JornadaBLL;
import br.com.controlepontosimples.bll.LembreteBLL;
import br.com.controlepontosimples.bll.PontoBLL;
import br.com.controlepontosimples.model.JornadaItem;
import br.com.controlepontosimples.model.LembreteItem;
import br.com.controlepontosimples.model.PontoItem;

public class PontoHojeFragment extends Fragment implements OnTimeSetListener {

	private ListView lvPontoDia;
	private Typeface tf;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_ponto_hoje, container, false);

		// Font path
		String fontPath = "fonts/DroidSerif-BoldItalic.ttf";
		// Loading Font Face
		tf = Typeface.createFromAsset(this.getActivity().getAssets(), fontPath);

		TextView tvDataAtual = (TextView) rootView.findViewById(R.id.tvDataAtual);
		tvDataAtual.setText(new SimpleDateFormat("d 'de' MMMM 'de' yyyy").format(new Date()));
		// Applying font
		tvDataAtual.setTypeface(tf);

		this.lvPontoDia = (ListView) rootView.findViewById(R.id.lvPontoDia);

		PontoDiaAdapter adapter = new PontoDiaAdapter(this.getActivity(), listarPontos());

		lvPontoDia.setAdapter(adapter);
		lvPontoDia.setClickable(true);
		// lvPontoDia.setOnItemClickListener(new PontoDiaClickListener());

		return rootView;
	}

	private List<PontoItem> listarPontos() {
		JornadaBLL bllJornada = new JornadaBLL(this.getActivity());
		JornadaItem jornada = bllJornada.getJornada();
		PontoBLL bllPonto = new PontoBLL(this.getActivity());
		bllJornada.setEmptyNewPontoForJornada(jornada);
		return jornada.getPontos();
	}

	private class PontoDiaAdapter extends ArrayAdapter<PontoItem> {
		private final Context context;
		private final List<PontoItem> values;

		public PontoDiaAdapter(Context context, List<PontoItem> values) {
			super(context, R.layout.ponto_hoje_item, values);
			this.context = context;
			this.values = values;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.ponto_hoje_item, parent, false);

			// hora inicio
			TextView tv = (TextView) convertView.findViewById(R.id.tvHoraInicio);
			tv.setOnClickListener(new HoraPontoShortClickListener());
			tv.setOnLongClickListener(new HoraPontoLongClickListener());
			tv.setTag(position);
			tv.setTypeface(tf);
			if (this.values.get(position).getDataPontoInicio() == null) {
				tv.setText(R.string.hora_ponto_mascara);
			} else {
				tv.setText(new SimpleDateFormat(this.context.getString(R.string.hora_ponto_formato)).format(this.values
						.get(position).getDataPontoInicio()));
			}

			// hora fim
			tv = (TextView) convertView.findViewById(R.id.tvHoraFim);
			tv.setOnClickListener(new HoraPontoShortClickListener());
			tv.setOnLongClickListener(new HoraPontoLongClickListener());
			tv.setTag(position);
			tv.setTypeface(tf);
			if (this.values.get(position).getDataPontoFim() == null) {
				tv.setText(R.string.hora_ponto_mascara);
			} else {
				tv.setText(new SimpleDateFormat(this.context.getString(R.string.hora_ponto_formato)).format(this.values
						.get(position).getDataPontoFim()));
			}

			// hora total
			tv = (TextView) convertView.findViewById(R.id.tvHoraTotal);
			tv.setTypeface(tf);
			if (this.values.get(position).getDataPontoFim() != null) {
				Duration dur = new Duration(this.values.get(position).getDataPontoInicio(), this.values.get(position)
						.getDataPontoFim());
				tv.setText(String.format("%02d:%02d", dur.toPeriod().getHours(), dur.toPeriod().getMinutes()));
			} else {
				tv.setText(this.context.getString(R.string.hora_ponto_mascara));
			}

			return convertView;
		}

		public PontoItem getObjectDetails(int clickedPosition) {
			return this.values.get(clickedPosition);
		}
	}

	private class HoraPontoShortClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			int linha = Integer.valueOf(v.getTag().toString());
			PontoItem item = ((PontoDiaAdapter) lvPontoDia.getAdapter()).getObjectDetails(linha);

			if (v.getId() == R.id.tvHoraInicio) {
				// clicou no inicio
				if (item.getDataPontoInicio() == null) {
					// incluir novo
					item.setDataPontoInicio(new Date().getTime());
					savePontoItem(item);
				} else {
					// editar existente
					showTimePicker(linha, item.getDataPontoInicio(), TimePickerDialogFragment.TIPO_CAMPO_HORA_INICIO);
				}

			} else if (v.getId() == R.id.tvHoraFim) {
				if (item.getDataPontoInicio() != null) {
					// clicou no fim
					if (item.getDataPontoFim() == null) {
						// incluir novo
						item.setDataPontoFim(new Date().getTime());
						savePontoItem(item);
					} else {
						// editar existente
						showTimePicker(linha, item.getDataPontoFim(), TimePickerDialogFragment.TIPO_CAMPO_HORA_FIM);
					}
				} else {
					Toast.makeText(getActivity(), R.string.msg_definir_hora_inicio, Toast.LENGTH_LONG).show();
				}

			}
		}
	}

	private class HoraPontoLongClickListener implements OnLongClickListener {
		@Override
		public boolean onLongClick(View v) {
			int linha = Integer.valueOf(v.getTag().toString());
			PontoItem item = ((PontoDiaAdapter) lvPontoDia.getAdapter()).getObjectDetails(linha);

			if (v.getId() == R.id.tvHoraInicio) {
				// clicou no inicio
				if (item.getDataPontoInicio() != null) {
					PontoBLL bllPonto = new PontoBLL(getActivity());
					bllPonto.deletePonto(item);
					lvPontoDia.setAdapter(new PontoDiaAdapter(getActivity(), listarPontos()));
					return true;
				}

			} else if (v.getId() == R.id.tvHoraFim) {
				// clicou no fim
				if (item.getDataPontoFim() != null) {
					item.setDataPontoFim(null);
					savePontoItem(item);
					return true;
				}
			}
			return false;
		}

	}

	private void savePontoItem(final PontoItem item) {
		PontoBLL bllPonto = new PontoBLL(getActivity());
		bllPonto.savePonto(item);

		lvPontoDia.setAdapter(new PontoDiaAdapter(getActivity(), listarPontos()));
		// lvPontoDia.invalidate();
		// alert para notificacao
		AlertDialog janela = new AlertDialog.Builder(getActivity())
				.setMessage(this.getString(R.string.msg_deseja_incluir_notificacao))
				.setPositiveButton(R.string.texto_botao_sim, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						LembreteBLL bllLembrete = new LembreteBLL(getActivity());
						LembreteItem novoLembrete = new LembreteItem();
						novoLembrete.setCodigoPonto(item.getId());
						DateTime time;
						if (item.getDataPontoFim() != null) {
							time = new DateTime(item.getDataPontoFim());
						} else {
							time = new DateTime(item.getDataPontoInicio());
						}

						novoLembrete.setDataLembrete(time.plusHours(1).getMillis());
						//novoLembrete.setDataLembrete(time.plusMinutes(1).getMillis());
						bllLembrete.saveLembrete(novoLembrete);
					}
				}).setNegativeButton(R.string.texto_botao_nao, null).create();

		janela.show();
	}

	private void showTimePicker(int linha, long timestamp, int tipoCampoEdicao) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		TimePickerDialogFragment.newInstance(this, linha, timestamp, tipoCampoEdicao).show(ft,
				TimePickerDialogFragment.TAG);
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		Calendar cal = Calendar.getInstance();
		Bundle args = getFragmentManager().findFragmentByTag(TimePickerDialogFragment.TAG).getArguments();

		cal.setTimeInMillis(args.getLong(TimePickerDialogFragment.ARG_FIELD_HORA_MINUTO));
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);

		PontoItem item = ((PontoDiaAdapter) lvPontoDia.getAdapter()).getObjectDetails(args
				.getInt(TimePickerDialogFragment.ARG_FIELD_LINHA));

		if (args.getInt(TimePickerDialogFragment.ARG_FIELD_TIPO_CAMPO_EDICAO_HORA) == TimePickerDialogFragment.TIPO_CAMPO_HORA_INICIO) {
			item.setDataPontoInicio(cal.getTimeInMillis());
		} else if (args.getInt(TimePickerDialogFragment.ARG_FIELD_TIPO_CAMPO_EDICAO_HORA) == TimePickerDialogFragment.TIPO_CAMPO_HORA_FIM) {
			item.setDataPontoFim(cal.getTimeInMillis());
		}

		if (item.getDataPontoFim() != null && (item.getDataPontoFim() < item.getDataPontoInicio())) {
			Toast.makeText(getActivity(), R.string.msg_definir_hora_fim_menor_inicio, Toast.LENGTH_LONG).show();
		} else {
			this.savePontoItem(item);
		}
	}

}
