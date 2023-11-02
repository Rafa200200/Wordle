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

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class pt_lvl5_controller implements Initializable {
    @FXML
    private TextField guessInput;

    @FXML
    private Label box00 = new Label();

    @FXML
    private Label box01 = new Label();

    @FXML
    private Label box02 = new Label();

    @FXML
    private Label box03 = new Label();

    @FXML
    private Label box04 = new Label();

    @FXML
    private Label box05 = new Label();

    @FXML
    private Label box06 = new Label();

    @FXML
    private Label t = new Label();

    @FXML
    private Button bt = new Button();

    @FXML
    private ImageView home_bt;

    private int trest = 7;

    private String word = "teclado";
    private List<String> wordList = new ArrayList<>();

    @FXML
    protected void checkGuess() {
        Label[] row = {box00, box01, box02, box03, box04, box05, box06};
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

        if (guessLowerCase.length() != 7 ) {
            // Criar o objeto Alerta
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Palavra inválida");
            alerta.setHeaderText(null);
            alerta.setContentText("A palavra precisa de ter 7 letras.");
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
            {
                {
                    // Criar o objeto Alerta
                    Alert alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("PARABÉNS !\n");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Parabéns, venceu os 5 níveis !! \n");
                    // Exibir o alerta
                    alerta.showAndWait();
                    Platform.exit();
                }
                Platform.exit();
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
