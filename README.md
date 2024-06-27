# ShoesCream(신발 입찰 거래 플랫폼) 프로젝트 - Kream 사이트 기반 개발

## 🚀 프로젝트 소개

슈크림(shoescream)은 원하는 가격에 상품을 사고 팔수 있는 입찰 거래 eCommerce 플랫폼입니다. 사용자는 리뷰 기능을 활용해 원하는 상품을 탐색하고, 동시에 다양한 상품을 결제하고 판매 할 수 있는 통합 서비스를 경험할 수 있습니다. 이 서비스는 사용자 중심의 웹 사이트로, 원하는 상품을 쉽고 편리하게 거래할 수 있는 환경을 제공합니다.

### 📅 프로젝트 실행 기간

2024년 4월 16일부터 2024년 6월 20일까지

### 👤 프로젝트 인원 구성
Front-End

|                         [심채윤](https://github.com/chaeyun-sim)                          |                       [배동우](https://github.com/BaeDongWoo)                        |                       [서유민](https://github.com/seoyumin1018)                       |
|:---------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------:|
| <img width="130px" src="https://avatars.githubusercontent.com/u/111689342?v=4" /> | <img width="130px" src="https://avatars.githubusercontent.com/u/114900672?v=4"/> | <img width="130px" src="https://avatars.githubusercontent.com/u/137030928?v=4"/> |

Back-End

|                       [배준오](https://github.com/Junobee25)                       |                       [최나영](https://github.com/rxmxntic)                       |
|:---------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------:|
| <img width="130px" src="https://avatars.githubusercontent.com/u/109403631?v=4" /> | <img width="130px" src="https://avatars.githubusercontent.com/u/121682792?v=4"/> |


---

## 📜 프로젝트 설명

슈크림은 사용자들이 원하는 상품을 원하는 가격에 입찰 등록 할 수 있고 상품 구매까지 할 수 있는 플랫폼입니다. 사용자들은 상품에 대한 실시간 정보와 리뷰를 소셜 미디어 형식으로 접하며, 이를 기반으로 정보에 입각한 구매, 판매 결정을 할 수 있습니다.

## 🗂 ERD

![shoescream ERD](https://github.com/shoescream/backend/assets/109403631/c7a6a7ea-7f1e-43c0-b521-ca7018dd082c)

---

## 📚 API 명세서 [Click](https://www.notion.so/API-dff7ba4cbafb44da8063a4e787975ebc)
<img src="https://github.com/shoescream/backend/assets/109403631/93a40a5b-dd5f-4c9a-8784-4cf4433b2870">
---

## 🌟 주요 기능

- **로그인 기능**: 사용자는 일반 로그인과 Oauth 인증 방식(Kakao Login)의 로그인을 모두 경험 할 수 있습니다.
- **본인 인증 기능**: 회원 가입 시 JavaMailSender를 통한 이메일 본인 인증이 이뤄집니다.
- **실시간 알림 기능**: SSE를 적용하여 입찰 거래 체결시 구매자에게 실시간 알림이 전송될 수 있도록 개발하였습니다.
- **결제 기능**: Kakao Pay API를 통해 간편한 결제 프로세스를 경험할 수 있습니다.
- **시세 조회 기능**: 사용자는 기간 별 단일 상품에 대한 시세(당시 거래 가격)를 조회할 수 있습니다.
- **랭킹 기능**: 단일 상품 조회 수를 기반으로 카테고리별 인기 상품을 조회할 수 있습니다.
- **상품 정보 공유 및 리뷰 기능**: 상품에 대한 정보 게시, 댓글 작성을 통해 다른 사용자와 경험을 공유할 수 있습니다.

---

## 🛠 기술 스택

### 🖥 Backend
- Java17
- Spring Boot
- Spring Security
- JPA / Hibernate
- MySQL
- SSE(Servcer-Sent-Events)
- AWS EC2, S3
- Nginx

### 🖥 Frontend
- JavaScript
- TypeScript
- React.js
- Next.js
- Html/Css(styled-components)
---

## 🚧 프로젝트 아키텍처
<img src="https://github.com/shoescream/backend/assets/109403631/dfcdf833-9553-4d4b-b4b3-78b471f84fca">
---

## 📈 성능 최적화 및 트러블슈팅

프로젝트 개발 과정에서 발생한 주요 성능 최적화 작업과 트러블슈팅 사례를 공유합니다. 이는 프로젝트 진행 중 직면한 기술적 문제를 해결하고, 프로젝트의 전반적인 성능을 향상시킨 경험을 공유하는 자료입니다.

### 성능 최적화 사례

### 트러블슈팅 경험

## 🔎 코드 리뷰

- 1회차(2024.06.27) 
  - [리뷰 작성 메서드 리팩토링](https://github.com/shoescream/backend/pull/73)