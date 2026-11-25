/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.jabaranocoffee.view;

import com.mycompany.jabaranocoffee.entity.CafeTable;
import com.mycompany.jabaranocoffee.entity.QueueEntry;
import com.mycompany.jabaranocoffee.entity.QueueStatusHistory;
import com.mycompany.jabaranocoffee.entity.User;
import com.mycompany.jabaranocoffee.repository.QueueRepository;
import com.mycompany.jabaranocoffee.repository.TableRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 *
 * @author Shafira Nailah
 */
public class FormAntrean extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormAntrean.class.getName());
    private final User loggedUser;
    private final QueueRepository queueRepository;
    private final TableRepository tableRepository;

    private int selectedQueueId = -1;

    /**
     * Creates new form FormAntrean
     */
    
    public FormAntrean(){
        this(null);
    }
    public FormAntrean(User loggedUser) {
        this.loggedUser = loggedUser;
        this.queueRepository = new QueueRepository();
        this.tableRepository = new TableRepository();
        initComponents();
        customizeComponents();
        loadTable();
    }
    
    private void customizeComponents(){
        setLocationRelativeTo(null);
        SpnJumlah.setModel(new SpinnerNumberModel(1, 1, 20, 1));
        lblInfo.setText("");
        updateActionButtons(false);
        
        setupTable();
        
        tblAntrean.getSelectionModel().addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()){
                onRowSelected();
            }      
        });
        btnDaftarkan.addActionListener(e -> doDaftarkan());
    }
    
    private void setupTable(){
        String[] columns = {
            "No Antrean", "Nama Pelanggan", "Jumlah Orang",
            "Kode Akses", "Status" , "Meja"
        };
        
        DefaultTableModel model = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tblAntrean.setModel(model);
        tblAntrean.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblAntrean.getTableHeader().setReorderingAllowed(false);           
    }
    
    private void loadTable(){
        DefaultTableModel model = (DefaultTableModel) tblAntrean.getModel();
        model.setRowCount(0);
        selectedQueueId = -1;
        updateActionButtons (false);
        
        List<QueueEntry> list = queueRepository.findTodayAll();
        
        for(QueueEntry q : list){
            String tableNum = q.getTableId() != null
            ? "T" + q.getTableId() : "-";
            model.addRow(new Object[]{
                q.getQueueNumber(), q.getCustomerName(), 
                q.getPartySize(), q.getAccessCode(),
                q.getStatus(), tableNum
            });
        }
        
        queueList = list;
        lblInfo.setText("Total Hari ini: " + list.size() + " antrean" );
    }
    
    private List <QueueEntry> queueList = new java.util.ArrayList<>();
    
    private void onRowSelected() {
    int row = tblAntrean.getSelectedRow();
    if (row < 0 || row >= queueList.size()) {
        selectedQueueId = -1;
        updateActionButtons(false);
        return;
    }

    QueueEntry selected = queueList.get(row);
    selectedQueueId = selected.getId();
    String status = selected.getStatus();
    
    btnPanggil.setEnabled("WAITING".equals(status));
    btnSelesai.setEnabled("CALLED".equals(status));
    btnBatalkan.setEnabled("WAITING".equals(status) || "CALLED".equals(status));
    btnRiwayat.setEnabled(true);
}
    
    private void updateActionButtons(boolean enabled){
        btnPanggil.setEnabled(enabled);
        btnSelesai.setEnabled(enabled);
        btnBatalkan.setEnabled(enabled);
        btnRiwayat.setEnabled(enabled);
    }
    
    private void doDaftarkan(){
        String nama = txtNama.getText().trim();
        int jumlahOrang = (int) SpnJumlah.getValue();

        if (nama.isEmpty()) {
            showInfo("Nama pelanggan harus diisi!.", true);
            txtNama.requestFocus();
            return;
        }
        if (jumlahOrang <= 0) {
            showInfo("Jumlah orang harus lebih dari 0.", true);
            SpnJumlah.requestFocus();
            return;
        }

        QueueEntry baru = queueRepository.registerCustomer(nama, jumlahOrang);

        if (baru != null) {
            // Tampilkan nomor antrean dan kode akses ke staff
            JOptionPane.showMessageDialog(this,
                "Pelanggan berhasil didaftarkan!\n\n" +
                "Nomor Antrean : " + baru.getQueueNumber() + "\n" +
                "Kode Akses    : " + baru.getAccessCode() + "\n\n" +
                "Berikan informasi ini kepada pelanggan.",
                "Pendaftaran Berhasil",
                JOptionPane.INFORMATION_MESSAGE);

            txtNama.setText("");
            SpnJumlah.setValue(1);
            loadTable();
        } else {
            showInfo("Gagal mendaftarkan pelanggan. Coba lagi.", true);
        }
        
    }
    
    private void doPanggil() {
        if (selectedQueueId < 0) return;

        int row = tblAntrean.getSelectedRow();
        QueueEntry selected = queueList.get(row);

        // Ambil meja yang tersedia sesuai kapasitas
        List<CafeTable> tersedia =
            tableRepository.findAvailableByCapacity(selected.getPartySize());

        if (tersedia.isEmpty()) {
            showInfo("Tidak ada meja tersedia untuk " + selected.getPartySize() + " orang.", true);
            return;
        }
        CafeTable pilihan = showPilihMejaDialog(selected, tersedia);
        if (pilihan == null) return;

        String result = queueRepository.callQueue(selectedQueueId, pilihan.getId());
        if ("SUCCESS".equals(result)) {
            showInfo("Antrean " + selected.getQueueNumber() +
                     " dipanggil ke meja " + pilihan.getTableNumber() + ".", false);
            loadTable();
        } else {
            showInfo(result, true);
        }
    }
    
        private CafeTable showPilihMejaDialog(QueueEntry queue, List<CafeTable> tersedia) {
        JComboBox<CafeTable> combo = new JComboBox<>(tersedia.toArray(new CafeTable[0]));

        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 6));
        panel.add(new JLabel("Antrean  : " + queue.getQueueNumber() +
                             " — " + queue.getCustomerName() +
                             " (" + queue.getPartySize() + " orang)"));
        panel.add(new JLabel("Pilih meja (kapasitas ≥ " + queue.getPartySize() + "):"));
        panel.add(combo);

        int option = JOptionPane.showConfirmDialog(
            this, panel, "Pilih Meja",JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            return (CafeTable) combo.getSelectedItem();
        }

        return null;
    }
        
    private void doSelesai() {
        if (selectedQueueId < 0) return;

        int row = tblAntrean.getSelectedRow();
        QueueEntry selected = queueList.get(row);

        int confirm = JOptionPane.showConfirmDialog(this, "Tandai antrean " + selected.getQueueNumber() +
            " (" + selected.getCustomerName() + ") sebagai SELESAI?", "Konfirmasi",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        String result = queueRepository.finishQueue(selectedQueueId);

        if ("SUCCESS".equals(result)) {
            showInfo("Antrean " + selected.getQueueNumber() + " selesai. Meja dikembalikan.", false);
            loadTable();
        } else {
            showInfo(result, true);
        }
    }
    
    private void doBatalkan() {
        if (selectedQueueId < 0) return;

        int row = tblAntrean.getSelectedRow();
        QueueEntry selected = queueList.get(row);

        int confirm = JOptionPane.showConfirmDialog(this,
            "Batalkan antrean " + selected.getQueueNumber() +
            " (" + selected.getCustomerName() + ")?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = queueRepository.cancelQueue(selectedQueueId);
        if (ok) {
            showInfo("Antrean " + selected.getQueueNumber() + " dibatalkan.", false);
            loadTable();
        } else {
            showInfo("Gagal membatalkan antrean. " + "Pastikan status masih WAITING.", true);
        }
    }
    
    private void doRiwayat() {
        if (selectedQueueId < 0) return;

        int row = tblAntrean.getSelectedRow();
        QueueEntry selected = queueList.get(row);

        List<QueueStatusHistory> history =
            queueRepository.findHistoryByQueueId(selectedQueueId);

        // Buat tabel riwayat
        String[] columns = {"Dari", "Ke", "Waktu"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        for (QueueStatusHistory h : history) {
            model.addRow(new Object[]{
                h.getOldStatus() != null ? h.getOldStatus() : "-",
                h.getNewStatus(),
                h.getChangedAt() != null
                    ? h.getChangedAt().format(
                        java.time.format.DateTimeFormatter
                            .ofPattern("HH:mm:ss"))
                    : "-"
            });
        }

        JTable tblHistory = new JTable(model);
        tblHistory.setEnabled(false);
        JScrollPane scroll = new JScrollPane(tblHistory);
        scroll.setPreferredSize(new Dimension(380, 160));

        JOptionPane.showMessageDialog(this,scroll, "Riwayat — " 
                + selected.getQueueNumber() + " " + selected.getCustomerName(),
            JOptionPane.PLAIN_MESSAGE);
    }
    
    private void doKembali() {
        this.dispose();
    }
    
    private void showInfo(String message, boolean isError) {
        lblInfo.setForeground(isError ? Color.RED : new Color(27, 94, 32));
        lblInfo.setText(message);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        btnKembali = new javax.swing.JButton();
        lblTambah = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        lblJumlah = new javax.swing.JLabel();
        SpnJumlah = new javax.swing.JSpinner();
        btnDaftarkan = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lblAksi = new javax.swing.JLabel();
        lblPilihInfo = new javax.swing.JLabel();
        btnPanggil = new javax.swing.JButton();
        btnSelesai = new javax.swing.JButton();
        btnBatalkan = new javax.swing.JButton();
        btnRiwayat = new javax.swing.JButton();
        lblTabelJudul = new javax.swing.JLabel();
        lblInfo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAntrean = new javax.swing.JTable();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Kelola Antrean");

        btnKembali.setText("Kembali ke Dasboard");
        btnKembali.setActionCommand("");
        btnKembali.addActionListener(this::btnKembaliActionPerformed);

        lblTambah.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblTambah.setText("TAMBAH PELANGGAN");
        lblTambah.setToolTipText("");

        lblNama.setText("Nama");

        txtNama.addActionListener(this::txtNamaActionPerformed);

        lblJumlah.setText("Jumlah Orang");

        btnDaftarkan.setText("+ Daftarkan");

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(153, 153, 153));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblAksi.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblAksi.setText("AKSI ANTREAN");

        lblPilihInfo.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lblPilihInfo.setText("Pilih Baris di Tabel ->");

        btnPanggil.setBackground(new java.awt.Color(204, 255, 255));
        btnPanggil.setText("Panggil");
        btnPanggil.addActionListener(this::btnPanggilActionPerformed);

        btnSelesai.setBackground(new java.awt.Color(204, 255, 204));
        btnSelesai.setText("Selesai");
        btnSelesai.addActionListener(this::btnSelesaiActionPerformed);

        btnBatalkan.setBackground(new java.awt.Color(255, 204, 204));
        btnBatalkan.setText("Batalkan");
        btnBatalkan.addActionListener(this::btnBatalkanActionPerformed);

        btnRiwayat.setBackground(new java.awt.Color(204, 204, 204));
        btnRiwayat.setText("Riwayat");
        btnRiwayat.addActionListener(this::btnRiwayatActionPerformed);

        lblTabelJudul.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblTabelJudul.setText("ANTREAN HARI INI");

        lblInfo.setForeground(java.awt.Color.gray);
        lblInfo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        tblAntrean.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nama", "Jumlah", "Kode", "Status", "Meja"
            }
        ));
        jScrollPane1.setViewportView(tblAntrean);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRiwayat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBatalkan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblJumlah)
                                        .addComponent(lblNama)
                                        .addComponent(txtNama)
                                        .addComponent(jLabel1)
                                        .addComponent(lblTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnDaftarkan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator1)
                                        .addComponent(SpnJumlah)
                                        .addComponent(lblAksi)
                                        .addComponent(lblPilihInfo)
                                        .addComponent(btnPanggil, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lblTabelJudul))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(327, 327, 327)
                        .addComponent(btnKembali))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTabelJudul)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKembali))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNama)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblJumlah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SpnJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDaftarkan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAksi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPilihInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPanggil)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSelesai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBatalkan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRiwayat)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        doKembali();
    }                                          

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        
    }                                       

    private void btnSelesaiActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        doSelesai();
    }                                          

    private void btnBatalkanActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        doBatalkan();
    }                                           

    private void btnRiwayatActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        doRiwayat();
    }                                          

    private void btnPanggilActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        doPanggil();
    }                                          

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FormAntrean().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JSpinner SpnJumlah;
    private javax.swing.JButton btnBatalkan;
    private javax.swing.JButton btnDaftarkan;
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnPanggil;
    private javax.swing.JButton btnRiwayat;
    private javax.swing.JButton btnSelesai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblAksi;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblJumlah;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblPilihInfo;
    private javax.swing.JLabel lblTabelJudul;
    private javax.swing.JLabel lblTambah;
    private javax.swing.JTable tblAntrean;
    private javax.swing.JTextField txtNama;
    // End of variables declaration                   
}
