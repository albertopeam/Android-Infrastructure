package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import android.arch.lifecycle.Lifecycle;

import java.util.List;

/**
 * Created by Alberto Penas Amor on 06/06/2017.
 */

class Tasks {


    private List<Task> tasks;


    Tasks(List<Task> tasks) {
        this.tasks = tasks;
    }


    synchronized boolean alreadyAdded(UseCase useCase){
        for (Task task:tasks){
            if (task.useCase == useCase){
                return true;
            }
        }
        return false;
    }


    synchronized Task findTask(UseCase useCase) throws NullPointerException{
        for (Task task : tasks) {
            if (task.useCase == useCase) {
                return task;
            }
        }
        throw new NullPointerException();
    }


    synchronized boolean canRespond(UseCase useCase){
        for (Task task:tasks){
            if (task.useCase == useCase){
                return !task.canceled;
            }
        }
        return false;
    }


    synchronized void addUseCase(UseCase useCase, Lifecycle lifecycle){
        tasks.add(new Task(useCase, lifecycle));
    }


    synchronized void removeUseCase(UseCase useCase){
        try{
            Task task = findTask(useCase);
            tasks.remove(task);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    synchronized List<Task>tasks(){
        return tasks;
    }

}
