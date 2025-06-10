# OldProject - Spring Boot Application

一個基於 Spring Boot 3.4.4 和 Java 21 的全端 Web 應用程式，整合了 JWT 認證、郵件服務、資料庫管理和第三方金流支付等功能。

## 🚀 技術棧

### 後端框架
- **Spring Boot 3.4.4** - 主要框架
- **Spring Security** - 安全認證與授權
- **Spring Data JPA** - 資料持久層
- **Hibernate** - ORM 框架
- **Java 21** - 開發語言

### 資料庫
- **MySQL** - 主要資料庫
- **AWS RDS** - 雲端資料庫服務

### 認證與安全
- **JWT (JSON Web Token)** - 無狀態認證
- **Spring Security** - 安全框架

### 第三方整合
- **ECPay** - 金流支付服務
- **Gmail SMTP** - 郵件發送服務

### 開發工具
- **Lombok** - 減少樣板代碼
- **Spring Boot DevTools** - 開發時熱重載
- **SpringDoc OpenAPI** - API 文檔生成
- **Maven** - 專案管理工具

## 📋 主要功能

- 🔐 **用戶認證系統** - JWT Token 認證
- 📧 **郵件服務** - 支援 Gmail SMTP
- 💳 **金流整合** - ECPay 第三方支付
- 📁 **檔案上傳** - 支援多媒體檔案上傳
- 🌐 **CORS 跨域支援** - 前後端分離架構
- 📊 **API 文檔** - OpenAPI/Swagger 整合
- 🗄️ **資料庫管理** - JPA/Hibernate ORM

## 🛠️ 環境要求

- **Java**: JDK 21 或以上
- **Maven**: 3.6+ 
- **MySQL**: 8.0+
- **Node.js**: 18+ (前端開發)

## 🚀 快速開始

### 1. clone專案
```bash
git clone https://github.com/ChanglinTsaiForJava/EEITgroupFinal.git
cd EEITgroupFinal
```

### 2. 資料庫設定
創建 MySQL 資料庫：
```sql
CREATE DATABASE final;
CREATE USER 'final'@'localhost' IDENTIFIED BY 'final';
GRANT ALL PRIVILEGES ON final.* TO 'final'@'localhost';
```

### 3. 環境配置
根據您的環境修改 `application.properties`：

#### 開發環境 (預設)
- 埠口: `8082`
- 資料庫: AWS RDS (已配置)
- 前端 CORS: 支援多個開發埠口

#### 生產環境
使用 `application-prod.properties`：
- 埠口: `8080`
- 本地資料庫配置

### 4. 郵件服務設定
在 `application.properties` 中設定您的 Gmail 憑證：
```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

### 5. ECPay 金流設定
配置您的 ECPay 測試帳號資訊：
```properties
ecpay.merchant-id=your-merchant-id
ecpay.hash-key=your-hash-key
ecpay.hash-iv=your-hash-iv
```

應用程式將在 `http://localhost:8082` 啟動

## 📁 專案結構

```
src/
├── main/
│   ├── java/eeit/OldProject/
│   │   ├── SpringBootConfig.java      # CORS 跨域配置
│   │   └── ...                        # 其他業務邏輯
│   └── resources/
│       ├── application.properties      # 開發環境配置
│       └── application-prod.properties # 生產環境配置
└── test/                              # 測試程式碼
```

## 🔧 重要配置說明

### CORS 跨域設定
支援多個前端開發環境：
- 開發伺服器: `localhost:5173`, `localhost:4173`
- 預覽伺服器: `localhost:4174`, `localhost:4175`
- Nginx 代理: `localhost:6173`
- 內網 IP 支援多個網段

### 檔案上傳
- 最大檔案大小: 20MB
- 最大請求大小: 20MB
- 支援多媒體檔案類型

### Session 設定
- Session 超時: 1440 分鐘 (24小時)

## 📚 API 文檔

啟動應用程式後，可以通過以下地址查看 API 文檔：
- Swagger UI: `http://localhost:8082/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8082/v3/api-docs`

