package  com.eazico.speakzy;

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.util.ArrayList;

        import android.content.Context;
        import android.os.Environment;
        import android.speech.RecognitionListener;
        import android.speech.RecognizerIntent;
        import android.speech.SpeechRecognizer;
        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.text.InputType;
        import android.util.Log;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.CompoundButton;
        import android.widget.CompoundButton.OnCheckedChangeListener;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.ToggleButton;

public class MainActivity extends Activity implements
        RecognitionListener {
    private EditText in;
    private TextView returnedText;
    private ToggleButton toggleButton;

    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        in = (EditText) findViewById(R.id.content);
        in.setSelection(0);
        in.setInputType(InputType.TYPE_NULL);
        returnedText = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
        toggleButton.setChecked(false);

        progressBar.setVisibility(View.INVISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);


        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    speech.startListening(recognizerIntent);
               } else {
                    progressBar.setIndeterminate(false);
                   progressBar.setVisibility(View.INVISIBLE);
                   speech.stopListening();
                }
            }
        });

    }
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (speech != null) {
            {  speech.destroy();

           }
            Log.i(LOG_TAG, "destroy");
        }

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");

        progressBar.setIndeterminate(true);
        if(toggleButton.isChecked())
               toggleButton.setChecked(false);

    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        returnedText.setText(errorMessage);
       toggleButton.setChecked(true);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
       Log.i(LOG_TAG, "onEvent");


    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
       Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
       // String text = "";

       // for (String result : matches)
         //text += result;


        in.append(matches.get(0));
        in.setSelection(in.getText().length());
        //speech.startListening(recognizerIntent);
       // onResume();
        toggleButton.setChecked(true);

    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);

    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    public void onClick(MenuItem item) {
        if(toggleButton.isChecked())
        {
            toggleButton.setChecked(true);
        }
        else
           toggleButton.setChecked(true);
        in.setText("");
    }
    public void onSave(MenuItem item){
        //getFilesDir();
        String filename="speakout.txt";
        Toast.makeText(getApplicationContext(),"saving",Toast.LENGTH_SHORT).show();
      //  FileOutputStream file= null;

      /*  try {
            //file = openFileOutput(Environment.getExternalStorageDirectory(), this.MODE_APPEND);
            String s=in.getText().toString();
            file.write(s.getBytes());
           file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

       // Context context=null;
       // assert context != null;
       // File file = new File(context.getFilesDir(), filename);
       // file.setWritable(true);
        //FileOutputStream outputStream;

        /*try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write((in.getText().toString()).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"file not saved",Toast.LENGTH_SHORT).show();

        }*/
      //  File[] fll = file.listFiles();

        //String content = "hello world";
        File file;
        FileOutputStream outputStream;
        try {
            file = new File(Environment.getExternalStorageDirectory(), "speakfiles1.txt");

            outputStream = new FileOutputStream(file);
            outputStream.write((in.getText().toString()).getBytes());
            outputStream.close();
            Toast.makeText(getApplicationContext(),"file  saved sucessfully",Toast.LENGTH_SHORT);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"file not saved",Toast.LENGTH_SHORT);
        }


    }


}