package mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class CalcAve {
	public static RReceiver[] posit
	(int minor, BeaconAggregator coll1, MongoCollection<Document> coll3){

		//そのビーコンが認識されている受信機のリスト
		List<String> receivers = coll1.getBeaconReceiverList(minor);
		System.out.println(minor + ": " + receivers.toString());

		// *****
		// レシーバーの位置と平均距離を求める
		// *****
		RReceiver[] c = new RReceiver[receivers.size()];
		for(int i=0; i<receivers.size(); i++){
			//RCircleを初期化
			c[i] = new RReceiver();

			//レシーバーの位置を取得
			Document doc = coll3.find(Filters.eq("name", receivers.get(i))).first();
			double x = doc.getDouble("x");
			//y座標を取得する
			double y = doc.getDouble("y");
			//x座標をセット
			c[i].x = x;
			//y座標をセット
			c[i].y = y;
			//receiverの名前をセット
			c[i].name = doc.getString("name");
			//平均距離を取得
			c[i].r = coll1.getDistanceAverage(minor, receivers.get(i));
			//System.out.println(c[i].toString());
		}

		return c;
	}

	public static RPoint posiz(RCircle[] c){
		// *****
		// ビーコンの位置を求める
		// *****
		RPoint answerPoint = null;
		if(c.length >= 3){

			//円の組み合わせを探して、条件を満たすなら座標を求める
			ArrayList<RPoint> p = new ArrayList<RPoint>();
			ArrayList<Double> r = new ArrayList<Double>();
			for(int j=0; j<c.length; j++){
				for(int k=j+1; k<c.length; k++){
					for(int l=k+1; l<c.length; l++){
						//座標計算をする
							RPoint p1 = ZahyoMongo.getPosition(c[j],c[k],c[l]);
							if( ZahyoMongo.inroom(p1) ){
								System.out.format("%d-%d-%d は計算の対象です\n", j, k, l);
								//System.out.println("\t" + c[j].toString());
								//System.out.println("\t" + c[k].toString());
								//System.out.println("\t" + c[l].toString());
								System.out.println("\t===>" + p1.toString());
								r.add(c[j].r + c[k].r + c[l].r);
								System.out.println(c[j].r + c[k].r + c[l].r);
								p.add(p1);
							}else{
								//System.out.format("%d-%d-%d は部屋の範囲外です\n", j, k, l);
							}
							//System.out.format("%d-%d-%d は外接円の条件に反します\n", j, k, l);
					}
				}
			}

			if( p.size() <= 0 ){
				System.out.println("適切なデータを検出できませんでした");
				return null;
			}

			RCircle nearly = c[0];
			int ni = 0;
			for(int i=1; i<c.length; i++){
				if( nearly.r > c[i].r){
					nearly = c[i];
					ni = i;
				}
			}
			System.out.println("最小距離のビーコン: " + ni);

			RPoint answer = p.get(0);
			double dmin = nearly.distance(answer);
			for(int i=1; i<p.size(); i++){
				if( dmin > nearly.distance(p.get(i)) ){
					answer = p.get(i);
					dmin = nearly.distance(answer);
				}
			}


//			int answer = 0;
//			for(int i=1;i<r.size();i++){
//				if(r.get(i) < r.get(answer)){
//					answer = i;
//				}
//			}
			answerPoint = answer;
		}

		return answerPoint;
	}

}