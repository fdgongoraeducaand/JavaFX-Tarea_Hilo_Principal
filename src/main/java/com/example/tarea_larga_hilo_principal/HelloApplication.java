package com.example.tarea_larga_hilo_principal;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private Button btnIniciarBloqueante;
    private Button btnIniciarBien;
    private Label lblEstado;
    private ProgressBar progressBar;
    private TextField campoTexto;
    @Override
    public void start(Stage stage) throws IOException {
        btnIniciarBloqueante = new Button("Iniciar Tarea Bloqueante");
        btnIniciarBien = new Button("Iniciar Tarea BIEN");
        lblEstado = new Label("Esperando...");
        progressBar = new ProgressBar(0);
        campoTexto = new TextField();

        btnIniciarBloqueante.setOnAction(event -> {
            iniciarTareaLarga();
        });

        btnIniciarBien.setOnAction(event -> {
            iniciarTareaLargaSinBloqueo();
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(btnIniciarBloqueante, btnIniciarBien, lblEstado, progressBar, campoTexto);

        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Tarea Larga Bloqueante en JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    private void iniciarTareaLarga() {
        btnIniciarBloqueante.setDisable(true);
        lblEstado.setText("Procesando...");
        progressBar.progressProperty().unbind();

        for (int i = 0; i <= 100; i++) {
            try {
                //Este comando no es un hilo adicional, es un retardo.
                /* Thread.sleep() es un método estático en Java que hace
                                  que el hilo actual se pause durante un tiempo especificado.
                                   En otras palabras, detiene la ejecución del hilo durante
                                   un número determinado de milisegundos.
                */
                Thread.sleep(50); // Simula una tarea larga

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressBar.setProgress(i / 100.0);
        }

        lblEstado.setText("Tarea Completada!");
        btnIniciarBloqueante.setDisable(false);
    }

    private void iniciarTareaLargaSinBloqueo() {
        lblEstado.setText("Procesando...");

        Task<Void> tarea = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(50); // Simula una tarea larga
                    updateProgress(i, 100);
                }
                return null;
            }
        };

        tarea.setOnSucceeded(event -> {
            lblEstado.setText("Tarea Completada!");

        });

        progressBar.progressProperty().bind(tarea.progressProperty());

        Thread hilo = new Thread(tarea);
        hilo.start();
    }

    public static void main(String[] args) {
        launch();
    }
}