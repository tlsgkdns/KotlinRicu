# 概要
## 紹介
共通の関心事を話すことができるギャラリーを作成し、ギャラリーで掲示文を作成することができるコミュニティ掲示板
## 企画意図
ブートキャンプで学んだことを適用してみるために, 初めての個人ウェブプロジェクトだった, [RICU](https://github.com/tlsgkdns/ricu)をKotlinに移し，改善するために企画した
## 開発期間
2024-03-12 ~ 2024-04-02
## 開発環境
* OS: <img src="https://img.shields.io/badge/window 10-0078D6?style=for-the-badge&logo=windows 10&logoColor=white">
* IDE: <img src="https://img.shields.io/badge/intellij 2023.3.1-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
* 実行ブラウザ: <img src="https://img.shields.io/badge/chrome-4285f4?style=for-the-badge&logo=googlechrome&logoColor=white">
## 技術スタック
* Front-end: <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white"> <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white"> <img src="https://img.shields.io/badge/axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white">
* Back-end: <img src="https://img.shields.io/badge/kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"> <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">, QueryDsl
* Database: <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
* ORM: JPA
## ERD
<img src="https://github.com/tlsgkdns/RicuKotlin/assets/24753709/a928284c-8eab-4859-938f-90a5c7a6b465" width="500"></img>

---

# 実装段階
 * 1段階: ギャラリー、掲示板、コメント CRUD
 * 2段階: Spring Securityで会員登録/ログイン機能の追加、ギャラリー及びプロフィール写真 CRD
 * 3段階: フロントエンド
 * 4段階: Rest-APIを活用したおすすめ検索語機能
 * 5段階: テキストが有効かどうかをすぐに示す機能 
---
# 技術的意思決定
## カスタムエラーの悩み
JPARepositoryでエンティティがない場合、カスタムエラー発生を望んだが、エンティティごとにカスタムエラー発生メソッドを定義することが面倒に思われたため、改善に悩んだ。
### JPARepositoryを継承するクラスを生成した後、このクラスでカスタムエラーが発生させたらどうだろうか?
  * Repositoryを相続したクラス内で、クエリーと関係のないメスドを生成することができないため、具現自体ができない.
  * さらに、Service 端ではなくRepository 端でカスタムエラーを発生させることは望ましくないため、この案は廃棄した.
### Genericを活用してエンティティIDとRepositoryを受け取るstaticメソッドを生成したらどうだろうか?
  * エンティティごとのメソッド定義なしでカスタムエラー発生
  * 重複コードが最小化され、拡張性があるため、この案を採用した.
```kotlin
        fun <T, ID> getValidatedEntity(repository: JpaRepository<T, ID>, entityId: ID): T
        {
            val entity = repository.findByIdOrNull(entityId)
                ?: throw ModelNotFoundException(repository.javaClass.genericSuperclass.typeName, entityId.toString())
            return entity
        }
```

## クライアントサイドレンダリング方式の採用
 * Thymeleaf使用を最小化した
 * Rest-APIを積極的に活用できる方法を考えた結果、以前のサーバサイドレンダリング方式から、クライアントサイドレンダリング方式に近い形式に切り替えた。
 * ReactやVueの使用に悩んだが、ランニングカーブがあり、時間があまりなかったため、既存のThymeleafをリサイクルして、クライアントサイド方式に切り替えた。
 * ただし、いくつからThymeleafを消すのは難しいと判断して、Thymeleafを部分的に取り入れた。

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
> 以前のSSR方式のフロントエンドを

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
> CSR方式に近い方式に切り替えた

## コマンド·パターン
 * コマンドパターンを活用して拡張性を増した
 * 特定値が有効なのかフロントエンドにすぐに示す形式を選んだが、有効値を検証するメソッドの骨組みが似ていて、コードの重複を減らす方法を悩んだ
 * 有効値の検証は他の場所でも使用し、有効基準もその都度変わることがあるので、より拡張性のある方法を好んだので、この方式を選んだ。.
 * なるべくサーバとクライアントとの間の通信を減らしたいと思い、最初は有効値の検証をフロントエンドで行ったが、非同期関数の導入により他の関数に影響が出たため、バックエンドに移った

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
> 繰り返す検証メソッドを

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
        return AvailableResponse(true, "使用できます.")
    }
}
```

```kotlin
class LengthChecker(
    private val minLength: Int,
    private val maxLength: Int,
    override val errorMessage: String = "長さは $minLength 以上 $maxLength 以下でなければなりません"
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

> 拡張性があるように修正

# 結果.
![image](https://github.com/tlsgkdns/RicuKotlin/assets/24753709/55979753-57e9-490b-a79d-ab89956330f0)
[ここ](https://www.figma.com/file/wIPE420qzrb4f53U4WjNgL/KotlinRICU?type=design&node-id=0%3A1&mode=design&t=NYxpf2tXbEIm1T8H-1)で詳細な画面を見ることができる。

# 改善すべき点
## キャッシング機能
このウェブサイトにはいいね機能が存在する。 キャッシング機能を使えば，より早く掲示板を読み込むことができる。
テストをする時はnGrinderのようなツールを活用することを期待する。

## フロントエンドの物足りなさ
既存のRICUのフロントエンドデザインをそのまま持ってきたが、Bootstrapテンプレートを持ってきて組み立てたものなので、多少足りない面がある。
上記の理由によりThymeleafを使用したが、修正する機会があれば、ReactやVueを学んで使用してみたい
