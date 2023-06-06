package com.sd.csgobrasil.service;

import com.sd.csgobrasil.entity.DTO.SkinMovement;
import com.sd.csgobrasil.entity.Movement;
import com.sd.csgobrasil.entity.Skin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sd.csgobrasil.repository.SkinRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkinService {
    @Autowired
    private SkinRepository repository;

    public List<Skin> listSkins() {
        return repository.listSkins();
    }

    public Skin getSkinInfo(Skin skin) {
        return repository.getSkinInfo(skin);
    }

    public Skin addSkin(Skin skin) {
        return repository.addSkin(skin);
    }

    public Skin updateSkin(Long id, Skin skin) {
        return repository.updateSkin(id, skin);
    }

    public Skin findBySkinId(Long id) {
        return repository.findById(id);
    }

    public List<SkinMovement> getSkinMovements(List<Movement> movements){
        List<SkinMovement> movementsList = new ArrayList<>();
        for (Movement movement : movements) {
            SkinMovement skinMovement = new SkinMovement();
            Skin skin = findBySkinId(movement.getIdSkin());
            skinMovement.setNome(skin.getNome());
            skinMovement.setArma(skin.getArma());
            skinMovement.setRaridade(skin.getRaridade());
            skinMovement.setPreco(skin.getPreco());
            skinMovement.setImagem(skin.getImagem());
            skinMovement.setIdVenda(movement.getIdVenda());
            movementsList.add(skinMovement);
        }
        return movementsList;
    }

    public void deleteSkin(Long id){
        repository.deleteSkin(id);
    }
}
