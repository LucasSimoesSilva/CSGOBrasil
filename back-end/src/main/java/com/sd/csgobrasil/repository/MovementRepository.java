package com.sd.csgobrasil.repository;

import com.sd.csgobrasil.conn.ConnectionJdbc;
import com.sd.csgobrasil.entity.DTO.Report;
import com.sd.csgobrasil.entity.Movement;
import com.sd.csgobrasil.entity.Skin;
import com.sd.csgobrasil.entity.User;
import com.sd.csgobrasil.service.SkinService;
import com.sd.csgobrasil.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class MovementRepository {

    private List<Movement> movementList;
    List<Report> reports;
    private PreparedStatement st;
    private ResultSet rs;

    @Autowired
    private UserService userService;
    @Autowired
    private SkinService skinService;

    public MovementRepository() {
        movementList = new ArrayList<>();
        reports = new ArrayList<>();
    }

    public List<Movement> listMovement() {
        movementList.clear();
        Movement movement;
        String mySql = "SELECT * FROM movement";

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            rs = st.executeQuery();
            while (rs.next()) {
                movement = getMovement();
                movementList.add(movement);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar movimentacoes\n" + e);
        }

        return movementList;

    }

    public Movement updateM(Long id, Movement m) {
        String mySql = "UPDATE movement SET id_vendedor=?, id_comprador=?, id_skin=?, estado_venda=?, pontos=? WHERE id_venda=?";

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            st.setLong(1, m.getIdVendedor());
            st.setLong(2, m.getIdComprador());
            st.setLong(3, m.getIdSkin());
            st.setBoolean(4, m.isEstadoVenda());
            st.setLong(5, m.getPontos());
            st.setLong(6, id);
            st.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar movimentacao\n" + e);
        }

        return findById(id);
    }


    public Movement addMovement(Movement m) {
        String mySql = "INSERT INTO movement (id_vendedor,id_skin,estado_venda, pontos) values(?,?,?,?)";
        Long id = 0L;

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql, Statement.RETURN_GENERATED_KEYS);
            st.setLong(1, m.getIdVendedor());
            st.setLong(2, m.getIdSkin());
            st.setBoolean(3, m.isEstadoVenda());
            st.setLong(4, m.getPontos());
            st.execute();

            ResultSet key = st.getGeneratedKeys();

            if (key.next()) {
                id = key.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar movimentacao\n" + e);
        }

        return findById(id);
    }

    public void cancelMovement(Long idVenda) {
        String deleteSql = "DELETE FROM movement WHERE id_venda=?";
        Movement movement = findById(idVenda);


        try (Connection conn = ConnectionJdbc.getConnection()) {
            if (movement.isEstadoVenda()) {
                System.out.println("Movimentacoes concluídas não podem ser canceladas.");
            } else if (movement != null) {
                st = conn.prepareStatement(deleteSql);
                st.setLong(1, idVenda);
                st.execute();
                System.out.println("Movimentacao cancelada");
            } else {
                System.out.println("Movimentacao não encontrada.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cancelar movimentacao\n" + e);
        }
    }

    public Movement findById(Long id) {
        String mySql = "SELECT * FROM movement WHERE movement.id_venda = ?";
        Movement movement = null;
        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            st.setLong(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                movement = getMovement();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar movimentacao\n" + e);
        }

        return movement;
    }

    public List<Report> makeReport() {
        reports.clear();
        Report report;
        String mySql = """
                SELECT m.id_venda,uc.nome AS nome_comprador, uv.nome AS nome_vendedor, s.nome AS nome_skin, m.pontos, m.estado_venda
                FROM movement AS m
                LEFT JOIN user AS uc ON m.id_comprador = uc.id
                JOIN user AS uv ON m.id_vendedor = uv.id
                JOIN skin AS s ON m.id_skin = s.id
                ORDER BY m.id_venda
                """;

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            rs = st.executeQuery();
            if (rs.next()) {
                report = new Report();
                report.setIdVenda(rs.getLong("id_venda"));
                report.setNameComprador(rs.getString("nome_comprador"));
                report.setNameVendedor(rs.getString("nome_vendedor"));
                report.setNameSkin(rs.getString("nome_skin"));
                report.setPontos(rs.getInt("pontos"));
                report.setEstadoVenda(rs.getBoolean("estado_venda"));
                reports.add(report);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao realizar relatório\n" + e);
        }

        return reports;
    }

    public boolean makeMovement(Long idVenda, Long idComprador) {
        Movement movement = findById(idVenda);
        Skin skinMovement = skinService.findBySkinId(movement.getIdSkin());
        User comprador = userService.findByUserId(idComprador);
        User vendedor = userService.findByUserId(movement.getIdVendedor());

        if (Objects.equals(vendedor.getId(), comprador.getId())) {
            System.out.println("O vendedor não pode comprar sua própria skin");
            return false;
        } else if (comprador.getPontos() >= movement.getPontos()) {
            movement.setEstadoVenda(true);
            movement.setIdComprador(comprador.getId());

            comprador.setPontos(comprador.getPontos() - movement.getPontos());
            vendedor.setPontos(vendedor.getPontos() + movement.getPontos());

            userService.addSkinFromUser(skinMovement.getId(), comprador.getId());
            userService.deleteSkinFromUser(skinMovement, vendedor);
            userService.updateUser(comprador.getId(), comprador);
            userService.updateUser(vendedor.getId(), vendedor);
            updateM(movement.getIdVenda(), movement);
            return true;
        }
        return false;
    }


    private Movement getMovement() throws SQLException {
        Movement movement;
        movement = new Movement();
        movement.setIdVenda(rs.getLong("id_venda"));
        movement.setIdVendedor(rs.getLong("id_vendedor"));
        movement.setIdComprador(rs.getLong("id_comprador"));
        movement.setIdSkin(rs.getLong("id_skin"));
        movement.setEstadoVenda(rs.getBoolean("estado_venda"));
        movement.setPontos(rs.getInt("pontos"));
        return movement;
    }
}
