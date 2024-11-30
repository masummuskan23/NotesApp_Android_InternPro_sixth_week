package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private ListView notesListView;
    private ArrayList<String> notes;
    private ArrayAdapter<String> notesAdapter;
    private SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesListView = findViewById(R.id.notesListView);
        db = new SQLiteHelper(this);

        notes = db.getAllNotes();
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        notesListView.setAdapter(notesAdapter);

        findViewById(R.id.addNoteButton).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
            startActivity(intent);
        });
        notesListView.setOnItemClickListener((AdapterView<?> adapterView, android.view.View view, int position, long id) -> {
            Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
            int noteId = db.getNoteIdByPosition(position); // Get note ID from the database
            intent.putExtra("noteId", noteId); // Pass ID
            intent.putExtra("note", notes.get(position)); // Pass note text
            startActivity(intent);
        });

        // Delete a note on long press
        notesListView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            int noteId = db.getNoteIdByPosition(position); // Get note ID
            db.deleteNoteById(noteId); // Delete from database
            notes.remove(position); // Remove from list
            notesAdapter.notifyDataSetChanged(); // Refresh UI
            return true;
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        notes.clear();
        notes.addAll(db.getAllNotes());
        notesAdapter.notifyDataSetChanged();
    }
}
