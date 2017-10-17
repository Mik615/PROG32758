/*

 * ----------------------------------------------------------------------------+
 * Group Leader: Daniel Hope
 * Member(s): Georgina Luce
 *            Nathaniel Primo
 *            Michael Marc
 * Group #: 1
 * Filename: CreateDataBAse.java
 * Other Files in this Project:
 *     -
 * Assignment:
 * Creation Date: 10, 2017 16
 * Last Modified: 10, 2017 16
 * Java Version:
 * Description: The representation of a Car object
 * ----------------------------------------------------------------------------+
 */

package com.util;

import javax.swing.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;


public class CreateDataBase {

    // creates table called Players in DBProg32758

    public static void createTable(String user, String password) {

        Connection conn = null;

        Statement stm = null;


        // DB check
        try {

            //Check for DB
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", user, password);

            if (conn != null) {

                ResultSet rs = conn.getMetaData().getCatalogs();

                boolean doesExist = false;

                while (rs.next()) {

                    String catalog = rs.getString(1);

                    if (catalog.equalsIgnoreCase("DBProg32758")) {

                        doesExist = true;

                        break;
                    }
                }

                if (doesExist) {

                    // window that warns user that the database already exists

                    JOptionPane.showMessageDialog(null, "The database already exsists.", "Car Racing Game", javax.swing.JOptionPane.WARNING_MESSAGE);

                } else {

                    try {

                        String createDB = "CREATE DATABASE DBProg32758;";

                        stm = conn.createStatement();

                        stm.execute(createDB);

                        JOptionPane.showMessageDialog(null, "Database Successfully created...\n Click OK to continue.", "Car Racing Game", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {

                        if (ex.getSQLState().equals("HY000") && ex.getErrorCode() == 1007)

                            JOptionPane.showMessageDialog(null, "The database already exsists.", "Car Racing Game", javax.swing.JOptionPane.WARNING_MESSAGE);

                        else {

                            JOptionPane.showMessageDialog(null, ex.getMessage() + "SQL State: " + ex.getSQLState() + " ErrorCode: " + ex.getErrorCode(), "Car Racing Game", javax.swing.JOptionPane.WARNING_MESSAGE);
                        }

                    }

                }
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, ex.getMessage() + "SQL State: " + ex.getSQLState() + " ErrorCode: " + ex.getErrorCode(), "Car Racing Game", javax.swing.JOptionPane.WARNING_MESSAGE);

        }

        String createTblSql = "CREATE TABLE Players (`Last_Name` VARCHAR(20), `First_Name` VARCHAR(20), `Group` VARCHAR(20), `Login` VARCHAR(20), `Password` VARCHAR(20), `Preferred_Car_Name` VARCHAR(20), `Logo` VARCHAR(20), `Score` VARCHAR(20));";

        try {

            stm = conn.createStatement();

            stm.execute("USE DBProg32758;");

            stm.executeUpdate(createTblSql);

            JOptionPane.showMessageDialog(null, "Table Successfully created...\n Click OK to continue.", "Car Racing Game", javax.swing.JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {

            if (ex.getSQLState().equals("42S01") && ex.getErrorCode() == 1050)

                JOptionPane.showMessageDialog(null, "The table already exsists.", "Car Racing Game", javax.swing.JOptionPane.WARNING_MESSAGE);

            else {

                JOptionPane.showMessageDialog(null, ex.getMessage() + "SQL State: "
                        + ex.getSQLState() + " ErrorCode: "
                        + ex.getErrorCode(), "Car Racing Game", JOptionPane.WARNING_MESSAGE);
            }

        } finally {

            try {

                stm.close();

                conn.close();
            } catch (java.sql.SQLException e) {

                JOptionPane.showMessageDialog(null, e.getMessage() + "SQL State: " + e.getSQLState() + " ErrorCode: " + e.getErrorCode(), "Car Racing Game", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        }

    }

}