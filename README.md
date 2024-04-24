# 개요
## 소개
공통된 관심사를 이야기할 수 있는 갤러리를 생성하여, 갤러리마다의 게시글을 작성할 수 있는 커뮤니티 게시판
## 기획 의도
부트캠프에서 배운 것을 적용해보기 위해서, 첫 개인 웹 프로젝트였던, [RICU](https://github.com/tlsgkdns/ricu)를 Kotlin 으로 옮기고, 개선하기 위해 기획하였다.
## 개발 기간
2024-03-12 ~ 2024-04-02
## 개발 환경
* OS: <img src="https://img.shields.io/badge/window 10-0078D6?style=for-the-badge&logo=windows 10&logoColor=white">
* IDE: <img src="https://img.shields.io/badge/intellij 2023.3.1-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
* 실행 브라우저: <img src="https://img.shields.io/badge/chrome-4285f4?style=for-the-badge&logo=googlechrome&logoColor=white">
## 기술 스택
* Front-end: <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white"> <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white"> <img src="https://img.shields.io/badge/axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white">
* Back-end: <img src="https://img.shields.io/badge/kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"> <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">, QueryDsl
* Database: <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
* ORM: JPA
## ERD
<img src="https://github.com/tlsgkdns/RicuKotlin/assets/24753709/a928284c-8eab-4859-938f-90a5c7a6b465" width="500"></img>

---

# 구현 단계
 * 1단계: 갤러리, 게시판, 댓글 CRUD
 * 2단계: Spring Security로 회원가입/로그인 기능 구현, 갤러리 및 프로필 사진 CRD
 * 3단계: 프론트엔드
 * 4단계: Rest-API를 활용한 추천 검색어 기능
 * 5단계: 값 유효 여부를 바로 보여주는 기능 
---

# 세부 사항
## 커스텀 에러의 고민
JPARepository에서 엔티티가 없을 경우 커스텀 에러 발생을 웠했지만, 엔티티마다 커스텀 에러 발생 메소드를 정의하는 것이 번거롭게 여겨졌기에 개선을 고민하였다.
### JPARepository를 상속 받는 클래스를 생성한 뒤, 이 클래스에서 커스텀 에러 발생시키면 어떨까?
  * Repository를 상속 받은 클래스 내에서 쿼리와 상관없는 메스드를 생성할 수가 없어서 구현 자체가 안 된다.
  * 거기다가, Service단이 아닌 Repository단에서 커스텀 에러를 발생시키는 것은 바람직하지 않아서 이 안은 폐기했다.
### Generic을 활용해서 엔티티 ID와 Repository를 받는 static 메소드 생성하면 어떨까?
  * 엔티티 마다의 메소드 정의 없이 커스텀 에러 발생
  * 중복 코드가 최소화되었고, 확장성 있어서, 이 안을 채용하였다.

## 클라이언트 사이드 렌더링 방식 채택
 * Thymeleaf를 최소화했다.
 * Rest-API를 적극적으로 활용할 수 있는 방법을 고민한 결과, 기존의 서버 사이드 렌더링 방식에서, 클라이언트 사이드 렌더링 방식에 가까운 형식으로 전환했다.
 * React나 Vue의 사용을 고민했지만, 러닝 커브가 있고, 시간이 많이 없었기에, 기존의 Thymeleaf를 재활용해서, 클라이언트 사이드 방식으로 전환했다.
 * 다만, 몇몇 뼈대에서 Thymeleaf를 지우는 것은 힘들었기에, Thymeleaf를 부분적으로 도입하였다.

``` html
<div class="card">
        <div class="card-header">
            Writer: [[${dto.writer}]]&nbsp;|&nbsp;<svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor" class="bi bi-hand-thumbs-up my-1" viewBox="0 0 16 16">
            <path d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2.144 2.144 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a9.84 9.84 0 0 0-.443.05 9.365 9.365 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111L8.864.046zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a8.908 8.908 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.224 2.224 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.866.866 0 0 1-.121.416c-.165.288-.503.56-1.066.56z"/>
        </svg>
            <span class="likeCountHeadTxt">
                [[${dto.likeCount}]]
            </span>
            <div class="float-end">
                [[${#temporals.format(dto.regDate, 'yyyy-MM-dd HH:mm:ss')}]]
            </div>
        </div>
        <div class="card-body">
            <br>
            <div th:each="line: ${#strings.listSplit(dto.content, newLineChar)}">
                [[${line}]]
            </div>
            <br><br><br><br><br><br><br><br>
            <div class="offset-5">
                <button class="btn btn-success likeBtn"><svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-hand-thumbs-up my-1" viewBox="0 0 16 16">
                    <path d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2.144 2.144 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a9.84 9.84 0 0 0-.443.05 9.365 9.365 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111L8.864.046zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a8.908 8.908 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.224 2.224 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.866.866 0 0 1-.121.416c-.165.288-.503.56-1.066.56z"/>
                </svg>&nbsp;<span style="size: 50px"><h3 class="likeCountBtnTxt">Like: [[${dto.likeCount}]]</h3></span></button>
            </div>
            <div class="my-4" th:with="user=${#authentication.principal}">
                <div class="float-end" th:with="link = ${pageRequestDTO.getLink()}">
                    <a th:href="|@{/gallery/board/list}?id=${galleryDTO.galleryID}&${link}|" class="text-decoration-none">
                        <button type="button" class="btn btn-primary">List</button>
                    </a>
                    <a sec:authorize="isAuthenticated()" th:if="${user.nickname == dto.writer || user.memberID == galleryDTO.managerID}"
                       class="text-decoration-none">
                        <button type="button" class="btn btn-danger removeBtn">Remove</button>
                    </a>
                    <a sec:authorize="isAuthenticated()" th:if="${user.nickname == dto.writer}"
                       th:href="|@{/gallery/board/modify(id=${galleryDTO.galleryID}, bno=${dto.bno})}&${link}|" class="text-decoration-none">
                        <button type="button" class="btn btn-secondary">Modify</button>
                    </a>
                </div>
            </div>
        </div>
    </div>
```
> 기존의 SSR 방식의 프론트엔드를

```javascript
getBoard(gid, boardNum).then(board => {
        if(board.errorCode) notExistPage()
        boardBtns.innerHTML += ` <div class="float-end">
                    <a href="/gallery/board/list?id=${gid}&page=${boardPage}" class="text-decoration-none">
                        <button type="button" class="btn btn-primary">List</button>
                    </a>`
        isAuthenticated().then(data => {
            if(data) {
                getLoginUsername().then(username => {
                    if(board.creatorUsername === username) {
                        boardBtns.innerHTML += `<a class="text-decoration-none">
                                    <button type="button" class="btn btn-danger removeBtn" onclick="removeBoard()">Remove</button>
                                    </a>`
                        boardBtns.innerHTML += `<a class="text-decoration-none">
                                        <button type="button" class="btn btn-secondary" onclick="getEditBoardPage()">Modify</button>
                                         </a>`
                    }
                    boardBtns.innerHTML += `</div>`
                })
            }
        })
        document.getElementById("likeCountBtnTxt").innerHTML += `Like: ${board.likeCount}`
        document.getElementById("likeCountHeadTxt").innerHTML += `${board.likeCount}`
        document.getElementById("boardTitle").innerHTML += `${board.title}`
        document.getElementById("boardWriter").innerHTML += `Writer: ${board.creatorUsername} |`
        let contentLine = ''
        for(const line of board.content.split('\n')) contentLine += line + `<br>`
        document.getElementById("boardContent").innerHTML += contentLine
        document.getElementById("boardCreatedTime").innerHTML += dateFormat(board.createdTime)
    })
```
> CSR 방식에 가까운 방식으로 전환했다.

## 커맨드 패턴
 * 커맨드 패턴을 활용해서 확장성을 늘렸다.
 * 특정 값이 유효한지 프론트엔드에 바로바로 보여주는 형식을 택했는데 유효값을 검증하는 메소드의 뼈대가 비슷해서, 코드의 중복을 줄이는 방법을 고민하였다.
 * 유효 값 검증은 다른 곳에서도 사용을 할 것이고, 유효 기준도 그때 그때 달라질 수 있어서, 좀 더 확장성 있는 방법을 선호했고, 따라서 이 방식을 택했다.
 * 되도록 서버와 클라이언트 사이의 통신을 줄이고 싶어서, 처음엔, 유효값 검증을 프론트엔드에서 했지만, 비동기 함수의 도입으로 인해서 다른 함수에 영향이 미쳐서 백엔드로 옮겼다.

```java
    @Override
    public int isAvailableNickname(String nickname)
    {
        if(nickname == null || nickname.length() < 3 || nickname.length() > 20 ||
                    !nickname.matches("[0-9|a-z|A-Z]*")) return -1;
        if(memberRepository.existsByNickname(nickname)) return 1;
        return 0;
    }

    @Override
    public int isAvailableID(String memberID)
    {
        if(memberID == null || memberID.length() < 3 || memberID.length() >  20 ||
                !memberID.matches("[0-9|a-z|A-Z]*")) return -1;
        if(memberRepository.existsById(memberID)) return 1;
        return 0;
    }
```
> 반복되는 검증 메소드를

```kotlin
class AvailableCheckList(
    val checkList: List<ValueChecker> = listOf()
) {
    fun checkValueAvailable(checkValue: String): AvailableResponse
    {
        for(checker in checkList)
            if(!checker.check(checkValue))
            {
                return AvailableResponse(false, checker.errorMessage)
            }
        return AvailableResponse(true, "사용할 수 있습니다.")
    }
}
```

```kotlin
class LengthChecker(
    private val minLength: Int,
    private val maxLength: Int,
    override val errorMessage: String = "길이는 $minLength 이상 $maxLength 이하여야 합니다."
): ValueChecker {
    override fun check(checkValue: String): Boolean {
        return checkValue.length >= this.minLength && checkValue.length <= this.maxLength
    }
}
```

```kotlin
    private val nicknameAvailableCheckList = AvailableCheckList(
        listOf(
            DuplicateChecker(memberRepository::existsByNickname),
            LengthChecker(3, 20),
            RegexChecker()
            )
    )
    private val usernameAvailableCheckList = AvailableCheckList(
        listOf(
            DuplicateChecker(memberRepository::existsByUsername),
            LengthChecker(3, 20),
            RegexChecker()
        )
    )
```

> 확장성 있도록 수정

# 결과
![image](https://github.com/tlsgkdns/RicuKotlin/assets/24753709/55979753-57e9-490b-a79d-ab89956330f0)
[여기](https://www.figma.com/file/wIPE420qzrb4f53U4WjNgL/KotlinRICU?type=design&node-id=0%3A1&mode=design&t=NYxpf2tXbEIm1T8H-1)에서 자세한 화면을 볼 수 있다.

# 개선해 볼 점
## 캐싱 기능
이 웹 사이트엔 좋아요 기능이 존재한다. 캐싱 기능을 이용하면 더 빠르게 게시글을 불러올 수 있을 것이다.
테스트를 할 땐 nGrinder 같은 도구를 활용해 볼 수 있을 것이다.

## 프론트엔드의 아쉬움
기존 RICU의 프론트엔드 디자인을 그대로 가져왔는데, Bootstrap 템플릿을 가져와서 조립한 것이라서, 다소 거친면이 있다.
위에서 언급된 이유로 인해 Thymeleaf를 사용했지만, 수정할 기회가 있다면, React나 Vue를 배워서 사용을 해보고 싶다.
