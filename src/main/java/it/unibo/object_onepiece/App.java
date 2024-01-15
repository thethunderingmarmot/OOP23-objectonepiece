/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package it.unibo.object_onepiece;


import javafx.application.Application;

/** Main application entry-point's class. */

public final class App {
    private App() { }

    /**
     * Main application entry-point.
     * @param args
     */
    public static void main(final String[] args) {
        Application.launch(JavaFXApp.class, args);
        // The following line raises: Error: class it.unibo.samplejavafx.App is not a subclass of javafx.application.Application
        // JavaFXApp.launch(args);
        // While the following would do just fine:
        // JavaFXApp.run(args)
    }
}
