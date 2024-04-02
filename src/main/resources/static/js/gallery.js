const galleryClient = axios.create(
    {
        headers: {
            "Authorization": localStorage.getItem("jwt")
        }
    }
)
async function getGalleryListData(page, size, keyword)
{
    const result = await axios.get(`/galleries`, {params: {page, size, keyword}})
    return result.data
}

async function createGallery(title, explanation, galleryImageName)
{
    let imageName = null
    if(galleryImageName) imageName = galleryImageName.getAttribute("data-src")
    const result = await galleryClient.post(`/galleries`, {
        title: title,
        explanation: explanation,
        galleryImageName: imageName
    })
    return result.data
}
async function getGallery(galleryId)
{
    const result = await axios.get(`/galleries/${galleryId}`)
    return result.data
}

async function editGallery(galleryId, explanation, popularThreshold, galleryImage)
{
    const result = await galleryClient.patch(`/galleries/${galleryId}`, {
        "explanation": explanation, "popularThreshold": popularThreshold, "galleryImageName": galleryImage
    })
    return result.data
}
async function isAvailableTitle(title)
{
    console.log(title)
    const result = await axios.get(`/galleries/available/title`, {
        params: {
            "wantCheckValue" : title
        }
    })
    return result.data
}
/*
val galleryUrl: String,
    val title: String,
    val explanation: String,
    val galleryImageName: String?
 */