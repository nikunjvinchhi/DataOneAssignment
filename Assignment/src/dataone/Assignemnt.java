package dataone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Assignemnt {

	/**
	 * @param args
	 * @author Nikunj
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dataFile;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String key="";
        String value;
        HashMap<String,Double> temp=new HashMap<String, Double>();
        HashMap<String,Double> price=new HashMap<String, Double>();
        HashMap<String,String> product_map=new HashMap<String, String>();
        try {
        	//taking input 
        	Scanner sc=new Scanner(System.in);
        	String product_user=sc.nextLine();
        	String[] product1=product_user.split(" ");
        	dataFile=product1[0];
        	String userProduct=product1[1]+","+product1[2];
        	br = new BufferedReader(new FileReader(dataFile));
        	
        	/*parsing the csvfile and creating the mapping between product id and price 
        		& product id and product*/
        	
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] product= line.split(cvsSplitBy);
                value=product[1];
                
                for(int i=2;i<product.length;i++){
                	if(key.equals(""))
                		key=product[i];
                	else
                		key=key+","+product[i];
                }
                key=key.trim();
                //System.out.println(key);
                if(!price.containsKey(product[0]))
                	price.put(product[0], Double.parseDouble(value));
                else
                	price.put(product[0], Double.parseDouble(product[1])+price.get(product[0]));
                if(!product_map.containsKey(product[0]))
                	product_map.put(product[0],key);
                else
                	product_map.put(product[0],product_map.get(product[0])+","+key);
                key="";
            }
            
            // Creating final mapping taking key as Productid and product and value is price
            
            for(Map.Entry m:product_map.entrySet()){
            	String Key=m.getKey().toString();
            	String Value=m.getValue().toString();
            	temp.put(Key+","+Value, price.get(Key));
            	//System.out.println(m.getKey()+" "+m.getValue());  
            }
            
            //below logic to find the minimum cost value and product id
            
            Double cost=0.0;
            Double min_cost=0.0;
            int i=0;
            int status=0;
            String product_id="";
            for(Map.Entry m:temp.entrySet()){  
            	//System.out.println(m.getKey()+" "+m.getValue());
            	
            	//if temp hashmap key contains the userProduct then we consider its cost 
            	
            	if(m.getKey().toString().contains(userProduct)){
            		cost=Double.parseDouble(m.getValue().toString());
            		
            		//if current cost is less than the previous then we update and also update product_id
            		
            		if(i==0){
            			min_cost=cost;
            			product_id=m.getKey().toString().split(",")[0];
            		}
            		else
            		{
            			if(min_cost>cost){
            				min_cost=cost;
            				product_id=m.getKey().toString().split(",")[0];
            			}
            		}
            		i++;
            	}
            	else
            		status++;
            }
            
            //if we find the product then it will print the product id and cost otherwise none.
            
            if(status!=temp.size())
            	System.out.println(product_id+","+min_cost);
            else
            	System.out.println("None");
        }
        catch (Exception e) {
                e.printStackTrace();
            }       
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } 
    }
}