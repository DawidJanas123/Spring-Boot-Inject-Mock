package com.example.animatedpotatorest;

import com.example.animatedpotatorest.DTO.Enums.Category;
import com.example.animatedpotatorest.DTO.SouvenirDTO;
import com.example.animatedpotatorest.Entity.Souvenir;
import com.example.animatedpotatorest.Repository.SouvenirRepository;

import com.example.animatedpotatorest.Service.PotatoSouvenirsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AnimatedPotatoRestRepositoryTesting {

    @Mock
    private SouvenirRepository souvenirRepository;

    @InjectMocks
    private PotatoSouvenirsService potatoSouvenirsService;

    private SouvenirDTO souvenirDTO = new SouvenirDTO();
    private Souvenir souvenir = new Souvenir();

    @Before
    public void setUp() {
        souvenirDTO = new SouvenirDTO();
        souvenirDTO.setName("little angel");
        souvenirDTO.setPrice(15.45f);
        souvenirDTO.setQuantity(5);
        souvenirDTO.setType(Category.STATUETTE);

        souvenir = new Souvenir();
        souvenir.setName("little angel");
        souvenir.setPrice(15.45f);
        souvenir.setQuantity(10);
        souvenir.setType(Category.STATUETTE);
    }

    @Test
    public void testFindBySouvenirName() {

        Mockito.when(
                souvenirRepository.findBySouvenirName(
                        "little angel", Category.STATUETTE
                )
        ).thenReturn(Optional.of(souvenir));


        Optional<Souvenir> result =
                souvenirRepository.findBySouvenirName(
                        "little angel", Category.STATUETTE
                );


        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("little angel", result.get().getName());
        Assert.assertEquals(Category.STATUETTE, result.get().getType());

        Mockito.verify(souvenirRepository)
                .findBySouvenirName("little angel", Category.STATUETTE);
    }

    @Test
    public void shouldReturnOkWhenExistingSouvenirHasFairPrice() {
        Mockito.when(souvenirRepository.findBySouvenirName(
                souvenirDTO.getName(),
                souvenirDTO.getType())).thenReturn(Optional.of(souvenir));

        ResponseEntity<SouvenirDTO> response = potatoSouvenirsService.addSouvenir(souvenirDTO);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(souvenirRepository).save(souvenir);
    }

    @Test
    public void shouldReturnConflictWhenPriceIsInvalid() {
        souvenirDTO.setPrice(30.00f);

        Mockito.when(souvenirRepository.findBySouvenirName(
                        souvenirDTO.getName(), souvenirDTO.getType()))
                .thenReturn(Optional.of(souvenir));

        ResponseEntity<SouvenirDTO> response = potatoSouvenirsService.addSouvenir(souvenirDTO);

        Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Mockito.verify(souvenirRepository, Mockito.never()).save(Mockito.any(Souvenir.class));
    }

    @Test
    public void shouldReturnNoContentWhenQuantityIsZero() {
        souvenirDTO.setQuantity(0);

        Mockito.when(souvenirRepository.findBySouvenirName(
                souvenirDTO.getName(),
                souvenirDTO.getType())).thenReturn(Optional.of(souvenir));

        ResponseEntity<SouvenirDTO> response = potatoSouvenirsService.addSouvenir(souvenirDTO);

        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(souvenirRepository, Mockito.never()).save(Mockito.any(Souvenir.class));
    }

    @Test
    public void shouldReturnBadRequestWhenSaveFails() {
        Mockito.when(souvenirRepository.findBySouvenirName(
                souvenirDTO.getName(), souvenirDTO.getType())).thenReturn(Optional.of(souvenir));

        Mockito.when(souvenirRepository.save(souvenir)).thenThrow(new RuntimeException());

        ResponseEntity<SouvenirDTO> response = potatoSouvenirsService.addSouvenir(souvenirDTO);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Mockito.verify(souvenirRepository).save(souvenir);
    }
}