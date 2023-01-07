public class JediMasterDeserializer {

 public JediMaster parse(String lineFeed) {
   JediMaster records = new JediMaster();
       records.firstName = lineFeed.substring(0,20).trim();
       records.lastName = lineFeed.substring(21,40).trim();
       records.level = lineFeed.substring(41,50).trim();
       records.jediClass = lineFeed.substring(51,60).trim();
       records.age = Integer.parseInt(lineFeed.substring(61,70).trim());
      return records;
 }
}

