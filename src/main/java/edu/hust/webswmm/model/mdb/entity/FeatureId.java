package edu.hust.webswmm.model.mdb.entity;

import edu.hust.webswmm.model.mdb.nonentity.AllFeatures;

/**
 * 要素和要素ID的实体类
 */

public class FeatureId extends AllFeatures {
     private String getId;//获取的ID
     private String featuresType;//ID所对应的要素类型

     public String getGetId() {
          return getId;
     }

     public void setGetId(String getId) {
          this.getId = getId;
     }

     public String getFeaturesType() {
          return featuresType;
     }

     public void setFeaturesType(String featuresType) {
          this.featuresType = featuresType;
     }
}
