# Corriendo por mi Patria 5K — Java Web para Render

Versión web del proyecto de escritorio, manteniendo la identidad visual amarilla/blanca, portada del venado con Guatemala, patrocinadores, animaciones, inscripción y panel privado.

## Evento
- Domingo 13 de septiembre de 2026
- 6:30 AM
- Parqueo Plaza Américas
- Contraseña coordinadores por defecto: `2026`

## Funciones
- Inscripción pública: nombre, edad, municipio y teléfono.
- Dorsal automático: `PATRIA-0001`, `PATRIA-0002`, etc.
- Confirmación imprimible.
- Panel privado `/admin`.
- Buscar, editar y eliminar participantes.
- Exportar CSV compatible con Excel.
- PostgreSQL para producción y H2 local como respaldo de desarrollo.
- Diseño responsive para celular, tablet y computadora.

## Abrir en NetBeans
1. Archivo > Abrir proyecto.
2. Selecciona esta carpeta (es un proyecto Maven).
3. JDK 17.
4. Ejecuta el proyecto. En local abre `http://localhost:8080`.

## Probar localmente
```bash
mvn spring-boot:run
```

Sin variables externas se usa una base H2 en `./data/patria5k`.

## Render + Neon (producción)
Render ejecuta el servidor. Neon guarda los datos de manera persistente.

En Render configura estas variables:
- `ADMIN_PASSWORD` = `2026`
- `SPRING_DATASOURCE_URL` = URL JDBC de PostgreSQL, por ejemplo `jdbc:postgresql://HOST/DB?sslmode=require`
- `SPRING_DATASOURCE_USERNAME` = usuario de Neon
- `SPRING_DATASOURCE_PASSWORD` = contraseña de Neon

Importante: no uses H2 como base definitiva en Render porque el disco del servicio gratuito no debe considerarse almacenamiento persistente para tus inscripciones.

## Publicación
Este proyecto incluye `Dockerfile` y `render.yaml`, por lo que Render puede construirlo como servicio Docker.
