/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author manu_
 */
package com.Contratos.dao;

import com.Contratos.model.Contrato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContratoDAO {
    private final Database db;

    public ContratoDAO(Database db) {
        this.db = db;
    }

    public void insertContrato(Contrato c) throws SQLException {
        String sql = "INSERT INTO contratos (id, fecha, proveedor, importe, objeto, organismo, tipo_contrato, otros) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getId());
            ps.setString(2, c.getFecha());
            ps.setString(3, c.getProveedor());
            if (c.getImporte() != null) ps.setDouble(4, c.getImporte()); else ps.setNull(4, java.sql.Types.DOUBLE);
            ps.setString(5, c.getObjeto());
            ps.setString(6, c.getOrganismo());
            ps.setString(7, c.getTipoContrato());
            // Para 'otros' convertimos mapa a JSON-like string simple
            if (c.getOtros() != null) {
                StringBuilder sb = new StringBuilder("{");
                c.getOtros().forEach((k,v) -> sb.append("\"").append(k).append("\":\"").append(v.replace("\"","\\\"")).append("\","));
                if (sb.charAt(sb.length()-1)==',') sb.deleteCharAt(sb.length()-1);
                sb.append("}");
                ps.setString(8, sb.toString());

            } else {
                ps.setNull(8, java.sql.Types.VARCHAR);
            }
            ps.executeUpdate();
        }
    }
}

