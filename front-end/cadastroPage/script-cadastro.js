async function checkIfExist(data) {
    const response = await fetch('http://localhost:8080/user/register', {
        method: 'POST',
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify(data)
    });


    const myJson = await response.json();
    return myJson;
}

async function checkLogin(data) {
    const response = await fetch('http://localhost:8080/user/login', {
        method: 'POST',
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify(data)
    });

    const myJson = await response.json();
    return myJson;
}

async function makeLogin(email, senha) {
    const data = {
        email: email,
        senha: senha,
    };


    checkLogin(data).then((result) => {
        if (result) {
            getUserInfo(email).then(() => {
                window.location.href = "/homePage/home-page.html";
            });
        } else {
            alertText_login.classList.remove('invisibleText');
            alertText_login.classList.add('alertText');
        }
    });
}



async function getUserInfo(email) {
    const response = await fetch('http://localhost:8080/user/info', {
        method: 'POST',
        headers: {
            "content-type": "text/plain"
        },
        body: email
    });

    await response.json().then((usuario) => {
        localStorage.setItem("usuarioId", usuario.id);
    });
    
}


async function addUserPersistence(data) {
    const response = await fetch('http://localhost:8080/user', {
        method: 'POST',
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify(data)
    });
    const myJson = await response.json();
    return myJson;
}

// Função para adicionar um usuário à lista
function addUser(nome, email, senha) {
    const data = {
        nome: nome,
        email: email,
        senha: senha,
    };

    checkIfExist(data).then((result) => {
        if (result) {
            alertText_cad.classList.remove('invisibleText');
            alertText_cad.classList.add('alertText');
        } else {
            addUserPersistence(data);
            getUserInfo(email).then(() => {
                window.location.href = "/homePage/home-page.html";
            });
        }
    });


}

// Ação para cadastro de usuário
document.getElementById("form_cad").addEventListener("submit", function (event) {
    event.preventDefault();
    const nome = document.getElementById("nome_cad").value;
    const email = document.getElementById("email_cad").value;
    const senha = document.getElementById("senha_cad").value;

    addUser(nome, email, senha);
});

//Ação para login de usuário
document.getElementById("form_login").addEventListener("submit", function (event) {
    event.preventDefault();
    const email = document.getElementById("email_login").value;
    const senha = document.getElementById("senha_login").value;

    makeLogin(email, senha);
});

var alertText_cad = document.getElementById('alertText_cad');
var alertText_login = document.getElementById('alertText_login');


// Função para ler todos os usuários da lista
// function listUsers() {
//     return usersList;
// }

// function getUserInfo(email) {
//     const index = findIndexByEmail(email);
//     const usuario = usersList[index];
//     return usuario;
// }

// function findIndexByEmail(email) {
//     for (let i = 0; i < usersList.length; i++) {
//         if (usersList[i].email === email) {
//             return i;
//         }
//     }
//     return -1;
// }

// // Função para atualizar os dados de um usuário
// function updateUser(user, nome, email, senha) {
//     index = findIndexByEmail(user.email);
//     const usuario = usersList[index];
//     usuario.nome = nome;
//     usuario.email = email;
//     usuario.senha = senha;
//     return true;
// }
