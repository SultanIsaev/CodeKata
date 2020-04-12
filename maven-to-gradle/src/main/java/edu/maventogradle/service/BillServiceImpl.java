package edu.maventogradle.service;

import edu.maventogradle.model.Billionaire;
import edu.maventogradle.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository repository;

    @Autowired
    public BillServiceImpl(BillRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Billionaire> findAll() {
        return repository.findAll();
    }
}
