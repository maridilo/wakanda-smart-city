# Wakanda Smart City - Sistema de Gestión de Servicios

###  Miembros del Grupo
* **María Díaz - Heredero López**
* **Cintia Santillan Garcia**
* **Suren Hashemi Allam**

---

###  Lógica de la Solución

El proyecto implementa una arquitectura de **Microservicios** distribuida basada en **Spring Cloud**, diseñada para gestionar los servicios de una ciudad inteligente (Tráfico, Emergencias, Residuos, Seguridad y Salud).

El flujo de la solución sigue estos principios técnicos:
1.  **Configuración Centralizada:** Todos los microservicios obtienen su configuración al arrancar desde un servidor central (`config-server`).
2.  **Descubrimiento de Servicios:** Los servicios se registran automáticamente en **Eureka** (`discovery-service`), permitiendo la comunicación sin conocer IPs fijas.
3.  **Punto Único de Entrada:** Un **API Gateway** recibe todas las peticiones externas y las enruta al microservicio correspondiente usando balanceo de carga (`LoadBalancer`) y gestionando CORS para el dashboard.
4.  **Comunicación entre Servicios:** El servicio de Emergencias se comunica de forma síncrona con el de Tráfico utilizando **OpenFeign** para coordinar acciones (ej: consultar semáforos ante un incendio).
5.  **Resiliencia:** Se implementa el patrón **Circuit Breaker** (Resilience4j) en el servicio de Tráfico para manejar fallos y ofrecer respuestas por defecto (*fallback*).
6.  **Monitoreo y Trazabilidad:** Se exponen métricas mediante **Actuator/Prometheus** y se inyectan identificadores de traza (**TraceID**) en los logs para el seguimiento de peticiones distribuidas.

---

###  Estructura de Archivos Relevantes

A continuación se detallan los archivos necesarios para entender la implementación de la solución:

#### 1. Infraestructura (Core)
* **`config-server/.../ConfigServerApplication.java`**: Habilita el servidor de configuración centralizado mediante la anotación `@EnableConfigServer`.
* **`config-server/.../resources/application.yml`**: Configura el perfil `native` para servir configuraciones desde la carpeta local `resources/config` sin necesidad de Git remoto.
* **`discovery-service/.../DiscoveryServiceApplication.java`**: Arranca el servidor de registro Eureka con `@EnableEurekaServer`.
* **`discovery-service/.../resources/application.yml`**: Define el puerto 8761 y desactiva el auto-registro del propio servidor para actuar como registro maestro.

#### 2. API Gateway (Enrutamiento)
* **`gateway-service/pom.xml`**: Define las dependencias críticas: `spring-cloud-starter-gateway` (Reactivo) y `spring-cloud-starter-loadbalancer` (necesario para resolver `lb://`).
* **`gateway-service/.../resources/application.yml`**: Configura las rutas dinámicas, los predicados de ruta y habilita `globalcors` para permitir la conexión desde el Dashboard HTML.

#### 3. Microservicios de Negocio
* **`traffic-service/.../TrafficController.java`**: Contiene la lógica de negocio de los semáforos e implementa la anotación `@CircuitBreaker` para tolerancia a fallos.
* **`traffic-service/.../resources/application.yml`**: Configura el patrón de logging para incluir el `traceId` y `spanId` en la consola (Trazabilidad).
* **`emergency-service/.../EmergencyServiceApplication.java`**: Habilita el cliente Feign (`@EnableFeignClients`) para permitir la inyección de otros microservicios.
* **`emergency-service/.../client/TrafficClient.java`**: Interfaz declarativa (Feign Client) que define la estructura para consumir el API del `traffic-service`.
* **`emergency-service/.../EmergencyController.java`**: Orquestador que recibe la alerta, consulta el estado del tráfico vía Feign y devuelve una respuesta combinada.
* **`waste-service/.../WasteController.java`**: Simula sensores IoT de contenedores inteligentes devolviendo estados de llenado aleatorios.
* **`security-service/.../SecurityController.java`**: Gestiona la lógica simulada de cámaras de vigilancia y detección de intrusos.
* **`health-service/.../HealthController.java`**: Provee información simulada en tiempo real sobre la capacidad y recursos del hospital.

#### 4. Frontend (Visualización)
* **`wakanda-dashboard.html`**: Interfaz web cliente que consume la API a través del Gateway (puerto 8080) para visualizar el estado de todos los servicios en tiempo real.
