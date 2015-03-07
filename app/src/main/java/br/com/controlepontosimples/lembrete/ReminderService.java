package br.com.controlepontosimples.lembrete;

import br.com.controlepontosimples.MainActivity;
import br.com.controlepontosimples.R;
import br.com.controlepontosimples.bll.LembreteBLL;
import br.com.controlepontosimples.dal.DatabaseContract.LembreteEntry;
import br.com.controlepontosimples.model.LembreteItem;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

public class ReminderService extends WakeReminderIntentService {

	public ReminderService() {
		super(ReminderService.class.getSimpleName());
	}

	@Override
	void doReminderWork(Intent intent) {
		Integer id = intent.getExtras().getInt(LembreteEntry._ID);
		LembreteBLL bllLembrete = new LembreteBLL(getBaseContext());
		LembreteItem item = bllLembrete.getLembrete(id);
		// Status bar notification Code Goes here.

		NotificationManager mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Intent notificationIntent = new Intent(this, MainActivity.class);
		// notificationIntent.putExtra(LembreteEntry._ID, id);

		PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

		Notification note = new NotificationCompat.Builder(getBaseContext())
				.setContentTitle(this.getText(R.string.app_name))
				.setContentText(
						String.format(this.getText(R.string.msg_lembrete_notificacao).toString(),
								item.getDataLembrete())).setSmallIcon(R.drawable.ic_action_clock)
				// .setLights(Notification.FLAG_SHOW_LIGHTS, onMs, offMs)
				// .setLargeIcon(R.drawable.ic_action_clock)
				.build();

		note.flags |= Notification.FLAG_AUTO_CANCEL;
		note.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS; 
		note.ledARGB = Color.YELLOW;
		note.ledOnMS = 500;
		note.ledOffMS = 500;
		note.flags |= Notification.FLAG_SHOW_LIGHTS;

		mgr.notify(id, note);
		bllLembrete.deleteLembrete(id);
	}
}
