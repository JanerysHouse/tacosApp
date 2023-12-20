package ru.rgroupe.springinaction.tacosapp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rgroupe.springinaction.tacosapp.entities.Tacos;
import ru.rgroupe.springinaction.tacosapp.repositories.TacoRepository;

import java.util.Optional;

@RestController
@RequestMapping(path="/api/tacos",
        produces="application/json")
@CrossOrigin(origins="http://tacocloud:8080")
@RequiredArgsConstructor
public class TacoRestController {
    private final TacoRepository tacoRepo;

    @GetMapping(params = "recent")
    public Iterable<Tacos> recentTacos() {
        PageRequest page = PageRequest.of(
                0, 12,
                Sort.by("createdAt").descending());

        return tacoRepo.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tacos> getTacoById(@PathVariable("id") Long id) {
        Optional<Tacos> optional = tacoRepo.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Tacos postAddTacos(@RequestBody Tacos tacos) {
        return tacoRepo.save(tacos);
    }







}
