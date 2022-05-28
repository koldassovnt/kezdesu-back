package kz.sueta.eventservice.register;

import kz.sueta.eventservice.dto.response.ComplainListResponse;

import java.sql.SQLException;

public interface ComplainRegister {

    ComplainListResponse complainList() throws SQLException;
}
