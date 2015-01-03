package smczk.alarman.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.IBinder;
import android.widget.NumberPicker;

import java.util.Random;

import smczk.alarman.R;

public class AlarmService extends Service {

    private NumberPicker intervalMinutesPicker;
    private NumberPicker randomMinutesPicker;
    private SoundPool soundPool;
    private int soundId;
    private Random random;
    private Thread thread;
    private AudioManager audioManager;

    @Override
    public void onCreate() {
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final int intervalMinutes = intent.getIntExtra("INTERVAL", -1);
        final int randomMinutes = intent.getIntExtra("RANDOM", -1);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

                loopSoundTask(intervalMinutes, randomMinutes);
            }
        });

        soundId = soundPool.load(this, R.raw.okite_02, 1);

        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {

        thread.interrupt();
    }

    public void loopSoundTask(final int intervalMinutes, final int randomMinutes) {

        thread = new Thread(new Runnable() {

            @Override
            public void run() {

                int soundVol;
                while(true) {

                    try{

                        if(intervalMinutes != 0) {
                            Thread.sleep(intervalMinutes * 60000);
                        }

                        Thread.sleep(randomMinutes * 60000);
                        soundVol = audioManager.getStreamVolume(AudioManager.STREAM_RING);
                        soundPool.play(soundId, soundVol, soundVol, 0, 0, 1);

                    }catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        thread.start();
    }
}
