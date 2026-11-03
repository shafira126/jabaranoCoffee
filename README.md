# Aplikasi Antrean Jabarano Cafe

## 1. Deskripsi Proyek

Aplikasi Antrean Jabarano Cafe merupakan aplikasi yang dirancang untuk membantu staf dalam mengelola antrean pelanggan dan penggunaan meja di Jabarano Cafe. Aplikasi ini memungkinkan staf untuk mendaftarkan pelanggan, mengatur antrean, memanggil pelanggan, serta memantau ketersediaan meja berdasarkan kapasitasnya.

Selain digunakan oleh staf, aplikasi ini juga menyediakan halaman tracking yang dapat diakses oleh pelanggan untuk memantau posisi dan status antrean secara mandiri. Dengan adanya sistem ini, proses pengelolaan antrean diharapkan menjadi lebih terorganisasi dan memudahkan pelanggan dalam mengetahui perkembangan antreannya.

## 2. Perencanaan Proyek

### 2.1. Teknologi yang Digunakan

- **Bahasa Pemrograman:** Java
- **GUI:** Java Swing (JFrame)
- **IDE:** NetBeans
- **Database:** MySQL
- **Database Connection:** JDBC
- **Halaman Tracking Pelanggan:** Web

### 2.2. Perencanaan Alur

#### Staff

<ol>
1. Login ke sistem
</ol>
<p>Staf melakukan login menggunakan username dan password yang sudah terdaftar.</p>

<ol>
2. Mendaftarkan pelanggan ke dalam antrean
</ol>
<p>Staf memasukkan data pelanggan berupa nama, nomor telepon (opsional), dan jumlah orang. Sistem akan membuat nomor antrean secara otomatis.</p>

<ol>
3. Mengelola dan memanggil antrean
</ol>
<p>Staf dapat melihat daftar antrean, memantau posisi pelanggan, serta memanggil pelanggan sesuai urutan antrean.</p>

<ol>
4. Mengatur meja dan mengubah status meja
</ol>
<p>Staf dapat melihat ketersediaan meja, memilih meja berdasarkan jumlah pelanggan dan kapasitas meja, serta memperbarui status meja.</p>

<ol>
5. Melihat riwayat antrean
</ol>
<p>Staf dapat melihat riwayat antrean pelanggan, termasuk antrean yang selesai, dibatalkan, maupun pelanggan yang tidak datang.</p>

#### Pelanggan

<ol>
1. Memasukkan nomor antrean
</ol>
<p>Pelanggan memasukkan nomor antrean atau kode tracking yang diberikan oleh staf untuk mengakses informasi antrean.</p>

<ol>
2. Melihat posisi antrean saat ini
</ol>
<p>Sistem menampilkan posisi antrean pelanggan berdasarkan jumlah antrean yang masih menunggu.</p>

<ol>
3. Melihat estimasi waktu tunggu
</ol>
<p>Pelanggan dapat melihat perkiraan waktu tunggu sebelum mendapatkan giliran.</p>

<ol>
4. Melihat status antrean
</ol>
<p>Pelanggan dapat memantau status antrean, mulai dari masih menunggu, sudah dipanggil, hingga sudah mendapatkan meja.</p>

### 2.3. Perencanaan Fitur

**Fitur Staff:**
- Login dan logout.
- Dashboard untuk melihat ringkasan antrean dan meja.
- Menambahkan dan mengelola data antrean pelanggan.
- Membuat nomor antrean secara otomatis.
- Memanggil pelanggan sesuai urutan antrean.
- Memilih meja berdasarkan kapasitas pelanggan.
- Mengubah status meja.
- Melihat riwayat dan laporan antrean.

**Fitur Pelanggan:**
- Tracking antrean menggunakan nomor antrean atau kode tracking.
- Melihat posisi antrean.
- Melihat estimasi waktu tunggu.
- Memantau perubahan status antrean.

### 2.4. Perencanaan Database

Database yang digunakan adalah MySQL dengan beberapa tabel utama:

**1. users**

Menyimpan data akun staf yang dapat mengakses aplikasi.

- id
- username
- password
- full_name
- role

**2. cafe_tables**

Menyimpan informasi meja yang tersedia di cafe.

- id
- table_number
- capacity
- status

**3. queue_entries**

Menyimpan data antrean pelanggan.

- id
- queue_number
- queue_date
- customer_name
- phone
- party_size
- table_id
- status
- joined_at
- called_at
- seated_at
- finished_at
- handled_by

### 2.5. Perencanaan Status

**Status Antrean:**

- WAITING: Pelanggan sedang menunggu.
- CALLED: Pelanggan sudah dipanggil.
- SEATED: Pelanggan sudah mendapatkan meja.
- FINISHED: Pelanggan selesai menggunakan meja.
- CANCELLED: Antrean dibatalkan.
- NO_SHOW: Pelanggan tidak datang setelah dipanggil.

**Status Meja:**

- AVAILABLE: Meja tersedia.
- RESERVED: Meja sudah dialokasikan untuk pelanggan.
- OCCUPIED: Meja sedang digunakan.
- CLEANING: Meja sedang dibersihkan.

## 3. Pembagian Modul

| Modul | Keterangan |
|---|---|
| Login | Mengelola autentikasi staf |
| Dashboard | Menampilkan ringkasan informasi antrean dan meja |
| Manajemen Antrean | Mengelola pendaftaran dan data antrean pelanggan |
| Manajemen Meja | Mengelola kapasitas dan status meja |
| Pemanggilan Antrean | Mengelola proses pemanggilan pelanggan |
| Tracking Pelanggan | Menampilkan informasi antrean kepada pelanggan |
| Laporan | Menampilkan riwayat dan statistik antrean |

## 4. Rencana Pengembangan

Pengembangan aplikasi akan dilakukan secara bertahap, dimulai dari pembuatan database dan koneksi Java dengan MySQL, dilanjutkan dengan pembuatan fitur login, manajemen antrean, manajemen meja, dan halaman tracking pelanggan. Setelah fitur utama selesai, dilakukan pengujian untuk memastikan setiap fungsi berjalan sesuai kebutuhan.

