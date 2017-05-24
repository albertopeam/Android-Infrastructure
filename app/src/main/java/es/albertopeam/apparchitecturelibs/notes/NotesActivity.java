package es.albertopeam.apparchitecturelibs.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import es.albertopeam.apparchitecturelibs.R;

//TODO: revisar si setear al repo los datos del viewmodel en la construccion(factoría) es el lugar adecuado
//TODO: observe notes...pa ver como funciona el live data...abrir otra activity y modificar contenidos
//TODO: sync or lock in viewmodel
public class NotesActivity
        extends AppCompatActivity
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
        presenter = NotesFactory.provide(this);
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
    public void onClick(View v) {
        presenter.addNote(noteET.getText().toString());
    }


    private void setup(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NotesAdapter());
        findViewById(R.id.addbutton).setOnClickListener(this);
        noteET = (EditText) findViewById(R.id.editText);
    }
}
