package $

{mock.classPath};


import com.alipay.gateway.adapterservice.annotation.OperationType;
import com.ipay.ibizecoprod.common.service.facade.response.CityLabel;
import com.ipay.iexpbizprod.capability.imgs.ImgsResult;
import com.ipay.iexpbizprod.imgs.partner.voyage.dto.MpOpenMarketRecordDto;
import com.ipay.iexpbizprod.imgs.partner.voyage.dto.MpVoyageCompanyInformationDto;
import com.ipay.iexpbizprod.imgs.partner.voyage.request.*;
import com.ipay.iexpbizprod.imgs.partner.voyage.response.*;

import java.util.List;

/**
 * 全球远航
 * 远程开店
 */
public interface $ {
    ClassName
}RpcFacade{

//company

/**
 * 单条查询主体信息
 * 根据id
 *
 * @param request
 * @return
 */
@OperationType("com.worldfirst.portal.pc.voyage.queryCompanyInfo")
  ImgsResult<QueryVoyageCompanyInfoResponse> queryCompanyInfo(QueryVoyageCompanyInfoRequest request);

/**
 * 分页多条查询主体信息
 * @param request
 * @return
 */
@OperationType("com.worldfirst.portal.pc.voyage.queryCompanyInfoList")
  ImgsResult<PaginatorResponse<List<MpVoyageCompanyInformationDto>>>queryCompanyInfoList(QueryVoyageCompanyInfoListRequest request);

/**
 * 更新主体信息
 * @param request
 * @return
 */
@OperationType("com.worldfirst.portal.pc.voyage.updateCompanyInfo")
  ImgsResult<UpdateVoyageCompanyInfoResponse> updateCompanyInfo(UpdateVoyageCompanyInfoRequest request);

/**
 * 删除主体信息
 * @param request
 * @return
 */
@OperationType("com.worldfirst.portal.pc.voyage.deleteCompanyInfo")
  ImgsResult<DeleteVoyageCompanyInfoResponse> deleteCompanyInfo(DeleteVoyageCompanyInfoRequest request);

/**
 * 添加主体信息
 * @param request
 * @return
 */
@OperationType("com.worldfirst.portal.pc.voyage.addCompanyInfo")
  ImgsResult<AddVoyageCompanyInfoResponse> addCompanyInfo(AddVoyageCompanyInfoRequest request);


//MarketPlace
/**
 * 单条查询MarketPlace申请
 *
 * 如果传入applyid根据主键id查询
 * @param request
 * @return
 */
@OperationType("com.worldfirst.portal.pc.voyage.queryMarketPlaceApply")
  ImgsResult<MpOpenMarketRecordDto> queryMarketPlaceApply(QueryVoyageApplyRequest request);


/**
 * 分页多条查询MarketPlace申请
 * @param request
 * @return
 */
@OperationType("com.worldfirst.portal.pc.voyage.queryMarketPlaceApplyList")
  ImgsResult<PaginatorResponse<List<MpOpenMarketRecordDto>>>queryMarketPlaceApplyList(QueryVoyageApplyListRequest request);


/**
 * 添加开店申请
 * @param request
 * @return
 */
@OperationType("com.worldfirst.portal.pc.voyage.addMarketPlaceApply")
  ImgsResult<AddVoyageApplyResponse> addMarketPlaceApply(AddVoyageApplyRequest request);


@OperationType("com.worldfirst.portal.pc.voyage.updateMarketPlace")
  ImgsResult<UpdateVoyageApplyResponse> updateMarketPlace(UpdateVoyageApplyRequest request);


@OperationType("com.worldfirst.portal.pc.voyage.querySiteInfo")
  ImgsResult<QueryVoyageSitesInfoResponse> querySiteInfo(QueryVoyageSitesInfoRequest request);


@OperationType("com.worldfirst.portal.pc.voyage.sentSmsCode")
  ImgsResult<SentSmsCodeResponse> sentSmsCode(SentSmsCodeRequest request);


@OperationType("com.worldfirst.portal.pc.voyage.getCityCodeLabel")
  ImgsResult<List<CityLabel>>queryCityCodeLabel(QueryCityCodeLabelRequest request);

@OperationType("com.worldfirst.portal.pc.voyage.reminder")
  ImgsResult<ReminderResponse> reminder(ReminderRequest request);


/**
 * 获取用户的入驻信息
 * @return
 */
@OperationType("com.worldfirst.portal.pc.voyage.queryOnboardingInfo")
  ImgsResult<QueryVoyageOnboardingInfoResponse> queryOnboardingInfo(QueryVoyageOnboardingInfoRequest request);

/**
 * 暂存 远航批量开店表单数据
 * @return
 */
@OperationType("com.worldfirst.portal.pc.voyage.storeMpApplyFormData")
  ImgsResult<StoreMpApplyFormDataResponse> storeMpApplyFormData(StoreMpApplyFormDataRequest request);

/**
 * 查询 暂存的远航批量开店表单数据
 * @return
 */
@OperationType("com.worldfirst.portal.pc.voyage.queryMpApplyFormData")
  ImgsResult<QueryMpApplyFormDataResponse> queryMpApplyFormData(QueryMpApplyFormDataRequest request);
        }
