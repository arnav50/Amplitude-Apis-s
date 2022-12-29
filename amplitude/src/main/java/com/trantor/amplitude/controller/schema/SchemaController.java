package com.trantor.amplitude.controller.schema;

import com.trantor.amplitude.controller.ResponseInterceptor;
import com.trantor.amplitude.service.schema.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchemaController {

    @Autowired
    SchemaService schemaService;

    @GetMapping("/streams")
    public ResponseEntity<String> getObjects() {
        return ResponseInterceptor.prepareResponseEntity(schemaService.getObjectsIDAndDesc());
    }

    @GetMapping("/stream/{streamId}/schema")
    public ResponseEntity<String> getObjectFields(@PathVariable String streamId) {
        return getobjectsMetadata(streamId);
    }

    private ResponseEntity<String> getobjectsMetadata(String streamId) {
        return ResponseInterceptor
                .prepareResponseEntity(schemaService.getSchemaObjectsMetaData(streamId));
    }
}
