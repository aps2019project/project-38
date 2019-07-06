package view.fxmlControllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.cards.CardCustomizer;
import model.exceptions.NotEnoughConditions;
import view.Utility;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

import java.net.URL;
import java.util.ResourceBundle;

public class MoreCustomCardController implements Initializable {
    public TextField sName;
    public ChoiceBox sTarget;
    public TextField sPrice;
    public TextField sHBD;
    public TextField sPD;
    public TextField sPWBD;
    public TextField sPWBHP;
    public CheckBox sHB;
    public CheckBox sP;
    public CheckBox sPWB;
    public CheckBox sS;
    public CheckBox sD;
    public TextField sSD;
    public TextField sDD;
    public TextField sPWBAP;
    public TextField sHBA;
    public TextField sDes;
    public Spinner sMana;

    public TextField mName;
    public TextField mAP;
    public TextField mHP;
    public CheckBox mMeele;
    public CheckBox mRanged;
    public Spinner mRange;
    public ChoiceBox mO;
    public ChoiceBox mT;
    public TextField mDD;
    public TextField mSD;
    public TextField mPWBD;
    public TextField mPWBHP;
    public CheckBox mHB;
    public CheckBox mP;
    public CheckBox mPWB;
    public CheckBox mS;
    public CheckBox mD;
    public TextField mPD;
    public TextField mHBD;
    public TextField mPWBAP;
    public TextField mHBA;
    public TextField mPrice;
    public TextField mDes;
    public Spinner mMana;

    public TextField hName;
    public TextField hAP;
    public TextField hHP;
    public CheckBox hMelee;
    public CheckBox hRanged;
    public Spinner hRange;
    public TextField hPrice;
    public Spinner hCool;
    public ChoiceBox hTar;
    public TextField hSD;
    public TextField hDD;
    public TextField hPD;
    public TextField hPWBHP;
    public CheckBox hHB;
    public CheckBox hP;
    public CheckBox hPWB;
    public CheckBox hS;
    public CheckBox hD;
    public TextField hHBD;
    public TextField hPWBD;
    public TextField hPWBAP;
    public TextField hHBA;
    public TextField hDes;
    public Spinner hMana;
    public TextField hSPName;

    public TabPane tp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sTarget.setItems(FXCollections.observableArrayList("A 2*2 Square(Friends)", "A 2*2 Square(Enemies)", "A Friend Warrior", "An Opponent Warrior", "All Opponent Warriors", "All Friend Warriors", "Random Enemy", "Random Friend"));
        sMana.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9));

        mRange.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12));
        mO.setItems(FXCollections.observableArrayList("Attack", "Spawn", "Death", "Turn End", "Defend", "Passive"));
        mT.setItems(FXCollections.observableArrayList("Adjacent cells(Friends)","Adjacent cells(Enemies)", "All Friend Warriors", "All Enemy Warriors", "Enemy Hero", "Friend Hero", "Random Enemy", "Random Friend", "Self", "Attacker(for on attack)"));
        mMana.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9));

        hRange.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12));
        hCool.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        hTar.setItems(FXCollections.observableArrayList("A 2*2 Square(Friends)", "A 2*2 Square(Enemies)", "A Friend Warrior", "An Opponent Warrior", "All Opponent Warriors", "All Friend Warriors", "Random Enemy", "Random Friend"));
        hMana.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9));
    }

    public void sub() {
        try {
            new CardCustomizer(this).build();
            can();
        } catch (NotEnoughConditions notEnoughConditions) {
            Utility.showMessage(notEnoughConditions.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Utility.showMessage("Wrong inputs");
        }
    }

    public void can() {
        LoadedScenes.cleanCustomCard();
        WindowChanger.instance.setNewScene(LoadedScenes.mainMenu);
    }
}
