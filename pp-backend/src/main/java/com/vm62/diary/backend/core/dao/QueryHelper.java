package com.vm62.diary.backend.core.dao;




import com.vm62.diary.common.utils.NumberUtils;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class   QueryHelper {

	/**
	 * Maximum number of elements that can be used with IN operator.
	 * If elements number exceeds this value they should be broken in portions.
     *
     * There is not restriction for number of elements in "IN" statement in MySQL database.
	 */
	public static final int MAX_IN_ELEMENTS = 100;
	
	/**
	 * Limits given query to return necessary number of result rows according to the given limitations.
	 * @param query query to be limited.
	 * @param maxRows maximum number of returned rows.
	 * @param startRow number of the row in the whole result to start with. 
	 */
	public static <T extends Query> void limitQueryResults( T query, Integer maxRows, Integer startRow) {
		Integer checkedMaxRows = NumberUtils.toNullOrPositiveInteger(maxRows);
		Integer checkedStartRow = NumberUtils.toNullOrPositiveInteger(startRow);
		if (checkedMaxRows != null) {
			query.setMaxResults(checkedMaxRows);
		}
		if (checkedStartRow != null) {
			query.setFirstResult(checkedStartRow);
		}
	}
	
	/**
	 * Fills parameters in a given query with values provided in criteria map.
	 * @param <T>
	 * @param query query to be filled with parameter values. 
	 * @param criteria a map of following structure: 
	 * 		  key (String) - parameter name, value(Object) - parameter value.
	 */
	public static <T extends Query> void fillQueryParameters( T query,  Map<String, Object> criteria) {
		for ( Map.Entry<String, Object> criterion : criteria.entrySet()) {
			query.setParameter(criterion.getKey(), criterion.getValue());
		}
	}
	
	/**
	 * Executes given typed query in parts to prevent DB from throwing an exception if the size
	 * of the IN parameter is larger than {@link #MAX_IN_ELEMENTS}.
	 * <br>
	 * <b>Note:</b> method provides partial execution by only one IN parameter (but query can contain other IN operators and parameters).  
	 *
	 * @param <R> result type of typed query.
	 * @param <V> type of IN parameter values.
	 * @param query the query that must be executed in parts.
	 * @param inCriterionName the name of parameter following the IN operator.
	 * @param inValues collection of values for IN operator. Must not be null or empty.
	 * @param criteria the map of criteria with values that must be applied to the query (excluding collection of values for IN operator).
	 * @param maxRows maximum number of rows to be selected.
	 * @param startRow row number to start with.
	 */
	public static <R, V> List<R> executeTypedQueryInParts(
			 TypedQuery<R> query, String inCriterionName,  List<V> inValues,  Map<String, Object> criteria,
			Integer maxRows, Integer startRow) {

		 List<R> resultList = new ArrayList<R>();

		int totalSize = inValues.size();
		int fromIndex = 0;
		int toIndex = Math.min(MAX_IN_ELEMENTS, totalSize);
		
		while (fromIndex < inValues.size()) {
			 List<V> inValuesPartial = inValues.subList(fromIndex, toIndex);
			fromIndex = toIndex;
			toIndex = Math.min(toIndex + MAX_IN_ELEMENTS, totalSize);

			criteria.put(inCriterionName, inValuesPartial);
			fillQueryParameters(query, criteria);
			limitQueryResults(query, maxRows, startRow);

			List<R> resultListPartial = query.getResultList();

			resultList.addAll(resultListPartial);
		}

		return resultList;
	}

	/**
	 * Executes given query in parts to prevent DB from throwing an exception if the size
	 * of the IN parameter is larger than {@link #MAX_IN_ELEMENTS}.
	 * <br>
	 * <b>Note:</b> method provides partial execution by only one IN parameter (but query can contain other IN operators and parameters).  
	 *
	 * @param <V> type of IN parameter values.
	 * @param <R> type of return values.
	 * @param query the query that must be executed in parts.
	 * @param inCriterionName the name of parameter following the IN operator.
	 * @param inValues collection of values for IN operator. Must not be null or empty.
	 * @param criteria the map of criteria with values that must be applied to the query (excluding collection of values for IN operator).
	 * @param maxRows maximum number of rows to be selected.
	 * @param startRow row number to start with.
	 */
	@SuppressWarnings("unchecked")
	public static <R, V> List<R> executeQueryInParts(
			 Query query, String inCriterionName,  List<V> inValues,  Map<String, Object> criteria,
			Integer maxRows, Integer startRow) {
		
		int totalSize = inValues.size();
		int fromIndex = 0;
		int toIndex = Math.min(MAX_IN_ELEMENTS, totalSize);
		
		 List<R> resultList = new ArrayList<R>();
		
		while (fromIndex < inValues.size()) {
			 List<V> inValuesPartial = inValues.subList(fromIndex, toIndex);
			fromIndex = toIndex;
			toIndex = Math.min(toIndex + MAX_IN_ELEMENTS, totalSize);
			
			criteria.put(inCriterionName, inValuesPartial);
			fillQueryParameters(query, criteria);
			limitQueryResults(query, maxRows, startRow);
			
			List<R> resultListPartial = query.getResultList();
			resultList.addAll(resultListPartial);
		}
		
		return resultList;
	}
	
	/**
	 * Executes count query in parts to prevent DB from throwing an exception if the size
	 * of the IN parameter is larger than {@link #MAX_IN_ELEMENTS}.
	 * <br>
	 * <b>Note:</b> method provides partial execution by only one IN parameter (but query can contain other IN operators and parameters).
	 *
	 * @param <V> type of IN parameter values.
	 * @param query the query that must be executed in parts.
	 * @param inCriterionName the name of parameter following the IN operator.
	 * @param inValues collection of values for IN operator. Must not be null or empty.
	 * @param criteria the map of criteria with values that must be applied to the query (excluding collection of values for IN operator).
	 * @return summarized result of all executed query parts.
	 */
	public static <V> long executeCountTypedQueryInParts( TypedQuery<Long> query,
	                                                     String inCriterionName,  List<V> inValues,  Map<String, Object> criteria) {
		int totalSize = inValues.size();
		int fromIndex = 0;
		int toIndex = Math.min(MAX_IN_ELEMENTS, totalSize);
		long result = 0;
		while (fromIndex < inValues.size()) {
			 List<V> inValuesPartial = inValues.subList(fromIndex, toIndex);
			fromIndex = toIndex;
			toIndex = Math.min(toIndex + MAX_IN_ELEMENTS, totalSize);

			criteria.put(inCriterionName, inValuesPartial);

			QueryHelper.fillQueryParameters(query, criteria);

			Long resultPartial = query.getSingleResult();
			result += resultPartial;
		}
		return result;
	}
	
	/**
	 * Helps to return single <code>T</code> value or null if no result returned from database.
	 * <br>This method can be used to prevent no data found exception.
	 * 
	 * <br><b>IMPORTANT: </b>In case when result contains more than one records this method returns first one.
     * @param query the query implies single or no result. 
     * @return
     */
    public static <T> T getSingleValueOrNull( TypedQuery<T> query) {
        List<T> results = query.getResultList();
        if (results.isEmpty()){
            return null;
        } else {
            return results.get(0);
        }
    }

	/**
	 * Helps to return single value or null if no result returned from database.
	 * Return value is casted to given type <T>.
	 * <br>This method can be used to prevent no data found exception.
	 *
	 * <br><b>IMPORTANT: </b>In case when result contains more than one records this method returns first one.
	 * @param query the query implies single or no result.
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> T getSingleValueOrNull( Query query) {
		List results = query.getResultList();
		if (results.isEmpty()){
			return null;
		} else {
			return (T)results.get(0);
		}
	}
}
