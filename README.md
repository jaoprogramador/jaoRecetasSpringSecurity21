Requisitos
############
Java 21 con Spring Boot
Docker Desktop -  Contenedor MySQL

API para gestionar las recetas grastronomicas organizadas por categorias
Endpoints:
    http://localhost:8090/api/recetas/5
    http://localhost:8090/api/recetas/search/rroz
    
    http://localhost:8090/api/categorias/5
    http://localhost:8090/api/categorias/search/lian
GET http://localhost:8090/api/categorias/page?pageNumber=0&pageSize=5&sortField=nombre&sortDir=desc
    http://localhost:8090/api/categorias/page
    http://localhost:8090/api/recetas/page
    http://localhost:8090/api/recetas/page?pageNumber=0&pageSize=5&sortField=titulo&sortDir=desc

45.-Clases de configuración en Spring Boot. Vamos a crear una clase de configuración a la BD sin usar el application.properties
- comentamos en el application.properties las refeceencias a la configuracion de BD
- creamos una clase en el paquete config con la conexión y sus credenciales
- he tenido que ajustar las entidades en las columnas con barras bajas porque no era la configuracion camelsensitive
46.- Spring Security
- añadimos el starter de spring security y ahora ya no podremos consultar los endpoints
- nos da 401 unautorizated
- metemos en postman el token de acceso del log Using generated security password: dd1860f9-0180-415e-b27b-d357cfaf611b
- en postman hacemos consulta basic con user y dd1860f9-0180-415e-b27b-d357cfaf611b de pass y ya nos devuelve las consultas de postman
- creamos un paquete security y un bean de configuracion con jaoprogramador pass 1234 y probamos a hacer un get en postman
- tambien si ejecutamos http://localhost:8090/login nos muestra un login web básico contra las credenciales definidas en ConfigSecurity
47.- Spring security contra una BD con usuarios-roles-permisos
- creamos las entidades de autenticación usuario-rol-permiso con sus relaciones
- creamos el servicio UserDetailsServiceImpl que implementa UserDetailsService que implementa UserDetails loadUserByUsername 
- creamos los repositorios y service
-creamos un útil-runDatabase para cargar los datos la primera vez que se carga
- modificamos el SecurityConfig para que en vez de tirar de memoria con InMemoryUserDetailsManager y usar SecurityFilterChain
- Testing con postman: ahora para poder consultar el api necesitamos hacerlo con un usuario de bd y clave encriptada
