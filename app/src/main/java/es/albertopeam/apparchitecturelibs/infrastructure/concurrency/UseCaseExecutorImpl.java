package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.Error;
import es.albertopeam.apparchitecturelibs.infrastructure.exceptions.ExceptionController;

/**
 * Created by Al on 25/05/2017.
 */

class UseCaseExecutorImpl
    implements UseCaseExecutor{


    private ExceptionController exceptionController;
    private Executor executor;
    private MainThreadImpl mainThread;
    private List<Task> tasks = new ArrayList<>();


    UseCaseExecutorImpl(Executor executor,
                        MainThreadImpl mainThread,
                        ExceptionController exceptionController) {
        this.executor = executor;
        this.mainThread = mainThread;
        this.exceptionController = exceptionController;
    }


    @Override
    public <Args, Response> void execute(final Args args,
                                         final UseCase<Args, Response> useCase,
                                         final Callback<Response>callback){
        if (find(useCase)){
            return;
        }
        addUseCase(useCase);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = useCase.run(args);
                    notifySuccess(useCase, callback, response);
                } catch (Exception e) {
                    notifyError(useCase, callback, e);
                }

            }
        });
    }


    @Override
    public synchronized void cancel(UseCase... useCases){
        for (UseCase useCase:useCases) {
            try {
                Task task = findTask(useCase);
                task.canceled = true;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }


    synchronized void cancelAll(){
        for (Task task:tasks) {
            try {
                task.canceled = true;
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }


    synchronized void setExceptionController(ExceptionController exceptionController) {
        this.exceptionController = exceptionController;
    }


    private boolean find(UseCase useCase){
        for (Task task:tasks){
            if (task.useCase == useCase){
                return true;
            }
        }
        return false;
    }


    private Task findTask(UseCase useCase) throws NullPointerException{
        for (Task task : tasks) {
            if (task.useCase == useCase) {
                return task;
            }
        }
        throw new NullPointerException();
    }


    private boolean canRespond(UseCase useCase){
        for (Task task:tasks){
            if (task.useCase == useCase){
                return !task.canceled;
            }
        }
        return false;
    }


    private <Args, Response> void notifySuccess(
                                final UseCase<Args, Response> useCase,
                                final Callback<Response> callback,
                                final Response success){
        mainThread.run(new Runnable() {
            @Override
            public void run() {
                if (canRespond(useCase)){
                    callback.onSuccess(success);
                }
                removeUseCase(useCase);
            }
        });
    }


    private void notifyError(final UseCase useCase,
                             final Callback callback,
                             final Exception e){
        mainThread.run(new Runnable() {
            @Override
            public void run() {
                if (canRespond(useCase)){
                    final Error error = exceptionController.handle(e);
                    callback.onError(error);
                    removeUseCase(useCase);
                }
            }
        });
    }


    private synchronized void addUseCase(UseCase useCase){
        tasks.add(new Task(useCase));
    }


    private synchronized void removeUseCase(UseCase useCase){
        try{
            Task task = findTask(useCase);
            tasks.remove(task);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
