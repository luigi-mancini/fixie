package FixieGui;



public class Main {
    public static void main(String args[]) throws Exception {
        Preferences pref = new Preferences();
        pref.ReadPreference();

        App app = new App();
        FixieController fc = new FixieController(pref);

        app.setController(fc);
        fc.setView(app);

        app.InitGui(pref);
    }

}