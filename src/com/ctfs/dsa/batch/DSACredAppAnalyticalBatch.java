package com.ctfs.dsa.batch;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;
import com.ctal.utility.log.Log;
import com.ctfs.dsa.db.DSACredAppAnalyticalProcessor;
import com.ctfs.utility.exception.DBFailureException;

public class DSACredAppAnalyticalBatch {

	private static Logger log = Logger.getLogger(DSACredAppAnalyticalBatch.class.getName());

	public static void main(String[] args) {
		log.info("Entering insde the DSAInstoreTokenBatch main method");
		try {
			if (args.length < 1) {
				System.exit(-1);
			}
			
			String outputFileName = args[0];
			System.out.println("Output filename is " + outputFileName);
			DSACredAppAnalyticalProcessor processor = new DSACredAppAnalyticalProcessor();
			processor.processData(outputFileName);
		} catch (SQLException sql) {
			Log.error("SQL Exception while trying to extract data from DB", sql);
			System.exit(1);
		} catch (DBFailureException sqlexe) {
			Log.error("DB Failure while processing data", sqlexe);
			System.exit(2);
		} catch (IOException ioe) {
			Log.error("IOException while writing data into the output file", ioe);
			System.exit(3);
		} catch (Exception e) {
			Log.error("General exception occurred", e);
			System.exit(4);
		}
		log.info("Exiting from DSACredAppAnalyticalBatch main method");
		System.exit(0);
	}
}
