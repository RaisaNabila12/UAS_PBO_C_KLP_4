# UAS_PBO_C_KLP_4

**UAS PRAKTIKUM PBO ANGGOTA KELOMPOK 4:**

1.	RAISA NABILA (2408107010037)
2.	KHALISHA UFAIRAH (2408107010084)
3.	MUHAMMAD ATHALLAH ASSYARIF (2408107010088)
4.	MUHAMMAD RASEUKI (2408107010093)
5.	ARIQ RABBANI (2408107010111)
6.	AHMAD HANIF (2408107010114)


**DESKRIPSI SISTEM**

Sistem ini menerapkan konsep Pemrograman Berbasis Objek (PBO) secara menyeluruh, termasuk Encapsulation, Inheritance, Polymorphism, Abstraction, Collection, dan Exception Handling.

**Fungsionalitas Utama:**

1. Admin		: Dapat mengelola data BUKU (Tambah, Edit, Hapus), Lihat Daftar Anggota, dan Lihat semua Riwayat Transaksi.
2. Anggota (User)	: Dapat melihat daftar buku, melakukan peminjaman dan pengembalian buku, dan juga Riwayat peminjaman pribadi.

**Konsep OOP yang Diterapkan:**

1. Inheritance & Abstraction: Kelas Admin dan Anggota mewarisi dari kelas abstrak Pengguna.
2. Polymorphism: Method tampilkanMenu() di-override oleh Admin dan Anggota untuk menampilkan menu yang berbeda sesuai hak akses.
3. Collection: Menggunakan HashMap untuk menyimpan data login dan ArrayList untuk menyimpan daftar Buku dan Transaksi.
4. Exception Handling: Digunakan try-catch pada input CLI untuk mencegah crash program akibat InputMismatchException.

**Akun Demo:**

Admin

Username : admin

Password : admin123

anggota

Username : budi

Password : 123

