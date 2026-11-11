package com.mycompany.jabaranocoffee.view;

import com.mycompany.jabaranocoffee.repository.QueueRepository;

import javax.swing.*;
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

        panelCalled.setBackground(new Color(45, 45, 45));

        lblKosong.setForeground(new Color(120, 120, 120));
        lblKosong.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 20));

        lblRefresh.setForeground(new Color(100, 100, 100));
        lblRefresh.setFont(new java.awt.Font("Helvetica Neue", Font.PLAIN, 12));
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        lblJudul      = new javax.swing.JLabel();
        lblSubjudul   = new javax.swing.JLabel();
        lblNowServing = new javax.swing.JLabel();
        panelCalled   = new javax.swing.JPanel();
        lblKosong     = new javax.swing.JLabel();
        lblRefresh    = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Jabarano Coffee — Public Display");
        setBackground(new Color(30, 30, 30));

        lblJudul.setText("Jabarano Coffee");
        lblJudul.setHorizontalAlignment(SwingConstants.CENTER);

        lblSubjudul.setText("Sistem Antrean");
        lblSubjudul.setHorizontalAlignment(SwingConstants.CENTER);

        lblNowServing.setText("Sedang Dilayani");
        lblNowServing.setHorizontalAlignment(SwingConstants.CENTER);

        panelCalled.setLayout(new java.awt.FlowLayout(FlowLayout.CENTER, 16, 16));

        lblKosong.setText("Belum ada antrean yang dipanggil");
        lblKosong.setHorizontalAlignment(SwingConstants.CENTER);

        lblRefresh.setText("Diperbarui: -");
        lblRefresh.setHorizontalAlignment(SwingConstants.RIGHT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJudul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubjudul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNowServing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCalled, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40)
                .addComponent(lblJudul)
                .addGap(8)
                .addComponent(lblSubjudul)
                .addGap(32)
                .addComponent(lblNowServing)
                .addGap(16)
                .addComponent(panelCalled, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8)
                .addComponent(lblRefresh)
                .addGap(16))
        );

        pack();
    }// </editor-fold>

    // ----------------------------------------------------------------
    // Deklarasi komponen
    // ----------------------------------------------------------------
    private javax.swing.JLabel  lblJudul;
    private javax.swing.JLabel  lblSubjudul;
    private javax.swing.JLabel  lblNowServing;
    private javax.swing.JPanel  panelCalled;
    private javax.swing.JLabel  lblKosong;
    private javax.swing.JLabel  lblRefresh;
}
