package kr.co.tworld.shop.framework.model;

/**
 * Validation Markers interface for @Validated annotation
 * @author Sangjun, Park
 *
 */
public interface ValidationMarkers {
	/** Validation Marker for Create-request */
	interface Create {}
	/** Validation Marker for Retrieve-request */
	interface Retrieve {}
	/** Validation Marker for Update-request */
	interface Update {}
	/** Validation Marker for Delete-request */
	interface Delete {}
}
