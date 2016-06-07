# 适用于 Android 的 Office 365 Meeting Feedback 示例

[![适用于 Android 的 Meeting Feedback 示例](../readme-images/O365-Android-MeetingFeedback-video_play_icon.png)](http://youtu.be/VXdEtKIPxi8 "单击查看活动示例")

Meeting Feedback 使用 Microsoft Graph 中的 Office 365 终结点检索日历事件并代表登录用户发送电子邮件。

## 设备要求

要运行该示例，您的设备需要满足以下要求：

* Android API 级别为 15 级或更高。
 
## 先决条件

此示例包括一个 Android 客户端和 Ruby on Rails 服务器端组件。您还必须设置您的用户可以从中获取电子邮件通知的别名。

### Android 客户端组件

* [Android Studio](http://developer.android.com/sdk/index.html) 1.0 或更高版本。
* Office 365 帐户。您可以注册 [Office 365 开发人员订阅](http://aka.ms/o365-android-connect-signup)，其中包含您开始构建 Office 365 应用所需的资源。如果您可以获取开发人员订阅，那么您也就拥有 Office 365 帐户。 
* 在 Azure 中注册的应用程序的客户端 ID 和重定向 URI 值。应用程序必须运行“以用户身份发送邮件”权限和“读取用户日历”权限。您还可以[在 Azure 中添加本机客户端应用程序](https://msdn.microsoft.com/office/office365/HowTo/add-common-consent-manually#bk_RegisterNativeApp)并[向其授予适当的权限](https://github.com/OfficeDev/O365-Android-MeetingFeedback/wiki/Grant-permissions-to-the-application-in-Azure)。

### Ruby on Rails 应用程序组件

* Ruby 2.0.0
* Ruby 开发工具包（仅适用于 Windows 安装）

## 安装 Ruby on Rails 应用程序

1. 转到 O365 Android\_MeetingFeedback 存储库中的 Web 服务文件夹。
2. 运行以下命令执行下列任务：

	* 安装生成器 gem
	* 安装 Meeting Feedback Ruby on Rails 应用程序
	* 创建 Meeting Feedback 数据库
	* 在端口 5000 上启动 Meeting Feedback 服务

```
gem install builder
bundle install
rake db:create && rake db:migrate && bundle exec foreman start -p 5000
```

## 设置电子邮件组接收匿名电子邮件通知

在审阅会议组织者主持的一个会议时，该应用向会议组织者发送电子邮件通知。电子邮件来自一个指定的电子邮件地址，以使反馈保持匿名状态。您必须在 Office 365 上设置一个 Exchange 组以便实现此目的。要设置 Exchange 组，请执行以下步骤。

1. 访问 http://office.com，并使用租户管理员帐户登录。
2. 单击“管理”磁贴。
3. 在导航栏上，单击“管理”->“Exchange”。
4. 单击“收件人”部分中的“组”。
5. 单击加号以添加新的“安全组”。
6. 添加显示名称、别名和电子邮件地址并保存组。
7. 选择组，并单击要编辑的铅笔图标。
8. 转到“组委派”并授予应用用户“代理发送”权限，如下图所示。![Office 365 Meeting Feedback 示例](../readme-images/O365-Android-MeetingFeedback-SendAs.png "Exchange 组中的“代理发送”权限")
9. 更新 **Constants.java** 文件中 **REVIEW\_SENDER\_ADDRESS** 的值以匹配组的电子邮件地址。
 

## 打开使用 Android Studio 的示例

1. 安装 [Android Studio](http://developer.android.com/tools/studio/index.html#install-updates) 并根据 developer.android.com 上的[说明](http://developer.android.com/sdk/installing/adding-packages.html)添加 Android SDK 程序包。
2. 下载或克隆该示例。
3. 启动 Android Studio。
	1. 选择“打开一个现有的 Android Studio 项目”。
	2. 浏览到您的本地存储库并选择 O365-Android-MeetingFeedback 项目。单击“确定”。
4. 打开 Constants.java 文件。
	1. 查找 SERVICE\_ENDPOINT 常数并将该值设置为在其中运行 Ruby on Rails 服务的计算机的 IP 地址和端口。
	2. 查找 CLIENT\_ID 常数并将其字符串值设置为与您在 Azure Active Directory 中注册的客户端 ID 相等。
	3. 查找 REDIRECT\_URI 常数并将其字符串值设置为与您在 Azure Active Directory 中注册的重定向 URI 相等。![Office 365 Meeting Feedback 示例](../readme-images/O365-Android-MeetingFeedback-Constants.png "常量文件中的客户端 ID 和重定向 URI 值。")

    > 注意： 如果您没有 CLIENT\_ID 和 REDIRECT\_URI 值，请[在 Azure 中添加本机客户端应用程序](https://msdn.microsoft.com/office/office365/HowTo/add-common-consent-manually#bk_RegisterNativeApp)并记录 CLIENT\_ID 和 REDIRECT\_URI。

构建示例后，您可以在仿真器或设备中运行。

## 问题和意见

我们希望获得有关 Office 365 Meeting Feedback 示例的意见。您可以在该存储库中的[问题](https://github.com/OfficeDev/O365-Android-Connect/issues)部分将问题和建议发送给我们。

与 Office 365 开发相关的问题一般应发布到 [Stack Overflow](http://stackoverflow.com/questions/tagged/Office365+API)。确保您的问题或意见使用了 [Office365] 和 [API] 标记。

是否想要阅读有关我们构建此示例的开发人员历程？ 阅读我们的[媒体文章](https://medium.com/p/572432b96089)。

## 其他资源

* [Office 365 API 平台概述](https://msdn.microsoft.com/office/office365/howto/platform-development-overview)  
* [适于 Android 的 Office 365 SDK](https://github.com/OfficeDev/Office-365-SDK-for-Android)  
* [应用中 Office 365 API 入门](https://msdn.microsoft.com/office/office365/howto/getting-started-Office-365-APIs)  
* [Office 365 API 初学者项目和代码示例](https://msdn.microsoft.com/office/office365/howto/starter-projects-and-code-samples)  
* [适用于 Android 的 Office 365 Connect 示例](https://github.com/OfficeDev/O365-Android-Connect)  
* [适用于 Android 的 Office 365 代码段](https://github.com/OfficeDev/O365-Android-Snippets)  
* [Android Office 365 API 初学者项目](https://github.com/OfficeDev/O365-Android-Start)  
* [Android Office 365 Profile 示例](https://github.com/OfficeDev/O365-Android-Profile)  


## 版权所有
版权所有 (c) 2015 Microsoft。保留所有权利。
