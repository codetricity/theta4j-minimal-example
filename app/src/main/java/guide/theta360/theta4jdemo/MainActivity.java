package guide.theta360.theta4jdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.theta360.pluginlibrary.activity.PluginActivity;
import com.theta360.pluginlibrary.callback.KeyCallback;
import com.theta360.pluginlibrary.receiver.KeyReceiver;

import org.theta4j.webapi.Theta;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends PluginActivity {

    final Theta theta = Theta.createForPlugin();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private KeyCallback keyCallback = new KeyCallback() {
        @Override
        public void onKeyDown(int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyReceiver.KEYCODE_CAMERA) {
                // use lambda expression with Java 1.8
                executor.submit(() -> {

                    Log.d("THETA", "take picture");

                    try {
                        theta.takePicture();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            }

        }

        @Override
        public void onKeyUp(int keyCode, KeyEvent keyEvent) {

        }

        @Override
        public void onKeyLongPress(int keyCode, KeyEvent keyEvent) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAutoClose(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setKeyCallback(keyCallback);

        if (isApConnected()) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
