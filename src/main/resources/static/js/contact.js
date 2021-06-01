document.addEventListener('DOMContentLoaded', init)

function init(){
    document.getElementById('createEmpBtn').addEventListener('click', sendMail);
}

function sendMail(ev) {
    ev.preventDefault()

    let name = document.getElementById("name");
    let tlfnr = document.getElementById("tlfnr");
    let email = document.getElementById("email");
    let subject = document.getElementById("subject")
    let text = document.getElementById("text");

    if(name.value === "" || tlfnr.value === "" || email.value === "" || subject.value === "" || text.value === "" ){
        window.alert("Venligst udfyld alle felter")
    }else{
        let newMail = {
            "name" : `${name.value}`,
            "phone" : `${tlfnr.value}`,
            "mail" : `${email.value}`,
            "subject" : `${subject.value}`,
            "text" : `${text.value}`
        }
        let body = JSON.stringify(newMail);

        const postObject = {
            headers: {
                'Content-Type': 'application/json',
            },
            method: "POST",
            body: body,
            credentials: "include"
        };

        const URL = "http://localhost:8080/sendMail";

        fetch(URL, postObject)
            .then(response => response.json())
            .then(data => {
                console.log("Succes!", data)
            }).catch((error) => {
            console.log("error", error)
        });
        window.alert("Din besked er nu sendt. Vi vender tilbage hurtigst muligt")
        location.reload();
    }





}