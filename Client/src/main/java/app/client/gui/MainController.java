package app.client.gui;

import app.model.*;
import app.services.AppException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import app.services.IObserver;
import app.services.IServices;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class MainController implements Initializable, IObserver {
    @FXML
    public TableView<GameDTO> clasamentTable;
    @FXML
    public TableColumn<GameDTO,String> aliasColumn;
    @FXML
    public TableColumn<GameDTO,String> scoreColumn;
    @FXML
    public TableColumn<GameDTO,String> durationColumn;
    @FXML
    private GridPane jocGrid;
    @FXML
    private Button searchButton;

    private IServices server;
    private Player loggedUser;
    private Stage stage;
    boolean finished;
    int score;
    Position position;
    LocalDateTime start;
    private Button[][] cells = new Button[5][5];

    public MainController() {
        System.out.println("MainController constructor");
    }

    public MainController(IServices server) {
        this.server = server;
        System.out.println("MainController constructor with server parameters");
    }

    public void setServer(IServices server) {
        this.server = server;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        aliasColumn.setCellValueFactory(new PropertyValueFactory<>("Alias"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("Duration"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("Score"));

    }

    public void handleLogout(ActionEvent actionEvent) {
        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    void logout() {

    }

    private void initModel() throws AppException {
        finished = false;
        score = 0;
        start = LocalDateTime.now();
        jocGrid.getChildren().clear();
        position = new Position(null,"Gol","Gol","Gol","Gol","Gol","Gol","Gol","Gol","Gol");
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button();
                button.setPrefSize(50, 50);
                button.setUserData(new int[]{row, col});
                button.setOnMouseClicked(this::handleClickedButton);
                button.setStyle("-fx-text-fill: white");
                cells[row][col] = button;
                cells[row][col].setText("Gol");
                jocGrid.add(button, col, row);
            }
        }
    }


    private void handleClickedButton(MouseEvent mouseEvent) {
        try{
            Button clickedButton = (Button) mouseEvent.getSource();
            int[] coords = (int[]) clickedButton.getUserData();
            int row = coords[0];
            int col = coords[1];
            cells[row][col].setStyle("-fx-text-fill: black");
            cells[row][col].setDisable(true);
            cells[row][col].setText("X");
            if(row == 0)
            {
                if(col == 0) position.setPos1("X");
                else if(col == 1) position.setPos2("X");
                else if(col == 2) position.setPos3("X");
            }
            else if(row == 1)
            {
                if(col == 0) position.setPos4("X");
                else if(col == 1) position.setPos5("X");
                else if(col == 2) position.setPos6("X");
            }
            else if(row == 2){
                if(col == 0) position.setPos7("X");
                else if(col == 1) position.setPos8("X");
                else if(col == 2) position.setPos9("X");
            }
            int rowS = row;
            int colS = col;
            int gata = 1;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(cells[i][j].getText().equals("Gol")) gata=0;
                }
            }
            if(gata == 1) finished=true;
            while(!cells[rowS][colS].getText().equals("Gol") && gata==0){
                Random rand = new Random();
                rowS = rand.nextInt(0,3);
                colS = rand.nextInt(0,3);
            }
            cells[rowS][colS].setDisable(true);
            cells[rowS][colS].setText("O");
            cells[rowS][colS].setStyle("-fx-text-fill: black");
            if(rowS == 0)
            {
                if(colS == 0) position.setPos1("O");
                else if(colS == 1) position.setPos2("O");
                else if(colS == 2) position.setPos3("O");
            }
            else if(rowS == 1)
            {
                if(colS == 0) position.setPos4("O");
                else if(colS == 1) position.setPos5("O");
                else if(colS == 2) position.setPos6("O");
            }
            else if(rowS == 2){
                if(colS == 0) position.setPos7("O");
                else if(colS == 1) position.setPos8("O");
                else if(colS == 2) position.setPos9("O");
            }
            if(!finished) {
                if (verifGame("X")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ai castigat");
                    alert.showAndWait();
                    score = 10;
                    endGame();
                } else if (verifGame("O")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ai pierdut");
                    alert.showAndWait();
                    score = -10;
                    endGame();
                }
            }
            else {score = 5; endGame();}
        }
        catch(AppException e){
            System.out.println(e.getMessage());
        }
    }

    public void endGame() throws AppException {
        Player player = server.findPlayerByAlias(loggedUser.getAlias());
        Game game = new Game(player,score,start, LocalDateTime.now());
        server.addGame(game);
        Game game1 = server.getAllGamesForPlayer(player).get(0);
        position.setGame(game1);
        server.addPosition(position);
        List<Position> poss = server.getAllPositions();
        for(Position p : poss){
            System.out.println(p);
        }
        initModel();
    }

    private void loadLeaderboard() {
        try {
            List<Game> gameList = server.getAllGames();
            ObservableList<GameDTO> games = FXCollections.observableArrayList();
            for(Game g : gameList) {
                Player p = server.findPlayerByAlias(g.getPlayer().getAlias());
                GameDTO dto = new GameDTO(p.getAlias(), Duration.between(g.getStartTime(),g.getEndTime()).toSeconds(),g.getScore());
                games.add(dto);
            }
            List<GameDTO> sortedGames = games.stream()
                    .sorted(Comparator.comparing(GameDTO::getScore))
                    .collect(Collectors.toList());
            clasamentTable.setItems(FXCollections.observableList(sortedGames));
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    private boolean verifGame(String x) {
        if(position.getPos1().equals(x) && position.getPos4().equals(x) && position.getPos7().equals(x) ||
        position.getPos2().equals(x) && position.getPos5().equals(x) && position.getPos8().equals(x) ||
        position.getPos3().equals(x) && position.getPos6().equals(x) && position.getPos9().equals(x) ||
        position.getPos1().equals(x) && position.getPos2().equals(x) && position.getPos3().equals(x) ||
        position.getPos4().equals(x) && position.getPos5().equals(x) && position.getPos6().equals(x) ||
        position.getPos7().equals(x) && position.getPos8().equals(x) && position.getPos9().equals(x) ||
        position.getPos1().equals(x) && position.getPos5().equals(x) && position.getPos9().equals(x) ||
        position.getPos3().equals(x) && position.getPos5().equals(x) && position.getPos7().equals(x)) return true;
        return false;
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) searchButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void gameAdded(Game game) throws AppException {
        Platform.runLater(this::loadLeaderboard);
    }

    public void setLoggedUser(Player user) throws AppException {
        this.loggedUser = user;
        initModel();
        loadLeaderboard();
    }
}