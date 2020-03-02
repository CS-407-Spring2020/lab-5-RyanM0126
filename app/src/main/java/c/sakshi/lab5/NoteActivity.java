package c.sakshi.lab5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    int noteid = -1;
    EditText editNote;
    String content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("made it to note activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        editNote = findViewById(R.id.editNote);
        Intent editIntent = getIntent();
        noteid = editIntent.getIntExtra("noteid", -1);

        if (noteid != -1) { // updating note
            Note note = SecondActivity.notes.get(noteid);
            String noteContent = note.getContent();
            editNote.setText(noteContent);
        }
    }

    public void save(View view) {
        editNote = findViewById(R.id.editNote);
        content = editNote.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) { // add note
            title = "NOTE_" + (SecondActivity.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else { // update note
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }

        Intent homeIntent = new Intent(this, SecondActivity.class);
        startActivity(homeIntent);
    }
}
