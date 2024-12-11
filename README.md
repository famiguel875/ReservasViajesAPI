## Documentación del Proyecto: ReservasViajes API

1. **Nombre del Proyecto**

ReservasViajes API

2. **Idea del Proyecto**

El proyecto consiste en desarrollar una API REST para gestionar reservas de viajes, incluyendo la administración de usuarios, la creación de reservas y la gestión de los detalles de cada reserva. La API debe ser segura, eficiente y capaz de manejar las operaciones requeridas por una plataforma de reservas de viajes.

3. **Justificación del Proyecto**

La digitalización de las reservas de viaje permite a las agencias y clientes optimizar procesos, reducir errores y mejorar la experiencia del usuario. Esta API servirá como base para una plataforma escalable y segura que centraliza la gestión de reservas y datos relacionados.

4. **Descripción Detallada de las Tablas**

````
Usuarios

Descripción: Tabla que almacena información de los usuarios registrados.

Campos:

id_usuario (PK, autoincremental)

nombre (string)

correo (string, único)

contraseña (string, encriptada)

fecha_creacion (timestamp)

Reservas

Descripción: Tabla que almacena información de las reservas realizadas.

Campos:

id_reserva (PK, autoincremental)

id_usuario (FK, referencia a Usuarios)

fecha_reserva (timestamp)

estado (enum: "pendiente", "confirmada", "cancelada")

Detalles_Reserva

Descripción: Tabla que almacena detalles específicos de cada reserva.

Campos:

id_detalle (PK, autoincremental)

id_reserva (FK, referencia a Reservas)

destino (string)

fecha_inicio (date)

fecha_fin (date)

precio_total (decimal)
````

5. **Endpoints de la API**

a. Endpoints a Desarrollar para Cada Tabla

````
Usuarios

POST /usuarios

GET /usuarios/{id}

PUT /usuarios/{id}

DELETE /usuarios/{id}

Reservas

POST /reservas

GET /reservas/{id}

GET /reservas

PUT /reservas/{id}

DELETE /reservas/{id}

Detalles_Reserva

POST /detalles

GET /detalles/{id}

PUT /detalles/{id}

DELETE /detalles/{id}
````
b. Descripción de los Endpoints
````
Usuarios

POST /usuarios: Crea un nuevo usuario. Recibe datos como nombre, correo y contraseña.

GET /usuarios/{id}: Obtiene información de un usuario específico por su ID.

PUT /usuarios/{id}: Actualiza la información de un usuario.

DELETE /usuarios/{id}: Elimina un usuario del sistema.
````
````
Reservas

POST /reservas: Crea una nueva reserva asociada a un usuario.

GET /reservas/{id}: Obtiene detalles de una reserva específica.

GET /reservas: Lista todas las reservas existentes.

PUT /reservas/{id}: Actualiza el estado de una reserva (por ejemplo, confirmar o cancelar).

DELETE /reservas/{id}: Cancela una reserva pendiente.
````
````
Detalles_Reserva

POST /detalles: Agrega detalles a una reserva existente.

GET /detalles/{id}: Obtiene los detalles específicos de una reserva.

PUT /detalles/{id}: Modifica los detalles de una reserva.

DELETE /detalles/{id}: Elimina detalles específicos de una reserva.
````
6. **Lógica de Negocio**
````
Usuarios

Validación de datos al crear o actualizar usuarios.

Encriptación de contraseñas para mayor seguridad.
````
````
Reservas

Una reserva solo puede tener detalles si está en estado "confirmada".

No se permite eliminar reservas en estado "confirmada".
````
````
Detalles_Reserva

Validar que las fechas de inicio y fin sean coherentes.

Calcular el precio total basado en la duración y tarifas.
````

7. **Excepciones y Códigos de Estado**

````
Usuarios

400: Datos inválidos en la solicitud.

404: Usuario no encontrado.

409: Conflicto (por ejemplo, correo duplicado).
````
````
Reservas

400: Datos inválidos en la solicitud.

404: Reserva no encontrada.

403: Operación no permitida (por ejemplo, eliminar una reserva confirmada).
````
````
Detalles_Reserva

400: Datos inválidos en la solicitud.

404: Detalle no encontrado.

422: Inconsistencias en las fechas.
````
8. **Restricciones de Seguridad**

Autenticación y Autorización

Uso de JWT (JSON Web Tokens) para autenticar sesiones de usuario.

Implementación de roles para restringir acceso (admin y cliente).

Cifrado

Cifrado de contraseñas utilizando bcrypt.

Uso de HTTPS para la comunicación entre cliente y servidor.

Validación de Solicitudes

Sanitización de entradas para prevenir inyecciones SQL y ataques XSS.

Límites de tasa de solicitudes para evitar ataques de fuerza bruta.


