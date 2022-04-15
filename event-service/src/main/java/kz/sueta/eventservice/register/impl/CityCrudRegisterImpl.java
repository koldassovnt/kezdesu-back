package kz.sueta.eventservice.register.impl;

import com.google.common.base.Strings;
import kz.sueta.eventservice.dto.request.CreateCityRequest;
import kz.sueta.eventservice.dto.request.DetailRequest;
import kz.sueta.eventservice.dto.request.DictionaryFilter;
import kz.sueta.eventservice.dto.request.EditCityRequest;
import kz.sueta.eventservice.dto.response.CityDetailResponse;
import kz.sueta.eventservice.dto.response.CityListResponse;
import kz.sueta.eventservice.dto.response.EventResponse;
import kz.sueta.eventservice.entity.CityDictionary;
import kz.sueta.eventservice.exception.NoDataByIdException;
import kz.sueta.eventservice.register.CityCrudRegister;
import kz.sueta.eventservice.repository.CityDictionaryDao;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CityCrudRegisterImpl implements CityCrudRegister {

    private final CityDictionaryDao cityDictionaryDao;
    private final EntityManager entityManager;

    @Autowired
    public CityCrudRegisterImpl(CityDictionaryDao cityDictionaryDao,
                                EntityManager entityManager) {
        this.cityDictionaryDao = cityDictionaryDao;
        this.entityManager = entityManager;
    }

    @Override
    public void saveCity(CreateCityRequest request) {
        CityDictionary cityDictionary = new CityDictionary();
        cityDictionary.cityId = UUID.randomUUID().toString();
        cityDictionary.cityLabel = request.cityLabel;

        if (request.latitude == null) {
            request.latitude = 0.0;
        }

        if (request.longitude == null) {
            request.longitude = 0.0;
        }

        cityDictionary.latitude = request.latitude;
        cityDictionary.longitude = request.longitude;
        cityDictionary.actual = true;

        cityDictionaryDao.save(cityDictionary);
    }

    @Override
    public void editCity(EditCityRequest request) {
        CityDictionary cityDictionary =
                cityDictionaryDao.findCityDictionaryByCityIdAndActual(request.cityId, true);

        if (cityDictionary == null) {
            throw new NoDataByIdException();
        }

        if (!Strings.isNullOrEmpty(request.cityLabel)) {
            cityDictionary.cityLabel = request.cityLabel;
        }

        if (request.latitude != null) {
            cityDictionary.latitude = request.latitude;
        }

        if (request.longitude != null) {
            cityDictionary.longitude = request.longitude;
        }

        cityDictionaryDao.save(cityDictionary);
    }

    @Override
    public void deleteCity(DetailRequest request) {
        cityDictionaryDao.updateCityActual(false, request.id);
    }

    @SneakyThrows
    @Override
    public CityListResponse listCity(DictionaryFilter filter) {

        if (filter.limit == null) {
            filter.limit = 10;
        }

        if (filter.offset == null) {
            filter.offset = 0;
        }

        if (filter.actual == null) {
            filter.actual = true;
        }

        String sql =
                " select c.city_id as cityId, " +
                " c.city_label     as label, " +
                " c.actual        as actual, " +
                " c.latitude      as latitude, " +
                " c.longitude     as longitude " +
                " from city_dictionary c " +
                " where c.actual = ? " +
                " limit " + filter.limit + " offset " + filter.offset + " " ;

        TypedQuery<CityDetailResponse> query = entityManager.createQuery(sql, CityDetailResponse.class);
        query.setParameter("actual", filter.actual);

        List<CityDetailResponse> responseList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/event-service",
                "admin",
                "admin")) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setBoolean(1, filter.actual);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        CityDetailResponse detailResponse = new CityDetailResponse();
                        detailResponse.cityId = rs.getString("cityId");
                        detailResponse.label = rs.getString("label");
                        detailResponse.latitude = rs.getDouble("latitude");
                        detailResponse.longitude = rs.getDouble("longitude");
                        detailResponse.actual = rs.getBoolean("actual");
                        responseList.add(detailResponse);
                    }
                }
            }
        }

        return CityListResponse.of(responseList);
    }

    @SneakyThrows
    @Override
    public CityDetailResponse detailCity(DetailRequest request) {

        String sql =
                " select c.city_id as cityId, " +
                        " c.city_label     as label, " +
                        " c.actual        as actual, " +
                        " c.latitude      as latitude, " +
                        " c.longitude     as longitude " +
                        " from city_dictionary c " +
                        " where c.actual = true and c.city_id = ? ";

        CityDetailResponse detailResponse = new CityDetailResponse();

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/event-service",
                "admin",
                "admin")) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, request.id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        detailResponse.cityId = rs.getString("cityId");
                        detailResponse.label = rs.getString("label");
                        detailResponse.latitude = rs.getDouble("latitude");
                        detailResponse.longitude = rs.getDouble("longitude");
                        detailResponse.actual = rs.getBoolean("actual");
                    }
                }
            }
        }


        return detailResponse;
    }
}
