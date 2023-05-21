package com.ipay.iexpbizprod.partner.domain.service.voyage;


import com.ipay.ibizecoprod.common.service.facade.response.CityLabel;
import com.ipay.iexpbizprod.imgs.partner.voyage.dto.MpOpenMarketRecordDto;
import com.ipay.iexpbizprod.imgs.partner.voyage.dto.MpVoyageCompanyInformationDto;
import com.ipay.iexpbizprod.imgs.partner.voyage.request.*;
import com.ipay.iexpbizprod.imgs.partner.voyage.response.*;

import java.util.List;

public interface VoyageService {
    /**
     * 单条查询主体信息
     * 根据id
     *
     * @param request
     * @return
     */
    QueryVoyageCompanyInfoResponse queryCompanyInfo(QueryVoyageCompanyInfoRequest request);

    /**
     * 分页多条查询主体信息
     *
     * @param request
     * @return
     */
    PaginatorResponse<List<MpVoyageCompanyInformationDto>> queryCompanyInfoList(QueryVoyageCompanyInfoListRequest request);

    /**
     * 更新主体信息
     *
     * @param request
     * @return
     */
    UpdateVoyageCompanyInfoResponse updateCompanyInfo(UpdateVoyageCompanyInfoRequest request);

    /**
     * 删除主体信息
     *
     * @param request
     * @return
     */
    DeleteVoyageCompanyInfoResponse deleteCompanyInfo(DeleteVoyageCompanyInfoRequest request);

    /**
     * 添加主体信息
     *
     * @param request
     * @return
     */

    AddVoyageCompanyInfoResponse addCompanyInfo(AddVoyageCompanyInfoRequest request);


    //MarketPlace

    /**
     * 单条查询MarketPlace申请
     * <p>
     * 如果传入applyid根据主键id查询
     *
     * @param request
     * @return
     */
    QueryVoyageApplyResponse queryMarketPlaceApply(QueryVoyageApplyRequest request);


    /**
     * 分页多条查询MarketPlace申请
     *
     * @param request
     * @return
     */
    PaginatorResponse<List<MpOpenMarketRecordDto>> queryMarketPlaceApplyList(QueryVoyageApplyListRequest request);


    /**
     * 删除开店申请
     *
     * @param request
     * @return
     */
    DeleteVoyageApplyResponse deleteMarketPlaceApply(DeleteVoyageApplyRequest request);

    /**
     * 添加开店申请
     *
     * @param request
     * @return
     */
    AddVoyageApplyResponse addMarketPlaceApply(AddVoyageApplyRequest request);


    UpdateVoyageApplyResponse updateMarketPlace(UpdateVoyageApplyRequest request);


    /**
     * 查询站点信息
     *
     * @param request
     * @return
     */
    QueryVoyageSitesInfoResponse querySiteInfo(QueryVoyageSitesInfoRequest request);

    /**
     * 发送短信验证码
     *
     * @param request
     * @return
     */
    SentSmsCodeResponse sentSmsCode(SentSmsCodeRequest request);

    /**
     * 获取联动城市选框数据
     *
     * @param request
     * @return
     */
    List<CityLabel> queryCityCodeLabel(QueryCityCodeLabelRequest request);

    /**
     * 开店催单
     *
     * @param request
     * @return
     */
    ReminderResponse reminder(ReminderRequest request);

    /**
     * 通过Mid获取用户的入驻信息
     *
     * @param request
     * @return
     */
    QueryVoyageOnboardingInfoResponse queryOnboardingInfo(QueryVoyageOnboardingInfoRequest request);

    /**
     * 暂存 远航批量开店表单数据
     *
     * @return
     */
    StoreMpApplyFormDataResponse storeMpApplyFormData(StoreMpApplyFormDataRequest request);

    /**
     * 查询 暂存的远航批量开店表单数据
     *
     * @return
     */
    QueryMpApplyFormDataResponse queryMpApplyFormData(QueryMpApplyFormDataRequest request);
}
