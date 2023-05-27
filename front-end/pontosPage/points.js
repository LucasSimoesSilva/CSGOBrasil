let points;

document.addEventListener("DOMContentLoaded", function () {
    points = document.querySelector(".pontos");
});

function showConfirmationPopup(moedas) {
    if (points) {
        Swal.fire({
            title: 'Confirmação de Compra',
            text: 'Você realmente deseja comprar ' + moedas + ' moedas ?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Confirmar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire('Compra Confirmada', 'Moedas adicionadas com sucesso!', 'success');
            } else {
                Swal.fire('Compra Cancelada', 'A compra foi cancelada.', 'info');
            }
        });
    }
}
