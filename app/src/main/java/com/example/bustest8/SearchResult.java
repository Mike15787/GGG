package com.example.bustest8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchResult extends AppCompatActivity {
    String ReallyHard[][] = {{"紅幹線","安平工業區","燕窩觀光工廠","開南公園","中華南路二段","新興國宅","德興路口","至善橋","郡南里","永成路三段","府南里","田寮里","新興國小","台南站","新光三越新天地","小西門(大億麗緻)","西門、友愛街口","中正、西門路口","林百貨(中正路)","中山、民權路口","台南醫院","臺南火車站","縣知事官邸","東門圓環","東門教會", "東門城","龍山寺","衛生局","富強市場","東門路",
            "虎尾寮","富農街口","崁腳","嘉南療養院","仁德交流道","仁德","仁義里","鍾厝","慈濟台南分會","義林一街口","林仔","貝汝流通","上帝廟","南興里","南保","姓李仔","後市","歸仁區公所","歸仁國中","新豐高中","歸仁市場","歸仁文化中心","善化寺","看東里","修元堂","歸仁交流道","大亞公司","關廟國中","關廟市場", "北勢里","花園","關廟轉運站","仰龍橋","牛稠埔","九天宮","龍崎"},
            {"綠幹線","臺南轉運站","臺南火車站","臺南公園(北門路)","台南二中","中樓","開元","崑山中學","南工宿舍","南工社區","中興","康福新城","二王","臺南大學附中","永康農會","永康","龍潭口","德芳社區","西勢","西勢東","南光藥廠","保生大帝宮","唪口","清水寺","新化站","新化區公所","新化保養廠","養護之家","仁愛之家","深坑子","畜試所","台南醫院新化分院","接天寺","那拔林","千鳥橋",
                    "隙子口","豐德","大立窯業","光和里","下菜寮","頂菜寮","左鎮化石園區","龍溝","邦寮","橄欖山","左鎮分駐所","左鎮果菜市場","左鎮","東屏厝","睦光里","竹坑","刺桐腳","後坑","九層林","愛文山","松腳","玉井懷恩堂","倒松","劉陳","望明口","玉山新城","新庄","玉井工商","玉井站"},
            {"藍幹線","安平工業區","安平港","服務中心","香腸博物館","文南里","家樂福","水萍塭公園","永華站","小西門(大億麗緻)","小西門","林百貨(忠義路)","中山、民權路口","台南醫院","臺南火車站","公園路","臺南轉運站","公園北路口","建成市場","延平市場(公園路)","正覺里","六甲頂","溪頂寮橋","安順派出所","安順","北安順","政安路口","新和順","安順國小","安順國中","舊和順北","大聖廟",
                    "福國寺","和順","和順國小","北和順","沙崙路口","中崙","新吉","海寮","南西港","西港","期美科技公司","北西港","頂中洲","三五甲","南勢","南佳里","佳里站"},
            {"5","桂田酒店","竹林里","鹽行","南鹽行","洲仔尾","愛買量販","大崎(家樂福中正店)","南大崎","尚頂里","中正南路","奇美醫院","大橋","臺南高工","南工宿舍","崑山中學","開元","富台新村","中樓","勝利北路","成大醫院(勝利路)","成功大學","小東路","臺南公園(北門路)","臺南火車站","南區健保局","民族路","赤崁樓","西門、民權路口","西門、友愛街口","小西門(大億麗緻)","新光三越新天地",
                    "西門路一段","台南站","家齊高中","臺南高商","體育公園(臺南大學)","竹溪寺(健康路)","南台南","大林新城","忠孝新村","大林","亞伯大飯店","生產路口","大同、機場路口","龍寶路口","大同路三段121巷","保仁路","仁愛里","二空東","二空新城","成功里活動中心","德東街口","東區電信局","市立醫院","仁愛里國泰社區","空軍臺南基地","臺南航空站(奇美幸福工廠)","奇美博物館","臺南家具產業博物館","嘉南藥理大學","二行里","觀音寺","大甲里"}
    };

    RecyclerView rvGroup;
    ArrayList<String> arrayListGroup;
    LinearLayoutManager layoutManagerGroup;
    Groupadp adapterGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
        //FirebaseFirestore db = FirebaseFirestore.getInstance();


        String Bs1[] = globalVariable.getBS1(); // 距離小於 2000m 的起點公車站牌
        String Bs2[] = globalVariable.getBS2(); // 距離小於 2000m 的終點公車站牌
        //double D1[] = globalVariable.getD1();
        //double D2[] = globalVariable.getD2();
        String BS3[] = {"0左(環狀線)", "10", "11", "14", "18", "19", "2", "3", "5", "6", "7", "9", "99", "橘11", "橘11-1", "橘12", "橘3"
                , "紅1", "紅2", "紅3", "紅4", "紅幹線", "綠12", "綠17", "綠幹線", "藍幹線", "雙層巴士"};
        int h=0;
        int m=0;

        //TextView textView = (TextView) findViewById(R.id.PG);
        //TextView textView1 = (TextView) findViewById(R.id.PG1);


        String ReplaceBs1[] = CleanNull(Bs1);
        String ReplaceBs2[] = CleanNull(Bs2);


        for(int i=0;i<ReplaceBs2.length;i++){
            for (int j=0;j<ReplaceBs1.length;j++){
                String Victory[][] = BusCompare(ReplaceBs2[i],ReplaceBs1[j]);
                h=j;
                if (Victory[0].length !=0){
                    break;
                }
            }
            String Vic[][] = BusCompare(ReplaceBs2[i],ReplaceBs1[h]);
            m=i;
            if(Vic.length!=0){
                break;
            }
        }
        String TheFinal[][] = BusCompare(ReplaceBs2[m],ReplaceBs1[h]);
        globalVariable.setTF(TheFinal);

        rvGroup = findViewById(R.id.rv_group);

        arrayListGroup = new ArrayList<>();
        for (int i=0;i<TheFinal.length;i++){
            arrayListGroup.add("路線" + i);
        }
        adapterGroup = new Groupadp(SearchResult.this,arrayListGroup);
        layoutManagerGroup = new LinearLayoutManager(this);
        rvGroup.setLayoutManager(layoutManagerGroup);
        rvGroup.setAdapter(adapterGroup);
    }

    public String[][] BusCompare (String S2,String S1){
        String Line[] = new String[20];
        String Line2[] = new String[20];
        String ResultLine[][];
        int TT[] = new int[10];
        int GG[] = new int[10];
        int g = 0;
        int r = 0;
        int l = 0;
        int aa=0;
        for (int i=0;i<4;i++) {
            for (int j = 1; j < ReallyHard[i].length; j++) {
                boolean EE = S2.equals(ReallyHard[i][j]); //經過離終點最近的站的公車Bs2
                boolean DD = S1.equals(ReallyHard[i][j]); //經過離起點最近的站的公車Bs1
                if (EE) {
                    Line2[g] = ReallyHard[i][0];  //路線名稱
                    GG[g] = i; //記錄這條公車的索引
                    g++;//2
                }
                if (DD) {
                    Line[r] = ReallyHard[i][0];
                    TT[r] = i;
                    r++;//1
                }
            }
        }
        //消除掉陣列的null
        String ReplaceLine[] = new String[r];//1
        String ReplaceLine2[] = new String[g];//2
        int ReplaceTT[] = new int[r];//1
        int ReplaceGG[] = new int[g];//2
        for (int y=0;y<g;y++){
            ReplaceLine2[y] = Line2[y];//2
            ReplaceGG[y] = GG[y];
        }
        for (int t=0;t<r;t++){
            ReplaceLine[t] = Line[t];
            ReplaceTT[t] = TT[t];
        }

        String TTT[][] = NoChangeBus(ReplaceLine2,ReplaceLine);
        Log.d("TTTTT", String.valueOf(TTT.length));
        if (TTT.length==0){
            ResultLine = OneChangeBus(g,r,ReplaceGG,ReplaceTT);
        } else {
            ResultLine = NoChangeBus(ReplaceLine2,ReplaceLine);
        }

        return ResultLine;
    }

    public String[][] NoChangeBus(String[] RLine2,String[] RLine){
        int k = 0;
        String SResult[] = new String[10];
        for (int j=0;j<RLine.length;j++){
            Log.d("TAG123", RLine[j]);
        }
        for (int j=0;j<RLine2.length;j++){
            Log.d("TGA", RLine2[j]);
        }
        for (int b=0;b<RLine.length;b++){
            for (int c=0;c<RLine2.length;c++){
                boolean ff = RLine[b].equals(RLine2[c]);
                if (ff==true){
                    SResult[k]=RLine[b];
                    Log.d("TAG",SResult[k]);
                    k++;
                }
            }
        }
        String ReplaceSResult[][] = new String[k][1];
        for (int x=0;x<k;x++){
            ReplaceSResult[x][0]= SResult[x];
            Log.d("Please", ReplaceSResult[x][0]);
            Log.d("Please", String.valueOf(x));
        }
        return ReplaceSResult;
    }

    public String[][] OneChangeBus(int g,int r,int[] ReplaceG,int[] ReplaceT){
        int k=0;
        String Savechange[][] = new String[8][2];
        for (int d=0;d<g;d++){ //末站的路線
            int x = ReplaceG[d]; //取得該公車索引13
            for (int v=0;v<r;v++){ //首站的路線
                int y = ReplaceT[v]; //取得該公車的索引2Noproblem
                for (int u=1;u<ReallyHard[x].length;u++){ //將兩台公車從頭到尾比對
                    Log.d("FFFFF", ReallyHard[x][u]);
                    for (int m=1;m<ReallyHard[y].length;m++){
                        boolean FF = ReallyHard[x][u].equals(ReallyHard[y][m]);
                        if (FF){ //成功比對到的就是可以的
                            Savechange[k][0] = ReallyHard[y][0];
                            Savechange[k][1] = ReallyHard[x][0];
                            Log.d("Change123", ReallyHard[x][u]);
                            Log.d("Change123", Savechange[k][0]);
                            Log.d("Change123", Savechange[k][1]);
                            k++;
                        }
                    }
                }
            }
        }
        String ReplaceSaveChange[][] = new String[k][2];
        for (int e=0;e<k;e++){
            ReplaceSaveChange[e][0] = Savechange[e][0];
            ReplaceSaveChange[e][1] = Savechange[e][1];
            Log.d("Re123", ReplaceSaveChange[e][0]);
            Log.d("Re123", ReplaceSaveChange[e][1]);
        }
        return ReplaceSaveChange;
    }

    public String[] CleanNull(String[] BS){
        int rr=0;
        for (int bs=0;bs<BS.length;bs++){
            if (BS[bs] != null){//Not Sure
                rr++;
            }
        }
        String Replace[] = new String[rr];
        for (int g=0;g<rr;g++){
            Replace[g] = BS[g];
        }
        return Replace;
    }
    public double[] CleanD(double[] D){
        int yy=0;
        for (int d=0;d<D.length;d++){
            if (D[d] != 0.0){
                yy++;
            }
        }
        double SubD[] = new double[yy];
        for (int f=0;f<yy;f++){
            SubD[f] = D[f];
        }
        return SubD;
    }
}