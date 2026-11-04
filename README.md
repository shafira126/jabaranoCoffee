# Sistem Antrean Jabarano Cafe

## 1. Deskripsi Proyek
Proyek ini merupakan aplikasi desktop berbasis Java Swing yang dirancang untuk membantu pengelolaan antrean dan meja di Jabarano Cafe.

Saat ini, pemanggilan pelanggan yang mendapatkan giliran sering kali dilakukan secara manual oleh staf. Hal tersebut dapat menyebabkan pelanggan tidak mendengar panggilan atau harus terus bertanya mengenai status antreannya.

Melalui proyek ini, staf dapat mengelola antrean dan ketersediaan meja secara terpusat. Pelanggan juga dapat melihat informasi ketersediaan meja serta memeriksa status antrean menggunakan kode akses yang diberikan oleh staf.

### Tujuan
- Mempermudah staf dalam mengelola antrean pelanggan.
- Mengurangi kebutuhan staf untuk memanggil pelanggan secara manual.
- Memudahkan pelanggan dalam memantau status antrean.
- Menampilkan informasi ketersediaan meja secara lebih terstruktur.

## 2. Pengguna Aplikasi
| Pengguna | Hak Akses |
|---|---|
| Admin | Mengelola akun staf, data meja, dan melihat laporan antrean |
| Staff | Mendaftarkan pelanggan, mengelola antrean, mengalokasikan meja, dan memperbarui status meja |
| Customer | Melihat ketersediaan meja dan memeriksa status antrean menggunakan kode akses |

Pelanggan tidak perlu membuat akun atau melakukan login. Data pelanggan akan dimasukkan oleh staf ketika pelanggan datang ke kafe.

## 4. Gambaran Aplikasi dan Fungsionalitas
### 4.1. Login
Admin dan staf harus melakukan login menggunakan username dan password yang telah terdaftar.

### 4.2. Dashboard
Menampilkan ringkasan informasi operasional kafe, seperti:
- Jumlah pelanggan yang sedang mengantre.
- Jumlah meja yang tersedia.
- Jumlah meja yang sedang digunakan.

### 4.3. Manajemen Antrean
Staf dapat melakukan beberapa aktivitas berikut:
- Mendaftarkan pelanggan dengan memasukkan nama dan jumlah orang.
- Menghasilkan nomor antrean secara otomatis, misalnya A012.
- Menghasilkan kode akses unik untuk setiap pelanggan, misalnya JBQ482.
- Melihat daftar antrean yang sedang berlangsung.
- Memanggil pelanggan berikutnya.
- Mengubah status antrean: SEATED, FINISHED, CANCELLED, atau NO_SHOW.

### 4.4. Manajemen Meja
**Admin dapat:**
- Menambahkan data meja baru.
- Mengubah nomor dan kapasitas meja.
- Menghapus data meja yang tidak lagi digunakan.

**Staf dapat:**
- Melihat daftar meja beserta kapasitas dan statusnya.
- Mengalokasikan meja kepada pelanggan yang dipanggil.
- Memperbarui status meja sesuai kondisi operasional.

Status meja terdiri dari:
- **Available:** Meja tersedia.
- **Reserved:** Meja telah dialokasikan untuk pelanggan yang dipanggil.
- **Occupied:** Meja sedang digunakan pelanggan.
- **Cleaning:** Meja sedang dibersihkan.

### 4.5. Tampilan Pelanggan (Customer Display)
Pelanggan dapat melihat informasi melalui tampilan khusus yang disediakan oleh aplikasi.

Fitur yang direncanakan:
- Melihat denah sederhana meja beserta kapasitas dan statusnya. --> (nanya saran dulu)
- Memasukkan kode akses yang diberikan staf.
- Melihat nomor antrean dan posisi antrean.
- Melihat status antrean.
- Melihat nomor meja setelah mendapatkan panggilan.

Denah meja hanya berfungsi untuk menampilkan informasi ketersediaan. Pemilihan dan alokasi meja tetap dilakukan oleh staf.

### 4.6. Public Display

Menampilkan informasi antrean yang sedang dipanggil agar pelanggan dapat mengetahui giliran mereka tanpa harus terus bertanya kepada staf.

Informasi yang ditampilkan:
- Nomor antrean yang sedang dipanggil.
- Nomor meja yang dituju.
- Daftar antrean berikutnya.

### 4.7. Laporan (masih bingung)
Admin dapat melihat riwayat antrean dan jumlah pelanggan yang telah dilayani berdasarkan data yang tersimpan.

## 5. Perencanaan Alur Aplikasi
### 5.1. Alur Antrean Pelanggan
1. Pelanggan datang ke Jabarano Cafe.
2. Staf memasukkan nama pelanggan dan jumlah orang ke aplikasi.
3. Sistem menghasilkan nomor antrean dan kode akses secara otomatis.
4. Staf memberikan nomor antrean dan kode akses kepada pelanggan.
5. Pelanggan dapat melihat informasi ketersediaan meja dan memeriksa status antrean menggunakan kode akses.
6. Ketika meja tersedia, staf memanggil pelanggan berikutnya.
7. Staf mengalokasikan meja yang sesuai dengan jumlah pelanggan.
8. Sistem mengubah status antrean menjadi CALLED dan status meja menjadi RESERVED.
9. Informasi nomor antrean dan nomor meja ditampilkan pada Public Display.
10. Setelah pelanggan menempati meja, staf mengubah status antrean menjadi SEATED dan status meja menjadi OCCUPIED. Apabila pelanggan tidak datang, staf memberi status no_show dan status meja kembali available
11. Setelah pelanggan selesai, staf mengubah status meja menjadi CLEANING, kemudian menjadi AVAILABLE setelah meja siap digunakan kembali.

