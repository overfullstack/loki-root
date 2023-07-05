package org.revcloud.loki.common.json;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.revcloud.loki.common.json.JsonReaderUtils.anyMap;
import static org.revcloud.loki.common.json.JsonReaderUtils.list;
import static org.revcloud.loki.common.json.JsonReaderUtils.nextString;
import static org.revcloud.loki.common.json.JsonReaderUtils.obj;
import static org.revcloud.loki.common.json.JsonReaderUtils.skipValue;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.ToJson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.revcloud.loki.common.adapters.EpochAdapter;

class JsonReaderUtilsKtTest {

  private static final String TEST_RESOURCES_PATH = "src/test/resources/";

  @Test
  @DisplayName("fromJson: PQ payload --> PlaceQuoteInputRep")
  void sObjectReqToObjectInputRep() {
    final var pqInputRep = JsonPojoUtils.<PlaceQuoteInputRepresentation>jsonFileToPojo(
        PlaceQuoteInputRepresentation.class, TEST_RESOURCES_PATH + "pq-payload.json",
        List.of(new EpochAdapter(), new PQInputRepUnMarshaller()));
    assertNotNull(pqInputRep);
  }

  static class PQInputRepUnMarshaller {
    @FromJson
    PlaceQuoteInputRepresentation fromJson(JsonReader reader) {
      return obj(PlaceQuoteInputRepresentation::new, reader, (pqir, key1) -> {
        switch (key1) {
          case "pricingPref": pqir.setPricingPref(PricingPreferenceEnum.valueOf(nextString(reader))); break;
          case "graph":
            pqir.setGraph(obj(ObjectGraphInputRepresentation::new, reader, (ogi, key2) -> {
              switch(key2) {
                case "graphId": ogi.setGraphId(nextString(reader)); break;
                case "records":
                  final var oripl = new ObjectWithReferenceInputRepresentationList();
                  ogi.setRecords(oripl);
                  oripl.setRecordsList(list(ObjectWithReferenceInputRepresentation::new, reader, (orir, key3) -> {
                    switch (key3) {
                      case "referenceId": orir.setReferenceId(nextString(reader)); break;
                      case "record":
                        final var oirm = new ObjectInputRepresentationMap();
                        orir.setRecord(oirm);
                        oirm.setRecordBody(anyMap(reader)); break;
                      default: skipValue(reader);
                    }})); break;
                default: skipValue(reader);
              }})); break;
          default: skipValue(reader);
        }});
    }

    @ToJson
    void toJson(JsonWriter writer, PlaceQuoteInputRepresentation placeQuoteInputRepresentation) {
      // noop
    }
  }
  
  enum PricingPreferenceEnum {
    Force,
    Skip,
    System;
  }
  static class PlaceQuoteInputRepresentation {
    private ObjectGraphInputRepresentation graph;
    private boolean isSetGraph;

    private PricingPreferenceEnum pricingPref;

    private boolean isSetPricingPref;

    public ObjectGraphInputRepresentation getGraph() {
      return this.graph;
    }

    public PricingPreferenceEnum getPricingPref() {
      return pricingPref;
    }
    
    public void setGraph(ObjectGraphInputRepresentation graph) {
      this.graph = graph;
      this.isSetGraph = true;
    }
    
    public void setPricingPref(PricingPreferenceEnum pricingPref) {
      this.pricingPref = pricingPref;
      this.isSetPricingPref = true;
    }

    public boolean _isSetGraph() {
      return this.isSetGraph;
    }

    public boolean _isSetPricingPref() {
      return this.isSetPricingPref;
    }
  }
  
  static class ObjectGraphInputRepresentation {

    private String graphId;

    private ObjectWithReferenceInputRepresentationList records;

    private boolean isSetGraphId;
    private boolean isSetRecords;

    /**
     * set the graphId of the request
     */
    
    public void setGraphId(String graphId) {
      this.graphId = graphId;
      this.isSetGraphId = true;
    }

    /**
     * @return the graph id
     */
    public String getGraphId() {
      return this.graphId;
    }

    /**
     * Set the records in the graph request. Wish we could call this setRecordList but that makes serialization and
     * deserialization less user friendly because we have to resort to using "recordList" as a JSON key.
     */
    
    public void setRecords(ObjectWithReferenceInputRepresentationList records) {
      this.records = records;
      this.isSetRecords = true;
    }

    /**
     * @return records comprising the graph request. Wish we could call this getRecordList but that makes serialization
     *         and deserialization less user friendly because we have to resort to using "recordList" as a JSON key.
     */
    public ObjectWithReferenceInputRepresentationList getRecords() {
      return this.records;
    }

    public boolean _isSetGraphId() {
      return this.isSetGraphId;
    }

    public boolean _isSetRecords() {
      return this.isSetRecords;
    }

  }

  static class ObjectWithReferenceInputRepresentationList {

    private List<ObjectWithReferenceInputRepresentation> recordsList;
    private boolean isSetRecordsList;

    public ObjectWithReferenceInputRepresentationList() {
      super();
      this.recordsList = new ArrayList<>();
    }

    /**
     * Set the records in the graph request
     */
    
    public void setRecordsList(List<ObjectWithReferenceInputRepresentation> recordsList) {
      this.recordsList = recordsList;
      this.isSetRecordsList = true;
    }

    /**
     * @return records comprising the graph request
     */
    public List<ObjectWithReferenceInputRepresentation> getRecordsList() {
      return this.recordsList;
    }

    public boolean _isSetRecordsList() {
      return this.isSetRecordsList;
    }

  }

  static class ObjectWithReferenceInputRepresentation {

    private String referenceId;
    private ObjectInputRepresentationMap record;

    private boolean isSetReferenceId;
    private boolean isSetRecord;

    /**
     * Set the wrapper around the SObject record that this request represents. Wish we could call this
     * setObjectRepresentation but that makes serialization and deserialization less user friendly because we have to
     * resort to using "objectRepresentation" as a JSON key.
     */
    
    public void setRecord(ObjectInputRepresentationMap record) {
      this.record = record;
      this.isSetRecord = true;
    }

    /**
     * @return the wrapper around the SObject record that this request represents. Wish we could call this
     *         getObjectRepresentation but that makes serialization and deserialization less user friendly because we
     *         have to resort to using "objectRepresentation" as a JSON key.
     */
    public ObjectInputRepresentationMap getRecord() {
      return this.record;
    }

    /**
     * set the reference id of this SObject
     */
    
    public void setReferenceId(String referenceId) {
      this.referenceId = referenceId;
      this.isSetReferenceId = true;
    }

    /**
     * @return the reference id of this SObject
     */
    public String getReferenceId() {
      return this.referenceId;
    }

    public boolean _isSetRecord() {
      return this.isSetRecord;
    }

    public boolean _isSetReferenceId() {
      return this.isSetReferenceId;
    }

  }

  static class ObjectInputRepresentationMap {

    private Map<String, Object> recordBody;
    private boolean isSetRecordBody;

    public ObjectInputRepresentationMap() {
      this.recordBody = new HashMap<>();
    }
    /**
     * Set the SObject record that this request represents
     */
    
    public void setRecordBody(Map<String, Object> recordBody) {
      this.recordBody = recordBody;
      this.isSetRecordBody = true;
    }

    /**
     * @return the SObject record that this request represents
     */
    public Map<String, Object> getRecordBody() {
      return this.recordBody;
    }

    public boolean _isSetRecordBody() {
      return this.isSetRecordBody;
    }

  }
}
