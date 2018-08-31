package com.lcf.erp.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/*
	加密的工具类
*/
public class EncryptUtil {

	//加密（MD5）
	public static String encode(String source, String salt) {
		/*
		 * 加密
		 * 	source：加密的内容
		 * 	salt：该参数保证相同内容加密后返回不同的内容。一般使用随机数或用户ID。
		 * 	num: 加密次数
		 */
		Md5Hash md5 = new Md5Hash(source, salt, 2);
		return md5.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(encode("sunwukong", "sunwukong"));
	}
	
}
