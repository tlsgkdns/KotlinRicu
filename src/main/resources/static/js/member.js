
const loginClient = axios.create(
    {
        headers: {
            "Authorization": localStorage.getItem("jwt")
        }
    }
)

async function isAuthenticated() {
    const result = await loginClient.get(`/members/username`)
    return result.data !== "anonymous"
}
async function isSameUser(creator)
{
    const result = await getLoginUsername()
    return creator === result
}
function needLoginPage()
{
    isAuthenticated().then(
        data => {
            if(!data)
            {
                alert("로그인을 해주세여.")
                location.href = "/member/login"
            }
        }
    )
}

function checkCreator(creator)
{
    isSameUser(creator).then(
        data => {
            if(data)
            {
                alert("접근 권한이 없습니다.")
                location.href = "/gallery/home"
            }
        }
    )
}

async function getLoginUsername() {
    const result = await loginClient.get(`/members/username`)
    return result.data
}

async function memberSignIn(username, password) {
    const result = await axios.post(`/members/signIn`, {"username": username, "password": password})
    return result.data
}

async function memberSignUp(nickname, username, email, password, profileImageName) {
    console.log(profileImageName)
    const result = await axios.post(`/members/signUp`, {
        "nickname": nickname,
        "username": username,
        "email": email,
        "password": password,
        "profileImageName": profileImageName
    })
    return result.data
}

async function getMember(memberId) {
    console.log(memberId)
    const result = await axios.get(`/members/${memberId}`)
    return result.data
}

async function editMember(memberId, email, nickname, profileImage) {
    const result = await axios.patch(`/members/${memberId}`, {
        "email": email,
        "nickname": nickname,
        "profileImage": profileImage
    })
    return result.data
}
async function isAvailableNickname(nickname)
{
    const result = await axios.get(`/members/available/nickname`, {
        params: {
            "wantCheckValue" : nickname
        }
    })
    return result.data
}
async function isAvailableUsername(username)
{
    const result = await axios.get(`/members/available/username`, {
        params: {
            "wantCheckValue" : username
        }
    })
    return result.data
}

