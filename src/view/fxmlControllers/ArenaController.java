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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ArenaController implements Initializable, PropertyChangeListener {
    public static ArenaController ac;
    public Game game;
    public GridPane grid;
    public Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        hashem

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


        for (int i = 0; i < 2; i++) {
            fxmlLoaders1[i] = new FXMLLoader(LoadedScenes.class.getResource("heroSpecialPower.fxml"));
            Pane pane = null;
            try {
                pane = fxmlLoaders1[i].load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            heroSpecialPowerControllers[i] = fxmlLoaders1[i].getController();
            if (i == 0) {
                hero1SpecialPower.getChildren().add(pane);
//                heroSpecialPowerControllers[i].setHeroSpecialPowerFirstInfo(player1);
            } else {
                hero2SpecialPower.getChildren().add(pane);
//                heroSpecialPowerControllers[i].setHeroSpecialPowerFirstInfo(player2);
            }
        }

        player1_avatar.setImage(LoadedImages.avatars[3]);
        player2_avatar.setImage(LoadedImages.avatars[9]);

        setActiveMana(8, 2);

        setActiveMana(6, 2);
        player1_username.setText("Hashem");
        player2_username.setText("Rima");

        setActivePlayer(1);

        setActiveMana(1, 1);

//        showCollectedCollectibleItems("TotalDisarm", 1);
//        showCollectedCollectibleItems("TotalDisarm", 2);

        setCoolDown(4, 2);
        setCoolDown(0, 1);

        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.put(2, "Jen");

        buildPlayerHand(hashMap, 1);

        transferToGraveYard("Jen", 2);

        //--------------------------------------------------------------------

        ac = this;

        transformGrid();

        //producing click boxes and fixing indexes of nodes of gridPane
        Platform.runLater(() ->

        {
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

        Platform.runLater(() -> {
            pane.requestFocus();
            pane.setOnKeyTyped(event -> {
                if (event.getCharacter().getBytes()[0]==27) {
                    game.getSelectionManager().deselectAction();
                }
            });
        });
    }

    VisualMinion[][] visualMinions;

    public void init(Game game) {
        this.game = game;
        visualMinions = new VisualMinion[5][9];

        //todo temp:
        game.getPlayers()[0].addListener(this);
        game.getPlayers()[1].addListener(this);

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

    //-----------------------:required things:--------------------------
    public Pane menu;
    public Pane graveYardPane;
    public HBox mainGraveYard;
    public VBox player1_items;
    public VBox player2_items;
    public HBox hand;
    private FXMLLoader[] fxmlLoaders = new FXMLLoader[6];
    private CardHolderController[] cardHolders = new CardHolderController[6];
    private ArrayList<ImageView> player1GraveYard = new ArrayList<>();
    private ArrayList<ImageView> player2GraveYard = new ArrayList<>();

    //----------------------:window top section:--------------------
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
    private FXMLLoader[] fxmlLoaders1 = new FXMLLoader[2];
    private HeroSpecialPowerController[] heroSpecialPowerControllers = new HeroSpecialPowerController[2];
    // todo: MOEINI, use ^this^ heroSpecialPowerControllers.gif to control hero special powers.
    public Pane hero1SpecialPower;
    public Pane hero2SpecialPower;
    private int[] playersMana = {0, 0};


    private void beforeStartTheGame(Player player1, Player player2) {
        player1.addListener(this);
        player2.addListener(this);

        player1_avatar.setImage(player1.avatar);
        player2_avatar.setImage(player2.avatar);

        player1_username.setText(player1.username);
        player2_username.setText(player2.username);


        for (int i = 0; i < 2; i++) {
            fxmlLoaders1[i] = new FXMLLoader(LoadedScenes.class.getResource("heroSpecialPower.fxml"));
            Pane pane = null;
            try {
                pane = fxmlLoaders1[i].load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            heroSpecialPowerControllers[i] = fxmlLoaders1[i].getController();
            if (i == 0) {
                hero1SpecialPower.getChildren().add(pane);
                heroSpecialPowerControllers[i].setHeroSpecialPowerFirstInfo(player1);
            } else {
                hero2SpecialPower.getChildren().add(pane);
                heroSpecialPowerControllers[i].setHeroSpecialPowerFirstInfo(player2);

            }
        }

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

    public void setCoolDown(int remainingTurn, int playerNumber /* 1 or 2 */) {
        heroSpecialPowerControllers[playerNumber - 1].setRemainedTurn(remainingTurn);
    }

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
    public void showCollectedCollectibleItems(String itemName, int playerNum /* 1 or 2 */) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoadedScenes.class.getResource("itemHolder.fxml"));
            Pane pane = fxmlLoader.load();
            Holder itemHolder = fxmlLoader.getController();
            VisualSpell vs = new VisualSpell(itemName);
            ImageView visualSpell = vs.view;
            itemHolder.put(visualSpell, vs.getWidth(), vs.getHeight());
            if (playerNum == 1) {
                player1_items.getChildren().add(pane);
            } else {
                player2_items.getChildren().add(pane);
                System.out.println("here in player 2 c adder");
            }

            pane.setOnMouseClicked(event -> {
                game.getSelectionManager().selectCollectibleItem(itemName);
            });
        } catch (IOException e) {
            e.printStackTrace();
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
                    if (i > 0) {
                        visualEntity.setOnMouseClicked(event -> game.getSelectionManager().selectCard(i - 1));
                    }
                    break;
                }
            }
        }
    }

    //call when using a card from "current" player hand
    public void useCard(int i) {
        cardHolders[i + 1].gif.getChildren().clear();
        cardHolders[i + 1].neededMana.setText("");
        for (int j = 1; j < 6; j++) {
            if (cardHolders[j].neededMana.getText().equals("")) continue;
            if (Integer.parseInt(cardHolders[j].neededMana.getText()) > playersMana[getCurrentPlayer()]) {
                cardHolders[j].manaBackGround.setEffect(new SepiaTone());
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

    //arena buttons:
    public void endTurn() {
        game.endTurn();
    }

    //grave yard:
    public void backFromGraveYard() {
        graveYardPane.toBack();
    }

    public void graveYard() {
        ArrayList<ImageView> template = player2GraveYard;
        if (getCurrentPlayer() == 0) {
            template = player1GraveYard;
        }
        mainGraveYard.getChildren().clear();
        for (ImageView imageView : template) {
            mainGraveYard.getChildren().add(imageView);
        }
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
        game.getGameMode().winner = game.getOtherPlayer(game.getActivePlayer());
        game.endGame();
    }

    public void endGame(Player winner) {
        //todo a banner or sth
        WindowChanger.instance.setNewScene(LoadedScenes.mainMenu);
    }

    int getCurrentPlayer() {
        return ArenaController.ac.game.turn % 2;
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        setActiveMana((int) propertyChangeEvent.getNewValue(), game.getPlayerNumber(game.getActivePlayer()) + 1);
    }
}