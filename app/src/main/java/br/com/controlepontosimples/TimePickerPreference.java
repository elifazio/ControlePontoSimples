package br.com.controlepontosimples;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

/**
 * Classe para implementar uma janela customizada no tela de configurações para escolher uma hora no formato hh:mm
 * <p/>
 * Created by Elifazio on 08/03/15.
 */
public class TimePickerPreference extends DialogPreference {

    private final String DEFAULT_VALUE = "00:00";
    private String mNovoValor;
    private String mValorAtual;
    private TimePicker mTimePicker;

    public TimePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setPositiveButtonText(R.string.texto_botao_ok);
        this.setNegativeButtonText(R.string.texto_botao_cancelar);
    }

    public int getHora(String tempo) {
        String[] partes = tempo.split(":");

        return (Integer.parseInt(partes[0]));
    }

    public int getMinuto(String tempo) {
        String[] partes = tempo.split(":");

        return (Integer.parseInt(partes[1]));
    }


    @Override
    protected View onCreateDialogView() {
        mTimePicker = new TimePicker(this.getContext());
        mTimePicker.setIs24HourView(true);
        return mTimePicker;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        mTimePicker.setCurrentHour(this.getHora(mValorAtual));
        mTimePicker.setCurrentMinute(this.getMinuto(mValorAtual));
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            if (defaultValue == null) {
                mValorAtual = this.getPersistedString(DEFAULT_VALUE);
            } else {
                mValorAtual = this.getPersistedString(defaultValue.toString());
            }
        } else {
            mValorAtual = defaultValue.toString();
            this.persistString(mValorAtual);
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            mNovoValor = String.format("%02d:%02d", mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute());
            this.persistString(mNovoValor);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        String valorPadrao = a.getString(index);
        if (valorPadrao == null) {
            valorPadrao = DEFAULT_VALUE;
        }
        return valorPadrao;
    }
}
