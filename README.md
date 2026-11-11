# Sistem Antrean Jabarano Coffee

## 1. Deskripsi Proyek

Proyek ini merupakan aplikasi desktop berbasis Java Swing untuk mengelola antrean pelanggan di Jabarano Coffee. Aplikasi ini memungkinkan staff mendaftarkan pelanggan, mengatur status antrean, mengelola ketersediaan meja, dan menampilkan informasi antrean yang sedang dipanggil sehingga pelanggan dapat mengetahui perkembangan antreannya tanpa harus terus bertanya kepada staff.

### Latar Belakang

Saat ini, pemanggilan pelanggan dilakukan secara manual oleh staff. Hal ini menyebabkan pelanggan sering tidak mendengar panggilan atau harus terus bertanya mengenai status antreannya. Selain itu, pengelolaan antrean dan ketersediaan meja perlu dilakukan secara terintegrasi agar tidak terjadi kesalahan dalam penempatan pelanggan.

### Tujuan

- Mempermudah staff dalam mengelola antrean pelanggan.
- Mengurangi kebutuhan staff untuk memanggil pelanggan secara manual.
- Memudahkan pelanggan dalam memantau status antrean.
- Membantu staff mengelola ketersediaan meja berdasarkan kapasitasnya.
- Menyimpan riwayat perubahan status antrean untuk kebutuhan pemantauan.

### Teknologi

| Komponen | Teknologi |
| --- | --- |
| Interface | Java Swing |
| Database | MySQL |
| DB Connection | JDBC |

JabaQueue adalah aplikasi desktop berbasis Java Swing untuk mengelola antrean pelanggan di Jabarano Cafe. Aplikasi ini memungkinkan staf mendaftarkan pelanggan, mengatur status antrean, dan menampilkan informasi antrean yang sedang dipanggil — sehingga pelanggan dapat mengetahui perkembangan antreannya tanpa harus terus bertanya kepada staf.

### Latar Belakang

Saat ini, pemanggilan pelanggan dilakukan secara manual oleh staf. Hal ini menyebabkan pelanggan sering tidak mendengar panggilan atau harus terus bertanya mengenai status antreannya.

### Tujuan

- Mempermudah staf dalam mengelola antrean pelanggan.
- Mengurangi kebutuhan staf untuk memanggil pelanggan secara manual.
- Memudahkan pelanggan dalam memantau status antrean.

### Teknologi

| Komponen | Teknologi |
| --- | --- |
| Antarmuka | Java Swing |
| Database | MySQL |
| Koneksi DB | JDBC |

## 2. Pengguna Aplikasi

| Pengguna | Hak Akses |
| --- | --- |
| Staff | Login, mendaftarkan pelanggan, melihat daftar antrean, memanggil antrean, memilih meja, dan memperbarui status antrean |
| Customer | Melihat status antrean menggunakan kode akses (tanpa login atau registrasi) |

Data pelanggan dimasukkan oleh staff saat pelanggan datang ke Jabarano Coffee. Pelanggan tidak perlu membuat akun.

JabaQueue hanya memiliki satu jenis pengguna internal: **Staff**. Tidak ada role Admin terpisah.

| Pengguna | Hak Akses |
| --- | --- |
| Staff | Login, mendaftarkan pelanggan, mengelola antrean, memanggil antrean, memperbarui status antrean |
| Customer | Melihat status antrean menggunakan kode akses (tanpa login atau registrasi) |

Data pelanggan dimasukkan oleh staf saat pelanggan datang ke kafe. Pelanggan tidak perlu membuat akun.

## 3. Gambaran Aplikasi dan Fungsionalitas

### 3.1. Login

Staff login menggunakan username dan password. Jika berhasil, diarahkan ke dashboard. Jika gagal, muncul pesan kesalahan.

### 3.2. Dashboard

Menunjukkan ringkasan operasional:

- Jumlah pelanggan yang sedang mengantre.
- Jumlah pelanggan yang sudah dipanggil.
- Jumlah meja yang tersedia.
- Jumlah antrean yang sudah selesai dilayani.

### 3.3. Manajemen Antrean

Staff dapat:

- Mendaftarkan pelanggan (nama dan jumlah orang).
- Melihat daftar antrean aktif.
- Memanggil pelanggan berikutnya.
- Memilih meja yang tersedia dan sesuai dengan jumlah pelanggan.
- Mengubah status antrean.
- Melihat riwayat perubahan status antrean.

Sistem secara otomatis menghasilkan:

- **Nomor antrean**, contoh: `A001`, `A002` (reset setiap hari).
- **Kode akses unik**, contoh: `JBQ482`.

Status antrean:

| Status | Keterangan |
| --- | --- |
| `WAITING` | Pelanggan sedang menunggu |
| `CALLED` | Pelanggan sudah dipanggil dan mendapatkan meja |
| `FINISHED` | Pelanggan sudah selesai dilayani |
| `CANCELLED` | Antrean dibatalkan |

### 3.4. Manajemen Meja

Data meja disimpan di database dan digunakan untuk membantu staff menentukan meja yang sesuai dengan jumlah pelanggan.

Informasi meja meliputi:

- Nomor meja.
- Kapasitas meja.
- Status ketersediaan meja.

Status meja:

| Status | Keterangan |
| --- | --- |
| `AVAILABLE` | Meja tersedia |
| `RESERVED` | Meja sudah dialokasikan untuk pelanggan |

Data meja dapat dimasukkan melalui database. Tidak diperlukan halaman CRUD khusus untuk pengelolaan meja.

### 3.5. Customer Queue Checker

Pelanggan memasukkan kode akses untuk melihat:

- Nomor antrean.
- Posisi dalam antrean.
- Status antrean saat ini.
- Nomor meja (jika sudah dipanggil).

Tidak memerlukan login atau registrasi.

### 3.6. Public Display

Layar terpisah yang menampilkan nomor antrean yang sedang dipanggil beserta nomor meja yang dituju. Diperbarui otomatis saat staff menekan tombol panggil.

### 3.1. Login

Staf login menggunakan username dan password. Jika berhasil, diarahkan ke dashboard. Jika gagal, muncul pesan kesalahan.

### 3.2. Dashboard

Menunjukkan ringkasan operasional:

- Jumlah pelanggan yang sedang mengantre.
- Jumlah pelanggan yang sudah dipanggil.

### 3.3. Manajemen Antrean

Staf dapat:

- Mendaftarkan pelanggan (nama dan jumlah orang).
- Melihat daftar antrean aktif.
- Memanggil pelanggan berikutnya.
- Memilih nomor meja saat memanggil (dari daftar meja hardcode).
- Mengubah status antrean.

Sistem secara otomatis menghasilkan:

- **Nomor antrean**, contoh: `A001`, `A002` (reset setiap hari).
- **Kode akses unik**, contoh: `JBQ482`.

Status antrean:

| Status | Keterangan |
| --- | --- |
| `WAITING` | Pelanggan sedang menunggu |
| `CALLED` | Pelanggan sudah dipanggil |
| `FINISHED` | Pelanggan sudah selesai dilayani |
| `CANCELLED` | Antrean dibatalkan |

### 3.4. Customer Queue Checker

Pelanggan memasukkan kode akses untuk melihat:

- Nomor antrean.
- Posisi dalam antrean.
- Status antrean saat ini.
- Nomor meja (jika sudah dipanggil).

Tidak memerlukan login atau registrasi.

### 3.5. Public Display

Layar terpisah yang menampilkan nomor antrean yang sedang dipanggil beserta nomor meja yang dituju. Diperbarui otomatis saat staf menekan tombol panggil.

## 4. Perencanaan Alur Aplikasi

### 4.1. Alur Utama Antrean

1. Pelanggan datang ke Jabarano Coffee.
2. Staff memasukkan nama pelanggan dan jumlah orang.
3. Sistem menghasilkan nomor antrean dan kode akses secara otomatis.
4. Data antrean disimpan ke database dengan status `WAITING`.
5. Staff memberikan nomor antrean dan kode akses kepada pelanggan.
6. Pelanggan dapat mengecek status antreannya menggunakan kode akses di Customer Display.
7. Staff memilih antrean yang akan dipanggil dan menentukan meja yang tersedia serta sesuai dengan kapasitas pelanggan.
8. Sistem mengubah status antrean menjadi `CALLED` dan status meja menjadi `RESERVED`.
9. Nomor antrean dan meja ditampilkan di Public Display.
10. Setelah pelanggan selesai, staff mengubah status antrean menjadi `FINISHED` dan status meja kembali menjadi `AVAILABLE`.

### 4.2. Alur Login

1. Staff membuka aplikasi.
2. Staff memasukkan username dan password.
3. Sistem memvalidasi data login.
4. Jika berhasil → diarahkan ke dashboard. Jika gagal → muncul pesan kesalahan.

### 4.3. Alur Pengecekan Antrean oleh Pelanggan

1. Pelanggan membuka Customer Display.
2. Pelanggan memasukkan kode akses.
3. Sistem mencari data antrean berdasarkan kode tersebut.
4. Jika valid → sistem menampilkan nomor antrean, posisi, dan status.
5. Jika sudah dipanggil → sistem juga menampilkan nomor meja.

### 4.4. Alur Pengelolaan Meja

1. Staff membuka daftar meja yang tersedia.
2. Sistem menampilkan nomor meja, kapasitas, dan status meja.
3. Staff memilih meja yang sesuai dengan jumlah pelanggan.
4. Saat antrean dipanggil, status meja berubah menjadi `RESERVED`.
5. Setelah antrean selesai, status meja berubah kembali menjadi `AVAILABLE`.

### 4.1. Alur Utama Antrean

1. Pelanggan datang ke Jabarano Cafe.
2. Staf memasukkan nama pelanggan dan jumlah orang.
3. Sistem menghasilkan nomor antrean dan kode akses secara otomatis.
4. Staf memberikan nomor antrean dan kode akses kepada pelanggan.
5. Pelanggan dapat mengecek status antreannya menggunakan kode akses di Customer Display.
6. Staf memanggil pelanggan berikutnya dan memilih nomor meja.
7. Status antrean berubah menjadi `CALLED`. Nomor antrean dan meja ditampilkan di Public Display.
8. Setelah pelanggan selesai, staf mengubah status menjadi `FINISHED`.

### 4.2. Alur Login

1. Staf membuka aplikasi.
2. Staf memasukkan username dan password.
3. Sistem memvalidasi data login.
4. Jika berhasil → diarahkan ke dashboard. Jika gagal → muncul pesan kesalahan.

### 4.3. Alur Pengecekan Antrean oleh Pelanggan

1. Pelanggan membuka Customer Display.
2. Pelanggan memasukkan kode akses.
3. Sistem mencari data antrean berdasarkan kode tersebut.
4. Jika valid → sistem menampilkan nomor antrean, posisi, dan status.
5. Jika sudah dipanggil → sistem juga menampilkan nomor meja.

## 5. Rancangan Database

Database terdiri dari empat tabel, yaitu `users`, `cafe_tables`, `queue_entries`, dan `queue_status_history`.

### Tabel `users`

| Kolom | Tipe Data | Keterangan |
| --- | --- | --- |
| id | INT | Primary Key, Auto Increment |
| username | VARCHAR(50) | Username staff, Unique |
| password\_hash | VARCHAR(255) | Password yang disimpan dalam bentuk hash |
| created\_at | DATETIME | Waktu pembuatan akun |

### Tabel `cafe_tables`

| Kolom | Tipe Data | Keterangan |
| --- | --- | --- |
| id | INT | Primary Key, Auto Increment |
| table\_number | VARCHAR(10) | Nomor meja, Unique |
| capacity | INT | Kapasitas maksimal pelanggan |
| status | VARCHAR(20) | AVAILABLE atau RESERVED |

### Tabel `queue_entries`

| Kolom | Tipe Data | Keterangan |
| --- | --- | --- |
| id | INT | Primary Key, Auto Increment |
| queue\_number | VARCHAR(10) | Nomor antrean, contoh: `A001` |
| access\_code | VARCHAR(20) | Kode akses unik pelanggan |
| customer\_name | VARCHAR(100) | Nama pelanggan |
| party\_size | INT | Jumlah orang |
| table\_id | INT | Foreign Key ke `cafe_tables.id`, NULL selama WAITING |
| status | VARCHAR(20) | WAITING, CALLED, FINISHED, CANCELLED |
| joined\_at | DATETIME | Waktu pendaftaran |
| queue\_date | DATE | Tanggal antrean (untuk reset harian) |

Constraint dan Index:

