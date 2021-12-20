package com.skywaet.middlewarefactory.grpcserver.service.configuration;

import java.util.List;

public interface IConfigurationService {

    List<String> getMiddlewaresForRequest(coprocess.CoprocessCommon.HookType type, String method, String url);

}
