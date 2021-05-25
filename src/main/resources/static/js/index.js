const requestObject = {
    method : "GET",
    "content-type" : "application/json",
    redirect : "follow"
}

function fetchAllImages() {
    fetch('http://localhost:8080/findAllImages', requestObject)
        .then(response => response.json())
        .then(images => loadImages(images));
}

fetchAllImages();

function loadImages(images){
    images.forEach(image =>{
        const featDiv = document.getElementById("feat" + `${image.featuredID}`);
        const imgTag = document.createElement('IMG');
        imgTag.setAttribute('src', 'http://localhost:8080/downloadFile/' + `${image.featuredID}`);
        featDiv.append(imgTag);
    })
}



