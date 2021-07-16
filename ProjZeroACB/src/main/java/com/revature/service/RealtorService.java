package com.revature.service;

import java.util.List;

import com.revature.dao.RealtorRepository;
import com.revature.dto.PostRealtorDTO;
import com.revature.exception.AddRealtorException;
import com.revature.exception.BadParameterException;
import com.revature.exception.DatabaseException;
import com.revature.exception.RealtorNotFoundException;
import com.revature.model.Realtor;

public class RealtorService {
	
	private RealtorRepository realtorRepository;
	
	public RealtorService() {
		super();
		this.realtorRepository = new RealtorRepository();
	}
	
	public RealtorService(RealtorRepository realtorRepository) {
		this.realtorRepository = realtorRepository;
	}
	
	public Realtor addRealtor(PostRealtorDTO realtorDTO) throws DatabaseException, AddRealtorException {
		if (realtorRepository.getRealtorByName(realtorDTO.getFirstName(), realtorDTO.getLastName()) != null) {
			throw new AddRealtorException("User tried to add a realtor that already exists with that name");
		}
		
		if (realtorDTO.getFirstName().trim().equals("") || realtorDTO.getLastName().trim().equals("")) {
			throw new AddRealtorException("User tried to add a realtor with a blank name");
		}
		
		return realtorRepository.addRealtor(realtorDTO);
	}
	
	public List<Realtor> getRealtors() throws RealtorNotFoundException, DatabaseException {
			List<Realtor> realtors = realtorRepository.getRealtors();
			
			if (realtors == null) {
				throw new RealtorNotFoundException("There are no realtors in the database");
			}
			
			return realtorRepository.getRealtors();
	}
	
	public Realtor getRealtorById(String stringId) throws DatabaseException, BadParameterException, RealtorNotFoundException {
		try {
			int id = Integer.parseInt(stringId);
			
			Realtor realtor = realtorRepository.getRealtorById(id);
			
			if (realtor == null) {
				throw new RealtorNotFoundException("Realtor with id of " + id + " was not found");
			}
			
			return realtor;
		} catch (NumberFormatException e) {
			throw new BadParameterException("Realtor id must be an int. User provided " + stringId);
		}
	
	}
	
	public Realtor updateRealtor(String stringId, PostRealtorDTO realtorDTO) throws DatabaseException, BadParameterException, RealtorNotFoundException {
		try {
			int id = Integer.parseInt(stringId);
			
			Realtor realtor = realtorRepository.updateRealtor(id, realtorDTO);
			
			if (realtor == null) {
				throw new RealtorNotFoundException("Realtor with id of " + id + " was not found");
			}
			
			return realtor;
		} catch (NumberFormatException e) {
			throw new BadParameterException("Realtor id must be an int. User provided " + stringId);
		}
	
	}
	
	public void deleteRealtor(String stringId) throws DatabaseException, BadParameterException, RealtorNotFoundException {
		try {
			int id = Integer.parseInt(stringId);
			
			realtorRepository.deleteRealtor(id);
			
			if (stringId == null) {
				throw new RealtorNotFoundException("Realtor with id of " + id + " was not found");
			}

		} catch (NumberFormatException e) {
			throw new BadParameterException("Realtor id must be an int. User provided " + stringId);
		}
	
	}
	
}
