package br.com.controlepontosimples.lembrete;

import java.util.List;

import br.com.controlepontosimples.bll.LembreteBLL;
import br.com.controlepontosimples.model.LembreteItem;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OnBootReceiver extends BroadcastReceiver {

	private static final String TAG = OnBootReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "Adding alarm from boot.");
		ReminderManager reminderMgr = new ReminderManager(context);

		LembreteBLL bllLembrete = new LembreteBLL(context);
		List<LembreteItem> lstLembretes = bllLembrete.getLembretes();

		for (LembreteItem lembreteItem : lstLembretes) {
			reminderMgr.setReminder(lembreteItem.getId(), lembreteItem.getDataLembrete());
			Log.d(TAG, "id - " + lembreteItem.getId());
		}
	}
}
