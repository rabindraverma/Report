package com.report.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.report.binding.CitizenPlan;

@EnableJpaRepositories
public interface CitizenPlanRepository extends JpaRepository<CitizenPlan, Integer> {

    @Query("select distinct(planName) from CitizenPlan")
    List<String> getByPlanNames();

    @Query("select distinct(planStatus) from CitizenPlan")
    List<String> getByPlanStatues();
}
