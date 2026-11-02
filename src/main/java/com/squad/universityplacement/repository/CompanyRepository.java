package com.squad.universityplacement.repository;

import com.university.placement.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {

    boolean existsByNameIgnoreCase(String name);

    List<CompanyEntity> findAllByOrderByCompanyIdAsc();

    @Query("""
            select c
            from CompanyEntity c, StudentEntity s
            where s.rollNo = :rollNo
              and s.cgpa >= c.minCgpa
              and s.activeBacklogs <= c.maxBacklogs
            order by c.packageLpa desc, c.companyId
            """)
    List<CompanyEntity> findEligibleForStudent(@Param("rollNo") String rollNo);

    @Query("""
            select c.name as companyName,
                   c.packageLpa as packageLpa,
                   count(a.appId) as applicants,
                   sum(case when a.interviewStatus = com.university.placement.entity.InterviewStatus.OFFERED
                       then 1 else 0 end) as offers
            from CompanyEntity c
            left join ApplicationEntity a on a.company = c
            group by c.companyId, c.name, c.packageLpa
            order by c.packageLpa desc, c.companyId
            """)
    List<CompanyDriveSummaryProjection> findDriveSummaries();
}
