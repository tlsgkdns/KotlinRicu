async function uploadToServer(formObj)
{
    console.log("upload to server........")

    const response = await axios(
        {
            method: 'post',
            url: '/images',
            data: formObj,
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        }
    );
    return response.data;
}
async function removeFileToServer(uuid, fileName)
{
    const response = await axios.delete(`/images/${uuid}_${fileName}`)
    return response.data
}
async function removeFileLinkToServer(link)
{
    await axios.delete(`/images/${link}`)
}

function getExtension(filename) {
    const parts = filename.split('.');
    return parts[parts.length - 1];
}

function isImage(filename) {
    const ext = getExtension(filename);
    switch (ext.toLowerCase()) {
        case 'jpg':
        case 'gif':
        case 'bmp':
        case 'png':
            //etc
            return true;
    }
    return false;
}
function showUploadModal(e, modal)
{
    e.preventDefault()
    e.stopPropagation()
    modal.show()
}
function hideUploadModal(e, modal)
{
    e.preventDefault()
    e.stopPropagation()
    modal.hide()
}

function uploadImage(fileInput, showImage)
{
    const formObj = new FormData();

    console.log(fileInput.files)

    const files = fileInput.files

    for(let i=0; i < files.length; i++)
    {
        if(!isImage(files[i].name))
        {
            alert("You have to insert Image File!")
            fileInput.innerHTML = ''
            continue
        }
        formObj.append("files", files[i]);
        console.log(files[i] + " ADDED!")
    }
    console.log("Upload Btn Clicked!")
    uploadToServer(formObj).then(result => {
        for(const uploadResult of result)
        {
            console.log("Show UpLoad!!!!")
            showImage(uploadResult)
        }
        uploadModal.hide()
    }).catch(e => {
        console.log("ERROR!")
        uploadModal.hide()
    })
}