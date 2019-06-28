package view.fxmlControllers;

import controller.window.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomCardController implements Initializable {
    //two girds:
    public GridPane buffGrid;
    public GridPane generalGrid;

    //general grid items:
    public ChoiceBox<String> type_chb;
    public ChoiceBox<String> spellTarget_chb;
    public ChoiceBox<String> warriorAttackType_chb;
    public ChoiceBox<String> warriorSpecialPower_chb;
    public ChoiceBox<String> minionSpecialPowerActivation_chb;
    public TextField cardName_tf;
    public TextField warriorAP_tf;
    public TextField warriorHP_tf;
    public TextField warriorRange_tf;
    public TextField cardCost_tf;
    public TextField heroSpecialPowerCoolDown_tf;

    //buff grid items:
    public ChoiceBox<String> buffType_chb;
    public ChoiceBox<String> buffFriendOrEnemy_chb;
    public TextField buffEffectValue_tf;
    public TextField buffDelay_tf;
    public TextField buffLast_tf;

    //lists of choice boxes:
    private ObservableList<String> type_list = FXCollections.observableArrayList("Spell", "Minion", "Hero");
    private ObservableList<String> spellTarget_list = FXCollections.observableArrayList("Opponent Hero", "Friend Hero", "A 2*2 Square", "A Friend Warrior", "An Opponent Warrior", "All Opponent Warriors", "All Friend Warriors", "A Random Warrior");
    private ObservableList<String> warriorAttackType_list = FXCollections.observableArrayList("Ranged", "Melee", "Hybrid");
    private ObservableList<String> warriorSpecialPower_list = FXCollections.observableArrayList(
            "Stuns opponent warrior for current turn (ATTACK)",
            "Attacks 5 times more than number of previous attacks (ATTACK)",
            "Disarms opponent warrior for one turn and poison it for 4 turn (ATTACK)",
            "Has 10 numbers PowerBuf with HP increment (PASSIVE)",
            "Attacks on around opponent minions (on nearest 8 cells) by 2 numbers (DEATH)",
            "Poisons opponent warrior by 3 turn (ATTACK)",
            "HolyBuff has no effect on attack of this warrior (ATTACK)",
            "Opponent minions with a 2 manhattan distance, attacked 1 more number on each being attacked (SPAWN)",
            "HP of this minion decreased by 6 and 4 numbers in two coming turns after attacking on an opponent minion (ATTACK)",
            "HP of this minion decreased by 8 numbers in coming turn after attacking on an opponent minion (ATTACK)",
            "On coming turn, it's HP decreased by 6 number (ATTACK)",
            "Gives a Power buff with +2 AP and a Weakness buff with -1 HP to itself and every friendly minion in 8 adjacent spaces (PASSIVE)",
            "Gives a Power buff with +2 AP and a Holy buff to every friendly minion in 8 adjacent spaces (PASSIVE)",
            "Gives all friendly minions a passive Power buff with +1 AP (TURN)",
            "Deals 16 damage to a random enemy minion (SPAWN)",
            "Disables every positive affects of attacked minion (ATTACK)",
            "Stun enemy minions in 8 adjacent spaces for one turn (SPAWN)",
            "Has 12 numbers of HolyBuf with CONTINUOUS mode (PASSIVE)",
            "Deals 6 damage to enemy Hero on death (DEATH)");
    private ObservableList<String> minionSpecialPowerActivation_list = FXCollections.observableArrayList("On Attack", "On Spawn", "On Death", "On Turn", "On Defend", "Passive", "Combo");
    private ObservableList<String> buffType_list = FXCollections.observableArrayList("Holy", "Power", "Poison", "Weakness", "Stun", "Disarm");
    private ObservableList<String> buffFriendOrEnemy_list = FXCollections.observableArrayList("Friend", "Enemy");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPreValues();
    }

    //use this function before each enter to custom card menu:
    public void setPreValues() {
        clearForNewUse();
        type_chb.setItems(type_list);
        type_chb.setValue("Spell");
        //
        spellTarget_chb.setItems(spellTarget_list);
        spellTarget_chb.setValue("Opponent Hero");
        //
        warriorAttackType_chb.setItems(warriorAttackType_list);
        warriorAttackType_chb.setValue("Ranged");
        //
        warriorSpecialPower_chb.setItems(warriorSpecialPower_list);
        warriorSpecialPower_chb.setValue("Stuns opponent warrior for current turn (ATTACK)");
        //
        minionSpecialPowerActivation_chb.setItems(minionSpecialPowerActivation_list);
        minionSpecialPowerActivation_chb.setValue("On Attack");
        //
        buffType_chb.setItems(buffType_list);
        buffType_chb.setValue("Holy");
        //
        buffFriendOrEnemy_chb.setItems(buffFriendOrEnemy_list);
        buffFriendOrEnemy_chb.setValue("Friend");
    }

    private void clearForNewUse() {
        for (Node node : generalGrid.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).clear();
            }
            if (node instanceof ChoiceBox) {
                ((ChoiceBox) node).getItems().clear();
            }
        }
    }


    // buttons :

    public void back_btn() {
        WindowChanger.instance.setNewScene(LoadedScenes.mainMenu);
    }

    public void apply_btn() {
        // todo for MOEINI
    }

    //--------.: GETTERS :.---------


    public String getCardName() {
        return cardName_tf.getText();
    }

    public String getCardType() {
        return type_chb.getValue();
    }

    public String getTargetOfSpell() {
        return spellTarget_chb.getValue();
    }

    public String getWarriorAP() {
        return warriorAP_tf.getText();
    }

    public String getWarriorHP() {
        return warriorHP_tf.getText();
    }

    public String getWarriorSttackType() {
        return warriorAttackType_chb.getValue();
    }

    public int getWarriorRange() {
        try {
            return Integer.parseInt(warriorRange_tf.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getWarriorSpecialPower() {
        return warriorSpecialPower_chb.getValue();
    }

    public String getMinionSpecialPowerActication() {
        return minionSpecialPowerActivation_chb.getValue();

    }

    public int getHeroSpecialPowerCoolDown() {
        try {
            return Integer.parseInt(heroSpecialPowerCoolDown_tf.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getCardCost() {
        try {
            return Integer.parseInt(cardCost_tf.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getBuffType() {
        return buffType_chb.getValue();
    }

    public int getEffectValue() {
        try {
            return Integer.parseInt(buffEffectValue_tf.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getBuffDelay() {
        try {
            return Integer.parseInt(buffDelay_tf.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getBuffLastDuration() {
        try {
            return Integer.parseInt(buffLast_tf.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getBuffFriendOrEnemy() {
        return buffFriendOrEnemy_chb.getValue();
    }
}
