package com.banry.pscm.tenant.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.banry.pscm.tenant.service.TenantException;
import com.banry.pscm.tenant.service.biz.BizAuthority;
import com.banry.pscm.tenant.service.biz.BizAuthorityService;
import com.banry.pscm.tenant.service.biz.DataTableModel;
import com.banry.pscm.tenant.service.biz.PscmBusiness;
import com.banry.pscm.tenant.service.biz.Tenant;
import com.banry.pscm.tenant.service.biz.TenantRealWithBLOBs;
import com.banry.pscm.tenant.service.biz.TenantService;

/**
 * 功能模块Controller
 * 
 * @author chenJuan
 * @date 2018-04-16
 */
@Controller
@RequestMapping("/bizAuthority")
public class BizAuthorityController {

	@Autowired
	BizAuthorityService bizAuthService;
	@Autowired
	TenantService tenantService;

	private static Logger log = LoggerFactory.getLogger(BizAuthorityController.class);

	/**
	 * 查询所有业务模块
	 * 
	 * @return data
	 */
	@RequestMapping("/getBizAuthList")
	@ResponseBody
	public Object getBizAuthList() {
		DataTableModel data = new DataTableModel();
		try {
			List<PscmBusiness> bizList = bizAuthService.findPscmBusinessList();
			data.setData(bizList);
			return data;
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			return null;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return null;
		}
	}

	/**
	 * 根据code查找业务模块及该业务模块授予的租户
	 * 
	 * @param code
	 * @return data
	 */
	@RequestMapping("/getBizAuthByCode")
	@ResponseBody
	public Object getBizAuthByCode(String code) {
		List<Object> list = new ArrayList<>();
		try {
			PscmBusiness ps = bizAuthService.findPscmBusinessByCode(code);
			list.add(ps);
			// 查询模块授权的租户
			List<BizAuthority> bizListByCode = bizAuthService.findBizAuthorityListByBizCode(code);
			for (BizAuthority biz : bizListByCode) {
				Tenant tenant = tenantService.findTenantByCode(biz.getTenantCode());
				list.add(tenant);
			}
			return list;
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			return null;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return null;
		}
	}

	/**
	 * 查询指定租户授权的模块
	 * 
	 * @param bizCode
	 * @return
	 */
	@RequestMapping("/getBizAuthByTenantCode")
	@ResponseBody
	public Object getBizAuthByTenantCode(String tenantCode) {
		DataTableModel data = new DataTableModel();
		try {
			List<BizAuthority> bizListByTenCode = bizAuthService.findBizAuthorityListByTenantCode(tenantCode);
			List<PscmBusiness> businessList = new ArrayList<>();
			for(BizAuthority biz : bizListByTenCode){
				PscmBusiness business = bizAuthService.findPscmBusinessByCode(biz.getBizCode());
				businessList.add(business);
			}
			data.setData(businessList);
			return data;
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			return null;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return null;
		}
	}

