/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jabaranocoffee.repository;

import com.mycompany.jabaranocoffee.entity.QueueEntry;
import com.mycompany.jabaranocoffee.entity.QueueStatusHistory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shafira Nailah
 */
public class QueueRepository {
    public QueueRepository(){}
    public QueueEntry registerCustomer(String customerName, int partySize){
        String queueNumber = generateQueueNumber();
        String accessCode = generateAccessCode();
        LocalDate today = LocalDate.now();
        
        String sql = "INSERT INTO queue_entries "
                + "(queue_number, access_code, customer_name, party_size, " + "status, queue_date) "
                + "VALUES (?, ?, ?, ?, 'WAITING', ?)";
        
        try(Connection conn = Koneksi.getConnection(); 
            PreparedStatement ps = conn.prepareStatement
            (sql, Statement.RETURN_GENERATED_KEYS)){
            
            
            ps.setString(1, queueNumber);
            ps.setString(2, accessCode);
            ps.setString(3, customerName);
            ps.setInt(4, partySize);
            ps.setDate(5, Date.valueOf(today));
            
            int affected = ps.executeUpdate();
            
            if(affected > 0){
                try(ResultSet keys = ps.getGeneratedKeys()){
                    if(keys.next()){
                        return findById(keys.getInt(1));             
                    }              
                }               
            }
        }  catch(SQLException e){
            System.err.println("[QueueRepository] Error registerCustomer: "
                    + e.getMessage());
        } 
        return null;
    }   

