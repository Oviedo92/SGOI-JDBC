# SGOI-JDBC

## Módulo de Gestión de Clientes

Aplicación stand-alone desarrollada como parte del **Sistema de Gestión de Órdenes de Servicio e Inventario (SGOI)** para la empresa Motores y Bobinados (M&B).

Este proyecto implementa el módulo de Gestión de Clientes mediante Java 17, JDBC y PostgreSQL 17, utilizando una arquitectura por capas y Maven para la gestión de dependencias y compilación.

## 1. Objetivo

Desarrollar un módulo que permita administrar la información de los clientes mediante operaciones CRUD (Create, Read, Update y Delete), garantizando la persistencia de los datos en una base de datos PostgreSQL.

## 2. Alcance funcional

El módulo contempla las siguientes funcionalidades:

| Operación | Descripción |
|---|---|
| Registrar cliente | Almacena un nuevo cliente. |
| Listar clientes | Consulta todos los clientes registrados. |
| Buscar por ID | Consulta un cliente mediante su identificador. |
| Actualizar cliente | Modifica la información de un cliente existente. |
| Eliminar cliente | Elimina un cliente previa confirmación del usuario. |

Los datos administrados son: identificador, nombre, documento, teléfono, correo electrónico, dirección y fecha de registro.

**Nota:** este repositorio contiene el módulo stand-alone de Gestión de Clientes. No representa la implementación completa del sistema SGOI.

## 3. Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java 17 | Lenguaje de programación. |
| JDBC | Conexión y comunicación con la base de datos. |
| PostgreSQL 17 | Sistema gestor de base de datos. |
| PostgreSQL JDBC 42.7.7 | Controlador de conexión JDBC. |
| Apache Maven | Gestión de dependencias y compilación. |
| Git | Control de versiones. |
| GitHub | Hospedaje del repositorio. |
| PlantUML | Elaboración de diagramas de diseño y arquitectura. |
| Visual Studio Code | Entorno de desarrollo. |

## 4. Arquitectura del proyecto

El proyecto utiliza una arquitectura por capas:

- **Presentación (`ui`):** gestiona la interacción con el usuario mediante un menú de consola.
- **Negocio (`service`):** aplica las validaciones y coordina las operaciones del módulo.
- **Acceso a datos (`dao`):** ejecuta las consultas SQL mediante JDBC.
- **Configuración (`config`):** administra la obtención de conexiones con PostgreSQL.
- **Modelo (`model`):** representa la entidad Cliente.
- **Excepciones (`exception`):** centraliza la excepción personalizada para errores de acceso a datos.

## 5. Estructura del proyecto

```text
SGOI-JDBC/
├── database/
│   └── schema.sql
├── docs/
│   └── diagramas/
│       ├── actividad/
│       ├── c4/
│       ├── casos-uso/
│       ├── clases/
│       ├── componentes/
│       ├── modelo-datos/
│       └── secuencia/
├── src/
│   ├── main/
│   │   ├── java/com/sgoi/
│   │   │   ├── Main.java
│   │   │   ├── config/
│   │   │   ├── dao/
│   │   │   ├── exception/
│   │   │   ├── model/
│   │   │   ├── service/
│   │   │   └── ui/
│   │   └── resources/
│   └── test/
│       └── java/com/sgoi/
├── .gitignore
├── pom.xml
└── README.md
```

## 6. Requisitos previos

Antes de ejecutar el proyecto, se requiere:

- Java JDK 17.
- Apache Maven 3.10 o compatible.
- PostgreSQL 17.
- Git (opcional para ejecutar, necesario para trabajar con el repositorio).

Verificar las instalaciones:

```powershell
java -version
mvn -version
git --version
```

## 7. Configuración de la base de datos

### 7.1 Crear la base de datos

Desde PostgreSQL, ejecutar:

```sql
CREATE DATABASE sgoi_db;
```

### 7.2 Crear la tabla

Ejecutar el archivo `database/schema.sql` desde `psql`:

```sql
\i 'RUTA_DEL_PROYECTO/database/schema.sql'
```

También se puede ejecutar el contenido del archivo desde pgAdmin.

El script crea la tabla `clientes`, con una clave primaria autogenerada y una restricción de unicidad para el documento.

## 8. Configuración de las variables de entorno

La aplicación utiliza variables de entorno para establecer la conexión JDBC. No se almacenan credenciales directamente en el código fuente.

En PowerShell, configurar:

```powershell
$env:SGOI_DB_URL = "jdbc:postgresql://localhost:5432/sgoi_db"
$env:SGOI_DB_USER = "postgres"
$env:SGOI_DB_PASSWORD = Read-Host "Introduce tu contraseña de PostgreSQL"
```

Se debe sustituir el usuario y la contraseña según la configuración local de PostgreSQL.

Estas variables deben configurarse en la misma sesión de PowerShell desde la que se ejecutará Maven.

