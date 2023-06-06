document.querySelectorAll(".items").forEach((skin) => {
    skin.addEventListener('click', () => {
        const idVenda = skin.getAttribute('data-id');
        window.location.href = `/skinCompra/compra.html?id=${encodeURIComponent(idVenda)}`;
    })
});


const gradeDiv = document.querySelector('.grade');


function mostrarSkins() {
    getAllMovementsSkins().then((movements) => {
        movements.forEach((movementSkin) => {
            const imagemSkin = document.createElement('img');
            const novaLista = document.createElement('ul');
            const nomeLi = document.createElement('li');
            const precoLi = document.createElement('li');
            const raridadeLi = document.createElement('li');

            const divItem = document.createElement('div');
            divItem.classList.add('items');
            divItem.style.cursor = 'pointer';
            divItem.setAttribute('data-id', movementSkin.idVenda);

            divItem.addEventListener('click', () => {
                const idVenda = divItem.getAttribute('data-id');
                window.location.href = `/skinCompra/compra.html?id=${encodeURIComponent(idVenda)}`;
            });


            var nome = movementSkin.arma + ' ' + movementSkin.nome;

            imagemSkin.src = '/skin_imagens/' + movementSkin.imagem;
            imagemSkin.alt = nome;
            imagemSkin.classList.add('cardimg');


            nomeLi.classList.add('nomeSkin');
            nomeLi.textContent = 'Nome: ' + nome;

            precoLi.classList.add('precoSkin');
            precoLi.textContent = 'Preço: ' + movementSkin.preco + ' pontos';

            raridadeLi.classList.add('raridadeSkin');
            raridadeLi.textContent = 'Raridade: ' + movementSkin.raridade;
            divItem.appendChild(imagemSkin);
            divItem.appendChild(novaLista);
            novaLista.appendChild(nomeLi);
            novaLista.appendChild(precoLi);
            novaLista.appendChild(raridadeLi);

            gradeDiv.appendChild(divItem);
        });
    });
}



async function getAllMovementsSkins() {
    const response = await fetch('http://localhost:8080/movement/skinMovements', {
        method: 'GET',
        headers: {
            "content-type": "application/json"
        }
    });

    const myJson = await response.json();
    return myJson;
}


document.addEventListener('DOMContentLoaded', function () {
    mostrarSkins();
});
