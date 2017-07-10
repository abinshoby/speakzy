package com.eazico.speakzy;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.eazico.speakzy.MainActivity.in;

public class dialog2 extends Activity {
    EditText filename;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog2);
        filename = (EditText) findViewById(R.id.filename);
    }

    public void canceloret(View view) {

        startActivity(new Intent(this, com.eazico.speakzy.MainActivity.class));
    }

    public void savedialog(View view) {

        File file;
        FileOutputStream outputStream;
        if (filename.getText().toString().trim().length()==0)
            Toast.makeText(getApplicationContext(), "File name should not be empty!", Toast.LENGTH_SHORT).show();

        else {
            Toast.makeText(getApplicationContext(), "saving", Toast.LENGTH_SHORT).show();
            try {
                file = new File(Environment.getExternalStorageDirectory(), filename.getText() + ".txt");

                outputStream = new FileOutputStream(file);
                outputStream.write((in.getText().toString()).getBytes());

                outputStream.close();
                Toast.makeText(getApplicationContext(), "file  saved sucessfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "file not saved", Toast.LENGTH_SHORT).show();
            }
            canceloret(findViewById(R.id.cancel));

        }


    }
}
