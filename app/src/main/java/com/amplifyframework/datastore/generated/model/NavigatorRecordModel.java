package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the NavigatorRecordModel type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "NavigatorRecordModels", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class NavigatorRecordModel implements Model {
  public static final QueryField ID = field("NavigatorRecordModel", "id");
  public static final QueryField ORIGIN = field("NavigatorRecordModel", "origin");
  public static final QueryField DEST = field("NavigatorRecordModel", "dest");
  public static final QueryField ESTIMATE_TIME = field("NavigatorRecordModel", "estimate_time");
  public static final QueryField ESTIMATE_DISTANCE = field("NavigatorRecordModel", "estimate_distance");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String origin;
  private final @ModelField(targetType="String") String dest;
  private final @ModelField(targetType="String") String estimate_time;
  private final @ModelField(targetType="String") String estimate_distance;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getOrigin() {
      return origin;
  }
  
  public String getDest() {
      return dest;
  }
  
  public String getEstimateTime() {
      return estimate_time;
  }
  
  public String getEstimateDistance() {
      return estimate_distance;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private NavigatorRecordModel(String id, String origin, String dest, String estimate_time, String estimate_distance) {
    this.id = id;
    this.origin = origin;
    this.dest = dest;
    this.estimate_time = estimate_time;
    this.estimate_distance = estimate_distance;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      NavigatorRecordModel navigatorRecordModel = (NavigatorRecordModel) obj;
      return ObjectsCompat.equals(getId(), navigatorRecordModel.getId()) &&
              ObjectsCompat.equals(getOrigin(), navigatorRecordModel.getOrigin()) &&
              ObjectsCompat.equals(getDest(), navigatorRecordModel.getDest()) &&
              ObjectsCompat.equals(getEstimateTime(), navigatorRecordModel.getEstimateTime()) &&
              ObjectsCompat.equals(getEstimateDistance(), navigatorRecordModel.getEstimateDistance()) &&
              ObjectsCompat.equals(getCreatedAt(), navigatorRecordModel.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), navigatorRecordModel.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getOrigin())
      .append(getDest())
      .append(getEstimateTime())
      .append(getEstimateDistance())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("NavigatorRecordModel {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("origin=" + String.valueOf(getOrigin()) + ", ")
      .append("dest=" + String.valueOf(getDest()) + ", ")
      .append("estimate_time=" + String.valueOf(getEstimateTime()) + ", ")
      .append("estimate_distance=" + String.valueOf(getEstimateDistance()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static NavigatorRecordModel justId(String id) {
    return new NavigatorRecordModel(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      origin,
      dest,
      estimate_time,
      estimate_distance);
  }
  public interface BuildStep {
    NavigatorRecordModel build();
    BuildStep id(String id);
    BuildStep origin(String origin);
    BuildStep dest(String dest);
    BuildStep estimateTime(String estimateTime);
    BuildStep estimateDistance(String estimateDistance);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String origin;
    private String dest;
    private String estimate_time;
    private String estimate_distance;
    @Override
     public NavigatorRecordModel build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new NavigatorRecordModel(
          id,
          origin,
          dest,
          estimate_time,
          estimate_distance);
    }
    
    @Override
     public BuildStep origin(String origin) {
        this.origin = origin;
        return this;
    }
    
    @Override
     public BuildStep dest(String dest) {
        this.dest = dest;
        return this;
    }
    
    @Override
     public BuildStep estimateTime(String estimateTime) {
        this.estimate_time = estimateTime;
        return this;
    }
    
    @Override
     public BuildStep estimateDistance(String estimateDistance) {
        this.estimate_distance = estimateDistance;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String origin, String dest, String estimateTime, String estimateDistance) {
      super.id(id);
      super.origin(origin)
        .dest(dest)
        .estimateTime(estimateTime)
        .estimateDistance(estimateDistance);
    }
    
    @Override
     public CopyOfBuilder origin(String origin) {
      return (CopyOfBuilder) super.origin(origin);
    }
    
    @Override
     public CopyOfBuilder dest(String dest) {
      return (CopyOfBuilder) super.dest(dest);
    }
    
    @Override
     public CopyOfBuilder estimateTime(String estimateTime) {
      return (CopyOfBuilder) super.estimateTime(estimateTime);
    }
    
    @Override
     public CopyOfBuilder estimateDistance(String estimateDistance) {
      return (CopyOfBuilder) super.estimateDistance(estimateDistance);
    }
  }
  
}
