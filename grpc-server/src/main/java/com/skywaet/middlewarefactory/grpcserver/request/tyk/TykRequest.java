package com.skywaet.middlewarefactory.grpcserver.request.tyk;

import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import coprocess.CoprocessObject;
import com.skywaet.middlewarefactory.factorycommon.model.Phase;

public interface TykRequest extends BaseRequest {

    Phase getRequestPhase();

    String getApiId();

    String getOrgId();

    CoprocessObject.Object getRequestObject();
}
