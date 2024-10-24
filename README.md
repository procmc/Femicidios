# Proyecto IF7100.2023 - Femicidios

Este repositorio contiene el código fuente y la documentación para un software de registro de datos de feminicidios. El objetivo de este proyecto es proporcionar una herramienta para recopilar y analizar información sobre los feminicidios que ocurren en determinada región o país. El software está desarrollado utilizando el framework Spring Boot y utiliza GitHub Actions para implementar en Docker. El proyecto cuenta con la participación de los siguientes colaboradores:

- Carlos Morales Castro
- Jhonny David Solano Vargas
- Ismael Alexander Valverde Gómez
- Julio Moisés Jarquín Muñoz
- Kijan Acuña Medrano
- Hadji Dejan Loaiciga Herrera
- Saul Córdoba Solís
- Dillan Rodrigo Bermúdez González
- Lisbeth Ramírez Jiménez
- Michael Arauz Torrez
- Tishary Sallar Foster Blake
- Génesis Sequeira Navarro
- Ronny Salgado Moraga
- Kendall Barrantes Paniagua
- Ana Aguilar Vanegas

Ajustes durante el 2024 por Mauricio Torres Fernandez

## Características

El sistema tiene como objetivo principal facilitar el registro y análisis de casos de femicidio, que se define como el asesinato intencional de una mujer debido a su condición de género. El sistema también abarca los asesinatos de niñas y mujeres en general. En la Ley de Penalización de la Violencia contra las Mujeres de Costa Rica, se define el femicidio como la muerte de una mujer que tiene una relación de matrimonio o unión de hecho con el perpetrador.

El femicidio es considerado la forma más grave de violencia de género, donde las mujeres son asesinadas debido a su condición de mujer, generalmente a manos de su pareja actual o pasada, o de otros hombres con los que no tienen una relación de pareja. Estos casos no son simples homicidios, sino que suelen ser producto de una violencia escalonada y de una relación desigual entre la mujer y el hombre femicida.

En Costa Rica, desde la promulgación de la Ley de Penalización de la Violencia contra las Mujeres en 2007 hasta hoy, se registran muchos casos de femicidios, según los informes del Poder Judicial. Estas cifras muestran la importancia de contar con un sistema adecuado para el registro y seguimiento de estos casos.

El sistema propuesto incluye un estándar de datos de femicidios que define las entidades y categorías a ser registradas. Este estándar ha sido desarrollado con la colaboración de expertos en el tema y refleja una versión preliminar de las variables a registrar y las codificaciones correspondientes.

https://idatosabiertos.org/estandar-de-datos-de-femicidios-version-beta/

## Instalación

Los pasos para la instalación de Java, Docker, MySQL y una aplicación .jar:

### 1. Instalación de Java:

Ve al sitio web oficial de Java (https://www.java.com) y descarga la última versión del JDK (Java Development Kit) compatible con tu sistema operativo.
Ejecuta el instalador descargado y sigue las instrucciones del asistente de instalación para completar la instalación de Java en tu sistema.

### 2. Instalación de Docker:

Visita el sitio web oficial de Docker (https://www.docker.com) y descarga la versión adecuada para tu sistema operativo.
Una vez que la descarga se haya completado, ejecuta el instalador y sigue las instrucciones del asistente para instalar Docker en tu sistema.
Reinicia tu sistema después de la instalación para asegurarte de que Docker se configure correctamente.

### 3. Instalación de MySQL:

Visita el sitio web oficial de MySQL (https://www.mysql.com) y descarga la versión adecuada para tu sistema operativo.
Ejecuta el instalador descargado y sigue las instrucciones del asistente para completar la instalación de MySQL en tu sistema.
Durante el proceso de instalación, se te pedirá que establezcas una contraseña para el usuario "root" de MySQL. Asegúrate de recordar esta contraseña, ya que la necesitarás más adelante.

### 4. Ejecución de una aplicación .jar en Docker:
