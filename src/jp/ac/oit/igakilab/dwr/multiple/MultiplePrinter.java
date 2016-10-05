
package jp.ac.oit.igakilab.dwr.multiple;

import java.util.ArrayList;
import java.util.List;

/**
 * DWRでJSから呼ばれるメソッドはすべてpublicでなければならない．また，必要なクラスはすべてdwr.xmlに定義されている必要がある．
 * @author Hiroshi
 *
 */
public class MultiplePrinter {
	public List<String> execute(MultipleForm input) throws InvalidValueException {
	    List<String> list = new ArrayList<>();
	    int max = input.getMax();
	    int multiple = input.getMultiple();


	    if (multiple < 0 || max < 0){
	    	throw new InvalidValueException("倍数は 正の整数(>0)でなければいけません．現在の値：" + multiple);
	    }
	    for(int i= 1; i<=max; i++){
	    	if(i % multiple == 0){
	    		list.add("ryokun");
	    	}else{
	    		list.add(Integer.toString(i));
	    	}
	    }

	    return list;
	}


    public String helloWorld(String name){
    	return name + ":HelloWorld";

    }
}