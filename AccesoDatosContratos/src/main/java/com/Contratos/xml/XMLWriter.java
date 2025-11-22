/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author manu_
 */
package com.Contratos.xml;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLWriter {

    /**
     * Genera un nuevo XML a partir del Document original, pero elimina
     * todos los nodos con nombre "TIPO_DE_CONTRATO".
     * @param doc
     * @param outputPath
     * @throws java.lang.Exception
     */
    public static void writeWithoutTipo(Document doc, String outputPath) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        // clonar el documento para no modificar el original
        Document newDoc = db.newDocument();
        Node imported = newDoc.importNode(doc.getDocumentElement(), true);
        newDoc.appendChild(imported);

        // eliminar nodos TIPO_DE_CONTRATO en todo el árbol
        removeElementsByTagName(newDoc, "TIPO_DE_CONTRATO");

        // transformar a fichero
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(newDoc);
        StreamResult result = new StreamResult(new File(outputPath));
        transformer.transform(source, result);
    }

    private static void removeElementsByTagName(Document doc, String tagName) {
        NodeList nodes = doc.getElementsByTagName(tagName);
        // como NodeList es dinámico, copiamos en array
        int len = nodes.getLength();
        Element[] arr = new Element[len];
        for (int i = 0; i < len; i++) arr[i] = (Element) nodes.item(i);
        for (Element e : arr) {
            Node parent = e.getParentNode();
            if (parent != null) parent.removeChild(e);
        }
    }
}

