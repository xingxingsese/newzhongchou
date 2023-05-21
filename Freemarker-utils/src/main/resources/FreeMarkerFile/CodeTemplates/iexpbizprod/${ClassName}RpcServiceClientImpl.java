package com.ipay.iexpbizprod.integration.ibizecoprod.impl;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.ipay.ialicore.common.util.log.LogUtil;
import com.ipay.ibizecoprod.common.service.facade.CityCodeFacade;
import com.ipay.ibizecoprod.common.service.facade.MpOpenMarketPlaceFacade;
import com.ipay.ibizecoprod.common.service.facade.dto.MpCompanyInformationDto;
import com.ipay.ibizecoprod.common.service.facade.dto.MpOpenMarketApplyRecordDto;
import com.ipay.ibizecoprod.common.service.facade.dto.PaginatorResult;
import com.ipay.ibizecoprod.common.service.facade.enums.UpdateMarketFormEnum;
import com.ipay.ibizecoprod.common.service.facade.request.GetCityLabelRequest;
import com.ipay.ibizecoprod.common.service.facade.request.voyage.*;
import com.ipay.ibizecoprod.common.service.facade.response.GetCityLabelResponse;
import com.ipay.ibizecoprod.common.service.facade.response.voyage.*;
import com.ipay.iexpbizprod.capability.config.util.JSONUtil;
import com.ipay.iexpbizprod.integration.ibizecoprod.VoyageRpcServiceClient;

import java.util.List;


/**
 * @author ：lisc
 * @date ：Created in 2022/4/12 9:55
 * @description：全球远航-远程开店申请相关服务
 */
