# Android 的 Office 365 會議意見反應範例

[ ![Android 的會議意見反應範例](../readme-images/O365-Android-MeetingFeedback-video_play_icon.png)](http://youtu.be/VXdEtKIPxi8 "按一下以查看執行中的範例")  

會議意見反應會使用 Microsoft Graph 中的 Office 365 端點，代表登入的使用者擷取行事曆事件及傳送電子郵件。

## 裝置需求

若要執行範例，您的裝置必須符合下列需求：

* Android API 層級 15 或更高層級。
 
## 必要條件

這個範例包含 Android 用戶端和 Rails 上 Ruby 伺服器端元件。您也必須從您的使用者可以從中取得電子郵件通知的位置中設定別名。

### Android 用戶端元件

* [Android Studio](http://developer.android.com/sdk/index.html) 1.0 版或更新版本。
* Office 365 帳戶。您可以註冊 [Office 365 開發人員訂用帳戶](http://aka.ms/o365-android-connect-signup)，其中包含開始建置 Office 365 應用程式所需的資源。如果您收到開發人員訂閱，也會取得 Office 365 帳戶。 
* 在 Azure 中註冊之應用程式的用戶端識別碼和重新導向 URI 值。應用程式必須執行 [以使用者身分傳送郵件] 和 [讀取使用者行事曆] 權限。您也可以[在 Azure 中新增 Web 應用程式](https://msdn.microsoft.com/office/office365/HowTo/add-common-consent-manually#bk_RegisterNativeApp)和[授與適當的權限](https://github.com/OfficeDev/O365-Android-MeetingFeedback/wiki/Grant-permissions-to-the-application-in-Azure)給它。

### Rails 上 Ruby 應用程式元件

* Ruby 2.0.0
* Ruby 開發套件 (僅適用於 Windows 安裝)

## 安裝 Rails 上 Ruby 應用程式

1. 移至 O365 Android\_MeetingFeedback 儲存機制中的 webservice 資料夾。
2. 執行下列命令以執行下列工作：

	* 安裝產生器 gem
	* 安裝會議意見反應 Rails 上 Ruby 應用程式
	* 建立會議意見反應資料庫
	* 在連接埠 5000 上啟動會議意見反應服務

```
gem install builder
bundle install
rake db:create && rake db:migrate && bundle exec foreman start -p 5000
```

## 安裝電子郵件群組以接收匿名電子郵件通知

檢閱會議召集人主持的其中一個會議時，應用程式會將電子郵件通知傳給他。電子郵件是來自指定的電子郵件地址，以維持意見反應匿名。您必須在 Office 365 上設定 Exchange 群組，此工作才能運作。要安裝 Exchange 群組，請依照下列步驟執行。

1. 移至 http://office.com 並以承租人管理員身分登入。
2. 按一下 [管理] 磚。
3. 在導覽列中，按一下 [管理] -> [Exchange]。
4. 按一下 [收件者] 區段中的 [群組]。
5. 按一下加號以新增 [安全性群組]。
6. 新增顯示名稱、別名和電子郵件地址，並儲存群組。
7. 選取群組，再按一下鉛筆圖示進行編輯。
8. 移至 [群組委派]，並將 [傳送] 權限提供給應用程式使用者，如下列圖片所示。![Office 365 會議意見反應範例](../readme-images/O365-Android-MeetingFeedback-SendAs.png "Exchange 群組中的「以下列傳送」權限")
9. 更新 **Constants.java** 檔的值 **REVIEW\_SENDER\_ADDRESS**，以符合您群組的電子郵件地址。
 

## 使用 Android Studio 開啟範例

1. 根據 developer.android.com 上的[指示](http://developer.android.com/sdk/installing/adding-packages.html)，安裝 [Android Studio](http://developer.android.com/tools/studio/index.html#install-updates) 及新增 Android SDK 套件。
2. 下載或複製這個範例。
3. 啟動 Android Studio。
	1. 選取 [開啟現有的 Android Studio 專案]。
	2. 瀏覽至您的本機儲存機制，並選取 [O365-Android-MeetingFeedback] 專案。按一下 [確定]。
4. 開啟 Constants.java 檔案。
	1. 尋找 SERVICE\_ENDPOINT 常數，並將值設定為正在執行 Rails 上 Ruby 服務的電腦 IP 位址和連接埠。
	2. 尋找 CLIENT\_ID 常數並將其字串值設定為等於您在 Azure Active Directory 中註冊的用戶端識別碼。
	3. 尋找 REDIRECT\_URI 常數並將其字串值設定為等於您在 Azure Active Directory 中註冊的重新導向 URI。![Office 365 會議意見反應範例](../readme-images/O365-Android-MeetingFeedback-Constants.png "Constants 檔案中的用戶端識別碼和重新導向 URI 值")

    > 注意事項： 如果您沒有 CLIENT\_ID 和 REDIRECT\_URI 值，請[在 Azure 中新增原生用戶端應用程式](https://msdn.microsoft.com/office/office365/HowTo/add-common-consent-manually#bk_RegisterNativeApp)並記下 CLIENT\_ID 和 REDIRECT\_URI。

一旦建置範例，您可以在模擬器或裝置上執行它。

## 問題與意見

我們樂於取得您有關 Office 365 會議意見反應範例的意見。您可以在此儲存機制的[問題](https://github.com/OfficeDev/O365-Android-Connect/issues)區段中，將您的問題及建議傳送給我們。

請在 [Stack Overflow](http://stackoverflow.com/questions/tagged/Office365+API) 提出有關 Office 365 開發的一般問題。務必以 [Office365] 和 [API] 標記您的問題或意見。

您要閱讀有關我們在建置本範例的開發人員歷程嗎？ 請閱讀我們的[媒體文件](https://medium.com/p/572432b96089)。

## 其他資源

* [Office 365 API 平台概觀](https://msdn.microsoft.com/office/office365/howto/platform-development-overview)
* [Office 365 SDK for Android](https://github.com/OfficeDev/Office-365-SDK-for-Android)
* [在應用程式中開始使用 Office 365 API](https://msdn.microsoft.com/office/office365/howto/getting-started-Office-365-APIs)
* [Office 365 API 入門專案和程式碼範例](https://msdn.microsoft.com/office/office365/howto/starter-projects-and-code-samples)
* [Android 的 Office 365 Connect 範例](https://github.com/OfficeDev/O365-Android-Connect)
* [Android 的 Office 365 程式碼片段](https://github.com/OfficeDev/O365-Android-Snippets)
* [Android 的 Office 365 API 入門專案](https://github.com/OfficeDev/O365-Android-Start)
* [適用於 Android 的 Office 365 Profile 範例](https://github.com/OfficeDev/O365-Android-Profile)

## 著作權
Copyright (c) 2015 Microsoft.著作權所有，並保留一切權利。
