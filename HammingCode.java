package assignments.assignment1;

import java.util.Scanner;

public class HammingCode {
    static final int ENCODE_NUM = 1;
    static final int DECODE_NUM = 2;
    static final int EXIT_NUM = 3;

    /**
     * Method for Encoding Hamming Code.
     *
     * @param data adalah data yang akan di-encode.
     */
    public static String encode(String data) {
        int m = data.length(); // jumlah bit data

        int r = 0; // inisialisasi jumlah banyaknya parity bit
        while ((int) Math.pow(2, r) < m + r + 1) { // menghitung jumlah banyaknya parity bit
            r++;
        }

        StringBuilder dataStr = new StringBuilder(data);

        for (int i = 1; i <= m + r; i *= 2) { // mengisi slot untuk parity bit dengan huruf 'a'
            dataStr.insert(i - 1, 'a');
        }

        String sub = "";

        // melakukan cek n skip n, dengan n adalah parity bit-nya
        for (int i = 1; i <= m + r; i *= 2) {
            int count = 0;

            for (int j = i - 1; j < m + r; j += i * 2) {
                if (j + i > m + r) {
                    sub = dataStr.substring(j);
                } else {
                    sub = dataStr.substring(j, j + i);
                } // menghitung banyaknya angka 1 pada pattern yang dicek
                count += (int) sub.chars().filter(ch -> ch == '1').count();
            }

            if (count % 2 == 0) { // mengecek jika banyaknya angka 1 genap
                dataStr.setCharAt(i - 1, '0');
            } else {
                dataStr.setCharAt(i - 1, '1');
            }
        }

        return dataStr.toString();
    }

    /**
     * Method for Decoding Hamming Code.
     *
     * @param code adalah data yang akan di-decode.
     */
    public static String decode(String code) {
        StringBuilder codeStr = new StringBuilder(code);
        int jmlBitsCode = code.length();
        String sub = "";
        int errorBitPosition = 0;

        // melakukan cek n skip n, dengan n adalah parity bit-nya
        for (int i = 1; i <= jmlBitsCode; i *= 2) {
            int count = 0;

            for (int j = i - 1; j < jmlBitsCode; j += i * 2) {
                if (j + i > jmlBitsCode) {
                    sub = codeStr.substring(j);
                } else {
                    sub = codeStr.substring(j, j + i);
                } // menghitung banyaknya angka 1 pada pattern yang dicek
                count += (int) sub.chars().filter(ch -> ch == '1').count();
            }

            if (count % 2 != 0) { // menentukan errorBitPosition jika count ganjil
                errorBitPosition += i;
            }
        }

        if (errorBitPosition != 0) { // mengganti bit yang salah menjadi kebalikannya
            if (codeStr.charAt(errorBitPosition - 1) == '0') {
                codeStr.setCharAt(errorBitPosition - 1, '1');
            } else if (codeStr.charAt(errorBitPosition - 1) == '1') {
                codeStr.setCharAt(errorBitPosition - 1, '0');
            }
        }

        for (int i = 1; i <= jmlBitsCode; i *= 2) { // mengganti parity bits dengan huruf 'a'
            codeStr.replace(i - 1, i, "a");
        }

        String realCode;
        // me-replace semua huruf 'a' dengan string kosong
        realCode = codeStr.toString().replace("a", "");
        return realCode;
    }

    /**
     * Main program for Hamming Code.
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        System.out.println("Selamat datang di program Hamming Code!");
        System.out.println("=======================================");
        Scanner in = new Scanner(System.in);
        boolean hasChosenExit = false;
        while (!hasChosenExit) {
            System.out.println();
            System.out.println("Pilih operasi:");
            System.out.println("1. Encode");
            System.out.println("2. Decode");
            System.out.println("3. Exit");
            System.out.println("Masukkan nomor operasi yang diinginkan: ");
            int operation = in.nextInt();
            if (operation == ENCODE_NUM) {
                System.out.println("Masukkan data: ");
                String data = in.next();
                String code = encode(data);
                System.out.println("Code dari data tersebut adalah: " + code);
            } else if (operation == DECODE_NUM) {
                System.out.println("Masukkan code: ");
                String code = in.next();
                String data = decode(code);
                System.out.println("Data dari code tersebut adalah: " + data);
            } else if (operation == EXIT_NUM) {
                System.out.println("Sampai jumpa!");
                hasChosenExit = true;
            }
        }
        in.close();
    }
}
