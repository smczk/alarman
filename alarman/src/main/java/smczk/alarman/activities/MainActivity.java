package smczk.alarman.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;

import smczk.alarman.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NumberPicker intervalMinutesPicker;
        final NumberPicker randomMinutesPicker;

        intervalMinutesPicker = (NumberPicker)findViewById(R.id.numberPicker1);
        randomMinutesPicker = (NumberPicker)findViewById(R.id.numberPicker2);

        intervalMinutesPicker.setMinValue(0);
        intervalMinutesPicker.setMaxValue(20);
        randomMinutesPicker.setMinValue(0);
        randomMinutesPicker.setMaxValue(20);

        intervalMinutesPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                randomMinutesPicker.setMinValue(newVal);
                randomMinutesPicker.setMaxValue(20 + newVal);
            }
        });

        Switch serviceSwitch = (Switch) findViewById(R.id.switch1);
        serviceSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                NumberPicker intervalMinutesPicker = (NumberPicker)findViewById(R.id.numberPicker1);
                NumberPicker randomMinutesPicker = (NumberPicker)findViewById(R.id.numberPicker2);

                if(isChecked) {
                    intervalMinutesPicker.setEnabled(false);
                    randomMinutesPicker.setEnabled(false);
                }else{
                    intervalMinutesPicker.setEnabled(true);
                    randomMinutesPicker.setEnabled(true);
                }
            }
        });
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
