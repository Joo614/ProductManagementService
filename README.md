## 📌 서비스 요구사항
**1. 마트 권한과 일반 사용자 권한이 구분되어있다.**  
➡ 권한을 `ADMIN(MART)`, `CUSTOMER`로 구분하였습니다.

    
**2. 상품에 대한 생성, 수정, 삭제는 마트 권한이 필요하다.**  
➡ 해당 API들은 SecurityConfig에서 hasRole()로 권한을 달리 주어 구현하였습니다.


**3. 상품을 생성할 수 있다.**  
➡ 이 후 특정 시점의 상품 가격 조회 기능을 위해 생성 후 ProductRepository와 ProductPriceHistoryRepository에 각각 save하였습니다. 


**4. 상품 가격을 수정할 수 있다.**  
➡ 가격 수정 후 최신 상품 정보를 불러올 수 있도록 productRepository에 저장하고, 상품 가격 히스토리 정보도 불러올 수 있도록 ProductPriceHistoryRepository에도 save하였습니다. 


**5. 특정 시점의 상품 가격을 조회할 수 있다.**  
➡ 상품의 가격 변동 히스토리가 따로 저장될 수 있도록 ProductPriceHistory 도메인을 따로 만들고, 상품을 생성 시와 가격 수정 시 모두 productPriceHisotry 레포에 저장이 되도록 하여 조회 시점에 가격을 수정한 시점의 시각을 파라미터로 함께 넣어주어 가격을 조회할 수 있도록 구현하였습니다.
  >예시)
  >- 2023-01-01 00:00:00 시점의 A 상품 가격 = 1500원
  >- 2023-01-15 12:00:00 시점의 A 상품 가격 = 2000원  


**6. 상품을 삭제할 수 있다.**  
➡ 해당 productId를 가진 상품을 삭제하여 noContent()로 리턴하였습니다.  


**7. 주문에 대한 총 금액을 계산할 수 있다.**  
   - (각 주문 목록의 상품가격 * 개수) + 배달비  
➡ 주문 postDto에서 요청받은 orderItems를 stream으로 순환하며 (수량*가격)을 계산하였고 배달비는 임의로 3000원으로 넣어 계산하였습니다.

  
**8. 주문에 대한 필요 결제 금액을 계산할 수 있다.**  
   - 쿠폰을 적용하는 경우, 쿠폰으로 할인되는 금액을 반영해서 계산  
➡ 먼저, 쿠폰을 적용하였다면 해당 쿠폰의 scope가 FULL_ORDER인지 SPECIFIC_PRODUCT인지 switch를 사용하여 구분하였고,  
그 안에서도 쿠폰 Type이 RATIO인 경우와 FIXED인 경우를 각각 따로 계산하여 필요한 결제 금액을 리턴할 수 있도록 구현하였습니다.

---
## 📌 이외에 추가한 로직
- Spring Security를 이용한 회원 가입 및 로그인
- 회원 정보 조회 API
- 상품 전체 조회 API
- 전체 상품의 가격 히스토리 조회 API
- 쿠폰 생성 API

---
## 📌 API 명세
- [asciidoctor를 사용하여 REST Docs를 만들었습니다.](http://localhost:63342/ead61b63-b0a6-4ff2-a49a-86be75ccfd1a/source?file=C%3A%2FUsers%2FUSER%2FDesktop%2FProductManagementService%2Fsrc%2Fdocs%2Fasciidoc%2Findex.adoc&mac=aYwA7CcddKbYJROcM3UM0S/6SJXndsHjrq9V9HPwIYs=&projectUrl=C%3A%2FUsers%2FUSER%2FDesktop%2FProductManagementService)

