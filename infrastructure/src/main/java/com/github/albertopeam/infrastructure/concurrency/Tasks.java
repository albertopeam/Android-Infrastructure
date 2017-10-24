package com.github.albertopeam.infrastructure.concurrency;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Alberto Penas Amor on 06/06/2017.
 *
 * Handles all tasks sended to execution
 */

class Tasks {


    private List<Task> tasks;


    Tasks(@NonNull List<Task> tasks) {
        this.tasks = tasks;
    }


    boolean alreadyAdded(@NonNull UseCase useCase){
        synchronized (tasks) {
            for (Task task : tasks) {
                if (task.getUseCase() == useCase) {
                    return true;
                }
            }
            return false;
        }
    }


    @NonNull Task findTask(@NonNull UseCase useCase) throws NullPointerException{
        synchronized (tasks) {
            for (Task task : tasks) {
                if (task.getUseCase() == useCase) {
                    return task;
                }
            }
            throw new NullPointerException();
        }
    }


    void addUseCase(@NonNull UseCase useCase){
        synchronized (tasks) {
            tasks.add(new Task(useCase));
        }
    }


    void removeUseCase(@NonNull UseCase useCase){
        synchronized (tasks) {
            try {
                Task task = findTask(useCase);
                tasks.remove(task);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }


    @NonNull List<Task>tasks(){
        synchronized (tasks) {
            return tasks;
        }
    }

}
