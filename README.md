# SmartLibV2
Java • Hibernate (JPA) • SQLite • Maven • Konsol Uygulaması

##  Proje Sahibi

- ****Ad Soyad:**** Samet ERDOĞAN

- ****Öğrenci Numarası:**** 20230108039

- ****Bölüm:**** Bilgisayar Programcılığı

- ****Ders Adı:**** Nesneye Dayalı Programlama 2

- ****Ders Kodu:**** BIP2037

- ****Öğretim Görevlisi:**** Emrah SARIÇİÇEK

- ****Teslim Tarihi:**** 15/01/2026

## Kısa Açıklama
SmartLibV2, Java + Hibernate (JPA) + SQLite kullanan konsol tabanlı bir kütüphane yönetim uygulamasıdır. CRUD, ödünç verme/iade, DAO katmanı ve temel OOP yapıları içerir.

## Özellikler
- Kitap işlemleri: ekle, listele, güncelle, sil  
- Öğrenci işlemleri: ekle, listele  
- Ödünç verme ve iade (Loan kaydı)  
- Entity ilişkileri: Book ↔ Loan (OneToMany / ManyToOne), Student ↔ Loan (OneToMany / ManyToOne)  
- LazyInitializationException önlemleri için DAO seviyesinde join-fetch kullanımı  
- SLF4J uyarılarını engellemek için basit SLF4J bağlayıcısı (pom.xml) ve show_sql kapalı

## Kullanılan Teknolojiler
- Java 17  
- Hibernate ORM (JPA)  
- SQLite (org.xerial:sqlite-jdbc)  
- Maven  
- Visual Studio Code (Windows)

## Proje Yapısı (Önemli Dosyalar)
- src/main/java/com/smartlibrary/app/Main.java — Uygulama giriş noktası  
- src/main/java/com/smartlibrary/entity/* — Entity sınıfları (Book, Student, Loan)  
- src/main/java/com/smartlibrary/dao/* — DAO/Repository sınıfları (BookDao, StudentDao, LoanDao)  
- src/main/resources/hibernate.cfg.xml — Hibernate konfigürasyonu  
- pom.xml — Maven bağımlılıkları (slf4j-simple ve sqlite-jdbc)

## Kurulum (Windows + Visual Studio Code)
1. VS Code ile proje klasörünü aç  
   File → Open Folder → ...nesneyeDayali2Final\smartLibV2

2. Gereksinimler
   - JDK 17 yüklü ve JAVA_HOME ayarlı (komut: java -version)  
   - Maven yüklü (komut: mvn -v)  
   - VS Code eklentileri önerilir: Language Support for Java, Debugger for Java, Maven for Java

3. Bağımlılıkları indir ve proje derle
```bash
cd "...nesneyeDayali2Final\smartLibV2"
mvn clean package
```

4. Uygulamayı çalıştır (terminal)
```bash
mvn exec:java -Dexec.mainClass="com.smartlibrary.app.Main"
```
veya derleme sonrası doğrudan:
```bash
java -cp target\classes;target\dependency\* com.smartlibrary.app.Main
```

## SLF4J ve SQL Çıktıları
- pom.xml içinde `org.slf4j:slf4j-simple` eklendi; bu sayede "Failed to load class org.slf4j.impl.StaticLoggerBinder" uyarısı ortadan kalkmalıdır.  
- show_sql yerine log seviyesi veya hibernate.cfg.xml'de `show_sql=false` kullanıldı; ham SQL konsolda gösterilmeyecektir.


## Veritabanı
- Uygulama çalıştığında proje kökünde otomatik oluşturulur: `smartlibrary.db`  
- Örnek tablolar: `books (id, title, author, year, status)`, `students (id, name, department)`, `loans (id, book_id, student_id, borrowDate, returnDate)`

## Kullanım Notları
1. Önce öğrenci ve kitap ekleyin.  
2. Kitabı ödünç verin.  
3. Ödünç listesi (6) ile kayıtları görüp iade işlemi (7) yapın.  
4. Konsolda SLF4J uyarısı görünüyorsa `mvn clean package` ile bağımlılıkların yüklendiğinden emin olun.

## Amaç
Bu proje, OOP, JPA/Hibernate, DAO/Repository desenleri ve SQLite ile gerçekçi bir uygulama deneyimi sunmayı hedefler. Akademik teslim ve öğrenme amaçlı kullanılabilir.
