package com.ctfs.dsa.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.ctal.utility.log.Log;
import com.ctfs.dsa.batch.DSACustomerTransactionBatch;
import com.ctfs.dsa.connection.ConnectionUtil;

/**
 * 
 * Processor
 * 
 * @author bhada
 *
 */
public class DSACustomerTransactionProcessor {

	private static Logger log = Logger.getLogger(DSACustomerTransactionBatch.class.getName());

	public void processData() throws Exception {
		log.info("Entering inside the DSACustomerTransactionProcessor processData method");
		try (Connection con = ConnectionUtil.getWICIDBConnection();
				PreparedStatement deleteps = con.prepareStatement(DataAccessConstants.DELETE_CUSTOMER_TRANSACTION);) {
			deleteps.execute();
			con.commit();
		} catch (SQLException ex) {
			ex.printStackTrace();
			Log.error("SQL Exception while trying to clean CUSTOMERTRANSACTION data from DB", ex);
			throw ex;
		} catch (Exception ex) {
			throw ex;
		}
		log.info("Exiting from DSACustomerTransactionProcessor processData method");

	}
}
