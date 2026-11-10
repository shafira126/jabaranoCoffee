# Sistem Antrean Jabarano Cafe

## 1. Deskripsi Proyek
Proyek ini merupakan aplikasi desktop berbasis Java Swing untuk mengelola antrean pelanggan di Jabarano Cafe. Aplikasi ini memungkinkan staff mendaftarkan pelanggan, mengatur status antrean, dan menampilkan informasi antrean yang sedang dipanggil sehingga pelanggan dapat mengetahui perkembangan antreannya tanpa harus terus bertanya kepada staff.

### Latar Belakang
Saat ini, pemanggilan pelanggan dilakukan secara manual oleh staff. Hal ini menyebabkan pelanggan sering tidak mendengar panggilan atau harus terus bertanya mengenai status antreannya.

### Tujuan
- Mempermudah staff dalam mengelola antrean pelanggan.
- Mengurangi kebutuhan staff untuk memanggil pelanggan secara manual.
- Memudahkan pelanggan dalam memantau status antrean.

### Teknologi
| Komponen | Teknologi |
| --- | --- |
| Interface | Java Swing |
| Database | MySQL |
| DB Connection | JDBC |

## 2. Pengguna Aplikasi
| Pengguna | Hak Akses |
| --- | --- |
| Staff | Login, mendaftarkan pelanggan, mengelola antrean, memanggil antrean, memperbarui status antrean |
| Customer | Melihat status antrean menggunakan kode akses (tanpa login atau registrasi) |

Data pelanggan dimasukkan oleh staff saat pelanggan datang ke cafe. Pelanggan tidak perlu membuat akun.

## 3. Gambaran Aplikasi dan Fungsionalitas
### 3.1. Login
Staff login menggunakan username dan password. Jika berhasil, diarahkan ke dashboard. Jika gagal, muncul pesan kesalahan.

### 3.2. Dashboard
Menunjukkan ringkasan operasional:
- Jumlah pelanggan yang sedang mengantre.
- Jumlah pelanggan yang sudah dipanggil.

### 3.3. Manajemen Antrean
Staff dapat:
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
Layar terpisah yang menampilkan nomor antrean yang sedang dipanggil beserta nomor meja yang dituju. Diperbarui otomatis saat staff menekan tombol panggil.

## 4. Perencanaan Alur Aplikasi
### 4.1. Alur Utama Antrean
1. Pelanggan datang ke Jabarano Cafe.
2. Staff memasukkan nama pelanggan dan jumlah orang.
3. Sistem menghasilkan nomor antrean dan kode akses secara otomatis.
4. Staff memberikan nomor antrean dan kode akses kepada pelanggan.
5. Pelanggan dapat mengecek status antreannya menggunakan kode akses di Customer Display.
6. Staff memanggil pelanggan berikutnya dan memilih nomor meja.
7. Status antrean berubah menjadi `CALLED`. Nomor antrean dan meja ditampilkan di Public Display.
8. Setelah pelanggan selesai, staff mengubah status menjadi `FINISHED`.

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

## 5. Rancangan Database
Database terdiri dari dua tabel. Tidak ada tabel `cafe_tables` — data meja di-hardcode langsung di dalam program.

### Tabel `users`
| Kolom | Tipe Data | Keterangan |
| --- | --- | --- |
| id | INT | Primary Key, Auto Increment |
| username | VARCHAR(50) | Username staff |
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
| `User` | id, username, password |
| `QueueEntry` | id, queueNumber, accessCode, customerName, partySize, tableNumber, status, joinedAt |

### Database
| Class | Tanggung Jawab |
| --- | --- |
| `Koneksi` | Mengatur koneksi Java dengan MySQL |
| `UserDAO` | Mengelola data akun staff |
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
