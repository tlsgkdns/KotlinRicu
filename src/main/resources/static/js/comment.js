const commentClient = axios.create(
    {
        headers: {
            "Authorization": localStorage.getItem("jwt")
        }
    }
)
async function getCommentCount(galleryId, boardNum)
{
    const result = await axios.get(`/galleries/${galleryId}/boards/${boardNum}/comments/count`)
    return result.data
}
async function removeComment(galleryId, boardNum, commentID)
{
    const result = await commentClient.delete(`/galleries/${galleryId}/boards/${boardNum}/comments/${commentID}`)
    return result.data
}
async function getList(galleryId, boardNum, page, size, goLast)
{
    const result = await axios.get(`/galleries/${galleryId}/boards/${boardNum}/comments`, {params: {page, size}})
    if(goLast)
    {
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))
        return getList(galleryId, boardNum, lastPage, size)
    }
    return result.data
}

async function addComment(galleryId, boardNum, commentText)
{
    console.log(commentText)
    const result = await commentClient.post(`/galleries/${galleryId}/boards/${boardNum}/comments`, {"commentText": commentText})
    return result.data
}