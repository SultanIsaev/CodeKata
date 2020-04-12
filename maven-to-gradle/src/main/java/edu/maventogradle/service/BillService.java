package edu.maventogradle.service;

import edu.maventogradle.model.Billionaire;

import java.util.List;

public interface BillService {
    List<Billionaire> findAll();
}
