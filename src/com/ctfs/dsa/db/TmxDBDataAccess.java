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

public class TmxDBDataAccess {

	/**
	 * 
	 * @param con
	 * @param outputFileName
	 * @return
	 * @throws Exception
	 */
	public void processScanData(String outputFileName) throws Exception {
		Log.entry();
		PreparedStatement tmxProfilePreparedStatement = null;
		ResultSet rs = null;
		BufferedWriter bw = null;
		PreparedStatement updateTmxProfilePreparedStatement = null;
		Connection con= null;
		String sessionId = null;
		String createDate = null;
		String accountId = null;
		String customerId = null;
		String eventType = null;
		String policyName = null;
		String fraudScore = null;
		String ipAddress = null;
		String phoneNumber = null;
		String tmxRes = null;
		String id= null;
		int batchSize = 0;
		int count = 0;
		
		try {
			
			if(ApplicationContext.getProperty("batchSize") != null){
				
				batchSize = Integer.parseInt(ApplicationContext.getProperty("batchSize"));
				Log.info("batch size" + batchSize);
			}
			else{
				batchSize = 1000;
			}

			con = ConnectionUtil.getOracleDBConnection();
			con.setAutoCommit(false);
			
			tmxProfilePreparedStatement = con.prepareStatement(DataAccessConstants.PROCESS_TMX_DATA);
			rs = tmxProfilePreparedStatement.executeQuery();
			updateTmxProfilePreparedStatement = con.prepareStatement(DataAccessConstants.UPDATE_TMXDB_EXTRACTED_DATE);
			bw = new BufferedWriter(new FileWriter(outputFileName));
			Log.info("Writing TMX Data into output file");
			
			while (rs.next()) {
				sessionId = rs.getString("SESSION_ID");
				createDate = rs.getString("CREATE_TS");
				accountId = rs.getString("ACCOUNT_ID");
				customerId = rs.getString("CUSTOMER_ID");
				eventType = rs.getString("EVENT_TYPE");
				policyName = rs.getString("POLICY_NAME");
				fraudScore = rs.getString("FRAUD_SCORE");
				ipAddress = rs.getString("IP_ADDRESS");
				phoneNumber = rs.getString("PHONE_NUMBER");
				tmxRes = rs.getString("TMX_RES");
				id = rs.getString("ID");
				
				//DSS-2112
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
				if (customerId != null) {
					bw.write(customerId);
				} else {
					bw.write("");
				}
				bw.write("|");
				if (eventType != null) {
					bw.write(eventType);
				} else {
					bw.write("");
				}
				bw.write("|");
				if (policyName != null) {
					bw.write(policyName);
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
				if (ipAddress != null) {
					bw.write(ipAddress);
				} else {
					bw.write("");
				}
				bw.write("|");
				if (phoneNumber != null) {
					bw.write(phoneNumber);
				} else {
					bw.write("");
				}
				bw.write("|");
				if (tmxRes != null) {
					bw.write(tmxRes);
				} else {
					bw.write("");
				}
				bw.write("\n");
				
				updateTmxProfilePreparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				updateTmxProfilePreparedStatement.setString(2, id);
				updateTmxProfilePreparedStatement.addBatch();
				count++;
				
				if(count == batchSize){
				   Log.info("Batch size reached");	
				   updateTmxProfilePreparedStatement.executeBatch();
				   con.commit();
				   count = 0;
				}
			}
			
			if(count > 0){
				updateTmxProfilePreparedStatement.executeBatch();
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

			if ((tmxProfilePreparedStatement != null)) {
				tmxProfilePreparedStatement.close();
			}
			
			if ((updateTmxProfilePreparedStatement != null)) {
				updateTmxProfilePreparedStatement.close();
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
