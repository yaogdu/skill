package com.isoftstone.smart.kingstone.inject;

import com.isoftstone.smart.core.inject.ServiceModule;
import com.isoftstone.smart.kingstone.business.AccountService;
import com.isoftstone.smart.kingstone.business.AdjustAssetService;
import com.isoftstone.smart.kingstone.business.AdviceInvestService;
import com.isoftstone.smart.kingstone.business.BalanceService;
import com.isoftstone.smart.kingstone.business.ConsultantService;
import com.isoftstone.smart.kingstone.business.ConsultantlocationService;
import com.isoftstone.smart.kingstone.business.CustomerService;
import com.isoftstone.smart.kingstone.business.DocRecordService;
import com.isoftstone.smart.kingstone.business.DocService;
import com.isoftstone.smart.kingstone.business.EducationService;
import com.isoftstone.smart.kingstone.business.GroupMemberService;
import com.isoftstone.smart.kingstone.business.GroupService;
import com.isoftstone.smart.kingstone.business.IncomeService;
import com.isoftstone.smart.kingstone.business.InsuranceService;
import com.isoftstone.smart.kingstone.business.InvestmentService;
import com.isoftstone.smart.kingstone.business.JobConfigService;
import com.isoftstone.smart.kingstone.business.QaService;
import com.isoftstone.smart.kingstone.business.RiskService;
import com.isoftstone.smart.kingstone.entity.AdjustAsset;
import com.isoftstone.smart.kingstone.entity.AdviceInvest;
import com.isoftstone.smart.kingstone.entity.Balance;
import com.isoftstone.smart.kingstone.entity.CashConfig;
import com.isoftstone.smart.kingstone.entity.Consultant;
import com.isoftstone.smart.kingstone.entity.ConsultantLocation;
import com.isoftstone.smart.kingstone.entity.Customer;
import com.isoftstone.smart.kingstone.entity.Doc;
import com.isoftstone.smart.kingstone.entity.DocRecord;
import com.isoftstone.smart.kingstone.entity.Education;
import com.isoftstone.smart.kingstone.entity.Group;
import com.isoftstone.smart.kingstone.entity.GroupMember;
import com.isoftstone.smart.kingstone.entity.Income;
import com.isoftstone.smart.kingstone.entity.Insurance;
import com.isoftstone.smart.kingstone.entity.Investgroup;
import com.isoftstone.smart.kingstone.entity.Investment;
import com.isoftstone.smart.kingstone.entity.JobConfig;
import com.isoftstone.smart.kingstone.entity.Pension;
import com.isoftstone.smart.kingstone.entity.Qa;
import com.isoftstone.smart.kingstone.entity.Risk;
import com.isoftstone.smart.kingstone.entity.Visit;
import com.isoftstone.smart.kingstone.service.AccountResource;
import com.isoftstone.smart.kingstone.service.AdviceInvestResource;
import com.isoftstone.smart.kingstone.service.BalanceResource;
import com.isoftstone.smart.kingstone.service.ConsultantResource;
import com.isoftstone.smart.kingstone.service.CustomerResource;
import com.isoftstone.smart.kingstone.service.DocResource;
import com.isoftstone.smart.kingstone.service.EducationResource;
import com.isoftstone.smart.kingstone.service.GroupResource;
import com.isoftstone.smart.kingstone.service.IncomeResource;
import com.isoftstone.smart.kingstone.service.InsuranceResource;
import com.isoftstone.smart.kingstone.service.InvestmentResource;
import com.isoftstone.smart.kingstone.service.RiskResource;

public class WorkModule extends ServiceModule {

  @Override
  protected void configure() {

    addEntity(Customer.class);
    addEntity(Balance.class);
    addEntity(Consultant.class);
    addEntity(ConsultantLocation.class);
    addEntity(Doc.class);
    addEntity(Education.class);
    // addEntity(Goal.class);
    addEntity(Income.class);
    addEntity(Insurance.class);
    addEntity(Investgroup.class);
    addEntity(Investment.class);
    addEntity(Pension.class);
    addEntity(Risk.class);
    addEntity(CashConfig.class);
    addEntity(Visit.class);
    addEntity(Group.class);
    addEntity(GroupMember.class);
    addEntity(AdviceInvest.class);
    addEntity(AdjustAsset.class);
    addEntity(Qa.class);
    addEntity(DocRecord.class);
    addEntity(JobConfig.class);

    addService(GroupService.class);
    addService(EducationService.class);
    addService(ConsultantService.class);
    addService(ConsultantlocationService.class);
    addService(AccountService.class);
    addService(CustomerService.class);
    addService(BalanceService.class);
    addService(IncomeService.class);
    addService(DocService.class);
    addService(InsuranceService.class);
    addService(GroupMemberService.class);
    addService(RiskService.class);
    addService(ConsultantService.class);
    addService(InvestmentService.class);
    addService(AdviceInvestService.class);
    addService(AdjustAssetService.class);
    addService(QaService.class);
    addService(DocRecordService.class);
    addService(JobConfigService.class);

    addService(GroupResource.class);
    addService(EducationResource.class);
    addService(CustomerResource.class);
    addService(AccountResource.class);
    addService(ConsultantResource.class);
    addService(BalanceResource.class);
    addService(IncomeResource.class);
    addService(InsuranceResource.class);
    addService(DocResource.class);
    // addService(GoalResource.class);
    addService(RiskResource.class);
    addService(ConsultantResource.class);
    addService(InvestmentResource.class);
    addService(AdviceInvestResource.class);
  }
}
