const accountBtn = document.getElementById("formthing")
const featuredBtn = document.getElementById("featured")

document.getElementById("formthing").style.display = "block"
document.getElementById("featured").style.display = "none"

function account(){
    featuredBtn.style.display = "none";
    accountBtn.style.display = "block";
}

function featured(){
    featuredBtn.style.display = "block";
    accountBtn.style.display = "none";
}



function createEmployee(ev) {
    ev.preventDefault();
    let name = document.getElementById("name")
    let username = document.getElementById("email");
    let password = document.getElementById("password");
    let password2 = document.getElementById("password2");

    if(name.value === "" || username.value === "" || password.value === "" || password2.value === ""){
        window.alert("Venligst udfyld alle felterne")
    }else{
        let newEmployee = {
            "username" : `${username.value}`,
            "password" : `${password.value}`,
        };

        let body = JSON.stringify(newEmployee);
        //Port 9090 er til "backend" port 8080 til frontend.
        const URL = "http://34.237.223.97:9090/createEmployee";

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
            window.alert("Kontoen er nu oprettet")
            location.reload()

        }else{
            window.alert("Begge kodeord skal være ens")
        }
    }
}

document.addEventListener('DOMContentLoaded', init)

function init(){
    document.getElementById('submitBtn').addEventListener('click', upload);
    document.getElementById('createEmpBtn').addEventListener('click', createEmployee);
}


function upload(ev){
   ev.preventDefault();

    let h = new Headers();
    h.append('Accept', 'application/json');

    let fd = new FormData();
    let myFile = document.getElementById("featured_img").files[0];
    let featElem = document.getElementById("featNumber");
    let featNumber = featElem.value;
    let linkElem = document.getElementById("shopLink");
    let shopLink = linkElem.value;
    let itemName = document.getElementById("itemName").value;

    fd.append('featNumber', featNumber);
    fd.append('shopLink', shopLink)
    fd.append('itemName', itemName)
    fd.append('featured_img', myFile);

    if(document.getElementById("featured_img").files.length === 0 || featElem.value === "" || shopLink === "" || itemName === ""){
        window.alert("Udfyld venligst alle felter")
    }else{
        let req = new Request('http://34.237.223.97:9090/uploadFile', {
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
            window.alert("Featured item / animation er nu uploaded og erstattet")
        }

    }
}