- Kombinasi `queue_number` dan `queue_date` dibuat Unique agar nomor antrean tidak duplikat pada tanggal yang sama.
- `access_code` dibuat Unique.
- `table_id` menggunakan Foreign Key yang mengacu pada `cafe_tables.id`.
- Index pada `status` dan `queue_date` untuk membantu pencarian antrean aktif dan antrean harian.
- CHECK Constraint untuk memastikan `party_size` lebih dari nol.

### Tabel `queue_status_history`

| Kolom | Tipe Data | Keterangan |
| --- | --- | --- |
| id | INT | Primary Key, Auto Increment |
| queue\_id | INT | Foreign Key ke `queue_entries.id` |
| old\_status | VARCHAR(20) | Status antrean sebelumnya |
| new\_status | VARCHAR(20) | Status antrean terbaru |
| changed\_at | DATETIME | Waktu perubahan status |

Tabel ini digunakan untuk mencatat setiap perubahan status antrean, misalnya dari `WAITING` menjadi `CALLED`, sehingga riwayat antrean tetap tersimpan meskipun status pada tabel utama sudah berubah.

### Relasi Antar Tabel

- `cafe_tables` (1) ---- (N) `queue_entries`
- `queue_entries` (1) ---- (N) `queue_status_history`

### Fitur Database yang Digunakan

| Fitur | Nama | Fungsi |
| --- | --- | --- |
| Primary Key & Foreign Key | Relasi antar tabel | Menghubungkan data antrean, meja, dan riwayat status |
| Constraint | NOT NULL, UNIQUE, CHECK | Menjaga validitas dan konsistensi data |
| Index | `idx_queue_status_date` | Mempercepat pencarian antrean berdasarkan status dan tanggal |
| View | `v_queue_display` | Menggabungkan informasi antrean dan meja untuk ditampilkan pada Public Display |
| View | `v_daily_queue_summary` | Menampilkan ringkasan antrean harian |
| Trigger | `trg_queue_status_history` | Mencatat perubahan status antrean secara otomatis |
| Stored Procedure | `sp_call_queue` | Memanggil antrean dan memperbarui status meja serta antrean |
| Stored Procedure | `sp_finish_queue` | Menyelesaikan antrean dan mengembalikan status meja menjadi AVAILABLE |
| Query Agregat | COUNT, AVG, GROUP BY | Menghitung jumlah antrean, distribusi status, dan rata-rata waktu tunggu |

Database terdiri dari dua tabel. Tidak ada tabel `cafe_tables` — data meja di-hardcode langsung di dalam program.

### Tabel `users`

| Kolom | Tipe Data | Keterangan |
| --- | --- | --- |
| id | INT | Primary Key, Auto Increment |
| username | VARCHAR(50) | Username staf |
| password | VARCHAR(255) | Password |

### Tabel `queue_entries`

| Kolom | Tipe Data | Keterangan |
| --- | --- | --- |
| id | INT | Primary Key, Auto Increment |
| queue\_number | VARCHAR(10) | Nomor antrean, contoh: `A001` |
| access\_code | VARCHAR(20) | Kode akses unik pelanggan |
| customer\_name | VARCHAR(100) | Nama pelanggan |
| party\_size | INT | Jumlah orang |
| table\_number | VARCHAR(10) | Nomor meja (NULL selama WAITING) |
| status | VARCHAR(20) | WAITING, CALLED, FINISHED, CANCELLED |
| joined\_at | DATETIME | Waktu pendaftaran |
| queue\_date | DATE | Tanggal antrean (untuk reset harian) |

## 6. Rancangan Class

### Model

| Class | Atribut Utama |
| --- | --- |
| `User` | id, username, passwordHash |
| `CafeTable` | id, tableNumber, capacity, status |
| `QueueEntry` | id, queueNumber, accessCode, customerName, partySize, tableId, status, joinedAt |
| `QueueStatusHistory` | id, queueId, oldStatus, newStatus, changedAt |

### Database

| Class | Tanggung Jawab |
| --- | --- |
| `Koneksi` | Mengatur koneksi Java dengan MySQL |
| `UserDAO` | Mengelola data akun staff |
| `QueueDAO` | Mengelola data antrean dan memanggil stored procedure |
| `TableDAO` | Mengambil data meja dan ketersediaannya |
| `QueueHistoryDAO` | Mengambil riwayat perubahan status antrean |

