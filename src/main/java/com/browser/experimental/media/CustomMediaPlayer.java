package com.browser.experimental.media;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;


public class CustomMediaPlayer {
    public void init() {
        Stage primaryStage = new Stage();
        Media pick = new Media("Audio.mp3"); // replace this with your own audio file
        MediaPlayer player = new MediaPlayer(pick);

        // Add a mediaView, to display the media. Its necessary !
        // This mediaView is added to a Pane
        MediaView mediaView = new MediaView(player);

        // Add to scene
        Group root = new Group(mediaView);
        Scene scene = new Scene(root, 500, 200);

        // Show the stage
        primaryStage.setTitle("Media Player");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Play the media once the stage is shown
        player.play();
    }
}
