package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;


/**
 * Created by Al on 09/06/2017.
 */

public class TasksTest {


    private List<Task> taskList;
    private Tasks sut;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        taskList = new ArrayList<>();
        sut = new Tasks(taskList);
    }


    @Test
    public void givenInitTasksWhenGetTaskListThenReturnSameTaskList(){
        List<Task>tasks = sut.tasks();
        assertThat(tasks, equalTo(taskList));
    }


    @Test
    public void givenInitTasksWhenAddUseCaseThenTasksHaveOnlyThisUseCase(){
        UseCase mockUseCase = mock(UseCase.class);
        sut.addUseCase(mockUseCase);
        List<Task>tasks = sut.tasks();
        Task task = tasks.get(0);
        assertThat(task.getUseCase(), equalTo(mockUseCase));
    }


    @Test
    public void givenInitTasksWhenAddUseCaseAndCheckAlreadyAddedThenIsAdded(){
        UseCase mockUseCase = mock(UseCase.class);
        sut.addUseCase(mockUseCase);
        boolean isAdded = sut.alreadyAdded(mockUseCase);
        assertThat(isAdded, is(true));
    }


    @Test
    public void givenInitTasksWhenCheckAlreadyAddedWithoutUseCasesThenIsNotAdded(){
        UseCase mockUseCase = mock(UseCase.class);
        boolean isAdded = sut.alreadyAdded(mockUseCase);
        assertThat(isAdded, is(false));
    }


    @Test
    public void givenInitTasksWhenRemoveAlreadyAddedUseCaseThenTasksAreEmpty(){
        UseCase mockUseCase = mock(UseCase.class);
        sut.addUseCase(mockUseCase);
        sut.removeUseCase(mockUseCase);
        assertThat(sut.tasks().size(), equalTo(0));
    }


    @Test
    public void givenInitTasksWhenFindAlreadyAddedUseCaseThenReturnUseCase(){
        UseCase mockUseCase = mock(UseCase.class);
        sut.addUseCase(mockUseCase);
        Task task = sut.findTask(mockUseCase);
        assertThat(task.getUseCase(), equalTo(mockUseCase));
    }


    @Test(expected = NullPointerException.class)
    public void givenInitTasksWhenFindTryToFindNotAddedUseCaseThenThrowNPE(){
        UseCase mockUseCase = mock(UseCase.class);
        sut.findTask(mockUseCase);
    }
}
