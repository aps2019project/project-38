package view.fxmlControllers.cardHolder;

import javafx.scene.text.Text;

public class ShownWarriorInArenaController extends ShownSomethingInArenaController {
    public Text apText;
    public Text hpText;

    public void setAP(int AP) {
        this.apText.setText(String.valueOf(AP));
    }

    public void setHP(int HP) {
        this.hpText.setText(String.valueOf(HP));
    }
}
