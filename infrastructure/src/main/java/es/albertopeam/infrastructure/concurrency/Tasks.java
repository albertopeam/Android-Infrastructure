package es.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 06/06/2017.
 */

class Tasks {


    private List<Task> tasks;


    Tasks(@NonNull List<Task> tasks) {
        this.tasks = tasks;
    }


    synchronized boolean alreadyAdded(@NonNull UseCase useCase){
        for (Task task:tasks){
            if (task.getUseCase() == useCase){
                return true;
            }
        }
        return false;
    }


    synchronized @NonNull Task findTask(@NonNull UseCase useCase) throws NullPointerException{
        for (Task task : tasks) {
            if (task.getUseCase() == useCase) {
                return task;
            }
        }
        throw new NullPointerException();
    }


    synchronized void addUseCase(@NonNull UseCase useCase){
        tasks.add(new Task(useCase));
    }


    synchronized void removeUseCase(@NonNull UseCase useCase){
        try{
            Task task = findTask(useCase);
            tasks.remove(task);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    synchronized @NonNull List<Task>tasks(){
        return tasks;
    }

}
