package com.skywaet.middlewarefactory.grpcserver.converter.tyk;

import com.skywaet.middlewarefactory.grpcserver.converter.RequestConverter;
import com.skywaet.middlewarefactory.grpcserver.request.tyk.TykRequest;
import coprocess.CoprocessObject;

public interface TykRequestConverter extends RequestConverter<CoprocessObject.Object, TykRequest> {
}
