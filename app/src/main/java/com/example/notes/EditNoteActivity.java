package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;

public class EditNoteActivity extends AppCompatActivity {
    private EditText noteEditText;
    private Button saveButton;
    private SQLiteHelper db;
    private int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        noteEditText = findViewById(R.id.noteEditText);
        saveButton = findViewById(R.id.saveNoteButton);
        db = new SQLiteHelper(this);

        Intent intent = getIntent();
        String note = intent.getStringExtra("note");
        noteId = intent.getIntExtra("noteId", -1);
       // int position = intent.getIntExtra("position", -1);

        if (note != null) {
            noteEditText.setText(note);
        }

        saveButton.setOnClickListener(view -> {
            String updatedNote = noteEditText.getText().toString();
            if (noteId == -1) {
                db.addNote(updatedNote);
            } else {
                db.updateNoteById(noteId, updatedNote);
            }
            finish();
        });
    }
}
