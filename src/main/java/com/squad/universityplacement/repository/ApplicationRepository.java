package com.squad.universityplacement.repository;

import com.university.placement.entity.ApplicationEntity;
import com.university.placement.entity.InterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Integer> {

    boolean existsByStudent_RollNoAndCompany_CompanyId(String rollNo, Integer companyId);

    List<ApplicationEntity> findAllByOrderByAppIdAsc();

    List<ApplicationEntity> findByStudent_RollNoOrderByAppIdAsc(String rollNo);

    List<ApplicationEntity> findByCompany_CompanyIdOrderByAppIdAsc(Integer companyId);

    List<ApplicationEntity> findByInterviewStatus(InterviewStatus interviewStatus);

    @Query("""
            select max(c.packageLpa)
            from ApplicationEntity a join a.company c
            where a.student.rollNo = :rollNo and a.interviewStatus = :status
            """)
    BigDecimal findHighestOfferedPackage(@Param("rollNo") String rollNo,
                                        @Param("status") InterviewStatus status);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            update ApplicationEntity a
            set a.interviewStatus = com.university.placement.entity.InterviewStatus.REJECTED
            where a.student.rollNo = :rollNo
              and a.interviewStatus in (
                  com.university.placement.entity.InterviewStatus.APPLIED,
                  com.university.placement.entity.InterviewStatus.SHORTLISTED,
                  com.university.placement.entity.InterviewStatus.INTERVIEWING
              )
              and exists (
                  select c.companyId
                  from CompanyEntity c
                  where c = a.company and c.packageLpa < :offeredPackage
              )
            """)
    int rejectPendingApplicationsForLowerPackages(@Param("rollNo") String rollNo,
                                                 @Param("offeredPackage") BigDecimal offeredPackage);

    @Query("""
            select count(a) as totalOffers,
                   coalesce(avg(c.packageLpa), 0) as averagePackageLpa,
                   coalesce(max(c.packageLpa), 0) as highestPackageLpa
            from ApplicationEntity a join a.company c
            where a.interviewStatus = :status
            """)
    OfferAggregateProjection loadOfferAggregates(@Param("status") InterviewStatus status);
}
