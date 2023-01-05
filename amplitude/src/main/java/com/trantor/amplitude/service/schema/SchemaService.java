package com.trantor.amplitude.service.schema;


import com.trantor.amplitude.enums.SchemaObjectEnum;
import com.trantor.amplitude.utils.ResourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
@Slf4j
public class SchemaService {
    @Value("classpath:/input/amplitudeStreamsSchema.json")
    Resource amplitudeStreamsSchema;

    @Value("classpath:/input/amplitudeSchemaFieldActive_User.json")
    Resource amplitudeSchemaFieldActive_Users_Counts;

    @Value("classpath:/input/amplitudeSchemaFieldAnnotations.json")
    Resource amplitudeSchemaFieldAnnotations;

    @Value("classpath:/input/amplitudeSchemaFieldCohorts.json")
    Resource amplitudeSchemaFieldCohorts;

    @Value("classpath:/input/amplitudeSchemaFieldEvent.json")
    Resource amplitudeSchemaFieldEvent;

    @Value("classpath:/input/amplitudeSchemaFieldAverage_Session_Length.json")
    Resource amplitudeSchemaFieldAverage_Session_Length;


    public String getObjectsIDAndDesc() {
        return ResourceUtils.getResourceData(amplitudeStreamsSchema);
    }


    public String getSchemaObjectsMetaData(String streamId) {
        if (SchemaObjectEnum.Active_Users_Counts.getStreamObjectName().equals(streamId)) {
            return getJsonResponseFromFile(amplitudeSchemaFieldActive_Users_Counts);
        } else if (SchemaObjectEnum.Annotations.getStreamObjectName().equals(streamId)) {
            return getJsonResponseFromFile(amplitudeSchemaFieldCohorts);
        } else if (SchemaObjectEnum.Average_Session_Length.getStreamObjectName().equals(streamId)) {
            return getJsonResponseFromFile(amplitudeSchemaFieldAnnotations);
        } else if (SchemaObjectEnum.Cohorts.getStreamObjectName().equals(streamId)) {
            return getJsonResponseFromFile(amplitudeSchemaFieldEvent);
        } else if (SchemaObjectEnum.Events.getStreamObjectName().equals(streamId)) {
            return getJsonResponseFromFile(amplitudeSchemaFieldAverage_Session_Length);
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public String getJsonResponseFromFile(Resource inputResource) {
        String schemaFieldDetails = "";
        try (Reader reader = new InputStreamReader(inputResource.getInputStream())) {
            schemaFieldDetails = FileCopyUtils.copyToString(reader);
            log.debug(" Input Resource of filename {} :: {}", inputResource.getFilename(),
                    schemaFieldDetails);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error while reading " + inputResource.getFilename() + " from file.", e);
        }
        return schemaFieldDetails;
    }

}
