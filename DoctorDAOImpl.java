	package com.cts.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cts.entity.CheckupRequest;
import com.cts.entity.Doctor;
import com.cts.entity.Services;
import com.cts.entity.UserLogin;

@Repository("doctorDAO")
public class DoctorDAOImpl implements DoctorDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveDoctor(Doctor doctor) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(doctor);
		
	}


	@Override
	public Doctor getDoctor(String id) {
		Session session=sessionFactory.getCurrentSession();
		
//		
//		Criteria crit = session.createCriteria(Doctor.class);
//		crit.add(Restrictions.eq("Email_Id",id));
//		Doctor doctor= new Doctor();
		
//		List<Doctor> results = crit.list();
			
				//System.out.println(i+"````````");
//				System.out.println(results+" yes");
		
	UserLogin login=session.get(UserLogin.class,id);
	int i= login.getId();
	Doctor theDoctor =session.get(Doctor.class,i);
	System.out.println(theDoctor);
		return theDoctor;
		
	}


	@Override
	public List<CheckupRequest> getRequest(String p) {
		// TODO Auto-generated method stub
		
		Session session =sessionFactory.getCurrentSession();
//		Criteria criteria = session.createCriteria(Doctor.class);
//		Criterion nameCriterion = Restrictions.eq("City", "pune");
//		criteria.add(nameCriterion);
//		List books = criteria.list();
//		System.out.println(books);
		
		String sql="select doctor_table.*,checkup_request_tab.*\r\n" + 
				"  from doctor_table\r\n" + 
				"left outer\r\n" + 
				"  join checkup_request_tab\r\n" + 
				"    on doctor_table.Doctor_ID\r\n" + 
				"     = checkup_request_tab.Doctor_ID\r\n" + 
				" where checkup_request_tab.Status = 'pending' ";
		
//		String sql2= 
//				" from doctor_table \r\n" + 
//				"left outer\r\n" + 
//				"  join checkup_request_tab\r\n" + 
//				"    on doctor_table.Doctor_ID\r\n" + 
//				"     = checkup_request_tab.Doctor_ID\r\n" + 
//				" where checkup_request_tab.Status = 'pending' ";
		
		SQLQuery query = session.createSQLQuery(sql);
		//query.addEntity(Doctor.class); 
		query.addEntity(CheckupRequest.class);
		List <CheckupRequest>results = query.list();
//		List cats = session.createCriteria(Doctor.class)
//			    .add( Restrictions.eq("checkup_request_tab.Status", "pending") )
//			    .list();   
//		 
//		for(int i=0;i<results.size();i++){
//	    System.out.println(results.get(i));
//	} 
		for(CheckupRequest checkupRequest : results) {
			System.out.println(checkupRequest.getDate());
			System.out.println(checkupRequest.getDid());
			System.out.println(checkupRequest.getStatus());
		}
		System.out.println(results.toString());
		return results;
	}

	
	@Override
	public List<CheckupRequest> getAcceptedRequests(int id) {
		
		Session session=sessionFactory.getCurrentSession();
		
		CriteriaBuilder cb=session.getCriteriaBuilder();
		
		CriteriaQuery<Doctor> query = cb.createQuery(Doctor.class);
		Root<Doctor> doctor = query.from(Doctor.class);
		Join<CheckupRequest,Doctor> request=doctor.join("requests");
		query.select(request).where(cb.equal(request.get("status"), "Accepted"));
		
		Query pop =session.createQuery(query);
		List<CheckupRequest> results = pop.getResultList();
		return results;
		
	}


	@Override
	public List<CheckupRequest> getReports(int id) {
		
		Session session=sessionFactory.getCurrentSession();
		
		CriteriaBuilder cb=session.getCriteriaBuilder();
		
		CriteriaQuery<Doctor> query = cb.createQuery(Doctor.class);
		Root<Doctor> doctor = query.from(Doctor.class);
		Join<CheckupRequest,Doctor> request=doctor.join("requests");
		query.select(request).where(cb.equal(request.get("status"), "Finished"));
		
		Query pop =session.createQuery(query);
		List<CheckupRequest> results = pop.getResultList();
		return results;
	}


	@Override
	public List<Doctor> doctorList() {
		
		Session session=sessionFactory.getCurrentSession();
		CriteriaBuilder cb=session.getCriteriaBuilder();
		CriteriaQuery<Doctor> cq=cb.createQuery(Doctor.class);
		Root<Doctor> root =cq.from(Doctor.class);
		cq.select(root);
		Query query =session.createQuery(cq);
		System.out.println("YOLOOOO"+query.getResultList());
		return query.getResultList();
	}


	@Override
	public void deleteDoctor(int id) {

		Session session=sessionFactory.getCurrentSession();
		System.out.println("In DAOOOO");
		Doctor doctor =session.byId(Doctor.class).load(id);
		session.delete(doctor);
		
	}	
	

}
