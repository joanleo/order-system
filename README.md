# 🧾 Order System – Gestor de Pedidos y Clientes

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.x-brightgreen?logo=springboot)
![Java](https://img.shields.io/badge/Java-17-blue?logo=java)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Neon-blue?logo=postgresql)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)
![Status](https://img.shields.io/badge/Status-En%20Desarrollo-orange)

---

## 📘 Descripción

**Order System** es una aplicación desarrollada en **Spring Boot** bajo el enfoque de **arquitectura hexagonal**, diseñada para gestionar **clientes y pedidos** de manera eficiente.  
Está construida con **PostgreSQL (Neon Cloud)** como base de datos y expone servicios REST que pueden integrarse fácilmente con un frontend o un sistema ERP.

El objetivo es demostrar un desarrollo profesional, limpio y escalable como parte de un **portafolio backend**.

---

## 🧱 Arquitectura

El proyecto sigue el patrón **Hexagonal (Ports & Adapters)**, que permite separar la lógica del negocio del acceso a datos y la infraestructura.


---

## ⚙️ Tecnologías utilizadas

| Tecnología | Descripción |
|-------------|--------------|
| **Spring Boot 3.3.x** | Framework backend principal |
| **Spring Data JPA** | Abstracción de persistencia |
| **PostgreSQL (Neon)** | Base de datos en la nube |
| **Lombok** | Reducción de código boilerplate |
| **Jakarta Validation** | Validaciones de entidades |
| **Maven** | Gestor de dependencias |
| **Java 17** | Lenguaje base |
| **Render / Fly.io (opcional)** | Despliegue en la nube |

---

## 🚀 Ejecución local

### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/joanleon/order-system.git
cd order-system
```

### 2️⃣ Configurar el archivo de propiedades
Copia el archivo de ejemplo y agrega tus credenciales de Neon:

```
cp src/main/resources/application-example.properties src/main/resources/application.properties
```

Edita el archivo:

spring.datasource.url=jdbc:postgresql://<HOST>:5432/<DATABASE>?sslmode=require 
spring.datasource.username=<USER> 
spring.datasource.password=<PASSWORD> 
spring.jpa.hibernate.ddl-auto=update 



