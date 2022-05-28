package kz.sueta.eventservice.register.impl;

import kz.sueta.eventservice.dto.response.ComplainListResponse;
import kz.sueta.eventservice.dto.response.ComplainResponse;
import kz.sueta.eventservice.register.ComplainRegister;
import kz.sueta.eventservice.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ComplainRegisterImpl implements ComplainRegister {

    private final Environment environment;

    @Autowired
    public ComplainRegisterImpl(
            Environment environment) {
        this.environment = environment;
    }

    @Override
    public ComplainListResponse complainList() throws SQLException {

        List<ComplainResponse> complainResponseList = new ArrayList<>();

        try (Connection connection = DbUtil.getConnection(environment)) {
            try (PreparedStatement ps = connection.prepareStatement("" +
                    "select x.complain_id, x.client_id, x.complain_text, x2.event_id, x2.label " +
                    "from complain x " +
                    "left join event_complain x1 on x1.complain_id = x.complain_id " +
                    "left join event x2 on x2.event_id = x1.event_id ")) {

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        ComplainResponse response = new ComplainResponse();
                        response.complainId = rs.getString("complain_id");
                        response.clientId = rs.getString("client_id");
                        response.complainText = rs.getString("complain_text");
                        response.eventId = rs.getString("event_id");
                        response.eventName = rs.getString("label");
                        complainResponseList.add(response);
                    }
                }
            }
        }

        return ComplainListResponse.of(complainResponseList);
    }
}
