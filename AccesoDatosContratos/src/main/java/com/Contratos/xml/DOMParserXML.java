/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author manu_
 */
package com.Contratos.xml;

import com.Contratos.model.Contrato;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;

public class DOMParserXML {

    private final Document document;

    public DOMParserXML(String xmlFilePath) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        this.document = db.parse(new File(xmlFilePath));
        this.document.getDocumentElement().normalize();
    }

    public List<Contrato> parseContratos() {
        List<Contrato> lista = new ArrayList<>();
        Element root = document.getDocumentElement();
        // Asumimos que cada contrato está en elementos llamados "CONTRATO" o "registro"
        NodeList posibles = root.getElementsByTagName("CONTRATO");
        if (posibles.getLength() == 0) {
            // intentamos con "registro"
            posibles = root.getElementsByTagName("registro");
        }
        if (posibles.getLength() == 0) {
            // como fallback: coger hijos directos del root
            posibles = root.getChildNodes();
        }

        for (int i = 0; i < posibles.getLength(); i++) {
            Node node = posibles.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;
            Element e = (Element) node;
            Contrato c = new Contrato();

            // Ejemplo de mapeo: cambiar nombres si el XML es diferente
            c.setId(getElementText(e, "ID"));
            c.setFecha(getElementText(e, "FECHA"));
            c.setProveedor(getElementText(e, "PROVEEDOR"));
            String imp = getElementText(e, "IMPORTE");
            if (imp != null && !imp.isEmpty()) {
                try { c.setImporte(Double.valueOf(imp.replace(",", "."))); } catch (NumberFormatException ex) { c.setImporte(null); }
            }
            c.setObjeto(getElementText(e, "OBJETO"));
            c.setOrganismo(getElementText(e, "ORGANISMO"));
            c.setTipoContrato(getElementText(e, "TIPO_DE_CONTRATO")); // etiqueta esperada

            // recoger cualquier otro subelemento dinámicamente
            Map<String,String> otros = new HashMap<>();
            NodeList children = e.getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                Node ch = children.item(j);
                if (ch.getNodeType() != Node.ELEMENT_NODE) continue;
                String tag = ch.getNodeName();
                String text = ch.getTextContent().trim();
                if (!Arrays.asList("ID","FECHA","PROVEEDOR","IMPORTE","OBJETO","ORGANISMO","TIPO_DE_CONTRATO").contains(tag)) {
                    otros.put(tag, text);
                }
            }
            if (!otros.isEmpty()) c.setOtros(otros);

            // id fallback: usar índice si no hay id
            if (c.getId() == null || c.getId().isEmpty()) {
                c.setId("IDX_" + i);
            }
            lista.add(c);
        }
        return lista;
    }

    private String getElementText(Element parent, String tagName) {
        NodeList nl = parent.getElementsByTagName(tagName);
        if (nl.getLength() == 0) return null;
        Node n = nl.item(0);
        if (n == null) return null;
        return n.getTextContent().trim();
    }

    // Exponer Document para reescribir sin tipo de contrato si se desea
    public Document getDocument() {
        return this.document;
    }
}

