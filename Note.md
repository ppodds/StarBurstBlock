# 專案小筆記

## 踩過的坑

1. 資源引用

    打包的資源要引用比較標準的作法應該要用
   ```java
   // 第一種
   App.class.getResource(path);
   // 第二種
   App.class.getResourceAsStream(path);
   ```
   這種作法才不會在打包後抓不到資源，由於code又臭又長，可以搭配ResourceManager之類的靜態class使用

2. 編碼問題

    在打包後讀取文字檔案，若是沒有手動指定編碼就會導致中文亂碼
   ```java
   Scanner scanner = new Scanner(App.class.getResourceAsStream(path), StandardCharsets.UTF_8);
   ```
   利用這種方法可以解決

   maven其實也會踩到編碼坑，因此在`pom.xml`裡面加上下面的一串就可以解決問題
   ```xml
   <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.encoding>UTF-8</maven.compiler.encoding>
        ...
    </properties>
   ```

3. 打包

   由於新版JavaFX拿掉打包用的組件，只能靠maven的shade插件把library一起包進去了...
   之前自己試著不用maven來打包也是失敗