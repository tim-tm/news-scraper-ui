package me.tim;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import de.scraper.NewsEntry;
import de.scraper.TagesschauAPI;

public class App extends Application {
    private static ArrayList<NewsEntry> entries;
    private static final TagesschauAPI tagesschauAPI = new TagesschauAPI();
    private final ArrayList<Button> buttons = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        ScrollPane pane = new ScrollPane();
        VBox box = new VBox();
        box.setSpacing(10);
        box.setPadding(new Insets(10));

        for (NewsEntry entry : entries) {
            Button btn = new Button(entry.getTitle() + " - " + entry.getDate().split("T")[0]);
            btn.setOnMouseClicked(event -> {
                getHostServices().showDocument(entry.getShareURL());
            });
            buttons.add(btn);
        }
        box.getChildren().addAll(buttons);
        pane.setContent(box);
        pane.setPannable(true);

        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        entries = tagesschauAPI.fetchNews(1, false);
        tagesschauAPI.sortByTags(entries);
        launch();
    }
}
