package com.ctfs.dsa.batch;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.ctal.utility.log.Log;
import com.ctfs.dsa.db.DSAKofaxTranProcessor;
import com.ctfs.utility.exception.DBFailureException;


/**
 * this job delets data from the Kofax table less than 3 days
 * @author srish
 *
 */
public class DSAKofaxTransBatch {
	
public static void main(String[] args) {
	Log.entry();
	try {
		DSAKofaxTranProcessor dsaKofaxTranProcessor = new DSAKofaxTranProcessor();
		dsaKofaxTranProcessor.processData();
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
