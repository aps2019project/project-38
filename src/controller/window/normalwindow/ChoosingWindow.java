package controller.window.normalwindow;

import view.Request;
import view.windowgraphics.WindowGraphic;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChoosingWindow extends NormalWindow {
    private ArrayList<NormalWindow> subWindows = new ArrayList<>();
    private WindowGraphic graphic;

    public ChoosingWindow() {//todo
        super(new ChoosingWindow(), new WindowTitle(""));
    }

    @Override
    public void main() {
        while (true) {
            if (graphic == null) {
                WindowGraphic.getRandomWindowGraphics().showWindowBody(this);
            }else {
                graphic.showWindowBody(this);
            }
            String request = Request.getNextRequest();
            Pattern pattern = Pattern.compile("(\\d+)[\t ]*");
            Matcher matcher = pattern.matcher(request);
            if (matcher.matches()) {
                if (handleRequest(Integer.parseInt(matcher.group(1)))) {
                    break;
                }
            }
            else {
                System.out.println("wrong input");//todo go in messages
            }
        }
    }

    public boolean handleRequest(int request) {
        if (request == 0) {
            this.closeWindow();
            this.superWindow.openWindow();
            return true;
        }
        else if (1 <= request && request <= subWindows.size()) {
            this.closeWindow();
            this.subWindows.get(request - 1).openWindow();
            return true;
        }
        else {
            System.out.println("wrong input");//todo go in messages
            return false;
        }
    }

    public ArrayList<NormalWindow> getSubWindows() {
        return subWindows;
    }
}
