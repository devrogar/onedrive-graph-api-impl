package com.devrogar.utility.msft.graph.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devrogar.utility.msft.graph.service.GraphService;
import com.devrogar.utility.msft.graph.service.UploadService;

/**
 * 
 * @author Rohit Garlapati
 *
 */
@RestController
@RequestMapping("/api")
public class UploadController {
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private GraphService graphService;
	
	

	
	@GetMapping("msft/rdir")
	public String getMsftAuth(@RequestParam(value = "code") String code) {
		return graphService.createGraphClient(code);
	}
	
	@GetMapping("upload/{filepath}")
	public String upload(@PathVariable String filepath) {
		if (uploadService.uploadFile(filepath)) {
			return "Success";
		}
		return "Failed";
	}
	
}
