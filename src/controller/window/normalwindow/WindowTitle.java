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

    public WindowTitle(String titleAndTitleInSuper, String titleInSub) {
        this.title = titleAndTitleInSuper;
        this.titleInSuper = titleAndTitleInSuper;
        this.titleInSub = titleInSub;
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
