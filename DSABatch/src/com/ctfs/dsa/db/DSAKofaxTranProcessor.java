package com.ctfs.dsa.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ctal.utility.log.Log;
import com.ctfs.dsa.connection.ConnectionUtil;

public class DSAKofaxTranProcessor {
	
	/**
	 * 
	 * @throws Exception
	 */
	public void processData() throws Exception {
		Log.entry();
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ConnectionUtil.getOracleDBConnection();
			con.setAutoCommit(false);
			
			ps = con.prepareStatement(DataAccessConstants.DELETE_KOFAX_TRANS);
			ps.execute();
			con.commit();
		} catch (SQLException sqle) {
			Log.error("SQL Exception while trying to extract data from DB", sqle);
			throw sqle;
		}  catch (Exception e) {
			throw e;
		} finally {

			if ((ps != null)) {
				ps.close();
			}
			
			if(con != null){
				con.close();
			}

		}
		Log.exit();
		
	}

}
