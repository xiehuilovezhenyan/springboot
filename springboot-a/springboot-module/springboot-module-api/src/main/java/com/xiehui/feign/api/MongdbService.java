package com.xiehui.feign.api;

import com.xiehui.common.core.exception.CustomException;

public interface MongdbService {

	public void findAll() throws CustomException;

}