### 5.2. Alur Login
1. Admin atau staf membuka aplikasi.
2. Pengguna memasukkan username dan password.
3. Sistem memvalidasi data login.
4. Jika berhasil, pengguna diarahkan ke dashboard sesuai hak aksesnya.
5. Jika gagal, sistem menampilkan pesan kesalahan.

### 5.3. Alur Pengecekan Antrean oleh Pelanggan
1. Pelanggan membuka Customer Display.
2. Pelanggan melihat denah dan ketersediaan meja.
3. Pelanggan memasukkan kode akses.
4. Sistem mencari data antrean berdasarkan kode tersebut.
5. Jika kode valid, sistem menampilkan nomor antrean, posisi antrean, dan statusnya.
6. Jika pelanggan telah dipanggil, sistem juga menampilkan nomor meja yang harus dituju.

## 6. Rancangan Database
Database yang digunakan terdiri dari tiga tabel utama.

### 6.1. Tabel users
Menyimpan informasi akun admin dan staf.
| Nama Kolom | Tipe Data | Keterangan |
|---|---|---|
| id | INT | Primary Key, Auto Increment |
| username | VARCHAR(50) | Username pengguna |
| password_hash | VARCHAR(255) | Password yang telah di-hash |
| role | ENUM | ADMIN atau STAFF |

### 6.2. Tabel cafe_tables
Menyimpan informasi meja yang tersedia di kafe.
| Nama Kolom | Tipe Data | Keterangan |
|---|---|---|
| id | INT | Primary Key, Auto Increment |
| table_number | VARCHAR(10) | Nomor meja |
| capacity | INT | Kapasitas meja |
| status | ENUM | AVAILABLE, RESERVED, OCCUPIED, CLEANING |

### 6.3. Tabel queue_entries
Menyimpan informasi antrean pelanggan.
| Nama Kolom | Tipe Data | Keterangan |
|---|---|---|
| id | INT | Primary Key, Auto Increment |
| queue_number | VARCHAR(10) | Nomor antrean, reset harian |
| access_code | VARCHAR(20) | Kode akses pelanggan. unik |
| customer_name | VARCHAR(100) | Nama pelanggan |
| party_size | INT | Jumlah orang |
| table_id | INT | Foreign Key ke cafe_tables, boleh null (kosong selama WAITING) |
| status | ENUM | WAITING, CALLED, SEATED, FINISHED, CANCELLED, NO_SHOW |
| joined_at | DATETIME | Waktu pendaftaran |
| called_at | DATETIME | Waktu pemanggilan |
| queue_date | DATE | Tanggal antrean |

### 6.4. Relasi Antar Tabel
- Satu data meja dapat digunakan oleh banyak data antrean pada waktu yang berbeda.
- Setiap data antrean dapat memiliki satu meja yang dialokasikan.
- Tabel users menyimpan akun admin dan staf yang dapat mengakses sistem.

mermaid
    USERS {
        int id PK
        varchar username
        varchar password_hash
        enum role
    }

    CAFE_TABLES {
        int id PK
        varchar table_number
        int capacity
        enum status
    }

    QUEUE_ENTRIES {
        int id PK
        varchar queue_number
        varchar access_code
        varchar customer_name
        int party_size
        int table_id FK
        enum status
        datetime joined_at
        datetime called_at
        date queue_date
    }

    CAFE_TABLES ||--o{ QUEUE_ENTRIES : "dialokasikan ke"


## 7. Rancangan Class
Rancangan class dibagi menjadi beberapa bagian agar kode program lebih terstruktur.

### 7.1. Model

Class yang merepresentasikan data dalam database.

| Class | Atribut Utama |
|---|---|
| User | id, username, passwordHash, role |
| CafeTable | id, tableNumber, capacity, status |
| QueueEntry | id, queueNumber, accessCode, customerName, partySize, tableId, status, joinedAt |

### 7.2. Database

| Class | Tanggung Jawab |
|---|---|
| Koneksi | Mengatur koneksi Java dengan database MySQL |
| UserDAO | Mengelola data akun pengguna |
| TableDAO | Mengelola data meja |
| QueueDAO | Mengelola data antrean |

### 7.3. Service

| Class | Tanggung Jawab |
|---|---|
| AuthService | Memvalidasi proses login |
| QueueService | Mengatur proses antrean, membuat nomor antrean dan kode akses, serta memperbarui status antrean |
| TableService | Mengatur status meja dan mencari meja yang sesuai dengan jumlah pelanggan |

### 7.4. Form (Java Swing)

| Class | Tanggung Jawab |
|---|---|
| FormLogin | Tampilan login |
| FormDashboard | Tampilan dashboard |
| FormAntrean | Mengelola antrean pelanggan |
| FormMeja | Mengelola data dan status meja |
| FormCustomer | Menampilkan informasi meja dan status antrean pelanggan |
| FormDisplay | Menampilkan antrean yang sedang dipanggil |
| FormLaporan | Menampilkan riwayat antrean |
