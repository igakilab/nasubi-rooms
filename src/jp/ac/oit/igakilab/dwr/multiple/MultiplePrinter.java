
package jp.ac.oit.igakilab.dwr.multiple;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

/**
 * DWRでJSから呼ばれるメソッドはすべてpublicでなければならない．また，必要なクラスはすべてdwr.xmlに定義されている必要がある．
 * @author Hiroshi
 *
 */
public class MultiplePrinter {
	public List<String> execute(MultipleForm input) throws InvalidValueException {
	    List<String> list = new ArrayList<>();

	    List<BeaconPos> bpos = getBeaconPositions(102);
	    for(int i=0;i<bpos.size(); i++){
	    	BeaconPos p = bpos.get(i);
	    	String str = "x = " + p.getX() + ", y = " + p.getY();
	    	list.add(str);
	    }
	    return list;
	}

	/**
	 * ビーコンの座標の一覧を取得します
	 * @param beaconId 対象のビーコンIDです
	 * @return
	 */
	private List<BeaconPos> getBeaconPositions(int beaconId){
		//クライアントの作成
		MongoClient client = new MongoClient("150.89.234.253");
		MongoCollection<Document> col = client
			.getDatabase("myproject-room").getCollection("beacons1mz");

		//クエリーの作成
		Bson filter = Filters.eq("minor", beaconId);

		//結果の取得
		FindIterable<Document> result = col.find(filter);
		List<BeaconPos> positions = new ArrayList<BeaconPos>();

		//変換
		for(Document doc : result){
			BeaconPos pos = new BeaconPos();
			pos.setDate(doc.getDate("date"));
			pos.setX(doc.getDouble("x座標"));
			pos.setY(doc.getDouble("y座標"));
			positions.add(pos);
			if( positions.size() > 3 ){
				positions.remove(0);
			}
		}

		client.close();
		return positions;
	}
}