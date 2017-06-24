### Android architecture
This project shows how to build and app using mvp. It provides
the basic infrastructure to handle multhithreading and errors.
The project use Dagger2 DI framework.

The project is a sample that shows a list of notes, create notes
and remove notes.

Currently is under development.

##### Main idea
Apply a clean design(SOLID principles) and avoid coupling to
framework or external libs. Also provide tests for show that this
implementation can be tested easily.

##### How it works?
There is a package called infrastructure where we can found the neccesary classes to
make our code async. All the code inside other packages is synchronous. Also, there is a error handling
package to avoid duplicate code when handling exceptions(uses delegation pattern).

Domain package contains a repository for abstract the access to the data.

Data package contains code to decouple access to data providers, in this case the database.

Notes package is the sample activity with all the configuration stuff needed to make the use cases run.

##### Todos:
*  Replace NotRecoverableError String property with a int reference to support I18N
*  Test: unit and infrastructure(database)
*  Comment code: infrastructure->exceptions, review concurrency
*  Decouple infrastructure to a library and upload to bintray.
*  Library wiki(Github) with samples
*  Mount a jenkinsfile. Local jenkins. Search free jenkins providers.
*  Automatic upload from jenkins to bintray.

##### Collaborations
Via PR. They will be well received
