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

import com.banry.pscm.tenant.service.TenantException;
import com.banry.pscm.tenant.service.biz.DataTableModel;
import com.banry.pscm.tenant.service.biz.Tenant;
import com.banry.pscm.tenant.service.biz.TenantRealService;
import com.banry.pscm.tenant.service.biz.TenantRealWithBLOBs;
import com.banry.pscm.tenant.service.biz.TenantService;

/**
 * 企业数据Controller
 * @author chenJuan
 * @date 2018-04-13
 */
@Controller
@RequestMapping("/tenantReal")
public class TenantRealController {
	
	@Autowired
	TenantRealService tenRealService;
	@Autowired
	TenantService tenService;
	
	private static Logger log = LoggerFactory.getLogger(TenantRealController.class);
	
	/**
	 * 查询所有的企业信息
	 * @return data
	 */
	@RequestMapping("/getTenRealList")
	@ResponseBody
	public Object getTenRealList(){
		DataTableModel data = new DataTableModel();
		try {
			List<TenantRealWithBLOBs> allRealList = tenRealService.findTenantRealList();
			data.setData(allRealList);
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
	 * 查询所有的未关联租户的企业信息
	 * @return data
	 */
	@RequestMapping("/getTenRealListNotRelationTenant")
	@ResponseBody
	public Object getTenRealListNotRelationTenant(){
		DataTableModel data = new DataTableModel();
		try {
			List<TenantRealWithBLOBs> list = tenRealService.findTenantRealListNotRelationTenant();
			data.setData(list);
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
	 * 根据RealCode查询企业信息
	 * @param realCode
	 * @return
	 */
	@RequestMapping("/getTenRealByCode")
	@ResponseBody
	public Object getTenRealByCode(String realCode){
		TenantRealWithBLOBs realWithBLOBs = new TenantRealWithBLOBs();
		try {
			realWithBLOBs = tenRealService.findTenantRealByCode(realCode);
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
		} catch(Exception e){
			log.error("Exception异常：", e);
		}
		return realWithBLOBs;
	}
	
	/**
	 * 添加或修改企业信息
	 * @param record
	 * @param flag
	 * @return map
	 */
	@RequestMapping("/addOrUpdateTenReal")
	@ResponseBody
	public Map addOrUpdateTenReal(TenantRealWithBLOBs record,String flag){
		Map map=new HashMap();
		try {
			if ("I".equals(flag)) {
				TenantRealWithBLOBs bs = tenRealService.findTenantRealByCode(record.getRealCode());
				if(bs!=null){
					map.put("result", false);
					map.put("retMsg", "编号已存在");
					return map;
				}
				
				//可设置实体编号
				//record.setRealcode("");
				
				tenRealService.saveTenantReal(record);
				map.put("result", true);
				map.put("retMsg", "添加成功");
				return map;
			}
			tenRealService.updateTenantRealSelective(record);
			map.put("result", true);
			map.put("retMsg", "修改成功");
			return map;
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		} catch(Exception e){
			log.error("Exception异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 删除企业信息
	 * @param code
	 * @return map
	 */
	@RequestMapping("/deleteTenReal")
	@ResponseBody
	public Map deleteTenReal(String code){
		Map map=new HashMap();
		try {
//			Tenant ten = tenService.findTenantByRealCode(code);
//			if(ten!=null){
//				ten.setTenantReal(null);
//			}
			int i = tenRealService.deleteTenantReal(code);
			if(i==0){
				map.put("result", false);
				map.put("retMsg", "删除失败");
				return map;
			}else{
				map.put("result", true);
				map.put("retMsg", "删除成功");
				return map;
			}
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
//		} catch(Exception e){
//			log.error("Exception异常：", e);
//			map.put("result", false);
//			map.put("retMsg", e.getMessage());
//			return map;
		}
	}
	
	
	@RequestMapping("/joinTenantReal")
	@ResponseBody
	public Map joinTenantReal(String tenantCode,String realCode){
		Map map=new HashMap<>();
		try {
			if(tenantCode!=null && realCode!=null){
				Tenant tenant = tenService.findTenantByCode(tenantCode);
				TenantRealWithBLOBs bs = tenRealService.findTenantRealByCode(realCode);
				tenant.setTenantReal(bs);
				tenService.updateTenantByCodeSelective(tenant);
				map.put("result", true);
				map.put("retMsg", "关联成功");
				return map;
			}
			map.put("result", false);
			map.put("retMsg", "关联失败");
			return map;
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		} catch(Exception e){
			log.error("Exception异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
	
	@RequestMapping("/delJoinTenantReal")
	@ResponseBody
	public Map delJoinTenantReal(String tenantCode){
		Map map=new HashMap<>();
		try {
			if(tenantCode!=null){
				int i = tenService.setRealCodeNull(tenantCode);
				if(i>0){
					map.put("result", true);
					map.put("retMsg", "解除成功");
					return map;
				}else{
					map.put("result", false);
					map.put("retMsg", "解除失败");
					return map;
				}
			}
			map.put("result", false);
			map.put("retMsg", "解除失败");
			return map;
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		} catch(Exception e){
			log.error("Exception异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 跳转关联页面
	 * @param model
	 * @param realCode
	 * @return
	 */
	@RequestMapping("/editTenRealJoin")
    public String editTenRealJoin(Model model, String tenantCode){
		try {
			model.addAttribute("tenantCode",tenantCode);
		} catch(Exception e){
			log.error("Exception异常：", e);
		}
        return "tenantReal/tenantReal-join";
    }
	
	/**
	 * 跳转新增编辑页面
	 * @param model
	 * @param realCode
	 * @return
	 */
	@RequestMapping("/editTenReal")
    public String editTenantReal(Model model, String realCode){
		try {
			TenantRealWithBLOBs bs = tenRealService.findTenantRealByCode(realCode);
			model.addAttribute("tenantReal",bs);
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
		}  catch(Exception e){
			log.error("Exception异常：", e);
		}
        return "tenantReal/tenantReal-edit";
    }
	
}
