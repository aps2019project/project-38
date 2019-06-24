package view.fxmlControllers;

import controller.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Cell;
import model.Game;
import model.SelectionManager;
import model.cards.Card;
import model.cards.Warrior;
import model.player.Player;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;
import view.visualentities.VisualMinion;
import view.visualentities.VisualSpell;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ArenaController implements Initializable {
    public static ArenaController ac;
    Game game;
    public GridPane grid;
    public Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        beforeStartTheGame();
        for (int i = 0; i < 6; i++) {
            fxmlLoaders[i] = new FXMLLoader(LoadedScenes.class.getResource("cardHolder.fxml"));
            try {
                Pane pane = fxmlLoaders[i].load();
                cardHolders[i] = fxmlLoaders[i].getController();
                hand.getChildren().add(pane);
                if (i == 0) {
                    cardHolders[i].cardBackGround.setEffect(new SepiaTone());
                    cardHolders[i].manaBackGround.setEffect(new SepiaTone());
                    cardHolders[i].isThisCardComing = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        hashem
        player1_avatar.setImage(LoadedImages.avatars[4]);
        player2_avatar.setImage(LoadedImages.avatars[7]);
        setActiveMana(8, 2);
        setActiveMana(6, 2);
        player1_username.setText("Hashem");
        player2_username.setText("Rima");
        player1_specialPowerNeededMana.setText("4");
        player2_specialPowerNeededMana.setText("1");
        setActivePlayer(1);
        setActiveMana(1, 1);

        ImageView player1_VS = new VisualMinion("Jen").view;
        showCollectedCollectibleItems("Jen", 1);


        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(2, "Jen");
        buildPlayerHand(hashMap, 1);

        player1_VS.relocate(135, 165);
        pane.getChildren().add(player1_VS);
        setCoolDown(2, 2);
        setCoolDown(0, 1);

        transferToGraveYard("Jen", 2);
        //--------------------------------------------------------------------
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
                        setDefaultEffect(getGridNodeFromIndexes(finalI, finalJ));
                    });
                }
            }
        });
    }

    VisualMinion[][] visualMinions;

    public void init(Game game) {
        this.game = game;
        visualMinions = new VisualMinion[5][9];
//        beforeStartTheGame(game.getPlayers()[0],game.getPlayers()[1]); todo because hero powers do not have name or sprite yet. also remember that some heroes don't have power
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
            visualMinions[sRow][sCol] = null;
            visualMinions[tRow][tCol] = vm;
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
        new Thread(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    void cellOnMouseEvent(int row, int col) {
        game.getSelectionManager().selectCell(game.getBoard().getCell(row, col));

//        ColorAdjust colorAdjust = new ColorAdjust();
//        colorAdjust.setContrast(.3);
//        colorAdjust.setHue(.4);
//        colorAdjust.setBrightness(0.1);
//        colorAdjust.setSaturation(.8);
//        getGridNodeFromIndexes(row, col).setEffect(colorAdjust);
//        selectedNodes.add(getGridNodeFromIndexes(row, col));
    }

    ArrayList<Node> selectedNodes = new ArrayList<>();

    void setDefaultEffect(Node node) {
        if (selectedNodes.contains(node)) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setContrast(.3);
            colorAdjust.setHue(.4);
            colorAdjust.setBrightness(0.1);
            colorAdjust.setSaturation(.8);
            node.setEffect(colorAdjust);
        } else {
            node.setEffect(null);
        }
    }

    public void setSelectionEffect(SelectionManager sm) {
        rmSelectionEffects();

        for (Cell cell : sm.getCells()) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setContrast(.3);
            colorAdjust.setHue(.4);
            colorAdjust.setBrightness(0.1);
            colorAdjust.setSaturation(.8);
            Node node = getGridNodeFromIndexes(cell.getRow(), cell.getColumn());
            node.setEffect(colorAdjust);

            selectedNodes.add(node);
        }
        //todo other sm fields
    }

    void rmSelectionEffects() {
        for (Node node : selectedNodes) {
            node.setEffect(null);
        }
        selectedNodes.clear();
    }

    //required things: --------------------------
    public Pane menu;
    public Pane graveYardPane;
    public HBox mainGraveYard;
    public VBox player1_items;
    public VBox player2_items;
    public HBox hand;
    CardHolder[] cardHolders = new CardHolder[6];
    FXMLLoader[] fxmlLoaders = new FXMLLoader[6];
    public ImageView replaceButton;
    private ArrayList<ImageView> player1GraveYard = new ArrayList<>();
    private ArrayList<ImageView> player2GraveYard = new ArrayList<>();
    //....................:window top section:..................
    //top window items:
    public ImageView player1_avatar;
    public ImageView player2_avatar;
    public Label player1_username;
    public Label player2_username;
    public ImageView player1_avatarBorder;
    public ImageView player2_avatarBorder;
    public GridPane player1_mana;
    public GridPane player2_mana;
    //players special power mana:
    public Label player1_specialPowerRemainedTurn;
    public Label player2_specialPowerRemainedTurn;
    public Label player1_specialPowerNeededMana;
    public Label player2_specialPowerNeededMana;
    public ImageView player1_specialPowerBackGround;
    public ImageView player2_specialPowerBackGround;
    public ImageView player1_specialPowerRequiredManaBackGround;//todo MOEINI
    public ImageView player2_specialPowerRequiredManaBackGround;//todo MOEINI
    private int[] playersMana = {0, 0};


    private void beforeStartTheGame(Player player1, Player player2) {
        player1_avatar.setImage(player1.avatar);
        player2_avatar.setImage(player2.avatar);
        player1_username.setText(player1.username);
        player2_username.setText(player2.username);
        ImageView player1_VS = new VisualSpell(player2.getMainDeck().getHero().getPower().getName()).view;
        player1_VS.relocate(133, 165);
        ImageView player2_VS = new VisualSpell(player1.getMainDeck().getHero().getPower().getName()).view;
        player2_VS.relocate(1004, 165);
        player1_specialPowerNeededMana.setText(String.valueOf(player1.getMainDeck().getHero().getPower().getRequiredMana()));
        player2_specialPowerNeededMana.setText(String.valueOf(player2.getMainDeck().getHero().getPower().getRequiredMana()));

        for (int i = 0; i < 6; i++) {
            fxmlLoaders[i] = new FXMLLoader(LoadedScenes.class.getResource("cardHolder.fxml"));
            try {
                Pane pane = fxmlLoaders[i].load();
                cardHolders[i] = fxmlLoaders[i].getController();
                hand.getChildren().add(pane);
                if (i == 0) {
                    cardHolders[i].cardBackGround.setEffect(new SepiaTone());
                    cardHolders[i].manaBackGround.setEffect(new SepiaTone());
                    cardHolders[i].isThisCardComing = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //call when hero special power is used
    public void setCoolDown(int remainingTurn, int playerNumber /* 1 or 2 */) {
        Label label = player2_specialPowerRemainedTurn;
        ImageView backGround = player2_specialPowerBackGround;
        ImageView mana = player2_specialPowerRequiredManaBackGround;
        if (playerNumber == 1) {
            label = player1_specialPowerRemainedTurn;
            backGround = player1_specialPowerBackGround;
            mana = player1_specialPowerRequiredManaBackGround;
        }
        label.setText(String.valueOf(remainingTurn));
        if (remainingTurn == 0) {
            backGround.setImage(LoadedImages.blueCircle);
            mana.setImage(LoadedImages.blueMana);
        }
    }

    //call when something that needed mana is used or at the start of each turn
    public void setActiveMana(int number /* number of active mana */, int playerNumber /* 1 or 2 */) {
        playersMana[playerNumber - 1] = number;
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

    //call when a collectible-item is collected
    public void showCollectedCollectibleItems(String itemName, int playerName /* 1 or 2 */) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("itemHolder.fxml"));
            Pane pane = fxmlLoader.load();
            ItemHolder itemHolder = fxmlLoader.getController();
            ImageView visualSpell = new VisualMinion(itemName).view; //todo DANGER!!!!
            itemHolder.gif.getChildren().add(visualSpell);
            visualSpell.relocate(visualSpell.getX() - 17, visualSpell.getY() - 29);
            if (playerName == 1) {
                player1_items.getChildren().add(pane);
            } else {
                player2_items.getChildren().add(pane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //call on end turn
    public void setActivePlayer(int playerNumber /* 1 or 2 */) {
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

    public void buildPlayerHand(HashMap<Integer, String> cards, int playerNumber) {
        for (int i : cards.keySet()) {
            String name = cards.get(i);
            for (int cardID : Card.getAllCards().keySet()) {
                Card card = Card.getAllCards().get(cardID);
                if (card.getName().equals(name)) {
                    int requiredMana = card.getRequiredMana();
                    cardHolders[i].neededManaForCart.setText(String.valueOf(requiredMana));
                    if (playersMana[playerNumber - 1] < requiredMana) {
                        cardHolders[i].manaBackGround.setEffect(new SepiaTone());
                    } else {
                        cardHolders[i].manaBackGround.setEffect(null);
                    }
                    ImageView visualEntity;
                    if (card instanceof Warrior) {
                        visualEntity = new VisualMinion(name).view;
                    } else {
                        visualEntity = new VisualSpell(name).view;
                    }
                    cardHolders[i].gif.getChildren().add(visualEntity);
                    visualEntity.relocate(visualEntity.getX() - 10, visualEntity.getY() - 24);
                }
            }
        }
    }

    public void transferToGraveYard(String cardName, int playerNumber /* 1 or 2 */) {
        ArrayList<ImageView> graveYardCards = player2GraveYard;
        if (playerNumber == 1) {
            graveYardCards = player1GraveYard;
        }
        for (int cardID : Card.getAllCards().keySet()) {
            Card card = Card.getAllCards().get(cardID);
            if (card.getName().equals(cardName)) {
                if (card instanceof Warrior) {
                    graveYardCards.add(new VisualMinion(cardName).view);
                } else {
                    graveYardCards.add(new VisualSpell(cardName).view);
                }
            }
        }
        mainGraveYard.getChildren().clear();
        for (ImageView cardGif : graveYardCards) {
            mainGraveYard.getChildren().add(cardGif);
        }
    }

    public void useCollectibleItem(int i /* 0-base */, int playerNumber) {
        VBox vBox = player2_items;
        if (playerNumber == 1) {
            vBox = player1_items;
        }
        vBox.getChildren().remove(i);
    }

    public void useCard(int i) {
        cardHolders[i].gif.getChildren().clear();
    }


    //arena buttons:
    public void endTurn() {
        game.endTurn();
    }

    public void replace() {
        SelectionManager.replaceSelectedCard();
    }

    //grave yard:
    public void backFromGraveYard() {
        graveYardPane.toBack();
    }

    public void graveYard() {
        graveYardPane.toFront();
    }

    //menu:
    public void menu() {
        menu.toFront();
    }

    public void resume() {
        menu.toBack();
    }

    public void save() {
        //todo Optional
    }

    public void quit() {
        //todo set the other player as winner MOEINI
        Main.mainStage.setScene(LoadedScenes.mainMenu);
        Main.mainStage.setFullScreen(true);
    }
}