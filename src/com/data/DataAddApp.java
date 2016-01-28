package com.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.CommonMethods;

public class DataAddApp {

  public static List<Map<String, String>> getTestDataForPositiveTests()
  {    
    List<Map<String , String>> tests  = new ArrayList<Map<String,String>>();
    
    Map<String,String> testData = new HashMap<String, String>();
    testData.put(   "platformType",    "Android"                        );
    testData.put(   "name"        ,    CommonMethods.getUniqAppName()   );
    testData.put(   "description" ,    "Description app"                );
    testData.put(   "appIconFile" ,    "icon200x200.png"                );
    testData.put(   "category"    ,    "Business"                       );
    tests.add(0,testData);
    
    testData = new HashMap<String, String>();
    testData.put(   "platformType",    "Android"                        );
    testData.put(   "name"        ,    CommonMethods.getUniqAppName()   );
    testData.put(   "description" ,    null                             );
    testData.put(   "appIconFile" ,    null                             );
    testData.put(   "category"    ,    "Finance"                        );
    tests.add(1,testData);

    testData = new HashMap<String, String>();
    testData.put(   "platformType",    "iOS"                            );
    testData.put(   "name"        ,    CommonMethods.getUniqAppName()   );
    testData.put(   "description" ,    null                             );
    testData.put(   "appIconFile" ,    null                             );
    testData.put(   "category"    ,    "Catalogs"                       );
    tests.add(2,testData);

    // Data with unicode string
    testData = new HashMap<String, String>();
    testData.put(   "platformType",    null                             );
    testData.put(   "name"        ,    "Моя Апэкашечка © " + 
                                CommonMethods.getUniqAppName()          );
    testData.put(   "description" ,    null                             );
    testData.put(   "appIconFile" ,    null                             );
    testData.put(   "category"    ,    "Games"                          );
    tests.add(3,testData);
    
    return tests;
  }
  
  public static List<Map<String, String>> getTestDataForNegativeTests()
  {
    Map<String,String> testData = new HashMap<String, String>();
    List<Map<String , String>> tests  = new ArrayList<Map<String,String>>();
    
    testData.put(   "name"        ,    null                             );
    testData.put(   "category"    ,    null                             );
    tests.add(0,testData);
    
    testData = new HashMap<String, String>();
    testData.put(   "name"        ,    CommonMethods.getUniqAppName()   );
    testData.put(   "category"    ,    null                             );
    tests.add(1,testData);
    
    testData = new HashMap<String, String>();
    testData.put(   "name"        ,    null                             );
    testData.put(   "category"    ,    "Games"                          );
    tests.add(2,testData);

    testData = new HashMap<String, String>();
    testData.put(   "name"        ,    CommonMethods.getUniqAppName()   );
    testData.put(   "appIconFile" ,    "icon256x256.png"                );
    testData.put(   "category"    ,    "Business"                       );
    tests.add(3,testData);

    testData = new HashMap<String, String>();
    testData.put(   "name"        ,    null                             );
    testData.put(   "category"    ,    null                             );
    testData.put(   "appIconFile" ,    "icon256x256.png"                );
    tests.add(4,testData);
    
    return tests;
  }
}
