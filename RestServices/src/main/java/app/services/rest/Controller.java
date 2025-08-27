package app.services.rest;

import app.model.*;
import app.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin
@RestController

@RequestMapping("/app/exam")
public class Controller {

    private static final String template = "Hello, %s!";

    @Autowired
    private IGameRepository gameRepository;
    @Autowired
    private IPlayerRepository playerRepository;
    @Autowired
    private IConfigurationRepository configurationRepository;
    @Autowired
    private IPositionRepository positionRepository;
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @GetMapping("/player/{player_alias}/games")
    public List<Game> getGamesByPlayer(@PathVariable String player_alias) {
        List<Game> games = (List<Game>) gameRepository.findAllByPlayer(new Player(player_alias));
        return games.stream()
                .sorted(Comparator.comparing(
                        g -> Duration.between(g.getStartTime(), g.getEndTime()).toSeconds(),
                        Comparator.reverseOrder()
                ))
                .collect(Collectors.toList());
    }

    @PutMapping("/positions/{game_id}/{position_r}/{position_c}")
    public ResponseEntity<?> createConfiguration(
            @RequestBody Position configuration,
            @PathVariable Integer game_id,
            @PathVariable Integer position_r,
            @PathVariable Integer position_c) {

        Position optionalPosition = new Position();
        Iterable<Position> positionsIterable = positionRepository.findAll();
        for (Position position : positionsIterable) {
            System.out.println(position.getGame().getId());
            if(position.getGame().getId() == Long.parseLong(String.valueOf(game_id))) {
                optionalPosition.setId(position.getId());
                optionalPosition.setGame(position.getGame());
                optionalPosition.setPos1(position.getPos1());
                optionalPosition.setPos2(position.getPos2());
                optionalPosition.setPos3(position.getPos3());
                optionalPosition.setPos4(position.getPos4());
                optionalPosition.setPos5(position.getPos5());
                optionalPosition.setPos6(position.getPos6());
                optionalPosition.setPos7(position.getPos7());
                optionalPosition.setPos8(position.getPos8());
                optionalPosition.setPos9(position.getPos9());
                break;
            }
        }

        int index = (position_r - 1) * 3 + (position_c - 1);

        switch (index) {
            case 0: optionalPosition.setPos1("X"); break;
            case 1: optionalPosition.setPos2("X"); break;
            case 2: optionalPosition.setPos3("X"); break;
            case 3: optionalPosition.setPos4("X"); break;
            case 4: optionalPosition.setPos5("X"); break;
            case 5: optionalPosition.setPos6("X"); break;
            case 6: optionalPosition.setPos7("X"); break;
            case 7: optionalPosition.setPos8("X"); break;
            case 8: optionalPosition.setPos9("X"); break;
            default: return new ResponseEntity<>("Invalid position", HttpStatus.BAD_REQUEST);
        }

        try {
            Position updatedP = positionRepository.update(optionalPosition).orElse(null);
            return new ResponseEntity<>(updatedP, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}
