package com.ipay.iexpbizprod.partner.domain.service.voyage.impl;

import com.alibaba.common.lang.StringUtil;
import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.ipay.ialicore.common.enums.CountryEnum;
import com.ipay.ialicore.common.util.log.LogUtil;
import com.ipay.ibizecoprod.common.service.facade.dto.MarketPlaceSiteRuleDto;
import com.ipay.ibizecoprod.common.service.facade.dto.MpCompanyInformationDto;
import com.ipay.ibizecoprod.common.service.facade.dto.MpOpenMarketApplyRecordDto;
import com.ipay.ibizecoprod.common.service.facade.dto.PaginatorResult;
import com.ipay.ibizecoprod.common.service.facade.dto.SiteRuleDto;
import com.ipay.ibizecoprod.common.service.facade.enums.PlatformReviewEnum;
import com.ipay.ibizecoprod.common.service.facade.request.GetCityLabelRequest;
import com.ipay.ibizecoprod.common.service.facade.request.voyage.*;
import com.ipay.ibizecoprod.common.service.facade.response.CityLabel;
import com.ipay.ibizecoprod.common.service.facade.response.GetCityLabelResponse;
import com.ipay.ibizecoprod.common.service.facade.response.voyage.*;
import com.ipay.iexpbizprod.capability.authentication.UserAuthentication;
import com.ipay.iexpbizprod.capability.authentication.util.UserAuthenticationContext;
import com.ipay.iexpbizprod.capability.config.util.JSONUtil;
import com.ipay.iexpbizprod.capability.errorhandling.ErrorCode;
import com.ipay.iexpbizprod.capability.errorhandling.util.AssertService;
import com.ipay.iexpbizprod.capability.i18n.I18nUtils;
import com.ipay.iexpbizprod.capability.otp.dto.OtpTypeEnum;
import com.ipay.iexpbizprod.capability.otp.dto.PhoneNumber;
import com.ipay.iexpbizprod.capability.otp.request.OtpSendRequest;
import com.ipay.iexpbizprod.capability.otp.request.OtpVerifyRequest;
import com.ipay.iexpbizprod.capability.otp.result.OtpSendResult;
import com.ipay.iexpbizprod.capability.otp.result.OtpVerifyResult;
import com.ipay.iexpbizprod.capability.otp.service.OtpService;
import com.ipay.iexpbizprod.common.tool.constants.enums.ProductTypeEnum;
import com.ipay.iexpbizprod.imgs.partner.voyage.dto.MPSiteRuleDto;
import com.ipay.iexpbizprod.imgs.partner.voyage.dto.MpOpenMarketRecordDto;
import com.ipay.iexpbizprod.imgs.partner.voyage.dto.MpVoyageCompanyInformationDto;
import com.ipay.iexpbizprod.imgs.partner.voyage.dto.SiteLabelDto;
import com.ipay.iexpbizprod.imgs.partner.voyage.request.*;
import com.ipay.iexpbizprod.imgs.partner.voyage.response.*;
import com.ipay.iexpbizprod.integration.ibizecoprod.VoyageRpcServiceClient;
import com.ipay.iexpbizprod.integration.imember.ImemberProdClient;
import com.ipay.iexpbizprod.partner.domain.service.voyage.VoyageService;
import com.ipay.iexpbizprod.partner.error.EcoErrorCodeMappingEnum;
import com.ipay.imemberprod.service.facade.result.prod.memebership.MembershipLevelAndBenefitsQueryResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.ipay.iexpbizprod.capability.tools.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SofaService(bindings = {@SofaServiceBinding(bindingType = "tr")})
public class VoyageServiceImpl implements VoyageService {

    @SofaReference
    private VoyageRpcServiceClient voyageRpcServiceClient;

    /**
     * 发送短信验证
     */
    @SofaReference
    private OtpService otpService;

    @SofaReference
    private ImemberProdClient imemberProdClient;

    @SofaReference
    private AssertService assertService;


    private static final Logger logger = LoggerFactory.getLogger(VoyageServiceImpl.class);

    /**
     * 开店主体添加，全球远航手机OTP发送scene
     * 对应参数中心新的ServiceCode:VOYAGE_VERIFY_MOBILE / VOYAGE_VERIFY_MOBILE_EN 
     * OTP文案无#action#
     */
    private static final String VOYAGE_MOBILE_OTP = "VOYAGE_MOBILE_OTP";

    /**
     * 单条查询主体信息
     * 根据id
     *
     * @param request
     * @return
     */

    @Override
    public QueryVoyageCompanyInfoResponse queryCompanyInfo(QueryVoyageCompanyInfoRequest request) {

        LogUtil.info(logger, String.format("VoyageServiceImpl:queryCompanyInfo:start , request: %s", JSONUtil.objectToString(request)));
        QueryVoyageCompanyInfoResponse queryVoyageCompanyInfoResponse = new QueryVoyageCompanyInfoResponse();
        // 转换入参
        QueryCompanyInfoRequest queryCompanyInfoRequest = getQueryCompanyInfoRequest(request);
        QueryCompanyInfoResponse queryCompanyInfoResponse = voyageRpcServiceClient.queryCompanyInfo(queryCompanyInfoRequest);

        assertService.notNull(queryCompanyInfoResponse, ErrorCode.BIZECOPROD_INVOKE_FAILED);
        assertService.isTrue(queryCompanyInfoResponse.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);
        // 转换对象
        buildQueryVoyageCompanyInfoResponse(queryVoyageCompanyInfoResponse, queryCompanyInfoResponse);

        LogUtil.info(logger, String.format("VoyageServiceImpl:queryCompanyInfo:end , response: %s", JSONUtil.objectToString(queryVoyageCompanyInfoResponse)));
        return queryVoyageCompanyInfoResponse;
    }

    /**
     * 分页多条查询主体信息
     *
     * @param request
     * @return
     */

    @Override
    public PaginatorResponse<List<MpVoyageCompanyInformationDto>> queryCompanyInfoList(QueryVoyageCompanyInfoListRequest request) {
        LogUtil.info(logger, String.format("VoyageServiceImpl:queryCompanyInfoList:start , request: %s", JSONUtil.objectToString(request)));
        // 转换入参
        QueryCompanyInfoListRequest queryCompanyInfoListRequest = buildQueryCompanyInfoListRequest(request);
        PaginatorResult<List<MpCompanyInformationDto>> queryPaginatorResult = voyageRpcServiceClient.queryCompanyInfoList(queryCompanyInfoListRequest);

        assertService.isTrue(queryPaginatorResult.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);

        PaginatorResponse paginatorResponse = buildPaginatorResult(queryPaginatorResult);

        LogUtil.info(logger, String.format("VoyageServiceImpl:queryCompanyInfoList:end , response: %s", JSONUtil.objectToString(paginatorResponse)));
        return paginatorResponse;
    }

