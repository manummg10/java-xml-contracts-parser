/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Manu_
 */
package com.Contratos;

import com.Contratos.dao.ContratoDAO;
import com.Contratos.dao.Database;
import com.Contratos.model.Contrato;
import com.Contratos.xml.DOMParserXML;
import com.Contratos.xml.XMLWriter;
import java.sql.SQLException;
import org.w3c.dom.Document;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Rutas y configuración - personaliza antes de ejecutar
        String xmlInputPath = "src/main/resources/contratos-adjudicados-jun-25.xml"; // coloca el XML en data/
        String xmlOutputPath = "src/main/resources/contratos-sin-tipo.xml";

        // DB config - modifica con tus credenciales
        String dbHost = "localhost";
        int dbPort = 3306;
        String dbName = "contratos";
        String dbUser = "root";
        String dbPass = "";// Pon tu contraseña

        try {
            System.out.println("Iniciando parseo DOM...");
            DOMParserXML parser = new DOMParserXML(xmlInputPath);
            List<Contrato> lista = parser.parseContratos();
            System.out.println("Contratos leídos: " + lista.size());

            Database db = new Database(dbHost, dbPort, dbName, dbUser, dbPass);
            ContratoDAO dao = new ContratoDAO(db);

            System.out.println("Volcando a la base de datos...");
            int contador = 0;
            for (Contrato c : lista) {
                try {
                    dao.insertContrato(c);
                    contador++;
                } catch (SQLException ex) {
                    System.err.println("Error insertando id=" + c.getId() + ": " + ex.getMessage());
                    // seguir con siguiente
                }
            }
            System.out.println("Registros insertados: " + contador);

            System.out.println("Generando XML de salida sin TIPO_DE_CONTRATO...");
            Document origDoc = parser.getDocument();
            XMLWriter.writeWithoutTipo(origDoc, xmlOutputPath);
            System.out.println("XML de salida creado en: " + xmlOutputPath);

            System.out.println("Proceso finalizado correctamente.");

        } catch (Exception e) {
            System.err.println("Fallo en el proceso: " + e.getMessage());
        }
    }
}
