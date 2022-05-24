package com.example.speaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private Button speakb;
    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.editText);
        speakb = (Button) findViewById(R.id.button);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }
                    else {
                        Log.e("TTS", "Initilization Failed!");
                    }
                }
            }
        });

        speakb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void speak(){
        String data = input.getText().toString();

        tts.setPitch(1);
        tts.setSpeechRate(1);

        tts.speak(data,TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        // shutdown tts
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