    /**
     * 更新主体信息
     *
     * @param request
     * @return
     */

    @Override
    public UpdateVoyageCompanyInfoResponse updateCompanyInfo(UpdateVoyageCompanyInfoRequest request) {
        LogUtil.info(logger, String.format("VoyageServiceImpl:updateCompanyInfo:start , request: %s", JSONUtil.objectToString(request)));
        // 创建response对象
        UpdateVoyageCompanyInfoResponse updateVoyageCompanyInfoResponse = new UpdateVoyageCompanyInfoResponse();
        // 转换请求对象
        UpdateCompanyInfoRequest updateCompanyInfoRequest = buildUpdateCompanyInfoRequest(request);
        // 查询主体信息
        QueryCompanyInfoResponse queryCompanyInfo = getCompanyInfoResponse(request);
        assertService.notNull(queryCompanyInfo, ErrorCode.VOYAGE_COMPANY_INFO_DO_NOT_EXIST);
        assertService.notNull(queryCompanyInfo.getMpCompanyInformationDto(), ErrorCode.VOYAGE_COMPANY_INFO_DO_NOT_EXIST);
        String phoneNumber = request.getPhoneNumber();
        // 判断是否修改手机号
        if (!phoneNumber.equals(queryCompanyInfo.getMpCompanyInformationDto().getPhoneNumber()) && StringUtils.isNoneEmpty(request.getCode())) {
            // 获取短信验证码校验结果
            OtpVerifyResult otpResult = getVerifyOtpResult(request.getCode(), request.getCountryCode(), request.getPhoneNumber(), request.getEnvInfo().getLang(), request.getRequestId());
            assertService.isTrue(otpResult.isSuccess(), ErrorCode.VERIFY_OTP_FAILED);
        }
        UpdateCompanyInfoResponse updateCompanyInfoResponse = voyageRpcServiceClient.updateCompanyInfo(updateCompanyInfoRequest);

        assertService.notNull(updateCompanyInfoResponse, ErrorCode.BIZECOPROD_INVOKE_FAILED);
        assertService.isTrue(updateCompanyInfoResponse.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);

        buildUpdateCompanyInfoResponse(updateVoyageCompanyInfoResponse, updateCompanyInfoResponse);

        LogUtil.info(logger, String.format("VoyageServiceImpl:updateCompanyInfo:end , response: %s", JSONUtil.objectToString(updateVoyageCompanyInfoResponse)));
        return updateVoyageCompanyInfoResponse;
    }

    /**
     * 删除主体信息
     *
     * @param request
     * @return
     */

    @Override
    public DeleteVoyageCompanyInfoResponse deleteCompanyInfo(DeleteVoyageCompanyInfoRequest request) {
        LogUtil.info(logger, String.format("VoyageServiceImpl:deleteCompanyInfo:start , request: %s", JSONUtil.objectToString(request)));
        DeleteVoyageCompanyInfoResponse deleteVoyageCompanyInfoResponse = new DeleteVoyageCompanyInfoResponse();
        // 查询主体信息
        QueryCompanyInfoResponse queryCompanyInfoResponse = getQueryCompanyInfoResponse(request);
        assertService.notNull(queryCompanyInfoResponse, ErrorCode.VOYAGE_COMPANY_INFO_DO_NOT_EXIST);
        assertService.notNull(queryCompanyInfoResponse.getMpCompanyInformationDto(), ErrorCode.VOYAGE_COMPANY_INFO_DO_NOT_EXIST);
        // 判断是否允许删除
        assertService.isTrue(queryCompanyInfoResponse.getCanModify(), ErrorCode.VOYAGE_COMPANY_INFO_NOT_ALLOWED_DELETE);
        // 构建入参
        DeleteCompanyInfoRequest deleteCompanyInfoRequest = buildDeleteCompanyInfoRequest(request);
        DeleteCompanyInfoResponse deleteCompanyInfoResponse = voyageRpcServiceClient.deleteCompanyInfo(deleteCompanyInfoRequest);
        assertService.notNull(deleteCompanyInfoResponse, ErrorCode.BIZECOPROD_INVOKE_FAILED);
        assertService.isTrue(deleteCompanyInfoResponse.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);
        // 构建出参
        buildDeleteVoyageCompanyInfoResponse(deleteVoyageCompanyInfoResponse, deleteCompanyInfoResponse);

        LogUtil.info(logger, String.format("VoyageServiceImpl:deleteCompanyInfo:end , response: %s", JSONUtil.objectToString(deleteVoyageCompanyInfoResponse)));
        return deleteVoyageCompanyInfoResponse;
    }


    /**
     * 添加主体信息
     *
     * @param request
     * @return
     */

    @Override
    public AddVoyageCompanyInfoResponse addCompanyInfo(AddVoyageCompanyInfoRequest request) {
        LogUtil.info(logger, String.format("VoyageServiceImpl:addCompanyInfo:start , request: %s", JSONUtil.objectToString(request)));
        AddVoyageCompanyInfoResponse addVoyageCompanyInfoResponse = new AddVoyageCompanyInfoResponse();
        AddCompanyInfoRequest addCompanyInfoRequest = buildAddCompanyInfoRequest(request);
        // 校验短信验证码
        OtpVerifyResult otpResult = getVerifyOtpResult(request.getCode(), request.getCountryCode(), request.getPhoneNumber(), request.getEnvInfo().getLang(), request.getRequestId());
        assertService.isTrue(otpResult.isSuccess(), ErrorCode.VERIFY_OTP_FAILED);
        // 调下游服务,保存主体信息
        AddCompanyInfoResponse addCompanyInfoResponse = voyageRpcServiceClient.addCompanyInfo(addCompanyInfoRequest);
        assertService.notNull(addCompanyInfoResponse, ErrorCode.BIZECOPROD_INVOKE_FAILED);

        assertService.notEquals(EcoErrorCodeMappingEnum.COMPANY_INFO_EXIST.getExternalCode(), addCompanyInfoResponse.getErrorCode(),
                EcoErrorCodeMappingEnum.getByCode(addCompanyInfoResponse.getErrorCode()), "company info has exist");

        assertService.isTrue(addCompanyInfoResponse.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);

        buildAddVoyageCompanyInfoResponse(addVoyageCompanyInfoResponse, addCompanyInfoResponse);

        LogUtil.info(logger, String.format("VoyageServiceImpl:addCompanyInfo:end , response: %s", JSONUtil.objectToString(addVoyageCompanyInfoResponse)));
        return addVoyageCompanyInfoResponse;
    }

