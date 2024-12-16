-- Insertar usuarios de prueba
-- Contraseña user1 admin123
-- Contraseña user2 cliente123
-- Contraseña user3 cliente456
INSERT INTO usuarios (id, nombre, correo, contraseña, fecha_creacion) VALUES (1, 'Administrador', 'admin@empresa.com', '$2a$10$8I4aZkjKv3xlWibLfQ5DHe1I55EJXhTP5RxC7BN8OaWeg9LP9i0ZC', NOW()), (2, 'Cliente 1', 'cliente1@empresa.com', '$2a$10$JHdpeCV/WsP/RwBQ6w6MheMHG7vcylwSc9Dn7CpBDjZjSkVtHv5du', NOW()), (3, 'Cliente 2', 'cliente2@empresa.com', '$2a$10$XKXMvXQTVY8IfxQrxu68/OzrcvX.Y1E/B8jBgJtxXY22/qFANd8NS', NOW());

-- Asignar roles a los usuarios
INSERT INTO roles (usuario_id, role) VALUES (1, 'ADMIN'), (2, 'CLIENTE'), (3, 'CLIENTE');

-- Insertar reservas de prueba
INSERT INTO reservas (id, usuario_id, fecha_reserva, estado) VALUES (1, 2, NOW(), 'PENDIENTE'), (2, 2, NOW(), 'CONFIRMADA'), (3, 3, NOW(), 'CANCELADA');

-- Insertar detalles de reservas
INSERT INTO detalles_reserva (id, reserva_id, destino, fecha_inicio, fecha_fin, precio_total) VALUES (1, 2, 'Playa Cancún', '2024-01-10', '2024-01-15', 500.00), (2, 2, 'París', '2024-02-01', '2024-02-05', 800.00);