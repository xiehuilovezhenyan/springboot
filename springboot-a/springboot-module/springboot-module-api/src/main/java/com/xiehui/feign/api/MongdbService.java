package com.xiehui.feign.api;

import com.xiehui.common.core.exception.KnowledgeException;

public interface MongdbService {

	public void findAll() throws KnowledgeException;

}
