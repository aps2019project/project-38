package view.fxmlControllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Game;
import view.visualentities.VisualMinion;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ArenaController implements Initializable {
    public static ArenaController ac;

    Game game;
    public GridPane grid;
    public Pane pane;
    VisualMinion[][] visualMinions;

    public void init(Game game) {
        this.game = game;
        visualMinions = new VisualMinion[5][9];
    }

    public void put(int row, int col, String name) {
        Platform.runLater(() -> {
            VisualMinion vm = new VisualMinion(name);
            vm.view.relocate(getXFromIndexes(row, col, vm.animation.width, vm.animation.height), getYFromIndexes(row, col, vm.animation.width, vm.animation.height));

            pane.getChildren().add(vm.view);
            visualMinions[row][col] = vm;
        });
    }

    double getXFromIndexes(int row, int col, int width, int height) {
        Bounds bounds = grid.getCellBounds(0, 0);
//        return bounds.getMinX()/3 + grid.getLayoutX() + bounds.getWidth()*Math.pow(col,1.5)/5.5 + 40 +(130 - vm.animation.width);
        return bounds.getMinX() + grid.getLayoutX() + bounds.getWidth() * col * (row / 20f + .85) - 13 * row - width / 4 + 60;

    }

    double getYFromIndexes(int row, int col, int width, int height) {
        Bounds bounds = grid.getCellBounds(0, 0);
//        return bounds.getMinY()/4 + grid.getLayoutY() + bounds.getHeight()*Math.pow(row,1.8)/10 + 50 +(130 - vm.animation.height);
        return bounds.getMinY() + grid.getLayoutY() + bounds.getHeight() * Math.pow(row, 1.1) - height / 2.5 - 50;
    }

    public void move(int sRow, int sCol, int tRow, int tCol) {
        Platform.runLater(() -> {
            VisualMinion vm = visualMinions[sRow][sCol];
            if ((tCol - sCol) * vm.view.getScaleX() < 0) {
                vm.view.setScaleX(-vm.view.getScaleX());
            }
            vm.run();
            Duration duration = Duration.millis((Math.abs(sRow - tRow) + Math.abs(sCol - tCol)) * 400);
            KeyValue xValue = new KeyValue(vm.view.xProperty(), getXFromIndexes(tRow, tCol, vm.animation.width, vm.animation.height) - vm.view.getLayoutX());
            KeyValue yValue = new KeyValue(vm.view.yProperty(), getYFromIndexes(tRow, tCol, vm.animation.width, vm.animation.height) - vm.view.getLayoutY());
            KeyFrame keyFrame = new KeyFrame(duration, xValue, yValue);
            Timeline timeline = new Timeline(keyFrame);
            timeline.play();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    vm.breathing();
                }
            }, (long) duration.toMillis());
        });
    }

    public void attack(int sRow, int sCol, int tRow, int tCol) {
        Platform.runLater(() -> {
            VisualMinion vm = visualMinions[sRow][sCol];
            if ((tCol - sCol) * vm.view.getScaleX() < 0) {
                vm.view.setScaleX(-vm.view.getScaleX());
            }
            vm.attack();
        });
    }

    public void cast(int row, int col) {
        Platform.runLater(() -> {
            visualMinions[row][col].cast();
        });
    }

    public void kill(int row, int col) {
        Platform.runLater(() -> {
            visualMinions[row][col].death();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    visualMinions[row][col].view.setImage(null);
                    visualMinions[row][col] = null;
                }
            }, visualMinions[row][col].animation.realDuration);
        });
    }

    void cellOnMouseEvent(int row, int col) {
        System.out.println(row + " " + col);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ac = this;

        transformGrid();

        //producing click boxes and fixing indexes of nodes of gridPane
        Platform.runLater(() -> {
            for (Node node : grid.getChildren()) {
                if (!Objects.nonNull(GridPane.getRowIndex(node))) {
                    GridPane.setRowIndex(node, 0);
                }
                if (!Objects.nonNull(GridPane.getColumnIndex(node))) {
                    GridPane.setColumnIndex(node, 0);
                }
            }

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    ///////////////dirty code ahead//////////////////////
                    int height = (int) (10 * (i * .65f + 6));
                    int width = (int) (60 - 2.5f * (4 - i));
                    Rectangle rect = new Rectangle(width, height);
                    rect.setFill(Color.TRANSPARENT);
                    rect.relocate(getXFromIndexes(i, j, width - 40, height - 80), getYFromIndexes(i, j, width - 40, height - 80));
                    //////////////////////////////////////////////
                    pane.getChildren().add(rect);

                    int finalI = i;
                    int finalJ = j;
                    rect.setOnMouseClicked(event -> {
                        cellOnMouseEvent(finalI, finalJ);
                    });
                    rect.setOnMouseEntered(event -> {
                        getGridNodeFromIndexes(finalI,finalJ).setEffect(new Glow(1));
                    });
                    rect.setOnMouseExited(event -> {
                        getGridNodeFromIndexes(finalI, finalJ).setEffect(null);
                    });
                }
            }
        });
    }

    private Node getGridNodeFromIndexes(int finalI, int finalJ) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getRowIndex(node) == finalI && GridPane.getColumnIndex(node) == finalJ) {
                return node;
            }
        }
        return null;
    }


    private void transformGrid() {
        PerspectiveTransform perspectiveTransform = new PerspectiveTransform();
        perspectiveTransform.setUlx(70);
        perspectiveTransform.setUly(-30);
        perspectiveTransform.setUrx(530);
        perspectiveTransform.setUry(-30);
        perspectiveTransform.setLrx(600);
        perspectiveTransform.setLry(340);
        perspectiveTransform.setLlx(0);
        perspectiveTransform.setLly(340);
        grid.setEffect(perspectiveTransform);
    }
}