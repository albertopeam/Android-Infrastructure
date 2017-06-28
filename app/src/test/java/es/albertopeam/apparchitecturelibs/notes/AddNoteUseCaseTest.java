package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.Lifecycle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.albertopeam.apparchitecturelibs.domain.NotesRepository;
import es.albertopeam.apparchitecturelibs.notes.AddNoteUseCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doThrow;

/**
 * Created by Alberto Penas Amor on 06/06/2017.
 */

public class AddNoteUseCaseTest {


    private String result;
    private String note;
    private AddNoteUseCase sut;
    @Mock
    private NotesRepository notesRepositoryMock;
    @Mock
    private Lifecycle lifecycle;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        sut = new AddNoteUseCase(lifecycle, notesRepositoryMock);
    }


    @Test
    public void givenValidNoteWhenAddNoteThenReturnTheNote() throws Exception {
        givenValidNote();
        whenAddNote();
        thenNoteAdded();
    }



    @Test(expected = Exception.class)
    public void givenEmptyNoteWhenAddNoteThenThrowException() throws Exception {
        givenInvalidNote();
        whenAddNote();
    }


    private void givenValidNote() {
        note = "a note";
    }


    private void givenInvalidNote() {
        note = "";
    }


    private void whenAddNote() throws Exception {
        doThrow(new Exception()).when(notesRepositoryMock).addNote("");
        result = sut.run(note);
    }


    private void thenNoteAdded() {
        assertThat(result, equalTo("a note"));
    }
}
