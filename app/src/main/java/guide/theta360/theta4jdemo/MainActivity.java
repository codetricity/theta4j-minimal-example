package guide.theta360.theta4jdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.theta360.pluginlibrary.activity.PluginActivity;
import com.theta360.pluginlibrary.callback.KeyCallback;
import com.theta360.pluginlibrary.receiver.KeyReceiver;

import org.theta4j.webapi.CaptureMode;
import org.theta4j.webapi.ExposureCompensation;
import org.theta4j.webapi.ISOSpeed;
import org.theta4j.webapi.Theta;
import org.theta4j.webapi.Options.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.theta4j.webapi.ISOSpeed._200;
import static org.theta4j.webapi.Options.CAPTURE_MODE;
import static org.theta4j.webapi.Options.EXPOSURE_COMPENSATION;
import static org.theta4j.webapi.Options.ISO;

public class MainActivity extends PluginActivity {

    final Theta theta = Theta.createForPlugin();
    final String TAG = "THETA";
    private ThetaButtonCallback keyCallback = new ThetaButtonCallback();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

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

        executor.submit(()-> {
            try {
                theta.setOption(CAPTURE_MODE, CaptureMode.VIDEO);
                CaptureMode captureMode = theta.getOption(CAPTURE_MODE);
                Log.d(TAG, captureMode.toString());

                theta.setOption(EXPOSURE_COMPENSATION, ExposureCompensation.MINUS_1_0);
                ExposureCompensation exposureCompensation = theta.getOption(EXPOSURE_COMPENSATION);
                Log.d(TAG, exposureCompensation.toString());

//                theta.setOption(ISO, _200);



                ISOSpeed iso = theta.getOption(ISO);
                String isoString = iso.toString();
                Log.d(TAG, "ISO Speed: " + isoString);
//                Log.d(TAG, "ISO Speed Changed: " + theta.getOption(ISO).toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        if (isApConnected()) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        setKeyCallback(null);
    }

}
