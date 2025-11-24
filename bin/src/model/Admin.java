package model;

/**
 * Kelas yang merepresentasikan pengguna dengan hak akses Admin.
 */
public class Admin extends Pengguna {

    public Admin(String idPengguna, String nama, String username, String password) {
        super(idPengguna, nama, username, password);
    }
    
  /**
     * Implementasi method abstrak: menampilkan menu Admin.
     */
    @Override
    public void tampilkanMenu() {
        System.out.println("\n=== MENU ADMIN ===");
        System.out.println("1. Kelola Data Buku (Tambah/Edit/Hapus)");
        System.out.println("2. Lihat Daftar Anggota");
        System.out.println("3. Lihat Semua Transaksi Peminjaman");
        System.out.println("0. Logout");
        System.out.print("Pilih menu: ");
    }
}
