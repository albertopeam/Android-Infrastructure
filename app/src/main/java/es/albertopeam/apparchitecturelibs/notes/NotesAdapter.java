package es.albertopeam.apparchitecturelibs.notes;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.albertopeam.apparchitecturelibs.R;

/**
 * Created by Al on 22/05/2017.
 */

class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {


    private List<String> notes = new ArrayList<>();


    void setNotes(List<String> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }


    void removeNote(String note) {
        notes.remove(note);
        notifyDataSetChanged();
    }


    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_note, parent, false);
        return new NoteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.fill(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    static class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView noteTV;

        NoteViewHolder(View itemView) {
            super(itemView);
            noteTV = (TextView) itemView.findViewById(R.id.notetv);
        }


        void fill(String note){
            noteTV.setText(note);
        }
    }
}