public class VoyageRpcServiceClientImpl implements VoyageRpcServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(VoyageRpcServiceClientImpl.class);

    private MpOpenMarketPlaceFacade mpOpenMarketPlaceFacade;

    private CityCodeFacade cityCodeFacade;

    /**
     * 单条查询主体信息
     * 根据id
     *
     * @param request
     * @return
     */
    @Override
    public QueryCompanyInfoResponse queryCompanyInfo(QueryCompanyInfoRequest request) {
        QueryCompanyInfoResponse queryCompanyInfoResponse = mpOpenMarketPlaceFacade
                .queryCompanyInfo(request);
        LogUtil.info(logger, String.format("VoyageRpcServiceClient:queryCompanyInfo request:%s,response:%s",
                JSONUtil.objectToString(request), JSONUtil.objectToString(queryCompanyInfoResponse)));

        return queryCompanyInfoResponse;
    }

    /**
     * 分页多条查询主体信息
     *
     * @param request
     * @return
     */
    @Override
    public PaginatorResult<List<MpCompanyInformationDto>> queryCompanyInfoList(QueryCompanyInfoListRequest request) {
        PaginatorResult<List<MpCompanyInformationDto>> listPaginatorResult = mpOpenMarketPlaceFacade
                .queryCompanyInfoList(request);
        LogUtil.info(logger, String.format("VoyageRpcServiceClient:queryCompanyInfoList request:%s,response:%s",
                JSONUtil.objectToString(request), JSONUtil.objectToString(listPaginatorResult)));

        return listPaginatorResult;
    }


    /**
     * 更新主体信息
     *
     * @param request
     * @return
     */
    @Override
    public UpdateCompanyInfoResponse updateCompanyInfo(UpdateCompanyInfoRequest request) {
        UpdateCompanyInfoResponse updateCompanyInfoResponse = mpOpenMarketPlaceFacade
                .updateCompanyInfo(request);
        LogUtil.info(logger, String.format("VoyageRpcServiceClient:updateCompanyInfo request:%s,response:%s",
                JSONUtil.objectToString(request), JSONUtil.objectToString(updateCompanyInfoResponse)));

        return updateCompanyInfoResponse;
    }

    /**
     * 删除主体信息
     *
     * @param request
     * @return
     */
    @Override
    public DeleteCompanyInfoResponse deleteCompanyInfo(DeleteCompanyInfoRequest request) {
        DeleteCompanyInfoResponse deleteCompanyInfoResponse = mpOpenMarketPlaceFacade
                .deleteCompanyInfo(request);
        LogUtil.info(logger, String.format("VoyageRpcServiceClient:deleteCompanyInfo request:%s,response:%s",
                JSONUtil.objectToString(request), JSONUtil.objectToString(deleteCompanyInfoResponse)));
        return deleteCompanyInfoResponse;
    }

    /**
     * 添加主体信息
     *
     * @param request
     * @return
     */
    @Override
    public AddCompanyInfoResponse addCompanyInfo(AddCompanyInfoRequest request) {
        AddCompanyInfoResponse addCompanyInfoResponse = mpOpenMarketPlaceFacade
                .addCompanyInfo(request);
        LogUtil.info(logger, String.format("VoyageRpcServiceClient:addCompanyInfo request:%s,response:%s",
                JSONUtil.objectToString(request), JSONUtil.objectToString(addCompanyInfoResponse)));

        return addCompanyInfoResponse;
    }

    @Override
    public QueryMarketPlaceApplyResponse queryMarketPlaceApply(
            QueryMarketPlaceApplyRequest request) {
        QueryMarketPlaceApplyResponse response = mpOpenMarketPlaceFacade
                .queryMarketPlaceApply(request);
        LogUtil.info(logger, String.format("VoyageRpcServiceClient:queryMarketPlaceApply request:%s,response:%s",
                JSONUtil.objectToString(request), JSONUtil.objectToString(response)));
        return response;
    }

    @Override
    public PaginatorResult<List<MpOpenMarketApplyRecordDto>> queryMarketPlaceList(
            QueryMarketPlaceApplyListRequest request) {
        PaginatorResult<List<MpOpenMarketApplyRecordDto>> response = mpOpenMarketPlaceFacade
                .queryMarketPlaceList(request);

        LogUtil.info(logger, String.format("VoyageRpcServiceClient:queryMarketPlaceList request:%s,response:%s",
                JSONUtil.objectToString(request), JSONUtil.objectToString(response)));

        return response;
    }

    @Override
    public UpdateMarketPlaceApplyResponse updateMarketPlace(UpdateMarketPlaceApplyRequest request) {
        request.setUpdateType(UpdateMarketFormEnum.UPDATE_APPLE_INFO.getCode());
        UpdateMarketPlaceApplyResponse updateMarketPlaceApplyResponse = mpOpenMarketPlaceFacade
                .updateMarketPlace(request);
        LogUtil.info(logger, String.format("VoyageRpcServiceClient:updateMarketPlace request:%s,response:%s",
                JSONUtil.objectToString(request), JSONUtil.objectToString(updateMarketPlaceApplyResponse)));
        return updateMarketPlaceApplyResponse;
    }

    @Override
    public DeleteMarketPlaceApplyResponse deleteMarketPlace(DeleteMarketPlaceApplyRequest request) {
        DeleteMarketPlaceApplyResponse deleteMarketPlaceApplyResponse = mpOpenMarketPlaceFacade
                .deleteMarketPlace(request);
        LogUtil.info(logger, String.format("VoyageRpcServiceClient:updateMarketPlace request:%s,response:%s",
                JSONUtil.objectToString(request), JSONUtil.objectToString(deleteMarketPlaceApplyResponse)));
        return deleteMarketPlaceApplyResponse;
    }

    @Override
    public AddMarketPlaceApplyResponse addMarketPlaceApply(AddMarketPlaceApplyRequest request) {
        AddMarketPlaceApplyResponse addMarketPlaceApplyResponse = mpOpenMarketPlaceFacade
                .addMarketPlaceApply(request);

        LogUtil.info(logger, String.format("VoyageRpcServiceClient:updateMarketPlace request:%s,response:%s",
                JSONUtil.objectToString(request), JSONUtil.objectToString(addMarketPlaceApplyResponse)));

        return addMarketPlaceApplyResponse;
    }

    @Override
    public QueryMarketPlaceRuleInfoResponse queryMarketPlaceSiteInfo(
            QueryMarketPlaceRuleInfoRequest request) {
        QueryMarketPlaceRuleInfoResponse queryMarketPlaceRuleInfoResponse = mpOpenMarketPlaceFacade
                .queryMarketPlaceSiteInfo(request);

        LogUtil.info(logger, String.format("VoyageRpcServiceClient:queryMarketPlaceSiteInfo request:%s,response:%s",
                JSONUtil.objectToString(request), JSONUtil.objectToString(queryMarketPlaceRuleInfoResponse)));

        return queryMarketPlaceRuleInfoResponse;
    }

    @Override
    public GetCityLabelResponse getCityLabel(GetCityLabelRequest request) {
        GetCityLabelResponse cityLabel = cityCodeFacade.getCityLabel(request);
        LogUtil.info(logger, String.format("VoyageRpcServiceClient:queryMarketPlaceSiteInfo request:%s,response:%s",
                JSONUtil.objectToString(request), JSONUtil.objectToString(cityLabel)));
        return cityLabel;
    }

    @com.ipay.iexpbizprod.capability.log.Logger
    @Override
    public UpdateReminderInfoResponse UpdateReminderInfo(UpdateReminderInfoRequest request) {
        UpdateReminderInfoResponse updateReminderInfoResponse = mpOpenMarketPlaceFacade.UpdateReminderInfo(request);
        return updateReminderInfoResponse;
    }

    /**
     * 通过Mid获取用户的入驻信息
     *
     * @param request
     * @return
     */
    @Override
    public QueryOnboardingInfoResponse queryOnboardingInfo(QueryOnboardingInfoRequest request) {
        QueryOnboardingInfoResponse queryOnboardingInfoResponse = mpOpenMarketPlaceFacade.queryOnboardingInfo(request);
        LogUtil.info(logger, String.format("VoyageRpcServiceClientImpl:queryOnboardingInfo: request: %s response: %s",
                JSONUtil.objectToString(request), JSONUtil.objectToString(queryOnboardingInfoResponse)));
        return queryOnboardingInfoResponse;
    }

    @Override
    public SaveApplyFormDataResponse storeMpApplyFormData(SaveApplyFormDataRequest request) {
        return null;
    }

    @Override
    public QueryApplyFormDataResponse queryMpApplyFormData(QueryApplyFormDataRequest request) {
        return null;
    }

    /**
     * Sets the mpOpenMarketPlaceFacade. *
     * <p>You can use getMpOpenMarketPlaceFacade() to get the value of mpOpenMarketPlaceFacade</p>
     * * @param mpOpenMarketPlaceFacade mpOpenMarketPlaceFacade
     */
    public void setMpOpenMarketPlaceFacade(MpOpenMarketPlaceFacade mpOpenMarketPlaceFacade) {
        this.mpOpenMarketPlaceFacade = mpOpenMarketPlaceFacade;
    }

    public void setCityCodeFacade(CityCodeFacade cityCodeFacade) {
        this.cityCodeFacade = cityCodeFacade;
    }
}
