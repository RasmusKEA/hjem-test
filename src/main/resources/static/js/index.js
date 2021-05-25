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
        const featDiv = document.getElementById('featContainer');
        const singleFeatDiv = document.createElement('div')
        const imgTag = document.createElement('IMG');
        singleFeatDiv.className = 'featured-item';
        imgTag.setAttribute('src', 'http://localhost:8080/downloadFile/' + `${image.featuredID}`);
        imgTag.style.width = '200px';
        imgTag.style.height = '300px';
        singleFeatDiv.append(imgTag);
        featDiv.append(singleFeatDiv);
    })
}



