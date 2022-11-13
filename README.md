# Proyecto CUPONIC - Proyecto de Ingeniería 2021
### 1º Ingeniería Informática

<p align="center">
  <img src="https://github.com/alxgarci/cuponic/blob/master/imgs/logo.png?raw=true" alt="cuponic logo" width="500"/>
</p>

<!-- MarkdownTOC -->

- [Descripción](#descripci%C3%B3n)
- [Interfaz](#interfaz)
	- [Usuarios](#usuarios)
	- [Menú de usuario](#men%C3%BA-de-usuario)
	- [Menú del administrador](#men%C3%BA-del-administrador)
- [Participantes](#participantes)
- [Librerías usadas](#librer%C3%ADas-usadas)

<!-- /MarkdownTOC -->


## Descripción

*Comparador de precios en tiempo real, crea tu lista de la compra y te dice en qué supermercado te sale más barato comprarlo. Contiene una base de datos de cupones actualizable por un administrador y muestra códigos QR*


## Interfaz

### Usuarios

![Usuarios](/imgs/06.png)

En la aplicación se puede elegir la forma de ingresar (como usuario o como administrador) y después esto se sale otro pequeño menú donde existen 2 formas para entrar:
- Loguearnos con un usuario previamente creado introduciendo nuestra contraseña
- Podremos registrarnos dentro de la aplicación en el caso de no tener un usuario ya dado de alta y crear una contraseña


### Menú de usuario

![Menu Usuario](/imgs/03.png)

- **Buscar promociones**: en esta opción el usuario puede buscar promociones que ya existen en la aplicación introduciendo el producto, el supermercado, el descuento o la fecha de caducidad.
- **Guardar productos en la lista**: el usuario puede fácilmente guardar el producto en una lista de productos introduciendo el nombre del producto y la cantidad del mismo. A continuación se podrán seguir añadiendo productos si introducimos “c” o saldremos si introducimos “s”
- **Eliminar productos de la lista**: el usuario puede fácilmente eliminar el producto de una lista introduciendo el nombre del producto a eliminar.
- **Mostrar lista de compra**: lista todos los elementos de añadidos a la lista por el usuario y el supermercado donde se encuentra más barato.


### Menú del administrador

![Menu Administrador](/imgs/03.png)

- **Buscar promociones**: en esta opción el administrador puede buscar promociones que ya existen en la aplicación introduciendo el producto, el supermercado, el descuento o la fecha de caducidad
- **Dar de alta promociones**: el administrador puede dar de alta promociones nuevas indicando cada vez el supermercado, el código de la promoción, el descuento y la fecha de caducidad
- **Dar de baja promociones**: el administrador puede eliminar unos promociones indicando cada vez el código de la promoción que desea eliminar.
- **Consultar Log**: muestra los eventos mas importantes que se han dado en la aplicación, por ejemplo todos los elementos añadidos a la lista de promociones por el administrador.


## Participantes
| Nombre           | Tarea                 |
| ---------------- |-----------------------|
| Alejandro GARCÍA | Todo el código        |
| Mohamed Oussama  | Documentación / Ideas |
| Adrián GARCÍA    | Presentaciones        |
| Daniel GUTIÉRREZ | Documentación	       |


## Librerías usadas
- [Zxing](https://mvnrepository.com/artifact/com.google.zxing) de Google: *impresión de códigos QR*
- [Jsoup](https://mvnrepository.com/artifact/org.jsoup/jsoup) de Jsoup: *obtención de datos de las páginas web*
- [Maven](https://maven.apache.org/) de Apache: *recursos del proyecto, construcción del .jar*