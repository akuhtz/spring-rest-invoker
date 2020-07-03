package com.github.ggeorgovassilis.springjsonmapper.support;

import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

public class Utils {

	public static byte[] get(String classpathResource) throws Exception {
		ClassPathResource r = new ClassPathResource(classpathResource);
		InputStream in = r.getInputStream();
		byte[] b = FileCopyUtils.copyToByteArray(in);
		in.close();
		return b;
	}

	public static String sget(String classpathResource) throws Exception {
		String content = new String(get(classpathResource), "UTF-8");
		
		return content.replaceAll("\r\n", "\n");
	}

}
