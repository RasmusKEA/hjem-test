const requestObject = {
    method : "GET",
    "content-type" : "application/json",
    redirect : "follow"
}

function fetchAllImages() {
    //Port 9090 er til "backend" port 8080 til frontend.
    fetch('http://34.237.223.97:9090/findAllImages', requestObject)
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

        if (image.featuredID !== '5'){
            const aTag = document.createElement('a')
            aTag.href = image.shopLink;

            const itemName = document.createElement('p')
            itemName.innerText = image.itemName;
            itemName.className = "itemName";

            imgTag.setAttribute('src', 'http://34.237.223.97:9090/downloadFile/' + `${image.featuredID}`);
            imgTag.style.width = '200px';
            imgTag.style.height = '300px';
            aTag.append(imgTag)
            singleFeatDiv.append(aTag);
            singleFeatDiv.append(itemName)
            featDiv.append(singleFeatDiv);
        }else {
            const animationContainer = document.getElementById('animationContainer');
            const animationDiv = document.createElement('div');
            const animation = document.createElement('IMG');
            animationDiv.className = 'animationDiv';
            animation.setAttribute('src', 'http://34.237.223.97:9090/downloadFile/5');
            animationDiv.append(animation);
            animationContainer.append(animationDiv);
        }
    })
}



