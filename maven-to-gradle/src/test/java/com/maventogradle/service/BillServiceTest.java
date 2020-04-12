package com.maventogradle.service;

import edu.maventogradle.model.Billionaire;
import edu.maventogradle.repository.BillRepository;
import edu.maventogradle.service.BillService;
import edu.maventogradle.service.BillServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BillServiceTest {

    @Mock
    private BillRepository repository;

    private BillService service;

    private Billionaire sam;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        service = new BillServiceImpl(repository);

        sam = new Billionaire();
        sam.setFirstName("Sam");
        sam.setLastName("Walton");
        sam.setCareer("Founder the retailers Walmart and Sam's Club");
    }

    @Test
    public void findAll_Success() {
        //given
        when(repository.findAll()).thenReturn(List.of(sam));
        //when
        List<Billionaire> all = service.findAll();
        //then
        assertThat(all.contains(sam));
    }
}
