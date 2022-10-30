package com.ipay.iexpbizprod.integration.ibizecoprod;

import com.ipay.ibizecoprod.common.service.facade.dto.MpCompanyInformationDto;
import com.ipay.ibizecoprod.common.service.facade.dto.MpOpenMarketApplyRecordDto;
import com.ipay.ibizecoprod.common.service.facade.dto.PaginatorResult;
import com.ipay.ibizecoprod.common.service.facade.request.GetCityLabelRequest;
import com.ipay.ibizecoprod.common.service.facade.request.voyage.*;
import com.ipay.ibizecoprod.common.service.facade.response.GetCityLabelResponse;
import com.ipay.ibizecoprod.common.service.facade.response.voyage.*;
import com.ipay.iexpbizprod.imgs.partner.voyage.request.QueryMpApplyFormDataRequest;
import com.ipay.iexpbizprod.imgs.partner.voyage.request.StoreMpApplyFormDataRequest;
import com.ipay.iexpbizprod.imgs.partner.voyage.response.QueryMpApplyFormDataResponse;
import com.ipay.iexpbizprod.imgs.partner.voyage.response.StoreMpApplyFormDataResponse;

import java.util.List;


/**
 * @author ：lisc
 * @date ：Created in 2022/4/12 9:54
 * @description：ibizecoprod 全球远航-远程开店申请相关服务
 */
public interface VoyageRpcServiceClient {
    /**
     * 单条查询主体信息
     * 根据id
     *
     * @param request
     * @return
     */
    QueryCompanyInfoResponse queryCompanyInfo(QueryCompanyInfoRequest request);

    /**
     * 分页多条查询主体信息
     *
     * @param request
     * @return
     */
    PaginatorResult<List<MpCompanyInformationDto>> queryCompanyInfoList(QueryCompanyInfoListRequest request);

    /**
     * 更新主体信息
     *
     * @param request
     * @return
     */
    UpdateCompanyInfoResponse updateCompanyInfo(UpdateCompanyInfoRequest request);

    /**
     * 删除主体信息
     *
     * @param request
     * @return
     */
    DeleteCompanyInfoResponse deleteCompanyInfo(DeleteCompanyInfoRequest request);

    /**
     * 添加主体信息
     *
     * @param request
     * @return
     */
    AddCompanyInfoResponse addCompanyInfo(AddCompanyInfoRequest request);



    /**
     * 单条查询MarketPlace申请
     * <p>
     * 如果传入applyid根据主键id查询
     *
     * @param request
     * @return
     */
    QueryMarketPlaceApplyResponse queryMarketPlaceApply(QueryMarketPlaceApplyRequest request);


    /**
     * 分页多条查询MarketPlace申请
     *
     * @param request
     * @return
     */
    PaginatorResult<List<MpOpenMarketApplyRecordDto>> queryMarketPlaceList(QueryMarketPlaceApplyListRequest request);

    /**
     * 更新MarketPlace申请
     *
     * @param request
     * @return
     */
    UpdateMarketPlaceApplyResponse updateMarketPlace(UpdateMarketPlaceApplyRequest request);

    /**
     * 删除开店申请
     *
     * @param request
     * @return
     */
    DeleteMarketPlaceApplyResponse deleteMarketPlace(DeleteMarketPlaceApplyRequest request);

    /**
     * 添加开店申请
     *
     * @param request
     * @return
     */
    AddMarketPlaceApplyResponse addMarketPlaceApply(AddMarketPlaceApplyRequest request);

    /**
     * 查询站点信息
     * @param request
     * @return
     */
    QueryMarketPlaceRuleInfoResponse queryMarketPlaceSiteInfo(QueryMarketPlaceRuleInfoRequest request);


    /**
     * 获取城市日志
     * @param request
     * @return
     */
    GetCityLabelResponse getCityLabel(GetCityLabelRequest request);

    /**
     * 催单
     * @param request
     * @return
     */
    UpdateReminderInfoResponse UpdateReminderInfo(UpdateReminderInfoRequest request);

    /**
     * 通过Mid获取用户的入驻信息
     * @param request
     * @return
     */
    QueryOnboardingInfoResponse queryOnboardingInfo(QueryOnboardingInfoRequest request);


    /**
     * 暂存 远航批量开店表单数据
     * @return
     */
    SaveApplyFormDataResponse storeMpApplyFormData(SaveApplyFormDataRequest request);

    /**
     * 查询 暂存的远航批量开店表单数据
     * @return
     */
    QueryApplyFormDataResponse queryMpApplyFormData(QueryApplyFormDataRequest request);
}
