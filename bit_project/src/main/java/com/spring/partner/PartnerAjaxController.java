package com.spring.partner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartnerAjaxController {
	
	@Autowired
	private PartnerService service;

	@RequestMapping(value="/getChartJSON.pr", produces = "application/json;charset=utf-8")
	@ResponseBody
	public List<PartnerVO> getChartJSONGET() {
		System.out.println("컨트롤러 진입.");
		List<PartnerVO> list = service.getChartjson();
		System.out.println("컨트롤러에서 리턴하기 전.");
		return list;
	}
	
	@RequestMapping(value="/getChartJSON_filter.pr", produces = "application/json;charset=utf-8")
	@ResponseBody
	public List<PartnerVO> getChartJSONGET_filter() {
		System.out.println("컨트롤러 진입(필터).");
		List<PartnerVO> list = service.getChartjson();
		System.out.println("컨트롤러에서 리턴하기 전(필터).");
		return list;
	}
}