	/**
	 * 添加或修改业务模块
	 * 
	 * @param ps
	 * @param flag
	 * @return map
	 */
	@RequestMapping("/addOrUpdateBusiness")
	@ResponseBody
	public Map addOrUpdateBusiness(PscmBusiness ps, String flag) {
		Map map = new HashMap();
		try {
			if (flag.equals("I")) {
				PscmBusiness business = bizAuthService.findPscmBusinessByCode(ps.getBizCode());
				if (business != null) {
					map.put("result", false);
					map.put("retMsg", "编号已经存在");
					return map;
				}

				// 可设置模块编号
				// ps.setBizcode("");

				bizAuthService.savePscmBusiness(ps);
				map.put("result", true);
				map.put("retMsg", "添加成功");
				return map;
			}
			bizAuthService.updatePscmBusinessSelective(ps);
			map.put("result", true);
			map.put("retMsg", "修改成功");
			return map;
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}

	/**
	 * 根据指定业务模块code删除业务模块
	 * 
	 * @param bizCode
	 * @return map
	 */
	@RequestMapping("/deleteBusiness")
	@ResponseBody
	public Map deletePscmBusiness(String bizCode) {
		Map map = new HashMap();
		try {
			// 根据code判断是否有租户绑定该模块，如果有，禁止删除，如果没有，可直接删除模块
			List<BizAuthority> list = bizAuthService.findBizAuthorityListByBizCode(bizCode);
			if (list.size() > 0) {
				map.put("result", false);
				map.put("retMsg", "该模块已被授权，禁止删除");
				return map;
			}
			int i = bizAuthService.deletePscmBusiness(bizCode);
			if (i == 0) {
				map.put("result", false);
				map.put("retMsg", "不存在该业务模块");
				return map;
			}
			map.put("result", true);
			map.put("retMsg", "删除成功");
			return map;
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}

	/**
	 * 对指定的租户的业务模块进行授权
	 * 
	 * @param bizAuthList
	 * @return map
	 */
	@RequestMapping("/joinBizAuthority")
	@ResponseBody
	public Map joinBizAuthority(@RequestBody List<BizAuthority> bizAuthList) {
		Map map = new HashMap();
		try {
			if(bizAuthList.size()>0){
				for (BizAuthority biz : bizAuthList) {
					List<BizAuthority> list = bizAuthService.findBizAuthorityListByTenantCode(biz.getTenantCode());
					for(BizAuthority b : list){
						if(biz.getBizCode().equals(b.getBizCode())){
							map.put("result", false);
							map.put("retMsg", "该模块已授权");
							return map;
						}
					}
					PscmBusiness business = bizAuthService.findPscmBusinessByCode(biz.getBizCode());
					if(business!=null)biz.setBizChineseName(business.getBizChineseName());
				}
				bizAuthService.joinBizAuthority(bizAuthList);
				map.put("result", true);
				map.put("retMsg", "授权成功");
				return map;
			}else{
				map.put("result", false);
				map.put("retMsg", "授权失败");
				return map;
			}
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}

	/**
	 * 对指定的租户的业务模块进行解除
	 * 
	 * @param bizAuthList
	 * @return map
	 */
	@RequestMapping("/deleteJoinBizAuthority")
	@ResponseBody
	public Map deleteJoinBizAuthority(@RequestBody List<BizAuthority> bizAuthList) {
		Map map = new HashMap();
		try {
			if(bizAuthList.size()>0){
				int i = bizAuthService.deleteJoinBizAuthority(bizAuthList);
				if (i == 0) {
					map.put("result", false);
					map.put("retMsg", "解除失败");
					return map;
				}
				map.put("result", true);
				map.put("retMsg", "解除成功");
				return map;
			}else{
				map.put("result", false);
				return map;
			}
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
	
	@RequestMapping("/findUnauthorizedBusiness")
	@ResponseBody
	public Object findUnauthorizedBusiness(String tenantCode){
		DataTableModel data = new DataTableModel();
		try {
			List<BizAuthority> list = bizAuthService.findBizAuthorityListByTenantCode(tenantCode);
			List<PscmBusiness> allList = bizAuthService.findPscmBusinessList();
			List<PscmBusiness> busList = new ArrayList<>();
			List<String> listCode = new ArrayList<>();
			List<String> allListCode = new ArrayList<>();
			for(PscmBusiness bus : allList){
				allListCode.add(bus.getBizCode());
			}
			for(BizAuthority b : list){
				listCode.add(b.getBizCode());
			}
			allListCode.removeAll(listCode);
			for(String str : allListCode){
				PscmBusiness business = bizAuthService.findPscmBusinessByCode(str);
				busList.add(business);
			}
			data.setData(busList);
			return data;
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			return null;
		} catch(Exception e){
			log.error("Exception异常：", e);
			return null;
		}
	}
	
	/**
	 * 跳转授权页面
	 * @param model
	 * @param tenantCode
	 * @return
	 */
	@RequestMapping("/editBizAuthority")
    public String editBizAuthority(Model model, String tenantCode){
		try {
			model.addAttribute("tenantCode",tenantCode);
		} catch(Exception e){
			log.error("Exception异常：", e);
		}
        return "tenant/bizAuthority-edit";
    }
	/**
	 * 新增编辑功能模块页面
	 * @param model
	 * @param realCode
	 * @return
	 */
	@RequestMapping("/editBusiness")
    public String editBusiness(Model model, String bizCode){
		try {
			PscmBusiness business = bizAuthService.findPscmBusinessByCode(bizCode);
			model.addAttribute("business",business);
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
		} catch(Exception e){
			log.error("Exception异常：", e);
		}
        return "bizAuthority/business-edit";
    }
}
