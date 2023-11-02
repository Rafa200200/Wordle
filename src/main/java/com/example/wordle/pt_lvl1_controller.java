package com.example.wordle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class pt_lvl1_controller implements Initializable {
    @FXML
    private TextField guessInput;

    @FXML
    private Label box00 = new Label();

    @FXML
    private Label box01 = new Label();

    @FXML
    private Label box02 = new Label();

    private String word = "cao";

    @FXML
    private Button bt = new Button();

    @FXML
    private Label t = new Label();

    @FXML
    private ImageView home_bt;

    private int trest = 3;
    private List<String> wordList = new ArrayList<>();

    @FXML
    protected void checkGuess() {
        Label[] row = {box00, box01, box02};
        String guess = guessInput.getText();
        String guessLowerCase = guess.toLowerCase();
        if (!wordList.contains(guessLowerCase)) {
            // Criar o objeto Alerta
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Palavra inválida");
            alerta.setHeaderText(null);
            alerta.setContentText("Esta palavra não existe");
            // Exibir o alerta
            alerta.showAndWait();

            return; // Sai do método sem executar o restante do código
        }

        if (guessLowerCase.length() != 3 ) {
            // Criar o objeto Alerta
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Palavra inválida");
            alerta.setHeaderText(null);
            alerta.setContentText("A palavra precisa de ter 3 letras.");
            // Exibir o alerta
            alerta.showAndWait();

            return; // Sai do método sem executar o restante do código
        }

        for (int i = 0; i < guessLowerCase.length(); i++) {
            String letter = guessLowerCase.substring(i, i + 1);
            row[i].setText(letter);
            if (letter.equals(word.substring(i, i + 1))) {
                row[i].setStyle("-fx-background-color: #8eeda1;");
            } else if (word.indexOf(letter) > -1) {
                row[i].setStyle("-fx-background-color: yellow");
            } else {
                row[i].setStyle("-fx-background-color: #fd3412;");
            }
        }
        if (guessLowerCase.equals(word)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("pt_lvl2.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                Stage stage = (Stage) bt.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!word.equals(guessLowerCase)) {
            diminuirTentativas();
            atualizarLabelTentativas();
        }
    }

    @FXML
    private void click_home() {
        // Cria o alerta de confirmação
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText(null);
        alert.setContentText("Deseja voltar à página inicial?");

        // Obtém a janela atual
        Stage currentStage = (Stage) home_bt.getScene().getWindow();

        // Exibe o alerta e aguarda a resposta do usuário
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // O utilizador clicou em "OK", então volta à página inicial
            try {
                Parent root = FXMLLoader.load(getClass().getResource("language.fxml"));
                Scene scene = new Scene(root);
                currentStage.setScene(scene);
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para atualizar a label com o número de tentativas restantes
    private void atualizarLabelTentativas() {
        t.setText(Integer.toString(trest));
    }

    // Método para diminuir o número de tentativas restantes
    private void diminuirTentativas() {
        trest--;
        atualizarLabelTentativas();

        // Verifica se as tentativas acabaram
        if (trest == 0) {
            // Criar o objeto Alerta
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Game-Over");
            alerta.setHeaderText(null);
            alerta.setContentText("Esgotou todas as suas tentativas :(( !!");

            // Exibir o alerta
            alerta.showAndWait();

            Platform.exit();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        t.setText(String.valueOf(trest));

        // Leitura do arquivo words.txt e armazenamento das palavras em wordList
        try {
            InputStream inputStream = getClass().getResourceAsStream("ptwords.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            wordList = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                wordList.add(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        bt.setOnAction(e -> checkGuess()); // Ação do botão verificar

        atualizarLabelTentativas();
    }
}
