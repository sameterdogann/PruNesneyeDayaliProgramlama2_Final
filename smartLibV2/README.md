# smartLibV2

Basit bir Hibernate + SQLite tabanlı örnek proje şablonu.

Nasıl derlenir ve çalıştırılır:

1. Maven ile derleyin:

```
mvn package
```

2. Uygulamayı çalıştırın:

```
mvn exec:java -Dexec.mainClass="com.smartlibrary.app.Main"
```

Notlar:
- `hibernate.cfg.xml` proje kökünde oluşturulacak `smartlibrary.db` SQLite veritabanını kullanır.
- Gerekli bağımlılıklar `pom.xml` içinde tanımlıdır.
 
Not: Proje dizin adını da yerel dosya sisteminde `smartLibV2` yapmak isterseniz söyleyin, taşıma/yeniden adlandırma yapabilirim.
