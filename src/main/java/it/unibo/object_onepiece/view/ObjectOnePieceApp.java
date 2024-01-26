package it.unibo.object_onepiece.view;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.controlsfx.control.spreadsheet.SpreadsheetView;
import org.controlsfx.control.tableview2.TableColumn2;
import org.controlsfx.control.tableview2.TableView2;

import eu.lestard.grid.GridModel;
import eu.lestard.grid.GridView;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.WritableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Sample JavaFX application.
 */
public final class ObjectOnePieceApp extends Application {

    private static final int MAP_ROWS = 10;
    private static final int MAP_COLUMNS = 10;

    private static final Color CELL_BORDER_COLOR = Color.rgb(66, 138, 245);

    private final GridModel<EntityView> gridModel = new GridModel<>();
    private final GridView<EntityView> gridView = new GridView<>();

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Object One Piece!");

        BorderPane borderPane = new BorderPane();

        gridModel.setDefaultState(new EntityView());
        gridModel.setNumberOfColumns(MAP_COLUMNS);
        gridModel.setNumberOfRows(MAP_ROWS);
        gridView.setGridModel(gridModel);
        gridModel.getCells().forEach(i -> gridView.addColorMapping(i.getState(), i.getState().getColor()));

        gridView.cellBorderColorProperty().set(CELL_BORDER_COLOR);
        /*gridView.addNodeMapping(EntityView.PLAYER, i -> {
            Image img = new Image("pirate_ship_00000.png");
            ImageView pirateView = new ImageView(img);
            pirateView.setPreserveRatio(true); 
            pirateView.fitHeightProperty().bind(gridView.cellSizeProperty());
            gridView.addColorMapping(EntityView.PLAYER, EntityView.PLAYER.color.get());
            return pirateView;
        });*/

        movePlayer(0, 0);
        movePlayer(2, 3);

        TableView2<Entity> entityInfoTable = new TableView2<>();
        ObservableList<Entity> eList = FXCollections.observableArrayList(new Entity("Usopp", 100));
        entityInfoTable.setItems(eList);
        
        TableColumn2<Entity, Image> iconColumn = new TableColumn2<>("Image");
        TableColumn2<Entity, String> nameColumn = new TableColumn2<>("Name");
        nameColumn.setCellValueFactory(p -> p.getValue().name);
        TableColumn2<Entity, Integer> healthColumn = new TableColumn2<>("Health");
        healthColumn.setCellValueFactory(p -> p.getValue().health.asObject());

        entityInfoTable.getColumns().setAll(nameColumn, healthColumn);
        

        Canvas health = new Canvas(100, 100);
        GraphicsContext healthGC = health.getGraphicsContext2D();
        Rectangle healthBar = new Rectangle(20, 100);
        drawHealthBar(healthGC, healthBar);

        Label healthPoints = new Label("100");

        VBox healthContainer = new VBox();
        healthContainer.getChildren().addAll(health, healthPoints);


        borderPane.setCenter(gridView);
        borderPane.setRight(healthContainer);
        borderPane.setLeft(entityInfoTable);
        Scene scene = new Scene(borderPane, 600, 600);
        scene.getStylesheets().add("/css/ObjectOnePieceApp.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void movePlayer(int row, int col) {
        /*var playerList = gridModel.getCellsWithState(EntityView.PLAYER);
        if (!playerList.isEmpty() && playerList.size() > 1) {
            throw new IllegalStateException("There can't exist two players on the map");
        } else if (!playerList.isEmpty()) {
            playerList.get(0).changeState(EntityView.WATER);
        }   
        gridModel.getCell(col, row).changeState(EntityView.PLAYER);*/
    }

    private void drawHealthBar(GraphicsContext gc,Rectangle rect) {
        gc.setFill(Color.GREEN);
        gc.fillRect(rect.getX(),      
               rect.getY(), 
               rect.getWidth(), 
               rect.getHeight());
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
    }

    public class Entity {
        private StringProperty name;
        private IntegerProperty health;
        private Canvas healthBar;
        private GraphicsContext gc;

        public Entity(final String string, final int health) {
            this.name = new SimpleStringProperty(string);
            this.health = new SimpleIntegerProperty(health);
        }

    }

    /**
     * Program's entry point.
     * @param args
     */
    public static void run(final String... args) {
        launch(args);
    }

    // Defining the main methods directly within JavaFXApp may be problematic:
    // public static void main(final String[] args) {
    //        run();
    // }

    /**
     * Entry point's class.
     */
    public static final class Main {
        private Main() {
            // the constructor will never be called directly.
        }

        /**
         * Program's entry point.
         * @param args
         */
        public static void main(final String... args) {
            Application.launch(ObjectOnePieceApp.class, args);
            /* 
            The following line raises: Error: class it.unibo.samplejavafx.JavaFXApp$Main 
            is not a subclass of javafx.application.Application
            Because if you do not provide the Application subclass to launch() it will consider the enclosing class)
            */
            // JavaFXApp.launch(args);
            // Whereas the following would do just fine:
            // JavaFXApp.run(args)
        }
    }
}
