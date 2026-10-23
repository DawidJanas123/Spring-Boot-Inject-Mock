package com.example.animatedpotatorest.Service;

import com.example.animatedpotatorest.DTO.Enums.Category;
import com.example.animatedpotatorest.DTO.SouvenirDTO;
import com.example.animatedpotatorest.Entity.Souvenir;
import com.example.animatedpotatorest.Repository.SouvenirRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PotatoSouvenirsService {

    private final SouvenirRepository souvenirRepository;

    public PotatoSouvenirsService(SouvenirRepository souvenirRepository) {
        this.souvenirRepository = souvenirRepository;
    }

    public ResponseEntity<SouvenirDTO> addSouvenir(SouvenirDTO souvenirDTO) {

        Optional<Souvenir> _souvenir = souvenirRepository.findBySouvenirName(souvenirDTO.getName(), souvenirDTO.getType());

        if (_souvenir.isPresent()) {
            Souvenir souvenir = _souvenir.get();
            if (isFairPrice(souvenirDTO.getType(), souvenirDTO.getPrice())) {
                if (souvenirDTO.getQuantity() > 0) {
                    try {
                        souvenir.setQuantity(souvenir.getQuantity());
                        souvenirRepository.save(souvenir);
                        return new ResponseEntity<>(souvenirDTO, HttpStatus.OK);
                    } catch (Exception e) {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>(souvenirDTO, HttpStatus.NO_CONTENT);
                }
            } else {
                return new ResponseEntity<>(souvenirDTO, HttpStatus.CONFLICT);
            }
        } else {
            if (isFairPrice(souvenirDTO.getType(), souvenirDTO.getPrice())) {
                try {
                    Souvenir souvenir = new Souvenir();
                    souvenir.setName(souvenirDTO.getName());
                    souvenir.setPrice(souvenirDTO.getPrice());
                    souvenir.setType(souvenirDTO.getType());
                    souvenir.setQuantity(souvenirDTO.getQuantity());
                    souvenirRepository.save(souvenir);
                } catch (Exception e) {
                    return new ResponseEntity<>(souvenirDTO, HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>(souvenirDTO, HttpStatus.CONFLICT);
        }
    }

    private boolean isFairPrice(Category category, Float price) {
        return switch (category) {
            case STATUETTE -> isBetween(price, 9.99f, 19.99f);
            case MAGNET -> isBetween(price, 2.99f, 9.99f);
            case POSTCARD -> isBetween(price, 0.29f, 1.49f);
            default -> false;
        };
    }

    private boolean isBetween(float value, float min, float max) {
        final float eps = 0.0001f;
        return value >= min - eps && value <= max + eps;
    }
}
