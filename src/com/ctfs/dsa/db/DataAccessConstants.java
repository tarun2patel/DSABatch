package com.ctfs.dsa.db;

/**
 * DataAccessConstants class has all the SQL queries of Dash Acxiom Batch jobs
 * 
 * @author alosw
 *
 */
public class DataAccessConstants {

	static final String PROCESS_SELFIE_SCAN_DATA = "SELECT ID,REQUEST_IDENTIFIER,CREATE_DATE,ACCOUNT_ID,FRAUD_SCORE,KFAX_EVENT_TYPE,KFAX_RESP FROM KFAX_TRANS "
			+ "WHERE KFAX_EVENT_TYPE = 'kofaxMobileIdFacialRecognition' AND EXTRACTED_DATE IS NULL";

	static final String PROCESS_ID_SCAN_DATA = "SELECT ID,REQUEST_IDENTIFIER,CREATE_DATE,ACCOUNT_ID,FRAUD_SCORE,KFAX_EVENT_TYPE,KFAX_RESP,KFAX_RES_STATUS FROM KFAX_TRANS "
			+ "WHERE KFAX_EVENT_TYPE = 'kofaxMobileIDCaptureSync' AND EXTRACTED_DATE IS NULL";

	static final String PROCESS_TMX_DATA = "SELECT ID,SESSION_ID,CREATE_TS,ACCOUNT_ID,CUSTOMER_ID,EVENT_TYPE,POLICY_NAME,FRAUD_SCORE,IP_ADDRESS,PHONE_NUMBER,TMX_RES FROM TMX_PROFILE "
			+ "WHERE EXPORT_TS IS NULL";

	static String UPDATE_KFAX_TRANS_EXTRACTED_DATE = "UPDATE KFAX_TRANS SET EXTRACTED_DATE=? WHERE ID=?";

	static String UPDATE_TMXDB_EXTRACTED_DATE = "UPDATE TMX_PROFILE SET EXPORT_TS=? WHERE ID=?";

	static String DELETE_KOFAX_TRANS = "DELETE FROM KFAX_TRANS WHERE CREATE_DATE < (SYSDATE - 14)";

	static String DELETE_TMX_TRANS = "DELETE FROM TMX_PROFILE WHERE CREATE_TS <  (SYSDATE - 14)";

	static String DELETE_CUSTOMER_TRANSACTION = "DELETE FROM CUSTOMERTRANSACTION WHERE TRANSACTIONDATE <  (SYSDATE - 1)";

	static String DELETE_INSTOR_TOKEN = "DELETE FROM INSTOR_TOKEN WHERE CREATED_AT <  (SYSDATE - 3)";

	static String SELECT_CRED_APP_ANALYTICS = "SELECT CA.TSYS_APPLID_TXT AS TSYS_APPLID_TXT, CREDANA.FORM_SUBM_ID AS FORM_SUBM_ID, "
			+ " CREDANA.ANALYTICS_DATA AS ANALYTICS_DATA, CREDANA.EXPORT_TS AS EXPORT_TS,FS.INSRT_TMST AS INSRT_TMST"
			+ " FROM INST_CR.FORM_SUBM FS,CRED_APP_ANALYTICS CREDANA, CRED_APP CA "
			+ " WHERE FS.FORM_SUBM_ID=CREDANA.FORM_SUBM_ID AND FS.FORM_SUBM_ID=CA.FORM_SUBM_ID AND CREDANA.EXPORT_TS IS NULL";

	//static String SELECT_CRED_APP = "SELECT TSYS_APPLID_TXT from CRED_APP WHERE FORM_SUBM_ID=?";

	static String UPDATE_CRED_APP_ANALYTICS_EXTRACTED_DATE = "UPDATE CRED_APP_ANALYTICS SET EXPORT_TS=? WHERE FORM_SUBM_ID=?";

}
