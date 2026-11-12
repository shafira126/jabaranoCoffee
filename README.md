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

## 2. Pengguna Aplikasi

| Pengguna | Hak Akses |
| --- | --- |
| Staff | Login, mendaftarkan pelanggan, melihat daftar antrean, memanggil antrean, memilih meja, dan memperbarui status antrean |
| Customer | Melihat status antrean menggunakan kode akses (tanpa login atau registrasi) |

Data pelanggan dimasukkan oleh staff saat pelanggan datang ke Jabarano Coffee. Pelanggan tidak perlu membuat akun.

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

Data meja dimasukkan melalui database (tidak ada halaman CRUD khusus untuk pengelolaan meja).

### 3.5. Customer Queue Checker

Pelanggan memasukkan kode akses untuk melihat:

- Nomor antrean.
- Posisi dalam antrean.
- Status antrean saat ini.
- Nomor meja (jika sudah dipanggil).

Tidak memerlukan login atau registrasi.

### 3.6. Public Display

Layar terpisah yang menampilkan nomor antrean yang sedang dipanggil beserta nomor meja yang dituju. Informasi diperbarui secara otomatis menggunakan Swing Timer yang mengambil data terbaru dari database secara berkala.

## 4. Perencanaan Alur Aplikasi

### 4.1. Alur Utama Antrean

1. Pelanggan datang ke Jabarano Coffee.
2. Staff memasukkan nama pelanggan dan jumlah orang.
3. Sistem menghasilkan nomor antrean dan kode akses secara otomatis.
4. Data antrean disimpan ke database dengan status `WAITING`.
5. Staff memberikan nomor antrean dan kode akses kepada pelanggan.
6. Pelanggan dapat mengecek status antreannya menggunakan kode akses di Customer Queue Checker.
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

1. Pelanggan membuka Customer Queue Checker.
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

## 5. Rancangan Database

Database terdiri dari empat tabel, yaitu `users`, `cafe_tables`, `queue_entries`, dan `queue_status_history`.

### Tabel `users`

| Kolom | Tipe Data | Keterangan |
| --- | --- | --- |
| id | INT | Primary Key, Auto Increment |
| username | VARCHAR(50) | Username staff, Unique |
| password | VARCHAR(255) | Password staff |
| created_at | DATETIME | Waktu pembuatan akun |

### Tabel `cafe_tables`

| Kolom | Tipe Data | Keterangan |
| --- | --- | --- |
| id | INT | Primary Key, Auto Increment |
| table_number | VARCHAR(10) | Nomor meja, Unique |
| capacity | INT | Kapasitas maksimal pelanggan |
| status | VARCHAR(20) | AVAILABLE atau RESERVED |

### Tabel `queue_entries`

| Kolom | Tipe Data | Keterangan |
| --- | --- | --- |
| id | INT | Primary Key, Auto Increment |
| queue_number | VARCHAR(10) | Nomor antrean, contoh: `A001` |
| access_code | VARCHAR(20) | Kode akses unik pelanggan |
| customer_name | VARCHAR(100) | Nama pelanggan |
| party_size | INT | Jumlah orang |
| table_id | INT | Foreign Key ke `cafe_tables.id`, NULL selama WAITING |
| status | VARCHAR(20) | WAITING, CALLED, FINISHED, CANCELLED |
| joined_at | DATETIME | Waktu pendaftaran |
| called_at | DATETIME | Waktu antrean dipanggil, NULL selama WAITING |
| queue_date | DATE | Tanggal antrean (untuk reset harian) |

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
| queue_id | INT | Foreign Key ke `queue_entries.id` |
| old_status | VARCHAR(20) | Status antrean sebelumnya |
| new_status | VARCHAR(20) | Status antrean terbaru |
| changed_at | DATETIME | Waktu perubahan status |

Tabel ini digunakan untuk mencatat setiap perubahan status antrean, misalnya dari `WAITING` menjadi `CALLED`, sehingga riwayat antrean tetap tersimpan meskipun status pada tabel utama sudah berubah. Pencatatan dilakukan secara otomatis oleh trigger database.

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
| Trigger | `trg_queue_status_history` | Mencatat perubahan status antrean secara otomatis |
| Stored Procedure | `sp_call_queue` | Memanggil antrean, mengatur alokasi meja, serta memperbarui status antrean dan meja. Mengisi `called_at` menggunakan waktu saat antrean dipanggil |
| Stored Procedure | `sp_finish_queue` | Menyelesaikan antrean dan mengembalikan status meja menjadi AVAILABLE |
| Query Agregat | COUNT, AVG, GROUP BY | Menghitung jumlah antrean, distribusi status, dan rata-rata waktu tunggu berdasarkan `joined_at` dan `called_at` |

