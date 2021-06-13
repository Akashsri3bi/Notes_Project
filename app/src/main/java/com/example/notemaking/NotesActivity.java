package com.example.notemaking;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

    }

    public void Add(View view){
        EditText title = findViewById(R.id.notes_title) ;
        EditText description = findViewById(R.id.notes_description) ;

        NotesDetails notesDetails = new NotesDetails(this) ;
        //Todo get A Nice date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd/MM/yyyy hh:mm:ss");
        String date = simpleDateFormat.format(new Date()) ;
        boolean result = notesDetails.insertuserData(title.getText().toString() , description.getText().toString() , date) ;
        if(!result){
            notesDetails.UpdateuserData(title.getText().toString() , description.getText().toString() , date) ;
            Toast.makeText(this , "Updated" , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this , "New Added" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String position = intent.getStringExtra(Intent.EXTRA_TEXT);
        if(position!=null) {
            int pos = Integer.parseInt(position);
            NotesDetails notesDetails = new NotesDetails(this);
            Cursor cursor = notesDetails.getData();
            List<String> titles = new ArrayList<>();
            List<String> descriptions = new ArrayList<>();

            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    titles.add(cursor.getString(0));
                    descriptions.add(cursor.getString(1));
                }
                EditText title = findViewById(R.id.notes_title);
                EditText description = findViewById(R.id.notes_description);
                title.setText(titles.get(pos));
                description.setText(descriptions.get(pos));
            }
            notesDetails.close();
            cursor.close();
        }
    }
}