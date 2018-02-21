# 漢字組建
[![Build Status](https://travis-ci.org/sih4sing5hong5/han3_ji7_tsoo1_kian3.svg?branch=master)](https://travis-ci.org/sih4sing5hong5/han3_ji7_tsoo1_kian3)
[![Test Coverage](https://img.shields.io/codecov/c/github/sih4sing5hong5/han3_ji7_tsoo1_kian3.svg)](https://codecov.io/gh/sih4sing5hong5/han3_ji7_tsoo1_kian3)

產生方言、古書、古樂譜、姓名等漢語缺字。

* 功能展示
  * [意傳文化科技](http://意傳.台灣/%E7%B7%9A%E4%B8%8A%E7%B5%84%E5%AD%97)
  * [維基線上測試](https://ids-testing.wmflabs.org/wiki/Main_Page)

## 變遷
0712 程式修改成通用時jetty/Tomcat server的方式

## 開發說明

### 打包成war佈署到本地端的tomcat執行
```bash
./gradlew tomcatRunWar
```
* 可以到[localhost:8080/han3_ji7_tsoo1_kian3/組字式.png?字體=宋體](http://localhost:8080/han3_ji7_tsoo1_kian3/⿴辶⿴宀⿱珤⿰隹⿰貝招.png?字體=宋體)取得 PNG 和 SVG 了
* 程式起始點
  * `src/main/java/idsrend/services/IDSrendServlet.java`

### 打包成war
```bash
./gradlew war
```
* 包好的`war`會放在`build/libs`裡面
* 程式起始點
  * `src/main/java/idsrend/services/IDSrendServlet.java`
  
### 執行Jetty（獨立程式模式）
```bash
./gradlew run
```

* 可以到 [localhost:8060/組字式.png?字體=宋體](http://localhost:8060/%E2%BF%B1%E2%BF%B0(%E2%BF%B0V%E2%BF%B0)%E2%BF%B0@%E2%BF%B0@%E2%BF%B0(%E2%BF%B0V)%E2%BF%B0%E4%B8%89%E2%BF%B0(%E2%BF%B0_%E2%BF%B0_%E2%BF%B0_%E2%BF%B0_%E2%BF%B0)%E4%B8%89.png?%E5%AD%97%E9%AB%94=%E5%AE%8B%E9%AB%94) 取得 PNG 和 SVG 了。
* 程式起始點
  * `src/main/java/idsrend/services/HttpserverJetty.java`

### 試驗
```bash
./gradlew assemble
./gradlew check
```
* 若是試驗失敗，無法整合進`master`
* 程式起始點
  * `src/tets/java`

### 更新文件
傳程式的javadoc到gh-pages面頂
```bash
npm i && npm run deploy
```

## 程式流程
[javadoc](https://xn--5nqy36c.xn--p8s937b.xn--v0qr21b.xn--kpry57d)雖然沒有整理程式流程，不過說明可以加減參考一下

因為寫兩冬矣，所以寫程式的慣勢有改變，對英文→華文→閩南語，我~~會揣時間~~正在重構成華語。以「cc.連線服務.組字介面」來講，程式攏總有七步：

* 解析工具
    * 組字式→部件樹
* 部件結構調整工具
    * 部件樹→部件樹
* 揀字工具
    * 部件樹→活字樹
* 排版工具
    * 活字樹→活字樹
* 定版工具
    * 活字樹→單一活字（無字體外框）
* 筆觸工具
    * 單一活字（無字體外框）→單一活字（含字體外框）
* 印刷工具
    * 單一活字（含字體外框）→圖

詳細的功能請看java文件（/doc)

## 程式授權
本程式乃自由軟體，您必須遵照Affero通用公眾特許條款（Affero General Public License, AGPL)來修改和重新發佈這一程式，詳情請參閱條文。授權大略如下，若有歧異，以授權原文為主： 
1. 得使用、修改、複製並發佈此程式碼，且必須以通用公共授權發行；
2. 任何以程式碼衍生的執行檔或網路服務，必須公開全部程式碼； 
3. 將此程式的原始碼當函式庫引用入商業軟體，需公開非關此函式庫的任何程式碼

此開放原始碼、共享軟體或說明文件之使用或散佈不負擔保責任，並拒絕負擔因使用上述軟體或說明文件所致任何及一切賠償責任或損害。

漢字組建緣起於本土文化推廣與傳承，非常歡迎各界推廣使用，但希望在使用之餘，能夠提供建議、錯誤回報或修補，回饋給這塊土地。

謝謝您的使用與推廣～～

## 字體授權
* CNS11643全字庫：
    * [中華民國國家發展委員會](http://data.gov.tw/node/5961)提供
    * 授權：[政府資料開放授權條款-第1版](http://data.gov.tw/license )，相容「 創用CC授權 姓名標示 4.0 國際版本 」 

* 「中央研究院漢字部件檢字系統」2.65版釋出聲明
    * ……，但於「漢字字型」部份，則考量其具有圖形著作的分殊特性，故另行採用「GNU自由文件授權條款1.2版本(GNU Free Documentation License 1.2，以下簡稱『GFDL1.2』)」，以及「創用CC 姓名標示-相同方式分享台灣授權條款2.5版(Creative Commons Attribution-Share Alike 2.5 Taiwan，以下簡稱為『CC-BY-SA 2.5 TW』)」兩種授權方式併行釋出。
    * http://cdp.sinica.edu.tw/cdphanzi/declare.htm

* 吳守禮台語注音字型：
    * ©2012從宜工作室：吳守禮、吳昭新，以CC0 1.0通用(CC0 1.0)方式在法律許可的範圍內，拋棄本著作依著作權法所享有之權利，並宣告將本著作貢獻至公眾領域。將台語注音標註轉化為本字型之工作，由吳昭新與莊德明共同完成。使用者可以複製、修改、發布或展示此作品，亦可進行商業利用，完全不需要經過另行許可。
    * http://xiaoxue.iis.sinica.edu.tw/download/WSL_TPS_Font.htm