    /**
     * 单条查询MarketPlace申请
     * <p>
     * 如果传入applyid根据主键id查询
     *
     * @param request
     * @return
     */
    @com.ipay.iexpbizprod.capability.log.Logger
    @Override
    public QueryVoyageApplyResponse queryMarketPlaceApply(QueryVoyageApplyRequest request) {
        QueryMarketPlaceApplyRequest queryMarketPlaceApplyRequest = new QueryMarketPlaceApplyRequest();
        queryMarketPlaceApplyRequest.setApplyId(request.getApplyId());
        QueryMarketPlaceApplyResponse response = voyageRpcServiceClient
                .queryMarketPlaceApply(queryMarketPlaceApplyRequest);
        QueryVoyageApplyResponse queryVoyageApplyResponse = new QueryVoyageApplyResponse();

        assertService.notNull(response, ErrorCode.BIZECOPROD_INVOKE_FAILED);
        assertService.isTrue(response.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);

        MpOpenMarketApplyRecordDto mpOpenMarketApplyRecordDto = response.getMpOpenMarketApplyRecordDto();

        if (mpOpenMarketApplyRecordDto != null) {
            MpOpenMarketRecordDto mpOpenMarketRecordDto = new MpOpenMarketRecordDto();
            mpOpenMarketRecordDto = mpOpenMarketRecordDto.buildResponseDTO(mpOpenMarketApplyRecordDto);
            queryVoyageApplyResponse.setMpOpenMarketApplyRecordDto(mpOpenMarketRecordDto);
        }
        return queryVoyageApplyResponse;
    }

    /**
     * 分页多条查询MarketPlace申请
     *
     * @param request
     * @return
     */

    @Override
    public PaginatorResponse<List<MpOpenMarketRecordDto>> queryMarketPlaceApplyList(
            QueryVoyageApplyListRequest request) {
        QueryMarketPlaceApplyListRequest queryMarketPlaceApplyListRequest = new QueryMarketPlaceApplyListRequest();
        BeanUtils.copyProperties(request, queryMarketPlaceApplyListRequest);
        PaginatorResult<List<MpOpenMarketApplyRecordDto>> listPaginatorResult = voyageRpcServiceClient
                .queryMarketPlaceList(queryMarketPlaceApplyListRequest);

        assertService.isTrue(listPaginatorResult.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);

        List<MpOpenMarketRecordDto> mpOpenMarketRecordDtos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(listPaginatorResult.getValue())) {
            for (MpOpenMarketApplyRecordDto mpOpenMarketApplyRecordDto : listPaginatorResult
                    .getValue()) {
                MpOpenMarketRecordDto mpOpenMarketRecordDto = new MpOpenMarketRecordDto();
                MpOpenMarketRecordDto recordDto = mpOpenMarketRecordDto
                        .buildResponseDTO(mpOpenMarketApplyRecordDto);
                mpOpenMarketRecordDtos.add(recordDto);
            }
        }

