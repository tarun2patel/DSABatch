package com.ctfs.dsa.db;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ctal.utility.app.context.ApplicationContext;
import com.ctal.utility.log.Log;
import com.ctfs.dsa.connection.ConnectionUtil;

public class KofaxSelfieScanDataAccess {

	
	/**
	 * 
	 * @param con
	 * @param outputFileName
	 * @return
	 * @throws Exception
	 */
	public void processScanData(String outputFileName) throws Exception {
		Log.entry();
		
		PreparedStatement selfieScanPreparedStatement = null;
		ResultSet rs = null;
		BufferedWriter bw = null;
		PreparedStatement updateSelfieScanPreparedStatement = null;
		Connection con= null;
		
		try {
			con = ConnectionUtil.getOracleDBConnection();
			con.setAutoCommit(false);
			
			String sessionId = null;
			String createDate = null;
			String accountId = null;
			String fraudScore = null;
			String kfaxEventType =  null;
			String kfaxResp = null;
			String id = null;
			int batchSize = 0;
			int count = 0;
			
			if(ApplicationContext.getProperty("batchSize") != null){
				
				batchSize = Integer.parseInt(ApplicationContext.getProperty("batchSize"));
			}
			else{
				batchSize = 1000;
			}
			selfieScanPreparedStatement = con.prepareStatement(DataAccessConstants.PROCESS_SELFIE_SCAN_DATA);
			rs = selfieScanPreparedStatement.executeQuery();
			
			updateSelfieScanPreparedStatement = con.prepareStatement(DataAccessConstants.UPDATE_KFAX_TRANS_EXTRACTED_DATE);
			
			bw = new BufferedWriter(new FileWriter(outputFileName));
			Log.info("Writing Selfie Data into output file");
			while (rs.next()) {

				sessionId = rs.getString("REQUEST_IDENTIFIER");
				createDate = rs.getString("CREATE_DATE");
				accountId = rs.getString("ACCOUNT_ID");
				fraudScore = rs.getString("FRAUD_SCORE");
				kfaxEventType = rs.getString("KFAX_EVENT_TYPE");
				kfaxResp = rs.getString("KFAX_RESP");
				id = rs.getString("ID");

				if (id != null) {
					bw.write(id);
				} else {
					bw.write("");
				}
				bw.write("|");
				if (sessionId != null) {
					bw.write(sessionId);
				} else {
					bw.write("");
				}
				bw.write("|");
				if (createDate != null) {
					bw.write(createDate);
				} else {
					bw.write("");
				}
				bw.write("|");
				if (accountId != null) {
					bw.write(accountId);
				} else {
					bw.write("");
				}
				bw.write("|");
				if (fraudScore != null) {
					bw.write(fraudScore);
				} else {
					bw.write("");
				}
				bw.write("|");
				if (kfaxEventType != null) {
					bw.write(kfaxEventType);
				} else {
					bw.write("");
				}
				bw.write("|");
				if (kfaxResp != null) {
					bw.write(kfaxResp);
				} else {
					bw.write("");
				}
				bw.write("\n");
				
				updateSelfieScanPreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				updateSelfieScanPreparedStatement.setString(2, id);
				updateSelfieScanPreparedStatement.addBatch();
				count++;
				if(count == batchSize){
					Log.info("Batch size reached");	
				   updateSelfieScanPreparedStatement.executeBatch();
				   con.commit();
				   count = 0;
				}
				
			}
			if(count > 0){
				updateSelfieScanPreparedStatement.executeBatch();
				con.commit();
			}
			
		} catch (SQLException sqle) {
			Log.error("SQL Exception while trying to extract data from DB", sqle);
			throw sqle;
		} catch (IOException ioe) {
			Log.error("IOException while writing data into the output file", ioe);
			throw ioe;
		} catch (Exception e) {
			Log.error("General Exception while writing extracting user profile data", e);
			throw e;
		} finally {
			
			if ((rs != null)) {
				rs.close();
			}

			if ((selfieScanPreparedStatement != null)) {
				selfieScanPreparedStatement.close();
			}
			
			if ((updateSelfieScanPreparedStatement != null)) {
				updateSelfieScanPreparedStatement.close();
			}
			
			if (bw != null) {
				bw.close();
			}
			
			if(con != null){
				con.close();
			}

		}
		Log.exit();
	}
}
