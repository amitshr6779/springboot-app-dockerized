package com.QATechTesting.HMS.DockerSpringBoot.DAO;

import  com.QATechTesting.HMS.DockerSpringBoot.Model.HMS;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepo extends CrudRepository <HMS, Integer>	{
	List<HMS> findBypatientname(String patinetname);
}