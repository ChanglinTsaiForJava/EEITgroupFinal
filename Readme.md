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
