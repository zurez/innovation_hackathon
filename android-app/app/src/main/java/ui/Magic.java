package ui;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ats.team.pehchaan.R;
import core.Student;

public class Magic extends Activity {

    ImageView logo;
    TextView status;
    private MediaRecorder recorder;
    Student student;
    int i = 0;
    String filename = Student.DIR;
    FrameLayout bg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic);

        initRecord();

        initView();
    }

    private void initView() {

        student = new Student(this);
        status = (TextView) findViewById(R.id.status);
        bg = (FrameLayout) findViewById(R.id.bg);

        logo = (ImageView) findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("" + i);
                if (i % 2 == 0) {
                    status.setText("Say \"Present Bot\", for me");
                    try {
                        initRecord();


                        recorder.prepare();
                    } catch (IOException e) {
                        Log.e("audio", e.toString());
                    }
                    recorder.start();

                } else {
                    status.setText("Trying to authenticate your voice...");
                    recorder.stop();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new MultipartBuilder().type(MultipartBuilder.FORM)
                            .addFormDataPart("file", filename, RequestBody.create(MediaType.parse("audio/3gp"), filename))
                            .build();
                    Log.e("run", "af");

                    Request request = new Request.Builder()
                            .url("http:/192.168.1.142:5000/api/audio")
                            .post(requestBody)
                            .build();

                    // TODO: Image compression
                    client.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onFailure(Request request, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    bg.setBackgroundColor(getResources().getColor(R.color.orange));
                                    status.setText("Could not conect to server.");
                                }
                            });

                            Log.e("f", "failed " + e.toString());
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String responseStr = response.body().string();
                            Log.e("or", responseStr);
                            try {

                                final JSONObject json = new JSONObject(responseStr);
                                final String msg = json.getString("Message");
                                if (json.getString("Status").equals("200")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            bg.setBackgroundColor(getResources().getColor(R.color.green));
                                            status.setText("Authentication successful\n" + msg);
                                            Log.e("run", msg);

                                        }
                                    });

                                } else
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            bg.setBackgroundColor(getResources().getColor(R.color.orange));
                                            status.setText("Authentication unsuccessful");
                                        }
                                    });

                            } catch (JSONException e) {
                                Log.e("Exc", e.toString());
                            }
                            Log.e("respo", responseStr);

                        }
                    });

                }
                i++;

            }


        });
    }

 /*   private bool authenticate() {
        Ion.with(getApplicationContext())
                .load("https://koush.clockworkmod.com/test/echo")
                .setMultipartFile("3gp", "audio/3gp", new File(filename))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("isMatched").equals("true"))
                            return true;
                    }
                });
    }*/

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    void initRecord() {

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        filename = Student.DIR + "/" + System.currentTimeMillis() + ".3gp";
        recorder.setOutputFile(filename);
        Log.e("ir", filename);

    }
}
