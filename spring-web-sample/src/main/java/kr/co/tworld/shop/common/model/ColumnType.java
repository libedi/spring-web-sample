package kr.co.tworld.shop.common.model;

/**
 * ColumnType
 * @author Sangjun, Park
 *
 */
public enum ColumnType {

	/** Column type : String */
	STRING
	/** Column type : Integer */
	, INTEGER
	/** Column type : Double */
	, DOUBLE
	/** Column type : Date (yyyy-MM-dd) */
	, DATE
	/** Column type : Date (yyyy-MM-dd HH:mm) */
	, DATEHHMM
	/** Column type : Date (yyyy-MM-dd HH:mm:ss) */
	, DATETIME
	/** Column type : Header */
	, HEADER
	;
}
