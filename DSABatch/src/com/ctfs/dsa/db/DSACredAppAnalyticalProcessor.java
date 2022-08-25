package com.ctfs.dsa.db;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ctal.utility.log.Log;
import com.ctfs.dsa.connection.ConnectionUtil;

/**
 * 
 * Processor
 * 
 * @author vensr
 *
 */
public class DSACredAppAnalyticalProcessor 
{

	public void processData(String outputFileName) throws Exception 
	{

		Log.entry();
		PreparedStatement selectCredanaPS = null;
		ResultSet rs_credana = null;
		BufferedWriter bw = null;
		PreparedStatement updateCredanaPS = null;
		Connection con = null;
		
		String formSubmId = null;
		Timestamp extract_dt = null;
		String accId = null;
		String analyticsData = null;
		String application_dt = null;
		int count = 0;

		try 
		{
			con = ConnectionUtil.getOracleDBConnection();
			con.setAutoCommit(false);

			updateCredanaPS = con.prepareStatement(DataAccessConstants.UPDATE_CRED_APP_ANALYTICS_EXTRACTED_DATE);
			bw = new BufferedWriter(new FileWriter(outputFileName));
			
			Log.info("Writing CRED APP ANALYTICS Data into output file");
			
			selectCredanaPS = con.prepareStatement(DataAccessConstants.SELECT_CRED_APP_ANALYTICS);
			rs_credana = selectCredanaPS.executeQuery();

			while (rs_credana.next()) 
			{
				formSubmId = rs_credana.getString("FORM_SUBM_ID");
//				credAppPreparedStatement = con.prepareStatement(DataAccessConstants.SELECT_CRED_APP);
//				credAppPreparedStatement.setString(1, formSubId);
//				credAppRS = credAppPreparedStatement.executeQuery();
//				
//				while (credAppRS.next()) {
//					accId = credAppRS.getString(1);
//					Log.info((new StringBuilder("accId---")).append(accId).toString());
//				}
				accId = rs_credana.getString("TSYS_APPLID_TXT");
				extract_dt = new Timestamp(System.currentTimeMillis());
				analyticsData = rs_credana.getString("ANALYTICS_DATA");
				application_dt = rs_credana.getString("INSRT_TMST");

				if (accId != null) 
				{
					bw.write(accId);
				} 
				else 
				{
					bw.write("");
				}
				bw.write("|");

				if (formSubmId != null) 
				{
					bw.write(formSubmId);
				} 
				else 
				{
					bw.write("");
				}
				bw.write("|");
				bw.write(extract_dt.toString());
				bw.write("|");
				if (application_dt != null) 
				{
					bw.write(application_dt);
				} 
				else 
				{
					bw.write("");
				}
				bw.write("|");
				
				if (analyticsData != null) 
				{
					bw.write(analyticsData.replaceAll("\"", "\\\\\""));
				} 
				else 
				{
					bw.write("");
				}
				bw.write("\n");

				updateCredanaPS.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				updateCredanaPS.setString(2, formSubmId);
				updateCredanaPS.executeUpdate();
				con.commit();
				count++;
			}
			extract_dt = new Timestamp(System.currentTimeMillis());
			bw.write((new StringBuilder("TLR|")).append(count).append("|").append(extract_dt.toString()).toString());
			con.commit();
			Log.info("A total of " +count+ " records written into output file");
		} 
		catch (SQLException sqle) 
		{
			Log.error("SQL Exception while trying to extract data from DB", sqle);
			throw sqle;
		} 
		catch (IOException ioe) 
		{
			Log.error("IOException while writing data into the output file", ioe);
			throw ioe;
		} 
		catch (Exception e) 
		{
			Log.error("General Exception while writing extracting user profile data", e);
			throw e;
		} 
		finally 
		{
			if ((rs_credana != null)) 
			{
				rs_credana.close();
			}
			if ((selectCredanaPS != null)) 
			{
				selectCredanaPS.close();
			}
			if ((updateCredanaPS != null)) 
			{
				updateCredanaPS.close();
			}
			if (bw != null) 
			{
				bw.close();
			}
			if (con != null) 
			{
				con.close();
			}
		}
		Log.exit();
	}
}
