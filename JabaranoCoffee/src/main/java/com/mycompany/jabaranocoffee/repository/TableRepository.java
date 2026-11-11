package com.mycompany.jabaranocoffee.repository;

import com.mycompany.jabaranocoffee.entity.CafeTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Mengambil data meja, kapasitas, dan ketersediaannya.
 *
 * @author rangga
 */
public class TableRepository {

    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------
    public TableRepository() {}

    // ----------------------------------------------------------------
    // Input & Proses: query ke database
    // ----------------------------------------------------------------

    /**
     * Mengambil semua data meja.
     */
    public List<CafeTable> findAll() {
        List<CafeTable> tables = new ArrayList<>();
        String sql = "SELECT id, table_number, capacity, status FROM cafe_tables ORDER BY table_number";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tables.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("[TableRepository] Error findAll: " + e.getMessage());
        }

        return tables;
    }

    /**
     * Mengambil semua meja yang berstatus AVAILABLE.
     */
    public List<CafeTable> findAvailable() {
        List<CafeTable> tables = new ArrayList<>();
        String sql = "SELECT id, table_number, capacity, status " +
                     "FROM cafe_tables WHERE status = 'AVAILABLE' ORDER BY capacity";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tables.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("[TableRepository] Error findAvailable: " + e.getMessage());
        }

        return tables;
    }

    /**
     * Mengambil meja yang tersedia dan kapasitasnya cukup untuk jumlah tamu.
     *
     * @param partySize jumlah tamu
     */
    public List<CafeTable> findAvailableByCapacity(int partySize) {
        List<CafeTable> tables = new ArrayList<>();
        String sql = "SELECT id, table_number, capacity, status " +
                     "FROM cafe_tables " +
                     "WHERE status = 'AVAILABLE' AND capacity >= ? " +
                     "ORDER BY capacity";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, partySize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tables.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[TableRepository] Error findAvailableByCapacity: " + e.getMessage());
        }

        return tables;
    }

    /**
     * Menghitung jumlah meja yang berstatus AVAILABLE.
     */
    public int countAvailable() {
        String sql = "SELECT COUNT(*) FROM cafe_tables WHERE status = 'AVAILABLE'";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("[TableRepository] Error countAvailable: " + e.getMessage());
        }

        return 0;
    }

    // ----------------------------------------------------------------
    // Helper: mapping ResultSet -> CafeTable
    // ----------------------------------------------------------------
    private CafeTable mapResultSet(ResultSet rs) throws SQLException {
        CafeTable table = new CafeTable();
        table.setId(rs.getInt("id"));
        table.setTableNumber(rs.getString("table_number"));
        table.setCapacity(rs.getInt("capacity"));
        table.setStatus(rs.getString("status"));
        return table;
    }
}
