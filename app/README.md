# Android App Template

Este template está diseñado para aplicaciones Android
usando MVVM + Clean Architecture y Dagger Hilt.
Contiene una estructura modular y escalable,
con Retrofit, coroutines y configuración centralizada.

## Estructura del proyecto

- `data/`: Capa de datos, incluye repositorios y fuentes de datos.
- `domain/`: Capa de dominio, contiene los casos de uso y modelos de negocio.
- `ui/`: Capa de presentación, incluye ViewModels y vistas.
- `core/`: Utilidades comunes y configuración centralizada.
- `di/`: Configuración de Dagger Hilt.

## Configuración

1. Abre el archivo `core/Config.kt` y actualiza los valores según tu proyecto:
    - `BASE_URL`: URL base de la API.
    - `TIMEOUT_SECONDS`: Tiempo de espera máximo para las peticiones.

## Cómo usar este template

1. Duplica el proyecto o clónalo desde el repositorio de GitHub.
2. Cambia el nombre del paquete en el archivo `build.gradle` y el `AndroidManifest.xml`.
3. Configura los valores en `Config.kt`.
4. ¡Listo! Ya puedes empezar a desarrollar tu app.
