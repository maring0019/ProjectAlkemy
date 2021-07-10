INSERT INTO ROLES (id,deleted,name, roleName) VALUES
(1, 0,'ROLE_USER','ROLE_USER'),
(2, 0,'ROLE_ADMIN','ROLE_ADMIN');

INSERT INTO COMMENTS(id, users_id, body, news_id ) VALUES
(1,1 ,'caminos a todos ...',1),
(2,2 ,'avenida a todos ...',1);


INSERT INTO USERS (id, firstName, lastName, email, password, user_role) VALUES
(1, 'Martina','Ayala','martina@gmail.com','martina',1),
(2,'Paola','Fernandez','fer@gmail.com','fernandez',2);

INSERT INTO TESTIMONIALS (id, name) VALUES
(1,'Comentarios sobre el ....'),
(2,'Testimonials de ...' );

INSERT INTO NEWS (id, name, content, image) VALUES
(1, 'Mar','Ramirez cuenta con un mar en las islas ...','https://www.google.com/url?sa=i&url=https%3A%2F%2Fes.123rf.com%2Fimagenes-de-archivo%2Fmar.html&psig=AOvVaw38gtD0N9HADAS6OeXPf4ls&ust=1625970517184000&source=images&cd=vfe&ved=0CAoQjRxqFwoTCJjCgKy61_ECFQAAAAAdAAAAABAD'),
(2,'Sol','En la mañana podemos observar ...', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fsp.depositphotos.com%2Fstock-photos%2Fsol.html&psig=AOvVaw15BZ6SPbtgI641TZTdkRFJ&ust=1625970611199000&source=images&cd=vfe&ved=0CAoQjRxqFwoTCJDQi9q61_ECFQAAAAAdAAAAABAD');



