# SheetHub 🥁

드럼 악보 공유 사이트 백엔드 API

## 기술 스택

- Java 25
- Spring Boot 4.1.0
- Spring Security + JWT
- Spring Data JPA
- H2 / MySQL
- Lombok

## 실행 방법

1. H2 콘솔 실행
2. `application.properties` DB 설정 확인
3. 서버 실행 (`localhost:8080`)

---

## API 명세

### 인증
`/join`, `/login` 을 제외한 모든 API는 JWT 인증이 필요합니다.

요청 헤더에 아래와 같이 토큰을 넣어주세요.
```
Authorization: Bearer {토큰}
```

### 에러 코드
| 코드 | 설명 |
|---|---|
| 401 | 인증 실패 (토큰 없음 / 만료 / 비밀번호 틀림) |
| 403 | 권한 없음 |
| 404 | 리소스 없음 |

---

## 회원 API

### 회원가입
- **POST** `/sheetHub/join`
- Request
```json
{
  "username": "jaehun",
  "password": "1234"
}
```
- Response: `200 OK`

### 로그인
- **POST** `/sheetHub/login`
- Request
```json
{
  "username": "jaehun",
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

### 악보 삭제
- **DELETE** `/sheetHub/sheets/{id}`
- Response: `200 OK`

---

## 댓글 API 🔒

### 댓글 등록
- **POST** `/sheetHub/sheets/{sheetId}/comments`
- Request
```json
{
  "comment": "드럼 파트 너무 좋다"
}
```
- Response: `200 OK`
```json
{
  "id": 1,
  "comment": "드럼 파트 너무 좋다",
  "name": "jaehun"
}
```

### 댓글 수정
- **PUT** `/sheetHub/sheets/{sheetId}/comments/{commentId}`
- Request
```json
{
  "comment": "드럼 파트 진짜 미쳤다"
}
```
- Response: `200 OK`
```json
{
  "id": 1,
  "comment": "드럼 파트 진짜 미쳤다",
  "name": "jaehun"
}
```

### 댓글 삭제
- **DELETE** `/sheetHub/sheets/{sheetId}/comments/{commentId}`
- Response: `200 OK`

### 내 댓글 모아보기
- **GET** `/sheetHub/members/comments`
- Response: `200 OK`
```json
[
  {
    "commentId": 1,
    "comment": "드럼 파트 너무 좋다"
  }
]
```
