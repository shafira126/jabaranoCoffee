package com.mycompany.jabaranocoffee.view;

import com.mycompany.jabaranocoffee.entity.QueueStatusHistory;
import com.mycompany.jabaranocoffee.repository.QueueRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Layar publik yang menampilkan antrean yang sedang dipanggil.
 * Auto-refresh setiap 3 detik menggunakan Swing Timer.
 *
 * @author rangga
 */
public class FormDisplay extends javax.swing.JFrame {

    // ----------------------------------------------------------------
    // Atribut
    // ----------------------------------------------------------------
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(FormDisplay.class.getName());

    private final QueueRepository queueRepository;
    private javax.swing.Timer     refreshTimer;

    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------
    public FormDisplay() {
        this.queueRepository = new QueueRepository();
        initComponents();
        customizeComponents();
        startAutoRefresh();
        loadDisplay();
    }

    // ----------------------------------------------------------------
    // Styling tambahan
    // ----------------------------------------------------------------
    private void customizeComponents() {
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen

        getContentPane().setBackground(new Color(30, 30, 30));

        lblJudul.setForeground(Color.WHITE);
        lblJudul.setFont(new java.awt.Font("Helvetica Neue", Font.BOLD, 36));

        lblSubjudul.setForeground(new Color(180, 180, 180));
        lblSubjudul.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 16));

        lblNowServing.setForeground(new Color(180, 180, 180));
        lblNowServing.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 18));

        panelCalled.setBackground(new Color(30, 30, 30));

        lblKosong.setForeground(new Color(120, 120, 120));
        lblKosong.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 20));

        lblRefresh.setForeground(new Color(100, 100, 100));
        lblRefresh.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 12));
        
        btnCekAntrean.setBorderPainted(false);
        btnCekAntrean.setFocusPainted(false);
        btnCekAntrean.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    // ----------------------------------------------------------------
    // Proses: auto-refresh setiap 3 detik
    // ----------------------------------------------------------------
    private void startAutoRefresh() {
        refreshTimer = new javax.swing.Timer(3000, e -> loadDisplay());
        refreshTimer.start();
    }

    private void loadDisplay() {
        List<Object[]> calledList = queueRepository.findCalledForDisplay();

        panelCalled.removeAll();

        if (calledList.isEmpty()) {
            lblKosong.setVisible(true);
            panelCalled.add(lblKosong);
        } else {
            lblKosong.setVisible(false);
            for (Object[] row : calledList) {
                String queueNumber = (String) row[0];
                String tableNumber = (String) row[1];
                panelCalled.add(createCalledCard(queueNumber, tableNumber));
            }
        }

        panelCalled.revalidate();
        panelCalled.repaint();

        // Update timestamp refresh
        lblRefresh.setText("Diperbarui: " +
            java.time.LocalTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    // ----------------------------------------------------------------
    // Output: buat card untuk setiap antrean yang dipanggil
    // ----------------------------------------------------------------
    private JPanel createCalledCard(String queueNumber, String tableNumber) {
        JPanel card = new JPanel(new BorderLayout(0, 8));
        card.setBackground(new Color(60, 60, 60));
        card.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        card.setPreferredSize(new Dimension(220, 140));

        JLabel lblQueue = new JLabel(queueNumber, SwingConstants.CENTER);
        lblQueue.setFont(new java.awt.Font("Helvetica Neue", Font.BOLD, 48));
        lblQueue.setForeground(new Color(255, 200, 50));

        JLabel lblMeja = new JLabel("Meja " + tableNumber, SwingConstants.CENTER);
        lblMeja.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 20));
        lblMeja.setForeground(Color.WHITE);

        card.add(lblQueue, BorderLayout.CENTER);
        card.add(lblMeja, BorderLayout.SOUTH);

        return card;
    }

    // ----------------------------------------------------------------
    // Dialog cek antrean
    // ----------------------------------------------------------------
    private void showCekAntreanDialog() {
        JDialog dialog = new JDialog(this, "Cek Status Antrean", true);
        dialog.setSize(380, 480);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.getContentPane().setBackground(new Color(42, 42, 42));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(42, 42, 42));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // Input kode akses
        JLabel lblKode = new JLabel("Kode Akses");
        lblKode.setForeground(new Color(170, 170, 170));
        lblKode.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 12));
        lblKode.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtKode = new JTextField();
        txtKode.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        txtKode.setBackground(new Color(60, 60, 60));
        txtKode.setForeground(Color.WHITE);
        txtKode.setCaretColor(Color.WHITE);
        txtKode.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(85, 85, 85)),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        txtKode.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 14));
        txtKode.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Tombol
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelBtn.setBackground(new Color(42, 42, 42));
        panelBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton btnCek = new JButton("Cek");
        btnCek.setBackground(new Color(255, 200, 50));
        btnCek.setForeground(new Color(30, 30, 30));
        btnCek.setFont(new java.awt.Font("Helvetica Neue", Font.BOLD, 12));
        btnCek.setBorderPainted(false);
        btnCek.setFocusPainted(false);
        btnCek.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCek.setPreferredSize(new Dimension(80, 32));

        JButton btnReset = new JButton("Reset");
        btnReset.setBackground(new Color(60, 60, 60));
        btnReset.setForeground(new Color(170, 170, 170));
        btnReset.setBorder(BorderFactory.createLineBorder(new Color(85, 85, 85)));
        btnReset.setFocusPainted(false);
        btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnReset.setPreferredSize(new Dimension(80, 32));

        panelBtn.add(btnCek);
        panelBtn.add(Box.createHorizontalStrut(8));
        panelBtn.add(btnReset);

        // Label error
        JLabel lblError = new JLabel(" ");
        lblError.setForeground(new Color(255, 100, 100));
        lblError.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 12));
        lblError.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel hasil
        JPanel panelHasil = new JPanel(new GridLayout(2, 2, 10, 8));
        panelHasil.setBackground(new Color(60, 60, 60));
        panelHasil.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        panelHasil.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelHasil.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        panelHasil.setVisible(false);

        JLabel lblDescNo  = createDialogLabel("No Antrean", true);
        JLabel lblNo      = createDialogLabel("-", false);
        lblNo.setFont(new java.awt.Font("Helvetica Neue", Font.BOLD, 24));
        lblNo.setForeground(new Color(255, 200, 50));

        JLabel lblDescSts = createDialogLabel("Status", true);
        JLabel lblSts     = createDialogLabel("-", false);
        lblSts.setFont(new java.awt.Font("Helvetica Neue", Font.BOLD, 13));

        panelHasil.add(lblDescNo);
        panelHasil.add(lblDescSts);
        panelHasil.add(lblNo);
        panelHasil.add(lblSts);

        // Panel posisi + meja
        JPanel panelExtra = new JPanel(new GridLayout(1, 2, 10, 0));
        panelExtra.setBackground(new Color(60, 60, 60));
        panelExtra.setBorder(BorderFactory.createEmptyBorder(0, 14, 10, 14));
        panelExtra.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelExtra.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panelExtra.setVisible(false);

        JLabel lblDescPos  = createDialogLabel("Posisi", true);
        JLabel lblPos      = createDialogLabel("-", false);
        JLabel lblDescMeja = createDialogLabel("Meja", true);
        JLabel lblMeja2    = createDialogLabel("-", false);
        lblMeja2.setFont(new java.awt.Font("Helvetica Neue", Font.BOLD, 13));
        lblMeja2.setForeground(Color.WHITE);

        JPanel pPos = new JPanel(new BorderLayout());
        pPos.setBackground(new Color(60, 60, 60));
        pPos.add(lblDescPos, BorderLayout.NORTH);
        pPos.add(lblPos, BorderLayout.CENTER);

        JPanel pMeja = new JPanel(new BorderLayout());
        pMeja.setBackground(new Color(60, 60, 60));
        pMeja.add(lblDescMeja, BorderLayout.NORTH);
        pMeja.add(lblMeja2, BorderLayout.CENTER);

        panelExtra.add(pPos);
        panelExtra.add(pMeja);

        // Panel riwayat
        JPanel panelRiwayat = new JPanel(new BorderLayout());
        panelRiwayat.setBackground(new Color(60, 60, 60));
        panelRiwayat.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelRiwayat.setVisible(false);

        JLabel lblRiwayatJudul = createDialogLabel("Riwayat Status", true);
        lblRiwayatJudul.setBorder(BorderFactory.createEmptyBorder(8, 10, 6, 10));

        String[] colRiwayat = {"Dari", "Ke", "Waktu"};
        DefaultTableModel riwayatModel = new DefaultTableModel(colRiwayat, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tblRiwayat = new JTable(riwayatModel);
        tblRiwayat.setBackground(new Color(60, 60, 60));
        tblRiwayat.setForeground(new Color(200, 200, 200));
        tblRiwayat.setGridColor(new Color(80, 80, 80));
        tblRiwayat.setRowHeight(22);
        tblRiwayat.getTableHeader().setBackground(new Color(50, 50, 50));
        tblRiwayat.getTableHeader().setForeground(new Color(150, 150, 150));
        tblRiwayat.setEnabled(false);

        JScrollPane scrollRiwayat = new JScrollPane(tblRiwayat);
        scrollRiwayat.setPreferredSize(new Dimension(0, 110));
        scrollRiwayat.setBorder(BorderFactory.createEmptyBorder());

        panelRiwayat.add(lblRiwayatJudul, BorderLayout.NORTH);
        panelRiwayat.add(scrollRiwayat, BorderLayout.CENTER);

        // Susun semua ke mainPanel
        mainPanel.add(lblKode);
        mainPanel.add(Box.createVerticalStrut(4));
        mainPanel.add(txtKode);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(panelBtn);
        mainPanel.add(Box.createVerticalStrut(6));
        mainPanel.add(lblError);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(panelHasil);
        mainPanel.add(panelExtra);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(panelRiwayat);

        dialog.add(mainPanel);

        // ---- Action: cek ----
        Runnable doCek = () -> {
            String kode = txtKode.getText().trim().toUpperCase();
            if (kode.isEmpty()) {
                lblError.setText("Masukkan kode akses terlebih dahulu.");
                panelHasil.setVisible(false);
                panelExtra.setVisible(false);
                panelRiwayat.setVisible(false);
                return;
            }

            lblError.setText(" ");
            Object[] data = queueRepository.findByAccessCode(kode);

            if (data == null) {
                lblError.setText("Kode akses tidak ditemukan.");
                panelHasil.setVisible(false);
                panelExtra.setVisible(false);
                panelRiwayat.setVisible(false);
                return;
            }

            String queueNumber = (String) data[0];
            int    position    = (int)    data[1];
            String status      = (String) data[2];
            String tableNumber = (String) data[3];

            lblNo.setText(queueNumber);
            lblSts.setText(status);
            lblPos.setText(position > 0 ? "Ke-" + position : "-");
            lblMeja2.setText(tableNumber != null ? tableNumber : "-");

            switch (status) {
                case "WAITING"   -> lblSts.setForeground(new Color(255, 200, 50));
                case "CALLED"    -> lblSts.setForeground(new Color(100, 181, 246));
                case "FINISHED"  -> lblSts.setForeground(new Color(129, 199, 132));
                case "CANCELLED" -> lblSts.setForeground(new Color(255, 100, 100));
                default          -> lblSts.setForeground(Color.WHITE);
            }

            panelHasil.setVisible(true);
            panelExtra.setVisible(true);

            // Load riwayat kalau ada queueId di index 4
            if (data.length > 4 && data[4] != null) {
                int queueId = (int) data[4];
                List<QueueStatusHistory> history =
                    queueRepository.findHistoryByQueueId(queueId);

                riwayatModel.setRowCount(0);
                for (QueueStatusHistory h : history) {
                    riwayatModel.addRow(new Object[]{
                        h.getOldStatus() != null ? h.getOldStatus() : "-",
                        h.getNewStatus(),
                        h.getChangedAt() != null
                            ? h.getChangedAt().format(
                                java.time.format.DateTimeFormatter
                                    .ofPattern("HH:mm:ss"))
                            : "-"
                    });
                }
                panelRiwayat.setVisible(true);
            }

            dialog.revalidate();
            dialog.repaint();
            dialog.pack();
            dialog.setLocationRelativeTo(this);
        };

        btnCek.addActionListener(e -> doCek.run());
        txtKode.addActionListener(e -> doCek.run());

        btnReset.addActionListener(e -> {
            txtKode.setText("");
            lblError.setText(" ");
            panelHasil.setVisible(false);
            panelExtra.setVisible(false);
            panelRiwayat.setVisible(false);
            riwayatModel.setRowCount(0);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            txtKode.requestFocus();
        });

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // ----------------------------------------------------------------
    // Helper
    // ----------------------------------------------------------------
    private JLabel createDialogLabel(String text, boolean isDesc) {
        JLabel lbl = new JLabel(text);
        if (isDesc) {
            lbl.setForeground(new Color(136, 136, 136));
            lbl.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 10));
        } else {
            lbl.setForeground(new Color(200, 200, 200));
            lbl.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 13));
        }
        return lbl;
    }
    
    // ----------------------------------------------------------------
    // Stop timer saat form ditutup
    // ----------------------------------------------------------------
    @Override
    public void dispose() {
        if (refreshTimer != null) refreshTimer.stop();
        super.dispose();
    }

    // ----------------------------------------------------------------
    // Generated Code — GUI Builder
    // ----------------------------------------------------------------
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblJudul = new javax.swing.JLabel();
        lblSubjudul = new javax.swing.JLabel();
        lblNowServing = new javax.swing.JLabel();
        panelCalled = new javax.swing.JPanel();
        lblKosong = new javax.swing.JLabel();
        lblRefresh = new javax.swing.JLabel();
        btnCekAntrean = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Jabarano Coffee — Public Display");

        lblJudul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblJudul.setText("Jabarano Coffee");

        lblSubjudul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSubjudul.setText("Sistem Antrean");

        lblNowServing.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNowServing.setText("Sedang Dilayani");

        panelCalled.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 16, 16));

        lblKosong.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKosong.setText("Belum ada antrean yang dipanggil");
        panelCalled.add(lblKosong);

        lblRefresh.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRefresh.setText("Diperbarui: -");

        btnCekAntrean.setBackground(new java.awt.Color(255, 200, 50));
        btnCekAntrean.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        btnCekAntrean.setForeground(new java.awt.Color(30, 30, 30));
        btnCekAntrean.setText("Cek Antrean");
        btnCekAntrean.addActionListener(this::btnCekAntreanActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJudul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubjudul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNowServing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCalled, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCekAntrean)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCekAntrean)
                .addGap(11, 11, 11)
                .addComponent(lblJudul)
                .addGap(8, 8, 8)
                .addComponent(lblSubjudul)
                .addGap(32, 32, 32)
                .addComponent(lblNowServing)
                .addGap(16, 16, 16)
                .addComponent(panelCalled, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addComponent(lblRefresh)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCekAntreanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekAntreanActionPerformed
        showCekAntreanDialog();
    }//GEN-LAST:event_btnCekAntreanActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCekAntrean;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblKosong;
    private javax.swing.JLabel lblNowServing;
    private javax.swing.JLabel lblRefresh;
    private javax.swing.JLabel lblSubjudul;
    private javax.swing.JPanel panelCalled;
    // End of variables declaration//GEN-END:variables
}
