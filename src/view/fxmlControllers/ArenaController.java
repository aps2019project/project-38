package view.fxmlControllers;

import controller.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import model.Game;
import model.player.Player;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;
import view.visualentities.VisualMinion;
import view.visualentities.VisualSpell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class ArenaController implements Initializable {
    public static ArenaController ac;
    Game game;

    public GridPane grid;
    public Pane pane;
    VisualMinion[][] visualMinions;

    public void init(Game game) {
        this.game = game;
        visualMinions = new VisualMinion[5][9];
        //todo init generals
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
        game.getSelectionManager().selectCell(game.getBoard().getCell(row, col));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

////        hashem
//        player1_avatar.setImage(LoadedImages.avatars[4]);
//        player2_avatar.setImage(LoadedImages.avatars[7]);
//        setActiveMana(8, 2);
//        setActiveMana(6, 2);
//        player1_username.setText("Hashem");
//        player2_username.setText("Rima");
//        player1_neededManaForSpecialBuff.setText("4");
//        player2_neededManaForSpecialBuff.setText("1");
//        setActivePlayer(1);
//        setActiveMana(1, 1);
//
//        ImageView player1_VS = new VisualMinion("Jen").view;
//        player1_VS.relocate(135, 170);
//        pane.getChildren().add(player1_VS);

        ac = this;

        transformGrid();

        //producing click boxes and fixing indexes of nodes of gridPane
        Platform.runLater(() -> {
            fixGridNodesIndexes();

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
                        getGridNodeFromIndexes(finalI, finalJ).setEffect(new Glow(1));
                    });
                    rect.setOnMouseExited(event -> {
                        getGridNodeFromIndexes(finalI, finalJ).setEffect(null);
                    });
                }
            }
        });
    }

    private void fixGridNodesIndexes() {
        for (Node node : grid.getChildren()) {
            if (!Objects.nonNull(GridPane.getRowIndex(node))) {
                GridPane.setRowIndex(node, 0);
            }
            if (!Objects.nonNull(GridPane.getColumnIndex(node))) {
                GridPane.setColumnIndex(node, 0);
            }
        }
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

    //for showing items: --------------------------

    public Pane menu;
    public Label turn_btn;
    public ImageView player1_avatar;
    public ImageView player2_avatar;
    public Label player1_username;
    public Label player2_username;
    public GridPane player1_mana;
    public GridPane player2_mana;
    public ImageView player1_specialBuff;
    public Label player1_neededManaForSpecialBuff;
    public ImageView player2_specialBuff;
    public Label player2_neededManaForSpecialBuff;
    public ImageView player1_avatarBorder;
    public ImageView player2_avatarBorder;


    private void beforeStartTheGame(Player player1, Player player2) {
        player2_avatarBorder.getTransforms().add(new Scale(1.25, 1.25)); // just for first time
        player1_avatar.setImage(player1.avatar);
        player2_avatar.setImage(player2.avatar);
        player1_username.setText(player1.username);
        player2_username.setText(player2.username);
        ImageView player1_VS = new VisualSpell(player2.getMainDeck().getHero().getPower().getName()).view;
        player1_VS.relocate(135, 168);
        ImageView player2_VS = new VisualSpell(player1.getMainDeck().getHero().getPower().getName()).view;
        player2_VS.relocate(1004, 168);
//        player1_neededManaForSpecialBuff.setText();
//        player2_neededManaForSpecialBuff.setText();
    }

    private void setActiveMana(int number /* number of active mana */, int playerNumber /* 1 or 2 */) {
        GridPane gridPane = player2_mana;
        if (playerNumber == 1) {
            gridPane = player1_mana;
        }
        for (int i = 0; i < 9; i++) {
            ImageView imageView = (ImageView) gridPane.getChildren().get(i);
            if (i < number) {
                imageView.setImage(LoadedImages.blueMana);
            } else {
                imageView.setImage(LoadedImages.grayMana);
            }
        }
    }

    private void setActivePlayer(int playerNumber /* 1 or 2 */) {
        ImageView avatar = player2_avatar;
        ImageView border = player2_avatarBorder;
        ImageView otherAvatar = player1_avatar;
        ImageView otherBorder = player1_avatarBorder;
        if (playerNumber == 1) {
            avatar = player1_avatar;
            border = player1_avatarBorder;
            otherAvatar = player2_avatar;
            otherBorder = player2_avatarBorder;
        }
        avatar.setEffect(null);
        border.setEffect(null);
        otherAvatar.setEffect(new SepiaTone());
        otherBorder.setEffect(new SepiaTone());
    }

    public void resume(MouseEvent mouseEvent) {
        menu.toBack();
    }

    public void save(MouseEvent mouseEvent) {
        //todo
    }

    public void quit(MouseEvent mouseEvent) {
        //todo
        Main.mainStage.setScene(LoadedScenes.mainMenu);
        Main.mainStage.setFullScreen(true);
    }

    public static void showMessege(String message) {
        Popup popup = new Popup();
        Label label = new Label(message);
        label.setBackground(new Background(new BackgroundFill(Color.gray(.5, .5), new CornerRadii(10), new Insets(-5, -10, -5, -10))));
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(new Font(30));
        label.setTextFill(Color.WHITE);
        popup.getContent().add(label);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(popup::hide);
            }
        }, 1000);
        popup.show(Main.mainStage);
    }

    void setSelectionEffect(){

    }

    void rmSelectionEffects(){

    }
}