//package com.example.demo;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
//import com.example.demo.pojo.EntInfo;
//import com.example.demo.pojo.Gb;
//import com.example.demo.pojo.User;
//import com.example.demo.service.GbService;
//import com.example.demo.service.QiService;
//import com.example.demo.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@SpringBootTest
//public class ServiceDemoApplicationTests {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private QiService qiService;
//    @Autowired
//    private GbService gbService;
//   @Test
//    public void testGetCount(){
//       //查询总记录数，SELECT COUNT( * ) FROM user
//       long count=userService.count();
//       System.out.println("总记录数:"+count);
//   }
//    @Test
//    public void testSaveBatch(){
//// SQL长度有限制，海量数据插入单条SQL无法实行，
//// 因此MP将批量插入放在了通用Service中实现，而不是通用Mapper
//        ArrayList<User> users = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            User user = new User();
//            user.setName("ybc" + i);
//            user.setAge(20 + i);
//            users.add(user);
//        }
////SQL:INSERT INTO t_user ( username, age ) VALUES ( ?, ? )
//        boolean b = userService.saveBatch(users);
//        System.out.println(b);
//    }
//    public static String removeTrailingZeros(String str) {
//        if (str == null || str.length() == 0) {
//            return str;
//        }
//        int lastIndex = str.length() - 1;
//        while (lastIndex >= 0 && str.charAt(lastIndex) == '0') {
//            lastIndex--;
//        }
//        return str.substring(0, lastIndex + 1);
//    }
//    @Test
//    public void gbdata(){
//        LambdaQueryWrapper<EntInfo> lambdaUpdateWrapper = new LambdaQueryWrapper<>();
//        lambdaUpdateWrapper
//                .select(EntInfo::getDetailedUnitName,EntInfo::getIndustyCode)
//                .isNull(EntInfo::getGbname);
//        List<EntInfo> list = qiService.list(lambdaUpdateWrapper);
//        System.out.println(list);
//        for (EntInfo i:list) {
//            String industyCode = i.getIndustyCode();
//            String detailedUnitName = i.getDetailedUnitName();
//
//            String s = removeTrailingZeros(industyCode);
//            LambdaQueryWrapper<Gb> lambdaUpdateWrappera = new LambdaQueryWrapper<>();
//            lambdaUpdateWrappera
//                    .select(Gb::getName)
//                    .eq(Gb::getCode,s);
//            Gb data = gbService.getOne(lambdaUpdateWrappera);
//            if(data!=null){
//                LambdaUpdateWrapper<EntInfo> lambdaUpdateWrapper1 = new LambdaUpdateWrapper();
//                lambdaUpdateWrapper1
//                        .set(EntInfo::getGbname,data.getName())
//                        .eq(EntInfo::getDetailedUnitName,detailedUnitName);
//                qiService.update(lambdaUpdateWrapper1);
//                System.out.println(data);
//            }
//
//
//        }
////        List<Gb> list = userService.list();
////        System.out.println(list);
//
//    }
//    public static String getFirstTwoDigits(String input) {
//        if (input == null || input.length() < 2) {
//            return input;
//        }
//        return input.substring(0, 2);
//    }
//    @Test
//    public void gbdatb() {
//        LambdaQueryWrapper<EntInfo> lambdaUpdateWrapper = new LambdaQueryWrapper<>();
//        lambdaUpdateWrapper
//                .select(EntInfo::getDetailedUnitName, EntInfo::getIndustyCode)
//                .isNull(EntInfo::getGbnameone);
//        List<EntInfo> list = qiService.list(lambdaUpdateWrapper);
//        System.out.println(list);
//        for (EntInfo i : list) {
//            if (i.getIndustyCode() != null) {
//
//                String industyCode = i.getIndustyCode();
//                String detailedUnitName = i.getDetailedUnitName();
//                String firstTwoDigits = getFirstTwoDigits(industyCode);
//                System.out.println(firstTwoDigits);
//                System.out.println(firstTwoDigits);
////                if (firstTwoDigits != null) {
//                LambdaQueryWrapper<Gb> lambdaUpdateWrappera = new LambdaQueryWrapper<>();
//                lambdaUpdateWrappera
//                        .select(Gb::getName)
//                        .eq(Gb::getCode,firstTwoDigits);
//                Gb data = gbService.getOne(lambdaUpdateWrappera);
//                System.out.println(data);
//                System.out.println(firstTwoDigits);
//                if(data!=null){
//                    LambdaUpdateWrapper<EntInfo> lambdaUpdateWrapper1 = new LambdaUpdateWrapper();
//                    lambdaUpdateWrapper1
//                            .set(EntInfo::getGbnameone,data.getName())
//                            .eq(EntInfo::getDetailedUnitName,detailedUnitName);
//                    qiService.update(lambdaUpdateWrapper1);
//                    System.out.println(data);
//                }
//                }
//        }
//    }
//
//    @Test
//    public void tfFocus() {
//String a="'重庆雅迪科技有限公司', '重庆爱玛车业科技有限公司', '重庆京东方显示技术有限公司', '重庆华峰锦纶纤维有限公司', '重庆一德粮油有限公司', '亚士创能新材料（重庆）有限公司', '惠伦晶体（重庆）科技有限公司', '重庆市九龙万博新材料科技有限公司', '重庆市中润化学有限公司', '精诚工科汽车零部件（重庆）有限公司', '诺博汽车零部件（重庆）有限公司', '重庆百亚卫生用品股份有限公司', '重庆市欧华陶瓷（集团）有限责任公司', '重庆美心.麦森门业有限公司', '中国石化股份有限公司润滑油重庆分公司', '重庆溢哲渝实业有限公司', '红蜻蜓（重庆）植物油脂有限公司', '重庆凯瑞特种车有限公司', '致伸科技（重庆）有限公司', '重庆哈斯特铝板带有限公司', '重庆市中光电显示技术有限公司', '重庆庚业新材料科技有限公司', '重庆大江动力设备制造有限公司', '重庆望变电气（集团）股份有限公司', '渝丰科技股份有限公司', '重庆奕翔化工有限公司', '鞍钢蒂森克虏伯（重庆）汽车钢有限公司', '重庆美利信科技股份有限公司', '重庆新希望饲料有限公司', '重庆金鸿纬科技有限公司', '重庆海亮铜业有限公司', '重庆保时鑫电子科技有限公司', '重庆恒都食品开发有限公司', '重庆顶津食品有限公司', '重庆两江联创电子有限公司', '重庆建工建材物流有限公司', '云阳金田塑业有限公司', '重庆希尔安药业有限公司', '重庆万里新能源股份有限公司', '重庆环松科技工业有限公司', '重庆红九九食品有限公司', '重庆韩泰轮胎有限公司', '重庆聚光新材料科技股份有限公司', '重庆安道拓锋奥汽车部件系统有限公司', '重庆云天化天聚新材料有限公司', '重庆新民康科技有限公司', '纬腾（重庆）信息技术服务有限公司', '奥的斯机电电梯（重庆）有限公司', '重庆润际远东新材料科技股份有限公司', '重庆日和电子有限公司', '重庆宝钢汽车钢材部件有限公司', '重庆金茂联合电子有限公司', '马钢（重庆）材料技术有限公司', '南方天合底盘系统有限公司', '重庆市梁平区奇爽食品有限公司', '曼德汽车零部件（重庆）有限公司', '重庆科美模具有限公司', '重庆敏华家具制造有限公司', '中冶赛迪装备有限公司', '中粮粮油工业（重庆）有限公司', '重庆飞华环保科技有限责任公司', '川亿电脑（重庆）有限公司', '杜拉维特(中国)洁具有限公司', '重庆特瑞新能源材料有限公司', '重庆吉力芸峰实业(集团)有限公司', '恒安(重庆)生活用纸有限公司', '重庆綦远远成铝业有限公司', '峻凌电子（重庆）有限公司', '重庆国丰实业有限公司', '重庆龙海石化有限公司', '重庆宏立至信科技发展集团有限公司', '重庆龙润汽车转向器有限公司', '重庆力华自动化技术有限责任公司', '重庆市北方铝业有限公司', '重庆市宇邦线缆有限公司', '徐工重庆建机工程机械有限公司', '重庆润鑫环保管材有限公司', '李尔长安（重庆）汽车系统有限公司', '重庆迪马工业有限责任公司', '重庆开洲九鼎牧业科技开发有限公司', '重庆市南方阻燃电线电缆有限公司', '重庆百事天府饮料有限公司', '重庆江电电力设备有限公司', '重庆琪金食品集团有限公司', '重庆瑞莲新能源科技有限公司', '重庆德呈威科技有限公司', '重庆市钱江食品(集团)有限公司', '攀中伊红金属制品（重庆）有限责任公司', '道道全重庆粮油有限责任公司', '重庆三峡云海药业股份有限公司', '重庆元利科技有限公司', '重庆万国半导体科技有限公司', '重庆三友机器制造有限责任公司', '重庆捷荣汇盈精密制造有限公司', '精元(重庆)电脑有限公司', '广州双桥（重庆）有限公司', '重庆秋田齿轮有限责任公司', '重庆市鹿享家科技有限公司', '重庆光大产业有限公司', '北斗星通智联科技有限责任公司'";
//
//
//
//
//   }
//}
