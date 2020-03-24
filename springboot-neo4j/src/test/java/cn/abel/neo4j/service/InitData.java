package cn.abel.neo4j.service;

import cn.abel.neo4j.bean.King;
import cn.abel.neo4j.bean.Queen;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yyb
 * @time 2020/3/24
 */
public class InitData {

    public static List<King> initKing() {
        List<King> kingList = new ArrayList<>();

        King king1 = new King();
        king1.setId(1L);
        king1.setName("朱元璋");
        king1.setClan("朱五四之子朱棣之父");
        king1.setPosthumousTitle("高皇帝");
        king1.setTimes("洪武（1368年——1398年）");
        king1.setTempleNumber("太祖");
        king1.setTomb("孝陵");
        king1.setRemark("空印案，胡惟庸案");

        King king2 = new King();
        king2.setId(2L);
        king2.setName("朱允炆");
        king2.setClan("朱元璋之孙朱标次子");
        king2.setPosthumousTitle("成皇帝");
        king2.setTimes("建文（1399年——1402年）");
        king2.setTempleNumber("惠宗");
        king2.setTomb("上金贝古墓");
        king2.setRemark("");


        King king3 = new King();
        king3.setId(3L);
        king3.setName("朱棣");
        king3.setClan("朱元璋四子");
        king3.setPosthumousTitle("文皇帝");
        king3.setTimes("永乐（1403年——1424年）");
        king3.setTempleNumber("成祖（原为太宗）");
        king3.setTomb("长陵");
        king3.setRemark("");

        King king4 = new King();
        king4.setId(4L);
        king4.setName("朱高炽");
        king4.setClan("朱棣长子");
        king4.setPosthumousTitle("昭皇帝");
        king4.setTimes("洪熙（1424年——1425年）");
        king4.setTempleNumber("仁宗");
        king4.setTomb("献陵");
        king4.setRemark("");

        King king5 = new King();
        king5.setId(5L);
        king5.setName("朱瞻基");
        king5.setClan("朱高炽长子");
        king5.setPosthumousTitle("章皇帝");
        king5.setTimes("宣德（1426年——1435年）");
        king5.setTempleNumber("宣宗");
        king5.setTomb("景陵");
        king5.setRemark("");

        King king6 = new King();
        king6.setId(6L);
        king6.setName("朱祁镇");
        king6.setClan("朱瞻基长子");
        king6.setPosthumousTitle("睿皇帝");
        king6.setTimes("正统（1436年——1449年） 天顺（1457年——1464年）");
        king6.setTempleNumber("英宗");
        king6.setTomb("裕陵");
        king6.setRemark("");

        King king7 = new King();
        king7.setId(7L);
        king7.setName("朱祁钰");
        king7.setClan("朱瞻基次子（庶出），朱祁镇之弟（后英宗复位，代宗被废）");
        king7.setPosthumousTitle("景皇帝");
        king7.setTimes("景泰（1450年——1457年） ");
        king7.setTempleNumber("代宗");
        king7.setTomb("景泰陵");
        king7.setRemark("");

        King king8 = new King();
        king8.setId(8L);
        king8.setName("朱见深");
        king8.setClan("乃朱祁镇长子（庶出），朱祁钰从子");
        king8.setPosthumousTitle("纯皇帝");
        king8.setTimes("成化（1465年——1487年）");
        king8.setTempleNumber("宪宗");
        king8.setTomb("茂陵");
        king8.setRemark("");

        King king9 = new King();
        king9.setId(9L);
        king9.setName("朱祐樘");
        king9.setClan("乃朱见深三子（庶出）");
        king9.setPosthumousTitle("敬皇帝");
        king9.setTimes("弘治（1488年——1505年）");
        king9.setTempleNumber("孝宗");
        king9.setTomb("泰陵");
        king9.setRemark("");

        King king10 = new King();
        king10.setId(10L);
        king10.setName("朱厚照");
        king10.setClan("乃朱祐樘长子（武宗无嗣，乃由旁支皇室选择朱厚熜入继大统）");
        king10.setPosthumousTitle("高皇帝");
        king10.setTimes("洪武（1368年——1398年）");
        king10.setTempleNumber("武宗");
        king10.setTomb("孝陵");
        king10.setRemark("");

        King king11 = new King();
        king11.setId(11L);
        king11.setName("朱厚熜");
        king11.setClan("乃朱见深四子兴献王朱祐杬（庶出）之嫡长子，朱祐樘从子，朱厚照从弟");
        king11.setPosthumousTitle("肃皇帝");
        king11.setTimes("嘉靖（1522年——1566年）");
        king11.setTempleNumber("世宗");
        king11.setTomb("永陵");
        king11.setRemark("大仪礼");

        King king12 = new King();
        king12.setId(12L);
        king12.setName("朱载垕");
        king12.setClan("乃朱厚熜三子（庶出）");
        king12.setPosthumousTitle("庄皇帝");
        king12.setTimes("隆庆（1567年——1572年）");
        king12.setTempleNumber("穆宗");
        king12.setTomb("昭陵");
        king12.setRemark("");


        King king13 = new King();
        king13.setId(13L);
        king13.setName("朱翊钧");
        king13.setClan("乃朱载垕三子（庶出）");
        king13.setPosthumousTitle("显皇帝");
        king13.setTimes("万历（1573年——1620年）");
        king13.setTempleNumber("神宗");
        king13.setTomb("定陵");
        king13.setRemark("");

        King king14 = new King();
        king14.setId(14L);
        king14.setName("朱常洛");
        king14.setClan("乃朱翊钧长子");
        king14.setPosthumousTitle("贞皇帝");
        king14.setTimes("泰昌（1620年）实际在位一个月");
        king14.setTempleNumber("光宗");
        king14.setTomb("庆陵");
        king14.setRemark("");


        King king15 = new King();
        king15.setId(15L);
        king15.setName("朱由校");
        king15.setClan("乃朱常洛长子（庶出）（熹宗死后无嗣，乃诏令五弟信王朱由检入继大统）");
        king15.setPosthumousTitle("悊皇帝");
        king15.setTimes("天启（1621年——1627年）");
        king15.setTempleNumber("熹宗");
        king15.setTomb("德陵");
        king15.setRemark("");


        King king16 = new King();
        king16.setId(16L);
        king16.setName("朱由检");
        king16.setClan("乃朱常洛五子（庶出）（毅宗死后，明朝覆灭，宗室乃于南方割据）");
        king16.setPosthumousTitle("烈皇帝");
        king16.setTimes("崇祯（1628年——1644年）");
        king16.setTempleNumber("毅宗（原为思宗）");
        king16.setTomb("思陵");
        king16.setRemark("");


        king1.setSuccessor(king2);
        king2.setSuccessor(king3);
        king3.setSuccessor(king4);
        king4.setSuccessor(king5);
        king5.setSuccessor(king6);
        king6.setSuccessor(king7);
        king7.setSuccessor(king8);
        king8.setSuccessor(king9);
        king9.setSuccessor(king10);
        king10.setSuccessor(king11);
        king11.setSuccessor(king12);
        king12.setSuccessor(king13);
        king13.setSuccessor(king14);
        king14.setSuccessor(king15);
        king15.setSuccessor(king16);

        kingList.add(king1);
        kingList.add(king2);
        kingList.add(king3);
        kingList.add(king4);
        kingList.add(king5);
        kingList.add(king6);
        kingList.add(king7);
        kingList.add(king8);
        kingList.add(king9);
        kingList.add(king10);
        kingList.add(king11);
        kingList.add(king12);
        kingList.add(king13);
        kingList.add(king14);
        kingList.add(king15);
        kingList.add(king16);


        return kingList;
    }

