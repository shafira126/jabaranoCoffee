package com.mycompany.jabaranocoffee.view;

import com.mycompany.jabaranocoffee.repository.QueueRepository;

import javax.swing.*;
import java.awt.*;

/**
 * Form pengecekan antrean oleh pelanggan menggunakan kode akses.
 *
 * @author rangga
 */
public class FormCustomer extends javax.swing.JFrame {

    // ----------------------------------------------------------------
    // Atribut
    // ----------------------------------------------------------------
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(FormCustomer.class.getName());

    private final QueueRepository queueRepository;

    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------
    public FormCustomer() {
        this.queueRepository = new QueueRepository();
        initComponents();
        customizeComponents();
    }

    // ----------------------------------------------------------------
    // Styling tambahan
    // ----------------------------------------------------------------
    private void customizeComponents() {
        setLocationRelativeTo(null);
        panelHasil.setVisible(false);
        lblError.setVisible(false);

        btnCek.addActionListener(e -> doCek());
        btnReset.addActionListener(e -> doReset());
        txtKodeAkses.addActionListener(e -> doCek());
    }

    // ----------------------------------------------------------------
    // Input
    // ----------------------------------------------------------------
    private String getInputKodeAkses() {
        return txtKodeAkses.getText().trim().toUpperCase();
    }

    // ----------------------------------------------------------------
    // Proses: cek antrean berdasarkan kode akses
    // ----------------------------------------------------------------
    private void doCek() {
        String kode = getInputKodeAkses();

        if (kode.isEmpty()) {
            showError("Masukkan kode akses terlebih dahulu.");
            return;
        }

        lblError.setVisible(false);
        panelHasil.setVisible(false);

        Object[] data = queueRepository.findByAccessCode(kode);

        if (data == null) {
            showError("Kode akses tidak ditemukan.");
            return;
        }

        // data: [queueNumber, position, status, tableNumber]
        showHasil(
            (String) data[0],  // nomor antrean
            (int)    data[1],  // posisi
            (String) data[2],  // status
            (String) data[3]   // nomor meja (null kalau belum dipanggil)
        );
    }

    // ----------------------------------------------------------------
    // Output: tampilkan hasil
    // ----------------------------------------------------------------
    private void showHasil(String queueNumber, int position,
                            String status, String tableNumber) {
        lblNomorAntrean.setText(queueNumber);
        lblPosisi.setText(position > 0 ? "Posisi ke-" + position + " dalam antrean" : "-");
        lblStatus.setText(status);

        // Warna status
        switch (status) {
            case "WAITING"   -> lblStatus.setForeground(new Color(133, 100, 4));
            case "CALLED"    -> lblStatus.setForeground(new Color(13, 71, 161));
            case "FINISHED"  -> lblStatus.setForeground(new Color(27, 94, 32));
            case "CANCELLED" -> lblStatus.setForeground(Color.RED);
            default          -> lblStatus.setForeground(Color.BLACK);
        }

        if (tableNumber != null) {
            lblMeja.setText("Meja " + tableNumber);
            lblMeja.setVisible(true);
            lblDescMeja.setVisible(true);
        } else {
            lblMeja.setVisible(false);
            lblDescMeja.setVisible(false);
        }

        panelHasil.setVisible(true);
    }

    private void showError(String message) {
        lblError.setText(message);
        lblError.setVisible(true);
    }

    private void doReset() {
        txtKodeAkses.setText("");
        panelHasil.setVisible(false);
        lblError.setVisible(false);
        txtKodeAkses.requestFocus();
    }

    // ----------------------------------------------------------------
    // Generated Code — GUI Builder
    // ----------------------------------------------------------------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        lblJudul        = new javax.swing.JLabel();
        lblSubjudul     = new javax.swing.JLabel();
        lblKodeAkses    = new javax.swing.JLabel();
        txtKodeAkses    = new javax.swing.JTextField();
        btnCek          = new javax.swing.JButton();
        btnReset        = new javax.swing.JButton();
        lblError        = new javax.swing.JLabel();
        panelHasil      = new javax.swing.JPanel();
        lblDescAntrean  = new javax.swing.JLabel();
        lblNomorAntrean = new javax.swing.JLabel();
        lblDescPosisi   = new javax.swing.JLabel();
        lblPosisi       = new javax.swing.JLabel();
        lblDescStatus   = new javax.swing.JLabel();
        lblStatus       = new javax.swing.JLabel();
        lblDescMeja     = new javax.swing.JLabel();
        lblMeja         = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cek Status Antrean");
        setPreferredSize(new java.awt.Dimension(400, 480));
        setResizable(false);

