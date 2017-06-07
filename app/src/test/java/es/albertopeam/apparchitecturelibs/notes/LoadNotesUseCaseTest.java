package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.Lifecycle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import es.albertopeam.apparchitecturelibs.domain.NotesRepository;
import es.albertopeam.apparchitecturelibs.notes.LoadNotesUseCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

/**
 * Created by Al on 05/06/2017.
 */

public class LoadNotesUseCaseTest {


    private LoadNotesUseCase sut;
    private List<String> result;
    @Mock
    private NotesRepository notesRepositoryMock;
    @Mock
    private Lifecycle lifecycle;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        sut = new LoadNotesUseCase(lifecycle, notesRepositoryMock);
    }


    @Test
    public void givenValidNotesRepoWhenLoadNotesThenReturnNotes() throws Exception{
        givenValidRepo();
        whenLoadNotes();
        thenReturnNotes();
    }


    @Test(expected = Exception.class)
    public void givenInvalidNotesRepoWhenLoadNotesThenThrowException() throws Exception {
        givenInvalidRepo();
        whenLoadNotes();
    }


    private void givenValidRepo() throws Exception{
        when(notesRepositoryMock.loadNotes()).thenReturn(new ArrayList<String>());
    }


    private void whenLoadNotes() throws Exception{
        result = sut.run(null);
    }


    private void givenInvalidRepo() throws Exception{
        when(notesRepositoryMock.loadNotes()).thenThrow(new Exception());
    }


    private void thenReturnNotes() {
        assertThat(result, is(notNullValue()));
        assertThat(result.size(), equalTo(0));
    }
}
