package com.ctfs.dsa.batch;

import java.io.IOException;
import java.sql.SQLException;

import com.ctal.utility.log.Log;
import com.ctfs.dsa.db.TmxDBDataAccess;
import com.ctfs.utility.exception.DBFailureException;

public class TmxDBExtract {

	public static void main(String[] args) {
		Log.entry();
		try {
			
			if(args.length < 1){
				System.exit(-1);
			}
			
			String outputFileName = args[0];
			System.out.println("Output filename is " + outputFileName);
			// Get DB connection
			TmxDBDataAccess tmxDBDataAccess = new TmxDBDataAccess();
			tmxDBDataAccess.processScanData(outputFileName);
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
		Log.info("TMX DB Data extracted successfully");
		Log.exit();
		System.exit(0);
	}
}
