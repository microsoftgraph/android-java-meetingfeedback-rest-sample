# Exemple de Meeting Feedback Office 365 pour Android

[![Exemple de Meeting Feedback pour Android](../readme-images/O365-Android-MeetingFeedback-video_play_icon.png)](http://youtu.be/VXdEtKIPxi8 "Cliquez ici pour voir l’exemple en action")

Meeting Feedback utilise les points de terminaison Office 365 dans Microsoft Graph pour récupérer les événements de calendrier et envoyer des courriers électroniques pour l’utilisateur connecté.

## Configuration requise de l’appareil

Pour exécuter l’exemple, votre appareil doit respecter les exigences suivantes :

* Une API Android de niveau 15 ou supérieur.
 
## Conditions requises

L’exemple inclut un client Android et un composant côté serveur Ruby on Rails. Vous devez également configurer un alias à partir de l’endroit où vos utilisateurs peuvent recevoir des notifications par courrier électronique.

### Composant client Android

* [Android Studio](http://developer.android.com/sdk/index.html) version 1.0 ou ultérieure.
* Un compte Office 365. Vous pouvez vous inscrire à [Office 365 Developer](http://aka.ms/o365-android-connect-signup) pour accéder aux ressources dont vous avez besoin pour commencer à créer des applications Office 365. Si vous obtenez un abonnement développeur, vous obtenez également un compte Office 365. 
* Des valeurs d’ID client et d’URI de redirection d’une application inscrite auprès d’Azure. L’application doit exécuter les autorisations **Envoyer un courrier électronique en tant qu’utilisateur** et **Lecture et écriture de courrier électronique utilisateur**. Vous pouvez également [ajouter une application cliente native dans Azure](https://msdn.microsoft.com/office/office365/HowTo/add-common-consent-manually#bk_RegisterNativeApp) et [lui accorder les autorisations appropriées](https://github.com/OfficeDev/O365-Android-MeetingFeedback/wiki/Grant-permissions-to-the-application-in-Azure).

### Composant d’application Ruby on Rails

* Ruby 2.0.0
* Kit de développement Ruby (pour les installations Windows uniquement)

## Installer l’application Ruby on Rails

1. Accédez au dossier de service web dans le référentiel O365-Android\_MeetingFeedback.
2. Exécutez les commandes suivantes pour effectuer les tâches ci-dessous :

	* Installer le générateur GEM
	* Installer l’application Ruby on Rails Meeting Feedback
	* Créer la base de données Meeting Feedback
	* Démarrer le service Meeting Feedback sur le port 5 000

```
gem install builder
bundle install
rake db:create && rake db:migrate && bundle exec foreman start -p 5000
```

## Configurer un groupe de messagerie pour recevoir des notifications par courrier électronique anonyme

L’application envoie une notification par courrier électronique à l’organisateur de la réunion lorsqu’une réunion qu’il organise est évaluée. Le message provient d’une adresse de messagerie désignée pour conserver l’anonymat des commentaires. Vous devez configurer un groupe Exchange dans Office 365 pour que cela fonctionne. Pour configurer un groupe Exchange, procédez comme suit.

1. Accédez à http://office.com et connectez-vous à l’aide d’un administrateur client.
2. Cliquez sur la mosaïque **Administrateur**.
3. Dans la barre de navigation, cliquez sur **Administrateur** -> **Exchange**.
4. Cliquez sur **Groupes** dans la section **Destinataires**.
5. Cliquez sur le signe plus (+) pour ajouter un nouveau **groupe de sécurité**.
6. Ajouter un nom d’affichage, un alias et une adresse de messagerie, puis enregistrez le groupe.
7. Sélectionnez le groupe et cliquez sur l’icône de crayon à modifier.
8. Accédez à **Délégation de groupe** et accordez aux utilisateurs de l’application l’autorisation **Envoyer en tant que**, comme illustré dans l’image suivante. ![Exemple de Meeting Feedback Office 365](../readme-images/O365-Android-MeetingFeedback-SendAs.png "Autorisation Envoyer en tant que dans un groupe Exchange")
9. Mettez à jour la valeur **REVIEW\_SENDER\_ADDRESS** dans le fichier **Constants.java** pour qu’elle corresponde à l’adresse de messagerie de votre groupe.
 

## Ouverture de l’exemple à l’aide d’Android Studio

1. Installez [Android Studio](http://developer.android.com/tools/studio/index.html#install-updates) et ajoutez le kit de développement logiciel Android conformément aux [instructions](http://developer.android.com/sdk/installing/adding-packages.html) indiquées sur developer.android.com.
2. Téléchargez ou clonez cet exemple.
3. Démarrez Android Studio.
	1. Sélectionnez **Ouvrir un projet Android Studio existant**.
	2. Accédez à votre référentiel local et sélectionnez le projet O365-Android-MeetingFeedback. Cliquez sur **OK**.
4. Ouvrez le fichier Constants.java.
	1. Recherchez la constante SERVICE\_ENDPOINT et définissez la valeur sur l’adresse IP de l’ordinateur et le port à partir desquels vous exécutez le service Ruby on Rails.
	2. Recherchez la constante CLIENT\_ID et définissez sa valeur de chaîne sur l’ID client que vous avez inscrit auprès d’Azure Active Directory.
	3. Recherchez la constante REDIRECT\_URI et définissez sa valeur de chaîne sur l’URI de redirection que vous avez inscrite auprès d’Azure Active Directory. ![Exemple de Meeting Feedback Office 365](../readme-images/O365-Android-MeetingFeedback-Constants.png "Valeurs ID client et URI de redirection dans le fichier Constants")

    > Remarque : Si vous ne connaissez pas les valeurs de CLIENT\_ID et de REDIRECT\_URI, [ajoutez une application cliente native dans Azure](https://msdn.microsoft.com/office/office365/HowTo/add-common-consent-manually#bk_RegisterNativeApp) pour les noter.

Une fois l’exemple créé, vous pouvez l’exécuter sur un émulateur ou sur un appareil.

## Questions et commentaires

Nous aimerions connaître vos commentaires sur l’exemple de Meeting Feedback d’Office 365. Vous pouvez nous faire part de vos questions et suggestions dans la rubrique [Problèmes](https://github.com/OfficeDev/O365-Android-Connect/issues) de ce référentiel.

Si vous avez des questions sur le développement d’Office 365, envoyez-les sur [Stack Overflow](http://stackoverflow.com/questions/tagged/Office365+API). Posez vos questions avec les balises [API] et [Office365].

Vous souhaitez en savoir plus sur le création de cet exemple par nos développeurs ? Lisez notre [article Medium](https://medium.com/p/572432b96089).

## Ressources supplémentaires

* [Présentation de la plateforme des API Office 365](https://msdn.microsoft.com/office/office365/howto/platform-development-overview)
* [Kit de développement logiciel (SDK) Office 365 pour Android](https://github.com/OfficeDev/Office-365-SDK-for-Android)
* [Prise en main des API Office 365 dans les applications](https://msdn.microsoft.com/office/office365/howto/getting-started-Office-365-APIs)
* [Exemples de code et projets de lancement pour les API Office 365](https://msdn.microsoft.com/office/office365/howto/starter-projects-and-code-samples)
* [Exemple d’Office 365 Connect pour Android](https://github.com/OfficeDev/O365-Android-Connect)
* [Extraits de Code Office 365 pour Android](https://github.com/OfficeDev/O365-Android-Snippets)
* [Projet de lancement des API Office 365 pour Android](https://github.com/OfficeDev/O365-Android-Start)
* [Exemple de profil Office 365 pour Android](https://github.com/OfficeDev/O365-Android-Profile)


## Copyright
Copyright (c) 2015 Microsoft. Tous droits réservés.
