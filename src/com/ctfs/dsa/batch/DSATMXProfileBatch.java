package com.ctfs.dsa.batch;

import java.io.IOException;
import java.sql.SQLException;

import com.ctal.utility.log.Log;
import com.ctfs.dsa.db.DSATMXProfileProcessor;
import com.ctfs.utility.exception.DBFailureException;


/**
 * this batch delets the data from TMX table older than 3 days
 * @author srish
 *
 */
public class DSATMXProfileBatch {

	public static void main(String[] args) {
		Log.entry();
		try {
			DSATMXProfileProcessor dsatmxProfileProcessor = new DSATMXProfileProcessor();
			dsatmxProfileProcessor.processData();
		} catch (SQLException sqle) {
			System.exit(1);
		} catch (DBFailureException dbfe) {
			Log.error("DB Failure Exception while trying to open connection to DB", dbfe);
			System.exit(2);
		} catch (IOException ioe) {
			Log.error("IOException occurred", ioe);
			System.exit(3);
		} catch (Exception e) {
			Log.error("General exception occurred", e);
			System.exit(4);
		} 
		
		Log.exit();
	}
}
