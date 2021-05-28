const accountBtn = document.getElementById("formthing")
const featuredBtn = document.getElementById("featured")
//const animationBtn = document.getElementById("animation")

document.getElementById("formthing").style.display = "block"
document.getElementById("featured").style.display = "none"

function account(){
    featuredBtn.style.display = "none";
    accountBtn.style.display = "block";
}

function animation(){
    featuredBtn.style.display = "none";
    accountBtn.style.display = "none";
}

function featured(){
    featuredBtn.style.display = "block";
    accountBtn.style.display = "none";
}



function createEmployee() {
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

document.addEventListener('DOMContentLoaded', init)

function init(){
    document.getElementById('submitBtn').addEventListener('click', upload);
}


function upload(ev){
   ev.preventDefault();

    let h = new Headers();
    h.append('Accept', 'application/json');

    let fd = new FormData();
    let myFile = document.getElementById("featured_img").files[0];
    let featElem = document.getElementById("featNumber");
    let featNumber = featElem.value;
    fd.append('featNumber', featNumber);
    fd.append('featured_img', myFile);



    let req = new Request('http://localhost:8080/uploadFile', {
        method: 'POST',
        headers: h,
        mode: 'no-cors',
        body: fd
    })



    if(featNumber.includes("Hvilket featured item")){
        window.alert("Vælg venligst hvilket item du vil erstatte")
    }else{
        fetch(req)
            .catch((error) => {
                console.log("error", error)
            })
    }




}

