

const boardClient = axios.create(
    {
        headers: {
            "Authorization": localStorage.getItem("jwt")
        }
    }
)

async function getBoard(gno, bno) {
    const result = await axios.get(`/galleries/${gno}/boards/${bno}`)
    return result.data
}

async function deleteBoard(galleryId, boardNum) {
    await boardClient.delete(`/galleries/${galleryId}/boards/${boardNum}`)
}

async function getBoardList(galleryId, page, size, keyword, type, popular) {
    const result = await axios.get(`/galleries/${galleryId}/boards/`, {
        params: {
            "page": page,
            "size": size,
            "keyword": keyword,
            "types": type,
            "popular": popular
        }
    })
    return result.data
}

async function writeBoard(galleryId, title, content) {
    const result = await boardClient.post(`/galleries/${galleryId}/boards`, {"title": title, "content": content})
    return result.data
}

async function editBoard(galleryId, boardNum, title, content) {
    const result = await boardClient.patch(`/galleries/${galleryId}/boards/${boardNum}`, {
        "title": title,
        "content": content
    })
    return result.data
}

async function clickLike(galleryId, boardNum) {
    const result = await boardClient.patch(`/galleries/${galleryId}/boards/${boardNum}/likes`)
    return result.data
}

async function addView(galleryId, boardNum)
{
    const result = await axios.patch(`/galleries/${galleryId}/boards/${boardNum}/views`)
    return result.data
}