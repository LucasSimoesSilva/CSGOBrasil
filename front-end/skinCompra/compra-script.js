const urlParams = new URLSearchParams(window.location.search);
const id_venda = urlParams.get('id');

var nomeSkin = document.getElementById('nomeSkin');
var precoSkin = document.getElementById('precoSkin');
var raridadeSkin = document.getElementById('raridadeSkin');
var imagemSkin = document.getElementById('skinImage');
var btnComprar = document.getElementById('comprar')
var id_comprador = localStorage.getItem('usuarioId')

async function getSkinById(id){
    const response = await fetch('http://localhost:8080/skin/'+id, {
        method: 'GET',
        headers: {  
            "content-type": "application/json"
        }
    });

    await response.json().then((result) => {
        var nome = result.arma +' '+result.nome;
        completePage(nome, result.preco, result.raridade, result.imagem);
    });
}


async function getMovementById(id){
    const response = await fetch('http://localhost:8080/movement/'+id, {
        method: 'GET',
        headers: {  
            "content-type": "application/json"
        }
    });

    await response.json().then((result) => {
        getSkinById(result.idSkin);
    });
};

getMovementById(id_venda);

function completePage(nome, preco, raridade, imagem){
    nomeSkin.textContent = nome;
    precoSkin.textContent = 'Preço: '+preco + ' pontos';
    raridadeSkin.textContent = 'Raridade: '+raridade;
    imagemSkin.src = '/skin_imagens/'+imagem;
}


btnComprar.addEventListener('click', () => {
    var data = {
        idVenda: id_venda,
        idComprador: id_comprador
    }

    makeMovement(data).then((result) => {
        if(result){
            btnComprar.disabled = true;
        }
    });

    
});

async function makeMovement(data) {
    const response = await fetch('http://localhost:8080/movement', {
        method: 'PUT',
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify(data)
    });

    const myJson = await response.json();
    return myJson;
}