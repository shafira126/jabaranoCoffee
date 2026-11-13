package com.mycompany.jabaranocoffee.view;

import com.mycompany.jabaranocoffee.entity.User;
import com.mycompany.jabaranocoffee.repository.QueueRepository;
import com.mycompany.jabaranocoffee.repository.TableRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Dashboard utama staff Jabarano Coffee.
 * Menampilkan ringkasan operasional dan antrean aktif hari ini.
 *
 * @author rangga
 */
public class FormDashboard extends javax.swing.JFrame {

    // ----------------------------------------------------------------
    // Atribut
    // ----------------------------------------------------------------
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(FormDashboard.class.getName());

    private final User            loggedUser;
    private final QueueRepository queueRepository;
    private final TableRepository tableRepository;

    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------
    public FormDashboard(User user) {
        this.loggedUser      = user;
        this.queueRepository = new QueueRepository();
        this.tableRepository = new TableRepository();
        initComponents();
        customizeComponents();
        loadDashboard();
    }

    // ----------------------------------------------------------------
    // Styling tambahan
    // ----------------------------------------------------------------
    private void customizeComponents() {
        setLocationRelativeTo(null);

        // Tanggal hari ini
        String tanggal = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy",
                        new java.util.Locale("id", "ID")));
        lblWelcome.setText("Selamat datang, " + loggedUser.getUsername());
        lblTanggal.setText(tanggal);

        // Warna panel statistik
        panelWaiting.setBackground(new Color(255, 243, 205));
        panelCalled.setBackground(new Color(207, 226, 255));
        panelFinished.setBackground(new Color(209, 236, 214));
        panelTable.setBackground(new Color(240, 240, 240));

        lblCountWaiting.setForeground(new Color(133, 100, 4));
        lblCountCalled.setForeground(new Color(13, 71, 161));
        lblCountFinished.setForeground(new Color(27, 94, 32));

        // Setup tabel
        setupTable();

        // Tombol navigasi
        btnAntrean.addActionListener(e -> openAntrean());
        btnCustomer.addActionListener(e -> openCustomer());
        btnDisplay.addActionListener(e -> openDisplay());
        btnKeluar.addActionListener(e -> doLogout());
    }

    // ----------------------------------------------------------------
    // Input: setup kolom tabel
    // ----------------------------------------------------------------
    private void setupTable() {
        String[] columns = {"No Antrean", "Nama Pelanggan", "Jumlah Orang", "Status", "Waktu Daftar"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabel tidak bisa diedit
            }
        };
        tblAntrean.setModel(model);
        tblAntrean.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblAntrean.getTableHeader().setReorderingAllowed(false);
    }

    // ----------------------------------------------------------------
    // Proses: load data dashboard
    // ----------------------------------------------------------------
    private void loadDashboard() {
        // Hitung statistik
        int waiting  = queueRepository.countByStatus("WAITING");
        int called   = queueRepository.countByStatus("CALLED");
        int finished = queueRepository.countByStatus("FINISHED");
        int tables   = tableRepository.countAvailable();

        // Tampilkan statistik
        lblCountWaiting.setText(String.valueOf(waiting));
        lblCountCalled.setText(String.valueOf(called));
        lblCountFinished.setText(String.valueOf(finished));
        lblCountTable.setText(String.valueOf(tables));

        // Load tabel antrean aktif hari ini
        loadTableData();
    }

    private void loadTableData() {
        DefaultTableModel model = (DefaultTableModel) tblAntrean.getModel();
        model.setRowCount(0); // kosongkan dulu

        List<Object[]> rows = queueRepository.findTodayQueueForTable();
        for (Object[] row : rows) {
            model.addRow(row);
        }
    }

    // ----------------------------------------------------------------
    // Output: navigasi ke form lain
    // ----------------------------------------------------------------
    private void openAntrean() {
        new FormAntrean(loggedUser).setVisible(true);
    }

    private void openCustomer() {
        new FormCustomer().setVisible(true);
    }

    private void openDisplay() {
        new FormDisplay().setVisible(true);
    }

    private void doLogout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin keluar?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            new FormLogin().setVisible(true);
            this.dispose();
        }
    }

    // ----------------------------------------------------------------
    // Generated Code — GUI Builder
    // ----------------------------------------------------------------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWelcome = new javax.swing.JLabel();
        lblTanggal = new javax.swing.JLabel();
        panelWaiting = new javax.swing.JPanel();
        lblCountWaiting = new javax.swing.JLabel();
        lblDescWaiting = new javax.swing.JLabel();
        panelCalled = new javax.swing.JPanel();
        lblCountCalled = new javax.swing.JLabel();
        lblDescCalled = new javax.swing.JLabel();
        panelFinished = new javax.swing.JPanel();
        lblCountFinished = new javax.swing.JLabel();
        lblDescFinished = new javax.swing.JLabel();
        panelTable = new javax.swing.JPanel();
        lblCountTable = new javax.swing.JLabel();
        lblDescTable = new javax.swing.JLabel();
        lblAktif = new javax.swing.JLabel();
        scrollAntrean = new javax.swing.JScrollPane();
        tblAntrean = new javax.swing.JTable();
        btnAntrean = new javax.swing.JButton();
        btnCustomer = new javax.swing.JButton();
        btnDisplay = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jabarano Coffee — Dashboard");
        setPreferredSize(new java.awt.Dimension(700, 520));
        setResizable(false);

        lblWelcome.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N

        lblTanggal.setFont(new java.awt.Font("Helvetica Neue", 0, 12)); // NOI18N
        lblTanggal.setForeground(new java.awt.Color(100, 100, 100));

        panelWaiting.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 14, 10, 14));
        panelWaiting.setLayout(new java.awt.BorderLayout());

        lblCountWaiting.setFont(new java.awt.Font("Helvetica Neue", 1, 28)); // NOI18N
        lblCountWaiting.setText("0");
        panelWaiting.add(lblCountWaiting, java.awt.BorderLayout.CENTER);

        lblDescWaiting.setFont(new java.awt.Font("Helvetica Neue", 0, 11)); // NOI18N
        lblDescWaiting.setText("Menunggu");
        panelWaiting.add(lblDescWaiting, java.awt.BorderLayout.SOUTH);

        panelCalled.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 14, 10, 14));
        panelCalled.setLayout(new java.awt.BorderLayout());

        lblCountCalled.setFont(new java.awt.Font("Helvetica Neue", 1, 28)); // NOI18N
        lblCountCalled.setText("0");
        panelCalled.add(lblCountCalled, java.awt.BorderLayout.CENTER);

        lblDescCalled.setFont(new java.awt.Font("Helvetica Neue", 0, 11)); // NOI18N
        lblDescCalled.setText("Dipanggil");
        panelCalled.add(lblDescCalled, java.awt.BorderLayout.SOUTH);

        panelFinished.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 14, 10, 14));
        panelFinished.setLayout(new java.awt.BorderLayout());

        lblCountFinished.setFont(new java.awt.Font("Helvetica Neue", 1, 28)); // NOI18N
        lblCountFinished.setText("0");
        panelFinished.add(lblCountFinished, java.awt.BorderLayout.CENTER);

        lblDescFinished.setFont(new java.awt.Font("Helvetica Neue", 0, 11)); // NOI18N
        lblDescFinished.setText("Selesai");
        panelFinished.add(lblDescFinished, java.awt.BorderLayout.SOUTH);

        panelTable.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 14, 10, 14));
        panelTable.setLayout(new java.awt.BorderLayout());

        lblCountTable.setFont(new java.awt.Font("Helvetica Neue", 1, 28)); // NOI18N
        lblCountTable.setText("0");
        panelTable.add(lblCountTable, java.awt.BorderLayout.CENTER);

        lblDescTable.setFont(new java.awt.Font("Helvetica Neue", 0, 11)); // NOI18N
        lblDescTable.setText("Meja Tersedia");
        panelTable.add(lblDescTable, java.awt.BorderLayout.SOUTH);

        lblAktif.setFont(new java.awt.Font("Helvetica Neue", 1, 12)); // NOI18N
        lblAktif.setText("Antrean aktif hari ini");

        scrollAntrean.setViewportView(tblAntrean);

        btnAntrean.setText("Antrean");

        btnCustomer.setText("Cek Pelanggan");

        btnDisplay.setText("Public Display");

        btnKeluar.setForeground(new java.awt.Color(180, 0, 0));
        btnKeluar.setText("Keluar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblWelcome)
                            .addComponent(lblTanggal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAntrean)
                        .addGap(6, 6, 6)
                        .addComponent(btnCustomer)
                        .addGap(6, 6, 6)
                        .addComponent(btnDisplay)
                        .addGap(6, 6, 6)
                        .addComponent(btnKeluar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelWaiting, 0, 150, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(panelCalled, 0, 150, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(panelFinished, 0, 150, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(panelTable, 0, 150, Short.MAX_VALUE))
                    .addComponent(lblAktif)
                    .addComponent(scrollAntrean, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblWelcome)
                        .addGap(2, 2, 2)
                        .addComponent(lblTanggal))
                    .addComponent(btnAntrean)
                    .addComponent(btnCustomer)
                    .addComponent(btnDisplay)
                    .addComponent(btnKeluar))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelWaiting, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCalled, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelFinished, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelTable, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(lblAktif)
                .addGap(6, 6, 6)
                .addComponent(scrollAntrean, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAntrean;
    private javax.swing.JButton btnCustomer;
    private javax.swing.JButton btnDisplay;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JLabel lblAktif;
    private javax.swing.JLabel lblCountCalled;
    private javax.swing.JLabel lblCountFinished;
    private javax.swing.JLabel lblCountTable;
    private javax.swing.JLabel lblCountWaiting;
    private javax.swing.JLabel lblDescCalled;
    private javax.swing.JLabel lblDescFinished;
    private javax.swing.JLabel lblDescTable;
    private javax.swing.JLabel lblDescWaiting;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel panelCalled;
    private javax.swing.JPanel panelFinished;
    private javax.swing.JPanel panelTable;
    private javax.swing.JPanel panelWaiting;
    private javax.swing.JScrollPane scrollAntrean;
    private javax.swing.JTable tblAntrean;
    // End of variables declaration//GEN-END:variables
}
