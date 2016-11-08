package mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

/**
 * mongodbからビーコンのデータをとってきて集計するクラスです。
 * @author ryokun
 *
 */
public class BeaconAggregator {
	public static void main (String[] args){
		//mongodbの接続
		MongoClient client = new MongoClient("150.89.234.253");
		//クラスの初期化
		BeaconAggregator aggr = new BeaconAggregator(client);

		//期限を設定
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -60);
		aggr.setDeadline(cal.getTime());

		//認識されたビーコンの一覧を取得
		List<Integer> beacons = aggr.getBeaconList();

		for(Integer minor : beacons){
			//そのビーコンが認識されている受信機のリスト
			List<String> receivers = aggr.getBeaconReceiverList(minor);
			//if(receivers >= 3){
				System.out.println(minor + ": " + receivers.toString());
			//}

			//そのビーコンと受信機との平均距離を取得する
			double dist = aggr.getDistanceAverage(101, "1号機");
			System.out.println("101 - 1号機: " + dist);
		}
		
		aggr.getBeaconPositions(101).forEach((p) ->
			System.out.println(p.toString()));

		client.close();
	}

	//データベースのコレクション名の設定
	public static String DB_NAME = "myproject-room";
	public static String COL_NAME = "beacons";

	//ビーコンデータを取得するときの受信日時の指定(deadlineより後のデータが選択される)
	private Date deadline;
	//コレクションのキャッシュ
	private MongoCollection<Document> collection;

	/**
	 * このクラスのコンストラクタです。
	 * @param client 接続するdbのクライアント
	 */
	public BeaconAggregator(MongoClient client){
		deadline = null;
		collection = client.getDatabase(DB_NAME).getCollection(COL_NAME);
	}

	/**
	 * ビーコンデータのデッドラインを指定します。
	 * dateを設定すると、その日時より前のデータは対象から除外されます。
	 * @param deadline ビーコンデータの期限
	 */
	public void setDeadline(Date deadline){
		this.deadline = deadline;
	}

	/**
	 * dbに問い合わせする際の条件フィルターを生成します。
	 * 引数のnull以外の値についてフィルターを生成し、and結合して返します
	 * @param beaconId ビーコンIDの指定です
	 * @param receiver レシーバIDの指定です
	 * @param deadline　ビーコンデータの期限の指定です
	 * @return bsonフィルタ
	 */
	private Bson createFilter(Integer beaconId, String receiver, Date deadline){
		//一時的な変数を初期化
		List<Bson> filters = new ArrayList<Bson>();

		//引数がnullでなければ、filtersにフィルタを追加
		if( beaconId != null ){
			filters.add(Filters.eq("minor", beaconId));
		}
		if( receiver != null ){
			filters.add(Filters.eq("receiver", receiver));
		}
		if( deadline != null ){
			filters.add(Filters.gte("date", deadline));
		}

		//フィルタの個数によって返却値を返却、複数のフィルタはand結合する
		if( filters.size() > 1 ){
			return Filters.and(filters);
		}else if( filters.size() > 0 ){
			return filters.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 対象データのビーコンのリストを返却します
	 * @return dbに登録されているビーコンのリスト
	 */
	public List<Integer> getBeaconList(){
		List<Bson> query = new ArrayList<Bson>();

		//deadlineが設定されていたら、クエリーにフィルターを追加する
		if( deadline != null ){
			query.add(Aggregates.match(createFilter(null, null, deadline)));
		}
		//グループ化クエリーを追加する
		query.add(Aggregates.group("$minor"));

		//クエリーを発行する
		AggregateIterable<Document> result = collection.aggregate(query);

		//結果を配列に入れる
		List<Integer> beaconList = new ArrayList<Integer>();
		for(Document doc : result){
			beaconList.add(doc.getInteger("_id"));
		}

		return beaconList;
	}

	/**
	 *　指定されたビーコンIDが認識されている受信機のリストを返します
	 * @param beaconId
	 * @return 対象ビーコンが認識されている受信機のリスト
	 */
	public List<String> getBeaconReceiverList(int beaconId){
		//クエリーを作成
		List<Bson> query = Arrays.asList(
			Aggregates.match(createFilter(beaconId, null, deadline)),
			Aggregates.group("$receiver")
		);

		//クエリーを発行する
		AggregateIterable<Document> result = collection.aggregate(query);

		//結果を配列に入れる
		List<String> receiverList = new ArrayList<String>();
		for(Document doc : result){
			receiverList.add(doc.getString("_id"));
		}

		return receiverList;
	}

	/**
	 * ビーコンと受信機とのデータの平均距離を返します
	 * @param beaconId 対象のビーコンIDです
	 * @param receiver 平均距離を求める対象の受信機です
	 * @return ビーコンと受信機との平均距離
	 */
	public double getDistanceAverage(int beaconId, String receiver){
		//クエリーを作成
		List<Bson> query = Arrays.asList(
			Aggregates.match(createFilter(beaconId, receiver, deadline)),
			Aggregates.group(null, Accumulators.avg("average", "$距離"))
		);

		//結果の取得
		Document result = collection.aggregate(query).first();

		if( result != null ){
			return result.getDouble("average");
		}else{
			return 0;
		}
	}
	
	/**
	 * ビーコンの座標の一覧を取得します
	 * @param beaconId 対象のビーコンIDです
	 * @return
	 */
	public List<RPoint> getBeaconPositions(int beaconId){
		//クエリーの作成
		Bson filter = createFilter(beaconId, null, null);
		
		//結果の取得
		FindIterable<Document> result = collection.find(filter);
		List<RPoint> positions = new ArrayList<RPoint>();
		
		//変換
		for(Document doc : result){
			positions.add(new RPoint(doc.getDouble("x"), doc.getDouble("y")));
		}
		
		return positions;
	}
}
