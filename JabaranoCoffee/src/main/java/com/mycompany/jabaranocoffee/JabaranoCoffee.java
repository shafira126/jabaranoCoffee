/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.jabaranocoffee;

import com.mycompany.jabaranocoffee.view.FormCustomer;
import com.mycompany.jabaranocoffee.view.FormDisplay;
import com.mycompany.jabaranocoffee.view.FormLogin;
/**
 *
 * @author kayla
 */
public class JabaranoCoffee {

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(
                JabaranoCoffee.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            // Buka semua form sekaligus
            new FormLogin().setVisible(true);       // staff login
            new FormDisplay().setVisible(true);     // layar publik
        });
    }
}