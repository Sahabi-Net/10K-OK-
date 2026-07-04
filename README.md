<img width="244" height="203" alt="image" src="https://github.com/user-attachments/assets/91d3e905-f79c-4a3b-95b9-06353cf54922" />

# 10K OK - Aplikasi Sarapan serba 10K
10K OK! adalah aplikasi pemesanan sarapan berbasis Android yang dikembangkan menggunakan Kotlin dengan arsitektur Client-Server, memanfaatkan PHP Native dan MySQL sebagai backend (REST API), untuk memfasilitasi pemesanan serta pelacakan pesanan secara real-time melalui antarmuka yang bersih, menarik, dan interaktif bagi pengguna (pembeli) maupun mitra (penjual), dengan menyediakan berbagai pilihan menu makanan dan minuman seharga Rp10.000 per menu.
## ✨ Fitur Utama
### Sisi Pengguna (Pembeli)
* **Pemesanan Mudah**: Proses pemesanan produk yang intuitif dan cepat.
* **Lacak Pesanan (Tracking)**: Halaman pelacakan pesanan dengan visualisasi *timeline* vertikal yang dinamis (Menampilkan status: Diterima, Diproses, Dalam Pengantaran, Selesai, dan Cancel).
* **Kartu Selebrasi**: Apabila pesanan telah selesai, pengguna akan disajikan kartu perayaan dengan efek *confetti* untuk memberikan ulasan dan rating produk.
* **UI/UX Modern**: Menggunakan desain membulat (*rounded corners*), warna cerah, dan tipografi yang jelas untuk kenyamanan pengguna.
### Sisi Mitra (Admin/Penjual)
* **Manajemen Pesanan**: Melihat daftar pesanan masuk dari pelanggan secara langsung.
* **Ubah Status Pesanan**: Mitra dapat memperbarui status pesanan menjadi diproses, dikirim, selesai, maupun dibatalkan (*Cancel*). Perubahan status akan langsung terhubung dengan database dan diperbarui di aplikasi pembeli.
## 🛠️ Teknologi yang Digunakan
* **Frontend**: Android (Kotlin, XML, Material Components)
* **Backend**: PHP Native (REST API)
* **Database**: MySQL (XAMPP)
* **Networking**: Retrofit2 & Gson
## 🚀 Panduan Instalasi & Menjalankan Aplikasi
### 1. Persiapan Backend (API & Database)
1. Unduh dan instal **XAMPP**.
2. Nyalakan layanan **Apache** dan **MySQL** dari XAMPP Control Panel.
3. Buka **phpMyAdmin** (biasanya di `http://localhost/phpmyadmin`).
4. Buat database baru dengan nama `db_10kok` (atau sesuaikan dengan nama database Anda).
5. Impor file `.sql` yang tersedia (jika ada) ke dalam database tersebut.
6. Pindahkan folder API (misalnya folder `10kok_api`) ke dalam direktori `htdocs` pada instalasi XAMPP Anda (`C:\xampp\htdocs\10kok_api`).
### 2. Persiapan Frontend (Aplikasi Android)
1. Buka folder proyek Android menggunakan **Android Studio**.
2. Buka file `ApiClient.kt` yang terletak di `app/src/main/java/com/example/a10kok/api/`.
3. Ubah konstanta `BASE_URL` sesuai dengan IP lokal komputer/laptop Anda (Contoh: `http://192.168.1.5/10kok_api/`). **Pastikan HP/Emulator dan Laptop Anda terhubung dalam jaringan WiFi/LAN yang sama**.
4. Biarkan proses Gradle *sync* berjalan hingga selesai.
5. Jalankan aplikasi (tekan tombol *Run*) pada emulator atau *device* fisik Anda.
## 📝 Catatan Tambahan (Untuk Keperluan Tugas)
Aplikasi ini dioptimalkan khusus untuk keperluan demonstrasi tugas pengembangan UI/UX, dengan penyesuaian khusus pada halaman Lacak Pesanan untuk memudahkan proses presentasi akhir tanpa mengharuskan *state* pesanan berjalan secara *real-time* terus menerus.
---
