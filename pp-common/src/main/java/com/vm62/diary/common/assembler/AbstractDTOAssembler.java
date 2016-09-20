package com.vm62.diary.common.assembler;

import java.io.Serializable;
import java.util.*;

/**
 * 
 * This class is a parent for all dto assemblers.
 * DTO assembler converts entity (or view) classes into DTOs.
 * 
 * @param <E> entity or view type to be transformed into dto 
 * @param <D> result dto type 
 * 
 */
public abstract class AbstractDTOAssembler<E, D> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Converts entity into dto
	 * @param entity
	 * @return
	 */
	public abstract D mapEntityToDTO(E entity);

	/**
	 * Converts a list of entities into dtos, using {@link AbstractDTOAssembler#mapEntityToDTO(Object)} method
	 * @param entities
	 * @return
	 */
	public <ET extends E> List<D> mapEntitiesToDTOs(Collection<ET> entities) {
		if (entities == null) {
			return null;
		}
		List<D> dtos = new ArrayList<D>();
		for (E entity : entities) {
			dtos.add(mapEntityToDTO(entity));
		}
		return dtos;
	}
	
	/**
	 * Converts a collection of entities into set of dtos, using {@link AbstractDTOAssembler#mapEntityToDTO(Object)} method
	 * @param entities
	 * @return Set of DTO objects
	 */
	public <ET extends E> Set<D> mapEntitiesToDTOSet(Collection<ET> entities) {
		if (entities == null) {
			return null;
		}
		Set<D> dtos = new HashSet<D>();
		for (E entity : entities) {
			dtos.add(mapEntityToDTO(entity));
		}
		return dtos;
	}

    /**
     * Converts a map of entities with Long type of key and value is a list, using {@link AbstractDTOAssembler#mapEntityToDTO(Object)} method
     * @param entities
     * @param <ET>
     * @return
     */
    public <ET extends E> Map<Long, List<D>> mapEntitiesListToDTOMap(Map<Long, List<ET>> entities) {
        if (entities == null) {
            return null;
        }
        Map<Long, List<D>> dtos = new HashMap<>();
        for(Map.Entry<Long, List<ET>> entry : entities.entrySet()) {
            dtos.put(entry.getKey(), mapEntitiesToDTOs(entry.getValue()));
        }
        return dtos;
    }
}