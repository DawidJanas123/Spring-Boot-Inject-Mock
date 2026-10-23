package com.example.animatedpotatorest.Controller;


import com.example.animatedpotatorest.DTO.SouvenirDTO;
import com.example.animatedpotatorest.Service.PotatoSouvenirsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PotatoSouvenirsRestController {

    private final PotatoSouvenirsService potatoSouvenirsService;

    public PotatoSouvenirsRestController(PotatoSouvenirsService potatoSouvenirsService) {
        this.potatoSouvenirsService = potatoSouvenirsService;
    }

    @PostMapping
    public ResponseEntity<SouvenirDTO> addSouvenir(@RequestBody SouvenirDTO souvenirDTO) {
        return potatoSouvenirsService.addSouvenir(souvenirDTO);
    }

}
