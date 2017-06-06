### Android architecture
This project shows how to build and app using mvp. It provides
the basic infrastructure to handle multhithreading and errors.
The project doesnt use any DI framework, but can be used anyway.

The project is a sample that shows a list of notes, create notes
and remove notes.

Currently is under development.

##### Main idea
Apply a clean design(SOLID principles) and avoid coupling to
framework or external libs. Also provide tests for show that this
implementation can be tested easily.

##### Todos:
*  Test: unit and infrastructure(database, usecase handler)
*  Improve stoping use cases. Use Lyfecicles(pass Lyfecycle adapter to use case
executor, then add this adapter to the task. when the task ends or lyfecicle
stops then clear callback, maybe leave the task end or stop it if i can).
*  Comment code
*  Decouple infrastructure to a library and upload to bintray

##### Collaborations
Via PR. They will be well received
