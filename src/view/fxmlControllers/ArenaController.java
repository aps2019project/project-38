package view.fxmlControllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
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
import view.WindowChanger;
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
        for (int i = 0; i < 6; i++) {
            fxmlLoaders[i] = new FXMLLoader(LoadedScenes.class.getResource("cardHolder.fxml"));
            try {
                Pane pane = fxmlLoaders[i].load();
                cardHolders[i] = fxmlLoaders[i].getController();
                hand.getChildren().add(pane);
                if (i == 0) {
                    cardHolders[i].backGround.setEffect(new SepiaTone());
                    cardHolders[i].border.setEffect(new SepiaTone());
                    cardHolders[i].manaBackGround.setEffect(new SepiaTone());
                    cardHolders[i].isThisCardComingCard = true;
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

//        showCollectedCollectibleItems("TotalDisarm", 1); // DANGER
//        showCollectedCollectibleItems("TotalDisarm", 2);


        ImageView player1_VS = new VisualMinion("Ashkboos").view;
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
                        Platform.runLater(() -> {
                            pane.getChildren().remove(visualMinions[row][col].view);
                            visualMinions[row][col] = null;
                        });
                    }
                }, visualMinions[row][col].animation.realDuration);
            });
        }).start();

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
    CardHolderController[] cardHolders = new CardHolderController[6];
    FXMLLoader[] fxmlLoaders = new FXMLLoader[6];
    private ArrayList<ImageView> player1GraveYard = new ArrayList<>();
    private ArrayList<ImageView> player2GraveYard = new ArrayList<>();

    public ImageView replaceButton;
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
    public ImageView player1_specialPowerRequiredManaBackGround;
    public ImageView player2_specialPowerRequiredManaBackGround;
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
                    cardHolders[i].backGround.setEffect(new SepiaTone());
                    cardHolders[i].border.setEffect(new SepiaTone());
                    cardHolders[i].manaBackGround.setEffect(new SepiaTone());
                    cardHolders[i].isThisCardComingCard = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //use this method to set the number of turns needed for hero special ability to be ready to use.
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

    //use this ,method after any use of mana
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
            Holder itemHolder = fxmlLoader.getController();
            VisualSpell vs = new VisualSpell(itemName);
            ImageView visualSpell = vs.view;
            itemHolder.put(visualSpell, vs.getWidth(), vs.getHeight());
            if (playerName == 1) {
                player1_items.getChildren().add(pane);
            } else {
                player2_items.getChildren().add(pane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    //call at the start of turns
    public void buildPlayerHand(HashMap<Integer, String> cards, int playerNumber) {
        for (CardHolderController holder : cardHolders) {
            holder.gif.getChildren().clear();
        }
        for (int i : cards.keySet()) {
            String name = cards.get(i);
            for (int cardID : Card.getAllCards().keySet()) {
                Card card = Card.getAllCards().get(cardID);
                if (card.getName().equals(name)) {
                    int requiredMana = card.getRequiredMana();
                    cardHolders[i].neededMana.setText(String.valueOf(requiredMana));
                    if (playersMana[playerNumber - 1] < requiredMana || i == 0) {
                        cardHolders[i].manaBackGround.setEffect(new SepiaTone());
                    } else {
                        cardHolders[i].manaBackGround.setEffect(null);
                    }
                    ImageView visualEntity;
                    if (card instanceof Warrior) {
                        VisualMinion vm = new VisualMinion(name);
                        visualEntity = vm.view;
                        cardHolders[i].put(visualEntity, vm.getWidth(), vm.getHeight());
                    } else {
                        VisualSpell vm = new VisualSpell(name);
                        visualEntity = vm.view;
                        cardHolders[i].put(visualEntity, vm.getWidth(), vm.getHeight());
                    }
                    visualEntity.setOnMouseClicked(event -> game.getSelectionManager().selectCard(i - 1));
                    break;
                }
            }
        }
    }

    //call when you want to add a dead card to it's player grave yard
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

    //call when using a collectible of a player (item with 0 index is at bottom of the VBox)
    public void useCollectibleItem(int i /* 0-base */, int playerNumber /* 1 or 2 */) {
        VBox vBox = player2_items;
        if (playerNumber == 1) {
            vBox = player1_items;
        }
        vBox.getChildren().remove(i);
    }

    //call when using a card from "current" player hand
    public void useCard(int i) {
        cardHolders[i + 1].gif.getChildren().clear();
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
        //todo set the other player as winner ( MOEINI )
        WindowChanger.instance.setNewScene(LoadedScenes.mainMenu);
    }
}