        PaginatorResponse paginatorResponse = PaginatorResponse.builder()
                .paginator(listPaginatorResult.getPaginator())
                .value(mpOpenMarketRecordDtos).build();
        return paginatorResponse;
    }

    /**
     * 删除开店申请
     *
     * @param request
     * @return
     */

    @Override
    public DeleteVoyageApplyResponse deleteMarketPlaceApply(DeleteVoyageApplyRequest request) {
        DeleteVoyageApplyResponse response = new DeleteVoyageApplyResponse();

        DeleteMarketPlaceApplyRequest deleteMarketPlaceApplyRequest = new DeleteMarketPlaceApplyRequest();
        deleteMarketPlaceApplyRequest.setApplyId(request.getApplyId());
        deleteMarketPlaceApplyRequest.setUserId(request.getUserId());
        DeleteMarketPlaceApplyResponse deleteMarketPlaceApplyResponse = voyageRpcServiceClient
                .deleteMarketPlace(deleteMarketPlaceApplyRequest);
        BeanUtils.copyProperties(deleteMarketPlaceApplyResponse, response);
        return response;
    }

    /**
     * 添加开店申请
     *
     * @param request
     * @return
     */
    @Override
    public AddVoyageApplyResponse addMarketPlaceApply(AddVoyageApplyRequest request) {
        // 允许访问的地区
        List<String> countryList = Arrays.asList(CountryEnum.CHINA.getAlpha2Code(), CountryEnum.HONG_KONG.getAlpha2Code());
        // 当前用户地区
        UserAuthentication userAuthentication = UserAuthenticationContext.get();
        String productTypeCode = userAuthentication.getProductTypeCode();
        CountryEnum country = userAuthentication.getCountry();

        boolean countryCheck = countryList.contains(country.getAlpha2Code()) && ProductTypeEnum.E_COMMERCE.getCode().equalsIgnoreCase(productTypeCode);
        LogUtil.info(logger, String.format("VoyageServiceImpl:addMarketPlaceApply: UserAuthentication: %s " ,
                JSONUtil.objectToString(userAuthentication)));
        // 非指定地区
        assertService.isTrue(countryCheck, ErrorCode.VOAYGE_UNEXPECTED_REGION);

        //查询当前用户信息
        QueryCompanyInfoRequest queryCompanyInfoRequest = new QueryCompanyInfoRequest();
        queryCompanyInfoRequest.setUserId(request.getUserId());
        queryCompanyInfoRequest.setCompanyId(request.getCompanyId());
        QueryCompanyInfoResponse queryCompanyInfoResponse = voyageRpcServiceClient.queryCompanyInfo(queryCompanyInfoRequest);
        //查询结果不能为空
        assertService.notNull(queryCompanyInfoResponse, ErrorCode.VOYAGE_COMPANY_INFO_DO_NOT_EXIST);
        assertService.isTrue(queryCompanyInfoResponse.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);

        //手机号有修改，验证码不能为空
        if (!StringUtil.equals(queryCompanyInfoResponse.getMpCompanyInformationDto().getPhoneNumber(),request.getPhoneNumber())) {
            assertService.isTrue(StringUtils.isNotBlank(request.getCode()),ErrorCode.VOYAGE_PHONE_NUMBER_CHANGE_NEED_CODE);
            assertService.isTrue(StringUtils.isNotBlank(request.getRequestId()),ErrorCode.VOYAGE_PHONE_NUMBER_CHANGE_NEED_CODE);
            // 获取短信验证码校验结果
            OtpVerifyResult otpResult = getVerifyOtpResult(request.getCode(),
                    request.getCountryCode(), request.getPhoneNumber(), request.getEnvInfo().getLang(),
                    request.getRequestId());
            assertService.isTrue(otpResult.isSuccess(), ErrorCode.VERIFY_OTP_FAILED);
        }
        AddMarketPlaceApplyRequest addMarketPlaceApplyRequest = AddVoyageApplyRequest.buildRequestDTO(request);
        //获取用户等级
        MembershipLevelAndBenefitsQueryResult membershipLevelAndBenefitsQueryResult = imemberProdClient
                .queryMembershipLevelAndBenefits(UserAuthenticationContext.get().getUserId());
        membershipLevelAndBenefitsQueryResult.getLevelCode();
        addMarketPlaceApplyRequest.setUserLevel(membershipLevelAndBenefitsQueryResult.getLevelCode());
        //添加
        AddMarketPlaceApplyResponse addMarketPlaceApplyResponse = voyageRpcServiceClient.addMarketPlaceApply(addMarketPlaceApplyRequest);

        assertService.notEquals(EcoErrorCodeMappingEnum.APPLY_INFO_HAS_EXIST.getExternalCode(),
                addMarketPlaceApplyResponse.getErrorCode(),
                EcoErrorCodeMappingEnum.getByCode(addMarketPlaceApplyResponse.getErrorCode()),
                "apply info has exist");
        assertService.isTrue(addMarketPlaceApplyResponse.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);
        return AddVoyageApplyResponse
                .buildResponse(addMarketPlaceApplyResponse);
    }


    @Override
    public UpdateVoyageApplyResponse updateMarketPlace(UpdateVoyageApplyRequest request) {
        LogUtil.info(logger, String.format("VoyageServiceImpl:updateMarketPlace:end , request: %s",
                JSONUtil.objectToString(request)));
        QueryCompanyInfoRequest queryCompanyInfoRequest = new QueryCompanyInfoRequest();
        queryCompanyInfoRequest.setCompanyId(request.getCompanyId());
        queryCompanyInfoRequest.setUserId(request.getUserId());
        QueryCompanyInfoResponse queryCompanyInfoResponse = voyageRpcServiceClient
                .queryCompanyInfo(queryCompanyInfoRequest);
        assertService.isTrue(queryCompanyInfoResponse.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);

        //通过查询出的接口，进行逻辑判断，如果存在非fail状态，不能提交
        QueryMarketPlaceApplyListRequest applyListRequest=new QueryMarketPlaceApplyListRequest();
        applyListRequest.setUserId(request.getUserId());
        applyListRequest.setMarketPlace(request.getMarketplace());
        applyListRequest.setSite(request.getSite());
        List<String> platformReviews= Arrays.asList(PlatformReviewEnum.INIT.getCode(),PlatformReviewEnum.SUCCESS.getCode(),PlatformReviewEnum.PROCESS.getCode());
        applyListRequest.setCompanyId(request.getCompanyId());
        applyListRequest.setPlatformReviews(platformReviews);
        PaginatorResult<List<MpOpenMarketApplyRecordDto>> list = voyageRpcServiceClient
            .queryMarketPlaceList(applyListRequest);
        assertService.isTrue(list.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);
        assertService.isTrue(list.getPaginator().getTotalCount()==0, ErrorCode.VOYAGE_REPEAT_APPLY_INFO);


        if (!StringUtil.equals(request.getPhoneNumber(),
                queryCompanyInfoResponse.getMpCompanyInformationDto().getPhoneNumber())) {
            assertService.notBlank(request.getCode(), ErrorCode.VOYAGE_PHONE_NUMBER_CHANGE_NEED_CODE);
            // 校验短信验证码
            OtpVerifyResult otpResult = getVerifyOtpResult(request.getCode(),
                    request.getCountryCode(), request.getPhoneNumber(), request.getEnvInfo().getLang(),
                    request.getRequestId());
            assertService.isTrue(otpResult.isSuccess(), ErrorCode.VERIFY_OTP_FAILED);
        }
        UpdateMarketPlaceApplyRequest updateMarketPlaceApplyRequest = UpdateVoyageApplyRequest
                .buildRequest(request);
        UpdateMarketPlaceApplyResponse updateMarketPlaceApplyResponse = voyageRpcServiceClient
                .updateMarketPlace(updateMarketPlaceApplyRequest);

        assertService.isTrue(updateMarketPlaceApplyResponse.isSuccess(), ErrorCode.UPDATE_MARET_FAIL);

        return UpdateVoyageApplyResponse.buildResponse(updateMarketPlaceApplyResponse);
    }

    /**
     * 查询站点信息
     * @param request
     * @return
     */
    @Override
    public QueryVoyageSitesInfoResponse querySiteInfo(QueryVoyageSitesInfoRequest request) {
        QueryMarketPlaceRuleInfoRequest queryMarketPlaceRuleInfoRequest = new QueryMarketPlaceRuleInfoRequest(request.getMarketplace(),I18nUtils.getLocaleStringFromTraceContext());
        QueryMarketPlaceRuleInfoResponse queryMarketPlaceRuleInfoResponse = voyageRpcServiceClient.queryMarketPlaceSiteInfo(queryMarketPlaceRuleInfoRequest);
        assertService.isTrue(queryMarketPlaceRuleInfoResponse.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);
        return buildSiteInfoResponse(queryMarketPlaceRuleInfoResponse);
    }

    private QueryVoyageSitesInfoResponse buildSiteInfoResponse(
            QueryMarketPlaceRuleInfoResponse queryMarketPlaceRuleInfoResponse) {
        QueryVoyageSitesInfoResponse response = new QueryVoyageSitesInfoResponse();
        List<SiteRuleDto> siteList = queryMarketPlaceRuleInfoResponse
                .getSiteList();
        List<SiteLabelDto> siteLabelDtos = new ArrayList();
        if (CollectionUtils.isNotEmpty(siteList)) {
            for (SiteRuleDto siteRuleDto : siteList) {
                SiteLabelDto siteLabelDto = new SiteLabelDto();
                siteLabelDto.setLabel(siteRuleDto.getLabel());
                siteLabelDto.setValue(siteRuleDto.getSiteName());
                siteLabelDtos.add(siteLabelDto);
            }
        }

        BeanUtils.copyProperties(queryMarketPlaceRuleInfoResponse, response);
        List<MPSiteRuleDto> mpSiteRuleDtos = new ArrayList<>();
        if (CollectionUtils
                .isNotEmpty(queryMarketPlaceRuleInfoResponse.getMarketPlaceSiteRuleDtoList())) {
            for (MarketPlaceSiteRuleDto marketPlaceSiteRuleDto : queryMarketPlaceRuleInfoResponse
                    .getMarketPlaceSiteRuleDtoList()) {
                MPSiteRuleDto dto = new MPSiteRuleDto();
                dto.setKey(marketPlaceSiteRuleDto.getKey());
                if ("TRUE".equalsIgnoreCase(marketPlaceSiteRuleDto.getValue())) {
                    dto.setValue(Boolean.TRUE);
                } else {
                    dto.setValue(Boolean.FALSE);
                }
                mpSiteRuleDtos.add(dto);
            }
        }
        response.setSiteList(siteLabelDtos);
        response.setMpSiteRuleDtos(mpSiteRuleDtos);
        response.setSentMailExcuteTime(queryMarketPlaceRuleInfoResponse.getSentMailExcuteTimeByListString());
        //远航,平台,参数中心扩展配置
        response.setMarketPlaceExtConfigDto(queryMarketPlaceRuleInfoResponse.getMarketPlaceExtConfigDto());
        LogUtil.info(logger, String.format("VoyageServiceImpl:查询站点结果:%s",
                JSONUtil.objectToString(response)));
        return response;
    }

    /**
     * 发送短信验证码
     *
     * @param request
     * @return
     */

    @Override
    public SentSmsCodeResponse sentSmsCode(SentSmsCodeRequest request) {
        LogUtil.info(logger, String.format("VoyageServiceImpl:sentSmsCode:start , request: %s", JSONUtil.objectToString(request)));
        SentSmsCodeResponse sentSmsCodeResponse = new SentSmsCodeResponse();
        OtpSendRequest otpSendRequest = buildSendOtpRequest(request);
        OtpSendResult OtpSendResult = otpService.sendOtp(otpSendRequest);
        assertService.isTrue(OtpSendResult.isSuccess(), ErrorCode.SEND_OTP_FAILED);
        Map<String, Object> result = new HashMap<>();
        result.put("otpProperty", OtpSendResult.getOtpProperty());
        sentSmsCodeResponse.setOtpProperty(result);
        sentSmsCodeResponse.setRequestId(OtpSendResult.getRequestId());

        LogUtil.info(logger, String.format("VoyageServiceImpl:sentSmsCode: , request: %s", JSONUtil.objectToString(request)));

        return sentSmsCodeResponse;
    }

    @Override
    public List<CityLabel> queryCityCodeLabel(QueryCityCodeLabelRequest request) {
        GetCityLabelRequest getCityLabelRequest = new GetCityLabelRequest();
        getCityLabelRequest.setCode(request.getCode());
        getCityLabelRequest.setLocal(I18nUtils.getLocaleStringFromTraceContext());
        GetCityLabelResponse cityLabel = voyageRpcServiceClient.getCityLabel(getCityLabelRequest);

        LogUtil.info(logger, String.format("VoyageServiceImpl:queryCityCodeLabel: , request: %s",
            JSONUtil.objectToString(request)));
        assertService.isTrue(cityLabel.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);

        return cityLabel.getLabelList();
    }

    /**
     * 开店催单
     * @param request
     * @return
     */
    @Override
    public ReminderResponse reminder(ReminderRequest request) {
        ReminderResponse response = new ReminderResponse();
        // 调用ibizecoprod查询接口，获取当前user的开店申请信息
        QueryMarketPlaceApplyRequest queryMarketPlaceApplyRequest = new QueryMarketPlaceApplyRequest();
        queryMarketPlaceApplyRequest.setApplyId(request.getApplyId());
        QueryMarketPlaceApplyResponse queryMarketPlaceApplyResponse = voyageRpcServiceClient.queryMarketPlaceApply(
                queryMarketPlaceApplyRequest);
        assertService.isTrue(queryMarketPlaceApplyResponse.isSuccess(), ErrorCode.BIZECOPROD_INVOKE_FAILED);
        MpOpenMarketApplyRecordDto mpOpenMarketApplyRecordDto = queryMarketPlaceApplyResponse.getMpOpenMarketApplyRecordDto();
        // 判断是否可以催单
        boolean canReminder = false;
        if (PlatformReviewEnum.INIT.getCode().equals(mpOpenMarketApplyRecordDto.getPlatformReview())) {
            assertService.isTrue(mpOpenMarketApplyRecordDto.getRemainingSubmission() != null,
                    ErrorCode.NOT_ALLOW_REMINDER_WITH_CURRENT_STATUS);
            if (Integer.valueOf(mpOpenMarketApplyRecordDto.getRemainingSubmission()) < 0) {
                canReminder = true;
            }
        }
        if (PlatformReviewEnum.PROCESS.getCode().equals(mpOpenMarketApplyRecordDto.getPlatformReview())) {
            assertService.isTrue(mpOpenMarketApplyRecordDto.getRemainingAudit() != null, ErrorCode.NOT_ALLOW_REMINDER_WITH_CURRENT_STATUS);
            if (Integer.valueOf(mpOpenMarketApplyRecordDto.getRemainingAudit()) < 0) {
                canReminder = true;
            }
        }
        // 如果不需要催单组装错误码
        assertService.isTrue(canReminder, ErrorCode.NOT_ALLOW_REMINDER_WITH_CURRENT_STATUS);
        // 否则调用ibizecoprod的催单接口，进行催单处理
        UpdateReminderInfoRequest updateReminderInfoRequest = new UpdateReminderInfoRequest();
        updateReminderInfoRequest.setApplyId(request.getApplyId());
        updateReminderInfoRequest.setUserId(UserAuthenticationContext.get().getUserId());
        UpdateReminderInfoResponse updateReminderInfoResponse = voyageRpcServiceClient.UpdateReminderInfo(updateReminderInfoRequest);
        assertService.isTrue(updateReminderInfoResponse.isSuccess(), ErrorCode.UPDATE_REMINDER_FAILED);
        return response;
    }

    @Override
    public QueryVoyageOnboardingInfoResponse queryOnboardingInfo(QueryVoyageOnboardingInfoRequest request) {
        QueryVoyageOnboardingInfoResponse response = new QueryVoyageOnboardingInfoResponse();
        QueryOnboardingInfoRequest queryOnboardingInfoRequest = new QueryOnboardingInfoRequest();
        queryOnboardingInfoRequest.setIpayUserId(UserAuthenticationContext.get().getUserId());
        queryOnboardingInfoRequest.setLanguagePrefer(request.getEnvInfo().getLang());

        QueryOnboardingInfoResponse queryOnboardingInfoResponse = voyageRpcServiceClient.queryOnboardingInfo(queryOnboardingInfoRequest);

        assertService.isTrue(null != queryOnboardingInfoResponse, ErrorCode.BIZECOPROD_INVOKE_FAILED);
        response.setCompanyLicenseCode(queryOnboardingInfoResponse.getCompanyLicenseCode());
        response.setCompanyName(queryOnboardingInfoResponse.getCompanyName());
        return response;
    }

    /**
     * 暂存 远航批量开店表单数据
     * @return
     */
    @Override
    public StoreMpApplyFormDataResponse storeMpApplyFormData(StoreMpApplyFormDataRequest request) {
        return null;
    }

    @Override
    public QueryMpApplyFormDataResponse queryMpApplyFormData(QueryMpApplyFormDataRequest request) {
        return null;
    }

    /**
     * 构建sentSmsCode() 入参
     *
     * @param request
     * @return
     */
    public OtpSendRequest buildSendOtpRequest(SentSmsCodeRequest request) {
        OtpSendRequest otpSendRequest = new OtpSendRequest();
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setTelNumber(request.getPhoneNumber());
        phoneNumber.setTelCode(request.getCountryCode());
        otpSendRequest.setPhoneNumber(phoneNumber);
        otpSendRequest.setScene(VOYAGE_MOBILE_OTP);
        otpSendRequest.setType(OtpTypeEnum.MOBILE.getCode());
        otpSendRequest.setLcoale(request.getEnvInfo().getLang());
        otpSendRequest.getEnvInfo().setLang(request.getEnvInfo().getLang());
        return otpSendRequest;
    }

    /**
     * 构建 queryCompanyInfo() 入参
     *
     * @param request
     * @return
     */

    public QueryCompanyInfoRequest getQueryCompanyInfoRequest(QueryVoyageCompanyInfoRequest request) {
        QueryCompanyInfoRequest queryCompanyInfoRequest = new QueryCompanyInfoRequest();
        queryCompanyInfoRequest.setRegistrationNo(request.getRegistrationNo());
        queryCompanyInfoRequest.setCompanyId(request.getCompanyId());
        queryCompanyInfoRequest.setLegalName(request.getLegalName());
        queryCompanyInfoRequest.setUserId(request.getUserId());
        return queryCompanyInfoRequest;
    }

    /**
     * 构建 queryCompanyInfo() 出参
     *
     * @param queryVoyageCompanyInfoResponse
     * @param queryCompanyInfoResponse
     */

    public void buildQueryVoyageCompanyInfoResponse(QueryVoyageCompanyInfoResponse queryVoyageCompanyInfoResponse, QueryCompanyInfoResponse queryCompanyInfoResponse) {

        queryVoyageCompanyInfoResponse.setCanModify(queryCompanyInfoResponse.getCanModify());

        MpCompanyInformationDto mpCompanyInformationDto = queryCompanyInfoResponse.getMpCompanyInformationDto();
        MpVoyageCompanyInformationDto mpVoyageCompanyInformationDto = new MpVoyageCompanyInformationDto();
        mpVoyageCompanyInformationDto.setApplyEmail(mpCompanyInformationDto.getApplyEmail());
        mpVoyageCompanyInformationDto.setApplyType(mpCompanyInformationDto.getApplyType());
        mpVoyageCompanyInformationDto.setBrand(mpCompanyInformationDto.getBrand());
        mpVoyageCompanyInformationDto.setCountryCode(mpCompanyInformationDto.getCountryCode());
        mpVoyageCompanyInformationDto.setCompanyId(mpCompanyInformationDto.getCompanyId());
        mpVoyageCompanyInformationDto.setCompanyType(mpCompanyInformationDto.getCompanyType());
        mpVoyageCompanyInformationDto.setCurrencyType(mpCompanyInformationDto.getCurrencyType());
        mpVoyageCompanyInformationDto.setCanDelete(mpCompanyInformationDto.getCanDelete());
        mpVoyageCompanyInformationDto.setExistinStoreLink((List<String>) JSONUtil.stringToObject(mpCompanyInformationDto.getExistinStoreLink(), List.class));
        mpVoyageCompanyInformationDto.setExpectedCapitalFlow(mpCompanyInformationDto.getExpectedCapitalFlow());
        mpVoyageCompanyInformationDto.setForeignOperator((List<String>) JSONUtil.stringToObject(mpCompanyInformationDto.getForeignOperator(), List.class));
        mpVoyageCompanyInformationDto.setHasVat((List<String>) JSONUtil.stringToObject(mpCompanyInformationDto.getHasVat(), List.class));
        mpVoyageCompanyInformationDto.setLegalName(mpCompanyInformationDto.getLegalName());
        mpVoyageCompanyInformationDto.setMainCategory(mpCompanyInformationDto.getMainCategory());
        mpVoyageCompanyInformationDto.setOperatorEmail(mpCompanyInformationDto.getOperatorEmail());
        mpVoyageCompanyInformationDto.setOverseasWarehouse((List<String>) JSONUtil.stringToObject(mpCompanyInformationDto.getOverseasWarehouse(), List.class));
        mpVoyageCompanyInformationDto.setPhoneNumber(mpCompanyInformationDto.getPhoneNumber());
        mpVoyageCompanyInformationDto.setProvince((List<String>) JSONUtil.stringToObject(mpCompanyInformationDto.getProvince(), List.class));
        mpVoyageCompanyInformationDto.setQqNumber(mpCompanyInformationDto.getQqNumber());
        mpVoyageCompanyInformationDto.setRegistrationNo(mpCompanyInformationDto.getRegistrationNo());
        mpVoyageCompanyInformationDto.setTotalSku(mpCompanyInformationDto.getTotalSku());
        mpVoyageCompanyInformationDto.setUserId(mpCompanyInformationDto.getUserId());
        mpVoyageCompanyInformationDto.setUsername(mpCompanyInformationDto.getUsername());
        mpVoyageCompanyInformationDto.setWeChat(mpCompanyInformationDto.getWeChat());
        mpVoyageCompanyInformationDto.setGmtCreate(mpCompanyInformationDto.getGmtCreate());
        mpVoyageCompanyInformationDto.setGmtModified(mpCompanyInformationDto.getGmtModified());
        queryVoyageCompanyInfoResponse.setMpVoyageCompanyInformationDto(mpVoyageCompanyInformationDto);
    }

    /**
     * 构建queryCompanyInfoList() 入参
     *
     * @param request
     * @return
     */

    public QueryCompanyInfoListRequest buildQueryCompanyInfoListRequest(QueryVoyageCompanyInfoListRequest request) {
        QueryCompanyInfoListRequest queryCompanyInfoListRequest = new QueryCompanyInfoListRequest();
        queryCompanyInfoListRequest.setUserId(request.getUserId());
        queryCompanyInfoListRequest.setCompanyId(request.getCompanyId());
        queryCompanyInfoListRequest.setPageNumber(request.getPageNumber());
        queryCompanyInfoListRequest.setPageSize(request.getPageSize());
        queryCompanyInfoListRequest.setCompanyType(request.getCompanyType());
        queryCompanyInfoListRequest.setApplyEmail(request.getApplyEmail());
        queryCompanyInfoListRequest.setLegalName(request.getLegalName());
        queryCompanyInfoListRequest.setApplyType(request.getApplyType());
        queryCompanyInfoListRequest.setMainCategory(request.getMainCategory());
        queryCompanyInfoListRequest.setRegistrationNo(request.getRegistrationNo());
        queryCompanyInfoListRequest.setExtParams(request.getExtParams());
        queryCompanyInfoListRequest.setGmtCreate(request.getGmtCreate());
        return queryCompanyInfoListRequest;
    }

    /**
     * 构建 queryCompanyInfoList() 出参
     *
     * @param queryPaginatorResult
     */
    public PaginatorResponse buildPaginatorResult(PaginatorResult<List<MpCompanyInformationDto>> queryPaginatorResult) {
        ArrayList<MpVoyageCompanyInformationDto> mpVoyageCompanyInformationDtos = new ArrayList<>();
        List<MpCompanyInformationDto> queryPaginatorResultValue = queryPaginatorResult.getValue();
        if (CollectionUtils.isNotEmpty(queryPaginatorResultValue)) {
            for (MpCompanyInformationDto mpCompanyInformationDto : queryPaginatorResultValue) {
                MpVoyageCompanyInformationDto mpVoyageCompanyInformationDto = new MpVoyageCompanyInformationDto();
                mpVoyageCompanyInformationDto
                        .setApplyEmail(mpCompanyInformationDto.getApplyEmail());
                mpVoyageCompanyInformationDto.setApplyType(mpCompanyInformationDto.getApplyType());
                mpVoyageCompanyInformationDto.setBrand(mpCompanyInformationDto.getBrand());
                mpVoyageCompanyInformationDto
                        .setCountryCode(mpCompanyInformationDto.getCountryCode());
                mpVoyageCompanyInformationDto.setCompanyId(mpCompanyInformationDto.getCompanyId());
                mpVoyageCompanyInformationDto
                        .setCompanyType(mpCompanyInformationDto.getCompanyType());
                mpVoyageCompanyInformationDto
                        .setCurrencyType(mpCompanyInformationDto.getCurrencyType());
                mpVoyageCompanyInformationDto.setCanDelete(mpCompanyInformationDto.getCanDelete());
                mpVoyageCompanyInformationDto.setExistinStoreLink((List<String>) JSONUtil
                        .stringToObject(mpCompanyInformationDto.getExistinStoreLink(), List.class));
                mpVoyageCompanyInformationDto
                        .setExpectedCapitalFlow(mpCompanyInformationDto.getExpectedCapitalFlow());
                mpVoyageCompanyInformationDto.setForeignOperator((List<String>) JSONUtil
                        .stringToObject(mpCompanyInformationDto.getForeignOperator(), List.class));
                mpVoyageCompanyInformationDto.setHasVat((List<String>) JSONUtil
                        .stringToObject(mpCompanyInformationDto.getHasVat(), List.class));
                mpVoyageCompanyInformationDto.setLegalName(mpCompanyInformationDto.getLegalName());
                mpVoyageCompanyInformationDto
                        .setMainCategory(mpCompanyInformationDto.getMainCategory());
                mpVoyageCompanyInformationDto
                        .setOperatorEmail(mpCompanyInformationDto.getOperatorEmail());
                mpVoyageCompanyInformationDto.setOverseasWarehouse((List<String>) JSONUtil
                        .stringToObject(mpCompanyInformationDto.getOverseasWarehouse(), List.class));
                mpVoyageCompanyInformationDto
                        .setPhoneNumber(mpCompanyInformationDto.getPhoneNumber());
                mpVoyageCompanyInformationDto.setProvince((List<String>) JSONUtil
                        .stringToObject(mpCompanyInformationDto.getProvince(), List.class));
                mpVoyageCompanyInformationDto.setQqNumber(mpCompanyInformationDto.getQqNumber());
                mpVoyageCompanyInformationDto
                        .setRegistrationNo(mpCompanyInformationDto.getRegistrationNo());
                mpVoyageCompanyInformationDto.setTotalSku(mpCompanyInformationDto.getTotalSku());
                mpVoyageCompanyInformationDto.setUserId(mpCompanyInformationDto.getUserId());
                mpVoyageCompanyInformationDto.setUsername(mpCompanyInformationDto.getUsername());
                mpVoyageCompanyInformationDto.setWeChat(mpCompanyInformationDto.getWeChat());
                mpVoyageCompanyInformationDto.setGmtCreate(mpCompanyInformationDto.getGmtCreate());
                mpVoyageCompanyInformationDto
                        .setGmtModified(mpCompanyInformationDto.getGmtModified());
                mpVoyageCompanyInformationDtos.add(mpVoyageCompanyInformationDto);
            }
        }

        PaginatorResponse paginatorResponse = PaginatorResponse.builder()
                .paginator(queryPaginatorResult.getPaginator())
                .extParams(queryPaginatorResult.getSelfExtParams())
                .value(mpVoyageCompanyInformationDtos).build();
        return paginatorResponse;
    }

    /**
     * 构建 updateCompanyInfo() 入参
     *
     * @param request
     * @return
     */
    public UpdateCompanyInfoRequest buildUpdateCompanyInfoRequest(UpdateVoyageCompanyInfoRequest request) {
        UpdateCompanyInfoRequest updateCompanyInfoRequest = new UpdateCompanyInfoRequest();
        updateCompanyInfoRequest.setApplyEmail(request.getApplyEmail());
        updateCompanyInfoRequest.setApplyType(request.getApplyType());
        updateCompanyInfoRequest.setBrand(request.getBrand());
        updateCompanyInfoRequest.setCountryCode(request.getCountryCode());
        updateCompanyInfoRequest.setCompanyId(request.getCompanyId());
        updateCompanyInfoRequest.setCompanyType(request.getCompanyType());
        updateCompanyInfoRequest.setCurrencyType(request.getCurrencyType());
        updateCompanyInfoRequest.setExtParams(request.getExtParams());
        updateCompanyInfoRequest.setExistinStoreLink(JSONUtil.objectToString(request.getExistinStoreLink()));
        updateCompanyInfoRequest.setExpectedCapitalFlow(request.getExpectedCapitalFlow());
        updateCompanyInfoRequest.setForeignOperator(JSONUtil.objectToString(request.getForeignOperator()));
        updateCompanyInfoRequest.setHasVat(JSONUtil.objectToString(request.getHasVat()));
        updateCompanyInfoRequest.setLegalName(request.getLegalName());
        updateCompanyInfoRequest.setMainCategory(request.getMainCategory());
        updateCompanyInfoRequest.setOperatorEmail(request.getOperatorEmail());
        updateCompanyInfoRequest.setOverseasWarehouse(JSONUtil.objectToString(request.getOverseasWarehouse()));
        updateCompanyInfoRequest.setPhoneNumber(request.getPhoneNumber());
        updateCompanyInfoRequest.setProvince(JSONUtil.objectToString(request.getProvince()));
        updateCompanyInfoRequest.setQqNumber(request.getQqNumber());
        updateCompanyInfoRequest.setRegistrationNo(request.getRegistrationNo());
        updateCompanyInfoRequest.setTotalSku(request.getTotalSku());
        updateCompanyInfoRequest.setUserId(request.getUserId());
        updateCompanyInfoRequest.setUsername(request.getUsername());
        updateCompanyInfoRequest.setWeChat(request.getWeChat());
        return updateCompanyInfoRequest;
    }

    /**
     * 构建 updateCompanyInfo() 出参
     *
     * @param updateVoyageCompanyInfoResponse
     * @param updateCompanyInfoResponse
     */
    public void buildUpdateCompanyInfoResponse(UpdateVoyageCompanyInfoResponse updateVoyageCompanyInfoResponse, UpdateCompanyInfoResponse updateCompanyInfoResponse) {
        updateVoyageCompanyInfoResponse.setCompanyId(updateCompanyInfoResponse.getCompanyId());
        updateVoyageCompanyInfoResponse.setGmtCreate(updateCompanyInfoResponse.getGmtCreate());
        updateVoyageCompanyInfoResponse.setLegalName(updateCompanyInfoResponse.getLegalName());
    }

    /**
     * 获取主体信息
     *
     * @param request
     * @return
     */
    public QueryCompanyInfoResponse getCompanyInfoResponse(UpdateVoyageCompanyInfoRequest request) {
        // 构建查询主体信息request
        QueryCompanyInfoRequest queryCompanyInfoRequest = new QueryCompanyInfoRequest();
        queryCompanyInfoRequest.setCompanyId(request.getCompanyId());
        queryCompanyInfoRequest.setUserId(request.getUserId());
        // 查询主体信息
        return voyageRpcServiceClient.queryCompanyInfo(queryCompanyInfoRequest);
    }

    /**
     * 校验短信验证码是否正确
     *
     * @param
     * @return
     */
    public OtpVerifyResult getVerifyOtpResult(String code, String countryCode, String phoneNumber, String lang, String requestId) {
        OtpVerifyRequest otpVerifyRequest = new OtpVerifyRequest();
        otpVerifyRequest.setOtpCode(code);
        PhoneNumber phone = new PhoneNumber();
        phone.setTelCode(countryCode);
        phone.setTelNumber(phoneNumber);
        otpVerifyRequest.setPhoneNumber(phone);
        otpVerifyRequest.setRequestId(requestId);
        otpVerifyRequest.setType(OtpTypeEnum.MOBILE.getCode());
        otpVerifyRequest.setScene(VOYAGE_MOBILE_OTP);
        otpVerifyRequest.setLocale(lang);
        otpVerifyRequest.getEnvInfo().setLang(lang);

        // 校验短信验证码是否正确
        OtpVerifyResult otpVerifyResult = otpService.verifyOtp(otpVerifyRequest);
        LogUtil.info(logger, String.format("VoyageServiceImpl:getVerifyOtpResult:end , response: %s", JSONUtil.objectToString(otpVerifyResult)));

        return otpVerifyResult;
    }

    /**
     * 构建 deleteCompanyInfo() 入参
     *
     * @param request
     * @return
     */
    private DeleteCompanyInfoRequest buildDeleteCompanyInfoRequest(DeleteVoyageCompanyInfoRequest request) {
        DeleteCompanyInfoRequest deleteCompanyInfoRequest = new DeleteCompanyInfoRequest();
        deleteCompanyInfoRequest.setCompanyId(request.getCompanyId());
        deleteCompanyInfoRequest.setUserId(request.getUserId());
        return deleteCompanyInfoRequest;
    }

    /**
     * 构建 deleteCompanyInfo() 出餐
     *
     * @param deleteVoyageCompanyInfoResponse
     * @param deleteCompanyInfoResponse
     */
    public void buildDeleteVoyageCompanyInfoResponse(DeleteVoyageCompanyInfoResponse deleteVoyageCompanyInfoResponse, DeleteCompanyInfoResponse deleteCompanyInfoResponse) {
        deleteVoyageCompanyInfoResponse.setSuccess(deleteCompanyInfoResponse.isSuccess());
    }

    /**
     * 构建addCompanyInfo() 入参
     *
     * @param request
     * @return
     */
    public AddCompanyInfoRequest buildAddCompanyInfoRequest(AddVoyageCompanyInfoRequest request) {
        AddCompanyInfoRequest addCompanyInfoRequest = new AddCompanyInfoRequest();
        addCompanyInfoRequest.setApplyEmail(request.getApplyEmail());
        addCompanyInfoRequest.setApplyType(request.getApplyType());
        addCompanyInfoRequest.setBrand(request.getBrand());
        addCompanyInfoRequest.setCountryCode(request.getCountryCode());
        addCompanyInfoRequest.setCompanyType(request.getCompanyType());
        addCompanyInfoRequest.setCurrencyType(request.getCurrencyType());
        addCompanyInfoRequest.setExtParams(request.getExtParams());
        addCompanyInfoRequest.setExistinStoreLink(JSONUtil.objectToString(request.getExistinStoreLink()));
        addCompanyInfoRequest.setExpectedCapitalFlow(request.getExpectedCapitalFlow());
        addCompanyInfoRequest.setForeignOperator(JSONUtil.objectToString(request.getForeignOperator()));
        addCompanyInfoRequest.setHasVat(JSONUtil.objectToString(request.getHasVat()));
        addCompanyInfoRequest.setLegalName(request.getLegalName());
        addCompanyInfoRequest.setMainCategory(request.getMainCategory());
        addCompanyInfoRequest.setOperatorEmail(request.getOperatorEmail());
        addCompanyInfoRequest.setOverseasWarehouse(JSONUtil.objectToString(request.getOverseasWarehouse()));
        addCompanyInfoRequest.setPhoneNumber(request.getPhoneNumber());
        addCompanyInfoRequest.setProvince(JSONUtil.objectToString(request.getProvince()));
        addCompanyInfoRequest.setQqNumber(request.getQqNumber());
        addCompanyInfoRequest.setRegistrationNo(request.getRegistrationNo());
        addCompanyInfoRequest.setTotalSku(request.getTotalSku());
        addCompanyInfoRequest.setUserId(request.getUserId());
        addCompanyInfoRequest.setUsername(request.getUsername());
        addCompanyInfoRequest.setWeChat(request.getWeChat());
        return addCompanyInfoRequest;
    }

    /**
     * 构建 addCompanyInfo() 出参
     *
     * @param addVoyageCompanyInfoResponse
     * @param addCompanyInfoResponse
     */
    public void buildAddVoyageCompanyInfoResponse(AddVoyageCompanyInfoResponse addVoyageCompanyInfoResponse, AddCompanyInfoResponse addCompanyInfoResponse) {
        addVoyageCompanyInfoResponse.setCompanyId(addCompanyInfoResponse.getCompanyId());
        addVoyageCompanyInfoResponse.setLegalName(addCompanyInfoResponse.getLegalName());
        addVoyageCompanyInfoResponse.setGmtCreate(addCompanyInfoResponse.getGmtCreate());
    }

    /**
     * 查询主体信息
     *
     * @param request
     * @return
     */
    public QueryCompanyInfoResponse getQueryCompanyInfoResponse(DeleteVoyageCompanyInfoRequest request) {
        QueryCompanyInfoRequest queryCompanyInfoRequest = new QueryCompanyInfoRequest();
        queryCompanyInfoRequest.setUserId(request.getUserId());
        queryCompanyInfoRequest.setCompanyId(request.getCompanyId());
        return voyageRpcServiceClient.queryCompanyInfo(queryCompanyInfoRequest);
    }
}

