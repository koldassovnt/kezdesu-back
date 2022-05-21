package kz.sueta.clientservice.register.impl;

import kz.sueta.clientservice.exception.ui.RestException;
import kz.sueta.clientservice.register.ContentRegister;
import kz.sueta.clientservice.service_messaging.FileServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContentRegisterImpl implements ContentRegister {

    private final FileServiceClient fileServiceClient;

    @Autowired
    public ContentRegisterImpl(FileServiceClient fileServiceClient) {
        this.fileServiceClient = fileServiceClient;
    }

    @Override
    public String getContentBase64ById(String id) {
        String result = fileServiceClient.getFileById(id);

        if (result == null) {
            throw new RestException("Kz8SskZG8s :: there is no content by id = " + id);
        }

        return result;
    }
}
