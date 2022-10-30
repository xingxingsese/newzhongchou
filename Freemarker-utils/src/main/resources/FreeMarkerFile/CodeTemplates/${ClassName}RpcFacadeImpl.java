package com.ipay.iexpbizprod.partner.imgs.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.ipay.ibizecoprod.common.service.facade.response.CityLabel;
import com.ipay.iexpbizprod.capability.authentication.util.UserAuthenticationContext;
import com.ipay.iexpbizprod.capability.imgs.ImgsResult;
import com.ipay.iexpbizprod.imgs.partner.voyage.VoyageRpcFacade;
import com.ipay.iexpbizprod.imgs.partner.voyage.dto.MpOpenMarketRecordDto;
import com.ipay.iexpbizprod.imgs.partner.voyage.dto.MpVoyageCompanyInformationDto;
import com.ipay.iexpbizprod.imgs.partner.voyage.request.*;
import com.ipay.iexpbizprod.imgs.partner.voyage.response.*;
import com.ipay.iexpbizprod.partner.domain.service.voyage.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@SofaService(bindings = {@SofaServiceBinding(bindingType = "tr")})
@Component
public class VoyageRpcFacadeImpl implements VoyageRpcFacade {

  @Autowired
  private VoyageService voyageService;

  /**
   * 单条查询主体信息 根据id
   *
   * @param request
   * @return
   */
  @Override
  public ImgsResult<QueryVoyageCompanyInfoResponse> queryCompanyInfo(QueryVoyageCompanyInfoRequest request) {
    request.setUserId(UserAuthenticationContext.get().getUserId());
    QueryVoyageCompanyInfoResponse queryVoyageCompanyInfoResponse = voyageService
        .queryCompanyInfo(request);
    return ImgsResult.buildSuccessResult(queryVoyageCompanyInfoResponse);
  }


  /**
   * 分页多条查询主体信息
   *
   * @param request
   * @return
   */
  @Override
  public ImgsResult<PaginatorResponse<List<MpVoyageCompanyInformationDto>>> queryCompanyInfoList(QueryVoyageCompanyInfoListRequest request) {
    request.setUserId(UserAuthenticationContext.get().getUserId());
    PaginatorResponse<List<MpVoyageCompanyInformationDto>> paginatorResponse = voyageService
        .queryCompanyInfoList(request);
    return ImgsResult.buildSuccessResult(paginatorResponse);
  }

  /**
   * 更新主体信息
   *
   * @param request
   * @return
   */
  @Override
  public ImgsResult<UpdateVoyageCompanyInfoResponse> updateCompanyInfo(UpdateVoyageCompanyInfoRequest request) {
    request.setUserId(UserAuthenticationContext.get().getUserId());
    UpdateVoyageCompanyInfoResponse updateVoyageCompanyInfoResponse = voyageService.updateCompanyInfo(request);
    return ImgsResult.buildSuccessResult(updateVoyageCompanyInfoResponse);
  }

  /**
   * 删除主体信息
   *
   * @param request
   * @return
   */
  @Override
  public ImgsResult<DeleteVoyageCompanyInfoResponse> deleteCompanyInfo(DeleteVoyageCompanyInfoRequest request) {
    request.setUserId(UserAuthenticationContext.get().getUserId());
    DeleteVoyageCompanyInfoResponse deleteVoyageCompanyInfoResponse = voyageService.deleteCompanyInfo(request);
    return ImgsResult.buildSuccessResult(deleteVoyageCompanyInfoResponse);
  }

  /**
   * 添加主体信息
   *
   * @param request
   * @return
   */
  @Override
  public ImgsResult<AddVoyageCompanyInfoResponse> addCompanyInfo(AddVoyageCompanyInfoRequest request) {
    request.setUserId(UserAuthenticationContext.get().getUserId());
    AddVoyageCompanyInfoResponse addVoyageCompanyInfoResponse = voyageService.addCompanyInfo(request);
    return ImgsResult.buildSuccessResult(addVoyageCompanyInfoResponse);
  }

  @Override
  public ImgsResult<MpOpenMarketRecordDto> queryMarketPlaceApply(QueryVoyageApplyRequest request) {

    QueryVoyageApplyResponse response = voyageService.queryMarketPlaceApply(request);

    return ImgsResult.buildSuccessResult(response.getMpOpenMarketApplyRecordDto());
  }

  @Override
  public ImgsResult<PaginatorResponse<List<MpOpenMarketRecordDto>>> queryMarketPlaceApplyList(QueryVoyageApplyListRequest request) {
    request.setUserId(UserAuthenticationContext.get().getUserId());
    PaginatorResponse<List<MpOpenMarketRecordDto>> marketPlaceApplyList = voyageService .queryMarketPlaceApplyList(request);
    return ImgsResult.buildSuccessResult(marketPlaceApplyList);
  }




  @Override
  public ImgsResult<AddVoyageApplyResponse> addMarketPlaceApply(AddVoyageApplyRequest request) {
    request.setUserId(UserAuthenticationContext.get().getUserId());

    return ImgsResult.buildSuccessResult(voyageService.addMarketPlaceApply(request));
  }

  @Override
  public ImgsResult<UpdateVoyageApplyResponse> updateMarketPlace(UpdateVoyageApplyRequest request) {
    request.setUserId(UserAuthenticationContext.get().getUserId());
    return ImgsResult.buildSuccessResult(voyageService.updateMarketPlace(request));
  }

  @Override
  public ImgsResult<QueryVoyageSitesInfoResponse> querySiteInfo(QueryVoyageSitesInfoRequest request) {
    return ImgsResult.buildSuccessResult(voyageService.querySiteInfo(request));
  }

  @Override
  public ImgsResult<SentSmsCodeResponse> sentSmsCode(SentSmsCodeRequest request) {
    SentSmsCodeResponse sentSmsCodeResponse = voyageService.sentSmsCode(request);
    return ImgsResult.buildSuccessResult(sentSmsCodeResponse);
  }

  @Override
  public ImgsResult<List<CityLabel>> queryCityCodeLabel(QueryCityCodeLabelRequest request) {
    return ImgsResult.buildSuccessResult(voyageService.queryCityCodeLabel(request));
  }

  @Override
  public ImgsResult<ReminderResponse> reminder(ReminderRequest request) {
    return ImgsResult.buildSuccessResult(voyageService.reminder(request));
  }

  /**
   * 获取用户的入驻信息
   * @return
   */
  @Override
  public ImgsResult<QueryVoyageOnboardingInfoResponse> queryOnboardingInfo(QueryVoyageOnboardingInfoRequest request) {
    return ImgsResult.buildSuccessResult(voyageService.queryOnboardingInfo(request));
  }

  /**
   * 暂存 远航批量开店表单数据
   * @return
   */
  @Override
  public ImgsResult<StoreMpApplyFormDataResponse> storeMpApplyFormData(StoreMpApplyFormDataRequest request) {
    return ImgsResult.buildSuccessResult(voyageService.storeMpApplyFormData(request));
  }

  /**
   * 查询 暂存的远航批量开店表单数据
   * @return
   */
  @Override
  public ImgsResult<QueryMpApplyFormDataResponse> queryMpApplyFormData(QueryMpApplyFormDataRequest request) {
    return ImgsResult.buildSuccessResult(voyageService.queryMpApplyFormData(request));
  }
}