## 🔐 安全特性

- JWT Token 認證機制
- Spring Security 安全框架
- CORS 跨域請求控制
- 檔案上傳安全限制

## 🚀 部署說明

### 本地部署
```bash
mvn clean package
java -jar target/OldProject-0.0.1-SNAPSHOT.war
```

### 生產環境部署
1. 設定生產環境 profile: `-Dspring.profiles.active=prod`
2. 配置生產資料庫連線
3. 設定適當的 CORS 允許網域
4. 配置生產環境的金流與郵件服務

## 👥 開發團隊

這是一個團隊協作專案，感謝所有貢獻者的努力！

## 📝 授權

本專案僅供學習和展示用途。

---

# 📰 News 系統後端 API 說明文件

本文件為 Spring Boot 開發之新聞管理系統的後端 API 說明。功能包含：
- 新聞 CRUD（新增、查詢、修改、刪除）
- 草稿/發布管理
- 熱門排行
- 彈性搜尋（關鍵字、分類、狀態、日期、排序）
- 前後台分流（/admin 與 /public）

---

## 📁 資料結構說明

### News Entity 範例
```json
{
  "newsId": 1,
  "title": "AI 正在改變世界",
  "content": "<p>內容</p>",
  "tags": "AI,科技",
  "status": 1,
  "publishAt": "2025-04-25T10:00:00",
  "createBy": "Allen",
  "modifyBy": "Allen",
  "viewCount": 123,
  "category": {
    "categoryId": 2,
    "categoryName": "科技"
  }
}
```

---

## ✅ 後台 API `/news/admin`

| 功能       | 方法 | 路徑 | 說明 |
|------------|------|------|------|
| 查所有     | GET  | `/news/admin` | 支援分頁排序 |
| 查單筆     | GET  | `/news/admin/{id}` | - |
| 新增       | POST | `/news/admin` | status 預設為 0 |
| 修改       | PUT  | `/news/admin/{id}` | - |
| 刪除       | DELETE | `/news/admin/{id}` | - |
| 發布       | PATCH | `/news/admin/{id}/publish` | status = 1 |
| 下架       | PATCH | `/news/admin/{id}/unpublish` | status = 0 |
| 彈性搜尋   | GET  | `/news/admin/search` | keyword、分類、狀態、時間、排序 |

### 🔍 搜尋條件參數
- `keyword`：模糊查詢 title、content、tags
- `categoryId`：分類 ID
- `status`：0=草稿、1=發布
- `dateFrom`、`dateTo`：時間區間
- `page`、`size`、`sort=欄位,desc`

---

## 🌐 前台 API `/news/public`

| 功能         | 方法 | 路徑 | 說明 |
|--------------|------|------|------|
| 查已發布列表 | GET  | `/news/public` | 預設依 publishAt DESC |
| 查單筆       | GET  | `/news/public/{id}` | 只有 status = 1 的才會成功，會自動 viewCount +1 |
| 搜尋新聞     | GET  | `/news/public/search` | 與後台相同但固定 status=1 |

---

## 📌 測試用 Postman URL 範例

### 查熱門前 5 筆新聞（前台）
```
GET /news/public?sort=viewCount,desc&page=0&size=5
```

### 查 4 月份「活動」新聞（後台）
```
GET /news/admin/search?keyword=活動&dateFrom=2025-04-01T00:00:00&dateTo=2025-04-30T23:59:59
```

### 新增新聞（後台）
```
POST /news/admin
Body (JSON):
{
  "title": "新新聞",
  "content": "<p>內容</p>",
  "tags": "AI,新聞",
  "category": { "categoryId": 2 },
  "createBy": "Allen",
  "modifyBy": "Allen"
}
```

---

## 🔧 技術棧
- Spring Boot 3
- Spring Data JPA
- MySQL
- Pageable + Sort 排序機制

---

若需前端整合或 Swagger/OpenAPI 文件，可延伸提供。
