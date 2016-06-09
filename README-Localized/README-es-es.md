# Ejemplo de Meeting Feedback de Office 365 para Android

[![Ejemplo Meeting Feedback para Android](../readme-images/O365-Android-MeetingFeedback-video_play_icon.png)](http://youtu.be/VXdEtKIPxi8 "Haga clic para ver el ejemplo en funcionamiento")

Meeting Feedback usa los puntos de conexión de Office 365 en Microsoft Graph para recuperar los eventos de calendario y enviar correos electrónicos en nombre del usuario con sesión iniciada.

## Requisitos del dispositivo

Para ejecutar el ejemplo, el dispositivo debe cumplir los siguientes requisitos:

* Nivel de API de Android 15 o posterior.
 
## Requisitos previos

El ejemplo incluye un cliente de Android y un componente del lado del servidor Ruby on Rails. También debe configurar un alias desde donde los usuarios puedan obtener notificaciones de correo electrónico.

### Componente de cliente de Android

* [Android Studio](http://developer.android.com/sdk/index.html) versión 1.0 o posterior.
* Una cuenta de Office 365. Puede registrarse en [una suscripción a Office 365 Developer](http://aka.ms/o365-android-connect-signup), que incluye los recursos que necesita para comenzar a crear aplicaciones de Office 365. Si recibe una suscripción de desarrollador obtendrá también una cuenta de Office 365. 
* Los valores de identificador de cliente e identificador URI de redireccionamiento de una aplicación registrada en Azure. La aplicación debe ejecutar los permisos **Enviar correo como un usuario** y **Leer los calendarios de usuarios**. También puede [agregar una aplicación de cliente nativo en Azure](https://msdn.microsoft.com/office/office365/HowTo/add-common-consent-manually#bk_RegisterNativeApp) y [concederle los permisos adecuados](https://github.com/OfficeDev/O365-Android-MeetingFeedback/wiki/Grant-permissions-to-the-application-in-Azure).

### Componente de la aplicación de Ruby on Rails

* Ruby 2.0.0
* Kit de desarrollo de Ruby (solo para instalaciones de Windows)

## Instalar la aplicación de Ruby on Rails

1. Vaya a la carpeta webservice en el repositorio Android\_MeetingFeedback de O365.
2. Ejecute los comandos siguientes para realizar las tareas siguientes:

	* Instalar builder gem
	* Instalar la aplicación Meeting Feedback de Ruby on Rails
	* Crear la base de datos de Meeting Feedback
	* Iniciar el servicio de Meeting Feedback en el puerto 5000

```
gem install builder
bundle install
rake db:create && rake db:migrate && bundle exec foreman start -p 5000
```

## Configurar un grupo de correo electrónico para recibir notificaciones anónimas de correo electrónico

La aplicación envía una notificación por correo electrónico al organizador de la reunión cuando una de las reuniones que hospeda se revisa. El correo electrónico proviene de una dirección de correo electrónico designada para mantener los comentarios anónimos. Debe configurar un grupo de Exchange en Office 365 para que esto funcione. Para configurar un grupo de Exchange, siga estos pasos.

1. Vaya a http://office.com e inicie sesión con un administrador de inquilinos.
2. Haga clic en el icono **Administrador**.
3. En la barra de navegación, haga clic en **Administrador** -> **Exchange**.
4. Haga clic en **grupos** en la sección **destinatarios**.
5. Haga clic en el signo más para agregar un nuevo **grupo de seguridad**.
6. Agregue un nombre para mostrar, un alias y una dirección de correo electrónico y guarde el grupo.
7. Seleccione el grupo y haga clic en el icono de lápiz para editar.
8. Vaya a **delegación de grupo** y proporcione a los usuarios de la aplicación el permiso **Enviar como** tal y como se muestra en la siguiente imagen. ![Ejemplo Meeting Feedback de Office 365](../readme-images/O365-Android-MeetingFeedback-SendAs.png "Permiso Enviar como en un grupo de Exchange")
9. Actualice el valor de **REVIEW\_SENDER\_ADDRESS** en el archivo **Constants.java** para que coincida con la dirección de correo electrónico de su grupo.
 

## Abrir el ejemplo con Android Studio

1. Instale [Android Studio](http://developer.android.com/tools/studio/index.html#install-updates) y agregue los paquetes del SDK de Android según las [instrucciones](http://developer.android.com/sdk/installing/adding-packages.html) de developer.android.com.
2. Descargue o clone este ejemplo.
3. Inicie Android Studio.
	1. Seleccione **Abrir un proyecto existente de Android Studio**.
	2. Vaya a su repositorio local y seleccione el proyecto Android-O365-MeetingFeedback. Haga clic en **Aceptar**.
4. Abra el archivo Constants.java.
	1. Busque la constante SERVICE\_ENDPOINT y establezca el valor en la dirección IP de la máquina y el puerto donde está ejecutando el servicio de Ruby on Rails.
	2. Busque la constante CLIENT\_ID y establezca el mismo valor de cadena que el valor del identificador de cliente que registró en Azure Active Directory.
	3. Busque la constante REDIRECT\_URI y establezca el mismo valor de cadena que el valor del identificador URI de redireccionamiento que registró en Azure Active Directory. ![Ejemplo Meeting Feedback de Office 365](../readme-images/O365-Android-MeetingFeedback-Constants.png "Valores de identificador de cliente y de identificador URI de redireccionamiento en el archivo Constants")

    > Nota: Si no dispone de los valores CLIENT\_ID y REDIRECT\_URI, [agregue una aplicación de cliente nativo a Azure](https://msdn.microsoft.com/office/office365/HowTo/add-common-consent-manually#bk_RegisterNativeApp) y anote los valores CLIENT\_ID y REDIRECT\_URI.

Una vez creado el ejemplo, puede ejecutarlo en un emulador o en un dispositivo.

## Preguntas y comentarios

Nos encantaría recibir sus comentarios acerca del ejemplo Meeting Feedback de Office 365. Puede enviarnos sus preguntas y sugerencias a través de la sección [Problemas](https://github.com/OfficeDev/O365-Android-Connect/issues) de este repositorio.

Las preguntas generales sobre desarrollo en Office 365 deben publicarse en [Stack Overflow](http://stackoverflow.com/questions/tagged/Office365+API). Asegúrese de que sus preguntas o comentarios se etiquetan con [Office365] y [API].

¿Desea leer acerca de nuestro recorrido como desarrolladores creando este ejemplo? Lea nuestro [artículo de Medium](https://medium.com/p/572432b96089).

## Recursos adicionales

* [Información general sobre la plataforma de las API de Office 365](https://msdn.microsoft.com/office/office365/howto/platform-development-overview)  
* [SDK de Office 365 para Android](https://github.com/OfficeDev/Office-365-SDK-for-Android)  
* [Introducción a las API de Office 365 en aplicaciones](https://msdn.microsoft.com/office/office365/howto/getting-started-Office-365-APIs)  
* [Proyectos de inicio de las API de Office 365 y ejemplos de código](https://msdn.microsoft.com/office/office365/howto/starter-projects-and-code-samples)  
* [Ejemplo Connect de Office 365 para Android](https://github.com/OfficeDev/O365-Android-Connect)  
* [Fragmentos de código de Office 365 para Android](https://github.com/OfficeDev/O365-Android-Snippets)  
* [Proyecto de inicio de las API de Office 365 para Android](https://github.com/OfficeDev/O365-Android-Start)  
*[Ejemplo de perfil de Office 365 para Android](https://github.com/OfficeDev/O365-Android-Profile)  


## Copyright
Copyright (c) 2015 Microsoft. Todos los derechos reservados.