    private String generateQueueNumber(){
        String sql = "SELECT COUNT(*) FROM queue_entries WHERE queue_date = ?";
        
        try(Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    int count = rs.getInt(1) + 1;
                    return String.format("A%03d", count);     
                }
            }
        } catch (SQLException e){
            System.err.println("[QueueRepository] Error generateQueueNumber: "
                    + e.getMessage());
        }
        return "A001";    
    }  
    
    
    private String generateAccessCode(){
        String code;
        do{
            int random = (int) (Math.random() * 900)+ 100;
            code = "JBQ" + random;
        } while(isAccessCodeExists(code));
        return code;    
    } 
    
    @SuppressWarnings("empty-statement")
    private boolean isAccessCodeExists(String code){
        String sql = "SELECT COUNT(*) FROM queue_entries WHERE access_code = ?";
        
        try(Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setString(1, code);
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1) > 0;
                }
            } 
        } catch(SQLException e){
            System.err.println("[QueueRepository] Error isAccessCodeExists: "
                + e.getMessage());
        }
        return false;
    }
        
    public QueueEntry findById(int id){
        String sql = "SELECT id, queue_number, access_code, customer_name, " + 
                    "party_size, table_id, status, joined_at, called_at, queue_date " +
                    "FROM queue_entries WHERE id = ?";
        
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("[QueueRepository] Error findById: "
                    + e.getMessage());
        }

        return null;
    }
    
    public List<QueueEntry> findTodayAll(){
        List<QueueEntry> list = new ArrayList<>();
        String sql = "SELECT id, queue_number, access_code, customer_name, " +
                     "party_size, table_id, status, joined_at, called_at, queue_date " +
                     "FROM queue_entries " +
                     "WHERE queue_date = ? " +
                     "ORDER BY joined_at ASC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(LocalDate.now()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[QueueRepository] Error findTodayAll: "
                    + e.getMessage());
        }

        return list; 
    }
    
    public List<Object[]> findTodayQueueForTable() {
        List<Object[]> rows = new ArrayList<>();
        String sql = "SELECT qe.queue_number, qe.customer_name, qe.party_size, " +
                     "qe.status, qe.joined_at " +
                     "FROM queue_entries qe " +
                     "WHERE qe.queue_date = ? " +
                     "ORDER BY qe.joined_at ASC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(LocalDate.now()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new Object[]{
                        rs.getString("queue_number"),
                        rs.getString("customer_name"),
                        rs.getInt("party_size"),
                        rs.getString("status"),
                        rs.getTimestamp("joined_at") != null
                            ? rs.getTimestamp("joined_at").toLocalDateTime()
                                .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
                            : "-"
                    });
                }
            }

        } catch (SQLException e) {
            System.err.println("[QueueRepository] Error findTodayQueueForTable: "
                    + e.getMessage());
        }

        return rows;
    }
    
    public List<Object[]> findCalledForDisplay() {
        List<Object[]> rows = new ArrayList<>();
        String sql = "SELECT queue_number, table_number FROM v_queue_display";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                rows.add(new Object[]{
                    rs.getString("queue_number"),
                    rs.getString("table_number")
                });
            }

        } catch (SQLException e) {
            System.err.println("[QueueRepository] Error findCalledForDisplay: "
                    + e.getMessage());
        }

        return rows;
    }
    
    public Object[] findByAccessCode(String accessCode) {
    String sql = "SELECT qe.id, qe.queue_number, qe.status, " +
                 "ct.table_number " +
                 "FROM queue_entries qe " +
                 "LEFT JOIN cafe_tables ct ON qe.table_id = ct.id " +
                 "WHERE qe.access_code = ?";

    try (Connection conn = Koneksi.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, accessCode);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int    id          = rs.getInt("id");
                String queueNumber = rs.getString("queue_number");
                String status      = rs.getString("status");
                String tableNumber = rs.getString("table_number");

                int position = 0;
                if ("WAITING".equals(status)) {
                    position = getPositionInQueue(id);
                }

                // Tambah queueId di index ke-4
                return new Object[]{queueNumber, position,
                                    status, tableNumber, id};
            }
        }

    } catch (SQLException e) {
        System.err.println("[QueueRepository] Error findByAccessCode: "
                + e.getMessage());
    }

    return null;
}
    
    private int getPositionInQueue(int queueId) {
        String sql = "SELECT COUNT(*) FROM queue_entries " +
                     "WHERE status = 'WAITING' " +
                     "AND queue_date = ? " +
                     "AND id <= ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, queueId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("[QueueRepository] Error getPositionInQueue: "
                    + e.getMessage());
        }

        return 0;
    }
    
    
    // memanggil antrean dan mengalokasikan meja 
    public String callQueue(int queueId, int tableId) {
        String sql = "{CALL sp_call_queue(?, ?, ?)}";

        try (Connection conn = Koneksi.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, queueId);
            cs.setInt(2, tableId);
            cs.registerOutParameter(3, Types.VARCHAR);

            cs.execute();

            return cs.getString(3); // SUCCESS atau pesan error

        } catch (SQLException e) {
            System.err.println("[QueueRepository] Error callQueue: "
                    + e.getMessage());
            return "ERROR: " + e.getMessage();
        }
    }
    
    // menyelesaikan antrean dan mengubah status meja
    public String finishQueue(int queueId) {
        String sql = "{CALL sp_finish_queue(?, ?)}";

        try (Connection conn = Koneksi.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, queueId);
            cs.registerOutParameter(2, Types.VARCHAR);

            cs.execute();

            return cs.getString(2);

        } catch (SQLException e) {
            System.err.println("[QueueRepository] Error finishQueue: "
                    + e.getMessage());
            return "ERROR: " + e.getMessage();
        }
    }
    
    //membatalkan antrean 
    public boolean cancelQueue(int queueId) {
        String sql = "UPDATE queue_entries SET status = 'CANCELLED' " + // mengubah status ke CANCELLED
                     "WHERE id = ? AND status = 'WAITING'";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, queueId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[QueueRepository] Error cancelQueue: "
                    + e.getMessage());
        }

        return false;
    }
    
    //mengambil history perubahan status
    public List<QueueStatusHistory> findHistoryByQueueId(int queueId) {
        List<QueueStatusHistory> list = new ArrayList<>();
        String sql = "SELECT id, queue_id, old_status, new_status, changed_at " +
                     "FROM queue_status_history " +
                     "WHERE queue_id = ? " +
                     "ORDER BY changed_at ASC";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, queueId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    QueueStatusHistory h = new QueueStatusHistory();
                    h.setId(rs.getInt("id"));
                    h.setQueueId(rs.getInt("queue_id"));
                    h.setOldStatus(rs.getString("old_status"));
                    h.setNewStatus(rs.getString("new_status"));

                    Timestamp ts = rs.getTimestamp("changed_at");
                    if (ts != null) {
                        h.setChangedAt(ts.toLocalDateTime());
                    }

                    list.add(h);
                }
            }

        } catch (SQLException e) {
            System.err.println("[QueueRepository] Error findHistoryByQueueId: "
                    + e.getMessage());
        }

        return list;
    }
    
    //menghitung jumlah antrean pada hari tsb berdasarkaan status
    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM queue_entries " +
                     "WHERE status = ? AND queue_date = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setDate(2, Date.valueOf(LocalDate.now()));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("[QueueRepository] Error countByStatus: "
                    + e.getMessage());
        }

        return 0;
    }
    
    private QueueEntry mapResultSet(ResultSet rs) throws SQLException {
        QueueEntry q = new QueueEntry();
        q.setId(rs.getInt("id"));
        q.setQueueNumber(rs.getString("queue_number"));
        q.setAccessCode(rs.getString("access_code"));
        q.setCustomerName(rs.getString("customer_name"));
        q.setPartySize(rs.getInt("party_size"));

        int tableId = rs.getInt("table_id");
        q.setTableId(rs.wasNull() ? null : tableId);

        q.setStatus(rs.getString("status"));

        Timestamp joinedAt = rs.getTimestamp("joined_at");
        if (joinedAt != null) q.setJoinedAt(joinedAt.toLocalDateTime());

        Timestamp calledAt = rs.getTimestamp("called_at");
        if (calledAt != null) q.setCalledAt(calledAt.toLocalDateTime());

        Date queueDate = rs.getDate("queue_date");
        if (queueDate != null) q.setQueueDate(queueDate.toLocalDate());

        return q;
    }
}
