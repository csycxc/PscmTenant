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

import com.banry.pscm.tenant.service.TenantException;
import com.banry.pscm.tenant.service.biz.DataTableModel;
import com.banry.pscm.tenant.service.biz.DynamicSource;
import com.banry.pscm.tenant.service.biz.DynamicSourceService;
import com.banry.pscm.tenant.service.biz.Tenant;
import com.banry.pscm.tenant.service.biz.TenantRealWithBLOBs;
import com.banry.pscm.tenant.service.biz.TenantService;

/**
 * 动态数据源Controller
 * @author chenJuan
 * @date 2018-04-16
 */
@Controller
@RequestMapping("/dynamicSource")
public class DynamicSourceController {

	@Autowired
	DynamicSourceService dynamicService;
	@Autowired
	TenantService tenService;
	
	private static Logger log = LoggerFactory.getLogger(DynamicSourceController.class);
	
	/**
	 * 查询全部数据
	 * @return data
	 */
	@RequestMapping("/getDynamicSourceList")
	@ResponseBody
	public Object getDynamicSourceList(){
		DataTableModel data = new DataTableModel();
		try {
			List<DynamicSource> allDynamicList = dynamicService.findDynamicSourceList();
			List<DynamicSource> dynamicList = new ArrayList<>();
			for(DynamicSource source : allDynamicList){
				Tenant tenant = tenService.findTenantBySourceCode(source.getSourceCode());
				if(tenant==null){
					dynamicList.add(source);
				}
			}
			data.setData(dynamicList);
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
	 * 根据Code查询数据
	 * @param code
	 * @return DynamicSource
	 */
	@RequestMapping("/getDynamicSourceByCode")
	@ResponseBody
	public Object getDynamicSourceByCode(String code){
		DynamicSource ds = null;
		try {
			ds = dynamicService.findDynamicSourceByCode(code);
		} catch (TenantException e) {
			log.error("TenantException异常：", e);
		} catch(Exception e){
			log.error("Exception异常：", e);
		}
		return ds;
	}
	
	/**
	 * 修改或添加数据
	 * @param dynamicSource
	 * @param flag
	 * @return map
	 */
	@RequestMapping("/addOrUpdateDynamicSource")
	@ResponseBody
	public Map addOrUpdateDynamicSource(DynamicSource dynamicSource,String flag){
		Map map = new HashMap();
		try {
			if(flag.equals("I")){
				DynamicSource source = dynamicService.findDynamicSourceByCode(dynamicSource.getSourceCode());
				if(source!=null){
					map.put("result", false);
					map.put("retMsg", "编号已经存在");
					return map;
				}
				
				//可设置数据源编号
				//dynamicSource.setSourcecode("");
				
				dynamicService.saveDynamicSource(dynamicSource);
				map.put("result", true);
				map.put("retMsg", "添加成功");
				return map;
			}
			dynamicService.updateDynamicSource(dynamicSource);
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
	 * 根据Code删除数据
	 * @param code
	 * @return map
	 */
	@RequestMapping("/deleteDynamicSource")
	@ResponseBody
	public Map deleteDynamicSource(String code){
		Map map = new HashMap();
		try {
			int i = dynamicService.deleteDynamicSource(code);
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
		} catch(Exception e){
			log.error("Exception异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
	
	@RequestMapping("/joinSource")
	@ResponseBody
	public Map joinSource(String tenantCode,String sourceCode){
		Map map=new HashMap<>();
		try {
			if(tenantCode!=null && sourceCode!=null){
				Tenant tenant = tenService.findTenantByCode(tenantCode);
				DynamicSource source = dynamicService.findDynamicSourceByCode(sourceCode);
				tenant.setSource(source);
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
	
	@RequestMapping("/delJoinSource")
	@ResponseBody
	public Map delJoinSource(String tenantCode){
		Map map=new HashMap<>();
		try {
			if(tenantCode!=null){
				int i = tenService.setSourceCodeNull(tenantCode);
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
	 * @param sourceCode
	 * @return
	 */
	@RequestMapping("/editSourceJoin")
    public String editSourceJoin(Model model, String tenantCode){
		try {
			model.addAttribute("tenantCode",tenantCode);
		} catch(Exception e){
			log.error("Exception异常：", e);
		}
        return "dynamicSource/dynamicSource-join";
    }	
	
	/**
	 * 跳转新增编辑页面
	 * @param model
	 * @param sourceCode
	 * @return
	 */
	@RequestMapping("/editDynamicSource")
    public String editDynamicSource(Model model, String sourceCode){
		try {
			DynamicSource source = dynamicService.findDynamicSourceByCode(sourceCode);
			model.addAttribute("source",source);
		} catch(TenantException e){
			log.error("TenantException异常：", e);
		} catch(Exception e){
			log.error("Exception异常：", e);
		}
        return "dynamicSource/dynamicSource-edit";
    }
}
