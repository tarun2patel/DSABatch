package com.ctfs.dsa.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.ctal.utility.log.Log;
import com.ctfs.dsa.connection.ConnectionUtil;

/**
 * 
 * Processor
 * 
 * @author leipr
 *
 */
public class DSAInstoreTokenProcessor {

	private static Logger log = Logger.getLogger(DSAInstoreTokenProcessor.class.getName());

	public void processData() throws Exception {
		log.info("Entering inside the DSAInstoreTokenProcessor processData method");
		try (Connection con = ConnectionUtil.getOracleDBConnection();
				PreparedStatement deleteps = con.prepareStatement(DataAccessConstants.DELETE_INSTOR_TOKEN);) {
			deleteps.execute();
			con.commit();
		} catch (SQLException ex) {
			ex.printStackTrace();
			Log.error("SQL Exception while trying to clean INSTOR_TOKEN data from DB", ex);
			throw ex;
		} catch (Exception ex) {
			throw ex;
		}
		log.info("Exiting from DSAInstoreTokenProcessor processData method");

	}
}