    public static List<Queen> initQueen() {

        King king1 = new King();
        king1.setId(1L);
        king1.setName("朱元璋");
        king1.setClan("朱五四之子朱棣之父");
        king1.setPosthumousTitle("高皇帝");
        king1.setTimes("洪武（1368年——1398年）");
        king1.setTempleNumber("太祖");
        king1.setTomb("孝陵");
        king1.setRemark("空印案，胡惟庸案");

        King king2 = new King();
        king2.setId(2L);
        king2.setName("朱允炆");
        king2.setClan("朱元璋之孙朱标次子");
        king2.setPosthumousTitle("成皇帝");
        king2.setTimes("建文（1399年——1402年）");
        king2.setTempleNumber("惠宗");
        king2.setTomb("上金贝古墓");
        king2.setRemark("");


        King king3 = new King();
        king3.setId(3L);
        king3.setName("朱棣");
        king3.setClan("朱元璋四子");
        king3.setPosthumousTitle("文皇帝");
        king3.setTimes("永乐（1403年——1424年）");
        king3.setTempleNumber("成祖（原为太宗）");
        king3.setTomb("长陵");
        king3.setRemark("");

        King king4 = new King();
        king4.setId(4L);
        king4.setName("朱高炽");
        king4.setClan("朱棣长子");
        king4.setPosthumousTitle("昭皇帝");
        king4.setTimes("洪熙（1424年——1425年）");
        king4.setTempleNumber("仁宗");
        king4.setTomb("献陵");
        king4.setRemark("");

        King king5 = new King();
        king5.setId(5L);
        king5.setName("朱瞻基");
        king5.setClan("朱高炽长子");
        king5.setPosthumousTitle("章皇帝");
        king5.setTimes("宣德（1426年——1435年）");
        king5.setTempleNumber("宣宗");
        king5.setTomb("景陵");
        king5.setRemark("");

        King king6 = new King();
        king6.setId(6L);
        king6.setName("朱祁镇");
        king6.setClan("朱瞻基长子");
        king6.setPosthumousTitle("睿皇帝");
        king6.setTimes("正统（1436年——1449年） 天顺（1457年——1464年）");
        king6.setTempleNumber("英宗");
        king6.setTomb("裕陵");
        king6.setRemark("");

        King king7 = new King();
        king7.setId(7L);
        king7.setName("朱祁钰");
        king7.setClan("朱瞻基次子（庶出），朱祁镇之弟（后英宗复位，代宗被废）");
        king7.setPosthumousTitle("景皇帝");
        king7.setTimes("景泰（1450年——1457年） ");
        king7.setTempleNumber("代宗");
        king7.setTomb("景泰陵");
        king7.setRemark("");

        King king8 = new King();
        king8.setId(8L);
        king8.setName("朱见深");
        king8.setClan("乃朱祁镇长子（庶出），朱祁钰从子");
        king8.setPosthumousTitle("纯皇帝");
        king8.setTimes("成化（1465年——1487年）");
        king8.setTempleNumber("宪宗");
        king8.setTomb("茂陵");
        king8.setRemark("");

        King king9 = new King();
        king9.setId(9L);
        king9.setName("朱祐樘");
        king9.setClan("乃朱见深三子（庶出）");
        king9.setPosthumousTitle("敬皇帝");
        king9.setTimes("弘治（1488年——1505年）");
        king9.setTempleNumber("孝宗");
        king9.setTomb("泰陵");
        king9.setRemark("");

        King king10 = new King();
        king10.setId(10L);
        king10.setName("朱厚照");
        king10.setClan("乃朱祐樘长子（武宗无嗣，乃由旁支皇室选择朱厚熜入继大统）");
        king10.setPosthumousTitle("高皇帝");
        king10.setTimes("洪武（1368年——1398年）");
        king10.setTempleNumber("武宗");
        king10.setTomb("孝陵");
        king10.setRemark("");

        King king11 = new King();
        king11.setId(11L);
        king11.setName("朱厚熜");
        king11.setClan("乃朱见深四子兴献王朱祐杬（庶出）之嫡长子，朱祐樘从子，朱厚照从弟");
        king11.setPosthumousTitle("肃皇帝");
        king11.setTimes("嘉靖（1522年——1566年）");
        king11.setTempleNumber("世宗");
        king11.setTomb("永陵");
        king11.setRemark("大仪礼");

        King king12 = new King();
        king12.setId(12L);
        king12.setName("朱载垕");
        king12.setClan("乃朱厚熜三子（庶出）");
        king12.setPosthumousTitle("庄皇帝");
        king12.setTimes("隆庆（1567年——1572年）");
        king12.setTempleNumber("穆宗");
        king12.setTomb("昭陵");
        king12.setRemark("");


        King king13 = new King();
        king13.setId(13L);
        king13.setName("朱翊钧");
        king13.setClan("乃朱载垕三子（庶出）");
        king13.setPosthumousTitle("显皇帝");
        king13.setTimes("万历（1573年——1620年）");
        king13.setTempleNumber("神宗");
        king13.setTomb("定陵");
        king13.setRemark("");

        King king14 = new King();
        king14.setId(14L);
        king14.setName("朱常洛");
        king14.setClan("乃朱翊钧长子");
        king14.setPosthumousTitle("贞皇帝");
        king14.setTimes("泰昌（1620年）实际在位一个月");
        king14.setTempleNumber("光宗");
        king14.setTomb("庆陵");
        king14.setRemark("");


        King king15 = new King();
        king15.setId(15L);
        king15.setName("朱由校");
        king15.setClan("乃朱常洛长子（庶出）（熹宗死后无嗣，乃诏令五弟信王朱由检入继大统）");
        king15.setPosthumousTitle("悊皇帝");
        king15.setTimes("天启（1621年——1627年）");
        king15.setTempleNumber("熹宗");
        king15.setTomb("德陵");
        king15.setRemark("");


        King king16 = new King();
        king16.setId(16L);
        king16.setName("朱由检");
        king16.setClan("乃朱常洛五子（庶出）（毅宗死后，明朝覆灭，宗室乃于南方割据）");
        king16.setPosthumousTitle("烈皇帝");
        king16.setTimes("崇祯（1628年——1644年）");
        king16.setTempleNumber("毅宗（原为思宗）");
        king16.setTomb("思陵");
        king16.setRemark("");

        /**
         * 初始化皇后信息
         */
        List<Queen> queenList = new ArrayList<>();

        Queen queen1 = new Queen();
        queen1.setId(101L);
        queen1.setPosthumousTitle("孝慈高皇后");
        queen1.setName("马秀英");
        queen1.setRemark("安徽宿州人，早年丧母，被郭子兴夫妇收养为义女。1382年（洪武十五年）五十一岁的马皇后病逝。");
        queen1.setTimes("1368年1月－1382年");
        queen1.setSon("明兴宗朱标、秦愍王朱樉、晋恭王朱㭎、明成祖朱棣、周定王朱橚（五子皆存疑），宁国公主，安庆公主");
        queen1.setKing(king1);


        Queen queen2 = new Queen();
        queen2.setId(102L);
        queen2.setPosthumousTitle("孝愍让皇后");
        queen2.setName("马全女");
        queen2.setRemark("洪武二十八年十月,册后为皇太孙妃。建文改元,立为后。生二子,文煃、文圭。");
        queen2.setTimes("1399年－1402年");
        queen2.setSon("和简太子朱文奎，润怀王朱文圭");
        queen2.setKing(king2);

        Queen queen3 = new Queen();
        queen3.setId(103L);
        queen3.setPosthumousTitle("孝文皇后");
        queen3.setName("徐氏 ");
        queen3.setTimes("1402年11月－1407年");
        queen3.setSon("明仁宗朱高炽、汉王朱高煦、赵王朱高燧");
        queen3.setRemark("洪武九年(1376年)册封为燕王妃，朱棣称帝册封为皇后。永乐五年(1407年)七月四日去世,享年46岁。");
        queen3.setKing(king3);

        Queen queen4 = new Queen();
        queen4.setId(104L);
        queen4.setPosthumousTitle("诚孝昭皇后");
        queen4.setName("张氏");
        queen4.setRemark("仁宗元配,永城人,指挥使赠彭城侯张麒诚孝昭皇后画像之女。洪武二十八年(1395年)封燕王世子妃,永乐二年(1404年)封皇太子妃。仁宗即位,册立为皇后。宣宗即位,尊为皇太后。英宗即位，尊为太皇太后。正统七年(1442年)十月十八日去世.");
        queen4.setTimes("1424年7月－1442年");
        queen4.setSon("明宣宗朱瞻基、越靖王朱瞻墉、襄宪王朱瞻墡、嘉兴公主");
        queen4.setKing(king4);

        Queen queen5 = new Queen();
        queen5.setId(105L);
        queen5.setPosthumousTitle("恭让章皇后");
        queen5.setName("胡善祥");
        queen5.setTimes("1426年－1428年");
        queen5.setSon("顺德公主");
        queen5.setRemark("皇后，后被废黜，后被追谥为恭让章皇后");
        queen5.setKing(king5);

        Queen queen51 = new Queen();
        queen51.setId(106L);
        queen51.setPosthumousTitle("孝翼皇后");
        queen51.setName("吴氏");
        queen51.setSon("明代宗朱祁钰");
        queen51.setRemark("太子妾，贤妃，后成皇太后，后被废，南明追谥孝翼温惠淑慎慈仁匡天锡圣太后");
        queen51.setKing(king5);

        Queen queen52 = new Queen();
        queen52.setId(107L);
        queen52.setPosthumousTitle("孝恭章皇后");
        queen52.setName("孙氏");
        queen52.setTimes("1426年－1462年");
        queen52.setSon("常德公主、明英宗（存疑）");
        queen52.setRemark("");
        queen52.setKing(king5);

        Queen queen6 = new Queen();
        queen6.setId(108L);
        queen6.setPosthumousTitle("孝庄睿皇后");
        queen6.setName("钱氏");
        queen6.setTimes("1442年－1468年6月");
        queen6.setRemark("英宗元配,海州人,都指挥佥事（后封安昌伯）钱贵女。正统七年(1442年)立为皇后。宪宗即位,尊为皇太后,加“慈懿”徽号。成化四年（1468年）六月二十六日,钱氏病故。  ");
        queen6.setKing(king6);

        Queen queen61 = new Queen();
        queen61.setId(109L);
        queen61.setPosthumousTitle("孝肃后");
        queen61.setName("周氏");
        queen61.setTimes("1464年－1504");
        queen61.setSon("重庆公主、明宪宗朱见深、崇简王朱见泽");
        queen61.setRemark("宪宗生母,昌平州文宁里柳林村(今属北京市海淀区)人,锦衣卫千户追封庆云侯赠宁国公周能的女儿。正统十二年(1447年)生宪宗皇帝,天顺元年（1457年）册封为贵妃。宪宗即位,尊为皇太后。孝宗即位后,尊为太皇太后。弘治十七年（1504年）三月一日去世.");
        queen61.setKing(king6);

        Queen queen7 = new Queen();
        queen7.setId(110L);
        queen7.setPosthumousTitle("孝渊皇后");
        queen7.setName("汪氏");
        queen7.setTimes("1449年－1452年");
        queen7.setSon("固安公主、二公主（出家）");
        queen7.setRemark("北京人，祖父汪泉世为金吾左卫指挥使。正统十年(1445)，郕王朱祁钰18岁，册封汪氏为郕王妃。正统十四年(1449) 册封王妃汪氏为皇后。进汪皇后祖父汪泉为都指挥同知；，汪氏去世与景帝合葬金山。");
        queen7.setKing(king7);

        Queen queen71 = new Queen();
        queen71.setId(111L);
        queen71.setPosthumousTitle("肃孝皇后");
        queen71.setName("杭氏");
        queen71.setSon("怀献太子朱见济");
        queen71.setTimes("1452年－1453年");
        queen71.setRemark("妾，后为贵妃，后成皇后，后被明英宗追废");
        queen71.setKing(king7);

        Queen queen8 = new Queen();
        queen8.setId(112L);
        queen8.setPosthumousTitle("皇后");
        queen8.setName("吴氏");
        queen8.setTimes("1464年7月27日－1464年8月28日");
        queen8.setSon("曾抚养明孝宗朱祐樘");
        queen8.setRemark("皇后，后被废黜,在位仅31日。为明朝在位时期最短的皇后。");
        queen8.setKing(king8);

        Queen queen81 = new Queen();
        queen81.setId(113L);
        queen81.setPosthumousTitle("孝贞纯皇后");
        queen81.setName("王氏");
        queen81.setTimes("1464年10月12日－1518年2月");
        queen81.setRemark("皇后，后成皇太后");
        queen81.setKing(king8);


        Queen queen82 = new Queen();
        queen82.setId(114L);
        queen82.setPosthumousTitle("孝穆皇后");
        queen82.setName("纪氏");
        queen82.setSon("明孝宗朱祐樘");
        queen82.setRemark("宫人，后成淑妃，后被追谥孝穆皇后, 瑶族人。暴死");
        queen82.setKing(king8);

        Queen queen83 = new Queen();
        queen83.setId(115L);
        queen83.setPosthumousTitle("孝惠皇后");
        queen83.setName("邵氏");
        queen83.setSon("宸妃，后成贵妃，嘉靖帝尊为寿安皇太后，后被追谥孝惠皇后");
        queen83.setRemark("宫人，后成淑妃，后被追谥孝穆皇后, 瑶族人。暴死");
        queen83.setKing(king8);

        Queen queen9 = new Queen();
        queen9.setId(116L);
        queen9.setPosthumousTitle("孝成敬皇后");
        queen9.setName("张氏");
        queen9.setTimes("1486年－1541年");
        queen9.setSon("明武宗朱厚照、朱厚炜、太康公主朱秀荣");
        queen9.setRemark("皇后，后成皇伯母皇太后，追谥孝康敬皇后，后改谥孝成敬皇后,丈夫孝宗在世期间未曾册封嫔妃，是中国历史上唯一终生一夫一妻的皇帝");
        queen9.setKing(king9);



        Queen queen10 = new Queen();
        queen10.setId(117L);
        queen10.setPosthumousTitle("孝静皇后");
        queen10.setName("夏氏");
        queen10.setTimes("1506年－1535年1月");
        queen10.setRemark("皇后，嘉靖帝尊封为皇嫂庄肃皇后");
        queen10.setKing(king10);



        Queen queen11 = new Queen();
        queen11.setId(118L);
        queen11.setPosthumousTitle("孝洁肃皇后");
        queen11.setName("陈氏");
        queen11.setTimes("1522年－1528年10月");
        queen11.setRemark("皇后，谥悼灵皇后，后改谥孝洁恭懿慈睿安庄相天翊圣肃皇后, 受惊流产而亡");
        queen11.setKing(king11);

        Queen queen12 = new Queen();
        queen12.setId(119L);
        queen12.setPosthumousTitle("孝懿庄皇后");
        queen12.setName("李氏");
        queen12.setTimes("?－1558年4月");
        queen12.setSon("宪怀太子朱翊、蓬莱公主");
        queen12.setRemark("结髪之妻，正室，后被追谥为孝懿贞惠顺哲恭仁俪天襄圣庄皇后");
        queen12.setKing(king12);


        Queen queen121 = new Queen();
        queen121.setId(120L);
        queen121.setPosthumousTitle("孝安皇后");
        queen121.setName("陈氏");
        queen121.setTimes("1567年3月－1596年7月");
        queen121.setSon("太和公主");
        queen121.setRemark("继室，后为皇后，后成皇太后，后被追谥为孝安贞懿恭纯温惠佐天弘圣皇后");
        queen121.setKing(king12);

        Queen queen13 = new Queen();
        queen13.setId(121L);
        queen13.setPosthumousTitle("孝端皇后");
        queen13.setName("王喜姐");
        queen13.setTimes("1578年－1620年");
        queen13.setRemark("荣昌公主朱轩媖");
        queen13.setKing(king13);


        Queen queen14 = new Queen();
        queen14.setId(122L);
        queen14.setPosthumousTitle("孝元贞皇后");
        queen14.setName("郭氏");
        queen14.setSon("怀淑公主朱徽娟");
        queen14.setRemark("结髪之妻，正室太子妃，后被追谥为孝元昭懿哲惠庄仁合天弼圣贞皇后");
        queen14.setKing(king14);


        Queen queen15 = new Queen();
        queen15.setId(123L);
        queen15.setPosthumousTitle("孝哀悊皇后");
        queen15.setName("张氏");
        queen15.setTimes("1621年4月－1644年2月28日");
        queen15.setSon("太子朱慈燃");
        queen15.setRemark("皇后，崇祯帝尊封为皇嫂懿安皇后，南明追谥孝哀慈靖恭惠温贞偕天协圣悊皇后,明亡时自缢身亡。");
        queen15.setKing(king15);


        Queen queen16 = new Queen();
        queen16.setId(124L);
        queen16.setPosthumousTitle("孝节烈皇后");
        queen16.setName("周氏");
        queen16.setTimes("1628年－1644年2月28日");
        queen16.setSon("坤仪公主、明悼帝朱慈烺、怀隐王朱慈烜、定哀王朱慈炯");
        queen16.setRemark("结髪之妻，正室，后成皇后，清追谥为庄烈愍皇后，南明追谥孝节贞肃渊恭庄毅奉天靖圣烈皇后,自杀");
        queen16.setKing(king16);


        queenList.add(queen1);
        queenList.add(queen2);
        queenList.add(queen3);
        queenList.add(queen4);
        queenList.add(queen5);
        queenList.add(queen5);
        queenList.add(queen51);
        queenList.add(queen52);

        queenList.add(queen6);
        queenList.add(queen61);

        queenList.add(queen7);
        queenList.add(queen71);

        queenList.add(queen8);
        queenList.add(queen81);
        queenList.add(queen82);
        queenList.add(queen83);

        queenList.add(queen9);
        queenList.add(queen10);
        queenList.add(queen11);
        queenList.add(queen12);
        queenList.add(queen121);

        queenList.add(queen13);
        queenList.add(queen14);
        queenList.add(queen15);
        queenList.add(queen16);

        return queenList;
    }
}
