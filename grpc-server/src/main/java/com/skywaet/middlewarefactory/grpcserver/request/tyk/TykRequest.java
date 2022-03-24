package com.skywaet.middlewarefactory.grpcserver.request.tyk;

import com.skywaet.middlewarefactory.grpcserver.model.Phase;
import com.skywaet.middlewarefactory.grpcserver.request.BaseRequest;
import coprocess.CoprocessCommon;
import coprocess.CoprocessObject;

public interface TykRequest extends BaseRequest {

    Phase getRequestPhase();

    String getApiId();

    String getOrgId();

    CoprocessObject.Object getRequestObject();
}
