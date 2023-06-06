package com.sd.csgobrasil.repository;

import com.sd.csgobrasil.conn.ConnectionJdbc;
import com.sd.csgobrasil.entity.DTO.UserLogin;
import com.sd.csgobrasil.entity.DTO.UserRegister;
import com.sd.csgobrasil.entity.DTO.UserSkin;
import com.sd.csgobrasil.entity.Skin;
import com.sd.csgobrasil.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private List<User> usuariosCadastrados;
    private PreparedStatement st;
    private ResultSet rs;

    @Autowired
    private SkinRepository skinRepository;

    public UserRepository() {
        this.usuariosCadastrados =  new ArrayList<>();
    }

    public List<User> listUsers() {
        usuariosCadastrados.clear();
        User user;
        String mySql = "SELECT * FROM user";

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            rs = st.executeQuery();
            while (rs.next()) {
                user = getUser();
                usuariosCadastrados.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários\n" + e);
        }
        usuariosCadastrados = fillSkins(usuariosCadastrados);
        return usuariosCadastrados;
    }

    private List<User> fillSkins(List<User> users){
        for (User user : users) {
            user.setSkinsPossuidas(listSkinsFromUser(user.getId()));
        }
        return users;
    }

    public boolean checkIfUserExist(UserRegister userRegister) {
        String  mySql = "SELECT * FROM user WHERE user.email = '%s' or user.nome= '%s'".formatted(userRegister.getEmail(),userRegister.getNome());

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            rs = st.executeQuery();
            if(rs.next()){
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Erro na execução da querry\n" + e);
        }

        return false;
    }

    public boolean checkLoginUser(UserLogin userLogin){
        String mySql = "SELECT * FROM user WHERE user.email = '%s'".formatted(userLogin.getEmail());

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            rs = st.executeQuery();
            if (rs.next() && userLogin.getEmail().equals(rs.getString("email")) &&
                    userLogin.getSenha().equals(rs.getString("senha"))) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Erro na execução da querry\n" + e);
        }

        return false;
    }

    public User getUserInfo(String email) {
        User user;
        String mySql = "SELECT * FROM user WHERE user.email = '%s'".formatted(email);

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            rs = st.executeQuery();
            rs.next();
            user = getUser();
            if (user.getEmail() != null) {
                user.setSkinsPossuidas(listSkinsFromUser(user.getId()));
                return user;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar informações do usuário\n" + e);
        }
        return null;
    }

    public User addUser(User user) {
        String mySql = "INSERT INTO user(nome,pontos,email,senha,cargo) values(?,?,?,?,?)";
        user.setCargo("cliente");
        user.setPontos(1000);
        Long id = 0L;

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, user.getNome());
            st.setInt(2, user.getPontos());
            st.setString(3, user.getEmail());
            st.setString(4, user.getSenha());
            st.setString(5, user.getCargo());
            st.execute();

            ResultSet key = st.getGeneratedKeys();

            if(key.next()){
                id = key.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário\n" + e);
        }

        return findById(id);
    }

    public User updateUser(Long id, User u) {
        String mySql = "UPDATE user SET nome=?, pontos=?, email=?, senha=? WHERE id=?";

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            st.setString(1, u.getNome());
            st.setInt(2, u.getPontos());
            st.setString(3, u.getEmail());
            st.setString(4, u.getSenha());
            st.setLong(5, id);
            st.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário" + e);
        }

        return findById(id);
    }

    public User findById(Long id) {
        String mySql = "SELECT * FROM user WHERE user.id = ?";
        User user = new User();
        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            st.setLong(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                user = getUser();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuário\n" + e);
        }

        return user;
    }

    public void deleteUser(Long id) {
        String deleteSql = "DELETE FROM user WHERE id=?";

        try (Connection conn = ConnectionJdbc.getConnection()) {
            if (findById(id) != null) {
                st = conn.prepareStatement(deleteSql);
                st.setLong(1, id);
                st.execute();
                System.out.println("Usuário removido");
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar usuário\n" + e);
        }
    }

    public UserSkin addSkinFromUser(Long idSkin, Long idUser) {
        String mySql = "insert into skinsuser(id_user, id_skin) values(?,?)";

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            st.setLong(1, idUser);
            st.setLong(2, idSkin);
            st.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar skin para o usuário\n");
        }

        return new UserSkin(idSkin, idUser);
    }

    public void deleteSkinFromUser(Skin skin, User user) {
        String mySql = "delete from skinsuser where id_skin = ? and id_user= ?";

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            st.setLong(1, skin.getId());
            st.setLong(2,user.getId());
            st.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao retirar skin do usuário\n");
        }

    }

    public List<Skin> listSkinsFromUser(Long idUser) {
        User user = findById(idUser);
        String mySql = "SELECT * FROM skinsuser WHERE id_user=?";
        List<Skin> skins = new ArrayList<>();

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            st.setLong(1, user.getId());
            rs = st.executeQuery();
            while (rs.next()) {
                Skin s = skinRepository.findById(rs.getLong("id_skin"));
                skins.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar skins do usuário\n");
        }

        return skins;
    }


    private User getUser() throws SQLException{
        User user;
        user = new User();
        user.setId(rs.getLong("id"));
        user.setNome(rs.getString("nome"));
        user.setPontos(rs.getInt("pontos"));
        user.setEmail(rs.getString("email"));
        user.setSenha(rs.getString("senha"));
        user.setCargo(rs.getString("cargo"));
        return user;
    }
}
