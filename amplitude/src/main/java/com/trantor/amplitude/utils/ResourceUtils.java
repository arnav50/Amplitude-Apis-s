package com.trantor.amplitude.utils;


import com.trantor.amplitude.exception.ResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Slf4j
@Component
public class ResourceUtils {


    public static String getResourceData(Resource resource) {
        String resourceJson = "";
        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            resourceJson = FileCopyUtils.copyToString(reader);
            log.debug("Resource contents are :: {}", resourceJson);
        } catch (IOException e) {
            throw new ResourceException("Error reading resource file :: " + resource.getFilename(), e);
        }
        return resourceJson;
    }

    private ResourceUtils() {
    }
}
