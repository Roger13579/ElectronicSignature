#  電子簽章

## 安裝
- Java 21, Gradle

1. 取得專案
   ```
   git clone  https://github.com/Roger13579/ElectronicSignature.git
   ```
2. 移動到專案中
   ```
   cd ElectronicSignature
   ```
3. 根據 `application.template.properties` 內容來調整設定
   ```
   app.tsa-url= #Timestamp Authority Url
   app.cert-psd= #憑證密碼
   app.keystore-path= #憑證位址
   ```
4. 運行專案
   
5. 開啟專案
   在瀏覽器中前往 `http://localhost:8080/` 後，即可使用
6. Swagger doc : http://localhost:8080/swagger-ui/index.html

## Tech Stack
- Java 21
- SpringBoot 3.4.0
- Spring Data JPA
- Spring Modulith
- Spring Event
- Swagger