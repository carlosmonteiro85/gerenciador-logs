package com.prototype.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prototype.core.PropertiesApplication;
import com.prototype.model.LogLine;
import com.prototype.util.FileUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ControllerLogs {

	private final PropertiesApplication properties;

	@RequestMapping(value = "logs", params = "type")
	public String logs(@RequestParam(value = "type", defaultValue = "", required = false) String type, Model model) {
		List<LogLine> collect = FileUtil.readFile().stream().filter(log -> log.getMensagem().contains(type))
				.collect(Collectors.toList());
		model.addAttribute("logs", collect);
		model.addAttribute("properties", properties);

		return "data";
	}

	@RequestMapping(value = "download")
	public void downloadLogs(HttpServletResponse response) {
		FileUtil.downloadFile(response);
	}

	@RequestMapping(value = "delete")
	public String delete() {
		FileUtil.clearFile();
		return "redirect:/logs?type=";
	}
}
