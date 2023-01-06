public class JediMasterDeserializer{
 public JediMaster parse(String lineFeed) {
 JediMaster record = new JediMaster();
 record.firstName = lineFeed.substring(0,20).trim();
 record.lastName = lineFeed.substring(21,40).trim();
 record.level = lineFeed.substring(41,50).trim();
 record.jediClass = lineFeed.substring(51,60).trim();
 record.age = Integer.parseInt(lineFeed.substring(61,70).trim());
 return record; 
 } 
}