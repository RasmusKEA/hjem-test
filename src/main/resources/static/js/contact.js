

function sendMail() {

    let name = document.getElementById("name");
    let addr = document.getElementById("addr");
    let tlfnr = document.getElementById("tlfnr");
    let email = document.getElementById("email");
    let subject = document.getElementById("subject")
    let text = document.getElementById("text");

    let newMail = {
        "name" : `${name.value}`,
        "address" : `${addr.value}`,
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

}