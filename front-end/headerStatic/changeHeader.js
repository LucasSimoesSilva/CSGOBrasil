var id = localStorage.getItem("usuarioId");
var loginLink = document.getElementById("login");
var skinsLink = document.getElementById("skins");
var relatorioLink = document.getElementById("relatorio");
var dropdowDiv = document.getElementById("dropdowDiv");
var logout = document.getElementById("logout");

var cargo = "cliente"


async function getUserById(id){
    const response = await fetch('http://localhost:8080/user/'+id, {
        method: 'GET',
        headers: {
            "content-type": "application/json"
        }
    });

    const myJson = await response.json();
    return myJson;
}


if (id > 0) {
    loginLink.classList.add("hiddenItem");
    skinsLink.classList.remove("hiddenItem")
    
    var usuarioLink = document.createElement("a");
    getUserById(id).then((result) => {
        usuarioLink.textContent = result.nome;
        usuarioLink.href = "#"
        cargo = result.cargo

        if(cargo === "admin"){
            relatorioLink.classList.remove("hiddenItem");
        }
    })
    
    usuarioLink.classList.add("nomeUsuario");
    dropdowDiv.appendChild(usuarioLink);
    dropdowDiv.classList.remove("hiddenItem")
    dropdowDiv.classList.add("dropdown");
    dropdowDiv.classList.add("last-element-bar");
}


logout.addEventListener('click', () => {
    localStorage.setItem("usuarioId",0);
    location.reload();

})