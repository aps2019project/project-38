package view.fxmlControllers;

import client.net.Digikala;
import client.net.Encoder;
import client.net.Message;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import model.Constant;
import model.SelectionManager;
import model.cards.Card;
import model.cards.Spell;
import model.cards.Warrior;
import view.WindowChanger;
import view.fxmlControllers.cardHolder.*;
import view.fxmls.LoadedScenes;
import view.images.LoadedImages;
import view.visualentities.SpriteType;
import view.visualentities.VisualMinion;
import view.visualentities.VisualSpell;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ArenaController implements Initializable {
    public static ArenaController ac;
    //    public Game game;//active player: spell,warrior,his index - board:warrior of a special cell , special cell
    public Pane pane;
    public GridPane grid;
    public SelectionManager sm;
    private VisualMinion[][] visualMinions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ac = this;
    }

    public void init(SelectionManager selectionManager) {
        transformGrid();
        fixGridNodesIndexes();
        produceClickBoxes();
        setEscapeAsDeselector();
        setCheat();

        //initializing essential fields
        this.sm = selectionManager;
        visualMinions = new VisualMinion[5][9];

        beforeStartTheGame();
    }

    private void setEscapeAsDeselector() {
        pane.requestFocus();
        pane.setOnKeyTyped(event -> {
            if (event.getCharacter().getBytes()[0] == 27) {
                sm.deselectAction();
            }
        });
    }

    private void setCheat(){
        StringBuilder keyLog = new StringBuilder();
        pane.requestFocus();
        pane.setOnKeyTyped(event -> {
            keyLog.append(event.getCharacter().getBytes()[0]);
            if(keyLog.toString().endsWith("mana")){
                Encoder.sendMessage(Message.manaCheat);
            }
        });
    }

    private void produceClickBoxes() {
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
    }

    public void put(int row, int col, String name) {
        VisualMinion vm = new VisualMinion(name);
        visualMinions[row][col] = vm;

        Platform.runLater(() -> {
            vm.view.relocate(getXFromIndexes(row, col, vm.animation.width, vm.animation.height), getYFromIndexes(row, col, vm.animation.width, vm.animation.height));
            pane.getChildren().add(vm.view);
        });

        vm.view.setOnMouseEntered(event -> {
            vm.isSelected.set(true);
            Warrior warrior = Digikala.getWarriorOfACell(row, col);
            try {
                showInfoOfACard(warrior.name, warrior.description.descriptionOfCardSpecialAbility, "warrior", warrior.hp, warrior.ap);
            } catch (NullPointerException e) {
                //ignore because it's so frequent
            }
        });

        vm.view.setOnMouseExited(event -> {
            vm.isSelected.set(false);
            endShowInfoOfACard();
        });
    }

    private double getXFromIndexes(int row, int col, int width, int height) {
//        Bounds bounds = grid.getCellBounds(0, 0);
//        return bounds.getMinX() + grid.getLayoutX() + bounds.getWidth() * col * (row / 20f + .85) - 13 * row - width / 4 + 60;
        return grid.getLayoutX() + 63 * col * (row / 20f + .85) - 13 * row - width / 4.0 + 60;
    }

    private double getYFromIndexes(int row, int col, int width, int height) {
//        Bounds bounds = grid.getCellBounds(0, 0);
//        return bounds.getMinY() + grid.getLayoutY() + bounds.getHeight() * Math.pow(row, 1.1) - height / 2.5 - 50;
        return +grid.getLayoutY() + 67 * Math.pow(row, 1.1) - height / 2.5 - 50;
    }

    public void move(int sRow, int sCol, int tRow, int tCol) {
        VisualMinion vm = visualMinions[sRow][sCol];
        visualMinions[sRow][sCol] = null;
        visualMinions[tRow][tCol] = vm;

        Platform.runLater(() -> {
            if ((tCol - sCol) * vm.view.getScaleX() < 0) {
                vm.view.setScaleX(-vm.view.getScaleX());
            }
            vm.isRunning.set(true);
            Duration duration = Duration.millis((Math.abs(sRow - tRow) + Math.abs(sCol - tCol)) * 400);
            KeyValue xValue = new KeyValue(vm.view.xProperty(), getXFromIndexes(tRow, tCol, vm.animation.width, vm.animation.height) - vm.view.getLayoutX());
            KeyValue yValue = new KeyValue(vm.view.yProperty(), getYFromIndexes(tRow, tCol, vm.animation.width, vm.animation.height) - vm.view.getLayoutY());
            KeyFrame keyFrame = new KeyFrame(duration, xValue, yValue);
            Timeline timeline = new Timeline(keyFrame);
            timeline.play();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    vm.isRunning.set(false);
                }
            }, (long) duration.toMillis());
        });

        vm.view.setOnMouseEntered(event -> {
            vm.isSelected.set(true);
            Warrior warrior = Digikala.getWarriorOfACell(tRow, tCol);
            showInfoOfACard(warrior.name, warrior.description.descriptionOfCardSpecialAbility, "warrior", warrior.hp, warrior.ap);
        });
    }

    public void attack(int sRow, int sCol, int tRow, int tCol) {
        VisualMinion vm = visualMinions[sRow][sCol];
        if ((tCol - sCol) * vm.view.getScaleX() < 0) {
            vm.view.setScaleX(-vm.view.getScaleX());
        }
        vm.actionQueue.offer(SpriteType.attack);
    }

    public void cast(int row, int col) {
        visualMinions[row][col].actionQueue.offer(SpriteType.cast);
    }

    public void kill(int row, int col) {
        VisualMinion vm = visualMinions[row][col];
        vm.actionQueue.offer(SpriteType.death);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (vm.animation.type == SpriteType.death) {
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            removeFromBoard(row, col);
                        }
                    }, vm.animation.realDuration);
                    stop();
                }
            }
        }.start();
    }

    private void removeFromBoard(int row, int col) {
        Platform.runLater(() -> {
            pane.getChildren().remove(visualMinions[row][col].view);
            visualMinions[row][col] = null;
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

    private void cellOnMouseEvent(int row, int col) {
        sm.selectCell(row, col);
    }

    ArrayList<Node> selectedNodes = new ArrayList<>();

    private void setDefaultEffect(Node node) {
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
            Node node = getGridNodeFromIndexes(cell.row, cell.column);
            node.setEffect(colorAdjust);

            selectedNodes.add(node);
        }

    }

    private void rmSelectionEffects() {
        for (Node node : selectedNodes) {
            node.setEffect(null);
        }
        selectedNodes.clear();
    }

    //-----------------------:required things:----------------------

    public Pane menu;
    public Pane graveYardPane;
    public HBox mainGraveYard;
    public VBox player1_items;
    public VBox player2_items;
    public HBox hand;
    private FXMLLoader[] fxmlLoaders = new FXMLLoader[6];
    private HandSpriteHolderController[] cardHolders = new HandSpriteHolderController[6];
    private ArrayList<ImageView> player1GraveYard = new ArrayList<>();
    private ArrayList<ImageView> player2GraveYard = new ArrayList<>();

    //----------------------:window top section:--------------------

    //top section items:
    public ImageView player1_avatar;
    public ImageView player2_avatar;
    public Label player1_username;
    public Label player2_username;
    public ImageView player1_avatarBorder;
    public ImageView player2_avatarBorder;
    public GridPane player1_mana;
    public GridPane player2_mana;

    //mana of players special power:
    public Pane hero1SpecialPower;
    public Pane hero2SpecialPower;
    private int[] playersMana = {0, 0};
    private FXMLLoader[] fxmlLoaders1 = new FXMLLoader[2];
    private HeroSpecialPowerSpriteController[] heroSpecialPowerControllers = new HeroSpecialPowerSpriteController[2];


    private void beforeStartTheGame() {
        player1_avatar.setImage(LoadedImages.avatars[Digikala.getIndexOfAvatar(1)]);
        player2_avatar.setImage(LoadedImages.avatars[Digikala.getIndexOfAvatar(2)]);

        player1_username.setText(Digikala.getPlayerUsername(1));
        player2_username.setText(Digikala.getPlayerUsername(2));

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
                heroSpecialPowerControllers[i].setHeroSpecialPowerFirstInfo(1);
            } else {
                hero2SpecialPower.getChildren().add(pane);
                heroSpecialPowerControllers[i].setHeroSpecialPowerFirstInfo(2);
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

    public void setHeroSpecialPowerCoolDown(int remainingTurn, int playerNumber /* 1 or 2 */) {
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
            }

            pane.setOnMouseClicked(event -> {
                sm.selectCollectibleItem(itemName);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //todo MOEINI

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
    } //todo MOEINI

    //call at the start of turns
    public void buildPlayerHand(HashMap<Integer, Card> cards, int playerNumber /* 1 or 2 */) {
        for (HandSpriteHolderController holder : cardHolders) {
            holder.gif.getChildren().clear();
        }
        for (int i : cards.keySet()) {
            Card card = cards.get(i);
            int requiredMana = card.requiredMana;
            cardHolders[i].neededMana.setText(String.valueOf(requiredMana));
            if (playersMana[playerNumber - 1] < requiredMana || i == 0) {
                cardHolders[i].manaBackGround.setEffect(new SepiaTone());
            } else {
                cardHolders[i].manaBackGround.setEffect(null);
            }
            ImageView visualEntity;
            if (card instanceof Warrior) {
                VisualMinion vm = new VisualMinion(card.name);
                visualEntity = vm.view;
                cardHolders[i].put(visualEntity, vm.getWidth(), vm.getHeight());

                visualEntity.setOnMouseEntered(event -> {//amir
                    vm.isSelected.set(true);
                    if (i == 0) {
                        Warrior w = (Warrior) card;
                        showInfoOfACard(w.name, w.description.descriptionOfCardSpecialAbility, "warrior", w.hp, w.ap);
                    } else {
                        Warrior w = (Warrior) card;
                        showInfoOfACard(w.name, w.description.descriptionOfCardSpecialAbility, "warrior", w.hp, w.ap);
                    }
                });
                visualEntity.setOnMouseExited(event -> {
                    vm.isSelected.set(false);
                    endShowInfoOfACard();
                });
            } else {
                VisualSpell vs = new VisualSpell(card.name);
                visualEntity = vs.view;
                cardHolders[i].put(visualEntity, vs.getWidth(), vs.getHeight());

                visualEntity.setOnMouseEntered(event -> {//amir
                    vs.idle();
                    if (i == 0) {
                        Spell w = (Spell) card;
                        showInfoOfACard(w.name, w.description.descriptionOfCardSpecialAbility, "spell", 0, 0);
                    } else {
                        Spell w = (Spell) card;
                        showInfoOfACard(w.name, w.description.descriptionOfCardSpecialAbility, "spell", 0, 0);
                    }
                });
                visualEntity.setOnMouseExited(event -> {
                    vs.breathing();
                    endShowInfoOfACard();
                });
            }

            if (i > 0) {//amir
                visualEntity.setOnMouseClicked(event -> sm.selectCard(i - 1));
            }
            break;
        }
    } //todo MOEINI

    //call when using a card from "current" player hand
    public void useCard(int i) {
        cardHolders[i + 1].gif.getChildren().clear();
        cardHolders[i + 1].neededMana.setText("");
        for (int j = 1; j < 6; j++) {
            if (cardHolders[j].neededMana.getText().equals("")) continue;
            if (Integer.parseInt(cardHolders[j].neededMana.getText()) > playersMana[Digikala.getActivePlayerIndex()]) {
                cardHolders[j].manaBackGround.setEffect(new SepiaTone());
            }
        }
    }

    //call when you want to add a dead card to it's player grave yard
    public void transferToGraveYard(String name, String type, int playerNumber /* 1 or 2 */) {
        ArrayList<ImageView> graveYardCards = player2GraveYard;
        if (playerNumber == 1) {
            graveYardCards = player1GraveYard;
        }

        if (type.equals("Warrior")) {
            graveYardCards.add(new VisualMinion(name).view);
        } else {
            graveYardCards.add(new VisualSpell(name).view);
        }

        mainGraveYard.getChildren().clear();
        for (ImageView cardGif : graveYardCards) {
            mainGraveYard.getChildren().add(cardGif);
        }
    }

    //arena buttons:
    public void endTurn() {
        sm.deselectAction();
        Encoder.sendMessage(Message.endTurn);
    }

    //grave yard:
    public void backFromGraveYard() {
        graveYardPane.toBack();
    }

    public void graveYard() {
        ArrayList<ImageView> template = player2GraveYard;
        if (Digikala.getActivePlayerIndex() == 0) {
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
        System.gc();
        menu.toFront();
    }

    public void resume() {
        menu.toBack();
    }

    public void save() {
        //todo Optional
    }

    public void quit() {
        Encoder.sendMessage(Message.quitTheGame);
    }

    public void endGame(String winnerUsername) {
        //todo a banner or sth
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LoadedScenes.cleanArena();
                WindowChanger.instance.setMainParent(LoadedScenes.mainMenu);
            }
        }, Constant.GameConstants.delayAfterGameEnd);
    }

    public Pane shownCardInformationHolder_pn;
    private Pane shownSpell_pn;
    private Pane shownWarrior_pn;

    public void showInfoOfACard(String name, String description, String type, int HP, int AP /* put anything if the card isn't a warrior */) {
        FXMLLoader spellFXML = new FXMLLoader(LoadedScenes.class.getResource("shownSpellInArena.fxml"));
        FXMLLoader warriorFXML = new FXMLLoader(LoadedScenes.class.getResource("shownWarriorInArena.fxml"));
        try {
            shownSpell_pn = spellFXML.load();
            shownWarrior_pn = warriorFXML.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ShownSomethingInArenaController shownSpellInArenaController = spellFXML.getController();
        ShownWarriorInArenaController shownWarriorInArenaController = warriorFXML.getController();

        if (type.equals("warrior")) {
            shownCardInformationHolder_pn.getChildren().add(shownWarrior_pn);
            shownWarriorInArenaController.setName(name);
            shownWarriorInArenaController.setDescription(description);
            shownWarriorInArenaController.setType("warrior");
            shownWarriorInArenaController.setAP(AP);
            shownWarriorInArenaController.setHP(HP);

            VisualMinion vm = new VisualMinion(name);
            shownWarriorInArenaController.put(vm.view, vm.animation.width, vm.animation.height);
        } else {
            shownCardInformationHolder_pn.getChildren().add(shownSpell_pn);
            shownSpellInArenaController.setName(name);
            shownSpellInArenaController.setDescription(description);
            shownSpellInArenaController.setType("spell");

            VisualSpell vs = new VisualSpell(name);
            shownSpellInArenaController.put(vs.view, vs.animation.width, vs.animation.height);
        }
    }

    public void endShowInfoOfACard() {
        shownCardInformationHolder_pn.getChildren().clear();
    }
}