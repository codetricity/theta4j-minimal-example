package guide.theta360.theta4jdemo;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.theta360.pluginlibrary.activity.PluginActivity;
import com.theta360.pluginlibrary.callback.KeyCallback;
import com.theta360.pluginlibrary.receiver.KeyReceiver;

import org.theta4j.osc.CommandResponse;
import org.theta4j.osc.CommandState;
import org.theta4j.webapi.TakePicture;
import org.theta4j.webapi.Theta;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends PluginActivity  {

    final Theta theta = Theta.createForPlugin();
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    ImageView imageView;
    URL url;

    private KeyCallback keyCallback = new KeyCallback() {
        @Override
        public void onKeyDown(int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyReceiver.KEYCODE_CAMERA) {
                // use lambda expression with Java 1.8
                executor.submit(() -> {
                    CommandResponse<TakePicture.Result> response = null;
                    try {
                        response = theta.takePicture();
                        Log.d("THETA", "take picture");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    while (response.getState() != CommandState.DONE) {
                        try {
                            response = theta.commandStatus(response);

                            Thread.sleep(100);


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    url = response.getResult().getFileUrl();
                    Log.d("THETA", "fileUrl: " + url);
                });
            }
        }

        @Override
        public void onKeyUp(int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyReceiver.KEYCODE_WLAN_ON_OFF) {
                if (url != null){
                    // test to view image
                    showImage(url);
                }
                Toast.makeText(MainActivity.this,
                        url.toString(),
                        Toast.LENGTH_LONG).show();

                getImagePath(url);

            }
        }

        @Override
        public void onKeyLongPress(int keyCode, KeyEvent keyEvent) {

        }
    };

    public void showImage(URL url) {
        Glide.with(MainActivity.this)
                    .load(url).into(imageView);

    }
    public String getImagePath(URL url) {
        String[] parts = url.toString().split("/");
        int length = parts.length;
        String filepath = Environment.getExternalStorageDirectory().getPath() +
                "/DCIM/100RICOH/" +
                parts[length -2] + "/" +
                parts[length - 1];
        Log.d("THETA", filepath);
        Toast.makeText(MainActivity.this,
                "image path: " + filepath, Toast.LENGTH_LONG)
                .show();
        return filepath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView =  findViewById(R.id.imageViewId);
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
