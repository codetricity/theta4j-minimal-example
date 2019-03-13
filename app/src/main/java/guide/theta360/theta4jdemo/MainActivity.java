package guide.theta360.theta4jdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.theta360.pluginlibrary.activity.PluginActivity;
import com.theta360.pluginlibrary.callback.KeyCallback;
import com.theta360.pluginlibrary.receiver.KeyReceiver;
import com.theta360.pluginlibrary.values.LedColor;

import org.theta4j.webapi.Theta;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static com.theta360.pluginlibrary.values.LedTarget.LED3;

public class MainActivity extends PluginActivity {

    private static final String TAG = "THETA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAutoClose(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setKeyCallback(new ThetaButtonCallback());

        if (isApConnected()) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        setKeyCallback(null);

    }

    public class ThetaButtonCallback implements KeyCallback {
        private ExecutorService executorService = Executors.newSingleThreadExecutor();

        private int pictureCounter = 0;
        private final int maxPicture = 3;
        final Theta theta = Theta.createForPlugin();



        @Override
        public void onKeyDown(int keyCode, KeyEvent keyEvent) {
            notificationAudioSelf();
            notificationLed3Show(LedColor.MAGENTA);

            if (keyCode == KeyReceiver.KEYCODE_CAMERA) {
                executorService.submit(() -> {
                    while (pictureCounter < maxPicture) {
                        try {

                            Log.d(TAG, "take picture " + pictureCounter);
                            theta.takePicture();
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        pictureCounter = pictureCounter + 1;

                    }
                    Log.d(TAG, "picture taking done");
                    notificationLedHide(LED3);
                    pictureCounter = 0;
                    notificationAudioSelf();
                });


            }

        }

        @Override
        public void onKeyUp(int keyCode, KeyEvent keyEvent) {

        }

        @Override
        public void onKeyLongPress(int keyCode, KeyEvent keyEvent) {

        }
    }

}
