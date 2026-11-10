Requisitos
############
Java 21 con Spring Boot
Docker Desktop -  Contenedor MySQL
    docker start postgres-recetas
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

48.- Spring Boot Security: configuración global de la seguridad(habilitar endpoints, deshabilitar paths, dar permisos segun rol de escritura)
  -marcamos la clase de conf security con EnableWebSecurity
  -creamos el bean SecurityFilterChain
 - esto por si no nos securiza nada porque nos permite consultar el api con usu y pass incorrectos.
 - creamos una configuración que cualquier endpoint este securizado por usu y pass de BD
  -creamos otra configuración sin CRFS porque no usamos formularios, es un api, quitamos los estados de sesión, y definimos los endpoints que habilitamos pot tipo GET y POST según los roles definidos en BD, read, write
  -ahora en postman si intentamos modificar una categoría sin tener permiso de escritura no nos dejará
 - como es un api rest habilitamos los CORS y definimos un bean CORS

49.- Spring Boot Security: seguridad a nivel de método
- configuramos nuestro security config con @EnableMethodSecurity para habilitar seguridad a nivel de metodo
- en el controler ponemos la anotación @Preautorize(“permitAll()” lo que hace que permite usar todo el controlador aunque no estemos autenticados
- cambiamsos la anotación y ponemos @Preautorized (“insAuthenticated) de tal forma que nos permite consultar el controller solo si estamos autenticados
- podemos tener esta anotación a nivel global de controller pero podemos poner a nivel de método @PreAuthorize(“hasAuthority(‘READ según el permiso
  También podemos poner permisos según rol

50.- manejo excepciones en seguridad.
-No siempre se interceptan los errores de spring security en un @ControllerAdvice
- Tenemos multitud de excepciones de seguridad:
- En globalExceptionHandeler crea,os capturas de AuetenticatedException, AccesDeniedException… y vemos que no nos llegan a capturar
- Creamos un componente CustomAuthenticatedEntryPointe implementar AuthenticatedEntryPointe
  Implementamos y alimentamos Errorinfo según el error según el constructor, ParameterException, GlobalExceptionHandler,
  Creamos otro componente error CustomAccesDeniedHandleer
  En la SecurityConfig tenemos que definir los beans que cargarán estos errores
- Vamos a postman he intentamos hacer un post con un usuario con permiso de lectura o inexistente y nos modtrará el error personalizado

51.- Spring security: JWT, usando json web token
- Añadimos dependencia java-jwt al pom
- vamos a https://www.base64encode.org/ copiamos un texto sobre el que crear la clave codificada, damos encode y copiamos el token
- Creamos en application.propierties el secret, el tiempo de duración y el generador. Copiamos el token en com.laboratorio.jwt.secret-word 
-Copiamos en https://www.base64decode.org/ podemos decodificar el token
En ervice creamos un paquete C:\Users\user\Documents\LINKEDINPosts\WSSpringBoot21\jaoRestaurante48\src\main\java\com\jao\recetas\services\security y pegamos las clases de UserDetailsServiceImpl, UsuarioService y UsuarioServiceImpl
Creamos JwtUtilsService.java que tendra las utilidades de JWT
Recuperamos las variables de jwt con value
Creamos un método para crear el token y otro para validarlo
Creamos un usuarioController, creamos un LoginInfo y tokenInfo que devolverá mi crontroller
En userDetailsServiceImpl creamos  el método login con TokenInfo
Creamos una excepción personalizada InvalidUserException
Hacemos la autenticación
Desde usuarioController llamamos al servicio de seguridad que hemos implementado
En globalExceptionHandler capturamos los errores
Creamos jwtRequestFilter en security
Modificamos la configuración global para usar estás clases de jwt e implementamos los métodos de builder.
Vamos a postman, nos logamos, nos da un token si el usuario existe con clave y con ese token consultamos las recetas pasando bearer y token
Si el token no es el dado tras login no da acceso, si el usuario y pass no es correcto no nos da token, si solo pasamos un parametro en login nos dice que usu y pass son obligatorios
- Web interesante www.jwt.io donde podemos ver info del token creado y podos ver la vericidad del firma del token pasando la clave del application.properties


Probamos desde postman el POST de http://localhost:8090/api/usuarios/login

52.- Spring mail . Vamos a crear un servicio y un endpoint para mandar mails 
- creamos el starter mail
- Creamos una clave en Gmail de doble autenticacion en mi usuario de gmail  y la variable del sistema
- Creamos la configuración en application.properties
- Creamos un serviceMail , impl. El mailInfo, el controller y el exception en el GlobalExceptionHandler
- creamos en endpoint en usuarios
- tenemos que habilitar el endpoint en el securityConfig
- Probamos en postman: nos logamos en el endpoint de http://localhost:8090/api/usuarios/login , nos da un token y desde http://localhost:8090/api/usuarios/email nos permite crear un JSON
para mandar el mail. Si el token no es correcto nos devuelve codigo.9.No está autorizado para hacer esta solicitud
