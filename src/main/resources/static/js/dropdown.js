function account(){
    const targetDiv = document.getElementById("formthing")
    const featured = document.getElementById("featured")
    featured.style.display = "none";
    targetDiv.style.display = "block";

}

function animation(){
    const targetDiv = document.getElementById("formthing")
    const featured = document.getElementById("featured")
    featured.style.display = "none";
    targetDiv.style.display = "none";
}

function featured(){
    const targetDiv = document.getElementById("formthing")
    const featured = document.getElementById("featured")
    featured.style.display = "block";
    targetDiv.style.display = "none";
}



function createAccount() {
    let name = document.getElementById("name");
    let username = document.getElementById("email");
    let password = document.getElementById("password");
    let password2 = document.getElementById("password2");

    let newEmployee = {
        "username" : `${username.value}`,
        "password" : `${password.value}`,
    };

    let body = JSON.stringify(newEmployee);

    const URL = "http://localhost:8080/createEmployee";

    const postObject = {
        headers: {
            'Content-Type': 'application/json',
        },
        method: "POST",
        body: body,
        credentials: "include"
    };

    if (password.value === password2.value){
        fetch(URL, postObject)
            .then(response => response.json())
            .then(data =>{
                console.log("Succes!", data)
            }).catch((error) => {
                console.log("error", error)
        });

    }
}

    document.getElementById("submitBtn").addEventListener('click', upload);


function upload(ev){
    ev.preventDefault();

    let h = new Headers();
    h.append('Accept', 'application/json');

    let fd = new FormData();
    let myFile = document.getElementById("featured_img").files[0];
    fd.append('featured_img', myFile);

    let req = new Request('http://localhost:8080/uploadFile', {
        method: 'POST',
        headers: h,
        mode: 'no-cors',
        body: fd
    })

    fetch(req)
        .catch((error) => {
        console.log("error", error)
    })

}

