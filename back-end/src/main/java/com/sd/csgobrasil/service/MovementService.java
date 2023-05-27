package com.sd.csgobrasil.service;

import com.sd.csgobrasil.entity.DTO.Report;
import com.sd.csgobrasil.entity.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sd.csgobrasil.repository.MovementRepository;

import java.util.List;

@Service
public class MovementService {
    @Autowired
    private MovementRepository repository;

    public List<Movement> listMovement(){return repository.listMovement();}

    public Movement updateM(Long id, Movement m){return repository.updateM(id, m);}

    public Movement addMovement(Movement m){return repository.addMovement(m);}

    public Movement findByMovementId(Long id){return repository.findById(id);}

    public boolean makeMovement(Long idVenda, Long idComprador){return repository.makeMovement(idVenda, idComprador);}
    public List<Report> makeReport(){return repository.makeReport();}

    public void cancelMovement(Long idVenda){
        repository.cancelMovement(idVenda);
    }
}
