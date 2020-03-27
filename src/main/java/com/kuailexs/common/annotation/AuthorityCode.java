package com.kuailexs.common.annotation;

import java.lang.annotation.*;

/**
 * 权限验证级别
 */
public enum AuthorityCode {

	ALL,
	LOGIN,
	ADMIN,
	;
	private AuthorityCode() {
	}
}