package guide.theta360.theta4jdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.theta360.pluginlibrary.activity.PluginActivity;
import com.theta360.pluginlibrary.callback.KeyCallback;
import com.theta360.pluginlibrary.receiver.KeyReceiver;

import org.theta4j.webapi.ISOSpeed;
import org.theta4j.webapi.Theta;
import org.theta4j.webapi.Options.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.theta4j.webapi.Options.ISO;

public class MainActivity extends PluginActivity {

    final Theta theta = Theta.createForPlugin();
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
                final ISOSpeed iso = theta.getOption(ISO);
                System.out.println("ISO Speed: " + iso);
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
    }

}
