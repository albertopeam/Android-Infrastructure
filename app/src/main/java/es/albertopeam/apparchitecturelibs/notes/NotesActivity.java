package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.LifecycleActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import es.albertopeam.apparchitecturelibs.R;

public class NotesActivity
        extends LifecycleActivity
        implements NotesView,
                View.OnClickListener {


    private EditText noteET;
    private RecyclerView recyclerView;
    private NotesPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        setup();
        presenter = makePresenter();
    }


    protected NotesPresenter makePresenter(){
        return NotesViewFactory.provide(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadNotes();
    }


    @Override
    public void onLoadedNotes(List<String> notes) {
        NotesAdapter notesAdapter = (NotesAdapter) recyclerView.getAdapter();
        notesAdapter.setNotes(notes);
    }


    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRemovedNote(String note) {
        NotesAdapter adapter = (NotesAdapter) recyclerView.getAdapter();
        adapter.removeNote(note);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addbutton:
                presenter.addNote(noteET.getText().toString());
                break;
            case R.id.removebutton:
                presenter.removeNote((String) v.getTag());
                break;
        }
    }


    private void setup(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NotesAdapter(this));
        findViewById(R.id.addbutton).setOnClickListener(this);
        noteET = (EditText) findViewById(R.id.editText);
    }
}