## 6. Rancangan Class

### Model

| Class | Atribut Utama |
| --- | --- |
| `User` | id, username, password |
| `CafeTable` | id, tableNumber, capacity, status |
| `QueueEntry` | id, queueNumber, accessCode, customerName, partySize, tableId, status, joinedAt, calledAt |
| `QueueStatusHistory` | id, queueId, oldStatus, newStatus, changedAt |

### Repository

| Class | Tanggung Jawab |
| --- | --- |
| `Koneksi` | Mengatur koneksi Java dengan MySQL melalui JDBC. Menyediakan `getConnection()` yang menghasilkan koneksi baru untuk setiap operasi |
| `UserRepository` | Mengelola data akun staff dan proses login |
| `QueueRepository` | Mengelola data antrean, pengecekan kode akses dan posisi antrean, pemanggilan serta penyelesaian antrean, riwayat status, dan data yang dibutuhkan untuk Customer Queue Checker serta Public Display |
| `TableRepository` | Mengambil data meja, kapasitas, dan ketersediaannya |

### Form (Java Swing)

| Class | Tanggung Jawab |
| --- | --- |
| `FormLogin` | Tampilan login |
| `FormDashboard` | Tampilan utama dan ringkasan operasional |
| `FormAntrean` | Mendaftarkan dan mengelola antrean |
| `FormCustomer` | Pelanggan mengecek status antrean |
| `FormDisplay` | Layar publik yang menampilkan antrean dipanggil |

## 7. Pembagian Tugas

Pola penulisan setiap class: constructor, getter, setter, input, proses, output.

### Kayla Hessa Ferdinan

**Tanggung jawab: Database Foundation & Authentication**

| File | Keterangan |
| --- | --- |
| `resources/script.sql` | Mengintegrasikan seluruh SQL, termasuk pembuatan database, tabel, constraint, index, view, trigger, dan stored procedure |
| `resources/seed.sql` | Menyediakan data awal berupa satu akun staff dan beberapa data meja dengan kapasitas berbeda |
| `repository/Koneksi.java` | Menyediakan koneksi JDBC melalui `getConnection()`, dengan koneksi baru untuk setiap operasi repository |
| `entity/User.java` | Data staff |
| `repository/UserRepository.java` | Mengelola data akun staff dan proses login |
| `view/FormLogin.java` | Form login |
| `entity/QueueEntry.java` | Data antrean |

### Shafira Nailah Afifah

**Tanggung jawab: Queue Management**

| File | Keterangan |
| --- | --- |
| `repository/QueueRepository.java` | Mengelola data antrean, pengecekan kode akses dan posisi antrean, serta memanggil stored procedure |
| `view/FormAntrean.java` | Form pendaftaran dan pengelolaan antrean |
| `entity/QueueStatusHistory.java` | Data riwayat perubahan status |

### M Rangga Tri Utama Abdillah

**Tanggung jawab: Table Management & Display**

| File | Keterangan |
| --- | --- |
| `entity/CafeTable.java` | Data meja |
| `repository/TableRepository.java` | Mengambil data meja, kapasitas, dan ketersediaannya |
| `view/FormDashboard.java` | Dashboard ringkasan operasional |
| `view/FormCustomer.java` | Form pengecekan antrean oleh pelanggan |
| `view/FormDisplay.java` | Layar publik antrean dipanggil |

## 8. Cara Menjalankan

1. Jalankan `src/main/resources/script.sql` di MySQL untuk membuat database, tabel, dan fitur database lainnya.
2. Jalankan `src/main/resources/seed.sql` untuk memasukkan data awal berupa akun staff dan data meja.
3. Sesuaikan `url`, `user`, dan `password` database di `Koneksi.java`.
4. Buka proyek di IDE (NetBeans atau IntelliJ), lalu jalankan `FormLogin.java`.
