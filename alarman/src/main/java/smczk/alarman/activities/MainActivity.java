package smczk.alarman.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;

import smczk.alarman.R;
import smczk.alarman.services.AlarmService;


public class MainActivity extends ActionBarActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.activity_main);

        final NumberPicker intervalMinutesPicker  = (NumberPicker)findViewById(R.id.numberPicker1);;
        final NumberPicker randomMinutesPicker = (NumberPicker)findViewById(R.id.numberPicker2);;
        intent = new Intent(MainActivity.this, AlarmService.class);

        intervalMinutesPicker.setMinValue(0);
        intervalMinutesPicker.setMaxValue(20);
        randomMinutesPicker.setMinValue(1);
        randomMinutesPicker.setMaxValue(21);

        intervalMinutesPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                randomMinutesPicker.setMinValue(1 + newVal);
                randomMinutesPicker.setMaxValue(21 + newVal);
            }
        });

        Switch serviceSwitch = (Switch) findViewById(R.id.switch1);
        serviceSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    intervalMinutesPicker.setEnabled(false);
                    randomMinutesPicker.setEnabled(false);
                    intent.putExtra("INTERVAL", intervalMinutesPicker.getValue());
                    intent.putExtra("RANDOM", randomMinutesPicker.getValue());
                    startService(intent);
                }else{
                    intervalMinutesPicker.setEnabled(true);
                    randomMinutesPicker.setEnabled(true);
                    stopService(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {

        stopService(intent);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
