# Aplicativo de exemplo Meeting Feedback do Office 365 para Android

[![Aplicativo de exemplo Meeting Feedback do Office 365 para Android](../readme-images/O365-Android-MeetingFeedback-video_play_icon.png)](http://youtu.be/VXdEtKIPxi8 "Clique para conferir o exemplo em ação")

O Meeting Feedback usa os pontos de extremidade do Office 365 no Microsoft Graph para recuperar os eventos de calendário e emails enviados em nome do usuário conectado.

## Requisitos do dispositivo

Para executar o exemplo, o dispositivo deve atender aos seguintes requisitos:

* API Android nível 15 ou superior.
 
## Pré-requisitos

O aplicativo de exemplo inclui um cliente Android e um componente do lado do servidor Ruby on Rails. Você também pode configurar um alias do local em que os usuários recebem notificações de email.

### Componente do cliente Android

* [Android Studio](http://developer.android.com/sdk/index.html) versão 1.0 ou posterior.
* Uma conta do Office 365. Inscreva-se para uma [Assinatura de Desenvolvedor do Office 365](http://aka.ms/o365-android-connect-signup), que inclui os recursos necessários para começar a criar aplicativos do Office 365. Se você receber uma assinatura de desenvolvedor, também obterá uma conta do Office 365. 
* Valores de uma ID do cliente e de um URI de redirecionamento de um aplicativo registrado no Azure. O aplicativo deve executar as permissões **Enviar um email como um usuário** e **Ler um calendário de usuário**. Você também pode [adicionar um aplicativo cliente nativo no Azure](https://msdn.microsoft.com/office/office365/HowTo/add-common-consent-manually#bk_RegisterNativeApp) e [conceder as permissões adequadas](https://github.com/OfficeDev/O365-Android-MeetingFeedback/wiki/Grant-permissions-to-the-application-in-Azure).

### Componente do aplicativo Rails no Ruby

* Ruby 2.0.0
* Kit de desenvolvimento do Ruby (para instalações do Windows)

## Instalar o aplicativo Ruby on Rails

1. Vá para a pasta webservice no repositório O365-Android_MeetingFeedback.
2. Execute os comandos a seguir para executar as seguintes tarefas:

	* Instalar o construtor GEM
	* Instalar o aplicativo Meeting Feedback Ruby on Rails
	* Crie o banco de dados de comentários de reunião
	* Inicie o serviço de comentários de reunião na porta 5000

```
gem install builder
bundle install
rake db:create && rake db:migrate && bundle exec foreman start -p 5000
```

## Configurar um grupo de email para receber notificações de email anônimo

O aplicativo envia uma notificação de email para o organizador da reunião quando uma das reuniões hospedadas é avaliada. O email vem de um endereço de email designado para manter os comentários anônimos. Você deve configurar um grupo do Exchange no Office 365 para que isso funcione. Para configurar um grupo do Exchange, siga estas etapas.

1. Vá para http://office.com e entre com um administrador de locatários.
2. Clique no bloco **Administrador**.
3. Na barra de navegação, clique em **Administrador** -&gt; **Exchange**.
4. Clique em **grupos** na seção **destinatários**.
5. Clique no sinal de mais para adicionar um **grupo de segurança**.
6. Adicionar um nome para exibição, um alias e um endereço para salvar o grupo.
7. Selecione o grupo e clique no ícone de lápis para editar.
8. Vá para **delegação de grupo** e forneça permissão aos usuários do aplicativo em **Enviar como**, conforme exibido na figura a seguir. ![Exemplo da reunião do Office 365](../readme-images/O365-Android-MeetingFeedback-SendAs.png "Permissão Enviar como em um grupo do Exchange")
9. Atualize o valor de **REVIEW\_SENDER\_ADDRESS** no arquivo **Constants.java** para corresponder ao endereço de email do seu grupo.
 

## Abra o exemplo usando o Android Studio

1. Instale o [Android Studio](http://developer.android.com/tools/studio/index.html#install-updates) e adicione os pacotes do SDK do Android de acordo com as [instruções](http://developer.android.com/sdk/installing/adding-packages.html) na página developer.android.com.
2. Baixe ou clone esse exemplo.
3. Inicie o Android Studio.
	1. Selecione **Abrir um projeto existente no Android Studio**.
	2. Navegue até o seu repositório local e selecione o projeto O365-Android-MeetingFeedback. Clique em **OK**.
4. Abra o arquivo Constants.java.
	1. Encontre a constante SERVICE\_ENDPOINT e defina o valor para o endereço IP do computador e a porta no local em que você está executando o serviço Ruby on Rails.
	2. Localize a constante CLIENT\_ID e defina o respectivo valor da cadeia de caracteres igual à ID do cliente registrada no Active Directory do Azure.
	3. Localize a constante REDIRECT\_URI e defina o respectivo valor da cadeia de caracteres igual à URI de redirecionamento registrada no Active Directory do Azure.
    ![Office 365 Meeting Feedback sample](../readme-images/O365-Android-MeetingFeedback-Constants.png "Client ID and Redirect URI values in Constants file")

    > Observação: caso não tenha os valores CLIENT\_ID e REDIRECT\_URI, [adicione um aplicativo cliente nativo no Azure](https://msdn.microsoft.com/office/office365/HowTo/add-common-consent-manually#bk_RegisterNativeApp) e anote os valores do CLIENT\_ID e do REDIRECT\_URI.

Depois de criar o exemplo, você poderá executá-lo em um emulador ou dispositivo.

## Perguntas e comentários

Adoraríamos receber seus comentários no aplicativo de exemplo Meeting Feedback do Office 365. Você pode enviar perguntas e sugestões na seção [Problemas](https://github.com/OfficeDev/O365-Android-Connect/issues) deste repositório.

As perguntas sobre o desenvolvimento do Office 365 em geral devem ser postadas no [Stack Overflow](http://stackoverflow.com/questions/tagged/Office365+API). Não deixe de marcar as perguntas ou comentários com [Office365] e [API].

Você deseja saber sobre a nossa criação da jornada de desenvolvedor deste exemplo? Leia o nosso [artigo Medium](https://medium.com/p/572432b96089).

## Recursos adicionais

* [Visão geral da plataforma de APIs do Office 365](https://msdn.microsoft.com/office/office365/howto/platform-development-overview)
* [SDK do Office 365 para Android](https://github.com/OfficeDev/Office-365-SDK-for-Android)
* [Introdução às APIs do Office 365 em aplicativos](https://msdn.microsoft.com/office/office365/howto/getting-started-Office-365-APIs)
* [Exemplos de código e projetos iniciais de APIs do Office 365](https://msdn.microsoft.com/office/office365/howto/starter-projects-and-code-samples)
* [Aplicativo de exemplo Office 365 Connect para Android](https://github.com/OfficeDev/O365-Android-Connect)
* [Trechos de código do Office 365 para Android](https://github.com/OfficeDev/O365-Android-Snippets)
* [Projeto inicial de APIs do Office 365 para Android](https://github.com/OfficeDev/O365-Android-Start)
* [Exemplo de perfil do Office 365 para Android](https://github.com/OfficeDev/O365-Android-Profile)

## Direitos autorais
Copyright © 2015 Microsoft. Todos os direitos reservados.

