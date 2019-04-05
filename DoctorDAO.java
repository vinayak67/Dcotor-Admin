package com.cts.dao;

import java.util.List;

import com.cts.entity.CheckupRequest;
import com.cts.entity.Doctor;


public interface DoctorDAO {
	
	void  saveDoctor(Doctor doctor);
	public Doctor getDoctor(String p);
	List<Doctor> doctorList();
	public List<CheckupRequest> getRequest(String p);
	public List<CheckupRequest> getAcceptedRequests(int id);
	public List<CheckupRequest> getReports(int id);
	public void deleteDoctor(int id);
	
}
