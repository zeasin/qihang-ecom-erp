# 启航电商ERP系统


启航电商ERP系统是一套为中小电商企业构建的一套简单、实用、覆盖全流程的电商系统，本项目采用Java SpringBoot3+Vue2前后端分离开发。 



支持供应商一件代发和仓库发货两种发货方式，功能覆盖采购、网店订单处理、供应商一件代发、仓库发货、网店售后、网店商品管理、仓库出入库、采购结算、代发结算等功能，基本上覆盖了电商日常业务。 

支持的电商平台有：淘宝、京东、拼多多、抖店、视频号小店、快手、小红书。

![预览](docs/preview.png)

**为方便不是程序员的兄弟们想要看演示效果，本人特地制作了一个windows一键演示包，解压后直接按上面的步骤运行即可，不需要任何额外安装。**

[获取一件演示包](https://mp.weixin.qq.com/s/MtXFijnq0Ti461hO5Sulhw)

## 项目介绍
**启航电商ERP可以说是我这五年以来的工作经验成果。**

公司从2019年踏入电商以来，一直都是由我组建和带领一帮技术人员从零开始建设了一套完全适应公司业务需要的电商ERP系统，包括WMS仓库系统、OMS订单处理系统、财务系统、直播运营系统等子系统组成。主要功能模块包括：采购模块、出入库模块、订单发货模块、网店订单管理模块、电子面单打印模块。公司ERP对接了批批网、1688、蘑菇街、淘宝天猫、拼多多、抖店、快手小店平台。

### 技术栈
+ Java17
+ SpringBoot3
+ Redis
+ Nacos
+ MyBatis-Plus
+ MySQL8


### 公司应用场景一：抖店直播


```mermaid
graph TD
A[早上7点开播] -->B(10点结束直播-一般是500单左右)
    B --> C[使用ERP系统打单-对接平台电子面单接口]
    C --> E(库存核对)
    E --> F[库存不足]
    F --> F1(采购补货)
    F1 --> G1
    E --> G(库存充足)
    G --> G1[打印订单]
    G1 --> G5(推送快递单号到平台)
    G1 --> G2(生成备货单)
    G2 --> G3(仓库人员备货出库)
    G3 --> H(完成)
    G5 --> H
```


## 一、功能模块
### 供应链管理
+ 供应商管理：管理供应商信息
+ 采购订单管理：管理采购流程，包括供应商选择、采购订单生成、采购合同管理等。
+ 采购账单管理
+ 采购退货管理
+ 采购物流管理：跟踪采购订单物流信息。
+ 供应商代发管理：管理一件代发订单。
+ 代发账单管理

**采购流程**

```mermaid
graph LR
A[创建采购订单] -->B(审核)
    B --> C[供应商确认]
    C --> E[供应商发货]
    E --> F1(生成物流信息)
    F1 --> G1[确认收货]
    G1 --> G3[生成入库单]
    G3 --> G4(入库)
    G1 --> G2[生成财务应付及明细]
    G4 --> H(完成)
    G2 --> H

```

### 订单管理
+ 订单管理：所有导入的订单统一管理，
  + 查询、管理所有店铺审核过的订单。
+ 创建订单：手动创建订单。
+ 店铺订单导入：处理和管理多平台原始订单。
  + 支持淘宝、京东、拼多多、抖店、微信视频号小店、快手小店、小红书平台订单接口；
  + 审核订单，审核之后订单到统一订单库；
  + 更新订单，更新订单信息和状态


### 发货管理
+ 备货清单：展示需要发货的订单明细
  + 生成出库单
+ 拣货出库：拣货出库、生成出库单减库存；
+ 打包发货：记录包裹信息、物流发货、同步发货状态；
+ 物流跟踪：跟踪发货快递物流；
+ 供应商代发管理：管理供应商代发的订单

**订单发货流程**
```mermaid
graph TD
A[网店拉取订单] -->B(审核订单)
    B --> C[订单库]
    C --> C1[备货清单中展示需要发货的订单]
    C1 --> D[仓库发货-生成出库单]
    C1 --> E[分配供应商发货]
    D --> F(拣货出库)
    F --> F1[出库]
    E --> H(打包发货-记录包裹信息)
    F1 --> H
    H --> G(推送发货信息-记录包裹信息-生成发货费用-物流费和代发费)
    G --> I(完成)
```

**仓库发货流程**
```mermaid
graph TD
A[查询备货清单] -->B(生成拣货单)
    B --> C[拣货出库]
    C --> E(减库存)
    E --> F[打包发货]
    F --> F1(记录包裹信息)
    F1 --> G(填写物流信息)
    G --> G1[同步发货状态]
    G1 --> H(完成)
    G --> G2[生成物流费用账单]
    G2 --> H

```

### 售后管理
对退货、换货、维修等售后处理进行管理，包括退款审核、退货入库、退款处理等环节。
+ 店铺售后管理：处理和管理多平台售后包括录入售后数据、退货入库、换货处理等。
  + 支持拼多多、抖店、快手小店、小红书平台售后接口；
  + 支持手动录入、备注；
+ 退货处理：数据录入、仓库收货确认、库存处理等。
+ 换货处理：数据录入、仓库收货确认、仓库发货、库存处理等。

**退货退款流程**
```mermaid
graph LR
A[录入退款退货] -->B(仅退款)
    B --> H(完成)
    A --> D(退货退款)
    D --> E[仓库收货]
    E --> F[退货入库处理]
    F --> H
```

**售后流程**
```mermaid
graph LR
A[录入售后] -->B(补发商品)
    B --> H[仓库出库-记录]
    A --> D(换货)
    D --> E[仓库收货-入库]
    E --> H
    H --> F(完成)
```
**订单拦截**
```mermaid
graph LR
A[录入拦截] -->B(通知仓库)
    B --> H1[未发货-直接入库]
    B --> D(已发货)
    D --> E[通知消费者拒收]
    E --> H[消费者拒收]
    H --> C[退回入库]
    C --> F(退款给消费者)
    F --> G[完成]
    H1 --> G
```
### 店铺管理
+ 店铺商品管理
  + 淘宝商品管理：同步淘宝店铺商品，关联到ERP商品（用于仓库发货处理）；
  + 多多商品管理：同步多多店铺商品，关联到ERP商品（用于仓库发货处理）；
  + 抖店商品管理：同步抖店店铺商品，关联到ERP商品（用于仓库发货处理）；
  + 京东、视频号、快手、小红书同上
+ 店铺管理：网店管理、API参数设置；
+ 平台参数设置

### 库存管理

+ 入库管理
+ 出库管理
+ 库存查询：跟踪和管理库存，包括批次管理、库存盘点、库存调整、库存预警等。
+ 库位管理


### 商品管理
商品信息、分类信息、属性信息等管理。


## 二、部署说明

**项目采用SpringBoot+vue2开发。具体使用方法如下**



#### 2.1 配置启动MySQL

+ 创建数据库`qihang-erp`并导入sql脚本`docs\qihang-erp.sql`



#### 2.3 启动Redis
项目开发采用Redis7

#### 2.4 启动Nacos
项目开发采用Nacos2.2.0


#### 2.3 启动后端api
+ 修改`api`项目中的配置文件`application.yml`配置`Nacos`相关。
+ Nacos新建dataId:`ecerp-dev`配置项，配置内容在`docs\NacosConfig\ecerp-dev.txt`文件中。
+ IDE启动项目


#### 2.4 启动前端 `vue`
+ `npm install`
+ `npm run dev`
+ 打包`npm run build:prod`
+ 访问web
  + 访问地址：`http://localhost`
  + 登录名：`admin`
  + 登录密码：`admin123`


## 三、获取一键演示包

启航电商ERP系统自从开源以来，收到很多兄弟们的关注，也很多兄弟们想看演示效果，由于项目是非商业化的没有财力去支撑演示环境服务器，为了满足兄弟们想看系统演示，作者特地制作了一个一键演示包，获取之后直接在Windows电脑中即可运行。

[一键演示包获取地址](https://mp.weixin.qq.com/s/MtXFijnq0Ti461hO5Sulhw)


## 四、支持作者

**感谢大家的关注与支持！希望利用本人从事电商10余年的经验帮助到大家提升工作效率！**

💖 如果觉得有用记得点 Star⭐


### 4.1 有偿服务
+ [提供本地部署服务](https://mp.weixin.qq.com/s/8U4NvMiAP0vDsTDBzlHJbw)
+ [提供一键演示包](https://mp.weixin.qq.com/s/MtXFijnq0Ti461hO5Sulhw)
+ [提供定制化开发服务](https://mp.weixin.qq.com/s/U-1FKfa84Dfz17WL9GHyqw)
+ [提供电商系统软著代申请服务（文档、源代码）](https://mp.weixin.qq.com/s/8N1PeNHw9jCBR__AsSjeqg)


### 4.2 更多服务

更多服务，请关注作者微信公众号：qihangerp168

<img src="docs/公众号.jpg" width="300px" />


💖 欢迎一起交流！ 

### 4.3 捐助支持
作者为兼职做开源,平时还需要工作,如果帮到了您可以请作者吃个盒饭

<img src="docs/weixinzhifu.jpg" width="300px" />
<img src="docs/zhifubao.jpg" width="300px" />



