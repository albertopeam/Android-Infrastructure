package es.albertopeam.apparchitecturelibs.infrastructure.concurrency;

import java.util.List;

/**
 * Created by Alberto Penas Amorberto Penas Amor on 06/06/2017.
 */

class Tasks {


    private List<Task> tasks;


    Tasks(List<Task> tasks) {
        this.tasks = tasks;
    }


    synchronized boolean alreadyAdded(UseCase useCase){
        for (Task task:tasks){
            if (task.getUseCase() == useCase){
                return true;
            }
        }
        return false;
    }


    synchronized Task findTask(UseCase useCase) throws NullPointerException{
        for (Task task : tasks) {
            if (task.getUseCase() == useCase) {
                return task;
            }
        }
        throw new NullPointerException();
    }


    synchronized void addUseCase(UseCase useCase){
        tasks.add(new Task(useCase));
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
