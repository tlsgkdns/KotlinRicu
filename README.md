# 개요
## 소개
공통된 관심사를 이야기할 수 있는 갤러리를 생성하여, 갤러리마다의 게시글을 작성할 수 있는 커뮤니티 게시판,
기존의 Java 프로젝트인 [RICU](https://github.com/tlsgkdns/ricu)를 부트캠프를 통해 배운 Kotlin 으로 옮기고, 구조를 개선한 프로젝트
## 개발 기간
2024-03-12 ~ 2024-04-02
## 개발 환경
* OS: Window 10
* IDE: Intellij IDEA 2023.3.1]
* 실행 브라우저: Chrome
## 기술 스택
* 언어: Kotlin, Javascript
* Front-end: JavaScript, Thymeleaf, Axios
* Back-end: SpringBoot(3.2.1), Spring-Security, QueryDSL, JPA
* Database: MySQL
## ERD
<img src="https://github.com/tlsgkdns/RicuKotlin/assets/24753709/a928284c-8eab-4859-938f-90a5c7a6b465" width="500"></img>

# 주요 변경점
## 클라이언트 사이드 렌더링 방식
 * Thymeleaf를 최소화했다.  
 * Rest-API를 적극적으로 활용할 수 있는 방법을 고민한 결과, 기존의 서버 사이드 렌더링 방식에서, 클라이언트 사이드 렌더링 방식에 가까운 형식으로 전환했다.
 * React나 Vue의 사용을 고민했지만, 러닝 커브가 있고, 시간이 많이 없었기에, 기존의 Thymeleaf를 재활용해서, 클라이언트 사이드 방식으로 전환했다.
 * 다만, 몇몇 뼈대에서 Thymeleaf를 지우는 것은 힘들었기에, Thymeleaf를 부분적으로 도입하였다.

<img src="https://github.com/tlsgkdns/RicuKotlin/assets/24753709/882d6552-9ddd-4826-bb70-f8c4af28f845" width="500"></img>
> 기존의 SSR 방식의 프론트엔드를

<img src="https://github.com/tlsgkdns/RicuKotlin/assets/24753709/537f8c48-80bf-4139-9aea-684c60382c49" width="500"></img>
> CSR 방식에 가까운 방식으로 전환했다.

## 커맨드 패턴
 * 커맨드 패턴을 활용해서 확장성을 늘렸다.
 * 특정 값이 유효한지 프론트엔드에 바로바로 보여주는 형식을 택했는데 유효값을 검증하는 메소드의 뼈대가 비슷해서, 코드의 중복을 줄이는 방법을 고민하였다.
 * 유효 값 검증은 다른 곳에서도 사용을 할 것이고, 유효 기준도 그때 그때 달라질 수 있어서, 좀 더 확장성 있는 방법을 선호했고, 따라서 이 방식을 택했다.
 * 되도록 서버와 클라이언트 사이의 통신을 줄이고 싶어서, 처음엔, 유효값 검증을 프론트엔드에서 했지만, 비동기 함수의 도입으로 인해서 다른 함수에 영향이 미쳐서 백엔드로 옮겼다.

<img src="https://github.com/tlsgkdns/RicuKotlin/assets/24753709/41988e0c-1527-4d03-afed-ac5ace0e99f1" width="500"></img>
> 반복되는 검증 메소드를

<img src="https://github.com/tlsgkdns/RicuKotlin/assets/24753709/3223eec0-da97-480b-b5fc-d74fb8831d67" width="250"></img>
<img src="https://github.com/tlsgkdns/RicuKotlin/assets/24753709/0832dfd5-2c95-44ef-ad45-796dad9d302f" width="250"></img>
> 확장성 있도록 수정