## 9. Instalación y ejecución

Clonar el repositorio:

```powershell
git clone URL_DEL_REPOSITORIO
```

Ingresar a la carpeta:

```powershell
cd SGOI-JDBC
```

Compilar el proyecto:

```powershell
mvn clean compile
```

Ejecutar la aplicación:

```powershell
mvn exec:java
```

La aplicación verificará la conexión con PostgreSQL e iniciará el menú de Gestión de Clientes si la conexión es exitosa.

## 10. Operaciones disponibles

Al iniciar la aplicación se presenta el siguiente menú:

```text
==================================
 SISTEMA SGOI - GESTIÓN DE CLIENTES
==================================
1. Registrar cliente
2. Listar clientes
3. Buscar cliente por ID
4. Actualizar cliente
5. Eliminar cliente
0. Salir
==================================
```

El usuario selecciona la operación que desea ejecutar y sigue las instrucciones de la consola.

## 11. Modelo de datos

La persistencia del módulo se realiza en la tabla `clientes`.

| Campo | Tipo | Restricción |
|---|---|---|
| id | BIGINT | PRIMARY KEY, IDENTITY |
| nombre | VARCHAR(100) | NOT NULL |
| documento | VARCHAR(30) | NOT NULL, UNIQUE |
| telefono | VARCHAR(20) | Opcional |
| correo | VARCHAR(120) | Opcional |
| direccion | VARCHAR(200) | Opcional |
| fecha_registro | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP |

## 12. Diagramas de diseño y arquitectura

Los diagramas del proyecto se encuentran organizados en `docs/diagramas/`. A continuación, se presentan las imágenes correspondientes a cada vista del diseño y arquitectura.

### 12.1 Diagrama de casos de uso

![Diagrama de casos de uso](<docs/diagramas/casos-uso/diagrama de casos de uso.png>)

### 12.2 Diagrama de clases UML

![Diagrama de clases UML](<docs/diagramas/clases/Diagrama de clases UML.png>)

### 12.3 Diagrama de componentes

![Diagrama de componentes](<docs/diagramas/componentes/Diagrama de componentes.png>)

### 12.4 Diagramas de secuencia

**Vista general**

![Diagramas de secuencia](<docs/diagramas/secuencia/Diagramas de secuencia.png>)

**Registrar y listar clientes**

![Diagrama de secuencia - Listar clientes](<docs/diagramas/secuencia/Diagramad e secuencia - Listar clientes.png>)

**Buscar cliente por ID**

![Diagrama de secuencia - Buscar cliente](<docs/diagramas/secuencia/Diagrama de Secuencia - Buscar cliente por ID.png>)

**Actualizar cliente**

![Diagrama de secuencia - Actualizar cliente](<docs/diagramas/secuencia/Diagrama de Secuencia - Actualizar cliente.png>)

**Eliminar cliente**

![Diagrama de secuencia - Eliminar cliente](<docs/diagramas/secuencia/Diagrama de Secuencia - Eliminar cliente.png>)

### 12.5 Diagrama entidad-relación (ERD)

![Diagrama entidad-relación](<docs/diagramas/modelo-datos/Diagrama entidad-relación (ERD).png>)

### 12.6 Diagrama de actividad

![Diagrama de actividad](<docs/diagramas/actividad/Diagrama de actividad - flujo.png>)

### 12.7 Diagramas C4

**Vista de contexto**

![Diagramas C4](<docs/diagramas/c4/Diagramas C4.png>)

**Vista de contenedores**

![Diagrama de contenedores](<docs/diagramas/c4/Diagrama de contenedores.png>)

Estos artefactos documentan las funcionalidades, estructura, comportamiento y arquitectura del módulo.

## 13. Estándares de codificación

El desarrollo aplica convenciones de nomenclatura de Java:

- **Clases:** PascalCase. Ejemplo: `ClienteService`.
- **Métodos y variables:** camelCase. Ejemplo: `buscarPorId`.
- **Constantes:** MAYÚSCULAS_CON_GUION_BAJO. Ejemplo: `LISTAR_TODOS`.
- **Paquetes:** nombres en minúsculas. Ejemplo: `com.sgoi.dao`.

También se emplean separación de responsabilidades, encapsulamiento, `PreparedStatement`, `try-with-resources` y manejo de excepciones.

## 14. Control de versiones

El proyecto utiliza Git para el control de versiones y GitHub como plataforma de alojamiento del repositorio.

## 15. Autoría

Proyecto académico desarrollado como evidencia de formación del programa de Análisis y Desarrollo de Software (ADSO) del SENA.

**Proyecto:** Sistema de Gestión de Órdenes de Servicio e Inventario (SGOI).

**Módulo:** Gestión de Clientes.

**Tecnologías principales:** Java 17, JDBC, PostgreSQL 17 y Maven.
