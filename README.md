# Enoca Project

Enoca Project, kullanıcıların sepet yönetimi ve ürün ekleme işlemlerini kolaylaştıran bir e-ticaret uygulamasıdır. Bu proje, ürünlerin listelenmesi, sepete eklenmesi, sepetin güncellenmesi ve ürünlerin kaldırılması gibi temel işlevsellikleri içermektedir.

## Özellikler
- Kullanıcı ekleme
- Cart'a ürün ekleme silme
- Cart'ı boşaltmak.
- Cart'da bulunan ürünün adetini güncellemek. 
- Ürün oluşturma, güncelleme ve silme
- Sepet oluşturma ve yönetme
- Sepete ürün ekleme ve kaldırma
- Sepet toplam fiyat hesaplama
- Kullanıcı bilgileri ile ilişkilendirilmiş sepet yönetimi

## Teknolojiler

- Java
- Spring Boot
- JPA (Hibernate)
- H2 Database (Geliştirme için)
- Lombok
- Jakarta EE

## Kurulum

### Gereksinimler

- Java 17 veya üstü
- Maven
- IDE (IntelliJ IDEA, Eclipse vb.)

### Projeyi Klonlama

```bash
git clone https://github.com/kullanici_adi/enoca_project.git
cd enoca_project
 ```

### Uygulamayı Çalıştırma
```bash
mvn spring-boot:run
 ```
 **Uygulama, varsayılan olarak `http://localhost:8082` adresinde çalışacaktır.**
## API Kullanımı

### Ürünler

- **Ürün Bilgisi Alma**

  `GET /products/{id}`

- **Ürün Güncelleme**

  `PUT /products/{id}`

  **Body:**
  ```json
  {
    "name": "Güncellenmiş Ürün Adı",
    "price": 120.00,
    "stockQuantity": 30
  }
  ```
- **Ürün Silme**

  `DELETE /products/{id}`

- **Ürün Oluşturma**

  `POST /products`

**Body:**
  ```json

{
  "name": "Ürün Adı",
  "price": 100.00,
  "stockQuantity": 50
}
```
 ### Sepet
  - **Sepetteki Ürün Miktarını Güncelleme**

  `PUT /carts/{cartId}/products/{productId}/quantity`


- **Sepete Ürün Ekleme**

  `POST /carts/{cartId}/products/{productId}`


- **Sepetten Ürün Kaldırma**

  `DELETE /carts/{cartId}/products/{productId}`

- **Sepeti Alma**

  `GET /carts/{id}`

- **Sepeti Boşaltma**

  `DELETE /carts/{id}/empty`

 ### Siparişler
- **Sipariş Oluşturma**

  `POST /orders/place/{customerId}`


-**Sipariş Bilgisi Alma**

  `GET /orders/{id}`

-**Müşteri Siparişlerini Alma**

  `GET /orders/customer/{customerId}`

### Müşteriler
-**Müşteri Oluşturma**

  `POST /customers`

Body:

```json

{
  "firstName": "Enes",
  "lastName": "Akdoğan",
  "email": "enesakdogan948@gmail.com"
}
```
### Uygulama çalışırken ekran görüntüleri;
![image](https://github.com/user-attachments/assets/5a8a03a7-7199-4df9-8ca0-d3fc14c8390f)

![image](https://github.com/user-attachments/assets/3e02bdf6-b655-46d8-bbd4-1679c72511ae)


