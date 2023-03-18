package com.prototype.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.prototype.model.LogLine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {
	
	private static final String HOME = System.getProperty("user.dir");
	private static final String SEPARATOR = System.getProperty("file.separator");
	private static final String FILE_PATH = HOME + SEPARATOR + "logs" + SEPARATOR + "log-file.log";

	public static List<LogLine> readFile() {

		List<LogLine> logs = new ArrayList<>();

		String linha = "";
		int contador = 1;

		try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

			while ((linha = br.readLine()) != null) {

				StringBuilder sb = new StringBuilder();

				String[] split = linha.split("---");

				sb.append(split[0]).append("---")
				  .append(split[1]).append("---")
				  .append(split[2]).append("---")
				  .append(split[3].toUpperCase());
				
				logs.add(new LogLine(contador, sb.toString()));
				contador++;
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return logs;
	}
	
	public static void downloadFile(HttpServletResponse response) {
        File file = new File(FILE_PATH);
        final int BUFFER_SIZE = 4096;
        FileInputStream is;
        try {
            is = new FileInputStream(file);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=logs_"+ System.currentTimeMillis() +".txt");
            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = is.read(buffer)) != -1 ) {
                out.write(buffer, 0 , bytesRead);
            }
            is.close();
            out.close();
            
        } catch (IOException e) {
        	log.error(e.getMessage());
        }
	}
	
	public static void clearFile(){
		try (PrintWriter pw = new PrintWriter(new FileWriter(new File(FILE_PATH)), false);) {
			pw.flush();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}
}
