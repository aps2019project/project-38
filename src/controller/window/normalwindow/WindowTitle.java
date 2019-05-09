package controller.window.normalwindow;

public class WindowTitle {
    String title;
    String titleInSuper;
    String titleInSub;

    public WindowTitle(String title, String titleInSuper, String titleInSub) {
        this.title = title;
        this.titleInSuper = titleInSuper;
        this.titleInSub = titleInSub;
    }

    public WindowTitle(String titleAndTitleInSub, String titleInSuper) {
        this.title = titleAndTitleInSub;
        this.titleInSuper = titleInSuper;
        this.titleInSub = titleAndTitleInSub;
    }

    public WindowTitle(String titleInSuper) {
        this.titleInSuper = titleInSuper;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleInSuper() {
        return titleInSuper;
    }

    public String getTitleInSub() {
        return titleInSub;
    }
}
