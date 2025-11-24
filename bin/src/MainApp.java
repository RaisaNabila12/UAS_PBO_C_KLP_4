package main;

import model.*;
import service.PerpustakaanService;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

/**
 * Kelas utama untuk menjalankan sistem CLI.
 * Menerapkan Exception Handling dan Polimorfisme.
 */
public class MainApp {
    private static PerpustakaanService service = new PerpustakaanService();
    private static Scanner scanner = new Scanner(System.in);
    private static Pengguna penggunaAktif = null;

    public static void main(String[] args) {
        int pilihan;
        do {
            System.out.println("\n==================================");
            System.out.println("| SISTEM MANAJEMEN PERPUSTAKAAN |");
            System.out.println("==================================");
            System.out.println("1. Login");
            System.out.println("0. Keluar");
            
            try {
                System.out.print("Pilih: ");
                pilihan = scanner.nextInt();
                scanner.nextLine();
                
                switch (pilihan) {
                    case 1:
                        menuLogin();
                        break;
                    case 0:
                        System.out.println("Terima kasih telah menggunakan sistem.");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Masukkan harus berupa angka.");
                scanner.nextLine();
                pilihan = -1;
            }
            
        } while (pilihan != 0);
    }
    
    private static void menuLogin() {
        System.out.println("\n--- Halaman Login ---");
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        penggunaAktif = service.login(user, pass);

        if (penggunaAktif != null) {
            System.out.println("Login berhasil! Selamat datang, " + penggunaAktif.getNama());
            menuUtama(); 
        } else {
            System.out.println("Login gagal! Username atau Password salah.");
        }
    }

    private static void menuUtama() {
        int pilihan;
        
        while (penggunaAktif != null) {
            penggunaAktif.tampilkanMenu(); // Polymorphism
            
            try {
                pilihan = scanner.nextInt();
                scanner.nextLine();
                
                if (pilihan == 0) {
                    penggunaAktif = null; 
                    System.out.println("Anda telah logout.");
                } else {
                    if (penggunaAktif instanceof Admin) {
                        handleMenuAdmin(pilihan);
                    } else if (penggunaAktif instanceof Anggota) {
                        handleMenuAnggota(pilihan);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Masukkan harus berupa angka.");
                scanner.nextLine();
            }
        }
    }
    
    // --- Handler Menu Admin ---
    private static void handleMenuAdmin(int pilihan) {
        switch (pilihan) {
            case 1:
                menuKelolaBuku();
                break;
            case 2:
                tampilkanDaftarAnggota();
                break;
            case 3:
                tampilkanDaftarTransaksi(service.getDaftarTransaksi(), true);
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    // --- Handler Menu Anggota ---
    private static void handleMenuAnggota(int pilihan) {
        Anggota anggota = (Anggota) penggunaAktif;
        switch (pilihan) {
            case 1:
                tampilkanDaftarBuku();
                break;
            case 2:
                menuPinjamBuku(anggota);
                break;
            case 3:
                menuKembalikanBuku();
                break;
            case 4:
                menuRiwayatPeminjaman(anggota);
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }
    
    // --- Fungsionalitas Tampilan ---

    private static void tampilkanDaftarBuku() {
        System.out.println("\n+-----------------------------------------------------------------------------------+");
        System.out.println("|                            DAFTAR BUKU PERPUSTAKAAN                               |");
        System.out.println("+--------+------------------------------------------+----------------------+--------+");
        System.out.println("| Kode   | Judul                                    | Penulis              | Stok   |");
        System.out.println("+--------+------------------------------------------+----------------------+--------+");
        
        for (Buku buku : service.getDaftarBuku()) {
            System.out.println(buku.toString());
        }
        System.out.println("+--------+------------------------------------------+----------------------+--------+");
    }
    
    private static void tampilkanDaftarAnggota() {
        System.out.println("\n--- DAFTAR ANGGOTA SISTEM ---");
        List<Pengguna> anggotaList = service.getDaftarAnggota();
        if (anggotaList.isEmpty()) {
             System.out.println("Tidak ada anggota yang terdaftar.");
             return;
        }
        for (Pengguna p : anggotaList) {
            Anggota a = (Anggota) p;
            System.out.println("ID: " + a.getIdPengguna() + ", Nama: " + a.getNama() + ", Username: " + a.getUsername() + ", Bergabung: " + a.getTanggalBergabung());
        }
    }

    private static void tampilkanDaftarTransaksi(List<Transaksi> daftar, boolean isAdmin) {
        System.out.println("\n--- DAFTAR TRANSAKSI ---");
        if (daftar.isEmpty()) {
            System.out.println("Belum ada transaksi yang tercatat.");
            return;
        }
        for (Transaksi t : daftar) {
            System.out.println("--------------------------------------------------");
            System.out.println("ID Transaksi: " + t.getIdTransaksi());
            if (isAdmin) {
                System.out.println("Anggota: " + t.getAnggota().getNama() + " (" + t.getAnggota().getIdPengguna() + ")");
            }
            System.out.println("Buku: " + t.getBuku().getJudul() + " (" + t.getBuku().getKodeBuku() + ")");
            System.out.println("Tanggal Pinjam: " + t.getTglPinjam());
            System.out.println("Tanggal Kembali: " + (t.getTglKembali() != null ? t.getTglKembali() : "-"));
            System.out.println("Status: " + t.getStatus());
        }
        System.out.println("--------------------------------------------------");
    }

    // --- Fungsionalitas Admin: Kelola Buku (CRUD) ---
    private static void menuKelolaBuku() {
        int pil;
        do {
            System.out.println("\n--- KELOLA DATA BUKU ---");
            System.out.println("1. Tampilkan Semua Buku");
            System.out.println("2. Tambah Buku Baru");
            System.out.println("3. Hapus Buku");
            System.out.println("4. Edit Data Buku");
            System.out.println("0. Kembali ke Menu Admin");
            
            try {
                System.out.print("Pilih: ");
                pil = scanner.nextInt();
                scanner.nextLine();
                
                switch (pil) {
                    case 1:
                        tampilkanDaftarBuku();
                        break;
                    case 2:
                        tambahBuku();
                        break;
                    case 3:
                        hapusBuku();
                        break;
                    case 4:
                        editBuku();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Masukkan harus berupa angka.");
                scanner.nextLine();
                pil = -1;
            }
        } while (pil != 0);
    }
    
    private static void tambahBuku() {
        System.out.print("Kode Buku (misal B004): ");
        String kode = scanner.nextLine();
        System.out.print("Judul Buku: ");
        String judul = scanner.nextLine();
        System.out.print("Penulis: ");
        String penulis = scanner.nextLine();
        
        try {
            System.out.print("Stok: ");
            int stok = scanner.nextInt();
            scanner.nextLine();

            if (service.cariBuku(kode) != null) {
                System.out.println("Gagal: Kode buku sudah ada.");
                return;
            }
            
            service.tambahBuku(new Buku(kode, judul, penulis, stok));
            System.out.println("Buku berhasil ditambahkan!");
        } catch (InputMismatchException e) {
            System.out.println("Gagal: Stok harus berupa angka.");
            scanner.nextLine();
        }
    }

    private static void hapusBuku() {
        System.out.print("Masukkan Kode Buku yang akan dihapus: ");
        String kode = scanner.nextLine();
        
        if (service.hapusBuku(kode)) {
            System.out.println("Buku dengan kode " + kode + " berhasil dihapus.");
        } else {
            System.out.println("Gagal: Kode buku tidak ditemukan.");
        }
    }

    private static void editBuku() {
        tampilkanDaftarBuku();
        System.out.print("Masukkan Kode Buku yang akan diedit: ");
        String kode = scanner.nextLine();

        Buku bukuLama = service.cariBuku(kode);
        if (bukuLama == null) {
            System.out.println("Gagal: Kode buku tidak ditemukan.");
            return;
        }

        System.out.println("\n--- EDIT BUKU: " + bukuLama.getJudul() + " ---");
        
        System.out.print("Judul Baru (Kosongkan jika tidak diubah, Saat ini: " + bukuLama.getJudul() + "): ");
        String judulBaru = scanner.nextLine();
        if (judulBaru.isEmpty()) {
            judulBaru = bukuLama.getJudul();
        }
        
        System.out.print("Penulis Baru (Kosongkan jika tidak diubah, Saat ini: " + bukuLama.getPenulis() + "): ");
        String penulisBaru = scanner.nextLine();
        if (penulisBaru.isEmpty()) {
            penulisBaru = bukuLama.getPenulis();
        }
        
        int stokBaru = bukuLama.getStok();
        try {
            System.out.print("Stok Baru (Kosongkan jika tidak diubah, Saat ini: " + bukuLama.getStok() + "): ");
            String stokInput = scanner.nextLine();
            
            if (!stokInput.isEmpty()) {
                stokBaru = Integer.parseInt(stokInput);
            }
        } catch (NumberFormatException e) {
            System.out.println("Gagal: Stok harus berupa angka. Pembatalan Edit.");
            return;
        }

        if (service.editBuku(kode, judulBaru, penulisBaru, stokBaru)) {
            System.out.println("Data buku berhasil diperbarui!");
        } else {
            System.out.println("Terjadi kesalahan saat menyimpan data.");
        }
    }
    
    // --- Fungsionalitas Anggota: Peminjaman ---
    private static void menuPinjamBuku(Anggota anggota) {
        tampilkanDaftarBuku();
        System.out.print("Masukkan Kode Buku yang akan dipinjam: ");
        String kode = scanner.nextLine();
        
        if (service.pinjamBuku(anggota, kode)) {
            System.out.println("Peminjaman buku berhasil!");
            System.out.println("   Buku: " + service.cariBuku(kode).getJudul() + " telah dicatat pada tanggal " + LocalDate.now());
        } else {
            Buku buku = service.cariBuku(kode);
            if (buku == null) {
                System.out.println("Gagal: Kode buku tidak ditemukan.");
            } else if (buku.getStok() == 0) {
                System.out.println("Gagal: Stok buku kosong.");
            }
        }
    }

    // --- Fungsionalitas Anggota: Pengembalian ---
    private static void menuKembalikanBuku() {
        System.out.println("\n--- PENGEMBALIAN BUKU ---");
        System.out.print("Masukkan ID Transaksi yang akan dikembalikan (misal: TRX001): ");
        String idTransaksi = scanner.nextLine();

        if (service.kembalikanBuku(idTransaksi)) {
            System.out.println("Pengembalian berhasil! Stok buku telah ditambahkan kembali.");
        } else {
            System.out.println("Gagal: ID Transaksi tidak ditemukan atau buku sudah dikembalikan.");
        }
    }

    // --- Fungsionalitas Anggota: Riwayat ---
    private static void menuRiwayatPeminjaman(Anggota anggota) {
        System.out.println("\n--- RIWAYAT PEMINJAMAN SAYA (" + anggota.getNama() + ") ---");
        List<Transaksi> riwayat = service.getRiwayatPeminjamanByAnggota(anggota);
        
        if (riwayat.isEmpty()) {
            System.out.println("Anda belum pernah melakukan transaksi peminjaman.");
        } else {
            tampilkanDaftarTransaksi(riwayat, false);
        }
    }
}

