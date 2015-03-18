package br.com.controlepontosimples.bll;

import java.util.List;

import android.content.Context;
import android.preference.PreferenceManager;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.MutableDateTime;

import br.com.controlepontosimples.ConfiguracoesActivity;
import br.com.controlepontosimples.R;
import br.com.controlepontosimples.dal.LembreteDAL;
import br.com.controlepontosimples.lembrete.ReminderManager;
import br.com.controlepontosimples.model.LembreteItem;
import br.com.controlepontosimples.model.PontoItem;
import br.com.controlepontosimples.util.PontoUtil;

public class LembreteBLL {

	private Context ctx;
	private LembreteDAL dal;

	public LembreteBLL(Context context) {
		this.ctx = context;
		this.dal = new LembreteDAL(context);
	}

	public boolean saveLembrete(LembreteItem item) {
		if (this._saveLembrete(item)) {
			ReminderManager rm = new ReminderManager(this.ctx);
			rm.setReminder(item.getId(), item.getDataLembrete());
			return true;
		}
		return false;
	}

	private boolean _saveLembrete(LembreteItem item) {
		if (item.getId() > 0) {
			return this.dal.updateLembrete(item) > 0;
		} else {
			item.setId((int) this.dal.insertLembrete(item));
			return item.getId() > 0;
		}
	}

	public LembreteItem getLembrete(int id) {
		return this.dal.getLembrete(id);
	}

	public List<LembreteItem> getLembretes() {
		return this.dal.getLembretes();
	}

	public boolean deleteLembrete(PontoItem item) {
		if (item.getId() > 0) {
			return this.dal.deleteLembrete(item.getId()) > 0;
		}
		return false;
	}

	public boolean deleteLembrete(int id) {
		if (id > 0) {
			return this.dal.deleteLembrete(id) > 0;
		}
		return false;
	}

    /*
    Cria um lembrete para notificar o usuário para a volta do almoço. O tempo do intervalo usado será o parametrizado nas configurações
     */
    public boolean criarLembreteAlmoco(PontoItem ponto) {
        LembreteItem novoLembrete = new LembreteItem();
        novoLembrete.setCodigoPonto(ponto.getId());

        DateTime dtLembrete;
        if (ponto.getDataPontoFim() != null) {
            dtLembrete = new DateTime(ponto.getDataPontoFim());
        } else {
            dtLembrete = new DateTime(ponto.getDataPontoInicio());
        }

        int intervaloAlmoco = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this.ctx).getString(ConfiguracoesActivity.KEY_PREF_INTERVALO_ALMOCO, this.ctx.getResources().getString(R.string.pref_intervalo_almoco_default_value)));
        novoLembrete.setDataLembrete(dtLembrete.plusHours(intervaloAlmoco).getMillis());

        return this.saveLembrete(novoLembrete);
    }

    /*
    Verdadeiro se o ponto está no intervalo de almoço.
     */
    public boolean isIntervaloAlmoco(PontoItem ponto) {
        String horaMinutoInicioAlmoco = PreferenceManager.getDefaultSharedPreferences(this.ctx).getString(ConfiguracoesActivity.KEY_PREF_HORA_INICIO_ALMOCO, this.ctx.getResources().getString(R.string.pref_hora_inicio_almoco_default_value));

        LocalTime ltInicioAlmoco = PontoUtil.convertHoraMinutoJodaTime(horaMinutoInicioAlmoco);
        MutableDateTime mdtInicioAlmoco = new MutableDateTime();
        mdtInicioAlmoco.setHourOfDay(ltInicioAlmoco.getHourOfDay());
        mdtInicioAlmoco.setMinuteOfHour(ltInicioAlmoco.getMinuteOfHour());

        // remove alguns minutos do horário de início par aumentar a janela para detectar que está no intervalo de almoço
        mdtInicioAlmoco.addMinutes(-10);

        DateTime dtPontoFim = new DateTime(ponto.getDataPontoFim());

        int intervaloAlmoco = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this.ctx).getString(ConfiguracoesActivity.KEY_PREF_INTERVALO_ALMOCO, this.ctx.getResources().getString(R.string.pref_intervalo_almoco_default_value)));

        MutableDateTime dtPontoFimIntervaloAlmoco = new MutableDateTime(mdtInicioAlmoco);
        dtPontoFimIntervaloAlmoco.addHours(intervaloAlmoco);

        return dtPontoFim.isAfter(mdtInicioAlmoco) & dtPontoFim.isBefore(dtPontoFimIntervaloAlmoco);
    }

}
