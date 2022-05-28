package kz.sueta.clientservice.register;

import kz.sueta.clientservice.dto.services.request.*;
import kz.sueta.clientservice.dto.services.response.CategoryListResponse;
import kz.sueta.clientservice.dto.services.response.EventListResponse;
import kz.sueta.clientservice.dto.ui.response.ClientEventListResponse;
import kz.sueta.clientservice.dto.ui.response.ClientEventResponse;
import kz.sueta.clientservice.dto.ui.response.ClientInfoResponse;
import kz.sueta.clientservice.dto.ui.response.MessageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface EventRegister {

    ClientEventListResponse listEvents(EventListFilter filter);

    ClientEventResponse detailEvent(DetailRequest request);

    MessageResponse saveEvent(SaveEventRequest saveRequest, String clientId);

    MessageResponse editEvent(EditEventRequest editEventRequest, String clientId);

    ClientInfoResponse joinEvent(DetailRequest detailRequest, String clientId);

    MessageResponse qrEvent(DetailRequest detailRequest, String clientId);

    EventListResponse clientEvents(String clientId);

    EventListResponse clientParticipatedEvents(String clientId);

    CategoryListResponse categoryList();

    MessageResponse saveEventContent(MultipartFile file, String id, String clientId);

    MessageResponse complainEvent(ComplainEventRequest request, String clientId);
}
