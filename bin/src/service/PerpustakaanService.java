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

    
