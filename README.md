# java-xml-contracts-parser

Aplicación en Java para leer, procesar y almacenar contratos menores.  
El programa realiza tres funciones principales:

1. Leer un archivo XML original utilizando DOM.
2. Insertar los datos extraídos en una base de datos MySQL mediante JDBC.
3. Generar un nuevo XML eliminando el nodo `<TIPO_DE_CONTRATO>`.

Este proyecto forma parte del trabajo del módulo **Acceso a Datos** del ciclo DAM.

---

## 🚀 Funcionalidades

- Parseo de XML con DOM (lectura y modificación).
- Eliminación de nodos específicos del documento.
- Inserción de contratos en una BD MySQL.
- Generación de un XML final limpio.
- Manejo de campos nulos y datos faltantes.
- Gestión de excepciones SQL y de estructura XML.

---

## 🛠 Tecnologías utilizadas

- **Java 24**  
- **DOM Parser (javax.xml.parsers / org.w3c.dom)**  
- **MySQL 8**  
- **JDBC**  
- **Maven o proyecto estándar de Java**  

---


---

## 🗄 Diseño de la base de datos

```sql
CREATE TABLE contratos (
    id VARCHAR(100) PRIMARY KEY,
    fecha DATE,
    proveedor VARCHAR(255),
    organismo VARCHAR(255),
    importe DECIMAL(12,2),
    tipo_contrato VARCHAR(200),
    objeto TEXT,
    otros JSON NULL
);


