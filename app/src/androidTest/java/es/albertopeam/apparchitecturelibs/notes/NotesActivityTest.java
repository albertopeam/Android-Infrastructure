package es.albertopeam.apparchitecturelibs.notes;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.albertopeam.apparchitecturelibs.App;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCaseExecutor;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Al on 09/06/2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class NotesActivityTest {

/*
    @Rule
    public ActivityTestRule<NotesActivity> activityTestRule =
            new ActivityTestRule<>(NotesActivity.class, true, false);
    @Mock
    private UseCaseExecutor mockUseCaseExecutor;
    private NotesPresenter notesPresenter;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        notesPresenter = new NotesPresenter(
                activityTestRule.launchActivity(null),
                mock(NotesViewModel.class),
                mockUseCaseExecutor,
                mock(LoadNotesUseCase.class),
                mock(AddNoteUseCase.class),
                mock(RemoveNoteUseCase.class));
        Context context = InstrumentationRegistry.getTargetContext();
        App app = (App) context.getApplicationContext();
        Container mockContainer = mock(Container.class);
        when(mockContainer.provide(any(NotesActivity.class))).thenReturn(notesPresenter);
        app.setContainer(mockContainer);
    }


    @Test
    public void givenClearAppWhenLoadNotesThenEmpty(){
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                activityTestRule.getActivity().onLoadedNotes(new ArrayList<String>());
                return null;
            }
        }).when(notesPresenter).loadNotes();
        //onView(withId(R.id.recycler)).check(new RecyclerViewItemCountAssertion(0));
    }


    @Test
    public void givenNewNoteWhenClickAddThenIsAdded(){

    }


    @Test
    public void givenEmptyNoteWhenClickAddThenShowToast(){

    }
    */
}
