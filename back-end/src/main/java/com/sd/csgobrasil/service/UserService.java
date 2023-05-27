package com.sd.csgobrasil.service;

import com.sd.csgobrasil.entity.DTO.UserLogin;
import com.sd.csgobrasil.entity.DTO.UserRegister;
import com.sd.csgobrasil.entity.DTO.UserSkin;
import com.sd.csgobrasil.entity.Skin;
import com.sd.csgobrasil.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sd.csgobrasil.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> listUsers() {return repository.listUsers();}

    public boolean checkIfUserExist(UserRegister userRegister) {
        return repository.checkIfUserExist(userRegister);
    }

    public boolean checkLoginUser(UserLogin userLogin){return repository.checkLoginUser(userLogin);}

    public User getUserInfo(String email){
        return repository.getUserInfo(email);
    }

    public User addUser(User user){
        return repository.addUser(user);
    }

    public User updateUser(Long id, User user){return repository.updateUser(id, user);}

    public User findByUserId(Long id){return repository.findById(id);}

    public void deleteUser(Long id){repository.deleteUser(id);}

    public UserSkin addSkinFromUser(Long idSkin, Long idUser){return repository.addSkinFromUser(idSkin,idUser);}

    public void deleteSkinFromUser(Skin skin, User user) {repository.deleteSkinFromUser(skin,user);}

    public List<Skin> listSkinsFromUser(Long idUser){return repository.listSkinsFromUser(idUser);}

}
