package view.fxmlControllers;

import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import view.WindowChanger;
import view.fxmls.LoadedScenes;

public class ChooseBattleKindController {

    public ImageView single_btn;
    public ImageView two_btn;
    public ImageView story_btn;
    BoxBlur boxBlur = new BoxBlur(5, 5, 1);

    public void singlePlayer() {
        //todo
    }

    public void single_enter() {
        single_btn.setEffect(null);
    }

    public void single_exit() {
        single_btn.setEffect(boxBlur);
    }

    //---------------

    public void twoPlayer() {
        //todo
    }

    public void two_enter() {
        two_btn.setEffect(null);
    }

    public void two_exit() {
        two_btn.setEffect(boxBlur);
    }

    //---------------

    public void storyMode() {
        //todo
    }

    public void story_enter() {
        story_btn.setEffect(null);
    }

    public void story_exit() {
        story_btn.setEffect(boxBlur);
    }

    //---------------

    public void back() {
        WindowChanger.instance.setMainParent(LoadedScenes.mainMenu);
    }
}
