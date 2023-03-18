package com.prototype.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogLine {
	private Integer numberLine;
	private String mensagem;
}
