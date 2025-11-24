package service;

import model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

/**
 * Kelas utama yang mengelola koleksi data dan menyediakan fungsionalitas utama sistem.
 * Menerapkan konsep Collection (HashMap dan ArrayList).
 */
public class PerpustakaanService {
    
    private Map<String, Pengguna> daftarPengguna; 
    private List<Buku> daftarBuku; 
    private List<Transaksi> daftarTransaksi;

    public PerpustakaanService() {
        this.daftarPengguna = new HashMap<>();
        this.daftarBuku = new ArrayList<>();
        this.daftarTransaksi = new ArrayList<>();
        
        inisialisasiData();
    }
    
private void inisialisasiData() {
        // Data Login Awal (Dummy Data)
        Admin admin = new Admin("ADM001", "Admin Utama", "admin", "admin123");
        Anggota anggota1 = new Anggota("ANG001", "Budi Santoso", "budi", "123", "2024-01-01");
        
        daftarPengguna.put(admin.getUsername(), admin);
        daftarPengguna.put(anggota1.getUsername(), anggota1);
        
        // Data Buku Awal
        daftarBuku.add(new Buku("B001", "Java Programming Fundamentals", "Joko Susilo", 5));
        daftarBuku.add(new Buku("B002", "Dasar Pemrograman Berorientasi Objek", "Rina Dewi", 3));
        daftarBuku.add(new Buku("B003", "Struktur Data Lanjut", "Ahmad Fauzi", 7));
    }

// --- Fungsionalitas Login & Pencarian ---
    public Pengguna login(String username, String password) {
        Pengguna p = daftarPengguna.get(username);
        if (p != null && p.getPassword().equals(password)) {
            return p;
        }
        return null;
    }
    
    public Buku cariBuku(String kode) {
        for (Buku buku : daftarBuku) {
            if (buku.getKodeBuku().equalsIgnoreCase(kode)) {
                return buku;
            }
        }
        return null;
    }

    public Transaksi cariTransaksiAktif(String idTransaksi) {
        for (Transaksi t : daftarTransaksi) {
            if (t.getIdTransaksi().equalsIgnoreCase(idTransaksi) && t.getStatus().equals("DIPINJAM")) {
                return t;
            }
        }
        return null;
    }

    // --- Fungsionalitas Admin: Kelola Buku (CRUD) ---
    public void tambahBuku(Buku buku) {
        daftarBuku.add(buku);
    }
    
    public boolean hapusBuku(String kode) {
        Buku buku = cariBuku(kode);
        if (buku != null) {
            daftarBuku.remove(buku);
            return true;
        }
        return false;
    }

    public boolean editBuku(String kodeBuku, String judulBaru, String penulisBaru, int stokBaru) {
        Buku buku = cariBuku(kodeBuku);
        
        if (buku != null) {
            buku.setJudul(judulBaru);
            buku.setPenulis(penulisBaru);
            buku.setStok(stokBaru);
            return true;
        }
        return false;
    }
    
    // --- Fungsionalitas Anggota: Peminjaman ---
    public boolean pinjamBuku(Anggota anggota, String kodeBuku) {
        Buku buku = cariBuku(kodeBuku);
        if (buku != null && buku.getStok() > 0) {
            buku.setStok(buku.getStok() - 1);
            Transaksi t = new Transaksi(anggota, buku, LocalDate.now().toString());
            daftarTransaksi.add(t);
            return true;
        }
        return false;
    }
    
    // --- Fungsionalitas Anggota: Pengembalian ---
    public boolean kembalikanBuku(String idTransaksi) {
        Transaksi transaksi = cariTransaksiAktif(idTransaksi);
        
        if (transaksi != null) {
            transaksi.setPengembalian(LocalDate.now().toString());
            Buku buku = transaksi.getBuku();
            buku.setStok(buku.getStok() + 1);
            
            return true;
        }
        return false;
    }

    // --- Fungsionalitas Anggota: Riwayat ---
    public List<Transaksi> getRiwayatPeminjamanByAnggota(Anggota anggota) {
        List<Transaksi> riwayat = new ArrayList<>();
        for (Transaksi t : daftarTransaksi) {
            if (t.getAnggota().getIdPengguna().equals(anggota.getIdPengguna())) {
                riwayat.add(t);
            }
        }
        return riwayat;
    }
    
    // Getters untuk data
    public List<Buku> getDaftarBuku() {
        return daftarBuku;
    }
    
    public List<Pengguna> getDaftarAnggota() {
        List<Pengguna> anggotaList = new ArrayList<>();
        for (Pengguna p : daftarPengguna.values()) {
            if (p instanceof Anggota) {
                anggotaList.add(p);
            }
        }
        return anggotaList;
    }

    public List<Transaksi> getDaftarTransaksi() {
        return daftarTransaksi;
    }
}    
    
