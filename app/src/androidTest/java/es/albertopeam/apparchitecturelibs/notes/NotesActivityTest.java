package es.albertopeam.apparchitecturelibs.notes;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import es.albertopeam.apparchitecturelibs.R;
import es.albertopeam.apparchitecturelibs.di.EspressoDaggerMockRule;
import es.albertopeam.apparchitecturelibs.espresso.RecyclerViewAssertions;
import com.github.albertopeam.infrastructure.concurrency.Callback;
import com.github.albertopeam.infrastructure.concurrency.UseCaseExecutor;
import com.github.albertopeam.infrastructure.exceptions.ExceptionController;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by Alberto Penas Amor on 09/06/2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class NotesActivityTest {

    @Mock
    UseCaseExecutor mockUseCaseExecutor;
    @Mock
    ExceptionController mocExceptionController;
    @Mock
    LoadNotesUseCase mockLoadNotesUseCase;
    @Mock
    AddNoteUseCase mockAddNoteUseCase;
    @Mock
    NotesViewModel mockNotesViewModel;
    @Rule
    public EspressoDaggerMockRule rule =
            new EspressoDaggerMockRule();
    @Rule
    public ActivityTestRule<NotesActivity> activityTestRule =
            new ActivityTestRule<>(NotesActivity.class, true, false);


    @Test
    public void givenResumedThenLoadingIsVisible(){
        activityTestRule.launchActivity(null);
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }


    @Test
    public void givenResumedWhenLoadedNotesThenShowThenInAList() throws InterruptedException {
        final List<String> notes = new ArrayList<>();
        notes.add("a-note");
          doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                ((Callback<List<String>>)invocationOnMock.getArguments()[2]).onSuccess(notes);
                return null;
            }
        }).when(mockUseCaseExecutor).execute(
                ArgumentMatchers.<Void>any(),
                any(LoadNotesUseCase.class),
                ArgumentMatchers.<Callback<List<String>>>any());
        activityTestRule.launchActivity(null);
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.recycler)).check(RecyclerViewAssertions.hasItemsCount(1));
    }


    @Test
    public void givenNewNoteWhenClickAddThenIsAdded(){
        //TODO:
    }


    @Test
    public void givenEmptyNoteWhenClickAddThenShowToast(){
        //TODO:
    }
}
