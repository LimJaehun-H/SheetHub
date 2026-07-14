# SheetHub 🥁
드럼 악보 공유 사이트 백엔드 API

## 기술 스택
- Java 21
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA
- H2 / MySQL
- Lombok

---

## 실행 방법
1. `application.properties` DB 설정 확인
2. 서버 실행 (`localhost:8080`)
3. H2 콘솔: `localhost:8080/h2-console`

---

## 사용 흐름

SheetHub API는 아래 순서로 사용합니다.

```
1. 회원가입        POST /sheetHub/join
2. 로그인          POST /sheetHub/login  →  JWT 토큰 발급
3. 토큰을 헤더에 담아 이후 API 호출
       Authorization: Bearer {토큰}
4. 악보 등록/조회/삭제
5. 댓글 등록/수정/삭제
```

> `/join`, `/login` 을 제외한 모든 API는 JWT 인증이 필요합니다.
> 수정/삭제는 본인이 작성한 것만 가능합니다.

---

## 에러 코드

| 코드 | 설명 |
|---|---|
| 400 | 입력값 오류 (필수 항목 누락 등) |
| 401 | 인증 실패 (토큰 없음 / 만료 / 비밀번호 틀림) |
| 403 | 권한 없음 (본인 것이 아닌 리소스 수정/삭제 시도) |
| 404 | 리소스 없음 |
| 409 | 중복 (이미 존재하는 username) |

---

## 회원 API

### 회원가입
- **POST** `/sheetHub/join`
- Request
```json
{
  "username": "test",
  "password": "1234"
}
```
- Response: `200 OK`

### 로그인
- **POST** `/sheetHub/login`
- Request
```json
{
  "username": "test",
  "password": "1234"
}
```
- Response: `200 OK`
```
eyJhbGciOiJIUzI1NiJ9...
```

---

## 악보 API 🔒

### 악보 등록
- **POST** `/sheetHub/sheets`
- Request
```json
{
  "title": "Blinding Lights",
  "artist": "The Weeknd"
}
```
- Response: `200 OK`
```json
{
  "id": 1,
  "title": "Blinding Lights",
  "artist": "The Weeknd"
}
```

### 악보 전체 조회
- **GET** `/sheetHub/sheets`
- Response: `200 OK`
```json
[
  {
    "id": 1,
    "title": "Blinding Lights",
    "artist": "The Weeknd"
  }
]
```

### 악보 제목으로 검색
- **GET** `/sheetHub/sheets?title={제목}`
- Response: `200 OK`
```json
[
  {
    "id": 1,
    "title": "Blinding Lights",
    "artist": "The Weeknd"
  }
]
```

### 악보 삭제 (본인만 가능)
- **DELETE** `/sheetHub/sheets/{id}`
- Response: `200 OK`

---

## 댓글 API 🔒

### 댓글 등록
- **POST** `/sheetHub/sheets/{sheetId}/comments`
- Request
```json
{
  "comment": "test comment"
}
```
- Response: `200 OK`
```json
{
  "id": 1,
  "comment": "test comment",
  "name": "test"
}
```

### 댓글 수정 (본인만 가능)
- **PUT** `/sheetHub/sheets/{sheetId}/comments/{commentId}`
- Request
```json
{
  "comment": "test comment updated"
}
```
- Response: `200 OK`
```json
{
  "id": 1,
  "comment": "test comment updated",
  "name": "test"
}
```

### 댓글 삭제 (본인만 가능)
- **DELETE** `/sheetHub/sheets/{sheetId}/comments/{commentId}`
- Response: `200 OK`

### 내 댓글 모아보기
- **GET** `/sheetHub/members/comments`
- Response: `200 OK`
```json
[
  {
    "commentId": 1,
    "comment": "test comment"
  }
]
```