        lblJudul.setFont(new java.awt.Font("Helvetica Neue", Font.BOLD, 18));
        lblJudul.setHorizontalAlignment(SwingConstants.CENTER);
        lblJudul.setText("Cek Antrean");

        lblSubjudul.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 12));
        lblSubjudul.setForeground(new Color(100, 100, 100));
        lblSubjudul.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubjudul.setText("Masukkan kode akses yang diberikan staff");

        lblKodeAkses.setText("Kode Akses");

        txtKodeAkses.setColumns(20);
        txtKodeAkses.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 16));
        txtKodeAkses.setHorizontalAlignment(JTextField.CENTER);

        btnCek.setText("Cek Status");
        btnReset.setText("Reset");

        lblError.setForeground(Color.RED);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        lblError.setText("");

        // Panel hasil
        panelHasil.setBorder(javax.swing.BorderFactory.createTitledBorder("Hasil Pengecekan"));

        lblDescAntrean.setText("Nomor Antrean");
        lblDescAntrean.setForeground(new Color(100, 100, 100));
        lblNomorAntrean.setFont(new java.awt.Font("Helvetica Neue", Font.BOLD, 24));
        lblNomorAntrean.setText("-");

        lblDescPosisi.setText("Posisi");
        lblDescPosisi.setForeground(new Color(100, 100, 100));
        lblPosisi.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 13));
        lblPosisi.setText("-");

        lblDescStatus.setText("Status");
        lblDescStatus.setForeground(new Color(100, 100, 100));
        lblStatus.setFont(new java.awt.Font("Helvetica Neue", Font.BOLD, 13));
        lblStatus.setText("-");

        lblDescMeja.setText("Nomor Meja");
        lblDescMeja.setForeground(new Color(100, 100, 100));
        lblMeja.setFont(new java.awt.Font("Helvetica Neue", Font.BOLD, 16));
        lblMeja.setText("-");

        javax.swing.GroupLayout panelHasilLayout = new javax.swing.GroupLayout(panelHasil);
        panelHasil.setLayout(panelHasilLayout);
        panelHasilLayout.setHorizontalGroup(
            panelHasilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHasilLayout.createSequentialGroup()
                .addGap(16)
                .addGroup(panelHasilLayout.createParallelGroup()
                    .addComponent(lblDescAntrean)
                    .addComponent(lblNomorAntrean)
                    .addComponent(lblDescPosisi)
                    .addComponent(lblPosisi)
                    .addComponent(lblDescStatus)
                    .addComponent(lblStatus)
                    .addComponent(lblDescMeja)
                    .addComponent(lblMeja))
                .addGap(16))
        );
        panelHasilLayout.setVerticalGroup(
            panelHasilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHasilLayout.createSequentialGroup()
                .addGap(8)
                .addComponent(lblDescAntrean)
                .addGap(2)
                .addComponent(lblNomorAntrean)
                .addGap(10)
                .addComponent(lblDescPosisi)
                .addGap(2)
                .addComponent(lblPosisi)
                .addGap(10)
                .addComponent(lblDescStatus)
                .addGap(2)
                .addComponent(lblStatus)
                .addGap(10)
                .addComponent(lblDescMeja)
                .addGap(2)
                .addComponent(lblMeja)
                .addGap(8))
        );

        // Layout utama
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJudul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubjudul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblKodeAkses)
                    .addComponent(txtKodeAkses)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCek, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(8)
                        .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelHasil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24)
                .addComponent(lblJudul)
                .addGap(4)
                .addComponent(lblSubjudul)
                .addGap(20)
                .addComponent(lblKodeAkses)
                .addGap(4)
                .addComponent(txtKodeAkses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCek)
                    .addComponent(btnReset))
                .addGap(8)
                .addComponent(lblError)
                .addGap(12)
                .addComponent(panelHasil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24))
        );

        pack();
    }// </editor-fold>

    // ----------------------------------------------------------------
    // Deklarasi komponen
    // ----------------------------------------------------------------
    private javax.swing.JLabel  lblJudul;
    private javax.swing.JLabel  lblSubjudul;
    private javax.swing.JLabel  lblKodeAkses;
    private javax.swing.JTextField txtKodeAkses;
    private javax.swing.JButton btnCek;
    private javax.swing.JButton btnReset;
    private javax.swing.JLabel  lblError;
    private javax.swing.JPanel  panelHasil;
    private javax.swing.JLabel  lblDescAntrean;
    private javax.swing.JLabel  lblNomorAntrean;
    private javax.swing.JLabel  lblDescPosisi;
    private javax.swing.JLabel  lblPosisi;
    private javax.swing.JLabel  lblDescStatus;
    private javax.swing.JLabel  lblStatus;
    private javax.swing.JLabel  lblDescMeja;
    private javax.swing.JLabel  lblMeja;
}
