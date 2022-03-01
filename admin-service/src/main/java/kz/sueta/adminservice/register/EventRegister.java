package kz.sueta.adminservice.register;

import kz.sueta.adminservice.dto.services.request.SaveEventRequest;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;

public interface EventRegister {

    MessageResponse saveEvent(SaveEventRequest saveRequest);
}
