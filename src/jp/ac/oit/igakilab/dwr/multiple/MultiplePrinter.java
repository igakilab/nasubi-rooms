
package jp.ac.oit.igakilab.dwr.multiple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

/**
 * DWRでJSから呼ばれるメソッドはすべてpublicでなければならない．また，必要なクラスはすべてdwr.xmlに定義されている必要がある．
 * @author Hiroshi
 *
 */
public class MultiplePrinter {
	public List<Mozyao> execute() throws InvalidValueException {
	    List<Mozyao> list = new ArrayList<>();
	    List<Integer> minors = Arrays.asList(101, 102, 103);

	    for(int i=0; i<minors.size(); i++){
	    	//最新の座標をpに取ってくる
	    	BeaconPos p = getBeaconPositions(minors.get(i));

	    	//Mozyaoに値を設定する
	    	Mozyao m = new Mozyao();
	    	m.minor = minors.get(i);
	    	if( p != null ){
	    		m.x = p.getX();
	    		m.y = p.getY();
	    	}

	    	//返却するリストに入れる
	    	list.add(m);
	    }

	    return list;
	}

	public static class Mozyao{
		private int minor;
		private double x,y;

		public int getMinor() {
			return minor;
		}
		public void setMinor(int minor) {
			this.minor = minor;
		}
		public double getX() {
			return x;
		}
		public void setX(double x) {
			this.x = x;
		}
		public double getY() {
			return y;
		}
		public void setY(double y) {
			this.y = y;
		}

	}


	/**
	 * ビーコンの座標の一覧を取得します
	 * @param beaconId 対象のビーコンIDです
	 * @return
	 */
	private BeaconPos getBeaconPositions(int beaconId){
		//クライアントの作成
		MongoClient client = new MongoClient("150.89.234.253");
		MongoCollection<Document> col = client
			.getDatabase("myproject-room").getCollection("beacons1mz");

		//クエリーの作成
		Bson filter = Filters.eq("minor", beaconId);

		//結果の取得
		Document doc = col.find(filter).sort(Sorts.descending("date")).first();

		if( doc == null ){
			client.close();
			return null;
		}

		//変換
		BeaconPos pos = new BeaconPos();
		pos.setDate(doc.getDate("date"));
		pos.setX(doc.getDouble("x座標"));
		pos.setY(doc.getDouble("y座標"));

		client.close();
		return pos;
	}
}