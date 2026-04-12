package Easy;
import java.util.Scanner;
import java.util.ArrayList;

abstract class Siswa {
    private String nama;
    protected int saldo; 
    private String tipe;

    public Siswa(String nama, String tipe) {
        this.nama = nama;
        this.tipe = tipe;
        this.saldo = 0; 
    }

    public String getNama() {
        return nama;
    }

    public int getSaldo() {
        return saldo;
    }

    public String getTipe() {
        return tipe;
    }

    public void menabung(int jumlah) {
        this.saldo += jumlah;
    }

    public abstract boolean ambilUang(int jumlah);
}

class SiswaReguler extends Siswa {
    public SiswaReguler(String nama) {
        super(nama, "REGULER");
    }

    @Override
    public boolean ambilUang(int jumlah) {
        if (this.saldo >= jumlah) {
            this.saldo -= jumlah;
            return true;
        }
        return false;
    }
}

class SiswaBeasiswa extends Siswa {
    public SiswaBeasiswa(String nama) {
        super(nama, "BEASISWA");
    }

    @Override
    public boolean ambilUang(int jumlah) {
        int totalTagihan = jumlah + 1000;
        if (this.saldo >= totalTagihan) {
            this.saldo -= totalTagihan;
            return true;
        }
        return false;
    }
}

public class tabungan {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Siswa> listSiswa = new ArrayList<>();
        int n = Integer.parseInt(input.nextLine());

        for (int i = 0; i < n; i++) {
            String baris = input.nextLine();
            String[] bagian = baris.split(" ");
            String perintah = bagian[0];

            if (perintah.equals("CREATE")) {
                String tipe = bagian[1];
                String nama = bagian[2];

                boolean status = false;
                for (int j = 0; j < listSiswa.size(); j++) {
                    if (listSiswa.get(j).getNama().equals(nama)) {
                        status = true;
                        break;
                    }
                }

                if (status == true) {
                    System.out.println("Akun sudah terdaftar");
                } else {
                    if (tipe.equals("REGULER")) {
                        listSiswa.add(new SiswaReguler(nama));
                    } else {
                        listSiswa.add(new SiswaBeasiswa(nama));
                    }
                    System.out.println(tipe + " " + nama + " berhasil dibuat");
                }

            } else if (perintah.equals("SAVE")) {
                String nama = bagian[1];
                int nominal = Integer.parseInt(bagian[2]);
                boolean status = false;

                for (int j = 0; j < listSiswa.size(); j++) {
                    if (listSiswa.get(j).getNama().equals(nama)) {
                        listSiswa.get(j).menabung(nominal);
                        System.out.println("Saldo " + nama + ": " + listSiswa.get(j).getSaldo());
                        status = true;
                        break;
                    }
                }

                if (status == false) {
                    System.out.println("Akun tidak ditemukan");
                }

            } else if (perintah.equals("TAKE")) {
                String nama = bagian[1];
                int nominal = Integer.parseInt(bagian[2]);
                boolean status = false;

                for (int j = 0; j < listSiswa.size(); j++) {
                    if (listSiswa.get(j).getNama().equals(nama)) {
                        status = true;
                        if (listSiswa.get(j).ambilUang(nominal)) {
                            System.out.println("Saldo " + nama + ": " + listSiswa.get(j).getSaldo());
                        } else {
                            System.out.println("Saldo " + nama + " tidak cukup");
                        }
                        break;
                    }
                }

                if (status == false) {
                    System.out.println("Akun tidak ditemukan");
                }

            } else if (perintah.equals("CHECK")) {
                String nama = bagian[1];
                boolean status = false;

                for (int j = 0; j < listSiswa.size(); j++) {
                    Siswa s = listSiswa.get(j);
                    if (s.getNama().equals(nama)) {
                        System.out.println(s.getNama() + " | " + s.getTipe() + " | saldo: " + s.getSaldo());
                        status = true;
                        break;
                    }
                }

                if (status == false) {
                    System.out.println("Akun tidak ditemukan");
                }
            }
        }
        input.close();
    }
}