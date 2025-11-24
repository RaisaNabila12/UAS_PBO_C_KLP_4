package model;

/**
 * Kelas yang merepresentasikan pengguna dengan hak akses Anggota (User).
 */
public class Anggota extends Pengguna {
    private String tanggalBergabung;

    public Anggota(String idPengguna, String nama, String username, String password, String tanggalBergabung) {
        super(idPengguna, nama, username, password);
        this.tanggalBergabung = tanggalBergabung;
    }

    /**
     * Implementasi method abstrak: menampilkan menu Anggota.
     */
     @Override
    public void tampilkanMenu() {
        System.out.println("\n=== MENU ANGGOTA ===");
        System.out.println("1. Lihat Daftar Buku");
        System.out.println("2. Pinjam Buku");
        System.out.println("3. Kembalikan Buku");
        System.out.println("4. Lihat Riwayat Peminjaman Saya");
        System.out.println("0. Logout");
        System.out.print("Pilih menu: ");
    }

    // Encapsulation: Getter
    public String getTanggalBergabung() {
        return tanggalBergabung;
    }
}
