package guide.theta360.theta4jdemo;

import android.util.Log;
import android.view.KeyEvent;

import com.theta360.pluginlibrary.callback.KeyCallback;
import com.theta360.pluginlibrary.receiver.KeyReceiver;

import org.theta4j.webapi.Theta;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThetaButtonCallback implements KeyCallback {

    final Theta theta = Theta.createForPlugin();
    private ExecutorService executor = Executors.newSingleThreadExecutor();


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
}
