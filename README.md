# Microsoft Graph SDK Implementation in JAVA
 
## Upload a file to personal one drive, using authorization code flow

* Download or clone this maven project and open in your favourite IDE.
* Start the Spring Boot Server \[Your app].
* Follow initial steps to register your app in the Azure portal and add a web platform.
* Make sure to add the "http://localhost:6060/api/msft/rdir" as the redirect uri.
* Authorize your app, by calling the authorize endpoint.
* After the consent, you will be redirected to your app via the redirect-uri you have mentioned, with an authorization code.
* The application will then be initialized with the Auth Code flow. I have provided example to upload file to personal one drive account.

**Note:**
 * The tenant here is "consumers" as this was implemented to work with personal microsoft accounts.