### Service

| Class | Tanggung Jawab |
| --- | --- |
| `AuthService` | Memvalidasi proses login |
| `QueueService` | Membuat nomor antrean dan kode akses, mengatur antrean, serta menjalankan proses pemanggilan dan penyelesaian antrean |
| `TableService` | Mengelola pemeriksaan ketersediaan dan kapasitas meja |

### Form (Java Swing)

| Class | Tanggung Jawab |
| --- | --- |
| `FormLogin` | Tampilan login |
| `FormDashboard` | Tampilan utama dan ringkasan |
| `FormAntrean` | Mendaftarkan dan mengelola antrean |
| `FormCustomer` | Pelanggan mengecek status antrean |
| `FormDisplay` | Layar publik yang menampilkan antrean dipanggil |

### Model

| Class | Atribut Utama |
| --- | --- |
| `User` | id, username, password |
| `QueueEntry` | id, queueNumber, accessCode, customerName, partySize, tableNumber, status, joinedAt |

### Database

| Class | Tanggung Jawab |
| --- | --- |
| `Koneksi` | Mengatur koneksi Java dengan MySQL |
| `UserDAO` | Mengelola data akun staf |
| `QueueDAO` | Mengelola data antrean |

### Service

| Class | Tanggung Jawab |
| --- | --- |
| `AuthService` | Memvalidasi proses login |
| `QueueService` | Membuat nomor antrean dan kode akses, mengatur dan memperbarui status antrean |

### Form (Java Swing)

| Class | Tanggung Jawab |
| --- | --- |
| `FormLogin` | Tampilan login |
| `FormDashboard` | Tampilan utama dan ringkasan |
| `FormAntrean` | Mendaftarkan dan mengelola antrean |
| `FormCustomer` | Pelanggan mengecek status antrean |
| `FormDisplay` | Layar publik yang menampilkan antrean dipanggil |

## 7. Pembagian Tugas

Pola penulisan setiap class: constructor, getter, setter, input, proses, output.

### Kayla Hessa Ferdinan

| File | Keterangan |
| --- | --- |
| `resources/script.sql` | Bagian pembuatan database, tabel, constraint, dan data awal |
| `repository/Koneksi.java` | Koneksi JDBC |
| `entity/User.java` | Data staff |
| `repository/UserDAO.java` | CRUD data akun staff |
| `service/AuthService.java` | Validasi proses login |
| `view/FormLogin.java` | Form login |
| `view/FormDashboard.java` | Dashboard ringkasan operasional |
| `entity/QueueEntry.java` | Data antrean |

### Shafira Nailah Afifah

| File | Keterangan |
| --- | --- |
| `repository/QueueDAO.java` | Mengelola data antrean dan memanggil stored procedure |
| `service/QueueService.java` | Nomor antrean otomatis, kode akses, dan perubahan status antrean |
| `view/FormAntrean.java` | Form pendaftaran dan pengelolaan antrean |
| `entity/QueueStatusHistory.java` | Data riwayat perubahan status |
| `repository/QueueHistoryDAO.java` | Mengambil riwayat perubahan status antrean |

### M Rangga Tri Utama Abdillah

| File | Keterangan |
| --- | --- |
| `resources/script.sql` | Bagian view, trigger, dan stored procedure |
| `entity/CafeTable.java` | Data meja |
| `repository/TableDAO.java` | Mengambil data meja dan ketersediaannya |
| `service/TableService.java` | Pemeriksaan ketersediaan dan kapasitas meja |
| `view/FormCustomer.java` | Form pengecekan antrean oleh pelanggan |
| `view/FormDisplay.java` | Layar publik antrean dipanggil |

File `script.sql` dikerjakan bersama: Kayla menulis bagian tabel dan data awal, Rangga menambahkan view, trigger, dan stored procedure di bagian bawahnya.

## 8. Cara Menjalankan

1. Jalankan `src/main/resources/script.sql` di MySQL untuk membuat database.
2. Sesuaikan `url`, `user`, dan `password` di `Koneksi.java`.
3. Buka proyek di IDE (NetBeans atau IntelliJ), lalu jalankan `FormLogin.java`.
