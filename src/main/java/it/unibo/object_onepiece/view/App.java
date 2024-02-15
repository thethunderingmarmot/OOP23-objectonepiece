package it.unibo.object_onepiece.view;

import javafx.application.Application;

/**
* Entry point's class.
*/
public final class App {
    private App() {
        throw new UnsupportedOperationException();
    }
    /**
    * Program's entry point.
    * 
    * @param args
    */
    public static void main(final String[] args) {
        Application.launch(ObjectOnePiece.class);
    }
}
