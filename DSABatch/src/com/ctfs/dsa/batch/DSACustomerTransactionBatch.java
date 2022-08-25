package com.ctfs.dsa.batch;

import java.sql.SQLException;
import java.util.logging.Logger;

import com.ctal.utility.log.Log;
import com.ctfs.dsa.db.DSACustomerTransactionProcessor;
import com.ctfs.utility.exception.DBFailureException;


/**
 * 
 * Batch job invoked to delete data from customertransaction and insert into customertransaction_hist table
 * @author goeam
 *
 */
public class DSACustomerTransactionBatch {
	
	private static Logger log = Logger.getLogger(DSACustomerTransactionBatch.class.getName());
	
	public static void main(String[] args) {
		log.info("Entering insde the DSACustomerTransactionBatch main method");
		try {
			DSACustomerTransactionProcessor processor = new DSACustomerTransactionProcessor();
			processor.processData();
		} catch (SQLException | DBFailureException sqlexe) {
			Log.error("DB Failure while processing data", sqlexe);
			System.exit(1);
		} catch (Exception e) {
			Log.error("General exception occurred", e);
			System.exit(2);
		} 
		
		log.info("Exiting from DSACustomerTransactionBatch main method");
	}
}
