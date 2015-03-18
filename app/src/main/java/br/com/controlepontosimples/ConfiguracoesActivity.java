package br.com.controlepontosimples;

import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Created by Elifazio on 08/03/15.
 */
public class ConfiguracoesActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_PREF_HORA_INICIO_ALMOCO = "pref_key_hora_inicio_almoco";
    public static final String KEY_PREF_INTERVALO_ALMOCO = "pref_key_intervalo_almoco";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.layout.activity_configuracoes);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_PREF_HORA_INICIO_ALMOCO)) {
            Preference timePickerPref = this.findPreference(key);
            timePickerPref.setSummary(sharedPreferences.getString(KEY_PREF_HORA_INICIO_ALMOCO, this.getResources().getString(R.string.pref_hora_inicio_almoco_default_value)));
        } else if (key.equals(KEY_PREF_INTERVALO_ALMOCO)) {
            Preference timePickerPref = this.findPreference(key);
            timePickerPref.setSummary(this.getResources().getStringArray(R.array.pref_intevalo_almoco_list_titles)[Integer.valueOf(sharedPreferences.getString(KEY_PREF_INTERVALO_ALMOCO, "1"))-1]);
        }
        // ringtone
        // For ringtone preferences, look up the correct display value
        // using RingtoneManager.
//        if (TextUtils.isEmpty(stringValue)) {
//            // Empty values correspond to 'silent' (no ringtone).
//            preference.setSummary(R.string.pref_ringtone_silent);
//
//        } else {
//            Ringtone ringtone = RingtoneManager.getRingtone(
//                    preference.getContext(), Uri.parse(stringValue));
//
//            if (ringtone == null) {
//                // Clear the summary if there was a lookup error.
//                preference.setSummary(null);
//            } else {
//                // Set the summary to reflect the new ringtone display
//                // name.
//                String name = ringtone.getTitle(preference.getContext());
//                preference.setSummary(name);
//            }
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        this.onSharedPreferenceChanged(this.getPreferenceScreen().getSharedPreferences(), KEY_PREF_HORA_INICIO_ALMOCO);
        this.onSharedPreferenceChanged(this.getPreferenceScreen().getSharedPreferences(), KEY_PREF_INTERVALO_ALMOCO);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
