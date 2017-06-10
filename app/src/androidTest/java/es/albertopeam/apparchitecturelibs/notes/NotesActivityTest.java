package es.albertopeam.apparchitecturelibs.notes;

import android.arch.lifecycle.ViewModel;
import android.support.test.espresso.core.deps.guava.base.Strings;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import es.albertopeam.apparchitecturelibs.R;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.Callback;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCase;
import es.albertopeam.apparchitecturelibs.infrastructure.concurrency.UseCaseExecutor;

import static android.support.test.espresso.Espresso.onView;
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



    //TODO: Caused by: java.lang.RuntimeException: Unable to resolve activity for: Intent { act=android.intent.action.MAIN flg=0x14000000 cmp=es.albertopeam.apparchitecturelibs/.notes.NotesActivityTest$NotesActivityExtended }
    //TODO: check if needed to declare in test manifest
    @Rule
    public ActivityTestRule<NotesActivityExtended> activityTestRule =
            new ActivityTestRule<>(NotesActivityExtended.class, true, true);
    private NotesPresenter mockNotesPresenter;
    private UseCaseExecutor mockUseCaseExecutor;


    public class NotesActivityExtended
            extends NotesActivity{
        @Override
        protected NotesPresenter makePresenter() {
            NotesViewModel mockNotesViewModel = mock(NotesViewModel.class);
            mockUseCaseExecutor = mock(UseCaseExecutor.class);
            LoadNotesUseCase mockLoadNotesUseCase = mock(LoadNotesUseCase.class);
            AddNoteUseCase mockAddNoteUseCase = mock(AddNoteUseCase.class);
            RemoveNoteUseCase mockRemoveNoteUseCase = mock(RemoveNoteUseCase.class);
            mockNotesPresenter = new NotesPresenter(
                    activityTestRule.getActivity(),
                    mockNotesViewModel,
                    mockUseCaseExecutor,
                    mockLoadNotesUseCase,
                    mockAddNoteUseCase,
                    mockRemoveNoteUseCase);
            return mockNotesPresenter;
        }
    }


    @Before
    public void setUp(){
        //todo: replace deps-> presenter with viewmodel, use cases...etc
        //todo: posibilidades:
         //extend activity and replace some method in which make injection
         //implements a generic interface where it returns the presenter and all deps. we need to extend and override... the same as first
         //@visibleForTesting...
    }


    @Test
    public void givenClearAppWhenLoadNotesThenEmpty(){
        when(mockUseCaseExecutor.execute(any(Object.class), any(UseCase.class), any(Callback.class))).then(new Answer<List<String>>() {
            public List<String> answer(InvocationOnMock invocation) {
                ((Callback) invocation.getArguments()[1]).onSuccess(new ArrayList<String>());
                return null;
            }
        });
        onView(withId(R.id.recycler)).check(new RecyclerViewItemCountAssertion(0));

    }


    @Test
    public void givenNewNoteWhenClickAddThenIsAdded(){

    }


    @Test
    public void givenEmptyNoteWhenClickAddThenShowToast(){

    }
}
