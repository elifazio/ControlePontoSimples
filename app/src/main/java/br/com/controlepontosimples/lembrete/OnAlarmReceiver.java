package br.com.controlepontosimples.lembrete;

import br.com.controlepontosimples.dal.DatabaseContract.LembreteEntry;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnAlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		int id = intent.getExtras().getInt(LembreteEntry._ID);
		WakeReminderIntentService.acquireStaticLock(context);
		Intent i = new Intent(context, ReminderService.class);
		i.putExtra(LembreteEntry._ID, id);
		context.startService(i);
	}

}
